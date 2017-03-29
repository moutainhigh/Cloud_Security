<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>安全帮-中国电信云安全服务在线商城</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet" />	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/source/attacking/css/attacking.css" type="text/css"></link>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts3/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts3/china.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/anquan_state.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>

<style>
			.img{
				display: block;
				width: 386px;
				height: 386px;
				border-radius: 50%;
			}
			
			@keyframes ancircle{
				0%{transform: rotate(0deg);}
				100%{transform: rotate(360deg);}
			}
			@-webkit-keyframes ancircle{
				0%{transform: rotate(0deg);}
				100%{transform: rotate(360deg);}
			}
			@-moz-keyframes ancircle{
				0%{transform: rotate(0deg);}
				100%{transform: rotate(360deg);}
			}
			@-ms-keyframes ancircle{
				0%{transform: rotate(0deg);}
				100%{transform: rotate(360deg);}
			}
			#vulnscanAlarmOneHour,#wafOneHour{
				width:600px;height:500px; z-index:99; position:absolute;
				top:0; left:0;
			}
			
	body{
        background:url(${ctx}/source/images/personalCenter/background4.png);
    }	
	.contentBox{
		/*background-color: #04144d;*/
		position: relative;
	}
	.contentBox nav{
		/*top:20px;*/
		left: 230px;
		position: absolute;
		z-index:99999;
	
	}
	.contentBox nav ul{
		/*margin-right: -100px;
		padding-top: 30px;*/
		position: absolute;
		left: 0px;
		top:0px
	}
	.contentBox nav li{
		float:left;
		padding-right: 64px;
		line-height: 50px;
		position: relative;
	}
	.contentBox nav li a{
		font-size: 16px;
		color: #fff;
	}
	.contentBox nav{
		width: 1120px;
/* 		margin: 0 auto; */
		/*overflow: hidden;*/
		height: 60px;
		
		
	}
	.contentBox nav ul li.active a{
		font-size: 16px;
		color: #00aeff;
		
	}
	.contentLeft{
		margin-top: 42px;
		width: 125px;
		position: absolute;
		left: 20px;
		top:70px
	}
	.contentLeft ul{
		width: 125px;
		background-color: #364371;
		min-height: 400px;
	}
	.contentRight{
		margin-left: 150px; 
	}

	.contentcenter{
		/*width: 1120px;*/
		margin: 0 auto;
		/*padding-top: 40px;*/
	}
	.contentcenter ul li{
		height: 52px;
		line-height: 52px;
		text-align: center;
		background: url(${ctx}/source/images/personalCenter/bt.png) no-repeat left bottom;
	}
	
	
	.contentcenter ul li a{
		color: #fff;
		font-size: 16px;
		display: block;
	}
	.contentcenter ul li.active a{
		width: 100%;
		height: 100%;
		border-left: 6px solid #0d84bf;
		color: #00aeff;
	}
	
	.contentcenterdata{
		/*width: 1120px;*/
		margin: 0 auto;
		padding-top: 40px;
	}
	.contentcenterdata ul li{
		line-height: 52px;
		text-align: center;
	}
	
	#logo_img {
		padding-right:20px;
		max-width:200px;
		myimg:expression(onload=function(){
			this.style.width=(this.offsetWidth > 500)?”500px”:”auto”}
		);
	}
	
