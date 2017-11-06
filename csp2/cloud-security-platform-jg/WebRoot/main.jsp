<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>安全帮-中国电信云安全服务在线商城</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<script src="${ctx}/source/scripts/common/adview_pic_cpc_cpm_cpa_guanggao_gg_ads_300x250.js" ></script>  
<script type="text/javascript">
 if (typeof(killads)=='undefined'){alert('您的浏览器可能安装了广告屏蔽插件，请检查');}
</script> 
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />

				<style>
html, body {
	height: 100%;
	min-height: 100%;
	position: relative;
}
</style>
</head>

<body>
	<div class="safeBox">
		<div class="safe01">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img
							src="${ctx}/source/images/portal/main_logo.png" alt=""
							style="position:relative; top:5px;" /></a>
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html"
							class="hbule this">首页</a></span>

						<!-- 商品分类 start -->
						<c:import url="/category.html"></c:import>
						<!-- 商品分类 end -->

						<span class="fl"><a href="${ctx}/knowUs.html" class="hbule">关于我们</a></span>
						<span class="fl shopping" style="margin-right:0"> <a
							href="${ctx}/showShopCar.html"><i></i>购物车</a>
						</span>

					</div>
					<div class="safer fr" style="margin-left:0px;">
						<!-- 如果已经登录则显示用户名，否则需要登录 -->
						<c:if test="${sessionScope.globle_user!=null }">
							<div class="login clearfix">
								<a href="${ctx}/userCenterUI.html" class="fl loginname">${sessionScope.globle_user.name }</a>
								<em class="fl">|</em> <a href="${ctx}/exit.html" class="fl">退出</a>
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
			<div class="bannerBox vBox">
				<div class="v_show">

					<div class="bannerB v_cont">
						<ul class="bannerHeight banner_img clearfix">
							<!--  <li style=" display: list-item;"><div class="vb"><a href="${ctx}/registUI.html"><img src="${ctx}/source/images/portal/NewVIP.png" alt=""></a></div></li>
						<li style=""><div class="vb"><a href="#"><img src="${ctx}/source/images/portal/NewVIP2.png" alt=""></a></div></li>
						<li style=""><div class="vb"><a href="#"><img src="${ctx}/source/images/portal/t1.png" alt=""></a></div></li>
                        <li style=""><div class="vb"><a href="#"><img src="${ctx}/source/images/portal/t3.png" alt=""></a></div></li>
						-->
							<c:forEach var="list" items="${adList}" varStatus="status">
								<c:if test="${status.index ==0}">
									<li style=" display: list-item;">
								</c:if>
								<c:if test="${status.index !=0}">
									<li style="">
								</c:if>
								<div class="vb">
									<c:if test="${list.id ==4 && list.image == 'NewVIP.png'}">
										<a href="${ctx}/registUI.html"><img
											src="${ctx}/source/images/ad/${list.image }" alt=""
											style="width:100%;height:100%"></a>
									</c:if>
									<c:if test="${list.id ==13 && list.image == 'xthd.png'}">
										<a href="${ctx}/system_activity.html"><img
											src="${ctx}/source/images/ad/${list.image }" alt=""
											style="width:100%;height:100%"></a>
									</c:if>
									<c:if test="${list.id !=4 && list.image != 'NewVIP.png' && list.id !=13 && list.image != 'xthd.png'}">
										<a href="#"><img
											src="${ctx}/source/images/ad/${list.image }" alt=""
											style="width:100%;height:100%;cursor: default;"></a>
									</c:if>

								</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<ol class="bannerbtn circle clearfix">
					<!-- <li class="active"></li>
                    <li></li>
                    <li></li>
                    <li></li> -->
					<c:forEach var="list" items="${adList}" varStatus="status">
						<c:if test="${status.index ==0}">
							<li class="active"></li>
						</c:if>
						<c:if test="${status.index !=0}">
							<li></li>
						</c:if>
					</c:forEach>
				</ol>
			</div>

		</div>
		<div class="safe02" style="padding-bottom:120px;">

			<div class="imgBox" style="width:1108px; margin:0 auto">
				<h2>热门服务</h2>
				<div class="listBox clearfix">
					<div class="listL fl">
						<div class="">
							<a href="${ctx}/web_anquanbang.html">
								<p>
									<img src="${ctx}/source/images/portal/web.png" alt="">
								</p>
							</a>
						</div>
					</div>
					<div class="listR fl">
						<div class="new-title clearfix">
							<a href="${ctx}/web_anquanbang.html"><h3 class="fl">网站安全帮</h3></a>
							<a href="${ctx}/web_anquanbang.html" class="fr">查看更多<b></b></a>
						</div>
						<ul class="newlist newlist-top fl">
							<c:forEach var="list" items="${servList}" varStatus="status">
								<c:choose>
									<c:when test="${list.id<3 || list.id==6}">
										<li class="fl">
											<!--<a href="${ctx}/selfHelpOrderInit.html?serviceId=${list.id }&indexPage=1">
                                    --> 
                                    	<a href="javascript:;" onclick="buySelfHelpOrderMain(${list.id });">
											<c:if test="${list.id==6 }">												
												<form action="wafDetails.html" method="post"
													id="wafDetailsForm">
													<input type="hidden" id="serviceIdHidden" name="serviceId" />
													<input type="hidden" id="indexPage" name="indexPage"
														value="1" />
												</form>
											</c:if>
											<c:if test="${list.id<3 }">												
												<form action="selfHelpOrderInit.html" method="post"
													id="selfHelpOrderInitForm">
													<input type="hidden" id="serviceIdHidden" name="serviceId" />
													<input type="hidden" id="indexPage" name="indexPage"
														value="1" />
												</form>
											</c:if>
												
													
												 <i><img src="${ctx}/source/images/portal/HOT.png"
													alt=""></i>
												<p style="width:100%;height:180px;">
													<!-- <img src="${ctx}/source/images/serviceIcon/${list.icon}" alt="" style="width:auto;height:auto;max-width:50%;max-height:50%;vertical-align:middle;"> -->
													<img
														src="${ctx}/source/images/serviceIcon/${list.home_icon}"
														alt=""
														style="width:130px;height:auto;margin-top: 25px;margin-left: 65px;">
												</p> <!--<c:if test="${list.id ==1}">
	                                    <p><img src="${ctx}/source/images/portal/vulnScan.png" alt=""></p>
                                    </c:if>
                                    <c:if test="${list.id ==2}">
                                    	<p><img src="${ctx}/source/images/portal/trojanDetect.png" alt=""></p>
                                    </c:if>
                                    <c:if test="${list.id ==3}">
                                    	<p><img src="${ctx}/source/images/portal/webPageTamper.png" alt=""></p>
                                    </c:if>-->
												<h4>${list.name }</h4>
												<div class="purchase" style="left:77px">
													<c:if test="${empty list.price}">
														<strong
															style="font-size: 20px; color: #D00000;margin-right: 16px;font-family: Arial Regular; font-weight:normal">0.00
															<em style="font-size: 14px;color: #D00000;">&nbsp;安全币</em>
														</strong>
														<strong
															style="text-decoration:line-through;color: #d00000;"><em
															style="font-family: Arial Regular;font-size: 16px;">99.00
																<em style="font-size: 14px;color: #D00000;">&nbsp;安全币</em>
														</em></strong>
													</c:if>
													<c:if test="${!empty list.price}">
														<strong
															style="font-size: 20px; color: #D00000;margin-right: 16px;font-family: Arial Regular; font-weight:normal">
															<fmt:formatNumber type="number" value="${list.price }"
																maxFractionDigits="2" minFractionDigits="2" /><em
															style="font-size: 14px;color: #D00000;">&nbsp;安全币</em>
														</strong>
													</c:if>

													<!--<strong style="text-decoration:line-through;color: #d00000;"><em style="font-family: Arial Regular;font-size: 16px;">¥99</em></strong>
                                    -->
												</div>
										</a>
										</li>
									</c:when>
								</c:choose>
							</c:forEach>
							<!--
                            <li class="fl">
                                <a href="${ctx}/selfHelpOrderInit.html?serviceId=2&indexPage=1">
                                    <i><img src="${ctx}/source/images/portal/free.png" alt=""></i>
                                    <p><img src="${ctx}/source/images/portal/trojanDetect.png" alt=""></p>
                                    <h4>网站挂马监测服务</h4>
                                    <div class="purchase">
                                    	<strong style="font-size: 20px; color: #D00000;margin-right: 16px;font-family: Arial Regular; font-weight:normal">¥0</strong>
                                    	<strong style="text-decoration:line-through;color: #d00000;"><em style="font-family: Arial Regular;font-size: 16px;">¥99</em></strong>
                                    </div>
                                </a>
                            </li>
                            <li class="fl">
                                <a href="${ctx}/selfHelpOrderInit.html?serviceId=4&indexPage=1">
                                    <i style="display:none"><img src="${ctx}/source/images/portal/free.png" alt=""></i>
                                    <p><img src="${ctx}/source/images/portal/sensitiveWord.png" alt=""></p>
                                    <h4>网页敏感内容监测服务</h4>
                                    <div class="purchase">
                                    	<strong style="font-size: 20px; color: #D00000;margin-right: 16px;font-family: Arial Regular; font-weight:normal">¥0</strong>
                                    	<strong style="text-decoration:line-through;color: #d00000;"><em style="font-family: Arial Regular;font-size: 16px;">¥99</em></strong>
                                    </div>
                                </a>
                            </li>
                        -->
						</ul>
					</div>
				</div>
				
                <div class="listBox clearfix">
                    <div class="listL fl">
                        <div class="">
                            <a href="${ctx}/system_anquanbang.html">
                            <p><img src="${ctx}/source/images/portal/system_1.png" alt="" style="width:262px;height:330px"></p>
                            </a>
                        </div>
                    </div>
					<div class="listR fl">
						<div class="new-title clearfix">
							<a href="${ctx}/system_anquanbang.html"><h3 class="fl">系统安全帮</h3></a>
							<a href="${ctx}/system_anquanbang.html" class="fr">查看更多<b></b></a>
						</div>
						<ul class="newlist newlist-top fl">
							<c:forEach var="sysList" items="${servSyslist}"
								varStatus="status">
								<c:choose>
									<c:when test="${sysList.id>=7}">

										<li class="fl"><a href="javascript:;"
											onclick="buySelfHelpOrderSysMain(${sysList.id});">
												<i><img src="${ctx}/source/images/portal/system_Sign.png"
													alt=""></i> 
												<p>
													<img
														src="${ctx}/source/images/serviceIcon/${sysList.home_icon}"
														alt=""
														style="width:130px;height:auto;margin-top: 25px;margin-left: 65px;">
												</p>
												<h4>${sysList.name}</h4>
												<div class="purchase" style="left:77px">
													<strong
														style="font-size: 20px; color: #D00000;margin-right: 16px;font-family: Arial Regular; font-weight:normal">
														<fmt:formatNumber type="number" value="${sysList.price }"
															maxFractionDigits="2" minFractionDigits="2" /><em
														style="font-size: 14px;color: #D00000;">&nbsp;安全币</em>
													</strong>
													<!-- <strong style="font-size: 20px; color: #D00000;margin-right: 16px;font-family: Arial Regular; font-weight:normal">${sysList.price}<em style="font-size: 14px;color: #D00000;">&nbsp;安全币</em></strong>
	                                    	<strong class="oldprice">${sysList.price}<em style="font-size: 11px;">安全币</em></strong> -->
												</div>
										</a>
									</c:when>
								</c:choose>
							</c:forEach>
						</ul>
						<form action="systemOrderOperaInit.html" method="post"
							id="SysMainForm">
							<input type="hidden" id="sysIdHidden" name="serviceId" value="" /> <input
								type="hidden" id="indexPage" name="indexPage" value="4" />
						</form>
					</div>
				</div>
				<div class="listBox clearfix">
					<div class="listL fl">
						<div class="">
							<a href="${ctx}/api_anquanbang.html">
								<p>
									<img src="${ctx}/source/images/portal/API262-330.png" alt="">
								</p>
							</a>
						</div>
					</div>
					<div class="listR fl">
						<div class="new-title clearfix">
							<a href="${ctx}/api_anquanbang.html"><h3 class="fl">安全能力API</h3></a>
							<a href="${ctx}/api_anquanbang.html" class="fr">查看更多<b></b></a>
						</div>
						<ul class="newlist newlist-top fl">
							<c:forEach var="apiList" items="${servAPIList}"
								varStatus="status">
								<c:choose>
									<c:when test="${apiList.id<=3}">

										<li class="fl"><a href="javascript:;"
											onclick="buySelfHelpOrderAPIMain(${apiList.id});">
												<i><img src="${ctx}/source/images/portal/API_Sign.png"
													alt=""></i> <!--<c:if test="${apiList.id ==3}">
                                    	<p><img src="${ctx}/source/images/portal/webPageTamper.png" alt=""></p>
	                           		</c:if>
	                           		<c:if test="${apiList.id ==4}">
                                    	<p><img src="${ctx}/source/images/portal/sensitiveWord.png" alt=""></p>
	                           		</c:if>
	                           		<c:if test="${apiList.id ==5}">
                                    	<p><img src="${ctx}/source/images/portal/availability.png" alt=""></p>
	                           		</c:if> -->
												<p>
													<img
														src="${ctx}/source/images/serviceIcon/${apiList.home_icon}"
														alt=""
														style="width:130px;height:auto;margin-top: 25px;margin-left: 65px;">
												</p>
												<h4>${apiList.name}</h4>
												<div class="purchase" style="left:77px">
													<strong
														style="font-size: 20px; color: #D00000;margin-right: 16px;font-family: Arial Regular; font-weight:normal">
														<fmt:formatNumber type="number" value="${apiList.price }"
															maxFractionDigits="2" minFractionDigits="2" /><em
														style="font-size: 14px;color: #D00000;">&nbsp;安全币</em>
													</strong>
													<!-- <strong style="font-size: 20px; color: #D00000;margin-right: 16px;font-family: Arial Regular; font-weight:normal">${apiList.price}<em style="font-size: 14px;color: #D00000;">&nbsp;安全币</em></strong>
                                    	<strong class="oldprice">${apiList.price}<em style="font-size: 11px;">安全币</em></strong> -->
												</div>
										</a>
									</c:when>
								</c:choose>
							</c:forEach>
						</ul>
						<form action="selfHelpOrderAPIInit.html" method="post"
							id="APIMainForm">
							<input type="hidden" id="apiIdHidden" name="apiId" value="" /> <input
								type="hidden" id="indexPage" name="indexPage" value="2" />
						</form>
					</div>
				</div>



			</div>


		<div class="safe05 special">
			<div class="specialblock" style="position:relative">
				<h3>专题模块</h3>
				<dl class="clearfix" style="margin-right:-20px;">
					<a href="${ctx}/sa_anquanbang.html" target="_blank">
						<dd class="fl frn">
							<div class="introduce">
								<h4>安全大数据</h4>
								<p>安全大数据分析</p>
								<p>可视化地域告警统计分析</p>
								<p>实时安全趋势预测</p>
							</div>
						</dd>
					</a>
					<a href="${ctx}/Xlist.html">
						<dd class="fl frn frn2">
							<div class="introduce">
								<h4>X专区</h4>
								<p>独特极客安全工具</p>
								<p>特定漏洞专用检测工具</p>
							</div>
						</dd>
					</a>
				</dl>
			</div>
		</div>
		<div class="safe03" style=" background:#efefef">
			<div class="imgBox" id="carrousel" style="width: 1180px;">
				<h2>合作伙伴</h2>

				<div class="friend-Link ">
					<div class="left-arrow">
						<a href="javascript:;"><img
							src="${ctx}/source/images/portal/left.png"></a>
					</div>
					<div class="fl-pic">
						<ul class="imgist clearfix">
							<li><img src="${ctx}/source/images/portal/ico14.png" alt=""></li>
							<li><img src="${ctx}/source/images/portal/ico11.png" alt=""></li>
							<li><img src="${ctx}/source/images/portal/ico17.png" alt=""></li>
							<li><img src="${ctx}/source/images/portal/ico18.png" alt=""
								height="150" width="150"></li>
							<li><img src="${ctx}/source/images/portal/ico12.png" alt=""></li>
							<li><img src="${ctx}/source/images/portal/ico15.png" alt=""></li>
							<li><img src="${ctx}/source/images/portal/ico13.png" alt=""></li>
							<li><img src="${ctx}/source/images/portal/ico16.png" alt=""></li>
							<li><img src="${ctx}/source/images/portal/ico19.png" alt=""></li>
							<!--<li><a href="#"><img src="imges/ico13.png" alt=""></a></li>
                        <li><a href="#"><img src="imges/ico14.png" alt=""></a></li>
                        <li><a href="#"><img src="imges/ico15.png" alt=""></a></li>-->
						</ul>
					</div>
					<div class="right-arrow">
						<a href="javascript:;"><img
							src="${ctx}/source/images/portal/right.png"></a>
					</div>
				</div>

			</div>
		</div>
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<!--修改-->
					<a href="${ctx}/index.html"> <img
						src="${ctx}/source/images/portal/new-footer-logo.png" alt="" />
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
							<dd>
								<a href="${ctx}/knowUs.html">了解安全帮</a>
							</dd>
							<dd>
								<a href="${ctx}/joinUs.html">加入安全帮</a>
							</dd>
							<dd>联系我们</dd>
						</dl>
					</li>
					<li>
						<h2>关注我们</h2>
						<dl>
							<dd>
								QQ交流群<br>470899318
							</dd>
							<dd class="weixin">
								<a href="#">官方微信</a>
							</dd>
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
		<!--<div class="foot">
			<p>
			<span id='cnzz_stat_icon_1259574975'></span>
			<script src=' http://s95.cnzz.com/z_stat.php?id=1259574975&show=pic1' type='text/javascript'></script>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版权所有Copyright © 2015 中国电信股份有限公司北京研究院京ICP备12019458号-10
			</p>
		</div>-->
	</div>
	<!---执行效果-->
	<div class="weixinshow popBoxhide" id="weixin">
		<i class="close chide"></i>
		<div class="Pophead">
			<h1 class="heaf">安全帮微信二维码</h1>
		</div>
		<div class="popBox">
			<p>
				打开微信，点击右上角的“+”，选择“扫一扫”功能，<br> 对准下方二维码即可。 
			</p>
			<div class="weinImg" style="text-align:center;">
				<img src="${ctx}/source/images/portal/weixin.jpg" alt="">
			</div>
		</div>

	</div>

	<div class="shade"></div>
</body>
<script>
	$(function() {
		$('#carrousel').hover(function() {
			$('.left-arrow').fadeIn();
			$('.right-arrow').fadeIn();
		}, function() {
			$('.right-arrow').fadeOut();
			$('.left-arrow').fadeOut();
		})

		$(".fl-pic").slidelf({
			"prev" : "left-arrow",
			"next" : "right-arrow",
			"speed" : 300
		//时间可以任意调动  以毫秒为单位
		});

	})
</script>
<script>
	//var w=$(document).width();
	//$('.bannerHeight').width(w);
	//$('.vBox').width(w);
	//$('.banner_img li').width(w);
	//$('.banner_img li img').width(w);
</script>

</html>