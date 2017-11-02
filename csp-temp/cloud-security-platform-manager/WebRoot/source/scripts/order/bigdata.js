var lastdayArray = [];
$(function(){
	//最近一小时内漏洞跟踪
	var myChart0 = echarts.init(document.getElementById('wafOneHour'));
	   $.ajax({
	    	type : "post",
	    	url:"getWafOneHour.html",
	        dataType:"json",
	        success:function(obj){
		   		var option0 = {
		   			  /*  title : {
		   	        text: '南丁格尔玫瑰图',
		   	        subtext: '纯属虚构',
		   	        x:'center'
		   	    },*/
		   	    tooltip : {
		   	        trigger: 'item',
		   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
		   	    },
		   	    legend: {
		   	        x : 'center',
		   	        y : 'bottom',
		   	        data:['rose1','rose2','rose3','rose4','rose5','rose6','rose7','rose8']
		   	    },
		   	  /*  toolbox: {
		   	        show : true,
		   	        feature : {
		   	            mark : {show: true},
		   	            dataView : {show: true, readOnly: false},
		   	            magicType : {
		   	                show: true,
		   	                type: ['pie', 'funnel']
		   	            },
		   	            restore : {show: true},
		   	            saveAsImage : {show: true}
		   	        }
		   	    },*/
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
	
	//最近一小时内漏洞跟踪
	//var imgPath = getRootPath() + "/source/images/rose1.png";
	//alert(imgPath);
	var myChart1 = echarts.init(document.getElementById('serviceUseInfo'));
	   $.ajax({
	    	type : "post",
	    	url:"getVulnscanAlarmOneHour.html",
	        dataType:"json",
	        success:function(obj){
		   		var option1 = {
		   			    title : {
		   	        text: '最近一小时内漏洞跟踪',
		   	        //subtext: '纯属虚构',
		   	        x:'center'
		   	    },
		   		//renderAsImage:imgPath,
		   	    tooltip : {
		   	        trigger: 'item',
		   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
		   	    },
/*		   	    legend: {
		   	        x : 'center',
		   	        y : 'bottom',
		   	        data:['rose1','rose2','rose3','rose4','rose5','rose6','rose7','rose8']
		   	    },*/
		   	   /* toolbox: {
		   	        show : true,
		   	        feature : {
		   	            mark : {show: true},
		   	            dataView : {show: true, readOnly: false},
		   	            magicType : {
		   	                show: true,
		   	                type: ['pie', 'funnel']
		   	            },
		   	            restore : {show: true},
		   	            saveAsImage : {show: true}
		   	        }
		   	    },*/
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

/*	   //5、	重大漏洞分布视图
	var myChart2 = echarts.init(document.getElementById('vulnscanAlarmTOP20'));
	   $.ajax({
	    	type : "post",
	    	url:"getVulnscanAlarmTOP20.html",
	        dataType:"json",
	        success:function(obj){
		   		var option2 = {
		   			 backgroundColor: '#2c343c',

		   		    title: {
		   		        text: 'Customized Pie',
		   		        left: 'center',
		   		        top: 20,
		   		        textStyle: {
		   		            color: '#ccc'
		   		        }
		   		    },

		   		    tooltip : {
		   		        trigger: 'item',
		   		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		   		    },

		   		    visualMap: {
		   		        show: false,
		   		        min: 80,
		   		        max: 600,
		   		        inRange: {
		   		            colorLightness: [0, 1]
		   		        }
		   		    },
		   		    series : [
		   		        {
		   		            name:'访问来源',
		   		            type:'pie',
		   		            radius : '55%',
		   		            center: ['50%', '50%'],
		   		            data:obj.dataArray.sort(function (a, b) { return a.value - b.value}),
		   		            roseType: 'angle',
		   		            label: {
		   		                normal: {
		   		                    textStyle: {
		   		                        color: 'rgba(255, 255, 255, 0.3)'
		   		                    }
		   		                }
		   		            },
		   		            labelLine: {
		   		                normal: {
		   		                    lineStyle: {
		   		                        color: 'rgba(255, 255, 255, 0.3)'
		   		                    },
		   		                    smooth: 0.2,
		   		                    length: 10,
		   		                    length2: 20
		   		                }
		   		            },
		   		            itemStyle: {
		   		                normal: {
		   		            		color: '#1c3',
		   		                    shadowBlur: 200,
		   		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		   		                }
		   		            }
		   		        }
		   		    ]
		   	};
		   		myChart2.setOption(option2);
	     },

	});*/
	 
	   //a)	最近6个月各等级漏洞分布
	var myChart3 = echarts.init(document.getElementById('vulnscanAlarmByLevelMonth6'));
	   $.ajax({
	    	type : "post",
	    	url:"getVulnscanAlarmByLevelMonth6.html",
	        dataType:"json",
	        success:function(obj){
		   		var option3 = {
		   			 title : {
		   		        text: '最\n近\n六\n个\n月\n漏\n洞\n等\n级\n分\n布\n',
		   		        top:150
		   		        //subtext: '纯属虚构'
		   		    },
		   			 tooltip : {
		   	        trigger: 'axis',
		   	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		   	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		   	        }
		   	    },
		   	    legend: {
		   	        data:obj.levelList
		   	    },
		   	    grid: {
		   	        left: '3%',
		   	        right: '4%',
		   	        bottom: '3%',
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
	   
	   //用户行业分布
	var myChart5 = echarts.init(document.getElementById('service1OneHour'));
	   $.ajax({
	    	type : "post",
	    	url:"getIndustryStatistics.html",
	        dataType:"json",
	        success:function(obj){
		   		var option5 = {
	   			 title : {
		   		        text: '用\n户\n行\n业\n分\n布\n',
		   		        top:200
		   		        //subtext: '纯属虚构'
		   		},
		   	    tooltip : {
		   	        trigger: 'axis'
		   	    },
		   	    legend: {
		   	        data:['各行业注册用户数','已下订单数']
		   	    },
		   	 
		   	    //calculable : true,
		   	    xAxis : [
		   	        {
		   	            type : 'category',
		   	            offset:5,
		   	         axisLabel:{
	                         interval:0,
	                         rotate:45,
	                         margin:0,
	                         textStyle:{
	                             color:"#222"
	                         }
	                     },
		   	            data : obj.industryList
		   	        }
		   	    ],
		   	    grid: { // 控制图的大小，调整下面这些值就可以，
		             x: 55,
		             x2: 100,
		             y2: 150,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
		        },
		   	    yAxis : [
		   	        {
		   	            type : 'value'
		   	        }
		   	    ],
		   	    series : [
		   	        {
		   	            name:'各行业注册用户数',
		   	            type:'bar',
		   	            data:obj.userList,
		   	            markPoint : {
		   	                data : [
		   	                    {type : 'max', name: '最大值'},
		   	                    {type : 'min', name: '最小值'}
		   	                ]
		   	            }
		   	        },
		   	        {
		   	            name:'已下订单数',
		   	            type:'bar',
		   	            data:obj.orderList,
		   	            markPoint : {
		   	        		data : [
		   	                    {type : 'max', name: '最大值'},
		   	                    {type : 'min', name: '最小值'}
		   	                ]
		   	            }
		   	        }
		   	    ]
		   	};
		   		myChart5.setOption(option5);
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
			   	        text: '订\n单\n集\n中\n的\n时\n间\n点\n',
			   	        top:200
			   	    },
			   	    tooltip : {
			   	        trigger: 'axis'
			   	    },
			   	    legend: {
			   	        data:obj.servNameList
			   	    },
			   	  
			   	    grid: {
			   	        left: '3%',
			   	        right: '4%',
			   	        bottom: '10%',
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
			   		                         /*formatter: function(value) 
				   		                     { 
			   		                        	 //alert(value);
			   		                        	// alert(value.substring(8,10));
			   		                        	 if(value.substring(8,10)=='30'||value.substring(8,10)=='31'){
			   		                        		 return value; 
			   		                        	 }
			   		                        	 //return value+”kg”; 
				   		                     } */
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
			   	    	/*[
			   	        {
			   	            name:'邮件营销',
			   	            type:'line',
			   	            stack: '总量',
			   	            areaStyle: {normal: {}},
			   	            data:[120, 132, 101, 134, 90, 230, 210]
			   	        },
			   	        {
			   	            name:'联盟广告',
			   	            type:'line',
			   	            stack: '总量',
			   	            areaStyle: {normal: {}},
			   	            data:[220, 182, 191, 234, 290, 330, 310]
			   	        },
			   	        {
			   	            name:'视频广告',
			   	            type:'line',
			   	            stack: '总量',
			   	            areaStyle: {normal: {}},
			   	            data:[150, 232, 201, 154, 190, 330, 410]
			   	        },
			   	        {
			   	            name:'直接访问',
			   	            type:'line',
			   	            stack: '总量',
			   	            areaStyle: {normal: {}},
			   	            data:[320, 332, 301, 334, 390, 330, 320]
			   	        },
			   	        {
			   	            name:'搜索引擎',
			   	            type:'line',
			   	            stack: '总量',
			   	            label: {
			   	                normal: {
			   	                    show: true,
			   	                    position: 'top'
			   	                }
			   	            },
			   	            areaStyle: {normal: {}},
			   	            data:[820, 932, 901, 934, 1290, 1330, 1320]
			   	        }
			   	    ]*/
			   	};
			   		myChart6.setOption(option6);
		     },

		});
		   

		 //同一网站三次结果中同一漏洞的重复率：左图为所有网站中出现过连续三次结果中有同一漏洞告警的网站占比情况
			var myChart7 = echarts.init(document.getElementById('assetPercent'));
			   $.ajax({
			    	type : "post",
			    	url:"getAssetPercent.html",
			        dataType:"json",
			        success:function(obj){
				   		var option7 = {
				   				title : {
				   			        text: '网站漏洞重复率',
				   			        //subtext: '纯属虚构',
				   			        x:'center'
				   			    },
				   			    tooltip : {
				   			        trigger: 'item',
				   			        formatter: "{a} <br/>{b} : {c} ({d}%)"
				   			    },
				   			   /* legend: {
				   			        orient: 'vertical',
				   			        left: 'left',
				   			        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
				   			    },*/
				   			    series : [
				   			        {
				   			            name: '访问来源',
				   			            type: 'pie',
				   			            radius : '55%',
				   			            center: ['50%', '60%'],
				   			            data:obj.jsonList,
				   			            itemStyle: {
				   			                emphasis: {
				   			                    shadowBlur: 10,
				   			                    shadowOffsetX: 0,
				   			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				   			                }
				   			            }
				   			        }
				   			    ]
				   	};
				   		myChart7.setOption(option7);
			     },

			});   
});

function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}
