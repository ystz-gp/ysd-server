<!DOCTYPE html>
<html>
  <head>
  <title>${Application ["qmd.setting.name"]}-发布项目</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/js/borrow/borrowRaise.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxImageUpload.js"></script>
	<script type="text/javascript" src="${base}/js/jquery/jquery.lSelect.js"></script>
<script>
$().ready(function() {

});


function showContractInfo() {
	var borrowType = $("#borrowType").val();
	var raiseTypeCode = $("#businessType").val();
	
	$.ajax({
		url: "${base}/borrow/ajaxBorrowInputRaiseContract.do",
		data: {"btp":borrowType,"raiseTypeCode":raiseTypeCode},
		type: "GET",
		dataType: "html",
		cache: false,
		success: function(data) {
			$("#tab_contract").html(data);
			
		}
	});
}

function showRepayPlan(){

	checkPieceInput();

	var number = document.getElementById("number").value;
	var account=document.getElementById("account").value;
	var apr = document.getElementById("apr").value;
	//var limit = document.getElementById("timeLimit").value;
	var periodSize = $("#hidPeriodSize").val();
	var repaymentPeriod = $("#repaymentPeriod").val();
	
	if(account==""){
		return false;
	}else if(apr==""){
		return false;
	} else if(periodSize==""||periodSize=="0"){
		return false;
	} 
	
	// 还款计划设置
	var str="";
	var num = parseInt(periodSize);
	
	var apr_period = parseFloat(apr)*parseInt(repaymentPeriod);
	var lixi_period = changeTwoDecimal(parseFloat(account) * apr_period / 1000);
	
	var lixi_all = parseFloat(lixi_period) * parseFloat(num);
	
	document.getElementById("number").value=num;
	
	var tet;
	for(var i=0;i<num;i++){
		var j=parseFloat(i)+parseFloat(1);
		tet=j;

		str +="<tr>";
		str +="  <td class=\"kaqu_r8\" > 第"+tet+"期&nbsp;</td>";
		str +="  <td class=\"kaqu_r8\"  style=\"text-align:right;padding-right:30px;\">"+lixi_period+"元</td>";
		str +="  <td class=\"kaqu_r8\"  style=\"text-align:right;padding-right:30px;\">0.00元</td>";
		str +="  <td class=\"kaqu_r8\"  style=\"text-align:right;padding-right:30px;\">"+lixi_period+"元</td>";
		str +="</tr>";
	}
	
	tet++;
	str +="<tr>";
	str +="  <td class=\"kaqu_r8\" > 第"+tet+"期&nbsp;</td>";
	str +="  <td class=\"kaqu_r8\"  style=\"text-align:right;padding-right:30px;\">"+changeTwoDecimal(account)+"元</td>";
	str +="  <td class=\"kaqu_r8\"  style=\"text-align:right;padding-right:30px;\">"+changeTwoDecimal(account)+"元</td>";
	str +="  <td class=\"kaqu_r8\"  style=\"text-align:right;padding-right:30px;\">0.00元</td>";
	str +="</tr>";
	str +="<tr><td class=\"kaqu_r8\" >小计</td><td class=\"kaqu_r8\"   style=\"text-align:right;padding-right:30px;\"><em class=\"c1\">￥"+changeTwoDecimal(parseFloat(account)+parseFloat(lixi_all))+"</em>元</td><td  class=\"kaqu_r8\"  style=\"text-align:right;padding-right:30px;\"><em class=\"c1\">￥"+changeTwoDecimal(account)+"</em>元</td><td  class=\"kaqu_r8\"  style=\"text-align:right;padding-right:30px;\"><em class=\"c1\">￥"+changeTwoDecimal(lixi_all)+"</em>元</td></tr>";
   	$("#setPlanTable").html(str);			
}
	

<#--年利率转月利率
	年利率%，月利率%保留2位小数-->
function rateYear2Month(rv)
{
	if (parseFloat(rv) > 0)
	{
		var d_rv=parseFloat(rv);
	
		var y_rv = number2Decimal(parseFloat(d_rv)/12,2);
		return y_rv;
	}
}
function rateDay2Year(rv)
{
	if (parseFloat(rv) > 0)
	{
		var d_rv=parseFloat(rv);
	
		var y_rv = number2Decimal(parseFloat(d_rv*365)/10,2);
		return y_rv;
	}
}

function onblurAutoRateRaise(obj,no) {
	var y_rv=parseFloat(obj.value);
	var m_rv=rateYear2Month(y_rv);
	$("#span_monthrate_"+no).html(m_rv);
}
function onblurAutoRate(obj,no) {
	var d_rv=parseFloat(obj.value);
	var y_rv=rateDay2Year(d_rv);
	$("#span_yearrate_"+no).html(y_rv);
}
<#--校验正浮点数-->
function checkFloat(str)
{
    var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
    if(!reg.test(str))
    {
        return false;
    }
    return true;
}
<#--校验正整数-->
function checkPositiveInteger(str) 
{
    var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
    if(!reg.test(str))
    {
        return false;
    }
    return true;
}

