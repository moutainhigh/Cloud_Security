<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>我的订单-告警详情</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet" />	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />

<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/wafHistory.js"></script>
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>

<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>


<script type="text/javascript">
 <%   
         java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM"); 
         java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
         String str_date = formatter.format(currentTime); //将日期时间格式化 
         
         java.text.SimpleDateFormat formatterY = new java.text.SimpleDateFormat("yyyy"); 
         String str_dateY = formatterY.format(currentTime); //将日期时间格式化 
         
         SimpleDateFormat formatterW = new SimpleDateFormat("yyyy-MM-dd");    
         String str_dateW = formatterW.format(currentTime); //将日期时间格式化    
    	 Calendar cal = Calendar.getInstance();
    	 cal.setTime(formatterW.parse(str_dateW));
         // Calendar默认周日为第一天, 所以设置为1
         cal.set(Calendar.DAY_OF_WEEK, 1);
 %>
 
$(document).ready(function(){
	 //初始化时间格式
	$("#weeknumber").hide(); 
	$('#beginDate_m').val('<%=str_date %>');
	$('#beginDate_y').val('<%=str_dateY %>');
     	$('#beginDate_w').val('<%=str_dateW %>');
	$("#beginDate_m").bind("focus", function () {
		WdatePicker({dateFmt: 'yyyy-MM', isShowToday: false, isShowClear: false, readOnly:true });  
	});
	
	$("#beginDate_y").bind("focus", function () {
   		WdatePicker({ dateFmt: 'yyyy', isShowToday: false, isShowClear: false, readOnly:true });
   	});
	
	$("#beginDate_w").bind("focus", function () {
   		WdatePicker({ dateFmt: 'yyyy-MM-dd', isShowWeek:true,isShowClear: false, readOnly:true, onpicked:function(){$dp.$('weeknumber').value='第'+$dp.cal.getP('W','W')+'周'; }  });
   	});
	var beginD = GetQueryString("beginDate"); 
	var be = beginD.split("-");
	var s = be.length;
	if(s == 1){
		$('#beginDate_y').val(beginD);
		$("#beginDate_y").show();
	}else if(s == 2){
		$('#beginDate_m').val(beginD);
		$("#beginDate_m").show();
	}else if(s == 3){
		$('#beginDate_w').val(beginD);
		$("#beginDate_w").show();
		$("#weeknumber").show();
	}
 
     //radio点击事件
     $('input:radio[name="radioType"]').on('click', function() {    	
        var type=$(this).val();
        if(type=='month'){
        	$('input:text[name="beginDate"]').hide();
        	$("#weeknumber").hide();
        	$("#beginDate_m").show();
   		}else if(type=='year'){
   			$('input:text[name="beginDate"]').hide();
   			$("#weeknumber").hide();
        	$("#beginDate_y").show();
   		}else if(type=='week'){
   			$('input:text[name="beginDate"]').hide();
        	$("#beginDate_w").show();
   			if($("#weeknumber").is(":hidden")){$("#weeknumber").show();}
   			
   		}
     });
     
     /*
     function getMonday(){
		  //var today=new Date();
		  var today=new Date($("#beginDate").val().replace(/-/g,   "/"));
		  var weekday=today.getDay();    
		  var monday=new Date(1000*60*60*24*(1-weekday) + today.getTime());
		  var sunday=new Date(1000*60*60*24*(7-weekday) + today.getTime());      
		  $("#beginDate").val(getDateStr(monday)+" "+getDateStr(sunday));
	 }
	 function getDateStr(dd){
	     var y = dd.getFullYear();
	 
	     var m = dd.getMonth()+1;//获取当前月份的日期
	     m=parseInt(m,10);
	     if(m<10){
	         m="0"+m;
	     }
	 
	     var d = dd.getDate();
	     d=parseInt(d,10);
	     if(d<10){
	         d="0"+d;
	     }
	     return y+"-"+m+"-"+d;
	 }
	 
	 function getSunday(){
	    //var today=new Date();
	    var today=new Date($("#beginDate").val().replace(/-/g,   "/"));
	    var weekday=today.getDay();    
	    var sunday=new Date(1000*60*60*24*(7-weekday) + today.getTime());    
	    $("#beginDate").val(getDateStr(sunday));
	 }
	 */
	 //图表
	 var startDate;
	var type=$('input:radio[name="radioType"]:checked').val();
	if(type == 'year'){
		startDate=$("#beginDate_y").val();
	}else if(type == 'month'){	
		startDate=$("#beginDate_m").val();
	}else if(type == 'week'){
		startDate=$("#beginDate_w").val();
		
	}
   	 var timeUnit = $('input:radio[name="radioType"]:checked').val();
   	 
 	 pielevel(startDate,timeUnit); //事件风险分布图 	 
 	 pieEvent(startDate,timeUnit);//事件类型统计图、分布图
 	 //barEvent(startDate,timeUnit);
 	 lineEvent(startDate,timeUnit);//告警时间短折线图、表格
   	 updateAttackSourceTable(startDate,timeUnit);//攻击源

     
}); 

