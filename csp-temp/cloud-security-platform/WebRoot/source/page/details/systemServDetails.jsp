<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>${service.name }商品信息</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript"  src="${ctx}/source/scripts/common/jquery.form.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script src="${ctx}/source/scripts/order/sysServDetail.js"></script>
<!-- <script src="${ctx}/source/scripts/order/wafDetail.js"></script>
<script src="${ctx}/source/scripts/order/details.js"></script> -->
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/zezao.js"></script> 

<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<style>
.dataBox .dataR ul li label {width: 74px;}
.dataR ul li .text {width: 90px;}
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
                        <a href="${ctx}/web_anquanbang.html"><strong style="font-size:20px; color:#fff; padding-left:10px;position:relative; top:-10px;font-weight:normal;">系统安全帮</strong></a>
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

	<div class="dataCent">
			<div class="data-crumbs">
				<a href="${ctx}/index.html" style="font-size: 20px;">安全帮</a><i>&gt;</i><a href="${ctx}/system_anquanbang.html">系统安全帮</a><i>&gt;</i><a href="javascript:;">${service.name }</a>
			</div>
				<input type="hidden" id="serviceIdHidden" value="${service.id }"/>
				
				<input type="hidden" id="message" value="${message }"/>
				
			<div class="dataBox clearfix">
				<div class="dataL fl">
					<div class="dataImg fl">
					<img src="${ctx}/source/images/serviceIcon/${service.detailIcon }" alt="" style=""/>
					</div>
				</div>
					<div class="dataR detailsR fl" style="width:640px;">
					<h2 style="font-size:20px; margin-bottom:18px;">${service.name }</h2>				
                  <a href="javascript:showShopCar();" class="buttoncar" style="right:0px;"><b id="shopCarNum">${carnum}</b><i></i>我的购物车&gt;</a>
					<ul>
						<li class="clearfix">
							<label class="fl">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</label>
                            <div class="fl price"><strong id="price">0</strong><strong></strong></div> 
                          	<span style="position: relative;top: 7px;left:7px;color:#d00000">安全币</span>
                          	<c:if test="${service.id==7 }">
                          		<span style="position: relative;top: 7px;left:7px"><u>由绿盟科技提供服务并开具发票</u></span>
                          	</c:if>
						</li>
                        <c:if test="${service.id==10}">
                         <li class="clearfix" style="margin-bottom:-5px;">
                           <label class="fl">监控目标</label>
                           <div class="fl" style="top:3px;">
                           	<span><input id="ip" type="text" style="height:35px;width:376px;color:#999;border: #e5e5e5 solid 1px;border-radius: 2px;" value="&nbsp;&nbsp;例如：https://127.0.0.1 或 http://www.google.com" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#999'}"></span>
                           
                           </div>
                         </li>
                         <span id="ipCheck" style="color:red;margin-left:95px;"></span>
                         <li class="clearfix" style="margin-bottom:-5px;">
                           <label class="fl">监控服务</label>
                           <div id="changePort" class="fl" style="top:3px;">
                           	<span>
                            	<select id="servName" class="text select" onchange="changePort()" style="margin-left: 0px;">
                           			<option value="" data-type="">服务名称</option>
                           			<option value="22" data-type="SSH">SSH</option>
                           			<option value="80" data-type="WEB">WEB</option>
                           			<option value="3306" data-type="MYSQL">MYSQL</option>
                           		</select> 
                           	</span>
                           	<span><input id="portNum" type="text" class="text" style="height:35px;width:116px;"></span>
	                            <button class="click Single" id="addService">添加</button>
                           </div>
                         </li>
                         <span id="portCheck" style="color:red;margin-left:240px;"></span>
                         <li class="clearfix" style="margin-top:5px;">
                               <span><input id="servAddedHidden" type="hidden" class="text" style="height:34px;width:480px;"></span>
                               <div>
                                 <span><input id="servAdded" type="text" class="text" style="margin-left:94px;height:34px;width:260px;" readonly="readonly">
                                 <button class="click Single" id="reset" style="margin-left:30px;">重置</button></span>
                               </div>
                         </li>
                         <span id="addedPortCheck" style="color:red;margin-left:90px;"></span>
                         <li class="clearfix">
                               <label class="fl">监测频率</label>
                            <div class="fl clickBox" id="time">
	                            <button class="clickTime" value="1">15分钟</button>
	                            <button class="long" value="2">30分钟</button>
	                            <button class="long" value="3">1小时</button>
                            </div> 
                         </li>
                       </c:if>
                          	<li class="clearfix">
							<label class="fl">服务期限</label>
                           	<div class="fl" style="top:3px;">
                           <!-- 
                           <select class="text select" id="duration" onchange="changePrice();" style="margin-left: 0px;">
                            <select class="text select" id="duration" onchange="changeDur();" style="margin-left: 0px;">
                            -->
                           	<c:if test="${service.id==7}">
                           		<select class="text select" id="duration" onchange="changePrice();" style="margin-left: 0px;">
                           			<option value="12">1年</option>
                           		</select> 
                           	</c:if>
                           	<c:if test="${service.id==8}">
                           		<select class="text select" id="duration" onchange="changeDur();" style="margin-left: 0px;">
                           			<option value="1">1月</option>
                           			<option value="2">2月</option>
                           			<option value="3">3月</option>
                           			<option value="4">4月</option>
                           			<option value="5">5月</option>
                           			<option value="6">6月</option>
                           			<option value="7">7月</option>
                           			<option value="8">8月</option>
                           			<option value="9">9月</option>
                           			<option value="10">10月</option>
                           			<option value="11">11月</option>
                           			<option value="12">1年</option>
                           			<option value="24">2年</option>
                           		</select> 
                           	</c:if>
                           	<c:if test="${service.id==9}">
                           		<select class="text select" id="duration" onchange="changePrice();" style="margin-left: 0px;">
                           			<option value="1">1月</option>
                           			<option value="2">2月</option>
                           			<option value="3">3月</option>
                           			<option value="4">4月</option>
                           			<option value="5">5月</option>
                           			<option value="6">6月</option>
                           			<option value="7">7月</option>
                           			<option value="8">8月</option>
                           			<option value="9">9月</option>
                           			<option value="10">10月</option>
                           			<option value="11">11月</option>
                           			<option value="12">1年</option>
                           		</select> 
                           	</c:if>
                           	<c:if test="${service.id==10}">
                           		<select class="text select" id="duration" onchange="changePrice();" style="margin-left: 0px;">
                           			<option value="1">1月</option>
                           			<option value="2">2月</option>
                           			<option value="3">3月</option>
                           			<option value="4">4月</option>
                           			<option value="5">5月</option>
                           			<option value="6">6月</option>
                           			<option value="7">7月</option>
                           			<option value="8">8月</option>
                           			<option value="9">9月</option>
                           			<option value="10">10月</option>
                           			<option value="11">11月</option>
                           			<option value="12">1年</option>
                           			<option value="24">2年</option>
                           		</select> 
                           	</c:if>                           	                   
                           </div>                            
						</li>
						<c:if test="${service.id==7 }">
						<li class="clearfix">
	                        	<label class="fl">内网主机数</label>
	                        	<div class="fl" style="top:3px;">
	                              <select class="text select" id="ipNum" onchange="changePrice()" style="margin-left: 0px;">   
	                                <option value="64">64</option>
	                                <option value="128">128</option>
	                              </select> 
	                           </div>  
	                     </li>
	                     </c:if>
						
                        <c:if test="${service.id==8 }">
	                         <li class="clearfix">
	                        	<label class="fl">节点个数</label>
	                        	<div class="fl" style="top:3px;">
	                              <select class="text select" id="nodeNum" onchange="changePrice()" style="margin-left: 0px;"> 
	                              <!-- 
	                                <option value="10">10</option>
	                                <option value="20">20</option>
	                                <option value="30">30</option>
	                                <option value="50">50</option>
	                                <option value="100">100</option>
	                                <option value="200">200</option>
	                                <option value="500">500</option>
	                                  --> 
	                              </select> 
	                           </div>  
	                        </li>
                        </c:if>
					</ul>
                    <div class="btnBox" style="text-align:left; margin-left:0px; margin-top:32px; margin-bottom:0px;">                  
                    	<button style="background:#d00000; width:146px;" id="addSysCar">添加到购物车</button>
                        <button style="background:#5aba5f; width:126px" id="buyNowsys">立即购买</button>
                    </div>
				</div>
				
			</div>
		</div>
        <div class="commodity">
        	<div class="imgBox clearfix">
            	<h4>商品信息</h4>
            	<c:forEach var="detailImage" items="${detailImages}" varStatus="status">
	            	<c:if test="${detailImage != null && detailImage != ''}">
	            		<div class="commoditys" style="overflow:hidden">
	            			<img src="${ctx}/source/images/serviceDetail/${detailImage }" alt=""/>
	            		</div>
	            	</c:if>
            	</c:forEach>
            	<!--
                <div class="commoditys" style="overflow:hidden">
                	<img src="${ctx}/source/images/portal/jiguang_detail.jpg" alt="">
                </div>
                  -->
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
                        	<dd><a href="#">QQ交流群<br>470899318</a></dd>
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

    
</div>

</body>
</html>
