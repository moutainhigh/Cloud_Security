//用户行业分布统计
function findApikey(){
	var userName = $("#userName").val();
	var apikey = $("#apikey").val();

	$.ajax({
        type: "POST",
        url: "apikeyAnalysis.html",
        data: {"userName":userName,"apikey":apikey},
        dataType:"json",
        success: function(data){
    		$("#body1").html("");
    		var list = data;
    		for (var i = 0; i < list.length; i++){
    			var index = i+1;
    			var trid = "tr1" + i;
    			if(i%2==0){
    				$("#body1").append( "<tr id='"+trid+"'" +"style='background: rgb(250, 250, 250) none repeat scroll 0% 0%;'>" );
    			}else{
    				$("#body1").append( "<tr id='"+trid+"'");
    			}
    			
    			$("#"+trid).append( "<td>" + index + "</td>");
    			
    			if (typeof(list[i].apikey)=="undefined") {
    				$("#"+trid).append( "<td colspan='2'></td>");
    			} else{
	    			$("#"+trid).append( "<td colspan='2'>" + list[i].apikey + "</td>");
    			}
    			$("#"+trid).append( "<td colspan='2'>" + list[i].name + "</td>");

    			$("#body1").append( "</tr>" );
    		}        		     	
        },
	});
}