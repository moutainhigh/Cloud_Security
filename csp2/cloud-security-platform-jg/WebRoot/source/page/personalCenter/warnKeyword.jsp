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
<title>我的订单-告警详情</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />

<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet" />	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/exporting.js"></script>
  
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>

<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>

<script type="text/javascript">
$(function () {
	//tab页初始化  add by tangxr 2016-5-5
	$(".not-used").eq(0).show();
	//getData();
	//window.setInterval(getData,30000);
	//warningTask();
    //window.setInterval(warningTask,60000);
});
/* today yesterday all hours day week */
/*$(function() {
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
 
 
});*/

/*function getData(){
	var orderId = $("#orderId").val();
 		$.ajax({
           type: "POST",
           url: "scaning.html",
           data: {"orderId":orderId,"status":${status},"group_flag":$("#group_flag").val()},
           dataType:"json",
           success: function(data){
          		var progress = data.progress;
           		$("#bar1").html(progress+"%");
           		$("#bar2").css("width", progress+"%");
           		$("#bar2").html(progress+"%");
           		$("#url").html("当前URL:"+data.currentUrl);
           		if(${status}==2){
           			$('.scan').removeClass('scancur');
           			$('.scan').eq(1).addClass('scancur');
           		}
           		if(${status}==3){
           			$('.scan').removeClass('scancur');
           			$('.scan').eq(2).addClass('scancur');
           		}
           }
        });
}
//实时刷新
function warningTask(){
	var orderId = $("#orderId").val();
	var type = $("#type").val();
 		$.ajax({
           type: "POST",
           url: "warningTask.html",
           data: {"orderId":orderId,"group_flag":$("#group_flag").val(),"type":type},
           dataType:"json",
           success: function(data){
                updateTable(data);
           }
        });
}*/
//加载模板下拉框选项 
/*$(document).ready(function() {
	var orderId = $("#orderId").val();
	$.ajax({ 
		type: "POST",
		url: "getExecuteTime.html",
        data: {"orderId":orderId,"status":${status}},
        dataType:"text",
		success : function(result){
			$("#execute_Time").append(result); 
		} 
	});
}); */

function historicalDetails(){
	var orderId = $("#orderId").val();
	var taskId = $("#execute_Time").val();
	var groupId = $("#execute_Time").val();
	var type = $("#type").val();
//	window.location.href = "${ctx}/historyInit.html?execute_Time="
	//							+ execute_Time+"&orderId="+orderId;
	if($("#execute_Time").val()!=-1){
		window.open("${ctx}/warningInit.html?groupId="
                + groupId+"&orderId="+orderId+"&type="+type); 
	}
}

//更新table的内容
function updateTable(data){ 
       //清除表格
    clearTable();
    var executeTime  =  data.executeTime;//取结点里的数据 
    var issueCount =  data.issueCount; 
    var requestCount  =  data.requestCount;
    var urlCount    =  data.urlCount;
    var averResponse   =  data.averResponse;  
    var averSendCount   =  data.averSendCount;
    var sendBytes   =  data.sendBytes;
    var receiveBytes   =  data.receiveBytes; 
    var endTime = data.endTime;
       $("#confTable").append("<span class='scrg_sp'><span class='scrg_ti'>开始时间</span><span class='scrg_de'>"+executeTime+"</span></span>"+
       "<span class='scrg_sp'><span class='scrg_ti'>请求次数</span><span class='scrg_de'>"+requestCount+"次</span></span>"+
       "<span class='scrg_sp'><span class='scrg_ti'>平均响应时间</span><span class='scrg_de'>"+averResponse+"毫秒</span></span>"+
       "<span class='scrg_sp'><span class='scrg_ti'>发送字节</span><span class='scrg_de'>"+sendBytes+"</span></span>"+
       "<span class='scrg_sp'><span class='scrg_ti'>结束时间</span><span class='scrg_de'>"+endTime+"</span></span>"+
       "<span class='scrg_sp'><span class='scrg_ti'>URL个数</span><span class='scrg_de'>"+urlCount+"个</span></span>"+
       "<span class='scrg_sp'><span class='scrg_ti'>每秒访问个数</span><span class='scrg_de'>"+averSendCount+"个</span></span>"+
       "<span class='scrg_sp'><span class='scrg_ti'>接收字节</span><span class='scrg_de'>"+receiveBytes+"</span></span>");    
}

