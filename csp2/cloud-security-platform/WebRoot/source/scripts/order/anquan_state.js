var lastdayArray = [];
$(function(){
	//setInterval(function(){
		//最近一小时漏洞
		var myChart1 = echarts.init(document.getElementById('vulnscanAlarmOneHour'));
		   $.ajax({
		    	type : "post",
		    	url:"getVulnscanAlarmOneHour.html",
		        dataType:"json",
		        success:function(obj){
			   		var option1 = {
			   			title : {
			   			textStyle:{
			   				color:'#7aff75',
			   				fontSize:14
			   			},
			   	        text: '最新漏洞类型分布',
			   	        x:'center'
			   	    },

			   	    tooltip : {
			   	        trigger: 'item',
			   	        formatter: "{b} : {c} ({d}%)"
			   	    },

			   	    calculable : true,
			   	    series : [
			   	        
			   	        {
			   	            name:'',
			   	            type:'pie',
			   	            radius : [15, 150],
			   	            center : ['50%', '50%'],
			   	            roseType : 'area',
			   	         label: {
			   	                normal: {
			   	                    show: true,
			   	                   /* formatter: function(params){
			   	                    	var res=params.name;
			   	                    	if(params.name.length>9){
			   	                    		var temp = res.substring(0,9);
			   	                    		var temp2 = res.substr(9);
			   	                    		res= temp + "\n" + temp2;
			   	                    	}
			   	                    	return res;
			   	                    }*/
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
			   	    ],
			   	    color:["#b675ff","#ffb675","#757aff","#ff757a","#f942ff","#fffa75","#42a7ff","#4249ff"]
			   	};
			   	myChart1.setOption(option1);
		     },
		});
		   
		//最近一小时内waf
		var myChart0 = echarts.init(document.getElementById('wafOneHour'));
		   $.ajax({
		    	type : "post",
		    	url:"getWafOneHour.html",
		        dataType:"json",
		        success:function(obj){
			   		var option0 = {
			   		 title : {
			   	        text: '最新攻击类型分布',
			   	        textStyle:{
			   				color:'#7aff75',
			   				fontSize:14
			   			},
			   	        x:'center'
			   	    },
			   	    tooltip : {
			   	        trigger: 'item',
			   	        formatter: "{b} : {c} ({d}%)"
			   	    },
			   	  
			   	    calculable : true,
			   	    series : [			   	        
			   	        {
			   	            name:'漏洞类型',
			   	            type:'pie',
			   	            radius : [15, 150],
			   	            center : ['50%', '50%'],
			   	            roseType : 'area',
				   	        label: {
			   	                normal: {
			   	                    show: true,
			   	                    /*formatter: function(params){
		                        	  var res=params.name;
		                        	  if(params.name.length>9){
		                        		  var temp = res.substring(0,9);
		                        		  var temp2 = res.substr(9);
		                        		  res= temp + "\n" + temp2;
		                        	  }
			                          return res;
			                       }*/
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
			   	    ],
			   	    color:["#b675ff","#ffb675","#757aff","#ff757a","#f942ff","#fffa75","#42a7ff","#4249ff"]
			   	};
			   	myChart0.setOption(option0);
		     },

		});
  	//}, 5000);
		  
	   //最近六个月等级漏洞分布
	   var myChart3 = echarts.init(document.getElementById('vulnscanAlarmByLevelMonth6'));
	   $.ajax({
	    	type : "post",
	    	url:"getVulnscanAlarmByLevelMonth6.html",
	        dataType:"json",
	        success:function(obj){
		   		var option3 = {
		   			 title : {
		   				 text:'漏洞等级分布走势',
		   				 textStyle:{
			   			 color:'#7aff75',
			   			fontSize:14
			   			},
		   		         x:'center'
		   		     },
		   			 tooltip : {
		   	         trigger: 'axis',
		   	         axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		   	             type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		   	         }
		   	    },
		   	    legend: {
		   	        data:obj.levelList,
		   	        y:'bottom',
			   	    textStyle:{
	                    color:"#00faf2"
	                },
		   	        padding:0
		   	    },
		   	    grid: {
		   	        left: '10%',
		   	        right: '0%',		 
		   	        bottom: '15%'
		   	    },
		   	    xAxis : [
		   	        {
		   	            type : 'category',
			   	         axisLabel:{	                         
	                         textStyle:{
	                             color:"#7aff75"
	                         }
	                     },
		   	            data : obj.monthList
		   	        }
		   	    ],
		   	    yAxis : [
		   	        {
		   	            type : 'value',
			   	        axisLabel:{
	                        textStyle:{
	                            color:"#7aff75"
	                        }
	                    }		   	           
		   	        }
		   	    ],
		   	    series : obj.dataArray
		   	};
		   	myChart3.setOption(option3);
	     },

	});
	   
	   //一年内历史订单
		var myChart6 = echarts.init(document.getElementById('orderServiceTimes'));
		   $.ajax({
		    	type : "post",
		    	url:"getOrderServiceTimes.html",
		        dataType:"json",
		        success:function(obj){
			    lastdayArray = obj.lastdayList;
			    var colors = ["#b675ff","#ffb675","#757aff","#ff757a","#f942ff","#fffa75"];
			    var datas = [];
			    for(var i = 0; i < obj.servNameList.length; i++){
			    	var textName = obj.servNameList[i];
			    	datas.push(
			    			{
			    				name:textName,
			    				textStyle:{
			    					color:colors[i]
			    				}
			    			}
			    	);
			    }
			    

			   		var option6 = {
		   				title: {
		   					text: '年度服务订购趋势',
					   	    textStyle:{
					   			color:'#7aff75',
					   			fontSize:14
					   		},
					   	    x:'center'
		   				},
				   	    tooltip : {
				   	        trigger: 'axis'
				   	    },
				   	    legend: {
				   	        data:datas,
				   	        y:'bottom',
					   	    
				   	    },				   	  
				   	    grid: {
				   	        left: '1%',
				   	        right: '3%',
				   	        bottom: '15%',
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
	   		                             color:"#7aff75"
	   		                         },
		   		                     formatter: function (value, index) {
		   		                        if(lastdayArray.indexOf(value)!=-1){
		   		                        	return value.substring(0,7);
		   		                        }
		   		                     }
		   		               },
				   	           data : obj.dayList
				   	        }
				   	    ],
				   	    yAxis : [
				   	        {
				   	            type : 'value',
					   	         axisLabel:{
			                         textStyle:{
			                             color:"#7aff75",
			                             fontSize:14
			                         },
				                 }
				   	        }
				   	    ],
				   	    series : obj.seriesList,
				   	    color:colors
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
			   			 title: {
			   	        text: '服务数量分布',
				   	    textStyle:{
				   			color:'#7aff75',
				   			fontSize:14
			   			},
			   	        x:'150'
			   	    },
			   	    tooltip: {
			   	        trigger: 'item',
			   	        backgroundColor : 'rgba(0,0,250,0.2)'
			   	    },
				   	 legend: {
				         data: (function (){
				        	return monthList;
				         })(),
				         y:'bottom',
				         x:'left',
				         textStyle:{
				   			color:'#00faf2'
				   		 }
				     },
				    /* visualMap: {
				         color: ['red', 'yellow']
				     },*/
			   	    radar: {
			   	       indicator : indicatorList,
			   	       radius:'50%',
			   	       center:['40%', '52%']
			   	    },
			   	    series : (function (){
			   	        var series = [];
			   	        var colors = ["#52a5e5","#eabf6c","#fa7e7d","#6bc770","#806bff","#ddde73"];
			   	        for (var i = 0; i < 6; i++) {
			   	        	var list = [];
			   	        	list.push(dataArray[i]);
			   	            series.push({
			   	                name:'',
			   	                type: 'radar',
			   	                symbol: 'none',
			   	                itemStyle: {
			   	                    normal: {
			   	                        lineStyle: {
			   	                          width:1
			   	                        },
			   	                        color:colors[i]
			   	                    }
			   	                },			   	             
			   	                data:list
			   	            });
			   	        }
			   	        return series;
			   	    })()
			   	};
			   	myChart4.setOption(option4);
		     }
		});
		   
		   //用户行业分布
			var myChart5 = echarts.init(document.getElementById('userIndustry'));
			   $.ajax({
			    	type : "post",
			    	url:"getIndustryStatistics.html",
			        dataType:"json",
			        success:function(obj){
				   		var option5 = {
			   			 title : {
				   		     text: '用户行业分布',
				   		     textStyle:{
				   				 color:'#7aff75',
				   				fontSize:14
				   			 },
			   		         x:'center'
				   		},
				   	    tooltip : {
				   	        trigger: 'axis'
				   	    },
				   	    legend: {
				   	        data:['各行业注册用户数','已下订单数'],
				   	        y:'bottom',
				   	     textStyle:{
                             color:"#00faf2"
                         }
				   	    },
				   	    xAxis : [
				   	        {
				   	            type : 'category',
				   	            offset:5,
				   	            axisLabel:{
			                         interval:0,
			                         rotate:40,
			                         margin:8,			                       
			                         textStyle:{
			                             color:"#7aff75"
			                         }
			                     },
				   	            data : obj.industryList
				   	        }
				   	    ],
				   	    grid: { // 控制图的大小，调整下面这些值就可以，
				             x: 100,
				             x2: 30,
				             y2: 160,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
				        },
				   	    yAxis : [
				   	        {
				   	            type : 'value',
				   	            axisLabel:{		                         
				   	            	textStyle:{
				   	            		color:"#7aff75"
				   	            	}
				   	            }
				   	        }
				   	    ],
				   	    series : [
				   	        {
				   	            name:'各行业注册用户数',
				   	            type:'bar',
				   	            data:obj.userList,
					   	        itemStyle:{
					                normal:{color:'#52a2e5'}
					            }
				   	        },
				   	        {
				   	            name:'已下订单数',
				   	            type:'bar',
				   	            data:obj.orderList,
					   	        itemStyle:{
					                normal:{color:'#5aba5f'}
					            }
				   	        }
				   	    ]
				   	};
				   	myChart5.setOption(option5);
			     },

			});
			   
				 //同一网站三次结果中同一漏洞的重复率：左图为所有网站中出现过连续三次结果中有同一漏洞告警的网站占比情况
/*				var myChart7 = echarts.init(document.getElementById('assetPercent'));
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
					   			        formatter: "{b} : {c} ({d}%)"
					   			    },
					   			
					   			    series : [
					   			        {
					   			            name: '访问来源',
					   			            type: 'pie',
					   			            radius : '55%',
					   			            center: ['50%', '60%'],
					   			            data:obj.jsonList,
					   			         label: {
							   	                normal: {
							   	                    show: true,
							   	                 formatter: function(params){
						                        	 // alert(params.name.length);
						                        	  var res=params.name;
						                        	  if(params.name.length>4){
						                        		  var temp = res.substring(0,4);
						                        		  var temp2 = res.substr(4);
						                        		  res= temp + "\n" + temp2;
						                        	  }
						                          return res;
						                         }
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

				}); */
				   
			   //网站用途分布
/*				var myChart8 = echarts.init(document.getElementById('assetPurpose'));
				 $.ajax({
				    	type : "post",
				    	url:"getAssetPurpose.html",
				        dataType:"json",
				        success:function(obj){
					   		var option8 = {
					   				title : {
					   			        text: '网站漏洞重复率大等于3的行业分布情况',
					   			        textStyle:{
							   				color:'#7aff75'
							   			},
					   			        x:'center'
					   			    },
					   			    tooltip : {
					   			        trigger: 'item',
					   			        formatter: "{b} : {d}%"
					   			    },					   			  
					   			    series : [
					   			        {
					   			            name: '访问来源',
					   			            type: 'pie',
					   			            radius : '75%',
					   			            center: ['50%', '60%'],
					   			            label: {
							   	                normal: {
							   	                    show: true,
								   	                formatter: function(params){
							                        	var res=params.name;
							                        	if(params.name.length>2){
							                        		var temp = res.substring(0,2);
							                        		var temp2 = res.substr(2);
							                        		res= temp + "\n" + temp2;
							                        	}
							                        	return res;
							                        }
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
					   			            data:obj.jsonList,
					   			            itemStyle: {
					   			                emphasis: {
					   			                    shadowBlur: 10,
					   			                    shadowOffsetX: 0,
					   			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					   			                }
					   			            }
					   			        }
					   			    ],
					   		color:["#fa7e7d","#eabf6c","#ddde73","#6bc770","#52a2e5","#806bff"]
					   	};
					   	myChart8.setOption(option8);
				     },

				}); */
				 
				   //最近六个月攻击等级分布
				   var myChart9 = echarts.init(document.getElementById('wafByLevelMonth6'));
				   $.ajax({
				    	type : "post",
				    	url:"getWafByLevelMonth6.html",
				        dataType:"json",
				        success:function(obj){
					   		var option9 = {
					   			 title : {
					   				 text:'攻击等级分布走势',
					   				 textStyle:{
						   				 color:'#7aff75',
						   				fontSize:14
						   			 },
					   		         x:'center'
					   		    },
					   			tooltip : {
					   	        trigger: 'axis',
					   	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					   	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					   	        }
					   	    },
					   	    legend: {
					   	        data:obj.levelList,
					   	        y:'bottom',
						   	    textStyle:{
		                            color:"#00faf2"
		                        },
					   	        padding:0
					   	    },
					   	    grid: {
					   	        left: '10%',
					   	        right: '0%',
					   	        bottom: '15%'
					   	    },
					   	    xAxis : [
					   	        {
					   	            type : 'category',
					   	            axisLabel:{ 
			                        textStyle:{
			                            color:"#7aff75"
			                        }
			                     },
					   	         data : obj.monthList
					   	       }
					   	    ],
					   	    yAxis : [
					   	        {
					   	            type : 'value',
					   	            axisLabel:{			                         
			                        textStyle:{
			                            color:"#7aff75"
			                        }
			                     }
					   	      }
					   	    ],
					   	    series : obj.dataArray
					   	};
					   	myChart9.setOption(option9);
				     },

				});
				//start   bug漏洞
//				   window.dateMaxIndex=0;
			        var myChart = echarts.init(document.getElementById('bugMainId'));
			        myChart.showLoading({
			        	  text: '正在加载中',
			        	  color: '#c23531',
			        	  textColor: '#073763',
			        	  maskColor: 'rgba(255, 255, 255, 0)',
			        	  zlevel: 0
			        	});
					var option;
			//         var option = {
			// 		    title: {
			// 		        text: '折线图堆叠'
			// 		    },
			// 		    tooltip: {
			// 		        trigger: 'axis'
			// 		    },
			// 		    legend: {
			// 		        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
			// 		    },
			// 		    grid: {
			// 		        left: '3%',
			// 		        right: '4%',
			// 		        bottom: '3%',
			// 		        containLabel: true
			// 		    },
			// 		    toolbox: {
			// 		        feature: {
			// 		            saveAsImage: {}
			// 		        }
			// 		    },
			// 		    xAxis: {
			// 		        type: 'category',
			// 		        boundaryGap: false,
			// 		        data: ['周一','周二','周三','周四','周五','周六','周日']
			// 		    },
			// 		    yAxis: {
			// 		        type: 'value'
			// 		    },
			// 		    series: [
			// 		        {
			// 		            name:'邮件营销',
			// 		            type:'line',
			// 		            stack: '总量',
			// 		            data:[120, 132, 101, 134, 90, 230, 210]
			// 		        },
			// 		        {
			// 		            name:'联盟广告',
			// 		            type:'line',
			// 		            stack: '总量',
			// 		            data:[220, 182, 191, 234, 290, 330, 310]
			// 		        },
			// 		        {
			// 		            name:'视频广告',
			// 		            type:'line',
			// 		            stack: '总量',
			// 		            data:[150, 232, 201, 154, 190, 330, 410]
			// 		        },
			// 		        {
			// 		            name:'直接访问',
			// 		            type:'line',
			// 		            stack: '总量',
			// 		            data:[320, 332, 301, 334, 390, 330, 320]
			// 		        },
			// 		        {
			// 		            name:'搜索引擎',
			// 		            type:'line',
			// 		            stack: '总量',
			// 		            data:[820, 932, 901, 934, 1290, 1330, 1320]
			// 		        }
			// 		    ]
			// 		};
			//         使用刚指定的配置项和数据显示图表。
			//         myChart.setOption(option);
			        
					 var bugLegendData=[];
					 var bugxAxisData=[];
					 var bugseriesData=[];
					 var selected="{";
					 var bugColor=['#75fffa','#fa75ff','#fffa75','#38fff8','#42a7ff','#75bfff','#ff75bf','#bfff75','#00faf2','#4249ff','#757aff','#ff757a','#7aff75','#ff383f','#9a42ff','#b675ff','#ffb675','#75ffb6','#fa0008','#f942ff'];
					 $.ajax({
						        type: "POST",
						        cache: false,
						        dataType: "json",
						        url: "bug.html", 
						        success: function(obj){
						        var data= eval ("(" + obj.listResult + ")");
						        var objNames= obj.names;
					// 	        	从后台得到返回的值，是一个json对象。 
						        	var startDate=obj.startTime;
						        	var endDate=obj.endTime;
					// 	        	生成开始日期~今天的日期列表
						        	var startTime=getDate(startDate);
						        	var endTime=getDate(endDate);
						        	var currentTime=getDate(startDate);
						        	for(var i=0;i<31;i++){
						        		if(currentTime<=endTime){
						        			currentTime.setDate(currentTime.getDate()+1);
						        			bugxAxisData.push(currentTime.Format("yyyy-MM-dd"));
						        		}
						        	}
						        	//selected.push("{");
						        	var num=1;
						        	for(var j=0;j<data.length;j++){
						        		var dataUnit=data[j];
						        		var textData=dataUnit.splice(0,1);
						        		var textName = "'"+textData+"'";
						        		//alert(textData);
						        		if(textData =="E-Mail地址" || textData =="可能的网站路径泄露" || textData =="目录浏览" || textData =="内网IP" || textData =="脚本木马"){
						        			if(num == 1){
						        				selected += textName + ":" +"false";
						        			}else{
						        				selected +=","+ textName + ":" +"false";
						        			}
						        			num++;
						        		}
						        		
			        			    	bugLegendData.push(
			        			    			{
			        			    				name:eval(textName),
			        			    				textStyle:{
			        			    					color:bugColor[j]
			        			    				}
			        			    			}
			        			    	);
						        		bugseriesData.push({
							            name:textData,
							            type:'line',
							            //stack: '总量',
							            itemStyle : {
													normal : {
														color:bugColor[j],
														
														lineStyle:{
															color:bugColor[j]
														}
													}
												},
							            data:dataUnit
							        });
						        		
						        	}
						        	selected += "}";
						  option = {
							    title: {
							        text: '漏洞类型分布及趋势'.split("").join("\n"),
							         x:'left',
							        // y:'center',
							         padding:100,
							         textStyle:{
							         	color:'#7aff75',
							         	fontSize:14
							         }
							         
							    },
							    tooltip: {
							        trigger: 'axis'
							    },
							    legend: {
							    	y:'435',
							    	x:'150',
							    	width:1200,
							        itemGap: 15,
							        selected:eval("("+selected+")"),
							        data:bugLegendData
									//textStyle:{color: 'auto'}
							    },
							    grid: {
							    	height:333,
							        containLabel: true
							    },
							    toolbox: {
							    	show:false,
							        feature: {
							            saveAsImage: {}
							        }
							    },
							    xAxis: {
							    	type : 'category',
									position: 'bottom',
									boundaryGap: true,
									axisLabel : {
									show:true,
									interval: 0, 
									rotate: 30,
									margin: 8,
									formatter: '{value}',
									textStyle: {color: '#7aff75',fontFamily: 'sans-serif',fontSize:12,fontStyle: '幼圆',fontWeight: 'bold'}
									},
									data : bugxAxisData 
							    },
							    yAxis : [
									        {
									            type : 'value',
									            position: 'left',
									            boundaryGap: [0,0],
									            axisLine : {    // 轴线
									                show: true,
									                lineStyle: {
									                    type: 'solid',
									                    width: 1
									                }
									            },
									           
									            axisLabel : {
									                show:true,
									                interval: 'auto',    // {number}
									                margin: 18,
									                formatter: '{value}',    // Template formatter!
									                textStyle: {
									                    color: '#7aff75',
									                    fontFamily: 'verdana',
									                    fontSize: 10,
									                    fontStyle: 'normal',
									                    fontWeight: 'bold'
									                }
									            },
									            splitLine : {
									                show:true,
									                lineStyle: {
									                    color: '#483d8b',
									                    type: 'dotted',
									                    width: 1
									                }
									            }
									        }
									    ],
							    series: bugseriesData
							};		
						  			myChart.hideLoading();
					    			myChart.setOption(option);
						     	},
						     	error:function(event){
						     		alert(event);
						     	}
							});
						//end  bug漏洞
					 
					 
					 
					 
					 
					 	//start attack漏洞
//					 	window.dateMaxIndex=0;
				        var attackMyChart = echarts.init(document.getElementById('attackMainId'));
				        attackMyChart.showLoading({
				        	  text: '正在加载中',
				        	  color: '#c23531',
				        	  textColor: '#073763',
				        	  maskColor: 'rgba(255, 255, 255, 0)',
				        	  zlevel: 0
				        	});
						var option;
				//         var option = {
				// 		    title: {
				// 		        text: '折线图堆叠'
				// 		    },
				// 		    tooltip: {
				// 		        trigger: 'axis'
				// 		    },
				// 		    legend: {
				// 		        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
				// 		    },
				// 		    grid: {
				// 		        left: '3%',
				// 		        right: '4%',
				// 		        bottom: '3%',
				// 		        containLabel: true
				// 		    },
				// 		    toolbox: {
				// 		        feature: {
				// 		            saveAsImage: {}
				// 		        }
				// 		    },
				// 		    xAxis: {
				// 		        type: 'category',
				// 		        boundaryGap: false,
				// 		        data: ['周一','周二','周三','周四','周五','周六','周日']
				// 		    },
				// 		    yAxis: {
				// 		        type: 'value'
				// 		    },
				// 		    series: [
				// 		        {
				// 		            name:'邮件营销',
				// 		            type:'line',
				// 		            stack: '总量',
				// 		            data:[120, 132, 101, 134, 90, 230, 210]
				// 		        },
				// 		        {
				// 		            name:'联盟广告',
				// 		            type:'line',
				// 		            stack: '总量',
				// 		            data:[220, 182, 191, 234, 290, 330, 310]
				// 		        },
				// 		        {
				// 		            name:'视频广告',
				// 		            type:'line',
				// 		            stack: '总量',
				// 		            data:[150, 232, 201, 154, 190, 330, 410]
				// 		        },
				// 		        {
				// 		            name:'直接访问',
				// 		            type:'line',
				// 		            stack: '总量',
				// 		            data:[320, 332, 301, 334, 390, 330, 320]
				// 		        },
				// 		        {
				// 		            name:'搜索引擎',
				// 		            type:'line',
				// 		            stack: '总量',
				// 		            data:[820, 932, 901, 934, 1290, 1330, 1320]
				// 		        }
				// 		    ]
				// 		};
				//         使用刚指定的配置项和数据显示图表。
				//         myChart.setOption(option);
				        
				 var attackLegendData=[];
				 var attackxAxisData=[];
				 var attackseriesData=[];
				 var bugColor1=['#75fffa','#fa75ff','#fffa75','#38fff8','#42a7ff','#75bfff','#ff75bf','#bfff75','#00faf2','#4249ff','#757aff','#ff757a','#7aff75','#ff383f','#9a42ff','#b675ff','#ffb675','#75ffb6','#fa0008','#f942ff'];
				 $.ajax({
					        type: "POST",
					        cache: false,
					        dataType: "json",
					        url: "attackCount.html", 
					        success: function(obj){
					        var data= obj.listResult;
					       // attackLegendData= obj.names;
				// 	        	从后台得到返回的值，是一个json对象。 
					        	var startDate=obj.startTime;
					        	var endDate=obj.endTime;
				// 	        	生成开始日期~今天的日期列表
					        	var startTime=getDate(startDate);
					        	var endTime=getDate(endDate);
					        	var currentTime=getDate(startDate);
					        	for(var i=0;i<31;i++){
					        		if(currentTime<=endTime){
					        			currentTime.setDate(currentTime.getDate()+1);
					        			attackxAxisData.push(currentTime.Format("yyyy-MM-dd"));
					        		}
					        	}
					        	for(var j=0;j<data.length;j++){
					        		var dataUnit=data[j];
					        		var textData=dataUnit.splice(0,1);
					        		var textName1="'"+textData+"'";
					        		
					        		attackLegendData.push(
		        			    			{
		        			    				name:eval(textName1),
		        			    				textStyle:{
		        			    					color:bugColor1[j]
		        			    				}
		        			    			}
		        			    	);
					        		attackseriesData.push({
						            name:textData,
						            type:'line',
						           // stack: '总量',
						            itemStyle : {
										normal : {
											color:bugColor1[j],
											
											lineStyle:{
												color:bugColor1[j]
											}
										}
									},
						            data:dataUnit
						        });
					        		
					        	}
					        	option = {
								    title: {
								        text: '攻击类型分布及趋势'.split("").join("\n"),
								         x:'left',
								         padding:100,
								         //y:'center',
								         textStyle:{
								         	color:'#7aff75',
								         	fontSize:14
								         }
								    },
								    tooltip: {
								        trigger: 'axis'
								    },
								     legend: {
								    	y:'435',
								    	x:'150',
								    	width:1200,
								    	itemGap: 15,
								        data:attackLegendData
										//textStyle:{color: 'auto'}
								    },
								    grid: {
								    	height:333,
								        containLabel: true
								    },
								    toolbox: {
								    	show:false,
								        feature: {
								            saveAsImage: {}
								        }
								    },
								    xAxis: {
								    	type : 'category',
										position: 'bottom',
										boundaryGap: true,
										axisLabel : {
										show:true,
										interval: 0, 
										rotate: 30,
										margin: 8,
										formatter: '{value}',
										textStyle: {color: '#7aff75',fontFamily: 'sans-serif',fontSize: 10,fontStyle: '幼圆',fontWeight: 'bold'}
										},
										data : attackxAxisData
								    },
								    yAxis: [
										        {
										            type : 'value',
										            position: 'left',
										            boundaryGap: [0,0.1],
										            axisLine : {    // 轴线
										                show: true,
										                lineStyle: {
										                    type: 'solid',
										                    width: 1
										                }
										            },
										           
										            axisLabel : {
										                show:true,
										                interval: 'auto',    // {number}
										                margin: 18,
										                formatter: '{value}',    // Template formatter!
										                textStyle: {
										                    color: '#7aff75',
										                    fontFamily: 'verdana',
										                    fontSize: 10,
										                    fontStyle: 'normal',
										                    fontWeight: 'bold'
										                }
										            },
										            splitLine : {
										                show:true,
										                lineStyle: {
										                    color: '#483d8b',
										                    type: 'dotted',
										                    width: 1
										                }
										            }
										        }
										    ],
								    series: attackseriesData
								};
					        	
					        	attackMyChart.hideLoading();
				    			attackMyChart.setOption(option);
					     	}
						});
				
		
			 	//end attack漏洞
				var serviceUserMyChart = echarts.init(document.getElementById('serviceUserId'));
				var serviceUserOption;
					$.ajax({
				        type: "POST",
				        cache: false,
				        dataType: "json",
				        url: "serviceUser.html", 
				        success: function(obj){
				        	var titleData=obj.title;
				        	var legendData=obj.legend;
				        	var serviceUserList=obj.serviceUserList;
				        	
				        	serviceUserOption = {
								    title : {
								        text: titleData,
								        x:'center',
								        y:'15px',
								        textStyle:{
								         	color:'#7aff75',
								         	fontSize:14
								         }
								    },
								    tooltip : {
								        trigger: 'item',
								        formatter: "{a} <br/>{b} :({d}%)"
								    },
								    legend: {
								    	show:false,
								    	orient: 'vertical',
								        left: 'left',
								        data: legendData
								    },
								    series : [
								        {
								            name: '',
								            type: 'pie',
								            radius : '55%',
								            center: ['50%', '60%'],
								            data:serviceUserList,
								            itemStyle: {
								                emphasis: {
								                    shadowBlur: 10,
								                    shadowOffsetX: 0,
								                    shadowColor: 'rgba(0, 0, 0, 0.5)'
								                },
								                normal:{ 
								                	normal : {
									                    color : 'rgba(255, 255, 64,1)'
									                },
					                                label:{ 
					                                   show: true, 
					                                   formatter: '{b} : ({d}%)' ,
					                                   position: 'outer'
					                                }, 
					                                labelLine :{show:true}
					                            } 
								            }
								        }
								    ],
								    color:[ 
								     '#ffe074','#ff383f' 
								     ]
								};
				        	serviceUserMyChart.setOption(serviceUserOption);
							        }
					});
					var orderServiceMyChart = echarts.init(document.getElementById('orderServiceId'));
					var orderServiceOption;
						$.ajax({
					        type: "POST",
					        cache: false,
					        dataType: "json",
					        url: "orderServiceCount.html", 
					        success: function(obj){
					        	var titleData=obj.title;
					        	var typeData=obj.type;
					        	var innerRingData=obj.innerRing;
					        	var detailServiceData=obj.detailService;
					        	
					        	orderServiceOption = {
				        			title : {
								        text: titleData,
								        x:'center',
								        y:'15px',
								        textStyle:{
								         	color:'#7aff75',
								         	fontSize:14
								         }
								    },
								    tooltip: {
								        trigger: 'item',
								        formatter: "{a} <br/>{b}:  ({d}%)"
								    },
								    legend: {
								    	show:false,
								    	orient: 'vertical',
								        x: 'left',
								        y:'top',
								        data:typeData
								    },
								    series: [
								        {
								            name:'',
								            type:'pie',
								            selectedMode: 'single',
								            radius: [0, '30%'],
								
								            label: {
								                normal: {
								                    position: 'inner'
								                }
								            },
								            labelLine: {
								                normal: {
								                    show: false
								                }
								            },
								            data:innerRingData,
								            itemStyle:{ 
					                            normal:{ 
					                                label:{ 
					                                   show: true, 
					                                   formatter: '{b} :  ({d}%)' 
					                                }, 
					                                labelLine :{show:true}
					                            } 
					                        } 
								        },
								        {
								            name:'',
								            type:'pie',
								            radius: ['40%', '55%'],
								            data:detailServiceData,
								            itemStyle:{ 
					                            normal:{ 
					                                label:{ 
					                                   show: true, 
					                                   formatter: '{b} : ({d}%)' 
					                                }, 
					                                labelLine :{show:true}
					                            } 
					                        } 
								        }
								    ],
								    color:[ 
										     '#4249ff','#04d8d0','#4249ff','#7cc2fd',
										     '#7cc2fd','#74fffa','#a3ffa0','#ffe074',
										     '#9a4aff','#ffb675','#ff383f','#ff7474',
										     '#ff75bf','#f28afe','#b675ff' 
										  ]
								};	        	
					        	orderServiceMyChart.setOption(orderServiceOption);
				        	}
						});
						
});