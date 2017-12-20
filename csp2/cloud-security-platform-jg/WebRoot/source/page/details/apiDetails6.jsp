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
                        	<dd class="active">获取单IP经纬度信息</dd>
                            <dd>获取多IP经纬度信息</dd>
                            <dd>获取国内IP地址经纬度数据总数</dd>
                            <dd>获取国内IP地址经纬度数据块</dd>
                            <dd>获取全球IP地址经纬度数据总数</dd>
                            <dd>获取全球IP地址经纬度数据块</dd>
                        </dl>
                    </div>
                    <div class="apiR fl">
                    	<div class="listtab" style="display:block">
                        	<ul>
                        	<li class="clearfix">
                            	<label class="fl">接口描述：</label>
                                <div class="fl centapi">
                                	<p>获取单IP经纬度信息</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/secbasedata/iplatlong/getlatlongbyip/{token}</p>
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
                                            	<td width="170">ipType</td>
                                                <td width="100">否</td>
                                                <td class="aw" width="446">IP类型，分为IPV4,IPV6；可空，缺省为IPV4</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">ip</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">具体公网IP地址</td>
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
                                	<div class="cent-div" style="height:180px;">
                                    	POST  / rest/openapi/secbasedata/iplatlong/getlatlongbyip/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										    "ipType":"IPV4",<br/>
										    "ip":"10.10.10.10",<br/>
										    <!--  "randomChar" : ""<br/> -->
										}<br/><br/>
										                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:200px;">
                                    	成功：<br/>{"code":200,"ip":"10.10.10.10","network":"","netmask":"","latitude":"","longtitude":"","continent":"","country":"","country_iso_code":"","subdivision_1_name":"","subdivision_2_name":"","city":""}<br/>

										失败：<br/>{"code":404,"message":"查询失败"}<br/>
										      {"code":405,"message":"参数无效"}<br/>
										      {"code":420,"message":"无效IP"}<br/>
										                                    	
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
                                                <td width="446">200：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">ip</td>
                                                <td width="446">要查询的ip地址</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">network</td>
                                                <td width="446">ip地址段</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">netmask</td>
                                                <td width="446">ip地址段掩码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">latitude</td>
                                                <td width="446">纬度</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">longtitude</td>
                                                <td width="446">经度</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">continent</td>
                                                <td width="446">洲</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">country</td>
                                                <td width="446">国家</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">country_iso_code</td>
                                                <td width="446">国家ISO代码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">subdivision_1_name</td>
                                                <td width="446">一级分区名称 例：省（中国）州（美国）</td>
                                            </tr>
                                             <tr>
                                            	<td width="270">subdivision_2_name</td>
                                                <td width="446">二级分区名称 例：（英国，英格兰）林肯郡</td>
                                            </tr>
                                             <tr>
                                            	<td width="270">city</td>
                                                <td width="446">城市</td>
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
                                                <td width="446">404：查询失败</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">405：参数无效</td>
                                            </tr>                                             
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">410：无效IP</td>
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
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">425: 购买的服务次数已用完，请重新购买</td>
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
                                	<p>获取多个IP经纬度信息</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/secbasedata/iplatlong/getlatlongbyiplist/{token}</p>
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
                                            	<td width="170">ipType</td>
                                                <td width="100">否</td>
                                                <td class="aw" width="446">IP类型，分为IPV4,IPV6；可空，缺省为IPV4</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">ipList</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">具体公网IP地址列表，IP地址间以分号(“;”)分割，IP地址数量限定10个，如超过10个，则只返回前10个</td>
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
                                    	POST  / rest/openapi/secbasedata/iplatlong/ getlatlongbyiplist /{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										   "ipType":"IPV4",<br/>
										    "ipList":"10.10.10.10; 20.20.20.20; 30.30.30.30",<br/>
										    <!--  "endTime" : "",<br/>
										    "scanPeriod" : ""<br/> -->
										}<br/><br/>
										                                    	
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:200px;">
                                    	成功：<br/>{"code":200,"latlongList":[{"ip":"10.10.10.10","network":"","netmask":"","latitude":"","longtitude":"","continent":"","country":"","country_iso_code":"","subdivision_1_name":"","subdivision_2_name":"","city":""},...]}<br/>

										失败：<br/>{"code":404,"message":"查询失败"}<br/>
										      {"code":405,"message":"参数无效"}<br/>
										      {"code":420,"message":"无效IP列表参数"}<br/>
										    <!--   {"code":424,"message":"服务已过期，请重新购买"}<br/> --> 
										                                    	
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
                                                <td width="446">200：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">ip</td>
                                                <td width="446">要查询的ip地址</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">network</td>
                                                <td width="446">ip地址段</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">netmask</td>
                                                <td width="446">ip地址段掩码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">latitude</td>
                                                <td width="446">纬度</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">longtitude</td>
                                                <td width="446">经度</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">continent</td>
                                                <td width="446">洲</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">country</td>
                                                <td width="446">国家</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">country_iso_code</td>
                                                <td width="446">国家ISO代码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">subdivision_1_name</td>
                                                <td width="446">一级分区名称 例：省（中国）州（美国）</td>
                                            </tr>
                                             <tr>
                                            	<td width="270">subdivision_2_name</td>
                                                <td width="446">二级分区名称 例：（英国，英格兰）林肯郡</td>
                                            </tr>
                                             <tr>
                                            	<td width="270">city</td>
                                                <td width="446">城市</td>
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
                                                <td width="446">404：查询失败</td>
                                            </tr>
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">405：参数无效</td>
                                            </tr>                                             
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">410：无效IP列表参数</td>
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
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">425: 购买的服务次数已用完，请重新购买</td>
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
                                	<p>获取国内IP地址经纬度数据总数</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/secbasedata/iplatlong/getcncount/{token}</p>
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
                                            	<td width="170">token</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">用户登录后获取到的会话token</td>
                                            </tr>
                                         <!--     <tr>
                                            	<td width="170">token</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">用户登录后获取到的会话token</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">opt</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">pause/resume/stop</td>
                                            </tr> -->
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                             <li class="clearfix" style="margin-top:28px;">
                            	<label class="fl">发送请求：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:120px;">
                                    	GET  /rest/openapi/secbasedata/iplatlong/getcncount/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										   <!--   "opt" : "pause/resume/stop"<br/> -->
										}<br/>

                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:50px;">
                                    	成功：{"code":200,"recordCount":"11000"}<br/>

										失败：{"code":404,"message":"查询失败"}<br/>
										  <!--    {"code":422,"message":"token无效"}<br/>
										      {"code":423,"message":"用户无权限"}<br/>
										      {"code":424,"message":"服务已过期，请重新购买"} <br/>  -->
										                                    	
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
                                                <td width="446">200：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">recordCount</td>
                                                <td width="446">记录数量</td>
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
                                                <td width="446">404：查询失败</td>
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
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">425: 购买的服务次数已用完，请重新购买</td>
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
                                	<p>获取国内IP地址经纬度数据块</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/secbasedata/iplatlong/getcndatablock/{token}</p>
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
                                            	<td width="170">begIndex</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">开始索引</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">endIndex</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">结束索引</td>
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
                                	<div class="cent-div" style="height:160px;">
                                    	POST  /rest/openapi/secbasedata/iplatlong/getcndatablock/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										    "begIndex":"1",<br/>
										    "endIndex":"1000",<br/>
										}<br/>

                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:240px;">
                                    	成功：<br/>
										{"code":200,"latlongList":[{"ip":"10.10.10.10","network":"","netmask":"","latitude":"","longtitude":"" ,"subdivision_1_name":"","subdivision_2_name":"","city":""},...]}<br/>
										
										失败：<br/>
										{"code":404,"message":"失败"}<br/>
										{"code":405,"message":"参数无效"}<br/>
										{"code":422,"message":"token无效"}<br/>
										{"code":423,"message":"用户无权限"}<br/>
										{"code":424,"message":"服务已过期，请重新购买"}<br/>
										{"code":425,"message":"购买的服务次数已用完，请重新购买"}<br/>
										                                    	
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
                                                <td width="446">200：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">latlongList</td>
                                                <td width="446">数据信息列表</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">ip</td>
                                                <td width="446">要查询的ip地址</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">network</td>
                                                <td width="446">ip地址段</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">netmask</td>
                                                <td width="446">ip地址段掩码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">latitude</td>
                                                <td width="446">纬度</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">longtitude</td>
                                                <td width="446">经度</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">continent</td>
                                                <td width="446">洲</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">country</td>
                                                <td width="446">国家</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">country_iso_code</td>
                                                <td width="446">国家ISO代码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">subdivision_1_name</td>
                                                <td width="446">一级分区名称 例：省（中国）州（美国）</td>
                                            </tr>
                                             <tr>
                                            	<td width="270">subdivision_2_name</td>
                                                <td width="446">二级分区名称 例：（英国，英格兰）林肯郡</td>
                                            </tr>
                                             <tr>
                                            	<td width="270">city</td>
                                                <td width="446">城市</td>
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
                                                <td width="446">405：参数无效</td>
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
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">425: 购买的服务次数已用完，请重新购买</td>
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
                                	<p>获取全球IP地址经纬度数据总数</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/secbasedata/iplatlong/gettotalcount/{token}</p>
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
                                        <!--	<tr>
                                            	<td width="170">orderid</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">订单编号</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">taskid</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">订单任务编号</td>
                                            </tr> --> 
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
                                	<div class="cent-div" style="height:120px;">
                                    	POST  /rest/openapi/secbasedata/iplatlong/gettotalcount/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{  <br/>
										 <!--   "ContentType" : "Coustom"<br/>
										    "Content" : "VulnInfo"<br/>
										    "ResultSet":"VulnID,CVE,VulnDesc"<br/>  --> 
										}<br/>

                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:200px;">
                                    	成功：<br/>
										{"code":200,"recordCount":"11000"}<br/>
										
										失败：<br/>
										{"code":404,"message":"失败"}<br/>
                                        {"code":422,"message":"token无效"}<br/>
                                        {"code":423,"message":"用户无权限"}<br/>
                                        {"code":424,"message":"服务已过期，请重新购买"}<br/>
                                        {"code":425,"message":"购买的服务次数已用完，请重新购买"}<br/>

                                    	
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
                                                <td width="446">200：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">recordCount</td>
                                                <td width="446">记录数量</td>
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
                                             <tr>
                                            	<td width="270"></td>
                                                <td width="446">425: 购买的服务次数已用完，请重新购买</td>
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
                                	<p>获取全球IP地址经纬度数据块</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/secbasedata/iplatlong/getdatablock/{token}</p>
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
                                            	<td width="170">begIndex</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">开始索引</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">endIndex</td>
                                                <td width="100">是</td>
                                                <td class="aw" width="446">结束索引</td>
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
                                	<div class="cent-div" style="height:160px;">
                                    	POST  /rest/openapi/secbasedata/iplatlong/getcndatablock/{token}<br/>
										Content-Type: application/json<br/>
										Accept: application/json;version=1.0<br/>
										{     <br/>
										    "begIndex":"1",<br/>
										    "endIndex":"1000",<br/>
										}<br/>

                                    </div>
                                </div>
                            </li>
                            <li class="clearfix" style="margin:22px 0px;">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:240px;">
                                                                                      成功：<br/>
										{"code":200,"latlongList":[{"ip":"10.10.10.10","network":"","netmask":"","latitude":"","longtitude":"","continent":"","country":"","country_iso_code":"","subdivision_1_name":"","subdivision_2_name":"","city":""},...]}<br/>
										
										失败：<br/>
										{"code":404,"message":"失败"}<br/>
                                        {"code":422,"message":"token无效"}<br/>
                                        {"code":423,"message":"用户无权限"}<br/>
                                        {"code":424,"message":"服务已过期，请重新购买"}<br/>
                                        {"code":425,"message":"购买的服务次数已用完，请重新购买"}<br/>
                                    	
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
                                                <td width="446">200：成功</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">latlongList</td>
                                                <td width="446">数据信息列表</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">ip</td>
                                                <td width="446">要查询的ip地址</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">network</td>
                                                <td width="446">ip地址段</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">netmask</td>
                                                <td width="446">ip地址段掩码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">latitude</td>
                                                <td width="446">纬度</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">longtitude</td>
                                                <td width="446">经度</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">continent</td>
                                                <td width="446">洲</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">country</td>
                                                <td width="446">国家</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">country_iso_code</td>
                                                <td width="446">国家ISO代码</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">subdivision_1_name</td>
                                                <td width="446">一级分区名称 例：省（中国）州（美国）</td>
                                            </tr>
                                             <tr>
                                            	<td width="270">subdivision_2_name</td>
                                                <td width="446">二级分区名称 例：（英国，英格兰）林肯郡</td>
                                            </tr>
                                             <tr>
                                            	<td width="270">city</td>
                                                <td width="446">城市</td>
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
                                                <td width="446">405：参数无效</td>
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
                                            <tr>
                                            	<td width="270"></td>
                                                <td width="446">425: 购买的服务次数已用完，请重新购买</td>
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
