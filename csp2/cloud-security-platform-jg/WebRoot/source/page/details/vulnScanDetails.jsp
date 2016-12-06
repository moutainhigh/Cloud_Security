<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%   
          java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
          java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
          String str_date = formatter.format(currentTime); //将日期时间格式化
 %> 
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

<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/source/scripts/order/details.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/zezao.js"></script>

<script type="text/javascript">

$(document).ready(function(){
    //回显
	var orderType = ${servDetail.servType};
    if(orderType == 0){
			$('.end').hide();
			$('.time').hide();
	}
	if(orderType == 1){
		$('.type').hide();	
		$('.end').show();
	}
	if(orderType == 2){
		$('.type').hide();	
		$('.end').hide();
	}
	var type = ${orderDetail.type};
	if(type==2){
		$("#endDate").val("");
    	$("#beginDate").val("<fmt:formatDate value='${orderDetail.begin_date}' pattern='yyyy-MM-dd HH:mm:ss'/>");
	}else if(type==1){
		var scanType = $("#scanType").val();
		$('.long').addClass('click').siblings().removeClass('click');	
		$('.end').show();
		$('.time').show();
    	$("#endDate").val("<fmt:formatDate value='${orderDetail.end_date}' pattern='yyyy-MM-dd HH:mm:ss'/>");
    	$("#beginDate").val("<fmt:formatDate value='${orderDetail.begin_date}' pattern='yyyy-MM-dd HH:mm:ss'/>");
    	$('.time button').each(function(){
    		if($(this).val()==scanType){
    			$(this).addClass('clickTime').siblings().removeClass('clickTime');	
    		}
		});
	}
	//回显选中的资产
	var assetIds = $("#assetIds").val();
	var assetNames = $("#assetNames").val();
	
	var arrLink = assetNames.split(',');	
	var arrId = assetIds.split(',');
	
	$('.gt').hide();
	$('.httpBox').show();
	var list='';
	var index=0;
	for(var i=0;i<arrLink.length;i++){
		 index++;
		 if(arrId[i]!=null&&arrId[i]!=''&&arrLink[i]!=null&&arrLink[i]!=''){
		 	list+="<li id="+ index +" assetId='"+arrId[i]+"'>"+ arrLink[i] +"<i></i></li>";  
		 }		 
	}
	
	$('.httpBox').append(list);
	
	var tleng= $('.httpBox li').length;
	if(tleng==0){
		$('.gt').show();		
	}

    $("#price").html("${orderDetail.price}");
    
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$('#us').hide();
$("#beginDate").click(function(){
$("#us").show("slow");
$("#beginDate").blur(function(){
$("#us").hide("slow");
});
});
})
</script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<style>
.dataBox .dataR .select .dropdown-menu li label{ width:86%;}
      .select .dropdown-menu li{ height:auto !important}
       .select .dropdown-menu li label input{ margin-left:-20px;}
 .select .dropdown-menu li label span{ padding-left:10px; top:-3px;}
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
		<input type="hidden" id="serviceId" value="${orderDetail.serviceId }"/>
		<input type="hidden" id="indexPage" value="${indexPage }"/>
		<input type="hidden" id="orderType" value="${servDetail.servType}"/>
		<input type="hidden" id="scanType" value="${orderDetail.scan_type }"/>
		<input type="hidden" id="assetIds" value="${orderDetail.asstId}"/>
		<input type="hidden" id="assetAddr" value="${orderDetail.assetAddr}"/>
		<input type="hidden" id="assetNames" value="${orderDetail.assetName}"/>
		<input type="hidden" id="timesHidden" value=""/>
		<input type="hidden" id="begin" value="<%=str_date%>"/>
		<!-- 资产个数 -->
		<input type="hidden" id="AssetCount" value="${AssetCount}"/>
		<div class="dataCent">
			<div class="data-crumbs">
				<a href="${ctx}/index.html" style="font-size: 20px;">安全帮</a><i>&gt;</i><a href="${ctx}/web_anquanbang.html">网站安全帮</a><i>&gt;</i><a href="javascript:;">${service.name }</a>
			</div>
			<div class="dataBox clearfix">
				<div class="dataL fl">
					<div class="dataImg fl">
						<img src="${ctx}/source/images/serviceIcon/${service.detailIcon }" alt="" style="width:420px;height:420px;"/>
						<!-- <img src="${ctx}/source/images/portal/product.png" alt="" /> -->
					</div>
				</div>
				<div class="dataR detailsR fl" style="width:640px;">
					<h2 style="font-size:20px; margin-bottom:18px;">${service.name }</h2>
                  <a href="javascript:showShopCar();" class="buttoncar" style="right:0px;"><b id="shopCarNum">${carnum}</b><i></i>我的购物车&gt;</a>
					<ul>
						<li class="clearfix">
							<!-- <label class="fl">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</label> -->
							<label class="fl">${servDetail.priceTitle }</label>
                            <div class="fl price">
	                            <strong id="price">0</strong>
	                            <strong></strong>
                            </div>
                            <span style="position: relative;top: 7px;left:7px;color:#d00000">安全币</span>
                            <span style="position: relative;top: 7px;left:7px">（推广初期价格）</span>
						</li>
						 <li class="clearfix type">
							<!-- <label class="fl">选 类型</label> -->
							<label class="fl">${servDetail.typeTitle }</label>
                            <div class="fl clickBox" id="clickBox">
	                            <button class="click Single" value="2" id="singleBtn" onclick="calPrice(null,null);">单次</button>
	                            <button class="long" value="1" id="longBtn" onclick="calPriceLong(null,null,null)">长期</button>
                            </div> 
						</li>
						<li class="clearfix" style="height:30px;margin-left: 160px;" id="us">建议选择网站业务空闲时间</li>
						<li class="clearfix">
							<label class="fl">服务时间</label>
                            <div class="fl" style="top:3px;">
                            	<span class="start">开始时间 <input type="text" style="width:156px;" class="text" value="" id="beginDate" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'<%=str_date%>',startDate:'<%=str_date%>',dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(dp){calPriceLong(null,null,null); }})"/></span>
                                <span class="end" style="display:none; margin-right:0px;">结束时间 <input type="text" style="width:156px;" class="text" value="" id="endDate" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'<%=str_date%>',startDate:'<%=str_date%>',dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(dp){calPriceLong(null,null,null); }})"/></span>
                            </div>
						</li>
						<li class="clearfix time">
							<!-- <label class="fl">服务频率</label> -->
							<label class="fl">${servDetail.ratesTitle }</label>
							
                            <div class="fl clickBox" id="time">
                            <input type="hidden" id="serviceIdHidden" value="${service.id}"/>
                              <c:forEach items="${scanTypeList}" var="scanType" varStatus="status">
                               <c:if test="${status.index==0}">
	                             <button class="clickTime" value="${scanType.scan_type}" onclick="calPriceLong(this,null,null)">${scanType.scan_name}</button>
	                             </c:if>
	                               <c:if test="${status.index!=0}">
	                               <button value="${scanType.scan_type}" onclick="calPriceLong(this,null,null)">${scanType.scan_name}</button>
	                               </c:if>
	                             </c:forEach>
	                        
	                          </div> 
                            
					   </li>
						
						   <li class="clearfix waf-datails" style="height:auto">
                            	<label class="fl">网&nbsp;&nbsp;&nbsp;&nbsp;站</label>
                            <div class="fl" style="top:10px;">
                                <div class="fl" style="color:#a3a3a3; margin-right:30px;">
                                    <b class="gt">尚未选择网站</b>
                                    <ul class="httpBox clearfix" style="max-width:366px; position:relative; top:-8px; display:none;">
                                        <!--<li class="aBtn"><em>安全帮</em><i></i></li>-->
                                        <!--此处添加-->
                                    </ul>
                                </div>
                                
                            </div>
                           
                            <a href="javascript:;" id="addhttp" class="href_btn fl" style="color:#2499fb; position:relative; top:10px;">点击此处选择网站</a>
                            
						</li>
					</ul>
                    <div class="btnBox" style="text-align:left; margin-left:0px;">
                    	<button style="background:#d00000; width:146px;" id="addCar">添加到购物车</button>
                        <button style="background:#5aba5f; width:126px" id="buyNow">立即购买</button>
                    </div>
				</div>
                
				
			</div>
		</div>
        <div class="commodity">
        	<div class="imgBox clearfix">
            	<h4>商品信息</h4>
            	<c:forEach var="detailImage" items="${detailImages}" varStatus="status">
	            	<c:if test="${detailImage != null && detailImage != ''}">
	            		<div class="commoditys" style="overflow:hidden">
	            			<img src="${ctx}/source/images/serviceDetail/${detailImage }" alt=""/>
	            		</div>
	            	</c:if>
            	</c:forEach>
                <!-- <div class="commoditys" style="height:618px; overflow:hidden">
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
                </div> -->
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


 <div class="waf-detais-pop flaw popBoxhide">
        <i class="close chide waf-off" id="close"></i>
        <div class="Pophead">
            <h2>请选择要服务的网站</h2>
            <a href="#" class="ass "><i><img src="${ctx}/source/images/add.png" alt=""></i>新增网站</a>

        </div>
    
  <script>
            $(function() {
            	
                $('.ass').click(function() {
               		$('#saveAsset')[0].reset();		
                    $('#senone').hide(1);
                    $('#sentwo').show(1);
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
                    
                });
                $('#back').click(function() {
                    $('#sentwo').hide(1);
                    $('#senone').show(1);
                });


                $('.waf-ym i').click(function() {
                    $('.waf-ym i').removeClass('this');
                    $(this).addClass('this');
                })
            })
        </script>
	<div class="popBox">
    	<!--请选择要服务的网站内容显示-->
        <div class="centone" id="senone" style="display:block;">
	        <ul class="allBox">
		        <c:forEach items="${serviceAssetList}" var="asset"  varStatus="status">
		        	<li>
		            	<div class="rcent">
                            <h3>
                                <c:if test="${asset.status == '0' }">
                                      <label for="${status.count }" style="margin:0 16px 0 0;">
	                                       <span style="padding-left:40px;padding-top:20px">${asset.name }</span>
		                              </label>
		                        </c:if>
		                        
		                        <c:if test="${asset.status == '1' }">
	                                <label for="${status.count }" style="margin:0 16px 0 0;">
			                                 <input type="checkbox" class="ck"  value="${asset.id}" style=" display:none;"/><i class="cek" data-id="${status.count }" onclick='selAsset(this)'></i>
	                                 </label>
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
             <div class="bottom clearfix" style="float:none">
                    <div class="bl fl">
                        <!--<label><i class="cheall" style="margin-right:20px;"></i>全选</label>-->
                        <span>已选择<em id="number">0</em>个网站</span>
                    </div>
                    <a href="javascript:;" class="btn ok fr">确定</a>
                </div>
        
        </div>
        <!--新增网站内容显示-->
        <div class="centone" id="sentwo" style="display:none;">
        <form id="saveAsset" action="" method="post">
        			<div class="addhttp">
                        <div class="popBox">
                            <ul style="height:auto;">
                                <li class="clearfix">
                                    <label class="fl">网站名称</label> <div class="fl"><input class="boz_inout_1" type="text" name="name" id="assetName" maxlength="20"></div>
                                    <div class="addMsg" style="color:#e32929;text-align:left;font-size: 14px;" id="assetName_msg"></div>
                                </li>
                                <!-- <li class="clearfix">
                                    <label class="fl">网站地址类型</label> 
                                    <div class="fl">
                                        <label><input type="radio" name="addrType" checked="checked" id="assetAddrType1" value="http">http</input></label>
                                        <label style="margin-left:10px;"><input type="radio" name="addrType" id="assetAddrType2" value="https">https</label>
                                    </div>
                                </li> --> 
                                <li class="clearfix">
                                    <label class="fl">网站地址</label> <div class="fl"><input class="boz_inout_1" type="text" name="addr" id="InertAddr"  maxlength="100" onkeyup="value=value.replace(/[\u4E00-\u9FA5]/g,'')"></div>
                                    <div class="addMsg" style="color:#e32929;text-align:left;font-size: 14px;" id="assetAddr_msg"></div>
                                </li>
                                 <li class="clearfix">
                                    <label class="fl">示例</label> 
                                    <div class="fl">
                                        <p>http://xxx.xxx.xxx.xxx</p>
                                        <p>https://xxx.xxx.xxx.xxx:1234</p>
                                        <p>http://xxx.xxx.xxx.xxx:8080/home</p>
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
                                <a href="javascript:;" class="btn sub" style="margin:0 20px 0 30px;" onclick="saveAsset()">提交</a>
                                <a href="javascript:;" class="btn sub return" id="back">返回</a>
                            </div>
        
        </form>
        
        </div>
        	
   	</div>
	
</div>

	
<div class="shade"></div>
</body>


</html>