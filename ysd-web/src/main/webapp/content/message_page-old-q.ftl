<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>乐商贷-专业安全透明的互联网金融服务平台 系统信息</title>
	<#include "/content/common/meta.ftl">
	
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">
<div class="find">
  <div style="width:1200px;height:530px; margin:30px auto 90px;background:#fff;">
      <div class="top">
          <p><span>系统通知</span></p>
      </div>
      <div style="margin:85px auto 0;font-size:16px;">
         <div style="color:#666;text-align:center;">
            <span style="width:34px;height:34px;display:inline-block;line-height:34px;text-align:center;color:#fff;font-weight:bold;border-radius:50%;background:#fd7c1a;margin-right:8px;font-size:24px;">!</span>${msg}
          </div>
         <div style="text-align:center;margin-top:130px;">
           <a  href="${base}${msgUrl}" style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#fd7c1a;color:#fff;display:inline-block;text-align:center;cursor:pointer;">请点击这里</a>
         </div>
      </div>
  </div>
</div><!-- 找回密码 end -->
<#include "/content/common/footer.ftl">

</body>
</html>
