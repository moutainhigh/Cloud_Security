<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>数据分析</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
</head>

<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
       <li class="b_current"><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/dataAnalysisUI.html" class="white">数据分析</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/SystemManageUI.html" class="white">系统管理</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/adminImages/b_photo.jpg" width="43" height="42"></a> </div>
      <div class="lagst-right">
        <p ><a href="###" class="white">${sessionScope.globle_user.name }</a></p>
        <p> <a href="${ctx}/adminExit.html" class="white">退出</a></p>
      </div>
    </div>
  </div>
</div>
<!--头部代码结束-->
<div class="main_wrap">
	<div class="main_center">
    	<div class="data_title">
        	<button class="dataana_btn datab_cur fl" id="dd_btn">订单统计分析</button>
            <div class="bor_t_small fl"></div>
            <button class="dataana_btn fl" id="gj_btn">告警统计分析</button>
            <div class="bor_t_big fl"></div>
        </div>
        <div class="dd_data_box">
        	<div class="data_choose dd_choose">
            	<form>
               	  <label class="fl">统计时段</label>
              <select class="se_big fl">
                    	<option></option>
                    </select>
              <select class="se_big fl se_last">
                    	<option></option>
                    </select>
                  <label class="fl">订单类型</label>
              <select class="se_small fl se_last">
                    	<option></option>
                    </select>
                  <label class="fl">订单统计类型</label>
              <select class="se_small fl se_last">
                    	<option></option>
                    </select>
                  <label class="fl">订单状态</label>
              <select class="se_small fl se_last">
                    	<option></option>
                    </select>
                  <input type="submit" class="dd_select fl" value="">
                </form>
            </div>
            <div class="data_detail">
       	    	<img src="${ctx}/source/adminImages/b_dataanalysis.jpg" width="1097" height="351">
            </div>
        </div>
        <div class="gj_data_box">
        	<div class="data_choose gjchoose">
            	<form>
               	  <label class="fl">统计时段</label>
              <select class="se_big fl">
                    	<option></option>
                    </select>
              <select class="se_big fl se_last">
                    	<option></option>
                    </select>
                  <label class="fl ml20">服务类型</label>
              <select class="se_small fl se_last">
                    	<option></option>
                    </select>
                  <label class="fl ml20">告警级别</label>
              <select class="se_small fl se_last">
                    	<option></option>
                    </select>
                  <input type="submit" class="dd_select fl ml20" value="">
                </form>
            </div>
            <div class="data_detail">
       	    	<img src="${ctx}/source/adminImages/b_dataanalysis.jpg" width="1097" height="351">
            </div>
        </div>
    </div>
</div>
<!--尾部部分代码-->
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
<!--尾部部分代码结束-->
</body>
</html>
