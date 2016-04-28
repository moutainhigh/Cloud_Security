<%@ page language="java" import="java.util.*,java.lang.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<style>
.content{
	background-color:#010937;
	
}
.content .x{ width:670px; margin:0 auto;text-align:center; padding:200px 0;}
.content .x h2{ font-size:26px; color:#fff; font-weight:bold; margin-bottom:40px;}
.content .search .text{ 
	width:540px; 
	height:40px; 
	border:#CCC solid 1px; 
	border-radius:4px; 
	line-height:40px; 
	padding-left:20px;
	border-right:none;
	font-size:16px;
	
}
::-moz-placeholder {
    color: #aea4a2;
	font-weight:bold;
}

input:-ms-input-placeholder{
    color: #aea4a2;
	font-weight:bold;
}

input::-webkit-input-placeholder {
    color: #aea4a2;
	font-weight:bold;
}
.content .search .fous{
	border:#317ef3 solid 1px;
	border-right:none;
}
.content .x .btn{ 
	cursor: pointer;
    width: 102px;
    height:44px;
    line-height: 40px;
    padding: 0;
    border: 0;
    background: none;
    background-color: #38f;
    font-size: 16px;
    color: white;
    box-shadow: none;
    font-weight:bold;
	position:relative; 
	left:-2px;
	top:0px;
	font-family:"微软雅黑";

}
.logoimg{
	margin-bottom:24px;
}
.content .x .btn:hover{
	background-color:#317ef3;
}
.result h4{ 
	margin:20px 0 0  10px; 
	font-size:20px;
	cursor:pointer;
	color:#fff;
}
.result h4 span{
	font-size:20px;
	color:#b4bd42;
	padding-right:82px;
}
.result-cent{
	margin-top:20px;

}
</style>
<script>
	$(function(){
		
		fos();
			
	})
	function fos(){
		$('.text').focus(function(){
			$(this).addClass('fous');	
		})
		$('.text').blur(function(){
			$(this).removeClass('fous');	
		})	
	}
	
function detectionUrl(){
	var urlInfo = $("#urlInfo").val();
		$.ajax({
            type: "POST",
            url: "detectionUrl.html",
            data: {"urlInfo":urlInfo},
            dataType:"json",
            success: function(data){
                if(data){
                $("#noneId").hide();
            	    $("#haveId").show();
            	   return;
                }else{
            	     $("#noneId").show();
            	    $("#haveId").hide();
            	   
                }
            },
         }); 
	}
	
</script>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:285px; margin-right:20%">
						<img src="${ctx}/source/images/portal/logo.png" alt="" style="position:relative; top:5px;"/>
                        <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px; font-weight:normal;">X专区</strong>
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html" class="hbule this">首页</a></span>
						<div class="Divlist listJs fl">
							<a href="#" class="hbule">我的安全帮<i></i></a>
							<ul class="list listl">
								<li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
								<li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
								<li style="border: none;"><a href="${ctx}/userDataUI.html">个人信息</a></li>
							</ul>
						</div>
						<span class="fl ask">
                        	<a href="#">手机APP</a>
                        	<b style="display:none"><img src="${ctx}/source/images/portal/apk.png" alt=""></b>
                        </span>
						<span class="fl"><a href="${ctx}/aider.html" class="hbule">帮助</a></span>
						
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
		
        <div class="content">
        	<div class="x">
            		<p class="logoimg"><img src="${ctx}/source/images/portal/newlogo-footer.png" alt=""></p>
            		<h2>Struts2 S2-032漏洞在线检测 </h2>
                    <div class="search">
                    	<input type="text" class="text "placeholder="请输入站点域名或IP" id="urlInfo"><input type="button"  value="开始检测" class="btn"  onclick="detectionUrl();">
                    </div>
                    <!--有漏洞-->
                    <div class="result clearfix" id="haveId" style="display:none" >
                    	<h4 class="fl">检测结果：<span>发现漏洞</span>
                    	<a href="http://mp.weixin.qq.com/s?__biz=MzIxNTE4ODQ1Mg==&mid=2649747802&idx=1&sn=65d056186175e8866df22b58de53bd4c&scene=0#wechat_redirect" target="_blank" style="color:#fff">点击此处，获取处理方案</a>
                    	</h4>
                       
                    </div>
                    <!--无漏洞-->
                     <div class="result clearfix" style="display:none" id="noneId">
                    	<h4 class="fl">检测结果：无。</h4>
                    </div>
            </div>
        
        </div>
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<!--修改-->
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
			<p>Label@1234567890北京电信研究院<br>
				www.anquanbang.net
			</p>
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
