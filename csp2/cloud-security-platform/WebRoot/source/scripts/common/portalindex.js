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
})*/

//高级搜索切换
$('.words').click(function(){
		var _this=$(this).children('em').hasClass('add');
		if(_this==true)
		{	
			$(this).find('.nitial').removeClass('initial')
			$(this).children('em').removeClass('add');
			$(this).children('em').text('高级筛选条件');
			$('.coreshow').hide();	
			//---------Add by zsh 2016-08-15 start 
			//高级筛选条件选择API的场合,关闭高级筛选条件时，资产名称 disabled=false
			var servNameValue = $("#servName").val();
			if(servNameValue.slice(-1)=="1"){
				$("#search").attr('placeholder','输入资产名称或者资产地址进行搜索');
			}
			$("#search").attr("disabled",false);
			$("#type").attr("disabled",false);
			//-------------Add by zsh 2016-08-15 end
			$("#type").val("");
		    $("#servName").val("");
		    $("#begin_date").val("");
		    $("#end_date").val("");
			
		}else{
			$(this).find('.nitial').addClass('initial');
			$(this).children('em').addClass('add');
			$(this).children('em').text('精简筛选条件');
			
			$('.coreshow').show();	
		}
		
		
	})	
//列表悬停效果
topshow();
topshow2();
//修改发票
tab();
//改变个数
//minsum();

//遮罩层
showout();
//二维码
ask();

