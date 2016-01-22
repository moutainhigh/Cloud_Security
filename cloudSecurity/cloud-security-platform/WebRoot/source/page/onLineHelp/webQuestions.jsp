<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线帮助</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link rel="shortcut icon" href="${ctx}/source/images/chinatelecom.ico" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
</head>
<body>
<div>
<div class="head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
    <div class="lagst">
      <div class="lagst-left"> <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a> </div>
      <div class="lagst-right">
        <p><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
        <p><a href="${ctx}/exit.html">退出</a></p>
      </div>
    </div>
    <div class="list">
      <ul>
        <ul>
          <li><a href="${ctx}/index.html">首页</a></li>
          <li><a href="###">我的订单</a></li>
          <li class="list_active" ><a href="${ctx}/aider.html">在线帮助</a></li>
          <li style="border-right:1px solid #1369C0;">
     		<a href="${ctx}/userCenterUI.html">用户中心</a></li>
        </ul>
      </ul>
    </div>
  </div>
</div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <div class="user_left" style="width:160px;">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center; width:160px;"><a  style="color:#4593fd; " href="#">用户使用说明</a></li>
      <li   style="width:160px;"><a href="${ctx}/aider.html">用户注册</a></li>
      <li style="width:160px;"><a href="${ctx}/loginHelp.html">用户登录</a></li>
      <li   style="width:160px;"><a href="${ctx}/userCenterHelp.html">用户中心</a></li>
      <li  style="width:160px;"><a href="${ctx}/OrderHelp.html">订购中心</a></li>
         <h2><a href="${ctx}/webQuestions.html"  class="active">网站安全帮常见问题</a></h2>
    </ul>
  </div>
<!-- 用户登录-->
  <!-- 订单详情-->
  <div class="user_right" style="width: 942px;">
    <h3 class="helph"> 1、网站安全帮能为网站提供什么帮助？</h3>
    <p class="help_detail">网站安全帮是中国电信打造的自助式网站安全体检中心，集漏洞扫描、恶意代码监测、页面篡改监测、关键字监测与可用性监测能力于一身，7*24的贴身管家服务让您第一时间洞察网站安全态势。</p>
    <h3 class="helph">2、网站安全帮收费吗？</h3>
    <p class="help_detail">网站安全帮是全免费的服务平台。</p>
   
   <h3 class="helph"> 3、网站安全帮为什么要进行资产验证？</h3>
    <p class="help_detail">在进行网站安全监测前，需要对检测网站进行资产归属验证，确认用户的网站管理员身份，网站安全帮不会在未得到许可的情况下对网站进行监测。</p>
    <h3 class="helph">4、上传验证文件或代码会不会有危害？</h3>
    <p class="help_detail">网站安全帮提供的验证文件或代码绝不会对用户网站进行任何有危险性的操作，绝不影响网站的线上服务。</p>
    
    <h3 class="helph"> 5、如何进行网站安全监测？</h3>
    <p class="help_detail">1)首先登录网站安全帮，如没有账号需先注册账号；
	2)进入用户中心“我的资产”添加需监测的网站，并选择一种方式进行资产归属验证，上传验证文件或代码后网站安全帮会确认验证结果；
	3)进入“订购中心”自助下单，下单成功后网站安全帮将为您的网站提供远程安全监测服务；
</p>
    <h3 class="helph">6、网站安全帮通过什么途径进行告警</h3>
    <p class="help_detail">网站安全帮会在安全风险发生的第一时间将告警信息通知到用户预留的手机号码或电子邮箱。</p>
   </div>
</div>


<!-- 尾部代码开始-->
<div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
   <li><a href="${ctx}/registUI.html">新用户注册</a></li>
    <li><a href="${ctx}/loginUI.html">用户登录</a></li>
    <li><a href="${ctx}/forgetPass.html">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
 <h3><a href="###"> 帮助</a></h3>
  <ul>
  <li><a href="${ctx}/aider.html">常见问题</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###">厂商合作</a></h3>
  <ul>
    <li><a href="###">绿盟</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">华为</a></li>
  </ul>
</div>
<div  class="bottom_main">
<h3><a href="###">联系我们</a></h3>
<ul>
<li><a href="###">客户电话</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>
 <ul>
 <li>Copyright&nbsp;©&nbsp;2015 中国电信股份有限公司北京研究院<br />
京ICP备12019458号－10</li>

</div>
</div>
</div>
</div>
</body>
</html>
