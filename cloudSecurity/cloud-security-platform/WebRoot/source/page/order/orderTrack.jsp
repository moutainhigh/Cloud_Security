<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心-订单跟踪</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/user.js"></script>
</head>

<body>
<div class="head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
    <div class="lagst">
      <div class="lagst-left"> <a href="###"><img src="${ctx}/source/images/ren.png" /></a> </div>
      <div class="lagst-right">
        <p ><a href="###">adsfadsfasdfasdf</a></p>
        <p> <a href="###">注册</a></p>
      </div>
    </div>
    <div class="list">
      <ul>
        <ul>
          <li><a href="index.html">首页</a></li>
          <li><a href="###">我的订单</a></li>
          <li><a href="aider.html">在线帮助</a></li>
          <li class="list_active" style="border-right:1px solid #11871d;"><a href="user.html">用户中心</a></li>
        </ul>
      </ul>
    </div>
  </div>
</div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <div class="user_left">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center;"><a  style="color:#45b62b; " href="${ctx}/userCenterUI.html">用户中心</a></li>
      <li><a href="${ctx}/userDataUI.html">基本资料</a></li>
      <li><a href="${ctx}/userBillUI.html">我的账单</a></li>
      <li><a href="${ctx}/userAccetsUI.html">我的资产</a></li>
      <h2>订购中心</h2>
      <li><a href="${ctx}/selfHelpOrderInit.html">自助下单</a></li>
      <li class="active"><a href="${ctx}/orderTrackInit.html">订单跟踪</a></li>
    </ul>
  </div>
  
  <!-- <jsp:include page="../userCenter/left.jsp"/> -->
  
  <!-- 订单跟踪-->
  <div class="user_right" >
  <form action="${ctx}/searchCombine.html" method="post" id="searchForm">
  
    <div class="user_top">
      <div class="user_sec_cont">
            <select class="user_secta spiclesel" id="type" name="type">
                <option selected="selected" value="">请选择类型</option>
                <option value="1" >长期</option>
                <option value="2" >单次</option>
            </select>
      </div>
      <div class="user_sec_cont" style=" left:172px; ">
         <select class="user_secta spiclesel" id="servName" name="servName">
            <option selected="selected " value="">请选择服务</option>
            <option value="1" >漏洞扫描服务</option>
            <option value="2" >恶意代码监测服务</option>
            <option value="3" >网页篡改监测服务</option>
            <option value="4" >关键字监测服务</option>
            <option value="5" >可用性监测服务</option>
            <option value="6" >日常流量监测服务</option>
            <option value="7" >日常攻击防护服务</option>
            <option value="8" >突发异常流量清洗服务</option>
        </select>
      </div>
      
      <div class="user_sec_cont" style=" left:302px; ">
         <select class="user_secta spiclesel" id="servName" name="servName">
            <option selected="selected" value="">请选择服务</option>
            <option value="1" >漏洞扫描服务</option>
            <option value="2" >恶意代码监测服务</option>
            <option value="3" >网页篡改监测服务</option>
            <option value="4" >关键字监测服务</option>
            <option value="5" >可用性监测服务</option>
            <option value="6" >日常流量监测服务</option>
            <option value="7" >日常攻击防护服务</option>
            <option value="8" >突发异常流量清洗服务</option>
        </select>
      </div>
      <div class="dan_3 user_sectime1">
          <input type="text" value="" id="begin_date" name="begin_datevo" onclick="WdatePicker()"/>
        </div>
        
        <div class="dan_4 user_sectime1">
          <input type="text" value="" id="end_date" name="end_datevo" onclick="WdatePicker()"/>
        </div>
      <div class="user_soucuo"><img src="${ctx}/source/images/user_submit_2.jpg" onclick="searchCombine()"/></div>
    </div>
   </form>
    <div class="zhangd_table">
      <table>
      
        <tbody>
              <tr style="background:#e0e0e0; height:30px; line-height:30px;">
                <td style="width:10%;">订单编号</td>
                <td  style="width:10%;">订单类型</td>
                <td  style="width:10%;">订单服务</td>
                <td  style="width:50%;">服务起止时间</td>
                <td  style="width:10%;">下单时间</td>
                <td  style="width:10%;"></td>
              </tr>
	          <c:forEach var="list" items="${orderList}" varStatus="status">
	              <tr>
	                <td>${list.id }</td>
	                <td>服务中</td>
	                <td>
	                                            
	                </td>
	                <td>${list.begin_date }~${list.end_date } </td>
	                <td>${list.create_date }</td>
	                <td><img src="${ctx}/source/images/user_ico_1.jpg" /></td>
	              </tr>
	          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
<!-- 尾部代码开始-->
<div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
    <li><a href="###">新用户注册</a></li>
    <li><a href="###">用户登录</a></li>
    <li><a href="###">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###"> 帮助</a></h3>
  <ul>
    <li><a href="###">常见问题</a></li>
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
<ul>
<li>如未经授权用作他处，将保留追究侵权者法律责任的权利。<br />
  京ICP备11111111号-4 / 京ICP证1111111号<br />
  北京市公安局朝阳分局备案编号:110105000501</li>
</div>
</div>
</div>
</body>
</html>
