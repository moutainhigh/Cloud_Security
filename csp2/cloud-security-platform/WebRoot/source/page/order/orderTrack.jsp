<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>
<%
    response.setHeader("Pragma","No-cache"); 
    response.setHeader("Cache-Control","no-cache"); 
    response.setDateHeader("Expires", -10); 
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>用户中心-订单跟踪</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/tableDetail.js"></script>

<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<!-- add by 2016-02 -->
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<!-- end -->
<script type="text/javascript" src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/order.js"></script>
<script type="text/javascript">
$.ajaxSetup({
    cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
    $("#type").val("${type}");
    $("#servName").val("${servName}");
    $("#state").val("${state}");
    $("#begin_date").val("${begin_date}");
    $("#end_date").val("${end_date}");
});
    function searchCombine(){
     $("#searchForm").submit();
     
}
function overfind(orderId){
        var obj = {'orderId':orderId};
        $.post("getAssetName.html", obj, function(data){
        	var assetName = data.assetName;
        	$('#'+orderId).attr("title", assetName);//设置title属性的值
        });
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
                	window.location.href="deleteOrder.html?orderId="+orderId;
                }
            },
         });
    } else {
        return;
    }
}


//订单操作
function optOrder(orderId,status){
	var meg = "";
	if(status==4){
		meg = "确实要暂停吗?";
	}else{
		meg = "确实要启动吗?";
	}
    if (window.confirm(meg)==true) {
    	var obj = {'orderId':orderId, 'status':status};
        $.post("optOrder.html", obj, function(data){
        	if (!data.status){
				alert("系统异常，暂停或启动失败！");
			}else{
				window.location.href="orderTrackInit.html";
			}
        });
    } else {
        return;
    }
}
</script>
</head>

<body>
<!--头部-->
<div class="safe01 detalis-head">
	<!--头部-->
	<div class="head" style="width:100%">
		<div class="headBox">
			<div class="safeL fl" style="width:260px; margin-right:13%">
				<img src="${ctx}/source/images/portal/newlogo-footer.png" alt="" style="position:relative; top:4px;"/>
			</div>
			<div class="safem fl">
				<span class="fl"><a href="${ctx}/index.html">首页</a></span>
				<div class="Divlist listJs fl">
					<a href="#" class="this">我的安全帮<i></i></a>
							<ul class="list listl">
								<li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
								<li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
								<li style="border: none;"><a href="${ctx}/userDataUI.html">个人信息</a></li>
							</ul>
				</div>
				<span class="fl ask">
					<a href="#" class="hbule">手机APP</a>
					<b style="display:none"><img src="${ctx}/source/images/portal/apk.png" alt=""></b>
				</span>
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
<!-- 头部代码结束-->
<div class="user_center clear">
  <div class="user_left">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center;"><a  style="color:#4593fd; " href="${ctx}/userCenterUI.html">用户中心</a></li>
      <li><a href="${ctx}/userDataUI.html">基本资料</a></li>
      <li><a href="${ctx}/userBillUI.html">我的账单</a></li>
      <li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
      <h2>订购中心</h2>
      <li><a onclick="tasknum_verification()" href="javascript:void(0)">自助下单</a></li>
      <li class="active"><a href="${ctx}/orderTrackInit.html">订单跟踪</a></li>
    </ul>
  </div>
  
  <!-- <jsp:include page="../userCenter/left.jsp"/> -->
  
  <!-- 订单跟踪-->
  <div class="user_right" >
  <form action="${ctx}/searchCombineOrderTrack.html" method="post" id="searchForm">
    <input type="hidden" id="mark" value="${mark}"/>
    <input type="hidden" id="typepage" value="${type}"/>
    <input type="hidden" id="servNamepage" value="${servName}"/>
    <input type="hidden" id="statepage" value="${state}"/>
    <input type="hidden" id="begin_datepage" value="${begin_date}"/>
    <input type="hidden" id="end_datepage" value="${end_date}"/>
    <div class="user_top">
      <div class="user_sec_cont">
            <select class="user_secta spiclesel" id="type" name="type">
                <option selected="selected" value="">请选择类型</option>
                <option value="1" >长期</option>
                <option value="2" >单次</option>
            </select>
      </div>
      <div class="user_sec_cont" style=" left:172px; ">
         <select class="user_secta spiclesel" id="servName" name="servName">
            <option selected="selected " value="">请选择服务</option>
            <option value="1" >漏洞扫描服务</option>
            <option value="2" >恶意代码监测服务</option>
            <option value="3" >网页篡改监测服务</option>
            <option value="4" >关键字监测服务</option>
            <option value="5" >可用性监测服务</option>
            <option value="6" >日常流量监测服务</option>
            <option value="7" >日常攻击防护服务</option>
            <option value="8" >突发异常流量清洗服务</option>
        </select>
      </div>
      
      <div class="user_sec_cont" style=" left:302px; ">
         <select class="user_secta spiclesel" id="state" name="state">
            <option selected="selected" value="">请选择状态</option>
            <option value="3" >已下单</option>
            <option value="1" >服务中</option>
            <option value="2" >已结束</option>
        </select>
      </div>
      <div class="dan_3 user_sectime1">
          <input type="text" value="" id="begin_date" name="begin_datevo" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
        </div>
        <div style="left:600px; position:absolute;">--</div>
        <div class="dan_4 user_sectime1">
          <input type="text" value="" id="end_date" name="end_datevo" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
        </div>
      <div class="user_soucuo"><img src="${ctx}/source/images/user_submit_2.jpg" onclick="searchCombine()"/></div>
    </div>
   </form>
    <div class="zhangd_table" id="content_data_div">
      <table id="orderTab">
      
              <tr style="background:#e0e0e0; height:30px; line-height:30px;">
                <td style="width:16%;" onclick="overfind('1245504423')">订单编号</td>
                <td  style="width:7%;">订单类型</td>
                <td  style="width:8%;">订单状态</td>
                <td  style="width:13%;">订单服务</td>
                <td  style="width:34%;">服务起止时间</td>
                <td  style="width:15%;">下单时间</td>
                <td  style="width:11%;"></td>
              </tr>
      </table>
    </div>
  </div>
</div>
<!-- 尾部代码开始-->
<div class="safeBox">
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="#"><img src="${ctx}/source/images/portal/footlogo.png" alt=""></a>
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd><a href="#">购买指南</a></dd>
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
                        	<dd><a href="#">QQ交流群</br>470899318</a></dd>
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
</div>
<!-- 尾部代码结束 -->
</body>
</html>
