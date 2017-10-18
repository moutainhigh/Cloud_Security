$(function(){
	
	// 定义数组
    var label = [];
    var value = [];
	//为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
    require.config({
        paths:{ 
            echarts:'../echarts/echarts',
            'echarts/chart/bar' : '../echarts/echarts-map',
            'echarts/chart/line': '../echarts/echarts-map',
            'echarts/chart/pie' : '../echarts/echarts-map'
        }
    });
    trading();
    function trading(){
    	
    	// 定义数组
    	var label = [];
    	var value = [];
    	$.ajax({
    		type: "POST",
    		url: "findTradingCurve.html",
    		dataType:"json",
    		success: function(data){
    			$.each(data.tradingCurveList,function(i,item){
    				label[i]=item['time'];
    				value[i]=item['price'];
    			});
    			require(['echarts','echarts/chart/line'],
    					function (ec) {
    				// 基于准备好的dom，初始化echarts图表
    				var myChart = ec.init(document.getElementById('tradingCurve')); 
    				//设置数据
    				var option = {
    						title : {
    							text: '最近6小时交易额变化曲线',
    							y: '30',
    							x: 'center'
    						},
    						tooltip : {
    							trigger: 'axis',
    							/* formatter: function(params) {
		    	            return params[0].name + '<br/>'
		    	                   + params[0].seriesName + ' : ' + params[0].value + ' (m^3/s)<br/>'
		    	                   + params[1].seriesName + ' : ' + -params[1].value + ' (mm)';
		    	        }*/
    						}, 
    						//设置坐标轴
    						xAxis : [{
    							type : 'category',
    							data : label
    						}],
    						yAxis : [{
    							type : 'value'
    						}],
    						//设置数据
    						series : [{
    							"name":"最近6小时交易额变化曲线",
    							"type":"line",
    							"data":value
    						}],
    						grid:{
    							width:440
    						}
    				};
    				// 为echarts对象加载数据 
    				myChart.setOption(option); 
    				window.onresize = myChart.resize;
    			});
    		}
    	});
    }
    
   
    $('#c_btn').click(function(){
		$(this).siblings().removeClass('datab_cur');
		$(this).addClass('datab_cur');
		$('.tabCont').show();
		$('.dd_data_box').hide();
		trading();
	})
	$('#st_btn').click(function(){
		$("#reportype").val("0");
		$("#servName").val("0");
		$("#timeDiv").css('display','none'); 
		$("#timeDiv1").css('display','none'); 
		$("#orderAmountLine").css('display','none'); 
		$("#orderAmountPie").css('display','none'); 
		$(this).siblings().removeClass('datab_cur');
		$(this).addClass('datab_cur');
		$('.dd_data_box').show();
		$('.tabCont').hide();
		//statisAnalysis();
	})
	
	$('#reportype').click(function(){
	    var val = $(this).val();
	    if(val == "1"){
	    	$("#create_date").val("");
	    	$("#create_date1").val("");
	    	$("#timeDiv").css("display","block");
	    	$("#timeDiv1").css("display","none");
	    }
	    if(val == "2"){
	    	$("#create_date").val("");
	    	$("#create_date1").val("");
	    	$("#timeDiv").css("display","none");
	    	$("#timeDiv1").css("display","block");
	    }
	    if(val == "0"){
	    	$("#create_date").val("");
	    	$("#create_date1").val("");
	    	$("#timeDiv").css("display","none");
	    	$("#timeDiv1").css("display","none");
	    }
	    
	})
  $('#statisAnalysis').click(function(){
	var reportype = $("#reportype").val();
	var createDate = "";
	if(reportype == "0"){
		alert("信息提示：报表类型不能为空！"); 
		return;
	}
	
	if(reportype == "1"){
		createDate = $("#create_date").val();
	}
    if(reportype == "2"){
    	createDate = $("#create_date1").val();
	}
	
	
	if(createDate == "" || createDate == null){  
		alert("信息提示：报表时间不能为空！"); 
		return;
	} 
	
	var servName = $("#servName").val();
	if(servName == "0"){
		$("#orderAmountPie").css("display","block");
		$("#orderAmountLine").css("display","none");
		var pielabel = [];
	    var pievalue = [];
		$.ajax({
			type : "POST",
			url : "findOrderAmountPie.html",
			data: {"create_date":createDate,"reportype":reportype},
			dataType : "json",
			success : function(data) {
				if(data.sta == "1"){
					var name = "";
					if(data.flag == "1"){
						name += "当日内各类服务订单交易额占比情况";
					}
	                if(data.flag == "2"){
						name += "当月内各类服务订单交易额占比情况";
					}
	                
					var series;
					$.each(data.orderAmountPieList, function(i, item) {
						pielabel[i]= item['servName'];
						pievalue[i]={'value':item['price'],'name':item['servName']};
					});
					require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
						// 基于准备好的dom，初始化echarts图表
						var myChart = ec.init(document.getElementById('orderAmountPie'));
						//设置数据
						var option = {
							      title : {
							        text: name,
							        x:'center'
							      },
							      tooltip : {
							        trigger: 'item',
							        formatter: "{a} <br/>{b} : {c} ({d}%)"
							      },
							      /*legend: {
							        orient : 'vertical',
							        x : 'left',
							        data:pielabel
							      },*/
							      calculable : false,
							      series : [
							        {
							          name:name,
							          type:'pie',
							          radius : '55%',
							          center: ['50%', '60%'],
							          data:pievalue
							        }
							      ]
							    };
						// 为echarts对象加载数据 
						myChart.setOption(option);
					});
				}else {
					$("#orderAmountPie").empty();
					//var htmlStr = "<div style="padding-left:200px">该时间内暂无数据！</div>"
					$("#orderAmountPie").append("<div style='padding-left: 260px;padding-top: 200px;font-size: 16px;'>该时间内暂无数据！</div>");
						return;
				}
				
			}
		});
	}else{
		$("#orderAmountPie").css("display","none");
		$("#orderAmountLine").css("display","block");
		// 定义数组
    	var label = [];
    	var value = [];
		$.ajax({
	        type: "POST",
	        url: "findOrderAmountLine.html",
	        data: {"create_date":createDate,"serviceId":servName,"reportype":reportype},
	        dataType:"json",
	        success: function(data){
	        	if(data.sta == "1"){
		        	var name = "";
		        	var nameDetail = "";
					if(data.flag == "1"){
						name += "日交易额总数";
						nameDetail += "时交易额总数";
					}
	                if(data.flag == "2"){
						name += "月交易额总数";
						nameDetail += "日交易额总数";
					}
	                
		        	$.each(data.orderAmountLineList,function(i,item){
	    				label[i]=item['time'];
	    				value[i]=item['price'];
	    			});
	    			require(['echarts','echarts/chart/line'],
	    					function (ec) {
	    				// 基于准备好的dom，初始化echarts图表
	    				var myChart = ec.init(document.getElementById('orderAmountLine')); 
	    				//设置数据
	    				var option = {
	    						title : {
	    							text: name,
	    							y: '30',
	    							x: 'center'
	    						},
	    						tooltip : {
	    							trigger: 'axis'
	    			
	    						}, 
	    						//设置坐标轴
	    						xAxis : [{
	    							type : 'category',
	    							data : label
	    						}],
	    						yAxis : [{
	    							type : 'value'
	    						}],
	    						//设置数据
	    						series : [{
	    							"name":nameDetail,
	    							"type":"line",
	    							"data":value
	    						}],
	    						grid:{
	    							width:440
	    						}
	    				};
	    				// 为echarts对象加载数据 
	    				myChart.setOption(option); 
	    				window.onresize = myChart.resize;
	    			})
	        	
	        	} else {
	        		$("#orderAmountLine").empty();
					//var htmlStr = "<div style="padding-left:200px">该时间内暂无数据！</div>"
					$("#orderAmountLine").append("<div style='padding-left: 260px;padding-top: 200px;font-size: 16px;'>该时间内暂无数据！</div>");
						return;
	        	}
	          }
	       });
	}
  })
})