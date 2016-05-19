<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>${service.name }订单</title>
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
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>

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
                        <a href="${ctx}/web_anquanbang.html"><strong style="font-size:20px; color:#fff; padding-left:10px;position:relative; top:-10px;font-weight:normal;">网站安全帮</strong></a>
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
		<div class="dataCent">
			<div class="data-crumbs">
				<a href="#" style="font-size: 20px;">安全帮</a><i>&gt;</i><a href="#">网站安全帮</a><i>&gt;</i><a href="javascript:;">${service.name }</a>
			</div>
			<input type="hidden" id="serviceIdHidden" value="${service.id }"/>
			<div class="dataBox clearfix" style="padding-bottom:10px;">
				<div class="dataL fl">
					<div class="dataImg fl">
					<img src="${ctx}/source/images/portal/product.png" alt="" />
					</div>
				</div>
				<div class="dataR detailsR fl" style="width:644px;">
					<h2>${service.name }</h2>
                  	
					<ul>
						<li class="clearfix">
							<label class="fl">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</label>
                            <div class="fl price"><strong style="font-weight: bold;" id="price">¥100</strong><strong></strong></div> 
                          
						</li>
                        <li class="clearfix">
							<label class="fl">选 择 型</label>
                           <div class="fl clickBox" id="clickBox">
                            <button class="click Single" value="0" onclick="chanageDiv();">包月</button>
                            <button class="long" value="1" onclick="chanageDiv();">包年</button>
                           </div> 
                           
						</li>
                        <li class="clearfix" id="monthDiv" style="display:block;">
							<label class="fl">服务时间</label>
                            <div class="fl" style="top:3px;">
                            	<span>开始时间 <input type="text" id="beginDate" class="text" style="width:156px;" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-{%d+1}',maxDate:'#F{$dp.$D(\'endDate\',{d:-31})}',dateFmt:'yyyy-MM'})"></span>
                                 <span>结束时间 <input type="text"  id="endDate" style="width:156px;" class="text" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-{%d+1}',minDate:'#F{$dp.$D(\'beginDate\',{d:31})}',dateFmt:'yyyy-MM'})"></span>
                           </div> 
                           
						</li> 
                            <li class="clearfix" id="yearDiv" style="display:none">
							<label class="fl">服务时间</label>
                              <div class="fl" style="top:3px;" >
                            	<span>开始时间<input type="text" id="beginDate" class="text" style="width:156px;" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-{%d+1}',maxDate:'#F{$dp.$D(\'endDate\',{d:-365})}',dateFmt:'yyyy-MM'})"></span>
                                 <span>结束时间 <input type="text"  id="endDate" style="width:156px;" class="text" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-{%d+1}',minDate:'#F{$dp.$D(\'beginDate\',{d:365})}',dateFmt:'yyyy-MM'})"></span>
                           </div> 
						</li>
                         <li class="clearfix">
							<label class="fl" style="position:relative; top:4px;">网站域名</label>
                            <div class="fl">
                          <c:if test="${not empty assList}">
	                            
                            	<select class="text select" style="width:248px; margin-left:0; height:36px;" id="domainName">
                            	   <c:forEach var="assInfo" items="${assList}" varStatus="status">
                                   <option value="${assInfo.addr}" assId="${assInfo.id}">${assInfo.name}</option> 
                                    </c:forEach>
                                </select>
                               
                                </c:if>
                            </div> 
						</li>
                        <li class="clearfix" style="height:auto">
							<label class="fl" style="position:relative; top:-2px; line-height:20px;">网站域名<br>对 应 I P</label>
                            <div class="fl">
                            	<ul class="waf_ip_list" id="wafbox">
                                	<li><input type="text" class="text"  style="width:237px;" name="IPValue"><i id="addlist"><img src="${ctx}/source/images/portal/add-ip.png"></i><span>添加IP地址<em>（域名对应多IP地址时添加IP）</em></span></li>
                                  
                                    
                                </ul>
                            </div> 
						</li>
                        
                        
					</ul>
                    <div class="btnBox" style="text-align:left; margin-left:0px; margin-bottom:0px; position:relative; top:-46px;">
                    	<button style="background:#d00000; width:146px;" id="addCar">添加到购物车</button>
                        <button style="background:#5aba5f; width:126px" id="buyNow">立即购买</button>
                    </div>
				</div>
                
				
			</div>
		</div>
        <div class="commodity">
        	<div class="imgBox clearfix">
            	<h4>商品信息</h4>
                <div class="commoditys" style="height:618px; overflow:hidden">
                	<c:if test="${service.id == 1}">
                		<img src="${ctx}/source/images/portal/servicePic1.png" alt=""/>
                	</c:if>
                	<c:if test="${service.id == 2}">
                		<img src="${ctx}/source/images/portal/servicePic2.png" alt=""/>
                	</c:if>
                	<c:if test="${service.id == 3}">
                		<img src="${ctx}/source/images/portal/servicePic3.png" alt=""/>
                	</c:if>
                	<c:if test="${service.id == 4}">
                		<img src="${ctx}/source/images/portal/servicePic4.png" alt=""/>
                	</c:if>
                	<c:if test="${service.id == 5}">
                		<img src="${ctx}/source/images/portal/servicePic5.png" alt=""/>
                	</c:if>
                </div>
                <div class="commoditys" style="height:750px; overflow:hidden">
                	<img src="${ctx}/source/images/portal/product2.png" alt=""/>
                </div>
                <div class="commoditys" style="height:600px; overflow:hidden">
                	<img src="${ctx}/source/images/portal/product3.png" alt=""/>
                </div>
                <div class="commoditys">
                	<img src="${ctx}/source/images/portal/product4.png" alt=""/>
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
           	<img src="${ctx}/source/images/portal/weixin.jpg" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</body>

<script>
	$(function(){
		function waf_adddel(){
			var html='';
		
		 html+= '<li class="add-list"> ';
				html+='<input type="text" class="text"  style="width:236px;" name="IPValue">';
					html+='<i class="del_list"><img src="${ctx}/source/images/portal/del.png"></i>';
				html+='</li>';
		
		
		$('#wafbox').delegate(".del_list","click",function(){
				$(this).parents('.add-list').remove();
		})
		
		
		$('#addlist').click(function(){
			$('#wafbox').children('li:first').after(html);	
		})	
			
		}
		waf_adddel();	
	})

</script>
</html>
