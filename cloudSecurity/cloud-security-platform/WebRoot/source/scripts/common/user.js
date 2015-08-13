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
		url: "/cloud-security-platform/orderDetail.html",
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