function changeTwoDecimal(x)
{
var f_x = parseFloat(x);
if (isNaN(f_x))
{
alert('您输入的数据不完整');
return false;
}
var f_x = Math.round(x*100)/100;
var s_x = f_x.toString();
var pos_decimal = s_x.indexOf('.');
if (pos_decimal < 0)
{
pos_decimal = s_x.length;
s_x += '.';
}
while (s_x.length <= pos_decimal + 2)
{
s_x += '0';
}
return s_x;
}
function checkRepaymentPeriod() {
<#--
	if ($("#timeLimit").val() != ''&& $("#repaymentPeriod").val()!=''&&checkMod($("#timeLimit").val(),$("#repaymentPeriod").val())==false ) {
		$("#hidPeriodSize").val(number2Decimal(getDivideInt($("#timeLimit").val(),$("#repaymentPeriod").val()),0));
		$("#showPeriodSize").html($("#hidPeriodSize").val());
		alert("借款时长不能被借款周期整除，请重新输入!");
		return false;
	}
	if ($("#timeLimit").val() != ''&& $("#repaymentPeriod").val()!=''&&checkMod($("#timeLimit").val(),$("#repaymentPeriod").val())==true ) {
		$("#hidPeriodSize").val(number2Decimal(getDivideInt($("#timeLimit").val(),$("#repaymentPeriod").val()),0));
		$("#showPeriodSize").html($("#hidPeriodSize").val());	
	}
	if ($("#timeLimit").val() == ''|| $("#repaymentPeriod").val()=='') {
		$("#hidPeriodSize").val(0);
		$("#showPeriodSize").html($("#hidPeriodSize").val());	
	}
	
	showRepayPlan();
-->
}
<#--小数位整理：2位小数-->
function number2Decimal(srcNumber, n) {
　　var dstNumber = parseFloat(srcNumber).toFixed(n+2);
　　if (isNaN(dstNumber)) {
　　　　return srcNumber;
　　}
　　if (dstNumber >= 0) {
　　　　dstNumber = parseInt(dstNumber * Math.pow(10, n) + 0.5) / Math.pow(10, n);
　　} else {
　　　　var tmpDstNumber = -dstNumber; dstNumber = parseInt(tmpDstNumber * Math.pow(10, n) + 0.5) / Math.pow(10, n);
　　}
　　var dstStrNumber = dstNumber.toString();
　　var dotIndex = dstStrNumber.indexOf('.');
　　if (dotIndex < 0 && n > 0) {
　　　　dotIndex = dstStrNumber.length; dstStrNumber += '.';
　　}

　　while (dstStrNumber.length <= dotIndex + n) {
　　　　dstStrNumber += '0';
　　}
　　return dstStrNumber;
}

	
function changeBusinessType() {
	var cd = $("#businessType").val();
	
	var ajaxParam = {
		"code" : cd
	}
	
	$("#businessSub").remove();
<#-->	
	$.ajax({
        type: "get",
        dataType : "json",
        data: ajaxParam,
        url: '${base}/borrow/ajaxBusinessSub.do',
        success: function(data, textStatus){
        	if (data=='') {
        		var str = '<input type="hidden" id="businessSub" name="borrow.businessTypeSub" value="0"/>';
        		$("#businessType").after(str);
        	} else {
	        	var json = eval(data);
	        	var str = '<select id="businessSub" name="borrow.businessTypeSub" class="kaqu_w101">';
	        	$.each(json, function(i, item) {
					str =str+ '<option  value="' + item.keyValue + '">'
							+ item.name + '</option>';
				});
				str =str+'</select>';
	        	$("#businessType").after(str);
        	}
        },
        error:function(statusText){
        	alert(statusText);
        },
        exception:function(){
        	alert(statusText);
        }
	});
-->
}
<#--点击：是否有奖励-->
 function change_award(){
   	if ($("#award1").is(':checked')) {
   		$("#funds").attr("disabled",false);
   		$("#funds").removeClass("input1").addClass("input2");
   		$("#award").val('1');
   	} else {
   		$("#funds").val('');
   		$("#funds").attr("disabled",true);
   		$("#funds").removeClass("input2").addClass("input1");
   		$("#award").val('0');
   	}
}

function changeBusinessType2() {
	var cd = $("#businessType").val();
	var cd_old =$("#businessTypeOld").val();
	$(".pawn_"+cd_old).hide();
	$(".pawn_"+cd_old).hide();
	$(".pawn_"+cd).show();
	$(".pawn_"+cd+"_p").show();
	$("#businessTypeOld").val(cd);
}


function uploadFileHideBack () {
	alert('上传成功！！');
}

// 借款凭证上传后
function uploadFileHideBackItem(aid) {
	alert('上传成功！');
	var obj = $("#"+aid);
	obj.after('<a style=" color:#c43b3b;" href="javascript:void(0);">上传成功</a>');
	obj.remove();
}

// 借款凭证
function addImgItem() {

	var x=parseInt($("#idSort").val());
	x = x + 1;
	$("#idSort").val(x);
	
	var str = '';
	str += '	<tr  id="trid_'+x+'" class="blueBg">';
	str += '		<td class="kaqu_sa">';
	str += '			<input type="text" name="vouchersTitle" class="input2 w_252"/>';
	str += '		</td>';
	str += '		<td class="kaqu_sa">';
	str += '			<input type="file" name="imageFile" id="imageItem_'+x+'" style="height:34px;margin-bottom:10px;"/>';
	str += '			<input type="hidden" name="vouchers" id="voucher_'+x+'" />';
	str += '		</td>';
	str += '		<td class="kaqu_sd">';
	str += '			<a id="btnUpload_'+x+'" onclick="ajaxFileUploadImageWithRtn(\'3\',\'imageItem_'+x+'\',\'${base}/file/ajaxUploadImage.do\',\'voucher_'+x+'\',uploadFileHideBackItem(\'btnUpload_'+x+'\'));" href="javascript:void(0);" class="kaqu_se">上传</a>';
	str += '		</td>';
	str += '		<td class="kaqu_e14">';
	str += '			<a onclick="moveUp(\''+x+'\')" href="javascript:void(0);" class="kaqu_top">上移</a>';
	str += '			<a onclick="moveDown(\''+x+'\')" href="javascript:void(0);" class="kaqu_down">下移</a>';
	str += '			<a onclick="moveOut(\''+x+'\')" href="javascript:void(0);"class="kaqu_close">删除</a>';
	str += '		</td>';
	str += '	</tr>';
	
    var tr_len = $("#table_img tr").length;
	if(tr_len==0) {
		$("#table_img").html(str);
	} else {
		$("#table_img").find('tr:last').after(str);
	}
}

