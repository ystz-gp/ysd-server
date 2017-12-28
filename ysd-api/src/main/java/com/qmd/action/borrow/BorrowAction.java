 package com.qmd.action.borrow;

 import com.qmd.action.base.BaseAction;
 import com.qmd.bean.PageBean;
 import com.qmd.bean.borrow.*;
 import com.qmd.bean.center.UserAuto;
 import com.qmd.bean.user.UserHongbaoItem;
 import com.qmd.mode.borrow.Borrow;
 import com.qmd.mode.borrow.BorrowRepaymentDetail;
 import com.qmd.mode.borrow.BorrowTender;
 import com.qmd.mode.user.User;
 import com.qmd.mode.user.UserHongbao;
 import com.qmd.mode.user.UserInfo;
 import com.qmd.mode.user.UserRepaymentDetail;
 import com.qmd.mode.util.NodeBean;
 import com.qmd.service.borrow.*;
 import com.qmd.service.mail.MailService;
 import com.qmd.service.user.*;
 import com.qmd.service.util.ListingService;
 import com.qmd.util.*;
 import com.qmd.util.bean.CarInfoJson;
 import com.qmd.util.bean.RepaymentDetail;
 import com.qmd.util.bean.RepaymentInfo;
 import net.sf.json.JSONArray;
 import net.sf.json.JSONException;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.InterceptorRef;
 import org.apache.struts2.convention.annotation.InterceptorRefs;
 import org.apache.struts2.convention.annotation.Result;
 import org.apache.struts2.json.annotations.JSON;
 import org.springframework.beans.BeanUtils;
 import org.springframework.stereotype.Service;

 import javax.annotation.Resource;
 import java.io.File;
 import java.math.BigDecimal;
 import java.util.*;
 import java.util.regex.Pattern;


