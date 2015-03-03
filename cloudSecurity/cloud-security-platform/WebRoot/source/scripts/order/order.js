$(function(){
	var type=null;//服务类型
	var factory=null;//服务厂商
	var parentC=null//服务大类
	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
	$(".dan_2").eq(1).hide();//隐藏结束下拉框时间
	$(".hideEnddate").hide();//隐藏联系信息下的结束时间
	$(".scan").hide(); //隐藏联系信息下的扫描频率
	//服务类型type和服务serviceId是从首页获取的
	var otype = $("#type").val();
	var serviceId = $("#serviceId").val();
	if(otype==1){//若是WEB云安全服务,单次不可用,长期选择状态
		$('input:radio[name="orderType"]:eq(0)').attr("disabled", true);
		$('input:radio[name="orderType"]:eq(1)').attr("checked",'checked');
		$(".dan_2").eq(1).show();
	}else if(otype==2){//Anti-DDOS云安全服务,长期不可用
		$('input:radio[name="orderType"]:eq(1)').attr("disabled", true);
	}
	//根据serviceId,设置自助下单-服务配置中各服务显隐
	if(serviceId!=null && serviceId!=""){
		getServicePage(serviceId);
	}
	
	function getServicePage(serviceId){
		var index = serviceId - 1;
		$('.peiz_center ul li').removeClass('peiz_active');
		$('.peiz_center ul li').eq(serviceId-1).addClass('peiz_active');
		
		$('.peiz_cont').hide();
		$('.peiz_cont').eq(serviceId-1).show();
		$('.peiz_center ul li').addClass('opacity');
   		$('.peiz_center ul li').eq(serviceId-1).removeClass('opacity');
	}
	
	
	$('.dan_1 input').click(function (){
		var orderType=$('input:radio[name="orderType"]:checked').val();
		if(orderType==2){//如为单次订单，只有开始时间，不需要设置结束时间
    		$(".dan_2").eq(1).hide();
    		$(".hideEnddate").hide();
    		$(".scan").hide();
    		$("#endDate").val("");
    	}else{
    		$(".dan_2").eq(1).show();
    		$(".hideEnddate").show();
    		$(".scan").show();
    		$("#endDate").val("");
    	}
	});
	
	//订单类型点击"下一步"进入服务配置
    $("#firstStep").click(function(){
    	var orderType=$('input:radio[name="orderType"]:checked').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	if(orderType==2){
    		if(beginDate==""||beginDate==null){
        		$("#beginDate_msg").html("开始时间不能为空");
        	}else{
           		$("#beginDate_msg").html("");
           		if(indexPage==""||indexPage==null){
           			$('.peiz_center ul li').removeClass('opacity');
               		$('.peiz_center ul li').eq(2).addClass('opacity');
            		$('.peiz_center ul li').eq(4).addClass('opacity');
            		$('.peiz_center ul li').eq(5).addClass('opacity');
            		$('.peiz_center ul li').eq(6).addClass('opacity');
           		}
        		
        		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "/cloud-security-platform/getSession.html", 
	    		     dataType: "json", 
//	    		     contentType: "application/json; charset=utf-8", 
	    		     success: function(data) {
	    		    	 $('.pinv').hide();
	    		    	 getActive(1);
	    		    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "/cloud-security-platform/loginUI.html"; } 
	    		    	 else { window.location.href = "/cloud-security-platform/loginUI.html"; } } 
        		});
        	}
    	}else{
    		if(beginDate==""||beginDate==null||endDate==""||endDate==null){
        		if(beginDate==""||beginDate==null){
            		$("#beginDate_msg").html("开始时间不能为空");
            	}else{
               		$("#beginDate_msg").html("");
            	}
            	if(endDate==""||endDate==null){
            		$("#endDate_msg").html("结束时间不能为空");
            	}else{
               		$("#endDate_msg").html("");
            	}
        	}else{
        		if(beginDate>=endDate){
            		alert("开始时间不能大于结束时间!");
            		return;
            	}
        		$("#beginDate_msg").html("");
        		$("#endDate_msg").html("");
        		if(indexPage==""||indexPage==null){
        			$('.peiz_center ul li').removeClass('opacity');
            		$('.peiz_center ul li').eq(7).addClass('opacity');
        		}
        		$('.pinv').show();
        		$('input:radio[name="scanType"]:eq(0)').attr("checked",'checked');
        		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "/cloud-security-platform/getSession.html", 
	    		     dataType: "json", 
//	    		     contentType: "application/json; charset=utf-8", 
	    		     success: function(data) {
	    		    	 getActive(1);
	    		    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "/cloud-security-platform/loginUI.html"; } 
	    		    	 else { window.location.href = "/cloud-security-platform/loginUI.html"; } } 
       		});
        	}
    	}
    	
    	
    });
    
    //服务配置界面点击"上一步"返回到订单类型
    $("#firstGoback").click(function(){
    	getBackActive(0);
    });
    
    //服务配置点击"下一步"进入联系信息
    $("#twoStep").click(function(){
    	var orderType=$('input:radio[name="orderType"]:checked').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var scanType=$('input:radio[name="scanType"]:checked').val();
    	var serviceId=$('.peiz_active').attr("id");
    	var index = serviceId-1;
    	var servName=$('.peiz_active').attr("name");
    	var servRemark=$('.peiz_active input[name="remarks"]').val();
    	var ip=$('#ip'+index).val();
    	
    	var typeName=null;
    	if(orderType==2){
    		typeName="单次";
    	}else{
    		typeName="长期";
    	}
    	var scanName=null;
    	if(scanType==1){
    		scanName="每天0:0:10开始";
    	}else if(scanType==2){
    		scanName="每周一0:0:10开始";
    	}else if(scanType==3){
    		scanName="每月1日0:0:10开始";
    	}
    	$('td[name="orderName"]').html(typeName);
    	$('td[name="begin"]').html(beginDate);
    	$('td[name="end"]').html(endDate);
    	$('td[name="scanName"]').html(scanName);
    	$('td[name="servName"]').html(servName);
    	$('h3[name="servName"]').html(servName);
    	$('p[name="servRemark"]').html(servRemark);
    	$('img[name="servImg"]').attr("src","/cloud-security-platform/source/images/center_"+serviceId+".png");
    	if(serviceId!=6&&serviceId!=7&&serviceId!=8){
    		if($(".leftTr"+index+" input:checkbox[name='serviceAssetId']:checked").length==0){
    			alert("在资产列表中选择服务对象资产!");
    			return;
    		}
    	}
    	
   		var assetIds = "";
   		$(".leftTr"+index+" input:checkbox[name='serviceAssetId']:checked").each(function(){
   			assetIds = assetIds + $(this).val() + ",";
		});
   		if(serviceId==2 || serviceId ==4){
    		var scanDate=$('input[name="scanType'+index+'"]').val();
    	}else{
    		var scanType=$('input:radio[name="scanType'+index+'"]:checked').val();
    	}
		var obj = {'serviceId':serviceId,
				   'assetIds':assetIds,
				   'scanType':scanType,
				   'scanDate':scanDate,
				   'ip':ip};
    	$.post("/cloud-security-platform/checkOrderAsset.html", obj, function(data){
    		if(data.assetNames!=null){
    			$(".assets_msg").eq(index).html('资产('+data.assetNames+')针对该资产有同类任务，请重新设置!');
    		}else if(data.ipText){
    			alert("ip不可用");
    		}else{
    			$(".assets_msg").eq(index).html("");
    			$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "/cloud-security-platform/getSession.html", 
	    		     dataType: "json", 
//	    		     contentType: "application/json; charset=utf-8", 
	    		     success: function(data) {
	    		    	 getActive(2);
	    		    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "/cloud-security-platform/loginUI.html"; } 
	    		    	 else { window.location.href = "/cloud-security-platform/loginUI.html"; } } 
    			});
    		}
        });
    });
    
    //联系信息界面点击"上一步"返回到服务配置
    $("#twoGoback").click(function(){
    	$("#linkname_msg").html("");
		$("#phone_msg").html("");
		getBackActive(1);
    });
    
    //联系信息点击"下一步"进入确认订单
    $("#threeStep").click(function(){
    	var linkname=$('#linkname').val();
    	var phone=$('#phone').val();
    	var email=$('#email').val();
    	var company=$('#company').val();
    	var address=$('#address').val();
    	if(linkname==""||linkname==null||phone==""||phone==null){
    		if(linkname==""||linkname==null){
        		$("#linkname_msg").html("联系人不能为空");
        	}else{
           		$("#linkname_msg").html("");
        	}
        	if(phone==""||phone==null){
        		$("#phone_msg").html("电话不能为空");
        	}else{
           		$("#phone_msg").html("");
        	}
    	}else{
    		$("#linkname_msg").html("");
    		$("#phone_msg").html("");
    		$.ajax({ type: "POST",
   		     async: false, 
   		     url: "/cloud-security-platform/getSession.html", 
   		     dataType: "json", 
//   		     contentType: "application/json; charset=utf-8", 
   		     success: function(data) {
   		    	 getActive(3);
   		    	 }, 
   		     error: function(data){ 
   		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
   		    		 window.location.href = "/cloud-security-platform/loginUI.html"; } 
   		    	 else { window.location.href = "/cloud-security-platform/loginUI.html"; } } 
    		});
    	}
    	$('td[name="linkname"]').html(linkname);
    	$('td[name="phone"]').html(phone);
    	$('td[name="email"]').html(email);
    	$('td[name="company"]').html(company);
    	$('td[name="address"]').html(address);
    });
    
    //确认订单界面点击"上一步"返回到联系信息
    $("#threeGoback").click(function(){
    	getBackActive(2);
    });
    
    //确认订单界面点击"确认订单"进入完成
    $("#fourStep").click(function(){
    	//var orderId = MathRand();
    	var createDate = getCreateDate();
    	var orderType=$('input:radio[name="orderType"]:checked').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var serviceId=$('.peiz_active').attr("id");
    	var index = serviceId-1;
    	if(serviceId==2 || serviceId ==4){
    		var scanDate=$('input[name="scanType'+index+'"]').val();
    	}else{
    		var scanType=$('input:radio[name="scanType'+index+'"]:checked').val();
    	}
    	
    	var linkname=$('#linkname').val();
    	var phone=$('#phone').val();
    	var email=$('#email').val();
    	var company=$('#company').val();
    	var address=$('#address').val();
    	if(orderType==2){
    		scanType="";
    	}
    	//if(beginDate>createDate){
    		//获得服务资产
	    	var assetIds = "";
			$(".leftTr"+index+" input:checkbox[name='serviceAssetId']:checked").each(function(){
				assetIds = assetIds + $(this).val() + ",";
			});
			var ip=$('#ip'+index).val();
			var bandwidth=$('input:radio[name="bandwidth'+index+'"]:checked').val();
	    	var obj = {
	    			   'orderType':orderType,//'orderId':orderId,
	    			   'beginDate': beginDate,
	    			   'endDate':endDate,
	    			   'createDate':createDate,
	    			   'scanType':scanType,
	    			   'scanDate':scanDate,
	    			   'serviceId':serviceId,
	    			   'linkname':linkname,
	    			   'phone':phone,
	    			   'email':email,
	    			   'company':company,
	    			   'address':address,
	    			   'assetIds':assetIds,
	    			   'ip':ip,
	    			   'bandwidth':bandwidth};
	    	var result = window.confirm("确定要提交订单吗？");
	    	if(result){
//		    	$.post("/cloud-security-platform/saveOrder.html", obj, function(data){
//		    		$("#orderId").html(data.orderId);
//		    		getActive(4);
//		        });
		    	
		    	$.ajax({ type: "POST",
		    		     async: false, 
		    		     url: "/cloud-security-platform/saveOrder.html", 
		    		     data: {"orderType":orderType,//'orderId':orderId,
			    			   	"beginDate": beginDate,
			    			   	"endDate":endDate,
			    			   	"createDate":createDate,
			    			   	"scanType":scanType,
			    			   	"scanDate":scanDate,
			    			   	"serviceId":serviceId,
			    			   	"linkname":linkname,
			    			   	"phone":phone,
			    			   	"email":email,
			    			   	"company":company,
			    			   	"address":address,
			    			   	"assetIds":assetIds,
			    			   	"ip":ip,
			    			   	"bandwidth":bandwidth},  
		    		     dataType: "json", 
//		    		     contentType: "application/json; charset=utf-8", 
		    		     success: function(data) {
		    		    	 if(data.timeCompare == true){
		    		    		 $("#orderId").html(data.orderId);
					    		 getActive(4); 
		    		    	 }else{
		    		    		 alert("订单开始时间不能早于当前订单提交时间!");
		    		     		 return;
		    		    	 }
		    		    	 }, 
		    		     error: function(data){ 
		    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		    		 window.location.href = "/cloud-security-platform/loginUI.html"; } 
		    		    	 else { window.location.href = "/cloud-security-platform/loginUI.html"; } } 
		    	});
	    	}
//    	}else{
//    		alert("订单开始时间不能早于当前订单提交时间!");
//    		return;
//    	}
    });
    
   function getActive(index){
		$('.ding_nav ul li').removeClass('ding_active');
		$('.ding_nav ul li').eq(index).addClass('ding_active');
		$('.ding_nav ul li').eq(index).prevAll().addClass('ding_active_1');
		$('.ding_nav ul li').eq(index).addClass('ding_active');
		
		$('.ding_center').hide();
		$('.ding_center').eq(index).show();
	}
   
   function getBackActive(index){
		$('.ding_nav ul li').removeClass('ding_active');
		$('.ding_nav ul li').eq(index).addClass('ding_active');
		$('.ding_nav ul li').eq(index).removeClass('ding_active_1');
		$('.ding_nav ul li').eq(index).nextAll().removeClass('ding_active_1');
		$('.ding_nav ul li').eq(index).prevAll().addClass('ding_active_1');
		
		$('.ding_center').hide();
		$('.ding_center').eq(index).show();
	}
	
   $(".checkItems").click(function(){
	   	var serviceId=$('.peiz_active').attr("id");
	    var index = $(".checkItems").index(this); //获取当前点击按钮
	    var items = $('.leftTr'+index+' input:checkbox[name="serviceAssetId"]');
		for(var i=0;i<items.length;i++){
			var item = items[i];
			item.checked = this.checked;
		}	
	});
   
   //勾选服务对象到右侧
   $(".to_right").click(function(){
	    var serviceId=$('.peiz_active').attr("id");
	    var index = $(".to_right").index(this); //获取当前点击按钮
   		if($(".leftTr"+index+" input:checkbox[name='serviceAssetId']:checked").length==0){
			alert("在资产列表中选择服务对象资产!");
			return;
		}
   		var assetIds = "";
   		$(".leftTr"+index+" input:checkbox[name='serviceAssetId']:checked").each(function(){
   			assetIds = assetIds + $(this).parent().siblings('input').attr("value") + ",";
		});
   		if(serviceId==2 || serviceId ==4){
    		var scanDate=$('input[name="scanType'+index+'"]').val();
    	}else{
    		var scanType=$('input:radio[name="scanType'+index+'"]:checked').val();
    	}
		var obj = {'serviceId':serviceId,
				   'assetIds':assetIds,
				   'scanType':scanType,
				   'scanDate':scanDate};
    	$.post("/cloud-security-platform/checkOrderAsset.html", obj, function(data){
    		if(data.assetNames!=null){
    			$(".assets_msg").eq(index).html(data.assetNames + "不能用,请重新选择!");
    		}else{
    			$(".assets_msg").eq(index).html("");
    			var $removeTr = $(".leftTr"+index+" input:checkbox[name='serviceAssetId']:checked").parent().html('<a href="###" class="delete">X </a>');
    	   		$removeTr = $removeTr.parent().detach();
    			$(".rightTr"+index).append($removeTr);
    		}
        });
   });
   
   //删除服务对象
   $(".delete").live('click',function(){
	    var serviceId=$('.peiz_active').attr("id");
	    var index  = serviceId-1;
	    var _index = $(".delete").index(this);
   		var $removeTr = $(this).parent().html("<input type='checkbox' name='serviceAssetId'/>");
   		$removeTr = $removeTr.parent().detach();
		$(".leftTr"+index).append($removeTr);
   });
   
   $('.pei_ul_1 li').click(function (){
	   var orderType=$('input:radio[name="orderType"]:checked').val();
	   var _index = $(".pei_ul_1 li").index(this);
	   type = $(this).attr("name");
	   
	   if((_index==0 && orderType ==2)||(_index==0 && orderType ==1)){
		   $('.pei_ul_1 li').removeClass('pei_active');
		   $(this).addClass('pei_active');
		   type=null;
		   var obj = {'type':type,
				      'factory':factory,
				      'parentC':parentC,
				      'orderType':orderType};
		   $.post("/cloud-security-platform/select.html", obj, function(data){
			   if(data.servs!=null){
				   var temp = data.servs;
				   var res = temp.split(",");
				   $('.peiz_center ul li').addClass('opacity');
				   for(var n=0;n<res.length-1;n++){	
			   			$('.peiz_center ul li').eq(res[n]-1).removeClass('opacity');
				   }
			   }
		   });
	   }else{
//		   $('.pei_ul_1 li').eq(0).removeClass('pei_active');
		   $('.pei_ul_1 li').removeClass('pei_active');
		   $(this).addClass('pei_active');
		   var obj = {'type':type,
				      'factory':factory,
				      'parentC':parentC,
				      'orderType':orderType};
		   $.post("/cloud-security-platform/select.html", obj, function(data){
			   if(data.servs!=null){
				   var temp = data.servs;
				   var res = temp.split(",");
				   $('.peiz_center ul li').addClass('opacity');
				   for(var n=0;n<res.length-1;n++){	
			   			$('.peiz_center ul li').eq(res[n]-1).removeClass('opacity');
				   }
			   }
		   });
	   }
	   
	});
   
   $('.pei_ul_2 li').click(function (){
	   var orderType=$('input:radio[name="orderType"]:checked').val();
	   var _index = $(".pei_ul_2 li").index(this);
	   factory = $(this).attr("name");
	   if((_index==0 && orderType ==2)||(_index==0 && orderType ==1)){
		   $('.pei_ul_2 li').removeClass('pei_active');
		   $(this).addClass('pei_active');
		   factory=null;
		   var obj = {'type':type,
				      'factory':factory,
				      'parentC':parentC,
				      'orderType':orderType};
		   $.post("/cloud-security-platform/select.html", obj, function(data){
			   if(data.servs!=null){
				   var temp = data.servs;
				   var res = temp.split(",");
				   $('.peiz_center ul li').addClass('opacity');
				   for(var n=0;n<res.length-1;n++){	
			   			$('.peiz_center ul li').eq(res[n]-1).removeClass('opacity');
				   }
			   }
		   });
	   }else{
//		   $('.pei_ul_1 li').eq(0).removeClass('pei_active');
		   $('.pei_ul_2 li').removeClass('pei_active');
		   $(this).addClass('pei_active');
		   var obj = {'type':type,
				      'factory':factory,
				      'parentC':parentC,
				      'orderType':orderType};
		   $.post("/cloud-security-platform/select.html", obj, function(data){
			   if(data.servs!=null){
				   var temp = data.servs;
				   var res = temp.split(",");
				   $('.peiz_center ul li').addClass('opacity');
				   for(var n=0;n<res.length-1;n++){	
			   			$('.peiz_center ul li').eq(res[n]-1).removeClass('opacity');
				   }
			   }
		   });
	   }
	   
	});
   
   $('.pei_ul_3 li').click(function (){
	   var orderType=$('input:radio[name="orderType"]:checked').val();
	   var _index = $(".pei_ul_3 li").index(this);
	   parentC = $(this).attr("name");
	   var obj = {'type':type,
			      'factory':factory,
			      'parentC':parentC,
			      'orderType':orderType};
	   if((_index==0 && orderType ==2)||(_index==0 && orderType ==1)){
		   $('.pei_ul_3 li').removeClass('pei_active');
		   $(this).addClass('pei_active');
		   parentC=null;
		   var obj = {'type':type,
				      'factory':factory,
				      'parentC':parentC,
				      'orderType':orderType};
		   $.post("/cloud-security-platform/select.html", obj, function(data){
			   if(data.servs!=null){
				   var temp = data.servs;
				   var res = temp.split(",");
				   $('.peiz_center ul li').addClass('opacity');
				   for(var n=0;n<res.length-1;n++){	
			   			$('.peiz_center ul li').eq(res[n]-1).removeClass('opacity');
				   }
			   }
		   });
	   }else{
//		   $('.pei_ul_1 li').eq(0).removeClass('pei_active');
		   $('.pei_ul_3 li').removeClass('pei_active');
		   $(this).addClass('pei_active');
		   var obj = {'type':type,
				      'factory':factory,
				      'parentC':parentC,
				      'orderType':orderType};
		   $.post("/cloud-security-platform/select.html", obj, function(data){
			   if(data.servs!=null){
				   var temp = data.servs;
				   var res = temp.split(",");
				   $('.peiz_center ul li').addClass('opacity');
				   for(var n=0;n<res.length-1;n++){	
			   			$('.peiz_center ul li').eq(res[n]-1).removeClass('opacity');
				   }
			   }
		   });
	   }
	   
	});
   
   
   //随机数生成
   function MathRand(){ 
	   var Num=""; 
	   for(var i=0;i<10;i++){ 
		   Num+=Math.floor(Math.random()*10); 
	   } 
	   document.getElementById("orderId").innerText=Num; 
	   document.getElementById("orderId").innerHTML=Num; 
	   return Num;
   } 
   
   function getCreateDate(){
	   var now = new Date();
   	   var createDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
   	   document.getElementById("createDate").innerText=createDate; 
	   document.getElementById("createDate").innerHTML=createDate;
   	   return createDate;
   }
   
   

    
});
