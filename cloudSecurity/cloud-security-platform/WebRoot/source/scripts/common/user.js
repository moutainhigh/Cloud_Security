// JavaScript Document
$(function (){
	$('.user_1 li').click(function (){
		
		$('.user_1 li').removeClass('active');
		$(this).addClass('active');
		
		$('.user_right').hide();
		$('.user_right').eq($(this).index()).show();
	});
});