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
      <div class="user_ctive"><a href="###"><img src="${ctx}/source/images/user_ico_3.jpg" /></a></div>
      <div class="user_sec_cont">
        <div class="user_secta" value="0">全部类型</div>
        <div class="user_secta_list">
          <ul>
            <li><a href="###">全部类型</a></li>
            <li><a href="###">全部类型</a></li>
            <li><a href="###">全部类型</a></li>
            <li><a href="###">全部类型</a></li>
            <li><a href="###">全部类型</a></li>
          </ul>
        </div>
      </div>
      <div class="user_sec_cont" style=" left:360px; ">
        <div class="user_secta" value="0">全部状态</div>
        <div class="user_secta_list">
          <ul>
            <li><a href="###">全部类型</a></li>
            <li><a href="###">全部类型</a></li>
            <li><a href="###">全部类型</a></li>
            <li><a href="###">全部类型</a></li>
            <li><a href="###">全部类型</a></li>
          </ul>
        </div>
      </div>
      <div class="user_sec_txt">
        <input type="text"  value="请输入关键字"/>
      </div>
      <div class="user_soucuo" style="left:740px;"><img src="${ctx}/source/images/user_submit_2.jpg" /></div>
    </div>
    <div class="zhangd_table">
      <table>
        <tbody>
          <tr style="background:#e0e0e0; height:30px; line-height:30px;">
            <td style="width:20%;">资产名称</td>
            <td  style="width:20%;">资产类型</td>
            <td  style="width:20%;">资产地址</td>
            <td  style="width:20%;">资产状态</td>
            <td  style="width:20%;">操作</td>
          </tr>
          <c:forEach items="${list}" var="o"> 
          <tr>
            <td>${o.name}</td>
            <td>${o.type}</td>
            <td>${o.addr}</td>
            <td><div class="zican_wei">未验证</div>
              <div  class="zican_top">立即验证</div></td>
            <td><div class="zican_top">修改</div>
              <div class="zican_bottom">删除</div></td>
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
    <form id="saveAsset" action="${ctx}/addAsset.html">
    <div class="text_top">新增资产</div>
    <div class="text_bottm">
  
      <table>
        <tr>
          <td style="width:20%;">资产名称</td>
          <td style="width:45%;"><input class="boz_inout_1" type="text" name="name" id="assetName"/></td>
          <td style="width:35%; text-align:left; color:#e32929;">资产名称不允许为空！</td>
        </tr>
        <tr></tr>
        <tr>
          <td>资产地址</td>
          <td><input class="boz_inout_1" type="text" name="addr" id="assetAddr"/></td>
          <td style="color:#e32929;"">资产IP重复，该资产已添加过！</td>
        </tr>
      </table>
    </div>
    <div style="margin-top:35px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/user_submit_3.jpg" onclick="saveAsset()"/></a></div>
  </div>
  </form>
</div>
<div id="box_logoIn2">
<div  id="close2"></div>
<div class="text_1">
  <div class="text_top">请验证资产1.1.1.1的权限</div>
  <div class="text_bottm">
    <div class="txt_yz clear">
      <div class="txt_yz_left">
        <input type="radio" />
        &nbsp;&nbsp;代码验证 ( 推荐 )</div>
      <div class="txt_yz_right">
        <input type="radio" />
        &nbsp;&nbsp;上传文件验证</div>
    </div>
    <div class="txt_1">
      <div class="txt_2"> “http://webscan.360.cn/index/checkwebsite/url/1.1.1.1”
        name=“9d4f9e27bc0c401fb20aqa2420ff1e5”>云服务平台</div>
      <p>请在您的<span>网站首页</span>中任何位置加入如上代码。（<a href="###">复制代码</a>）<br />
        如果添加成功，您的网站首页上能够看到右边的文字：“云服务平台”</p>
    </div>
  </div>
  <div style="margin-top:35px;"><a href="###"><img src="${ctx}/source/images/user_submit_4.jpg" /></a></div>

</body>
</html>
