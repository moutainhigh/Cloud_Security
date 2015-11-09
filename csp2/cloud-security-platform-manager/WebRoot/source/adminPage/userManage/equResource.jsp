<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>系统管理</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/jquery-ui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/popBox.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/assetJs/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/assetJs/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${ctx}/source/scripts/common/echarts-all.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery-ui.min.js"></script>
<script type="text/javascript">
$(function(){
	
	//添加设备引擎信息
	$('#popBox').dialog({
		autoOpen: false,
		width:538,
		height:332,
		modal: true,
		resizable:false,
			close:function(){$(this).dialog("close")}
		});	
	
	$('.new').click(function(){
		 $( "#popBox" ).dialog( "open" );
		  return false;
		
	})
	
	//设备引擎名称
	$('#namePopBox').dialog({
		autoOpen: false,
		width:1100,
		height:600,
		modal: true,
		resizable:false,
			close:function(){$(this).dialog("close")}
		});	
	$('.table table td span').click(function(){
		
		 $( "#namePopBox" ).dialog( "open" );
		  return false;
		
	})
})
</script>
</head>
<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
        <li class="b_current"><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li><!--
        <li><a href="${ctx}/adminchinas.html" target="_blank" class="white">安全态势</a></li>
        --><li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAssetUI.html" class="white">资产分析</a></li>
        <li><a href="${ctx}/adminUserManageUI.html" class="white">用户分析</a></li>
        <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">订单分析</a></li>
        <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">告警分析</a></li>
        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/adminImages/b_photo.jpg" width="43" height="42"></a> </div>
      <div class="lagst-right">
        <p ><a href="###" class="white">${sessionScope.admin_user.name }</a></p>
        <p> <a href="${ctx}/adminExit.html" class="white">退出</a></p>
      </div>
    </div>
  </div>
</div>
<!--头部代码结束-->
<div class="main_wrap">
	<div class="main_center">
    	<a href="javascript:;" class="equBtn new">添加设备引擎信息</a>
    	<form class="clearfix equipment analysecent">
        	<input type="text" class="text" placeholder="请输入设备厂家">      
              <input type="text" class="text" placeholder="请输入设备引擎IP地址">  
               <input type="text" class="text" placeholder="请输入设备引擎名称">                               
            <input type="submit" class="sub" value="" style="right:-130px; top:16px;">
        </form>
        
        <div class="table">
        	<style>
            	.table table th,.table table td{ text-align:center;}
				.table{ margin-bottom:40px;}
            	.table table th{ background:#e0e0e0; font-weight:normal; color:#888888;}
				.table table td{border-bottom:#e0e0e0 solid 1px; color:#888888; font-size:14px;}
				.table table td a{ color:#f1a31a; margin-right:23px;}
				.table table td a:hover{ text-decoration:underline;}
            </style>
        	<table cellpadding="0" cellspacing="0">
            	<thead>
                    <tr height="40">
                        <th width="250">设备引擎名称</th>
                        <th width="336">设备引擎IP地址</th>
                        <th width="250">设备厂家</th>
                        <th width="260">操作</th>
                     </tr>
                </thead>
                <tbody>
                	<tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                    <tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                    <tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                    <tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                    <tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                    <tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                    <tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                    <tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                    <tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                    <tr height="40">
                    	<td width="250"><span>admin92t4045iy0</span></td>
                        <td width="336">102.398.45.01</td>
                        <td width="250">超级管理员</td>
                        <td width="260"><a href="#">修改</a><a href="#">删除</a></td>
                    </tr>
                
                </tbody>
            
            </table>
        
        </div>
    </div>
</div>
<!--尾部部分代码-->
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
<li><a href="###">客户电话</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>
 <ul>
 <li>Copyright&nbsp;©&nbsp;2015 中国电信股份有限公司北京研究院<br />
京ICP备12019458号－10</li>
</div>
</div>
</div>
<!--尾部部分代码结束-->
<!--弹框-->
 <div id="namePopBox" class="popBox" title="设备引擎名称"> 
 	<div class="item clearfix">
    	<div class="name_l fl">
        	<div class="nameItem" style="height:183px">
            	<img src="${ctx}/source/images/name01.jpg" alt="">
            </div>
            <div class="nameItem" style="height:250px;">
            	<img src="${ctx}/source/images/name02.jpg" alt="">
            </div>
        </div>
        <div class="name_l fr" style="width:370px;">
        	<div class="nameItem" style="height:340px;">
            	<img src="${ctx}/source/images/name03.jpg" alt="">
            </div>
            <div class="nameItem" style="height:60px;">
            	<img src="${ctx}/source/images/name04.jpg" alt="">
            </div>
        </div>
    </div>
 
</div>

<!--弹框-->
 <div id="popBox" class="popBox" title="添加设备引擎信息"> 
 	<div class="item">
    	<div class="clearfix">
        	<label>设备引擎名称</label>
            <input type="text">
            
        </div>
        <div class="clearfix">
        	<label>设备引擎IP地址</label>
            <input type="text">
            
        </div>
        <div class="clearfix">
        	<label>设备厂家</label>
            <input type="text">
            
        </div>
    	 <div class="clearfix" style="text-align:center">
        	<input type="submit" value="立即添加">
        </div>
    </div>
 
</div>


</body>
</html>
