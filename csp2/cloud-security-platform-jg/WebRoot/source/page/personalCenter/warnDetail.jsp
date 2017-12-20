<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>
<% 
    request.setAttribute("vEnter", "\n");   
    //奇怪的是这一行我用 <c:set var="vEnter" value="\n" scope="request"/>
    //都没用,估计是set标签里对value值处理了一下,没jstl的源码,不清楚JSTL是怎么处理的. 
%>
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
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>

<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>


<!-- <script type="text/javascript" src="${ctx}/source/scripts/order/warnDetail.js"></script>-->
<script type="text/javascript" src="${ctx}/source/scripts/order/warnDetail1.js"></script>
<script type="text/javascript">
$(function () {
	//tab页初始化  add by tangxr 2016-5-5
	$(".not-used").eq(0).show();
	getCharsData(1)
	//getData();
	//window.setInterval(getData,30000);
});
/*function getData(){
	var orderId = $("#orderId").val();
 		$.ajax({
           type: "POST",
           url: "${ctx}/scaning.html",
           data: {"orderId":orderId,"group_flag":$("#group_flag").val()},
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
}*/
//加载模板下拉框选项 
/*$(document).ready(function() {
	var orderId = $("#orderId").val();
	$.ajax({ 
		type: "POST",
		url: "${ctx}/getExecuteTime.html",
        data: {"orderId":orderId,"status":${status}},
        dataType:"text",
		success : function(result){
			$("#execute_Time").append(result); 
		} 
	});
}); */
function historicalDetails(index){
	var orderId = $("#orderId").val();
	var taskId = $("#execute_Time").val();
	var groupId = $("#execute_Time"+index).val();
	var type = $("#type").val();
//	window.location.href = "${ctx}/historyInit.html?execute_Time="
	//							+ execute_Time+"&orderId="+orderId;
	if($("#execute_Time"+index).val()!=-1){
		window.open("${ctx}/warningInit.html?groupId="
                + groupId+"&orderId="+orderId+"&type="+type); 
	}
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
			            	<c:if test="${(asset.task.status==2 && (order.status == 4 || order.status == 3)) || order.status == 0}">
								<!-- 任务运行start -->
								<c:import url="/taskRunning.html?orderId=${order.id }&type=${order.type}&orderAssetId=${asset.orderAssetId }&index=${status.index+1 }"></c:import>
								<!-- end -->
							</c:if>
			            	<c:if test="${asset.task.status==3 || order.status == 1 || order.status == 2}">
			            		<div class="user_center clear">
								  <form id="exportForm${status.index+1 }" action="${ctx}/export.html" method="post">
								    <!-- 告警详情-->
								     <div class="user_right" style="margin:auto;float:none;">
								        
								        <div class="gj_title webgj_title">
								        <div class="gj_fl">
								        
								            <c:if test="${asset.aNum==0}">
								               <img src="${ctx}/source/images/icon_cg-green.jpg" width="85" height="85" />
								               <c:if test="${order.serviceId==1 }">
								                <p>未发现异常</p>
								               </c:if>
								               <c:if test="${order.serviceId==2 }">
								                <p>未发现异常</p>
								               </c:if>
								               
								            </c:if>
								            <c:if test="${asset.aNum!=0}">
								                <img src="${ctx}/source/images/icon_cg.jpg" width="85" height="85" />
								                <c:if test="${order.serviceId==1 }">
								                <p>发现漏洞个数</p>
								               </c:if>
								               <c:if test="${order.serviceId==2 }">
								                <p>木马检测个数</p>
								               </c:if>
								                
								                <p class="web_num">${asset.aNum}个</p>
								            </c:if>
								            
								          </div>
								        <div class="gj_fr">
								            <input type="hidden" value="${order.id }" id="orderId" name="orderId"/>
								            <input type="hidden" value="${order.type }" id="type"/>
								            <input type="hidden" value="${group_flag }" id="group_flag" name="group_flag"/>
								            <input type="hidden" value="${order.websoc }" id="websoc"/>
								            <input type="hidden" value="${asset.orderAssetId }" id="orderAssetId${status.index+1 }" name="orderAssetId"/>
								            <input type="hidden" value="${status.index+1 }" id="index"/>
								            <p><span class="bigfont">${order.name }</span>
								            
								            </p>            
								            <p>
								            <div style="overflow:hidden;">
								            <div style="float:left">资产：</div>
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
								                    <select class="historyse" id="execute_Time${status.index+1 }" name="execute_Time" onchange="historicalDetails(${status.index+1 })">
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
								            <c:if test="${asset.aNum!=0}">
								            <p >
								             <span>下载Word报表&nbsp;</span>
								             <span><a href="javascript:void(0)" onclick="exportImg(${status.index+1 })" ><img src="${ctx}/source/images/export.png" width="22" height="23"/>
								            </a></span></p></c:if>
								        </div>
								        </div>
								        
										<!-- 进度 -->
										<div class="process">
								       	  <p style="padding-bottom:30px;"><span class="scantitle">扫描状态</span>
								       	  	<c:if test="${asset.task.status==3 || order.status == 1 || order.status == 2}">
									       	  <span class="scan">未开始</span><span class="scan">扫描中</span><span class="scan scancur">完成</span>
									       	</c:if>
									       	<c:if test="${asset.task.status==2}">
									       	  <span class="scan">未开始</span><span class="scan scancur">扫描中</span><span class="scan ">完成</span>
									       	</c:if>
								       	  </p>
								          <!--<p><span class="scantitle">扫描进度</span><span class="propercent" id=bar1>${asset.progress }%</span>
								            <span class="processingbox">
								            	<span class="progress">
								                    <span class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: ${asset.progress }%" id="bar2">${asset.progress }%</span>
												</span>
								            <c:if test="${websoc!=1}"><span class="prourl" id="url">当前URL:${asset.currentUrl }</span></c:if>
								            <span class="prourl" id="url">当前URL:${asset.currentUrl }</span>
								            </span>
								          </p>-->
								          <p><span class="scantitle">扫描进度</span><span class="propercent" id=bar1>100%</span>
								            <span class="processingbox">
								            	<span class="progress">
								                    <span class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 100%" id="bar2">100%</span>
												</span>
								            <span class="prourl" id="url">当前URL:${asset.currentUrl }</span>
								            </span>
								          </p>
								          <c:if test="${(empty asset.task.urlCount && empty asset.send && empty asset.receive) || asset.currentUrl eq '[]' }">
									          <p>
									          	<br />
									            <span class="prourl">
									            	(网站访问被拒绝,可能有以下原因：</span>
									            <span class="prourl" style="margin-left:-60px">
													1：该网址不存在；</span>
												<span class="prourl" style="margin-left:31px">
													2：网站安装了防护类的产品导致扫描断断续续；</span>
												<span class="prourl" style="margin-left:65px">
													3：由于持续扫描等原因，安全帮扫描IP可能被列入黑名单。</span>
												<span class="prourl" style="margin-left:15px">
													注：请联系在线客服咨询相关问题)
									            </span>
									          </p>
								          </c:if>
								        </div>
								        
								        <div class="gj_box">
								            <div class="gj_left fl safebox">
								                <div class="detail_title">安全风险</div>
								                <div class="aqfx" id="aqfx${status.index+1 }">
								                    <!-- <img src="${ctx}/source/images/aufx.jpg" width="271" height="263" /> -->
								                </div>
								            </div>
								            <div class="fl numbox gj_left">
								                <div class="detail_title">漏洞个数</div>
								                <c:if test="${asset.aNum==0}">
								                    <div class="ldgs" style="padding-left: 105px;margin-top: 55px;">
								                        <img src="${ctx}/source/images/nodata1.png" width="148" height="146" />  
								                    </div>
								                </c:if>
								                <c:if test="${asset.aNum!=0}">
									                <div class="ldgs" id="ldgs${status.index+1 }">
									                </div>
								                </c:if>
								            </div>
								            
											<input type="hidden" name="imgPie" id="imgPie${status.index+1 }" />
											<input type="hidden" name="imgBar" id="imgBar${status.index+1 }" />
											<input type="hidden" name="imgLine" id="imgLine${status.index+1 }" />
								
								            <div class="fl">
								            	<div class="detail_title">基本信息</div>
								                <P class="formalinfo"><span class="infotitle">开始时间</span><span>${asset.task.executeTime}</span></P>
								                <P class="formalinfo"><span class="infotitle">结束时间</span><span>${asset.task.endTime}</span></P>
								                <P class="formalinfo"><span class="infotitle">扫描时长</span><span>${asset.scanTime}</span></P>
								                <P class="formalinfo"><span class="infotitle2">已经发现弱点数</span><span>${asset.aNum}个</span></P>
								                <c:if test="${websoc!=1}">
									                <P class="formalinfo"><span class="infotitle2">请求次数</span><span>${asset.task.requestCount}次</span></P>
									                <P class="formalinfo"><span class="infotitle2">URL个数</span><span>${asset.task.urlCount}个</span></P>
									                <P class="formalinfo"><span class="infotitle2">平均响应时间</span><span>${asset.task.averResponse}毫秒</span></P>
									                <P class="formalinfo"><span class="infotitle2">每秒访问个数</span><span>${asset.task.averSendCount}个</span></P>
									                <P class="formalinfo"><span class="infotitle2">发送字节</span><span>${asset.send}</span></P>
									                <P class="formalinfo"><span class="infotitle2">接收字节</span><span>${asset.receive}</span></P>                  
								                </c:if>
								            </div>
								        </div>
								        <c:if test="${order.type==1 && asset.aNum!=0}"><!-- 单次订单不显示趋势图 -->
								        <div class="gj_boxs">
								            <div class="detail_title">安全趋势</div>
								            <div class="aqpf" id="aqpf${status.index+1 }">
								                <!-- <img src="${ctx}/source/images/aqpf.jpg" width="870" height="235" /> -->
								            </div>
								        </div>
								        </c:if>
								        <c:if test="${asset.aNum!=0}">
								        <div class="gj_boxs" >
								            <div style="padding:4px 10px;margin:20px 0 28px 30px; font-size:16px;width:64px;"></div>
								            <div class="aqpf" id="aqpf1${status.index+1 }" >
								                <!-- <img src="${ctx}/source/images/aqpf.jpg" width="870" height="235" /> -->
								            </div>
								        </div>
								        </c:if>
								    <c:if test="${asset.aNum!=0}">
								    <div class="zhangd_table">
								        <div class="detail_title">漏洞说明</div>
								      <table class="ld_table" style="margin-bottom:0;width: 938px;margin-left: 0px;">
								        <tbody>                                                                                   
								          <tr style="background:#e0e0e0; height:30px; line-height:30px;">
								            <td style="width:8%;">编号</td>
								            <td  style="width:22%;">漏洞名称</td>
								            <td  style="width:10%;">漏洞级别</td>
								            <td  style="width:45%;">漏洞详情描述</td>
								            <td  style="width:20%;">修复建议&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          </tr>
								        </tbody>
								      </table>
								      <div style="overflow:auto;height:400px;width:938px">
								      <table class="ld_table" style="width:921px;margin-left:0;">
								        <tbody>        
								          <c:forEach var="alarm" items="${asset.asset_alarmList}" varStatus="sta">
									          <tr>                                            
									            <td style="width:8%;">${sta.index+1 }</td>
									            <td  style="width:22%;">${alarm.name }</td>
									            <td  style="width:10%;">
									               <c:if test="${alarm.level==-1}">信息</c:if>
									               <c:if test="${alarm.level==0}">低</c:if>
								                   <c:if test="${alarm.level==1}">中</c:if>
								                   <c:if test="${alarm.level==2}">高</c:if> 
								                   <c:if test="${alarm.level==3}">紧急</c:if> 
									            </td>
									            <td  style="width:45%;">URL:&nbsp;&nbsp;${alarm.alarm_content }<br/>弱点：&nbsp;&nbsp;${fn:replace(alarm.keyword,"<", "&lt;")}</td>
									            <td  style="width:20%;" class="seedetail" value="0" name="${order.id}" onclick="seedetail1(this)"><span>查看建议</span></td>
									            </tr>
									            
									          <tr  class="detailbox">
								                <td colspan="5"><div  class="zhangd_div2">${fn:replace(alarm.advice,vEnter, "<br />")} </div>
								                 </td>
								              </tr>
								          </c:forEach>
								          
								          
								        </tbody>
								      </table>
								      </div>
								    </div>
								    </c:if>
								  </div>
								  </form>
								</div>
							</c:if>
							
			            	
			            	
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
