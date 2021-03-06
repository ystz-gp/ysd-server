<!DOCTYPE html>
<html>
  <head>
    <title>${Application ["qmd.setting.name"]} 系统信息</title>
	
    <#include "/content/common/meta.ftl">
    <script>  
		
		function redirectUrl() {
			<#if redirectUrl??>
				window.location.href = "${redirectUrl}"
			<#else>
				window.history.back();
			</#if>
		}
		function auto_jump() {  
		    secs = $("#init_value").val() - 1;  
		    $("#init_value").val(secs);  
		        if(secs < 0){return false;}  
		    if(secs == 0){  
		        clearInterval(time);  
		        redirectUrl();
		    }else{
		        $("#showSecond").html(secs);  
		    }
		}
		$(function(){  
		    time = setInterval('auto_jump()',1000);  
		})
</script> 
  </head>
  
  <body>
 <!-- header -->
<#include "/content/common/header.ftl">
<input type='hidden' id='init_value' value="3" />
  
  
  <div style="padding:100px 50px 150px 50px; background:#efebdf; width:800px; margin:0 auto; text-align:center;">
      <div style="color:#595757; font-size:27px; padding:50px 0px 0 0;">
      	<#assign flag=true>
			<#if (errorMessages?? && errorMessages?size gt 0)>
				<#list errorMessages as actionMessage>${actionMessage}&nbsp;</#list>
			<#elseif fieldErrors?? && fieldErrors?size gt 0>
				<#list fieldErrors.keySet() as fieldErrorKey>
					<#list fieldErrors[fieldErrorKey] as fieldErrorValue>
										${fieldErrorValue}&nbsp;
					</#list>
				</#list>
			<#else>
				<#assign flag=false>
								页面不存在或者出现系统错误！
			</#if>
      </div>
 	  <div>
 	  <#if flag>
 	  		<a href="javascript:void(0);" style="color:#f74405;font-family:'宋体'; margin-top:20px;">系统将<span id="showSecond">3</span>秒后跳转...</a></a>
 	  <#else>
        	<a href="${base}/index.do" style="color:#f74405;font-family:'宋体'; margin-top:20px;">进入首页</a>
      </#if>
 	  </div>
  </div>
  
  
  
<#include "/content/common/footer.ftl">

</body>
</html>