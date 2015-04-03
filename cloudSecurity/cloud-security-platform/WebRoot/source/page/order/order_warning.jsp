<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单跟踪-告警详情</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script src="${ctx}/source/scripts/echarts/esl.js"></script>
<script src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/warning.js"></script>
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
  
    <!-- 告警详情-->
     <div class="user_right" style="margin:auto;float:none;">
        <div class="gj_top">
            <a href="#" class="aelse">订单跟踪</a>　>　<a href="#" class="acur">告警详情</a>
        </div>
       <c:forEach var="order" items="${orderList}" varStatus="status">
        <div class="gj_title">
            <input type="hidden" value="${order.id }" id="orderId"/>
            <input type="hidden" value="${order.type }" id="type"/>
            <p><span class="bigfont">${order.name }</span>
            <span>(  订单编号：${order.id }  )</span>
            <c:if test="${order.type==1 }">
                <a href="${ctx}/historyInit.html?orderId=${order.id }" target="_blank"><span style="float:right; margin-right:30px; dispiay:inline-block;color:#999; ">历史记录</span></a>
            </c:if>
            </p>  
            <p>监控IP：<span>
            <c:forEach var="ip" items="${ipList}" varStatus="status">${ip.ip }&nbsp;&nbsp;</c:forEach>
            </span></p>          
        </div>
       </c:forEach>
       
    <div class="zhangd_table" style="margin-left: -63px;">
      <table class="ld_table" style="width: 1004px; border-left:1px solid #888;border-right:1px solid #888;border-bottom:1px solid #888;border-top:1px solid #888;height: auto;">
        <tbody>                                                                                   
          <tr style="background:#e0e0e0; height:30px; line-height:0px;">
            <td style="width:5%;">序号</td>
            <td  style="width:23%;">告警上报时间</td>
            <c:if test="${serviceId==6 }">
                <td  style="width:7%;">漏洞级别</td>
            	<td  style="width:24%;">攻击开始时间</td>
            	<td>攻击源IP</td>
            	<td  style="width:33%;">攻击流量信息</td>
            </c:if>
            <c:if test="${serviceId==7 || serviceId==8}">
            <td>攻击源IP</td>
            <td>攻击类型</td>
            <td>攻击流量</td>
            <td style="width:23%;">攻击开始时间</td>
            <td>攻击结束时间</td>
            <td>攻击持续时间</td>
            </c:if>
          </tr>
          <c:forEach var="alarm" items="${alarmList}" varStatus="status">
	          <tr>                                            
	            <td>${alarm.id }</td>
	            <td><fmt:formatDate value="${alarm.alarm_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	            <c:if test="${serviceId==6 }">
	            <td>
	               <c:if test="${alarm.attack_type==0}">低</c:if>
                   <c:if test="${alarm.attack_type==1}">中</c:if>
                   <c:if test="${alarm.attack_type==2}">高</c:if> 
	            </td>
	            <td><fmt:formatDate value="${alarm.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	            <td>${alarm.addr_ip }</td>
	            <td>${alarm.attack_flow }</td>
	            </c:if>
			<c:if test="${serviceId==7 || serviceId==8}">
			<td>${alarm.addr_ip }</td>
			<td>${alarm.attack_type }</td>
			<td>${alarm.attack_flow }</td>
			<td><fmt:formatDate value="${alarm.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${alarm.end_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${alarm.attack_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			
			
			
			
			
			</c:if>
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
</div>

</body>
</html>
