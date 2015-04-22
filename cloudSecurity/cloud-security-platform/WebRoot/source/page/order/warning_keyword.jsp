<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单跟踪-告警详情-关键字</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
  <script type="text/javascript" src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
  <script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
  <script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/exporting.js"></script>
  
<script type="text/javascript" src="${ctx}/source/scripts/order/warning_keyword.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/warning.js"></script>
<script type="text/javascript">
$(function () {
	getData();
	window.setInterval(getData,30000);
});
function getData(){
	var orderId = $("#orderId").val();
 		$.ajax({
           type: "POST",
           url: "/cloud-security-platform/scaning.html",
           data: {"orderId":orderId},
           dataType:"json",
           success: function(data){
          		var progress = data.progress;
           		$("#bar1").html(progress+"%");
           		$("#bar2").css("width", progress+"%");
           		$("#bar2").html(progress+"%");
           		$("#url").html("当前URL:"+data.currentUrl);
           }
        });
}
//加载模板下拉框选项 
$(document).ready(function() {
	var orderId = $("#orderId").val();
	$.ajax({ 
		type: "POST",
		url: "/cloud-security-platform/getExecuteTime.html",
        data: {"orderId":orderId},
        dataType:"text",
		success : function(result){
			$("#execute_Time").append(result); 
		} 
	});
}); 
function historicalDetails(){
	var orderId = $("#orderId").val();
	var execute_Time = $("#execute_Time").val();
	var type = $("#type").val();
//	window.location.href = "${ctx}/historyInit.html?execute_Time="
	//							+ execute_Time+"&orderId="+orderId;
	window.open("${ctx}/historyInit.html?execute_Time="
								+ execute_Time+"&orderId="+orderId+"&type="+type); 
	

}
</script>
</head>

