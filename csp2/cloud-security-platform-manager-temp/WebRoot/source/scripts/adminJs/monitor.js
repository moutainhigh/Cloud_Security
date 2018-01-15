
$(function(){
	/*
	setInterval(function(){
		monitorLine();
	},300000);
	*/
	
	var oMark =document.getElementById('modelbox');
	var oLogin =document.getElementById('box_logoIn');
	
	//修改资产
	$('.zc_edit').delegate(this,'click',function(){
		var id = $(this).attr("id");
		var name = $(this).attr("name");
		var addr = $(this).attr("addr");
		var districtId = $(this).attr("districtId");
		var type = $(this).attr("assetType");
		var temp = $(this).attr("city");
		var purpose = $(this).attr("purpose");

				$(".editMsg").html("");
				$(".w634").html("修改项目");
				$("#hiddenEditName").val(name);
				$("#hiddenEditAddr").val(addr);
				$("#editAssetName").val(name);
				$("#editAssetAddr").val(addr);
				$("#editDistrictId").val(districtId);
				
				if (type==0) {
					$("input[name='editAssetType'][value='http']").attr("checked",true); 
				}else if(type==1){
					$("input[name='editAssetType'][value='https']").attr("checked",true);
				}
				


				oMark.style.display ="block";
				oLogin.style.display ="block";
				oMark.style.width = viewWidth() + 'px';
				oMark.style.height = documentHeight() + 'px';
				oLogin.style.left = (viewWidth() - oLogin.offsetWidth)/2 + 'px';
				oLogin.style.top = (viewHeight() - oLogin.offsetHeight)/2-25 + 'px';	
				

	})
	
	
	
	//添加 点击创建项目
	
	$("#add_ser").click(function(){
		var _index =$(".add_ser").index(this);  //获取当前点击按钮
		$(".prompt").html("");
		$("#name").val("");
		$("#addr").val("");
		$("#email").val("");
		$("#message").val("");
		
		//var image=$(this).parent().find("a img");
		//$(".box_logoIn").empty()
		oMark.style.display ="block";
		oLogin.style.display ="block";
		oMark.style.width = viewWidth() + 'px';
		oMark.style.height = documentHeight() + 'px';
		oLogin.style.left = (viewWidth() - oLogin.offsetWidth)/2 + 'px';
		oLogin.style.top = (viewHeight() - oLogin.offsetHeight)/2-25 + 'px';	
		
		//image.clone(true).appendTo(".box_logoIn");
	
	})    
	
	function toClose(){
		var oClose = document.getElementById('close');
		oClose.onclick = function(){
			oMark.style.display ="none";
			oLogin.style.display ="none";
			//$(".box_logoIn").empty()			
		};
	}
	toClose();
		
	window.onscroll = window.onresize = function(){
		
		var oDiv = document.getElementById('box_logoIn');
		if(oDiv){
			oDiv.style.left = (viewWidth() - oDiv.offsetWidth)/2 + 'px';
			oDiv.style.top = (viewHeight() - oDiv.offsetHeight)/2-25 + 'px';
		}
	
	}
})

//获取浏览器可视区的宽度和高度
function viewWidth(){
	return document.documentElement.clientWidth;
}
function viewHeight(){
	return document.documentElement.clientHeight;
}
function documentHeight(){
	return Math.max(document.documentElement.scrollHeight || document.body.scrollHeight,document.documentElement.clientHeight);
}
function scrollY(){
	return document.documentElement.scrollTop || document.body.scrollTop;
}


//关闭按钮
function toClose(){
	var oClose = document.getElementById('close');
	oClose.onclick = function(){
		oMark.style.display ="none";
		oLogin.style.display ="none";
		//$(".box_logoIn").empty()
		
	};
}



//删除
function deleteMon(id){}

//修改
function AlterMon(id){}

