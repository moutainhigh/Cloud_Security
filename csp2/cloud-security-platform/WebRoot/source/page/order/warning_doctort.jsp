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
<title>订单跟踪-告警详情-篡改</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>

<script type="text/javascript" src="${ctx}/source/scripts/order/warning_keyword.js"></script>
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
	//getData();
	window.setInterval(getData,30000);
	if($("#group_flag").val()==null){
		//warningTask();
	    window.setInterval(warningTask,60000);
	}
	
});
function getData(){
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
}
//加载模板下拉框选项 
/*$(document).ready(function() {
	var orderId = $("#orderId").val();
	$.ajax({ 
		type: "POST",
		url: "getExecuteTime.html",
        data: {"orderId":orderId,"status":${state}},
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
    var endTime = data.endTime;
    var receiveBytes   =  data.receiveBytes; 
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
</script>
</head>

<body> 
<body>
<div>
<!--头部-->
<div class="safe01 detalis-head">
	<!--头部-->
	<div class="head" style="width:100%">
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
<!-- 头部代码结束-->
<div class="user_center clear">
  
    <!-- 告警详情-->
     <div class="user_right" style="width:1102px;" >
    	<div class="gj_top" style="width:1048px;">
        	<a href="#" class="aelse">订单跟踪</a>　>　<a href="#" class="acur">告警详情</a>
        </div>
        <div class="gj_title webgj_title">
        	<div class="gj_fl">
        	<c:if test="${alist==0}">
        	   <img src="${ctx}/source/images/icon_cg-green.jpg" width="85" height="85" />
        	   <p>篡改告警正常</p>
        	</c:if>
        	<c:if test="${alist!=0}">
                <img src="${ctx}/source/images/icon_cg.jpg" width="85" height="85" />
                <p>篡改告警次数</p>
                <p class="web_num">${alist}次</p>
            </c:if>
          </div>
           <c:forEach var="order" items="${orderList}" varStatus="status">
       			<div class="gj_fr">
		            <input type="hidden" value="${order.id }" id="orderId"/>
		            <input type="hidden" value="${order.type }" id="type"/>
		            <input type="hidden" value="${group_flag }" id="group_flag"/>
		            <p><span class="bigfont">${order.name }</span>
		            <span>(  订单编号：${order.id }  )</span>
		            
		            </p>  
		            <div style="overflow:hidden;">
		            <div style="float:left">资产：</div>
		            <div style="float:left">
		            <c:forEach var="asset" items="${assetList}" varStatus="status">
		            <span class="asset" style="display:block">${asset.name }&nbsp;&nbsp;(${asset.addr })</span>
		            </c:forEach>
		            </div></div>
		            <c:if test="${order.type==1 && group_flag==null}">
                        <p><span class="bigfont historyde">历史详情</span>
                            <select class="historyse" id="execute_Time" name="execute_Time" onchange="historicalDetails()">
                                <option>请选择</option>
                                <c:forEach var="time" items="${taskTime}" varStatus="status">
                                   <c:if test="${timeSize!=0}">
                                       <c:if test="${not status.last}">
                                       <option><fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss"/></option>
                                       </c:if>
                                   </c:if>
                                   <c:if test="${timeSize==0}">
                                       <option><fmt:formatDate value="${time.group_flag }" pattern="yyyy-MM-dd HH:mm:ss"/></option>
                                   </c:if>
                                </c:forEach>
                            </select>
                        </p>
                        <!--  <a href="${ctx}/historyInit.html?orderId=${order.id }" target="_blank"><span style="float:right; margin-right:30px; dispiay:inline-block;color:#999; ">历史记录</span></a>
                        -->
                    </c:if>        
		        </div>
          </c:forEach>
        </div>
        <div class="process">
       	  <p style="padding-bottom:30px;"><span class="scantitle">扫描状态</span>
       	  <c:if test="${status==3}">
       	  <span class="scan">未开始</span><span class="scan">扫描中</span><span class="scan scancur">完成</span>
       	  </c:if>
       	  <c:if test="${status==2}">
       	  <span class="scan">未开始</span><span class="scan scancur">扫描中</span><span class="scan ">完成</span>
       	  </c:if>
       	  </p>
            <p><span class="scantitle">扫描进度</span><span class="propercent" id="bar1">${progress }%</span>
            <span class="processingbox">
            	<span class="progress">
                    <span class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: ${progress }%" id="bar2">${progress }%</span>
				</span>
            <span class="prourl" id="url">当前URL:${currentUrl }</span>
            </span></p>
            <div class="scrg" id="confTable">
            	<span class="scrg_sp"><span class="scrg_ti">开始时间</span><span class="scrg_de">${task.executeTime}</span></span>
            	<span class="scrg_sp"><span class="scrg_ti">请求次数</span><span class="scrg_de">${task.requestCount}次</span></span>
                <span class="scrg_sp"><span class="scrg_ti">平均响应时间</span><span class="scrg_de">${task.averResponse}毫秒</span></span>
                <span class="scrg_sp"><span class="scrg_ti">发送字节</span><span class="scrg_de">${send}</span></span>
                <span class="scrg_sp"><span class="scrg_ti">结束时间</span><span class="scrg_de"><c:if test="${task.endTime==null}">--</c:if><c:if test="${task.endTime!=null}">${task.endTime}</c:if></span></span>
                <span class="scrg_sp"><span class="scrg_ti">URL个数</span><span class="scrg_de">${task.urlCount}个</span></span>
                <span class="scrg_sp"><span class="scrg_ti">每秒访问个数</span><span class="scrg_de">${task.averSendCount}个</span></span>
                <span class="scrg_sp"><span class="scrg_ti">接收字节</span><span class="scrg_de">${receive}</span></span>
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
            <td  style="width:25%;">篡改页面</td>
            <td  style="width:35%;">篡改内容</td>
          </tr>
          <c:forEach var="alarm" items="${alarm}" varStatus="status">
	          <tr>                                            
	            <td>${status.index+1}</td>
	            <td>${alarm.alarmTime}</td>
	            <td>
	            	<c:if test="${alarm.level==0}">低</c:if>
	            	<c:if test="${alarm.level==1}">中</c:if>
	            	<c:if test="${alarm.level==2}">高</c:if>
	            </td>
	            <td>${alarm.url}</td>
	            <td>${alarm.alarm_content}</td>
	          </tr>
          </c:forEach>
          
        </tbody>
      </table>
    </div>
    <div class="web_data">
    	<div class="web_detail_title">篡改告警统计</div>
    <c:forEach var="a" items="${alarm}" varStatus="stus">
    <c:if test="${stus.first }">
     <input type="hidden" value="${a.url }" id="url"/>
       <div class="web_topbox">
       	   <div class="web_datal" id="web_datal" style="width: 271px; padding-left: 0px">
            	<p>监测URL：<span>${a.url}</span></p>
                <p>监测频率：<span>
                			<c:if test="${a.scan_type==1}">每天</c:if>
                			<c:if test="${a.scan_type==2}">每周</c:if>
                			<c:if test="${a.scan_type==3}">每月</c:if>
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
           	    	<!-- <img src="${ctx}/source/images/mgdata.jpg" width="428" height="254" style="margin: 48px 0 0 20px;" /> -->
                </div>
         </div>
           <!--  <div class="web_datar">
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
            </div> -->
        </div>
        </c:if>
        </c:forEach>
    </div>
  </div>
  
</div>
<!-- 尾部代码开始-->
<div class="safeBox">
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="${ctx}/index.html">
		               <img src="${ctx}/source/images/portal/new-footer-logo.png" alt="" />
                   </a>
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd><a href="#">购物指南</a></dd>
                            <dd><a href="#">在线帮助</a></dd>
                            <dd><a href="#">常见问题</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关于安全帮</h2>
                        <dl>
                        	<dd><a href="${ctx}/knowUs.html">了解安全帮</a></dd>
                            <dd><a href="${ctx}/joinUs.html">加入安全帮</a></dd>
                            <dd><a href="#">联系我们</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd><a href="#">QQ交流群</br>470899318</a></dd>
                            <dd class="weixin"><a href="#">官方微信</a></dd>
                       </dl>
                    </li>
                     <li>
                    	<h2>特色服务</h2>
                        <dl>
                        	<dd><a href="#">优惠劵通道</a></dd>
                            <dd><a href="#">专家服务通道</a></dd>
                       </dl>
                    </li>
					
				</ol>
				
			</div>
		</div>
		<!-- 底部 start -->
		<c:import url="/foot.jsp"></c:import>
		<!-- 底部 end -->
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
</div>
<!-- 尾部代码结束 -->
</div>

</body>
</html>
