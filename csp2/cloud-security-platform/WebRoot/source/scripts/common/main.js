

$(document).ready(function(){
	var num=$('.circle li').length;
	var i_mun=0;
	var timer_banner=null;

	$('.banner_img li:gt(0)').hide();//页面加载隐藏所有的li 除了第一个
	
//底下小图标点击切换
	$('.circle li').click(function(){
		$(this).addClass('active')
			   .siblings('li').removeClass('active');
		var i_mun1=$('.circle li').index(this);
		$('.banner_img li').eq(i_mun1).fadeIn('slow')
			                   .siblings('li').fadeOut('slow');

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















