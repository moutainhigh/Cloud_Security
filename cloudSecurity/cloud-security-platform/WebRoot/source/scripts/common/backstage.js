// JavaScript Document
$(function(){
//服务管理中服务的修改与删除的显示隐藏--ld
	$('.ser_list li').hover(function(){
		$(this).children('dl').css('background','#e0e0e0');
		$(this).children('dl').children('dt').css('background','url(images/b_ser_icon2.jpg) no-repeat center center');
		$(this).children('.ser_change').show();
	},function(){
		$(this).children('dl').css('background','#fff');
		$(this).children('dl').children('dt').css('background','url(images/b_ser_icon.jpg) no-repeat center center');
		$(this).children('.ser_change').hide();
	});	
//数据分析页面的分析类型切换--ld
	
		$('#dd_btn').click(function(){
			$(this).siblings().removeClass('datab_cur');
			$(this).addClass('datab_cur');
			$('.dd_data_box').show();
			$('.gj_data_box').hide();
		});
		$('#gj_btn').click(function(){
			$(this).siblings().removeClass('datab_cur');
			$(this).addClass('datab_cur');
			$('.gj_data_box').show();
			$('.dd_data_box').hide();
		});
//用户管理页面的用户类型切换--ld
	$('.b_user_table_box').click(function(){
		//超级管理员
		$.ajax({
	        type: "POST",
	        url: "/cloud-security-platform/adminUserList.html",
	        data: {"type":0},
	        dataType:"json",
	        success: function(data){
	        	alert(data.userList);
	        },
	     }); 
		$(this).addClass('userbox_cur');
		$(this).siblings().removeClass('userbox_cur');
	});
		$('#supper').click(function(){
			//超级管理员
			$.ajax({
		        type: "POST",
		        url: "/cloud-security-platform/adminUserList.html",
		        data: {"type":0},
		        dataType:"json",
		        success: function(data){
		        	alert(data.userList);
		        },
		     }); 
			$('.supper_table').show();
			$('.system_table').hide();
			$('.users_table').hide();
		});
		$('#system').click(function(){
			//系统管理员
			$.ajax({
		        type: "POST",
		        url: "/cloud-security-platform/adminUserList.html",
		        data: {"type":1},
		        dataType:"json",
		        success: function(data){
		        	alert(data.userList);
		        },
		     }); 
			$('.system_table').show();
			$('.supper_table').hide();
			$('.users_table').hide();
		});
		$('#users').click(function(){
			//注册用户
			$.ajax({
		        type: "POST",
		        url: "/cloud-security-platform/adminUserList.html",
		        data: {"type":2},
		        dataType:"json",
		        success: function(data){
		        	alert(data.userList);
		        },
		     }); 
			$('.users_table').show();
			$('.system_table').hide();
			$('.supper_table').hide();
		});
});