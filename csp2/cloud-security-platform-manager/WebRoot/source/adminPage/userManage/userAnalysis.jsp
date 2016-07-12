<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户分析</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/raphael.2.1.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/justgage.1.0.1.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/userAnalysis.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//	用户活跃度统计分析
	$("#begin_date").val("${begin_date}");
	$("#end_date").val("${end_date}");
	$("#content").val("${content}");
	if("${content}"=="1"){
		$("#m_user_num").show();
		$("#m_user_percent").hide();
		$("#m_user_default").hide();
	}else if("${content}"=="2"){
		$("#m_user_num").hide();
		$("#m_user_percent").show();
		$("#m_user_default").hide();
	}else{
		$("#m_user_num").hide();
		$("#m_user_percent").hide();
		$("#m_user_default").show();
	}



});

</script>
</head>

<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
      	<li><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
		<li><a href="${ctx}/adminchinas.html" target="_blank" class="white">安全态势</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAssetUI.html" class="white">资产分析</a></li>
        <li class="b_current"><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
        <!-- <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">订单分析</a></li>-->
        <li><a href="${ctx}/orderformanalyse.html" class="white">订单分析</a></li>
        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li><a href="${ctx}/adminAPIAnalysisUI.html" class="white">API分析</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
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
	<div class="main_center user">
    	<ul class="main_nav tabList clearfix">
        	<li class="active">用户活跃度统计分析<i></i></li>
            <li class="">用户使用习惯统计分析<i></i></li>
            <li class="">用户行业分布统计<i></i></li>
        </ul>
        <div class="tabBox tabCont">
        	<div class="item clearfix tabItem" style="display:block">
            	 <!--用户分析-->
                           <form class="clearfix analysecent user_form" id="activeForm" method="post" action="${ctx}/adminUserAnalysis.html">
                               
                                 <div class="analyse_lable fl">
                                    <label>统计开始时间</label>
                                    <input type="text" class="text" id="begin_date_active" name="begin_date" value="" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                    <label>-结束时间</label>
                                    <input type="text" class="text" id="end_date_active" name="end_date" value="" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                </div>
                                <div class="analyse_lable fl">
                                    <label>统计内容</label>
                                    <select class="text user_select" id="activeSel" name="activeSel">
                                        <option value="1">登录用户数量</option>
                                        <option value="2">用户活跃度排行榜</option>
                                    </select>
                                </div>
                                <input type="button" class="sub" value="" style="right:-130px;" onclick="activeAnalysis()">

                            </form>
                            <div class="tableBox">
                            	<style>
									.tableBox{ margin-top:0px;}
                                </style>
                                
                                <div class="tableUser" style="">
                                	<table cellpadding="0" cellspacing="0" border="1" width="954" bordercolor="#e0e0e0">
                                    <thead>
                                        <tr style="width:100%">
                                            <th>登录用户数量</th>
                                            <th>占系统所有用户数的比例</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td id="loginCount"></td>
                                            <td id="loginParent"></td>
                                        </tr>                                        
                                    </tbody>
                                </table>
                                
                                </div>
                                <div class="tableUsert" style="height:260px; overflow-y:auto; display:none;">
                                	<table cellpadding="0" cellspacing="0" border="1" width="954" bordercolor="#e0e0e0">
                                    <thead>
                                        <tr style="width:100%">
                                           <th width="180">序号</th>
                                            <th  colspan="4" width="611">登录最频繁用户TOP10</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tId">
                                     <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                    </tbody>
                                </table>
                                
                                </div>
                                
                            </div>	
            </div>
            <div class="item tabItem">
            		 <form class="clearfix analysecent user_form">
                               
                                 <div class="analyse_lable fl">
                                    <label>统计开始时间</label>
                                    <input type="text" class="text" id="begin_date_use" name="begin_date" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                    <label>-结束时间</label>
                                    <input type="text" class="text" id="end_date_use" name="end_date" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                </div>
                                <div class="analyse_lable fl">
                                    <label>统计内容</label>
                                    <select class="text user_select" id="useSel">
                                        <option value="1">用户登录时间段</option>
                                        <option value="2">用户下单时间段</option>
                                    </select>
                                </div>
                                <input type="button" class="sub" value="" style="right:-130px;" onclick="useAnalysis()">
                            </form>
                     <div class="tableBox">
                            	
                                <div class="tableUser" style="height:260px; overflow-y:auto;">
                                	<table cellpadding="0" cellspacing="0" border="1" width="954" bordercolor="#e0e0e0">
                                    <thead>
                                        <tr style="width:100%">
                                            <th width="180">序号</th>
                                            <th colspan="4" width="611">用户登录最集中的时间段TOP5</th>
                                        </tr>
                                    </thead>
                                    <tbody id="body1">
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                    </tbody>
                                </table>
                                
                                </div>
                                <div class="tableUsert" style="height:260px; overflow-y:auto; display:none;">
                                	<table cellpadding="0" cellspacing="0" border="1" width="954" bordercolor="#e0e0e0">
                                    <thead>
                                        <tr style="width:100%">
                                            <th width="180">序号</th>
                                            <th colspan="4" width="611">用户下单最集中的时间段TOP5</th>
                                        </tr>
                                    </thead>
                                    <tbody id="body2">
                                     <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="4"></td>
                                        </tr>
                                    </tbody>
                                </table>
                                
                                </div>
                                
                            </div>	
            </div>
            <div class="item tabItem">
            		<form class="clearfix analysecent user_form">
                               
                         <div class="analyse_lable fl">
                            <label>统计开始时间</label>
                            <input type="text" class="text" id="begin_date_industry" name="begin_date" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                            <label>-结束时间</label>
                            <input type="text" class="text" id="end_date_industry" name="end_date" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                        </div>
                        <div class="analyse_lable fl">
                            <label>用户所属行业</label>
                            <select class="text" id="industry">
                            	<option selected="selected" value="-1">请选择行业</option>
								<option value="农、林、牧、渔业" > 农、林、牧、渔业</option>
			         			<option value="采矿业" > 采矿业</option>
			         			<option value="电力、热力、燃气及水生产和供应业" >电力、热力、燃气及水生产和供应业</option>
			         			<option value="建筑业" >建筑业</option>
			         			<option value="批发和零售业" >批发和零售业</option>
			         			<option value="交通运输、仓储和邮政业" >交通运输、仓储和邮政业</option>
			         			<option value="住宿和餐饮业" >住宿和餐饮业</option>
			         			<option value="信息传输、软件和信息技术服务业" >信息传输、软件和信息技术服务业</option>
			         			<option value="金融业" >金融业</option>
			         			<option value="房地产业" >房地产业</option>
			         			<option value="租赁和商务服务业" >租赁和商务服务业</option>
			         			<option value="科学研究和技术服务业" >科学研究和技术服务业</option> 
                            </select>
                        </div>
                        <input type="button" class="sub" value="" style="right:-130px;" onclick="indusAnalysis()">
                    </form>	
                    <div class="tableBox">
                    				
                                	<table cellpadding="0" cellspacing="0" border="1" width="954" bordercolor="#e0e0e0">
                                    <thead>
                                        <tr style="width:100%">
                                            <th width="180">序号</th>
                                            <th colspan="2" width="305">用户</th>
                                            <th colspan="2" width="305">用户所属行业</th>
                                        </tr>
                                    </thead>
                                    <tbody id="body3">
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                    </tbody>
                                </table>
                                
                                </div>
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
</body>
</html>
