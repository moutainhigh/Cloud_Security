<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>个人中心</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript">
function orderTrack(state, list_group){
	//虚拟表单post提交
	var tempForm = document.createElement("form");
  	tempForm.action = "orderTrackInit.html";
  	tempForm.method = "post";
  	tempForm.style.display = "none";
  							
  	var stateInput = document.createElement("input");
  	stateInput.type="hidden"; 
	stateInput.name= "state"; 
	stateInput.value= state; 
	tempForm.appendChild(stateInput);
							
	var listGroupInput = document.createElement("input");
  	listGroupInput.type="hidden"; 
	listGroupInput.name= "list_group"; 
	listGroupInput.value= list_group; 
	tempForm.appendChild(listGroupInput); 
							
	document.body.appendChild(tempForm);
	tempForm.submit();
	document.body.removeChild(tempForm);
}
//删除
function deleteOrder(orderId,begin_date){
    if (window.confirm("确实要删除吗?")==true) {
    	$.ajax({
            type: "POST",
            url: "checkOrderStatus.html",
            data: {"orderId":orderId,"begin_date":begin_date},
            dataType:"json",
            success: function(data){
                if(!data.status){
                    alert("订单正在执行,不可以删订单!");
                }else{
                	//window.location.href="userdeleteOrder.html?orderId="+orderId;
                	//虚拟表单post提交
					var tempForm = document.createElement("form");
  					tempForm.action = "userdeleteOrder.html";
  					tempForm.method = "post";
  					tempForm.style.display = "none";
  							
  					var orderIdInput = document.createElement("input");
  					orderIdInput.type="hidden"; 
					orderIdInput.name= "orderId"; 
					orderIdInput.value= orderId; 
					tempForm.appendChild(orderIdInput);
							
					document.body.appendChild(tempForm);
					tempForm.submit();
					document.body.removeChild(tempForm);
                }
            },
         });
    } else {
        return;
    }
}
//今日未签到时，签到按钮添加click事件
$(document).ready(function(){
  var signFlg = ${requestScope.signIn};
  if (!signFlg) {
  	$(".qdbtn").click(signIn);
  }
});

function signIn(){
	$.ajax({ type: "POST",
    		 url: "signIn.html", 
    		 success: function(data) {
	    		 	 if(data.collect == 0){
	    		 	 	alert("系统异常，请稍后领取~~");
	    		 	 }else if(data.collect == 1){
    		    		alert("今日金额已经领取，不能重复领取！");  
			    	 }else if(data.collect==2) {
			    	 	//签到成功
			    	 	//移除click事件
			    	 	$('.qdbtn').unbind("click",signIn);
			    	 	
			    	 	//签到可获得10安全币-->“今日已签到”字样
			    	 	$('.qdbtn').children('b').remove();
						var projectName=window.document.location.pathname;
						projectName=projectName.substring(0,projectName.substr(1).indexOf('/')+1);
						//html+='<b style="padding-left:10px;"><i style="width:auto;padding-right: 8px;"><img src="/cloud-security-platform/source/images/balance/minig.png" alt=""></i>今日已签到</b>';
						var html='';
						html+='<b style="padding-left:10px;"><i style="width:auto;padding-right: 8px;"><img src="'+projectName+'/source/images/balance/minig.png" alt=""></i>今日已签到</b>';
						$('.qdbtn').append(html);
						$('.succeed').fadeIn(500);
						$('.succeed').fadeOut(2000);
						
						//"安全币余额"的 显示改变	
						$('.b_aic').children('em').remove();
						html = '<em>'+data.balance+'</em>';
						$('.b_aic').append(html);
			    	 }
    		    	 }, 
    		  error: function(data){ 
    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
    		    		 window.location.href = "loginUI.html"; } 
    		    	 else { window.location.href = "loginUI.html"; } } 
    	});
}

