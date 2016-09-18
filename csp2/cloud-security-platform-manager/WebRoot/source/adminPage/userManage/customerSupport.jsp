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
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
      	<li><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
		<li><a href="${ctx}/adminchinas.html" target="_blank" class="white">安全态势</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAssetUI.html" class="white">资产分析</a></li>
        <li><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
        <!-- <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">订单分析</a></li>-->
        <li><a href="${ctx}/orderformanalyse.html" class="white">订单分析</a></li>
        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li><a href="${ctx}/adminAPIAnalysisUI.html" class="white">API分析</a></li>
        <li><a href="${ctx}/adminLogsAnalysisUI.html" class="white">日志分析</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
        <li class="b_current"><a href="${ctx}/customerSupportUI.html" class="white">客服支持</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/adminImages/b_photo.jpg" width="43" height="42"></a> </div>
      <div class="lagst-right">
        <p ><a href="###" class="white">${sessionScope.admin_user.name }</a></p>
        <p> <a href="${ctx}/adminExit.html" class="white">退出</a></p>
      </div>
    </div>
  </div>
</div>
<!--头部代码结束-->
<div class="main_wrap">
	<div class="main_center user">
    	<ul class="main_nav tabList clearfix">
        	<li class="active">用户信息查询<i></i></li>
            <li class="">订单查询<i></i></li>
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

						<div class="tableUser" style="height:260px; overflow-y:auto;">
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
			</div>

		</div>
</div>
<!--尾部部分代码-->
<div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
      <li><a href="${ctx}/registUI.html">新用户注册</a></li>
    <li><a href="${ctx}/loginUI.html">用户登录</a></li>
    <li><a href="###">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
 <h3><a href="###"> 帮助</a></h3>
  <ul>
  <li><a href="${ctx}/aider.html">常见问题</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###">厂商合作</a></h3>
  <ul>
    <li><a href="###">华为</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">知道创宇</a></li>
  </ul>
</div>
<div  class="bottom_main">
<h3><a href="###">联系我们</a></h3>
<ul>
<li><a href="###">客户电话</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>
 <ul>
 <li>Copyright&nbsp;©&nbsp;2015 中国电信股份有限公司北京研究院<br />
京ICP备12019458号－10</li>
</div>
</div>
</div>
<!--尾部部分代码结束-->
</body>
</html>
