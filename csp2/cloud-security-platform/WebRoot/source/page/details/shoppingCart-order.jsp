<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<div class="safeL fl" style="width:270px; margin-right:18%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px;">购物车</strong>
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html">首页</a></span>
						
						<!-- 商品分类 start -->
						<c:import url="/category.html"></c:import>
						<!-- 商品分类 end -->
						
						<span class="fl"><a href="#">手机APP</a></span>
						<span class="fl"><a href="${ctx}/aider.html">帮助</a></span>
						
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
			

		</div>
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
                                       
                                        <td width="24%">资产内容</td>
                                        <td width="6%">价格</td>
                                        <td width="7%">操作</td>
                                    </tr>
                                </tbody>
                            </table>
                        	<table class="test-table" width="100%">
                        	<tbody>
                            	
                                   	<c:forEach var="shopCar" items="${shopCarList}">
                                     	<tr height="40">
                                     		<td width="20%"><label >
                                            <input type="checkbox" class="ck" style="display:none" value="${shopCar.orderId}" isApi="0" name="check_name"><i class="chck"></i>
                                            </label><a href="${ctx}/selfHelpOrderInit.html?serviceId=${shopCar.serviceId}&indexPage=1">${shopCar.serverName}</a>
                                            </td>
                                    <td width="36%"><p style="padding-left:40px;">
                                     <input type="hidden" name="isAPI" value="${shopCar.isAPI}"/>
                                    ${shopCar.astName}</p></td>
                                    <td width="8%"><em class="price">${shopCar.price}</em></td>
                                    <td width="9%"><a href="#" onclick="delShopCar('${shopCar.orderId}');">删除</a></td>
                                  </tr>
                                    </c:forEach>
                              
                                 
                            </tbody>
                        </table>
                      
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
                                       
                                        <td width="24%">数量</td>
                                        <td width="6%">价格</td>
                                        <td width="7%">操作</td>
                                    </tr>
                                </tbody>
                            </table>
                        	<table class="test-table" width="100%">
                        	<tbody>
                        		<c:forEach var="shopAPI" items="${apiList}">
                            	 <tr height="40">
                                 	<td width="20%"><label >
                                            <input type="checkbox" class="ck" style="display:none" value="${shopAPI.orderId}" isApi="1" name="check_name"><i class="chck"></i>
                                         
                                            </label>
                                          <a href="${ctx}/selfHelpOrderAPIInit.html?apiId=${shopAPI.serviceId}&indexPage=2">${shopAPI.serverName}</a></td>
                                    <td width="36%"><p style="padding-left:40px;">
                                    ${shopAPI.num}</p></td>
                                    <td width="8%"><em class="price">${shopAPI.price}</em></td>
                                    <td width="9%"><a href="#" onclick="delShopCar('${shopAPI.orderId}');">删除</a></td>
                                 </tr>
                                  </c:forEach>
                                   	
                                 
                            </tbody>
                        </table>
                       
                        </div>
                         </c:if>
                        
                       
                        	<div class="settle" style="width:1108px;">
                                <span>已选择<b>0</b>个订单</span><span>总价 <em style="position:relative; top:-2px;">¥<i id="priceTotal" style="font-size: 26px;
    font-family: Arial Regular;">00.00</i></em></span><button type="button" style="font-family:'微软雅黑'" id="shopBuy">去结算</button>
                            </div>
                        
                        

                    </li>
                </ul>
            	
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
                        	<dd><a href="#">购物指南</a></dd>
                            <dd><a href="#">在线帮助</a></dd>
                            <dd><a href="#">常见问题</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关于安全帮</h2>
                        <dl>
                        	<dd><a href="#">了解安全帮</a></dd>
                            <dd><a href="#">加入安全帮</a></dd>
                            <dd><a href="#">联系我们</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd><a href="#">QQ交流群<br>470899318</a></dd>
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
           	<img src="${ctx}/source/images/portal/weixin.png" alt="">
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