function buySelfHelpOrder(serviceId){
	
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
function buyAPI(apiId){
		var tempForm = document.createElement("form");
  		tempForm.action = "selfHelpOrderAPIInit.html";
  		tempForm.method = "post";
  		tempForm.style.display = "none";
  							
  		var apiIdInput = document.createElement("input");
  		apiIdInput.type="hidden"; 
		apiIdInput.name= "apiId"; 
		apiIdInput.value= apiId; 
		tempForm.appendChild(apiIdInput);
					
		var indexPageInput = document.createElement("input");
  		indexPageInput.type="hidden"; 
		indexPageInput.name= "indexPage"; 
		indexPageInput.value= 2; 
		tempForm.appendChild(indexPageInput);
							
		document.body.appendChild(tempForm);
		tempForm.submit();
		document.body.removeChild(tempForm);				
}
</script>
<style>
.mnlist .tablist-head{ border:#e5e5e5 solid 1px; border-bottom:none;}
.mnlist{ border:none;}
.order{ padding:0px;}
.htd{ position:relative}

.order .tl{
	margin-left: -72px;
	padding-top: 23px;
	*position: absolute;
	*top:0;
	*left: 92px;	
}
.order .tr h2{
	height: 100px;
	line-height: 100px;
	font-size: 14px;
	color: #343434;
	text-align: left;
	*width: 208px;
	*text-align: left;
}
.order .listDiv p{
	color: #929292;
	margin-top:44px; 
	font-size:14px; 
	text-align:center;
	
	padding-right: 20px;

}
.order .stylep{
	height: 100px;
	line-height: 100px;
	font-size: 14px;
	color: #343434;
	
}
.order p em{
	color: #929292;
}
.order p a{
	color: #343434;
	font-size: 14px;
}
.tabList table td{ border:#e5e5e5 solid 1px; border-top:none;}
.tabList table .only{ border-top:none;}
.tabList table .onlytwo{border-bottom:none;}

.b_bic{
	background-color: #fff3dc;
	width: 188px;
	height: 34px;
	line-height: 32px;
	border-radius: 20px;
	display: inline-block;
	vertical-align: middle;
	font-size: 14px;
	color: #f6a525;
	cursor: pointer;
}

.b_bic i{
	width: 32px;
	/*height: 32px;*/
	display: inline-block;
	padding-right: 12px;
	vertical-align: middle;
}
</style>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
						<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        <strong style="font-size:20px; color:#fff; padding-left:10px;position:relative; top:-10px; font-weight:normal;">个人中心</strong>
					</div>
					
					<div class="safem fl">
				        <!-- <span class="fl"><a href="${ctx}/updateUserProvice.html">更新用户</a></span>
						<span class="fl"><a href="${ctx}/updateAssetProvince.html">更新资产</a></span> -->
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
		<div class="core clearfix" style="margin-bottom:343px;">
        	<div class="coreLeft fl">
            	<a href="${ctx}/userCenterUI.html"><h3><i></i>个人中心</h3></a>
                <dl>
                	<dt>交易管理</dt>
                    <dd><a href="${ctx}/orderTrackInit.html">我的订单</a></dd>
                    <!-- <dd><a href="#">我的优惠劵</a></dd> -->
                    <dd><a href="${ctx}/balanceUI.html">安全币</a></dd>
                    <dt>个人信息管理</dt>
                    <dd><a href="${ctx}/userDataUI.html">个人资料</a></dd>
                    <dd style="border:none;"><a href="${ctx }/userAssetsUI.html">我的资产</a></dd>
                </dl>
            </div>
        	<div class="coreRight my-order fl">
            	
               
               
                <div class="my-orderTabBox personalIndex">
                	<div class="TabBoxtitle">
                     <dl class="clearfix">
                   	  <dt class="fl">消息提示</dt>
                        <a href="${ctx}/orderTrackInit.html"><dd class="fl"><i><img src="${ctx}/source/images/personalCenter/orderCount.png" alt=""></i><span>订单总数<em>${orderNum}</em></span></dd></a>
                        <a href="#" onclick="orderTrack(1,1)"><dd class="fl"><i><img src="${ctx}/source/images/personalCenter/orderRuning.png" alt=""></i><span>服务中的订单<em>${servNum}</em></span></dd></a>
                        <a href="#" onclick="orderTrack(2,1)"><dd class="fl"><i><img src="${ctx}/source/images/personalCenter/orderWarn.png" alt=""></i><span>告警订单<em>${alarmSum}</em></span></dd></a>
                      	<span class="b_bic qdbtn" style="float:right;margin-top:13px;color: #f6a525;">
                           		<i><img src="${ctx}/source/images/balance/sign_in.png" alt=""></i>
	                        	<c:if test="${!signIn}">
                           			<b>签到可获得10安全币</b>
	                        	</c:if>
	                        	<c:if test="${signIn}">
	                        		<b style="padding-left:10px;"><i style="width:auto;padding-right: 8px;"><img src="${ctx}/source/images/balance/minig.png" alt=""></i>今日已签到</b>
	                        	</c:if>
                            </span>
                      </dl>                          
                    </div>
                     
                    <div class="neworder">
                        	<h4 class="fl">最新订单</h4>
                            <a href="${ctx}/orderTrackInit.html" class="fr btn">更多订单</a>
                        </div>
                    	<table class="tabBox" cellpadding="0" cellspacing="">
                            <tbody>
                                <tr>
                                    <td width="295">订单信息</td>
                                    <td width="85">订单类型</td>
                                    <td width="109">订单状态</td>
                                    <td width="175">服务起止时间</td>
                                    <td width="160">价格（安全币）</td>
                                    <td width="96">操作</td>
                                </tr>
                                
                            </tbody>
                     
                     </table>
                     

				    <!-- 订单列表-->
					<c:import url="/orderList.html"></c:import>
                    
                  </div>
                    
                    <!--常用服务-->
                    <div class="Commonly-used-services" style="width:928px;">
                    	<h2>常用服务</h2>
                    	<ul class="clearfix">
                        	<li class="fl">
                            	<!--需要显示AIP图标，给I加上类show-->
                                <!--<a href="${ctx}/selfHelpOrderAPIInit.html?apiId=1&indexPage=2">
                                	-->
                                	<a href="javascript:;" onclick="buyAPI(1);">
                                	<i class="show"></i>
                                    <p style=" text-align:center"><img src="${ctx}/source/images/personalCenter/1.png" alt=""></p>
                                    <!--<h5><a href="${ctx}/selfHelpOrderAPIInit.html?apiId=1&indexPage=2">WEB漏洞监测能力API</a></h5>
                                -->
                                	<h5><a href="javascript:;" onclick="buyAPI(1);">WEB漏洞监测能力API</a></h5>
                                </a>
                                
                            </li>
                            <li class="fl">
                            	<!--<a href="${ctx}/selfHelpOrderInit.html?serviceId=2&indexPage=1">
                                	-->
                                	<a href="javascript:;" onclick="buySelfHelpOrder(2);">
                                	<i></i>
                                    <p><img src="${ctx}/source/images/personalCenter/2.png" alt=""></p>
                                    <!--<h5><a href="${ctx}/selfHelpOrderInit.html?serviceId=2&indexPage=1">网站挂马监测服务</a></h5>
                            	-->
                            	<h5><a href="javascript:;" onclick="buySelfHelpOrder(2);">网站挂马监测服务</a></h5>
                            	</a>
                            </li>
                            <li class="fl">
                            	<!--<a href="${ctx}/selfHelpOrderInit.html?serviceId=5&indexPage=1">
                                	-->
                                	<a href="javascript:;" onclick="buySelfHelpOrder(5);">
                                	<i></i>
                                   	<p style="padding-top:38px;"><img src="${ctx}/source/images/personalCenter/5.png" alt=""></p>
                                    <!--<h5><a href="${ctx}/selfHelpOrderInit.html?serviceId=5&indexPage=1">网站可用性监测服务</a></h5>
                                -->
                                <h5><a href="javascript:;" onclick="buySelfHelpOrder(5);">网站可用性监测服务</a></h5>
                                </a>
                               
                            </li>
                            <li class="fl" style="margin-right:0px;">
                            	<!--<a href="${ctx}/selfHelpOrderInit.html?serviceId=3&indexPage=1">
                                	-->
                                	<a href="javascript:;" onclick="buySelfHelpOrder(3);">
                                	<i></i>
                                    <p><img src="${ctx}/source/images/personalCenter/3.png" alt=""></p>
                                    <!--<h5><a href="${ctx}/selfHelpOrderInit.html?serviceId=3&indexPage=1">网页篡改监测服务</a></h5>
                                -->
	                                <h5><a href="javascript:;" onclick="buySelfHelpOrder(3);">网页篡改监测服务</a></h5>
	                                </a>
                                
                            </li>
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
	</div>
<script>
	$(function(){
		/*$('.listul').each(function(index, element) {
           var h = $(this).height();
		   $(this).children('li').height(h);
        });*/
		/*提示文字效果*/
		$('.promptext').focus(function(){
			if($(this).val()=='输入资产名称或订单号进行搜索'){
				$(this).val('');
				$(this).css('color','#343434');
			}else{
				$(this).css('color','#343434');
			}
		});
		$('.promptext').blur(function(){
			if($(this).val()==''){
				$(this).val('输入资产名称或订单号进行搜索');
				$(this).css('color','#929292');	
			}
				
		})
	})
	
	
</script>

<!--签到弹框-->
    <div class="succeed">
    	<div class="spopo">
        	<i class="icof"></i><span>签到成功</span><em>+<b>10</b></em><i class="icoc"></i>
        </div>
    
    </div>
 
</body>


</html>
