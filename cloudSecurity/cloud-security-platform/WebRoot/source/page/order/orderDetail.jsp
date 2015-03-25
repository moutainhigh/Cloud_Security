<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单跟踪-订单详情</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
</head>

<body>
<div class="head_bj">
        <div class="head">
           <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
           <div class="lagst">
               <div class="lagst-left">
                <c:if test="${sessionScope.globle_user!=null }">
                   <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a>
                </c:if>
                 <c:if test="${sessionScope.globle_user==null }">
                    <a href="${ctx}/toLoginUI.html"><img src="${ctx}/source/images/ren.png" /></a>
                 </c:if>
               </div>
               <div class="lagst-right">
               <!-- 如果已经登录则显示用户名，否则需要登录 -->
               <c:if test="${sessionScope.globle_user!=null }">
                <p><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
                <p><a href="${ctx}/exit.html">退出</a></p>
               </c:if>
               <c:if test="${sessionScope.globle_user==null }">
                     <p><a href="${pageContext.request.contextPath}/loginUI.html">登录</a></p>
                     <p><a href="${pageContext.request.contextPath}/registUI.html">注册</a></p>
               </c:if>
               </div>
           </div>
            <div class="list">
               <ul>
                   <li><a href="${ctx}/index.html">首页</a></li>
                   <li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
                   <li><a href="aider.html">在线帮助</a></li>
                   <li style="border-right:1px solid #11871d;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
               </ul>
           </div>
        </div>
    </div>
<div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <!-- 订单详情-->
  <div class="user_right" style="margin:auto;float:none;">
    <div class="gj_top"> <a href="#" class="aelse">订单跟踪</a>　>　<a href="#" class="acur">订单详情</a> </div>
    <c:forEach var="order" items="${orderList}" varStatus="status">
	    <p style="margin:0 0 38px 286px;"><span class="bigfont">订单编号</span><span class="bigfont" style="margin-right:20px;">${order.id }</span><span class="bigfont">下单时间</span><span class="bigfont"><fmt:formatDate value="${order.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
	    <p class="dd_detail"><span class="detail_l fl">订单状态</span><span class="detail_r fl">
	       <c:set var="temp" value="${nowDate }"/>
           <c:if test="${order.type==1}">
               <c:if test="${order.end_date>=temp}">服务中</c:if>
               <c:if test="${order.end_date<temp}">已结束</c:if>
           </c:if>
           <c:if test="${order.type==2}">
               <c:if test="${order.begin_date>=temp}">服务中</c:if>
               <c:if test="${order.begin_date<temp}">已结束</c:if>
           </c:if>
	    </span></p>
	    <p class="dd_detail"><span class="detail_l fl">订单类型</span><span class="detail_r fl">
	       <c:if test="${order.type==1}">长期</c:if>
           <c:if test="${order.type==2}">单次</c:if> 
	    </span></p>
	    <p class="dd_detail"><span class="detail_l fl">订单开始时间</span><span class="detail_r fl"><fmt:formatDate value="${order.begin_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
	    <c:if test="${order.type==1}">
	       <p class="dd_detail"><span class="detail_l fl">订单结束时间</span><span class="detail_r fl"><fmt:formatDate value="${order.end_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
	    </c:if>
	    <p class="dd_detail"><span class="detail_l fl">订单服务类型</span><span class="detail_r fl">${order.name }</span></p>
	    <c:if test="${order.parentC==1}">
	    <c:if test="${order.type==1}">
           <p class="dd_detail"><span class="detail_l fl">检测周期</span><span class="detail_r fl">
                <c:if test="${order.serviceId==1}">
                    <c:if test="${order.scan_type==1}">每天</c:if>
                    <c:if test="${order.scan_type==2}">每周</c:if>
                    <c:if test="${order.scan_type==3}">每月</c:if>
                </c:if>
                <c:if test="${order.serviceId==3}">
                    <c:if test="${order.scan_type==1}">30分钟</c:if>
                    <c:if test="${order.scan_type==2}">1小时</c:if>
                    <c:if test="${order.scan_type==3}">2小时</c:if>
                    <c:if test="${order.scan_type==4}">1天</c:if>
                </c:if>
                <c:if test="${order.serviceId==5}">
                    <c:if test="${order.scan_type==1}">10分钟</c:if>
                    <c:if test="${order.scan_type==2}">30分钟</c:if>
                    <c:if test="${order.scan_type==3}">1小时</c:if>
                    <c:if test="${order.scan_type==4}">2小时</c:if>
                </c:if>
           </span></p>
           <p class="dd_detail"><span class="detail_l fl">最近检测时间</span><span class="detail_r fl">
           <c:if test="${checkTime>0 }">
                <fmt:formatDate value="${lastTime.execute_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
           </c:if>
           <c:if test="${checkTime==0 }">暂无</c:if>
           </span></p>
           <p class="dd_detail"><span class="detail_l fl">检测次数</span>
           <span class="detail_r fl">
           <c:if test="${checkTime>0 }"><a href="${ctx}/historyInit.html?orderId=${order.id }" target="_blank">${checkTime }次</a></c:if>
           <c:if test="${checkTime==0 }">${checkTime }次</c:if>
           </span></p>
        </c:if>
	    <!-- <p class="dd_detail"><span class="detail_l fl">服务情况跟踪</span><span class="detail_r fl">
	xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</span></p> -->
		<p class="dd_detail"><span class="detail_l fl">服务对象资</span>
	        <c:forEach var="asset" items="${assetList}" varStatus="status">
	        <span class="detail_r fl">${asset.name }</span>
	        </c:forEach>
	    </p>
	    </c:if>
	    <c:if test="${order.parentC==2}">
	       <c:forEach var="list" items="${ipList}" varStatus="status">
	       <p class="dd_detail"><span class="detail_l fl">监控对象</span><span class="detail_r fl">${list.ip }</span></p>
           </c:forEach>
           <c:if test="${order.type==1}">
                <p class="dd_detail"><span class="detail_l fl">累计告警数</span><span class="detail_r fl"></span></p>
           </c:if>   
	    </c:if>
	</c:forEach>
    
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
</div>
</body>
</html>
