<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>用户中心-基本资料</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/regist.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/userzezao.js"></script>
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
<!-- end -->
<script type="text/javascript" src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/order.js"></script>
<script type="text/javascript">
function selected(){
	var job =$("#hid_job").val();
	var industry =$("#hid_industry").val();
    $("#job option[value='"+job+"']").attr("selected", true);
    $("#industry option[value='"+industry+"']").attr("selected", true);
}
//$(document).ready(function(){
	
//});

</script>
<script type="text/javascript">
var checkName1=0;
var checkMobile1=0;
var checkEmail1=0;
//验证用户名
function checkName(){
	var name = $("#regist_name").val();
	var	pattern	= /^[a-zA-Z0-9]{4,20}$/;
	var originalName = $("#originalName").val();
	if(name!=originalName){
		var flag = pattern.test(name);
		if(name==""||name==null){
			$("#regist_name_msg").html("请输入用户名！");
			checkName1=0;
		}else{
			if(flag){
				$.ajax({
	               type: "POST",
	               url: "regist_checkName.html",
	               data: {"name":name},
	               dataType:"json",
	               success: function(data){
	                   if(data.count>0){
	                   		$("#regist_name_msg").html("用户名已经使用!");
	                   		checkName1=0;
	                   }else{
	                   		$("#regist_name_msg").html("");
	                   		checkName1=1;
	                   }
	               },
	            }); 
			}else{
				$("#regist_name_msg").html("请输入4-20位字符！");
				checkName1=0;
			}
		}
	}else{
		$("#regist_name_msg").html("");
		checkName1=1;
	}
}
//验证邮箱和手机号码
function checkMobileAndEmail(){
	var mobile = $("#regist_phone").val();
	var email = $("#regist_email").val();
	if((mobile!=""&&mobile!=null)||(email!=""&&email!=null)){
		if(mobile!=""&&mobile!=null){
		var originalMobile = $("#originalMobile").val();
		if(mobile!=originalMobile){
				var pattern = /^1[3|5|8|7][0-9]{9}$/;
				var flag = pattern.test(mobile);
				if(flag){
					$.ajax({
			               type: "POST",
			               url: "regist_checkMobile.html",
			               data: {"mobile":mobile},
			               dataType:"json",
			               success: function(data){
			                    if(data.count>0){
			                        $("#regist_mobile_msg").html("您填写的手机号码已使用!");
			                        checkMobile1 = 0;
			                    }else{
			                   		$("#regist_mobile_msg").html("");
			                   		checkMobile1=1;
			                    }
			               }
			            });
				}else{
					$("#regist_mobile_msg").html("手机号码格式不正确！");
					checkMobile1=0;
					}
			}else{
				$("#regist_mobile_msg").html("");
				checkMobile1=1;
			}
		}
		if(email!=""&&email!=null){
				var originalEmail = $("#originalEmail").val();
				if(email!=originalEmail){
					var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
					var flag = pattern.test(email);
					if(flag){
						$.ajax({
				               type: "POST",
				               url: "regist_checkEmail.html",
				               data: {"email":email},
				               dataType:"json",
				               success: function(data){
				                    if(data.count>0){
				                        $("#regist_mobile_email_msg").html("您填写的邮箱已使用!");
				                        checkEmail1 = 0;
				                    }else{
				                   		$("#regist_mobile_email_msg").html("");
				                   		checkEmail1=1;
				                    }
				               }
				            }); 
					}else{
						$("#regist_mobile_email_msg").html("邮箱格式不正确！");
						 checkEmail1=0;
					}
				}else{
					$("#regist_mobile_email_msg").html("");
					checkEmail1=1;
				}
				
			}
	}else{
		$("#regist_mobile_email_msg").html("请输入手机或邮箱号码！");
		checkEmail1=0;
		checkMobile1=0;
		}
}

