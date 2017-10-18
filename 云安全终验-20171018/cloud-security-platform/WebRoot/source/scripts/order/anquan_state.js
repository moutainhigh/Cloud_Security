var lastdayArray = [];
var numMsg = "漏洞总数";
var typeMsg = "漏洞类型";
$(function(){
	//选项卡切换
	$('.navlist li:last').css('background-image','none');
	$('.navlist li').click(function(){
		    var zindex=$(this).index();
			$('.navlist li').removeClass('none');
			$(this).addClass('this');
			$(this).prev('li').addClass('none');
			$(this).addClass('active').siblings('li').removeClass('active');
			$(".tabBox .not-used").eq($(this).index()).show().siblings().hide();
			
			//验证登录用户
			$.ajax({
		    	type : "post",
		    	url:"getLoginUser.html",
		        dataType:"json",
		        success:function(obj){
		        	var user = obj.LoginUser;
		        	if (user != null) {
		        		if (zindex != 0 && !(user.name =='anquanbang' ||user.name =='liuyanzhe' ||user.name =='timelysnow' ||user.name =='test11')) {
		        			window.location.href = "sa_anquanbang.html";
		        		}else {
							if (zindex == 1) {        //数据总览 
								//最新漏洞类型分布
								showVulnscanAlarmOneHour();
								//最新攻击类型分布
								showWafOneHour();
								//漏洞等级分布走势
								showVulnscanAlarmByLevelMonth6();
								//攻击等级分布走势
								showWafByLevelMonth6();
								
							}else if (zindex == 2) {  //地域分布
								showSecurityStateMap();
							}else if (zindex == 3) {  //安全趋势
								//漏洞类型分布及趋势
								showBug();
								//攻击类型分布及趋势
								showAttack();
							}else if (zindex == 4) {  //安全关注点
								//年度服务订购趋势
								showOrderServiceTimes();
								//服务数量分布
								showServiceUseInfoMonth6();
								//服务类型关注分布
								showOrderService();
								//不同频次服务用户分布 
								showServiceUser();
								//用户行业分布
								showUserIndustry();
							}
		        		}
						
		        	}else {
		        		window.location.href = "loginUI.html";
		        	}
		        },
		        error:function(obj){
		        	window.location.href = "loginUI.html";
		        }
		     });
			
			
			
	});
	
	//地图切换
	$('#content_ul li').click(function(){
		var index = $(this).index();
		$(this).addClass('active').siblings('li').removeClass('active');
		//验证登录用户
			$.ajax({
		    	type : "post",
		    	url:"getLoginUser.html",
		        dataType:"json",
		        success:function(obj){
		        	var user = obj.LoginUser;
		        	if (user != null) {
		        		if (!(user.name =='anquanbang' ||user.name =='liuyanzhe' ||user.name =='timelysnow' ||user.name =='test11')) {
		        			window.location.href = "sa_anquanbang.html";
		        		}else {
			        		if(index == 0){
			        			//漏洞分布
			        			showSecurityStateMap();
			        		}else if (index == 1) {
			        			//监测数据
			        			showHighSiteMap();
			        		}else if(index == 2) {
			        			//攻击源分布
			        			showHackerMap();
			        		}else if(index == 3) {
			        			//用户分布
			        			showUserMap();
			        		}
		        		}
						
		        	}else {
		        		window.location.href = "loginUI.html";
		        	}
		        },
		        error:function(obj){
		        	window.location.href = "loginUI.html";
		        }
		     });
	}); 
	
	
	//setInterval(function(){
		
		   
		
  	//}, 5000);
		  
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
				   
				
					
						
});

//数据总览  最新漏洞类型分布
function showVulnscanAlarmOneHour(){
		//最近一小时漏洞
		var myChart1 = echarts.init(document.getElementById('vulnscanAlarmOneHour'));
		myChart1.showLoading({
			    text: '正在加载中',
			    color: '#42a7ff',
				textColor: '#42a7ff',
			    maskColor: 'rgba(255, 255, 255, 0)',
			    zlevel: 0
		});
		
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
			   	myChart1.hideLoading();
			   	myChart1.setOption(option1);
		     },
		});
}