onclick('clickBox');
onclickTime('time');
//复选框
chck();


		//按钮切换显示	
		$('.Single').click(function(){
			$('.start').show();
			$('.end').hide();
			$('.time').hide();	
			$("#endDate").val("");
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
			$('.end').show();
			$('.long').addClass('click').siblings().removeClass('click');	
		}
		
		
//选项卡切换
	tablist();
//我的余额效果
	balance();
})
///这里是结尾




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
	if(t.val()==''){
		alert("请输入购买数量!");
		return;
	}
	t.val(parseInt(t.val())+1) 
	}) 
	$(".min").click(function(){ 
	
	var t=$('input[class*=text_box]'); 
	if(t.val()==''){
		alert("请输入购买数量!");
		return;
	}

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
  
  //复选框
 function chck(){
	$('.password label').click(function(){
		var is=$(this).children('em').hasClass('right');
		if(is==false)
		{
			$(this).children('em').addClass('right');
			
		}else{
			$(this).children('em').removeClass('right');
		}	
	})
		 
} 


//选项卡
function tablist(){
	$('.navlist li:last').css('background-image','none');
	$('.navlist li').click(function(){
		var zindex=$(this).index();
		
		$('.navlist li').removeClass('none');
		$(this).addClass('this');
		$(this).prev('li').addClass('none');
		$(this).addClass('active').siblings('li').removeClass('active');
		$(".tabBox .not-used").eq($(this).index()).show().siblings().hide();		
	})
	
}

//购物车效果
function sck(){
	//单个选择
	$('.chck').delegate(this,'click',function(){
		//计算价格方法
		function getTotal() {
		var seleted = 0;
		price = 0;
		var HTMLstr = '';
		for (var i = 0, len = tr.length; i < len; i++) {
				price += parseFloat(tr[i].innerHTML);		
		}	
		priceTotal.innerHTML = price.toFixed(2);
		
	}
		var is=$(this).hasClass('this');
		var clength=$('.count');
		var lenth=$('.chck');
		var clengthb=clength.length;
		var lents=lenth.length;
		
		if(is==false)
		{
			$('.settle b').text();
			$(this).addClass('this');
			$(this).addClass('count');
			var alllength=$('.count').length;
			var s=$('.settle').find('b').text();;
			var num2 = parseFloat(alllength);
			var total =num2;
			$('.settle').find('b').text(total);
			
			//获取当前表格的个数
			var allth=$(this).parents('.tabox').children('.test-table').find('.count').length;
			var tablenth=$(this).parents('.tabox').children('.test-table').length;
			if(tablenth==allth){
				$(this).parents('.tabox').find('.check-all').addClass('this');
			}
			//计算价格
			$(this).parents('tr').find('.price').addClass('ps');
			 var tr =$('.ps');
			 var price;
			var priceTotal = document.getElementById('priceTotal');
			//计算方法调用
			 getTotal(); 
			 
			 var sum=Number(price).toFixed(2);
			 var zsh=$('#priceTotal').text(sum);
	
		}else{
			$(this).removeClass('count');
			$(this).removeClass('this');
			//单个取消订单个数更新;
			var et=$('.count').length;
			$('.settle b').text(et);
			//获取当前表格的个数
			var allth=$(this).parents('.tabox').children('.test-table').find('.count').length;
			var tablenth=$(this).parents('.tabox').children('.test-table').length;
			if(tablenth > allth){
				$(this).parents('.tabox').find('.check-all').removeClass('this');
			}
			//计算价格
			$(this).parents('tr').find('.price').removeClass('ps');
			 var tr =$('.ps');
			 var price;
			var priceTotal = document.getElementById('priceTotal');
			 //计算方法调用
			 getTotal(); 
			 var sum=Number(price).toFixed(2);
			 var zsh=$('#priceTotal').text(sum);
			
		}
		
		
	})
	//全选算价格
	$('.check-all').click(function(){
		//计算价格方法
		var is=$(this).hasClass('this');
		var isOk=$(this).parents('.tabox').find('.test-table tr');
		if(is==false)
		{
			
			for (var i = 0; i < isOk.length ; i++) {
				if($(isOk[i]).find('i.chck').length>0){
					$(isOk[i]).find('.price').addClass('ps');
				}
			}
			 var tr =$('.ps');
			 var price;
			var priceTotal = document.getElementById('priceTotal')
			
			//计算方法调用
			 getTotal(); 
			 var sum=Number(price).toFixed(2);
			 var zsh=$('#priceTotal').text(sum);
			 
			 
			var spt=$(this).parents('tr').find('.price').text();
			var cent=$('#priceTotal').text();
			$(this).parents('.tabox').find('.chck').addClass('count');
			var total=$('.count').length;
			
			$(this).parents('.tabox').find('.ck').prop("checked",true);
			$(this).addClass('this');
			$(this).parents('.tabox').find('.chck').addClass('this');
			$('.settle').find('b').text(total);
			 
		}else{
			
			var pri= $(this).parents('.tabox').find('.ps').addClass('ps2').removeClass('ps');
			var zsh=$('#priceTotal').text();
			var tab=$(this).parents('.tabox').children('.test-table')
			 var tr = tab.find('.ps2');
			 var price;
			var priceTotal = document.getElementById('priceTotal')
			
			//计算方法调用
			 getTotal(); 
			 var zsb=(Number(zsh).toFixed(2));
			 /*正在点击的和*/
			 var ing=(Number(price).toFixed(2));
			 var nu=Number(ing).toFixed(2);
			var sum=(Number(zsb)-Number(nu)).toFixed(2);
			 var zsh=$('#priceTotal').text(sum);
			$(this).parents('.tabox').find('.chck').removeClass('count');
			var total=$('.count').length;
			$(this).parents('.tabox').find('.ck').prop("checked",true);
			$(this).parents('.tabox').find('.chck').removeClass('this');
			$(this).removeClass('this');
			$('.settle').find('b').text(total);
			$(this).parents('.tabox').find('.ck').attr('checked',false);
			
		}
		function getTotal() {
			var seleted = 0;
			price = 0;
			var HTMLstr = '';
			
			for (var i = 0, len = tr.length; i < len; i++) {
			
					price += parseFloat(tr[i].innerHTML);
					
			}	
		
		priceTotal.innerHTML = price.toFixed(2);
		
		}
		
	})

}

//我的余额效果
function balance(){
	$('#gz').click(function(){
		$('.rule').slideDown();	
	})	
	
}
//设置域名解析
function domainNameUI(orderId){
//虚拟表单post提交
	var tempForm = document.createElement("form");
  	tempForm.action = "domainNameUI.html";
  	tempForm.method = "post";
  	tempForm.style.display = "none";
  							
  	var orderIdInput = document.createElement("input");
  	orderIdInput.type="hidden"; 
	orderIdInput.name= "orderId"; 
	orderIdInput.value= orderId; 
	tempForm.appendChild(orderIdInput);
							
	document.body.appendChild(tempForm);
	tempForm.submit();
	document.body.removeChild(tempForm);

}