function moveUp(x) {
	var obj = $("#trid_"+x);
	var prev = obj.prev();
	prev.before(obj);
}
function moveDown(x) {
	var obj = $("#trid_"+x);
	var next = obj.next();
	next.after(obj);
}
function moveOut(x) {
	var obj = $("#trid_"+x);
	obj.remove();
}
<#--设置分期利率 开始-->
function clickPoputRateStepAgile() {

 var lt = $("#timeLimit").val();
	var size = $("#sel_stepNo").val();
	
	var zq = parseInt(lt)/parseInt(size);
	
	var str ="";
	for(var i =1;i<=size;i++) {
		var zq_e = parseInt(zq)*i;
		var zq_b = parseInt(zq)*(i-1)+1;
		str +=' <tr class = "whiteBg">';
		str +='	<td class="kaqu_r8" >第'+i+'级</td>';
		str +='	<td class="kaqu_r8" >';
		if (i==1) {
		str +='     <input type="text" class="kaqu_r12" id="stepAgile_dayBegin'+i+'" onblur="jsonRank();" style="width:30px;text-align:right;" value="1" readonly />~';
		} else {
		str +='     <input type="text" class="kaqu_r12" id="stepAgile_dayBegin'+i+'" onblur="jsonRank();" style="width:30px;text-align:right;" value="'+zq_b+'"/>~';
		}
		if (i==size) {
		str +='     <input type="text" class="kaqu_r12" id="stepAgile_dayEnd'+i+'" onblur="jsonRank();" style="width:30px;text-align:right;" value="'+lt+'" readonly />';
		} else {
		str +='     <input type="text" class="kaqu_r12" id="stepAgile_dayEnd'+i+'" onblur="jsonRank();"  style="width:30px;text-align:right;" value="'+zq_e+'"/>';
		}
		str +='  </td>';
		
		str +='	<td class="kaqu_r8" >';
		if (i==1) {
			str +='	<input type="text" id="text_dayrate_'+i+'" onblur="onblurAutoRate(this,\''+i+'\');onblur_showApr(this);jsonRank();"  class="kaqu_r12" style="width:68px;"/>';
		} else {
			str +='	<input type="text" id="text_dayrate_'+i+'" onblur="onblurAutoRate(this,\''+i+'\');jsonRank();"  class="kaqu_r12" style="width:68px;"/>';
		}
		str +='	&nbsp;‰</td>';
		str +='	<td class="kaqu_r8" ><span  id="span_yearrate_'+i+'">0.00</span>&nbsp;%</td>';
		str +='	<td class="kaqu_r8" >--&nbsp;&nbsp;</td>';
		
		str +='</tr>';
	}
	$("#show_tbody_rateStep_agile").html(str);
}
<#--设置手续费 开始-->
function clickPoputFeeStepAgile() {

 var lt = $("#timeLimit").val();
	var size = $("#agileFeeSize").val();
	
	
	var str ="";
	for(var i =1;i<=size;i++) {
		var zq_e = "";
		var zq_b = "";
		str +=' <tr class = "whiteBg">';
		str +='	<td class="kaqu_r8" >第'+i+'段</td>';
		str +='	<td class="kaqu_r8" >';
		if (i==1) {
		str +='     <input type="text" class="kaqu_r12" id="stepFee_dayBegin'+i+'" onblur="jsonFee();" style="width:30px;text-align:right;" value="1" readonly />~';
		} else {
		str +='     <input type="text" class="kaqu_r12" id="stepFee_dayBegin'+i+'" onblur="jsonFee();" style="width:30px;text-align:right;" value="'+zq_b+'"/>~';
		}
		if (i==size) {
		str +='     <input type="text" class="kaqu_r12" id="stepFee_dayEnd'+i+'" onblur="jsonFee();" style="width:30px;text-align:right;" value="'+lt+'" readonly />';
		} else {
		str +='     <input type="text" class="kaqu_r12" id="stepFee_dayEnd'+i+'" onblur="jsonFee();"  style="width:30px;text-align:right;" value="'+zq_e+'"/>';
		}
		str +='  </td>';
		
		str +='	<td class="kaqu_r8" >';
		str +='	<input type="text" id="text_dayFee_'+i+'" onblur="jsonFee();"  class="kaqu_r12" style="width:68px;"/>';
		
		str +='	&nbsp;‰</td>';
		
		str +='</tr>';
	}
	$("#show_tbody_feeStep_agile").html(str);
}


function onblur_showApr(obj) {
	var apr = $(obj).val();
	$("#apr").val(apr);
	
	var yearApr = changeTwoDecimal(parseFloat(apr*365)/10);
	document.getElementById("yearapr").value = yearApr;
	document.getElementById("yearapr").innerHTML=yearApr;
}

