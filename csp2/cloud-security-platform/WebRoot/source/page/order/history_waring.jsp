<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*,java.io.*,java.util.*,java.text.*"  %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>订单跟踪-告警详情-历史详情</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/prompt.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/warning.js"></script>
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<!-- add by 2016-02 -->
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript">
$(function () {
	getData();
	window.setInterval(getData,30000);
});
function getData(){
	var orderId = $("#orderId").val();
 		$.ajax({
           type: "POST",
           url: "scaning.html",
           data: {"orderId":orderId,"status":${status}},
           dataType:"json",
           success: function(data){
          		var progress = data.progress;
           		$("#bar1").html(progress+"%");
           		$("#bar2").css("width", progress+"%");
           		$("#bar2").html(progress+"%");
           		/*if(${status}==2){
           			$('.scan').removeClass('scancur');
           			$('.scan').eq(1).addClass('scancur');
           		}
           		if(${status}==3){
           			$('.scan').removeClass('scancur');
           			$('.scan').eq(2).addClass('scancur');
           		}*/
           }
        });
}
function seedetail1(e) {
    var uservalue=$(e).attr('value');
    if(uservalue==0)
    {
      $(e).parents().next('.detailbox').show();
      $(e).attr('value',1);
     }
      else if(uservalue==1)
      {
        $(e).parents().next('.detailbox').hide();
        $(e).attr('value','0');
      }

};
//加载模板下拉框选项 
</script>
</head>