//数据总览  最新攻击类型分布
function showWafOneHour() {
		//最近一小时内waf
		var myChart0 = echarts.init(document.getElementById('wafOneHour'));
		myChart0.showLoading({
			text: '正在加载中',
			color: '#42a7ff',
			textColor: '#42a7ff',
			maskColor: 'rgba(255, 255, 255, 0)',
			zlevel: 0
		});
			        	
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
			   	myChart0.hideLoading();
			   	myChart0.setOption(option0);
		     },

		});
}

//数据总览  漏洞等级分布走势
function showVulnscanAlarmByLevelMonth6(){
	//最近六个月等级漏洞分布
	   var myChart3 = echarts.init(document.getElementById('vulnscanAlarmByLevelMonth6'));
	   myChart3.showLoading({
			text: '正在加载中',
			color: '#42a7ff',
			textColor: '#42a7ff',
			maskColor: 'rgba(255, 255, 255, 0)',
			zlevel: 0
		});
			        	
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
		   	                type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
		   	             },
				   		  formatter: function(params) {
				   			var level = obj.levelList;
	                        var str = "<table style='text-align:left;width:auto;'><tr><td style='width:auto'>" + params[0].name + "</td><tr>";
	                       // var colors[] = {"#52a2e5","#eabf6c","#6bc770","#fa7e7d","#806bff"};
	                        var bgColor="";
	                        for (var i = 0; i < params.length;i++) {
	                        	for(var j=0;j<level.length;j++){
	                        		if(params[i].seriesName == level[j]){
	                        			if(j == 0){
	                        				bgColor="#52a2e5";
	                        			}else if(j == 1){
	                        				bgColor="#eabf6c";
	                        			}else if(j == 2){
	                        				bgColor="#6bc770";
	                        			}else if(j == 3){
	                        				bgColor="#fa7e7d";
	                        			}else{ 
	                        				bgColor="#806bff";
	                        			}
	                        		}
	                        	}
	                        	str = str + "<tr><td style='height:20px;' width='10'><span style='display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:"+bgColor+"'></span><span style=''>" + params[i].seriesName + " : " + params[i].value + "</span></td></tr>";
	                        }
	                        str = str + "</table>";
	                        return str;
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
		   	myChart3.hideLoading();
		   	myChart3.setOption(option3);
	     },

	});
}

//安全关注点	年度服务订购趋势
function showOrderServiceTimes() {
		//一年内历史订单
		var myChart6 = echarts.init(document.getElementById('orderServiceTimes'));
		myChart6.showLoading({
			text: '正在加载中',
			color: '#42a7ff',
			textColor: '#42a7ff',
			maskColor: 'rgba(255, 255, 255, 0)',
			zlevel: 0
		});
			        	
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
				   	myChart6.hideLoading();
			   		myChart6.setOption(option6);
		     },

		});
}

//安全关注点	服务数量分布
function showServiceUseInfoMonth6(){
			//订单类型分布
		   var myChart4 = echarts.init(document.getElementById('serviceUseInfoMonth6'));
		   myChart4.showLoading({
			   text: '正在加载中',
			   color: '#42a7ff',
			   textColor: '#42a7ff',
			   maskColor: 'rgba(255, 255, 255, 0)',
			   zlevel: 0
		   });
			        	
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
			   	        x:'150',
			   	        y:'15'
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
			   	myChart4.hideLoading();
			   	myChart4.setOption(option4);
		     }
		});
}
//安全关注点	用户行业分布
function showUserIndustry(){
			//用户行业分布
			var myChart5 = echarts.init(document.getElementById('userIndustry'));
			myChart5.showLoading({
			   text: '正在加载中',
			   color: '#42a7ff',
			   textColor: '#42a7ff',
			   maskColor: 'rgba(255, 255, 255, 0)',
			   zlevel: 0
			});
			        	
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
				   	        data:['注册用户数','已下订单数'],
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
				   	            name:'注册用户数',
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
				   	myChart5.hideLoading();
				   	myChart5.setOption(option5);
			     },

			});
}

