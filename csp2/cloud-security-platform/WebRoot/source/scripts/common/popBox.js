// JavaScript Document
$(function(){
	//增加资产
	$('.addass').delegate(this,'click',function(){	
		$(".addMsg").html("");
		$('.shade').show();
		$('#revise').show();
		$('html').css({overflow:"hidden"});
	})
	
	//修改资产
	$('.zc_edit').delegate(this,'click',function(){
		$(".editMsg").html("");
		$("#hiddenEditName").val($(this).attr("name"));
		$("#hiddenEditAddr").val($(this).attr("addr"));
		$("#editAssetName").val($(this).attr("name"));
		$("#editAssetAddr").val($(this).attr("addr"));
		$("#editDistrictId").val($(this).attr("districtId"));
		getEditCitys($(this).attr("districtId"));
		var temp = $(this).attr("city");

		$("#editPurpose").val($(this).attr("purpose"));
		$("#editAssetid").val($(this).attr("id"));
		setTimeout(function(){
			$("#editCity").val(temp);
		},100);

		$('.shade').show();
		$('#updateAssest').show();
		$('html').css({overflow:"hidden"});
	})
	
	//关闭按钮
	
	$('.closed').delegate(this,'click',function(){
		$('.popBoxhide').hide();
		$('.shade').hide();
		$('html').css({overflow:'auto'})		
	})

	
//微信
$('.weixin').delegate(this,'click',function(){
		$('.shade').show();
		$('#weixin').animate({
			top:'50%',
			opacity:'100',	
		},500)
		$('html').css({overflow:'hidden'})
	
})

//waf
$('#addhttp').bind('click',function(){
	
		$('.shade').show();
		$('.waf-detais-pop').animate({
			opacity:'100',
			top:'50%',
			left:'50%'	
		},500)
		$('html').css({overflow:'hidden'})
	
})
//waf选中效果
$('.rcent .radio').click(function(){
	$('.waf-detais-pop ul li').removeClass('this');
	$(this).parents('li').addClass('this');
		
})
//删除
function del(){
	$('.httpBox').delegate('i','click',function(){
			alert("已经删除")
			$(this)	.parent('li').remove();
	})	
	
}

del();


//漏洞监测 选中动作
//计算列表个数
function nu(){
		var n=$('.n').length; 
		$('#number').text(n);	
	
};

//漏洞-选中-单选动作
$('.rcent').delegate('.cek','click',function(){
	var is=$(this).hasClass('this');
	if(is==false){
		$(this).addClass('this');	
		$(this).parents('li').addClass('ac');
		var num=$(this).addClass('n');
		 nu();
		 var lingth= $('.allBox li').length;
		 var n=$('.n').length; 
		 if(n==lingth){
			$('.cheall').addClass('this');	 
		}
		
	}else{
		$(this).removeClass('this');	
		$(this).removeClass('n');
		nu();
		$(this).parents('li').removeClass('ac');
		var lingth= $('.allBox li').length;
		 var n=$('.n').length; 
		 if(n!=lingth){
			$('.cheall').removeClass('this');	 
		}
		
	}
	
	
})

$('.cheall').click(function(){	
	var is=$(this).hasClass('this');
	if(is==false){
		$(this).addClass('this');	
		$('.allBox li').addClass('ac');
		$('.allBox .cek').addClass('this');
		$('.allBox input:checkbox').prop('checked',true);
		var num=$('.allBox .cek').addClass('n');
		nu()
		
	}else{
		$(this).removeClass('this');
		$('.allBox li').removeClass('ac');
		$('.allBox .cek').removeClass('this');
		$('.allBox input:checkbox').prop('checked',false);
		$('.allBox .cek').removeClass('n');
		nu();
		$(this).parents('li').removeClass('ac');
	}
})

$('#ass_http').bind('click',function(){
		$('.shade').show();
		$('.add_httpPop').fadeIn(300);
		$('html').css({overflow:'hidden'})
})
//关闭，确定，还原相应的内容
function offcent(){
	$('.allBox .cek').removeClass('this');
		$('html').css({overflow:'auto'});
		$('.allBox input:checkbox').prop('checked',false);	
		$('.allBox li').removeClass('ac');
		$('.bl input:checkbox').prop('checked',false);
		$('#number').text('0');	
		$('.allBox .cek').removeClass('n');
		nu();
		$('.cheall').removeClass('this');	
	
}


$('.ok').delegate(this,'click',function(){
		$(this).parents('.popBoxhide').animate({
			top:'-80%',
			opacity:'0',	
		},500);
		$('.shade').hide();
		
		
		offcent();
		
	})

	
//关闭按钮
	
	$('.chide').delegate(this,'click',function(){
		$(this).parents('.popBoxhide').animate({
			top:'-80%',
			opacity:'0',	
		},500);
		$('.shade').hide();
		$('html').css({overflow:'auto'});
		offcent();	
	})
		
	
})

function hide(){
	$('.add_httpPop').fadeOut(300); 
}


