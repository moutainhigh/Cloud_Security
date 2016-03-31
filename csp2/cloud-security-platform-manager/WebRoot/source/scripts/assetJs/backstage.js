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
   
   if(tablList==1&&anList==0){
	    $("#assertName1").val(assertName1);
	    $("#serverId1").val(serverId1);
	    $("#begin_date1").val(begin_date1);
	    $("#end_date1").val(end_date1);
	    $("#alarmRank1").val(alarmRank1);
	    $("#orderCode1").val(orderCode1);	   
   }else if(tablList==1&&anList==1){
	    $("#assertName2").val(assertName1);
	    $("#serverId2").val(serverId1);
	    $("#begin_date2").val(begin_date1);
	    $("#end_date2").val(end_date1);
	    $("#alarmName1").val(alarmName1);
	    $("#orderCode2").val(orderCode1);  
   }else if(tablList==1&&anList==2){
		$("#assertName3").val(assertName1);
		$("#serverId3").val(serverId1);
		$("#begin_date3").val(begin_date1);
	   $("#end_date3").val(end_date1);
   }else if(tablList==0&&anList==0){
	   $("#assetType1").val(assetUserType); 
	    if(city!=''){
	       getCitys(prov);
	     }  
   }else if(tablList==0&&anList==1){
	   $("#assetType2").val(assetUserType);
	    $("#purpose1").val(purpose);  
   }else if(tablList==0&&anList==2){
	   $("#assetType3").val(assetUserType);
	   $("#purpose2").val(purpose);
	   $("#begin_date").val(begin_date1);
	   $("#end_date").val(end_date1);  
  }   
});

function assert(m,n){
	if(m==0&&n==0){
	  	if($("#assetType1").val()!=""||$("#prov").val()!=""||$("#city").val()!=""){
	  		$("#assetForm").action="${ctx}/adminDataAssetUI.html?tablList=0&anList=0"
	  		$("#assetForm").submit();
	  	}else{
	  		alert("至少输入一个查询条件！");
	  	} 
	}else if(m==0&&n==1){
	  	if($("#assetType2").val()!=""||$("#purpose1").val()!=""){
	  		$("#purposeForm").action="${ctx}/adminPurposeAssetUI.html?tablList=0&anList=1"
	  		$("#purposeForm").submit();
	  	}else{
	  		alert("至少输入一个查询条件！");
	  	} 
	}else if(m==0&&n==2){
	  	if($("#assetType3").val()!=""||$("#purpose2").val()!=""){
	  		var begin_date = $("#begin_date").val();
	  		var end_date = $("#end_date").val();
	  		if(begin_date!=null&&begin_date!=''&&end_date!=null&&end_date!=''){
		  		var beginDate=new Date(begin_date.replace("-", "/").replace("-", "/"));  
		  	    var endDate=new Date(end_date.replace("-", "/").replace("-", "/"));  
		  	    if(endDate<beginDate){  
		  	        alert("信息提示：统计结束时间不能小于统计开始时间！"); 
		  	        return;
		  	    }
	  		}
 
	  		$("#serverForm").action="${ctx}/adminPurposeAssetUI.html?tablList=0&anList=2"
	  		$("#serverForm").submit();
	  	}else{
	  		alert("在资产所属用户和资产用途中至少选择一个查询条件！");
	  	} 
	}else if(m==1&&n==0){
	  	if($("#serverId1").val()!=""||$("#alarmRank1").val()!=""){
	  		var begin_date = $("#begin_date1").val();
	  		var end_date = $("#end_date1").val();
	  		if(begin_date!=null&&begin_date!=''&&end_date!=null&&end_date!=''){
		  		var beginDate=new Date(begin_date.replace("-", "/").replace("-", "/"));  
		  	    var endDate=new Date(end_date.replace("-", "/").replace("-", "/"));  
		  	    if(endDate<beginDate){  
		  	        alert("信息提示：统计结束时间不能小于统计开始时间！"); 
		  	        return;
		  	    }
	  		}
	  		$("#alarmForm").action="${ctx}/admineAssetAlarmUI.html?tablList=1&anList=0"
	  		$("#alarmForm").submit();
	  	}else{
	  		alert("在服务类型和告警等级中至少选择一个查询条件！");
	  	} 
	}else if(m==1&&n==1){
	  	if($("#serverId2").val()!=""){
	  		var begin_date = $("#begin_date2").val();
	  		var end_date = $("#end_date2").val();
	  		if(begin_date!=null&&begin_date!=''&&end_date!=null&&end_date!=''){
		  		var beginDate=new Date(begin_date.replace("-", "/").replace("-", "/"));  
		  	    var endDate=new Date(end_date.replace("-", "/").replace("-", "/"));  
		  	    if(endDate<beginDate){  
		  	        alert("信息提示：统计结束时间不能小于统计开始时间！"); 
		  	        return;
		  	    }
	  		}
	  		$("#alarmForm1").action="${ctx}/admineAssetAlarmUI.html?tablList=1&anList=1"
	  		$("#alarmForm1").submit();
	  	}else{
	  		alert("服务类型为必选项！");
	  	} 
	}else if(m==1&&n==2){
	  	if($("#serverId3").val()!=""||$("#timeTtype").val()!=""){
	  		var begin_date = $("#begin_date3").val();
	  		var end_date = $("#end_date3").val();
	  		if(begin_date!=null&&begin_date!=''&&end_date!=null&&end_date!=''){
		  		var beginDate=new Date(begin_date.replace("-", "/").replace("-", "/"));  
		  	    var endDate=new Date(end_date.replace("-", "/").replace("-", "/"));  
		  	    if(endDate<beginDate){  
		  	        alert("信息提示：统计结束时间不能小于统计开始时间！"); 
		  	        return;
		  	    }
	  		}
	  		getAlarmTrend();
	  	}else{
	  		alert("在服务类型和时间类型中至少选择一个查询条件！");
	  	} 
	}
}

function getAlarmTrend(){
	 var assert=$("#assertName3").val();
	 var service=$("#serverId3").val();
	 var bdate=$("#begin_date3").val();
	 var edate=$("#end_date3").val();;
	 var timet=$("#timeTtype").val();
	 getAll('2012-06-24','2013-02-25');
	 $.ajax({
		 	data: {"assertName":assert,"serverId":service,"begin_date":bdate,"end_date":edate,"timeTtype":timet},
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "admineAssetAlarmTrendUI.html", 
	        success: function(obj){
	        	var list=obj.AlarmTrendlist;
	        	assertAlarm(list,timet);	
	        	
	     	}
		});
}

