<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script src="${ctx}/source/scripts/order/shopCart.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<style>
	.order .settlement-content .listone .test-table td, .order .ordertab td{text-align:center}
	.order .settlement-content .listone .test-table td{
		font-size:14px;
	}
	
</style>
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
            $(".phone_wrong p").html("请输入11位手机号码");
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
        $(".list-txt").html("联系人信息");
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
		<input type="hidden" id="userIdHidden" value="${user.id}"/>
		<input type="hidden" id="assetIds" value="${assetIds }"/>
		<input type="hidden" id="serviceId" value="${serviceId }"/>
		<input type="hidden" id="orderType" value="${orderType }"/>
		<input type="hidden" id="beginDate" value="${beginDate }"/>
		<input type="hidden" id="endDate" value="${endDate }"/>
		<input type="hidden" id="scanType" value="${scanType }"/>
		
		<input type="hidden" id="time" value="${time }"/>
		<input type="hidden" id="num" value="${num }"/>
		<input type="hidden" id="apiId" value="${apiId }"/>
		<input type="hidden" id="type" value="${type }"/>
		<div class="dataCent seetlentBox order">
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
                                    <input type='text' id="txtName" value="${linkman.name}" maxlength="10">
                                </div>
                                <div class="list_wrong name1"><b></b><p>请输入联系人姓名</p></div>
                                <div class="name_wrong"><b></b><p></p></div>
                            </div>
                            <div class="show_name">
                                <div class="n_txt1">电话：</div>
                                <div class="c_input">
                                    <input type='text' id="txtTel" value="${linkman.mobile }">
                                </div>
                                <div class="list_wrong name2"><b></b><p>请输入联系人电话，便于接收告警短信</p></div>
                                <div class="phone_wrong"><b></b><p></p></div>
                            </div>
                            <div class="show_name">
                                <div class="n_txt1">邮箱：</div>
                                <div class="c_input">
                                    <input type='text' id="txtAdd" value="${linkman.email }">
                                </div>
                                <div class="list_wrong name3"><b></b><p>请输入电子邮箱，便于接受告警邮件</p></div>
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
                        	  <c:if test="${not empty linkman}">
                            	 <tr height="40">
                                 	<td width="18%" style="font-size:14px; padding-left:30px;">
                                    	姓名：<i class="test_name" id="test_name">${linkman.name}</i>
                                    </td>
                                    <td width="24%" style="font-size:14px;">
                                    	电话：<i class="test_iphone">${linkman.mobile }</i>
                                    </td>
                                    <td width="40%" style="font-size:14px;">
                                    	邮箱：<i class="test_add">${linkman.email }</i>
                                    </td>
                                       <td width="16%" style="font-size:14px;"><span class="test-tablespan" style="cursor: pointer;"><b></b>修改</span></td>
                                 </tr>
                                 </c:if>
                            </tbody>
                        </table>
                        
                        </div>
                        <div class="hr" style="margin-top:30px;"></div>
                    </li>
                     <li class="listone">
                    	<h3>支付方式</h3>
                       <div class="clickBox" style="margin-top:20px; margin-left:50px;" id="clickBox">
                       	<!-- <button type="button" class="click">在线支付</button>
                        <button type="button">邮局汇款</button>
                        <button type="button">公司转账</button> -->
                        <button type="button" class="click">安全币</button>
                       </div>
                        <div class="hr"></div>
                    </li>
                    <li class="listone">
                    	<h3>全部订单</h3>
                    	 <a class="fr" style="color:#2499fb;padding-right:54px;" href="${ctx}/showShopCar.html"  style="cursor:hand;">返回购物车</a>
                    	 	 <c:if test="${not empty shopList}">
                        <div class="tabox" style=" margin-left:0px; width:1108px; padding:0;padding-top:10px">
                        <style>
							.order .settlement-content .listone .test-table td, .order .ordertab td{text-align:center}
							.order .settlement-content .listone .test-table td{
								font-size:14px;
							}
                        </style>
                        	<table class="ordertab" width="100%">
                            	<tbody>
                                	<tr align="center">
                                    		<td width="20%">网站安全帮</td>
                                        <td width="30%">资产内容</td>
                                         <td width="35%">服务时间</td>
                                        <td width="15%">价格</td>
                                       
                                    </tr>
                                </tbody>
                            </table>
                            <c:forEach var="shopCar" items="${shopList}">
                        	<table class="test-table" width="100%">
                        	<tbody>
                            	 <tr height="40">
                                 	<td width="20%" style="padding-left:60px;">
                                 	  <input type="hidden" name="orderId" value="${shopCar.orderId}"/>
                                         <a href="${ctx}/selfHelpOrderInit.html?serviceId=${shopCar.serviceId}&indexPage=1" target="_blank">${shopCar.serverName}</a>
                                         </td>
                                    <td width="30%"> 
	                                    <c:if test="${fn:contains(shopCar.astName,',')}">   
	                                    <c:set value="${fn:split(shopCar.astName, ',')}" var="astName" />
	                                    <c:forEach items="${astName}" var="astVal">
								              <p>
	                                            ${astVal}</p>
	                                         </c:forEach>
	                                   </c:if>
	                                     <c:if test="${!fn:contains(shopCar.astName,',')}">   
	                                          <p>   ${shopCar.astName}</p>
	                                   </c:if>
                                   
                                   </td>
                                    <td width="35%">
                                     <fmt:formatDate value="${shopCar.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                   
                                     <c:if test="${shopCar.endDate!=null&&shopCar.endDate!=''}">
                                       -   
                                       <fmt:formatDate value="${shopCar.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                     
                                     </c:if>
                                    </td>
                                    <td width="15%">
                                    	<em class="price">
                                    	<fmt:formatNumber type="number" value="${shopCar.price}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false"/>
                                    	</em>
                                    </td>
                                    
                                 </tr>
                                 
                            </tbody>
                        </table>
                        </c:forEach>
                        </div>
                        </c:if>
                       <c:if test="${not empty shopAPIList}">
                        <div class="tabox" style=" margin-left:0px; width:1108px; padding:0;padding-top:10px">
                           <c:forEach var="shopAPI" items="${shopAPIList}">
                        	<table class="test-table" width="100%">
                                <tbody>
                                     <tr height="40">
                                        <td width="20%" style="padding-left:60px;">
                                               <input type="hidden" name="orderId" value="${shopAPI.orderId}"/>
                                       <a href="${ctx}/selfHelpOrderAPIInit.html?apiId=${shopAPI.serviceId}&indexPage=2" target="_blank">${shopAPI.serverName}</a>
                                       </td>
                                        <td width="30%"><p>
                                       <!--   <c:if test="${shopAPI.astName==1}">
                                                                                                                             套餐一
                                      </c:if>
                                       <c:if test="${shopAPI.astName==2}">
                                                                                                                             套餐二
                                      </c:if>
                                       <c:if test="${shopAPI.astName==3}">
                                                                                                                             套餐三
                                      </c:if>--> 
                                        ${shopAPI.buynum}
                                        
                                        </p></td>
                                        <td width="35%">
                                        <fmt:formatDate value="${shopAPI.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                         - <fmt:formatDate value="${shopAPI.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                         </td>
                                        <td width="15%">
	                                        <em class="price">
	                                        	<fmt:formatNumber type="number" value="${shopAPI.price}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false"/>
	                                        </em>
                                        </td>
                                        
                                     </tr>
                                     
                                </tbody>
                            </table>
                           </c:forEach>
                        </div>
                      </c:if>   
                        
                        <div class="hr" style="margin-top:30px;"></div>
                    </li>
                      <!-- 
                     <li class="listone">
                    	<h3>发票信息</h3>
                       <div class="clickBox" style="margin-top:20px; margin-left:50px;">
                       	<button type="button" class="click">索要发票</button>
                        
                       </div>
                       <div class="invoiceshow" style="margin-top:20px; margin-left:50px;">
                       		<span class="invoice-one" style="margin-right:16px;">普通发票（纸质）</span>
                            <span class="introduce-two">中国电信股份有限公司北京研究院</span>
                            <span class="introduce-three"style="margin-left:22px;">技术服务</span>
                            <a href="javascript:;" class="modify" style="margin-left:45px; color:#2499fb;">修改</a>
                       </div>
                     	<div class="invoicehide modifyBox"  style="display:none;">
                        	<div class="invoiclist">
                            	<label>发票抬头</label>
                                <input type="text" class="text textvalue" style="width:278px;">
                            </div>
                            <div class="invoiclist">
                            	<label>发票类型</label>
                                <select class="select" style="width:198px; height:36px;">
                                	<option value="请选择">请选择</option>
                                    <option value="纸质">纸质</option>
                                    <option value="电子">电子</option>
                                </select>
                            </div>
                            
                            <div class="btnBox clearfix" style="text-align:left">
                            	<button type="button fl" class="btn preservation" style=" width:120px; margin-right:20px;">保存发票信息</button>
                                <button type="button fl" class="btn cancel" style="width:80px;">取消</button>
                            </div>
                        </div> 
                        <div class="hr"></div>
                        
                        <div class="Coupon">
                        	<label>使用优惠劵</label>
                            <select>
                            	<option>可用优惠劵</option>
                                <option>可用优惠劵</option>
                                <option>可用优惠劵</option>
                            </select>
                        </div>
                    </li> -->
                </ul>
            
            </div>
            
            <ul class="Price">
            	<li>
                	<i>${orderNum}</i>个订单，总额：<span>${shopCount}<em>&nbsp;安全币</em></span>
                </li>
                   <!--  
                <li>
                	优惠劵：<span>￥0.00</span>
                </li>
                -->
                <li>
                       应付总额：
                    <span>${shopCount}<em>&nbsp;安全币</em></span>
                </li>
            </ul>
			<div class="SubmitBox">
			<input type="hidden" id="countPrice" value="${shopCount}"/>
            	<p>应付总额：
            	<span style="padding-bottom:10px;">${shopCount}<em style="font-size:14px">&nbsp;安全币</em></span>
            	<c:if test="${shop eq '0' }">
            		<input id="shopSettlement" class="submit" type="button" value="提交订单" style="cursor:pointer"/>
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