$(function() {
     $(".data_cuo").on('click', function() {
        $(".mark,.data_tanc").hide();
     });
     
     $(".data_btn").on('click', function() {
        $(".mark,.data_tanc").hide();
     });
 });

function GetQueryString(name) {  
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
    var r = window.location.search.substr(1).match(reg);  //获取url中"?"符后的字符串并正则匹配
    var context = "";  
    if (r != null)  
         context = r[2];  
    reg = null;  
    r = null;  
    return context == null || context == "" || context == "undefined" ? "" : context;  
}

function generate(){
	var type=$('input:radio[name="radioType"]:checked').val();
	var beginDate;
	if(type == 'year'){
		beginDate=$("#beginDate_y").val();
	}else if(type == 'month'){	
		beginDate=$("#beginDate_m").val();
	}else if(type == 'week'){
		beginDate=$("#beginDate_w").val();
	   	var s = beginDate.replace(/-/g,"/");
		var date = new Date(s);
		var curTime = date.getTime() ; 
		var day = date.getDay();
		var oneDayTime = 24*60*60*1000 ; 

		//显示周一
		var MondayTime = curTime - (day-1)*oneDayTime ; 
		//显示周日
		//var SundayTime =  curTime + (7-day)*oneDayTime ; 

		//初始化日期时间
		var monday = new Date(MondayTime);
		//var sunday = new Date(SundayTime);
		beginDate = monday.getFullYear() + '-' + (monday.getMonth() + 1) + '-' + monday.getDate(); 

	}
	window.location.href='warningWaf.html?orderId=${order.id }&isHis=1&type='+type+'&beginDate='+beginDate;
}
</script>

