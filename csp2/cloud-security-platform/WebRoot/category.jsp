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

	<body>
		<div class="Divlist listJs fl">
			<a href="#" class="hbule">商品分类<i></i>
			</a>
			<ul class="list listl">
				<li>
					<a href="${ctx}/web_anquanbang.html">网站安全帮</a>
					<ol id="anquanbang_ol">
						<c:forEach var="list" items="${servList}" varStatus="status">
							<c:if test="${list.factory == 1}">
							<c:if test="${status.count == 1}">
							  <li style="border: none;">
							</c:if>
							<c:if test="${status.count != 1}">
							  <li>
							</c:if>
									<a href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">${list.name }</a>
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
								<a href="${ctx}/selfHelpOrderAPIInit.html?apiId=${apiList.id }&indexPage=2">${apiList.name }</a>
							</li>
						</c:forEach>
					</ol>
				</li>
				<li>
					<a href="${ctx}/sa_anquanbang.html">安全态势感知</a>
				</li>
				<li style="border: none;">
					<a href="${ctx}/Xpage.html">x专区</a>
				</li>
			</ul>
		</div>
	</body>
</html>
