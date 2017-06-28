<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>安全帮-中国电信云安全服务在线商城</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<!-- <div class="safeL fl">
						<img src="${ctx}/source/images/portal/logo.png" alt=""/>
					</div> -->
					<div class="safeL fl" style="width:272px; margin-right:12%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/portal/logo.png" alt="" style="position:relative; top:4px;"/></a>
                          <span style="font-size: 20px;
    color: #4a4a4a; padding-left:10px;position:relative; top:-10px;">系统安全帮</span>
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
			<div class="bannerBox chlid-index">
            <div class="v_show vBox">
				<div class="bannerB">	
				 	<div class="vb"><a href="#"><img src="${ctx}/source/images/ad/${adList[0].image }" alt="" style="width:100%;height:100%"></a></div>
			</div>

		</div>
		<div class="safe02" style="padding-bottom:30px;">
        
        	<div class="imgBox index_child">
				<h2>系统安全监测服务</h2>
                <div class="listBox" style="width: 100%;">
                	<div class="listR" style="width:1108px; margin:0 auto;">
                    	<ul class="newlist row newlist-top child-newlist clearfix" style="margin-right:-38px;">
                            <c:forEach var="list" items="${servList}" varStatus="status">
	                          <!--<c:choose>
                               <c:when test="${status.index<5}">-->
		                            <li class="fl col-md-4">		                   
		                                    <p><img src="${ctx}/source/images/serviceIcon/${list.category_icon }" alt="" style="width:210px;height:auto;margin-top: 35px;margin-left: 62px;"></p>
		                                    <h4>${list.name }</h4>
		                                    <span style="padding-right: 30px">
		                                    	${list.remarks }
		                                    </span>
		                                    <div class="purchase">
			                                     <c:if test="${!empty list.price}">
			                                    	<strong style="text-decoration:none;font-size: 22px;">
			                                    	<fmt:formatNumber type="number" value="${list.price}" maxFractionDigits="2" minFractionDigits="2"/>
			                                    	<em style="font-size: 14px;color: #D00000;">安全币</em>
			                                    	</strong>
			                                    </c:if>
			                                    <c:if test="${empty list.price}">
			                                    	<strong>
			                                    		0<em style="font-size: 14px;color:#D00000;">&nbsp;安全币</em>
			                                    	</strong>
			                                    	<strong style="text-decoration:line-through;text-decoration-color:#D00000;">
			                                    		<em style="font-family:Arial Regular;font-size:22px;">99 </em> 
			                                    		<em style="font-size: 14px;">安全币</em>
			                                    	</strong>
		                                    	</c:if>
		                                    	<!--<a style="left:112px" href="${ctx}/selfHelpOrderAPIInit.html?apiId=${list.id }&indexPage=2" class="btn">购买</a>
		                                    -->
		                                    <a style="left:62px" href="javascript:;" onclick="buySys(${list.id });" class="btn">购买</a>
		                                    <form action="systemOrderOperaInit.html" method="post" id="systemOrderOperaInitNewForm">
												<input type="hidden" id="sysIdNew" name="serviceId"/>
												<input type="hidden" id="indexPage" name="indexPage" value="4"/>
											</form>
		                                    </div>
		                               
		                            </li>
		                         <!--</c:when>
		                       </c:choose>-->
                            </c:forEach>
                        </ul>
                    </div>
                </div>
        </div>
			
		</div>
		
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
		    	<!--修改-->
				   <a href="${ctx}/index.html">
		               <img src="${ctx}/source/images/portal/new-footer-logo.png" alt="" />
                   </a>
                <!--修改--> 
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
