// JavaScript Document
window.onload =function(){
	//登录
	$('.user_login').click(function(){
			$(this).siblings().removeClass('cur');
			$(this).addClass('cur');
			$('.login_form').show();
			$('.regist_form').hide();
	});
	//注册
	$('.user_regist').click(function(){
			$(this).siblings().removeClass('cur');
			$(this).addClass('cur');
			$('.login_form').hide();
			$('.regist_form').show();
	});
	//切换邮箱验证
	$('#get_email_yzm').click(function(){
		$(this).hide();
		$('#phone_yzm').hide();
		$('#get_phone_yzm').show();
		$('#email_yzm').show();
		$('.yzfs').text('验证邮箱');
		$('#regist_phone').remove();
		$('#yzbox').html('<input type="text" class="regist_txt required email" name="email" id="regist_email" onblur="checkEmail()" /><span id="regist_mobile_email_msg" style="color:red">');
	});
	//切换手机验证
	$('#get_phone_yzm').click(function(){
		$(this).hide();
		$('#email_yzm').hide();
		$('#get_email_yzm').show();
		$('#phone_yzm').show();
		$('.yzfs').text('验证手机');
		$('#regist_email').remove();
		$('#yzbox').html('<input type="text" class="regist_txt required" name="phone" id="regist_phone" onblur="checkMobile()" /><span id="regist_mobile_email_msg" style="color:red">');
	});
	//增加新规则
	jQuery.validator.addMethod("username", function(value, element) {  
		var tel = /^1[3|5|8|7][0-9]{9}$/;
		var mail= /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
		return this.optional(element) || (tel.test(value)) || (mail.test(value));
	}, "请正确输入您的用户名");
	jQuery.validator.addMethod("regist_username", function(value, element) {  
		var registtel = /^([a-zA-Z0-9@#$%^&*=+]){6,16}$/;
		return this.optional(element) || (registtel.test(value));
	}, "请正确输入您的用户名");
	jQuery.validator.addMethod("phone", function(value, element) {  
		var registphone = /^1[3|5|8|7][0-9]{9}$/;
		return this.optional(element) || (registphone.test(value));
	}, "请正确输入您手机号码");
	//登录表单验证
	$("#form_login").validate({
		rules: {
			//验证用户名
			username: {    
				required:true,
				username: true
        	},
			password: {
				required: true,
				minlength: 6
		   }
    	},
    messages: {
	   username: {
		required: "请输入用户名",
		email: "请输入正确的email地址"
	   },
	   password: {
		required: "请输入密码",
		minlength: jQuery.format("密码不能小于{0}个字符")
	   },
	   yzm: {
		required: "请输入验证码"
	   }
	 }
	});
		//注册表单验证
	$("#form_regist").validate({
		rules: {
			//验证用户名
			regist_username: {    
				required:true,
				regist_username: true
        	},
			password: {
				required: true,
				minlength: 6
		   },
		   confirm_password: {
			required: true,
			minlength: 6,
			equalTo: "#regist_password"
		   },
		   phone:{
				required:true,
				phone: true
			}
    	},
    messages: {
	   regist_username: {
		required: "请输入用户名",
		email: "请输入正确的email地址"
	   },
	   password: {
		required: "请输入密码",
		minlength: jQuery.format("密码不能小于{0}个字符")
	   },
	   phone: {
		required: "请输入手机号码"
	   },
	   yzm: {
		required: "请输入验证码"
	   },
	   confirm_password: {
		required: "请输入确认密码",
		minlength: "确认密码不能小于6个字符",
		equalTo: "两次输入密码不一致"
	   },
	   email:{
			required:"请输入邮箱"   
		}
	 }
	});
	jQuery.extend(jQuery.validator.messages, {
	    required: "请填写",
	    remote: "验证失败",
	    email: "请正确输入您的邮箱",
	    url: "请输入正确的网址",
	    date: "请输入正确的日期",
	    dateISO: "请输入正确的日期 (ISO).",
	    number: "请输入正确的数字",
	    digits: "请输入正确的整数",
	    creditcard: "请输入正确的信用卡号",
	    equalTo: "请再次输入相同的值",
	    accept: "请输入指定的后缀名的字符串",
	    maxlength: jQuery.validator.format("允许的最大长度为 {0} 个字符"),
	    minlength: jQuery.validator.format("密码大于 {0} 位"),
	    rangelength: jQuery.validator.format("允许的长度为{0}和{1}之间"),
	    range: jQuery.validator.format("请输入介于 {0} 和 {1} 之间的值"),
	    max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
	    min: jQuery.validator.format("请输入一个最小为 {0} 的值")
	});
	function checkDel(){
		return delConfirm();
	};
//跟换验证码
function checkNumberImage(){
	var imageNumber = document.getElementById("imageNumber");
	imageNumber.src = "images/login_yzm.png?timestamp="+new Date().getTime();
};
 $('#imageNumber').click(function(){checkNumberImage()});
}