function jsonRank() {

	var size = $("#sel_stepNo").val();
	
	var str ='';
	for(var i =1;i<=size;i++) {
		var dayrate = $("#text_dayrate_"+i).val();
		var monthApr = changeTwoDecimal(parseFloat(dayrate*30)/10);
		var yearApr = changeTwoDecimal(parseFloat(dayrate*365)/10);
		var daysBegin = $("#stepAgile_dayBegin"+i).val();
		var daysEnd = $("#stepAgile_dayEnd"+i).val();
		if(i>1) str = str +',';
		str=str+'{';
		str=str+'"seq":'+i;
		str=str+',"rateDay":'+dayrate;
		str=str+',"rateMonty":'+monthApr;
		str=str+',"rateYear":'+yearApr;
		str=str+',"daysBegin":'+daysBegin;
		str=str+',"daysEnd":'+daysEnd;
		str=str+'}';
	}
	
	str ='['+str+']';
	$("#stepjson").val(str);
}

function jsonFee() {

	var size = $("#agileFeeSize").val();
	
	var str ='';
	for(var i =1;i<=size;i++) {
		var feeRate = $("#text_dayFee_"+i).val();
		feeRate = feeRate / 1000;
		var daysBegin = $("#stepFee_dayBegin"+i).val();
		var daysEnd = $("#stepFee_dayEnd"+i).val();
		if(i>1) str = str +',';
		str=str+'{';
		str=str+'"seq":'+i;
		str=str+',"feeRate":'+feeRate;
		str=str+',"daysBegin":'+daysBegin;
		str=str+',"daysEnd":'+daysEnd;
		str=str+'}';
	}
	
	str ='['+str+']';
	$("#agileFeesjson").val(str);
}


<#--设置阶梯利率 开始-->


function btnRaiseSave(){

	var type = $("#borrowType").val();
		if ($("#borrowname").val()=='') {
			alert("标题不能为空，请重新输入!");
			return false;
		}else if($("#businessCode").val()==''){
			alert("项目编号不能为空，请重新输入!");
			return false;
		}else if($("#businessContractCode").val()==''){
			alert("借款合同编号不能为空，请重新输入!");
			return false;
<#-->
		}
		else if ($("#borImageFirst").val()=='') {
			alert("图片不能为空，请重新输入!");
			return false;
-->
		}else if($("#financierInfo").val()==''){
			alert("融资人信息不能为空，请重新输入!");
			return false;
		}else if($("#borrowContentInfo").val()==''){
			alert("项目信息不能为空，请重新输入!");
			return false;
		}else if($("#purposeFactor").val()==''){
			alert("资金用途不能为空，请重新输入!");
			return false;
		}else if($("#paymentFactor").val()==''){
			alert("还款来源不能为空，请重新输入!");
			return false;
		}else if($("#vouchMeasure").val()==''){
			alert("风控措施不能为空，请重新输入!");
			return false;
		}else if ($("#account").val()=='') {
			alert("借款金额不能为空，请重新输入!");
			return false;
		}else if (checkPositiveInteger($("#account").val())==false) {
			alert("借款金额不正确，请重新输入!");
			return false;
		}else if ($("#apr").val()=='') {
			alert("借款利率不能为空，请重新输入!");
			return false;
		}else if (checkFloat($("#apr").val()==false)) {
			alert("借款利率不正确，请重新输入!");
			return false; 
		} else if (($("#award").val()==1||$("#award").val()==2) && $("#funds").val()=='') {
			alert("奖励不能为空，请重新输入!");
			return false;
		}else if (($("#award").val()==1||$("#award").val()==2) && parseFloat($("#funds").val())<=0) {
			alert("奖励不能小于0，请重新输入!");
			return false;
		} else if ($("#timeLimit").val()=='') {
			alert("借款时长不能为空，请重新输入!");
			return false;
		} else if (checkPositiveInteger($("#timeLimit").val())==false) {
			alert("借款时长不正确，请重新输入!");
			return false;
		}  else if(type==1){
			if($("#borStages").val()==''||$("#borStages").val()=='0') {
				alert("请设置还款计划!");
				return false;
			}
		} else if ($("#isDxb").val()==1 && $("#pwd").val()=='') {
			alert("定向密码不能为空，请重新输入!");
			return false;
		} else if ($("#editor").val()=='') {
			alert("项目的详情不能为空，请重新输入!");
			return false;
		} else if ($("#mycode").val()=='') {
			alert("校验码不能为空，请重新输入!");
			return false;
		} else {
		
			fillContractParam();
			fillContractPawns();
			
			//var dinfo = fillDirectInfo();
			//$("#borrowDirectInfo").val(dinfo);
		
			return true;
		}	
		
	}

<#--校验
	false:借款金额不能被总份数整除-->
function checkPieceInput() {
	var wanderPieceMoney = $("#wanderPieceMoney").val();
	
	if ($("#account").val() != ''&&checkMod($("#account").val(),wanderPieceMoney)==false ) {
		alert("借款金额不能被"+wanderPieceMoney+"整除，请重新输入!");
		return false;
	}
	
	if ($("#account").val() != '') {
		$("#wanderPieceSize").val(getDivideInt($("#account").val(),wanderPieceMoney));
	}

	if ($("#account").val() != ''&& $("#wanderPieceSize").val()!=''&&checkMod($("#account").val(),$("#wanderPieceSize").val())==false ) {
		$("#showPieceMoney").html(number2Decimal(getDivideInt($("#account").val(),$("#wanderPieceSize").val()),2));
		alert("借款金额不能被总份数整除，请重新输入!");
		return false;
	}
	
	if ($("#account").val() != ''&& $("#wanderPieceSize").val()!=''&&checkMod($("#account").val(),$("#wanderPieceSize").val())==true ) {
		$("#showPieceMoney").html(number2Decimal(getDivideInt($("#account").val(),$("#wanderPieceSize").val()),2));
	}
}
<#--整除校验
	true：str可以被dov整除-->
