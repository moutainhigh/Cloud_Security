<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>漏洞扫描订单</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>

<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/source/scripts/order/details.js"></script>
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
                        <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px;">网络安全帮</strong>
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
		
		<input type="hidden" id="type" value="${type }"/>
		<input type="hidden" id="serviceId" value="${serviceId }"/>
		<input type="hidden" id="indexPage" value="${indexPage }"/>
		<div class="dataCent">
			<div class="data-crumbs">
				<a href="#" style="font-size: 20px;">安全帮</a><i>&gt;</i><a href="#">网络安全帮</a><i>&gt;</i><a href="javascript:;">漏洞扫描服务</a>
			</div>
			<div class="dataBox clearfix">
				<div class="dataL fl">
					<div class="dataImg fl">
						<img src="${ctx}/source/images/portal/product.png" alt="" />
					</div>
				</div>
				<div class="dataR fl">
					<h2>漏洞监测服务</h2>
                  <button class="buttoncar"><i></i>我的购物车</button>
					<ul>
						<li class="clearfix">
							<label class="fl">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</label>
                            <div class="fl"><strong>￥0</strong><strong><em>￥99</em></strong></div> 
						</li>
                        <li class="clearfix">
							<label class="fl">选 择 型</label>
                            <div class="fl clickBox" id="clickBox">
	                            <button class="click Single" value="2">单次</button>
                            	<button class="long" value="1">长期</button>
                            </div> 
						</li>
                        <li class="clearfix">
							<label class="fl">服务时间</label>
                            <div class="fl">
                            	<span class="start">开始时间 <input type="text" class="text" value="" id="beginDate" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></span>
                                <span class="end" style="display:none">结束时间 <input type="text" class="text" value="" id="endDate" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></span>
                            </div> 
						</li>
                        <li class="clearfix">
							<label class="fl">服务频率</label>
                            <div class="fl clickBox" id="time">
	                            <button class="clickTime" value="2">每周</button>
	                            <button value="3">每月</button>
	                            
                            </div> 
						</li>
                         <li class="clearfix">
							<label class="fl">资&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产</label>
                            <div class="fl">
                            	<div class="select">
                                    <div class="dropdown form-control line" style="width:355px;">
                                      <button class="btnNew" id="dLabel" type="button" style="width:100%">
                                        <em></em>
                                        <span class="caret"></span>
                                      </button>
                                      <ul class="dropdown-menu pop">
	                                     <c:if test="${not empty serviceAssetList}">
	                                     	<c:forEach var="list" items="${serviceAssetList}" varStatus="status">
	                                       		<li><label name="a"><input type="checkbox"  id="${list.id }" >${list.addr}</label></li>
	                                     	</c:forEach>
	                                     </c:if>
                                      </ul>
                                    </div>
                                </div>
                            </div> 
						</li>
					</ul>
                    <div class="btnBox">
                    	<button style="background:#d00000; width:146px;">添加到购物车</button>
                        <button style="background:#5aba5f; width:126px" id="buyNow">立即购买</button>
                    </div>
				</div>
                
				
			</div>
		</div>
        <div class="commodity">
        	<div class="imgBox clearfix">
            	<h4>商品名称</h4>
                <div class="commoditys">
                	<img src="${ctx}/source/images/portal/product1.png" alt="">
                </div>
                <div class="commoditys">
                	<img src="${ctx}/source/images/portal/product2.png" alt="">
                </div>
                <div class="commoditys">
                	<img src="${ctx}/source/images/portal/product3.png" alt="">
                </div>
                <div class="commoditys">
                	<img src="${ctx}/source/images/portal/product4.png" alt="">
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

