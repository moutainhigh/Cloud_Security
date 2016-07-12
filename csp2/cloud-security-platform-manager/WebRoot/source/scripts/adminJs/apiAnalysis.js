var Months = ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"];
var stime;
var etime;
var defaultBDate = new Date(new Date().getFullYear(), 0, 1);
var defaultStime = defaultBDate.getFullYear()+"-"+(defaultBDate.getMonth()+1)+"-"+defaultBDate.getDate();
$(function(){
	createDemos();	
	//为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
    require.config({
        paths:{ 
            echarts:'../echarts/echarts',
            'echarts/chart/bar' : '../echarts/echarts-map',
            'echarts/chart/line': '../echarts/echarts-map',
            'echarts/chart/pie' : '../echarts/echarts-map'
        }
    });
    
	analysisAPI();
});

function createDemos(){		
	var	date1 = $("<div id='date' />").appendTo($("#dateSlider"));//渲染日期组件
	var dateSilderObj=date1.dateRangeSlider({
		arrows:false,//是否显示左右箭头
		bounds: {min: new Date(new Date().getFullYear(), 0, 1), max: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 12, 59, 59)},//最大 最少日期
		defaultValues: {min: new Date(new Date().getFullYear(), 0, 1), max: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate())},//默认选中区域
		scales:[{
				first: function(value){return value; },
				end: function(value) {return value; },
				next: function(val){
					var next = new Date(val);
					return new Date(next.setMonth(next.getMonth() + 1));
				 },
				label: function(val){
					return Months[val.getMonth()];
				},
				format: function(tickContainer, tickStart, tickEnd){
					tickContainer.addClass("myCustomClass");
				}
		}]
		
				
	});//日期控件
	
	//重新赋值（整个时间轴）
	/*dateSilderObj.dateRangeSlider("bounds", new Date(new Date().getFullYear(), 0, 1), new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 12, 59, 59));*/

	//重新赋值（选中区域）
	/*dateSilderObj.dateRangeSlider("values", new Date(new Date().getFullYear(), 0, 1), new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()));*/

	
	//拖动完毕后的事件 获取到拖动后的起始时间stime 和 结束时间etime
	dateSilderObj.bind("valuesChanged", function(e, data){
		
	  	//window.clearTimeout(timer);//去掉定时器 
		var val=data.values;
		stime=val.min.getFullYear()+"-"+(val.min.getMonth()+1)+"-"+val.min.getDate();
		etime=val.max.getFullYear()+"-"+(val.max.getMonth()+1)+"-"+val.max.getDate();
	  	console.log("起止时间："+stime+" 至 "+etime);

	  	//拖动后显示操作
	  	analysisAPI();
	  	//alert("stime:"+stime+",defaulttime:"+defaultStime);
	  	if(stime==defaultStime){
	  		window.clearTimeout(timer);//去掉定时器 
	  	}else{
	  		var timer=setTimeout(function(){
		  		//8秒恢复默认选中区域
		  		dateSilderObj.dateRangeSlider("values", new Date(new Date().getFullYear(), 0, 1), new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()));
		  		//analysisAPI();
		  	}, 5000);
	  	}
	});

}

