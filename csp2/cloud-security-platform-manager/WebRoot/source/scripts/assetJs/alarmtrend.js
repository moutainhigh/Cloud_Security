"use strict";
function assertAlarm(list,timet){
var alarmlist = [];
var timetypeList;
var timeIntList;
if(timet=="2"){
	timetypeList=['1日','2日','3日','4日','5日','6日','7日','8日','9日','10日','11日','12日','13日','14日','15日','16日','17日','18日','19日','20日','21日','22日','23日','24日','25日','26日','27日','28日','29日','30日','31日'];
	timeIntList=['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31'];
}else if(timet=="3"){
	timetypeList=['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
	timeIntList=['01','02','03','04','05','06','07','08','09','10','11','12'];
}else{
	timetypeList=['1时','2时','3时','4时','5时','6时','7时','8时','9时','10时','11时','12时','13时','14时','15时','16时','17时','18时','19时','20时','21时','22时','23时','24时'];
	timeIntList=['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24'];
}
for(var j=0;j<timeIntList.length;j++){
	var timety=timeIntList[j];
	var tmp=0;
  for (var i = 0; i < list.length; i++) {
	var timep=list[i].begin_date;
    if(timep==timety){
		tmp=list[i].num;
	  }
	}
	alarmlist.push(tmp);
}
console.log(alarmlist);

var myChart = echarts.init(document.getElementById('charts_map'));
var option = {
	    title : {
	    	show: false,
	        text: '资产警告趋势',
	        subtext: '警告数量'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	    	show: false,
	        data:['按时间排序']
	    },
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            axisLine: true,
	            splitLine: false,
	            data : timetypeList
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLine: true,
	            splitLine: false,
	            data : alarmlist
	        }
	    ],
	    series : [
	        {
	            name:'警告数量',
	            type:'line',
	            effect: {
	            	show: true,
	            	color: 'white'
	            },
	            itemStyle: {
	            	normal: {
	                    color: '#48b',
	            		lineStyle: {
	            			color: '#48b'
	            		}
	            	}
	            },
	            data: alarmlist
	        }
	    ]
	};
	                    
myChart.setOption(option); 
}