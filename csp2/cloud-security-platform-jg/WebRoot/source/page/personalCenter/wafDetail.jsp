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
<title>我的订单-告警详情</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet" />	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>

<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/wafPic.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/wafDownloadDetail.js"></script>

<script type="text/javascript">
$(function() {
     $(".data_cuo").on('click', function() {
        $(".mark,.data_tanc").hide();
     });
     
     $(".data_btn").on('click', function() {
        $(".mark,.data_tanc").hide();
     });
 });
function websecDetail(logId){
     $.ajax({
       type: "POST",
       url: "warningWafDetail.html",
       data: {"logId":logId},
       dataType:"json",
       success: function(data){
      		$("#dstIp").html(data.dstIp);
      		$("#srcIp").html(data.srcIp);
      		$("#srcPort").html(data.srcPort);
      		$("#alertlevel").html(data.alertlevel);
      		$("#eventType").html(data.eventType);
      		$("#statTime").html(data.statTime);
      		$("#alertinfo").html(data.alertinfo);
      		$("#protocolType").html(data.protocolType);
      		
      		$(".mark,.data_tanc").show();
      	}
     });
}
</script>

</head>
<style type="text/css">
   .data_box{ width: 1180px; margin-top: 20px;margin-bottom: 60px; }
   .data_nav{ height: 38px; border-bottom: 1px solid #cfcfcf; margin-bottom: 10px;} 
   .data_nav ul li { width: 160px; float:left; text-align: center; line-height: 38px; height: 38px; font-size: 16px; border: 1px solid #a6a6a6;  border-bottom: 0px; border-radius: 10px 10px 0 0 ;}
   .data_min{clear: both; overflow: hidden; }
   .data_1{ width:382px; border:1px solid #cbcbcb; height: 380px; float: left; margin-right: 13px;}
   .data_table{ width: 1138px; height: 360px; border:1px solid #cbcbcb; margin-top: 15px; padding: 20px;}
   .data_table_tab {margin-bottom: 20px; width: 100%; }
   .data_table_tab tr th{ border-bottom: 1px solid #000;height: 40px; text-align: left;} 
   .data_table_tab tr td{ height: 40px; background: #f0f8fa;} 
   .data_table_cont a{ color: #00bfff;}
   .mark{ width: 100%; 
    height: 100%;
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    background: #000;
    filter: alpha(opacity=40);
    opacity: 0.4;
    z-index: 400;}
   .data_tanc{ width: 680px; height: 355px;  position: fixed; z-index: 500; top: 50%; margin-top: -202px; left: 50%; margin-left:-340px; background: #fff;  border: 1px solid #787878; display: none; }
   .data_tanctop{ height: 46px; border-bottom: 1px solid #787878; background: #3c85db;}
   .data_tanctop h2{ color: #fff; font-size: 18px; line-height: 46px; padding-left: 30px; width: 200px; float: left;}
   .data_cuo{ width: 21px; height: 21px; float: right; background: url(${ctx}/source/images/dailog-close.png); margin-right: 10px; margin-top: 10px; cursor: pointer;}
   .data_tancmain{ padding: 10px 20px; }
   .tancmain_table{  width: 100%;}
   .tancmain_table tr th{ border: 1px solid #787878; height: 28px; text-align: center;} 
   .tancmain_table tr td{ height: 28px; font-size: 14px; line-height: 29px;} 
   .data_btn{ width: 145px; height: 30px; line-height: 30px; font-size: 16px; text-align: center; background: #cdcdcd; border-radius: 10px; margin:auto; color: #323232; cursor: pointer;}
   .nodata{
	    text-align: center;
	    margin: auto;
	    padding-top: 100px;
	}
   

</style>

<body>

  <div class="data_tanc">
     <div class="data_tanctop">
       <h2>事件详情</h2>
       <div class="data_cuo"></div>
     </div>
     <div class="data_tancmain">
       <table class="tancmain_table" width="100%">
              <tbody>
                 <tr>
                     <th width="25%">防护对象</th>
                     <th width="75%" id="dstIp" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">服务器IP</th>
                     <th width="75%" id="srcIp" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">服务器端口</th>
                     <th width="75%" id="srcPort" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">风险级别</th>
                     <th width="75%" id="alertlevel" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">告警类型</th>
                     <th width="75%" id="eventType" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">告警发生时间</th>
                     <th width="75%" id="statTime" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">告警信息</th>
                     <th width="75%" id="alertinfo" style="text-align:left"></th>
                                     
                 </tr>
                  <tr>
                     <th width="25%">HTTP请求或者相应信息</th>
                     <th width="75%" id="protocolType" style="text-align:left"></th>
                                     
                 </tr>
                
              </tbody>
            </table>
     </div>
     <div class="data_btn">关闭</div>
  </div>

	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        
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
		<div class="dataCent seetlentBox order" style="width: 1156px;">
		<form id="exportForm" action="${ctx}/exportWAF.html" method="post">
       <div class="data_box">
        <div class="data_nav">
          <input type="hidden" value="${order.id }" id="orderId" name="orderId"/>
          <ul>
            <li>实时数据</li>
          </ul>
          <p align='right'><span>下载Word报表&nbsp;</span>
						<span><a href="javascript:void(0)" onclick="exportImgWAF()" ><img src="${ctx}/source/images/export.png" width="22" height="23"/>
						</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		</p>
        </div> 
        <div class="data_min">
        	<c:if test="${websecNum>0}">
             <div class="data_1" id="levelPie"></div>
             <div class="data_1" id="eventBar"></div>
             <div class="data_1" style="margin-right:0px;" id="eventPie"></div>
            </c:if>
            <c:if test="${websecNum==0}">
             <div class="data_1"><div class="nodata"><img src="${ctx}/source/images/waf_nodata.png"/></div></div>
             <div class="data_1"><div class="nodata"><img src="${ctx}/source/images/waf_nodata.png"/></div></div>
             <div class="data_1" style="margin-right:0px;"><div class="nodata"><img src="${ctx}/source/images/waf_nodata.png"/></div></div>
            </c:if>
        </div>
        <div class="data_table">
            <c:if test="${websecNum!=0}">
	            <table class="data_table_tab" width="100%">
	              <tbody>
	                 <tr>
	                     <th width="30%">开始时间</th>
	                     <th width="30%">告警类型</th>
	                     <th width="25%">服务器IP地址</th> 
	                     <th width="15%">风险级别</th>                     
	                 </tr>
	              </tbody>
	             </table>
	             <div style="overflow:auto;height:312px;width:1138px">
	             <table class="data_table_tab" width="100%">
	              <tbody>
	                 <c:forEach var="list" items="${websecList}" varStatus="sta">
	                 	 <input type="hidden" id="logIdHidden" value="${list.logId}">
		                 <tr>
		                     <td width="30%">${list.statTime }</td>
		                     <td width="30%" class="data_table_cont"><a href="javascript:void(0)" onclick="websecDetail(${list.logId })">${list.eventType }</a></td>
		                     <td width="25%">${list.dstIp }</td> 
		                     <td width="13%">
			                     <c:if test="${list.alertlevel eq 'LOW'}">低风险</c:if>
				                 <c:if test="${list.alertlevel eq 'MEDIUM'}">中风险</c:if>
			                     <c:if test="${list.alertlevel eq 'HIGH'}">高风险</c:if>
		                     </td>                     
		                 </tr>
	                 </c:forEach>
	              </tbody>
	            </table>
	            </div>
            </c:if>
            <c:if test="${websecNum==0}">
	            	<div class="nodata"><img src="${ctx}/source/images/waf_nodata.png"/></div>
            </c:if>
        </div>
       </div>
       </form>        
		</div>
        
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="${ctx}/index.html">
						<img src="${ctx}/source/images/portal/new-footer-logo.png" alt="">
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


</html>
