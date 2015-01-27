<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心-我的资产</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/zezao.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/asset.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#status").val("${status}");
	$("#searchAssetName").val("${name}");
});
</script>
</head>

<body>

<div class="head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
    <div class="lagst">
      <div class="lagst-left"> <a href="###"><img src="${ctx}/source/images/ren.png" /></a> </div>
      <div class="lagst-right">
         <p><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
        <p><a href="${ctx}/exit.html">退出</a></p>
      </div>
    </div>
    <div class="list">
      <ul>
        <ul>
         <li><a href="${ctx}/index.html">首页</a></li>
          <li><a href="###">我的订单</a></li>
          <li><a href="aider.html">在线帮助</a></li>
          <li class="list_active" style="border-right:1px solid #11871d;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
        </ul>
      </ul>
    </div>
  </div>
</div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <div class="user_left">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center;"><a  style="color:#45b62b; " href="${ctx}/userCenterUI.html">用户中心</a></li>
      <li><a href="${ctx}/userDataUI.html">基本资料</a></li>
      <li><a href="${ctx}/userBillUI.html">我的账单</a></li>
      <li class="active"><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
      <h2>订购中心</h2>
      <li><a href="${ctx}/selfHelpOrderInit.html">自助下单</a></li>
      <li><a href="${ctx}/orderTrackInit.html">订单跟踪</a></li>
    </ul>
  </div>
  
  <!-- <jsp:include page="left.jsp"/> -->
  
  <!--我的资产-->
  <div class="user_right" >
    <div class="user_top">
      <div class="user_ctive" style="left:200px;"><a href="###"><img src="${ctx}/source/images/user_ico_3.jpg" /></a></div>
  <form action="${ctx}/searchAssetCombine.html" method="post" id="searchAssetForm">
      <div class="user_sec_cont" style=" left:200px; ">
      </div>
      <div class="user_sec_cont" style="left:350px;">
        <select id="status" name="status" class="user_secta spiclesel">
       		<option select="selected" value="">请选择状态</option>
       		<option value="0" >未验证</option>
       		<option value="1" >已验证</option>
  		</select>
      </div>
      <div class="user_sec_txt">
        <input type="text" name="name" id="searchAssetName" value="请输入关键字"></input>
      </div>
      <div class="user_soucuo" style="left:754px;"><img src="${ctx}/source/images/user_submit_2.jpg" onclick="searchAssetCombine()"/></div>
  </form>
    </div>
    
    <div class="zhangd_table">
      <table>
        <tbody>
          <tr style="background:#e0e0e0; height:30px; line-height:30px;">
            <td style="width:20%;">资产名称</td>
            <td  style="width:20%;">资产地址</td>
            <td  style="width:20%;">资产状态</td>
            <td  style="width:20%;">操作</td>
          </tr>
          <c:forEach items="${list}" var="asset"> 
          <tr>
            <td>${asset.name}</td>
            <td>${asset.addr}</td>
            <c:if test="${asset.status==0}">
            <td><div class="zican_wei">未验证</div>
              <div class="zican_top" id="${asset.id}" name="${asset.name}" addr="${asset.addr}">立即验证</div></td>
              </c:if>
              <c:if test="${asset.status==1}">
            <td><div class="zican_wei">已验证</div></td>
              </c:if>
            <td><div class="zc_edit" id="${asset.id}" name="${asset.name}" addr="${asset.addr}">修改</div>
              <div class="zican_bottom"><a href="javascript:void(0)" onclick="deleteAsset('${asset.id}')" >删除</a></div></td>
          </tr>
          </c:forEach>
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
    <li><a href="###">新用户注册</a></li>
    <li><a href="###">用户登录</a></li>
    <li><a href="###">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###"> 帮助</a></h3>
  <ul>
    <li><a href="###">常见问题</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###">厂商合作</a></h3>
  <ul>
    <li><a href="###">华为</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">360</a></li>
  </ul>
</div>
<div  class="bottom_main">
<h3><a href="###">联系我们</a></h3>
<ul>
<li><a href="###">客户电话</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>
<ul>
<li>如未经授权用作他处，将保留追究侵权者法律责任的权利。<br />
  京ICP备11111111号-4 / 京ICP证1111111号<br />
  北京市公安局朝阳分局备案编号:110105000501</li>
</div>
</div>
</div>
</div>

