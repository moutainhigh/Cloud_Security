$(function(){
	
    //选项卡
$('.tabList li').click(function(){
	var index=$(this).index();
	//alert("tabList"+index);
	$(this).addClass('active').siblings('li').removeClass('active');
	$('.tabCont .tabItem:eq('+index+')').show().siblings().hide();
});
	
	
	
$('.anlist li').click(function(){
	var index=$(this).index();
	//alert("anlist"+index);
	$(this).addClass('active').siblings('li').removeClass('active');
	
	$(this).parent('.anlist').siblings('.analyse_tabCont').children('.analyse_tabItem:eq('+index+')').show().siblings().hide();
	
});



$('.tableBox tbody tr:even').css('background','#fafafa')	

//select切换
$('.user_select').change(function(){
	var val=$(this).val();
	if(val==1)
	{
		$(this).parents('.user_form').siblings('.tableBox').find('.tableUsert').hide();
		$(this).parents('.user_form').siblings('.tableBox').find('.tableUser').show();	
		
	}
	else
	{
		$(this).parents('.user_form').siblings('.tableBox').find('.tableUsert').show();
		$(this).parents('.user_form').siblings('.tableBox').find('.tableUser').hide();		
	}
})

})

//添加省下拉框
function putOption(obj){
	var list = obj;
	for (var i = 0; i < list.length; i++){
		var tmp=list[i].id;
		if(prov!=''){
			if(tmp==prov){
			    $("#prov").append( "<option selected='selected' value="+list[i].id+">"+list[i].name+"</option>" );
			}else{
				$("#prov").append( "<option value="+list[i].id+">"+list[i].name+"</option>" );
			}
		}else{
			$("#prov").append( "<option value="+list[i].id+">"+list[i].name+"</option>" );
		}
		
	}
}

function getCitys(provId){
	//alert("ddddddddddddd");
	//查询省对应的市  
	 $.ajax({
		 	data: {"provId":provId},
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "getCityList.html", 
	        success: function(obj){
	        	putCityOption(obj);
	        	
	     	}
		});
}

//添加市下拉框
function putCityOption(obj){
	//清空城市下拉框
	$("#city").empty();
	var list = obj;
	if(list != null && list.length > 0){
		//alert(city);
		for (var i = 0; i < list.length; i++){
			var tmp=list[i].id;
            if(city!=''){
				if(tmp==city){
					$("#city").append( "<option selected='selected' value="+list[i].id+">"+list[i].name+"</option>" );	
				}else{
					$("#city").append( "<option value="+list[i].id+">"+list[i].name+"</option>" );	
				}	
			}else{
			   $("#city").append( "<option value="+list[i].id+">"+list[i].name+"</option>" );	
		    }		
		}
		$("#city").removeAttr("disabled");
	}
}

$(document).ready(function(){
	$('.tabList').children().eq(tablList).addClass('active').siblings('li').removeClass('active');
	$('.tabCont .tabItem:eq('+tablList+')').show().siblings().hide();
    $('.anlist:eq('+tablList+')').children().eq(anList).addClass('active').siblings('li').removeClass('active');
   // alert($('.analyse_tabCont:eq('+tablList+')').children().eq(anList).html());
    $('.analyse_tabCont:eq('+tablList+')').children().eq(anList).show().siblings().hide();
  //查询省
	 $.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "getDistrictListAll.html", 
	        success: function(obj){
	        	putOption(obj);
	     	}
		});
	
    $("#assetType1").val(assetUserType);
     if(city!=''){
       getCitys(prov);
     }
});
