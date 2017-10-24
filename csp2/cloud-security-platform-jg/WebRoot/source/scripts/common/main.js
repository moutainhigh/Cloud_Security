$(document).ready(function(){
		//菜单下拉效果	
	$('.listJs').hover(function(){
		$(this).children('a').find('i').addClass('active');
		$('.listl').slideDown();
	},function(){
		$(this).children('a').find('i').removeClass('active');
		$('.listl').stop(true,true).slideUp("slow");
	})

	$('.listl li').hover(function(){
		$(this).children('ol').show(400);
	},function(){
		$(this).children('ol').stop(true,true).hide(400);
	})
	
	//二维码
function ask(){
	$('.ask').hover(function(){
		$(this).children('b').show();
	},function(){
		$(this).children('b').hide();
	})	
}
	ask();
	
	
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
topshow();
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
topshow2();	

function topshow3(){
	$('.specialblock dd').hover(function(){
		//$(this).addClass('showdow');
		$(this).stop().animate({
			top:'-6px',
		},500)
	},function(){
		//$(this).removeClass('showdow');
		$(this).stop().animate({
			top:'0'
			},500)
		})

}
topshow3();
	
	
	
	var num=$('.circle li').length;
	var i_mun=0;
	var timer_banner=null;

	$('.banner_img li:gt(0)').hide();//页面加载隐藏所有的li 除了第一个
	
//底下小图标点击切换
	$('.circle li').click(function(){
		$(this).addClass('active').siblings('li').removeClass('active');
		var i_mun1=$('.circle li').index(this);
		$('.banner_img li').eq(i_mun1).fadeIn('slow').siblings('li').fadeOut('slow');
		i_mun=i_mun1;
	});
	
	
	//自动播放函数
	function bannerMoveks(){
		timer_banner=setInterval(function(){
			move_banner()
		},5000)
	};
	bannerMoveks();//开始自动播放

	//鼠标移动到banner上时停止播放
	$('.vBox').mouseover(function(){
		clearInterval(timer_banner);
	});

	//鼠标离开 banner 开启定时播放
	$('.vBox').mouseout(function(){
		bannerMoveks();
	});


//banner 右边点击执行函数
   function move_banner(){
			if(i_mun==num-1){
				i_mun=-1
			}
			//大图切换
			$('.banner_img li').eq(i_mun+1).fadeIn('slow')
									   .siblings('li').fadeOut('slow');
			//小图切换
			$('.circle li').eq(i_mun+1).addClass('active')
					   .siblings('li').removeClass('active');

			i_mun++
		
		}

})

function buySelfHelpOrderMain(serviceId){
	$("#serviceIdHidden").val(serviceId);	
	if(serviceId != 6){
		$("#selfHelpOrderInitForm").submit();
	}else{
		$("#wafDetailsForm").submit();
	}
	//$("#mainForm").submit();	
}
function buySelfHelpOrderAPIMain(apiId){
	$("#apiIdHidden").val(apiId);
	$("#APIMainForm").submit();
}	
function buySelfHelpOrderSysMain(serviceId){
	//$("#sysIdHidden").val(serviceId);
	$("#sysIdHidden").val(serviceId);
	$("#SysMainForm").submit();
}	















