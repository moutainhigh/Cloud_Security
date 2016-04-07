// JavaScript Document
$(function(){
	//点击空白 关闭弹出窗口	
$(document).bind("mousedown",function(e){var target=$(e.target);if(target.closest(".pop").length==0)$(".pop").hide()});

//模拟多选
sck();
//结算
deck();
//API切换
tabApi();
//
/*$('.hbule').hover(function(){
	$('.hbule').css('color','#4a4a4a');
	$(this).css('color','#2499fb');
},function(){
	$('.hbule').css('color','#4a4a4a');
	$(this).css('color','#4a4a4a');
})

$('.listl').mouseover(function(){
	$('.listJs').children('a').css('color','#2499fb');	
})
$('.listl').mouseout(function(){
	$('.listJs').children('a').css('color','#4a4a4a');	
})
*/
//

//菜单下拉效果	
/*$('.listJs').hover(function(){
	$(this).children('a').find('i').addClass('active');
	$('.listl').stop().slideDown();
},function(){
	$(this).children('a').find('i').removeClass('active');
	$('.listl').stop().slideUp("slow");
})
*/

//列表悬停效果
topshow();
topshow2();
//修改发票
tab();
//改变个数
minsum();

//遮罩层
showout();
//二维码
ask();

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
			$('.time').hide();	
		})
		$('.long').click(function(){
			$('.start').show();
			$('.end').show();	
			$('.time').show();	
		})	

		//结束时间和频率初始化  add by tangxr 2016-3-14 
		var orderType = $("#orderType").val();
		if(orderType == 0){
			$('.end').hide();
			$('.time').hide();
		}
		if(orderType == 1){
			$('.type').hide();	
		}
}); 
function topshow(){
	$('.newlist-top li').hover(function(){
		$(this).addClass('showdow');
		$(this).stop().animate({
			top:'-6px',
		},500)
	},function(){
		$(this).removeClass('showdow');
		$(this).stop().animate({
			top:'0'
			},500)
		})

}

function topshow2(){
	$('.fl-pic .imgist li').hover(function(){
		$(this).addClass('showdow');
		$(this).stop().animate({
			top:'-6px',
		},500)
	},function(){
		$(this).removeClass('showdow');
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

//修改发票切换
function tab(){
	$('.modify').bind('click',function(){
		$('.textvalue').val('');
		$('.invoiceshow').hide();
		$('.modifyBox').show();
		
	});


	$('.cancel').bind('click',function(){
		$('.textvalue').val('');
		$('.invoiceshow').show();
		$('.modifyBox').hide();
	})

	function value(){
		
		var _val;
		$('.select').change(function(){
			_val=$(this).val();	
		});

		$('.preservation').bind('click',function(){
			var textv=$('.textvalue').val();
			$('.modifyBox').hide();
			$('.introduce-two').text('');
			$('.invoice-one').text('');
			$('.introduce-two').text(textv);
			$('.invoice-one').text(_val);
			$('.invoiceshow').show();
			
		})
	}
	value();

}

//模拟多选
function sck(){
	$('.chck').delegate(this,'click',function(){
		var rmbBox=[];
		var is=$(this).hasClass('this');
		if(is==false)
		{
			$('.settle b').text();
			$(this).addClass('this');
			$(this).addClass('count');
		}else{
			$(this).removeClass('this');
			$(this).removeClass('count');
		}

		
		
	})
	$('#check').click(function(){
		var clength=$('.count');
		var lenth=$('.chck');
		var clengthb=clength.length;
		var lents=lenth.length;
		
		var is=$(this).hasClass('this');
		if(is==false)
		{
			$(this).addClass('this');
			$('.settle b').text(lents);
			
		}else{
			$(this).removeClass('this');
			$('.settle b').text(0);
			
		}
		var ose=$(this).hasClass('this');
		if(ose==true){
			
			$('.chck').addClass('this');
			$('.chck').addClass('count');
		}
		else
		{
			
			$('.chck').removeClass('this');
			$('.chck').removeClass('count');
		}
	})
	$('.chck').click(function(){
		var clength=$('.count');
		var lenth=$('.chck');
		var clengthb=clength.length;
		var lents=lenth.length;
		
		 if(clengthb==lents){
		 	$('#check').addClass('this');
			$('.settle b').text(clengthb);
			
		 }else{
		 	$('#check').removeClass('this');
			$('.settle b').text(clengthb);
		 }
	})

}


//二维码
function ask(){
	$('.ask').hover(function(){
		$(this).children('b').show();
	},function(){
		$(this).children('b').hide();
	})	
}


function deck(){
		// $('.pab ul li').children('i').remove();
		// $('.pab ul li').append('<i class="hide"></i>');
		$('.pab .zfblist li').delegate(this,'click',function(){
		var isc=$(this).hasClass('activer');
		if(isc==false)
		{
			 var src= $(this).children('img')[0].src;
			 var scc=$('.type img')[0].src=src;

			$(this).addClass('activer').siblings().removeClass('activer');
			$('.pab .zfblist li i').remove();
			$(this).append('<i></i>');

		}else{
			$('.pab .zfblist li').removeClass('activer');
			$(this).children('i').remove();
		}
	})

		
		$('.tablist li').click(function(){
			var index=$(this).index();
			$(this).addClass('active').siblings('li').removeClass('active');
			$('.pab ul:eq('+index+')').show().siblings().hide();
		});



}



//改变个数
function minsum(){
	$(".add").click(function(){ 
	var t=$('input[class*=text_box]'); 
	t.val(parseInt(t.val())+1) 
	}) 
	$(".min").click(function(){ 
	
	var t=$('input[class*=text_box]'); 
	t.val(parseInt(t.val())-1) 
	if(parseInt(t.val())<0){ 
	t.val(0); 
	} 
	
	}) 
	
	
}
	
//遮罩层
function showout(){
	$('.child-newlist li').hover(function(){
		$(this).children('.mask').stop().animate({
			top:0
		},500);	
	},function(){
		$(this).children('.mask').stop().animate({
			top:'1000px'
		},500);	
	})
}

//API切换
 function tabApi(){
   $('.apitab dd').click(function(){
		$(this).addClass('active').siblings('dd').removeClass();
		$(".listtab").eq($(this).index()-1).show().siblings().hide();	
	})	
   
  }