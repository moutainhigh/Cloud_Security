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
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/raphael.2.1.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/justgage.1.0.1.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/sysManage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/modules/exporting.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/highcharts-3d.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script>
var g1;
$(function () {
	test();
	window.setInterval(test,3000); 
});
function test(){
	$.ajax({
	      type: "POST",
	      url:"adminSysCpuUsage.html",
	      dataType:"json",
	      success: function(data){
		      var printCpuPerc;
	 		  $.each(data,function(i,p){
	          	printCpuPerc=p['printCpuPerc'];
	          });
	          $("#system1").html("");
	          var g1 = new JustGage({
		          id: "system1", 
		          value: printCpuPerc, 
		          min: 0,
		          max: 100,
		          title: "Speedometer",
		          label: "percent",
					levelColors: [
					  "#FF0000",
					  "#FFF000",
					  "#FF0000"
					]    
		        });
	      	},
	   });
}
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
        <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">数据分析</a></li>
        <li class="b_current"><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
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
        	<button class="dataana_btn datab_cur fl" id="dd_btn">系统资源</button>
            <div class="bor_t_small fl"></div>
            <button class="dataana_btn fl" id="gj_btn">参数配置</button>
            <div class="bor_t_big fl"></div>
        </div>
        <div class="dd_data_box">
        	<div class="system_zy">
            	<div class="zy_left fl">
                	<div class="zy_top1">
                    	<div class="zy_top_l fl" id="system1">
                        	<!--<img src="${ctx}/source/adminImages/system1.jpg" width="249" height="164">  -->
                        </div>
                        <div class="zy_top_r fl" id="system2">
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
                            	<p><span class="nc_p">总内存</span><span class="nc_num">${total}GB</span></p>
                                <!-- <p><span class="nc_p">已使用内存</span><span class="nc_num_r">${use}MB</span></p> -->
                            </div>
                        </div>
                        <div class="zy_top_r fl" id="system4">
                        	<!-- <img src="${ctx}/source/adminImages/system4.jpg"> -->
                        </div>
                    </div>
                    <div class="zy_top2 h30">
                    	<p class="system_title">内存占用情况</p>
                    </div>
                </div>
                <div class="zy_right fl">
                	<div class="zy_rtop1" id="system5">
                    	<!-- <img src="${ctx}/source/adminImages/system5.jpg"> -->
                    </div>
                    <div class="zy_rtop2">
                    	<!-- <img src="${ctx}/source/adminImages/system6.jpg"> -->
                    	<div>磁盘总大小：${totalSpace}GB</div>
                    	
                    </div>
                    <div class="zy_top2 h30 w364">
                    	<p class="system_title">磁盘占用情况</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="gj_data_box">
        	<div class="system_pz">
            	<form id="form_sys" action="${ctx}/adminSaveServerParam.html" method="post">
                	<div class="pz_form">
                    	<label>用户会话时长</label>
                        <input type="text" name="sessionTime" value="${serverParamConfiguration.sessionTime}" class="pz_input">
                    </div>
                    <div class="pz_form">
                    	<label>邮件服务器地址</label>
                        <input type="text" name="serverEmailAdd" value="${serverParamConfiguration.serverEmailAdd}" class="pz_input">
                    </div>
                    <div class="pz_form">
                    	<label>邮件用户名</label>
                        <input type="text" name="serverEmailName" value="${serverParamConfiguration.serverEmailName}" class="pz_input">
                    </div>
                    <div class="pz_form">
                    	<label>邮件密码</label>
                        <input type="password" name="serverEmailPassword" value="${serverParamConfiguration.serverEmailPassword}" class="pz_input">
                    </div>
                    <div class="pz_form mt20">
                    	<input type="button" class="system_btn" onclick="sysForm()" value="保存"/>
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
      <li><a href="${ctx}/registUI.html">新用户注册</a></li>
    <li><a href="${ctx}/loginUI.html">用户登录</a></li>
    <li><a href="${ctx}/forgetPass.html">找回密码</a></li>
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
    <li><a href="###">绿盟</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">华为</a></li>
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
