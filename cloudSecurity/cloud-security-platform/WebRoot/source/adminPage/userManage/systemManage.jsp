<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>系统管理</title>
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
    <div class="logo"><img src="images/b_logo2.jpg"/></div>
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
        	<button class="dataana_btn datab_cur fl" id="dd_btn">系统资源</button>
            <div class="bor_t_small fl"></div>
            <button class="dataana_btn fl" id="gj_btn">参数配置</button>
            <div class="bor_t_big fl"></div>
        </div>
        <div class="dd_data_box">
        	<div class="system_zy">
            	<div class="zy_left fl">
                	<div class="zy_top1">
                    	<div class="zy_top_l fl">
                        	<img src="${ctx}/source/adminImages/system1.jpg" width="249" height="164">
                        </div>
                        <div class="zy_top_r fl">
                        	<img src="${ctx}/source/adminImages/system2.jpg">
                        </div>
                    </div>
                    <div class="zy_top2">
                    	<p class="system_title">CPU占用情况</p>
                    </div>
                    <div class="zy_top3">
                    	<div class="zy_top_l fl">
                        	<div class="nc_jpg"><img src="${ctx}/source/adminImages/system3.jpg"></div>
                            <div class="nc_font">
                            	<p><span class="nc_p">总内存</span><span class="nc_num">80GB</span></p>
                                <p><span class="nc_p">已使用内存</span><span class="nc_num_r">52.6GB</span></p>
                            </div>
                        </div>
                        <div class="zy_top_r fl">
                        	<img src="${ctx}/source/adminImages/system4.jpg">
                        </div>
                    </div>
                    <div class="zy_top2 h30">
                    	<p class="system_title">内存占用情况</p>
                    </div>
                </div>
                <div class="zy_right fl">
                	<div class="zy_rtop1">
                    	<img src="${ctx}/source/adminImages/system5.jpg">
                    </div>
                    <div class="zy_rtop2">
                    	<img src="${ctx}/source/adminImages/system6.jpg">
                    </div>
                    <div class="zy_top2 h30 w364">
                    	<p class="system_title">磁盘占用情况</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="gj_data_box">
        	<div class="system_pz">
            	<form>
                	<div class="pz_form">
                    	<label>用户会话时长</label>
                        <input type="text" class="pz_input">
                    </div>
                    <div class="pz_form">
                    	<label>邮件服务器地址</label>
                        <input type="text" class="pz_input">
                    </div>
                    <div class="pz_form">
                    	<label>邮件用户名</label>
                        <input type="text" class="pz_input">
                    </div>
                    <div class="pz_form">
                    	<label>邮件密码</label>
                        <input type="text" class="pz_input">
                    </div>
                    <div class="pz_form mt20">
                    	<button class="system_btn">保存</button>
                        <button class="system_btn">取消</button>
                    </div>
                </form>
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
