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
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html">
							<img src="${ctx}/source/images/portal/logo.png" alt="" style="position:relative; top:4px;"/>
						</a>
						<span style="font-size: 20px;color: #4a4a4a; padding-left:10px;position:relative; top:-10px;">漏洞检测</span>
                        <!-- <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px; font-weight:normal;">X专区</strong>
						 -->
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html" class="hbule this">首页</a></span>
						
						<!-- 商品分类 start -->
						<c:import url="/category.html"></c:import>
						<!-- 商品分类 end -->
						
						<span class="fl"><a href="${ctx}/knowUs.html" class="hbule">关于我们</a></span>
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
		
	   	<div style="height:550px;width:100%">
	   	    <div class='top'>
	   	        <div class='link'>
	   	            <a href="${ctx}/index.html" style="font-size: 20px;">安全帮</a><i>&gt;</i><a href="${ctx}/Xlist.html">X专区</a><i>&gt;</i><a href="javascript:;">漏洞检测</a>
	   	        </div>
	   	    </div>
	        <iframe src="http://0day.websaas.com.cn/ctcc.html" width="100%" height="100%" scrolling="no" style="border:0px"></iframe>
	        
	    </div>
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<!--修改-->
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
<script type="text/javascript">

</script>
</html>
