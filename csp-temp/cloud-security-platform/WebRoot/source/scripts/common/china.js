/*
 * 云安全漏洞图页面外部js文件。
 */
"use strict";
var fox = 0;
var orgnames="";
var serviceId ="";
var numMsg = "";
var typeMsg = "";
var max = "";

function redrawBranch(obj) {
	//var list = obj.data.flawList;
	var list = obj;
	//console.log(list);
	var branchData = [];
	var coorStr = "";
	var coorData;

	// 取得分院的信息。 
	// 取得json的值重新转化为我们需要的json对象。 
	if(null!=list && list.length>0){
		for (var i = 0; i < list.length; i++) {
			// 漏洞的名称和数量。 
			// console.log(list[i].orgName +":"+list[i].level);
			var str = "{name:'" + list[i].id + "',value:" + list[i].count + "}"
			var obj = eval('(' + str + ')');
			// console.log(obj);
			branchData.push(obj);
	
			// 得到各省会的坐标。 
			// 将所有省会的坐标组成一个字符串。 
			coorStr += ("'" + list[i].id + "'" + ":[" + list[i].longitude + ","
					+ list[i].latitude + "],");
			console.log(coorStr);
		}
	}

	// 给坐标字符串套上大括号。 
	var coorEnd = "{" + coorStr + "}";
	// 将坐标数据转换成json格式。
	coorData = eval('(' + coorEnd + ')');
	//console.log(coorData);

	// 为ECharts准备一个具备大小（宽高）的Dom
	// 基于准备好的dom，初始化echarts图表
	var myChart = echarts.init(document.getElementById('safe-map'));
	// 定义图表选项，包含图表实例可配置选项： 公共选项 ， 组件选项 ， 数据选项
	var option = {
		// 设置该实例的背景色
		// backgroundColor : '#1b1b1b',
		// 标题设置
		/*
		 * title : { text : '', subtext : '', sublink : '', x : 'center' },
		 */
		// itemStyle.normal.areaStyle.color : 'rgba(0,0,0,0)',
	
		// 气泡提示框，用于展现浮动窗口用
		tooltip : {
			show : true, // 触发展示浮动窗口的事件类型，此处为数据触发，
			//trigger : 'item', //
			backgroundColor : 'rgba(20, 94, 181,0.8)',
			textStyle : {
				color : 'white',
				// fontFamily : 'verdana',
				fontSize : 12,
				//fontStyle : 'normal',
			// fontWeight : 'bold'
			},
			//borderWidth : 1,
			enterable : false,
			formatter : function(param){
				getOrgData(param);
				return orgnames;
		 }
		},

		dataRange : {
			show : false,
			min : 0,
			max : 5000,
			calculable : false,
//		    color : [ '#E6E8FA' ]
//			color : [ '#D64529', 'orange', 'yellow', '#81BA77', '#5688C1','#E6E8FA' ]
//			color : [ '#ec8589', '#ed8ab1', '#3ed2de', '#67d777', '#e3de8e', '#ffffff']
//			color : [ '#D64529', '#ec8589','orange', '#ed8ab1','yellow','#3ed2de', '#81BA77','#67d777', '#5688C1','#e3de8e','#E6E8FA' ]
			color : [ '#ff81ff', '#99d9eb', '#c9bfe7', '#ffff9b', '#ffffff']
		},
		map : {
			// mapType : 'china', // 各省的mapType暂时都用中文
			mapLocation : {
				x : 'center',
				y : 'center'
			// width // 自适应
			// height // 自适应
			},
			showLegendSymbol : true, // 显示图例颜色标识（系列标识的小圆点），存在legend时生效
			// 图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）
		},
		// 地图生成的数据内容数组
		series : [ {
			name : '',
			type : 'map',
			mapType : 'china',
			// 鼠标悬浮区域是否高亮
			hoverable : false,
			// 是否支持滚轮缩放功能
			roam : false,
			//滚轮缩放的极限控制
			scaleLimit:{min:0.8,max:10.0},
			// 设定地图的颜色
			itemStyle : {
				normal : {
					borderWidth : 2,
					//borderWidth : "",
					// 设置地图边框的颜色
					borderColor : 'rgb(110,117,145)',//"white",//'rgb(85,85,85)',
					// 设置地图内的填充色
					areaStyle : {
						color : 'rgba(0,0,0,0.1)'
						//color : 'rgba(255,255,255,0.1)'
					}
				}
			},
			data : [],
			// 系列中的数据标注内容
			markPoint : {
				// 闪烁的点的样式为圆形的点
				symbol : 'circle',
//				symbolSize : 5, // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
				symbolSize : function(v){
					return v/max*10+3;
				},
				// 图形样式
				itemStyle : {
					normal : {
						// 此颜色值为亮蓝灰色
						// 设置闪烁边框的颜色
						borderColor : 'white',
						borderWidth : 0, // 标注边线线宽，单位px，默认为1
						// 是否显示点的数值
						label : {
							show : false
						}
					},
					emphasis : {
						borderColor : '#1e90ff',
						borderWidth : 1,
						label : {
							show : false
						}
					}
				},
				// 标注的数据内容

				// 标注的数据内容
				data : branchData
			},
			// 坐标数据
			geoCoord : coorData
		}, {
			// name : '国内各分院的安全事件发生情况',
			// name : '',
			type : 'map',
			mapType : 'china',
			data : [],
			markPoint : {
				symbol : 'emptyCircle',
				//symbolSize : 10,
				symbolSize : function(v) {
					return 10 + v / max *10
				},
				effect : {
					show : true,
					shadowBlur : 0
				},
				itemStyle : {
					normal : {
						label : {
							show : false
						}
					}
				},
				data :branchData
			}
		} ]
	};
	myChart.setOption(option);    
 }

