<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>安全帮-中国电信云安全服务在线商城</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>

<script src="${ctx}/source/scripts/order/details.js"></script>
<script src="${ctx}/source/scripts/order/wafDetail.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
  $(function() {
     $(".test-tablespan").on('click', function() {
        $(".ta-box").hide();
        $(".listone_box").show();
        $(".list-txt").html("修改联系人信息");

     });

     $("#ad-tj").on('click', function() {
     		//姓名
     		$(".name1").show();
        	$(".name_wrong p").html("");
            $(".name_wrong").hide();
            //手机号码
            $(".name2").show();
            $(".phone_wrong p").html("");
            $(".phone_wrong").hide();
            //邮箱地址
            $(".name3").show();
            $(".add_wrong p").html("");
            $(".add_wrong").hide();
     

        var userName = $("#txtName").val();
        var userAdd = $("#txtAdd").val();
        var mobile = $("#txtTel").val();
        
        if (userName == "") {
            $(".name1").hide();
            $(".name_wrong").show();
            $(".name_wrong p").html("请输入您的姓名");
            return false;
        };

        if (mobile == "") {
            $(".name2").hide();
            $(".phone_wrong").show();
            $(".phone_wrong p").html("请输入您的电话");
            return false;
        };

        var myreg = /^1[3|4|5|7|8][0-9](\d{8})$/;
        if (!(myreg.test(mobile))) {
            $(".name2").hide();
            $(".phone_wrong").show();
            $(".phone_wrong p").html('请输入有效的手机号码！');
            return false;
        };
        if (mobile.length != 11) {
            $(".name2").hide();
            $(".phone_wrong").show();
            $(".phone_wrong p").html("请输入11位手机号码!");
            return false;
        };
        //if (userAdd == "") {
        //    $(".name3").hide();
        //    $(".add_wrong").show();
        //    $(".add_wrong p").html("请输入您的邮箱地址!");
        //    return false;
        //};
        var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        if (userAdd!="" && !emailReg.test(userAdd)) {
            $(".name3").hide();
            $(".add_wrong").show();
            $(".add_wrong p").html("请输入正确的邮箱地址!");
            return false;
        };

        $(".listone_box").hide();
        $(".ta-box").show();
        $(".list-txt").html("联系人信息!");
        $(".test_name").html(userName);
        $(".test_iphone").html(mobile);
        $(".test_add").html(userAdd);
            
    });


      $("#cd-tj").on('click', function() {
        $(".listone_box").hide();
        $(".ta-box").show();
        $(".list-txt").html("联系人信息");
        $(".test_name").html(userName);
        $(".test_iphone").html(mobile);
        $(".test_add").html(userAdd);

     });

  });  
