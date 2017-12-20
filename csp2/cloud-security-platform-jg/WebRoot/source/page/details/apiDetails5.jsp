<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>${serviceAPI.name }商品信息</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>

<script src="${ctx}/source/scripts/order/apidetails.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    //回显
    var type = ${type};
    var num = ${num};
    $('.clickBox button').each(function(){
    		if($(this).attr("name")==type){
    			$(this).addClass('click').siblings().removeClass('click');	
    		}
		});
	$('#num').val(num);
    
});
</script>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:275px; margin-right:11%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        <a href="${ctx}/api_anquanbang.html"><strong style="font-size:20px; color:#fff; padding-left:10px;position:relative; top:-10px;font-weight:normal;">安全能力API</strong></a>
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
		<input type="hidden" id="apiId" value="${apiId }"/>
		<input type="hidden" id="indexPage" value="${indexPage }"/>
		<div class="dataCent detailsAPI">
			<div class="data-crumbs">
				<a href="${ctx}/index.html" style="font-size: 20px;">安全帮</a><i>&gt;</i><a href="${ctx}/api_anquanbang.html">安全能力API</a><i>&gt;</i><a href="javascript:;">${serviceAPI.name }</a>
			</div>
			<div class="dataBox clearfix">
				<div class="dataL fl">
					<div class="dataImg fl">
						<img src="${ctx}/source/images/portal/product.png" alt="" />
					</div>
				</div>
				<div class="dataR fl">
					<h2 style="font-size:20px; margin-bottom:18px;">${serviceAPI.name }</h2>
                  <a href="javascript:showShopCar();" class="buttoncar" style="right:0px;"><b id="shopCarNum">${carnum}</b><i></i>我的购物车&gt;</a>
					<ul>
						<li class="clearfix" style="height:50px;">
							<label class="fl">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</label>
                           <div class="fl price"><strong id="price">0</strong><strong></strong></div>
                           <span style="position: relative;top: 7px;left:7px;color:#d00000">安全币</span>
                           <span style="position: relative;top: 7px;left:7px">（推广初期价格）</span>
						</li>
                        <li class="clearfix">
                        	<label class="fl">使用有效期</label>
                            <div class="fl"><span style="top:9px;">自购买日起一年内有效</span></div>
                        </li>
                        <li class="clearfix">
                        	<label class="fl" style="top:4px;">API&nbsp;&nbsp;简&nbsp;&nbsp;介</label>
                            <div class="fl" style="width:546px;">
                            	<p>为目标网站提供敏感性词汇的监测。
