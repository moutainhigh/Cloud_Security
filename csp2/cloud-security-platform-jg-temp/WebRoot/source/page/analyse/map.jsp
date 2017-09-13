<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>模拟动态攻击效果</title>
<link rel="stylesheet" href="${ctx}/source/attacking/css/attacking.css" type="text/css"></link>
</head>
<body>
<div class="background-image"><img src="${ctx}/source/attacking/img/body-background.png" alt="background-image" /></div>
<div class="content">
	<div class="data attack-type box gray-bg">
		<div id="attack-type-table-container">
			<table id="attack-type">
				<colgroup>
					<col class="col-type" span="1">
					<col class="col-number" span="1">
				</colgroup>
				<tbody>
					<tr class="second-level">
						<th class="th-title">攻击类型</th>
						<th class="th-title">
							<div class="title-circle">
								<div class="title-triangle"></div>
							</div>
						</th>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="console box gray-bg">
		<div id="console">
			<table id="events">
				<colgroup class="second-level">
					<col class="col-timestamp" span="1">
					<col class="col-border" span="1">
					<col class="col-attacker-org" span="1">
					<col class="col-border" span="1">
					<col class="col-attacker-ip" span="1">
					<col class="col-border" span="1">
					<col class="col-attacked-org" span="1">
					<col class="col-border" span="1">
					<col class="col-attacked-ip" span="1">
					<col class="col-border" span="1">
					<col class="col-service" span="1">
					<col class="col-border" span="1">
					<col class="col-port" span="1">
				</colgroup>

				<tbody>
					<tr class="second-level">
						<th class="th-title col-timestamp">时间</th>
						<th class="col-border"></th>
						<th class="th-title col-attacker-org">攻击方</th>
						<th class="col-border"></th>
						<th class="th-title col-attacker-ip">攻击方IP</th>
						<th class="col-border"></th>
						<th class="th-title col-attacked-org">被攻击方</th>
						<th class="col-border"></th>
						<th class="th-title col-attacked-ip">被攻击IP</th>
						<th class="col-border"></th>
						<th class="th-title col-service">攻击类型</th>
						<th class="col-border"></th>
						<th class="th-title col-port">端口</th>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="${ctx}/source/attacking/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/attacking/js/d3/d3.min.js"></script>
<script type="text/javascript" src="${ctx}/source/attacking/js/d3/d3.geo.projection.min.js"></script>
<script type="text/javascript" src="${ctx}/source/attacking/js/plugins/queue.min.js"></script>
<script type="text/javascript" src="${ctx}/source/attacking/js/plugins/topojson.min.js"></script>
<script type="text/javascript">
var typejson ='${wafEventTypeCount}';
console.log(typejson);
</script>
<script type="text/javascript" src="${ctx}/source/attacking/js/attacking.js"></script>
</body>
</html>
