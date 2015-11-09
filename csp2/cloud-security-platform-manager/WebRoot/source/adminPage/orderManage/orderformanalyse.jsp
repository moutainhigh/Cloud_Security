<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>订单分析图</title>
<link href="${ctx}/source/orderCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/orderCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/orderCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/order/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/orderformanalyse.js"></script>
</head>

<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head clear">
    <div class="logo"><img src="${ctx}/source/images/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul class="clear">
        <li><a href="#" class="white">用户管理</a></li>
        <li><a href="#" class="white">服务管理</a></li>
        <li><a href="propertystatisticsanalyse.html" class="white">资产分析</a></li>
        <li><a href="useranalyse.html" class="white">用户分析</a></li>
        <li class="b_current"><a href="orderformanalyse.html" class="white">订单分析</a></li>
        <li><a href="warnanalyse.html" class="white">告警分析</a></li>
        <li><a href="equipment.html" class="white">设备资源管理</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="#" class="white">系统管理</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/images/b_photo.jpg" width="43" height="42"></a> </div>
      <div class="lagst-right">
        <p ><a href="###" class="white">adsfadsfasdfasdf</a></p>
        <p> <a href="###" class="white">退出</a></p>
      </div>
    </div>
  </div>
</div>
<!--头部代码结束-->
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
                                    <select class="servicetext" id="servicetype1">
                                    </select>
                                </div>
                                <input type="button" class="sub" style="right:-70px;" value="" onclick="getServiceDate(0)">
                            </form>
                            <div class="tableBox">
                                <div class="">
                                	<div class="order" id="system3" style="width:600px;height:300px"></div>
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
                                    <select class="servicetext" id="servicetype2">
                                    </select>
                                </div>
                                <input type="button" class="sub" value="" style="right:-70px;" onclick="getServiceDate(1)">
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
<!--尾部部分代码-->
<div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
    <li><a href="###">新用户注册</a></li>
    <li><a href="###">用户登录</a></li>
    <li><a href="###">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###"> 帮助</a></h3>
  <ul>
    <li><a href="###">常见问题</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###">厂商合作</a></h3>
  <ul>
    <li><a href="###">华为</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">360</a></li>
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
<li>如未经授权用作他处，将保留追究侵权者法律责任的权利。<br />
  京ICP备11111111号-4 / 京ICP证1111111号<br />
  北京市公安局朝阳分局备案编号:110105000501</li>
</div>
</div>
</div>
<!--尾部部分代码结束-->
</body>
</html>