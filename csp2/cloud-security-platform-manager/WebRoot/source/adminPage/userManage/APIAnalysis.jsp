<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>API分析</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>

<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/dateSlider.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.ui.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<!--<script type="text/javascript" src="${ctx}/source/scripts/common/modelbox.js"></script>
--><script type="text/javascript" src="${ctx}/source/scripts/common/raphael.2.1.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/justgage.1.0.1.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/apiAnalysis.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/DateSlider.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<style type="text/css">
.clearfix:after {content: " "; display: block; clear: both; height: 0; visibility: hidden; line-height: 0;font-size:0; }
.clearfix{ zoom:1;}
.text{margin-bottom: 30px;}
.text span{display: inline-block; width: 23%; }
.text span, .text span i{font-size: 18px; color: #009393; font-weight: 700;}
.text  span{float:left;}
.text span i{ font-style: normal; padding: 0 10px; }
#dateSlider{width:970px; height:40px; padding-top: 20px;}
.info-33{ float: left; width:33%; }
.info-50{ float: left; width:50%; }
.infos{ margin-top: 30px; }
.infos dt{padding: 10px 0; text-align: center; font-size: 24px}
.infos .cont{ padding: 5px 10px; min-height: 200px; /*overflow: scroll;  border: 1px solid #ccc;*/} 
.myCustomClass > span{ display: none; }
.ui-rangeSlider-label-value, .ui-ruler-tick-label{ font-size: 16px; }
  	.assetsaAdd{
	    display: none;
		padding: 20px;
		position: fixed;
		top: 50%;
		left: 50%;
		z-index: 99;
		text-align: center;
		background: #fff;
		border-radius: 10px;
		width: 670px;
		height: 400px;
		margin-left:-355px;
		margin-top:-220px;
			
}
popBoxhide .close{ width:26px; height:26px; display:block; position:absolute; right:12px; top:13px; background:url(../images/user_ico_4.jpg) no-repeat; cursor:pointer;}


.user_table tbody tr {height: 40px;line-height: 40px;}
.user_table thead tr th:nth-child(1) {width: 50px;padding-left: 60px;padding-right: 50px;}
.user_table thead tr th:nth-child(2) {padding-right: 160px;}
.user_table thead tr th:nth-child(3) {padding-right: 100px;}
.user_table tbody tr td:nth-child(1) {width: 50px;padding-left: 60px;padding-right: 50px;}
.user_table tbody tr td:nth-child(2) {width: 200px;text-align: left;}
.user_table tbody tr td:nth-child(3) {width: 100px;text-align: left;}
.analyse_lable label {
    color: #888888;
    font-size: 16px;
}
.analyse_lable .text {
    width: 136px;
    height: 26px;
    border: #e0e0e0 solid 1px;
}
.analyse_lable .sub {
    width: 82px;
    height: 32px;
    background: url(${ctx}/source/images/b_search_bg.jpg) no-repeat;
    border: none;
    position: absolute;
    right: 10px;
    cursor: pointer;
}
</style>
 <script type="text/javascript">

	

	
   </script> 
</head>

<body>
<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
<div class="main_wrap">
	<div class="main_center">
		<div class="text clearfix">
			<span>API累计调用数<i>${apiUseCount}</i></span>
			<span>扫描网站数<i>${assetCount}</i></span>
			<span>接入APIKEY数<i>${apiCount}</i></span>
		</div>
	
		<!-- <div class="dateSlider" id="dateSlider"></div> -->
		<div class="analyse_lable" style="width:100%;display: block;margin-top: 50px;height:50px">
            <label>统计开始时间 </label>
            <input type="text" class="text"  value="" id="begin_date1" name="begin_datevo" style="width:110px;text-align: center;" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})">
            <label> - 结束时间 </label>
            <input type="text" class="text"  value="" id="end_date1" name="end_datevo" style="width:110px;text-align: center;" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})">
            <input type="button" class="sub" value="" id="analysisId">
         </div>
	
	    	<div class="data_title" style="margin-top: 0px;">
	        	<button class="dataana_btn datab_cur fl" id="dd_btn">服务类型统计</button>
	            <div class="bor_t_small fl"></div>
	            <button class="dataana_btn fl" id="gj_btn">用户信息统计</button>
	            <div class="bor_t_big fl"></div>
	        </div>
	        <div class="dd_data_box">
	        <div class="infos clearfix">
			<dl class="info-33">
				<dt>web漏洞扫描API</dt>
				<dd class="cont" style="height:234px">
					<div style="height:214px;width:340px"  id="api1"></div>
				</dd>
					
			</dl>
			<dl class="info-33">
				<dt>木马检测API</dt>
				<dd class="cont" style="height:234px">
					<div style="height:214px;width:340px"  id="api2"></div>
				</dd>
			</dl>
			<dl class="info-33">
				<dt>网页篡改监测API</dt>
				<dd class="cont" style="height:234px">
					<div style="height:214px;width:340px"  id="api3"></div>
				</dd>
			</dl>
			<dl class="info-33">
				<dt>网页敏感内容监测API</dt>
				<dd class="cont" style="height:234px">
					<div style="height:214px;width:340px"  id="api4"></div>
				</dd>
			</dl>
			<dl class="info-33">
				<dt>网站可用性监测API</dt>
				<dd class="cont" style="height:234px">
					<div style="height:214px;width:340px"  id="api5"></div>
				</dd>
			</dl>
			<dl class="info-33">
				<dt>会话管理API</dt>
				<dd class="cont" style="height:234px">
					<div style="height:214px;width:340px"  id="api6"></div>
				</dd>
			</dl>
			<dl class="info-50">
				<dt>API服务使用时段统计</dt>
				<dd class="cont" style="height:234px">
					<div style="height:214px;"  id="apiUseBar"></div>
				</dd>
			</dl>
			<dl class="info-50">
				<dt>最近7日使用趋势图</dt>
				<dd class="cont" style="height:234px">
					<div style="height:214px;"  id="apiUseLine"></div>
				</dd>
			</dl>
		</div>
        </div>
        <div class="gj_data_box">
        	<div class="system_pz" style="margin:0px;width:1200px;height:800px">
			    <div>
			      <input type="text" name="name" id="searchName" style="border:1px solid red;" placeholder="请输入用户名">
			      <a id="search" href="javascript:;" onclick="analysisAPIUser();">搜索</a>
			    </div>   
			<div class="infos clearfix">
				<dl class="info-50" style="width:100%;">
					<dt>API服务使用时段统计</dt>
					<dd class="cont" style="height:300px">
						<div style="height:280px;width:1000px"  id="userBar"></div>	
					</dd>
				</dl>
				
			</div> 
			<div class="infos clearfix">
				<dl class="info-50">
					<dt>开发者调用次数TOP5</dt>
					<dd class="cont" style="height:234px">
						<div style="height:214px;width:500px" id="userBarTop5"></div>	
					</dd>
				</dl>
				<dl class="info-50">
					<dt>开发者使用服务统计</dt>
					<dd class="cont">
						<div style="height:214px;width:500px" id="apiCountForUser"></div>	
					</dd>
				</dl>
				<!---<dl class="info-50">
					<a id="allAPIUsers" href="javascript:;">查看完整列表</a>
				</dl>-->
			</div> 
			<div class="infos clearfix">
				<dl class="info-50">
					<a id="allAPIUsers" href="javascript:;">查看完整列表</a>
				</dl>
			</div> 
            </div>
        </div>
    </div>
</div>

    <!---完整列表-->
<div class="assetsaAdd hide popBoxhide" id="revise">
	  <div class="add_ser_top w678" style="margin-left:0px">
	<p class="w634">用户列表</p><p id="close" class="modelclose"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
   </div> 
        <div class="system_table" style="display:block;overflow-y:auto;height:260px">
        	<table class="user_table" cellpadding="0" cellspacing="0" style="width:670px;">
            	<thead>
                	<tr>
                		<th class="t_username" style="text-align:center">序号</th>
                    	<th class="t_username" style="text-align:center">用户名</th>
                        <th class="t_date" style="text-align:center">使用API次数</th>
                    </tr>
                </thead>
                <tbody id="apiUserCountList">
	              

                </tbody>
            </table>
        </div>
</div>
<!--============bottom============-->
	<!-- footer start -->
	<c:import url="/footer.html"></c:import>
	<!-- footer end -->
</div>
</body>
</html>