function checkMod(str,dov) {
	var a = parseInt(str);
	var b = parseInt(dov);
	if (a%b==0){
		return true;
	}
	return false;
}
<#--相除-->
function getDivideInt(str,dov) {
	var a = parseInt(str);
	var b = parseInt(dov);
	if (b==0) {
		return 0;
	}
	return a/b;
}

<#--合同参数-->
function fillContractParam() {
	var str = '';
	var tr_ojbs = $(".contract_param");
	if (tr_ojbs.length > 0) {
		tr_ojbs.each(function(i,t){
			if(i>0) str+=',';
			
			var txt_objs=$(t).find('input:text');
			txt_objs.each(function(ii,tt){
				str += '\"'+$(this).attr('name')+'\":'+'"'+$(this).val()+'"';
			});
		});
	}
	
	if ($("#hidden_financeSignImg").length>0) {
		str+=',';
		str += '\"financeSignImg\":'+'"'+$("#hidden_financeSignImg").val()+'"';
	}
	
	if(str !='') {
		str = '{'+str+'}';
	}
	
	$("#borrowContractParam").val(str);
}
<#--抵押物列表-->
function fillContractPawns() {
	var str ='';

	$("#table_contract_pawn tr").each(function(i,tm){
		if(i>0) str+=',';
		str+=fillContract_pawn_tr($(this));
	});
	if(str !='') {
		str = '['+str+']';
	}
	$("#borrowContractPawns").val(str);
}
<#--抵押物单个-->
function fillContract_pawn_tr(obj) {
	var tr_obj=$(obj).find('input:text');
	var str = '';
	tr_obj.each(function(i,t){
		if(i>0) str+=',';
		str += '\"'+$(this).attr('name')+'\":'+'"'+$(this).val()+'"';
	});
	if(str !='') {
		str = '{'+str+'}';
	}
	
	return str;
}
<#---※※※抵押物设置 开始※※※----->
<#--新增抵押物--->
function addContractPawnItem() {
	var tr_len = $("#table_contract_pawn tr").length;
	if(tr_len==0) {
		$("#table_contract_pawn").html($("#init_pawn").html());
	} else {
		$("#table_contract_pawn").find('tr:last').after($("#init_pawn").html());
	}
}
<#--删除抵押物-->
function delContractPawnItem(obj) {
	$(obj).parent().parent().remove();
}
<#---※※※抵押物设置 结束※※※----->

function changeValidateCode(obj) {
        var timenow = new Date().getTime();
           //每次请求需要一个不同的参数，否则可能会返回同样的验证码
        	//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
        obj.src="${base}/rand.do?d="+timenow;
	}
	
//<!--设定定向标-->
function change_d(type){
  var obj=document.getElementsByName('isDxb1');  
  for(var i=0; i<obj.length; i++){
    if(obj[i].checked) {
    	$("#pwd").removeClass("input1").addClass("input2");
    	jQuery("#pwd").attr("disabled",false); 
    	document.getElementById("isDxb").value=obj[i].value;
    }else{
    	$("#pwd").removeClass("input2").addClass("input1");
    	jQuery("#pwd").attr("disabled",true);
    	document.getElementById("isDxb").value=0;
    	document.getElementById("pwd").value="";
    }
   }
}	
	
	</script>
	
</head>
 <#--
<body onload="checkimg()">
-->
<body class="body_bg">
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">

