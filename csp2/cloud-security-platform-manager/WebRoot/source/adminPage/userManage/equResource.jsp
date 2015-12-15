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
<link href="${ctx}/source/manageCss/page.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/assetJs/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/assetJs/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${ctx}/source/scripts/common/echarts-all.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/raphael.2.1.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/justgage.1.0.1.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/modules/exporting.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/highcharts-3d.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/equipment/equipment.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/equipment/equipmentCharts.js"></script>
<script type="text/javascript">
$(function(){
	
	//添加设备引擎信息
	$('#popBox').dialog({
		autoOpen: false,
		width:538,
		height:482,
		modal: true,
		resizable:false,
			close:function(){$(this).dialog("close")}
		});	
	
	$('.new').click(function(){
		 $( "#popBox" ).dialog( "open" );
		 $('#popBox').dialog({title:'添加设备引擎信息'});	
		 $(':input','#addAndUpd').val('');
		 $("#butType").empty();
		 $("#butType").append("<input type='button' class='submit' id='add' onclick='addAndUpdate()' value='立即添加' >");
		  return false;
		
	});
	
	//设备引擎名称
	$('#namePopBox').dialog({
		autoOpen: false,
		width:1100,
		height:600,
		modal: true,
		resizable:false,
			close:function(){$(this).dialog("close")}
		});	
	/* $('.table table td span').click(function(){
		 $( "#namePopBox" ).dialog( "open" );
		  return false;
		
	}) */
})
</script>
<style type="text/css">
#factory{
background: rgba(0, 0, 0, 0) url("${ctx}/source/images/b_safes_icon.jpg") no-repeat scroll 0 0;
    float: right;
    height: 36px;
    line-height: 36px;
    margin-left: 15px;
    padding-left: 10px;
    width: 178px;
    border:none; 
}
.submit{ 
width:183px; 
height:42px; 
font-size:18px; 
color:#fff; 
background:url(${ctx}/source/images/b_btn_bg2.jpg) no-repeat; 
border:none; 
text-align:center; 
margin-top:20px; 
cursor:pointer; 
}

</style>
</head>
<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head clear">
    <div class="logo"><img src="${ctx}/source/images/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul class="clear">
      	<li><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
		<li><a href="${ctx}/adminchinas.html" target="_blank" class="white">安全态势</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAssetUI.html" class="white">资产分析</a></li>
        <li><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
        <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">订单分析</a></li>
        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
        <li class="b_current" ><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/images/b_photo.jpg" width="43" height="42"></a> </div>
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
    	<a href="javascript:;" class="equBtn new" >添加设备引擎信息</a>
    	<form class="clearfix equipment analysecent">
        	<input type="text" class="text" placeholder="请输入设备引擎名称" id="engineName">   
            <input type="text" class="text" placeholder="请输入设备引擎IP地址" id="engineAddr">  
            <!-- <input type="text" class="text" placeholder="请输入设备厂家" id="factory">  -->  
            <select id="factory" class="text">
            	<option value="">全部</option>
            	<option value="0">安恒</option>
            	<option value="1">创宇</option>
            </select>                            
            <input type="button" onclick="getAllEngine();" class="sub" value="" style="right:-130px; top:16px;">
        </form>
        
        <div class="table" id="div">
        	<style>
            	.table table th,.table table td{ text-align:center;}
				.table{ margin-bottom:40px;}
            	.table table th{ background:#e0e0e0; font-weight:normal; color:#888888;}
				.table table td{border-bottom:#e0e0e0 solid 1px; color:#888888; font-size:14px;}
				.table table td a{ color:#f1a31a; margin-right:23px;}
				.table table td a:hover{ text-decoration:underline;}
            </style>
        	<table cellpadding="0" cellspacing="0" id="table">
            	<thead>
                    <tr height="40">
                    	<th style="display: none;"></th>
                        <th width="250">设备引擎名称</th>
                        <th width="336">设备引擎IP地址</th>
                        <th width="250">设备厂家</th>
                        <th width="260">操作</th>
                     </tr>
                </thead>
                <tbody id="tbody">
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
 <div class="dd_data_box">
        	<div class="system_zy">
            	<div class="zy_left fl" style="width:70%;">
                	<div class="zy_top1">
                    	<div class="zy_top_l fl" id="system1">
                        	<!--<img src="${ctx}/source/adminImages/system1.jpg" width="249" height="164">  -->
                        </div>
                        <div class="zy_top_r fl" id="system2" style="width:300px;height:164px;">
                        	<!-- <img src="${ctx}/source/adminImages/system2.jpg"> -->
                        </div>
                    </div>
                    <div class="zy_top2">
                    	<p class="system_title">CPU占用情况</p>
                    </div>
                    <div class="zy_top3">
                    	<div class="zy_top_l fl">
                        	<div class="nc_jpg" id="system3">
                        		<!-- <img src="${ctx}/source/adminImages/system3.jpg"> -->
                        	</div>
                            <div class="nc_font">
                            	<p><span class="nc_p">总内存</span><span class="nc_num" id="rawNum"></span></p>
                                <!-- <p><span class="nc_p">已使用内存</span><span class="nc_num_r">${use}MB</span></p> -->
                            </div>
                        </div>
                        <div class="zy_top_r fl" id="system4" style="width:300px;height:164px;">
                        	<!-- <img src="${ctx}/source/adminImages/system4.jpg"> -->
                        </div>
                    </div>
                    <div class="zy_top2 h30">
                    	<p class="system_title">内存占用情况</p>
                    </div>
                </div>
                <div class="zy_right fl" style="width:30%;">
                	<div class="zy_rtop1" id="system5" style="width:249px;">
                    	<!-- <img src="${ctx}/source/adminImages/system5.jpg"> -->
                    </div>
                    <div class="zy_rtop2" style="height:20px;">
                    	<!-- <img src="${ctx}/source/adminImages/system6.jpg"> -->
                    	<div>磁盘总大小：<span id="diskNum"></span>GB</div>
                    	
                    </div>
                    <div class="zy_top2 h30 w364">
                    	<p class="system_title">磁盘占用情况</p>
                    </div>
                    <div><span id="system6" style="padding-left:55%;"></span></div>
                    <div class="zy_top2 h30 w364">
                    	<p class="system_title">运行服务数</p>
                    </div>
                </div>
            </div>
        </div>
 
</div>

<!--弹框-->
 <div id="popBox" class="popBox" > 
 	<div class="item">
 		<form id="addAndUpd" method="post" name="addAndUpd" onsubmit="vaildata();">
 		<input type="text" id="id" name="id" style="display:none;" />
    	<div class="clearfix">
        	<label>设备引擎名称</label>
            <input type="text" id="equName" name="equName">
            
        </div>
        <div class="clearfix">
        	<label>设备引擎IP地址</label>
            <input type="text" id="equIP" name="equIP">
            
        </div>
        <div class="clearfix">
        	<label>设备厂家</label>
           <!--  <input type="text"> -->
            <select id="factoryName" name="factoryName" style="width:260px;">
            	<option value="0">安恒</option>
            	<option value="1">创宇</option>
            </select>
        </div>
    	 <div id="butType" class="clearfix" style="text-align:center">
        </div>
        </form>
    </div>
</div>


</body>
</html>
