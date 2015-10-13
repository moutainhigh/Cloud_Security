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
			var index = $(this).index()+1;
			$('.peiz_center ul li').removeClass('peiz_active');
			$('.peiz_center ul li a').removeClass('peiz_w');
			$('.peiz_center ul li a').addClass('peiz_b');
			$('.peiz_center ul li img').attr("src","");
			var imgs = $('.peiz_center ul li img');
			for(var i=0;i<imgs.length;i++){
				var img = imgs[i];
				var imgIndex = i+1;
				img.src="/cloud-security-platform-manager/source/images/user_"+imgIndex+".jpg"; 
			}
			
			$(this).addClass('peiz_active');
			$(this).find('a').addClass('peiz_w');
			$(this).find('img').attr("src","/cloud-security-platform-manager/source/images/user_"+index+".png");
			
			$('.peiz_cont').hide();
			//清空checkbox
			var items = $('input:checkbox');
			for(var i=0;i<items.length;i++){
				var item = items[i];
				item.checked = false;
			}
			
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

function seedetail(e) {
	var _index =$(".seedetail").index(e);  
	var orderId= $(".seedetail").eq(_index).attr("name");
	//资产个数
	$.ajax({
		type: "POST",
		url: "/cloud-security-platform-manager/orderDetail.html",
		data: {"orderId" : orderId},
		dataType:"json",
		success: function(data){
			$("#"+orderId).html(data.count);
			$("#"+orderId+"scan").html(data.num);
		},
	});
	var uservalue=$(e).attr('value');
    if(uservalue==0)
	{
      $(e).parents().next('.detailbox').show();
      $(e).attr('value',1);
     }
	  else if(uservalue==1)
	  {
        $(e).parents().next('.detailbox').hide();
        $(e).attr('value','0');
      }

};


