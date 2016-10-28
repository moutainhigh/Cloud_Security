<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>运营管理</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
		<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
		<script type="text/javascript" src="${ctx}/source/scripts/echarts3/echarts.js"></script>
		<script type="text/javascript" src="${ctx}/source/scripts/order/anquan_state.js"></script>
		<script type="text/javascript">
			$(function(){
				showOrderServiceTimesIndex();
			});
			
		</script>
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
