$(function(){
	var serv = $(".servicetext");
	$.ajax({
		type: "POST",
		url: "getAllService.html",
		dataType:"json",
		success: function(data){
			serv.append("<option value=''>请选择服务</option>");
			$.each(data,function(i,p){
				serv.append("<option value="+p["id"]+">"+p["name"]+"</option>");
            });
		}
	});
	
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
    $.ajax({
		type: "POST",
		url: "getlinedate.html",
		dataType:"json",
		success: function(data){
			$.each(data,function(i,p){
				label[i]=p['time'];
				value[i]=p['count'];
            });
    require(['echarts','echarts/chart/line'],
    		  function (ec) {
    		    // 基于准备好的dom，初始化echarts图表
    		    var myChart = ec.init(document.getElementById('system1')); 
    		    //设置数据
    		    var option = {
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
    		          "name":"监测数据",
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
    var pielabel = [];
    var pievalue = [];
	$.ajax({
		type : "POST",
		url : "getpiedate.html",
		dataType : "json",
		success : function(data) {
			var series;
			$.each(data, function(i, p) {
				pielabel[i]=p['name'];
				pievalue[i]={'value':p['count'],'name':p['name']};
			});
			require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
				// 基于准备好的dom，初始化echarts图表
				var myChart = ec.init(document.getElementById('system2'));
				//设置数据
				var option = {
					      title : {
					        text: '订单分析饼图',
					        subtext: '',
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
					          name:'饼图实例',
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
		}
	});
});
var getServiceDate=function(repeat){
//	 	var pielabel = [];
//	    var pievalue = [];
	    // 定义数组
	    var barlabel = [];
	    var barvalue = [];
	    //为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
	    require.config({
	        paths:{ 
	            echarts:'../echarts/echarts',
	            'echarts/chart/bar' : '../echarts/echarts-map',
	            'echarts/chart/line': '../echarts/echarts-map',
	            'echarts/chart/pie' : '../echarts/echarts-map'
	        }
	    });
	    var serviceid = "";
	    var begindate =  "";
	    var enddate =  "";
	    var divname;
	    if(repeat==0){
	    	divname='system3';
	    	serviceid =  $("#servicetype1").val();
	  	    begindate =  $("#begin_date1").val();
	  	    enddate =  $("#end_date1").val();
	    }else{
	    	divname='system4';
	    	serviceid =  $("#servicetype2").val();
	  	    begindate =  $("#begin_date2").val();
	  	    enddate =  $("#end_date2").val();
	    }
	    
	    var beginDate=new Date(begindate.replace("-", "/").replace("-", "/"));  
	    var endDate=new Date(enddate.replace("-", "/").replace("-", "/"));  
	    if(endDate<beginDate){  
	        alert("信息提示：统计结束时间不能小于统计开始时间！"); 
	        return;
	    } 
	    if(serviceid == ""){  
	        alert("信息提示：请选择服务类型！"); 
	        return;
	    }
		/*$.ajax({
			type : "POST",
			url : "getServiceDate.html",
			dataType : "json",
			data:{
				serviceid:serviceid,
				begindate:begindate,
				enddate:enddate,
				repeat:repeat
			},
			success : function(data) {
				var series;
				$.each(data, function(i, p) {
					pielabel[i]=p['name'];
					pievalue[i]={'value':p['count'],'name':p['name']};
				});
				require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
					// 基于准备好的dom，初始化echarts图表
					var myChart = ec.init(document.getElementById(divname));
					//设置数据
					var option = {
						      title : {
						        text: '',
						        subtext: '',
						        x:'center'
						      },
						      tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						      },
						      legend: {
						        orient : 'vertical',
						        x : 'right',
						        data:pielabel
						      },
						      calculable : false,
						      series : [
						        {
						          name:'饼图实例',
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
			}
		});*/
		
	    $.ajax({
			type: "POST",
			url: "getServiceDate.html",
			dataType:"json",
			data:{
				serviceid:serviceid,
				begindate:begindate,
				enddate:enddate,
				repeat:repeat
			},
			success: function(data){
				if (data.length <= 0){
					$("#"+divname).empty();
					$("#"+divname).append("<div style='padding-left: 350px;padding-top: 150px;font-size: 16px;'>没有符合条件的订单！</div>");
					return;
				}
				
				$.each(data,function(i,p){
					barlabel[i]=p['time'];
					barvalue[i]=p['count'];
	            });
	    require(['echarts','echarts/chart/bar'],
	    		  function (ec) {
	    		    // 基于准备好的dom，初始化echarts图表
	    		    var myChart = ec.init(document.getElementById(divname));
	    		    //设置数据
	    		    var option = {
			    	  tooltip : {
	//	                            trigger: 'axis',
	                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	                        }
	                  },
	    		      //设置坐标轴
	    		      xAxis : [{
	    		          type : 'category',
	    		          data : barlabel
	    		        }],
	    		      yAxis : [{
	    		          type : 'value'
	    		        }],
	    		      //设置数据
	    		      series : [{
	    		          "name":"订单数量",
	    		          "type":"bar",
	    		          "data":barvalue
	    		        }],
	    		      grid:{
	    		            width:640
	    		          }
	    		    };
	    		    // 为echarts对象加载数据 
	    		    myChart.setOption(option); 
	    		    window.onresize = myChart.resize;
	    		  });
			}
		});
}

//回购率
var getServiceDateReBuy=function(repeat){
 	var pielabel = [];
    var pievalue = [];
    var serviceid = "";
    var begindate =  "";
    var enddate =  "";
    var divname;
    if(repeat==0){
    	divname='system3';
    	serviceid =  $("#servicetype1").val();
  	    begindate =  $("#begin_date1").val();
  	    enddate =  $("#end_date1").val();
    }else{
    	divname='system4';
    	serviceid =  $("#servicetype2").val();
  	    begindate =  $("#begin_date2").val();
  	    enddate =  $("#end_date2").val();
    }
    
    var beginDate=new Date(begindate.replace("-", "/").replace("-", "/"));  
    var endDate=new Date(enddate.replace("-", "/").replace("-", "/"));  
    if(endDate<beginDate){  
        alert("信息提示：统计结束时间不能小于统计开始时间！"); 
        return;
    } 
    if(serviceid == ""){  
        alert("信息提示：请选择服务类型！"); 
        return;
    }
	$.ajax({
		type : "POST",
		url : "getServiceDateReBuy.html",
		dataType : "json",
		data:{
			serviceid:serviceid,
			begindate:begindate,
			enddate:enddate,
			repeat:repeat
		},
		success : function(data) {
			if (data.length <= 0){
				$("#"+divname).empty();
				$("#"+divname).append("<div style='padding-left: 350px;padding-top: 150px;font-size: 16px;'>没有符合条件的订单！</div>");
				return;
			}
			var series;
			$.each(data, function(i, p) {
				pielabel[i]=p['name'];
				pievalue[i]={'value':p['count'],'name':p['name']};
			});
			require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
				// 基于准备好的dom，初始化echarts图表
				var myChart = ec.init(document.getElementById(divname));
				//设置数据
				var option = {
					      title : {
					        text: '',
					        subtext: '',
					        x:'center'
					      },
					      
					      tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					      },
					      color:['lightgreen', 'orange'],
					      legend: {
					        orient : 'vertical',
					        x : 'right',
					        data:pielabel
					      },
					      calculable : false,
					      series : [
					        {
					          name:'饼图实例',
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
		}
	});
}