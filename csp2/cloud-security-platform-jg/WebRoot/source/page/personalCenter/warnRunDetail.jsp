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
<title>订单跟踪-告警详情</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript">
$(function () {
	getData();
	window.setInterval(getData,30000);
	warningTask();
	window.setInterval(warningTask,60000);
	
});
</script>

<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/warning.js"></script>
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<!-- add by 2016-02 -->
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript">
$(function () {
	getData();
	window.setInterval(getData,30000);
	//warningTask();
	window.setInterval(warningTask,60000);
	
});
function getData(){
	var orderId = $("#orderId").val();
 		$.ajax({
           type: "POST",
           url: "scaning.html",
           data: {"orderId":orderId,"status":${status},"group_flag":$("#group_flag").val(),"orderAssetId":$("#orderAssetId").val()},
           dataType:"json",
           success: function(data){
          		var progress = data.progress;
           		$("#bar1"+${index}).html(progress+"%");
           		$("#bar2"+${index}).css("width", progress+"%");
           		$("#bar2"+${index}).html(progress+"%");
           		$("#url"+${index}).html("当前URL:"+data.currentUrl);
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
           data: {"orderId":orderId,"group_flag":$("#group_flag").val(),"type":type,"orderAssetId":$("#orderAssetId").val()},
           dataType:"json",
           success: function(data){
                updateTable(data);
           }
        });
}
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
	var groupId = $("#execute_Time").val();
	var type = $("#type").val();
//	window.location.href = "${ctx}/historyInit.html?execute_Time="
	//							+ execute_Time+"&orderId="+orderId;
	window.open("${ctx}/warningInit.html?groupId="
								+ groupId+"&orderId="+orderId+"&type="+type); 
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
    if(${websoc}!=1){
    	$("#confTable").append("<tr><td style='line-height:20px;'>"+executeTime+"</td>"+
    		       "<td style='line-height:20px;'>--</td><td>--</td><td>"+issueCount+"个</td><td>"+requestCount+"次</td>"+
    		       "<td>"+urlCount+"个</td><td>"+averResponse+"毫秒</td><td>"+averSendCount+"个</td><td>"+sendBytes+"</td><td>"+receiveBytes+"</td></tr>");    
    }else{
    	$("#confTable").append("<tr><td style='line-height:20px;'>"+executeTime+"</td>"+
    		       "<td style='line-height:20px;'>--</td><td>--</td></tr>");    
    }
       
}
          
//清除表格内容
function clearTable(){
   var cit= $("#confTable");
   if(cit.size()>0) {
        cit.find("tr:not(:first)").remove();
    }
}
</script>
</head>
<body>

<div class="user_center clear">
    <!-- 告警详情-->
     <div class="user_right" style="width:1106px;">
   		<div class="gj_title">
            <input type="hidden" value="${order.id }" id="orderId"/>
            <input type="hidden" value="${order.type }" id="type"/>
            <input type="hidden" value="${group_flag }" id="group_flag"/>
            <input type="hidden" value="${task.order_asset_Id }" id="orderAssetId"/>
            <p><span class="bigfont">${order.name }</span>
            </p>
            <div style="overflow:hidden;">
            <div style="float:left">资产：</div>
            <div style="float:left">
            <span class="asset" style="display:block">${asset.addr }</span>
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
               
        </div>
        <div class="process">
       	  <p style="padding-bottom:30px;"><span class="scantitle">扫描状态</span>
       	  	<span class="scan">未开始</span><span class="scan scancur">扫描中</span><span class="scan">完成</span>
       	  
       	  </p>
            <p><span class="scantitle">扫描进度</span><span class="propercent" id="bar1${index}">${progress }%</span>
            <span class="processingbox">
            	<span class="progress">
                    <span class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: ${progress }%" id="bar2${index}">${progress }%</span>
				</span>
            <span class="prourl" id="url${index}">当前URL:${task.currentUrl }</span>
            </span></p>
        </div>
        
        
    <div class="zhangd_table">
    	<div class="detail_title">基本信息</div>
      <table class="ld_table" style="width:930px;margin-left:0;" id="confTable">
        <tboby>
          <tr style="background:#e0e0e0; height:30px; line-height:30px;text-align:center;">
            <td style="width:;">开始时间</td>
            <td  style="width:%;">结束时间</td>
            <td  style="width:%;">扫描时长</td>
            <c:if test="${websoc!=1}">
	            <td  style="width:%;">已经发现弱点数</td>
	            <td  style="width:%;">请求次数</td>
	            <td  style="width:%;">URL个数</td>
	            <td  style="width:%;">平均响应时间</td>
	            <td  style="width:%;">每秒访问个数</td>
	            <td  style="width:%;">发送字节</td>
	            <td  style="width:%;">接收字节</td>
            </c:if>
          </tr>
          <tr>                                            
            <td style="line-height:20px;">${task.executeTime}</td>
            <td style="line-height:20px;">--</td>
            <td>--</td>
            <c:if test="${websoc!=1}">
	            <td>${task.issueCount}个</td>
	            <td>${task.requestCount}次</td>
	            <td>${task.urlCount}个</td>            
	            <td>${task.averResponse}毫秒</td>
	            <td>${task.averSendCount}个</td>
	            <td>${send}</td>
	            <td>${receive}</td>
            </c:if>
          </tr>
        </tboby>
      </table>
    </div>
  </div>
</div>

</body>
</html>