<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="#">发布借款</a></li>
            <li>
	            <#if borrow.type=7>债权转让
				<#elseif borrow.type=8>抵押质押
				<#elseif borrow.type=9>信用
				<#elseif borrow.type=10>体验
				<#elseif borrow.type=11>灵活宝
				<#else>
				</#if>
			</li>
			
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>项目基本信息</h3>
       	 	<form id="raiseForm" enctype="multipart/form-data" >
				 <input type="hidden" name="borrow.award" id="award" value="0" />
				 <input type="hidden" name="borrow.isDxb" id="isDxb" value="0" />
				 <input type="hidden" name="number" id="number" value="0" />
				 <input type="hidden" name="borrow.borStages" id="borStages" value="0" />
				 <input type="hidden" name="borrow.type" id="borrowType" value="${btp}" />
				 <input type="hidden" name="borrow.isday" value="0" />
				 <input type="hidden" name="borrow.rateStep" id="stepjson" value="" />
				 <input type="hidden" name="borrow.agileFees" id="agileFeesjson" value="" />
				 <input type="hidden" name="borrow.agencyGuaranteeId" value="${borrow.agencyGuaranteeId}"/>
				 <input type="hidden" name="borrow.contractParam" id="borrowContractParam" />
				 <input type="hidden" name="borrow.contractPawns" id="borrowContractPawns" />
				 <input type="hidden" name="borrow.directInfo" id="borrowDirectInfo" />
				 <input type="hidden" id="businessTypeOld" value="0" />
       	 	
       	 	
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40"></td>
                <td class="text_r grayBg" width="86">项目类型：</td>
                <td><input class="input1" disabled="disabled" type="text" value="<#if borrow.type=7>债权转让
									<#elseif borrow.type=8>抵押质押
									<#elseif borrow.type=9>信用
									<#elseif borrow.type=10>体验
									<#elseif borrow.type=11>灵活宝
									<#else>
									</#if>" size="35"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">项目编号：</td>
                <td><input class="input2 w_252" id = "businessCode" name="borrow.businessCode" type="text" size="35"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">借款合同编号：</td>
                <td><input class="input2 w_252" type="text" id = "businessContractCode"  name="borrow.businessContractCode"  value = "${borrow.businessContractCode}" size="35"/></td>
              </tr>
             <#--> <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">担保公司：</td>
                <td>
                 	<input disabled="disabled" class="input1" type="text" value = "<#if agencyDb?if_exists>${agencyDb.companyName}<#else>无</#if>" size="35"/>
				</td>
              </tr> -->
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">借款人：</td>
                <td>
	            	<select class="kaqu_w101" name="borrow.borrowerId">
		                <#list borrowerList as borrower>
							<option value="${borrower.id}">${borrower.username}</option>
						</#list>
					</select>
				</td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">项目标题：</td>
                <td><input class="input2 w_252" id="borrowname" name="borrow.name" type="text" size="35"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">项目所属地：</td>
				<td><input type="text" id="areaSelect" name="areaId" class="hidden" value="<#if borrow.area?exists>${borrow.area}<#elseif borrow.city?exists>${borrow.city}<#else>${borrow.province}</#if>" defaultSelectedPath="${borrow.province?default('')},${borrow.city?default('')},${borrow.area?default('')}" /></td>
              </tr> 
              <#-->
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">项目描述：</td>
                <td><textarea class="textarea" name="borrow.content" style="width:400px">${borrow.content}</textarea></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">担保措施：</td>
                <td><textarea name="borrow.vouchMeasure" class="textarea" style="width:400px">${borrow.vouchMeasure}</textarea></td>
              </tr> -->
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">融资人信息：</td>
                <td><textarea class="textarea" id = "financierInfo" name="borrowContent.financierInfo" style="width:400px"></textarea></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg"></td>
          	    <td class="text_r grayBg">审核内容：</td>
            	<td>
					<label><input type="checkbox" name="auditStatus" value="0">身份证</label>
					<label><input type="checkbox" name="auditStatus" value="1" >户口簿</label>
					<label><input type="checkbox" name="auditStatus" value="2" >征集报告</label>
					<label><input type="checkbox" name="auditStatus" value="3" >收入证明</label>
					<label><input type="checkbox" name="auditStatus" value="4" >车辆行驶证</label>
					<label><input type="checkbox" name="auditStatus" value="5" >车辆完税证明</label>
					<label><input type="checkbox" name="auditStatus" value="6" >购车发票</label>
					<label><input type="checkbox" name="auditStatus" value="7" >机动车登记证</label>
					<label><input type="checkbox" name="auditStatus" value="8" >企业营业执照</label>
					<label><input type="checkbox" name="auditStatus" value="9" >企业税务登记证</label>
					<label><input type="checkbox" name="auditStatus" value="10" >企业组织机构代码证</label>
					<label><input type="checkbox" name="auditStatus" value="11" >房产证</label>
					<label><input type="checkbox" name="auditStatus" value="12" >土地证</label>
					<label><input type="checkbox" name="auditStatus" value="13" >房产契证</label>
					<label><input type="checkbox" name="auditStatus" value="14" >房产评估报告</label>
					<label><input type="checkbox" name="auditStatus" value="15" >他项权证</label>
					<label><input type="checkbox" name="auditStatus" value="16" >财务报表</label>
				</td>
         	 </tr> 	
          	
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">项目信息：</td>
                <td><textarea class="textarea" id="borrowContentInfo" name="borrow.content" style="width:400px">${borrow.content}</textarea></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">资金用途：</td>
                <td><textarea class="textarea" id="purposeFactor" name="borrowContent.purposeFactor" style="width:400px"></textarea></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">还款来源：</td>
                <td><textarea class="textarea" id="paymentFactor" name="borrowContent.paymentFactor" style="width:400px"></textarea></td>
              </tr> 
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg"><#-->担保<-->风控措施：</td>
                <td><textarea id="vouchMeasure" name="borrow.vouchMeasure" class="textarea" style="width:400px">${borrow.vouchMeasure}</textarea></td>
              </tr> 
          	
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">项目凭证：</td>
                <td><input type="button" value="新增凭证" class = "btn3 white" onclick="addImgItem();">
					<input type="hidden" id="idSort" value="${(noteImg)!?size}"/>
					<img src="${base}/static/img/qs.png" width="20" height="20" title="新增凭证"/ class="kaqu_e5"></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg"></td>
                <td>
						<table class="tableShadow"  border="0" cellspacing="1" cellpadding="0" style="width: 65%;">
								<thead>
									<tr>
										<td>类型</td>
										<td>标题</td>
										<td>操作</td>
										<td>编辑</td>
									</tr>
								</thead>
								<tbody id="table_img">
									
								</tbody>
							</table>
				</td>
         	 </tr> 	
             </table> 
             <#-- -----------------table1 over    -------------------->
             
         <h3>项目参数设置</h3>
           
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="86">每份金额：</td>
                <td>
                	<select id="wanderPieceMoney" name="borrow.wanderPieceMoney" class="kaqu_r3" onchange="checkPieceInput()">
						<option value="500">500</option>
						<option value="1000">1000</option>
						<option value="2000">2000</option>
						<option value="5000">5000</option>
						<option value="10000">10000</option>
					</select></td>
              </tr>
              <tr>
                <td class="text_r org grayBg" >*</td>
                <td class="text_r grayBg">项目金额：</td>
                <td>
                	<input class="input2 w_252" name="borrow.account" id="account"  value="${borrow.account}" onkeyup="value=value.replace(/[^0-9]/g,'')" onBlur="checkPieceInput()" type="text" size="35"/>元
                	<img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/></td>
              </tr>

              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">认购总份数：</td>
                <td><input class="input1" type="text" size="35" name="borrow.wanderPieceSize" id="wanderPieceSize" value="${borrow.wanderPieceSize}" readonly onKeyUp="value=value.replace(/[^0-9.]/g,'')"  onBlur="checkPieceInput()" value=""  />份</td>
              </tr>
             
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">最小认购数：</td>
                <td>
					<select id="lowestAccount" name="borrow.lowestAccount" class="kaqu_r3">
						<@listing_childlist sign="borrow_lowest_wander"; listingList>
							<#list listingList as listing>
								<option value="${listing.keyValue}" >${listing.name}</option>
							</#list>
						</@listing_childlist>
					</select>
				</td>
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">最大认购数：</td>
                <td>
                 	<select id="mostAccount" name="borrow.mostAccount" class="kaqu_r3" >
						<option value="" selected="selected">不限</option>
						<@listing_childlist sign="borrow_most_wander"; listingList>
							<#list listingList as listing>
								<option value="${listing.keyValue}" >${listing.name}</option>
							</#list>
						</@listing_childlist>	
					</select>
                </td>
              </tr> 
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">投资奖励：</td>
                <td>
	            	<label><input type="checkbox" name="award1" id="award1" onclick="change_award();" value="1" />&nbsp;&nbsp;是否启用奖励，项目金额的</label>&nbsp;&nbsp;
					<input type="text" id="funds" name="borrow.funds" disabled value="" onkeyup="clearNoNum(this)" class="input1 w_252"/>&nbsp;%<br />
				</td>
              </tr>
