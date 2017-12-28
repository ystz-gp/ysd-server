<!DOCTYPE html>
<html>
  <head>
    <title>${Application ["qmd.setting.name"]} 系统信息</title>
	
    <#include "/content/common/meta.ftl">
  </head>
  <body>
 <!-- header -->
<#include "/content/common/header.ftl">
  
  <div style="padding:100px 50px 150px 50px; background:#efebdf; width:800px; margin:0 auto; text-align:center;">
      <div style="color:#595757; font-size:27px; padding:50px 0px 0 0;">
      	<#if (errorMessages?? && errorMessages?size gt 0)>
				<#list errorMessages as actionMessage>${actionMessage}&nbsp;</#list>
			<#elseif fieldErrors?? && fieldErrors?size gt 0>
			<#list fieldErrors.keySet() as fieldErrorKey>
			<#list fieldErrors[fieldErrorKey] as fieldErrorValue>
								${fieldErrorValue}&nbsp;
			</#list>
		</#list>
		<#else>
						页面不存在或者出现系统错误！
		</#if>
      </div>
      <div><a href="${base}/index.do" style="color:#f74405;font-family:'宋体'; margin-top:20px;">进入首页</a></div>
  </div>
  
<#include "/content/common/footer.ftl">

</body>
</html>