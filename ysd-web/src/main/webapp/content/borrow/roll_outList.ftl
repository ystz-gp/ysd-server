<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-续投设置</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
	
	<script type="text/javascript">
function clickToContinue(rid) {

	var ctne = $("#ctne_"+rid).val();
	
	var cmMsg = '';
	var changeStatus = '';
	if (ctne=='1') {
		cmMsg = "确定要取消续投么？";
		changeStatus = '0';
	} else {
		cmMsg = "确定要续投么？";
		changeStatus = '1';
	}

	$("#btnctue_"+rid).hide();
	$("#btnctue_"+rid).after('<span id=\"loading_'+rid+'\"><img src="${base}/static/img/loadingmini.gif" /></span>');
	
	if (!confirm(cmMsg)) {
		$("#loading_"+rid).remove();
		$("#btnctue_"+rid).show();
		return false;
	}
	
	var ajaxParam = {
		"bId" : rid,
		"bType" : changeStatus
	}
	
	$.ajax({
        type: "post",
        dataType : "json",
        data: ajaxParam,
        url: '${base}/borrow/ajaxApplyContinueTotal.do',
        success: function(data, textStatus){
        	if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
        		alert("续投状态修改失败！");
        		$("#btnctue_"+rid).html(old_showname);
        		$("#loading_"+rid).remove();
				$("#btnctue_"+rid).show();
        	} else if (data.status=="success") {
        		alert("续投状态修改成功");
        		if(changeStatus=='1') {
        			$("#btnctue_"+rid).html("开通");
        		} else {
        			$("#btnctue_"+rid).html("关闭");
        		}
        		$("#ctne_"+rid).val(changeStatus);
        		
        		$("#loading_"+rid).remove();
				$("#btnctue_"+rid).show();
        	} else {
	        	alert(data.message);
	        	$("#loading_"+rid).remove();
				$("#btnctue_"+rid).show();
        	}
        	
        	
        },
        error:function(statusText){
        	alert("请重新登录！");
        	window.location.href='${base}/user/login.do?loginRedirectUrl=%2FuserCenter%2FbidSetRollList.do';
        },
        exception:function(){
        	alert(statusText);
        }
	});
	
}
</script>
	
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">



<!-- content -->
<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
	<#include "/content/common/user_center_header.ftl">
<div style="width:955px; float:left; background:#fff; clear:both;">
        <#include "/content/common/user_center_left.ftl">
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
            
			<a style="color:#646464;font-family:'宋体';">${Application ["qmd.setting.name"]}</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" >续投设置</a>
            
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            		<a href="javascript:void(0);" class="hr hre">续投设置</a>
					<a href="${base}/userCenter/getExtraMoney.do" class="hr">续投转出</a>
          </div>
			<form id="listForm" method="post" action="bidSetRollList.do" >
          <div style="font-family:'宋体'; color:#000; margin-top:80px;clear:both;">

										
					应收日期：
					<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)!}" style="width:75px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>&nbsp;到&nbsp;
					<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)!}" style="width:75px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
					&nbsp;&nbsp;关键字：<input type="text" name = "keywords" value="${keywords}" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>&nbsp;
					<input type="button" onclick="gotoPage(1);" value="搜&nbsp;索" style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;"/>
			
			</div>          
          <div style=" margin-top:10px;">
            <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
				<thead bgcolor="#efebdf" align="center">
					      <tr height="35">
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">标题</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">期数</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">待收总额</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">待收本金</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">待收利息</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">净收利息</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">应收日期</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">续投状态</th>
						  </tr>
						</thead>
						<tbody align="center">
							
							<#list pager.result as userRepaymentDetail>
							<tr height="42">
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><a href="${base}/borrow/detail.do?bId=${userRepaymentDetail.borrowId}" title = "${userRepaymentDetail.borrowName}" target="_blank">${substring(userRepaymentDetail.borrowName, 16, "")}</a></td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(userRepaymentDetail.borrowPeriods+1)}/<#if userRepaymentDetail.borrowtype==2>1<#else>${userRepaymentDetail.divides}</#if></td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userRepaymentDetail.repaymentAccount}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userRepaymentDetail.waitAccount}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userRepaymentDetail.waitInterest}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userRepaymentDetail.profit}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userRepaymentDetail.repaymentTime?string("yyyy-MM-dd")}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
							   		<#if userRepaymentDetail.status==0>
							   		 	<#if userRepaymentDetail.borrowtype!=1>
							   		 	
							   				<#if userRepaymentDetail.applyContinueTotal==1>
												<a id="btnctue_${userRepaymentDetail.id}" href="javascript:void(0);" onclick="clickToContinue(${userRepaymentDetail.id})">开通</a>
											<#else>
												<a id="btnctue_${userRepaymentDetail.id}" href="javascript:void(0);" onclick="clickToContinue(${userRepaymentDetail.id})">关闭</a>
											</#if>
											<#--<#if userRepaymentDetail.applyContinueTotal==1>
												开通
											<#else>
												关闭
											</#if>-->
										<#else>
											<#if userRepaymentDetail.applyContinueTotal==1>
												开通
											<#else>
												关闭
											</#if>
										</#if>
							   		<#elseif userRepaymentDetail.status==1>
										<#if userRepaymentDetail.applyContinueTotal==1>
											开通
										<#else>
											关闭
										</#if>
							   		<#else>${userRepaymentDetail.status}
							   		</#if>
							   		<input type="hidden" id="ctne_${userRepaymentDetail.id}" value="${userRepaymentDetail.applyContinueTotal}"/>
							   	</td>
							</tr>
						</#list>
						</tbody>
					</table>   
				<@pagination pager=pager baseUrl="/borrow/bidSetRollList.do" parameterMap = parameterMap>
					<#include "/content/pager.ftl">
				</@pagination> 
          </div>
       </div>
    </div>
</form>
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->    




<#include "/content/common/footer.ftl">

<script type="text/javascript">
$(function(){
	$("#investment_continue").addClass("nsg nsg_a1");
});
</script>
</body>
</html>
