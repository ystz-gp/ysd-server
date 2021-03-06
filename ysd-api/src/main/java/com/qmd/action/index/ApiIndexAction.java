package com.qmd.action.index;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.index.*;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.place.PlaceChilder;
import com.qmd.mode.user.UserStartingApp;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.Scrollpic;
import com.qmd.service.activity.ActivityService;
import com.qmd.service.article.ArticleService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.place.PlaceChilderService;
import com.qmd.service.user.UserStartingAppService;
import com.qmd.service.util.ListingService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InterceptorRefs({@InterceptorRef(value = "qmdDefaultStack")})
@Service("apiIndexAction")
public class ApiIndexAction extends ApiBaseAction {

    private static final long serialVersionUID = -7128220348706865257L;

    Logger log = Logger.getLogger(ApiIndexAction.class);

    @Resource
    BorrowService borrowService;
    @Resource
    ListingService listingService;
    @Resource
    ArticleService articleService;
    @Resource
    ActivityService activityService;

    private String deviceToken;
    private String appType;
    private String im;
    private String placeName;

    @Resource
    UserStartingAppService userStartingAppService;
    @Resource
    PlaceChilderService placeChilderService;
    private int way;

    @Action(value = "/api/firstStarting")
    public String apiFirstStarting() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceToken", deviceToken);
        map.put("appType", appType);
        List<UserStartingApp> list = userStartingAppService.queryListByMap(map);
        if (list == null || list.size() < 1) {
            UserStartingApp app = new UserStartingApp();
            app.setDeviceToken(deviceToken);
            app.setAppType(appType);
            app.setImei(im);
            app.setPlaceName(placeName);
            if (StringUtils.isNotEmpty(placeName)) {
                map.clear();
                map.put("random", placeName);
                List<PlaceChilder> plist = placeChilderService.queryListByMap(map);
                if (plist != null && plist.size() > 0) {
                    PlaceChilder pc = plist.get(0);
                    app.setPlaceChilderId(pc.getId());
                }
            } else if (StringUtils.isNotEmpty(appType) && "0".equals(appType)) { //IOS App Store
                map = new HashMap<String, Object>();
                map.put("random", "app store");
                List<PlaceChilder> plist = placeChilderService.queryListByMap(map);
                if (plist != null && plist.size() > 0) {
                    PlaceChilder pc = plist.get(0);
                    app.setPlaceChilderId(pc.getId());
                }
            }
            userStartingAppService.save(app);
        }
        return ajaxJson("R0001", "");
    }

    /**
     * type =4 app 弹出图
     *
     * @return
     */
    @Action(value = "/api/scrollpic")
    public String apiScrollpic() {
        Map<String, Object> mapp = new HashMap<String, Object>();
        mapp.put("type", way);
        Scrollpic s = new Scrollpic();
        List<Scrollpic> scrollpicList = listingService.findScrollpicListMap(mapp);
        if (scrollpicList != null && scrollpicList.size() > 0) {
            s = scrollpicList.get(0);
        }
        IndexImageItem bean = new IndexImageItem();
        bean.setImageUrl(s.getImg());
        bean.setType(1); //
        bean.setTypeTarget(s.getUrl());
        return ajax(JsonUtil.toJson(bean));
    }

    @Action(value = "/api/index")
    public String apiIndex() {

        IndexBean indexBean = new IndexBean();


        // 滚动图片
        List<IndexImageItem> indexImageItemList = new ArrayList<IndexImageItem>();
        IndexImageItem indexImageItem = null;
        Map<String, Object> mapp = new HashMap<String, Object>();
        mapp.put("type", 2);

        List<Scrollpic> scrollpicList = listingService.findScrollpicListMap(mapp);
        for (Scrollpic s : scrollpicList) {
            indexImageItem = new IndexImageItem();
            indexImageItem.setImageUrl(s.getImg());
            indexImageItem.setType(1); // 类型：0无1网页2项目类型3文章id
            indexImageItem.setTypeTarget(s.getUrl());   // 类型目标：网页url（类型1时启用）类型2时，为项目类型。类型3时，为文章id
            indexImageItemList.add(indexImageItem);
        }
        indexBean.setIndexImageItemList(indexImageItemList);

        // 推荐项目
        IndexBorrow indexBorrow = new IndexBorrow();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderBy", "b.show_top desc ,b.show_sort desc,CAST(b.schedule as SIGNED) asc,b.create_date desc");

//		int[] types = {14,15,16};
        int[] types = {16};
        map.put("types", types);

        int[] array = {1, 3, 5, 7};
        map.put("array", array);
        if (pager == null) {
            pager = new Pager();
        }
        pager.setPageSize(1);
        pager = borrowService.queryBorrowList(pager, map);
        List<Borrow> borrowList = (List<Borrow>) pager.getResult();

        if (borrowList != null && borrowList.size() > 0) {
            Borrow borrow = borrowList.get(0);

            indexBorrow.setId(borrow.getId());
            indexBorrow.setName(borrow.getName());
            indexBorrow.setType(borrow.getType());
            indexBorrow.setBusinessType(borrow.getIsVouch());
            indexBorrow.setBusinessCode(borrow.getBusinessCode());// 项目编号
            indexBorrow.setTimeLimit(borrow.getTimeLimit());// 借款天数
            indexBorrow.setAccount(borrow.getAccount());// 借款总金额
//			indexBorrow.setApr(borrow.getApr());// 年利率
            indexBorrow.setApr(CommonUtil.setPriceScale(borrow.getVaryYearRate().multiply(new BigDecimal(100))).doubleValue());// 年利率
            indexBorrow.setAward(borrow.getAward());// 投标奖励方式
            indexBorrow.setFunds(borrow.getFunds());// 投标金额比例奖励
            indexBorrow.setStatus(borrow.getStatus());// 状态码0-发表未审核；1-审核通过；2-审核未通过；3-满标审核通过；4-满标审核未通过；5-等待满标审核；6-过期或撤回；7-已还完;8-删除状态
            indexBorrow.setShowBorrowStatus(borrow.getShowBorrowStatus());// 状态名称
            indexBorrow.setSchedule(borrow.getSchedule()); // 投标的百分比
            indexBorrow.setTenderSize(StringUtils.isEmpty(borrow
                    .getTenderTimes()) ? 0 : Integer.parseInt(borrow
                    .getTenderTimes()));// 已投次数
            indexBorrow.setTenderSubject(0);// 新客标记
            indexBorrow.setVerifyTime(borrow.getVerifyTime());// 审核时间
            indexBorrow.setShowBorrowType(borrow.getType());// 类型名称
            indexBorrow.setLowestAccount(borrow.getLowestAccount());
            indexBorrow.setBalance(borrow.getBalance());
            indexBorrow.setBaseApr(CommonUtil.setPriceScale(borrow.getBaseApr().multiply(new BigDecimal(100))));
            indexBorrow.setAwardApr(CommonUtil.setPriceScale(borrow.getAwardApr().multiply(new BigDecimal(100))));
        }
        indexBean.setIndexBorrow(indexBorrow);

        // 项目类型
        List<IndexTypeItem> indexTypeItemList = new ArrayList<IndexTypeItem>();
        IndexTypeItem indexTypeItem = new IndexTypeItem();
        indexTypeItem.setType("14");
        indexTypeItem.setShowBorrowType("天标");
        indexTypeItem.setTypeDescribe("描述");
        indexTypeItem.setTypeImage("");
        indexTypeItemList.add(indexTypeItem);

        indexTypeItem = new IndexTypeItem();
        indexTypeItem.setType("15");
        indexTypeItem.setShowBorrowType("月标");
        indexTypeItem.setTypeDescribe("描述");
        indexTypeItem.setTypeImage("");
        indexTypeItemList.add(indexTypeItem);

        indexTypeItem = new IndexTypeItem();
        indexTypeItem.setType("16");
        indexTypeItem.setShowBorrowType("新手标");
        indexTypeItem.setTypeDescribe("描述");
        indexTypeItem.setTypeImage("");
        indexTypeItemList.add(indexTypeItem);

        indexBean.setIndexTypeItemList(indexTypeItemList);

        map = new HashMap<String, Object>();
        map.put("sign", "site_notice");
        Integer n = articleService.getArticleBySignCount(map);
        indexBean.setNotReadNum(n == null ? 0 : n);//未读

        indexBean.setUserFlg(0);//
        if (getLoginUser() != null) {
            indexBean.setUserFlg(1);//
        }
        return ajax(JsonUtil.toJson(indexBean));

    }

    @Action(value = "/api/notFound")
    public void notFound() {
        ajaxJson("S0404", ApiConstantUtil.S0404);
    }

    @Action(value = "/api/exception")
    public void exception() {
        ajaxJson("S0001", ApiConstantUtil.S0001);
    }

    @Action(value = "/api/exceptionTest")
    public void exceptionTest() {
        int a = 2;
        int b = 0;
        double c = 1 / b;
        ajaxJson("M0010", ApiConstantUtil.M0010);
    }

    /**
     * 新版本废弃
     */
    @Action(value = "/api/versionfq")
    public String versionfq() {
        Listing l = listingService.getChildListingBySignList("new_version").get(0);
        VersionBean bean = new VersionBean();
        bean.setUrl(l.getDescription());
        bean.setVersionCode(l.getOrderList());
        bean.setVersionName(l.getName());
        return ajax(JsonUtil.toJson(bean));
    }


    @Action(value = "/api/version")
    public String version() {
        Listing l = listingService.getListing(2051);//版本号
        Listing ll = listingService.getListing(2052);//版本信息说明
        VersionBean bean = new VersionBean();
        Map m = new HashMap();
        m.put("rcd", "R0001");
        m.put("rmg", "");
        if (l != null && ll != null) {
            m.put("rmg", ll.getDescription());
            m.put("versionCode", l.getOrderList());
            m.put("versionName", l.getName());
            m.put("url", l.getDescription());
        }
        System.out.println(m);
        return ajax(m);
    }


    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }


    /**
     * qxw h5
     *
     * @return
     */
    @Action(value = "/api/indexH")
    public String apiIndexH() {

        IndexBean indexBean = new IndexBean();

        //统计2个数据；
        //	int totalUserNum=Integer.parseInt(listingService.getKeyValue("index_report_statistics_user_count"));
        //	int totalTenderMoney=Integer.parseInt(listingService.getKeyValue("index_report_statistics_tender"));

        try {

            Map<String, Object> map = new HashMap<String, Object>();
            String totalUserNum = "0";
            String totalTenderMoney = "0";
            String totalgetMoney = "0";
            String signForTotalUserNum = "total_user_num";
            Listing listingForTotalUserNum = listingService.getListingBysign(signForTotalUserNum);
            if (listingForTotalUserNum != null && listingForTotalUserNum.getIsEnabled()) {
                String totalUserNumKeyValue = listingForTotalUserNum.getKeyValue();
                if (StringUtils.isNumeric(totalUserNumKeyValue)) {
                    totalUserNum = totalUserNumKeyValue;
                }
            } else {
                map.put("key", 1);
                totalUserNum = listingService.selectSumHomeData(map);
            }

            String signForTotalTenderMoney = "total_tender_money";
            Listing listingForTotalTenderMoney = listingService.getListingBysign(signForTotalTenderMoney);
            if (listingForTotalTenderMoney != null && listingForTotalTenderMoney.getIsEnabled()) {
                String totalTenderKeyValue = listingForTotalTenderMoney.getKeyValue();
                if (StringUtils.isNumeric(totalTenderKeyValue)) {
                    totalTenderMoney = totalTenderKeyValue;
                }
            } else {
                map.put("key", 2);
                totalTenderMoney=listingService.selectSumHomeData(map);
            }

            String signForGetMoney = "total_get_money";
            Listing listingForGetMoney = listingService.getListingBysign(signForGetMoney);
            if (listingForGetMoney != null && listingForGetMoney.getIsEnabled()) {
                String getMoneyKeyValue = listingForGetMoney.getKeyValue();
                if (StringUtils.isNumeric(getMoneyKeyValue)) {
                    totalgetMoney = getMoneyKeyValue;
                }
            } else {
                map.put("key", 3);
                totalgetMoney= listingService.selectSumHomeData(map);
            }

            indexBean.setTotalUserNum(NumberUtils.toInt(totalUserNum));//注册人数
            indexBean.setTotalTenderMoney(NumberUtils.toInt(totalTenderMoney));//用户投资总金额
        } catch (Exception e) {
            // 注册人数等相关数据,使用新的字典,total_user_num added by xsc
//            indexBean.setTotalUserNum(Integer.parseInt(listingService.getKeyValue("index_report_statistics_user_count")));//注册人数
//            indexBean.setTotalTenderMoney(Integer.parseInt(listingService.getKeyValue("index_report_statistics_tender")));//用户投资总金额
            log.error(e);
            indexBean.setTotalUserNum(0);//注册人数
            indexBean.setTotalTenderMoney(0);//用户投资总金额
        }


        Map<String, Object> mapnull = new HashMap<String, Object>();
        indexBean.setActivity(activityService.queryLastActivity(mapnull));

        // 滚动图片
        List<IndexImageItem> indexImageItemList = new ArrayList<IndexImageItem>();
        IndexImageItem indexImageItem = null;
        Map<String, Object> mapp = new HashMap<String, Object>();
        mapp.put("type", 2);

        List<Scrollpic> scrollpicList = listingService.findScrollpicListMap(mapp);
        for (Scrollpic s : scrollpicList) {
            indexImageItem = new IndexImageItem();
            indexImageItem.setImageUrl(s.getImg());
            indexImageItem.setType(1); // 类型：0无1网页2项目类型3文章id
            indexImageItem.setTypeTarget(s.getUrl());   // 类型目标：网页url（类型1时启用）类型2时，为项目类型。类型3时，为文章id
            indexImageItemList.add(indexImageItem);
        }
        indexBean.setIndexImageItemList(indexImageItemList);

        // 推荐项目
        IndexBorrow indexBorrow = new IndexBorrow();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderBy", "b.show_top desc ,CAST(b.schedule as SIGNED) asc,b.show_sort desc,b.create_date desc");

//		int[] types = {14,15,16};
        int[] types = {16};
        map.put("types", types);

        int[] array = {1, 3, 5, 7};
        map.put("array", array);
        if (pager == null) {
            pager = new Pager();
        }
        pager.setPageSize(1);
        pager = borrowService.queryBorrowList(pager, map);
        List<Borrow> borrowList = (List<Borrow>) pager.getResult();

        if (borrowList != null && borrowList.size() > 0) {
            Borrow borrow = borrowList.get(0);

            indexBorrow.setId(borrow.getId());
            indexBorrow.setName(borrow.getName());
            indexBorrow.setType(borrow.getType());
            indexBorrow.setBusinessType(borrow.getIsVouch());
            indexBorrow.setBusinessCode(borrow.getBusinessCode());// 项目编号
            indexBorrow.setTimeLimit(borrow.getTimeLimit());// 借款天数
            indexBorrow.setAccount(borrow.getAccount());// 借款总金额
//			indexBorrow.setApr(borrow.getApr());// 年利率
            indexBorrow.setApr(CommonUtil.setPriceScale(borrow.getVaryYearRate().multiply(new BigDecimal(100))).doubleValue());// 年利率
            indexBorrow.setAward(borrow.getAward());// 投标奖励方式
            indexBorrow.setFunds(borrow.getFunds());// 投标金额比例奖励
            indexBorrow.setStatus(borrow.getStatus());// 状态码0-发表未审核；1-审核通过；2-审核未通过；3-满标审核通过；4-满标审核未通过；5-等待满标审核；6-过期或撤回；7-已还完;8-删除状态
            indexBorrow.setShowBorrowStatus(borrow.getShowBorrowStatus());// 状态名称
            indexBorrow.setSchedule(borrow.getSchedule()); // 投标的百分比
            indexBorrow.setTenderSize(StringUtils.isEmpty(borrow
                    .getTenderTimes()) ? 0 : Integer.parseInt(borrow
                    .getTenderTimes()));// 已投次数
            indexBorrow.setTenderSubject(0);// 新客标记
            indexBorrow.setVerifyTime(borrow.getVerifyTime());// 审核时间
            indexBorrow.setShowBorrowType(borrow.getType());// 类型名称
            indexBorrow.setLowestAccount(borrow.getLowestAccount());
            indexBorrow.setBalance(borrow.getBalance());
            indexBorrow.setBaseApr(CommonUtil.setPriceScale(borrow.getBaseApr().multiply(new BigDecimal(100))));
            indexBorrow.setAwardApr(CommonUtil.setPriceScale(borrow.getAwardApr().multiply(new BigDecimal(100))));
        }
        indexBean.setIndexBorrow(indexBorrow);

        // 项目类型
        List<IndexTypeItem> indexTypeItemList = new ArrayList<IndexTypeItem>();
        IndexTypeItem indexTypeItem = new IndexTypeItem();
        indexTypeItem.setType("14");
        indexTypeItem.setShowBorrowType("天标");
        indexTypeItem.setTypeDescribe("描述");
        indexTypeItem.setTypeImage("");
        indexTypeItemList.add(indexTypeItem);

        indexTypeItem = new IndexTypeItem();
        indexTypeItem.setType("15");
        indexTypeItem.setShowBorrowType("月标");
        indexTypeItem.setTypeDescribe("描述");
        indexTypeItem.setTypeImage("");
        indexTypeItemList.add(indexTypeItem);

        indexTypeItem = new IndexTypeItem();
        indexTypeItem.setType("16");
        indexTypeItem.setShowBorrowType("新手标");
        indexTypeItem.setTypeDescribe("描述");
        indexTypeItem.setTypeImage("");
        indexTypeItemList.add(indexTypeItem);

        indexBean.setIndexTypeItemList(indexTypeItemList);

        map = new HashMap<String, Object>();
        map.put("sign", "site_notice");
        Integer n = articleService.getArticleBySignCount(map);
        indexBean.setNotReadNum(n == null ? 0 : n);//未读

        indexBean.setUserFlg(0);//
        if (getLoginUser() != null) {
            indexBean.setUserFlg(1);//
        }
        return ajax(JsonUtil.toJson(indexBean));

    }

}
