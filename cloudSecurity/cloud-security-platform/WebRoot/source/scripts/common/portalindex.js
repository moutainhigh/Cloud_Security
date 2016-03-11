// JavaScript Document
$(function(){
	//点击空白 关闭弹出窗口	
$(document).bind("mousedown",function(e){var target=$(e.target);if(target.closest(".pop").length==0)$(".pop").hide()});
//菜单下拉效果	
$('.listJs').hover(function(){
	$(this).children('a').find('i').addClass('active');
	$('.listl').slideDown();
},function(){
	$(this).children('a').find('i').removeClass('active');
	$('.listl').stop().slideUp("slow");
})
//列表悬停效果
topshow();

onclick('clickBox');
onclickTime('time');
$('.dropdown').click(function(){
	$('.dropdown-menu').fadeIn();

})
$('.dropdown-menu li').hover(function(){
			$(this).addClass('active');
		},function(){
			$(this).removeClass('active');
		})
//		for(var i=0;i<10; i++){
//			$('.dropdown-menu').append('<li><label name="a"><input type="checkbox"  id='+ i +' >1</label></li>');	
//		}

		$('.dropdown-menu li').each(function(){
			$(this).delegate(this,'click',function(){
				var ck=$(this).find('input');
				var id=$(this).find('input').attr('id');
				if($(ck).is(':checked')){
					var v= $(this).children('label').text();
					$('.btnNew em').before('<i id='+ id +'>'+ v +',</i>');	
				}else
				{
					$('.btnNew i').each(function(index, element) {
                        var iId =$(this).attr('id');
						if(id==iId){
							$(this).remove();	
						}
                    });
				}
				
			
			})
			
		})
		//按钮切换显示	
		$('.Single').click(function(){
			$('.start').show();
			$('.end').hide();	
		})
		$('.long').click(function(){
			$('.start').show();
			$('.end').show();	
		})	

}); 
function topshow(){
	$('.newlist-top li').hover(function(){
		$(this).stop().animate({
			top:'-6px'
		},500)
	},function(){
$(this).stop().animate({
			top:'0'
		},500)
	})

}

function onclick(id){
	var cass=document.getElementById(id);
	$(cass).children('button').click(function(){
		$(this).addClass('click').siblings().removeClass('click');	
	})	
}

function onclickTime(id){
	var cass=document.getElementById(id);
	$(cass).children('button').click(function(){
		$(this).addClass('clickTime').siblings().removeClass('clickTime');	
	})	
}





		
