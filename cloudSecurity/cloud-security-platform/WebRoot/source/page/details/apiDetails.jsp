<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>${serviceAPI.name }订单</title>
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
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:285px; margin-right:20%">
						<img src="${ctx}/source/images/portal/newlogo-footer.png" alt="" style="position:relative; top:4px;"/>
                        <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px;">安全能力API</strong>
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
						<span class="fl"><a href="${ctx}/app.html">手机APP</a></span>
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
		<input type="hidden" id="apiId" value="${apiId }"/>
		<input type="hidden" id="indexPage" value="${indexPage }"/>
		<div class="dataCent detailsAPI">
			<div class="data-crumbs">
				<a href="#" style="font-size: 20px;">安全帮</a><i>&gt;</i><a href="#">安全能力API</a><i>&gt;</i><a href="javascript:;">${serviceAPI.name }</a>
			</div>
			<div class="dataBox clearfix">
				<div class="dataL fl">
					<div class="dataImg fl">
						<img src="${ctx}/source/images/portal/product.png" alt="" />
					</div>
				</div>
				<div class="dataR fl">
					<h2 style="font-size:20px; margin-bottom:18px;">${serviceAPI.name }</h2>
                  	<a href="javascript:;" class="buttoncar"><b>1</b><i></i>我的购物车&gt;</a>
					<ul>
						<li class="clearfix">
							<label class="fl">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</label>
                            <div class="fl"><strong>￥00.00</strong><strong></strong></div> 
						</li>
                        <li class="clearfix">
                        	<label class="fl">使用有效期</label>
                            <div class="fl"><span style="top:9px;">自购买日起一年内有效</span></div>
                        </li>
                        <li class="clearfix">
                        	<label class="fl" style="top:4px;">API&nbsp;&nbsp;简&nbsp;&nbsp;介</label>
                            <div class="fl" style="width:546px;">
                            	<p>${serviceAPI.remarks }</p>
                            </div>
                        </li>
                        <li class="clearfix">
							<label class="fl">套&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;餐</label>
                           <div class="fl clickBox">
                           	<div id="clickBox" style="margin-right:-25px;">
                            	<button class="click Single" value="5" id="free" name="1"><i>5次</i>&nbsp;&nbsp;免费使用</button>
                                <button class="long" value="1000" name="2"><i>1000次</i> 峰值100次/秒</button>
                                <button class="long" value="3000" name="3"><i>3000次</i> 峰值100次/秒</button>
                            
                            </div>
                           </div> 
                           
						</li>
                        <li class="clearfix" style="margin-top:34px;">
                        	<label class="fl" style="top:4px;">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量</label>
                            <div class="fl" style="width:546px;">
                            	<div class="btnBoxs clearfix">
                                	<input type="text" value="1" class="text text_box fl" id="num">
                                    <span class="texts fl">
                                    	<em class="add" style="border-bottom:#e5e5e5 solid 1px;"><img src="${ctx}/source/images/portal/-.png" alt=""></em>
                                        <em class="min"><img style="margin-top:9px" src="${ctx}/source/images/portal/sum.png" alt=""></em>
                                    </span>
                                </div>
                            </div>
                        </li>
                       
                         
					</ul>
                    <div class="btnBox" style="text-align:left; margin:10px 0 0 0px;">
                    	<button style="background:#d00000; width:146px;">添加到购物车</button>
                        <button style="background:#5aba5f; width:126px" id="buyAPI">立即购买</button>
                        
                    </div>
				</div>
			</div>
		</div>
        <div class="commodity">
        	<div class="imgBox clearfix">
            	<h4>商品信息</h4>
               <div class="apicent clearfix">
               		<div class="apiL fl">
                    	<dl>
                        	<dt>API表格</dt>
                            <dd class="active">创建敏感词监测订单（任务）</dd>
                            <dd>订单（任务）状态</dd>
                            <dd>获取订单（任务）状态</dd>
                            <dd>获取订单结果</dd>
                            <dd>获取订单结果报告</dd>
                        </dl>
                    </div>
                    <div class="apiR fl">
                    	<ul>
                        	<li class="clearfix">
                            	<label class="fl">接口描述：</label>
                                <div class="fl centapi">
                                	<p>创建敏感词监测订单（任务）</p>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">接口地址：</label>
                                <div class="fl centapi">
                                	<p>rest/openapi/vulnscan/orfer</p>
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
                                            	<td width="170">1</td>
                                                <td width="100">1</td>
                                                <td width="446">1</td>
                                            </tr>
                                            <tr>
                                            	<td width="170">1</td>
                                                <td width="100">1</td>
                                                <td width="446">1</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                             <li class="clearfix">
                            	<label class="fl">发送请求：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:344px;margin-top:8px;">
                                    	vulnscan/orfevulnscan/orfevulnscan/orfevulnscan/orfevulnscan/orfevulnscan/orfevulnscan/orfevulnscan/orfevulnscan/orfevulnscan/orfe
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">返回结果：(示例)</label>
                                <div class="fl centapi">
                                	<div class="cent-div" style="height:38px;">
                                    	
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
                                            	<td width="270">1</td>
                                                <td width="446">1</td>
                                            </tr>
                                            <tr>
                                            	<td width="270">1</td>
                                                <td width="446">1</td>
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
                                            	<td width="270">1</td>
                                                <td width="446">1</td>
                                            </tr>
                                          <tr>
                                            	<td width="270">1</td>
                                                <td width="446">1</td>
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
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="#"><img src="${ctx}/source/images/portal/footlogo.png" alt=""></a>
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
		<div class="foot">
			<p>版权所有Copyright © 2015 中国电信股份有限公司北京研究院京ICP备12019458号-10</p>
		</div>
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
</body> 

</html>
