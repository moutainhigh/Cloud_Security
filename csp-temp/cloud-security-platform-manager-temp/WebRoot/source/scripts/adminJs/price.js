$(function(){
	$('.addtd').click(function(){
		
			$(this).parents('#td0').append($("#td").clone(true));
			
	});
	$('.deltd').click(function(){

		$(this).parents('#td').remove();	
	});
	
	$("#price").keyup(function(){      
        $(this).val($(this).val().replace(/[^0-9.]/g,''));      
    }).bind("paste",function(){  //CTR+V事件处理      
        $(this).val($(this).val().replace(/[^0-9.]/g,''));       
    }).css("ime-mode", "disabled"); //CSS设置输入法不可用        
	
/*	$('#add_price').click(function(){
		$.ajax({
	        type: "POST",
	        url: "addServicePriceUI.html",
	        data: {},
	        dataType:"json",
	        success: function(data){
	        	var $td0 = $("#td0");
	        	$td0.html(data);
	        		
	        }

	     });
	});*/
});

function savePrice(){
	var serviceId = $("#serviceId").val();
	var singlePrice = $("#singlePrice").val();
	var timesMax = $("#timesMax").val();
	var timesMaxPrice = $("#timesMaxPrice").val();
	if(serviceId==''||serviceId==null){
		alert("请输入服务id!");
		return;
	}
	if(singlePrice==''||singlePrice==null||timesMax==''||timesMax==null||timesMaxPrice==''||timesMaxPrice==null){
		alert("单次价格、最大值及最大值价格为必填项!");
		return;
	}else{
		//删除原始数据
		$.ajax({
	        type: "POST",
	        url: "delServicePrice.html",
	        data: {"serviceId":serviceId},
	        dataType:"json",
	        success: function(data){
	        	if(!data.success){
	        		alert("删除原始价格失败!");
	        		return;
	        	}else{
	        		//添加单次
	        		$.ajax({
	        	        type: "POST",
	        	        url: "addServicePrice.html",
	        	        data: {"serviceId":serviceId,"timesG":0,"timesLE":0,"type":0,"price":singlePrice},
	        	        dataType:"json",
	        	        success: function(data){
	        	        	if(data.success){
	        	        		//添加最大限值
	        	        		$.ajax({
	        	        	        type: "POST",
	        	        	        url: "addServicePrice.html",
	        	        	        data: {"serviceId":serviceId,"type":2,"timesG":timesMax,"timesLE":0,"price":timesMaxPrice},
	        	        	        dataType:"json",
	        	        	        success: function(data){
	        	        	        	if(data.success){
	        	        	        		//添加区间值
	        	        	        		var list = [];//保存起始和结束map

	        	        	        	        		$('#td0 #td').each(function(i){
	        	        	        	        			var timesG = $(this).find("input[id=timesG]").val();        			
	        	        	        	        			var timesLE = $(this).find("input[id=timesLE]").val();        			
	        	        	        	        			var price  = $(this).find("input[id=price]").val();
	        	        	        	        			
	        	        	        	        			if((timesG==""||timesG==null)&&(timesLE==""||timesLE==null)&&(price==""||price==null)){
	        	        	        	        				return true;//跳出当前循环
	        	        	        	        			}
	        	        	        	        			if(timesG==""||timesG==null||timesLE==""||timesLE==null||price==""||price==null){
	        	        	        	        				alert("范围及价格均不能为空!");
	        	        	        	        				return;
	        	        	        	        			}
	        	        	        	        			if(parseInt(timesG)>=parseInt(timesLE)){
	        	        	        	        				alert("范围填写错误!");
	        	        	        	        				return;
	        	        	        	        			}
	        	        	        	        			//两次范围不能重叠
	        	        	        	        			if(list.length>0){
	        	        	        	        				for(var i = 0; i < list.length; i++){
	        	        	        	        					var begin = 0;
	        	        	        	            				var end = 0;
	        	        	        	            				var flag = false;
	        	        	        	            				var newMap = list[i];
	        	        	        	        					//遍历map
	        	        	        	                			for(var prop in newMap){
	        	        	        	                			    if(newMap.hasOwnProperty(prop)){
	        	        	        	                			        //console.log('key is ' + prop +' and value is' + newMap[prop]);
	        	        	        	                			        if(prop=='timesG'){
	        	        	        	                			        	begin = newMap[prop];
	        	        	        	                			        }else{
	        	        	        	                			        	end = newMap[prop];
	        	        	        	                			        }
	        	        	        	                			    }
	        	        	        	                			   
	        	        	        	                			}
	        	        	        	            		/*	 if((parseInt(timesBegin)>parseInt(end) && parseInt(timesEnd)>parseInt(end))||
	        	        	        	              			       (parseInt(timesBegin)<parseInt(begin) && parseInt(timesEnd)<parseInt(begin))){
	        	        	        	                  				//范围合理
	        	        	        	                  				flag = true;
	        	        	        	                  		 }*/
	        	        	        	            			 flag = true;
	        	        	        	        				}
	        	        	        	        				
	        	        	        	        				if(flag){
	        	        	        	        					var map = {};//起始和结束map
	        	        	        	        					map["timesG"]=timesG;
	        	        	        	                			map["timesLE"]=timesLE;
	        	        	        	                			list.push(map);
	        	        	        	        				}else{
	        	        	        	        					alert("价格范围有重叠!");
	        	        	        	        					return;
	        	        	        	        				}
	        	        	        	        			}else{
	        	        	        	        				var map = {};//起始和结束map
	        	        	        	        				map["timesG"]=timesG;
	        	        	        	            			map["timesLE"]=timesLE;
	        	        	        	            			list.push(map);
	        	        	        	        			}
	        	        	        	        			

	        	        	        	        			$.ajax({
	        	        	        	        		        type: "POST",
	        	        	        	        		        url: "addServicePrice.html",
	        	        	        	        		        data: {"serviceId":serviceId,"timesG":timesG,"timesLE":timesLE,"price":price,"type":1},
	        	        	        	        		        dataType:"json",
	        	        	        	        		        success: function(data){
	        	        	        	        		        	if(data.success){
	        	        	        	        		        		alert("添加成功!");
	        	        	        	        		        	}else{
	        	        	        	        		        		alert("添加失败!");
	        	        	        	        		        	}
	        	        	        	        		        		
	        	        	        	        		        }

	        	        	        	        		     });
	        	        	        	        		});
	        	        	        	        	
	        	        	        	}else{
	        	        	        		alert("添加失败!");
	        	        	        	}
	        	        	        		
	        	        	        }

	        	        	     });
	        	        	}else{
	        	        		alert("添加失败!");
	        	        	}
	        	        		
	        	        }

	        	     });
	        	}
	        }
		});
		
		
		
	}
}