主要接口：任务创建、任务操作、任务执行状态、任务结果获取、订单报告获取等。</p>
                            </div>
                        </li>
                        <!-- <li class="clearfix">
							<label class="fl">套&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;餐</label>
                           <div class="fl clickBox">
                           	<div id="clickBox" style="margin-right:-25px;">
                                <button class="click Single" value="5" id="free" name="1"><i>5次</i>&nbsp;&nbsp;免费使用</button>
                                <button class="long" value="1000" name="2"><i>1000次</i> 峰值100次/秒</button>
                                <button class="long" value="3000" name="3"><i>3000次</i> 峰值100次/秒</button>
                            
                            </div>
                           </div> 
                           
						</li> -->
                        <li class="clearfix" style="margin-top:41px;">
                        	<label class="fl" style="top:4px;">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量</label>
                            <div class="fl" style="width:546px;">
                            	<div class="btnBoxs clearfix">
                                    <input type="text" value="1" class="text text_box fl" id="num" onKeyUp="checkNum()" onafterpaste="checkNum()" maxlength="5">
                                    <span class="texts fl">
                                    	<em class="add" style="border-bottom:#e5e5e5 solid 1px;"><img src="${ctx}/source/images/portal/-.png" alt=""></em>
                                        <em class="min"><img src="${ctx}/source/images/portal/sum.png" alt=""></em>
                                    </span>
                                </div>
                            </div>
                        </li>
                       
                         
					</ul>
                    <div class="btnBox" style="text-align:left; margin:10px 0 0 0px;">
                        <button style="background:#d00000; width:146px;" id="shopCarAPI">添加到购物车</button>
                        <button style="background:#5aba5f; width:126px" id="buyAPI">立即购买</button>
                    </div>
				</div>
			</div>
		</div>
        <div class="commodity">
        	<div class="imgBox clearfix">
            	<h4 style="color:#343434; margin-bottom:30px;">商品信息</h4>
               <div class="apicent clearfix">
               		<div class="apiL fl" style="width:256px;">
                    	<dl class="apitab">
                        	<dt>API表格</dt>
                        	<dd class="active">获取会话令牌</dd>
                            <dd>创建可用性监测订单</dd>
                            <dd>订单（任务）操作</dd>
                            <dd>获取订单（任务）状态</dd>
                            <dd>获取订单结果</dd>
                            <dd>获取订单结果报告</dd>
                        </dl>
                    </div>
                    <div class="apiR fl">
                    	<div class="listtab" style="display:block">
                        	<ul>
                        	<li class="clearfix">
                            	<label class="fl">接口描述：</label>
                                <div class="fl centapi">
                                	<p>获取会话令牌</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/useraction/login</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">请求方法：</label>
                                <div class="fl centapi">
                                	<p>POST</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">参数说明：</label>
                                <div class="fl centapi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="170">名称</th>
                                                <th width="100">必选</th>
                                                <th width="446">描述</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="170">userID</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">用户在云安全服务平台注册时系统分配的ID</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">apiKey</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">用户在云安全服务平台注册时系统分配的APIKey</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">randomChar</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">用户用于登录时随机生成的字符串，该字符串用户自己生成（长度限制16字节），平台用其生成后续交互使用的token</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                             <li class="clearfix" style="margin-top:28px;">
                            	<label class="fl">发送请求：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:200px;">
                                    	POST  /rest/openapi/ useraction /login<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										    "userID" : "",<br/>
										    "apiKey"  : "",<br/>
										    "randomChar" : ""<br/>
										}<br/><br/>
										                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:98px;">
                                    	成功：{"code":201,"token":"a05d8b432183ea025276cc6640cf9643"," "expireTime":"2018-03-90T11:01:02.002+0800" }<br/>

										失败：{"code":404,"message":"无效的UserID或APIKey"}<br/>
										      {"code":424,"message":"token过期"}<br/>
										                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">返回结果<br>代码表：</label>
                                <div class="fl centapi capi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270">成功代码</th>
                                                <th width="446">描述</th> 
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">201：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">token</td>
                                                <td width="446">会话token，用户进行API调用的标识，后续所有API接口调用都需使用该标识。</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">expireTime</td>
                                                <td width="446">会话token过期时间，格式：ISO8601标准格式，YYYY-MM-DD'T'HH:mm:ss.sssZ</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <table class="tableapi capi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270" align="left">错误代码</th>
                                                <th width="446" align="left">描述</th>
                                               
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">404：无效的UserID或APIKey</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">424: token过期</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                        
                        </ul>
                        </div>
                    	<div class="listtab">
                        	<ul>
                        	<li class="clearfix">
                            	<label class="fl">接口描述：</label>
                                <div class="fl centapi">
                                	<p>创建可用性监测订单</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/availability/order/{token}</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">请求方法：</label>
                                <div class="fl centapi">
                                	<p>POST</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">参数说明：</label>
                                <div class="fl centapi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="170">名称</th>
                                                <th width="100">必选</th>
                                                <th width="446">描述</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                            	<td width="170">targetURL</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">目标地址，字符串</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">startTime</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">计划开始时间（不早于当前时间）</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">endTime</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">计划结束时间</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">scanPeriod</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">检测周期（固定）M1：10分钟、M3: 30分钟、H: 1小时</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                             <li class="clearfix" style="margin-top:28px;">
                            	<label class="fl">发送请求：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:344px;">
                                    	POST  /rest/openapi/availability/order/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										    "targetURL" : "",<br/>
										    "startTime" : "",<br/>
										    "endTime" : "",<br/>
										    "scanPeriod" : ""<br/>
										}<br/><br/>
										                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:98px;">
                                    	成功：{"code":201,"orderId":"16021715560798361"}<br/>

										失败：{"code":404,"message":"创建订单失败"}<br/>
										      {"code":422,"message":"token无效"}<br/>
										      {"code":423,"message":"用户无权限"}<br/>
										      {"code":424,"message":"服务已过期，请重新购买"}<br/>
										                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">返回结果<br>代码表：</label>
                                <div class="fl centapi capi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270">成功代码</th>
                                                <th width="446">描述</th> 
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">201：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">orderId</td>
                                                <td width="446">返回订单Id</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <table class="tableapi capi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270" align="left">错误代码</th>
                                                <th width="446" align="left">描述</th>
                                               
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">404：失败</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">422：token无效</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">423: 用户无权限</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">424: 服务已过期，请重新购买</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                        
                        </ul>
                        </div>
                        <div class="listtab">
                        	<ul>
                        	<li class="clearfix">
                            	<label class="fl">接口描述：</label>
                                <div class="fl centapi">
                                	<p>订单(任务)操作</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/availability/order/{orderid}/{token}</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">请求方法：</label>
                                <div class="fl centapi">
                                	<p>PUT</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">参数说明：</label>
                                <div class="fl centapi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="170">名称</th>
                                                <th width="100">必选</th>
                                                <th width="446">描述</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="170">orderid</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">订单编号</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">token</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">用户登录后获取到的会话token</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">opt</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">pause/resume/stop</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                             <li class="clearfix" style="margin-top:28px;">
                            	<label class="fl">发送请求：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:344px;">
                                    	PUT /rest/openapi/availability/order/{orderid}/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										    "opt" : "pause/resume/stop"<br/>
										}<br/>

                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:98px;">
                                    	成功：{"code":200}<br/>

										失败： {"code":404,"message":"订单操作失败"}<br/>
										      {"code":422,"message":"token无效"}<br/>
										      {"code":423,"message":"用户无权限"}<br/>
										      {"code":424,"message":"服务已过期，请重新购买"} <br/>
										                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">返回结果<br>代码表：</label>
                                <div class="fl centapi capi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270">成功代码</th>
                                                <th width="446">描述</th> 
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">201：成功</td>
                                            </tr>
                                          
                                        </tbody>
                                    </table>
                                    <table class="tableapi capi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270" align="left">错误代码</th>
                                                <th width="446" align="left">描述</th>
                                               
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">404：失败</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">422：token无效</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">423: 用户无权限</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">424: 服务已过期，请重新购买</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                        
                        </ul>
                        </div>
                        <div class="listtab">
                        	<ul>
                        	<li class="clearfix">
                            	<label class="fl">接口描述：</label>
                                <div class="fl centapi">
                                	<p>获取订单（任务）状态</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/availability/orderStatus/{orderid}/{token}</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">请求方法：</label>
                                <div class="fl centapi">
                                	<p>GET</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">参数说明：</label>
                                <div class="fl centapi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="170">名称</th>
                                                <th width="100">必选</th>
                                                <th width="446">描述</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="170">orderid</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">订单编号</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">token</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">用户登录后获取到的会话token</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                             <li class="clearfix" style="margin-top:28px;">
                            	<label class="fl">发送请求：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:344px;">
                                    	GET / rest/openapi/availability/orderStatus/{orderid}/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										    "Content" : "Status"<br/>
										}<br/>

                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:400px;">
                                    	成功：<br/>
										{"code":200,"status":5, "taskObj":<br/>
										[{"taskId":1,"sendBytes":"9216","status":3,"scanTime":"818000",<br/>
										"taskProgress":"101","beginTime":"2016-02-16 15:05:11","receiveBytes":"39936",<br/>
										"executeTime":"2016-02-16 15:06:22","endTime":"2016-02-16 15:20:00",<br/>
										"engineIP":"172.16.100.101","averSendCount":"40","averResponse":"3483",<br/>
										"issueCount":"24","requestCount":"24603",<br/>
										"currentUrl":"http://www.sinosoft.com.cn:80/resources/tools/",<br/>
										"urlCount":"510"}]}<br/>
										
										失败：<br/>
										{"code":404,"message":"获取status失败"}<br/>
										{"code":421,"message":"订单不存在"}<br/>
										{"code":422,"message":"token无效"}<br/>
										{"code":423,"message":"用户无权限"}<br/>
										{"code":424,"message":"服务已过期，请重新购买"}<br/>
										                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">返回结果<br>代码表：</label>
                                <div class="fl centapi capi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270">成功代码</th>
                                                <th width="446">描述</th> 
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">201：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">status</td>
                                                <td width="446">订单状态</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">1：完成无告警</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">2：完成有告警</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">3：扫描中有告警</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">4：开始扫描</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">5：停止</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">taskObj</td>
                                                <td width="446">任务数组名称</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">taskId</td>
                                                <td width="446">任务id</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">status</td>
                                                <td width="446">任务状态,2：运行中3：结束</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">beginTime</td>
                                                <td width="446">开始时间</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">endTime</td>
                                                <td width="446">结束时间</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">executeTime</td>
                                                <td width="446">执行时间</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">scanTime</td>
                                                <td width="446">运行时长</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">taskProgress</td>
                                                <td width="446">任务进度</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">currentUrl</td>
                                                <td width="446">当前url</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">engineIP</td>
                                                <td width="446">引擎ip</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">sendBytes</td>
                                                <td width="446">发送字节</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">receiveBytes</td>
                                                <td width="446">接收字节</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">averSendCount</td>
                                                <td width="446">每秒访问个数</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">averResponse</td>
                                                <td width="446">平均响应时间</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">issueCount</td>
                                                <td width="446">已经发现弱点个数</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">requestCount</td>
                                                <td width="446">请求次数</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">urlCount</td>
                                                <td width="446">url个数</td>
                                            </tr>
                                          
                                        </tbody>
                                    </table>
                                    <table class="tableapi capi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270" align="left">错误代码</th>
                                                <th width="446" align="left">描述</th>
                                               
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">404：获取status失败</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">421：订单不存在</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">422：token无效</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">423: 用户无权限</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">424: 服务已过期，请重新购买</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                        
                        </ul>
                        </div>
                        <div class="listtab">
                        	<ul>
                        	<li class="clearfix">
                            	<label class="fl">接口描述：</label>
                                <div class="fl centapi">
                                	<p>获取订单结果</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/availability/orderResult/{orderid}/{taskid}/{token}</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">请求方法：</label>
                                <div class="fl centapi">
                                	<p>GET</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">参数说明：</label>
                                <div class="fl centapi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="170">名称</th>
                                                <th width="100">必选</th>
                                                <th width="446">描述</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="170">orderid</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">订单编号</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">taskid</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">订单任务编号</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">token</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">用户登录后获取到的会话token</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                             <li class="clearfix" style="margin-top:28px;">
                            	<label class="fl">发送请求：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:344px;">
                                    	GET / rest/openapi/availability/orderResult/{orderid}/{taskid}/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{  <br/>
										    "ContentType" : "Coustom"<br/>
										    "Content" : "VulnInfo"<br/>
										    "ResultSet":"VulnID,CVE,VulnDesc"<br/>
										}<br/>

                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:400px;">
                                    	成功：<br/>
										{"code":200,"alarmObj":[{"advice":"一、关闭web服务器错误提示。<br/>
										二、确保代码注释中不含有电话号码。","alarm_content":"http://www.sinosoft.com.cn:80/sy/gg_more.html",<br/>
										"alarm_time":"2016-02-16 15:06:11.0","alarm_type":"漏洞扫描" , <br/>
										"level":0,"name":"电话号码","score":"79" ,"taskId":1,<br/>
										"url":"http://www.sinosoft.com.cn:80/"},<br/>
										{"advice":"一、关闭web服务器错误提示。二、确保代码注释中不含有电话号码。",<br/>
										"alarm_content":"http://www.sinosoft.com.cn:80/xwzx/xwzx_news/xwzx_news_0074.html",<br/>
										"alarm_time":"2016-02-16 15:06:11.0","alarm_type":"漏洞扫描" , "level":0,"name":"电话号码",<br/>
										"score":"79","taskId":1,"url":"http://www.sinosoft.com.cn:80/"}]}<br/>
										
										失败：<br/>
										{"code":404,"message":"获取result失败"}<br/>
										{"code":421,"message":"订单不存在"}<br/>
										{"code":422,"message":"token无效"}<br/>
										{"code":423,"message":"用户无权限"}<br/>
										{"code":424,"message":"服务已过期，请重新购买"}<br/>
                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">返回结果<br>代码表：</label>
                                <div class="fl centapi capi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270">成功代码</th>
                                                <th width="446">描述</th> 
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">201：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">alarmObj</td>
                                                <td width="446">结果数组名称</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">taskId</td>
                                                <td width="446">任务id</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">advice</td>
                                                <td width="446">告警建议</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">alarm_content</td>
                                                <td width="446">告警内容</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">alarm_time</td>
                                                <td width="446">告警时间</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">alarm_type</td>
                                                <td width="446">告警服务类型</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">level</td>
                                                <td width="446">告警级别</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">name</td>
                                                <td width="446">告警名称</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">score</td>
                                                <td width="446">得分</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">url</td>
                                                <td width="446">告警资源地址</td>
                                            </tr>
                                          
                                        </tbody>
                                    </table>
                                    <table class="tableapi capi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270" align="left">错误代码</th>
                                                <th width="446" align="left">描述</th>
                                               
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270">code</td>
                                                <td width="446">返回码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">404：获取result失败</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">421：订单不存在</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">422：token无效</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">423: 用户无权限</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">424: 服务已过期，请重新购买</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                        
                        </ul>
                        </div>
                        <div class="listtab">
                        	<ul>
                        	<li class="clearfix">
                            	<label class="fl">接口描述：</label>
                                <div class="fl centapi">
                                	<p>获取订单结果报告</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/availability/orderReport/{orderid}/{token}</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">请求方法：</label>
                                <div class="fl centapi">
                                	<p>POST</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">参数说明：</label>
                                <div class="fl centapi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="170">名称</th>
                                                <th width="100">必选</th>
                                                <th width="446">描述</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="170">orderid</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">订单编号</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">token</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">用户登录后获取到的会话token</td>
                                            </tr>
                                            
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                             <li class="clearfix" style="margin-top:28px;">
                            	<label class="fl">发送请求：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:200px;">
                                    	POST /rest/openapi/availability/orderReport/{orderid}/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										    "Content" : "Report"<br/>
										}<br/>

                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:38px;">
                                    	成功：
订单报告文件流
                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">返回结果<br>代码表：</label>
                                <div class="fl centapi capi">
                                	<table class="tableapi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270">成功代码</th>
                                                <th width="446">描述</th> 
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270"></td>
                                                <td width="446"></td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446"></td>
                                            </tr>
                                          
                                        </tbody>
                                    </table>
                                    <table class="tableapi capi" width="718" cellpadding="0" cellspacing="0">
                                    	<thead>
                                        	<tr>
                                            	<th width="270" align="left">错误代码</th>
                                                <th width="446" align="left">描述</th>
                                               
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td width="270"></td>
                                                <td width="446"></td>
                                            </tr>
                                          <tr>
                                            	<td width="270"></td>
                                                <td width="446"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                        
                        </ul>
                        </div>
                    
                    </div>
               </div>
            </div>
        </div>
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
