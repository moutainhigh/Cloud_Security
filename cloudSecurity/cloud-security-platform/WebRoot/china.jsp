<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>云安全服务平台</title>
<link type="text/css" rel="stylesheet" href="${ctx}/source/css/china.css"/>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />

<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${ctx}/source/scripts/common/echarts-all.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/linechart.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/china.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/regionTop5.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/serviceTop5.js"></script>
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
<div class="head_bj">
        <div class="head">
           <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
           <div class="lagst">
               <div class="lagst-left">
                <c:if test="${sessionScope.globle_user!=null }">
                   <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a>
                </c:if>
                 <c:if test="${sessionScope.globle_user==null }">
                    <a href="${ctx}/toLoginUI.html"><img src="${ctx}/source/images/ren.png" /></a>
                 </c:if>
               </div>
               <div class="lagst-right">
               <!-- 如果已经登录则显示用户名，否则需要登录 -->
               <c:if test="${sessionScope.globle_user!=null }">
                <p><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
                <p><a href="${ctx}/exit.html">退出</a></p>
               </c:if>
               <c:if test="${sessionScope.globle_user==null }">
                     <p><a href="${pageContext.request.contextPath}/loginUI.html">登录</a></p>
                     <p><a href="${pageContext.request.contextPath}/registUI.html">注册</a></p>
               </c:if>
               </div>
           </div>
            <div class="list">
               <ul>
                   <li><a href="${ctx}/index.html">首页</a></li>
                   <li class="list_active"><a href="${ctx}/chinas.html">安全态势</a></li>
                   <li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
                   <li><a href="${ctx}/aider.html">在线帮助</a></li>
                   <li style="border-right:1px solid #1369C0;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
               </ul>
           </div>
        </div>
    </div>
<!-- 头部代码结束-->
<div class="user_center safe clear">
  <div class="safe_middle tab">
  	<ul class="safe_list tabList clearfix">
    	<li class="this">漏洞扫描</li>
        <li>木马检测</li>
        <li>页面篡改</li>
        <li>关键字检测</li>
        <li>可用性</li>
        <input type="hidden" id="serviceId" value="1"/>
        <input type="hidden" id="numMsg" value="漏洞总数"/>
        <input type="hidden" id="typeMsg" value="漏洞类型"/>
        <input type="hidden" id="lineMsg" value="漏洞发展趋势"/>
    </ul>
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
                <a target="_blank" href="${ctx}/adminAssetAddr.html">
                    <p>监测网站</p>
                    <h2>${webSite }</h2></a>
                </li>
                <li>
                    <p>断网警告</p>
                    <h2>${brokenNetwork }</h2>
                </li>
            </ul>
            <div class="piece"  style="width:320px; top:450px; left:0" >
            <div class="warn">
                    <ul class="warn_tab">
                        <li>
                                                                              正在使用网站安全帮的有：
                         </li>
                       
                    </ul>
                    <div class="warnBox" id="piece" onmouseover="iScrollAmount=0" onmouseout="iScrollAmount=1">
                        
                    </div>
              </div>
            </div>
        	<div style="margin-left: -100px;" class="safe_map" id="safe-map">
        	
        	</div>
            <div class="safe_right">
            	<!--警告-->
            	<div class="warn tab">
                	<ul class="warn_tab tabList clearfix">
                    	<li class="this">
                        	<strong name="region">地域告警TOP5</strong>
                        	<p class="l"></p>
                         </li>
                        <li id="server">
                        	<strong name="service">漏洞告警TOP5</strong>
                        	<p class="r"></p>
                        </li>
                    </ul>
                    <div class="warnBox tabCont">
                    	<div class="warn_b tabItem" id="regionTOP" style="display:block">
                    	<table id="left-data"></table>
                    	</div>
                        <div class="warn_b tabItem" id="serviceTOP" >
                        <table id="left-data2"></table>
                        </div>
                    </div>
                </div>
            	<!--漏洞-->
                <div class="leak" id="total-level-list"><%-- <img src="${ctx}/source/img/b.jpg" alt=""> --%></div>
            
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
</div>

</div>
</div>
<script>
var oMarquee = document.getElementById("piece"); //滚动对象
//alert(oMarquee);
var iLineHeight = 14; //单行高度，像素
var iLineCount = 6; //实际行数
var iScrollAmount = 2; //每次滚动高度，像素
function run() {
oMarquee.scrollTop += iScrollAmount;
if ( oMarquee.scrollTop == iLineCount * iLineHeight )
oMarquee.scrollTop = 0;
if ( oMarquee.scrollTop % iLineHeight == 0 ) {
window.setTimeout( "run()", 500 );
} else {
window.setTimeout( "run()", 50 );
}
}
oMarquee.innerHTML += oMarquee.innerHTML;
window.setTimeout( "run()", 500 );
</script>
</body>
</html>
