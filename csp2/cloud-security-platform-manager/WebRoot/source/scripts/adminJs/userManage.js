var checkName1 = 0;
var checkPassword1 = 0;
var checkConfirmPassword1 = 0;

//校验用户名
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
               url: "/cloud-security-platform-manager/regist_checkName.html",
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
			$("#regist_name_msg").html("请输入4-20位英文、数字、下划线及其组合");
			checkName1=0;
		}
	}
}
//校验密码	
function checkPassword(){
	var p1 = $("#regist_password").val();
	
	if(p1==""||p1==null){
		$("#regist_password_msg").html("密码不能为空");
		checkPassword1 = 0;
	}else{
		if(p1.length<6||p1.length>20){
			$("#regist_password_msg").html("请输入6-20位，支持英文，数字，字符组合");
			checkPassword1 = 0;
		}else{
			$("#regist_password_msg").html("");
			checkPassword1 = 1;
		}
		
	}
}
//校验确认密码	
function checkConfirmPassword(){
	  var p1=$("#regist_password").val();//获取密码框的值
	  var p2=$("#regist_confirm_password").val();//获取重新输入的密码值
	  if(p2!=null&&p2!=""){
		  if (p1!=p2) {
		   	$("#regist_confirm_password_msg").html("<font color='red'>两次输入密码不一致，请重新输入</font>");
		    checkConfirmPassword1 = 0;
		  }else{
		    $("#regist_confirm_password_msg").html("");
		    checkConfirmPassword1 = 1;
		  }
	  }else{
		  $("#regist_confirm_password_msg").html("<font color='red'>确认密码不能为空</font>");
		  checkConfirmPassword1 = 0;
	  }
}
//提交
function add(){
	 var name=$("#regist_name").val();//用户名
	 var type=$("#addType").val();//获取用户类型
	 var ipStart=$("#addStartIP").val();
	 var ipEnd=$("#addEndIP").val();
	 var p1=$("#regist_password").val();//获取密码框的值
	 var p2=$("#regist_confirm_password").val();//获取重新输入的密码值
	 var realName=$("#realName").val();//真实姓名

	 checkName();
	 checkPassword();
	 checkConfirmPassword();
	 
	 if(checkName1==1&&checkPassword1==1&&checkConfirmPassword1==1){
		 if (name == "") {
				$("#regist_name_msg").html("<font color='red'>用户账号不能为空!</font>");
			} else if (p1.length < 4 || p1.length > 20) {
				$("#regist_name_msg").html("4-20位字符，支持英文，数字，字符组合");
			} else {
				$("#regist_name_msg").html("");
			}
		 if(realName.length>12){
			 $("#regist_realname_msg").html("真实姓名长度不能大于12");
			 return;
		 }else{
			 $("#regist_realname_msg").html("");
		 }
//			if (p1 == "") {
//				$("#regist_password_msg").html("<font color='red'>密码不能为空!</font>");
//			} else if (p1.length < 6 || p1.length > 20) {
//				$("#regist_password_msg").html("请输入6-20位，支持英文，数字，字符组合");
//			} else {
//				$("#regist_password_msg").html("");
//			}
//			if (p2 == "") {
//				$("#regist_confirm_password_msg").html("<font color='red'>确认密码不能为空!</font>");
//			} else if (p2.length < 6 || p2.length > 20) {
//				$("#regist_confirm_password_msg").html("请输入6-20位，支持英文，数字，字符组合");
//			} else {
//				$("#regist_confirm_password_msg").html("");
//			}
			if (p2 != null && p2 != "") {
				if (p1 != p2) {
					$("#regist_confirm_password_msg").html("<font color='red'>两次输入密码不一致，请重新输入</font>");
				} else {
					$("#regist_confirm_password_msg").html("");
					if (type == "-1") {
						$("#regist_type_msg").html("<font color='red'>请选择用户分组!</font>");
					} else {
						if (type == "3") {
							// 手机号不能为空
							var mobile = $("#addPhone").val();
							if (mobile == "" || mobile == null) {
								$("#add_mobile_msg").html("企业用户手机号码不能为空!");
							} else {
								$("#add_mobile_msg").html("");
								if (checkIP(ipStart, ipEnd)) {
									$("#form_regist").submit();
								}
							}
						} else {
							$("#add_mobile_msg").html("");
							$("#form_regist").submit();
						}
					}
				}
			}
	 }
	
}

//验证IP
function checkIP(ipStart,ipEnd){
	var reg=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
	if(ipStart==""){
		$("#add_ip_msg").html("起始IP不能为空!");
		return false;
	}else if(reg.test(ipStart)) {    
	    if( !(RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)){   
	    	   $("#add_ip_msg").html("起始IP格式不正确!");
	    	   return false;
	    }else{
	    	$("#add_ip_msg").html("");
	    }
	}else{
		$("#add_ip_msg").html("起始IP格式不正确!");
		return false;
	}
	
	if(ipEnd==""){
		$("#add_ip_msg").html("<font color='red'>终止IP不能为空!</font>");
		return false;
	}else if(reg.test(ipEnd)) {    
	    if( !(RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)){   
	    	   $("#add_ip_msg").html("<font color='red'>终止IP格式不正确!</font>");
	    	   return false;
	    }else{
	       $("#add_ip_msg").html("");
	    }
	}else{
		$("#add_ip_msg").html("<font color='red'>终止IP格式不正确!</font>");
		return false;
	}

	var temp1 = ipStart.split(".");  
    var temp2 = ipEnd.split(".");   
    var ips = 0;
    var ipe = 0;
    for (var i = 0; i < 4; ++i)  
    {  
        ips = ips << 8 | parseInt(temp1[i]);   
        ipe = ipe << 8 | parseInt(temp2[i]);  
    }
    if (ips > ipe) {
    	$("#add_ip_msg").html("<font color='red'>起始IP大于终止IP!</font>");
    	return false;
    }
    return true;
}
//删除
function deleteUser(id){
	var userId = id;
	//检查订单  资产表是否有此用户的 资产和订单，若有：则提示不能删除，若无：则可以删除
	$.post("/cloud-security-platform-manager/adminDeleteCheck.html", {"id" : userId}, function(data, textStatus) {
		if (data.count>0){
			alert("您要删除的用户下有订单或资产，不能删除！");
			return false;
		}else{
			if (window.confirm("确实要删除吗?")==true) {
				window.location.href="/cloud-security-platform-manager/adminDeleteUser.html?id="+userId;
			} else {
				return;
			}
		}
	});
}
//搜索用户
function adminSearch(){
	$("#searchForm").submit();
}

//添加用户时显示IP段输入框
function addShow(index){
	if(index != "-1"){
		 $("#regist_type_msg").html("");
		if(index=="3"){
			$("#ipAddRange").show();
		}else{
			$("#ipAddRange").hide();
		}
	}
}



//校验手机号码是否出现重复
function checkMobile(){
	var mobile = $("#addPhone").val();
	var pattern = /^1[3|5|8|7][0-9]{9}$/;
	var flag = pattern.test(mobile);
	if(mobile!="" && mobile!=null){
		if(flag){
			$.ajax({
               type: "POST",
               url: "regist_checkMobile.html",
               data: {"mobile":mobile},
               dataType:"json",
               success: function(data){
                    if(data.count>0){
                        $("#add_mobile_msg").html("您填写的手机号码已使用");//将发送验证码的button锁住，此时不能点击发送验证码
                    }else{
                   		$("#add_mobile_msg").html("");
                    }
               }
            });
		}else{
			$("#add_mobile_msg").html("手机号码格式不对");
		}
	}
}	

