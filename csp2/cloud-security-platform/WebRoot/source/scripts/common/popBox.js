// JavaScript Document
$(function(){
	 //增加指标
        $('.addass').delegate(this, 'click', function () {
            $('.shade').show();
           $('#revise').show();
       })


	
	//关闭按钮
	
	$('.closed').click(function () {
            $('.popBoxhide').hide();
            $('.shade').hide();
            $('html').css({
                overflow: 'auto'
            })
    })

})

//数组 - 存放已选择的网站
var arrLink = [];
$(document).ready(function () {
		//扩展数组原型，添加del方法。删除数组元素，返回新的数组
	Array.prototype.del=function(index){
	    if(isNaN(index)||index>=this.length){
	        return false;
	    }
	    for(var i=0,n=0;i<this.length;i++){
	        if(this[i]!=this[index]){
	            this[n++]=this[i];
	        }
	    }
	    this.length-=1;
	};
    //选择网站点击效果
    $('#addhttp').click(function () {
		
        //显示遮罩层
        $('.shade').show();
        //显示
        $('.waf-detais-pop').animate({
            opacity: '1',
            top: '50%',
            left: '50%',
            marginTop: '-224px'
        }, 500);
        //alert(arrLink)
        //关闭后清掉所有内容
        for(var i=0;i<$('.cek').length;i++){
            for(var j=0;j<arrLink.length;j++){
                if(arrLink[j]==$('.cek').eq(i).parent().siblings('b').html()){
					$('.cek').eq(i).addClass('this');
                }
            }
        }
		
    })
     //关闭按钮
    $('#close').click(function () {
        //关闭后效果
        $('.waf-detais-pop').animate({
            opacity: '1',
            top: '50%',
            left: '50%',
            marginTop: '-1200px'
        }, 500);
        //隐藏遮罩层
        $('.shade').hide();
        //循环匹配内容 并清除掉
        for(var i=0;i<$('.cek').length;i++){
            $('.cek').eq(i).attr('class','cek');
        }
		var th= $('.httpBox li').length;
		if(th==0){
			$('.gt').show();		
			
		}else{
			$('.gt').hide();	
		}
		
		
        
    })
    //选择
    for(var i=0;i<$('.cek').length;i++){
//        $('.cek').attr('check','false');
        $('.cek').eq(i).click(function(){
            if($(this).attr('class').indexOf('this')!=-1){
                $(this).removeClass('this');
				var legth = $('.allBox .this').length;
				//alert(legth)
				$('#number').text(legth);
				//$('.gt').show();	
            }
            else{
				 $(this).addClass('this');
				var legth = $('.allBox .this').length;
				if(legth==6){
					$(this).removeClass('this');
					alert("不能超过5个")
					$('#number').text('5');
				}else{
					$('#number').text(legth)	
				}
				
               
            }
        })
    }
    //点击OK以后，插入数据
    $('.ok').click(function(){

		var th= $('.allBox .this').length;
		$('.httpBox li').remove();
		if(th==6){
			alert("超过5个");
			
		}else{
			//点击ok之前先清空一次数组，再重新添加内容
			arrLink = [];	
			for(var i=0;i<$('.cek').length;i++){
				if($('.cek').eq(i).attr('class').indexOf('this')!=-1){
					//判断如果数组中没有，就插入，有的话 忽略
					arrLink.push($('.cek').eq(i).parent().siblings('b').html());	
				}
			}
			
			$('.gt').hide();
			$('.httpBox').show();
			var list='';
			for(var i=0;i<arrLink.length;i++){
				
				 list+='<li data='+ arrLink[i] +'>'+ arrLink[i] +'<i></i></li>';  
			}
			
			$('.httpBox').append(list);
			//alert(arrLink);
			
			var tleng= $('.httpBox li').length;
			if(tleng==0){
				$('.gt').show();		
			}
			
			
			 //关闭后效果
			$('.waf-detais-pop').animate({
				opacity: '1',
				top: '50%',
				left: '50%',
				marginTop: '-1200px'
			}, 500);
			//隐藏遮罩层
			$('.shade').hide();
		}
       
    })
//删除添加的-对应删除弹框的
$('.httpBox').delegate('i','click',function(){

    var _this=$(this).parent('li').attr('data');

           	$('.allBox b').each(function(index, element) {
                var ac= $(this).text();

				if(ac==_this){
					
					$(this).parent('h3').find('i').removeClass('this');

					for(i=0;i<arrLink.length;i++){
						if(arrLink[i]==_this){
							arrLink.del(i)	
						}
					}
				}
            });
            $(this) .parent('li').remove();
			var tleng= $('.httpBox li').length;
			if(tleng==0){
				$('.gt').show();		
			}

		})

});
