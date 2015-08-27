<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>云安全服务平台</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/filter.js"></script>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
$(document).ready(function(){
	var serviceId = ${serviceId};
	var index = serviceId - 1;
    $(".chooseservice ul li").eq(index).siblings().remove();
});

</script>
</head>
<body>
<div>
<div class="head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
    <div class="lagst">
      <div class="lagst-left"> <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a> </div>
      <div class="lagst-right">
        <p ><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
        <p> <a href="${ctx}/exit.html">退出</a></p>
      </div>
    </div>
    <div class="list">
      <ul>
        <ul>
          <li><a href="${ctx}/index.html">首页</a></li>
          <li><a href="${ctx}/chinas.html">安全态势</a></li>
          <li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
          <li><a href="${ctx}/aider.html">在线帮助</a></li>
          <li style="border-right:1px solid #1369C0;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
        </ul>
      </ul>
    </div>
  </div>
</div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <!-- 订单详情-->
  <p class="bigfont">服务筛选</p>
   <div class="peiz_nav" style="margin:20px 0 0 0;width:1050px;">
        <ul class="pei_ul_1">
          <div class="pei_ul_txt"><a href="###">类型：</a></div>
          <li class="pei_active"><a href="###">WEB安全</a></li>
          <li><a href="###">DDOS防护</a></li>
          <li><a href="###">DNS安全</a></li>
          <li><a href="###">移动安全</a></li>
        </ul>
        <ul class="pei_ul_1">
          <div class="pei_ul_txt"><a href="###">厂商：</a></div>
          <li><a href="###">电信</a></li>
          <li><a href="###">360</a></li>
          <li><a href="###">华为</a></li>
          <li><a href="###">绿盟</a></li>
          <li><a href="###">其他</a></li>          
        </ul>
        <ul class="pei_ul_1">
          <div class="pei_ul_txt"><a href="###">服务：</a></div>
          <li class="pei_active"><a href="###">扫描类</a></li>
          <li><a href="###">监控类</a></li>
          <li><a href="###">防护类</a></li>
          <li><a href="###">其他</a></li>
        </ul>
      </div>
      <div class="chooseservice">
        <ul>
          <c:forEach var="list" items="${servList}" varStatus="status">
            <c:choose>
               <c:when test="${status.first}">
                  <li class="peiz_active">
                    <a href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1" class="peiz_name">
		            <div><img src="${ctx}/source/images/user_${status.index+1 }.jpg" /></div>
		            <a href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1" class="peiz_name">${list.name }</a><p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p></a></li>
               </c:when>
               <c:when test="${!status.first}">
                   <li>
		            <div><img src="${ctx}/source/images/user_${status.index+1 }.jpg" /></div>
		            <a href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1" class="peiz_name">${list.name }</a> <p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p></li>
               </c:when>
            </c:choose>
          </c:forEach>
          <!-- <li class="peiz_active">
            <div><img src="${ctx}/source/images/user_1.jpg" /></div>
            <a href="###" class="peiz_name">漏洞扫描服务</a><p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p></li>
          <li>
            <div><img src="${ctx}/source/images/user_2.jpg" /></div>
            <a href="###" class="peiz_name">恶意代码监测服务</a> <p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p></li>
         <li>
            <div><img src="${ctx}/source/images/user_3.jpg" /></div>
            <a href="###" class="peiz_name">网页篡改监测服务</a> <p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p></li>
          <li>
            <div><img src="${ctx}/source/images/user_4.jpg" /></div>
            <a href="###" class="peiz_name">关键字监测服务</a><p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p> </li>
          <li>
            <div><img src="${ctx}/source/images/user_5.jpg" /></div>
            <a href="###" class="peiz_name">可用性监测服务</a> <p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p></li>
          <li>
            <div><img src="${ctx}/source/images/user_6.jpg" /></div>
            <a href="###" class="peiz_name">日常流量监控服务</a><p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p> </li>
          <li>
            <div><img src="${ctx}/source/images/user_7.jpg" /></div>
            <a href="###" class="peiz_name">日常攻击防护服务</a><p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p> </li>
          <li>
            <div><img src="${ctx}/source/images/user_8.jpg" /></div>
            <a href="###" class="peiz_name">突发异常流量清洗服务</a><p class="peiz_company">厂商：中国电信</p> <p class="peiz_company">(扫描类)</p> </li> -->
        </ul>
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
    <li><a href="###">知道创宇</a></li>
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
