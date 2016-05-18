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
	var list = [];//保存起始和结束map
	//删除之前的价格
	$.ajax({
        type: "POST",
        url: "delServicePrice.html",
        data: {"serviceId":serviceId},
        dataType:"json",
        success: function(data){
        	if(data.success){
        		$('#td0 #td').each(function(i){
        			var timesBegin = $(this).find("input[id=timesBegin]").val();        			
        			var timesEnd = $(this).find("input[id=timesEnd]").val();        			
        			var price  = $(this).find("input[id=price]").val();
        			
        			if((timesBegin==""||timesBegin==null)&&(timesEnd==""||timesEnd==null)&&(price==""||price==null)){
        				return true;//跳出当前循环
        			}
        			if(timesBegin==""||timesBegin==null||timesEnd==""||timesEnd==null||price==""||price==null){
        				alert("范围及价格均不能为空!");
        				return;
        			}
        			if(parseInt(timesBegin)>=parseInt(timesEnd)){
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
                			        if(prop=='timesBegin'){
                			        	begin = newMap[prop];
                			        }else{
                			        	end = newMap[prop];
                			        }
                			    }
                			   
                			}
            			 if((parseInt(timesBegin)>parseInt(end) && parseInt(timesEnd)>parseInt(end))||
              			       (parseInt(timesBegin)<parseInt(begin) && parseInt(timesEnd)<parseInt(begin))){
                  				//范围合理
                  				flag = true;
                  		 }
        				}
        				
        				if(flag){
        					var map = {};//起始和结束map
        					map["timesBegin"]=timesBegin;
                			map["timesEnd"]=timesEnd;
                			list.push(map);
        				}else{
        					alert("价格范围有重叠!");
        					return;
        				}
        			}else{
        				var map = {};//起始和结束map
        				map["timesBegin"]=timesBegin;
            			map["timesEnd"]=timesEnd;
            			list.push(map);
        			}
        			

        			$.ajax({
        		        type: "POST",
        		        url: "addServicePrice.html",
        		        data: {"serviceId":serviceId,"timesBegin":timesBegin,"timesEnd":timesEnd,"price":price},
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

}