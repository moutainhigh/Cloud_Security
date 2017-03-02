saveAssetFlag = 0;
//默认服务频率
var servType = 0;
$(function(){
	//修改时间控件选择的最小日期：服务器的当前时间
	$("#beginDate").onfocus=function(){
		var datetime = $("#begin").val();
		WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:datetime,startDate:datetime,alwaysUseStartDate:true,dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(dp){calPriceLong(null,null,null); }})
	}
	$("#endDate").onfocus=function(){
		var datetime = $("#begin").val();
		WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:datetime,startDate:datetime,alwaysUseStartDate:true,dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(dp){calPriceLong(null,null,null); }})
	}
	//默认选中资产数
	var assetCount = $('.httpBox li').length;

   		
	//生成默认价格
	calDefaultPrice();
	

    //选择网站点击效果
    $('#addhttp').click(function () {
    	//如果当前用户还没有资产，则进入新增资产页面
    	$.ajax({ type: "POST",
	     	async: false, 
	     	url: "getAssetList.html",
	     	data: {"wafFlag":"0"}, 
	     	dataType: "json", 
	     	success: function(data) {
		     	if(data.success){
		     	//如果当前用户没有资产，则进入新增资产页面
				var assCount = data.assList.length;
				if(assCount!='0'){
					 $('#sentwo').hide(1);
	                 $('#senone').show(1);
				}else{
					$('#senone').hide(1);
                    $('#sentwo').show(1);
				}
		     	}
	     	},
	     	error:function(data) {
	      		if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		 		window.location.href = "loginUI.html"; } 
	    	 	else { window.location.href = "loginUI.html"; } 
	     	}
	  });
        //显示遮罩层
        $('.shade').show();
        //显示
        $('.waf-detais-pop').animate({
            opacity: '1',
            top: '50%',
            left: '50%',
            marginTop: '-224px'
        }, 500);

        //资产被选中
        var arrlist = [];
    	$('.httpBox li').each(function(index, element) {
            arrlist.push($(this).attr("assetId"));
        });
        for(var i=0;i<$('.cek').length;i++){
            for(var j=0;j<arrlist.length;j++){
                if(arrlist[j]==$('.cek').eq(i).prev().val()){
					$('.cek').eq(i).addClass('this');
                }
            }
        }
        
        $('#number').text($('.httpBox li').length);
		
    })
    
    
	//点击添加资产的确定
	    //点击OK以后，插入数据
    $('.ok').click(function(){
		var th= $('.allBox .this').length;
		$('.httpBox li').remove();
		if(th==6){
			alert("超过5个");
			
		}else{
			assetCount = th;
			//点击ok之前先清空一次数组，再重新添加内容
			arrLink = [];	
			arrId = [];
			for(var i=0;i<$('.cek').length;i++){
				if($('.cek').eq(i).attr('class').indexOf('this')!=-1){
					//判断如果数组中没有，就插入，有的话 忽略
					arrLink.push($('.cek').eq(i).parent().siblings('b').html());
					arrId.push($('.cek').eq(i).siblings('input').val());
				}
			}
			
			$('.gt').hide();
			$('.httpBox').show();
			var list='';
			var index=0;
			for(var i=0;i<arrLink.length;i++){
				index++;
				 list+="<li id="+ index +" assetId='"+arrId[i]+"'>"+ arrLink[i] +"<i></i></li>";  
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
			
			var type = $(".click").val();
			if(type="1"){//长期
				calPriceLong(null,servType,assetCount);
			}else{
				calPrice(assetCount);
			}
		}
		
		
       
    })
        
	
    //确认订单界面点击"确认订单"进入完成
    $("#buyNow").click(function(){
    	var createDate = getCreateDate();
    	var type = $('.click').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var scanType = $('.clickTime').val();
    	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
    	var serviceId = $("#serviceIdHidden").val();
    	var times = $("#timesHidden").val();
    	if(type==2){
    		scanType="";
    	}

    	//获得服务资产
    	var assetIds = "";
    	$('.httpBox li').each(function(index, element) {
            assetIds = assetIds + $(this).attr("assetId") + ",";
        });
    	var ip="";
		var bandwidth="";
		if(type==2){
			if(beginDate==""||beginDate==null){
        		alert("开始时间不能为空");
        		return;
			}
		}else{
			if(beginDate==""||beginDate==null||endDate==""||endDate==null){
        		if(beginDate==""||beginDate==null){
            		alert("开始时间不能为空");
            		return;
            	}
            	if(endDate==""||endDate==null){
            		alert("结束时间不能为空");
            		return;
            	}
        	}else{
        		if(beginDate>=endDate){
            		alert("开始时间不能大于结束时间!");
            		return;
            	}
        	}
		}
		if(assetIds==""||assetIds==null){
			alert("请选择要服务的网站!");
    		return;
		}

		$.ajax({ type: "POST",
		     async: false, 
		     url: "getSession.html", 
		     dataType: "json", 
		     success: function(data) {
		     	//虚拟表单post提交
					    	var tempForm = document.createElement("form");
  							tempForm.action = "selfHelpOrderOpera.html";
  							tempForm.method = "post";
  							tempForm.style.display = "none";
  							
  							var typeInput = document.createElement("input");
  							typeInput.type="hidden"; 
							typeInput.name= "type"; 
							typeInput.value= type; 
							tempForm.appendChild(typeInput);
							
							var beginDateInput = document.createElement("input");
  							beginDateInput.type="hidden"; 
							beginDateInput.name= "beginDate"; 
							beginDateInput.value= beginDate; 
							tempForm.appendChild(beginDateInput); 
							
							var endDateInput = document.createElement("input");
  							endDateInput.type="hidden"; 
							endDateInput.name= "endDate"; 
							endDateInput.value= endDate; 
							tempForm.appendChild(endDateInput);
							
							var scanTypeInput = document.createElement("input");
  							scanTypeInput.type="hidden"; 
							scanTypeInput.name= "scanType"; 
							scanTypeInput.value= scanType; 
							tempForm.appendChild(scanTypeInput);
							
							var serviceIdInput = document.createElement("input");
  							serviceIdInput.type="hidden"; 
							serviceIdInput.name= "serviceId"; 
							serviceIdInput.value= serviceId; 
							tempForm.appendChild(serviceIdInput);
							
							var assetIdsInput = document.createElement("input");
  							assetIdsInput.type="hidden"; 
							assetIdsInput.name= "assetIds"; 
							assetIdsInput.value= assetIds; 
							tempForm.appendChild(assetIdsInput);
							
							var timesInput = document.createElement("input");
  							timesInput.type="hidden"; 
							timesInput.name= "buy_times"; 
							timesInput.value= times; 
							tempForm.appendChild(timesInput);
							
							
							
							document.body.appendChild(tempForm);
							tempForm.submit();
							document.body.removeChild(tempForm);
		    	 //window.location.href="settlement.html?type="+type+"&beginDate="+beginDate+"&endDate="+endDate+"&scanType="+scanType+"&serviceId="+serviceId+"&assetIds="+assetIds+"&buy_times="+times+"&price="+priceVal;
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

    });
    
    
    $("#settlement").click(function(){
    	var createDate = getCreateDate();
    	var orderType = $('#orderType').val();
    	var orderDetailId = $("#orderDetailId").val();
       var userName =  $(".test_name").text();
        var userAdd = $(".test_add").text();
        var mobile =  $(".test_iphone").text();
        var userId = $("#userIdHidden").val();
    	if(orderType==2){
    		scanType="";
    	}
    	//获得服务资产
    	var assetIds = $("#assetIds").val();
    
	
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "saveOrder.html", 
	    		     data: {"orderDetailId":orderDetailId,
    			            
		    			   	"createDate":createDate,
		    			   	"linkname":userName,
		    			   	"phone":mobile,
		    			   	"email":userAdd
			    			},  
	    		     dataType: "json", 
//		    		     contentType: "application/json; charset=utf-8", 
	    		     success: function(data) {
			    			if(data.error){
			    				alert("参数值数据异常!!");
			    				window.location.href = "index.html";
		    		     	    return;
			    			}else if(data.assetsStatus == true){
		    					alert("订单资产没有验证,请重新下单!");
		    		     	    return;
		    				}else if(data.timeCompare == false){
		    					alert("订单无效,请重新下单!");
		    		     		return;
		    				}else if(data.orderStatus == false){
		    					alert("订单有异常,请重新下单!");
		    		     	    return;
		    				}else{
			    		    	var orderListId = data.orderListId;
	    		    			//window.location.href = "cashierUI.html?orderListId="+orderListId;
	    		    			//虚拟表单post提交
						    	var tempForm = document.createElement("form");
	  							tempForm.action = "cashierUI.html";
	  							tempForm.method = "post";
	  							tempForm.style.display = "none";
  							
	  							var orderListIdInput = document.createElement("input");
	  							orderListIdInput.type="hidden"; 
								orderListIdInput.name= "orderListId"; 
								orderListIdInput.value= orderListId; 
								tempForm.appendChild(orderListIdInput);
							
								document.body.appendChild(tempForm);
								tempForm.submit();
								document.body.removeChild(tempForm);
		    				}
	    		    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "loginUI.html"; } 
	    		    	 else { window.location.href = "loginUI.html"; } } 
		    	});

    });
    
    
    $("#settlementAPI").click(function(){
    
    	//var time = $('#time').val();//次数
    	//var num = $('#num').val();//数量
    	//var type = $('#type').val();//套餐类型
    	 var orderDetailId = $('#orderDetailId').val();
    	 var userName =  $(".test_name").text();
        var userAdd = $(".test_add").text();
        var mobile =  $(".test_iphone").text();
      //  var userId = $("#userIdHidden").val();
		//var result = window.confirm("确定要提交订单吗？");
    	//if(result){
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "saveOrderAPI.html", 
	    		     data: {"orderDetailId":orderDetailId,
		    			    "linkname":userName,
		    			   	"phone":mobile,
		    			   	"email":userAdd},  
	    		     dataType: "json",
	    		     success: function(data) {
    			   		 if(data.error){
    			   			 alert("参数数据异常!");
    			   			 window.location.href = "index.html";
	    		     	     return;
    			   		 }else if(data.timeCompare == false){
		    					alert("订单无效,请重新下单!");
		    		     		return;
		    			}else if(data.message == true){
	    		    		 //alert("完成下单，去订单跟踪查看订单吧~~");  
    		    			 //window.location.href = "orderTrackInit.html";
    		    			 var orderListId = data.orderListId;
	    		    		 window.location.href = "cashierUI.html?orderListId="+orderListId;
				    	 }else{
				    		 alert(data.message);
				     		 return;
				    	 }
	    		    		 
	    		    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "loginUI.html"; } 
	    		    	 else { window.location.href = "loginUI.html"; } } 
		    	});
    	//}
    });
   
   