</head>
<style type="text/css">
   .data_box{ width: 1180px; margin-top: 20px;margin-bottom: 60px; }
   .data_nav{ height: 38px; /*border-bottom: 1px solid #cfcfcf;*/ margin-bottom: 10px;} 
   .data_nav ul li { width: 160px; float:left; text-align: center; line-height: 38px; height: 38px; font-size: 16px; border: 1px solid #a6a6a6;  border-bottom: 0px; border-radius: 10px 10px 0 0 ;}
   .data_min{clear: both; overflow: hidden; }
   .data_1{ width:382px; border:1px solid #cbcbcb; height: 380px; float: left; margin-right: 13px;}
   .data_table{ width: 1138px; height: 360px; border:1px solid #cbcbcb; margin-top: 15px; padding: 20px;}
   .data_table_tab {margin-bottom: 20px; width: 100%; }
   .data_table_tab tr th{ border-bottom: 1px solid #000;height: 40px; text-align: left;} 
   .data_table_tab tr td{ height: 40px; background: #f0f8fa;} 
   .data_table_cont a{ color: #00bfff;}
   .mark{ width: 100%; 
    height: 100%;
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    background: #000;
    filter: alpha(opacity=40);
    opacity: 0.4;
    z-index: 400;}
   .data_tanc{ width: 680px; height: 355px;  position: fixed; z-index: 500; top: 50%; margin-top: -202px; left: 50%; margin-left:-340px; background: #fff;  border: 1px solid #787878; display: none; }
   .data_tanctop{ height: 46px; border-bottom: 1px solid #787878; background: #3c85db;}
   .data_tanctop h2{ color: #fff; font-size: 18px; line-height: 46px; padding-left: 30px; width: 200px; float: left;}
   .data_cuo{ width: 21px; height: 21px; float: right; background: url(${ctx}/source/images/dailog-close.png); margin-right: 10px; margin-top: 10px; cursor: pointer;}
   .data_tancmain{ padding: 10px 20px; }
   .tancmain_table{  width: 100%;}
   .tancmain_table tr th{ border: 1px solid #787878; height: 28px; text-align: center;} 
   .tancmain_table tr td{ height: 28px; font-size: 14px; line-height: 29px;} 
   .data_btn{ width: 145px; height: 30px; line-height: 30px; font-size: 16px; text-align: center; background: #cdcdcd; border-radius: 10px; margin:auto; color: #323232; cursor: pointer;}
   .nodata{
	    text-align: center;
	    margin: auto;
	    padding-top: 100px;
	}
   

</style>

<body>

	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html">首页</a></span>
						<!-- 商品分类 start -->
						<c:import url="/category.html"></c:import>
						<!-- 商品分类 end -->
						<span class="fl"><a href="${ctx}/knowUs.html">关于我们</a></span>
						<span class="fl shopping" style="margin-right:0">
							<a href="${ctx}/showShopCar.html"><i></i>购物车</a>
						</span>
						
					</div>
					<div class="safer fr" style="margin-left:0px;">
						<!-- 如果已经登录则显示用户名，否则需要登录 -->
				         <c:if test="${sessionScope.globle_user!=null }">
				         <div class="login clearfix">
					        <a href="${ctx}/userCenterUI.html"  class="fl loginname">${sessionScope.globle_user.name }</a>
					        <em class="fl">|</em>
					        <a href="${ctx}/exit.html" class="fl" >退出</a>
					      </div>
				         </c:if>
				         <c:if test="${sessionScope.globle_user==null }">
				            <a href="${ctx}/loginUI.html">登录</a>
							<em>|</em>
							<a href="${ctx}/registUI.html">注册</a>
				         </c:if>
					</div>
				</div>
			</div>
		</div>
		
	<form id="exportWafForm" action="${ctx}/exportWaf.html" method="post">
		<div class="dataCent seetlentBox order" style="width: 1156px;">
		
       <div class="data_box">
        <div class="data_nav">
          <input type="hidden" value="${order.id }" id="orderId" name="orderId"/>
          <input type="hidden" value="1" id="isHis" name="isHis"/>
          <input type="hidden" value="${level }" id="level" />
          <input type="hidden" value="${levelType}" id="levelType" />
          <input type="hidden" value="${websecNum}" id="websecNum" />
          
          <!-- 导出图像 -->
          <input type="hidden" name="imgPieLevel" id="imgPieLevel" />
          <input type="hidden" name="imgPieEvent" id="imgPieEvent" />
		  <input type="hidden" name="imgBar" id="imgBar" />
		  <input type="hidden" name="imgOntimeLine" id="imgOntimeLine"/>
          
          <ul>
            <li style="background-color: #f3f3f3;border-bottom: 1px solid #a6a6a6;"><a href="${ctx}/warningWaf.html?orderId=${order.id }" title="" style="color: #343434;">实时数据</a></li>
            <li><a href="${ctx}/warningWaf.html?orderId=${order.id }&isHis=1&type=month&beginDate=<%=str_date %>" title="" style="color: #2499fb;">历史数据</a></li>
          	<li style="width: 855px;border: 0px;border-bottom: 1px solid #a6a6a6;"></li>
          </ul>
        </div> 
        <!-- 查询条件 -->
        <div>
          <div>周期类型
          	<label>
              <input type="radio" name="radioType"  style="width:20px;" value="month" ${type == "month" ? "checked" : ""}/>月报
              &nbsp;
              <input type="radio" name="radioType" style="width:20px;" value="year" ${type == "year" ? "checked" : ""}/>年报
              &nbsp;
            </label>
          </div>
          <label class="fl">时间</label>
          <div class="fl" style="top:3px;">
          	<input type="text" style="width:256px; display:none; " name="beginDate" id="beginDate_m" readonly="readonly" value="${beginDate }" />
          	<input type="text" style="width:256px; display:none; " name="beginDate" id="beginDate_y" readonly="readonly" value="${beginDate }" />
          	<input type="text" style="width:256px; display:none; " name="beginDate" id="beginDate_w" readonly="readonly" value="${beginDate }" />
          	<a href="javascript:void(0)" onclick="generate()" >生成</a>
            <a href="javascript:void(0)" onclick="exportWaf()" >下载报表</a>
          </div>
        </div>
			
        <div class="data_min">
        	 <div class="data_1" id="levelPie"></div>
             <div class="data_1" id="eventBar"></div>
             <div class="data_1" style="margin-right:0px;" id="eventPie"></div>
        	<!--<c:if test="${level!=0}">
             <div class="data_1" id="levelPie"></div>
            </c:if>
            <c:if test="${level==0}">
             <div class="data_1"><div class="nodata"><img src="${ctx}/source/images/waf_nodata.png"/></div></div>
            </c:if>
            <c:if test="${levelType!=0}">
             <div class="data_1" id="eventBar"></div>
             <div class="data_1" style="margin-right:0px;" id="eventPie"></div>
            </c:if>
            <c:if test="${levelType==0}">
             <div class="data_1"><div class="nodata"><img src="${ctx}/source/images/waf_nodata.png"/></div></div>
             <div class="data_1" style="margin-right:0px;"><div class="nodata"><img src="${ctx}/source/images/waf_nodata.png"/></div></div>
            </c:if>-->
        </div>
        <!-- 按时间段统计 -->
        <div class="data_table">
            
				<div style="width:100%; height: 380px; padding: 20px;" id="ontimeLine"></div>
            
            <!--<c:if test="${websecNum==0}">
	            <div class="nodata"><img src="${ctx}/source/images/waf_nodata.png"/></div>
            </c:if>-->
        </div>
        告警时段统计数据
        <!-- 告警时段统计数据 -->
        <div class="data_table">
     
				<table class="data_table_tab" width="100%">
	              <tbody>
	                 <tr>
	                     <th width="30%">时段</th>
	                     <th width="30%">总计</th>                  
	                 </tr>
	              </tbody>
	             </table>
	             <div style="overflow:auto;height:312px;width:1138px">
	             <table class="data_table_tab" width="100%">
	              <tbody id="timeList">
	                 <!--<c:forEach var="list" items="${resultList}" varStatus="sta">
		                 <tr>
		                     <td width="30%">${list.time }</td>
		                     <td width="30%">${list.count }</td>                     
		                 </tr>
	                 </c:forEach>-->
	              </tbody>
	            </table>
	            </div>
	        
	    </div>
        
        <div class="data_table">
	            <table class="data_table_tab websecTable" width="100%">
	              <tbody>
	                 <tr>
	                     <th width="30%">攻击源IP</th>
	                     <th width="30%">攻击次数</th>                  
	                 </tr>
	              </tbody>
	             </table>
	            <div style="overflow:auto;height:312px;width:1138px" class="websecTable">
	             <table class="data_table_tab websecTable" width="100%" >
	              <tbody id="websecItem">
	                 <!--<c:forEach var="list" items="${websecList}" varStatus="sta">
		                 <tr>
		                     <td width="30%">${list.dstIp }</td>
		                     <td width="30%">${list.count }</td>                     
		                 </tr>
	                 </c:forEach>-->
	              </tbody>
	            </table>
	            </div>
	            <div class="nodata" style="display:none"><img src="${ctx}/source/images/waf_nodata.png"/></div>
        </div>
        
       </div>    
		</div>
		</form>
        
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="${ctx}/index.html">
						<img src="${ctx}/source/images/portal/new-footer-logo.png" alt="">
                   </a>
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd>购物指南</dd>
                            <dd>在线帮助</dd>
                            <dd>常见问题</dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关于安全帮</h2>
                        <dl>
                            <dd><a href="${ctx}/knowUs.html">了解安全帮</a></dd>
                            <dd><a href="${ctx}/joinUs.html">加入安全帮</a></dd>
                            <dd>联系我们</dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd>QQ交流群<br>470899318</dd>
                            <dd class="weixin"><a href="#">官方微信</a></dd>
                       </dl>
                    </li>
                     <li>
                    	<h2>特色服务</h2>
                        <dl>
                        	<dd>优惠劵通道</dd>
                            <dd>专家服务通道</dd>
                       </dl>
                    </li>
					
				</ol>
				
			</div>
		</div>
		<!-- 底部 start -->
		<c:import url="/foot.jsp"></c:import>
		<!-- 底部 end -->
	</div>
<!---执行效果-->
<div class="weixinshow popBoxhide" id="weixin">
	<i class="close chide"></i>
    <div class="Pophead">
    	<h1 class="heaf">安全帮微信二维码</h1>
    </div>
	<div class="popBox">
    	 <p>打开微信，点击右上角的“+”，选择“扫一扫”功能，<br>
对准下方二维码即可。
		</p>
           <div class="weinImg" style="text-align:center;">
           	<img src="${ctx}/source/images/portal/weixin.jpg" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</body>


</html>
