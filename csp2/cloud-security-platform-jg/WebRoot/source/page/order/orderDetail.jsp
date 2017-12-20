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
<title>订单跟踪-订单详情</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
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
</head>

<body>
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
  <!-- 订单详情-->
  <div class="user_right" style="margin:auto;float:none;">
    <div class="gj_top"> <a href="#" class="aelse">我的订单</a>　>　<a href="#" class="acur">订单详情</a> </div>
    <c:forEach var="order" items="${orderList}" varStatus="status">
	    <p style="margin:0 0 38px 286px;"><span class="bigfont">订单编号</span><span class="bigfont" style="margin-right:20px;">${order.id }</span><span class="bigfont">下单时间</span><span class="bigfont"><fmt:formatDate value="${order.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
	    <p class="dd_detail"><span class="detail_l fl">订单状态</span><span class="detail_r fl">
           <c:set var="temp" value="${nowDate }"/>
           <c:if test="${order.status==0}">已下单</c:if>
           <c:if test="${order.status==5}">域名解析未生效</c:if>
           <!-- <c:if test="${order.begin_date<=temp&&order.status==0}">服务中</c:if>
           <c:if test="${order.status==1||order.status==2}">已结束</c:if> -->
	    </span></p>
	    <p class="dd_detail"><span class="detail_l fl">订单类型</span><span class="detail_r fl">
	       <c:if test="${order.type==1}">长期</c:if>
           <c:if test="${order.type==2}">单次</c:if> 
	    </span></p>
	    <p class="dd_detail"><span class="detail_l fl">订单开始时间</span><span class="detail_r fl"><fmt:formatDate value="${order.begin_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
	    <c:if test="${order.type==1}">
	       <p class="dd_detail"><span class="detail_l fl">订单结束时间</span><span class="detail_r fl"><fmt:formatDate value="${order.end_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
	    </c:if>
	    <p class="dd_detail"><span class="detail_l fl">订单服务类型</span><span class="detail_r fl">${order.name }</span></p>
	    <c:if test="${order.parentC==1}">
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
           <p class="dd_detail"><span class="detail_l fl">最近检测时间</span><span class="detail_r fl">
           <c:if test="${checkTime>0 }">
                <fmt:formatDate value="${lastTime.group_flag }" pattern="yyyy-MM-dd HH:mm:ss"/>
           </c:if>
           <c:if test="${checkTime==0 }">暂无</c:if>
           </span></p>
           <!-- <p class="dd_detail"><span class="detail_l fl">检测次数</span>
           <span class="detail_r fl">
           <c:if test="${checkTime>0 }"><a href="${ctx}/warningInit.html?orderId=${order.id }&type=${order.type}&websoc=${order.websoc}" target="_blank">${checkTime }次</a></c:if>
           <c:if test="${checkTime==0 }">${checkTime }次</c:if>
           </span></p> -->
        </c:if>
	    <!-- <p class="dd_detail"><span class="detail_l fl">服务情况跟踪</span><span class="detail_r fl">
	xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</span></p> -->
		<div class="dd_detail">
		    <div class="detail_l fl"><span style="line-height:30px;">服务对象资产</span></div>
		    <div class="detail_r fl">
	        <c:forEach var="asset" items="${assetList}" varStatus="status">
	        <span style="display:block; width:600px;line-height:30px; ">${asset.name }&nbsp;(${asset.addr })</span>
	        </c:forEach>
	        </div>
	    </div>
	    </c:if>
	    <c:if test="${order.parentC==2}">
	       <c:forEach var="list" items="${ipList}" varStatus="status">
	       <p class="dd_detail"><span class="detail_l fl">监控对象</span><span class="detail_r fl">${list.ip }</span></p>
           </c:forEach>
           <c:if test="${order.type==1}">
                <p class="dd_detail"><span class="detail_l fl">累计告警数</span><span class="detail_r fl"></span></p>
           </c:if>   
	    </c:if>
	</c:forEach>
    
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
</body>
</html>
