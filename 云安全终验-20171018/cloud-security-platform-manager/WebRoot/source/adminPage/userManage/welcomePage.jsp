<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		<title>运营管理</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
		<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
		
		<script type="text/javascript" src="${ctx}/source/scripts/echarts3/echarts.js"></script>
		<script type="text/javascript" src="${ctx}/source/scripts/order/anquan_state.js"></script>
	<!-- 	<script type="text/javascript">
			$(function(){
				showOrderServiceTimesIndex();
			});
			
		</script> --> 
	</head>
	<body>
		<div id="container">
			<!--=============top==============-->
			
			<!-- menu start -->
			<c:import url="/menu.html"></c:import>
			<!-- menu end -->
			
			<!--===========content============-->
			<div id="content">
				<div class="numbers">
					<ul class="num-wrap">
						<li>
							<div class="num">${userSum }</div>
							<div>用户数</div>
						</li>
						<li>
							<div class="num">${webSite }</div>
							<div>网站检测数</div>
						</li>
						<li class="right">
							<div style="width: auto;" class="num">${webPageNum }</div>
							<div>网页数</div>
						</li>
						<li>
							<div class="num">${orderSum }</div>
							<div>订单数</div>
						</li>
						<li>
							<div class="num">${wafNum }</div>
							<div>防护网站数</div>
						</li>
						<li class="right">
							<div style="width: auto;" class="num">${leakNum }</div>
							<div>告警数</div>
						</li>
					</ul>
				</div>
				<div class="charts" id="orderServiceTimes" style="text-align: center;margin: auto;padding-top:30px;padding-bottom:70px;width:1130px;height:400px"></div>
			</div>
			
			<!--============bottom============-->
			<!-- footer start -->
			<c:import url="/footer.html"></c:import>
			<!-- footer end -->
			
			
			
		</div>
	</body>
	
</html>