//立即创建
function add(){
	$(".prompt").html("");
	var name = $("#name").val();
	var addr = $("#addr").val();
	var frequency = $("input:radio[name='frequency']:checked").val();
	var typeFalg=0;
	var monType = null;
	var serverType = null;
	var alarm = null;	//格式：email,***;message,***
	var alarmFalg = 0;
	
	if(!name){
		$("#name_msg").html("监控名称不能为空！"); 
		$("#name").focus(); 
		return false;
	}
	if(!addr){
		$("#addr_msg").html("监控地址不能为空！"); 
		$("#addr").focus(); 
		return false;
	}
	
	$("input:checkbox[name='monType']:checked").each(function(){
		typeFalg=1;
		monType+=$(this).val()+',';
		if($(this).val()=='server'){
			serverType = $("#serverType").val();
		}
	});
	if(!typeFalg){
		$("#type_msg").html("选择告警提示方式！"); 
		$("#host").focus(); 
		return false;
	}else{
		if(monType!=null){	
			monType=monType.substring(0,monType.length-1);
		}
	}
	
	$("input:checkbox[name='alarm']:checked").each(function(){
		alarmFalg=1;
		if($(this).val()=='email'){
			if($("#email").val()==""){
				$("#alarm_msg").html("邮箱地址不能为空！"); 
				$("#email").focus();
				return false;
			}
			if (!$("#email").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)) { 
				$("#alarm_msg").html("邮箱格式不正确！请重新输入！"); 
				$("#email").focus(); 
				return false; 
			}
			alarm+="email,"+$("#email").val()+";";
		}
		else if($(this).val()=='message'){
			if ($("#message").val() == "") { 
				$("#alarm_msg").html("手机号码不能为空！"); 
				$("#message").focus(); 
				return false; 
			} 

			if (!$("#message").val().match(/^(((13[0-9]{1})|159|153)+\d{8})$/)) { 
				$("#alarm_msg").html("手机号码格式不正确！请重新输入！"); 
				$("#message").focus(); 
				return false; 
			}
			alarm+="message,"+$("#message").val()+";";
		}		
	});
	if(!alarmFalg){
		$("#alarm_msg").html("选择告警提示方式！"); 
		$("#alarm1").focus(); 
		return false;
	}else{
		if(alarm){
			alarm=alarm.substring(0, alarm.length-1);
		}
	}
	
	$.ajax({
		type:"post",
		url:"",
		data:{
			"name":name,
			"addr":addr,
			"frequency":frequency,
			"monType":monType,
			"serverType":serverType,
			"alarm":alarm
		},
		dataType:"json",
		success:function(data){
			
		},
		error:function(data){
			alert("cuowu");
		}
	});
}

//显示列表
function monitorLine(){
	$.ajax({
		type:"post",
		url:"monitorLine.html",
		dataType:"json",
		success:function(data){
			var htmlStr="";		
			for(i=0;i<data.taskList.length;i++){
				var lasttime,createtime;
				if(data.taskList[i].lastdetecttime){
					lasttime=data.taskList[i].lastdetecttime.split(" ")[0];
				}else{
					lasttime="null";
				}
				if(data.taskList[i].createtime){
					createtime=data.taskList[i].createtime.split(" ")[0];
				}else{
					createtime="null";
				}
				htmlStr += '<tr><td width="12%"><span>'+data.taskList[i].taskname+'</span></td>'+'<td width="16%"><span>'+data.taskList[i].targeturl+'</span></td>'
				+'<td width="6%">'+data.taskList[i].monitor_type+'</td>'+'<td width="6%">'+data.taskList[i].frequency+'</td>'
				+'<td width="6%">'+data.taskList[i].availability+'</td>'+'<td width="6%">'+data.taskList[i].responsetime+'</td>'
				+'<td width="6%">'+data.taskList[i].laststatus+'</td>'+'<td width="18%">'+lasttime+'</td>'
				+'<td width="18%">'+createtime+'</td>'+'<td width="6%"><a href="javascript:void(0)" style="color:#2499fb;" onclick="deleteMon('+data.taskList[i].id+')">删除</a></td></tr>';
			}
			$("#monList").html(htmlStr);
		},
		error:function(data){
			alert("cuowu");
		}
	});
}