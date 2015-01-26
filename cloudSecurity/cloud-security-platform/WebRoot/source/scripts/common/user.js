// JavaScript Document

//$(function (){
//	$('.ding_nav ul li').click(function (){
//		
//		$('.ding_nav ul li').removeClass('ding_active');
//		$(this).addClass('ding_active');
//		$(this).prevAll().addClass('ding_active_1');
//		$(this).addClass('ding_active');
//		
//		$('.ding_center').hide();
//		$('.ding_center').eq($(this).index()).show();
//	});
//});

$(function (){
	$('.peiz_center ul li').click(function (){
		if(!$(this).hasClass("opacity")){
			$('.peiz_center ul li').removeClass('peiz_active');
			$(this).addClass('peiz_active');
			
			$('.peiz_cont').hide();
			$('.peiz_cont').eq($(this).index()).show();
		}
	});
});

$(function (){
	$('.pinv_sub_nav li').click(function (){
		
		$('.pinv_sub_nav li').removeClass('pinv_active');
		$(this).addClass('pinv_active');
        $('.pinv_subcenter').hide();
		$('.pinv_subcenter').eq($(this).index()).show();
	});
});


$(function(){/*
    $('.user_secta').click(function(){
    var uservalue=$(this).attr('value');
    if(uservalue==0)
	{
      $(this).siblings('.user_secta_list').show();
      $(this).attr('value',1);
     }
	  else if(uservalue==1)
	  {
        $(this).siblings('.user_secta_list').hide();
        $(this).attr('value','0');
      }
});*/
$('.seedetail').click(function(){
    var uservalue=$(this).attr('value');
    if(uservalue==0)
	{
      $(this).parents().next('.detailbox').show();
      $(this).attr('value',1);
     }
	  else if(uservalue==1)
	  {
        $(this).parents().next('.detailbox').hide();
        $(this).attr('value','0');
      }
});
});

