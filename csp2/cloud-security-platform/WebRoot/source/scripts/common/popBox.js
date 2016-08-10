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
		var id = $(this).attr("id");
		var name = $(this).attr("name");
		var addr = $(this).attr("addr");
		var districtId = $(this).attr("districtId");
		var type = $(this).attr("assetType");
		var temp = $(this).attr("city");
		var purpose = $(this).attr("purpose");
		$.post("checkedit.html", {"id" : id}, function(data, textStatus) {
			if (data.checkId == false || data.assetId != id) {
				window.location.href = "index.html";
			}else if (data.count>0){
				alert("您正在执行的订单中包含此资产，暂时不能修改！");
				return false;
			}else{
				$(".editMsg").html("");
				$("#hiddenEditName").val(name);
				$("#hiddenEditAddr").val(addr);
				$("#editAssetName").val(name);
				$("#editAssetAddr").val(addr);
				$("#editDistrictId").val(districtId);
				
				if (type==0) {
					$("input[name='editAssetType'][value='http']").attr("checked",true); 
				}else if(type==1){
					$("input[name='editAssetType'][value='https']").attr("checked",true);
				}
				
				getEditCitys(districtId);
				

				$("#editPurpose").val(purpose);
				$("#editAssetid").val(id);
				setTimeout(function(){
					$("#editCity").val(temp);
				},100);

				$('.shade').show();
				$('#updateAssest').show();
				$('html').css({overflow:"hidden"});
			}
		})
		

	})
	
	
	//关闭按钮
	
	$('.closed').click(function () {
			$('#assetName').val('');
			$('#assetAddr').val('');
			$('#districtId').val(-1);
			//清空城市下拉框
			$("#city").empty();
			$("#city").append( '<option value="-1">请选择城市</option>');
			$("#city").attr('disabled','disabled');
			$('#purpose').val(-1);
			$(".addMsg").html("");
			
            //$('.popBoxhide').hide();
            $(this).parents('.popBoxhide').hide();
            $('.shade').hide();
            $('html').css({
                overflow: 'auto'
            });
    })
    //微信
$('.weixin').delegate(this,'click',function(){
		$('.shade').show();
		$('#weixin').animate({
			top:'50%',
			opacity:'100',	
		},500);
		
		$('html').css({overflow:'hidden'});
	
})

	
//关闭按钮
	
	$('.chide').delegate(this,'click',function(){
		$(this).parents('.popBoxhide').animate({
			top:'-80%',
			opacity:'0',	
		},500);
		$('.shade').hide();
		$('html').css({overflow:'auto'})		
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
		
		$('#assetName').val('');
		$('#InertAddr').val('');
		$('#districtId').val(-1);
		//清空城市下拉框
		$("#city").empty();
		$("#city").append( '<option value="-1">请选择城市</option>');
		$("#city").attr('disabled','disabled');
		$('#purpose').val(-1);
		$(".addMsg").html("");
        
    })

});