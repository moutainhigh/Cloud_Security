<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>订单跟踪-告警详情-可用性</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
  
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/warning_usable.js"></script>
<!-- <SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT> -->
<!-- add by 2016-02 -->
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/main.js"></script>
<style>
.table-c table{border-right:1px solid #000;border-bottom:1px solid #000}
.table-c table td{border-left:1px solid #000;border-top:1px solid #000}
</style>
<script type="text/javascript">
//今天、昨天、全部的判定
$(document).ready(function() {
	var flag = ${value};
	if(flag == 1){//今天
	 	$("#today").addClass("web_scancur");//今天
		$("#yesterday").removeClass("web_scancur");//昨天 
		$("#all").removeClass("web_scancur");//全部
		
		$("#hours").addClass("web_scancur");//小时
		$("#day").removeClass("web_scancur"); //天
		$("#week").removeClass("web_scancur");//周
   	}
   	if(flag == 2){
	 	$("#yesterday").addClass("web_scancur");//昨天
		$("#today").removeClass("web_scancur");//今天 
		$("#all").removeClass("web_scancur");//全部
		
		$("#hours").addClass("web_scancur");//小时
		$("#day").removeClass("web_scancur"); //天
		$("#week").removeClass("web_scancur");//周
   	}
   	if(flag == 3){
	 	$("#yesterday").removeClass("web_scancur");//昨天
		$("#today").removeClass("web_scancur");//今天 
		$("#all").addClass("web_scancur");//全部
		
		$("#hours").removeClass("web_scancur");//小时
		$("#day").removeClass("web_scancur"); //天
		$("#week").addClass("web_scancur");//周
   	}
});

/* today yesterday all hours day week */
$(function() {
    //今天的攻击
	$("#today").click( function() {
	 	var orderId = $("#orderId").val();
	 	var type=$("#type").val();
 		window.location.href="${ctx}/warningInit.html?orderId="+orderId+"&type="+type+"&flag=1";
	 	$("#today").addClass("web_scancur");
		$("#yesterday").removeClass("web_scancur"); 
		$("#all").removeClass("web_scancur");
		$("#week").attr("disabled", true);
 });
 //昨天的攻击
 $("#yesterday").click( function() {
	var orderId = $("#orderId").val();
	 	var type=$("#type").val();
 		window.location.href="${ctx}/warningInit.html?orderId="+orderId+"&type="+type+"&flag=2";
	 	$("#yesterday").addClass("web_scancur");
		$("#toady").removeClass("web_scancur"); 
		$("#all").removeClass("web_scancur");
		$("#week").attr("disabled", true);
 });
 //所有的攻击
 	$("#all").click( function() {
	 	var orderId = $("#orderId").val();
	 	var type=$("#type").val();
 		window.location.href="${ctx}/warningInit.html?orderId="+orderId+"&type="+type;
	 	$("#all").addClass("web_scancur");
		$("#today").removeClass("web_scancur");
		$("#yesterday").removeClass("web_scancur"); 
		$("#week").attr("disabled", true);
 });
 
 
});
</script>
</head>

<body>
<div>
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
<!-- 头部代码结束-->
<div class="user_center clear">
  
    <!-- 告警详情-->
     <div class="user_right" style="width:1102px;" >
    	<div class="gj_top" style="width:1048px;">
        	<a href="#" class="aelse">订单跟踪</a>　>　<a href="#" class="acur">告警详情</a>
        </div>
        <div class="gj_title webgj_title">
        	<div class="gj_fl">
              <c:if test="${wlist==0}">
               <img src="${ctx}/source/images/icon_cg-green.jpg" width="85" height="85" />
	               <p>可用性告警正常</p>
	            </c:if>
	            <c:if test="${wlist!=0}">
	                <img src="${ctx}/source/images/icon_cg.jpg" width="85" height="85" />
	                <p>可用性告警次数</p>
	                <p class="web_num">${wlist}次</p>
	           </c:if>
          </div>
           <c:forEach var="order" items="${orderList}" varStatus="status">
       			<div class="gj_fr">
		            <input type="hidden" value="${order.id }" id="orderId"/>
		            <input type="hidden" value="${order.type }" id="type"/>
		            <input type="hidden" value="${status }" id="status"/>
		            <p><span class="bigfont">${order.name }</span>
		            <span>(  订单编号：${order.id }  )</span>
		            </p>
		            <p>         
			            <div style="overflow:hidden;">
	                    <div style="float:left">资产：</div>
	                    <div style="float:left">
	                    <c:forEach var="asset" items="${assetList}" varStatus="status">
	                    <span class="asset" style="display:block">${asset.name }&nbsp;&nbsp;(${asset.addr })</span>
	                    </c:forEach>
	                    </div></div></p>
		        </div>
          </c:forEach>
        </div>

    <div class="zhangd_table" style="border-bottom:1px solid #e0e0e0;width: 1068px;margin-left:0;padding-left: 35px;">
    	<div class="web_detail_title">告警信息</div>
    	  <table class="ld_table">
			 	<tr style="background:#e0e0e0; height:30px; line-height:30px;">
			    	<td>序号</td>
			        <td>告警时间</th>
			        <td>告警级别</th>
			        <td>URL</th>
			        <td>告警内容</th>
			        <td>MSG</th>
			        <td>原始事件数量</th>
			    </tr>
			  	<c:forEach var="taskWarn" items="${taskWarnList}" varStatus="status">
		          <tr>                                            
		            <td>${status.index+1}</td>
		            <td>${taskWarn.warnTime}</td>
		            <td>${taskWarn.severity}</td>
		            <td>${taskWarn.url}</td>
		            <td>${taskWarn.name}</td>
		            <td>${taskWarn.msg}</td>
		            <td>${taskWarn.ct}</td>
		          </tr>
	          </c:forEach>
			  </table>
		  <br/>
    </div>
    <div class="web_data">
    	<div class="web_detail_title">可用率统计</div>
        <c:forEach var="a" items="${listUseable}" varStatus="stus">
        <c:if test="${stus.first }">
     <input type="hidden" value="${a.url }" id="url"/>
        <div class="web_topbox">
           <div class="web_datal" id="web_datal" style="width: 271px; padding-left: 0px">
            	<p>监测URL：<span>${a.url}</span></p>
                <p>监测频率：<span>
                			<c:if test="${a.scan_type==1}">10分钟</c:if>
                			<c:if test="${a.scan_type==2}">30分钟</c:if>
                			<c:if test="${a.scan_type==3}">1小时</c:if>
                			<c:if test="${a.scan_type==4}">2小时</c:if>
                		  </span></p>
                <p>平均可用率：<span>${usabling}</span></p>
            </div>
            <div class="web_datac">
           	  <div class="web_way">
                	<input type="button" value="今日" class="scan web_scan"  id="today" name="today"/>
                    <input type="button" value="昨日" class="scan web_scan web_scancur" id="yesterday"/>
                    <input type="button" value="全部" class="scan web_scan"  id="all"/>
                    <span class="webway_span">排列方式：</span>
                    <input type="button" value="小时" class="scan web_scan" id="hours"/>
                    <input type="button" value="天" class="scan web_scan web_scancur" id="day"/>
                    <input type="button" value="周" class="scan web_scan" id="week"/>
                </div>
                <div class="web_box" id="pic" style="width:750px">
           	    	<!-- <img src="${ctx}/source/images/mgdata.jpg" width="428" height="254" style="margin: 48px 0 0 20px;" /> -->
                </div>
         </div>
        </div>
        </c:if>
        </c:forEach>
    </div>
  </div>
  
</div>
<!-- 尾部代码开始-->
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
<!-- 尾部代码结束 -->
</div>

</body>
</html>
