<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>财务统计分析</title>
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
<script type="text/javascript" src="${ctx}/source/scripts/financial/financialManage.js"></script>
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
       	    	<div  id="orderAmountLine" style="width:530px;height:350px;margin-left:200px;"></div> 
       	    	<div  id="orderAmountPie" style="width:530px;height:350px;margin-left:200px;"></div> 
            </div>
       <!--      <div class="data_detail" id="orderPie">
            </div> -->
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