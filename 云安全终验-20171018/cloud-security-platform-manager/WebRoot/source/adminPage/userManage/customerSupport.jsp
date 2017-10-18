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
<title>客服支持系统</title>
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
<script type="text/javascript">
$(function() {
     $(".data_cuo").on('click', function() {
        $(".mark,.data_tanc").hide();
     });
     
     $(".data_btn").on('click', function() {
        $(".mark,.data_tanc").hide();
     });
 });

</script>
</head>

<style type="text/css">
   .data_box{ width: 1180px; margin-top: 20px;margin-bottom: 60px; }
   .data_nav{ height: 38px; border-bottom: 1px solid #cfcfcf; margin-bottom: 10px;} 
   .data_nav ul li { width: 160px; float:left; text-align: center; line-height: 38px; height: 38px; font-size: 16px; border: 1px solid #a6a6a6;  border-bottom: 0px; border-radius: 10px 10px 0 0 ;}
   .data_min{clear: both; overflow: hidden; }
   .data_1{ width:382px; border:1px solid #cbcbcb; height: 380px; float: left; margin-right: 13px;}
   .data_table{ width: 1138px; height: 360px; border:1px solid #cbcbcb; margin-top: 15px; padding: 20px;}
   .data_table_tab {margin-bottom: 20px; width: 100%; }
   .data_table_tab tr th{ border-bottom: 1px solid #000;height: 40px; text-align: left;} 
   .data_table_tab tr td{ height: 40px; background: #f0f8fa;} 
   .data_table_cont a{ color: #00bfff;}
   .mark{ width: 100%; 
    height: 100%;
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    background: #000;
    filter: alpha(opacity=40);
    opacity: 0.4;
    z-index: 400;}
   .data_tanc{ width: 680px; height: 355px;  position: fixed; z-index: 500; top: 50%; margin-top: -202px; left: 50%; margin-left:-340px; background: #fff;  border: 1px solid #787878; display: none; }
   .data_tanctop{ height: 46px; border-bottom: 1px solid #787878; background: #3c85db;}
   .data_tanctop h2{ color: #fff; font-size: 18px; line-height: 46px; padding-left: 30px; width: 200px; float: left;}
   .data_cuo{ width: 21px; height: 21px; float: right; background: url(${ctx}/source/images/dailog-close.png); margin-right: 10px; margin-top: 10px; cursor: pointer;}
   .data_tancmain{ padding: 10px 20px; }
   .tancmain_table{  width: 100%;}
   .tancmain_table tr th{ border: 1px solid #787878; height: 28px; text-align: center;} 
   .tancmain_table tr td{ height: 28px; font-size: 14px; line-height: 29px;} 
   .data_btn{ width: 145px; height: 30px; line-height: 30px; font-size: 16px; text-align: center; background: #cdcdcd; border-radius: 10px; margin:auto; color: #323232; cursor: pointer;}
   .nodata{
	    text-align: center;
	    margin: auto;
	    padding-top: 100px;
	}
   

</style>

<body>
<!-- 资产详细信息开始 -->
  <div class="data_tanc">
     <div class="data_tanctop">
       <h2>资产详情</h2>
       <div class="data_cuo"></div>
     </div>
     <div class="data_tancmain">
       <table class="tancmain_table" width="100%">
              <tbody>
                 <tr>
                     <th width="25%">资产名称</th>
                     <th width="75%" id="assetName" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">资产地址</th>
                     <th width="75%" id="assetAddr" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">创建日期</th>
                     <th width="75%" id="create_date" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">资产用途</th>
                     <th width="75%" id="purpose" style="text-align:left"></th>
                                     
                 </tr>
                 
                
              </tbody>
            </table>
     </div>
     <div class="data_btn">关闭</div>
  </div>
<!-- 资产详细信息结束 -->

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
