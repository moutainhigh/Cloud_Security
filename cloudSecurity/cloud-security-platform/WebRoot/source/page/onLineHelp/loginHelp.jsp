<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录</title>
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
          <li class="list_active"><a href="${ctx}/aider.html">在线帮助</a></li>
          <li  style="border-right:1px solid #1369C0;">
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
      <li style="width:160px;"><a href="${ctx}/aider.html">用户注册</a></li>
      <li  class="active"  style="width:160px;"><a href="${ctx}/loginHelp.html">用户登录</a></li>
      <li style="width:160px;"><a href="${ctx}/userCenterHelp.html">用户中心</a></li>
      <li  style="width:160px;"><a href="${ctx}/OrderHelp.html">订购中心</a></li>
      <h2><a href="${ctx}/webQuestions.html">网站安全帮常见问题</a></h2>
    </ul>
  </div>
  <!-- 用户登录-->
  <div class="user_right" style="width: 942px;" id="login">
    
    <h4 class="helph">2.1.2 用户登录</h4>
    <p class="help_detail">用户注册成功后即可使用已注册的用户名密码登录网站安全帮。</p>
   	<div class="help_right"><img src="${ctx}/source/images/help03.jpg" /></div>
   	
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
    <li><a href="###">找回密码</a></li>
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
    <li><a href="###">华为</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">360</a></li>
  </ul>
</div>
<div  class="bottom_main">
<h3><a href="###">联系我们</a></h3>
<ul>
<li><a href="###">客户电话</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>


</div>
</div>
</div>
</div>
</body>
</html>
