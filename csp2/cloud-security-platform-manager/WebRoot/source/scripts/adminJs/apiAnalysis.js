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
	var myChart1 = ec.init(document.getElementById('api1'));

	 //后台获取数据
    $.ajax({
    	type : "post",
    	url:"adminAPICount.html",
    	data:{"serviceType":1},
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
    			        trigger: 'axis',
    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    			            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
    			        }
    			    },
    			    legend: {
    			    	textStyle:{
    			    	fontSize:20
    			    },
    			        data: ['web漏洞扫描API']
    			    },
    			    grid: { // 控制图的大小，调整下面这些值就可以，
    		             x: 130,
    		             x2: 50,
    		             y2: 50,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
    		         },
    			    xAxis:  {
    			        type: 'value',
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    yAxis: {
    			        type: 'category',
    			        data: ['获取订单结果报告','获取订单结果','获取订单(任务)状态','订单(任务)操作','创建web漏洞扫描订单'],
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    series: [
    			        {
    			            name: '数量',
    			            type: 'bar',
    			            //stack: '总量',
    			            label: {
    			                normal: {
    			                    show: true,
    			                    position: 'insideRight'
    			                }
    			            },
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
	    	data:{"serviceType":2},
	        dataType:"json",
	        success:function(data){
	    		var countList = [];
	    		var list = data.countList;
	    		for(var i = 0; i < list.length; i++){
	    			//alert(list[i].count);
	    			countList.push(list[i].count);
	    		}
	    		option2 = {
	    			    tooltip : {
	    			        trigger: 'axis',
	    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	    			            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
	    			        }
	    			    },
	    			    legend: {
	    			        data: ['木马检测API']
	    			    },
	    			    /*grid: {
	    			        left: '3%',
	    			        right: '4%',
	    			        bottom: '3%',
	    			        containLabel: true
	    			    },*/
	    			    xAxis:  {
	    			        type: 'value',
	    			        splitLine:{
	    			    		show:false
	    			    	}
	    			    },
	    			    yAxis: {
	    			        type: 'category',
	    			        data: ['创建木马检测订单','订单(任务)操作','获取订单(任务)状态','获取订单结果','获取订单结果报告'],
	    			        splitLine:{
	    			    		show:false
	    			    	}
	    			    },
	    			    series: [
	    			        {
	    			            name: '数量',
	    			            type: 'bar',
	    			            //stack: '总量',
	    			            label: {
	    			                normal: {
	    			                    show: true,
	    			                    position: 'right'
	    			                }
	    			            },
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
    	data:{"serviceType":3},
        dataType:"json",
        success:function(data){
    		var countList = [];
    		var list = data.countList;
    		for(var i = 0; i < list.length; i++){
    			//alert(list[i].count);
    			countList.push(list[i].count);
    		}
    		option3 = {
    			    tooltip : {
    			        trigger: 'axis',
    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    			            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
    			        }
    			    },
    			    legend: {
    			        data: ['网页篡改监测API']
    			    },
    			    /*grid: {
    			        left: '3%',
    			        right: '4%',
    			        bottom: '3%',
    			        containLabel: true
    			    },*/
    			    xAxis:  {
    			        type: 'value',
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    yAxis: {
    			        type: 'category',
    			        data: ['创建网页篡改监测订单','订单(任务)操作','获取订单(任务)状态','获取订单结果','获取订单结果报告'],
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    series: [
    			        {
    			            name: '数量',
    			            type: 'bar',
    			            //stack: '总量',
    			            label: {
    			                normal: {
    			                    show: true,
    			                    position: 'right'
    			                }
    			            },
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
    	data:{"serviceType":4},
        dataType:"json",
        success:function(data){
    		var countList = [];
    		var list = data.countList;
    		for(var i = 0; i < list.length; i++){
    			//alert(list[i].count);
    			countList.push(list[i].count);
    		}
    		option4 = {
    			    tooltip : {
    			        trigger: 'axis',
    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    			            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
    			        }
    			    },
    			    legend: {
    			        data: ['网页敏感内容监测API']
    			    },
    			    /*grid: {
    			        left: '3%',
    			        right: '4%',
    			        bottom: '3%',
    			        containLabel: true
    			    },*/
    			    xAxis:  {
    			        type: 'value',
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    yAxis: {
    			        type: 'category',
    			        data: ['创建网页敏感内容监测订单','订单(任务)操作','获取订单(任务)状态','获取订单结果','获取订单结果报告'],
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    series: [
    			        {
    			            name: '数量',
    			            type: 'bar',
    			            //stack: '总量',
    			            label: {
    			                normal: {
    			                    show: true,
    			                    position: 'right'
    			                }
    			            },
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
    	data:{"serviceType":5},
        dataType:"json",
        success:function(data){
    		var countList = [];
    		var list = data.countList;
    		for(var i = 0; i < list.length; i++){
    			//alert(list[i].count);
    			countList.push(list[i].count);
    		}
    		option5 = {
    			    tooltip : {
    			        trigger: 'axis',
    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    			            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
    			        }
    			    },
    			    legend: {
    			        data: ['网页敏感内容监测API']
    			    },
    			    /*grid: {
    			        left: '3%',
    			        right: '4%',
    			        bottom: '3%',
    			        containLabel: true
    			    },*/
    			    xAxis:  {
    			        type: 'value',
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    yAxis: {
    			        type: 'category',
    			        data: ['创建网页敏感内容监测订单','订单(任务)操作','获取订单(任务)状态','获取订单结果','获取订单结果报告'],
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    series: [
    			        {
    			            name: '数量',
    			            type: 'bar',
    			            //stack: '总量',
    			            label: {
    			                normal: {
    			                    show: true,
    			                    position: 'right'
    			                }
    			            },
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
    	data:{"serviceType":100},
        dataType:"json",
        success:function(data){
    		var countList = [];
    		var list = data.countList;
    		for(var i = 0; i < list.length; i++){
    			//alert(list[i].count);
    			countList.push(list[i].count);
    		}
    		option6 = {
    			    tooltip : {
    			        trigger: 'axis',
    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    			            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
    			        }
    			    },
    			    legend: {
    			        data: ['网页敏感内容监测API']
    			    },
    			    /*grid: {
    			        left: '3%',
    			        right: '4%',
    			        bottom: '3%',
    			        containLabel: true
    			    },*/
    			    xAxis:  {
    			        type: 'value',
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    yAxis: {
    			        type: 'category',
    			        data: ['创建网页敏感内容监测订单','订单(任务)操作','获取订单(任务)状态','获取订单结果','获取订单结果报告'],
    			        splitLine:{
    			    		show:false
    			    	}
    			    },
    			    series: [
    			        {
    			            name: '数量',
    			            type: 'bar',
    			            //stack: '总量',
    			            label: {
    			                normal: {
    			                    show: true,
    			                    position: 'right'
    			                }
    			            },
    			            data: countList
    			        }
    			    ]
    			};
    		
    			myChart6.setOption(option6);
        	},
        });
	});
});