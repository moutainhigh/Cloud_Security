var Months = ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"];
var stime;
var etime;
var serviceList = ['漏洞扫描','木马监测','可用性','篡改','关键字'];
var timeList = ['00:00-04:00','04:00-08:00','8:00-12:00','12:00-16:00','16:00-20:00','20:00-24:00'];
var timeList1 = ['01:00','02:00','03:00','04:00','05:00','06:00','07:00','08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00','24:00'];
var nameList1 = ['订单创建','订单操作','获取状态','获取结果','获取报告'];
$(function(){
	$("#analysisId").delegate(this,'click',function(){
		stime= $("#begin_date1").val();
		etime= $("#end_date1").val();
		analysisAPI();
	  	analysisAPIUser();

	});
	//查看完整列表
	$('#allAPIUsers').delegate(this,'click',function(){	
		$("#apiUserCountList").empty();
	    $.ajax({
	    	type : "post",
	    	url:"getAllAPIUserList.html",
	    	data:{"beginTime":stime,"endTime":etime},
	        dataType:"json",
	        success:function(data){
	    		var list = data.countList;
	    		for(var i = 0; i < list.length; i++){
	    			  
	    			var temp = "<tr>"+
	    		    "<td class='t_username'>"+(i+1)+"</td>"+
                  	"<td class='t_username'>"+list[i].name+"</td>"+
                  	"<td class='t_username'>"+list[i].count1+"</td>"+                    
                  	"</tr>";
	    			$("#apiUserCountList").append(temp);
	    		}
	    	},
	    });
		$(".addMsg").html("");
		$('.shade').show();
		$('#revise').show();
		$('html').css({overflow:"hidden"});
	})
		//关闭按钮
	
	$('.modelclose').click(function () {
			
            $('#revise').hide();
            $('.shade').hide();
            $('html').css({
                overflow: 'auto'
            })
    })
    
	//createDemos();	
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
	analysisAPIUser();
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
	  	analysisAPIUser();
  		/*var timer=setTimeout(function(){
	  		//8秒恢复默认选中区域
	  		dateSilderObj.dateRangeSlider("values", new Date(new Date().getFullYear(), 0, 1), new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()));
	  		//analysisAPI();
	  	}, 5000);
	  	*/

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
    			var map = {};
    			map['value']=list[i].count;
    			map['name']=nameList1[i];
    			countList.push(map);
    		}
    		option1 = {
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
	    	            center: ['48.5%', '50%'],
	    	            itemStyle : { normal: {label : {show: true, position: 'outer',formatter: "{b}:{c}"}}},
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
	    			var map = {};
	    			map['value']=list[i].count;
	    			map['name']=nameList1[i];
	    			countList.push(map);
	    		}
	    		option2 = {
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
	    	            center: ['48.5%', '50%'],
	    	            itemStyle : { normal: {label : {show: true, position: 'outer',formatter: "{b}:{c}"}}},
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
    			var map = {};
    			map['value']=list[i].count;
    			map['name']=nameList1[i];
    			countList.push(map);
    		}
    		option3 = {
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
    	            center: ['48.5%', '50%'],
    	            itemStyle : { normal: {label : {show: true, position: 'outer',formatter: "{b}:{c}"}}},
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
    			var map = {};
    			map['value']=list[i].count;
    			map['name']=nameList1[i];
    			countList.push(map);
    		}
    		option4 = {
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
    	            center: ['48.5%', '50%'],
    	            itemStyle : { normal: {label : {show: true, position: 'outer',formatter: "{b}:{c}"}}},
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
    			var map = {};
    			map['value']=list[i].count;
    			map['name']=nameList1[i];
    			countList.push(map);
    		}
    		option5 = {
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
    	            center: ['48.5%', '50%'],
    	            itemStyle : { normal: {label : {show: true, position: 'outer',formatter: "{b}:{c}"}}},
    	            data: countList
    	        }
    	        ]
    			};
    		
    			myChart5.setOption(option5);
        	},
        });
    
	//回话管理API
    var nameList2 = ['获取回话令牌','注销回话令牌'];
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
    			var map = {};
    			map['value']=list[i].count;
    			map['name']=nameList2[i];
    			countList.push(map);
    		}
    		option6 = {
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
    	            center: ['48.5%', '50%'],
    	            itemStyle : { normal: {label : {show: true, position: 'outer',formatter: "{b}:{c}"}}},
    	            data: countList
    	        }
    	        ]
    			};
    		
    			myChart6.setOption(option6);
        	},
        });
    
    	//API服务使用时段统计

	var myChart7 = ec.init(document.getElementById('apiUseBar'));
	 //后台获取数据
	   $.ajax({
	   	type : "post",
	   	url:"adminAPIUseTimes.html",
	   	data:{"beginTime":stime,"endTime":etime},
	       dataType:"json",
	       success:function(data){
	   		var countList = [];
	   		var list = data.countList;
	   		//服务列表
	   		for(var j=0;j<serviceList.length;j++){

	   			var valueList = [];
	   			var map = {};
	   			map['name']=serviceList[j];
	   			map['type']='bar';
	   			map['stack']='总量';
	   			var map1 = {};
				map1['show']=false;
	   			//map1['show']=true;
	   			map1['position']='inside';
	   			var map2 = {};
	   			map2['label']=map1;
	   			//itemStyle: {normal: {label:{show:true,position:'inside',formatter:function(a,b,c){
	   			var itemStyleMap = {};
	   			itemStyleMap['normal']=map2;
	   			map['itemStyle']=itemStyleMap;
   				for(var k = 0; k < timeList.length; k++){
   		   			var timeFlag = false;//该时间段是否存在
   		   			for(var i = 0; i < list.length; i++){
	   					if(list[i].timevalue==timeList[k] && list[i].service_type==j+1){
	   						valueList.push(list[i].counts);
	   						timeFlag = true;
	   					}
			   			
   		   			}
	   		   		if(!timeFlag){
		   				valueList.push(0);
		   			}
	   		   			
		   		}
	   			map['data']=valueList;
	   			countList.push(map);
	   		}
	   		
	   		option7 = {
	   				tooltip : {
	   	        trigger: 'axis',
	   	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	   	            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
	   	        }
	   	    },
	   	    legend: {
	   	        data:serviceList
	   	    },

	   		  grid: { // 控制图的大小，调整下面这些值就可以，
		    	 y: 30,
	             x: 30,
	             x2: 50,
	             y2: 20,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
	             left:'10%',
	             right:'10%',
	             borderColor:'#fff'
	         },
	   	    calculable : false,
	   	    yAxis : [
	   	        {
	   	            type : 'value',
	   	            axisLabel:{  
			            show:false
			        }, 
			        axisLine:{
			        	show:false
			        },
		   	         splitLine:{
			    		show:false
			    	}
	   	        }
	   	    ],
	   	    xAxis : [
	   	        {
	   	            type : 'category',
	   	            splitLine:{
			    		show:false
			    	},
			    	axisLine:{
			    		onZero:true
			    	},
			    	axisLabel:{
		                 //X轴刻度配置
		                 interval:0 //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
		            },
	   	            data : timeList
	   	        }
	   	    ],
	   	    series : countList
	   			};
	   		
	   			myChart7.setOption(option7);
	       	},
	   	});
	   		
	   			//最近7日使用趋势图
	   			var myChart8 = ec.init(document.getElementById('apiUseLine'));
	   		 //后台获取数据
	   		   $.ajax({
	   		   	type : "post",
	   		   	url:"adminAPICountLast7Days.html",
	   		   	data:{},
	   		       dataType:"json",
	   		       success:function(data){
	   		   		var timeList = data.dateList;
	   		   		timeList.reverse();
	   		   		var countList = [];
	   		   		var list = data.countList;
	   		   		for(var j = 0; j < serviceList.length; j++){
	   		   			var map = {};
	   		   			map['name']=serviceList[j];
	   		   			map['type']='line';
	   		   			var valueList = [];
		   		  		for(var i = 0; i < timeList.length; i++){
		   		   			var hasFlag = false;//是否该日期下有值
		   		  			for(var k = 0; k < list.length; k++){
		   		  				if(list[k].service_type==j+1 && list[k].days==timeList[i]){
		   		  				    hasFlag = true;
		   		  					valueList.push(list[k].count1);
		   		  				}
			   		  			
		   		  			}
			   		  		if(!hasFlag){
		   		  				valueList.push(0);
		   		  			}
		   		   		}
		   		  		map['data']=valueList;
		   		  		countList.push(map);
	   		   		}
	   		  
	   		   		option8 = {
	   		   			tooltip : {
	   		         trigger: 'axis'
	   		     },
	   		     legend: {
	   		         data:serviceList
	   		     },
	   		    
	   		     calculable : false,
	   		     xAxis : [
	   		         {
	   		             type : 'category',
	   		             boundaryGap : false,
	   		             axisLabel:{
			                 //X轴刻度配置
			                 interval:0 //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
			             },
		   		          
	   		             data : timeList
	   		         }
	   		     ],
	   		     yAxis : [
	   		         {
	   		             type : 'value'
	   		         }
	   		     ],
	   		  grid: { // 控制图的大小，调整下面这些值就可以，
			    	 y: 30,
		             x: 50,
		             x2: 30,
		             y2: 20,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
		            // left:'10%',
		            // right:'10%',
		             borderColor:'#fff'
		         },
	   		     series : countList
	   		   	};
	   		   		
	   		   			myChart8.setOption(option8);
	   		      	},
	   		  });


	});	
}
//tab2
function analysisAPIUser(){
	require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	function (ec) {//回调函数
		   		var myChart9 = ec.init(document.getElementById('userBarTop5'));
		   	    //后台获取数据
		   	   $.ajax({
		   	   	type : "post",
		   	   	url:"adminAPIUserCountTop5.html",
		   	   	data:{"beginTime":stime,"endTime":etime},
		   	       dataType:"json",
		   	       success:function(data){
		   	   		var userList = [];
		   	   		var countList = [];
		   	   		var list = data.countList;
		   	   		for(var i = 0; i < list.length; i++){
		   	   			userList.push(list[i].name);
		   	   			countList.push(list[i].count1);
		   	   		}
		   	   		userList.reverse();
		   	   		countList.reverse();
		   	   		var option9 = {
		   	   			tooltip : {
							show:true,
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
					        }
				   		 },
				    
					   	 grid: { // 控制图的大小，调整下面这些值就可以，
					    	 y: 10,
				             x: 100,
				             x2: 80,
				             y2: 20,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
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
					        data: userList,
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
		   	   		
		   	   			myChart9.setOption(option9);
		   	    	},
		   	 });
		   	   
		   		var myChart10 = ec.init(document.getElementById('apiCountForUser'));
		   	    //后台获取数据
		   		var userName=$("#searchName").val();
		   	   $.ajax({
		   	   	type : "post",
		   	   	url:"getAPICountByUser.html",
		   	   	data:{"beginTime":stime,"endTime":etime,"userName":userName},
		   	       dataType:"json",
		   	       success:function(data){
		   	   		var countList = [];
		   	   		var list = data.countList;
		   	   		for(var i = 0; i < list.length; i++){
			   	   		var map = {};
		    			map['value']=list[i].count;
		    			map['name']=serviceList[i];
		    			countList.push(map);
		   	   		}
		   	   		
		   	   		var option10 = {
		   	   			tooltip : {
		    	        	trigger: 'item',
		    	        	formatter: "{a} <br/>{b} : {c} ({d}%)"
	    	    		}, 
			    	    legend: {
					        orient : 'vertical',
					        x : 'left',
					        data:serviceList
					    },   	    
			    	    //calculable : true,
			    	    series : [
			    	        {
			    	            name:'数量',
			    	            type:'pie',
			    	            radius : '50%',
			    	            center: ['48.5%', '50%'],
			    	            itemStyle : { normal: {label : {show: true, position: 'outer',formatter: "{b}:{c}"}}},
			    	            data: countList
			    	        }
			    	        ]
		   	 		};
		   	   		
		   	   			myChart10.setOption(option10);
		   	    	},
		   	 }); 
		   	   
		   	   
		    	//API服务使用时段统计

		   	var myChart11 = ec.init(document.getElementById('userBar'));
		   	 //后台获取数据
		   	   $.ajax({
		   	   	type : "post",
		   	   	url:"adminAPITimesByUser.html",
		   	   	data:{"beginTime":stime,"endTime":etime,"userName":userName},
		   	       dataType:"json",
		   	       success:function(data){
		   	   		var countList = [];
		   	   		var list = data.countList;
		   	   		for (var j = 0;j < serviceList.length;j++) {
		   	   			var valueList = [];
			   			var map = {};
			   			map['name']=serviceList[j];
			   			map['type']='bar';
			   			map['stack']='总量';
			   			
			   			var map1 = {};
						map1['show']=false;
			   			//map1['show']=true;
			   			map1['position']='inside';
			   			var map2 = {};
			   			map2['label']=map1;
			   			var itemStyleMap = {};
			   			itemStyleMap['normal']=map2;
			   			map['itemStyle']=itemStyleMap;
			   			
	      				for(var k = 0; k < timeList1.length; k++){
	      		   			var timeFlag = false;//该时间段是否存在
	      		   			for(var i = 0; i < list.length; i++){
		   	   					if(list[i].timevalue==timeList1[k]&& list[i].service_type==j+1){
		   	   						valueList.push(list[i].counts);
		   	   						timeFlag = true;
		   	   						break;
		   	   					}
	   			   			
	      		   			}
		   	   		   		if(!timeFlag){
		   	   		   			valueList.push(0);
		   		   			}
	   	   		   			
	   		   			}	   	   		
		   		   		map['data']=valueList;
	   					countList.push(map);
		   	   		}
		   	   		
		   	   		option11 = {
		   	   			tooltip : {
							show:true,
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
					        }
				    	},
					    legend: {
				   	        data:serviceList
				   	    },
					    calculable : false,
					    grid: { // 控制图的大小，调整下面这些值就可以，
					    	 y: 20,
				             x: 50,
				             x2: 50,
				             y2: 20,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
				             borderColor:'#fff'
				         },
					    xAxis : [
					        {
					            type : 'category',
					            splitLine:{
						    		show:false
						    	},
						    	axisLine:{
						    		onZero:true
						    	},
					            axisLabel:{
					                 //X轴刻度配置
					                 interval:0 //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
					            },
					            data : timeList1
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
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
					        }
					    ],
				    	series :countList
		   	   		};
		   	   		
		   	   		myChart11.setOption(option11);
		   	       	},
		   	   	});
    });
}
