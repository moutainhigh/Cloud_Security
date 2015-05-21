function edit(){
	
	 var p1=$("#update_password").val();//获取密码框的值
	  var p2=$("#update_confirm_password").val();//获取重新输入的密码值
	  if(p1==""){
		    	$("#update_password_msg").html("<font color='red'>密码不能为空!</font>");
	   }else if(p1.length<6||p1.length>20){
		   $("#update_password_msg").html("请输入6-20位，支持中英文，数字，字符组合");
	   }else{
		   	$("#update_password_msg").html("");
	   }
	  if(p2==""){
		   	$("#update_confirm_password_msg").html("<font color='red'>确认密码不能为空!</font>");
	  }else if(p2.length<6||p2.length>20){
		   $("#update_confirm_password_msg").html("请输入6-20位，支持中英文，数字，字符组合");
	   }else{
		   	$("#update_confirm_password_msg").html("");
	   }
	  if(p2!=null&&p2!=""){
		  if (p1!=p2) {
		   	$("#update_confirm_password_msg").html("<font color='red'>两次输入密码不一致，请重新输入</font>");
		
		  }else{
		    $("#update_confirm_password_msg").html("");
		   	$("#editUserForm").submit();
		  }
	  }
}