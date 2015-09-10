<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
<title>用户中心-我的账单</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/billDetail.js"></script>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
$.ajaxSetup({
    cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	$("#type").val("${type}");
	$("#servName").val("${servName}");
	$("#begin_date").val("${begin_date}");
	$("#end_date").val("${end_date}");
});

function searchCombine(){
     $("#searchForm").submit();
}
</script>
</head>

<body>

<div class="head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
    <div class="lagst">
      <div class="lagst-left"> <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a> </div>
      <div class="lagst-right">
         <p><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
        <p><a href="${ctx}/exit.html">退出</a></p>
      </div>
    </div>
    <div class="list">
      <ul>
        <ul>
           <li><a href="${ctx}/index.html">首页</a></li>
           <li><a href="${ctx}/chinas.html">安全态势</a></li>
          <li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
          <li><a href="${ctx}/aider.html">在线帮助</a></li>
          <li class="list_active" style="border-right:1px solid #1369C0;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
        </ul>
      </ul>
    </div>
  </div>
</div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <div class="user_left">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center;"><a  style="color:#4593fd; " href="${ctx}/userCenterUI.html">用户中心</a></li>
      <li><a href="${ctx}/userDataUI.html">基本资料</a></li>
      <li class="active"><a href="${ctx}/userBillUI.html">我的账单</a></li>
      <li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
      <h2>订购中心</h2>
      <li><a href="${ctx}/selfHelpOrderInit.html">自助下单</a></li>
      <li><a href="${ctx}/orderTrackInit.html">订单跟踪</a></li>
    </ul>
  </div>
  
  <!-- <jsp:include page="left.jsp"/> -->
  
  <!--我的账单-->
  <div class="user_right" >
  <form action="${ctx}/searchCombine.html" method="post" id="searchForm">
    <input type="hidden" id="mark" value="${mark}"/>
    <input type="hidden" id="typepage" value="${type}"/>
    <input type="hidden" id="servNamepage" value="${servName}"/>
    <input type="hidden" id="begin_datepage" value="${begin_date}"/>
    <input type="hidden" id="end_datepage" value="${end_date}"/>
    <div class="user_top">
      <div class="user_sec_cont">
         	<select id="type" name="type" class="user_secta spiclesel">
         		<option selected="selected" value="">请选择类型</option>
         		<option value="1" >长期</option>
         		<option value="2" >单次</option>
    		</select>
      </div>
      <div class="user_sec_cont" style=" left:196px; ">
         <select id="servName" name="servName" class="user_secta spiclesel">
        	<option selected="selected" value="">请选择服务</option>
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
      <div class="dan_3 user_sectime1" style="left:344px;">
          <input type="text" value="" id="begin_date" name="begin_datevo" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
      </div>
        <div style="left:520px; position:absolute;">--</div>
        <div class="dan_4 user_sectime1" style="left:552px;">
          <input type="text" value="" id="end_date" name="end_datevo"  onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
        </div>
      <div class="user_soucuo" style="left:764px;"><img src="${ctx}/source/images/user_submit_2.jpg" onclick="searchCombine()"/></div>
    </div>
   </form>
    <div class="zhangd_table" id="content_data_div">
      <table id="billTab">
        <tbody>
	          <tr style="background:#e0e0e0; height:30px; line-height:30px;">
	            <td style="width:16%;">订单编号</td>
	            <td  style="width:7%;">订单类型</td>
	            <td  style="width:13%;">订单服务</td>
	            <td  style="width:36%;">服务起止时间</td>
	            <td  style="width:15%;">下单时间</td>
	            <td  style="width:10%;"></td>
	          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<!-- 尾部代码开始-->
<div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
    <li><a href="${ctx}/registUI.html">新用户注册</a></li>
    <li><a href="${ctx}/loginUI.html">用户登录</a></li>
    <li><a href="###">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
   <h3><a href="###"> 帮助</a></h3>
  <ul>
     <li><a href="${ctx}/aider.html">常见问题</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###">厂商合作</a></h3>
  <ul>
    <li><a href="###">华为</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">知道创宇</a></li>
  </ul>
</div>
<div  class="bottom_main">
<h3><a href="###">联系我们</a></h3>
<ul>
<li><a href="###">QQ交流群470899318</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>
 <ul>
 <li>Copyright&nbsp;©&nbsp;2015 中国电信股份有限公司北京研究院<br />
京ICP备12019458号－10</li>
</div>
</div>
</div>

</body>
</html>