//数据总览  攻击等级分布走势
function showWafByLevelMonth6() {
//最近六个月攻击等级分布
				   var myChart9 = echarts.init(document.getElementById('wafByLevelMonth6'));
				   myChart9.showLoading({
			        	  text: '正在加载中',
			        	  color: '#42a7ff',
			   			  textColor: '#42a7ff',
			        	  maskColor: 'rgba(255, 255, 255, 0)',
			        	  zlevel: 0
			        	});
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
					   	        },
					   		  formatter: function(params) {
					   			var level = obj.levelList;
		                        var str = "<table style='text-align:left;width:auto;'><tr><td style='width:auto'>" + params[0].name + "</td><tr>";
		                       //String colors[] = {"#806bff","#eabf6c","#6bc770"};
		                        var bgColor="";
		                        for (var i = 0; i < params.length;i++) {
		                        	for(var j=0;j<level.length;j++){
		                        		if(params[i].seriesName == level[j]){
		                        			if(j == 0){
		                        				bgColor="#806bff";
		                        			}else if(j == 1){
		                        				bgColor="#eabf6c";
		                        			}else{
		                        				bgColor="#6bc770";
		                        			}
		                        		}
		                        	}
		                        	str = str + "<tr><td style='height:20px;' width='10'><span style='display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:"+bgColor+"'></span><span style=''>" + params[i].seriesName + " : " + params[i].value + "</span></td></tr>";
		                        }
		                        str = str + "</table>";
		                        return str;
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
					   	myChart9.hideLoading();
					   	myChart9.setOption(option9);
				     },

				});
}

//安全趋势  漏洞类型分布及趋势
function showBug() {
//start   bug漏洞
//				   window.dateMaxIndex=0;
			        var myChart = echarts.init(document.getElementById('bugMainId'));
			        myChart.showLoading({
			        	  text: '正在加载中',
			        	  //color: '#c23531',
			        	  //textColor: '#073763',
			        	  color: '#42a7ff',
			   			  textColor: '#42a7ff',
			        	  maskColor: 'rgba(255, 255, 255, 0)',
			        	  zlevel: 0
			        	});
					var option;
			        
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
							        trigger: 'axis',
							        position:function([x, y]) {
							        	if(x>550){
							        		return [x-250,-40]
							        	}
							        	return [x,-40]
							        }
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
									boundaryGap: false,
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
}

//安全趋势  攻击类型分布及趋势
function showAttack() {
//start attack漏洞
//					 	window.dateMaxIndex=0;
				var attackMyChart = echarts.init(document.getElementById('attackMainId'));
				attackMyChart.showLoading({
				        	  text: '正在加载中',
				        	  //color: '#c23531',
				        	  //textColor: '#073763',
				        	  color: '#42a7ff',
			   			      textColor: '#42a7ff',
				        	  maskColor: 'rgba(255, 255, 255, 0)',
				        	  zlevel: 0
				 });
				var option;
				
				        
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
								        trigger: 'axis',
								        position:function([x, y]) {
								        	if(x>550){
								        		return [x-250,-120]
								        	}
								        	return [x,-120]
								        }
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
										boundaryGap: false,
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
}

//安全关注点	不同频次服务用户分布 
function showServiceUser() {
				var serviceUserMyChart = echarts.init(document.getElementById('serviceUserId'));
				serviceUserMyChart.showLoading({
				        	  text: '正在加载中',
				        	  //color: '#c23531',
				        	  //textColor: '#073763',
				        	  color: '#42a7ff',
			   			  	  textColor: '#42a7ff',
				        	  maskColor: 'rgba(255, 255, 255, 0)',
				        	  zlevel: 0
				 });
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
							serviceUserMyChart.hideLoading();
				        	serviceUserMyChart.setOption(serviceUserOption);
							        }
					});
}
//安全关注点	服务类型关注分布
function showOrderService() {
					var orderServiceMyChart = echarts.init(document.getElementById('orderServiceId'));
					orderServiceMyChart.showLoading({
				        	  text: '正在加载中',
				        	  //color: '#42a7ff',
							  //textColor: '#42a7ff',
							  color: '#42a7ff',
			   			      textColor: '#42a7ff',
				        	  maskColor: 'rgba(255, 255, 255, 0)',
				        	  zlevel: 0
				 	});
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
								orderServiceMyChart.hideLoading();        	
					        	orderServiceMyChart.setOption(orderServiceOption);
				        	}
						});
}

