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
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/dataAnalysis.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/modules/exporting.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/highcharts-3d.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
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
        <li><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
        <li class="b_current"><a href="${ctx}/adminDataAnalysisUI.html" class="white">订单分析</a></li>
        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li><a href="${ctx}/adminAPIAnalysisUI.html" class="white">API分析</a></li>
        <li><a href="${ctx}/adminLogsAnalysisUI.html" class="white">日志分析</a></li>
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
    		<button class="dataana_btn datab_cur fl" id="yy_btn" >运营数据统计</button>
            <div class="bor_t_small fl"></div>
        	<button class="dataana_btn fl" id="dd_btn" >订单统计分析</button>
            <div class="bor_t_small fl"></div>
            <button class="dataana_btn fl" id="gj_btn" >告警统计分析</button>
            <div class="bor_t_big fl"></div>
        </div>
         <!-- 运营统计 -->
         <div class="sj_data_box">
             <div style=" margin-top:30px; margin-bottom:20px; clear:both; overflow:hidden; height:300px;">
                 <div class="m_user_table_box" id="m_supper">
                     <div class="m_user_table_c" >
                          <span class="user_title">注册用户数</a></span>
                          <span class="user_num">${registCount}</span>
                     </div>
                 </div>
                 <div class="m_user_table_box p_user_table" id="n_supper">
                 <a target="_blank" href="${ctx}/adminHaveServ.html">
                 <div class="m_user_table_c">
                       <span class="user_title">活跃用户数</span>
                      <span class="user_num">${haveServCount}</span>
                 </div>
                 </a>
                 </div>
                 <div class="m_user_table_box p_user_table" id="o_supper">
                 <a target="_blank" href="${ctx}/adminAssetAddr.html">
                 <div class="m_user_table_c">
                       <span class="user_title">监测网站数</span>
                      <span class="user_num">${asserAddrCount}</span>
                 </div>
                 </a>
                 </div>
                 <div class="m_user_table_box"id="p_supper">
                  <div class="m_user_table_c" style="border-right:1px solid #e0e0e0;">
                      <span class="user_title">扫描页面数</span>
                      <span class="user_num">${alarmCount}</span>
                 </div>
                 </div>
            </div>
        </div>
        <div class="dd_data_box" style="display:none;">
        	<div class="data_choose dd_choose">
            	<form action="${ctx}/adminOrderStatisticsAnalysis.html" method="post" id="searchForm" >
               	  <label class="fl">统计时段</label>
               	 	  <div class="se_big fl">
			        	<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="begin_date" name="begin_datevo" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			      </div>
			      <div style="float:left; width:20xp;margin-right:15px;">--</div>
                   <div class="se_big fl">
			     		<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="end_date" name="end_datevo"  onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			      </div>
                  <label class="fl"></label>
		            <select id="type" name="type" class="se_small fl se_last">
		         		<option selected="selected" value="">请选择订单类型</option>
		         		<option value="1" >长期</option>
		         		<option value="2" >单次</option>
		    		</select>
                  <label class="fl"></label>
                  <select id="servName" name="servName" class="se_small fl se_last">
		        	<option selected="selected" value="">请选择订单服务类型</option>
		      		<option value="漏洞扫描服务" >漏洞扫描服务</option>
		      		<option value="恶意代码监测服务" >恶意代码监测服务</option>
		      		<option value="网页篡改监测服务" >网页篡改监测服务</option>
		      		<option value="关键字监测服务" >关键字监测服务</option>
		      		<option value="可用性监测服务" >可用性监测服务</option>
		      		<option value="日常流量监测服务" >日常流量监测服务</option>
		      		<option value="日常攻击防护服务" >日常攻击防护服务</option>
		      		<option value="突发异常流量清洗服务" >突发异常流量清洗服务</option>
		    	</select>
                  <label class="fl"></label>
                   <select class="se_small fl se_last" id="state" name="state">
			            <option selected="selected" value="">请选择订单状态</option>
			            <option value="1" >服务中</option>
			            <option value="2" >已结束</option>
			       </select>
                  <input type="button" class="dd_select fl" value=""  onclick="orderData()"/>
                </form>
            </div>
            <div class="data_detail" id="orderStatisticsAnalysis">
       	    	<!--  <img src="${ctx}/source/adminImages/b_dataanalysis.jpg" width="1097" height="351"> -->
            </div>
        </div>
        <div class="gj_data_box">
        	<div class="data_choose gjchoose">
            	<form action="${ctx}/adminWarningData.html" method="post" id="warningForm">
               	  <label class="fl">统计时段</label>
               	  <div class="se_big fl">
			        	<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="begin_dateW" name="begin_datevo" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			      </div>
			      <div style="float:left; width:20xp;margin-right:15px;">--</div>
                   <div class="se_big fl">
			     		<input style="border:1px solid #e0e0e0; height: 28px; line-height: 28px;" type="text" value="" id="end_dateW" name="end_datevo"  onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			      </div>
		             <select id="alarm_type" name="alarm_type" class="se_small fl se_last">
			        	<option selected="selected" value="">请选择服务类型</option>
			      		<!-- <option value="漏洞扫描" >漏洞扫描服务</option>
			      		<option value="恶意代码监测" >恶意代码监测服务</option>
			      		<option value="网页篡改监测" >网页篡改监测服务</option>
			      		<option value="关键字监测" >关键字监测服务</option>
			      		<option value="可用性监测" >可用性监测服务</option>
			      		<option value="日常流量监测" >日常流量监测服务</option>
			      		<option value="日常攻击防护" >日常攻击防护服务</option>
			      		<option value="突发异常流量清洗" >突发异常流量清洗服务</option>-->
			      		<option value="1" >漏洞扫描服务</option>
                        <option value="2" >恶意代码监测服务</option>
                        <option value="3" >网页篡改监测服务</option>
                        <option value="4" >关键字监测服务</option>
                        <option value="5" >可用性监测服务</option>
                        <option value="6" >日常流量监测服务</option>
                        <option value="7" >日常攻击防护服务</option>
                        <option value="8" >突发异常流量清洗服务</option>
			    	</select>
              		<select class="se_small fl se_last" id="level" name="level">
              			<option selected="selected" value="">请选择告警级别</option>
                    	<option value="0">低</option>
                    	<option value="1">中</option>
                    	<option value="2">高</option>
                    </select>
                  <input type="button" class="dd_select fl ml20" value="" onclick="warningData()"/>
                </form>
            </div>
            <div class="data_detail" id="warningDataAnalysis">
       	    	<!-- <img src="${ctx}/source/adminImages/b_dataanalysis.jpg" width="1097" height="351"> -->
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
