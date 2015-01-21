$(function(){
	$(".dan_2").eq(1).hide();
	$(".hideEnddate").hide(); 
	var type = $("#type").val();
	if(type==1){
		$('input:radio[name="orderType"]:eq(0)').attr("disabled", true);
		$('input:radio[name="orderType"]:eq(1)').attr("checked",'checked');
		$(".dan_2").eq(1).show();
	}else if(type==2){
		$('input:radio[name="orderType"]:eq(1)').attr("disabled", true);
	}
	
	$('.dan_1 input').click(function (){
		var orderType=$('input:radio[name="orderType"]:checked').val();
		if(orderType==2){//如为单次订单，只有开始时间，不需要设置结束时间
    		$(".dan_2").eq(1).hide();
    		$(".hideEnddate").hide(); 
    	}else{
    		$(".dan_2").eq(1).show();
    		$(".hideEnddate").show(); 
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
           		getActive(1);
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
        		$("#beginDate_msg").html("");
        		$("#endDate_msg").html("");
        		getActive(1);
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
    	var servName=$('.peiz_active').attr("name");
    	var servRemark=$('.peiz_active input').val();
    	if($('#addTr1 input[name="assetId"]').length==0){
			alert("在资产列表中选择服务对象资产!");
			return;
		}
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
    	getActive(2);
    	
    	
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
    		getActive(3);
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
    	var orderId = MathRand();
    	var createDate = getCreateDate();
    	var orderType=$('input:radio[name="orderType"]:checked').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var scanType=$('input:radio[name="scanType"]:checked').val();
    	var serviceId=$('.peiz_active').attr("id");
    	var linkname=$('#linkname').val();
    	var phone=$('#phone').val();
    	var email=$('#email').val();
    	var company=$('#company').val();
    	var address=$('#address').val();
    	var obj = {'orderId':orderId,
    			   'orderType':orderType,
    			   'beginDate': beginDate,
    			   'endDate':endDate,
    			   'createDate':createDate,
    			   'scanType':scanType,
    			   'serviceId':serviceId,
    			   'linkname':linkname,
    			   'phone':phone,
    			   'email':email,
    			   'company':company,
    			   'address':address};
    	var result = window.confirm("确定要提交订单吗？");
    	if(result){
	    	$.post("/cloud-security-platform/saveOrder.html", obj, function(data){
	    		getActive(4);
	        });
    	}
    	
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
	
   $("#checkItems").click(function(){
	    var items = $('input:checkbox[name="serviceAssetId"]');
		for(var i=0;i<items.length;i++){
			var item = items[i];
			item.checked = this.checked;
		}	
	});
   
   //勾选服务对象到右侧
   $("#to_right").click(function(){
   		if($('input:checkbox[name="serviceAssetId"]:checked').length==0){
			alert("在资产列表中选择服务对象资产!");
			return;
		}
   		var $removeTr = $('input:checkbox[name="serviceAssetId"]:checked').parent().html('<a href="###" class="delete">X </a>');
   		$removeTr = $removeTr.parent().detach();
		$("#addTr1").append($removeTr);
   });
   
   //删除服务对象
   $(".delete").click(function(){
	    var _index = $(".delete").index(this);
	    alert(_index);
   		var $removeTr = $(this).parent().html("<input type='checkbox' name='serviceAssetId'/>");
   		$removeTr = $removeTr.parent().detach();
		$("#addTr").append($removeTr);
   });
   
   $('.pei_ul_1 li').click(function (){
	   var _index = $(".pei_ul_1 li").index(this);
	   if(_index==0){
		   $('.pei_ul_1 li').removeClass('pei_active');
		   $(this).addClass('pei_active');
	   }else{
		   $('.pei_ul_1 li').eq(0).removeClass('pei_active');
		   $(this).addClass('pei_active');
	   }
	   
	});
   
   $('.pei_ul_2 li').click(function (){
	   var _index = $(".pei_ul_2 li").index(this);
	   if(_index==0){
		   $('.pei_ul_2 li').removeClass('pei_active');
		   $(this).addClass('pei_active');
	   }else{
		   $('.pei_ul_2 li').eq(0).removeClass('pei_active');
		   $(this).addClass('pei_active');
	   }
	   
	});
   
   $('.pei_ul_3 li').click(function (){
	   var _index = $(".pei_ul_3 li").index(this);
	   if(_index==0){
		   $('.pei_ul_3 li').removeClass('pei_active');
		   $(this).addClass('pei_active');
	   }else{
		   $('.pei_ul_3 li').eq(0).removeClass('pei_active');
		   $(this).addClass('pei_active');
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