function showHighSiteMap() {
	var myChart = echarts.init(document.getElementById('safe-map'));
	//地图初始化
	initHighSiteMap(myChart);
	//loading动画显示
	//myChart.showLoading();
	myChart.showLoading({
	 text: '正在加载中',
	 color: '#42a7ff',
	 //textColor: '#073763',
	  textColor: '#42a7ff',
	 maskColor: 'rgba(255, 255, 255, 0)',
	 zlevel: 0
	});
	
	
	//后台获取数据
    $.ajax({
           type : "post",
           url:"highSiteMap.html",
           dataType:"json",
           contentType: "application/x-www-form-urlencoded; charset=utf-8",
           success:function(data){
          	   var dataValue = [];
	           var dataList= data.list;
	           if(null!=dataList && dataList.length>0){
					for (var i = 0; i < dataList.length; i++) {

						var name = getDistrictName(dataList[i].name);
	  					var val ={'name':name,'value':dataList[i].alarmCount};
	  					dataValue.push(val);
					}
				}
				//loading动画隐藏 
				myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '告警总和超过100的网站数',
				            data: dataValue
				        }
				    ]
				});
           },
           error:function(){
           		myChart.hideLoading();
           		alert("系统异常，数据加载失败！");
           		return;
           }
     });
 }
 
