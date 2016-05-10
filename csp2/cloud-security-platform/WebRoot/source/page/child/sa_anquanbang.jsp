<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>安全帮-中国电信云安全服务在线商城</title>
<link type="text/css" rel="stylesheet" href="${ctx}/source/css/china.css"/>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${ctx}/source/scripts/common/echarts-all.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/linechart.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/d3.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/china.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/regionTop5.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/serviceTop5.js"></script>
<!-- add by 2016-02 -->
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script type="text/javascript">
$(function(){
	$('.safe_list li').click(function(){
		var index=$(this).index();
		var serviceId=$(this).index()+1;
		$(this).addClass('this').siblings('li').removeClass('this');
		//$(this).parents('.tab').find('.tabCont').children('.tabItem:eq('+index+')').show().siblings().hide();
		$("#serviceId").val(serviceId);
		//清空
		//$("#safe_map").empty();
		
        $("#server").show();
        $('.warn_tab li').eq(0).addClass('this').siblings('li').removeClass('this');
        $('.warn_tab li').eq(0).parents('.tab').find('.tabCont').children('.tabItem:eq(0)').show().siblings().hide();
		if(serviceId==1){
			$('strong[name="service"]').html("漏洞告警TOP5");
			$("#numMsg").val("漏洞总数");
			$("#typeMsg").val("漏洞类型");
			$("#lineMsg").val("漏洞发展趋势");
		}else if(serviceId==2){
            $('strong[name="service"]').html("木马检测TOP5");
            $("#numMsg").val("木马检测总数");
            $("#typeMsg").val("木马检测类型");
            $("#lineMsg").val("木马检测发展趋势");
		}else if(serviceId==3){
            $('strong[name="service"]').html("页面篡改TOP5");
            $("#numMsg").val("页面篡改总数");
            $("#typeMsg").val("页面篡改类型");
            $("#lineMsg").val("页面篡改发展趋势");
        }else if(serviceId==4){
            $('strong[name="service"]').html("关键字检测TOP5");
            $("#numMsg").val("关键字检测总数");
            $("#typeMsg").val("关键字检测类型");
            $("#lineMsg").val("关键字检测发展趋势");
        }else if(serviceId==5){
        	$("#server").hide();
            $('strong[name="service"]').html("可用性告警TOP5");
            $("#numMsg").val("可用性告警总数");
            $("#typeMsg").val("可用性告警类型");
            $("#lineMsg").val("可用性告警发展趋势");
        }
		//获取服务数据
		redrawBranch(null);
        getServiceData();
	    
	});	
	
	$('.warn_tab li').click(function(){
        var index=$(this).index();
        $(this).addClass('this').siblings('li').removeClass('this');
        $(this).parents('.tab').find('.tabCont').children('.tabItem:eq('+index+')').show().siblings().hide();
        
    });
	
	$('.tab .tabList li').mouseover(function(){
		$(this)	.addClass('hover').siblings('li').removeClass('hover');
	})
	$('.tab .tabList').mouseout(function(){
		$(this)	.children('li').removeClass('hover');
	})
	
})

</script>
</head>
<body>

