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
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px;">购物车</strong>
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
		
		<c:if test="${flag==true}">  
		<div class="dataCent seetlentBox order" style="width:1122px; position:relative; left:-1%;">
			
            <div class="settlement-content">
            	<ul class="settlementList">
                	<li class="listone">
                    	<h3>全部订单</h3>
                    
                    	
                    	  <c:if test="${not empty shopCarList}">
                        <div class="tabox" style=" margin-left:0px; width:1108px; padding:0;padding-top:10px">
                       
                        	<table class="ordertab" width="100%">
                            	<tbody>
                                	<tr>
                                    	<td width="18%">
                                        	
                                            <!--<input type="checkbox" style="display:none">-->
                                            <i class="check-all"></i>
                                            网站安全帮
                                        	
                                        </td>
                                       
                                       <td width="18%">资产内容</td>
                                        <td width="14%">服务时间</td>
                                        <td width="6%">价格</td>
                                        <td width="4%">操作</td>
                                    </tr>
                                </tbody>
                            </table>
                            
                                   	<c:forEach var="shopCar" items="${shopCarList}">
                        	<table class="test-table" width="100%">
                        	<tbody>
                            	
                                     	<tr height="40">
                                     		<td width="20%">
                                     		  <c:if test="${shopCar.status==-1}">
                                           <div style="width: 50px; float: left; vertical-align: middle; margin: 0px 20px;"><font color="red">已失效</font></div>
                                            </c:if>
                                             <c:if test="${shopCar.status!=-1}">
                                             <label style=" margin: 0 20px 0 40px; width:16px; height:16px;display:inline-block;">
                                             <input type="checkbox" class="ck" style="display:none" value="${shopCar.orderId}" isApi="0" name="check_name" />
                                           <i class="chck" style="margin: 0; position:relative; top:0"></i>
                                           </label>
                                            </c:if>
                                            
                                           <c:if test="${shopCar.serviceId!=6}">
                                          
                                            <a href="${ctx}/selfHelpOrderInit.html?serviceId=${shopCar.serviceId}&indexPage=1" target="_blank">${shopCar.serverName}</a>
                                            </c:if>
                                             <c:if test="${shopCar.serviceId==6}">
                                            
                                            <a href="${ctx}/wafDetails.html?serviceId=6" target="_blank">${shopCar.serverName}</a>
                                            </c:if>
                                            </td>
                                    <td width="20%">
                                    <input type="hidden" name="isAPI" value="${shopCar.isAPI}"/>
                                    <c:if test="${fn:contains(shopCar.astName,',')}">   
                                    <c:set value="${fn:split(shopCar.astName, ',')}" var="astName" />
                                    <c:forEach items="${astName}" var="astVal">
							              <p style="padding:5px 0 5px 40px;">
                                            ${astVal}</p>
                                         </c:forEach>
                                   </c:if>
                                     <c:if test="${!fn:contains(shopCar.astName,',')}">   
                                          <p  style="padding:5px 0 5px 40px;">   ${shopCar.astName}</p>
                                   </c:if>
                                    </td>
                                    <td width="29%">
                                 
                                     <p style="text-align: center">
                                     <fmt:formatDate value="${shopCar.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                   
                                     <c:if test="${shopCar.endDate!=null&&shopCar.endDate!=''}">
                                       ~  
                                       <fmt:formatDate value="${shopCar.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                     </c:if>
                                     </p>
                                     
                                    </td>
                                    <td width="10%">
                                      	<em class="price" style="padding:5px 0px 5px 10px">
                                      		<fmt:formatNumber type="number" value="${shopCar.price}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false"/>
                                      	</em>
                                    </td>
                                    <td width="5%" ><a href="#" onclick="delShopCar('${shopCar.orderId}');">删除</a></td>
                                    
                                  </tr>
                                  
                            </tbody>
                        </table>
                        </c:forEach>
                        </div>
                         </c:if>
                        <c:if test="${not empty apiList}">
                        <div class="tabox"  style=" margin-left:0px; width:1108px; padding:0;padding-top:10px">
                        
                        	<table class="ordertab" width="100%">
                            	<tbody>
                                	<tr>
                                    	<td width="18%">
                                        	
                                            <!--<input type="checkbox" style="display:none">-->
                                            <i id="check" class="check-all"></i>  安全能力API
                                        	
                                        </td>
                                       
                                        <td width="18%">数量</td>
                                         <td width="14%">服务时间</td>
                                        <td width="6%">价格</td>
                                        <td width="4%">操作</td>
                                    </tr>
                                </tbody>
                            </table>
                            	<c:forEach var="shopAPI" items="${apiList}">
                        	<table class="test-table" width="100%">
                        	<tbody>
                        	
                            	 <tr height="40">
                                 	<td width="20%">
                                            <c:if test="${shopAPI.status==-1}">
                                           <div style="width: 50px; float: left; vertical-align: middle; margin: 0px 20px;"><font color="red">已失效</font></div>
                                            </c:if>
                                             <c:if test="${shopAPI.status!=-1}">
                                             <label style=" margin: 0 20px 0 40px; width:16px; height:16px;display:inline-block;">
                                             <input type="checkbox" class="ck" style="display:none" value="${shopAPI.orderId}" isApi="1" name="check_name" />
                                           <i class="chck" style="margin: 0; position:relative; top:0"></i>
                                           </label>
                                            </c:if>
                                            
                                          <a href="${ctx}/selfHelpOrderAPIInit.html?apiId=${shopAPI.serviceId}&indexPage=2" target="_blank">${shopAPI.serverName}</a></td>
                                   
                                   
                                    <td width="20%"><p style="padding:5px 0 5px 40px;">
                                   	<!-- 
                                      <c:if test="${shopAPI.pack_type==1}">
                                                                                                                             套餐一
                                      </c:if>
                                       <c:if test="${shopAPI.pack_type==2}">
                                                                                                                             套餐二
                                      </c:if>
                                       <c:if test="${shopAPI.pack_type==3}">
                                                                                                                             套餐三
                                      </c:if>
                                     &nbsp;&nbsp;&nbsp; ${shopAPI.buynum}</p> -->
                                     ${shopAPI.buynum}
                                     </td>
                                   <td width="29%">
                                   <p style="text-align: center">
                                    <fmt:formatDate value="${shopAPI.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/> ~ <fmt:formatDate value="${shopAPI.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                   </p>
                                  </td>
                                  <td width="10%">
                                  	<em class="price" style="padding:5px 0px 5px 10px">
                                  		<fmt:formatNumber type="number" value="${shopAPI.price}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="false" />
                                  	</em>
                                  </td>
                                  <td width="5%"><a href="#" onclick="delShopCar('${shopAPI.orderId}');">删除</a></td>
                                 </tr>
                                
                            </tbody>
                        </table>
                         </c:forEach>
                        </div>
                         </c:if>
                        
                       
                        	<div class="settle" style="width:1108px;">
                                <span>已选择<b>0</b>个订单</span>
                                <span>总价&nbsp;&nbsp;<em style="position:relative; top:-2px;font-size: 14px;">
                                <i id="priceTotal" style="font-size: 26px;font-family: Arial Regular;">0.00</i>&nbsp;安全币
                                </em></span>
                                <button type="button" style="font-family:'微软雅黑'" id="shopBuy">去结算</button>
                            </div>
                     </li>
                </ul>
            </div>
       </div>
	</c:if>
                  <c:if test="${flag==false}">
                    <!--购物车为空显示-->
	            <div class="emptyBox clearfix">
	            	<span class="fl"><img src="${ctx}/source/images/portal/bule-shopping.jpg" alt=""></span>
	                <p class="fl">购物车空空的哦~,去看看心仪的商品吧~<br><a href="${ctx}/index.html"><em style="color:#2499fb">去商品分类查看&gt;</em></a></p>
	            </div>
	           
	           <!--购物车为空显示-->
                    	</c:if>
  
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
