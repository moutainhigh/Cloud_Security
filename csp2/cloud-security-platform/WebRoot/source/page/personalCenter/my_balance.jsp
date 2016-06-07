<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>个人中心-我的余额</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript">
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
						var html='';
						html+='<b style="padding-left:10px;"><i style="width:auto;padding-right: 8px;"><img src="/cloud-security-platform/source/images/balance/minig.png" alt=""></i>今日已签到</b>';
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
//今日未签到时，签到按钮添加click事件
$(document).ready(function(){
  var signFlg = ${requestScope.signIn};
  if (!signFlg) {
  	$(".qdbtn").click(signIn);
  }
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
		<div class="core balance clearfix" style="margin-bottom:343px;">
        	<div class="coreLeft fl">
            	<a href="${ctx}/userCenterUI.html"><h3><i></i>个人中心</h3></a>
                <dl>
                	<dt>交易管理</dt>
                    <dd><a href="${ctx}/orderTrackInit.html">我的订单</a></dd>
                    <dd><a href="${ctx}/balanceUI.html" class="active">我的余额</a></dd>
                    <dt>个人信息管理</dt>
                    <dd><a href="${ctx}/userDataUI.html">个人资料</a></dd>
                    <dd style="border:none;"><a href="${ctx }/userAssetsUI.html">我的资产</a></dd>
                </dl>
            </div>
        	<div class="coreRight fl" style="margin-bottom:200px;">
            		<div class="banlance_head clearfix">
                    	<div class="c-lan fl">
                        	<span class="b_aic">安全币余额：<em>${balance }</em></span>
                            <span class="b_bic qdbtn" >
                           		<i><img src="${ctx}/source/images/balance/sign_in.png" alt=""></i>
	                        	<c:if test="${!signIn}">
                           			<b>签到可获得10安全币</b>
	                        	</c:if>
	                        	<c:if test="${signIn}">
	                        		<b style="padding-left:10px;"><i style="width:auto;padding-right: 8px;"><img src="${ctx}/source/images/balance/minig.png" alt=""></i>今日已签到</b>
	                        	</c:if>
                            </span>
                        </div>
                        
                       <a href="javascript:;" id="gz" class="gz-btn fr">安全币规则</a>
                       <div class="gz pop rule">
                       		<dl>
                            	<dt><i><img src="${ctx}/source/images/balance/balance_rule.png"></i>安全币可以做什么？</dt>
                                <dd>1、可以用于购买安全帮内所有的安全服务</dd>
                                <dd>2、1安全币=1元人民币</dd>
                            </dl>
                            <dl>
                            	<dt><i><img src="${ctx}/source/images/balance/balance_get.png"></i>怎么获取安全币？</dt>
                                <dd>1、新用户注册可获得500安全币</dd>
                                <dd>2、每日签到可获得10安全币</dd>
                            </dl>
                       
                       </div>
                    	
                    </div>
                    <div class="hr" style="margin:14px 0 20px 0"></div>
            	<div class="take-notes">
                	<h3>消费记录</h3>
                    <table class="tabox">
                    	<thead>
                        	<tr height="50">
                            	<th width="134"><span>序号</span></th>
                                <th width="200">时间</th>
                                <th width="200">订单编号</th>
                                <th width="280">名称</th>
                                <th width="160">价格（元）</th>
                            </tr>
                            
                        </thead>
                       </table>
                       <c:forEach var="payItem" items="${pb.datas }" varStatus="status">
							<table class="tabox" style="background-color: #e4f3ff">
								<tbody>
									<tr height="80">
										<td width="134">${pb.pageSize * (pb.pageCode-1) + status.count }</td>
										<td width="200">
											<fmt:formatDate value="${payItem.pay_date }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td width="200">${payItem.id }</td>
										<td width="280" style="font-size: 16px;">${payItem.serverName }</td>
										<td width="160">
											<fmt:formatNumber  value="${payItem.price }" pattern="0.00"/>
										</td>
									</tr>
								</tbody>

							</table>
						</c:forEach>
                </div>
                <%--页码列表--%>
                <div class="page clearfix">
	                <c:choose>
	                	<c:when test="${pb.pageCode>1}">
	                		<!-- <a href="${ctx}/balanceUI.html?pageCode=${pb.pageCode-1 }" class="lt cl_active fl"></a> -->
	                		<a href="${ctx}/balanceUI.html?pageCode=${pb.pageCode-1 }" class="lt fl"></a>
	                	</c:when>
	                	<c:otherwise>
	                		<a href="#" class="lt fl"></a>
	                	</c:otherwise>
	                </c:choose>
                
                	<%--页码--%>
                    <ul class="bpage fl">
                    	<c:if test="${pb.totalPage != 1 && pb.pageCode == pb.totalPage}">
                       		<li><a href="${ctx}/balanceUI.html?pageCode=${pb.pageCode-1 }">${pb.pageCode-1 }</a></li>
                    	</c:if>
                    	
                    	<li style="background-color:#2499fb;"><a href="#" style="color:#f3f3f3;">${pb.pageCode }</a></li>
                    	
                    	<c:if test="${pb.pageCode < pb.totalPage}">
                        	<li><a href="${ctx}/balanceUI.html?pageCode=${pb.pageCode+1 }" >${pb.pageCode+1 }</a></li>
                    	</c:if>
                        
                    </ul>
                    
                    <c:choose>
	                	<c:when test="${pb.pageCode < pb.totalPage}">
	                		<a href="${ctx}/balanceUI.html?pageCode=${pb.pageCode+1 }" class="gt fl"></a>
                    		<!-- <a href="${ctx}/balanceUI.html?pageCode=${pb.pageCode+1 }" class="gt cr_active fl"></a> -->
	                	</c:when>
	                	<c:otherwise>
	                		<a href="#" class="gt fl"></a>
	                	</c:otherwise>
	                </c:choose>
                    <span class="pay fl">共${pb.totalPage }页</span>
                </div>
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
		<div class="foot">
			<p>版权所有Copyright © 2015 中国电信股份有限公司北京研究院京ICP备12019458号-10</p>
		</div>
	</div>
	
	<!--签到弹框-->
    <div class="succeed">
    	<div class="spopo">
        	<i class="icof"></i><span>签到成功</span><em>+<b>10</b></em><i class="icoc"></i>
        </div>
    
    </div>
</body>


</html>