<!--头部-->
<div class="head">
	<div class="headBox">
		<div class="safeL fl">
			<div class="logo">
			<img src="${ctx}/source/images/portal/logo.png" alt=""/><b></b><span>网站安全帮</span>
			</div>
		</div>
		
		<div class="safem fl">
			<span class="fl"><a href="${ctx}/index.html">首页</a></span>
			
			<!-- 商品分类 start -->
			<c:import url="/category.html"></c:import>
			<!-- 商品分类 end -->
				
			<span class="fl"><a href="#">手机APP</a></span>
			<span class="fl"><a href="${ctx}/aider.html">关于我们</a></span>
			
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
<!-- 头部代码结束-->
<div class="user_center safe clear">
  <div class="safe_middle tab" style="width: 930px;">
  	<!-- <ul class="safe_list tabList clearfix">
    	<li class="this">漏洞扫描</li>
        <li>木马检测</li>
        <li>页面篡改</li>
        <li>关键字检测</li>
        <li>可用性</li>
        
    </ul> -->
    <input type="hidden" id="serviceId" value="1"/>
       <input type="hidden" id="numMsg" value="漏洞总数"/>
       <input type="hidden" id="typeMsg" value="漏洞类型"/>
       <input type="hidden" id="lineMsg" value="漏洞发展趋势"/>
    <div class="safe_Box tabCont">
    	<div class="safe_cent tabItem" style="display:block">
        	<ul class="safe_left ">
            	<li>
                    <p>发现漏洞</p>
                    <h2>${leakNum }</h2>
                </li>
                <li>
                    <p>检测页面</p>
                    <h2>${webPageNum }</h2>
                </li>
                <li>
                    <p>监测网站</p>
                    <h2>${webSite }</h2>
                </li>
                <li>
                    <p>断网警告</p>
                    <h2>${brokenNetwork }</h2>
                </li>
            </ul>
            <div class="piece"  style="width:320px; top:395px; left:0" >
            <div class="warn">
                    <ul class="warn_tab">
                        <li>
                                                                              正在使用安全帮的有
                         </li>
                       
                    </ul>
                    <div class="warnBox" id="piece" onmouseover="iScrollAmount=0" onmouseout="iScrollAmount=1">
                        
                    </div>
              </div>
            </div>
        	<div style="margin-left: -15px;" class="safe_map" id="safe-map">
        	
        	</div>
            
        </div>
        <div class="cue">  </div>
        <!-- <div class="safe_cent tabItem">
        	
            
        </div>
        <div class="safe_cent tabItem">
        	
        </div>
        <div class="safe_cent tabItem">
        	
            
        </div>
        <div class="safe_cent tabItem">
        	<ul class="safe_left ">
            	<li>
                	<p>发现漏洞</p>
                	<h2>${leakNum }</h2>
                </li>
                <li>
                	<p>检测页面</p>
                	<h2>${webPageNum }</h2>
                </li>
                <li>
                	<p>监测网站</p>
                	<h2>${webSite }</h2>
                </li>
                <li>
                	<p>断网警告</p>
                	<h2>${brokenNetwork }</h2>
                </li>
            </ul>
        	<div class="safe_map" id="safe-map">
        	
        	</div>
            <div class="safe_right">
            	
            	<div class="warn tab">
                	<ul class="warn_tab tabList clearfix">
                    	<li class="this">
                        	<strong>地域告警TOP5</strong>
                        	<p class="l"></p>
                         </li>
                        <li>
                        	<strong>漏洞告警TOP5</strong>
                        	<p class="r"></p>
                        </li>
                    </ul>
                    <div class="warnBox tabCont">
                    	<div class="warn_b tabItem" style="display:block">
                        	
                        </div>
                        <div class="warn_b tabItem">
                        	
                        </div>
                    </div>
                </div>
            	
                <div class="leak"><img src="${ctx}/source/img/b.jpg" alt=""></div>
            
            </div>	 -->
            
        </div>
    
    </div>
  
</div>
<div class="safeBox">
<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="#"><img src="${ctx}/source/images/portal/new-footer-logo.png" alt=""></a>
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd><a href="#">购买指南</a></dd>
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
                        	<dd><a href="#">QQ交流群</br>470899318</a></dd>
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
           	<img src="${ctx}/source/images/portal/weixin.jpg" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
<script>
var oMarquee = document.getElementById("piece"); //滚动对象
function run() {
oMarquee.scrollTop += iScrollAmount;
if ( oMarquee.scrollTop == iLineCount * iLineHeight )
oMarquee.scrollTop = 0;
if ( oMarquee.scrollTop % iLineHeight == 0 ) {
window.setTimeout( "run()", 300 );
} else {
window.setTimeout( "run()", 50 );
}
}
oMarquee.innerHTML += oMarquee.innerHTML;
window.setTimeout( "run()", 300 );
</script>
</body>
</html>
