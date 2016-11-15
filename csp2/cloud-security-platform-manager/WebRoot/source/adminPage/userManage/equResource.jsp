<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>设备资源管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
<style>
.user_table thead tr th:nth-child(1) {padding-right: 125px;padding-left: 60px;}
.user_table thead tr th:nth-child(2) {padding-right: 239px;}
.user_table thead tr th:nth-child(3) {padding-right: 187px;}
.user_table tbody tr td:nth-child(1) {width: 220px;padding-left: 60px;}
.user_table tbody tr td:nth-child(2) {width: 360px;}
.user_table tbody tr td:nth-child(3) {width: 234px;}
</style>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
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
<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
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
        	<table class="user_table" cellpadding="0" cellspacing="0" id="table">
            	<thead>
                    <tr>
                        <th>设备引擎名称</th>
                        <th>设备引擎IP地址</th>
                        <th>设备厂家</th>
                        <th>操作</th>
                     </tr>
                </thead>
                <tbody id="tbody">
                </tbody>
            </table>
        </div>
    </div>
</div>
<!--============bottom============-->
	<!-- footer start -->
	<c:import url="/footer.html"></c:import>
	<!-- footer end -->
</div>
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
 		<input type="hidden" id="oldEquName" style="display:none;" />
    	<div class="clearfix">
        	<label>设备引擎名称</label>
            <input type="text" id="equName" name="equName" maxlength="10">
            
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