$(window).ready(function(){
	redrawBranch(null);
	getServiceData();
	getAddressAll();
}); 

//获取服务数据
function getServiceData(){
	serviceId = $("#serviceId").val();
	numMsg = $("#numMsg").val();
	typeMsg = $("#typeMsg").val();
//	alert("999"+numMsg);
	getBranchInfo();
	getRegionTOP();
	getServiceTOP();
	getLineChart();
}
//得各省信息，用于在地图上显示。 
function getBranchInfo(){
	 $.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "initDistrictList.html?serviceId="+serviceId, 
	        success: function(obj){
	        	// 从后台得到返回的值，是一个json对象。 
//	        	console.log(obj.max);
	        	max = obj.max;
	        	redrawBranch(obj.districtList);
	        	
	     	}
		});
}
/*地域告警TOP5*/
function getRegionTOP(){
	/*var narray =[];
	var carray =[];
	var nameArray = ['','','','',''];
	var countArray = ['','','','',''];*/
	$.ajax({
        type: "POST",
        cache: false,
        dataType: "json",
        url: "getDistrictAlarmTop5.html?serviceId="+serviceId, 
        success: function(obj){
        	// 从后台得到返回的值，是一个json对象。 
        	//console.log(obj.length);
        	
        	/*for (var i = 0;i<obj.length; i++) {
        		nameArray[i]=obj[i].name;
        		countArray[i]=obj[i].count;
			}
        	for (var j =countArray.length; j>0;j--) {
        		narray.push(nameArray[j-1]);
        		carray.push(countArray[j-1]);
        	}
        	
        	redrawEventList(narray,carray);*/
        	redrawLevelList(obj);
     	}
	});
}
/*漏洞告警TOP5*/
function getServiceTOP(){
	/*var narray =[];
	var carray =[];
	var nameArray = ['','','','',''];
	var countArray = ['','','','',''];*/
	$.ajax({
        type: "POST",
        cache: false,
        dataType: "json",
        url: "getServiceAlarmTop5.html?serviceId="+serviceId, 
        success: function(obj){
        	// 从后台得到返回的值，是一个json对象。 
        	//console.log(obj);
        	
        	/*for (var i = 0;i<obj.length; i++) {
        		nameArray[i]=obj[i].name;
        		countArray[i]=obj[i].count;
			}
        	for (var j =countArray.length; j>0;j--) {
        		narray.push(nameArray[j-1]);
        		carray.push(countArray[j-1]);
        	}*/
        	
        	redrawServiceList(obj);
     	}
	});
}
/*漏洞个数折线图*/
function getLineChart(){
	var monthsArray = [];
	var countArray = [];
	$.ajax({
        type: "POST",
        cache: false,
        dataType: "json",
        url: "getServiceAlarmMonth5.html?serviceId="+serviceId, 
        success: function(obj){
        	// 从后台得到返回的值，是一个json对象。 
//        	console.log(obj);
        	if(obj.length>0){
        		for (var i = 0; i<obj.length; i++) {
            		monthsArray.push(obj[i].months);
            		countArray.push(obj[i].count);
    			}
            	redrawTotalLevel(monthsArray,countArray);
        	}else{
        		$("#total-level-list").empty();
        	}
        	
     	}
	});
}

/*鼠标悬浮*/
function getOrgData(param){
	var reg = /^\+?[1-9][0-9]*$/;
	if (param.name != null && "" != param.name) {
		//console.log(param);
		var oname = param.name;
		var count = param.percent;
		var re = reg.test(oname);
		if (re) {
			$.ajax({
				type : "POST",
				cache : false,
				dataType : "json",
				url : "getDistrictData.html?id=" + oname + "&serviceId=" + serviceId,
				success : function(obj) {
					console.log(obj);
					var sname=obj[0].name;
					var leaks = "";
					
					for (var i=0; i< obj.length;i++) {
						leaks += "<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;.&nbsp;&nbsp;"+obj[i].leakName+"</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'>"+obj[i].count+"</td></tr>"
					}
					orgnames = "<div>"
						+"<table style='font-family: 'LTH';'>"
							+"<tr><th style='height: 24px;line-height: 24px;text-align: left;font-size: 12px; font-weight: bold;margin: 0;'>"+sname+"</th></tr>"
							+"<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;"+numMsg+"</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'>"+count+"</td></tr>"
							+"<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;"+typeMsg+"TOP5：</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'></td></tr>"
							+leaks
						+"</table>"
					+"</div>";
					
				}
			});
		}else {
			orgnames="";
		}
	}
	
}

/*动态地址栏*/
function getAddressAll(){
	//alert("ddddddddddddd");
	//alert($("#piece").html());
	$.ajax({
        type: "POST",
        cache: false,
        dataType: "json",
        url: "findUseAssetAddr.html", 
        success: function(obj){
        	// 从后台得到返回的值，是一个json对象。 
        // console.log(obj);
         var divs="<table>";
         for (var i=0; i< obj.length;i++) {
        	 var sname=obj[i].addr;
        	 //alert(name.length);
        	 if(sname.length>50){
        		sname=sname.substr(0,50);
        	 }
        	 divs += "<tr><td><div title='"+obj[i].addr+"'>"+sname+"</div></td></tr>";
			}
          divs += "</table>";
         $("#piece").html(divs);
         getLen(obj.length);
     	}
	});
}
var oMarquee = document.getElementById("piece"); //滚动对象
var iLineHeight = 10; //单行高度，像素
var iLineCount = 6; //实际行数
var iScrollAmount = 2; //每次滚动高度，像素
function getLen(len){
  //alert(len);
  iLineCount =len; //实际行数
 }