function analysisAPI(){
	require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	function (ec) {//回调函数
	//web漏洞扫描API
	var myChart1 = ec.init(document.getElementById('api1'));

	 //后台获取数据
    $.ajax({
    	type : "post",
    	url:"adminAPICount.html",
    	data:{"serviceType":1,"beginTime":stime,"endTime":etime},
        dataType:"json",
        success:function(data){
    		var countList = [];
    		var list = data.countList;
    		for(var i = 0; i < list.length; i++){
    			//alert(list[i].count);
    			countList.push(list[i].count);
    		
    		}
    		countList.reverse() ;
    		option1 = {
    			    tooltip : {
    					show:true,
    			        trigger: 'axis',
    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    			            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
    			        }
    			    },
    			    
    			    grid: { // 控制图的大小，调整下面这些值就可以，
    			    	 y: 10,
    		             x: 150,
    		             x2: 50,
    		             y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
    		             borderColor:'#fff'
    		         },
    		         calculable : false,
    			    xAxis:  {
    			        type: 'value',
    			        show:false,
    			        splitLine:{
    			    		show:false
    			    	},
    			    	axisLabel:{  
    			            show:false
    			        }, 
    			        axisLine:{
    			        	show:false
    			        }
    			    },
    			   
    			    yAxis: {
    			        type: 'category',
    			        data: ['获取订单结果报告','获取订单结果','获取订单(任务)状态','订单(任务)操作','创建web漏洞扫描订单'],
    			        show: false,
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    series: [
    			        {
    			            name: '数量',
    			            type: 'bar',
    			            itemStyle : { normal: {label : {show: true, position: 'right'}}},
    			            data: countList
    			        }
    			    ]
    			};
    		
    			myChart1.setOption(option1);
        	},
        });
        	
	
		
		//木马检测API
		var myChart2 = ec.init(document.getElementById('api2'));
		 //后台获取数据
	    $.ajax({
	    	type : "post",
	    	url:"adminAPICount.html",
	    	data:{"serviceType":2,"beginTime":stime,"endTime":etime},
	        dataType:"json",
	        success:function(data){
	    		var countList = [];
	    		var list = data.countList;
	    		for(var i = 0; i < list.length; i++){
	    			//alert(list[i].count);
	    			countList.push(list[i].count);
	    		}
	    		countList.reverse();
	    		option2 = {
	    				tooltip : {
							show:true,
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
					        }
					    },
					    
					    grid: { // 控制图的大小，调整下面这些值就可以，
	    			    	 y: 10,
	    		             x: 150,
	    		             x2: 50,
	    		             y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
	    		             borderColor:'#fff'
	    		         },
	    		         calculable : false,
	    		         xAxis:  {
	     			        type: 'value',
	     			        show:false,
	     			        splitLine:{
	     			    		show:false
	     			    	},
	     			    	axisLabel:{  
	     			            show:false
	     			        }, 
	     			        axisLine:{
	     			        	show:false
	     			        }
	     			    },
					    yAxis: {
					        type: 'category',
					        data: ['获取订单结果报告','获取订单结果','获取订单(任务)状态','订单(任务)操作','创建木马检测订单'],
					        splitLine:{
					    		show:false
					    	}
					    },
					    series: [
					        {
					            name: '数量',
					            type: 'bar',
					            //stack: '总量',
					            itemStyle : { normal: {label : {show: true, position: 'right'}}},
					            data: countList
					        }
					    ]
					};
	    		
	    			myChart2.setOption(option2);
	        	},
	        });

	
	//网页篡改监测API
	var myChart3 = ec.init(document.getElementById('api3'));
	 //后台获取数据
    $.ajax({
    	type : "post",
    	url:"adminAPICount.html",
    	data:{"serviceType":3,"beginTime":stime,"endTime":etime},
        dataType:"json",
        success:function(data){
    		var countList = [];
    		var list = data.countList;
    		for(var i = 0; i < list.length; i++){
    			//alert(list[i].count);
    			countList.push(list[i].count);
    		}
    		countList.reverse();
    		option3 = {
    				tooltip : {
						show:true,
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    
				    grid: { // 控制图的大小，调整下面这些值就可以，
   			    	 y: 10,
   		             x: 150,
   		             x2: 50,
   		             y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
   		             borderColor:'#fff'	
   		         	},
   		         xAxis:  {
    			        type: 'value',
    			        show:false,
    			        splitLine:{
    			    		show:false
    			    	},
    			    	axisLabel:{  
    			            show:false
    			        }, 
    			        axisLine:{
    			        	show:false
    			        }
    			    },
				    yAxis: {
				        type: 'category',
				        data: ['获取订单结果报告','获取订单结果','获取订单(任务)状态','订单(任务)操作','创建网页篡改监测订单'],
				        splitLine:{
				    		show:false
				    	}
				    },
				    series: [
				        {
				            name: '数量',
				            type: 'bar',
				            //stack: '总量',
				            itemStyle : { normal: {label : {show: true, position: 'right'}}},
				            data: countList
				        }
				    ]
				};
    		
    			myChart3.setOption(option3);
        	},
        });
    
	//网页敏感内容监测API
	var myChart4 = ec.init(document.getElementById('api4'));
	 //后台获取数据
    $.ajax({
    	type : "post",
    	url:"adminAPICount.html",
    	data:{"serviceType":4,"beginTime":stime,"endTime":etime},
        dataType:"json",
        success:function(data){
    		var countList = [];
    		var list = data.countList;
    		for(var i = 0; i < list.length; i++){
    			//alert(list[i].count);
    			countList.push(list[i].count);
    		}
    		countList.reverse();
    		option4 = {
    				tooltip : {
						show:true,
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    
				    grid: { // 控制图的大小，调整下面这些值就可以，
   			    	 y: 10,
   		             x: 150,
   		             x2: 50,
   		             y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
   		             borderColor:'#fff'
   		         	},
   		         xAxis:  {
    			        type: 'value',
    			        show:false,
    			        splitLine:{
    			    		show:false
    			    	},
    			    	axisLabel:{  
    			            show:false
    			        }, 
    			        axisLine:{
    			        	show:false
    			        }
    			    },
				    yAxis: {
				        type: 'category',
				        data: ['获取订单结果报告','获取订单结果','获取订单(任务)状态','订单(任务)操作','创建网页监测订单'],
				        splitLine:{
				    		show:false
				    	}
				    },
				    series: [
				        {
				            name: '数量',
				            type: 'bar',
				            //stack: '总量',
				            itemStyle : { normal: {label : {show: true, position: 'right'}}},
				            data: countList
				        }
				    ]
				};
    		
    			myChart4.setOption(option4);
        	},
        });
	
	//网站可用性监测API
	var myChart5 = ec.init(document.getElementById('api5'));
	 //后台获取数据
    $.ajax({
    	type : "post",
    	url:"adminAPICount.html",
    	data:{"serviceType":5,"beginTime":stime,"endTime":etime},
        dataType:"json",
        success:function(data){
    		var countList = [];
    		var list = data.countList;
    		for(var i = 0; i < list.length; i++){
    			//alert(list[i].count);
    			countList.push(list[i].count);
    		}
    		countList.reverse();
    		option5 = {
    				tooltip : {
						show:true,
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    
				    grid: { // 控制图的大小，调整下面这些值就可以，
   			    	 y: 10,
   		             x: 150,
   		             x2: 50,
   		             y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
   		             borderColor:'#fff'
   		         	},
   		         xAxis:  {
    			        type: 'value',
    			        show:false,
    			        splitLine:{
    			    		show:false
    			    	},
    			    	axisLabel:{  
    			            show:false
    			        }, 
    			        axisLine:{
    			        	show:false
    			        }
    			    },
				    yAxis: {
				        type: 'category',
				        data: ['获取订单结果报告','获取订单结果','获取订单(任务)状态','订单(任务)操作','创建可用性监测订单'],
				        splitLine:{
				    		show:false
				    	}
				    },
				    series: [
				        {
				            name: '数量',
				            type: 'bar',
				            //stack: '总量',
				            itemStyle : { normal: {label : {show: true, position: 'right'}}},
				            data: countList
				        }
				    ]
				};
    		
    			myChart5.setOption(option5);
        	},
        });
    
	//网站可用性监测API
	var myChart6 = ec.init(document.getElementById('api6'));
	 //后台获取数据
    $.ajax({
    	type : "post",
    	url:"adminAPICount.html",
    	data:{"serviceType":100,"beginTime":stime,"endTime":etime},
        dataType:"json",
        success:function(data){
    		var countList = [];
    		var list = data.countList;
    		for(var i = 0; i < list.length; i++){
    			//alert(list[i].count);
    			countList.push(list[i].count);
    		}
    		countList.reverse();
    		option6 = {
    				tooltip : {
						show:true,
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    
				    grid: { // 控制图的大小，调整下面这些值就可以，
   			    	 y: 10,
   		             x: 150,
   		             x2: 50,
   		             y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
   		             borderColor:'#fff'
   		         	},
   		         xAxis:  {
    			        type: 'value',
    			        show:false,
    			        splitLine:{
    			    		show:false
    			    	},
    			    	axisLabel:{  
    			            show:false
    			        }, 
    			        axisLine:{
    			        	show:false
    			        }
    			    },
				    yAxis: {
				        type: 'category',
				        data: ['获取订单结果报告','获取订单结果','获取订单(任务)状态','订单(任务)操作','创建可用性监测订单'],
				        splitLine:{
				    		show:false
				    	}
				    },
				    series: [
				        {
				            name: '数量',
				            type: 'bar',
				            //stack: '总量',
				            itemStyle : { normal: {label : {show: true, position: 'right'}}},
				            data: countList
				        }
				    ]
				};
    		
    			myChart6.setOption(option6);
        	},
        });
	});	
}