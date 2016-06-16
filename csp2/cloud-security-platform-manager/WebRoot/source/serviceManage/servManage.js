
//添加时改变一级分类
function changeParent(){
	var parent = $("#u14_input").val();
	if(parent=="API"){
		$("#u17_input").empty();
	}else{
		$("#u17_input").empty();
		var temp = "<option value='1'>监测及预警服务</option><option value='2'>防护及加固服务</option>";
		$("#u17_input").append(temp);
	}
}

//查询时改变一级分类
function changeParentForSearch(){
	var parent = $("#u5_input").val();
	if(parent=="API"){
		$("#u43_input").empty();
	}else{
		$("#u43_input").empty();
		var temp = "<option value='1'>监测及预警服务</option><option value='2'>防护及加固服务</option>";
		$("#u43_input").append(temp);
	}
}

//上传服务图标
function uploadIcon(){
	var options = {
		url:'uploadIcon.html',
		//beforeSubmit:showRequest,
		success: function(data) {
				
			if(data.success){
	    		alert("上传服务图标成功!");
	    		$("#filePathHidden").val(data.filePath);
	    	}else{
	    		alert("上传服务图标失败!");
	    	}
		  },
	      error:function(data){
	      	alert("err");
	      }
	}
	// 将options传给ajaxForm
	$('#form1').ajaxSubmit(options);

}

//保存服务
function saveServ(){
	var parent = $("#u14_input").val();
	var type = $("#u17_input").val();
	var name = $("#u2_input").val();
    var remarks = $("#u5_input").val();
    var icon = $("#filePathHidden").val();
    if(name==null||name==""){
    	alert("请填写服务名称!");
    	return;
    }else if(icon==null||icon==""){
    	alert("请上传服务图标!");
    	return;
    }else{
    	 $.ajax({
    	        type: "POST",
    	        url: "addServ.html",
    	        data: {"parent":parent,"type":type,"name":name,"remarks":remarks,"icon":icon},
    	        dataType:"json",
    	        success: function(data){
    	        	if(data.success){
    	        		alert("服务添加成功!");
    	        	}else{
    	        		alert("服务添加失败!");
    	        	}
    	        },
    	        error:function(data){
    	        	alert("err");
    	        }
    	     });
    }
   
}

function searchServ(){
	//服务名称
	var servName = $("#u2_input").val();
	//一级分类
	var parentC = $("#u5_input").val();
	//服务类型
	var servType = $("#u43_input").val();
	 $.ajax({
	        type: "POST",
	        url: "searchServ.html",
	        data: {"parent":parentC,"type":servType,"name":servName},
	        dataType:"json",
	        success: function(data){
	        	var list = data.servList;
	        	$("#servList").empty();
	        	$.each(list, function(i, value) {
	        		var type = this.typeName;
	        		if(typeof(type) == 'undefined'){
	        			type="";
	        		}
	        	     // this;      //this指向当前元素
	        	    // i;         //i表示Array当前下标
	        	    // value;     //value表示Array当前元素
	        	     var temp = "<tr>"+
                 	"<td class='t_username'>"+this.parentC+"</td>"+
                    "<td class='t_date'>"+type+"</td>"+
                    "<td class='t_assets'>"+this.name+"</td>"+
                    "<td class='t_service' style='text-align:center;width:320px'>"+this.remarks+"</td>"+
                    "<td class='t_operation'>"+
                    	"<a href='${ctx}/updateServUI.html?servId="+this.id+"&parent="+this.parentC+"&servName="+this.name+"&icon="+this.icon+"&remarks="+this.remarks+"&type="+this.servType+"' class='ope_a add_change'>修改</a>"+
                    	"<a href='javascript:void(0)' onclick='delServ(this)' class='ope_a ml20' servid='"+this.id+"' parentC='"+this.parentC+"'>删除</a>"+
                    	"<a href='${ctx}/addServicePriceUI.html?servId="+this.id+"&parent="+this.parentC+"' class='ope_a add_change'>设置价格</a>"+
                    "</td>"+
           		"</tr>";
	        	     $("#servList").append(temp);
	        	});
	        },
	        error:function(data){
	        	alert(data);
	        }
	     });
}

//删除服务
function delServ(Obj){
	var servid = $(Obj).attr("servid");
	var parentC = $(Obj).attr("parentC");
	 $.ajax({
	        type: "POST",
	        url: "delServ.html",
	        data: {"parent":parentC,"servId":servid},
	        dataType:"json",
	        success: function(data){
	        	if(data.success){
	        		alert("删除服务成功!");
	        	}else{
	        		alert("删除服务失败!");
	        	}
	        },
	        error:function(){
	        	alert("err");
	        }
	 });
}

//修改服务
function updateServ(Obj){
	var servid = $(Obj).attr("servid");
	var parentC = $(Obj).attr("parentC");
	 $.ajax({
	        type: "POST",
	        url: "updateServ.html",
	        data: {"parent":parentC,"servId":servid},
	        dataType:"json",
	        success: function(data){
	        	if(data.success){
	        		alert("修改服务成功!");
	        	}else{
	        		alert("修改服务失败!");
	        	}
	        },
	        error:function(){
	        	alert("err");
	        }
	 });
}

//修改服务
function updateServ(){
	var servId = $("#servIdHidden").val();
	var parent = $("#u14_input").val();
	var type = $("#u17_input").val();
	var name = $("#u2_input").val();
    var remarks = $("#u5_input").val();
    var icon = $("#filePathHidden").val();
    if(name==null||name==""){
    	alert("请填写服务名称!");
    	return;
    }else if(icon==null||icon==""){
    	alert("请上传服务图标!");
    	return;
    }else{
    	 $.ajax({
    	        type: "POST",
    	        url: "updateServ.html",
    	        data: {"servId":servId,"parent":parent,"type":type,"name":name,"remarks":remarks,"icon":icon},
    	        dataType:"json",
    	        success: function(data){
    	        	if(data.success){
    	        		alert("服务修改成功!");
    	        	}else{
    	        		alert("服务修改失败!");
    	        	}
    	        },
    	        error:function(data){
    	        	alert("err");
    	        }
    	     });
    }
}

//设置详情
function saveDetails(serviceId,parent){
	//服务频率
	var servRates = $("#u19_input").val();
	var list = [];
	//扫描类型
	var scanTypes = '';
	$("input[name='scanTypeCheck']").each(function(){ 
		if($(this).attr("checked")) 
		{ 
			list.push($(this).val());
		} 
		else 
		{ 
			list.pop($(this).val());
		} 
		}) ;
	$.each(list,function(n,value) {   
        	if(n<list.length-1){
                scanTypes+=value+",";
        	}else{
        		scanTypes+=value;
        	}
        });  
	$.ajax({
        type: "POST",
        url: "saveServDetails.html",
        data: {"serviceId":serviceId,"parent":parent,"scanTypes":scanTypes,"servRates":servRates},
        dataType:"json",
        success: function(data){
        	if(data.success){
        		alert("服务详情设置成功!");
        	}else{
        		alert("服务详情设置失败!");
        	}
        },
        error:function(data){
        	alert("err");
        }
     });
}