</style>
<script>
	var ctx='${ctx}';
	var wsurl=window.location.host+ctx;
	$(function(){
		//var h=$(window).height();
		t=$('#content').offset().top;
		l=$('#content_ul').offset().top;
		//$('#content_ul').height(h-l);
		//$('#content').height(h-t);
		
		

	});
	
	function getDate(strDate) {
           var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
            function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
           return date;
       }
       Date.prototype.Format = function (fmt) { 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
</script>
</head>

<body>

<div class="safeBox">
	<div class="contentBox" id="content">
		<div style="position:absolute; z-index:1">
			<img src="${ctx}/source/images/portal/state_logo.png" id="logo_img"/>
		</div>
		<c:if test="${sessionScope.globle_user.name =='anquanbang' ||sessionScope.globle_user.name =='liuyanzhe' ||sessionScope.globle_user.name =='timelysnow' ||sessionScope.globle_user.name =='test11'}">
		<nav>
			<ul class="clearfix navlist">
				<li class="active">
					<a href="#">攻击态势</a>
					<i></i>
				</li>
				<li>
					<a href="#">数据总览</a>
					<i></i>
				</li>
				<li>
					<a href="#">地域分布</a>
					<i></i>
				</li>
				<li>
					<a href="#">安全趋势</a>
					<i></i>
				</li>
				<li>
					<a href="#">安全关注点</a>
					<i></i>
				</li>
			</ul>
		</nav>
		</c:if>
		
		
		<div class="tabBox">
		<!-- 攻击态势 -->
        <div class="contentcenter not-used" style="display:block">
            <div class="words_map">
                <div class="word" style="width: 100%; height: 600px;">
                <!-- 世界地图背景色 -->
<!--                 	<div class="background-image"><img src="${ctx}/source/attacking/img/body-background.png" alt="background-image" /></div> -->
					<div class="content">
						<div class="data attack-type box gray-bg">
							<div id="attack-type-table-container">
								<table id="attack-type">
									<colgroup>
										<col class="col-type" span="1">
										<col class="col-number" span="1">
									</colgroup>
									<tbody>
										<tr class="second-level">
											<th class="th-title">攻击类型</th>
											<th class="th-title">
												<div class="title-circle">
													<div class="title-triangle"></div>
												</div>
											</th>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						
						<div class="console box gray-bg">
							<div id="console">
								<table id="events">
									<colgroup class="second-level">
										<col class="col-timestamp" span="1">
										<col class="col-border" span="1">
										<col class="col-attacker-org" span="1">
										<col class="col-border" span="1">
										<col class="col-attacker-ip" span="1">
										<col class="col-border" span="1">
										<col class="col-attacked-org" span="1">
										<col class="col-border" span="1">
										<col class="col-attacked-ip" span="1">
										<col class="col-border" span="1">
										<col class="col-service" span="1">
										<col class="col-border" span="1">
										<col class="col-port" span="1">
									</colgroup>
					
									<tbody>
										<tr class="second-level">
											<th class="th-title col-timestamp">时间</th>
											<th class="col-border"></th>
											<th class="th-title col-attacker-org">攻击方</th>
											<th class="col-border"></th>
											<th class="th-title col-attacker-ip">攻击方IP</th>
											<th class="col-border"></th>
											<th class="th-title col-attacked-org">被攻击方</th>
											<th class="col-border"></th>
											<th class="th-title col-attacked-ip">被攻击IP</th>
											<th class="col-border"></th>
											<th class="th-title col-service">攻击类型</th>
											<th class="col-border"></th>
											<th class="th-title col-port">端口</th>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
<!-- 					<script type="text/javascript" src="${ctx}/source/attacking/js/jquery-1.11.0.min.js"></script> -->
					<script type="text/javascript" src="${ctx}/source/attacking/js/d3/d3.min.js"></script>
					<script type="text/javascript" src="${ctx}/source/attacking/js/d3/d3.geo.projection.min.js"></script>
					<script type="text/javascript" src="${ctx}/source/attacking/js/plugins/queue.min.js"></script>
					<script type="text/javascript" src="${ctx}/source/attacking/js/plugins/topojson.min.js"></script>
					<script type="text/javascript">
					var typejson ='${wafEventTypeCount}';
					window.currentId='${currentId}';
					var error = '${error}';
					if(error!=''){
						alert(error);
					}
					</script>
					<script type="text/javascript" src="${ctx}/source/attacking/js/attacking.js"></script>
                </div>
<!--             	<div class="lt" style="width: 200px; height: 200px; border: #1C94C4 solid 1px;">左上的Div</div> -->
<!--             	<div class="lb" style="width: 200px; height: 200px;border: #1C94C4 solid 1px;">左下的Div</div> -->
                        		
       		</div>
        </div>
        、
        <!-- 数据简摘 -->
        <div class="contentcenterdata not-used">
            <ul class="clearfix" style="width:auto">
	            <li class="fl" style="width:600px;height:500px;position:relative; margin-left:40px">
	                <img class="img" src="${ctx}/source/images/rose4.png" style="margin-top:60px;margin-left:108px;"/>
	                <div id="vulnscanAlarmOneHour" ></div>
	            </li>
	            <li class="fl" style="width:600px;height:500px;position:relative;">
	                <img class="img" src="${ctx}/source/images/rose4.png"  style="margin-top:60px;margin-left:108px;"/>
	                <div id="wafOneHour"></div>
	            </li>
	           <li class="fl" style="width:600px;height:atuo;left:17px">
	               <div id="vulnscanAlarmByLevelMonth6" style="width:600px;height:394px"></div>
	           </li>
	           <li class="fl" style="width:600px;margin-left:57px">
	               <div id="wafByLevelMonth6" style="width:600px;height:394px"></div>
	           </li>
           </ul>
                        	
        </div>
        <!-- 地域分布 -->
		<div class="contentcenter not-used">
			<div class="contentLeft fl">
				<ul id="content_ul">
					<li class="active">
						<a href="#">漏洞分布</a>
					</li>
					<li>
						<a href="#">监测数据</a>
					</li>
					<li>
						<a href="#">攻击源分布</a>
					</li>
					<li>
						<a href="#">用户分布</a>
					</li>
					
				</ul>
			</div>
			<div class="contentRight">
                <div id="safe-map" style="display: block;height:600px">
                   <!-- <img src="${ctx}/source/images/safe/u121.png" alt="" /> -->
	        	</div>
	        </div>
		</div>
		
		<!-- 安全趋势 -->
        <div class="contentcenter not-used">
            <div class="point clearfix" style="margin-top:50px">
            	<h5 class="fl" style="color:#7aff75;font-family: sans-serif; font-style: normal; font-size: 14px; font-weight: bold;">风险预警</h5>
                <div class="pont fl">
                <c:if test="${wafAlarmLevel==4}">
                <span>低</span>
                <span>中</span>
                <span>高</span>
                <span>紧急<i ></i></span>
                </c:if>
                <c:if test="${wafAlarmLevel==3}">
                <span>低</span>
                <span>中</span>
                <span>高<i></i></span>
                <span>紧急</span>
                </c:if>
                <c:if test="${wafAlarmLevel==2}">
                <span>低</span> 
                <span>中<i></i></span>
                <span>高</span>	
                <span>紧急</span>		
                 </c:if>
                <c:if test="${wafAlarmLevel==1}">
                <span>低<i></i></span>
                <span>中</span>
                <span>高</span>
                <span>紧急</span>
				</c:if>
                </div>
            </div>
            <div class="exhibitionBox clearfix">
                 <div class="exhibitionList fl"  id="bugMainId"  style="width:1500px;height:520px;background-color:transparent;position:relative;right:100px;">
              	</div>
              	<div class="exhibitionList fl" id="attackMainId"  style="width:1500px;height:520px;background-color:transparent;position:relative;right:100px;">
              	</div>
                        		
            </div>
        </div>
                    	
        <!-- 安全关注点 -->
        <div class="contentcenter not-used">
            <div class="mapListBox clearfix">
            	<div class="ltmit" style="margin-top:25px">
                    <div id="orderServiceTimes" style="width:1330px;height:300px"></div>
                </div>
                <div class="ltmit" style="width:510px;height:400px">
                    <div id="serviceUseInfoMonth6" style="width:510px;height:400px"></div>
                </div>
                <div class="ltmit"  style="width:680px;height:400px;">
                <!-- 内圈为单次服务订单与长期服务订单的对比，外圈为两类订单中各服务订单的对比 -->
				<div id="orderServiceId" style="width:680px;height:400px;"></div>
                </div>

                <div class="ltmit" style="width:580px;height:350px;">
                <!-- 	使用单次服务与长期服务的情况对比 -->
				<div id="serviceUserId" style="width:580px;height:350px;"></div>
                </div>
                <div class="ltmit" style="width:680px;height:350px">
                    <div id="userIndustry" style="width:680px;height:350px"></div>
                </div>
            </div>
        </div>
       </div>
		
	</div>
</div>

<script>
	$(function(){
		/* $('.tab-pane span').click(function(){
		  var index = $(this).index();
		  $(this).addClass('active').siblings().removeClass('active');
		  $('.mapBox .list').eq(index).show().siblings().hide();
		}); */
		$("#vulnscanAlarmOneHour,#wafOneHour").hover(function(){
			$(this).parent("li").find(".img").css({
				"animation":"ancircle 3s infinite linear",
				"-webkit-animation":"ancircle 3s infinite linear",
				"-moz-animation":"ancircle 3s infinite linear",
				"-ms-animation":"ancircle 3s infinite linear",
				"-ms-animation-play-state":"running"
			});
		},function(){
			$(this).parent("li").find(".img").css({
				"animation-play-state":"paused",
				"animation-fill-mode":"forwards",
				"-webkit-animation-play-state":"paused",
				"-webkit-animation-fill-mode":"forwards",
				"-moz-animation-play-state":"paused",
				"-moz-animation-fill-mode":"forwards",
				"-ms-animation-play-state":"paused",
				"-ms-animation-fill-mode":"forwards",
			});
		});
	});
	
</script> 
</body>


</html>