function checkUserData(){
	var mobile = $("#regist_phone").val();
	var email = $("#regist_email").val();
	var urlAddr = $("#urlAddr").val();
	var industry = $("#industry").val();
	var job = $("#job").val();
	var company = $("#company").val();
	checkMobileAndEmail();
	//if(mobile!=null&&mobile!=""&&email!=null&&email!=""){		
	//	if(checkMobile1==1&&checkEmail1==1){
	//		$("#userdata").submit();
	//		alert("保存成功！");
	//	}
	//}else {
		if(checkMobile1==1&&checkEmail1==1){
			//modify by tangxr 2016-4-10			
			//$("#userdata").submit();
			//alert("保存成功！");
			
			$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "saveUserDataBate.html", 
	    		     data: {"mobile":mobile,
		    			   	"email":email,
		    			   	"urlAddr":urlAddr,
		    			   	"industry":industry,
		    			   	"job":job,
		    			   	"company":company},  
	    		     dataType: "json",
	    		     success: function(data) {
	    		    	 if(data.message == true){
	    		    		 alert("设置成功");  
				    	 }else{
				    		 alert(data.message);
				     		// return;
				    	 }
				    	 window.location.href = "userDataUI.html";
	    		    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "loginUI.html"; } 
	    		    	 else { window.location.href = "loginUI.html"; } } 
	    	});
		}
	//}
}

function checkName(){
	var name = $("#regist_name").val();
	var	pattern	= /^[a-zA-Z0-9_]{4,20}$/;
	var flag = pattern.test(name);
	if(name==""||name==null){
		$("#regist_name_msg").html("用户名不能为空");
		checkName1=0;
	}else{
		if(flag){
			$.ajax({
               type: "POST",
               url: "regist_checkName.html",
               data: {"name":name},
               dataType:"json",
               success: function(data){
                   if(data.count>0){
                   		$("#regist_name_msg").html("用户名已经存在");
                   		checkName1=0;
                   }else{
                   		$("#regist_name_msg").html("");
                   		checkName1=1;
                   }
               },
            }); 
		}else{
			$("#regist_name_msg").html("请输入4-20位字符");
			checkName1=0;
		}
	}
}

function checkPwd(){
	var opassword =$("#opassword").val();
	var name = $("#originalName").val();
	$.ajax({
             type: "POST",
             url: "regist_checkPwd.html",
             data: {"name":name,"opassword":opassword},
             dataType:"json",
             success: function(data){
                 if(data.count==true){
                 		$("#editPassword_msg").html("原密码不正确");
                 }else{
                 		$("#editPassword_msg").html("");
                 }
             },
          }); 
}


function editPassword(){
	checkPwd();
	var opassword =$("#opassword").val();
	var p1 = $("#regist_password").val();
	var p2 = $("#regist_confirm_password").val();
	if(opassword==""||opassword==null||p1==""||p1==null||p2==""||p2==null){
		if(null==opassword||""==opassword){
			$("#editPassword_msg").html("<font color='red'>原密码不能为空</font>");
		}else{
			$("#editPassword_msg").html("");
		}
	    if(null==p1||""==p1){
			$("#regist_password_msg").html("密码不能为空");
		}else{
			$("#regist_password_msg").html("");
		}
		if(null==p2||""==p2){
			$("#regist_confirm_password_msg").html("<font color='red'>确认密码不能为空</font>");
		}else{
			$("#regist_confirm_password_msg").html("");
		}
	}else{
		if(p1.length<6||p1.length>20){
	        $("#regist_password_msg").html("请输入6-20位，支持中英文，数字，字符组合");
	    }else if(p1!=p2) {
	   		$("#regist_confirm_password_msg").html("<font color='red'>两次输入密码不一致，请重新输入</font>");
		}else{
		    $("#regist_confirm_password_msg").html("");
		    $("#editPassword").submit();
		}
		
	}
	
}


</script> 


</head>

