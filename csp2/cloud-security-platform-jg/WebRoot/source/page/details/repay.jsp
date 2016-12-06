<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link href="${ctx}/source/css/cashierdesk.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript">
  $(function() {
     //$(".repay_anquan a").on('click', function() {
     //   $(".mark,.repay_tanc").show();
     //});
     $(".tanc_cuo").on('click', function() {
        $(".mark,.repay_tanc").hide();
     });
 });
 
 function collectBalance(){
 var orderListId=$("#orderListId").val();
 	$.ajax({
 		type:"POST",
 		async: false,
 		url:"collectBalance.html",
 		data:{"orderListId":orderListId},
 		dataType: "json", 
 		success:function(data) {
 			if (data.collect == 0){
 				$(".mark,.repay_tanc").show();
 				
 				$(".repay_anquan").children('div').remove();
 				var html='';
				html+='<div>已成功领取5安全币</div>';
				$(".repay_anquan").append(html);
 			} else {
 				alert("系统异常，安全币领取失败！");
 			}
 		},
 		error:function() {
 			alert("系统异常，安全币领取失败！");
 		}
 	});
 }
 
</script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
</head>

<body>
	<!-- 弹窗部分代码 -->
    <div class="mark"></div>
    <div class="repay_tanc">
        <div class="tanc_cuo">X</div>
        <div class="tanc_main">
            
            <p>+5</p>
            <b></b>
        </div>
    </div>
   	<!-- 弹窗部分代码 -->
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px;">收银台</strong>
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
		<div class="dataCent seetlentBox order">
			<div class="seetT">
				
				<div class="seetT">
        
        <!--修改-->
        <div class="step" style="left:740px">
          <!--修改-->
          <p class="step_top">
            <span class="sl">
              <i></i>
            </span>
            <span class="sr" style=" border-top:1px solid #95cfff">
              <i class="r0" style="background:#95cfff"></i>
              <i class="r1" style="background:#2499fb"></i>
            </span>
          </p>
          <p class="step_bottom">
              <em class="sone">1购物车</em>
              <em class="sone" style="left:126px">2填写核对信息</em>
              <c:if test="${paySuccess == 0}">
              <em class="stwo" style="left:280px">3成功提交订单</em>
              </c:if>
              <c:if test="${paySuccess == 1}">
              <em class="stwo" style="left:320px">3支付失败</em>
              </c:if>
          </p>
           </div>
        </div>
			</div>
            <div class="repay_box" style="padding: 20px 2px;">
                 <!-- 成功支付 -->
                 <c:if test="${paySuccess == 0}">
                      <div class="repay_txt"> 
                          
                          <p style="text-align:center;display: inline-block;"><b></b>恭喜您已成功付款<span>${price }</span>安全币！</p>
                      </div>
            	 <div class="repay_contant">
                      <c:if test="${modifyOrderId!= null && !empty modifyOrderId}">
	                      <div class="repay_list">
	                      	部分服务时间已根据订单支付成功时间自动调整为：<fmt:formatDate value="${beginDate }" pattern="yyyy-MM-dd HH:mm:ss"/>，请查看
	                      	<c:forEach items="${modifyOrderId}" var="orderId" varStatus="status">
	                 	  		订单编号：<a href="${ctx}/orderDetailsUI.html?orderId=${orderId }">${orderId }</a>
	                 	  		<c:if test="${!status.last}">,</c:if>
	                 	  	</c:forEach>
	                      	详情。
	                      	</div>
                      	</c:if>
                      <div class="repay_mylist">请在<a href="${ctx}/orderTrackInit.html">我的订单</a>中查看详情</div>
                      <div class="repay_anquan">
                      	<input type="hidden" id="orderListId" value="${orderList.id }" />
	                      <c:if test="${orderList.balanceFlag==0 && orderList.price>=5.00}">
	                      		<div><a href="###" onclick="collectBalance()">点击领取安全币</a> 下单获赠5安全币</div>
	                      </c:if>
	                      <c:if test="${orderList.balanceFlag==1 && orderList.price>=5.00}">
		                      <div>已成功领取5安全币</div>
	                      </c:if>
                      </div>
                 </div>
                 </c:if>
                 <!-- 失败支付 -->
                 <c:if test="${paySuccess == 1}">
                  <div class="repay_fail" >
                      <div class="repay_failtxt" > 
                          <b></b>
                          <p>支付失败 余额不足</p>
                      </div>
                      <div class="repay_faillist">请在<a href="${ctx}/balanceUI.html">个人中心-安全币</a>中查看余额</div>
                      <div class="repay_faillist">关于安全币余额不足问题请联系客服</div>
                    
                 </div>
                 </c:if>
            </div>
            
           
		</div>
        
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
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
<!---执行效果-->
<div class="weixinshow popBoxhide" id="weixin">
	<i class="close chide"></i>
    <div class="Pophead">
    	<h1 class="heaf">安全帮微信二维码</h1>
    </div>
	<div class="popBox">
    	 <p>打开微信，点击右上角的“+”，选择“扫一扫”功能，<br/>
对准下方二维码即可。
		</p>
           <div class="weinImg" style="text-align:center;">
           	<img src="${ctx}/source/images/portal/weixin.jpg" alt=""/>
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</body>


</html>
