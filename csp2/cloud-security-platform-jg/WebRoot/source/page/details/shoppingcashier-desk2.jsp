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
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/cashierdesk.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript">
$(function(){
    $('.shopCant_Btn').click(function(){
       
        var is=$(this).children('i').hasClass('chekthis');

        if(is==false)
        {
            $(this).children('i').addClass('chekthis');
            
        }else{
            $(this).children('i').removeClass('chekthis');
        }   
    })

 });
 
</script>
<script type="text/javascript">
    function  payConfirm() {
    	var is=$('.shopCant_Btn').children('i').hasClass('chekthis');
		if(is){
			alert("请选择付款方式!");
		}else{
			var orderListId=$("#orderListId").val();
			var pay = $("#pay").val();
		   	var renew=$("#renew").val();
		   	var price=$("#price").val();
		   	var serverName=$("#serverName").val();
		   	if(pay==1){	//安全币
				$.ajax({
					type:"POST",
					async: false, 
					url:"payConfirm.html",
					dataType:"json",
					data:{"orderListId":orderListId,"renew":renew},
					success: function(data) {
						if (data.payFlag == 1){
							alert("您的订单已支付完成，请勿重复支付!");
						} else if(data.payFlag ==2) {
							alert("该订单不存在!");
						} else if(data.payFlag ==3 || data.payFlag ==0) {
						    //余额不足/支付成功	
						    //虚拟表单post提交
						    var tempForm = document.createElement("form");
	  						tempForm.action = "repayUI.html";
	  						tempForm.method = "post";
	  						tempForm.style.display = "none";
	  							
	  						var orderListIdInput = document.createElement("input");
	  						orderListIdInput.type="hidden"; 
							orderListIdInput.name= "orderListId"; 
							orderListIdInput.value= data.orderListId; 
							tempForm.appendChild(orderListIdInput);
								
							var modifyOrderIdInput = document.createElement("input");
	  						modifyOrderIdInput.type="hidden"; 
							modifyOrderIdInput.name= "modifyOrderId"; 
							modifyOrderIdInput.value= data.modifyOrderId; 
							tempForm.appendChild(modifyOrderIdInput); 
																						
							document.body.appendChild(tempForm);
							tempForm.submit();
							document.body.removeChild(tempForm);
	  
						    //var modifyOrderId = data.modifyOrderId;
						    //orderListId = data.orderListId;
							//window.location.href = "repayUI.html?orderListId="+orderListId+"&modifyOrderId="+modifyOrderId;
												        					    						
						} else if (data.payFlag ==5){
							alert("部分订单已失效!");
						}else {
							alert("系统异常,您的订单已加入购物车,请稍后付款!");
			    		    return;
						}
					},
					error:function(data){
						 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
				    		 window.location.href = "loginUI.html"; } 
				    	 else { window.location.href = "loginUI.html"; }
					}
				});
		    }		   	
		    else if(pay==2){	//支付宝
		    	var tempForm = document.createElement("form");
				tempForm.action = "alipaytradepaagepay.html";
				tempForm.method = "post";
				tempForm.style.display = "none";  							  					
				
				var orderListIdInput = document.createElement("input");
				orderListIdInput.type="hidden"; 
				orderListIdInput.name= "orderListId"; 
				orderListIdInput.value= orderListId; 
				tempForm.appendChild(orderListIdInput);				
				
				var priceInput = document.createElement("input");
				priceInput.type="hidden"; 
				priceInput.name= "price"; 
				priceInput.value= price; 
				tempForm.appendChild(priceInput); 
				
				var serverNameInput = document.createElement("input");
				serverNameInput.type="hidden"; 
				serverNameInput.name= "serverName"; 
				serverNameInput.value= serverName; 
				tempForm.appendChild(serverNameInput); 
					
				document.body.appendChild(tempForm);
				tempForm.submit();
				document.body.removeChild(tempForm);
		    }							   	
		}
    }
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
		<input type="hidden" id="pay" value = "${pay }"/>
        <input type="hidden" id="price" value = "${price }"/>
        <input type="hidden" id="serverName" value = "${serverName }"/>
		<div class="dataCent seetlentBox" style="margin-top:20px; padding-top:30px;">
			<div class="seetT">
				<h2>请及时付款</h2>
				<div class="step" style="left:740px">
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
                        <em class="stwo" style="left:280px">3成功提交订单</em>
                    </p>

				</div>
			</div>
			<div class="settlement-content desk" style="margin-bottom:20px;">
            		<h4>交易信息</h4>
                    <div class="deskpay">	
                    	<div class="shoping_center">
                        	<ul>
                                <li>
                                    <b>购买时间</b>
                                    <P><fmt:formatDate value="${orderList.create_date }" pattern="yyyy年MM月dd日HH点mm分"/></P>
                                </li>
                                 <li>
                                 	<table>
                                 	<tr>
                                 	<td valign="top"><b>购买内容</b></td>
                                 	<td>
                                 	 <input type="hidden"  id="renew"  value="${renew }"/>
                                 	<p style="padding-left:4px;">
                                    	<c:forEach var="name" items="${serverName }" varStatus="status">
                                    		${name.key }*${name.value }&nbsp&nbsp&nbsp
                                    		
                                    		<c:if test="${status.count%3==0}">
                                    			<br/>
                                    		</c:if>
                                    	</c:forEach>
                                    </p>
                                 	</td>
                                 	</tr>
                                 	</table>
                                    
                                   <!--   <P>WEB漏洞监测服务 &nbsp WEB漏洞监测能力API &nbsp 套餐1</P> -->
                                </li>
                                 <li>
                                    <b>交易金额</b>
                                    <P>${price }</P>
                                </li>
                                <li>
                                    <b>付款方式</b>
                                    <c:if test="${pay==1 }">
                                    	<P>安全币</P>
                                    </c:if>
                                    <c:if test="${pay==2 }">
                                    	<P>人民币</P>
                                    </c:if>
                                </li>
                            <ul>
                        
                        </div>
                    </div>
                    <div class="shoping_line"></div>
                    <div class="shoping_txt">付款方式</div>
                    <div class="shoping_contant">
                         <div class="shopCant_Btn">
                             <input type="checkbox"class="cklost"  style="display:none" value="" />
                             <i class="chekLost"></i>
                        </div>
                        <div class="shopCant_fix"><i></i>
                        	<c:if test="${pay==1 }">
                        		安全币余额${balance }
                        	</c:if>
                        	<c:if test="${pay==2 }">
                        		支付宝
                        	</c:if>
                        </div>
                        <!-- <div class="shopCant_con">更换其他付款方式</div> -->
                        <div class="shop_pay"><b>支付：</b>${price }
                        	<c:if test="${pay==1 }">
                        		<em style="font-size:14px;line-height:36px">&nbsp;安全币</em>
                        	</c:if>
                        	<c:if test="${pay==2 }">
                        		<em style="font-size:14px;line-height:36px">&nbsp;人民币</em>
                        	</c:if>
                        </div>
                    </div>
            	    
            </div>
            
            <div class="btnFox" style="margin-bottom:200px;">
            	  <input type="hidden" id="orderListId" value = "${orderList.id }"/>
                  <a href="#" onclick="payConfirm()">确认付款</a>
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