<body onload="selected()">
<!--头部-->
<div class="safe01 detalis-head">
	<!--头部-->
	<div class="head" style="width:100%">
		<div class="headBox">
			<div class="safeL fl" style="width:260px; margin-right:13%">
				<a href="${ctx}/index.html"><img src="${ctx}/source/images/portal/newlogo-footer.png" alt="" style="position:relative; top:4px;"/></a>
			</div>
			<div class="safem fl">
				<span class="fl"><a href="${ctx}/index.html">首页</a></span>
				<div class="Divlist listJs fl">
					<a href="#" class="this">我的安全帮<i></i></a>
							<ul class="list listl">
								<li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
								<li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
								<li style="border: none;"><a href="${ctx}/userDataUI.html">个人信息</a></li>
							</ul>
				</div>
				<span class="fl ask">
					<a href="#" class="hbule">手机APP</a>
					<b style="display:none"><img src="${ctx}/source/images/portal/apk.png" alt=""></b>
				</span>
				<span class="fl"><a href="${ctx}/aider.html">帮助</a></span>
				
			</div>
			<div class="safer fr">
				<!-- 如果已经登录则显示用户名，否则需要登录 -->
		         <c:if test="${sessionScope.globle_user!=null }">
			        <a href="${ctx}/userDataUI.html">${sessionScope.globle_user.name }</a>
			        <em>|</em>
			        <a href="${ctx}/exit.html">退出</a>
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
<div class="user_center clear">
  <div class="user_left">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center;"><a  style="color:#4593fd; " href="${ctx}/userCenterUI.html">用户中心</a></li>
      <li class="active"><a href="${ctx}/userDataUI.html">基本资料</a></li>
      <li><a href="${ctx}/userBillUI.html">我的账单</a></li>
      <li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
      <h2>订购中心</h2>
      <!-- <li><a onclick="tasknum_verification()" href="javascript:void(0)">自助下单</a></li> -->
      <li><a href="${ctx}/orderTrackInit.html">订单跟踪</a></li>
    </ul>
  </div>
  
  <!-- <jsp:include page="left.jsp"/>  -->
  
  <!--基本资料-->
  <div class="user_right" style="position:relative;">
    <div class="user_ren"><a href="###"><img src="${ctx}/source/images/admin_rw.png" /></a></div>
    <div class="user_table">
      <form id="userdata" action="${ctx}/saveUserData.html">
        <table>
          <tr class="register_tr">
            <td class="regist_title">用户名</td>
            <td class="regist_input">
             	<input type="hidden" id="originalName" value="${user.name}">
            	<input type="text" name="name" disabled="true" value="${user.name}" id="regist_name" class="regist_txt required"/>
            	<span id="regist_name_msg" style="color:red;float:left"></span>
            </td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">密&nbsp;&nbsp;&nbsp;码</td>
            <td class="regist_input">
                <input type="hidden" id="originalPassword" value="${user.password}"/>
                <input type="password" value="******" disabled="true" id="regist_pwd" class="regist_txt required"/>
                <span id="regist_mobile_password_msg" style="color:red;float:left"></span>
            </td>
            <td class="regist_title"><div class="zc_edit" id="${user.id}" name="${user.name}" pwd="${user.password}" >修改</div></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">邮&nbsp;&nbsp;&nbsp;箱</td>
            <td class="regist_input">
            	<input type="hidden" id="originalEmail" value="${user.email}"/>
            	<input type="text" name="email" value="${user.email}" id="regist_email" class="regist_txt required"/>
            	<span id="regist_mobile_email_msg" style="color:red;float:left"></span>
            </td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">手&nbsp;&nbsp;&nbsp;机</td>
            <td class="regist_input">
                <input type="hidden" id="originalMobile"  value="${user.mobile}"/>
                <input type="text" name="mobile" value="${user.mobile}" id="regist_phone" class="regist_txt required" />
                <span id="regist_mobile_msg" style="color:red;float:left"></span>
            </td>
            <td class="regist_prompt"></td>
          </tr>
          <!-- add by tangxr 2016-3-2 -->
          <tr class="register_tr">
          	<td class="regist_title">所在行业</td>
 			<td class="regist_input">
 				<input type="hidden" id="hid_industry" value="${user.industry}"/>
                <select class="regist_sel" id="industry" name="industry">
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
            </td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
          	<td class="regist_title">职业</td>
 			<td class="regist_input">
 			    <input type="hidden" id="hid_job" value="${user.job}"/>
                <select class="regist_sel" id="job" name="job" >
                	<option selected="selected" value="-1">请选择职业</option>
                	<option value="党的机关、国家机关、群众团体和社会组织、企事业单位负责人" >党的机关、国家机关、群众团体和社会组织、企事业单位负责人</option>
         			<option value="专业技术人员" >专业技术人员</option>
         			<option value="办事人员和有关人员" >办事人员和有关人员</option>
         			<option value="社会生产服务和生活服务人员" >社会生产服务和生活服务人员</option>
         			<option value="农、林、牧、渔业生产及辅助人员" >农、林、牧、渔业生产及辅助人员</option>
         			<option value="生产制造及有关人员" >生产制造及有关人员</option>
         			<option value="军人" >军人</option>
         			<option value="不便分类的其他从业人员" >不便分类的其他从业人员</option>
                </select>
            </td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
          	<td class="regist_title">公司名称</td>
          	<td class="regist_input">
          		<input type="text" name="company" value="${user.company}" id="company" class="regist_txt"/>
          	</td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
          	<td class="regist_title">推送URL</td>
          	<td class="regist_input">
          		<input type="text" name="urlAddr" value="${user.urlAddr}" id="urlAddr" class="regist_txt"/>
          	</td>
            <td class="regist_prompt"></td>
          </tr>
        </table>
      <div class="user_sub"><a href="javascript:void(0)" onclick="checkUserData()"><img src="${ctx}/source/images/user_sub.png" /></a></div>
      </form>
    </div>
  </div>
