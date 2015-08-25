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

<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${ctx}/source/scripts/common/echarts-all.js" type="text/javascript"></script>
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
		//获取服务数据
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
                    <p>监测网站</p>
                    <h2>${webSite }</h2>
                </li>
                <li>
                    <p>断网警告</p>
                    <h2>${brokenNetwork }</h2>
                </li>
            </ul>
        	<div style="margin-left: -100px;" class="safe_map" id="safe-map">
        	
        	</div>
            <div class="safe_right">
            	<!--警告-->
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
                    	<div class="warn_b tabItem" id="regionTOP" style="display:block">asda</div>
                        <div class="warn_b tabItem" id="serviceTOP" style="width:330px; height:230px;">123123</div>
                    </div>
                </div>
            	<!--漏洞-->
                <div class="leak" id="total-level-list"><%-- <img src="${ctx}/source/img/b.jpg" alt=""> --%></div>
            
            </div>	
            
        </div>
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
<!-- 尾部代码开始-->
<div class="bottom_bj">
<div class="bottom clearfix">
    <div class="bottom_main">
      <h3><a href="###">新手入门</a></h3>
      <ul>
        <li><a href="###">新用户注册</a></li>
        <li><a href="###">用户登录</a></li>
        <li><a href="###">找回密码</a></li>
      </ul>
    </div>
    <div  class="bottom_main">
      <h3><a href="###"> 帮助</a></h3>
      <ul>
        <li><a href="###">常见问题</a></li>
      </ul>
    </div>
    <div  class="bottom_main">
      <h3><a href="###">厂商合作</a></h3>
      <ul>
        <li><a href="###">华为</a></li>
        <li><a href="###">安恒</a></li>
        <li><a href="###">360</a></li>
      </ul>
    </div>
    <div class="bottom_main">
        <h3><a href="###">联系我们</a></h3>
        <ul>
            <li><a href="###">客户电话</a></li>
        </ul>
   </div>
   	<div class="bottom_main" style="width:auto;">
        <h3><a href="###">版权信息</a></h3>
        <ul>
        <li>如未经授权用作他处，将保留追究侵权者法律责任的权利。<br />
          京ICP备11111111号-4 / 京ICP证1111111号<br />
          北京市公安局朝阳分局备案编号:110105000501</li>
        </ul>
    </div>
</div>
</div>
</div>
</div>
</body>
</html>
