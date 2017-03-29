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
<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/index.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>

<script type="text/javascript" src="${ctx}/source/scripts/regist/regist.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/registzezao.js"></script>
<style type="text/css">
@media screen and (min-width: 1201px) {
.login {padding-top: 172px}
}
/* css 注释说明：设置了浏览器宽度不小于1201px时*/

@media screen and (max-width: 1200px) {
.login {padding-top: 150px}
}
/* 设置了浏览器宽度不大于1200px时 */

@media screen and (max-width: 900px) {
.login {padding-top: 100px}
}
/* 设置了浏览器宽度不大于900px时*/

@media screen and (max-width: 500px) {
.login {padding-top: 60px}
}
/* 设置了浏览器宽度不大于500px时 */ 
</style>
<script>
	window.onload=function(){
		//页面加载时清空所有的val值，如需要请取消注释；
		$('.prompttext').val('');
		//注册按钮样式初始化
		$('#register_btn').removeAttr('disabled');
		$('#register_btn').css('background-color','#2499fb');
		$("#register_btn").css("opacity","");
}

</script>

</head>

<body>
	<div class="safeBox login register">
	<div class="loginBox" style="margin-bottom:370px;">
            <a href="${ctx}/index.html" class="logo">
                <img src="${ctx}/source/images/portal/logo.png" alt="">
            </a>
	
		 <div class="cent">

            	<form  class="form" autocomplete="off" id="form_regist" action="${ctx}/registToLogin.html" name="form_regist" method="post">
                	<h2>注册安全帮账号</h2>
                	<ul>
                    	<li class="clearfix">
                        	<div class="list fl">
                            	<input type="text" placeholder="" class="text prompttext" name="name" id="regist_name" onblur="checkName()" />
                                <p class="promp">用户名</p>
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
                        	<div class="list fl">
								<input type="text" placeholder="" class="text prompttext" name="confirm_email" id="regist_confirm_email" onblur="checkConfirmEmail()" autocomplete="false"/>
                                <p class="promp">邮箱地址</p>
                                <i class="right" style="display:none" id="regist_confirm_Email_flag"></i>
                            </div>
                         <div class="prompt fl" id="regist_confirm_email_prompt"><b></b>输入邮箱地址</div>
                        </li>
                         <li class="clearfix">
                        	<div class="list fl">
                            	<input type="text" placeholder="" class="text prompttext" name="company" id="company" onblur="checkCompanyFun()">
                                <p class="promp">公司名称</p>
                                <i class="right" style="display:none" id="company_flag"></i>
                            </div>
                            <div class="prompt fl" id="company_prompt"><b></b>输入公司名称 ，支持长度1-20</div>
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
                   		<!-- 新增合作方 add by tangxr 2016-11-30 -->     
                        <li class="clearfix">
                         	 <select class="scelt" id="partner" name="partner">
                                <option selected="selected" value="-1">请选择合作方</option>
				            	<c:forEach items="${partnerList}" var="partner"  varStatus="status">
					            	<option value="${partner.partnerName}">${partner.partnerName}</option>
				            	</c:forEach>
                             </select>
                        </li>
                        <li class="clearfix">
                        	<div class="list fl">
                            	<input type="text" placeholder="" class="text prompttext" name="mobile" id="regist_phone" onblur="checkMobile()"/>
                                <p class="promp">手机</p>
                                <i class="right" style="display:none" id="regist_phone_flag"></i>
                            </div>
                            <div class="prompt fl" id="regist_phone_prompt"><b></b>请输入手机号码</div>
                        </li>                       
                         <li class="clearfix">
                        	<div class="list fl" style="width:218px;">
                            	<input type="text" style="width:186px;" placeholder="" class="text prompttext" name="checkNumber" id="checkNumber1" onblur="checkActivationCode()">
                                <p class="promp">验证码</p>
                                <i class="right" style="display:none" id="checkNumber1_flag"></i>
                                <span class="test"><img src="${ctx}/image.jsp" alt="" width="108" height="42" id="imageNumber" title="点击换一张" onclick="checkNumberImage()"></img></span>
                            </div>
                            <div class="prompt fl" id="checkNumber1_prompt"><b></b>输入图片验证码</div>
                        </li>

                         <li class="clearfix">
                        	<div class="list fl" style="width:218px;">
                            	<input type="text" style="width:186px;" placeholder="" class="text prompttext" name="verification_code" id="verification_code" />
                                <p class="promp">手机验证码</p>
                                <i class="right" style="display:none" id="verification_code_flag"></i>
                                <a href="javascript:;" class="test verification" onclick="checkSendMobile()" id="phone_yzm">获取验证码</a>
                               </div>
                            <div class="prompt fl" id="verification_code_prompt"><b></b>输入短信验证码</div>
                        </li>
                       
                        <li class="clearfix" style=" position:relative; top:-5px;">
                        	<div class="list password fl" style="width:100%;">
                            	<label class="fl"><input type="checkbox"  class="login_checkbox" id="ck"/>我已阅读并同意
                            	<a href="javascript:void(0)" class="forget_pass" id="zc_regist">《安全帮用户协议》</a></label>                              
                            </div>
                            <div class="prompt fl" id="ck_prompt"><b></b></div>
                        </li>
                        
                    </ul>
                    <div class="subBox" style=" position:relative; top:-18px;">
                    	<input type="button" style="background-color: rgb(36, 153, 251);" class="submit" onclick="submitForm()" id="register_btn" value="立即注册">
                    </div>
                    <div class="subBox" style=" position:relative; top:5px;">
                    	<span>已有安全帮账号？<a href="${ctx }/loginUI.html">直接登录</a><i></i></span>
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
<div id="box_mark"></div>
<div id="box_logoIn_regist" style="display:none">
  <div id="close_edit"></div>  <div class="text_1">
    <form id="" action="" method="post">
    <div class="text_top" style="text-align:center;">安全帮用户协议</div>
      <div id="f1" class="dd" style="overflow:auto;height:430px;width:870px;margin-top:15px;">
        <p class="help_detail">中国电信股份有限公司北京研究院（以下简称"电信北研院"）同意按照本服务条款的规定基于安全帮（以下简称“平台”）提供云安全服务。为获得云安全服务，服务使用人（以下称"用户"）应当同意本协议，才能完成申请程序并通过平台的审核。用户在向平台提交审核申请时，首先签订了该用户协议后才可提交申请。</p>
        <h3 class="helph">1. 服务内容</h3>
        <p class="help_detail">1.1 安全帮是电信北研院为用户提供云安全服务的平台，所提供的服务包括但不限于：网站安全服务、系统安全服务、数据库安全服务、安全态势分析。</p>
        <h3 class="helph">2. 服务规则</h4>
        <p class="help_detail">2.1 用户在使用安全帮时，必须遵守中华人民共和国相关法律法规的规定，用户应同意将不会利用本平台进行任何违法或不正当的活动，包括但不限于下列行为:</p>
        <p class="help_detail">（1）上载、展示、张贴、传播或以其它方式传送含有下列内容之一的信息：</p>
        <p class="help_detail"> &nbsp; 1） 反对宪法所确定的基本原则的；</p>
        <p class="help_detail"> &nbsp; 2） 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；</p>
        <p class="help_detail"> &nbsp; 3） 损害国家荣誉和利益的；</p>
        <p class="help_detail"> &nbsp; 4） 煽动民族仇恨、民族歧视、破坏民族团结的；</p>
        <p class="help_detail">&nbsp;  5） 破坏国家宗教政策，宣扬邪教和封建迷信的；</p>
         <p class="help_detail">&nbsp; 6）散布谣言，扰乱社会秩序，破坏社会稳定的；</p>
        <p class="help_detail"> &nbsp; 7） 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；</p>
        <p class="help_detail"> &nbsp; 8） 侮辱或者诽谤他人，侵害他人合法权利的；</p>
        <p class="help_detail"> &nbsp; 9） 含有虚假、有害、胁迫、侵害他人隐私、骚扰、侵害、中伤、粗俗、猥亵、或其它道德上令人反感的内容；</p>
        <p class="help_detail"> &nbsp; 10） 含有中国法律、法规、规章、条例以及任何具有法律效力之规范所限制或禁止的其它内容的；</p>
         <p class="help_detail">（2）不得为任何非法目的而使用安全帮；</p> 
         <p class="help_detail">（3）不利用安全帮从事以下活动：</p> 
          <p class="help_detail"> &nbsp;  1） 未经允许，进入计算机信息网络或者使用计算机信息网络资源的；</p> 
           <p class="help_detail"> &nbsp;  2） 未经允许，对计算机信息网络功能进行删除、修改或者增加的；</p> 
            <p class="help_detail"> &nbsp; 3） 未经允许，对进入计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加的；</p> 
             <p class="help_detail"> &nbsp; 4） 故意制作、传播计算机病毒等破坏性程序的；</p> 
             <p class="help_detail"> &nbsp; 5） 其他危害计算机信息网络安全的行为。</p> 
              <p class="help_detail">2.2 用户违反本协议或相关的服务条款的规定，导致或产生的任何第三方主张的任何索赔、要求或损失，包括合理的律师费，您同意赔偿电信北研院与合作公司、关联公司，并使之免受损害。对此，电信北研院有权视用户的行为性质，采取包括但不限于删除用户发布信息内容、暂停使用许可、终止服务、限制使用、回收帐号、追究法律责任等措施。对恶意注册安全帮帐号或利用安全帮帐号进行违法活动、捣乱、骚扰、欺骗、其他用户以及其他违反本协议的行为，安全帮有权回收其帐号。同时，电信北研院会视司法部门的要求，协助调查。</p>
        <p class="help_detail">2.3 用户不得对安全帮服务任何部分或全部服务之使用或获得，进行出售、转售或用于任何其它商业目的。</p>
         <p class="help_detail">2.4 用户须对自己在使用安全帮过程中的行为承担法律责任。用户承担法律责任的形式包括但不限于：对受到侵害者进行赔偿，以及在电信北研院首先承担了因用户行为导致的行政处罚或侵权损害赔偿责任后，用户应给予电信北研院等额的赔偿。</p>
        <h3 class="helph">3. 用户信息保护</h4>
        <p class="help_detail">3.1 平台不得泄露用户的保密信息（不包括查询页面需要公布的信息），更不得以任何缘由对用户的隐私信息进行公布。</p>
        <p class="help_detail">3.2 电信北研院制定了以下四项隐私权保护原则：</p>
        <p class="help_detail">3.2.1 利用我们收集的信息为用户提供有价值的产品和服务。</p>
        <p class="help_detail">3.2.2 开发符合隐私权标准和隐私权惯例的产品。</p>
        <p class="help_detail">3.2.3 将个人信息的收集透明化，并由权威第三方监督。</p>
        <p class="help_detail">3.2.4 尽最大的努力保护我们掌握的信息。</p>
        <h3 class="helph">4. 版权声明</h4>
        <p class="help_detail">4.1 电信北研院对其所提供的验证服务内容，包括但不限于文字、软件、数据库、声音、图片、录像、图表等，拥有所有权和使用权。所有这些内容受版权、商标、专利和其它财产所有权法律的保护。</p>
        <p class="help_detail">4.2 任何人不得擅自以非法的方式传播、修改和使用本网站所提供的内容。</p>
        <h3 class="helph">5. 免责声明</h4>
        <p class="help_detail">5.1 用户知悉并理解，安全帮经过详细的测试，但不能保证与所有的软硬件系统完全兼容，不能保证所有服务完全没有错误，如果出现不兼容及服务错误的情况，用户可寻求技术支持以解决问题。电信北研院就此情形不承担任何责任。</p>
        <p class="help_detail">5.2 用户明确同意其免费使用安全帮所存在的风险将完全由其自己承担；在适用法律允许的最大范围内，对因使用或不能使用安全帮所产生的损害及风险，包括但不限于直接或间接的个人损害、商业赢利的丧失、贸易中断、商业信息的丢失或任何其它经济损失，电信北研院不承担任何责任。</p>
        <p class="help_detail">5.3 电信北研院不担保提供的功能及服务一定能满足用户的要求，也不担保服务不会中断，对服务的及时性、安全性、真实性、准确性都不作担保。 </p>
        <p class="help_detail">5.4 对于因运营商系统或互联网网络故障、计算机故障或病毒、信息损坏或丢失、计算机系统问题或其它任何不可抗力原因而产生损失，电信北研院不承担任何责任。</p>
        <p class="help_detail">5.5 用户违反本协议规定，对电信北研院造成损害的，电信北研院有权采取包括但不限于中断使用许可、停止提供服务、限制使用、法律追究等措施。</p>
        <h3 class="helph">6. 条款修改</h4>
        <p class="help_detail">6.1 电信北研院将有权随时修改本条款的有关内容，一旦本条款的内容发生变动，平台将通过适当方式作解释说明。</p>
        <p class="help_detail">6.2 如果用户不同意电信北研院对本条款相关内容所做的修改，有权停止使用安全帮。如果用户继续使用安全帮，则视为接受电信北研院对本协议相关内容所做的修改。</p>
        <p class="help_detail">本条款的更新修改及最终解释权归中国电信股份有限公司北京研究院所有。</p>
      </div>
      <input type="hidden" name="agreeId" id="agreeId"/>
     <div style="margin-top:35px;text-align:center;"><a href="javascript:void(0)"><img src="${ctx}/source/images/agree.png" id="agree"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="${ctx}/source/images/notagree.png" id="not_agree"/></a></div>
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
				//$(this).parents('li').children('.prompt').fadeOut();	
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
