var checkName1 = 0;

//校验用户名
function checkEditName(){
	var name = $("#editUseName").val();
	var	pattern	= /^[a-zA-Z0-9_]{4,20}$/;
	var flag = pattern.test(name);
	if(name==""||name==null){
		$("#update_name_msg").html("用户名不能为空");
		checkName1=0;
	}else{
		if(flag){
			$.ajax({
               type: "POST",
               url: "/cloud-security-platform-manager/regist_checkName.html",
               data: {"name":name,"mark":"edit"},
               dataType:"json",
               success: function(data){
                   if(data.count>0){
                   		$("#update_name_msg").html("用户名已经存在");
                   		checkName1=0;
                   }else{
                   		$("#update_name_msg").html("");
                   		checkName1=1;
                   }
               },
            }); 
		}else{
			$("#update_name_msg").html("请输入4-20位英文、数字、下划线及其组合");
			checkName1=0;
		}
	}
}

function edit(){
	var type = $("#editType").val();// 获取用户类型
	var ipStart = $("#editStartIP").val();
	var ipEnd = $("#editEndIP").val();
	var name = $("#editUseName").val();// 用户名
	var editRealName = $("#editRealName").val();//真实姓名

	checkEditName();
	if (checkName1 == 1) {
		if(editRealName.length>12){
			 $("#update_realname_msg").html("真实姓名长度不能大于12");
			 return;
		 }else{
			 $("#update_realname_msg").html("");
		 }
		var p1 = $("#update_password").val();// 获取密码框的值
		var p2 = $("#update_confirm_password").val();// 获取重新输入的密码值
		if (p1 == "") {
			$("#update_password_msg").html("<font color='red'>密码不能为空!</font>");
		} else if (p1.length < 6 || p1.length > 20) {
			$("#update_password_msg").html("请输入6-20位，支持英文，数字，字符组合");
		} else {
			$("#update_password_msg").html("");
		}
		if (p2 == "") {
			$("#update_confirm_password_msg").html(
					"<font color='red'>确认密码不能为空!</font>");
		} else if (p2.length < 6 || p2.length > 20) {
			$("#update_confirm_password_msg").html("请输入6-20位，支持英文，数字，字符组合");
		} else {
			$("#update_confirm_password_msg").html("");
		}
		if (p2 != null && p2 != "") {
			if (p1 != p2) {
				$("#update_confirm_password_msg").html("<font color='red'>两次输入密码不一致，请重新输入</font>");

			} else {
				$("#update_confirm_password_msg").html("");
				if (type == "-1") {
					$("#edit_type_msg").html(
							"<font color='red'>请选择用户分组!</font>");
				} else {
					if (type == "3") {
						var mobile = $("#editPhone").val();
						if (mobile == "" || mobile == null) {
							$("#edit_mobile_msg").html("企业用户手机号码不能为空!");
						} else {
							$("#edit_mobile_msg").html("");
							if (CheckIPEdit(ipStart, ipEnd)) {
								$("#editUserForm").submit();
							}
						}
					} else {
						$("#edit_mobile_msg").html("");
						$("#editUserForm").submit();
					}

				}
			}
		}
	}
}

//验证IP
function CheckIPEdit(ipStart,ipEnd){
	var reg=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
	if(ipStart==""){
		$("#edit_ip_msg").html("起始IP不能为空!");
		return false;
	}else if(reg.test(ipStart)) {    
	    if( !(RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)){   
	    	   $("#edit_ip_msg").html("起始IP格式不正确!");
	    	   return false;
	    }else{
	    	$("#edit_ip_msg").html("");
	    }
	}else{
		$("#edit_ip_msg").html("起始IP格式不正确!");
		return false;
	}
	
	if(ipEnd==""){
		$("#edit_ip_msg").html("<font color='red'>终止IP不能为空!</font>");
		return false;
	}else if(reg.test(ipEnd)) {    
	    if( !(RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)){   
	    	   $("#edit_ip_msg").html("<font color='red'>终止IP格式不正确!</font>");
	    	   return false;
	    }else{
	       $("#edit_ip_msg").html("");
	    }
	}else{
		$("#edit_ip_msg").html("<font color='red'>终止IP格式不正确!</font>");
		return false;
	}
	
	if(reg.test(ipStart))     
    {     
       if( !(RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)){   
    	   $("#edit_ip_msg").html("<font color='red'>起始IP格式不正确!</font>");
    	   return false;
       }
    }else{
       $("#edit_ip_msg").html("<font color='red'>起始IP格式不正确!</font>");
  	   return false;
    }
	if(reg.test(ipEnd))     
    {     
       if( !(RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)){   
    	   $("#edit_ip_msg").html("<font color='red'>终止IP格式不正确!</font>");
    	   return false;
       }
    }else{
 	   $("#edit_ip_msg").html("<font color='red'>终止IP格式不正确!</font>");
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
    	$("#edit_ip_msg").html("<font color='red'>起始IP大于终止IP!</font>");
    	return false;
    }
    return true;
}
//校验手机号码是否出现重复
function editCheckMobile(){
	var mobile = $("#editPhone").val();
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
                        $("#edit_mobile_msg").html("您填写的手机号码已使用");//将发送验证码的button锁住，此时不能点击发送验证码
                    }else{
                   		$("#edit_mobile_msg").html("");
                    }
               }
            });
		}else{
			$("#edit_mobile_msg").html("手机号码格式不对");
		}
	}
}

//修改时显示IP段输入框
function editShow(index){
	if(index != "-1"){
		 $("#edit_type_msg").html("");
		if(index=="3"){
			$("#ipRange").show();
		}else{
			$("#ipRange").hide();
		}
	}
}