<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"%>
<%
	request.setAttribute("vEnter", "\n");
	//奇怪的是这一行我用 <c:set var="vEnter" value="\n" scope="request"/>
	//都没用,估计是set标签里对value值处理了一下,没jstl的源码,不清楚JSTL是怎么处理的.
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>我的订单-系统安全帮详情</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/source/manageCss/common.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/source/manageCss/index.css" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/source/css/prompt.css" type="text/css"
	rel="stylesheet" />

<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/popBox.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/source/css/portalindex.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<!--<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
-->
<script type="text/javascript"
	src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript"
	src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript"
	src="${ctx}/source/scripts/echarts/echarts.js"></script>

<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<%-- <script type="text/javascript"
	src="${ctx}/source/scripts/order/apiDetail.js"></script> --%>
<style type="">
.detailbox {
	display: none;
}

.zhangd_table {
	width: 945px;
	color: 9a9a9a;
	margin-left: 35px;
}

.zhangd_table table {
	width: 945px;
	text-align: center;
	word-break: break-all;
	word-wrap: break-word;
	border-collapse: collapse;
	border: 0;
}

.zhangd_table table tr {
	height: 50px;
	line-height: 50px;
	font-size: 14px;
	border-bottom: 1px solid #e0e0e0;
}

.zhangd_table table tr td span {
	color: #49ad53;
	cursor: pointer;
}

.search {
	width: 78px;
	background: none;
	background-color: #f3f3f3;
	color: #343434;
	font-size: 14px;
	height: 38px;
	line-height: 38px;
	border: none;
	border-left: #e5e5e5 solid 1px;
	cursor: pointer;
	position: absolute;
}
</style>
</head>

<body>
	<div id="container">
		<!--=============top==============-->

		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
		<div class="detaails">
			<input type="hidden" id="orderIdHidden" value="${order.id}" />
			<div class="coreRight user_right coupon" style="width: 1154px;">
				<div class="gj_top" style="width: 1100px">
					<a href="#" class="acur">系统安全帮详情</a>
				</div>
				<div class="Ordernumber">
					<p>
						<span>订单号：</span>${order.id }</p>
				</div>
				<div class="Basement">
					<p>
						<span>用户Id:</span>${baseInfo.userId}</p>
					<p>
						<span>用户名:</span>${baseInfo.name}</p>
					<p>
						<span>orderListId:</span>${baseInfo.orderListId}</p>
				</div>
				
			</div>
		</div>
		<!--============bottom============-->
		<!-- footer start -->
		<c:import url="/footer.html"></c:import>
		<!-- footer end -->
	</div>
</body>
</html>