<#--
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">投资开放期：</td>
                <td>
					<select id="validTime" name="borrow.validTime" class="kaqu_r3">
						<@listing_childlist sign="borrow_valid_time"; listingList>
							<#list listingList as listing>
								<option value="${listing.description}" >${listing.name}</a></option>
							</#list>
						</@listing_childlist>
					</select>
				</td>
              </tr>
-->
<input type="hidden" id="validTime" name="borrow.validTime" value="0"/>
              <tr>
               <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">借款时长：</td>
                <td>
                 	<input class="input2 w_252" type="text"  id="timeLimit" name="borrow.timeLimit" value="${borrow.timeLimit}" onBlur="checkRepaymentPeriod()" onkeyup="value=value.replace(/[^0-9]/g,'')"  />
									&nbsp;天&nbsp;
									<img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/>
                </td>
              </tr> 
              
             <tr>
               <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">封闭期时长：</td>
                <td>
                 	<input class="input2 w_252" type="text"  id="assignDays" name="borrow.assignDays" value="${borrow.assignDays}" onkeyup="value=value.replace(/[^0-9]/g,'')"  />
									&nbsp;天&nbsp;
									<img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/>
                </td>
              </tr>
              
              <tr>
               <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">停购期时长：</td>
                <td>
                 	<input class="input2 w_252" type="text"  id="stopBuyDays" name="borrow.stopBuyDays" value="${borrow.stopBuyDays}" onkeyup="value=value.replace(/[^0-9]/g,'')"  />
									&nbsp;天&nbsp;
									<img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/>
                </td>
              </tr>
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">还款方式：</td>
                <td>
               		随时赎回，到期还本
               		<input type="hidden" name="borrow.style" value="6"/>
				</td>
              </tr> 
              
			  <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">赎回手续费<br/>(赎回期)：</td>
                <td>
               		<input class="input2 w_252" type="text" name="borrow.redeemFeeRate" id="redeemFeeRate" value="${borrow.redeemFeeRate}" />&nbsp;&nbsp;‰
				</td>
              </tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">赎回手续费<br/>(日常)：</td>
                <td>
               		<select id="agileFeeSize" class="kaqu_r3" name="borrow.agileFeeSize" onchange="clickPoputFeeStepAgile()">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
						</select>段
						<font style=" color:#ea5504">*最多可设置6段</font>&nbsp;&nbsp;
				</td>
              </tr> 
             
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg"></td>
                <td>
						<table class="tableShadow"  border="0" cellspacing="1" cellpadding="0" style="width: 35%;">
							<thead>
								<tr>
									<td class="kaqu_r6">序号</td>
									<td class="kaqu_r6">持有天数</td>
									<td class="kaqu_r6">手续费</td>
								</tr>
							</thead>
							<tbody id="show_tbody_feeStep_agile">
							</tbody>
						</table>
				</td>
              </tr> 
			  <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">分期利率：</td>
                <td>
						<select id="sel_stepNo" class="kaqu_r3" onchange="clickPoputRateStepAgile()">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
						</select>
						&nbsp;期数
						&nbsp;<#--><a id="btnRateStep" onclick="clickPoputRateStepRaise();" href="javascript:void(0)" class="kaqu_r13">设置阶梯利率</a>-->
						<#--<input type = "button" id="btnRateStep" onclick="clickPoputRateStepRaise();" class="btn3 white" value = "设置阶梯利率"/>-->
						<font style=" color:#ea5504">*最多可设置6级阶梯</font>&nbsp;&nbsp;
						<img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/>
				</td>
              </tr>
			  <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg"></td>
                <td>
						<table class="tableShadow"  border="0" cellspacing="1" cellpadding="0" style="width: 35%;">
							<thead>
								<tr>
									<td class="kaqu_r6">序号</td>
									<td class="kaqu_r6">持有天数</td>
									<td class="kaqu_r6">天利率</td>
									<td class="kaqu_r6">年化利率</td>
									<td class="kaqu_r6">操作</td>
								</tr>
							</thead>
							<tbody id="show_tbody_rateStep_agile">
							</tbody>
						</table>
				</td>
              </tr> 
			<tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">年利率：</td>
                <td>
					<input class="input1 w_252" type="text" name="borrow.apr" id="apr" value="${borrow.apr}" readonly onkeyup="clearNoNumApr(this)" onblur="commitMonth(this);"/>&nbsp;&nbsp;‰起，年利率为<span id="yearapr">0</span>%起。&nbsp;&nbsp; 
									<img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/>
				</td>
              </tr> 




             <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">是否定向：</td>
                <td>
					<input type="checkbox" name="isDxb1" id="isDxb1" onclick="change_d(1)" value="1"/>&nbsp;&nbsp;是，定向密码为：&nbsp;&nbsp;<input type="text" id="pwd" name="borrow.pwd" disabled class="input1 w_252"/>
					<img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/>
				</td>
              </tr> 
              </table>
             <#-- -----------------table2 over    -------------------->

             <#-- -----------------table3 over    -------------------->
   		  <h3>项目合同信息</h3>
         		
     		<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0" id="tab_contract">
              <tbody>
          	<tr>
                
                <td class="text_r grayBg" width="86">业务类型：</td>
                <td>
				<select id="businessType" name="borrow.businessType" onchange="showContractInfo()" class="kaqu_w101" >
					<#if borrow.type==7>
						<option value="01" <#if borrow.businessType == '01'>selected </#if> >房产</option>
						<option value="02" <#if borrow.businessType == '02'>selected </#if> >汽车</option>
						<option value="12" <#if borrow.businessType == '12'>selected </#if> >奢品贷</option>
					<#elseif borrow.type==8>
						<option value="01" <#if borrow.businessType == '01'>selected </#if>  >房产</option>
						<option value="02" <#if borrow.businessType == '02'>selected </#if>  >汽车</option>
						<option value="03" <#if borrow.businessType == '03'>selected </#if>  >动产</option>
						<option value="13" <#if borrow.businessType  == '13'>selected </#if> >益老贷</option>
						<option value="14" <#if borrow.businessType == '14'>selected </#if> >券贷通</option>
					<#elseif borrow.type==9>
						<option value="11" <#if borrow.businessType == '11'>selected </#if>  >信用</option>
					<#elseif borrow.type==10>
						<option value="99" <#if borrow.businessType == '99'>selected </#if>  >体验</option>
					<#elseif borrow.type==11>
						<option value="01" <#if borrow.businessType == '01'>selected </#if>  >房产</option>
						<option value="02" <#if borrow.businessType == '02'>selected </#if>  >汽车</option>
						<option value="03" <#if borrow.businessType == '03'>selected </#if>  >动产</option>
						<option value="04" <#if borrow.businessType == '04'>selected </#if>  >保证</option>
						
					</#if>
				</select>

				</td>
              </tr>
              </tbody>
        	</table>
        	 <#-- -----------------table4 over    -------------------->
    	  <h3>安全验证</h3>
        	<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
        	<tbody>
			<tr>
				<td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="86">安全密码：</td>
				<td>
					<input type="password" name="safepwd"  class="input2 w_252" size="35"/>
				</td>
			</tr>
			<tr>
				<td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">验证码：</td>
				<td>
					<input type="text" name="mycode" id="mycode" class="input2 w_252" size="35"/>
					<img id = "code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" />
				</td>
			</tr>
         	<tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg"></td>	
            	<td >
	            	
                	<input class="btn1 white" type="button" id = "submitButton" value="提交">
       	   			<input class="btn2 ml_15" type="button" onclick="window.history.back(); return false;" value="返回">
                </td>
              </tr>
         	</tbody>
           </table>  
             
            </form>       
        </div>                
    </div>
    
