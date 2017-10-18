<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>订单跟踪-订单详情</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<!-- <SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT> -->
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<!-- add by 2016-02 -->
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/main.js"></script>
</head>

<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
      	<li><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
		<li><a href="${ctx}/adminchinas.html" target="_blank" class="white">安全态势</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAssetUI.html" class="white">资产分析</a></li>
        <li><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
        <!-- <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">订单分析</a></li>-->
        <li><a href="${ctx}/orderformanalyse.html" class="white">订单分析</a></li>
        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li><a href="${ctx}/adminAPIAnalysisUI.html" class="white">API分析</a></li>
        <li><a href="${ctx}/adminLogsAnalysisUI.html" class="white">日志分析</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
        <li class="b_current"><a href="${ctx}/customerSupportUI.html" class="white">客服支持</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/adminImages/b_photo.jpg" width="43" height="42"></a> </div>
      <div class="lagst-right">
        <p ><a href="###" class="white">${sessionScope.admin_user.name }</a></p>
        <p> <a href="${ctx}/adminExit.html" class="white">退出</a></p>
      </div>
    </div>
  </div>
</div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <!-- 订单详情-->
  <div class="user_right" style="margin:auto;float:none;">
    <div class="gj_top"><a href="#" class="acur">订单详情</a> </div>
    <c:forEach var="order" items="${orderList}" varStatus="status">
	    <p style="margin:0 0 38px 286px;"><span class="bigfont">订单编号</span><span class="bigfont" style="margin-right:20px;">${order.id }</span><span class="bigfont">下单时间</span><span class="bigfont"><fmt:formatDate value="${order.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
	    <p class="dd_detail"><span class="detail_l fl">订单状态</span><span class="detail_r fl">
           <c:set var="temp" value="${nowDate }"/>
           <c:if test="${order.status==0}">已下单</c:if>
           <!-- <c:if test="${order.begin_date<=temp&&order.status==0}">服务中</c:if>
           <c:if test="${order.status==1||order.status==2}">已结束</c:if> -->
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
                    <c:if test="${order.scan_type==5}">每周</c:if>
                    <c:if test="${order.scan_type==6}">每月</c:if>
                </c:if>
                <c:if test="${order.serviceId==2}">
                	<c:if test="${order.scan_type==1}">30分钟</c:if>
                    <c:if test="${order.scan_type==2}">1小时</c:if>
                    <c:if test="${order.scan_type==3}">2小时</c:if>
                    <c:if test="${order.scan_type==4}">1天</c:if>
                    <c:if test="${order.scan_type==5}">每周</c:if>
                    <c:if test="${order.scan_type==6}">每月</c:if>
                </c:if>
                <c:if test="${order.serviceId==3}">
                    <c:if test="${order.scan_type==2}">30分钟</c:if>
                    <c:if test="${order.scan_type==3}">1小时</c:if>
                    <c:if test="${order.scan_type==4}">1天</c:if>
                </c:if>
                <c:if test="${order.serviceId==4}">
                    <c:if test="${order.scan_type==2}">30分钟</c:if>
                    <c:if test="${order.scan_type==3}">1小时</c:if>
                    <c:if test="${order.scan_type==4}">1天</c:if>
                </c:if>
                <c:if test="${order.serviceId==5}">
                    <c:if test="${order.scan_type==1}">10分钟</c:if>
                    <c:if test="${order.scan_type==2}">30分钟</c:if>
                    <c:if test="${order.scan_type==3}">1小时</c:if>
                </c:if>
           </span></p>
           <p class="dd_detail"><span class="detail_l fl">最近检测时间</span><span class="detail_r fl">
           <c:if test="${checkTime>0 }">
                <fmt:formatDate value="${lastTime.group_flag }" pattern="yyyy-MM-dd HH:mm:ss"/>
           </c:if>
           <c:if test="${checkTime==0 }">暂无</c:if>
           </span></p>
           <!-- <p class="dd_detail"><span class="detail_l fl">检测次数</span>
           <span class="detail_r fl">
           <c:if test="${checkTime>0 }"><a href="${ctx}/warningInit.html?orderId=${order.id }&type=${order.type}&websoc=${order.websoc}" target="_blank">${checkTime }次</a></c:if>
           <c:if test="${checkTime==0 }">${checkTime }次</c:if>
           </span></p> -->
        </c:if>
	    <!-- <p class="dd_detail"><span class="detail_l fl">服务情况跟踪</span><span class="detail_r fl">
	xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</span></p> -->
		<div class="dd_detail">
		    <div class="detail_l fl"><span style="line-height:30px;">服务对象资产</span></div>
		    <div class="detail_r fl">
	        <c:forEach var="asset" items="${assetList}" varStatus="status">
	        <span style="display:block; width:600px;line-height:30px; ">${asset.name }&nbsp;(${asset.addr })</span>
	        </c:forEach>
	        </div>
	    </div>
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
<!-- 尾部代码结束 -->
</body>
</html>