//点击“添加购物车”
    $("#addCar").click(function(){
    	var shopCarNum = Number($("#shopCarNum").html());
    	if (shopCarNum >= 99) {
    		alert("购物车中商品数量已达上限，请先清理购物车！");
    		return;
    	}
    	var createDate = getCreateDate();
    	var orderType = $('.click').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var scanType = $('.clickTime').val();
    	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
    	var serviceId = $("#serviceIdHidden").val();
    	var times = $("#timesHidden").val();
    	if(orderType==2){
    		scanType="";
    	}
    	//获得服务资产
    	var assetIds = "";
    	$('.httpBox li').each(function(index, element) {
            assetIds = assetIds + $(this).attr("assetId") + ",";
        });
    
    	var ip="";
		var bandwidth="";
		if(orderType==2){
			if(beginDate==""||beginDate==null){
        		alert("开始时间不能为空");
        		return;
			}
			
		}else{
			if(beginDate==""||beginDate==null||endDate==""||endDate==null){
        		if(beginDate==""||beginDate==null){
            		alert("开始时间不能为空");
            		return;
            	}
            	if(endDate==""||endDate==null){
            		alert("结束时间不能为空");
            		return;
            	}
        	}else{
        		if(beginDate>=endDate){
            		alert("开始时间不能大于结束时间!");
            		return;
            	}
        	}
		}
		if(assetIds==""||assetIds==null){
			alert("请选择要服务的网站!");
    		return;
		}

		$.ajax({ type: "POST",
		     async: false, 
		     url: "shoppingCar.html",
		     data: {"orderType":orderType,
    			   	"beginDate": beginDate,
    			   	"endDate":endDate,
    			   	"scanType":scanType,
    			   	"serviceId":serviceId,
    			   	"assetIds":assetIds,
    			   	"buy_times":times}, 
		     dataType: "json", 
		     success: function(data) {
    			   		if(data.error){
    			   			alert("参数值数据异常!");
    			   			window.location.href="index.html";
    			   		}
    			   		 if(data.sucess){
    			   			 alert("添加购物车成功!");
    			   			 buyService(serviceId);
    			   			// window.location.href="selfHelpOrderInit.html?serviceId="+serviceId+"&indexPage="+indexPage;
    			   		 }
    			   			
    			   	
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

    });
    


  //删除添加的-对应删除弹框的
    $('.httpBox').delegate('i','click',function(){
        //循环匹配内容 并清除掉
        for(var i=0;i<$('.cek').length;i++){
            $('.cek').eq(i).attr('class','cek');
        }
        
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
		$('#number').text($('.httpBox li').length);
		var tleng= $('.httpBox li').length;
		if(tleng==0){
			$('.gt').show();		
		}
    	
		assetCount = $('.httpBox li').length;
		var type = $(".click").val();
		if(type="1"){//长期
			calPriceLong(null,servType,assetCount);
		}else{
			calPrice(assetCount);
		}
    			
    	})


   function getCreateDate(){
	   var now = new Date();
   	   var createDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
   	   return createDate;
   }
   

});
//设置默认价格
function calDefaultPrice(){
	var serviceId = $("#serviceIdHidden").val();
	var type = $('.click').val();
    servType = $(".clickTime").val();
	if(typeof(serviceId) == "undefined"){
		return;
	}
	/*switch(parseInt(serviceId)){
	case 1://默认单次
	
		calPrice(null);
		break;
	case 2://默认单次
		
		calPrice(null);
		break;
	case 3://默认长期
	
		calPriceLong(null,servType,null);
		break;
	case 4://默认单次
		
		calPrice(null);
		break;
	case 5://默认长期
		
		calPriceLong(null,servType,null);
		break;
	}*/
	var orderType = $("#orderType").val();
	if(orderType==0 || orderType == 2){  //0:长期和单次 2：单次
		calPrice(null);
		
	} else if(orderType == 1) {   //1:长期
		calPriceLong(null,servType,null);
		
	}
	$("#timesHidden").val(1);
	
}
function tasknum_verification(){
		$.ajax({
	        type: "POST",
	        url: "verificateTaskNum.html",
	        data: {},
	        dataType:"json",
	        success: function(data){
	            if(data.msg){
	            	alert("个人用户累计下单不能超过20次，如欲继续下单，需升级为企业用户！");           	
	            }else{
	            	 window.location.href = "selfHelpOrderInit.html";
	            }
	        },
  		    error: function(data){ 
  		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
  		    		 window.location.href = "loginUI.html"; } 
  		    	 else { window.location.href = "loginUI.html"; } } 
	     });

}
  //跳转到购物车页面
   function showShopCar(){
		$.ajax({ type: "POST",
		     async: false, 
		     url: "getSession.html", 
		     dataType: "json", 
		     success: function(data) {
		    	 window.location.href="showShopCar.html";
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

		}
   //删除购物车
    function delShopCar(orderId){
    var result = window.confirm("确定要删除这个服务吗？");
    	if(result){
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "delShopCar.html", 
	    		     dataType: "json",
	    		     data:{"orderId":orderId},
	    		     success: function(data) {
	    		    	  window.location.href = "showShopCar.html";
				    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "loginUI.html"; } 
	    		    	 else { window.location.href = "loginUI.html"; } } 
		    	});
    	}
    }
    //返回修改订单信息
    function orderBack(){
    	$("#orderBackForm").submit();
    }
    
    
    //计算价格
    function calPrice(assetCount){//assetCount:资产数
    	var serviceId = $("#serviceIdHidden").val();
    	var assetCountNew = 0;
    	if(assetCount==null){
    		assetCountNew = $('.httpBox li').length;
    	}else{
        	assetCountNew = assetCount;
    	}
    	
    	if(assetCountNew==0){//如果资产不选，按单个资产算价格
    		assetCountNew = 1;
    	}
    	
    	$.ajax({ type: "POST",
   		     async: false, 
   		     url: "calPrice.html", 
   		     data:{"serviceId":serviceId,"assetCount":assetCountNew},
   		     dataType: "json",
   		     success: function(data) {
       			if(data.success){
     		    	  var price = data.price;
     		    	  $("#price").html(price);
     		    	  $("#timesHidden").val(data.times);
       			}

	    	 }, 
	    	 error: function(data){ 
	    		 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
	    	
    	});
	}
   
    //计算长期价格
    function calPriceLong(obj,typeDefault,assetCount){
    	var serviceId = $("#serviceIdHidden").val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	
    	var assetCountNew = 0;
    	if(assetCount==null){
    	   	var assetCountNew = $('.httpBox li').length;
    	}else{
        	assetCountNew = assetCount;
    	}
    	
    	if(assetCountNew==0){//如果资产不选，按单个资产算价格
    		assetCountNew = 1;
    	}

    	if(typeDefault!=null){
    		servType = typeDefault;
    	}else if(obj!=null){
    		servType = obj.value;
    	}else{
    		servType = $(".clickTime").val();
    		
    	}
    	//都不为空时，判断时间大小
    	if(beginDate!=""&&beginDate!=null&&endDate!=""&&endDate!=null){
    		if(beginDate>=endDate){
         		alert("开始时间不能大于结束时间!");
         		return;
         	}
		}
    	//判断服务频率是否是显示隐藏
    	if($("#singleBtn").attr("class").indexOf("click")>=0){
    		//单次
    		serviceType=2;
    	}else if($("#longBtn").attr("class").indexOf("click")>=0){
    		//长期
        	serviceType=1;
    	}
    	/*if($("#singleBtn").val() == "2"){
    		//单次
    		serviceType=2;
    	}
        if($("#longBtn").val() == "1"){
        	//长期
        	serviceType=1;
    	}*/
//    	alert($('.time').is(':hidden'));
//    	if($('.time').is(':hidden')==true){
//    	}else{	
//    	}
    
		$.ajax({ type: "POST",
	     async: false, 
	     url: "calPrice.html", 
	     data:{"serviceId":serviceId,"type":servType,"beginDate":beginDate,"endDate":endDate,"assetCount":assetCountNew,"orderType":serviceType},
	     dataType: "json",
	     success: function(data) {
   			if(data.success){
 		    	  var price = data.price;
 		    	  $("#price").html(price);
 		    	  $("#timesHidden").val(data.times);
   			}

	    	 }, 
	     error: function(data){ 
	    		 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } 
	    	} 
   	});

}
    
    function saveAsset() {
	    //防止重复提交
		if (saveAssetFlag == 1) {
			return;
		}
		saveAssetFlag = 1;
		
    	var assetName =$.trim($("#assetName").val());
    	var assetAddr = $.trim($("#InertAddr").val());
         var addrType = $('input:radio[name="addrType"]:checked').val();
         var purpose = $("#purpose").val();
         var prov = $("#districtId").val();
         var city = $("#city").val();
          var patrn=new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
	     var pattern = new RegExp("[`~!@#$^&*()=|{}';',<>?~！@#￥……&*（）——|{}【】‘；”“'。，？]"); 
	    
	     var newRegex = /^((?!([hH][tT][tT][pP][sS]?)\:*\/*)([\w\.\-]+(\:[\w\.\&%\$\-]+)*@)?((([^\s\(\)\<\>\\\"\.\[\]\,@;:]+)(\.[^\s\(\)\<\>\\\"\.\[\]\,@;:]+)*(\.[a-zA-Z]{2,4}))|((([01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}([01]?\d{1,2}|2[0-4]\d|25[0-5])))(\b\:(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|[1-5]\d{4}|[1-9]\d{0,3}|0)\b)?((\/[^\/][\w\.\,\?\'\\\/\+&%\$#\=~_\-@]*)*[^\.\,\?\"\'\(\)\[\]!;<>{}\s\x7F-\xFF])?)$/;
	     var strRegex = /^((([hH][tT][tT][pP][sS]?):\/\/)([\w\.\-]+(\:[\w\.\&%\$\-]+)*@)?((([^\s\(\)\<\>\\\"\.\[\]\,@;:]+)(\.[^\s\(\)\<\>\\\"\.\[\]\,@;:]+)*(\.[a-zA-Z]{2,4}))|((([01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}([01]?\d{1,2}|2[0-4]\d|25[0-5])))(\b\:(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|[1-5]\d{4}|[1-9]\d{0,3}|0)\b)?((\/[^\/][\w\.\,\?\'\\\/\+&%\$#\=~_\-@]*)*[^\.\,\?\"\'\(\)\[\]!;<>{}\s\x7F-\xFF])?)$/;
 	$("#assetName_msg").html("");
    	$("#assetAddr_msg").html("");
    	$("#location_msg").html("");
    	$("#assetUsage_msg").html("");
    	if(assetName == null || assetName == ""){
    		$("#assetName_msg").html("请输入网站名称!");
    		saveAssetFlag = 0;
    	}else if(patrn.test(assetName)){
    		$("#assetName_msg").html("请输入正确的网站名称!");
    		saveAssetFlag = 0;
    	}else if(assetAddr==null || assetAddr == ""){
    			$("#assetName_msg").html("");
    			$("#assetAddr_msg").html("请输入网站地址!");
    			saveAssetFlag = 0;
    	}else if(pattern.test(assetAddr)){
			$("#assetName_msg").html("");
			$("#assetAddr_msg").html("请输入正确的网站地址!");
			saveAssetFlag = 0;
	    }
    	else if((!strRegex.test(assetAddr) && !newRegex.test(assetAddr)) || (strRegex.test(assetAddr)&&assetAddr.indexOf('\/\/\/')!=-1)){
            $("#assetName_msg").html("");
            $("#assetAddr_msg").html("请输入正确的网站地址!");
            saveAssetFlag = 0;
    	}else if(prov == -1){
    		$("#assetName_msg").html("");
    		$("#assetAddr_msg").html("");
    		$("#location_msg").html("请选择网站所在物理地址!");
    		saveAssetFlag = 0;
    	}else if(purpose==-1){
    		$("#assetName_msg").html("");
    		$("#assetAddr_msg").html("");
    		$("#location_msg").html("");
    		$("#assetUsage_msg").html("请选择网站用途!");
    		saveAssetFlag = 0;
    	}else{
    			//验证资产是否重复
    			$.ajax({
    		        type: "POST",
    		        url: "asset_addrIsExist.html",
    		        data: {"addr":assetAddr,"name": encodeURI(assetName),"addrType":addrType},
    		        dataType:"json",
    		        success: function(data){
    		            if(data.msg=='1'){
    		            	$("#assetName_msg").html("网站名称重复!");
    		            	saveAssetFlag = 0;
    		            }else if(data.msg=='2'){
    		            	$("#assetName_msg").html("");
    		            	$("#assetAddr_msg").html("网站地址重复!");
    		            	saveAssetFlag = 0;
    		            }else{
    		            	$("#assetName_msg").html("");
    		            	$("#assetAddr_msg").html("");
    		            	//资产数验证
    		            	$.ajax({
    		    		        type: "POST",
    		    		        url: "asset_CountOver.html",
    		    		        data: {},
    		    		        dataType:"json",
    		    		        success: function(data){
    		    		            if(data.msg){
    		    		            	alert("免费用户管理资产数不能大于" + data.allowCount);
    		    		            	saveAssetFlag = 0;
    		    		            }else{
    		    			       			 $.ajax({
    		    			       		 			type:'POST',
    		    				 					url:'addWebSite.html',
    		    				 					data:{
    		    				  	               'assetName':assetName,
    		    				  	               'assetAddr':assetAddr,
    		    				  	               'purpose':purpose,
    		    				  	               'prov':prov,
    		    				  	               'city':city
    		    				 					},
    		    				 					//beforeSubmit:showRequest,
    		    				 					success: function(data) {
    		    				 						$("#assetName_msg").html("");
												    	$("#assetAddr_msg").html("");
												    	$("#location_msg").html("");
												    	$("#assetUsage_msg").html("");
    		    				 						switch(data.result) {
		    		            							case 0:
		    		            								//添加成功
		    		            								alert("添加成功!");
		    		            								var list = data.serviceAssetList;
	    		    				 							if(list!=null&&list.length>0){
	    		    				 								$(".allBox").empty();
	    		    				 								$.each(list,function(n,asset) {
	    		    				 									
	    		    				 									if (asset.status == '0') {
	    		    				 										var temp = "<li>"+
	    		    				 					            	 		"<div class='rcent'>"+
	    		    				 					            	 		"<h3>"+
	    		    				 			                                "<label for='"+(n+1)+"' style='margin:0 16px 0 0;'>"+
	    		    				 			                                     "<span style='padding-left:40px;padding-top:20px'>"+
	    		    				 			                                     	asset.name+
	    		    				 			                                     "</span>"+
	    		    				 			                                "</label>"+
	    		    				 			                            "</h3>"+
	    		    				 			                            "<div class='tBox'>"+asset.addr+
	    		    				 			                            	"<a href='userAssetsUI.html?type=1'  style='padding-left:20px;color:#2499fb;font-size:14px;'>未验证</a>"+
	    		    				 			                            "</div>"+
	    		    				 			                            "</div>"+
	    		    				 			                            "</li>";  
	    		    				 									}else {
	    		    				 										var temp = "<li>"+
	    		    				 					            	 		"<div class='rcent'>"+
	    		    				 					            	 		"<h3>"+
	    		    				 			                                "<label for='"+(n+1)+"' style='margin:0 16px 0 0;'>"+
	    		    				 			                                     "<input type='checkbox' class='ck'  value='"+asset.id+"' style='display:none;'><i class='cek' data-id='"+(n+1)+"' onclick='selAsset(this)'></i>"+
	    		    				 			                                "</label>"+
	    		    				 			                                 "<b>"+asset.name+"</b>"+
	    		    				 			                            
	    		    				 			                            "</h3>"+
	    		    				 			                            "<div class='tBox'>"+asset.addr+
	    		    				 			                            "</div>"+
	    		    				 			                            "</div>"+
	    		    				 			                            "</li>";  
	    		    				 									}
	    		    				 						         
	    		    				 						         		$(".allBox").append(temp);
	    		    				 						           });  
	    		    				 								} 
	    		    				 							$('#sentwo').fadeOut(20);
	    		    				 			                $('#senone').fadeIn(20);
		    		            								break;
		    		            							case 1:
		    		            								alert("免费用户管理资产数不能大于" + data.subResult);
		    		            								break;
		    		            							case 2:
					    		            					if (data.subResult == 1) {
					    		            						$("#assetName_msg").html("请输入网站名称!");
					    		            					}else if(data.subResult == 2) {
					    		            						$("#assetName_msg").html("请输入正确的网站名称!");
					    		            					}else if(data.subResult == 3) {
					    		            						$("#assetName_msg").html("网站名称重复!");
					    		            					}
					    		            					break;
					    		            				case 3:
					    		            					if (data.subResult == 1) {
					    		            						$("#assetAddr_msg").html("请输入网站地址!");
					    		            					} else if(data.subResult == 2) {
					    		            						$("#assetAddr_msg").html("请输入正确的网站地址!");
					    		            					}else if(data.subResult == 3) {
					    		            						$("#assetAddr_msg").html("网站地址重复!");
					    		            					}
					    		            					break;
					    		            				case 4:
					    		            					$("#location_msg").html("请选择网站所在物理地址!");
					    		            					break;
					    		            				case 5:
					    		            					$("#assetUsage_msg").html("请选择网站用途!");
					    		            					break;
					    		            				default:
					    		            					alert("添加失败!");
					    		            					break;
					    		            			}
					    		            			saveAssetFlag = 0;
    		    				 					},
    		    				 					error: function(data){
    		    				 						 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
    		    				 				    		 window.location.href = "loginUI.html"; } 
    		    				 				    	 else { window.location.href = "loginUI.html"; } 
    		    				 					}
    		    				 				});
    		    						 		 // 将options传给ajaxForm
    		    						 		 //$('#saveAsset').ajaxSubmit(options);
    		    		            }
    		    		        },
    		    		     }); 
    		            }
    		        },
    		     }); 
    		}
    	
    }
    
    function selAsset(Obj){
         if($(Obj).attr('class').indexOf('this')!=-1){
             $(Obj).removeClass('this');
				var legth = $('.allBox .this').length;
				//alert(legth)
				$('#number').text(legth);
				//$('.gt').show();	
         }
         else{
				 $(Obj).addClass('this');
				var legth = $('.allBox .this').length;
				if(legth==6){
					$(Obj).removeClass('this');
					alert("不能超过5个")
					$('#number').text('5');
				}else{
					$('#number').text(legth)	
				}
				
            
         }
    }
    
    function buyService(serviceId){

        $("#serviceIdNew").val(serviceId);
      	$("#selfHelpOrderInitNewForm").submit();
      }
      
      function buyAPI(apiId){
      	$("#apiIdNew").val(apiId);
      	$("#selfHelpOrderAPIInitNewForm").submit();
      
      }
