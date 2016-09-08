<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>财务统计分析</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/order/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/financial/financialManage.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
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
       <%--  <li class="b_current"><a href="${ctx}/orderformanalyse.html" class="white">订单分析</a></li> --%>
        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li><a href="${ctx}/adminAPIAnalysisUI.html" class="white">安全能力API分析</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
        <li class="b_current"><a href="${ctx}/financialStatis.html" class="white">财务统计分析</a></li>
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
	<div class="main_center">
	    <div class="data_title">
        	<button class="dataana_btn datab_cur fl" id="c_btn">日交易统计分析</button>
            <button class="dataana_btn fl" id="st_btn">统计分析</button>
        </div>
    	<!-- <ul class="main_nav tabList clearfix">
        	<li class="statistics active">日交易统计分析<i></i></li>
            <li class="analyse">统计分析<i></i></li>
        </ul> -->
        <div class="tabBox orderform tabCont">
        <div class="system_table" id="div" style="display:block;height:300px;">
             <div style="height:30px;font-size:16px;">当天订单交易额总数 : <span style="font-size:14px;">${orderAmount}</span></div>
        	   <table cellpadding="0" cellspacing="0" id="table" style="width:50%;" class="user_table">
            	<thead>
                    <tr height="40">
                    	<th style="display: none;"></th>
                        <th width="250" class="t_username">服务名</th>
                        <th width="336" class="t_username">订单交易总额</th>
                     </tr>
                </thead>
                <tbody id="tbody">
                   <c:forEach items="${serviceAmountList}" var="order">
                       <tr>
                          <td class="t_username">${order.servName }</td>
                          <td class="t_username">${order.price }</td>
                       </tr>
                   </c:forEach>
                </tbody>
            </table>
            	 	
                    <!-- <div class="fr order" id="system2" style="left:20px;width:500px;height:300px"></div>  -->
                <div  class="fr order" id="tradingCurve" style="left:500px;bottom:260px;width:530px;height:300px"></div> 
            </div>
        </div>
        
                    
           <div class="dd_data_box" id="report" style="display:none;">
        	<div class="data_choose dd_choose" style="padding-left:50px;">
            	<form action="" method="post" id="searchForm" class="analysecent">
            	    <label class="fl">报表类型</label>
		            <select id="reportype" name="type" class="se_small fl se_last" style="width:200px;">
		         		<option selected="selected" value="0">请选择报表类型</option>
		         		<option value="1">日报</option>
		         		<option value="2">月报</option>
		    		</select>
		    	  <div style="display:none;" id="timeDiv">
	               	  <label class="fl">报表时间</label>
	               	  <div class="se_big fl">
	               	         <input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" id="create_date" name="create_date" onclick="WdatePicker({startDate:'%y',maxDate:'%y-%M-{%d}',skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
				      </div>
			      </div>
                 <div style="display:none;" id="timeDiv1">
	               	  <label class="fl">报表时间</label>
	               	  <div class="se_big fl">
				        	 <input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" id="create_date1" name="create_date" onclick="WdatePicker({startDate:'%y',maxDate:'%y-%M',skin:'whyGreen',dateFmt:'yyyy-MM'})"/>
				      </div>
			      </div>
			      
                  <div style="position:relative;left:30px;"><label class="fl" >订单服务类型</label>
                  <select id="servName" name="servName" class="se_small fl se_last" style="width:200px;">
		        	<option selected="selected" value="0">请选择订单服务类型</option>
		      		<option value="1">WEB漏洞监测服务</option>
		      		<option value="2">网站挂马监测服务</option>
		      		<option value="3">网页篡改监测服务</option>
		      		<option value="4">网页敏感内容监测服务</option>
		      		<option value="5">网站可用性监测服务</option>
		      		<option value="6">云WAF网站安全防护服务</option>
		    	</select></div>
                  <input type="button" class="sub" id="statisAnalysis" style="right:-130px;" >
                </form>
            </div>
            <div class="data_detail" id="orderLine">
       	    	<div  id="orderAmountLine" style="width:530px;height:300px;margin-left:200px;"></div> 
       	    	<div  id="orderAmountPie" style="width:530px;height:300px;margin-left:200px;"></div> 
            </div>
       <!--      <div class="data_detail" id="orderPie">
            </div> -->
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