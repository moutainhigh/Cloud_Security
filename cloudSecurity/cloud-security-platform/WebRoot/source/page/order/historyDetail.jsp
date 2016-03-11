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
<title>订单跟踪-历史记录</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
</head>

<body>
<div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <!-- 订单详情-->
  <div class="user_right" style="margin:auto;float:none;">
    <div class="gj_top"> <a href="#" class="aelse">订单跟踪</a>　>　<a href="#" class="acur">历史记录</a> </div>
    <c:forEach var="order" items="${orderList}" varStatus="status">
	    <p style="margin:0 0 38px 286px;"><span class="bigfont">订单编号</span><span class="bigfont" style="margin-right:20px;">${order.id }</span><span class="bigfont">下单时间</span><span class="bigfont"><fmt:formatDate value="${order.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
	    <c:forEach var="check" items="${checkTime}" varStatus="status">
		    <c:if test="${!status.first}">
	        <p class="dd_detail"><span class="detail_l fl">历史检测时间${status.index}</span><span class="detail_r fl"><fmt:formatDate value="${check.execute_time }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
		    </c:if>
	    </c:forEach>
	</c:forEach>
   </div>
</div>
<!-- 尾部代码开始-->
<div class="safeBox">
<div class="safe04">
	<div class="imgBox clearfix">
		<div class="footL fl">
			<a href="#"><img src="${ctx}/source/images/portal/footlogo.png" alt=""></a>
		</div>
		<ol class="footr clearfix fr">
			<li><a href="#">关于我们</a></li>
			<li><a href="${ctx}/joinUs.html" target="_blank">加入我们</a></li>
			<li>
				<a href="#">关于安全帮</a>
				<em>QQ 470899318</em>
			</li>
			<li class="weixn weishow">
				<em>微信</em>
				<span style="display:none"><img src="${ctx}/source/images/portal/weixin.jpg" alt=""></span>
			</li>
			
		</ol>
		
	</div>
</div>
<div class="foot">
	<p>版权所有Copyright © 2015 中国电信股份有限公司北京研究院京ICP备12019458号-10</p>
</div>
</div>
</div>
</body>
</html>
