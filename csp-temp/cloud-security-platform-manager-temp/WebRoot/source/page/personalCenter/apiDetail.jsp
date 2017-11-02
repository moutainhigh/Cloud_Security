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
<title>我的订单-API详情</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />

<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet" />	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<!--<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
--><script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>

<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/apiDetail.js"></script>
<style type="">
.detailbox{display:none;}
.zhangd_table{ width:945px; color:9a9a9a; margin-left:35px;}
.zhangd_table table{ width:945px; text-align:center;word-break: break-all; word-wrap: break-word; border-collapse:collapse; border:0;}
.zhangd_table table tr{ height:50px; line-height:50px; font-size:14px; border-bottom:1px solid #e0e0e0;}
.zhangd_table table tr td span{ color:#49ad53; cursor:pointer;}
.search{	
	width: 78px;
	background:none;
	background-color: #f3f3f3;
	color: #343434;
	font-size: 14px;
	height: 38px;
	line-height: 38px;
	border: none;
	border-left: #e5e5e5 solid 1px;
	cursor: pointer;
	position: absolute;
}
</style>
</head>

<body>
<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
		<div class="detaails">
			<input type="hidden" id="orderIdHidden" value="${order.id}"/>
        	<div class="coreRight user_right coupon" style="width:1154px;">
            	<div class="gj_top" style="width: 1100px">
            		<a href="#" class="acur">API详情</a>
       			 </div>
                 <div class="Ordernumber">
                 	<p><span>订单号：</span>${order.id }</p>
                 </div>
                 <div class="coupontab" style="width:1100px;">
					<div class="user_center clear">
					<div class="user_right" style="border-bottom:#e5e5e5 solid 1px;min-height:auto">
						<div style="margin-top:14px;margin-left: 320px;">
							<p class="dd_detail"><span class="bigfont">${order.name }</span></p>
						    <p class="dd_detail"><span class="detail_l fl">购买次数</span><span class="detail_r fl">${apiCount}</span></p>
						    <p class="dd_detail"><span class="detail_l fl">订单开始时间</span><span class="detail_r fl"><fmt:formatDate value="${order.begin_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
						    <p class="dd_detail"><span class="detail_l fl">订单结束时间</span><span class="detail_r fl"><fmt:formatDate value="${order.end_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
			            </div>
		            </div>
					</div>
					<div class="user_center clear">
					<div class="user_right" style="border-bottom:0px;min-height:0px">
					 <div class="gj_box">
				            <div class="gj_left fl safebox">
				                <div class="detail_title" style="width:116px">已扫描网站次数</div>
				                <P class="formalinfo"><span>${createAPICount}</span></P>
				            </div>
				            <div class="fl">
				                <div class="detail_title"  style="width:116px">已使用接口统计</div>
              				<div class="ldgs" style="padding-left: 105px;margin-top: 0px;width:500px;height:300px;padding-top:0px" id="apiUse">
		                        <!--<img src="${ctx}/source/images/nodata1.png" width="148" height="146" />  
		                    --></div>
				            </div>
			        </div>
					</div>
                </div>
           <div class="user_center clear">
			<div class="user_right" style="border-bottom:0px;min-height:0px">

            <div class="zhangd_table">           
            <div class="detail_title" style="width:136px">接口调用历史记录</div>
		      <div class="coreList clearfix" style="padding-left:220px;position:relative;top:-62px">
               	<label class="container con-order fl">
                   	<input type="text" class="text promptext" style="margin-left:30px" placeholder="输入网站名称进行搜索"  style="width:auto" value=""  id="searchUrl" name="searchUrl"></label>
                 <label class="container fl">
                    <input class="text fl" type="text" style="width:auto;margin-left:10px" placeholder="服务开始时间" value="" id="begin_date" name="begin_date" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                    <em class="fl">到</em>
                    <input class="text fl" type="text" style="width:auto" placeholder="服务结束时间" value="" id="end_date" name="end_date" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </label> 

                      
                   	<input type="button" value="搜索" class="search" onclick="searchCombine()">

               </div>

		      <table class="ld_table" style="margin-bottom:0;width: 938px;margin-left: 0px;position:relative;top:-32px">
		        <tbody>                                                                                   
		          <tr style="background:#e0e0e0; height:30px; line-height:30px;">
		            <td style="width:18%;">调用时间</td>
		            <td  style="width:6%;">请求方法</td>
		            <td  style="width:20%;">接口内容</td>
		            <td  style="width:50%;">接口地址</td>
		            <td  style="width:10%;">操作结果</td>
		          </tr>
		        </tbody>
		      </table>
		      <div style="overflow:auto;height:400px;width:938px">
		      <table class="ld_table" style="width:921px;margin-left:0;">
		        <tbody id="infoBody">        
		          <!--<c:forEach var="info" items="${infoList}" varStatus="sta">
			          <tr>                                            
			            <td style="width:18%;">${info.create_time }</td>			            
			            <c:if test="${info.api_type==1}">
			            	<td  style="width:8%;">POST</td>
				            <c:if test="${info.service_type==1}">
					            <td  style="width:20%;">创建WEB漏洞扫描订单</td>
				            </c:if>
				            <c:if test="${info.service_type==2}">
				            	<td  style="width:20%;">创建木马检测订单（任务）</td>
				            </c:if>
				            <c:if test="${info.service_type==3}">
				            	<td  style="width:20%;">创建网页篡改订单（任务）</td>
				            </c:if>
				            <c:if test="${info.service_type==4}">
				            	<td  style="width:20%;">创建可用性监测订单（任务）</td>
				            </c:if>
				            <c:if test="${info.service_type==5}">
				            	<td  style="width:20%;">创建敏感词监测订单（任务）</td>
				            </c:if>
				            <td  style="width:45%;">${info.apiUrl}order/${info.token}</td>
			            </c:if>
			            <c:if test="${info.api_type==2}">
							<td  style="width:8%;">POST</td>
							<td  style="width:10%;">订单(任务)操作</td>
							<td  style="width:45%;">${info.apiUrl}order/${info.orderId}/${info.token}</td>
						</c:if>
			            <c:if test="${info.api_type==3}">
			            	<td  style="width:8%;">GET</td>
							<td  style="width:10%;">获取订单(任务)状态</td>
							<td  style="width:45%;">${info.apiUrl}orderStatus/${info.orderId}/${info.token}</td>
						</c:if>
			            <c:if test="${info.api_type==4}">
			            	<td  style="width:8%;">GET</td>
							<td  style="width:10%;">获取订单结果</td>
							<td  style="width:45%;">${info.apiUrl}orderResult/${info.orderId}/${info.taskId}/${info.token}</td>
						</c:if>
			            <c:if test="${info.api_type==5}">
			            	<td  style="width:8%;">POST</td>
							<td  style="width:10%;">获取订单结果报告</td>
							<td  style="width:45%;">${info.apiUrl}orderReport/${info.orderId}/${info.token}</td>
			            
						</c:if>
			            
			           
			            <c:if test="${info.status==1}">
			             	<td  style="width:8%;"><span>成功</span></td>
			            </c:if>
			            <c:if test="${info.status==0}">
			             	<td  style="width:8%;"><span>失败</span></td>
			            </c:if>
			         </tr>
		          </c:forEach>
		          
		          
		        --></tbody>
		      </table>
		      </div>
		    </div>
		    </div>
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