<!--新增资产遮罩层样式-->
<div id="box_mark"></div>
<div id="box_logoIn1">
  <div id="close1"></div>
  <div class="text_1">
    <form id="saveAsset" action="${ctx}/addAsset.html" method="post">
    <div class="text_top">新增资产</div>
    <div class="text_bottm">
  
      <table style="margin-top:56px;">
        <tr>
          <td style="width:30%;">资产名称</td>
          <td style="width:45%;"><input class="boz_inout_1" type="text" name="name" id="assetName"/></td>
          <td style="width:25%; text-align:left; color:#e32929;"><div id="assetName_msg"></div></td>
        </tr>
        <tr></tr>
        <tr>
          <td>资产地址</td>
          <td><input class="boz_inout_1" type="text" name="addr" id="assetAddr"/></td>
          <td style="color:#e32929;text-align:left"><div id="assetAddr_msg"></div></td>
        </tr>
      </table>
    </div>
    <div style="margin-top:35px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/user_submit_3.jpg" onclick="saveAsset()"/></a></div>
  </div>
  </form>
</div>

<div id="box_logoIn_edit">
  <div id="close_edit"></div>  <div class="text_1">
    <form id="editAsset" action="${ctx}/editAsset.html" method="post">
    <div class="text_top">修改资产</div>
    <div class="text_bottm">
  	<input type="hidden" name="id" id="hiddenEditAssetid"/>
  	<input type="hidden" name="hiddenEditAssetName" id="hiddenEditAssetName"/>
  	<input type="hidden" name="hiddenEditAssetAddr" id="hiddenEditAssetAddr"/>
      <table style="margin-top:56px;">
        <tr>
          <td style="width:30%;">资产名称</td>
          <td style="width:45%;"><input class="boz_inout_1" type="text" name="name" id="editAssetName"/></td>
          <td style="width:25%; text-align:left; color:#e32929;"><div id="editAssetName_msg"></div></td>
        </tr>
        <tr></tr>
        <tr>
          <td>资产地址</td>
          <td><input class="boz_inout_1" type="text" name="addr" id="editAssetAddr"/></td>
          <td style="color:#e32929;text-align:left"><div id="editAssetAddr_msg"></div></td>
        </tr>
      </table>
    </div>
    <div style="margin-top:35px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/user_submit_3.jpg" onclick="editAsset()"/></a></div>
  </div>
  </form>
</div>

<div id="box_logoIn2">
<div  id="close2"></div>
<form action="${ctx}/verificationAsset.html" method="post" id="verificationAssetForm">
<input type="hidden" value="" id="hiddenId" name="id"/>
<input type="hidden" value="" id="hiddenAddr" name="addr"/>
	<div class="text_1">
	  <div class="text_top" id="verificationName"></div>
	  <div class="text_bottm">
	    <div class="txt_yz clear">
	      <div class="txt_yz_left">
	        <input type="radio" name="verification_msg" checked="checked" id="codeVerification" value="codeVerification" onclick="showAndHiddenRadio()"/>
	        &nbsp;&nbsp;代码验证(推荐)</div>
	      <div class="txt_yz_right">
	        <input type="radio" name="verification_msg" id="fileVerification" value="fileVerification" onclick="showAndHiddenRadio()"/>
	        &nbsp;&nbsp;上传文件验证</div>
	    </div>
	    <div class="txt_1" id="codeVerificationID" style="display:block;">
	      <div class="txt_2" id="code">漏洞扫描服务</div>
	      <p>请在您的<span><a href="" id="c">网站首页</a></span>中任何位置加入如上代码。（<a href="javascript:void(0)" onclick="copyToClipBoard()">复制代码</a>）<br />
	        如果添加成功，您的网站首页上能够看到右边的文字：“云服务平台”</p>
	    </div>
	    
	    
	    <div class="txt_1" id="fileVerificationID" style="display:none;">
		    <div class="txt_2">
	        	<p>请点击下载：<a href="${ctx}/download.html?fileName=key.txt">key.txt</a></p>
	        </div>
		    <p>请在您的资产根目录下添加 <span> key.txt </span>作为验证文件,如果添加成功<br/>您的网站首页上能够看到右边的文字：“云服务平台”</p>
	    </div>
	    
	    
	  </div>
	  <div style="margin-top:35px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/user_submit_4.jpg" onclick="verificationAsset()"/></a></div>
	</div>
</form>
</body>
</html>
