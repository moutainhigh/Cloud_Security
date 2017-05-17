<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<!-- <link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
		 <script src="${ctx}/source/scripts/common/main.js"></script>
		<script src="${ctx}/source/scripts/common/portalindex.js"></script>-->
	</head>
<script type="text/javascript">
function buyService(serviceId){
	$("#category_serviceId").val(serviceId);
	if(serviceId != 6){
		$("#selfHelpOrderInitForm").submit();
	}else{
		$("#wafDetailsForm").submit();
	}
}
function buyServiceAPI(apiId){
	$("#category_apiId").val(apiId);
	$("#selfHelpOrderAPIInitForm").submit();
}
function buySystemService(servId){
	$("#category_systemServId").val(servId);
	$("#systemAnquanbangDetailUIForm").submit();
}
</script>
	<body>
		<div class="Divlist listJs fl" style="margin-right:48px;">
			<a href="#" class="hbule">商品分类<i></i>
			</a>
			<ul class="list listl">
				<li>
					<a href="${ctx}/web_anquanbang.html">网站安全帮</a>
					<ol id="anquanbang_ol">
						<c:forEach var="list" items="${servList}" varStatus="status">
							<c:if test="${list.id < 6}">
								<c:if test="${status.count == 1}">
								  <li style="border: none;">
								</c:if>
								<c:if test="${status.count != 1}">
								  <li>
								</c:if>
									<!--<a href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">${list.name }</a>
									-->
									<a href="javascript:;" onclick="buyService(${list.id });">${list.name }</a>
									<form action="selfHelpOrderInit.html" method="post" id="selfHelpOrderInitForm">
										<input type="hidden" name="type" value="${list.orderType }"/>
										<input type="hidden" id="category_serviceId" name="serviceId" value="${list.id }"/>
										<input type="hidden" name="indexPage" value="1"/>
									</form>
								</li>
							</c:if>
							<c:if test="${list.id == 6}">
								<li>
										<!--<a href="${ctx}/wafDetails.html?serviceId=${list.id }&indexPage=1">${list.name }</a>
									-->
									<a href="javascript:;" onclick="buyService(${list.id });">${list.name }</a>
									<form action="wafDetails.html" method="post" id="wafDetailsForm">
										<input type="hidden" id="category_serviceId" name="serviceId" value="${list.id }"/>
										<input type="hidden" name="indexPage" value="1"/>
									</form>
								</li>
							</c:if>
						</c:forEach>
					</ol>
				</li>
				<li>
					<a href="${ctx}/api_anquanbang.html">安全能力API</a>
					<ol id="anquanbangAPI_ol">
						<c:forEach var="apiList" items="${servAPIList}"  varStatus="status">
							<c:if test="${status.count == 1}">
							  <li style="border: none;">
							</c:if>
							<c:if test="${status.count != 1}">
							  <li>
							</c:if>
								<!--<a href="${ctx}/selfHelpOrderAPIInit.html?apiId=${apiList.id }&indexPage=2">${apiList.name }</a>
							-->
							<a href="javascript:;" onclick="buyServiceAPI(${apiList.id });">${apiList.name }</a>
							<form action="selfHelpOrderAPIInit.html" method="post" id="selfHelpOrderAPIInitForm">
								<input type="hidden" id="category_apiId" name="apiId" value="${apiList.id }"/>
								<input type="hidden" id="indexPage" name="indexPage" value="2"/>
							</form>
							</li>
						</c:forEach>
					</ol>
				</li>
				<li>
					<a href="${ctx}/system_anquanbang.html">系统安全帮</a>
					<ol id="anquanbangAPI_ol">
						<c:forEach var="serv" items="${systemServList}"  varStatus="status">
							<c:if test="${status.count == 1}">
							  <li style="border: none;">
							</c:if>
							<c:if test="${status.count != 1}">
							  <li>
							</c:if>
							<a href="javascript:;" onclick="buySystemService(${serv.id });">${serv.name }</a>
							
							<form action="systemOrderOperaInit.html" method="post" id="systemAnquanbangDetailUIForm">
							<!--
							systemOrderOperaInit.html systemOrderOperaInit
							<form action="systemAnquanbangDetailUI.html" method="post" id="systemAnquanbangDetailUIForm">
							-->
								<input type="hidden" id="category_systemServId" name="serviceId" value="${serv.id }"/>
								<input type="hidden" id="indexPage" name="indexPage" value="3"/>
							</form>
							</li>
						</c:forEach>
					</ol>
				</li>
				<li>
					<a href="${ctx}/sa_anquanbang.html" target="_blank">安全大数据</a>
				</li>
				<li>
					<a href="${ctx}/malicious_URL.html" target="_blank">钓鱼网站监测</a>
				</li>
				<li style="border: none;">
					<a href="${ctx}/Xpage.html">X专区</a>
				</li>
			</ul>

		</div>
	</body>
</html>
