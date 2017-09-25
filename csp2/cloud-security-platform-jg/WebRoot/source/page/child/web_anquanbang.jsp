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
<script src="${ctx}/source/scripts/order/wafDetail.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
    //首页广告页数据定时刷新,数据不去重
$(function () {
    //showUnreadNews();
    window.setInterval(showUnreadNews,30000);
});
function showUnreadNews()
{
    $.ajax({
        type: "GET",
        url: "${ctx}/getNum.html",
        dataType: "json",
        success: function(data) {
        	$("#num3").html(data.brokenNetwork);
        	$("#num2").html(data.leakNum);
            $("#num1").html(data.webPageNum);
            $("#num").html(data.webSite);
        }
    });
}

function buySefHelpOrder(serviceId){
	//虚拟表单post提交
	var tempForm = document.createElement("form");
  	tempForm.action = "selfHelpOrderInit.html";
  	tempForm.method = "post";
  	tempForm.style.display = "none";
  							
  	var serviceIdInput = document.createElement("input");
  	serviceIdInput.type="hidden"; 
	serviceIdInput.name= "serviceId"; 
	serviceIdInput.value= serviceId; 
	tempForm.appendChild(serviceIdInput);
	
	var indexPageInput = document.createElement("input");
  	indexPageInput.type="hidden"; 
	indexPageInput.name= "indexPage"; 
	indexPageInput.value= 1; 
	tempForm.appendChild(indexPageInput);
							
	document.body.appendChild(tempForm);
	tempForm.submit();
	document.body.removeChild(tempForm);
	
}
function buyWafOrder(serviceId){

    //虚拟表单post提交
	var tempForm = document.createElement("form");
  	tempForm.action = "wafDetails.html";
  	tempForm.method = "post";
  	tempForm.style.display = "none";
  							
  	var serviceIdInput = document.createElement("input");
  	serviceIdInput.type="hidden"; 
	serviceIdInput.name= "serviceId"; 
	serviceIdInput.value= serviceId; 
	tempForm.appendChild(serviceIdInput);
	
	var indexPageInput = document.createElement("input");
  	indexPageInput.type="hidden"; 
	indexPageInput.name= "indexPage"; 
	indexPageInput.value= 1; 
	tempForm.appendChild(indexPageInput);
							
	document.body.appendChild(tempForm);
	tempForm.submit();
	document.body.removeChild(tempForm);            							
                							
}
    //定时30s刷新一次
    //setInterval('showUnreadNews()',20000);
