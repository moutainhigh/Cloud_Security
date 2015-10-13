function loginSubmit(){
	var name = $("#login_name").val();
	var password = $("#login_password").val();
	var checkNumber = $("#checkNumber").val();
	if(name==null||name==""||password==null||password==""||checkNumber==null||checkNumber==""){
		if(name==null||name==""){
			$("#login_name_msg").html("请输入用户名");
		}else{
			$("#login_name_msg").html("");
		}
		if(password==null||password==""){
			$("#login_password_msg").html("请输入密码");
		}else{
			$("#login_password_msg").html("");
		}
		if(checkNumber==null||checkNumber==""){
			$("#login_checkNumber_msg").html("请输入验证码");
		}else{
			$("#login_checkNumber_msg").html("");
		}
	}else{
			$("#b_login").submit();
	}
}


