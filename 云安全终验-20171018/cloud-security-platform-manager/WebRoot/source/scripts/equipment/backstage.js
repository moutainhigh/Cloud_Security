$(function(){
	//选项卡
$('.tabList li').click(function(){
	var index=$(this).index();
	$(this).addClass('active').siblings('li').removeClass('active');
	$('.tabCont .tabItem:eq('+index+')').show().siblings().hide();
});
	
	
	
$('.anlist li').click(function(){
	var index=$(this).index();
	$(this).addClass('active').siblings('li').removeClass('active');
	
	$(this).parent('.anlist').siblings('.analyse_tabCont').children('.analyse_tabItem:eq('+index+')').show().siblings().hide();
	
});



$('.tableBox tbody tr:even').css('background','#fafafa')	

//select切换
$('.user_select').change(function(){
	var val=$(this).val();
	if(val==1)
	{
		$(this).parents('.user_form').siblings('.tableBox').find('.tableUsert').hide();
		$(this).parents('.user_form').siblings('.tableBox').find('.tableUser').show();	
		
	}
	else
	{
		$(this).parents('.user_form').siblings('.tableBox').find('.tableUsert').show();
		$(this).parents('.user_form').siblings('.tableBox').find('.tableUser').hide();		
	}
})
	
})