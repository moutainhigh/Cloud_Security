<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", -10); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>客服支持</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>

<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/raphael.2.1.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/justgage.1.0.1.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/customerSupport.js"></script>
</head>

<body>
<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
<div class="main_wrap">
	<div class="main_center user">
    	<ul class="main_nav tabList clearfix">
        	<li class="active">用户信息查询<i></i></li>
            <li class="">订单查询<i></i></li>
            <li class="">资源信息查询<i></i></li>
        </ul>
			<div class="tabBox tabCont">
				<div class="item clearfix tabItem" style="display:block">
					<!--用户信息查询-->
					<form class="clearfix analysecent user_form" id="activeForm"
						method="post" action="${ctx}/customerSupportUI.html">

						<div class="analyse_lable fl">
							<label>用户名</label> 
							<input type="text" class="text" id="username" name="username" value="">
							<label>邮箱</label> 
							<input type="text" class="text" id="email" name="email" value="">
							<label>电话</label> 
							<input type="text" class="text" id="mobile" name="mobile" value=""> 
							<label>订单编号</label> 
							<input type="text" class="text" id="orderno" name="orderno">
						</div>
						<div class="analyse_lable fl">
							<label>资产名称</label> 
							<input type="text" class="text" id="assetname" name="assetname"> 
							<label>资产地址</label> 
							<input type="text" class="text" id="assetaddr" name="assetaddr">
						</div>
						<input type="button" class="sub" value="" style="right:-130px;" onclick="activeCustomerSupport()">

					</form>
					<div id="userInfoTab">
						<div class="tableBox" style="margin-top:10px;">

							<div class="tableUser" style="">
								<table cellpadding="0" cellspacing="0" border="1" width="954"
									bordercolor="#e0e0e0">
									<thead>
										<tr style="width:100%">
											<th>用户名</th>
											<th>单位</th>
											<th>所属行业</th>
											<th>电话</th>
											<th>注册时间</th>
										</tr>
									</thead>
									<tbody id="userInfo">
									</tbody>
								</table>
							</div>

							<div class="tableUser" style="margin-top:10px;">
								<table cellpadding="0" cellspacing="0" border="1" width="954"
									bordercolor="#e0e0e0">
									<thead>
										<tr style="width:100%">
											<th>资产名称</th>
											<th>资产地址</th>
										</tr>
									</thead>
									<tbody id="assetInfo">
									</tbody>
								</table>
							</div>
							<div class="tableUser" style="margin-top:10px;">
								<table cellpadding="0" cellspacing="0" border="1" width="954"
									bordercolor="#e0e0e0">
									<thead>
										<tr style="width:100%">
											<th>订单编号</th>
											<th>下单时间</th>
											<th>下单用户</th>
											<th>订单类型</th>
											<th>订单服务起止时间</th>
											<th>服务类型</th>
										</tr>
									</thead>
									<tbody id="orderInfo">
									</tbody>
								</table>

							</div>
						</div>
					</div>
				</div>
				<div class="item tabItem">
					<form class="clearfix analysecent user_form">

						<div class="analyse_lable fl">
							<label>订单编号</label> 
							<input type="text" class="text" id="orderno2" name="orderno2">
							<label>下单时间</label> 
							<input type="text" class="text" id="order_createdate_start" name="begin_date"
								onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
							<label>-</label> 
							<input type="text" class="text" id="order_createdate_end" name="end_date"
								onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
							<label>服务时间</label> 
							<input type="text" class="text" id="service_startdate" name="begin_date"
								onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
							<label>-</label> 
							<input type="text" class="text" id="service_enddate" name="end_date"
								onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						</div>
						<div class="analyse_lable fl">
							<label>下单用户</label> 
							<input type="text" class="text" id="orderusername" name="orderusername">
							<label>服务类型</label> 
							<select class="text" id="isApiSel">
								<option value="0">监测服务</option>
								<option value="1">API服务</option>
								<option value="2">WAF防护</option>
							</select>
							<label>资产名称</label> 
							<input type="text" class="text" id="assetname2" name="assetname2"> 
							<label>资产地址</label> 
							<input type="text" class="text" id="assetaddr2" name="assetaddr2">
						</div>
						<input type="button" class="sub" value="" style="right:-130px;"
							onclick="activeQueryOrder()">
					</form>
					<div class="tableBox">

						<div class="tableUser" style="height:400px; overflow-y:auto;">
							<table cellpadding="0" cellspacing="0" border="1" width="954"
								bordercolor="#e0e0e0">
								<thead>
									<tr style="width:100%">
										<th>订单编号</th>
										<th>下单时间</th>
										<th>下单用户</th>
										<th>订单类型</th>
										<th>订单服务起止时间</th>
										<th>服务类型</th>
									</tr>
								</thead>
								<tbody id="queryOrder">
								</tbody>
							</table>

						</div>

					</div>
				</div>
				
				<!-- 资源信息查询  add by tang 2016-10-9 -->
				<div class="item tabItem">
					<form class="clearfix analysecent user_form">

						<div class="analyse_lable fl">
							<label>资源名称</label> 
							<input type="text" class="text" id="resourcename" name="resourcename"> 
							<label>资源地址</label> 
							<input type="text" class="text" id="resourceaddr" name="resourceaddr">
							<label>资源能力</label> 
							<select class="text" id="isApiSel">
								<option value="0">监测服务</option>
								<!-- <option value="1">API服务</option>
								<option value="2">WAF防护</option> -->
							</select>
							
						</div>
						<input type="button" class="sub" value="" style="right:-130px;"
							onclick="activeQueryResource()">
					</form>
					<div class="tableBox">

						<div class="tableUser" style="height:260px; overflow-y:auto;">
							<table cellpadding="0" cellspacing="0" border="1" width="954"
								bordercolor="#e0e0e0">
								<thead>
									<tr style="width:100%">
										<th>资源名称</th>
										<th>资源地址</th>
										<th>资源能力</th>
										<th>资源运行状态</th>
										<th>CPU占用率</th>
										<th>内存占用率</th>
										<th>磁盘占用率</th>
										<th>进行中任务数</th>
									</tr>
								</thead>
								<tbody id="queryResource">
								</tbody>
							</table>

						</div>

					</div>
				</div>
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
