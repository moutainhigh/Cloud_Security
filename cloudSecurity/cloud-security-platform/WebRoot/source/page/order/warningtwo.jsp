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
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/warning.js"></script>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>

<script type="text/javascript">
$(function () {
	//getData();
	window.setInterval(getData,30000);
	//warningTask();
	window.setInterval(warningTask,60000);
	
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
<!--头部-->
<div class="safe01 detalis-head">
	<!--头部-->
	<div class="head" style="width:100%">
		<div class="headBox">
			<div class="safeL fl" style="width:275px; margin-right:18%">
				<img src="${ctx}/source/images/portal/newlogo-footer.png" alt="" style="position:relative; top:4px;"/>
                      <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px;">网站安全帮</strong>
			</div>
			<div class="safem fl">
				<span class="fl"><a href="${ctx}/index.html">首页</a></span>
				<div class="Divlist listJs fl">
					<a href="${ctx}/orderTrackInit.html">我的安全帮<!--<i></i>--></a>
					<!--<ul class="list listl">
						<li><a href="#">我的订单</a></li>
						<li><a href="#">我的资产</a></li>
						<li style="border: none;"><a href="#">个人信息</a></li>
					</ul>-->
				</div>
				<span class="fl ask">
					<a href="#" class="hbule">手机APP</a>
					<b style="display:none"><img src="${ctx}/source/images/portal/apk.png" alt=""></b>
				</span>
				<span class="fl"><a href="${ctx}/aider.html">帮助</a></span>
				
			</div>
			<div class="safer fr">
				<!-- 如果已经登录则显示用户名，否则需要登录 -->
		         <c:if test="${sessionScope.globle_user!=null }">
			        <a href="${ctx}/userDataUI.html">${sessionScope.globle_user.name }</a>
			        <em>|</em>
			        <a href="${ctx}/exit.html">退出</a>
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
     <div class="user_right" style="width:1106px;">
    	<div class="gj_top" style="width:1054px">
        	<a href="#" class="aelse">订单跟踪</a>　>　<a href="#" class="acur">告警详情</a>
     </div>
        <c:forEach var="order" items="${orderList}" varStatus="status">
   		<div class="gj_title">
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
                    <select class="historyse" id=execute_Time name="execute_Time" onchange="historicalDetails()">
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
              <!--   <a href="${ctx}/historyInit.html?orderId=${order.id }" target="_blank"><span style="float:right; margin-right:30px; dispiay:inline-block;color:#999; ">历史记录</span></a>
                 -->
            </c:if>
               
        </div>
        <div class="process">
       	  <p style="padding-bottom:30px;"><span class="scantitle">扫描状态</span>
       	  	<span class="scan">未开始</span><span class="scan scancur">扫描中</span><span class="scan">完成</span>
       	  
       	  </p>
            <p><span class="scantitle">扫描进度</span><span class="propercent" id=bar1>${progress }%</span>
            <span class="processingbox">
            	<span class="progress">
                    <span class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: ${progress }%" id="bar2">${progress }%</span>
				</span>
            <c:if test="${websoc!=1}"><span class="prourl" id="url">当前URL:${currentUrl }</span></c:if>
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
            </c:forEach>
        </tboby>
      </table>
    </div>
  </div>
</div>
<!-- 尾部代码开始-->
<div class="safeBox">
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="#"><img src="${ctx}/source/images/portal/footlogo.png" alt=""></a>
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd><a href="#">购买指南</a></dd>
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
		<div class="foot">
			<p>版权所有Copyright © 2015 中国电信股份有限公司北京研究院京ICP备12019458号-10</p>
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
           	<img src="${ctx}/source/images/portal/weixin.png" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</div>
</body>
</html>
