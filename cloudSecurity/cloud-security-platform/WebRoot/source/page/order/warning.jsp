<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单跟踪-告警详情</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/warning.js"></script>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
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
	var taskId = $("#execute_Time").val();
	var groupId = $("#execute_Time").val();
	var type = $("#type").val();
//	window.location.href = "${ctx}/historyInit.html?execute_Time="
	//							+ execute_Time+"&orderId="+orderId;
	window.open("${ctx}/historyInit.html?groupId="
								+ groupId+"&orderId="+orderId+"&type="+type); 
	

}
function seedetail1(e) {
	var uservalue=$(e).attr('value');
    if(uservalue==0)
	{
      $(e).parents().next('.detailbox').show();
      $(e).attr('value',1);
     }
	  else if(uservalue==1)
	  {
        $(e).parents().next('.detailbox').hide();
        $(e).attr('value','0');
      }

};
</script>
</head>

<body> 
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
                   <li class="list_active" style="border-right:1px solid #1369C0;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
               </ul>
           </div>
        </div>
    </div>
<div>
<!-- 头部代码结束-->
<div class="user_center clear">
  
    <!-- 告警详情-->
     <div class="user_right" style="margin:auto;float:none;">
        <div class="gj_top">
            <a href="#" class="aelse">订单跟踪</a>　>　<a href="#" class="acur">告警详情</a>
        </div>
        
       <c:forEach var="order" items="${orderList}" varStatus="status">
        <div class="gj_title webgj_title">
        <div class="gj_fl">
                <img src="${ctx}/source/images/icon_cg.jpg" width="85" height="85" />
                <p>${order.name }</p>
                <p class="web_num">${aList}个</p>
          </div>
        <div class="gj_fr">
            <input type="hidden" value="${order.id }" id="orderId"/>
            <input type="hidden" value="${order.type }" id="type"/>
            <p><span class="bigfont">${order.name }</span>
            <span>(  订单编号：${order.id }  )</span>
            <c:if test="${order.type==1 }">
            	<p><span class="bigfont historyde">历史详情</span>
            		<select class="historyse" id=execute_Time name="execute_Time" onchange="historicalDetails()">
            			<option>请选择</option>
            		</select>
            	</p>
                <!--  <a href="${ctx}/historyInit.html?orderId=${order.id }" target="_blank"><span style="float:right; margin-right:30px; dispiay:inline-block;color:#999; ">历史记录</span></a>
             	-->
            </c:if>
            </p>            
            <p>资产：<span class="asset">
            <c:forEach var="asset" items="${assetList}" varStatus="status">
            <span class="assets">${asset.name }&nbsp;&nbsp;(${asset.addr })</span>
            </c:forEach>
            </span></p>
        </div>
        </div>
        <div class="process">
       	  <p style="padding-bottom:30px;"><span class="scantitle">扫描状态</span>
       	  <c:if test="${order.begin_date>temp }">
       	  	<span class="scan">未开始</span><span class="scan scancur">扫描中</span><span class="scan">完成</span>
       	  </c:if>
       	  <c:if test="${order.status==2 ||order.status==1}">
       	  	<span class="scan">未开始</span><span class="scan">扫描中</span><span class="scan scancur">完成</span>
       	  </c:if>
       	  <c:if test="${order.status==3 }">
       	  	<span class="scan scancur">未开始</span><span class="scan">扫描中</span><span class="scan">完成</span>
       	  </c:if>
       	  </p>
            <p><span class="scantitle">扫描进度</span><span class="propercent" id=bar1></span>
            <span class="processingbox">
            	<span class="progress">
                    <span class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100"  id="bar2"></span>
				</span>
            <span class="prourl" id="url">当前URL:http://www.sofpgipgospfops.cpm/</span>
            </span></p>
        </div>
        <div class="gj_box">
            <div class="gj_left fl safebox">
                <div class="detail_title">安全风险</div>
                <div class="aqfx" id="aqfx">
                    <!-- <img src="${ctx}/source/images/aufx.jpg" width="271" height="263" /> -->
                </div>
            </div>
            <div class="fl numbox gj_left">
                <div class="detail_title">漏洞个数</div>
                <div class="ldgs" id="ldgs">
                    <!-- <img src="${ctx}/source/images/ldgs.jpg" width="368" height="269" />  -->
                </div>
            </div>
            <div class="fl">
            	<div class="detail_title">基本信息</div>
                <P class="formalinfo"><span class="infotitle">开始时间</span><span>${task.executeTime}</span></P>
                <P class="formalinfo"><span class="infotitle">结束时间</span><span>${task.endTime}</span></P>
                <P class="formalinfo"><span class="infotitle">扫描时长</span><span>${scanTime}</span></P>
                <P class="formalinfo"><span class="infotitle2">已经发现弱点数</span><span>${task.issueCount}个</span></P>
                <P class="formalinfo"><span class="infotitle2">请求次数</span><span>${task.requestCount}次</span></P>
                <P class="formalinfo"><span class="infotitle2">URL个数</span><span>${task.urlCount}个</span></P>
                <P class="formalinfo"><span class="infotitle2">平均响应时间</span><span>${task.averResponse}毫秒</span></P>
                <P class="formalinfo"><span class="infotitle2">每秒访问个数</span><span>${task.averSendCount}个</span></P>
                <P class="formalinfo"><span class="infotitle2">发送字节</span><span>${send}</span></P>
                <P class="formalinfo"><span class="infotitle2">接收字节</span><span>${receive}</span></P>                  
            </div>
        </div>
        <c:if test="${order.type==1}"><!-- 单次订单不显示趋势图 -->
        <div class="gj_boxs">
            <div class="detail_title">安全评分</div>
            <div class="aqpf" id="aqpf">
                <!-- <img src="${ctx}/source/images/aqpf.jpg" width="870" height="235" /> -->
            </div>
        </div>
        </c:if>
       </c:forEach>
    <div class="zhangd_table">
        <div class="detail_title">漏洞说明</div>
      <table class="ld_table" style="margin-bottom:0;width: 938px;margin-left: 0px;">
        <tbody>                                                                                   
          <tr style="background:#e0e0e0; height:30px; line-height:30px;">
            <td style="width:8%;">编号</td>
            <td  style="width:22%;">漏洞名称</td>
            <td  style="width:10%;">漏洞级别</td>
            <td  style="width:35%;">漏洞详情描述</td>
            <td  style="width:25%;">修复建议&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          </tr>
        </tbody>
      </table>
      <div style="overflow:auto;height:400px;width:938px">
      <table class="ld_table" style="width:921px;margin-left:0;">
        <tbody>           
          <c:forEach var="alarm" items="${alarmList}" varStatus="status">
	          <tr>                                            
	            <td style="width:8%;">${status.index+1 }</td>
	            <td  style="width:22%;">${alarm.name }</td>
	            <td  style="width:10%;">
	               <c:if test="${alarm.level==0}">低</c:if>
                   <c:if test="${alarm.level==1}">中</c:if>
                   <c:if test="${alarm.level==2}">高</c:if> 
	            </td>
	            <td  style="width:35%;">${alarm.alarm_content }</td>
	           <!--  <td>${alarm.advice }</td> -->
	            <td  style="width:25%;" class="seedetail" value="0" name="${order.id}" onclick="seedetail1(this)"><span>查看建议</span></td>
	            </tr>
	            
	          <tr  class="detailbox">
                <td colspan="6"><div  class="zhangd_div2">${alarm.advice } </div>
                 </td>
              </tr>
          </c:forEach>
          
          
        </tbody>
      </table>
      </div>
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
