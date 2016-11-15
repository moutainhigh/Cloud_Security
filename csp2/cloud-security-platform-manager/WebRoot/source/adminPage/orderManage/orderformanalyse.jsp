<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>订单分析</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>

<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/order/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/orderformanalyse.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
</head>

<body>
<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
<div class="main_wrap">
	<div class="main_center">
    	<ul class="main_nav tabList clearfix">
        	<li class="statistics active">订单分析视图<i></i></li>
            <li class="analyse">订单统计<i></i></li>
        </ul>
        <div class="tabBox orderform tabCont">
        	<div class="item clearfix tabItem analyseBox" style="display:block">
            		<div class="fl order" id="system1" style="width:530px;height:300px"></div>
                    <div class="fl order" id="system2" style="left:20px;width:500px;height:300px"></div>
            </div>
            <div class="item tabItem analyseBox">
            		<ul class="analyse_list anlist clearfix">
                	<li class="active">订单时间段分布统计分析<b>|</b></li>
                    <li>订单服务回购率分析<b>|</b></li>
                </ul>
                	<div class="analyse_tabCont">
                        <div class="analyse_tabItem" style="display:block">
                    
                            <!--订单时间段分布统计分析-->
                           <form class="clearfix analysecent">
                               
                                 <div class="analyse_lable fl">
                                    <label>统计开始时间</label>
                                    <input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="begin_date1" name="begin_datevo" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                                    <label>-结束时间</label>
                                    <input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="end_date1" name="end_datevo"  onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                                </div>
                                <div class="analyse_lable fl">
                                    <label>服务类型</label>
                                    <select class="servicetext" id="servicetype1" style="border:1px solid #e0e0e0; height: 30px; line-height: 28px;">
                                    </select>
                                </div>
                                <input type="button" class="sub" style="right:-70px;" value="" onclick="getServiceDate(0)">
                            </form>
                            <div class="tableBox">
                                <div class="">
                                	<div class="order" id="system3" style="width:800px;height:300px"></div>
                                </div>
                            </div>
                        
                        </div>
                        
                    <div class="analyse_tabItem">
                    	<!--订单服务回购率分析-->
                            <form class="clearfix analysecent">
                               
                                 <div class="analyse_lable fl">
                                    <label>统计开始时间</label>
                                    <input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="begin_date2" name="begin_datevo" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                                    <label>-结束时间</label>
                                    <input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="end_date2" name="begin_datevo" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                                </div>
                                <div class="analyse_lable fl">
                                    <label>服务类型</label>
                                    <select class="servicetext" id="servicetype2" style="border:1px solid #e0e0e0; height: 30px; line-height: 28px;">
                                    </select>
                                </div>
                                <input type="button" class="sub" value="" style="right:-70px;" onclick="getServiceDateReBuy(1)">
                            </form>
                            <div class="tableBox">
                                <div class="">
                                	<div class="order" id="system4" style="width:600px;height:300px"></div>
                                </div>
                            </div>
                    
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