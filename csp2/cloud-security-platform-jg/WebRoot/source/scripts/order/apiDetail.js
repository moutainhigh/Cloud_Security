$(function(){
	//为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
    require.config({
        paths:{ 
            echarts:'../echarts/echarts',
            'echarts/chart/bar' : '../echarts/echarts-map',
            'echarts/chart/line': '../echarts/echarts-map',
            'echarts/chart/pie' : '../echarts/echarts-map'
        }
    });
    require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	function (ec) {//回调函数
	//web漏洞扫描API
	var myChart1 = ec.init(document.getElementById('apiUse'));
	var orderId = $("#orderIdHidden").val();
	 //后台获取数据
    $.ajax({
    	type : "post",
    	url:"getAllApiCount.html",
    	data:{"orderId":orderId},
        dataType:"json",
        success:function(data){

    		if(data.success){
        		var dataArray = data.dataArray;

    		var option1 = {
    			tooltip : {
    	        	trigger: 'item',
    	        	formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },    	    
    	    //calculable : true,
    	    series : [
    	        {
    	            name:'数量',
    	            type:'pie',
    	            radius : '50%',
    	            center: ['50%', '50%'],
    	            itemStyle : { normal: {label : {show: true, position: 'outer',formatter: "{b}:{c}"}}},
    	            data: dataArray
    	        }
    	        ]
    			};
    		
    			myChart1.setOption(option1);
    		}else{
    			window.location.href="index.html";
    		}
        	},
        });

});
    searchCombine();
});

function searchCombine(){
	var url = $("#searchUrl").val();
	var beginDate = $("#begin_date").val();
	var endDate = $("#end_date").val();
	var orderId = $("#orderIdHidden").val();
    $.ajax({
    	type : "post",
    	url:"getAPIHistoryInfo.html",
    	data:{"orderId":orderId,"beginDate":beginDate,"endDate":endDate,"url":url},
        dataType:"json",
        success:function(data){

    		if(data.success){
        		var infoList = data.infoList;
        		$("#infoBody").empty();
        		var html = "";
        		for(var i=0;i<infoList.length;i++){
        			html += "<tr>";
        			html += "<td style='width:18%;'>"+infoList[i].create_time+"</td>";
        			switch(infoList[i].api_type){
        			case 1:
        				html += "<td  style='width:6%;'>POST</td>";
        				html += "<td  style='width:20%;'>"+infoList[i].apiName+"</td>";
        				html += "<td  style='width:50%;'>"+infoList[i].apiUrl+"order/"+infoList[i].token+
        						"<br>"+
        						"<p style='font-size:12px;color:#888;margin-bottom:0px'>";
        				var temp = "";
        				var scan_type = "";
        				if(infoList[i].scan_type!=0){
        					scan_type = infoList[i].scan_type;
        				}
        				if(infoList[i].service_type==1){
        					temp = "参数  targetURL："+infoList[i].url+
		    						",  scanType："+
		    						",  startTime："+infoList[i].begin_date+
		    						",  scanDepth: "+
		    						",  maxPages: "+
		    						",  stategy: ";
        				}else if(infoList[i].service_type==2){
        					temp = "参数  targetURL："+infoList[i].url+
		    						",  scanType："+
		    						",  startTime："+infoList[i].begin_date+
		    						",  scanDepth: "+
		    						",  maxPages: "+
		    						",  stategy: ";
        				}else if(infoList[i].service_type==3){
        					temp = "参数  targetURL："+infoList[i].url+
		    						",  startTime："+infoList[i].begin_date+
		    						",  endTime :"+infoList[i].end_date+
		    						",  scanPeriod: " +scan_type+
		    						",  scanDepth: ";
        				}else if(infoList[i].service_type==4){
        					temp = "参数  targetURL："+infoList[i].url+
		    						",  startTime："+infoList[i].begin_date+
		    						",  scanDepth: "+
		    						",  stategy: ";
        				}else{
        					temp = "参数  targetURL："+infoList[i].url+
		    						",  startTime："+infoList[i].begin_date+
		    						",  endTime :"+infoList[i].end_date+
		    						",  scanPeriod: " +scan_type;
        				}
        					html += temp + "</p>"+ "</td>";
        				break;
        				
        			case 2:
        				html += "<td  style='width:6%;'>PUT</td>";
        				html += "<td  style='width:20%;'>订单(任务)操作</td>";
        				html += "<td  style='width:50%;'>"+infoList[i].apiUrl+"order/"+infoList[i].orderId+"/"+infoList[i].token+
        						"<br>"+
        						"<p style='font-size:12px;color:#888;margin-bottom:0px'>";
        				var temp = "";
        				if(infoList[i].service_type==1){
        					temp = "参数  opt：pause/resume/stop";
        				}else {
        					temp = "参数  opt：resume/stop";
        				}
        				html += temp + "</p>"+ "</td>";
        				break;
        				
        			case 3:
        				html += "<td  style='width:6%;'>GET</td>";
        				html += "<td  style='width:20%;'>获取订单(任务)状态</td>";
        				html += "<td  style='width:50%;'>"+infoList[i].apiUrl+"orderStatus/"+infoList[i].orderId+"/"+infoList[i].token+"</td>";
        				break;
        				
        			case 4:
        				html += "<td  style='width:6%;'>GET</td>";
        				html += "<td  style='width:20%;'>获取订单结果</td>";
        				html += "<td  style='width:50%;'>"+infoList[i].apiUrl+"orderResult/"+infoList[i].orderId+"/"+infoList[i].taskId+"/"+infoList[i].token+"</td>";
        				break;
        				
        			case 5:
        				html += "<td  style='width:6%;'>POST</td>";
        				html += "<td  style='width:20%;'>获取订单结果报告</td>";
        				html += "<td  style='width:50%;'>"+infoList[i].apiUrl+"orderReport/"+infoList[i].orderId+"/"+infoList[i].token;
        				if(infoList[i].service_type==1 || infoList[i].service_type==2){
        					html += "</td>";
        				}else{
        					html += "<br>"+
    								"<p style='font-size:12px;color:#888;margin-bottom:0px'>" + 
    								"参数  Content：Report" +
    								"</p>" +
    								"</td>";
        				}
        				break;
        			}
        			if(infoList[i].status==1){
        				html += "<td  style='width:10%;'><span>成功</span></td>";
        			}else{
        				html += "<td  style='width:10%;'><span>失败</span></td>";
        			}
            		html += "</tr>";
        			
        		}

        		$("#infoBody").append(html);
    		}else{
    			window.location.href="index.html";
    		}
    	},
    });
}