//清除表格内容
function clearTable(){
   var cit= $("#confTable");
   if(cit.size()>0) {
        cit.find("span").remove();
    }
}
//今天、昨天、全部的判定
/*$(document).ready(function() {
	var flag = ${value};
	if(flag == 1){//今天
	 	$("#today").addClass("web_scancur");//今天
		$("#yesterday").removeClass("web_scancur");//昨天 
		$("#all").removeClass("web_scancur");//全部
		
		$("#hours").addClass("web_scancur");//小时
		$("#day").removeClass("web_scancur"); //天
		$("#week").removeClass("web_scancur");//周
   	}
   	if(flag == 2){//昨天
	 	$("#yesterday").addClass("web_scancur");//昨天
		$("#today").removeClass("web_scancur");//今天 
		$("#all").removeClass("web_scancur");//全部
		
		$("#hours").addClass("web_scancur");//小时
		$("#day").removeClass("web_scancur"); //天
		$("#week").removeClass("web_scancur");//周
   	}
   	if(flag == 3){//全部
	 	$("#yesterday").removeClass("web_scancur");//昨天
		$("#today").removeClass("web_scancur");//今天 
		$("#all").addClass("web_scancur");//全部
		
		$("#hours").removeClass("web_scancur");//小时
		$("#day").removeClass("web_scancur"); //天
		$("#week").addClass("web_scancur");//周
   	}
});*/



</script>

<script type="text/javascript" src="${ctx}/source/scripts/order/warning_keyword.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/warning.js"></script>

