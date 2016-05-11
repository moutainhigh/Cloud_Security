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
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript">
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
                	window.location.href="deleteOrder.html?orderId="+orderId;
                }
            },
         });
    } else {
        return;
    }
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
                        <b class="dividing-line"></b>
                        <strong>个人中心</strong>
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
		<div class="core clearfix" style="margin-bottom:343px;">
        	<div class="coreLeft fl">
            	<a href="${ctx}/userCenterUI.html"><h3><i></i>个人中心</h3></a>
                <dl>
                	<dt>交易管理</dt>
                    <dd><a href="${ctx}/orderTrackInit.html">我的订单</a></dd>
                    <!-- <dd><a href="#">我的优惠劵</a></dd> -->
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
                        <a href="${ctx}/orderTrackInit.html?state=1"><dd class="fl"><i><img src="${ctx}/source/images/personalCenter/orderRuning.png" alt=""></i><span>服务中的订单<em>${servNum}</em></span></dd></a>
                        <a href="${ctx}/orderTrackInit.html?state=2"><dd class="fl"><i><img src="${ctx}/source/images/personalCenter/orderWarn.png" alt=""></i><span>告警订单<em>${alarmSum}</em></span></dd></a>
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
                                    <td width="175">服务起始时间</td>
                                    <td width="160">价格（元）</td>
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
                                <a href="${ctx}/selfHelpOrderAPIInit.html?apiId=1&indexPage=2">
                                	<i class="show"></i>
                                    <p style=" text-align:center"><img src="${ctx}/source/images/personalCenter/1.png" alt=""></p>
                                    <h5><a href="${ctx}/selfHelpOrderAPIInit.html?apiId=1&indexPage=2">WEB漏洞监测能力API</a></h5>
                                </a>
                                
                            </li>
                            <li class="fl">
                            	<a href="${ctx}/selfHelpOrderInit.html?serviceId=2&indexPage=1">
                                	<i></i>
                                    <p><img src="${ctx}/source/images/personalCenter/2.png" alt=""></p>
                                    <h5><a href="${ctx}/selfHelpOrderInit.html?serviceId=2&indexPage=1">网站挂马监测服务</a></h5>
                            	</a>
                            </li>
                            <li class="fl">
                            	<a href="${ctx}/selfHelpOrderInit.html?serviceId=5&indexPage=1">
                                	<i></i>
                                   	<p style="padding-top:38px;"><img src="${ctx}/source/images/personalCenter/5.png" alt=""></p>
                                    <h5><a href="${ctx}/selfHelpOrderInit.html?serviceId=5&indexPage=1">网站可用性监测服务</a></h5>
                                </a>
                               
                            </li>
                            <li class="fl" style="margin-right:0px;">
                            	<a href="${ctx}/selfHelpOrderInit.html?serviceId=3&indexPage=1">
                                	<i></i>
                                    <p><img src="${ctx}/source/images/personalCenter/3.png" alt=""></p>
                                    <h5><a href="${ctx}/selfHelpOrderInit.html?serviceId=3&indexPage=1">网页篡改监测服务</a></h5>
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
					<a href="${ctx}/index.html">
						<img src="${ctx}/source/images/portal/new-footer-logo.png" alt="">
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
                        	<dd><a href="${ctx}/knowUs.html">了解安全帮</a></dd>
                            <dd><a href="${ctx}/joinUs.html">加入安全帮</a></dd>
                            <dd><a href="#">联系我们</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd><a href="#">QQ交流群<br>470899318</a></dd>
                            <dd class=""><a href="#">官方微信</a></dd>
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
 
</body>


</html>
