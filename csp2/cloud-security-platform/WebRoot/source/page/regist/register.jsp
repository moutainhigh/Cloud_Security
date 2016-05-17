<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>注册</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/login.css">
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/source/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/common/index.js"></script>
<script src="${ctx}/source/common/popBox.js"></script>
<script src="${ctx}/source/common/slidelf.js"></script>
<script src="${ctx}/source/common/main.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/regist.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/registzezao.js"></script>
<script>
	window.onload=function(){
		//页面加载时清空所有的val值，如需要请取消注释；
		$('.prompttext').val('');
}

</script>

</head>

<body>
	<div class="safeBox login register">
	<div class="loginBox" style="margin-bottom:370px;">
            <a href="#" class="logo">
                <img src="${ctx}/source/images/portal/logo.png" alt="">
            </a>
	
		 <div class="cent">

            	<form  class="form" autocomplete="off" id="form_regist" action="${ctx}/registToLogin.html" name="form_regist" method="post">
                	<h2>注册安全帮账号</h2>
                	<ul>
                    	<li class="clearfix">
                        	<div class="list fl">
                            	<input type="text" placeholder="" class="text prompttext" name="name" id="regist_name" onblur="checkName()" />
                                <p class="promp">用户名/手机号</p>
                                <i class="error" style="display:none" id="regist_name_flag"></i>
                            </div>
                            <!--此为提示 默认隐藏-->
                            <div class="prompt fl" id="regist_name_prompt"><b></b>支持4-20位字母/数字/下划线及其组合</div>
                        </li>
                        <li class="clearfix">
                        	<div class="list fl">
                            	<input type="password" placeholder="" class="text prompttext" name="password" id="regist_password" onblur="checkPassword()"/>
                                <p class="promp">设置密码</p>
                                <i class="right" style="display:none" id="regist_password_flag"></i>
                            </div>
                            <div class="prompt fl" id="regist_password_prompt"><b></b>支持6-20位,且至少两种字符组合(大小写字母/数字/字符)</div>
                        </li>
                        <li class="clearfix">
                        	<div class="list fl">
								<input type="password" placeholder="" class="text prompttext" name="confirm_password" id="regist_confirm_password" onblur="checkConfirmPassword()" autocomplete="false"/>
                                <p class="promp">确认密码</p>
                                <i class="right" style="display:none" id="regist_confirm_password_flag"></i>
                            </div>
                           <div class="prompt fl" id="regist_confirm_password_prompt"><b></b>支持6-20位,且至少两种字符组合(大小写字母/数字/字符)</div>
                        </li>
                         <li class="clearfix">                       	
                            	 <select class="scelt" id="industry" name="industry">
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
                            <div class="prompt fl"><b></b>支持6-20位，支持中英文/数字/字符组合</div>
                        </li>
                         <li class="clearfix">
                        	
                            	 <select class="scelt" id="job" name="job">
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
                            <div class="prompt fl"><b></b>支持6-20位，支持中英文/数字/字符组合</div>
                        </li>
                        <li class="clearfix">
                        	<div class="list fl">
                            	<input type="text" placeholder="" class="text prompttext" name="company" id="company" onblur="checkCompanyFun()">
                                <p class="promp">公司名称</p>
                                <i class="right" style="display:none" id="company_flag"></i>
                            </div>
                            <div class="prompt fl" id="company_prompt"><b></b></div>
                        </li>
                        
                        <li class="clearfix">
                        	<div class="list fl">
                            	<input type="text" placeholder="" class="text prompttext" name="mobile" id="regist_phone" onblur="checkMobile()"/>
                                <p class="promp">手机</p>
                                <i class="right" style="display:none" id="regist_phone_flag"></i>
                            </div>
                            <div class="prompt fl" id="regist_phone_prompt"><b></b></div>
                        </li>
                         <li class="clearfix">
                        	<div class="list fl" style="width:218px;">
                            	<input type="text" style="width:186px;" placeholder="" class="text prompttext" name="verification_code" id="verification_code" onblur="checkActivationCode()"/>
                                <p class="promp">手机验证码</p>
                                <i class="right" style="display:none" id="verification_code_flag"></i>
                                <a href="#" class="test verification" onclick="checkSendMobile()" id="phone_yzm">获取验证码</a>
                            </div>
                            <div class="prompt fl" id="verification_code_prompt"><b></b></div>
                        </li>
                        <li class="clearfix">
                        	<div class="list fl" style="width:218px;">
                            	<input type="text" style="width:186px;" placeholder="" class="text prompttext" name="checkNumber" id="checkNumber1" onblur="checkActivationCode()">
                                <p class="promp">验证码</p>
                                <i class="right" style="display:none" id="checkNumber1_flag"></i>
                                <span class="test"><img src="${ctx}/image.jsp" alt="" width="108" height="42" id="imageNumber" title="点击换一张" onclick="checkNumberImage()"></img></span>
                            </div>
                            <div class="prompt fl" id="checkNumber1_prompt"><b></b></div>
                        </li>
                        <li class="clearfix" style=" position:relative; top:-5px;">
                        	<div class="list password fl" style="width:100%;">
                            	<label class="fl"><input type="checkbox"  class="login_checkbox" id="ck"/>我已阅读并同意
                            	<a href="javascript:void(0)" class="forget_pass" id="zc_regist">《云平台用户注册协议》</a></label>                              
                            </div>
                            <div class="prompt fl" id="ck_prompt"><b></b></div>
                        </li>
                        
                    </ul>
                    <div class="subBox" style=" position:relative; top:-18px;">
                    	<input type="button" class="submit" onclick="submitForm()" value="立即注册">
                    </div>
                    <div class="subBox" style=" position:relative; top:5px;">
                    	<span>已有有安全帮账号？<a href="${ctx }/loginUI.html">直接登录</a><i></i></span>
                    </div>
                    
                </form>
            </div>
        </div>
		
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="#">
                    	<img src="${ctx}/source/images/portal/new-footer-logo.png" alt="" />
                    </a>
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
                        	<dd><a href="#">了解安全帮</a></dd>
                            <dd><a href="#">加入安全帮</a></dd>
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
           		<img src="${ctx}/source/images/weixin.png" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
