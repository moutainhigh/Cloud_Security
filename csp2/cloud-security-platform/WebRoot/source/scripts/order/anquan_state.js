var lastdayArray = [];
$(function(){
	//最近一小时内waf
	var myChart0 = echarts.init(document.getElementById('wafOneHour'));
	   $.ajax({
	    	type : "post",
	    	url:"getWafOneHour.html",
	        dataType:"json",
	        success:function(obj){
		   		var option0 = {
		   		 title : {
		   	        text: '最近一小时内攻击',
		   	        x:'center'
		   	    },
		   	    tooltip : {
		   	        trigger: 'item',
		   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
		   	    },
		   	  
		   	    calculable : true,
		   	    series : [
		   	        
		   	        {
		   	            name:'面积模式',
		   	            type:'pie',
		   	            radius : [20, 180],
		   	            center : ['50%', '50%'],
		   	            roseType : 'area',
		   	            data:obj.dataArray
		   	        }
		   	    ]
		   	};
		   		myChart0.setOption(option0);
	     },

	});
	//最近一小时漏洞
	var myChart1 = echarts.init(document.getElementById('vulnscanAlarmOneHour'));
	   $.ajax({
	    	type : "post",
	    	url:"getVulnscanAlarmOneHour.html",
	        dataType:"json",
	        success:function(obj){
		   		var option1 = {
		   			title : {
		   	        text: '最近一小时内漏洞跟踪',
		   	        x:'center'
		   	    },

		   	    tooltip : {
		   	        trigger: 'item',
		   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
		   	    },

		   	    calculable : true,
		   	    series : [
		   	        
		   	        {
		   	            name:'面积模式',
		   	            type:'pie',
		   	            radius : [20, 180],
		   	            center : ['50%', '50%'],
		   	            roseType : 'area',
		   	         label: {
		   	                normal: {
		   	                    show: true
		   	                },
		   	                emphasis: {
		   	                    show: true
		   	                }
		   	            },
		   	            lableLine: {
		   	                normal: {
		   	                    show: true
		   	                },
		   	                emphasis: {
		   	                    show: true
		   	                }
		   	            },
		   	            data:obj.dataArray
		   	        }
		   	    ]
		   	};
		   		myChart1.setOption(option1);
	     },

	});
	  
	   //最近六个月等级漏洞分布
	   var myChart3 = echarts.init(document.getElementById('vulnscanAlarmByLevelMonth6'));
	   $.ajax({
	    	type : "post",
	    	url:"getVulnscanAlarmByLevelMonth6.html",
	        dataType:"json",
	        success:function(obj){
		   		var option3 = {
		   			 title : {
		   		        //text: '最\n近\n六\n个\n月\n漏\n洞\n等\n级\n分\n布\n',
		   				 text:'近六个月漏洞等级分布',
		   		         x:'center'
		   		    },
		   			 tooltip : {
		   	        trigger: 'axis',
		   	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		   	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		   	        }
		   	    },
		   	    /*legend: {
		   	        data:obj.levelList
		   	    },*/
		   	    grid: {
		   	        left: '5%',
		   	        right: '5%',
		 
		   	        bottom: '1%',
		   	        containLabel: true
		   	    },
		   	    xAxis : [
		   	        {
		   	            type : 'category',
		   	            data : obj.monthList
		   	        }
		   	    ],
		   	    yAxis : [
		   	        {
		   	            type : 'value'
		   	           
		   	        }
		   	    ],
		   	    series : obj.dataArray
		   	};
		   		myChart3.setOption(option3);
	     },

	});
	   
		var myChart6 = echarts.init(document.getElementById('orderServiceTimes'));
		   $.ajax({
		    	type : "post",
		    	url:"getOrderServiceTimes.html",
		        dataType:"json",
		        success:function(obj){
			   lastdayArray = obj.lastdayList;
			   		var option6 = {
			   				title: {
			   	        text: '历史订单（一年内）',
			   	        x:'left'
			   	    },
			   	    tooltip : {
			   	        trigger: 'axis'
			   	    },
			   	    legend: {
			   	        data:obj.servNameList
			   	    },
			   	  
			   	    grid: {
			   	        left: '50%',
			   	        right: '1%',
			   	        bottom: '1%',
			   	        containLabel: true
			   	    },
			   	    xAxis : [
			   	        {
			   	            type : 'category',
			   	            boundaryGap : false,
			   	            nameGap:20,
			   	            axisLabel:{
			   		                         interval:0,
			   		                         //rotate:45,
			   		                         margin:4,
			   		                         textStyle:{
			   		                             color:"#222"
			   		                         },

			   		                      formatter: function (value, index) {
			   		                          // 格式化成月/日，只在第一个刻度显示年份
			   		                         // var date = new Date(value);
			   		                          //if(date.getDate()==30||date.getDate()==31){
			   		                        	//var texts = [date.getYear(),(date.getMonth() + 1), date.getDate()];
			   		                        	//var res = texts.join('-');
			   		                        	if(lastdayArray.indexOf(value)!=-1){
			   		                        		return value.substring(0,7);
			   		                        	}
			   		                        	//return texts.join('/');
			   		                          //}
			   		                         /* var texts = [(date.getMonth() + 1), date.getDate()];
			   		                          if (index === 0) {
			   		                              texts.unshift(date.getYear());
			   		                          }
			   		                          return texts.join('/');
			   		                        	if(value.substring(8,10)=='30'||value.substring(8,10)=='31'){
			   		                        		 return value; 
			   		                        	 }*/
			   		                         }
			   		                     },
			   	           data : obj.dayList
			   	        }
			   	    ],
			   	    yAxis : [
			   	        {
			   	            type : 'value'
			   	        }
			   	    ],
			   	    series : obj.seriesList
			   	};
			   		myChart6.setOption(option6);
		     },

		});
		   
		   //订单类型分布
		   var myChart4 = echarts.init(document.getElementById('serviceUseInfoMonth6'));
		   $.ajax({
		    	type : "post",
		    	url:"getServiceUseInfoMonth6.html",
		        dataType:"json",
		        success:function(obj){
			   		var monthList = obj.monthList;
			   		var indicatorList = obj.indicatorList;
			   		var dataArray = obj.dataArray;
			   		var option4 = {
			   			/* title: {
			   	        text: '浏览器占比变化',
			   	        subtext: '纯属虚构',
			   	        x:'right',
			   	        y:'bottom'
			   	    },*/
			   	    tooltip: {
			   	        trigger: 'item',
			   	        backgroundColor : 'rgba(0,0,250,0.2)'
			   	    },
			   	   /* legend: {
			   	        data: (function (){
			   	            var list = [];
			   	            for (var i = 1; i <=28; i++) {
			   	                list.push(i + 2000 + '');
			   	            }
			   	            return list;
			   	        })()
			   	    },*/
			   	   /* visualMap: {
			   	        color: ['red', 'yellow']
			   	    },*/
			   	    radar: {
			   	       indicator : indicatorList
			   	    },
			   	    series : (function (){
			   	        var series = [];
			   	        for (var i = 1; i <= 24; i++) {
			   	        	var list = [];
			   	        	list.push(dataArray[i]);
			   	            series.push({
			   	                name:'浏览器（数据纯属虚构）',
			   	                type: 'radar',
			   	                symbol: 'none',
			   	                itemStyle: {
			   	                    normal: {
			   	                        lineStyle: {
			   	                          width:1
			   	                        }
			   	                    },
			   	                    emphasis : {
			   	                        areaStyle: {color:'rgba(0,250,0,0.3)'}
			   	                    }
			   	                },
			   	                data:list
			   	            });
			   	        }
			   	        return series;
			   	    })()
			   	};
			   		myChart4.setOption(option4);
		     },

		});
});