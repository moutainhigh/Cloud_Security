<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<body>
		<div id="top">
			<div class="cover">
			<div class="top-wrap">
				<a class="logo" href="adminWelcomeUI.html"/></a>
				<div class="hello">
					<a class="a_login" href="javascript:;">${sessionScope.admin_user.name }</a>
					|
					<a href="${ctx}/adminExit.html">退出</a>
				</div>
				<div class="refresh" id="synData">数据同步</div>
				<ul class="nav">
					<li>
						<a style="color: #ffffff;font-size: 18px;" href="adminWelcomeUI.html"/>首页</a>
					</li>
					<li>
						信息管理
						<div class="lists">
							<div class="list-inner">
								<p class="li-inner-title">软件管理</p>
								<p><a href="${ctx}/adminUserManageUI.html">用户管理</a></p>
								<!-- <p><a href="${ctx}/adminServUI.html">服务管理</a></p> -->
								<p><a href="${ctx}/adminNoticeManageUI.html">公告管理</a></p>
								<p></p>
								<p></p>
							</div>
							<div class="list-inner">
								<p class="li-inner-title">硬件管理</p>
								<p><a href="${ctx}/equResourceUI.html">设备资源管理</a></p>
								<p><a href="${ctx}/adminSystemManageUI.html">系统管理</a></p>
								<p></p>
								<p></p>
							</div>
							<div class="list-inner">
								<p class="li-inner-title">运营管理</p>
								<p><a href="${ctx}/customerSupportUI.html">客服支持系统</a></p>
								<p><a href="${ctx}/getServiceList.html">商品详情配置</a></p>
								<p><a href="${ctx}/adminAdvertisementManageUI.html">广告区配置</a></p>
								<p><a href="${ctx}/monitor.html">可用性监控</a></p>
							</div>
						</div>
					</li>
					<li>
						数据分析
						<div class="lists">
							<div class="list-inner">
								<p class="li-inner-title">基础分析</p>
								<p><a href="${ctx}/adminUserAnalysisUI.html">用户分析</a></p>
								<p><a href="${ctx}/adminDataAssetUI.html">资产分析</a></p>
								<p><a href="${ctx}/adminLogsAnalysisUI.html">平台日志分析</a></p>
								<p></p>
							</div>
							<div class="list-inner">
								<p class="li-inner-title">结果分析</p>
								<p><a href="${ctx}/orderformanalyse.html">订单分析</a></p>
								<p><a href="${ctx}/adminWarnAnalysisUI.html">告警分析</a></p>
								<p><a href="${ctx}/adminAPIAnalysisUI.html">API分析</a></p>
								<p><a href="${ctx}/financialManage.html">财务统计分析</a></p>
							</div>
						</div>
					</li>
					<li><a style="color: #ffffff;font-size: 18px;" href="${ctx}/sa_anquanbang.html" target="_blank">安全态势</a></li>
				</ul>
			</div>
			</div>
		</div>
			
	</body>

	<script src="${ctx}/source/scripts/backstageJS/index.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/source/scripts/adminJs/synData.js"></script>
</html>