</div>
<!--基本资料--> 
<!-- 尾部代码开始-->

<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
				<!--修改-->
				   <a href="${ctx}/index.html">
		               <img src="${ctx}/source/images/portal/logo footer.png" alt="">
	                   <i class="" style="height:35px; color:#b3b4b5; width:1px; display:inline-block;">|</i>
		               <img src="${ctx}/source/images/portal/newlogo-footer.png" alt="">
                   </a>
                <!--修改--> 
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd><a href="#">购物指南</a></dd>
                            <dd><a href="#">在线帮助</a></dd>
                            <dd><a href="#">常见问题</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关于安全帮</h2>
                        <dl>
                        	<dd><a href="${ctx}/knowUs.html">了解安全帮</a></dd>
                            <dd><a href="${ctx}/joinUs.html">加入安全帮</a></dd>
                            <dd><a href="#">联系我们</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd><a href="#">QQ交流群<br>470899318</a></dd>
                            <dd class="weixin"><a href="#">官方微信</a></dd>
                       </dl>
                    </li>
                     <li>
                    	<h2>特色服务</h2>
                        <dl>
                        	<dd><a href="#">优惠劵通道</a></dd>
                            <dd><a href="#">专家服务通道</a></dd>
                       </dl>
                    </li>
					
				</ol>
				
			</div>
		</div>
		<div class="foot">
			<p>版权所有Copyright © 2015 中国电信股份有限公司北京研究院京ICP备12019458号-10</p>
		</div>
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
           	<img src="${ctx}/source/images/portal/weixin.png" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</div>

<div id="box_mark"></div>
<div id="box_logoIn_edit">
  <div id="close_edit"></div>  <div class="text_1">
    <form id="editPassword" action="${ctx}/saveUserPassword.html" method="post">
    <div class="text_top">修改密码</div>
    <div class="text_bottm">
    <input type="hidden" name="id" id="hiddenEditUserid"/>
    <input type="hidden" name="hiddenEditUserName" id="hiddenEditUserName"/>
    <input type="hidden" name="hiddenEditUserPwd" id="hiddenEditUserPwd"/>
      <table style="margin-top:56px;width:630px">
        <tr>
          <td style="width:25%;">当前密码</td>
          <td style="width:37%;"><input class="boz_inout_1" style="height:30px;line-height:30px" type="password" name="opassword" id="opassword" onblur="checkPwd()"/></td>
          <td style="width:30%; text-align:left; color:#e32929;font-size:12px"><div id="editPassword_msg"></div></td>
        </tr>
        <tr>
          <td>新密码</td>
          <td style="width:37%;"><input class="boz_inout_1" style="height:30px;line-height:30px" type="password" name="password" id="regist_password" onblur="checkPassword()"/></td>
          <td style="width:30%; text-align:left; color:#e32929;font-size:12px"><div id="regist_password_msg"></div></td>
        </tr>
        <tr>
          <td>确认密码</td>
          <td><input class="boz_inout_1" style="height:30px;line-height:30px" type="password" name="confirm_password" id="regist_confirm_password" onblur="checkConfirmPassword()"/></td>
          <td style="color:#e32929;text-align:left"><div id="regist_confirm_password_msg"></div></td>
        </tr>
      </table>
    </div>
    <div style="margin-top:35px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/user_submit_3.jpg" onclick="editPassword()"/></a></div>
  </div>
  </form>
</div>

</body>
</html>