function initHighSiteMap(myChart) {
	myChart.setOption({
		//backgroundColor: '#404a59',
	    tooltip: {
	    	backgroundColor : 'rgba(20, 94, 181,0.8)',
	        trigger: 'item',
	        formatter: function (params) {
	        	var count = params.value;
	        	if(count== null || isNaN(count)) {
	        		count = 0;
	        	}
            	return params.name + ' : ' + count;
        	}
	    },
	    visualMap: {
	        min: 0,
	        max: 1000,
	        left: '10%',
	        bottom: '2%',
	        textStyle: {
	           color: '#fff'
	        },
	        inRange: {
	            //color : [ '#c4fffd', '#9ae9e6', '#6aceca', '#42aeaa', '#1e8682']
	            //color: ['#ffe074', '#fd2204']
	            color: ['#b8e2b0', '#93b06a','#b09664','#cc7c5f','#e5665a']
	       },
	        calculable: true
	    },
	    series: [
	        {
	            name: '告警总和超过100的网站数',
	            type: 'map',
	            mapType: 'china',
	            layoutCenter: ['50%', '50%'],  //地图中心到两侧的距离
				layoutSize: 780,  //地图大小
	            roam: false,
	            label: {
	                normal: {
	                    show: true,
	                    textStyle: {
				           color: '#fff'
				        }
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            itemStyle: {
		            normal: {
		            	borderWidth : 2,
		                areaColor : 'rgba(0,0,0,0.1)',
		                borderColor : 'rgb(110,117,145)'
		            },
		            emphasis: {
		                //areaColor: '#04144d',
		                areaColor : '#42a7ff'
		            }
		        },
	            data: []
	        }
	    ]
	});
}

function getDistrictName(key){
	var name;
	if(key == null || key == "") {
		name =  "其他";
	}else if (key.indexOf("内蒙古") != -1) {
		name =  "内蒙古";
	}else if (key.indexOf("黑龙江") != -1) {
		name =  "黑龙江";
	}else {
		name =  key.slice(0, 2);
	}
	return name;
}

//黑客分布地图
function showHackerMap() {
	var myChart = echarts.init(document.getElementById('safe-map'));
	//地图初始化
	initHackerMap(myChart);
	//loading动画显示
	//myChart.showLoading();
	myChart.showLoading({
	   text: '正在加载中',
	   color: '#42a7ff',
	   //textColor: '#073763',
	   textColor: '#42a7ff',
	   maskColor: 'rgba(255, 255, 255, 0)',
	   zlevel: 0
	});
	
	//后台获取数据
    $.ajax({
           type : "post",
           url:"HackerMap.html",
           dataType:"json",
           contentType: "application/x-www-form-urlencoded; charset=utf-8",
           success:function(data){
               var dataValue = [];
	           var dataList = data.list;
			  // 取得json的值重新转化为我们需要的json对象。 
			   if(null!=dataList && dataList.length>0){
					for (var i = 0; i < dataList.length; i++) {
						var value = [];
						value.push(dataList[i].longitude);
						value.push(dataList[i].latitude);
						value.push(dataList[i].val*100);
						
						var name = getDistrictName(dataList[i].name);
						dataValue[i]={'name':name,'value':value};
					}
				}
				myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '黑客数量',
				            data: dataValue
				        }
				    ]
				});
				
           },
           error:function(){
           		myChart.hideLoading();
           		alert("系统异常，数据加载失败！");
           }
     });
 }
 
 //黑客分布地图初始化
 function initHackerMap(myChart) {
 	// 为ECharts准备一个具备大小（宽高）的Dom
	// 基于准备好的dom，初始化echarts图表
	//var myChart = echarts.init(document.getElementById('safe-map'));
	// 定义图表选项，包含图表实例可配置选项： 公共选项 ， 组件选项 ， 数据选项
	myChart.setOption({
		//backgroundColor: '#404a59',
	    tooltip: {
	        trigger: 'item'
	    },
	    visualMap: {
	    	type: 'piecewise', // 定义为分段型 visualMap
	        left: '10%',
	        bottom: '2%',
	        textStyle: {
	           color: '#fff'
	        },
	        pieces: [
			    {gt: 0,   lte: 10},   // (0, 10]
			    {gt: 10,  lte: 50},  // (10, 50]
			    {gt: 50,  lte: 100},  // (50, 100]
			    {gt: 100, lte: 300},  // (100, 300]
	        	{gt: 300}          // (300, Infinity]
			],
			inRange: {
            	color: ['#6bc770','#ddde73','#eabf6c','#ea9068', '#fa7e7d']
        	}
	    },
	    geo: {
	        map: 'china',
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        roam: false,
	        layoutCenter: ['50%', '50%'],  //地图中心到两侧的距离
			layoutSize: 780,  //地图大小
	        itemStyle: {
		            normal: {
		            	borderWidth : 2,
		                areaColor : 'rgba(0,0,0,0.1)',
		                borderColor : 'rgb(110,117,145)'
		            },
		            emphasis: {
		                areaColor : 'rgba(0,0,0,0.1)'
		            }
		    }
	    },
	    series: [
	        {
	            name: '黑客数量',
	            type: 'heatmap',
        		coordinateSystem: 'geo',
	            mapType: 'china',
	            blurSize:20,
	            //minOpacity:0.2,
	            //maxOpacity:1,
	            //roam: false,
	            label: {
	                normal: {
	                    show: true
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            data: []
	        }
	    ]
	});
         
 }
 
//用户分布地图
function showUserMap() {
 	var myChart = echarts.init(document.getElementById('safe-map'));
 	//地图初始化
	initUserMap(myChart);
	//loading动画显示
 	//myChart.showLoading();
	myChart.showLoading({
	   text: '正在加载中',
	   color: '#42a7ff',
	   //textColor: '#073763',
	   textColor: '#42a7ff',
	   maskColor: 'rgba(255, 255, 255, 0)',
	   zlevel: 0
	});
	
	var maxVal = 0;
	//后台获取数据
    $.ajax({
           type : "post",
           url:"AssetMap.html",
           dataType:"json",
           contentType: "application/x-www-form-urlencoded; charset=utf-8",
           success:function(data){
           	   var dataValue = [];
	           var dataList = data.list;
			  // 取得json的值重新转化为我们需要的json对象。 
			   if(null!=dataList && dataList.length>0){
					for (var i = 0; i < dataList.length; i++) {

						var value = [];
						value.push(dataList[i].longitude);
						value.push(dataList[i].latitude);
						value.push(dataList[i].num);
						
						dataValue[i]={'name':dataList[i].name,'value':value};
						// 获取到最大的值 来控制气泡半径
						if (parseInt(dataList[i].num) >= maxVal) {
		    				maxVal = parseInt(dataList[i].num);
		   				}
		   				
					}
				}
				myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '用户地理位置分布',
				            data: dataValue,
				            symbolSize: function (val) {
				            // 根据最大的值 来控制气泡半径  （最小 10  最大 20）
			               		return 15 + val[2] * 10 / maxVal;
				            }
				            
				        }
				    ]
				});
				
           },
           error:function(data){
           		myChart.hideLoading();
           		alert("系统异常，数据加载失败！");
           		return;
           }
     });
 }
 
 function initUserMap(myChart,dataObj, maxVal) {
 	// 为ECharts准备一个具备大小（宽高）的Dom
	// 基于准备好的dom，初始化echarts图表
	//var myChart = echarts.init(document.getElementById('safe-map'));
	myChart.setOption({
		//backgroundColor: '#404a59',
	    //tooltip : {
	    //	backgroundColor : 'rgba(20, 94, 181,0.8)',
	    //    trigger: 'item',
	    //    formatter: function (params) {
        //    	return params.name + ' : ' + params.value[2];
        //	}
	    //},
	    visualMap: {
	    	show : false,
	    	type: 'piecewise', // 定义为分段型 visualMap
	        left: '10%',
	        bottom: '2%',
	        textStyle: {
	           color: '#fff'
	        },
	        calculable: false,
	        pieces: [
	        	{gt: 2000, color: '#ffffff'}, 
			    {gt: 1500, lte: 2000, color: '#ffffff'},
			    {gt: 1000, lte: 1500, color: '#ffffff'},
			    {gt: 500, lte: 1000,  color: '#ffffff'},
			    {gt: 0,    lte: 500,  color: '#ffffff'}
			]
    	},
	    geo: {
	        map: 'china',
	        layoutCenter: ['50%', '50%'],  //地图中心到两侧的距离
			layoutSize: 780,  //地图大小
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        roam: false,
	        itemStyle: {
	            normal: {
	                //areaColor: '#323c48',
	                //borderColor: '#111'
	                borderWidth : 2,
	                areaColor : 'rgba(0,0,0,0.1)',
	                borderColor : 'rgb(110,117,145)'
	            },
	            emphasis: {
	                //areaColor: '#2a333d',
	                areaColor : 'rgba(0,0,0,0.1)'
	            }
	        }
	    },
	    series : [
	        {
	            name: '用户地理位置分布',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            data: [],
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: false
	                },
	                emphasis: {
	                    show: false
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#ddb926'
	                }
	            }
	        }
	    ]
	});
}

