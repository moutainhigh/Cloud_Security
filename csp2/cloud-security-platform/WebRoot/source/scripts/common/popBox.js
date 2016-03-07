// JavaScript Document
$(function(){


	
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