<body> 
<body>
<div>
<div class="head_bj">
        <div class="head">
           <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
           <div class="lagst">
               <div class="lagst-left">
                <c:if test="${sessionScope.globle_user!=null }">
                   <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a>
                </c:if>
                 <c:if test="${sessionScope.globle_user==null }">
                    <a href="${ctx}/toLoginUI.html"><img src="${ctx}/source/images/ren.png" /></a>
                 </c:if>
               </div>
               <div class="lagst-right">
               <!-- 如果已经登录则显示用户名，否则需要登录 -->
               <c:if test="${sessionScope.globle_user!=null }">
                <p><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
                <p><a href="${ctx}/exit.html">退出</a></p>
               </c:if>
               <c:if test="${sessionScope.globle_user==null }">
                     <p><a href="${pageContext.request.contextPath}/loginUI.html">登录</a></p>
                     <p><a href="${pageContext.request.contextPath}/registUI.html">注册</a></p>
               </c:if>
               </div>
           </div>
            <div class="list">
               <ul>
                   <li><a href="${ctx}/index.html">首页</a></li>
                   <li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
                   <li><a href="aider.html">在线帮助</a></li>
                   <li style="border-right:1px solid #11871d;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
               </ul>
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
            	<img src="${ctx}/source/images/icon_cg.jpg" width="85" height="85" />
				<p>网页篡改告警</p>
              	<p class="web_num">${count}次</p>
          </div>
           <c:forEach var="order" items="${orderList}" varStatus="status">
       			<div class="gj_fr">
		            <input type="hidden" value="${order.id }" id="orderId"/>
		            <input type="hidden" value="${order.type }" id="type"/>
		            <p><span class="bigfont">${order.name }</span>
		            <span>(  订单编号：${order.id }  )</span>
		            </p>            
		            <p>资产：<span class="asset">
		            <c:forEach var="asset" items="${assetList}" varStatus="status">
		            <span class="assets">${asset.name }&nbsp;&nbsp;(${asset.addr })</span>
		            </c:forEach>
		            </span></p>
		        </div>
          </c:forEach>
        </div>
        <div class="process">
       	  <p style="padding-bottom:30px;"><span class="scantitle">扫描状态</span><span class="scan">未开始</span><span class="scan">扫描中</span><span class="scan scancur">完成</span></p>
            <p><span class="scantitle">扫描进度</span><span class="propercent" id="bar1"></span>
            <span class="processingbox">
            	<span class="progress">
                    <span class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" id="bar2"></span>
				</span>
            <span class="prourl" id="url">当前URL:http://www.sofpgipgospfops.cpm/</span>
            </span></p>
            <div class="scrg">
            	<span class="scrg_sp"><span class="scrg_ti">请求次数</span><span class="scrg_de">${task.requestCount}次</span></span>
                <span class="scrg_sp"><span class="scrg_ti">平均响应时间</span><span class="scrg_de">${task.averResponse}毫秒</span></span>
                <span class="scrg_sp"><span class="scrg_ti">发送字节</span><span class="scrg_de">${task.sendBytes}</span></span>
                <span class="scrg_sp"><span class="scrg_ti">URL个数</span><span class="scrg_de">${task.urlCount}个</span></span>
                <span class="scrg_sp"><span class="scrg_ti">每秒访问个数</span><span class="scrg_de">${task.averSendCount}个</span></span>
                <span class="scrg_sp"><span class="scrg_ti">接收字节</span><span class="scrg_de">${task.receiveBytes}</span></span>
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
            <td  style="width:35%;">关键字</td>
          </tr>
          <c:forEach var="list" items="${keywordList}" varStatus="status">
	          <tr>                                            
	            <td>${status.index+1}</td>
	            <td>${list.alarmTime}</td>
	            <td>${list.level}</td>
	            <td>${list.url}</td>
	            <td>${list.keyword}</td>
	          </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="web_data">
    	<div class="web_detail_title">关键字告警统计</div>
    <c:forEach var="a" items="${alarmKeyWordList}" >
       <div class="web_topbox">
           <div class="web_datal">
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
                	<input type="button" value="今日" class="scan web_scan" />
                    <input type="button" value="昨日" class="scan web_scan web_scancur" />
                    <input type="button" value="全部" class="scan web_scan" />
                    <span class="webway_span">排列方式：</span>
                    <input type="button" value="小时" class="scan web_scan" />
                    <input type="button" value="天" class="scan web_scan web_scancur" />
                    <input type="button" value="周" class="scan web_scan" />
                </div>
                <div class="web_box" id="pic">
           	    	<!-- <img src="${ctx}/source/images/mgdata.jpg" width="428" height="254" style="margin: 48px 0 0 20px;" />
                 -->
                </div>
         </div>
            <div class="web_datar">
            	<p class="pxtitle">敏感词排行榜</p>
                <div class="pxbox">
                	<p><span class="pxboxL">111</span>专业删帖服务</p>
                    <p><span class="pxboxL">102</span>水军军团招人</p>
                    <p><span class="pxboxL">93</span>专业投票服务</p>
                    <p><span class="pxboxL">84</span>删帖公司</p>
                    <p><span class="pxboxL">75</span>招聘网络水军</p>
                    <p><span class="pxboxL">66</span>水军兼职</p>
                    <p><span class="pxboxL">57</span>清除网络负面信息</p>
                    <p><span class="pxboxL">48</span>公关删除百度信息</p>
                    <p><span class="pxboxL">39</span>水军招聘</p>
                    <p><span class="pxboxL">15</span>公关负面信息处理</p>
                    <p><span class="pxboxL">14</span>负面新闻信息删除</p>
                    <p><span class="pxboxL">13</span>专业消除负面信息</p>
                    <p><span class="pxboxL">12</span>专业负面信息处理</p>
                    <p><span class="pxboxL">11</span>收费删帖</p>
                    <p><span class="pxboxL">10</span>负面信息删除</p>
                </div>
            </div>
        </div>
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
</div>

</body>
</html>