function showSecurityStateMap(){
	var myChart = echarts.init(document.getElementById('safe-map'));
 	//地图初始化
	initSecurityStateMap(myChart);
	//loading动画显示
 	//myChart.showLoading();
	myChart.showLoading({
	   text: '正在加载中',
	   color: '#42a7ff',
	   //textColor: '#073763',
	   textColor: '#42a7ff',
	   maskColor: 'rgba(255, 255, 255, 0)',
	   zlevel: 0
	});
	
	$.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "initDistrictList.html?serviceId=1", 
	        success: function(data){
	           var dataList = data.districtList;
	           //控制气泡半径
	           var maxVal = data.max;
	           var dataValue = [];
			  // 取得json的值重新转化为我们需要的json对象。 
			   if(null!=dataList && dataList.length>0){
					for (var i = 0; i < dataList.length; i++) {

						var value = [];
						value.push(dataList[i].longitude);
						value.push(dataList[i].latitude);
						value.push(dataList[i].count);
						dataValue[i]={'name':dataList[i].id,'value':value};
					}
				}
	        	myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '',
				            data: dataValue,
				            symbolSize: function (val) {
				            // 根据最大的值 来控制气泡半径  （最小 3  最大 13）
			               		return val[2]/maxVal*15 +10;
			               		
				            }
				            
				        },
				        {
				            name: 'effect',
				            data: dataValue,
				            symbolSize: function (val) {
				                // 根据最大的值 来控制气泡半径  （最小 3  最大 13）
			               		return val[2] / maxVal *15 +10;
				            }
				        }
				    ]
				});
	        	
	     	},
           error:function(){
           		myChart.hideLoading();
           		alert("系统异常，数据加载失败！");
           		return;
           }
		});
	
}