<body> 
<!--头部-->
<div class="safe01 detalis-head">
	<!--头部-->
	<div class="head" style="width:100%">
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
<!-- 头部代码结束-->
<!-- 头部代码结束-->
<div class="user_center clear">
  
    <!-- 告警详情-->
     <div class="user_right" style="margin:auto;float:none;">
        <div class="gj_top">
            <a href="#" class="aelse">订单跟踪</a>　>　<a href="#" class="acur">告警详情</a>>　历史详情
        </div>
       <c:forEach var="order" items="${orderList}" varStatus="status">
        <div class="gj_title webgj_title">
        <div class="gj_fl">
        	<c:if test="${alist==0}">
        	   <img src="${ctx}/source/images/icon_cg-green.jpg" width="85" height="85" />
        	   <c:if test="${order.serviceId==1 }">
                <p>漏洞告警正常</p>
               </c:if>
               <c:if test="${order.serviceId==2 }">
                <p>木马检测正常</p>
               </c:if>
        	</c:if>
        	<c:if test="${alist!=0}">
                <img src="${ctx}/source/images/icon_cg.jpg" width="85" height="85" />
                <c:if test="${order.serviceId==1 }">
                <p>发现漏洞个数</p>
               </c:if>
               <c:if test="${order.serviceId==2 }">
                <p>木马检测个数</p>
               </c:if>
                <p class="web_num">${alist}个</p>
            </c:if>
          </div>
        <div class="gj_fr">
            <input type="hidden" value="${order.id }" id="orderId"/>
            <input type="hidden" value="${order.type }" id="type"/>
            <input type="hidden" value="${group_flag }" id="group_flag"/>
            <p><span class="bigfont">${order.name }</span>
            <span>(  订单编号：${order.id }  )</span>
          <!--  <c:if test="${order.type==1 }">
            	<p><span class="bigfont historyde">历史详情</span>
            		<select class="historyse" id=execute_Time name="execute_Time" onchange="historicalDetails()">
            			<option>请选择</option>
            		</select>
            	</p>
                <a href="${ctx}/historyInit.html?orderId=${order.id }" target="_blank"><span style="float:right; margin-right:30px; dispiay:inline-block;color:#999; ">历史记录</span></a>
             	
            </c:if>-->  
            </p>            
            <p><p>
            <div style="overflow:hidden;"><div style="float:left">资产：</div>
            <div style="float:left">
            <c:forEach var="asset" items="${assetList}" varStatus="status">
            <span class="assets" style="display:block">${asset.name }&nbsp;&nbsp;(${asset.addr })</span>
            </c:forEach>
            </div></div></p></p>
        </div>
        </div>
        <div class="process">
       	  <p style="padding-bottom:30px;"><span class="scantitle">扫描状态</span>
       	  	<span class="scan">未开始</span><span class="scan">扫描中</span><span class="scan scancur">完成</span>
       	  </p>
            <p><span class="scantitle">扫描进度</span><span class="propercent" id=bar1>${progress }%</span>
            <span class="processingbox">
            	<span class="progress">
                    <span class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width:${progress }%" id="bar2">${progress }%</span>
				</span>
            <span class="prourl">当前URL${currentUrl }</span>
            </span></p>
        </div>
        <div class="gj_box">
            <div class="gj_left fl safebox">
                <div class="detail_title">安全风险</div>
                <div class="aqfx" id="aqfx">
                    <!-- <img src="${ctx}/source/images/aufx.jpg" width="271" height="263" /> -->
                </div>
            </div>
            <div class="fl numbox gj_left">
                <div class="detail_title">漏洞个数</div>
                <div class="ldgs" id="ldgs">
                    <!-- <img src="${ctx}/source/images/ldgs.jpg" width="368" height="269" />  -->
                </div>
            </div>
            <div class="fl">
            	<div class="detail_title">基本信息</div>
                <P class="formalinfo"><span class="infotitle">开始时间</span><span>${task.executeTime}</span></P>
                <P class="formalinfo"><span class="infotitle">结束时间</span><span>${task.endTime}</span></P>
                <P class="formalinfo"><span class="infotitle">扫描时长</span><span>${scanTime}</span></P>
                <P class="formalinfo"><span class="infotitle2">已经发现弱点数</span><span>${task.issueCount}个</span></P>
                <P class="formalinfo"><span class="infotitle2">请求次数</span><span>${task.requestCount}次</span></P>
                <P class="formalinfo"><span class="infotitle2">URL个数</span><span>${task.urlCount}个</span></P>
                <P class="formalinfo"><span class="infotitle2">平均响应时间</span><span>${task.averResponse}毫秒</span></P>
                <P class="formalinfo"><span class="infotitle2">每秒访问个数</span><span>${task.averSendCount}个</span></P>
                <P class="formalinfo"><span class="infotitle2">发送字节</span><span>${send}</span></P>
                <P class="formalinfo"><span class="infotitle2">接收字节</span><span>${receive}</span></P>                  
            </div>
        </div>
        <c:if test="${order.type==1}"><!-- 单次订单不显示趋势图 -->
        <div class="gj_boxs">
            <div class="detail_title">安全评分</div>
            <div class="aqpf" id="aqpf">
                <!-- <img src="${ctx}/source/images/aqpf.jpg" width="870" height="235" /> -->
            </div>
        </div>
        </c:if>
       </c:forEach>
    <div class="zhangd_table">
        <div class="detail_title">漏洞说明</div>
      <table class="ld_table" style="margin-bottom:0;width: 938px;margin-left: 0px;">
        <tbody>                                                                                   
          <tr style="background:#e0e0e0; height:30px; line-height:30px;">
            <td style="width:8%;">编号</td>
            <td  style="width:22%;">漏洞名称</td>
            <td  style="width:10%;">漏洞级别</td>
            <td  style="width:35%;">漏洞详情描述</td>
            <td  style="width:25%;">修复建议&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          </tr>
        </tbody>
      </table>
      <div style="overflow:auto;height:400px;width:938px">
      <table class="ld_table" style="width:921px;margin-left:0;">
        <tbody>           
          <c:forEach var="alarm" items="${alarmList}" varStatus="status">
              <tr>                                            
                <td style="width:8%;">${status.index+1 }</td>
                <td  style="width:22%;">${alarm.name }</td>
                <td  style="width:10%;">
                   <c:if test="${alarm.level==0}">低</c:if>
                   <c:if test="${alarm.level==1}">中</c:if>
                   <c:if test="${alarm.level==2}">高</c:if> 
                </td>
                <td  style="width:35%;">${alarm.alarm_content }</td>
               <!--  <td>${alarm.advice }</td> -->
                <td  style="width:25%;" class="seedetail" value="0" name="${order.id}" onclick="seedetail1(this)"><span>查看建议</span></td>
                </tr>
                
              <tr  class="detailbox">
                <td colspan="6"><div  class="zhangd_div2">${alarm.advice } </div>
                 </td>
              </tr>
          </c:forEach>
          
          
        </tbody>
      </table>
    </div>
  </div>
  </div>
</div>
<!-- 尾部代码开始-->
<div class="safeBox">
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="${ctx}/index.html">
		               <img src="${ctx}/source/images/portal/new-footer-logo.png" alt="" />
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
</div>
<!-- 尾部代码结束 -->

</body>
</html>
