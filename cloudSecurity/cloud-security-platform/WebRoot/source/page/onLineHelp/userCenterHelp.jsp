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
      <li class="active"  style="width:160px;"><a href="${ctx}/userCenterHelp.html">用户中心</a></li>
      <li  style="width:160px;"><a href="${ctx}/OrderHelp.html">订购中心</a></li>
       <h2><a href="${ctx}/webQuestions.html">网站安全帮常见问题</a></h2>
    </ul>
  </div>
  
  <!-- 用户中心-->

  <div class="user_right" style="width: 942px;">
    <div>
    <h4 class="helph">2.1.3 用户中心</h4>
    <p class="help_detail">用户登录成功后定位到用户中心界面。用户在用户中心可进行基本资料管理，账单查看并管理用户资产。</p>
   	<div class="help_right"><img src="${ctx}/source/images/help04.jpg" /></div>
   	</div>
   	
   	
   	<!--基本资料-->
	<div> 
    <h4 class="helph">2.1.3.1 基本资料</h4>
    <p class="help_detail">用户修改绑定手机号码或邮箱地址后需重新激活使用。</p>
   	<div class="help_right"><img src="${ctx}/source/images/help05.jpg" /></div>
  	</div>
  	
  	<!--我的账单-->
  	<div>
    <h4 class="helph">2.1.3.2 我的账单</h4>
    <p class="help_detail">“我的账单”提供用户所有已订单服务情况汇总，包括订单服务类型、订单服务时长、订单服务次数内容。</p>
   	<div class="help_right"><img src="${ctx}/source/images/help06.jpg" /></div>
  	</div>
  	
  	<!--我的资产-->
	  <div>
	    
	    <h4 class="helph">2.1.3.3 我的资产</h4>
	    <p class="help_detail">出于服务安全性及规范性的要求，用户需将欲购买WEB安全服务所针对的网站对象作为资产提前添加，并进行资产归属验证。</p>
	   	<div class="help_right"><img src="${ctx}/source/images/help07.jpg" /></div>
	   	 <p class="help_detail">新增资产</p>
	   	<div class="help_right"><img src="${ctx}/source/images/help08.jpg" /></div>
	   	 <p class="help_detail">修改资产</p>
	   	<div class="help_right"><img src="${ctx}/source/images/help09.jpg" /></div>
	   	<p class="help_detail">资产验证</p>
	   	<div class="help_right"><img src="${ctx}/source/images/help10.jpg" width=""/></div>
	  </div>
   	
   	
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