</div>

	<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){

	var $areaSelect = $("#areaSelect");
	
	// 地区选择菜单
	$areaSelect.lSelect({
			url: "${base}/area/ajaxArea.do"// AJAX数据获取url
	});
	
	if ($("#businessType").val()!=0) {
		changeBusinessType();
	}
});
</script>
<script type="text/javascript">
$(function(){
		
		<#if borrow.type=7>$("#li_a_zqlzxm").addClass("li_a_select");
		<#elseif borrow.type=8>$("#li_a_dyzyxm").addClass("li_a_select");
		<#elseif borrow.type=9>$("#li_a_fbxyxm").addClass("li_a_select");
		<#elseif borrow.type=10>$("#li_a_fbtyxm").addClass("li_a_select");
		<#else>
		</#if>
		$(".sssj").attr("id","sssj");
		

	
//	var bt = $("#businessType").val();
//	$("#businessTypeOld").val(bt);
//	$(".pawn_"+bt).show();
//	$(".pawn_"+bt+"_p").show();
	
//	if (bt=='02') {
//		fillCarPawnInit();
//	} else if (bt=='03') {
//		fillMovePawnInit();
//	}
	
//	showRepayPlan();
	//showRateStepInit();
	showContractInfo();
	
	
	$raiseForm = $("#raiseForm");
	
	$("#submitButton").click(function(){
		if(!btnRaiseSave()){return ;}
		
		$.ajax({
			url: qmd.base+"/borrow/ajaxSaveBorrowRaise.do",
			data: $raiseForm.serialize(),
			type: "POST",
			dataType: "json",
            async : false, 
			success: function(data) {
				if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
	        		alert("操作失败！");
	        	} else if (data.status=="success") {
	        		alert(data.message);
	        		window.location.href = qmd.base + '/borrow/userBorrowMgmt.do';
	        	} else {
		        	alert(data.message);
	        	}
			},
            error : function() {  
                alert('参数错误，请联系管理员！');
            } 
		});
	});
	
	
});
</script>
</html>