<style type="">
.detailbox{display:none;}
.zhangd_table{ width:945px; color:9a9a9a; margin-left:35px;}
.zhangd_table table{ width:945px; text-align:center;word-break: break-all; word-wrap: break-word; border-collapse:collapse; border:0;}
.zhangd_table table tr{ height:50px; line-height:50px; font-size:14px; border-bottom:1px solid #e0e0e0;}
.zhangd_table table tr td span{ color:#49ad53; cursor:pointer;}
</style>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html">首页</a></span>
						<!-- 商品分类 start -->
						<c:import url="/category.html"></c:import>
						<!-- 商品分类 end -->
						<span class="fl"><a href="${ctx}/knowUs.html">关于我们</a></span>
						<span class="fl shopping" style="margin-right:0">
							<a href="${ctx}/showShopCar.html"><i></i>购物车</a>
						</span>
						
					</div>
					<div class="safer fr" style="margin-left:0px;">
						<!-- 如果已经登录则显示用户名，否则需要登录 -->
				         <c:if test="${sessionScope.globle_user!=null }">
				         <div class="login clearfix">
					        <a href="${ctx}/userCenterUI.html"  class="fl loginname">${sessionScope.globle_user.name }</a>
					        <em class="fl">|</em>
					        <a href="${ctx}/exit.html" class="fl" >退出</a>
					      </div>
				         </c:if>
				         <c:if test="${sessionScope.globle_user==null }">
				            <a href="${ctx}/loginUI.html">登录</a>
							<em>|</em>
							<a href="${ctx}/registUI.html">注册</a>
				         </c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="detaails">
        	<div class="coreRight user_right coupon" style="width:1154px;">
            	<div class="gj_top" style="width: 1100px">
            		<a href="${ctx}/orderTrackInit.html" class="aelse">我的订单</a>　&gt;　<a href="#" class="acur">告警详情</a>
       			 </div>
                 <div class="Ordernumber">
                 	<p><span>订单号：</span>${order.id }</p>
                 </div>
                 <div class="coupontab" style="width:1100px;">
                	<ol class="navlist centlist assets clearfix">
                		<c:forEach var="a" items="${assets}" varStatus="status">
			            	<c:if test="${status.first}">
			            		<li class="active" onclick="getCharsData(${status.index+1 })">${a.name }</li>
			            	</c:if>
			            	<c:if test="${!status.first}">
			            		<li onclick="getCharsData(${status.index+1 })">${a.name }</li>
			            	</c:if>
			            </c:forEach>
                    </ol>
                    <div class="tabBox">
                    	<c:forEach var="asset" items="${assets}" varStatus="status">
			            	<div class="not-used">
			            	<!-- 告警开始 -->
								<div class="user_center clear">
								  
								    <!-- 告警详情-->
								     <div class="user_right" style="width:1102px;" >
								    	
								        <div class="gj_title webgj_title">
								        	<div class="gj_fl">
								        	<c:if test="${order.status == 1 || order.status == 2}">
									        	<c:if test="${keyList==0}">
									               <img src="${ctx}/source/images/icon_cg-green.jpg" width="85" height="85" />
									               <p>未发现异常</p>
									            </c:if>
									            <c:if test="${keyList!=0}">
									                <img src="${ctx}/source/images/icon_cg.jpg" width="85" height="85" />
									                <p>关键字告警个数</p>
									                <p class="web_num">${keyList}次</p>
									            </c:if>
								            </c:if>
								          </div>
						       			<div class="gj_fr">
								            <input type="hidden" value="${order.id }" id="orderId"/>
								            <input type="hidden" value="${order.type }" id="type"/>
								            <input type="hidden" value="${group_flag }" id="group_flag"/>
								            <input type="hidden" value="${order.websoc }" id="websoc"/>
								            <input type="hidden" value="${asset.orderAssetId }" id="orderAssetId${status.index+1 }" name="orderAssetId"/>
								            <input type="hidden" value="${status.index+1 }" id="index"/>
								            <p><span class="bigfont">${order.name }</span>
								            
								            </p>            
								            <p>
									           <div style="overflow:hidden;"><div style="float:left">资产：</div>
									           <div style="float:left">
									           	<span class="assets" style="display:block">${asset.addr }</span>
									           </div></div>
									           <!-- add by tangxr 2016-7-18 -->
									            <div style="margin-top:14px;margin-left:-92px">
												    <p class="dd_detail"><span class="detail_l fl">订单类型</span><span class="detail_r fl">
												       <c:if test="${order.type==1}">长期</c:if>
											           <c:if test="${order.type==2}">单次</c:if> 
												    </span></p>
												    <p class="dd_detail"><span class="detail_l fl">订单开始时间</span><span class="detail_r fl"><fmt:formatDate value="${order.begin_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
												    <c:if test="${order.type==1}">
												       <p class="dd_detail"><span class="detail_l fl">订单结束时间</span><span class="detail_r fl"><fmt:formatDate value="${order.end_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
												    </c:if>
												    
												    <c:if test="${order.type==1}">
											           <p class="dd_detail"><span class="detail_l fl">检测周期</span><span class="detail_r fl">
											                <c:if test="${order.serviceId==1}">
											                    <c:if test="${order.scan_type==5}">每周</c:if>
											                    <c:if test="${order.scan_type==6}">每月</c:if>
											                </c:if>
											                <c:if test="${order.serviceId==2}">
											                	<c:if test="${order.scan_type==1}">30分钟</c:if>
											                    <c:if test="${order.scan_type==2}">1小时</c:if>
											                    <c:if test="${order.scan_type==3}">2小时</c:if>
											                    <c:if test="${order.scan_type==4}">1天</c:if>
											                    <c:if test="${order.scan_type==5}">每周</c:if>
											                    <c:if test="${order.scan_type==6}">每月</c:if>
											                </c:if>
											                <c:if test="${order.serviceId==3}">
											                    <c:if test="${order.scan_type==2}">30分钟</c:if>
											                    <c:if test="${order.scan_type==3}">1小时</c:if>
											                    <c:if test="${order.scan_type==4}">1天</c:if>
											                </c:if>
											                <c:if test="${order.serviceId==4}">
											                    <c:if test="${order.scan_type==2}">30分钟</c:if>
											                    <c:if test="${order.scan_type==3}">1小时</c:if>
											                    <c:if test="${order.scan_type==4}">1天</c:if>
											                </c:if>
											                <c:if test="${order.serviceId==5}">
											                    <c:if test="${order.scan_type==1}">10分钟</c:if>
											                    <c:if test="${order.scan_type==2}">30分钟</c:if>
											                    <c:if test="${order.scan_type==3}">1小时</c:if>
											                </c:if>
											           </span></p>
											        </c:if>
									            </div>
									            <!-- end -->
									        </p>
									        
									        <c:if test="${order.type==1}"><!-- test="${order.type==1 && group_flag==null}" -->
								                <p><span class="bigfont historyde">历史详情</span>
								                    <select class="historyse" id=execute_Time name="execute_Time" onchange="historicalDetails()">
								                        <option value="-1">请选择</option>
								                        <c:forEach var="time" items="${taskTime}" varStatus="statusTime">
								                           <c:if test="${timeSize!=0}">
								                               <c:if test="${not statusTime.last}">
								                               <option><fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss"/></option>
								                               <!-- 有告警 
								                               <c:if test="${time.alarm_view_flag==null && time.sum_issue_count!=null}">
								                               <option style="color:red">                               
								                               <fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss" />
								                               </option>
								                               </c:if>-->
								                               <!-- 有告警  已查看
								                               <c:if test="${time.alarm_view_flag!=null && time.sum_issue_count!=null}">
								                               <option >                               
								                               <fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss" />
								                               </option>
								                               </c:if>
								                               <c:if test="${time.alarm_view_flag==null && time.sum_issue_count==null}">
								                               <option>                               
								                               <fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss" />
								                               </option>
								                               </c:if>-->
								                               </c:if>
								                           </c:if>
								                           <c:if test="${timeSize==0}">  
								                           		<option><fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss"/></option>                            
								                                <!-- 有告警 
								                               <c:if test="${time.alarm_view_flag==null && time.sum_issue_count!=null}">
								                               <option style="color:red">                               
								                               <fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss" />
								                               </option>
								                               </c:if>-->
								                                <!-- 有告警  已查看
								                               <c:if test="${time.alarm_view_flag!=null && time.sum_issue_count!=null}">
								                               <option>                               
								                               <fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss" />
								                               </option>
								                               </c:if>
								                               <c:if test="${time.alarm_view_flag==null && time.sum_issue_count==null}">
								                               <option>                               
								                               <fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss" />
								                               </option>
								                               </c:if>-->
								                           </c:if>
								                        </c:forEach>
								                    </select>
								                </p>
								                <!--  <a href="${ctx}/historyInit.html?orderId=${order.id }" target="_blank"><span style="float:right; margin-right:30px; dispiay:inline-block;color:#999; ">历史记录</span></a>
								                -->
								            </c:if>
								        </div>
								        </div>
								        <!-- 进度 -->
								        <div class="process">
								       	  <p style="padding-bottom:30px;"><span class="scantitle">扫描状态</span>
								       	  <c:if test="${asset.task.status==3 || order.status == 1 || order.status == 2}">
								       	  <span class="scan">未开始</span><span class="scan">扫描中</span><span class="scan scancur">完成</span>
								       	  </c:if>
								       	  <c:if test="${(asset.task.status==2 && (order.status == 4 || order.status == 3)) || order.status == 0}">
								       	  <span class="scan">未开始</span><span class="scan scancur">扫描中</span><span class="scan ">完成</span>
								       	  </c:if>
								       	  </p>
								            <p><span class="scantitle">扫描进度</span><span class="propercent" id="bar1">${asset.task.progress }%</span>
								            <span class="processingbox">
								            	<span class="progress">
								                    <span class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: ${asset.task.progress }%" id="bar2">${asset.task.progress }%</span>
												</span>
								            <span class="prourl" id="url">当前URL:${asset.currentUrl }</span>
								            </span></p>
								            <div class="scrg" id="confTable">
								            	<span class="scrg_sp"><span class="scrg_ti">开始时间</span><span class="scrg_de">${asset.task.executeTime}</span></span>
								            	<span class="scrg_sp"><span class="scrg_ti">请求次数</span><span class="scrg_de">${asset.task.requestCount}次</span></span>
								                <span class="scrg_sp"><span class="scrg_ti">平均响应时间</span><span class="scrg_de">${asset.task.averResponse}毫秒</span></span>
								                <span class="scrg_sp"><span class="scrg_ti">发送字节</span><span class="scrg_de">${asset.send}</span></span>
								                <span class="scrg_sp"><span class="scrg_ti">结束时间</span><span class="scrg_de"><c:if test="${asset.task.endTime==null}">--</c:if><c:if test="${asset.task.endTime!=null}">${asset.task.endTime}</c:if></span></span>
								                <span class="scrg_sp"><span class="scrg_ti">URL个数</span><span class="scrg_de">${asset.task.urlCount}个</span></span>
								                <span class="scrg_sp"><span class="scrg_ti">每秒访问个数</span><span class="scrg_de">${asset.task.averSendCount}个</span></span>
								                <span class="scrg_sp"><span class="scrg_ti">接收字节</span><span class="scrg_de">${asset.receive}</span></span>
								            </div>
								        </div>
								    <div class="zhangd_table" style="border-bottom:1px solid #e0e0e0;width: 1068px;margin-left:0;padding-left: 35px;">
								    	<div class="web_detail_title">告警信息</div>
								      <table class="ld_table">
								        <tbody>                                                                                   
								          <tr style="background:#e0e0e0; height:30px; line-height:30px;">
								            <td style="width:8%;">编号</td>
								            <td  style="width:22%;">告警时间</td>
								            <td  style="width:10%;">告警级别</td>
								            <td  style="width:25%;">告警地址</td>
								            <td  style="width:35%;">关键字&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          </tr>
								      <c:if test="${asset.task.status==3 && keyList!=0}">
								        </tbody>
								      </table>
								      <div style="overflow:auto;height:400px;width:938px">
								      <table class="ld_table" style="width:921px;">
								        <tbody> 
								      </c:if>
								          <c:forEach var="list" items="${keywordList}" varStatus="statu">
									          <tr>                                            
									            <td style="width:8%;">${statu.index+1}</td>
									            <td style="width:22%;">${list.alarmTime}</td>
									            <td style="width:10%;">${list.level}</td>
									            <td style="width:25%;">${list.url}</td>
									            <td style="width:35%;">${list.keyword}</td>
									          </tr>
								          </c:forEach>
								        </tbody>
								      </table>
								      <c:if test="${asset.task.status==3 && keyList!=0}">
								      </div>
								      </c:if>
								    </div>
								    <div class="web_data">
								    	<div class="web_detail_title">关键字告警统计</div>
								    <c:forEach var="a" items="${alarmKeyWordList}" varStatus="stus">
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
								                		  </span>
								                </p>
								                <p>得分：<span>${a.score}分</span></p>
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
								                <div class="web_box" id="pic">
								           	    	<!-- <img src="${ctx}/source/images/mgdata.jpg" width="428" height="254" style="margin: 48px 0 0 20px;" />
								                 -->
								                </div>
								         </div>
								            <div class="web_datar">
								            	<p class="pxtitle">敏感词排行榜</p>
								                <div class="pxbox" id="pxbox">
								                	<c:forEach items="${mapSortData}" var="keyword">
								                		<p><span class="pxboxL">${keyword.count}</span>${keyword.keyword}</p>
								                	</c:forEach>
								                </div>
								            </div>
								        </div>
								        </c:if>
								        </c:forEach>
								    </div>
								  </div>
								  
								</div>
			            	
			            	
			            	<!-- end -->
			            	
			            	</div>
			            </c:forEach>
                    </div>
                </div>
            
            
            </div>
        </div>
        
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="${ctx}/index.html">
						<img src="${ctx}/source/images/portal/new-footer-logo.png" alt="">
                   </a>
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd>购物指南</dd>
                            <dd>在线帮助</dd>
                            <dd>常见问题</dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关于安全帮</h2>
                        <dl>
                            <dd><a href="${ctx}/knowUs.html">了解安全帮</a></dd>
                            <dd><a href="${ctx}/joinUs.html">加入安全帮</a></dd>
                            <dd>联系我们</dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd>QQ交流群<br>470899318</dd>
                            <dd class="weixin"><a href="#">官方微信</a></dd>
                       </dl>
                    </li>
                     <li>
                    	<h2>特色服务</h2>
                        <dl>
                        	<dd>优惠劵通道</dd>
                            <dd>专家服务通道</dd>
                       </dl>
                    </li>
					
				</ol>
				
			</div>
		</div>
		<!-- 底部 start -->
		<c:import url="/foot.jsp"></c:import>
		<!-- 底部 end -->
	</div>
<!---执行效果-->
<div class="weixinshow popBoxhide" id="weixin">
	<i class="close chide"></i>
    <div class="Pophead">
    	<h1 class="heaf">安全帮微信二维码</h1>
    </div>
	<div class="popBox">
    	 <p>打开微信，点击右上角的“+”，选择“扫一扫”功能，<br>
对准下方二维码即可。
		</p>
           <div class="weinImg" style="text-align:center;">
           	<img src="${ctx}/source/images/portal/weixin.jpg" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</body>


</html>
