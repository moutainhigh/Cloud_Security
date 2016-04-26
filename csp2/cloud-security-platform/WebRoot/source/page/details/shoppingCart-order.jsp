<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<script src="${ctx}/source/scripts/order/details.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:270px; margin-right:18%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/portal/newlogo-footer.png" alt="" style="position:relative; top:4px;"/></a>
                        <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px;">购物车</strong>
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html">首页</a></span>
						<div class="Divlist listJs fl">
							<a href="${ctx}/orderTrackInit.html">我的安全帮<!--<i></i>--></a>
							<!-- <ul class="list listl">
								<li><a href="#">我的订单</a></li>
								<li><a href="#">我的资产</a></li>
								<li style="border: none;"><a href="#">个人信息</a></li>
							</ul> -->
						</div>
						<span class="fl"><a href="#">手机APP</a></span>
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
		<div class="dataCent seetlentBox order" style="width:1122px; position:relative; left:-2%;">
			
            <div class="settlement-content">
            	<ul class="settlementList">
                	<li class="listone">
                    	<h3>全部订单</h3>
                        <div class="tabox" id="cartTable" style=" margin-left:0px; width:1108px; padding:0">
                        	<table class="ordertab" width="100%">
                            	<tbody>
                                	<tr>
                                    	<td width="29%"><i id="check" class="check-all"></i>全选</td>
                                        <td width="23%">服务名称</td>
                                        <td width="25%">资产内容</td>
                                        <td width="14%">价格</td>
                                        <td width="12%">操作</td>
                                    </tr>
                                </tbody>
                            </table>
                        	<table class="test-table" width="100%">
                        	<tbody>
                            	 <tr height="40">
                                 	<td width="29%"><i class="chck"></i>订单1234567890</td>
                                    <td width="23%">漏洞扫描服务</td>
                                    <td width="25%">www.anquanbang.net</td>
                                    <td width="14%"><em class="price">199.00</em></td>
                                    <td width="12%"><a href="#">删除</a></td>
                                 </tr>
                                 
                            </tbody>
                        </table>
                        <table class="test-table" width="100%">
                        	<tbody>
                            	 <tr height="40">
                                 	<td width="29%"><i class="chck"></i>订单1234567890</td>
                                    <td width="23%">漏洞扫描服务</td>
                                    <td width="25%">www.anquanbang.net</td>
                                    <td width="14%"><em class="price">199.00</em></td>
                                    <td width="12%"><a href="#">删除</a></td>
                                 </tr>
                                 
                            </tbody>
                        </table>
                        	<div class="settle">
                                <span>已选择<b>0</b>个订单</span><span>总价 <em>¥<i id="priceTotal">00.00</i></em></span><button type="button">去结算</button>
                            </div>
                        </div>
                        
                    </li>
                </ul>
            	
            </div>
            
           
		</div>
        
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="${ctx}/index.html">
		               <img src="${ctx}/source/images/portal/logo footer.png" alt="">
	                   <i class="" style="height:35px; color:#b3b4b5; width:1px; display:inline-block;">|</i>
		               <img src="${ctx}/source/images/portal/newlogo-footer.png" alt="">
                   </a>
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
                        	<dd><a href="#">了解安全帮</a></dd>
                            <dd><a href="#">加入安全帮</a></dd>
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
<script>
$(function(){	
	$('#carrousel').hover(function(){
			$('.left-arrow').fadeIn();
			$('.right-arrow').fadeIn();
		},function(){
			$('.right-arrow').fadeOut();
			$('.left-arrow').fadeOut();
		})
	
	$(".fl-pic").slidelf({
			"prev":"left-arrow",
			"next":"right-arrow",
			"speed":300 //时间可以任意调动  以毫秒为单位
		});
		
	})
	
</script>
<script>
	var w=$(document).width();
	$('.bannerHeight').width(w);
	
	
            	
</script>

</html>