<div id="box_mark"></div>
<div id="box_logoIn_regist" style="display:none">
  <div id="close_edit"></div>  <div class="text_1">
    <form id="" action="${ctx}/saveUserPassword.html" method="post">
    <div class="text_top" style="text-align:center;">云安全服务平台用户协议</div>
      <div id="f1" class="dd" style="overflow:auto;height:430px;width:870px;margin-top:15px;">
        <p class="help_detail">中国电信股份有限公司北京研究院（以下简称"电信北研院"）同意按照本服务条款的规定基于云安全服务平台（以下简称“平台”）提供“WEB网站云安全服务”。为获得“WEB网站云安全服务”，服务使用人（以下称"用户"）应当同意本协议，才能完成申请程序并通过平台的审核。用户在向平台提交审核申请时，首先签订了该用户协议后才可提交申请。</p>
        <h3 class="helph">1. 服务内容</h3>
        <p class="help_detail">1.1 云安全服务平台是电信北研院为网站提供主动保护的服务平台，所提供的服务包括但不限于：漏洞扫描、恶意代码监测、网页篡改监测、关键字监测、可用性监测。</p>
        <p class="help_detail">1.2 云安全服务平台所提供的服务不得对用户的网站造成任何破坏。</p>
        <h3 class="helph">2. 服务规则</h4>
        <p class="help_detail">2.1 网站管理员权限验证</p>
        <p class="help_detail">2.1.1 用户在申请“WEB网站云安全服务”时，应提供网站管理员真实、完整和准确的信息及证明材料。如因故意或过失未向电信北研院提供真实、完整和准确的信息，导致电信北研院验证错误，造成相关各方损失的，由用户承担一切责任。</p>
        <p class="help_detail">2.1.2 用户通过了管理员权限验证，意味着默认确认了此条款。</p>
        <p class="help_detail">2.2 WEB网站云安全服务</p>
        <p class="help_detail">2.2.1 WEB网站云安全服务标准以不损害用户网站正常使用为准则，由电信北研院拟定</p>
        <p class="help_detail">2.2.2用户在使用" WEB网站云安全服务"过程中，用户会收到云安全服务平台的检测结果邮件或短信通知。</p>
        <p class="help_detail">2.2.3 由于涉及到对网站的安全情况的监控，因此电信北研院有权在用户提交验证申请后，自动将用户申请的网站进行挂马，篡改和漏洞扫描的监控。用户通过了管理员权限验证，就表明确认了此条款。</p>
        <h3 class="helph">3. 用户信息保护</h4>
        <p class="help_detail">3.1平台不得泄露用户的保密信息（不包括查询页面需要公布的信息），更不得以任何缘由对用户的隐私信息进行公布。</p>
        <p class="help_detail">3.2 电信北研院制定了以下四项隐私权保护原则：</p>
        <p class="help_detail">3.2.1 利用我们收集的信息为用户提供有价值的产品和服务。</p>
        <p class="help_detail">3.2.2 开发符合隐私权标准和隐私权惯例的产品。</p>
        <p class="help_detail">3.2.3 将个人信息的收集透明化，并由权威第三方监督。</p>
        <p class="help_detail">3.2.4 尽最大的努力保护我们掌握的信息。</p>
        <h3 class="helph">4. 版权声明</h4>
        <p class="help_detail">4.1 电信北研院对其所提供的验证服务内容，包括但不限于文字、软件、数据库、声音、图片、录像、图表等，拥有所有权和使用权。所有这些内容受版权、商标、专利和其它财产所有权法律的保护。</p>
        <p class="help_detail">4.2 任何人不得擅自以非法的方式传播、修改和使用本网站所提供的内容。</p>
        <h3 class="helph">5. 免责声明</h4>
        <p class="help_detail">5.1 用户知悉并理解，“WEB网站云安全服务”经过详细的测试，但不能保证与所有的软硬件系统完全兼容，不能保证本服务完全没有错误，如果出现不兼容及服务错误的情况，用户可寻求技术支持以解决问题。电信北研院就此情形不承担任何责任。</p>
        <p class="help_detail">5.2 用户明确同意其免费使用"“WEB网站云安全服务"所存在的风险将完全由其自己承担；在适用法律允许的最大范围内，对因使用或不能使用"“WEB网站云安全服务"所产生的损害及风险，包括但不限于直接或间接的个人损害、商业赢利的丧失、贸易中断、商业信息的丢失或任何其它经济损失，电信北研院不承担任何责任。</p>
        <p class="help_detail">5.3电信北研院不担保提供的功能及服务一定能满足用户的要求，也不担保服务不会中断，对服务的及时性、安全性、真实性、准确性都不作担保。</p>
        <p class="help_detail">5.4对于因运营商系统或互联网网络故障、计算机故障或病毒、信息损坏或丢失、计算机系统问题或其它任何不可抗力原因而产生损失，电信北研院不承担任何责任。</p>
        <p class="help_detail">5.5用户违反本协议规定，对电信北研院造成损害的，电信北研院有权采取包括但不限于中断使用许可、停止提供服务、限制使用、法律追究等措施。</p>
        <h3 class="helph">6. 条款修改</h4>
        <p class="help_detail">6.1 电信北研院将有权随时修改本条款的有关内容，一旦本条款的内容发生变动，平台将通过适当方式作解释说明。</p>
        <p class="help_detail">6.2 如果用户不同意电信北研院对本条款相关内容所做的修改，有权停止使用"“WEB网站云安全服务"。如果用户继续使用"“WEB网站云安全服务"，则视为接受电信北研院对本协议相关内容所做的修改。</p>
        <p class="help_detail" style="text-align:center;">本条款的更新修改及最终解释权归中国电信股份有限公司北京研究院所有。</p>
      </div>
      <input type="hidden" name="agreeId" id="agreeId"/>
    <div style="margin-top:35px;text-align:center;"><a href="javascript:void(0)"><img src="${ctx}/source/images/agree.png" id="agree"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="${ctx}/source/images/notagree.png" onclick="notagree()"/></a></div>
  </div>
  </form>
</div>
</body>

<script>
$(function(){

	/*提示文字效果*/
		$('.promp').click(function(){
			$(this).hide();
			$(this).siblings('.prompttext').focus();
		})
		
		$('.prompttext').focus(function(){
			$(this).siblings('p').hide();	
			//var tempId = $(this).parents('li').children('.prompt').attr("id");
			//if(tempId=='regist_name_prompt' || tempId=='regist_password_prompt'){
				$(this).parents('li').children('.prompt').fadeIn();	
			//}

		})
		$('.prompttext').blur(function(){
			//var tempId = $(this).parents('li').children('.prompt').attr("id");
			//if(tempId=='regist_name_prompt' || tempId=='regist_password_prompt'){
				$(this).parents('li').children('.prompt').fadeOut();	
				var _this=$(this).val();
				if(_this=='')
				{
					$(this).siblings('p').show();		
				}else
				{
					$(this).siblings('p').hide();	
				}
			//}
			
			
		})

		
})

</script>
</html>
