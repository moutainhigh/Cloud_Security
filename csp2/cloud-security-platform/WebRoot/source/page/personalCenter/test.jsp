<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>
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
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>我的订单-API详情</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />

<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet" />	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts3/echarts.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/doudong_test.js"></script>

</head>

<body>
		<div>最近一小时内漏洞跟踪</div>
          <div style="padding-left:100px; width:600px; height:500px;" id="serviceUseInfo">
        </div><!--
         <div>重大漏洞分布视图</div>
		<div  style="width:600px; height:500px;" id="vulnscanAlarmTOP20">		            
		 </div>
		--><div>最近6个月各等级漏洞分布</div>
		<div  style="width:100%; height:500px;" id="vulnscanAlarmByLevelMonth6">		            
		 </div>
		<div>用户最近6个月内不同类型服务订单数量变化</div>
		<div  style="width:600px; height:500px;" id="serviceUseInfoMonth6">		            
		 </div>
		 
		 <div style="width:116px">用户行业分布</div>
 				<div  style="width:600px; height:600px;" id="service1OneHour">
         </div>
        <div style="width:116px">最近一小时内攻击跟踪</div>
 				<div  style="width:600px; height:500px;" id="wafOneHour">
         </div>
        <div style="width:116px">订单集中的时间点</div>
 				<div  style="width:600px; height:500px;" id="orderServiceTimes">
         </div>
</body>
</html>