@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
@Service("borrowAction")
public class BorrowAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(BorrowAction.class);

	/**
	 * 获取标列表
	 * 
	 */
	// Pager page;
	private String mycode;// 验证码
	private String flag;// 标记
	private Map<String, String> parameterMap;// 参数
	private String minDate;// 起始时间
	private String maxDate;// 截止时间
	private String keywords;
	private String status;// 借款还款状态
	private String useReason;
	private String timeLimit;
	private String accountStart;
	private String accountEnd;
	private String fundsStart;
	private String fundsEnd;
	public String bType;
	public String bStarType;
	private String timeLimitStart;
	private String timeLimitEnd;
	private String aprStart;
	private String aprEnd;
	private String yearLimitStart;
	private String yearaprStart;
	private String yearaprEnd;
	private Borrow borrow;
	public Integer bId;
	private String errorMsg;
	private String errorCode;
	private String interest;
	public String[] bStatus;
	private File[] borImagesFile;
	private String[] borImageList;
	private List<NoteImgList> voucherImgList;
	private List<NoteImg> carImgList;
	private CarInfoJson carInfo;
	private VerifyMessJson verifyMessJson;
	private String borImageFirst;
	private String typeName;// 获取的标类型名称
	private String effectiveTime;// 获取的截止日名称
	private String repayMes;// 还款设置的天数和每期还款金额
	private Integer borrStatus;
	private String strMess = "";// 修改标时返回的还款记录
	private BigDecimal userAbleMoney;
	private BigDecimal continueTotal;
	private int borrowType;
	private int borrowIsday;
	private List<NoteImg> borrowImgList;
	private int showContinueTotal;
	private int showUserAbleMoney;
	private int maxWanderPiece;
	private int minWanderPiece;
	private int maxWanderPieceContinue;
	private int minWanderPieceContinue;
	private Integer wanderPlan;
	private List<NodeBean> nodeList;
	Map<String, Object> root = new HashMap<String, Object>();
	List<BorrowRepaymentDetail> borrowRepDetailList;
	private MonthPaymentDetail[] monthPaymentList;
	private String dxpwd;// 定向密码
	private Date nowDate;
	private int countDown;
	private String agreementId;// 协议号
	private String lastRdate;// 最后还款标完成日期
	private String dayFlg;
	private PledgeRepayPlanDetail pledgeRepayPlanDetail;
	private MonthRepayPlanDetail monthRepayPlanDetail;
	private WanderRepayPlanDetail wanderRepayPlanDetail;
	private UserInfo userInfo;
	private String agreement;// 判断是否出现借款协议
	private String lowestMoney;// 最小投资金额

	private String tenderMoneyContinue;

	User user;
	BorrowRepaymentDetail borrowRepaymentDetail;
	private List<BorrowRepaymentDetail> borrowRepaymentDetailList;
	List<BorrowTender> borrowTenderList;
	List<Borrow> userBorrowList;
	List<UserRepaymentDetail> userRepayDetailList;
	BorrowTender borrowTender;
	private PaymentView retBorrow;
	private String isDxb1;

	private String[] vouchers;
	private String[] vouchersTitle;

	private BigDecimal tenderAbleMoney;
	private BigDecimal tenderContinueMoney;

	private RepaymentInfo repaymentInfo;
	BorrowBean borrowBean;
	BorrowRepaymentInfo bri ;
	
	private Integer t;
	private Integer r;
	private Integer l;
	private Integer p;
	private String bq;
	
	
	
	private String type ;
	private UserAuto userAuto;
	private String safepwd;// 安全密码
	private String tenderMoney;//投资金额
	private String businessType;
	private Integer limitLevel;// 项目期限级别：ConstantUtil.LIST_QUERY_LIMIT
	private Integer desc;// 是否倒序排序
	
	
	

	@Resource
	BorrowService borrowService;
	@Resource
	BorrowSecondService borrowSecondService;
	@Resource
	BorrowWanderService borrowWanderService;
	// CalculationUtil cal;
	@Resource
	UserAccountService userAccountService;
	@Resource
	BorrowTenderService borrowTenderService;
	@Resource
	UserService userService;
	@Resource
	UserAccountDetailService userAccountDetailService;
	@Resource
	BorrowRepaymentDetailService borrowRepaymentDetailService;
	@Resource
	InterestCalUtil interestCalUtil;
	@Resource
	UserRepaymentDetailService userRepaymentDetailService;
	@Resource
	ListingService listingService;
	@Resource
	UserInfoService userinfoService;
	@Resource
	MailService mailService;
	@Resource
	BorrowPromoteService borrowPromoteService;
	@Resource
	UserHongbaoService userHongbaoService;

	
	@Action(value="/borrow/detail",results={@Result(name="success", location="/content/h5borrow/borrowDetail.ftl", type="freemarker")})
	public String borrowDetail()  {
			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));
			if (borrow == null) {
				msg="此标不存在!";
				return "error_ftl";
			}
			if (borrow.getStatus() == 0) {
				msg="此标未发布!";
				return "error_ftl";
			}
			if (borrow.getValidTime() == null
					|| "".equals(borrow.getValidTime())) {
				borrow.setValidTime("1");// 有效天数不存在是默认设为一天
			}

			nowDate = new Date();

			// 设置过期时间
			Calendar c = Calendar.getInstance();
			c.setTime(borrow.getVerifyTime());
			c.set(Calendar.DAY_OF_MONTH,
					c.get(Calendar.DAY_OF_MONTH)
							+ Integer.valueOf(borrow.getValidTime()));
			borrow.setOverDate(c.getTime());
			
			borrowBean = new BorrowBean();
			BeanUtils.copyProperties(borrow, borrowBean);
			borrowBean.setPwdFlg("1".equals(borrow.getIsDxb())?1:0);
			borrowBean.setYearApr(CommonUtil.setPriceScale(borrow.getVaryYearRate().multiply(new BigDecimal(100))));
			borrowBean.setBusinessType(borrow.getIsVouch());
			borrowBean.setCapitalEnsure("本息担保");
			borrowBean.setAwardApr(CommonUtil.setPriceScale(borrow.getAwardApr().multiply(new BigDecimal(100))));
			borrowBean.setBaseApr(CommonUtil.setPriceScale(borrow.getBaseApr().multiply(new BigDecimal(100))));
			
			borrowBean.setCurrentDate(nowDate);
			borrowBean.setAwardScale(borrow.getAwardScale());
			//awardScale 5%
			//获取登录用户，可用红包列表
			User u =getLoginUser(); 
			if(u != null){
				reloadUser();
				 u =getLoginUser(); 
				 borrowBean.setUserFlg(1);//
				 borrowBean.setUserAbleMoney(u.getAbleMoney());
				 borrowBean.setTasteMoney(u.getTasteMoney());
				 borrowBean.setRealStatus(u.getRealStatus());
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", u.getId());
				map.put("status", 0);
				map.put("orderBy", " t.end_time asc ");
				map.put("pageStart", 0);
				map.put("pageSize", 12);
				List<UserHongbao> hongbaoList = userHongbaoService.queryListByMap(map);
				List<UserHongbaoItem> hbItemList = new ArrayList<UserHongbaoItem>();
				
				if(hongbaoList  != null && hongbaoList.size() > 0){
					for(UserHongbao hb:hongbaoList){
						UserHongbaoItem hbItem = new UserHongbaoItem();
						BeanUtils.copyProperties(hb, hbItem);
						hbItemList.add(hbItem);
					}
					borrowBean.setUserHongbaoItem(hbItemList);
				}
			}
		return SUCCESS;

	}
	
	@Action(value="/borrowPic/detail",results={@Result(name="success", location="/content/h5borrow/borrowPic.ftl", type="freemarker")})
	public String borrowPic()  {
			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));
			if (borrow == null) {
				msg="此标不存在!";
				return "error_ftl";
			}
			if (borrow.getStatus() == 0) {
				msg="此标未发布!";
				return "error_ftl";
			}
			if (borrow.getValidTime() == null
					|| "".equals(borrow.getValidTime())) {
				borrow.setValidTime("1");// 有效天数不存在是默认设为一天
			}

			nowDate = new Date();

			// 设置过期时间
			Calendar c = Calendar.getInstance();
			c.setTime(borrow.getVerifyTime());
			c.set(Calendar.DAY_OF_MONTH,
					c.get(Calendar.DAY_OF_MONTH)
							+ Integer.valueOf(borrow.getValidTime()));
			borrow.setOverDate(c.getTime());
			
			borrowImgList = new ArrayList<NoteImg>();

			String img = borrow.getBorImage();
			if (img != null && !"".equals(img.trim())) {

				try {
					JSONArray jsonarray = JSONArray.fromObject(img);
					// System.out.println(jsonarray);
					borrowImgList = (List<NoteImg>) JSONArray.toList(
							jsonarray, NoteImg.class);
				} catch (JSONException je) {
					NoteImg ni = new NoteImg();
					ni.setName("");
					ni.setUrl(img);
					borrowImgList.add(ni);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
			
		return SUCCESS;

	}
	/**
	 * 车辆心信息
	 * id 项目ID
	 * @return
	 */
	@Action(value="/car/detail")
	public String apiCarDetail()  {

			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));
			if (borrow == null) {
//				ajaxJson("M0010",ApiConstantUtil.M0010);
			}
			if (borrow.getStatus() == 0) {
//				ajaxJson("M0011",ApiConstantUtil.M0011);
			}
			
			CarInfoBean bean = new CarInfoBean();
			BeanUtils.copyProperties(borrow, bean);
			if (getLoginUser()!=null) {
				bean.setUserFlg(1);//
				bean.setUserAbleMoney(getLoginUser().getAbleMoney());
			}
			
			return ajax(JsonUtil.toJson(bean));

	}


	
	/**
	 * 投资记录
	 * id 项目ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value="/borrowTenderList/detail")
	public String borrowTenderList(){
		try{
			BorrowTenderList btList = new BorrowTenderList();
			Map<String, Object> map = new HashMap<String, Object>();
	
			map.put("borrowId", id);
			BorrowTender bt = new BorrowTender();
			bt.setBorrowId(Integer.parseInt(id));
			pager = borrowTenderService.queryPageByOjbect(bt, pager.getPageSize(), pager.getPageNumber(), " q1.create_date desc ");
			
			if(pager.getTotalCount() ==0){
//				return ajaxJson("M0006",ApiConstantUtil.M0006);
			}
			
			List<BorrowTenderItem> btiList = (List<BorrowTenderItem>) pager.getResult();
			PageBean pb = new PageBean();
			pb.setPageNumber(pager.getPageNumber());
			pb.setPageCount(pager.getPageCount());
			pb.setPageSize(pager.getPageSize());
			pb.setTotalCount(pager.getTotalCount());
			btList.setPageBean(pb);
			
			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));
			btList.setApr(CommonUtil.setPriceScale(borrow.getVaryYearRate().multiply(new BigDecimal(100))).toString());//年利率
			
			btList.setBorrowTenderItemList(btiList);
			
			return ajax(JsonUtil.toJson(btList));
		}catch (Exception e) {
			e.printStackTrace();
//			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
		return SUCCESS;
	}

	/**
	 * 基本信息
	 * id 项目ID
	 * @return
	 */
	@Action(value="/repaymentInfo/detail",results={@Result(name="success", location="/content/h5borrow/basicBorrowDetail.ftl", type="freemarker")})
	public String repaymentInfoList(){
		try{

			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id",borrow.getUserId());
			User user = this.userService.getUser(map);
			if (borrow == null) {
				msg="此标不存在!";
				return "error_ftl";
			}
			
			repaymentInfo = PromoteUtil.promotePlan(Integer.parseInt(borrow
					.getIsday()), Integer.parseInt(borrow.getStyle()),
					Integer.parseInt(borrow.getTimeLimit()), borrow
							.getDivides(),
					new BigDecimal(borrow.getAccount()), new BigDecimal(
							borrow.getApr()));
			
			bri = new BorrowRepaymentInfo();
			bri.setContent(borrow.getContent());
			String str ="";
			if(StringUtils.isNotEmpty(user.getRealName())){
				str = user.getRealName();
				
			}
			bri.setRealName(str);
			if(StringUtils.isNotEmpty(borrow.getUseReason())){
				bri.setUseReason(borrow.getUseReason());
			}
			if(StringUtils.isNotEmpty(borrow.getBorStages())){
				bri.setPaymentSource(borrow.getBorStages());
			}
			bri.setDebtMess(borrow.getEachTime());
			bri.setAccount(CommonUtil.setPriceScale(repaymentInfo.getAccount()).toString());
			bri.setCapital(CommonUtil.setPriceScale(repaymentInfo.getCapital()).toString());
			bri.setInterest(CommonUtil.setPriceScale(repaymentInfo.getInterest()).toString());
			
			bri.setOrderNum(repaymentInfo.getOrderNum());
			bri.setRepaymentDateInt(repaymentInfo.getRepaymentDateInt());
			
			List<BorrowRepaymentDetailList> rdList = new ArrayList<BorrowRepaymentDetailList>();
			
			for(RepaymentDetail rd: repaymentInfo.getRepaymentDetailList()){
				BorrowRepaymentDetailList brd = new BorrowRepaymentDetailList();
				
				brd.setAccount(CommonUtil.setPriceScale(rd.getAccount()).toString());
				brd.setCapital(CommonUtil.setPriceScale(rd.getCapital()).toString());
				brd.setInterest(CommonUtil.setPriceScale(rd.getInterest()).toString());
				brd.setOrderNum(rd.getOrderNum());
				brd.setRepaymentDateInt(rd.getRepaymentDateInt());
				rdList.add(brd);
			}
			
			bri.setRepaymentDetailList(rdList);
			if("0".equals(borrow.getIsday())){
				bri.setIsdayString("月");
			}else if("1".equals(borrow.getIsday())){
				bri.setIsdayString("天");
			}
			
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			 msg="程序内部错误!";
			return "error_ftl";
		}
	}
	
	
	
	
	
	
	
	
	
	

	public String getShowButtonName() {
		if (borrow == null)
			return "立即投资";
		if (borrow.getStatus() == 1) {
			if (countDown == -1) {
				return "已过期";
			} else if (countDown == 0) {
				return "等待复审";
			} else {
				return "立即投标";
			}
		} else if (borrow.getStatus() == 3) {
			return "收益中";
		} else if (borrow.getStatus() == 5) {
			return "等待复审";
		} else if (borrow.getStatus() == 7) {
			return "已还完";
		}
		return "立即投资";

	}

	public int getShowButtonNameFlg() {
		if (borrow == null)
			return 1;
		if (borrow.getStatus() == 1) {
			if (countDown == -1) {
				return 0;
			} else if (countDown == 0) {
				return 0;
			} else {
				return 1;
			}
		} else if (borrow.getStatus() == 3) {
			return 0;
		} else if (borrow.getStatus() == 5) {
			return 0;
		} else if (borrow.getStatus() == 7) {
			return 0;
		}
		return 1;

	}

	public String[] getVouchers() {
		return vouchers;
	}

	public void setVouchers(String[] vouchers) {
		this.vouchers = vouchers;
	}

	public List<NoteImgList> getVoucherImgList() {
		return voucherImgList;
	}

	public void setVoucherImgList(List<NoteImgList> voucherImgList) {
		this.voucherImgList = voucherImgList;
	}

	public String getBorImageFirst() {
		return borImageFirst;
	}

	public void setBorImageFirst(String borImageFirst) {
		this.borImageFirst = borImageFirst;
	}

	public String[] getVouchersTitle() {
		return vouchersTitle;
	}

	public void setVouchersTitle(String[] vouchersTitle) {
		this.vouchersTitle = vouchersTitle;
	}

	public String getLowestMoney() {
		return lowestMoney;
	}

	public void setLowestMoney(String lowestMoney) {
		this.lowestMoney = lowestMoney;
	}

	public int getShowUserAbleMoney() {
		return showUserAbleMoney;
	}

	public void setShowUserAbleMoney(int showUserAbleMoney) {
		this.showUserAbleMoney = showUserAbleMoney;
	}

	public BigDecimal getTenderAbleMoney() {
		return tenderAbleMoney;
	}

	public void setTenderAbleMoney(BigDecimal tenderAbleMoney) {
		this.tenderAbleMoney = tenderAbleMoney;
	}

	public BigDecimal getTenderContinueMoney() {
		return tenderContinueMoney;
	}

	public void setTenderContinueMoney(BigDecimal tenderContinueMoney) {
		this.tenderContinueMoney = tenderContinueMoney;
	}

	public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList() {
		return borrowRepaymentDetailList;
	}

	public void setBorrowRepaymentDetailList(
			List<BorrowRepaymentDetail> borrowRepaymentDetailList) {
		this.borrowRepaymentDetailList = borrowRepaymentDetailList;
	}

	public RepaymentInfo getRepaymentInfo() {
		return repaymentInfo;
	}

	public void setRepaymentInfo(RepaymentInfo repaymentInfo) {
		this.repaymentInfo = repaymentInfo;
	}
	
	public String getRepayMes() {
		return repayMes;
	}

	public void setRepayMes(String repayMes) {
		this.repayMes = repayMes;
	}

	public BorrowTender getBorrowTender() {
		return borrowTender;
	}

	public void setBorrowTender(BorrowTender borrowTender) {
		this.borrowTender = borrowTender;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}
	public String getMycode() {
		return mycode.toLowerCase();
	}

	public void setMycode(String mycode) {
		this.mycode = mycode;
	}

	// public Pager getPage() {
	// return page;
	// }
	// public void setPage(Pager page) {
	// this.page = page;
	// }
	public String[] getbStatus() {
		return bStatus;
	}

	public void setbStatus(String[] bStatus) {
		this.bStatus = bStatus;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public BorrowRepaymentDetail getBorrowRepaymentDetail() {
		return borrowRepaymentDetail;
	}

	public void setBorrowRepaymentDetail(
			BorrowRepaymentDetail borrowRepaymentDetail) {
		this.borrowRepaymentDetail = borrowRepaymentDetail;
	}

	public List<BorrowRepaymentDetail> getBorrowRepDetailList() {
		return borrowRepDetailList;
	}

	public void setBorrowRepDetailList(
			List<BorrowRepaymentDetail> borrowRepDetailList) {
		this.borrowRepDetailList = borrowRepDetailList;
	}

	public BorrowRepaymentDetailService getBorrowRepaymentDetailService() {
		return borrowRepaymentDetailService;
	}

	public void setBorrowRepaymentDetailService(
			BorrowRepaymentDetailService borrowRepaymentDetailService) {
		this.borrowRepaymentDetailService = borrowRepaymentDetailService;
	}

	public String getTimeLimitStart() {
		return timeLimitStart;
	}

	public void setTimeLimitStart(String timeLimitStart) {
		this.timeLimitStart = timeLimitStart;
	}

	public String getTimeLimitEnd() {
		return timeLimitEnd;
	}

	public void setTimeLimitEnd(String timeLimitEnd) {
		this.timeLimitEnd = timeLimitEnd;
	}

	public String getAprStart() {
		return aprStart;
	}

	public void setAprStart(String aprStart) {
		this.aprStart = aprStart;
	}

	public String getAprEnd() {
		return aprEnd;
	}

	public void setAprEnd(String aprEnd) {
		this.aprEnd = aprEnd;
	}

	public String getYearLimitStart() {
		return yearLimitStart;
	}

	public void setYearLimitStart(String yearLimitStart) {
		this.yearLimitStart = yearLimitStart;
	}

	public String getYearaprStart() {
		return yearaprStart;
	}

	public void setYearaprStart(String yearaprStart) {
		this.yearaprStart = yearaprStart;
	}

	public String getYearaprEnd() {
		return yearaprEnd;
	}

	public void setYearaprEnd(String yearaprEnd) {
		this.yearaprEnd = yearaprEnd;
	}

	public String getbType() {
		return bType;
	}

	public void setbType(String bType) {
		this.bType = bType;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getUseReason() {
		return useReason;
	}

	public void setUseReason(String useReason) {
		this.useReason = useReason;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getAccountStart() {
		return accountStart;
	}

	public void setAccountStart(String accountStart) {
		this.accountStart = accountStart;
	}

	public String getAccountEnd() {
		return accountEnd;
	}

	public void setAccountEnd(String accountEnd) {
		this.accountEnd = accountEnd;
	}

	public String getFundsStart() {
		return fundsStart;
	}

	public void setFundsStart(String fundsStart) {
		this.fundsStart = fundsStart;
	}

	public String getFundsEnd() {
		return fundsEnd;
	}

	public void setFundsEnd(String fundsEnd) {
		this.fundsEnd = fundsEnd;
	}

	public Integer getbId() {
		return bId;
	}

	public void setbId(Integer bId) {
		this.bId = bId;
	}

	@JSON(serialize = true)
	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Borrow> getUserBorrowList() {
		return userBorrowList;
	}

	public void setUserBorrowList(List<Borrow> userBorrowList) {
		this.userBorrowList = userBorrowList;
	}

	public List<BorrowTender> getBorrowTenderList() {
		return borrowTenderList;
	}

	public void setBorrowTenderList(List<BorrowTender> borrowTenderList) {
		this.borrowTenderList = borrowTenderList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Map<String, String> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public File[] getBorImagesFile() {
		return borImagesFile;
	}

	public void setBorImagesFile(File[] borImagesFile) {
		this.borImagesFile = borImagesFile;
	}

	public String[] getBorImageList() {
		return borImageList;
	}

	public void setBorImageList(String[] borImageList) {
		this.borImageList = borImageList;
	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public MonthPaymentDetail[] getMonthPaymentList() {
		return monthPaymentList;
	}

	public void setMonthPaymentList(MonthPaymentDetail[] monthPaymentList) {
		this.monthPaymentList = monthPaymentList;
	}

	public PaymentView getRetBorrow() {
		return retBorrow;
	}

	public void setRetBorrow(PaymentView retBorrow) {
		this.retBorrow = retBorrow;
	}

	public Integer getBorrStatus() {
		return borrStatus;
	}

	public void setBorrStatus(Integer borrStatus) {
		this.borrStatus = borrStatus;
	}

	public String getStrMess() {
		return strMess;
	}

	public void setStrMess(String strMess) {
		this.strMess = strMess;
	}

	public BigDecimal getUserAbleMoney() {
		return userAbleMoney;
	}

	public void setUserAbleMoney(BigDecimal userAbleMoney) {
		this.userAbleMoney = userAbleMoney;
	}

	public List<NodeBean> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<NodeBean> nodeList) {
		this.nodeList = nodeList;
	}

	public int getMaxWanderPiece() {
		return maxWanderPiece;
	}

	public void setMaxWanderPiece(int maxWanderPiece) {
		this.maxWanderPiece = maxWanderPiece;
	}

	public String getDxpwd() {
		return dxpwd;
	}

	public void setDxpwd(String dxpwd) {
		this.dxpwd = dxpwd;
	}

	public String getSafepwd() {
		return safepwd;
	}

	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
	}

	public PledgeRepayPlanDetail getPledgeRepayPlanDetail() {
		return pledgeRepayPlanDetail;
	}

	public void setPledgeRepayPlanDetail(
			PledgeRepayPlanDetail pledgeRepayPlanDetail) {
		this.pledgeRepayPlanDetail = pledgeRepayPlanDetail;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

	public int getCountDown() {
		return countDown;
	}

	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}

	public WanderRepayPlanDetail getWanderRepayPlanDetail() {
		return wanderRepayPlanDetail;
	}

	public void setWanderRepayPlanDetail(
			WanderRepayPlanDetail wanderRepayPlanDetail) {
		this.wanderRepayPlanDetail = wanderRepayPlanDetail;
	}

	public Integer getWanderPlan() {
		return wanderPlan;
	}

	public void setWanderPlan(Integer wanderPlan) {
		this.wanderPlan = wanderPlan;
	}

	public String getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(String tenderMoney) {
		this.tenderMoney = tenderMoney;
	}

	public String getTenderMoneyContinue() {
		return tenderMoneyContinue;
	}

	public void setTenderMoneyContinue(String tenderMoneyContinue) {
		this.tenderMoneyContinue = tenderMoneyContinue;
	}

	public int getMinWanderPiece() {
		return minWanderPiece;
	}

	public void setMinWanderPiece(int minWanderPiece) {
		this.minWanderPiece = minWanderPiece;
	}

	public int getMaxWanderPieceContinue() {
		return maxWanderPieceContinue;
	}

	public void setMaxWanderPieceContinue(int maxWanderPieceContinue) {
		this.maxWanderPieceContinue = maxWanderPieceContinue;
	}

	public int getMinWanderPieceContinue() {
		return minWanderPieceContinue;
	}

	public void setMinWanderPieceContinue(int minWanderPieceContinue) {
		this.minWanderPieceContinue = minWanderPieceContinue;
	}

	public String getIsDxb1() {
		return isDxb1;
	}

	public void setIsDxb1(String isDxb1) {
		this.isDxb1 = isDxb1;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public String getLastRdate() {
		return lastRdate;
	}

	public void setLastRdate(String lastRdate) {
		this.lastRdate = lastRdate;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public List<UserRepaymentDetail> getUserRepayDetailList() {
		return userRepayDetailList;
	}

	public void setUserRepayDetailList(
			List<UserRepaymentDetail> userRepayDetailList) {
		this.userRepayDetailList = userRepayDetailList;
	}

	public String getbStarType() {
		return bStarType;
	}

	public void setbStarType(String bStarType) {
		this.bStarType = bStarType;
	}

	public String getDayFlg() {
		return dayFlg;
	}

	public void setDayFlg(String dayFlg) {
		this.dayFlg = dayFlg;
	}

	public MonthRepayPlanDetail getMonthRepayPlanDetail() {
		return monthRepayPlanDetail;
	}

	public void setMonthRepayPlanDetail(
			MonthRepayPlanDetail monthRepayPlanDetail) {
		this.monthRepayPlanDetail = monthRepayPlanDetail;
	}

	public BigDecimal getContinueTotal() {
		return continueTotal;
	}

	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
	}

	public int getShowContinueTotal() {
		return showContinueTotal;
	}

	public void setShowContinueTotal(int showContinueTotal) {
		this.showContinueTotal = showContinueTotal;
	}

	public Integer getT() {
		return t;
	}

	public void setT(Integer t) {
		this.t = t;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public Integer getL() {
		return l;
	}

	public void setL(Integer l) {
		this.l = l;
	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public String getBq() {
		return bq;
	}

	public void setBq(String bq) {
		this.bq = bq;
	}

	public int getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(int borrowType) {
		this.borrowType = borrowType;
	}

	public int getBorrowIsday() {
		return borrowIsday;
	}

	public void setBorrowIsday(int borrowIsday) {
		this.borrowIsday = borrowIsday;
	}

	public List<NoteImg> getBorrowImgList() {
		return borrowImgList;
	}

	public void setBorrowImgList(List<NoteImg> borrowImgList) {
		this.borrowImgList = borrowImgList;
	}

	public CarInfoJson getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CarInfoJson carInfo) {
		this.carInfo = carInfo;
	}

	public List<NoteImg> getCarImgList() {
		return carImgList;
	}

	public void setCarImgList(List<NoteImg> carImgList) {
		this.carImgList = carImgList;
	}

	public VerifyMessJson getVerifyMessJson() {
		return verifyMessJson;
	}

	public void setVerifyMessJson(VerifyMessJson verifyMessJson) {
		this.verifyMessJson = verifyMessJson;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public UserAuto getUserAuto() {
		return userAuto;
	}
	public void setUserAuto(UserAuto userAuto) {
		this.userAuto = userAuto;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Integer getLimitLevel() {
		return limitLevel;
	}
	public void setLimitLevel(Integer limitLevel) {
		this.limitLevel = limitLevel;
	}
	public Integer getDesc() {
		return desc;
	}
	public void setDesc(Integer desc) {
		this.desc = desc;
	}
	public BorrowBean getBorrowBean() {
		return borrowBean;
	}
	public void setBorrowBean(BorrowBean borrowBean) {
		this.borrowBean = borrowBean;
	}
	public BorrowRepaymentInfo getBri() {
		return bri;
	}
	public void setBri(BorrowRepaymentInfo bri) {
		this.bri = bri;
	}

	
}
