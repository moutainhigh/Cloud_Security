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
	
	$('.closed').click(function () {
            $('.popBoxhide').hide();
            $('.shade').hide();
            $('html').css({
                overflow: 'auto'
            })
    })
})
var arrLink = [];
//数组 - 存放已选择的网站
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

});










