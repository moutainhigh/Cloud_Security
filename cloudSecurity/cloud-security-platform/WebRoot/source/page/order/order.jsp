<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>用户中心-自助下单</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery-1.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/order.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>

</head>

<body>

<!--头部-->
<div class="head">
	<div class="headBox">
		<div class="safeL fl">
			<div class="logo">
			<img src="${ctx}/source/images/portal/logo.png" alt=""/><b></b><span>网站安全帮</span>
			</div>
		</div>
		
		<div class="safem fl">
			<span class="fl"><a href="${ctx}/index.html">首页</a></span>
			<div class="Divlist listJs fl">
				<a href="#">我的安全帮<i></i></a>
				<ul class="list listl">
					<li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
					<li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
					<li style="border: none;"><a href="${ctx}/userDataUI.html">个人信息</a></li>
				</ul>
			</div>
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
<!-- 头部代码结束-->
<div class="user_center clear">
  <div class="user_left">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center;"><a  style="color:#4593fd; " href="${ctx}/userCenterUI.html">用户中心</a></li>
      <li><a href="${ctx}/userDataUI.html">基本资料</a></li>
      <li><a href="${ctx}/userBillUI.html">我的账单</a></li>
      <li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
      <h2>订购中心</h2>
      <li class="active"><a href="${ctx}/selfHelpOrderInit.html">自助下单</a></li>
      <li><a href="${ctx}/orderTrackInit.html">订单跟踪</a></li>
    </ul>
  </div>
  
  <!-- <jsp:include page="../userCenter/left.jsp"/> -->
  
  <!--自助下单-->
  <input type="hidden" id="type" value="${type }"/>
  <input type="hidden" id="serviceId" value="${serviceId }"/>
  <input type="hidden" id="indexPage" value="${indexPage }"/>
  <div class="user_right" >
    <div class="ding_nav">
      <div style="border-top:1px solid #e0e0e0; width:800px; position:absolute; left:75px; top:111px;"></div>
      <ul>
        <li class="ding_active" style="left:75px; top:66px;"><a href="###">订单类型</a></li>
        <li style="left:212px; top:66px;" ><a href="###">服务配置</a></li>
        <li style="left:349px; top:66px;"><a href="###">联系信息</a></li>
        <li style="left:486px; top:66px;"><a href="###">确认订单</a></li>
        <li style="left:623px; top:66px;"><a style="right:-5px;" href="###">完成</a></li>
      </ul>
    </div>
    <!--订单类型-->
    <div class="ding_center" style="display:block;">
      <div class="dan_con">
        <div class="dan_1"> 订单类型&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="orderType" value="2" checked/>
          &nbsp;&nbsp;单次&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="orderType" value="1"/>
          &nbsp;&nbsp;长期 </div>
        <div >订单起止时间</div>
        <div class="dan_2"> 开始时间&nbsp; &nbsp;
          <input type="text" value="" id="beginDate" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/><font id="beginDate_msg" style="color:red;float:right"></font>
        </div>
        <div class="dan_2"> 结束时间&nbsp; &nbsp;
          <input type="text" value="" id="endDate" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/><font id="endDate_msg" style="color:red;float:right"></font>
        </div>
      </div>
      <div style=" margin-top:50px; margin-left:410px; margin-bottom:50px;">
	      <a href="###">
	      <img src="${ctx}/source/images/user_ico_11.jpg" id="firstStep"/></a>
      </div>
    </div>
    <div  class="ding_center">
      <div class="peiz_nav">
        <ul class="pei_ul_1">
          <div class="pei_ul_txt"><a href="###">类型</a></div>
          <li class="pei_active"><a href="###">全部</a></li>
          <c:forEach var="list" items="${typeList}" varStatus="status">
	          <li name="${list.id}"><a href="###">${list.name }</a></li>
          </c:forEach>
        </ul>
        <ul class="pei_ul_2">
          <div class="pei_ul_txt"><a href="###">厂商</a></div>
          <li class="pei_active"><a href="###">全部</a></li>
          <c:forEach var="list" items="${factoryList}" varStatus="status">
            <c:if test="${status.index ==0 || status.index ==1 || status.index ==3}">
                <li name="${list.factory}"><a href="###">${list.factory }</a></li>
            </c:if>
            <c:if test="${status.index ==2}">
                <div style="float:left; width:75px; height:22px;  margin-left:35px; text-align:center; font-size:14px; line-height:22px;" name="${list.factory}"><a href="###" style="color:#999;">${list.factory }</a></div>
            </c:if>
              
          </c:forEach>
        </ul>
        <ul class="pei_ul_3">
          <div class="pei_ul_txt"><a href="###">服务</a></div>
          <li class="pei_active"><a href="###">全部</a></li>
          <li name="1"><a href="###">WEB</a></li>
          <li name="2"><a href="###">Anti-DDOS</a></li>
        </ul>
      </div>
      <div class="peiz_center">
        <ul>
          <c:forEach var="list" items="${servList}" varStatus="status">
            <c:choose>
               <c:when test="${status.first}">
                   <li class="peiz_active" id="${list.id }" name="${list.name }" value="${list.parentC }">
                   <input type="hidden" value="${list.remarks }" name="remarks"/>
                   <input type="hidden" value="${list.orderType }" name="typeOrder"/>
                   <div><img src="${ctx}/source/images/user_${status.index+1 }.png" /></div>
                   <a class="peiz_w" href="###">${list.name }</a> </li>
               </c:when>
               <c:otherwise>
                   <li id="${list.id }" name="${list.name }" value="${list.parentC }">
                   <input type="hidden" value="${list.remarks }" name="remarks"/>
                   <input type="hidden" value="${list.orderType }" name="typeOrder"/>
                   <div><img src="${ctx}/source/images/user_${status.index+1 }.jpg" /></div>
                   <a class="peiz_b" href="###">${list.name }</a> </li>
               </c:otherwise>
            </c:choose>
          </c:forEach>
        </ul>
      </div>
      <div id="servicePage">
      <!-- 漏洞扫描服务-->
      <div class="peiz_cont" style="display:block;">
        <div style="position: absolute; top:-10px;"><img src="${ctx}/source/images/user_ico.jpg" /></div>
        <div class="clear">
          <h3>服务对象</h3>
          <div class="peiz_table">
            <table class="leftTr0">
              <tr style="background:#e0e0e0;">
                <td style="width:25%;">资产名称 </td>
                <td style="width:60%;">资产地址 </td>
                <td style="width:15%;"><input type="checkbox" class="checkItems"/></td>
              </tr>
              <c:if test="${empty serviceAssetList}">
                <tr>
                  <td colspan="3" style="color:#4593fd;">没有可选资产,请到"<a href="${ctx}/userAssetsUI.html" style="color:#4593fd;font-weight:bold;text-decoration:underline;">用户中心-我的资产</a>"菜单新增资产,并通过验证!</td>
                </tr>
              </c:if>
              <c:if test="${not empty serviceAssetList}">
	              <c:forEach var="list" items="${serviceAssetList}" varStatus="status">
		              <tr>
		                <input type="hidden" value="${list.id }" id="assetId" name="assetId"/>
		                <td>${list.name }</td>
		                <td>${list.addr}</td>
		                <td><input type="checkbox" name="serviceAssetId" value="${list.id }"/></td>
		              </tr>
	              </c:forEach>
              </c:if>
                            
            </table>
          </div>
          <!--<div class="peiz_ico"><img src="${ctx}/source/images/peiz_ico.jpg" class="to_right"/></div>
          <div class="peiz_table">
            <table class="rightTr0">
              <tr style="background:#e0e0e0;">
                <td style="width:30%;">资产名称 </td>
                <td style="width:25%;">资产类型 </td>
                <td style="width:30%;">资产地址 </td>
                <td style="width:15%;"></td>
              </tr>
            </table>
          </div> -->
        </div>
        <div><font class="assets_msg" style="color:red;float:right"></font></div>
        <div class="pinv">
          <h3>扫描频率</h3>
          <div class="pinv_subnav">
            <ul class="pinv_sub_nav">
              <!-- <li class="pinv_active">
                <input type="radio" name="scanType0" value="1" checked/>
                &nbsp;&nbsp;每天</li> -->
              <li>
                <input type="radio" name="scanType0" value="2" checked/>
                &nbsp;&nbsp;每周</li>
              <li>
                <input type="radio" name="scanType0" value="3"/>
                &nbsp;&nbsp;每月</li>
            </ul>
          </div>
        </div>
      </div>
      <!-- 恶意代码监测服务-->
      <div class="peiz_cont">
        <div style="position: absolute; top:-10px; left:180px;"><img src="${ctx}/source/images/user_ico.jpg" /></div>
        <div class="clear">
          <h3>服务对象</h3>
          <div class="peiz_table">
            <table class="leftTr1">
              <tr style="background:#e0e0e0;">
                <td style="width:25%;">资产名称 </td>
                <td style="width:60%;">资产地址 </td>
                <td style="width:15%;"><input type="checkbox" class="checkItems"/></td>
              </tr>
              
              <c:if test="${empty serviceAssetList}">
                <tr>
                  <td colspan="3" style="color:#4593fd;">没有可选资产,请到"<a href="${ctx}/userAssetsUI.html" style="color:#4593fd;font-weight:bold;text-decoration:underline;">用户中心-我的资产</a>"菜单新增资产,并通过验证!</td>
                </tr>
              </c:if>
              <c:if test="${not empty serviceAssetList}">
	              <c:forEach var="list" items="${serviceAssetList}" varStatus="status">
		              <tr>
		                <input type="hidden" value="${list.id }" id="assetId" name="assetId"/>
		                <td>${list.name }</td>
		                <td>${list.addr}</td>
		                <td><input type="checkbox" name="serviceAssetId" value="${list.id }"/></td>
		              </tr>
	              </c:forEach>
              </c:if>
            </table>
          </div>
          <!--<div class="peiz_ico"><img src="${ctx}/source/images/peiz_ico.jpg" class="to_right"/></div>
          <div class="peiz_table">
            <table class="rightTr1">
              <tr style="background:#e0e0e0;">
                <td style="width:30%;">资产名称 </td>
                <td style="width:25%;">资产类型 </td>
                <td style="width:30%;">资产地址 </td>
                <td style="width:15%;"></td>
              </tr>
            </table>
          </div>-->
        </div>
        <div><font class="assets_msg" style="color:red;float:right"></font></div>
        <div class="pinv">
          <h3>扫描频率</h3>
          <!-- <div class="pinv_subnav">
            <ul class="pinv_sub_nav" style="height:30px;">
              <li class="pinv_active">
                <input style="background: url(${ctx}/source/images/user_ico_8.jpg) no-repeat; width:176px; height:30px;padding-left:4px;" type="text" value="" name="scanType1" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
              </li>
            </ul>
          </div> -->
          
          <div class="pinv_subnav">
            <ul class="pinv_sub_nav">
              <li class="pinv_active">
                <input type="radio" name="scanType1" value="1" id="30分钟" checked/>
                &nbsp;&nbsp;30分钟</li>
              <!-- <li>
                <input type="radio" name="scanType1" value="2" id="1小时"/>
                &nbsp;&nbsp;1小时</li>
              <li>
                <input type="radio" name="scanType1" value="3" id="2小时"/>
                &nbsp;&nbsp;2小时</li>
              <li>
                <input type="radio" name="scanType1" value="4" id="1天"/>
                &nbsp;&nbsp;1天</li> -->
            </ul>
          </div>
        </div>
      </div>
      <!-- 网页篡改监测服务-->
      <div class="peiz_cont">
        <div style="position: absolute; top:-10px; left:300px;"><img src="${ctx}/source/images/user_ico.jpg" /></div>
        <div class="clear">
          <h3>服务对象</h3>
          <div class="peiz_table">
            <table class="leftTr2">
              <tr style="background:#e0e0e0;">
                <td style="width:25%;">资产名称 </td>
                <td style="width:60%;">资产地址 </td>
                <td style="width:15%;"><input type="checkbox" class="checkItems"/></td>
              </tr>
              <c:if test="${empty serviceAssetList}">
                <tr>
                  <td colspan="3" style="color:#4593fd;">没有可选资产,请到"<a href="${ctx}/userAssetsUI.html" style="color:#4593fd;font-weight:bold;text-decoration:underline;">用户中心-我的资产</a>"菜单新增资产,并通过验证!</td>
                </tr>
              </c:if>
              <c:if test="${not empty serviceAssetList}">
	              <c:forEach var="list" items="${serviceAssetList}" varStatus="status">
		              <tr>
		                <input type="hidden" value="${list.id }" id="assetId" name="assetId"/>
		                <td>${list.name }</td>
		                <td>${list.addr}</td>
		                <td><input type="checkbox" name="serviceAssetId" value="${list.id }"/></td>
		              </tr>
	              </c:forEach>
              </c:if>
            </table>
          </div>
          <!--<div class="peiz_ico"><img src="${ctx}/source/images/peiz_ico.jpg" class="to_right"/></div>
          <div class="peiz_table">
            <table class="rightTr2">
              <tr style="background:#e0e0e0;">
                <td style="width:30%;">资产名称 </td>
                <td style="width:25%;">资产类型 </td>
                <td style="width:30%;">资产地址 </td>
                <td style="width:15%;"></td>
              </tr>
            </table>
          </div>-->
        </div>
        <div><font class="assets_msg" style="color:red;float:right"></font></div>
        <div class="pinv">
          <h3>扫描频率</h3>
          <div class="pinv_subnav">
            <ul class="pinv_sub_nav">
              <!-- <li class="pinv_active">
                <input type="radio" name="scanType2" value="1" id="30分钟" checked/>
                &nbsp;&nbsp;30分钟</li>
              <li>
                <input type="radio" name="scanType2" value="2" id="1小时"/>
                &nbsp;&nbsp;1小时</li>
              <li>
                <input type="radio" name="scanType2" value="3" id="2小时"/>
                &nbsp;&nbsp;2小时</li> -->
              <li>
                <input type="radio" name="scanType2" value="4" id="1天" checked/>
                &nbsp;&nbsp;1天</li>
            </ul>
          </div>
        </div>
      </div>
      <!-- 关键字监测服务-->
      <div class="peiz_cont">
        <div style="position: absolute; top:-10px; left:420px;"><img src="${ctx}/source/images/user_ico.jpg" /></div>
        <div class="clear">
          <h3>服务对象</h3>
          <div class="peiz_table">
            <table class="leftTr3">
              <tr style="background:#e0e0e0;">
                <td style="width:25%;">资产名称 </td>
                <td style="width:60%;">资产地址 </td>
                <td style="width:15%;"><input type="checkbox" class="checkItems"/></td>
              </tr>
              <c:if test="${empty serviceAssetList}">
                <tr>
                  <td colspan="3" style="color:#4593fd;">没有可选资产,请到"<a href="${ctx}/userAssetsUI.html" style="color:#4593fd;font-weight:bold;text-decoration:underline;">用户中心-我的资产</a>"菜单新增资产,并通过验证!</td>
                </tr>
              </c:if>
              <c:if test="${not empty serviceAssetList}">
	              <c:forEach var="list" items="${serviceAssetList}" varStatus="status">
		              <tr>
		                <input type="hidden" value="${list.id }" id="assetId" name="assetId"/>
		                <td>${list.name }</td>
		                <td>${list.addr}</td>
		                <td><input type="checkbox" name="serviceAssetId" value="${list.id }"/></td>
		              </tr>
	              </c:forEach>
              </c:if>
            </table>
          </div>
          <!--<div class="peiz_ico"><img src="${ctx}/source/images/peiz_ico.jpg" class="to_right"/></div>
          <div class="peiz_table">
            <table class="rightTr3">
              <tr style="background:#e0e0e0;">
                <td style="width:30%;">资产名称 </td>
                <td style="width:25%;">资产类型 </td>
                <td style="width:30%;">资产地址 </td>
                <td style="width:15%;"></td>
              </tr>
            </table>
          </div>-->
        </div>
        <div><font class="assets_msg" style="color:red;float:right"></font></div>
        <div class="pinv">
          <h3>扫描频率</h3>
          <!-- <div class="pinv_subnav">
            <ul class="pinv_sub_nav">
              <li class="pinv_active">
                <input style="background: url(${ctx}/source/images/user_ico_8.jpg) no-repeat; width:176px; height:30px;padding-left:4px;" type="text" value="" name="scanType3" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
              </li>
            </ul>
          </div> -->
          <div class="pinv_subnav">
            <ul class="pinv_sub_nav">
              <!--<li class="pinv_active">
                <input type="radio" name="scanType3" value="1" id="30分钟" checked/>
                &nbsp;&nbsp;30分钟</li>
               <li>
                <input type="radio" name="scanType3" value="2" id="1小时"/>
                &nbsp;&nbsp;1小时</li>
              <li>
                <input type="radio" name="scanType3" value="3" id="2小时"/>
                &nbsp;&nbsp;2小时</li>-->
              <li>
                <input type="radio" name="scanType3" value="4" id="1天" checked/>
                &nbsp;&nbsp;1天</li> 
            </ul>
          </div>
        </div>
      </div>
      <!-- 可用性监测服务-->
      <div class="peiz_cont">
        <div style="position: absolute; top:-10px; left:540px;"><img src="${ctx}/source/images/user_ico.jpg" /></div>
        <div class="clear">
          <h3>服务对象</h3>
          <div class="peiz_table">
            <table class="leftTr4">
              <tr style="background:#e0e0e0;">
                <td style="width:25%;">资产名称 </td>
                <td style="width:60%;">资产地址 </td>
                <td style="width:15%;"><input type="checkbox" class="checkItems"/></td>
              </tr>
              <c:if test="${empty serviceAssetList}">
                <tr>
                  <td colspan="3" style="color:#4593fd;">没有可选资产,请到"<a href="${ctx}/userAssetsUI.html" style="color:#4593fd;font-weight:bold;text-decoration:underline;">用户中心-我的资产</a>"菜单新增资产,并通过验证!</td>
                </tr>
              </c:if>
              <c:if test="${not empty serviceAssetList}">
	              <c:forEach var="list" items="${serviceAssetList}" varStatus="status">
		              <tr>
		                <input type="hidden" value="${list.id }" id="assetId" name="assetId"/>
		                <td>${list.name }</td>
		                <td>${list.addr}</td>
		                <td><input type="checkbox" name="serviceAssetId" value="${list.id }"/></td>
		              </tr>
	              </c:forEach>
              </c:if>
            </table>
          </div>
          <!--<div class="peiz_ico"><img src="${ctx}/source/images/peiz_ico.jpg" class="to_right"/></div>
          <div class="peiz_table">
            <table class="rightTr4">
              <tr style="background:#e0e0e0;">
                <td style="width:30%;">资产名称 </td>
                <td style="width:25%;">资产类型 </td>
                <td style="width:30%;">资产地址 </td>
                <td style="width:15%;"></td>
              </tr>
            </table>
          </div>-->
        </div>
        <div><font class="assets_msg" style="color:red;float:right"></font></div>
        <div class="pinv">
          <h3>扫描频率</h3>
          <div class="pinv_subnav">
            <ul class="pinv_sub_nav">
              <!-- <li class="pinv_active">
                <input type="radio" name="scanType4" value="1" id="10分钟" checked/>
                &nbsp;&nbsp;10分钟</li>
              <li>
                <input type="radio" name="scanType4" value="2" id="30分钟" />
                &nbsp;&nbsp;30分钟</li> -->
              <li>
                <input type="radio" name="scanType4" value="3" id="1小时" checked/>
                &nbsp;&nbsp;1小时</li>
              <li>
                <input type="radio" name="scanType4" value="4" id="2小时" />
                &nbsp;&nbsp;2小时</li>
            </ul>
          </div>
        </div>
      </div>
      <!-- 日常流量监控服务-->
      <div class="peiz_cont">
        <div style="position: absolute; top:-10px; left:660px;"><img src="${ctx}/source/images/user_ico.jpg" /></div>
        <div class="jiance_top clear">
          <div class="jiance_sub">监控对象IP地址/IP地址段</div>
          <div class="jiance_txt">
            <input type="text" id="ip5"/>
          </div>
        </div>
        <div class="jiance_bottom clear">
          <div class="jiance_sub">防护带宽</div>
          <div>
            <div class="pinv_subnav">
	            <ul class="pinv_sub_nav">
	              <li class="pinv_active">
	                <input type="radio" name="bandwidth5" value="1" id="10M" checked/>
	                &nbsp;&nbsp;10M</li>
	              <li>
	                <input type="radio" name="bandwidth5" value="2" id="100M"/>
	                &nbsp;&nbsp;100M</li>
	              <li>
	                <input type="radio" name="bandwidth5" value="3" id="500M"/>
	                &nbsp;&nbsp;500M</li>
	              <li>
                    <input type="radio" name="bandwidth5" value="4" id="1G"/>
                    &nbsp;&nbsp;1G</li>
                  <li>
                    <input type="radio" name="bandwidth5" value="5" id="5G"/>
                    &nbsp;&nbsp;5G</li>
                  <li>
                    <input type="radio" name="bandwidth5" value="6" id="10G"/>
                    &nbsp;&nbsp;10G</li>
	            </ul>
            </div>
          </div>
        </div>
      </div>
      <!-- 日常攻击防护服务-->
      <div class="peiz_cont">
        <div style="position: absolute; top:-10px; left:780px;"><img src="${ctx}/source/images/user_ico.jpg" /></div>
        <div class="jiance_top clear">
          <div class="jiance_sub">监控对象IP地址/IP地址段</div>
          <div class="jiance_txt">
            <input type="text" id="ip6"/>
          </div>
        </div>
        <div class="jiance_bottom clear">
          <div class="jiance_sub">防护带宽</div>
          <div>
            <div class="pinv_subnav">
                <ul class="pinv_sub_nav">
                  <li class="pinv_active">
                    <input type="radio" name="bandwidth6" value="1" id="10M" checked/>
                    &nbsp;&nbsp;10M</li>
                  <li>
                    <input type="radio" name="bandwidth6" value="2" id="100M"/>
                    &nbsp;&nbsp;100M</li>
                  <li>
                    <input type="radio" name="bandwidth6" value="3" id="500M"/>
                    &nbsp;&nbsp;500M</li>
                  <li>
                    <input type="radio" name="bandwidth6" value="4" id="1G"/>
                    &nbsp;&nbsp;1G</li>
                  <li>
                    <input type="radio" name="bandwidth6" value="5" id="5G"/>
                    &nbsp;&nbsp;5G</li>
                  <li>
                    <input type="radio" name="bandwidth6" value="6" id="10G"/>
                    &nbsp;&nbsp;10G</li>
                </ul>
            </div>
          </div>
        </div>
      </div>
      <!-- 突发异常流量清洗服务-->
      <div class="peiz_cont">
        <div style="position: absolute; top:-10px; left:900px;"><img src="${ctx}/source/images/user_ico.jpg" /></div>
        <div class="jiance_top clear">
          <div class="jiance_sub">监控对象IP地址/IP地址段</div>
          <div class="jiance_txt">
            <input type="text" id="ip7"/>
          </div>
        </div>
        <div class="jiance_bottom clear">
          <div class="jiance_sub">防护带宽</div>
          <div>
            <div class="pinv_subnav">
                <ul class="pinv_sub_nav">
                  <li class="pinv_active">
                    <input type="radio" name="bandwidth7" value="1" id="10M" checked/>
                    &nbsp;&nbsp;10M</li>
                  <li>
                    <input type="radio" name="bandwidth7" value="2" id="100M"/>
                    &nbsp;&nbsp;100M</li>
                  <li>
                    <input type="radio" name="bandwidth7" value="3" id="500M"/>
                    &nbsp;&nbsp;500M</li>
                  <li>
                    <input type="radio" name="bandwidth7" value="4" id="1G"/>
                    &nbsp;&nbsp;1G</li>
                  <li>
                    <input type="radio" name="bandwidth7" value="5" id="5G"/>
                    &nbsp;&nbsp;5G</li>
                  <li>
                    <input type="radio" name="bandwidth7" value="6" id="10G"/>
                    &nbsp;&nbsp;10G</li>
                </ul>
            </div>
          </div>
        </div>
      </div>
      </div>
      <!--上一步-->
      <div class="clear" style="margin-bottom:30px;">
        <div class="peiz_shang"><a href="###"><img src="${ctx}/source/images/user_submit.jpg" id="firstGoback"/></a></div>
        <!--下一步-->
        <div class="peiz_xia"><a href="###"><img src="${ctx}/source/images/user_contsut.jpg" id="twoStep"/></a></div>
      </div>
    </div>
    <!-- 联系信息-->
    <div  class="ding_center">
      <div class="user_xinx clear">
        <div class="xinx_left"><img name="servImg" src="${ctx}/source/images/user_9.jpg" /></div>
        <div class="xinx_right">
          <h3 name="servName"></h3>
          <!-- <p name="servRemark"></p> -->
        </div>
      </div>
      <div class="xinx_table">
        <table>
          <tr>
            <td style="width:20%;">订单类型</td>
            <td colspan="3" style="width:80%;" name="orderName">长期</td>
          </tr>
          <tr>
            <td>订单起止时间</td>
            <td>开始时间</td>
            <td name="begin">2014 . 12 . 17</td>
          </tr>
          <tr class="hideEnddate">
            <td></td>
            <td>结束时间</td>
            <td name="end">2014 . 12 . 17</td>
          </tr>
          <tr>
            <td>服务对象</td>
            <td colspan="3" name="servName"></td>
          </tr>
          <tr class="scan">
            <td>扫描频率</td>
            <td name="scanName"></td>
          </tr>
          <tr class="IPAddress">
            <td>监控对象IP地址/IP地址段</td>
            <td name="IPAddressName"></td>
          </tr>
          <tr class="network">
            <td>防护带宽</td>
            <td name="networkName"></td>
          </tr>
          <tr></tr>
          <tr>
            <td>联系信息</td>
            <td>联系人姓名</td>
            <td colspan="2"><input class="xin_input" type="text" name="linkname" id="linkname"/>
              &nbsp;<span style="width:30px;">*</span>
              <span><font id="linkname_msg" style="color:red;float:right"></font></span></td>
          </tr>
          <tr>
            <td></td>
            <td>手机号</td>
            <td colspan="2"><input class="xin_input" type="text" name="phone" id="phone"/>
              &nbsp;<span  style="width:30px;">*</span>
              <span><font id="phone_msg" style="color:red;float:right"></font></span></td>
          </tr>
          <tr>
            <td></td>
            <td>邮箱</td>
            <td colspan="2"><input class="xin_input" type="text" name="email" id="email"/></td>
          </tr>
          <tr>
            <td></td>
            <td>单位名称</td>
            <td colspan="2"><input class="xin_input" type="text" name="company" id="company"/></td>
          </tr>
          <tr>
            <td></td>
            <td>地址</td>
            <td colspan="2"><input class="xin_input" type="text" name="address" id="address"/></td>
          </tr>
        </table>
      </div>
      <div class="clear" style="margin-bottom:30px;">
        <div class="peiz_shang"><a href="###"><img src="${ctx}/source/images/user_submit.jpg" id="twoGoback"/></a></div>
        <!--下一步-->
        <div class="peiz_xia"><a href="###"><img src="${ctx}/source/images/user_contsut.jpg" id="threeStep"/></a></div>
      </div>
    </div>
    <!-- 确认订单-->
    <div  class="ding_center">
      <div class="user_xinx clear">
        <div class="xinx_left"><img name="servImg" src="${ctx}/source/images/user_9.jpg" /></div>
        <div class="xinx_right">
          <h3 name="servName"></h3>
          <!-- <p name="servRemark"></p>-->
        </div>
      </div>
      <div class="xinx_table">
        <table>
          <tr>
            <td style="width:25%;">订单类型</td>
            <td colspan="3" style="width:75%;" name="orderName">长期</td>
          </tr>
          <tr>
            <td>订单起止时间</td>
            <td>开始时间</td>
            <td name="begin">2014 . 12 . 17</td>
          </tr>
          <tr class="hideEnddate">
            <td></td>
            <td>结束时间</td>
            <td name="end">2014 . 12 . 17</td>
          </tr>
          <tr>
            <td>服务对象</td>
            <td colspan="3" name="servName"></td>
          </tr>
          <tr class="scan">
            <td>扫描频率</td>
            <td name="scanName"></td>
          </tr>
          <tr class="IPAddress">
            <td>监控对象IP地址/IP地址段</td>
            <td name="IPAddressName"></td>
          </tr>
          <tr class="network">
            <td>防护带宽</td>
            <td name="networkName"></td>
          </tr>
          <tr></tr>
          <tr>
            <td>联系信息</td>
            <td>联系人姓名</td>
            <td colspan="2" name="linkname"></td>
          </tr>
          <tr>
            <td></td>
            <td>手机号</td>
            <td colspan="2" name="phone"></td>
          </tr>
          <tr>
            <td></td>
            <td>邮箱</td>
            <td colspan="2" name="email"></td>
          </tr>
          <tr>
            <td></td>
            <td>单位名称</td>
            <td colspan="2" name="company"></td>
          </tr>
          <tr>
            <td></td>
            <td>地址</td>
            <td colspan="2" name="address"></td>
          </tr>
        </table>
      </div>
      <div class="clear" style="margin-bottom:30px;"> 
        <!--上一步-->
        <div class="peiz_shang"><a href="###"><img src="${ctx}/source/images/user_submit.jpg" id="threeGoback"/></a></div>
        <!--确认订单-->
        <div class="peiz_xia"><a href="###"><img src="${ctx}/source/images/user_submit_1.jpg" id="fourStep"/></a></div>
      </div>
    </div>
    <!--下单成功-->
    <div  class="ding_center">
      <div  class="user_wanc">下单成功！&nbsp;   &nbsp;   订单编号  <font id="orderId"></font> &nbsp;&nbsp;      下单时间   <font id="createDate"></font></div>
      <div class="user_xinx clear">
        <div class="xinx_left"><img name="servImg" src="${ctx}/source/images/user_9.jpg" /></div>
        <div class="xinx_right">
          <h3 name="servName"></h3>
          <!-- <p name="servRemark"></p>-->
        </div>
      </div>
      <div class="xinx_table">
        <table>
          <tr>
            <td style="width:25%;">订单类型</td>
            <td colspan="3" style="width:75%;" name="orderName">长期</td>
          </tr>
          <tr>
            <td>订单起止时间</td>
            <td>开始时间</td>
            <td name="begin">2014 . 12 . 17</td>
          </tr>
          <tr class="hideEnddate">
            <td></td>
            <td>结束时间</td>
            <td name="end">2014 . 12 . 17</td>
          </tr>
          <tr>
            <td>服务对象</td>
            <td colspan="3" name="servName"></td>
          </tr>
          <tr class="scan">
            <td>扫描频率</td>
            <td name="scanName"></td>
          </tr>
          <tr class="IPAddress">
            <td>监控对象IP地址/IP地址段</td>
            <td name="IPAddressName"></td>
          </tr>
          <tr class="network">
            <td>防护带宽</td>
            <td name="networkName"></td>
          </tr>
          <tr></tr>
          <tr>
            <td>联系信息</td>
            <td>联系人姓名</td>
            <td colspan="2" name="linkname"></td>
          </tr>
          <tr>
            <td></td>
            <td>手机号</td>
            <td colspan="2" name="phone"></td>
          </tr>
          <tr>
            <td></td>
            <td>邮箱</td>
            <td colspan="2" name="email"></td>
          </tr>
          <tr>
            <td></td>
            <td>单位名称</td>
            <td colspan="2" name="company"></td>
          </tr>
          <tr>
            <td></td>
            <td>地址</td>
            <td colspan="2" name="address"></td>
          </tr>
        </table>
      </div>
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
</body>
</html>
