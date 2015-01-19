$(function(){
	$(".dan_2").eq(1).hide();
	$(".hideEnddate").hide(); 
	
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
    	$('td[name="address"]').html(address);
    });
    
    //确认订单界面点击"上一步"返回到联系信息
    $("#threeGoback").click(function(){
    	getBackActive(2);
    });
    
    //确认订单界面点击"确认订单"进入完成
    $("#fourStep").click(function(){
    	var orderType=$('input:radio[name="orderType"]:checked').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var scanType=$('input:radio[name="scanType"]:checked').val();
    	var serviceId=$('#addTr1[name="assetId"]').val();
    	var linkname=$('#linkname').val();
    	var phone=$('#phone').val();
    	var email=$('#email').val();
    	var company=$('#company').val();
    	var address=$('#address').val();
    	var obj = {'orderType':orderType,
    			   'beginDate': beginDate,
    			   'endDate':endDate,
    			   'scanType':scanType,
    			   'serviceId':serviceId};
    	$.post("/cloud-security-platform/saveOrder.html", obj, function(data){
    		if(isSuccess){
    			
    		}
        });
    	getActive(4);
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
			alert("选择要服务对象!");
			return;
		}
   		var $removeTr = $('input:checkbox[name="serviceAssetId"]:checked').parent().html('<a href="###" id="delete">X </a>');
   		$removeTr = $removeTr.parent().detach();
		$("#addTr1").append($removeTr);
   });
   
   //删除服务对象
   $("#delete").click(function(){
	    alert("11");
   		var $removeTr = $(this).parent().html("<input type='checkbox' name='serviceAssetId'/>");
   		$removeTr = $removeTr.parent().detach();
		$("#addTr").append($removeTr);
   });
    
});