</script>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px;">结算页</strong>
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
		<c:if test="${orderDetail.serviceId le 5 }">
			<form method="post" action="orderBack.html" id="orderBackForm">
		</c:if>
		<c:if test="${orderDetail.serviceId gt 5 }">
			<form method="post" action="wafOrderBack.html" id="orderBackForm">
		</c:if>
		<input type="hidden" id="userIdHidden" name="userIdHidden" value="${user.id}"/>
		<input type="hidden" id="orderDetailId" name="orderDetailId" value="${orderDetail.id}"/>
		<input type="hidden" id="assetIds" name="assetIds" value="${orderDetail.asstId}"/>
		<input type="hidden" id="assetAddr" name="domainName" value="${orderDetail.assetAddr}"/>
          <input type="hidden" id="orderType" name="orderType" value="${orderDetail.type}"/>
		<input type="hidden" id="ipArrayHidden" name="ipArray" value="${orderDetail.ipArray }"/>
		<input type="hidden" id="timesHidden" name="times" value="${times }"/>
			<input type="hidden" id="orderId" name="orderId" value="${orderId }"/>
	       <input type="hidden" id="orderListId" name="orderListId" value="${orderListId }"/>
		      <input type="hidden"  id="renew"  value="renew"/>
		<input type="hidden" name="indexPage" value="1"/>
		</form>
		<div class="dataCent seetlentBox">
			<div class="seetT">
				<h2>填写并核对订单信息</h2>
				<div class="step">
					<p class="step_top">
						<span class="sl">
							<i></i>
						</span>
						<span class="sr">
							<i class="r0"></i>
							<i class="r1"></i>
						</span>
					</p>
                    <p class="step_bottom">
                    	<em class="sone">1购物车</em>
                        <em class="stwo">2填写核对信息</em>
                        <em class="sthree">3成功提交订单</em>
                    </p> 

				</div>
			</div>
            <div class="settlement-content" style="padding: 20px 2px;">
            	<ul class="settlementList">
                	<li class="listone">
                    	<div style=" clear:both;overflow: hidden;">
                            <h3 class="list-txt" style="float:left">联系人信息</h3>
                            <!--  <div class="listone_add"><b></b><span>新增联系人</span></div>-->
                        </div>
                        <div class="listone_box">
                            <div class="show_name">
                                <div class="n_txt1">姓名：</div>
                                <div class="c_input">
                                    <input type='text' id="txtName" value="${user.name }" maxlength="10">
                                </div>
                                <div class="list_wrong name1"><b></b><p>请输入联系人姓名</p></div>
                                <div class="name_wrong"><b></b><p></p></div>
                            </div>
                            <div class="show_name">
                                <div class="n_txt1">电话：</div>
                                <div class="c_input">
                                    <input type='text' id="txtTel" value="${user.mobile }">
                                </div>
                                <div class="list_wrong name2"><b></b><p>请输入联系人电话，便于接收告警短信</p></div>
                                <div class="phone_wrong"><b></b><p></p></div>
                            </div>
                            <div class="show_name">
                                <div class="n_txt1">邮箱：</div>
                                <div class="c_input">
                                    <input type='text' id="txtAdd" value="${user.email }">
                                </div>
                                <div class="list_wrong name3"><b></b><p>请输入电子邮箱，便于接收告警邮件</p></div>
                                <div class="add_wrong"><b></b><p></p></div>
                            </div>
                            <div class="ad-tj">
                                <a href="javascript:;" id="ad-tj">提交信息</a>
                             </div>
                          <div class="cd-tj">
                                <a href="javascript:;" id="cd-tj">返回</a>
                             </div>
                        </div> 
                      
                        <div class="tabox ta-box" style="margin-left: 0px; width:1108px;">
                        	<table class="test-table mrn" width="100%">
                        	<tbody>
                        	 
                            	 <tr height="40">
                                 	<td width="18%" style="font-size:14px; padding-left:30px;">
                                    	姓名：<i class="test_name">${user.name }</i>
                                    </td>
                                    <td width="24%" style="font-size:14px;">
                                    	电话：<i class="test_iphone">${user.mobile }</i>
                                    </td>
                                    <td width="40%" style="font-size:14px;">
                                    	邮箱：<i class="test_add">${user.email }</i>
                                    </td>
                                       <td width="16%" style="font-size:14px;"><span class="test-tablespan" style="cursor: pointer;"><b></b>修改</span></td>
                                 </tr>
                               
                            </tbody>
                        </table>
                        
                        </div>
                        <div class="hr" style="margin-top:30px;"></div>
                    </li>
                    <li class="listone">
                    	<h3>支付方式</h3>
                       <div class="clickBox" style="margin-top:20px; margin-left:50px;" id="clickBox">
                        <button type="button" class="click">安全币</button>
                       </div>
                        <div class="hr"></div>
                    </li>
                    <li class="listone ">
                    <div class="clearfix">
                    	<h3 class="fl">订单详情</h3>
                    	
					<input type="hidden" id="serviceName" value="${service.name }"/>
                    </div>
                    	
                        <div class="tabox zfize" style="margin-top:20px;">
                        	<table class="test-table" style="width:966px;">
                        	<c:if test="${not empty orderDetail}">
                        	   
	                        	<tbody>
	                            	 <tr height="40">
	                                 	<td width="16%" style="font-size:14px;">
	                                    	${orderDetail.serviceName}
	                                    </td>

	                                    <td width="20%" style="font-size:14px;">
	                                    	${orderDetail.assetName}
	                                    </td>
	                             <td width="10%" >
                                               <select class="text select"  id="Remonth"   onchange="changeOrderInfo()">
						                                <option value="-1">请选择</option>
						                                <option value="1">1个月</option>
						                                <option value="2">2个月</option>
						                                <option value="3">3个月</option>
						                                 <option value="4">4个月</option>
						                                  <option value="5">5个月</option>
						                                  <option value="6">6个月</option>
						                                  <option value="7">7个月</option>
						                                   <option value="8">8个月</option>
						                                   <option value="9">9个月</option>
						                                    <option value="10">10个月</option>
						                                    <option value="11">11个月</option>
						                                       <option value="12">12个月</option>
					                              </select>
			                              </td>
	                                    <td width="30%" style="font-size:14px;" id="orderDate" >
	                                    	<fmt:formatDate value="${orderDetail.begin_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                    	<c:if test="${orderDetail.end_date!=null&&orderDetail.end_date!=''}">
	                                    	~<fmt:formatDate value="${orderDetail.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                    	<input type="hidden" id="endDate" value=""/>
	                                    	<input type="hidden" id="beginDate" value=""/>
	                                    	</c:if>
	                                    </td>
	                                    <td width="18%" style="font-size:14px;" name="orderPrice">
	                                    	<fmt:formatNumber type="number" value=" ${orderDetail.price}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false"/>
	                                    </td>
	                                 </tr>
	                            </tbody>
	                         
                           </c:if>
                           
                        </table>
                        
                        </div>
                    </li>
                 
                </ul>
            
            </div>
            <c:if test="${not empty orderDetail}">
            <ul class="Price">
            	<li>
                	<i>1</i>个订单，总额：
                	<span>
                	<em name="orderPrice">
                		<fmt:formatNumber type="number" value=" ${orderDetail.price}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false"/>
                		</em>
                		<em>&nbsp;安全币</em>
                	</span>
                </li>
             
                <li>
                	应付总额：
                	<span>
                	<em name="orderPrice">
                		<fmt:formatNumber type="number" value=" ${orderDetail.price}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false"/>
                		</em>
                		<em>&nbsp;安全币</em>
                	</span>
                </li>
            </ul>
            </c:if>
          
			<div class="SubmitBox">
			  <c:if test="${not empty orderDetail}">
            	<p>应付总额：
            	<span style="padding-bottom:10px;" >
            		<em name="orderPrice">
	            	<fmt:formatNumber type="number" value=" ${orderDetail.price}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false"/>
	            	</em>
	            	<em style="font-size:14px">&nbsp;安全币</em>
            	</span>
            	</c:if>
            	
            	<c:if test="${not empty orderDetail}">
	                <input id="settlementRenewWaf" class="submit" type="button" value="提交订单"/>
		      </c:if>
		         </p>
            </div>
		</div>
        
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<!--修改-->
				   <a href="${ctx}/index.html">
		               <img src="${ctx}/source/images/portal/new-footer-logo.png" alt="" />
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
<script>
$(function(){	
	$('#carrousel').hover(function(){
			$('.left-arrow').fadeIn();
			$('.right-arrow').fadeIn();
		},function(){
			$('.right-arrow').fadeOut();
			$('.left-arrow').fadeOut();
		})
	
	$(".fl-pic").slidelf({
			"prev":"left-arrow",
			"next":"right-arrow",
			"speed":300 //时间可以任意调动  以毫秒为单位
		});
		
	})
	
</script>
<script>
	var w=$(document).width();
	$('.bannerHeight').width(w);
	
	
            	
</script>

</html>
