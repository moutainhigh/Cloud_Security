<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>我的订单-API详情</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet" />	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts3/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts3/china.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/anquan_state.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/anquanStateMap.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
</head>

<body>
	<div class="safeBox">
		<div class="detaails">
        	<div class="coreRight coupon">
                 <div class="coupontab" style="width: 100%; margin: 0px auto;">
                	<ol class="navlist centlist assets clearfix">
                    	<li class="active">概况</li>
                        <li>地理位置</li>
                        <li>安全生态</li>
                        <li>用户行为</li>
                        <li>XXX</li>
                    </ol>
                    <div class="tabBox fadeBox" style="background-color:#04144d">
                   	 	
                   	 	<!-- 概况 -->
                    	<div class="not-used" style="display:block">
                    		<div class="words_map">
                        		<div class="word" style="width: 100%; height: 600px;">
                        			<img src="${ctx}/source/images/safe/u2.png" width="100%" height="100%" />
                        		</div>
                        		<div class="lt" style="width: 200px; height: 200px; border: #1C94C4 solid 1px;">左上的Div</div>
                        		<div class="lb" style="width: 200px; height: 200px;border: #1C94C4 solid 1px;">左下的Div</div>
                        		
                        	</div>
                    	</div>
                    	
                    	<!-- 地理位置 -->
                        <div class="not-used">
                        	<div class="tab">
                        		<div class="tabList tab-pane clearfix">
                        			<span class="active" onclick="showHighSiteMap()"><a href="#">监测数据</a></span>
                        			<!-- <span ><a href="#">WAF告警</a></span> -->
                        			<span onclick="showHackerMap()"><a href="#">攻击源</a></span>
                        			<span onclick="showUserMap()"><a href="#">安全帮用户</a></span>
                        			
                        		</div>
                        		<div class="mapBox">
                        			<div class="list" id="safe-map" style="display: block;">
                        				<img src="${ctx}/source/images/safe/u121.png" alt="" />
	                        		</div>
                        		</div>
                        		
                        	</div>
                        	
                    	</div>
                    	
                    	<!-- 安全生态 -->
                        <div class="not-used">
                        	<div class="point clearfix">
                        		<h5 class="fl">重大风险预警</h5>
                        		<div class="pont fl">
                        			<span>SEVERE</span>
                        			<span>HIGH</span>
                        			<span>ELEVATED</span>
                        			<span>GUARDED</span>
                        		</div>
                        	</div>
                        	<div class="exhibitionBox clearfix">
                        		<div class="exhibitionList fl">
                        			<img src="${ctx}/source/images/safe/20160808101501.png" />
                        		</div>
                        		<div class="exhibitionList fl">
                        			<img src="${ctx}/source/images/safe/20160808101512.png" />
                        		</div>
                        		
                        		<div class="exhibitionList fl" style="width: 540px;background:transparent none repeat scroll 0% 0%" id="vulnscanAlarmByLevelMonth6">
                        	
                        		</div>
                        		<div class="exhibitionList fl" style="width: 530px">
                        			<img src="${ctx}/source/images/safe/20160808101546.png" />
                        		</div>
                        	</div>
                    	</div>
                    	
                    	<!-- 用户行为 -->
                    	<div class="not-used">
                        	<div class="mapListBox clearfix">
                        		<div class="ltmit" >
                        			<div id="orderServiceTimes" style="width:1130px;height:300px"></div>
                        		</div>
                        		<div class="ltmit">
                        			<div id="serviceUseInfoMonth6" style="width:360px;height:300px"></div>
                        		</div>
                        		<div class="ltmit">
                        			<img src="${ctx}/source/images/safe/20160808105611.png" />
                        		</div>
                        		<div class="ltmit">
                        			<img src="${ctx}/source/images/safe/20160808105619.png" />
                        		</div>
                        		<div class="ltmit">
                        			<div id="userIndustry" style="width:540px;height:350px"></div>
                        		</div>
                        		<div class="ltmit">
                        			<div id="assetPercent" style="width:540px;height:350px"></div>
                        			<%-- <h3 style="height: 62px; color: #000; font-size: 24px;">用户行业分布</h3>
                        			<div class="fb">
                        				<div class="fbL" style="left: 0px; top:2px; width: 192px; height: 208px;">
                        					<img src="${ctx}/source/images/safe/20160808111717.png" width="100%"; height="100%">
                        				</div>
                        				<div class="fbt" style="left: 162px; top:5px; width: 192px; height: 24 px;">
                        					<img src="${ctx}/source/images/safe/20160808111740.png" width="100%"; height="100%">
                        				</div>
                        				<div class="fbb" style="left: 162px;  top: 175px; width: 192px; height: 24px;">
                        					<img src="${ctx}/source/images/safe/20160808111740.png" width="100%"; height="100%">
                        				</div>
                        				<div class="fbr" style="right: 0px; top:2px;width: 192px; height: 208px;">
                        					<img src="${ctx}/source/images/safe/20160808111725.png" width="100%"; height="100%">
                        				</div>
                        			</div> --%>
                        		</div>
                        	</div>
                    	</div>
                        
                        <!-- XXX 页面 -->
                    	<div class="not-used">
                        	<ul class="clearfix">
                        		<li class="fl" style="height:500px;background:url('${ctx}/source/images/rose2.png') 43px 8px no-repeat;">
                        				<div id="vulnscanAlarmOneHour" style="width:540px;height:500px"></div>
                        		</li>
                        		<li class="fl" style="height:500px;background:url('${ctx}/source/images/rose2.png') 43px 8px no-repeat;">
                        			<div id="wafOneHour" style="width:540px;height:500px"></div>
                        		</li>
                        	</ul>
                        	<div class="fadechid">
                        		<h2>安全帮使用情况</h2>
                        		<ul class="clearfix">
                        			<li class="fl">
                        				<img src="${ctx}/source/images/safe/u6.png">
                        				<span>扫描网站数</span>
                        				<strong>29</strong>
                        			</li>
                        			<li class="fl">
                        				<img src="${ctx}/source/images/safe/u10.png">
                        				<span>扫描页面数</span>
                        				<strong>283599</strong>
                        			</li>
                        			<li class="fl">
                        				<img src="${ctx}/source/images/safe/u14.png">
                        				<span>发现漏洞数</span>
                        				<strong>12580</strong>
                        			
                        			</li>
                        			<li class="fl">
                        				<img src="${ctx}/source/images/safe/u18.png">
                        				<span>抵御攻击次数</span>
                        				<strong>38182101</strong>
                        			</li>
                        		</ul>
                        	</div>
                    	</div>
                    
                    </div>
                </div>
            
            
            </div>
        </div>
        
		
		
	</div>
<!-- <script>
	$(function(){
		$('.tab-pane span').click(function(){
		  var index = $(this).index();
		  $(this).addClass('active').siblings().removeClass('active');
		  $('.mapBox .list').eq(index).show().siblings().hide();
		});

	});
	
</script> -->
</body>


</html>
