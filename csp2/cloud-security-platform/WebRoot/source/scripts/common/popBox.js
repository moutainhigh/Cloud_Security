// JavaScript Document
$(function(){
	//增加资产
	$('.addass').delegate(this,'click',function(){	
		$('.shade').show();
		$('#revise').show();
		$('html').css({overflow:"hidden"});
	})
	
	//修改资产
		$('.zc_edit').delegate(this,'click',function(){
		$("#editAssetName").val($(this).attr("name"));
		$("#editAssetAddr").val($(this).attr("addr"));
		$("#editDistrictId").val($(this).attr("districtId"));
		getEditCitys($(this).attr("districtId"));
		var temp = $(this).attr("city");

		$("#editPurpose").val($(this).attr("purpose"));
		$("#editAssetid").val($(this).attr("id"));
		setTimeout(function(){
			$("#editCity").val(temp);
		},100);

		$('.shade').show();
		$('#updateAssest').show();
		$('html').css({overflow:"hidden"});
	})
	
	//关闭按钮
	
	$('.closed').delegate(this,'click',function(){
		$('.popBoxhide').hide();
		$('.shade').hide();
		$('html').css({overflow:'auto'})		
	})

	
//微信
$('.weixin').delegate(this,'click',function(){
		$('.shade').show();
		$('#weixin').animate({
			top:'50%',
			opacity:'100',	
		},500)
		$('html').css({overflow:'hidden'})
	
})


	
//关闭按钮
	
	$('.chide').delegate(this,'click',function(){
		$(this).parents('.popBoxhide').animate({
			top:'-80%',
			opacity:'0',	
		},500);
		$('.shade').hide();
		$('html').css({overflow:'auto'})		
	})
		
	
})