function initSecurityStateMap(myChart){
	myChart.setOption({
		//backgroundColor: '#404a59',
	    tooltip : {
	    	show : true, // 触发展示浮动窗口的事件类型，此处为数据触发，
			//trigger : 'item', //
			backgroundColor : 'rgba(20, 94, 181,0.8)',
			textStyle : {
				color : 'white',
				fontSize : 12,
			},
			enterable : false,
			hideDelay : 0,
			formatter : function(param, ticket, callback){
				var reg = /^\+?[1-9][0-9]*$/;
				if (param.name != null && "" != param.name) {
					var oname = param.name;
					var count = param.value[2];
					var re = reg.test(oname);
					if (re) {
						$.ajax({
							type : "POST",
							cache : false,
							dataType : "json",
							url : "getDistrictData.html?id=" + oname + "&serviceId=1",
							success : function(obj) {
								var str = getDetailData(obj,count);
								 //setTimeout(function (){callback(ticket, str); },100);
								callback(ticket, str);
					         	
							}
						});
					
				     }
				}
			 	//return 'Loading';
			 	return '';
		 	}
	    },
	    visualMap: {
	    	show : true,
	    	type: 'piecewise', // 定义为分段型 visualMap
	        min: 0,
	        max: 5000,
	        left: '10%',
	        bottom: '2%',
	        textStyle: {
	           color: '#fff'
	        },
	        calculable: false,
	        //inRange: {
	        //    color : [ '#ff81ff', '#99d9eb', '#c9bfe7', '#ffff9b', '#ffffff']
	       // }
	        pieces: [
			    {gt: 4000, color: '#ff81ff'}, 
			    {gt: 3000, lte: 4000, color: '#99d9eb'},
			    {gt: 2000, lte: 3000, color: '#c9bfe7'},
			    {gt: 1000, lte: 2000,  color: '#ffff9b'},
			    {gt: 0,    lte: 1000,  color: '#ffffff'}
			]
    	},
	    geo: {
	        map: 'china',
	        layoutCenter: ['50%', '50%'],  //地图中心到两侧的距离
			layoutSize: 780,  //地图大小
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        // 是否支持滚轮缩放功能
	        roam: false,
	        // 设定地图的颜色
	        itemStyle: {
	            normal: {
	                borderWidth : 2,
	                areaColor : 'rgba(0,0,0,0.1)',
	                borderColor : 'rgb(110,117,145)'
	            },
	            emphasis: {
	                //areaColor: '#2a333d',
	                areaColor : 'rgba(0,0,0,0.1)'
	            }
	        }
	    },
	    series : [
	        {
	            name: '',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            data: [],
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: false
	                },
	                emphasis: {
	                    show: false
	                }
	            },
	            // 图形样式
	            itemStyle: {
	                normal: {
	                    color: '#ddb926'
	                }
	            }
	        },
	        {
	            name: 'effect',
	            type: 'effectScatter',
	            coordinateSystem: 'geo',
	            data: [],
	            symbol:'circle',
	            showEffectOn: 'render',
	            rippleEffect: {
	                //brushType: 'stroke',
	                brushType: 'fill',
	                scale:3, //动画中波纹的最大缩放比例
	                period:3 //动画的时间
	            },
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: false
	                },
	                emphasis: {
	                    show: false
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#f4e925',
	                    shadowBlur: 10,
	                    shadowColor: '#333'
	                }
	            },
	            zlevel: 1
	        }
	    ]
	});
}

function getDetailData(obj,count){
	//console.log(obj);
	var datalist = [];
	datalist = obj;
	var sname=obj[0].name;
	var leaks = "";
					
	for (var i=0; i< obj.length;i++) {
		leaks += "<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;.&nbsp;&nbsp;"+obj[i].leakName+"</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'>"+obj[i].count+"</td></tr>"
	}
	var orgnames = "<div>"
			+"<table style='font-family: 'LTH';'>"
			+"<tr><th style='height: 24px;line-height: 24px;text-align: left;font-size: 12px; font-weight: bold;margin: 0;'>"+sname+"</th></tr>"
			+"<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;"+numMsg+"</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'>"+count+"</td></tr>"
			+"<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;"+typeMsg+"TOP5：</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'></td></tr>"
			+leaks
			+"</table>"
			+"</div>";
	return orgnames;
}