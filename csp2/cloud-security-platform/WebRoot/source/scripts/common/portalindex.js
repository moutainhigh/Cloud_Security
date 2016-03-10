// JavaScript Document
$(function(){
//菜单下拉效果	
$('.listJs').hover(function(){
	$(this).children('a').find('i').addClass('active');
	$('.listl').stop().slideDown();
},function(){
	$(this).children('a').find('i').removeClass('active');
	$('.listl').stop().slideUp("slow");
})

});