</script>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<!-- <div class="safeL fl">
						<img src="${ctx}/source/images/portal/logo.png" alt=""/>
					</div> -->
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/portal/logo.png" alt="" style="position:relative; top:4px;"/></a>
                          <span style="font-size: 20px;
    color: #4a4a4a; padding-left:10px;position:relative; top:-10px;">网站安全帮</span>
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
			<div class="bannerBox chlid-index">
            <div class="v_show vBox">
				<div class="bannerB">
					<ul class="bannerHeight banner_img clearfix">
					<c:forEach var="list" items="${adList}" varStatus="status">
							<c:if test="${list.id == 8}">
								<li style="display: list-item;">
									<div class="vb data">
									<a href="#"><img src="${ctx}/source/images/ad/${list.image }" alt="" style="width:100%;height:100%">
										<span class="lt">已监测网站<em id="num">${webSite }</em>个</span>
				                        <span class="rt">发现断网<em id="num3">${brokenNetwork }</em>次</span>
				                        <span class="rm">发现<em id="num2">${leakNum }</em>个漏洞</span>
				                        <span class="lm">检测页面<em id="num1">${webPageNum }</em>个</span>
									</a>
									</div>
								</li>
							</c:if>
							<c:if test="${status.index !=0}">
								<li><div class="vb"><a href="#"><img src="${ctx}/source/images/ad/${list.image }" alt="" style="width:100%;height:100%"></a></div></li>
							</c:if>
					</c:forEach>
						
						<!-- <li style="display: list-item;">
							<div class="vb data">
							<a href="#"><img src="${ctx}/source/images/portal/banner4.png" alt="">
								<span class="lt">已监测网站<em id="num">${webSite }</em>个</span>
		                        <span class="rt">发现断网<em id="num3">${brokenNetwork }</em>次</span>
		                        <span class="rm">发现<em id="num2">${leakNum }</em>个漏洞</span>
		                        <span class="lm">检测页面<em id="num1">${webPageNum }</em>个</span>
							</a>
							</div></li>
						<li><div class="vb"><a href="#"><img src="${ctx}/source/images/portal/banner2.png" alt=""></a></div></li>
                        <li><div class="vb"><a href="#"><img src="${ctx}/source/images/portal/banner3.png" alt=""></a></div></li>
                        <li><div class="vb"><a href="#"><img src="${ctx}/source/images/portal/banner1.png" alt=""></a></div></li>
					 	-->
					</ul>
				</div>
             </div>
				<ol class="bannerbtn circle clearfix">
                   <!-- <li class="active"></li>
                    <li></li>
                    <li></li>
                    <li></li> --> 
                    <c:forEach var="list" items="${adList}" varStatus="status">
                    	<c:if test="${status.index ==0}">
							<li class="active"></li>
						</c:if>
						<c:if test="${status.index !=0}">
							<li></li>
						</c:if>
					</c:forEach>
				</ol>
			</div>

		</div>
		<div class="safe02" style="padding-bottom:30px;">
        
        	<div class="imgBox index_child" id='buydiv'>
				<h2>网站安全监测及预警服务</h2>
                <div class="listBox" style="width: 100%;">
                	<div class="listR" style="width:1108px; margin:0 auto;">
                    	<ul class="newlist row newlist-top child-newlist clearfix" style="margin-right:-38px;">
                            <c:forEach var="list" items="${servList}" varStatus="status">
	                          <c:choose>
                               <c:when test="${list.type==1}">
		                            <li class="fl col-md-4">
		                                    <!--<i><img src="imges/HOT.png" alt=""></i>-->
		                                    <p style="width:100%;height:269px;">
		                                    	<img src="${ctx}/source/images/serviceIcon/${list.category_icon }" alt="" style="width:210px;height:auto;margin-top: 35px;margin-left: 62px;">
		                                    </p>
		                                    <h4>${list.name }</h4>
		                                    <span style="right: 30px;">
		                                    	${list.remarks }
		                                    </span>
		                                    <div class="purchase buydiv">
		                                    <c:if test="${!empty list.price}">
		                                    	<strong style="text-decoration:none;" class="buystrong">
		                                    	<fmt:formatNumber type="number" value="${list.price}" maxFractionDigits="2" minFractionDigits="2"/>
		                                    	<em style="font-size: 14px;color: #D00000;">安全币</em>
		                                    	</strong>
		                                    </c:if>
		                                    
		                                   <c:if test="${empty list.price}">
		                                    	<strong style="text-decoration:none;" class="buystrong">
		                                    		0.00<em style="font-size: 14px;color: #D00000;">安全币</em>
		                                    	</strong>
		                                    </c:if>
		                                    	<!--<a href="${ctx}/selfHelpOrderInit.html?serviceId=${list.id }&indexPage=1" class="btn">购买</a>
		                                    -->
		                                    <a href="javascript:;" class="btn buy" onclick="buySefHelpOrder(${list.id });">购买</a>
		                                    </div>
		                               
		                            </li>
		                         </c:when>
		                           
		                       </c:choose>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

        </div>
			
		</div>
		
		        <div class="safe02 service">
        	<div class="imgBox index_child">
				<h2>网站安全防护及加固服务</h2>
                <div class="listBox" style="width: 100%;">
                	<div class="listR" style="width:1108px; margin:0 auto;">
                    	<ul class="newlist child-newlist clearfix" style="margin-right:-40px;">
                            <li class="fl">
                                    <p class="fl"><img src="${ctx}/source/images/portal/fanghu.png" alt=""></p>
                                    <div class="servicelistR fl">
                                    	<h4>云WAF网站安全防护服务</h4>
                                        <span>
                                            	全天候网站保镖，<br>一站式管家服务
                                        </span>
                                        <div class="purchase">
                                           <strong style="display:block">
                                            </strong>
                                             <strong style="display:block; font-size:21px;">
                                             <fmt:formatNumber type="number" value="${wafPrice }" maxFractionDigits="2" minFractionDigits="2"/>
                                             	<em style="font-size: 14px;color: #D00000;">安全币</em>
                                             </strong>
                                            <!--<a href="${ctx}/wafDetails.html?serviceId=6&indexPage=1" class="btn" style="width:80px;">购买</a>
                                        -->
                                         	<a href="javascript:;" class="btn" style="width:80px;" onclick="buyWafOrder(6);">购买</a>
                                        </div>
                                    </div>
                               
                                
                               
                            </li>
                            <li class="fl">
                                    <p class="fl"><img src="${ctx}/source/images/portal/jiagu.png" alt=""></p>
                                    <div class="servicelistR fl">
                                    	<h4>网站加固</h4>
                                        <span>
                                            	不只是打补丁，<br>让网站完美如新
                                        </span>
                                        <div class="purchase">
                                           
                                            <strong style="display:block">
                                             <em style="font-size:20px;font-family: Arial Regular;">199.00</em>
                                             <em style="font-size: 14px;">安全币</em>
                                            </strong>
                                             <strong style="display:block; font-size:30px;">100.00
                                             	<em style="font-size: 14px;color: #D00000;">安全币</em>
                                             </strong>
                                            <a href="#" class="btn" style="width:80px;">购买</a>
                                        </div>
                                    </div>
                                <div class="mask">
                                	<h6>即将上线</h6>
                                </div>
                               
                            </li>
                            <!--<c:forEach  var="list" items="${servList}" varStatus="status">
                            	<c:if test="${list.type == 2 && list.id != 6}">
                            	
	                            <li class="fl">
	                                    <p class="fl">
	                                    	<img src="${ctx}/source/images/serviceIcon/${list.category_icon }" alt="" style="width:220px;height:auto;margin-top: 41px;margin-left: 24px;" />
	                                    </p>
	                                    <div class="servicelistR fl">
	                                    	<h4>${list.name }</h4>
	                                        <span>
	                                            	${list.remarks }
	                                        </span>
	                                        <div class="purchase" style="padding-top:100px;">
	                                           
	                                            <c:if test="${!empty list.price}">
	                                             <strong style="display:block; font-size:30px;">${list.price }
		                                            <fmt:formatNumber type="number" value="${list.price}" maxFractionDigits="2" minFractionDigits="2"/>
			                                    	<em style="font-size: 14px;color: #D00000;">安全币</em>
	                                             </strong>
	                                             </c:if>
	                                             
	                                             <c:if test="${empty list.price}">
			                                    	<strong style="display:block; font-size:30px;">0.00
			                                    	<em style="font-size: 14px;color: #D00000;">安全币</em>
			                                    	</strong>
		                                    	</c:if>
	                                            <a href="javascript:;" class="btn" onclick="buySefHelpOrder(${list.id });" style="width:80px;">购买</a>
	                                        </div>
	                                    </div>
	                               
	                            </li>
	                            </c:if>
                            
                            </c:forEach>-->
                           </ul>
                        </div>
                   </div>
                
                
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


</html>
