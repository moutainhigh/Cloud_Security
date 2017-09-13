<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>${service.name }商品信息</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript"  src="${ctx}/source/scripts/common/jquery.form.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script src="${ctx}/source/scripts/order/wafDetail.js"></script>
<script src="${ctx}/source/scripts/order/details.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/zezao.js"></script>
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
        <input type="hidden" id="scanTypeHidden" value="${orderDetail.type}"/>
	 <input type="hidden" id="beginDateHidden" value="<fmt:formatDate value='${orderDetail.begin_date}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
	 <input type="hidden" id="timesHidden" value="${orderDetail.wafTimes}"/>
	 <input type="hidden" id="domainNameHidden" value="${orderDetail.assetAddr}"/>
	 <input type="hidden" id="domainIdHidden" value="${orderDetail.asstId}"/>
	 <input type="hidden" id="ipArrayHidden" value="${orderDetail.ipArray}"/>
		</div>
	<div class="dataCent">
			<div class="data-crumbs">
				<a href="${ctx}/index.html" style="font-size: 20px;">安全帮</a><i>&gt;</i><a href="${ctx}/web_anquanbang.html">网站安全帮</a><i>&gt;</i><a href="javascript:;">${service.name }</a>
			</div>
				<input type="hidden" id="serviceIdHidden" value="${service.id }"/>
			<div class="dataBox clearfix" style="padding-bottom:10px;">
				<div class="dataL fl">
					<div class="dataImg fl">
					<img src="${ctx}/source/images/portal/product.png" alt="" />
					</div>
				</div>
					<div class="dataR detailsR fl" style="width:640px;">
					<h2 style="font-size:20px; margin-bottom:18px;">${service.name }</h2>
                  <a href="javascript:showShopCar();" class="buttoncar" style="right:0px;"><b id="shopCarNum">${carnum}</b><i></i>我的购物车&gt;</a>
				
					<ul>
						<li class="clearfix">
							<label class="fl">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</label>
                            <div class="fl price"><strong id="price">880</strong><strong></strong></div> 
                          	<span style="position: relative;top: 7px;left:7px;color:#d00000">安全币</span>
						</li>
                          <li class="clearfix">
							<label class="fl">选 类 型</label>
                           <div class="fl clickBox" id="clickBox">
                            <button class="click Single" value="8" onclick="chanageDiv('Single');">包月</button>
                            <button class="long" value="9" onclick="chanageDiv('long');">包年</button>
                           </div> 
                           
						</li>
                 <li class="clearfix" id="monthDiv" style="display:block;">
							<label class="fl">服务时间</label>
                            <div class="fl" style="top:3px;">
                            	<span>开始时间 <input type="text" id="beginDateForMonth" class="text" style="width:156px;" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',onpicked:function(dp){ }})"></span>
                                 <!-- <span>结束时间 <input type="text"  id="endDate" style="width:156px;" class="text" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-{%d+1}',minDate:'#F{$dp.$D(\'beginDate\',{d:31})}',dateFmt:'yyyy-MM'})"></span> -->
                              <span>
                                                                                   服务期限
                              <select class="text select" id="month" onchange="changePrice();">
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
                              </select>
                              
                              </span>
                           </div> 
                           
						</li> 
                            <li class="clearfix" id="yearDiv" style="display:none">
							<label class="fl">服务时间</label>
                              <div class="fl" style="top:3px;" >
                            	<span>开始时间<input type="text" id="beginDateForYear" class="text" style="width:156px;" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',onpicked:function(dp){}})"></span>
                                 <!-- <span>结束时间 <input type="text"  id="endDate" style="width:156px;" class="text" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-{%d+1}',minDate:'#F{$dp.$D(\'beginDate\',{d:365})}',dateFmt:'yyyy-MM'})"></span> -->
                           </div> 
						</li>
                      	<li class="clearfix waf-datails" style="height:auto">
                        	<label class="fl">防护网站</label>
                            <div class="fl" style="top:10px;">
                            	<div class="fl" style="color:#a3a3a3; margin-right:20px;">
                                	<span class="not">尚未选择网站</span>
                                    <style>
                                    	.http li{ line-height:24px; height:24px !important; color:#5f5f5f; border:none;}
										.dele{ color:#d00000; margin-left:20px; cursor:pointer;}
                                    </style>
                                    <div class="http" style="display:none">
                                    	<!--这里放添加过来的数据-->
                                    	<!--<li><em>域名：</em><p>www.anquanbang.net</p></li>
                                        <li><em>ip1:</em><p>12.12.1.12</p></li>
                                        <li><em>ip2:</em><p>12.12.1.12</p></li>-->
                                        <p class="ym"><em>域名：</em><span></span></p>
                                        <ul class="fack">
                                        	
                                        </ul>
                                        
                                    </div>
                                 </div>
                                <a href="javascript:;" id="waf_http" class="href_btn fl" style="color:#2499fb">点击此处选择网站</a>
                                <em class="dele" style="display: none;" id="dele">删除</em>
                                
                            </div>
                        </li>
                         
                        
                        
					</ul>
                    <div class="btnBox" style="text-align:left; margin-left:0px; margin-top:32px; margin-bottom:0px;">
                    	<button style="background:#d00000; width:146px;" id="addCarWaf">添加到购物车</button>
                        <button style="background:#5aba5f; width:126px" id="buyNowWaf">立即购买</button>
                    </div>
				</div>
                <form action="wafDetails.html" method="post" id="wafDetailsForm">
					<input type="hidden" id="serviceIdWafHidden" name="serviceId" value=""/>
				</form>
				
			</div>
		</div>
        <div class="commodity">
        	<div class="imgBox clearfix">
            	<h4>商品信息</h4>
                <div class="commoditys" style="height:620px; overflow:hidden">
                	<img src="${ctx}/source/images/portal/wafImage1.png" alt="">
                </div>
                <div class="commoditys"style=" overflow:hidden">
                	<img src="${ctx}/source/images/portal/wafImage2.png" alt="">
                </div>
                <div class="commoditys" style="overflow:hidden">
                	<img src="${ctx}/source/images/portal/wafImage3.png" alt="">
                </div>
                <div class="commoditys" style="height:464px; overflow:hidden">
                	<img src="${ctx}/source/images/portal/wafImage4.png" alt="">
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
<div class="waf-detais-pop popBoxhide" id="wafpop">
	<i class="close chide" id="wafclose"></i>
    <div class="Pophead">
    	<h2>请选择要服务的网站域名（单选）</h2>
        <a href="#"  class="ass "><i><img src="${ctx}/source/images/add.png" alt=""></i>新增域名</a>
    </div>
	<div class="popBox">
    <!--请选择要服务的网站内容显示-->
        <div class="centone" id="senone" style="display:none;" >
             <ul style="height:175px;" id="assBox">
              <c:forEach items="${assList}" var="asset"  varStatus="status">
                <li>
                    <div class="rcent">
                        <!-- <h3>
                        <label onclick='selWafAsset(this)'><input type="radio" name="anquan" class="radio" style="display:none" value="${asset.id}"><i class=""></i></label>
                        	<b>${asset.name }</b>
                         </h3>
                       <div class="tBox">${asset.addr }</div>-->
                       <h3>
                            <c:if test="${asset.status == '0' }">
                                  <label style="margin:0 16px 0 0;">
                                       <span style="padding-left:40px;padding-top:20px">${asset.name }</span>
	                              </label>
	                        </c:if>
	                        
	                        <c:if test="${asset.status == '1' }">
                                <label onclick='selWafAsset(this)'><input type="radio" name="anquan" class="radio" style="display:none" value="${asset.id}"><i class=""></i></label>
                        		<b>${asset.name }</b>
	                        </c:if>
                        </h3>
                        <div class="tBox">${asset.addr }  
                            <c:if test="${asset.status == '0' }">
                          	<a href="${ctx}/userAssetsUI.html?type=1"  style='padding-left:20px;color:#2499fb;font-size:14px;'>未验证</a>
                     		</c:if>
                     	</div>
                    </div>
                </li>
                 </c:forEach>
            </ul>
           <div class="hide" style="position: relative;">
           	 <p style="margin: 20px 20px 0px 40px">填写域名对应的IP:端口号（例：192.168.1.1:8080），不填写端口号使用默认端口号(http:默认端口号80；https默认端口号443)，对应多个ip时请逐个添加
</p>
               <div class="add_ip clearfix">
                    <ol id="wafBox" class="waf-addBox fl">
                        <li class="basic"><input type="text" class="text"></li>
                        <span class="fl" id="acIp"><em><img src="${ctx}/source/images/add.png" alt=""></em>新增IP</span>
                    </ol>
           		</div>
           		
           </div>
           <style>

           
           </style>
           <div class="waf-detailsbtoom clearfix" style="margin-bottom:20px;">
           	<a href="javascript:;" class="submit">确定</a>
           </div>
        </div>
        <!--新增网站内容显示-->
        <div class="centone" id="sentwo" style="display:none;">
           <form id="saveWafAsset" action="" method="post">
            <div class="addhttp">
                <div class="popBox">
                    <ul style="height:auto;">
                      <li class="clearfix">
                          <label class="fl">网站名称</label> <div class="fl"><input class="boz_inout_1" type="text" name="name" id="assetName" maxlength="20"></div>
                            <div class="addMsg" style="color:#e32929;text-align:left;font-size: 14px;" id="assetName_msg"></div>
                        </li>
                     <!-- <li class="clearfix">
                            <label class="fl">网站地址类型</label><div class="fl">
                                <label><input type="radio" name="addrType" checked="checked" id="assetAddrType1" value="http">http</input></label>
                                <label style="margin-left:10px;"><input type="radio" name="addrType" id="assetAddrType2" value="https">https</label>
                             </div>
                        </li> --> 
                       <li class="clearfix">
                        	<label class="fl">网站地址</label> <div class="fl"><input class="boz_inout_1" type="text" name="addr" id="InertAddr"  maxlength="100" onkeyup="value=value.replace(/[\u4E00-\u9FA5]/g,'')"></div>
                            <div class="addMsg" style="color:#e32929;text-align:left;font-size: 14px;" id="assetAddr_msg"></div>
                        	<div class="fl" style="margin-left:88px;font-size: 14px;color: rgb(227, 41, 41);">云WAF订单只支持域名网站</div>
                        </li>
                        <li class="clearfix">
                            <label class="fl">示例</label> 
                            <div class="fl" style="margin-bottom:10px;">                              
                                 <p style="margin:0px;">http://xxx.xxx.xxx.xxx</p>
                                 <p style="margin:0px;">https://xxx.xxx.xxx.xxx:1234</p>
                                 <p style="margin:0px;">http://xxx.xxx.xxx.xxx:8080/home</p>                                 
                            </div>
                        </li>
                        
                         <li class="clearfix">
                            <label class="fl">物理位置</label> 
                                <div class="fl">
                                    <select class="user_secta_count spiclesel" id="districtId" name="districtId" onchange="getCitys(this.value)" style="width:119px;height: 35px;">
				                        <option selected="selected" value="-1">请选择省份</option>
			                        </select> 
			    					<select  class="user_secta_count spiclesel"  id="city" name="city" disabled="disabled" style="width:119px;height: 35px;">
			    			      		<option value="-1">请选择城市</option>
			    					</select>
                                </div>
			    					<div class="addMsg" style="color:#e32929;text-align:left;font-size: 14px;" id="location_msg"></div>
                        </li>
                        <li class="clearfix">
                            <label class="fl">网站用途</label> 
                            <div class="fl">
                                       <select class="user_secta_count spiclesel" id="purpose" name="purpose" style="width:242px;height: 35px;">
                                          <option selected="selected" value="-1">请选择资产用途</option>
							   				<option value="公共服务">公共服务</option>
							   				<option value="信息发布">信息发布</option>
							   				<option value="信息服务">信息服务</option>
							   				<option value="社交">社交</option>
							   				<option value="娱乐">娱乐</option>
							   				<option value="电子商务/金融">电子商务/金融</option>
							   				<option value="移动互联网">移动互联网</option>
							   				<option value="教育/医疗">教育/医疗</option>
							   				<option value="其他">其他</option>   
                                        </select>
                             </div>
                             <div class="addMsg" style="color:#e32929;text-align:left;font-size: 14px;" id="assetUsage_msg"></div>
                        </li>
                    </ul>
                   
                 </div>
            
            </div>
             <div class="bottom clearfix">
                        <a href="javascript:;" class="btn sub" style="margin:0 20px 0 30px;" onclick="saveWafAsset()">提交</a>
                        <a href="javascript:;" class="btn sub return" id="back">返回</a>
                    </div>
            </form>
        </div>
    </div>
	<div class="bottom clearfix">
    	<a href="javascript:;" class="btn ok">确定</a>
    </div>
    
</div>
<div class="shade"></div>
<script>
$(function(){
	//单选JS
$(document).ready(function(){
		var arrtlink=[];	
		$('#waf_http').click(function(){
		 $.ajax({ type: "POST",
		     	async: false, 
		     	url: "getAssetList.html",
		     	data: {"wafFlag":"1"}, 
		     	dataType: "json", 
		     	success: function(data) {
			     	if(data.success){
				     	//如果当前用户没有资产，则进入新增资产页面
						var assCount = data.assList.length;
						if(assCount!=0){
							$('#sentwo').hide(1);
							$('#senone').show(1);
						}else{
							$('#senone').hide(1);
							$('#sentwo').show(1);	
						}
			     	}
		     	},
		     	error:function(data) {
		      		if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 		window.location.href = "loginUI.html"; } 
		    	 	else { window.location.href = "loginUI.html"; } 
		    	 	} 
		  });
		  
		    var ipList = [];
			$('.fack li').each(function(index, element) {
				var ip= $(this).contents().filter(function() { return this.nodeType == 3; }).text(); 
				if(ip!=null && ip!=''){
					ipList.push(ip);
				}		
			});
			if(ipList.length!=0){
			//恢复输入框填写状态
			$('#wafBox li').remove();
			//恢复列表选中状态
			$('#senone li').removeClass('ac');
			$('#senone li i').removeClass('this');
			var ytext=$('.ym span').text();
			$('#senone .tBox').each(function(index, element) {
                var stxt= $(this).text();
				if(ytext==stxt){
					$(this)	.parents('li').addClass('ac');
					$(this)	.parents('.ac').find('i').addClass('this');
				}
				
            });
			//添加输入框的动作
			var list='';
				var index=1;
				for(var i=0;i<ipList.length;i++){
					index++;
					 list+='<li class="waflist">';
					list+='<input type="text" class="text" data-id='+ipList[i] +' value="'+ ipList[i]+'">';
					list+='<i><img src="${ctx}/source/images/p-dle.png" alt=""></i>';
					list+='</li>';  
				}
				$('#wafBox span').before(list);
				$('.hide').show();
			}
			
			//显示遮罩层
			$('.shade').show();
			//显示
			$('.waf-detais-pop').animate({
				opacity: '1',
				top: '45%',
				left: '50%',
				marginTop: '-224px'
			}, 500);
		})
        //弹框切换动作
		$('.ass').click(function(){
			$('#saveWafAsset')[0].reset();	
			$('#senone').hide();
			$('#sentwo').show();
			//添加网站页面的数据清空 start
			$('#assetName').val('');
			$('#InertAddr').val('');
			$('#districtId').val(-1);
			//清空城市下拉框
			$("#city").empty();
			$("#city").append( '<option value="-1">请选择城市</option>');
			$("#city").attr('disabled','disabled');
			$('#purpose').val(-1);
			$(".addMsg").html("");
			//添加网站页面的数据清空 end
		})
		$('#back').click(function(){
			$('#sentwo').hide();
			$('#senone').show();	
		})
		//添加输入框的动作
		
		var html='';
			html+='<li class="waflist">';
				html+='<input type="text" class="text"  value="">';
				html+='<i><img src="${ctx}/source/images/p-dle.png" alt=""></i>';
			html+='</li>';
			
			
		//添加输入框事件	
		$('#acIp').click(function(){
			var lenght=$('#wafBox li').length;
			if(lenght==5){
				$('#wafBox span').fadeOut(200);
					
			}else{
				$('#wafBox span').before(html);	
			}
		});
		//删除输入框
		$('#wafBox').delegate('i','click',function(){
           // alert($('#wafBox .waflist').length);
			var wf=$('.waflist').length;
			if(wf==4){
				$('#acIp').fadeIn(200);		
			}
			$(this).parent('li').remove();	
		});

		$('.submit').click(function(){
			var acleng =$('#senone .ac').length;
			var  ips = new Array();
			var flag = true;
			//判断是否什么都没选为空
			if(acleng==0){
			//	alert("请选择网站域名!");
			//关闭后效果
                $('.waf-detais-pop').animate({
                    opacity: '1',
                    top: '50%',
                    left: '50%',
                    marginTop: '-1200px'
                 }, 500);
                 //隐藏遮罩层
                 $('.shade').hide();
				return;	
			}
			//选中的网站地址           
            var acval=$('#senone .ac .rcent div').text();
            $('#wafBox li input:text').each(function(index, element) {
                var le= $(this).val();
                var temp = [];
                if(le!=null && le!=''){
                	temp = le.split(':');
                	if(temp.length>2){
                		alert("IP格式不正确!不正确IP为:"+le);
                		flag = false;
                		return;
                	}else if(temp.length==1){
                		if(!isIp(temp[0])){
	                		alert("IP格式不正确!不正确IP为:"+le);
	                		flag = false;
	                		return;
                		}else if(acval.indexOf('https')!=-1){
                			le+=":443";
                		}else if(acval.indexOf('http')!=-1){
                			le+=":80";
                		}
                	}else{
                		if((!isIp(temp[0]))||(!isPort(temp[1]))){
	                		alert("IP格式不正确!不正确IP为:"+le);
	                		flag = false;
	                		return;
                		}
                	}
                }else{
                	alert("请输入IP地址!");
                	flag = false;
	                return;
                }
                if(flag){
                	ips.push(le);
                }
            });
            if(!flag){
            	//ip地址有误
            	return;
            }

			else if(ips.length==0){
				alert("请输入IP地址!");
            }else if(isRepeat(ips)){
            	alert("IP地址不能重复!");
            }
			else{
                $('#wafBox input:text').each(function(index, element) {
                        arrtlink.length=0;
                        $('.fack li').remove();
                        //选中的网站地址           
                        var acval=$('#senone .ac .rcent div').text();
                        var assetId = $('#senone .ac .rcent').find('input').val();
                        $('#wafBox input:text').each(function(index, element) {
                            var tval= $(this).val();
                            //存入val值
                            arrtlink.push(tval);
                        });
                        //页面添加网址
                        $('.ym span').text(acval);                       
						$('.ym span').attr('id',assetId);
						
                        var list='';
                        var index=0;
                        for(var i=0;i<arrtlink.length;i++){
                            if(arrtlink[i]!=null && arrtlink[i]!=''){
                                index++;
                            	list+='<li id='+ index +'><em>ip<b>'+ index +'</b>：</em>'+ arrtlink[i] +'</li>';
                            }
                               
                        }
                        $('.fack').append(list);
                        
                        //显示页面删除和容器标签
                        $('#dele').show();
                        $('.not').hide();
                        $('.http').show();
                         //关闭后效果
                        $('.waf-detais-pop').animate({
                            opacity: '1',
                            top: '50%',
                            left: '50%',
                            marginTop: '-1200px'
                        }, 500);
                        //隐藏遮罩层
                        $('.shade').hide();
                    
                });



				
			}
			
			
		})
		
		
		//关闭窗口按钮
		$('#wafclose').click(function(){
			if(arrtlink.length==0){
				$('#senone .hide .text').val('');
				$('#senone li').removeClass('ac');
				$('#senone li i').removeClass('this');
				$('#senone .hide').hide();
                $('#wafBox .waflist').remove();	
				
			}
				 //关闭后效果
				$('.waf-detais-pop').animate({
					opacity: '1',
					top: '50%',
					left: '50%',
					marginTop: '-1200px'
				}, 500);
				//隐藏遮罩层
				$('.shade').hide();
				
				//添加网站页面的数据清空 start
				$('#assetName').val('');
				$('#InertAddr').val('');
				$('#districtId').val(-1);
				//清空城市下拉框
				$("#city").empty();
				$("#city").append( '<option value="-1">请选择城市</option>');
				$("#city").attr('disabled','disabled');
				$('#purpose').val(-1);
				$(".addMsg").html("");
				//添加网站页面的数据清空 end
		})
		//页面删除按钮
		$('#dele').click(function(){
				arrtlink.length=0;
                $('#senone .hide .text').val('');
               
				$('.ym span').text('');
				$('.fack li').remove();
				$('#senone li').removeClass('ac');
				$('#senone li i').removeClass('this');
				$('#wafBox .waflist').remove();
				$('#senone .hide').hide();
                $(this).hide();
                $('.http').hide();
                $('.not').show();
		})	
})	
})
</script>


</body>



</html>
