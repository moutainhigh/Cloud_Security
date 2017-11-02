//用户活跃度分析
function activeAnalysis(){
	var activeSel = $("#activeSel").val();
	var begin_date = $("#begin_date_active").val();
	var end_date = $("#end_date_active").val();
	
	var beginDate=new Date(begin_date.replace("-", "/").replace("-", "/"));  
    var endDate=new Date(end_date.replace("-", "/").replace("-", "/"));  
    if(endDate<beginDate){  
        alert("信息提示：统计结束时间不能小于统计开始时间！"); 
        return;
    } 
    
	$.ajax({
        type: "POST",
        url: "adminUserAnalysis.html",
        data: {"activeSel":activeSel,"begin_date":begin_date,"end_date":end_date},
        dataType:"json",
        success: function(data){
        	if(activeSel=="1"){
        		$("#loginCount").html(data.loginCount);
        		$("#loginParent").html(data.loginParent);
        	}else{
        		//$("#tId").html("");
        		$(".initTr").hide();
        		var list = data;
        		if(list.length==0){
        			alert("信息提示：未查询到结果！"); 
        			$(".seachTr").remove();
        			$(".initTr").show();
        			return;
        		}
        		$(".seachTr").remove();
        		for (var i = 0; i < list.length; i++){
        			var index = i+1;
        			var trid = "tr" + i;
        			if(i%2==0){
        				$("#tId").append( "<tr class='seachTr' id='"+trid+"'" +"style='background: rgb(250, 250, 250) none repeat scroll 0% 0%;'>" );
        			}else{
        				$("#tId").append( "<tr class='seachTr' id='"+trid+"'");
        			}
        			
        			$("#"+trid).append( "<td>" + index + "</td>");
        			$("#"+trid).append( "<td colspan='4'>" + list[i].name + "</td>");
        			$("#tId").append( "</tr>" );
        		}
        		
        	}
        },
     });
}

//用户使用习惯分析
function useAnalysis(){
	var useSel = $("#useSel").val();
	var begin_date = $("#begin_date_use").val();
	var end_date = $("#end_date_use").val();
	
	var beginDate=new Date(begin_date.replace("-", "/").replace("-", "/"));  
    var endDate=new Date(end_date.replace("-", "/").replace("-", "/"));  
    if(endDate<beginDate){  
        alert("信息提示：统计结束时间不能小于统计开始时间！"); 
        return;
    } 
    
	$.ajax({
        type: "POST",
        url: "adminUserHabitAnalysis.html",
        data: {"useSel":useSel,"begin_date":begin_date,"end_date":end_date},
        dataType:"json",
        success: function(data){
        	if(useSel=="1"){
        		//$("#body1").html("");
        		$(".initTr2").hide();
        		var list = data;
        		if(list.length==0){
        			alert("信息提示：未查询到结果！"); 
        			$(".seachTr2").remove();
        			$(".initTr2").show();
        		}else{
        			$(".seachTr2").remove();
        			for (var i = 0; i < list.length; i++){
            			var index = i+1;
            			var trid = "tr1" + i;
            			if(i%2==0){
            				$("#body1").append( "<tr class='seachTr2' id='"+trid+"'" +"style='background: rgb(250, 250, 250) none repeat scroll 0% 0%;'>" );
            			}else{
            				$("#body1").append( "<tr class='seachTr2' id='"+trid+"'");
            			}
            			
            			$("#"+trid).append( "<td>" + index + "</td>");
            			$("#"+trid).append( "<td colspan='4'>" + list[i].time+"~~~"+list[i].time1 + "</td>");
            			$("#body1").append( "</tr>" );
            		}
        		}
        	}else{
        		//$("#body2").html("");
        		$(".initTr3").hide();
        		var list = data;
        		if(list.length==0){
        			alert("信息提示：未查询到结果！"); 
        			$(".seachTr3").remove();
        			$(".initTr3").show();
        		}else{
        			$(".seachTr3").remove();
        			for (var i = 0; i < list.length; i++){
            			var index = i+1;
            			var trid = "tr2" + i;
            			if(i%2==0){
            				$("#body2").append( "<tr class='seachTr3' id='"+trid+"'" +"style='background: rgb(250, 250, 250) none repeat scroll 0% 0%;'>" );
            			}else{
            				$("#body2").append( "<tr class='seachTr3' id='"+trid+"'");
            			}
            			
            			$("#"+trid).append( "<td>" + index + "</td>");
            			$("#"+trid).append( "<td colspan='4'>" + list[i].time+"~~~"+list[i].time1 + "</td>");
            			$("#body2").append( "</tr>" );
            		}  
        		}
        	}
        },
     });
}

//用户行业分布统计
function indusAnalysis(){
	var begin_date = $("#begin_date_industry").val();
	var end_date = $("#end_date_industry").val();
	var industry = $("#industry").val();

	var beginDate=new Date(begin_date.replace("-", "/").replace("-", "/"));  
    var endDate=new Date(end_date.replace("-", "/").replace("-", "/"));  
    if(endDate<beginDate){  
        alert("信息提示：统计结束时间不能小于统计开始时间！"); 
        return;
    } 
//    if(industry==-1){  
//        alert("信息提示：请选择行业！"); 
//        return;
//    }

	$.ajax({
        type: "POST",
        url: "adminIndusAnalysis.html",
        data: {"begin_date":begin_date,"end_date":end_date,"industry":industry},
        dataType:"json",
        success: function(data){
    		$("#body3").html("");
    		var list = data;
    		for (var i = 0; i < list.length; i++){
    			var index = i+1;
    			var trid = "tr3" + i;
    			if(i%2==0){
    				$("#body3").append( "<tr id='"+trid+"'" +"style='background: rgb(250, 250, 250) none repeat scroll 0% 0%;'>" );
    			}else{
    				$("#body3").append( "<tr id='"+trid+"'");
    			}
    			
    			$("#"+trid).append( "<td>" + index + "</td>");
    			$("#"+trid).append( "<td colspan='2'>" + list[i].name + "</td>");
    			if(list[i].industry!=null && list[i].industry!=''){
        			$("#"+trid).append( "<td colspan='2'>" + list[i].industry + "</td>");
    			}else{
    				$("#"+trid).append( "<td colspan='2'>其他</td>");
    			}

    			//$("#"+trid).append( "<td colspan='2'>" + list[i].count + "</td>");
    			$("#body3").append( "</tr>" );
    		}        		     	
        },
	});
}