<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    response.setHeader("Pragma","No-cache"); 
    response.setHeader("Cache-Control","no-cache"); 
    response.setDateHeader("Expires", -10); 
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>个人中心-设置域名</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/index.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        <strong style="font-size:20px; color:#fff; padding-left:10px;position:relative; top:-10px; font-weight:normal;">个人中心</strong>
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
		<div class="core clearfix" style=" margin-bottom:306px;">
        	<div class="coreLeft fl">
            	<a href="${ctx}/userCenterUI.html"><h3><i></i>个人中心</h3></a>
                <dl>
                	<dt>交易管理</dt>
                    <dd><a  class="active" href="${ctx}/orderTrackInit.html" >我的订单</a></dd>
                    <!-- <dd><a href="#">我的优惠劵</a></dd> -->
                    <dd><a href="${ctx}/balanceUI.html">安全币</a></dd>
                    <dt>个人信息管理</dt>
                    <dd><a href="${ctx}/userDataUI.html">个人资料</a></dd>
                    <dd style="border-bottom:none;"><a  href="${ctx}/userAssetsUI.html">我的资产</a></dd>
                </dl>
            </div>
        	<div class="coreRight coupon  name_domain fl" style="margin-bottom:400px;">
            	<h1 style="padding-bottom:20px; font-size:20px;">设置域名解析</h1>
            	<div class="coupontab">
                	
                    <div class="tabBox" style="padding:30px;">
                    	<ul class="clearfix">
                        	<li style="width:272px;">当前域名：${domainName }</li>
                            <li style="width:336px;">当前A记录www值：${domainIp }</li>
                            <li class="explain">解析说明 <a href="#" class="btn" onclick="checkDomainName('${domainName }', '${ipAddress }', '${orderId }')">检测解析是否更改成功</a></li>
                        </ul>
                       <dl>
                       	<dt>解析说明:</dt>
                        <dd>1、A记录是用来将域名指向主机服务商提供的IP地址</dd>
                        <dd>2、A记录中www设置是将域名example.com解析为www.example.com,在主机记录（RR）处填写www即可</dd>
                        <dd>3、A记录中www对应记录值请填写您的服务器IP地址（必须为IPv4地址，例如：xxx.xx.x.xxx）,若不清楚IP，请咨询您的空间服务商。<br/>如果IP地址的格式中带有端口，如：xxx.xx.xx:8080</dd>
                       </dl>
                        <div class="hr" style="margin: 20px 0 20px 0;"></div>
                    	<h2>修改步骤</h2>
                        <ul class="bz clearfix">
                        	<li>1、域名解析中记录类型选择A记录</li>
                            <li>2、主机记录中填写www</li>
                            <li>3、记录值修改为安全帮防护WAF的地址219.141.189.183,点击保存等待生效</li>
                            <li>4、点击上方检测解析是否更改成功按钮测试解析是否成功</li>
                            <li>注：依据域名解析原理，解析生效，您访问网站或邮箱的生效时间取决于您所在地运营商（如电信、联通等）的缓存刷新时间。</li>
                            <li>一般需要10分钟，还请耐心等待！</li>
                        </ul>
                    	<h3>例子：万网域名解析设置</h3>
                        <div class="open_up"><img src="${ctx}/source/images/example_domainname.png" alt=""></div>
                    </div>
                </div>
            </div>
        </div>
       
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="${ctx}/index.html">
						<img src="${ctx}/source/images/portal/new-footer-logo.png" alt=""/>
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
                        	<dd>QQ交流群<br/>470899318</dd>
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
	<script>
		function checkDomainName(domainName, ipAddress, orderId) {
			$.ajax({
				type: "POST",
				url:"checkDomainName.html",
				data:{"domainName":domainName,"ipAddress":ipAddress,"orderId":orderId},
				dataType:"json",
				success: function(data) {
					if(data.success == true) {
					 	alert("域名解析更改成功！");
					 	window.location.href="domainNameUI.html?orderId="+orderId;
					} else {
						alert("域名解析更改失败！");
					}
				}
			});
		}
	
	</script>

</body>


</html>
