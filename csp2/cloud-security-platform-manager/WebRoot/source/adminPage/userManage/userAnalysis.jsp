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
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript"><!--
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
--></script>
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
        <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">数据分析</a></li>
        <li class="b_current"><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
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
	<div class="main_center">
    	<div class="data_title">
            <button class="dataana_btn datab_cur fl" id="yy_btn" >用户活跃度</button>
            <div class="bor_t_small fl"></div>
        	<button class="dataana_btn fl" id="dd_btn" >用户使用习惯</button>
            <div class="bor_t_small fl"></div>
            <button class="dataana_btn fl" id="gj_btn" >用户行业分布</button>
            <div class="bor_t_big fl"></div>
        </div>
		<div class="sj_data_box">
        	<div class="data_choose">
            	<form action="${ctx}/adminUserAnalysis.html" method="post" id="searchForm" >
               	  <label class="fl">统计时段</label>
               	 	  <div class="se_big fl">
			        	<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" id="begin_date" name="begin_datevo" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			      </div>
			      <div style="float:left; width:20xp;margin-right:15px;">--</div>
                   <div class="se_big fl">
			     		<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" id="end_date" name="end_datevo"  onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			      </div>
                  <label class="fl"></label>
		            <select id="content" name="content" class="se_small fl se_last">
		         		<option selected="selected" value="-1">请选择统计内容</option>
		         		<option value="1" >登录用户数量</option>
		         		<option value="2" >用户活跃度排行榜</option>
		    		</select>
		    		<input type="button" class="dd_select fl ml20" value="" onclick="userAnalysis()"/>
                </form>
            </div>
	   <div style=" margin-top:30px; margin-bottom:20px; clear:both; overflow:hidden; height:300px;display:none" id="m_user_num">
       <div class="m_user_table_box" id="m_supper">
           <div class="b_user_table_c" >
                <span class="user_title">登录系统用户数</span>
                <span class="user_num">${loginUserNum}</span>
           </div>
       </div>
       <div class="m_user_table_box p_user_table" id="n_supper">
       <div class="b_user_table_c" style="border-right:1px solid #e0e0e0;">
             <span class="user_title">占所有用户数比例</span>
            <span class="user_num">${loginUserPercent}%</span>
       </div>

       </div>
     
  	</div>
  	   <div style="margin-top:30px; margin-bottom:20px; clear:both; overflow:hidden; height:300px;" id="m_user_default">
       </div>
    <div class="system_table" style="margin-top:30px; margin-bottom:20px; clear:both; overflow:hidden; height:300px; display:none" id="m_user_percent">
 	<table class="user_table" cellpadding="0" cellspacing="0">

         	<tr>
             	<th class="t_username">序号</th>
                 <th class="t_date">用户名</th>
             </tr>
             <tr>
             	<td class="t_username">1</td>
	            <td class="t_date">a</td>
	         </tr>
             <tr>
             	<td class="t_username">2</td>
	            <td class="t_date">b</td>
	         </tr>
     </table>
 	</div>
       </div>
		<div class="dd_data_box" style="display:none;">
        	<div class="data_choose">
            	<form action="${ctx}/adminUserHabitsAnalysis.html" method="post" id="habitsSearchForm">
               	  <label class="fl">统计时段</label>
               	 	  <div class="se_big fl">
			        	<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" id="begin_date_habits" name="begin_datevo_habits" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			      </div>
			      <div style="float:left; width:20xp;margin-right:15px;">--</div>
                   <div class="se_big fl">
			     		<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" id="end_date_habits" name="end_datevo_habits"  onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			      </div>
                  <label class="fl"></label>
		            <select id="content_habits" name="content_habits" class="se_small fl se_last">
		         		<option selected="selected" value="-1">请选择统计内容</option>
		         		<option value="1" >用户登录时间段</option>
		         		<option value="2" >用户下单时间段</option>
		    		</select>
		    		<input type="button" class="dd_select fl" value="" onclick="userHabitsAnalysis()"/>
                </form>
            </div>
    <div class="system_table" style="margin-top:30px; margin-bottom:20px; clear:both; overflow:hidden; height:300px; display:none" id="m_login_time">
 	<table class="user_table" cellpadding="0" cellspacing="0">

         	<tr>
             	<th class="t_username">序号</th>
                 <th class="t_date">登录时间段</th>
             </tr>
             <tr>
             	<td class="t_username">1</td>
	            <td class="t_date">a</td>
	         </tr>
             <tr>
             	<td class="t_username">2</td>
	            <td class="t_date">b</td>
	         </tr>
     </table>
 	</div>
    <div class="system_table" style="margin-top:30px; margin-bottom:20px; clear:both; overflow:hidden; height:300px; display:none" id="m_order_time">
 	<table class="user_table" cellpadding="0" cellspacing="0">

         	<tr>
             	<th class="t_username">序号</th>
                 <th class="t_date">下单时间段</th>
             </tr>
             <tr>
             	<td class="t_username">1</td>
	            <td class="t_date">a</td>
	         </tr>
             <tr>
             	<td class="t_username">2</td>
	            <td class="t_date">b</td>
	         </tr>
     </table>
 	 </div>
 	 	<div style="margin-top:30px; margin-bottom:20px; clear:both; overflow:hidden; height:300px;" id="m_habits_default">
       </div>
     </div>
      <div class="gj_data_box">
       	<div class="data_choose gjchoose">
           	<form action="${ctx}/adminIndustryAnalysis.html" method="post" id="industryForm">
              	  <label class="fl">统计时段</label>
              	  <div class="se_big fl">
		        	<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="begin_date_industry" name="begin_datevo_industry" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		      </div>
		      <div style="float:left; width:20xp;margin-right:15px;">--</div>
                  <div class="se_big fl">
		     		<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="end_date_industry" name="end_datevo_industry"  onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		      </div>
	             <select id="industry" name="industry" class="se_small fl se_last">
		        	<option selected="selected" value="-1">请选择所属行业</option>
		      		<option value="1" >漏洞扫描服务</option>
                       <option value="2" >恶意代码监测服务</option>
                       <option value="3" >网页篡改监测服务</option>
                       <option value="4" >关键字监测服务</option>
                       <option value="5" >可用性监测服务</option>
                       <option value="6" >日常流量监测服务</option>
                       <option value="7" >日常攻击防护服务</option>
                       <option value="8" >突发异常流量清洗服务</option>
		    	</select>
                 <input type="button" class="dd_select fl ml20" value="" onclick="industryAnalysis()"/>
               </form>
           </div>
            <div class="data_detail" id="warningDataAnalysis">
       	    	<!-- <img src="${ctx}/source/adminImages/b_dataanalysis.jpg" width="1097" height="351"> -->
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
