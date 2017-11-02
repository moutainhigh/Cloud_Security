"use strict";
function assertAlarm(list,timet,bdate,edate){

var alarmlist = [];

//获得选取的时间数组
var dateArray = getAll(bdate,edate,timet);
/*$.each(dateArray,function(n,value) {
	alert(value);
});*/


  if(dateArray!=null && dateArray.length>0){
	  for(var j = 0; j < dateArray.length;j++){
		  if(list!=null&&list.length>0){
			  for (var i = 0; i < list.length; i++) {
				  var flag = 0;
				  if(timet!="2"){
					  if(list[i].begin_date==dateArray[j]){
						  var tmp = list[i].num;
						  alarmlist.push(tmp);
						  flag = 1;
						  break;
					  }
				  }else {
					  if(dateArray[j].substr(dateArray[j].lastIndexOf('-')+1)==list[i].begin_date){
						  var tmp = list[i].num;
						  alarmlist.push(tmp);
						  flag = 1;
						  break;
					  }
				  }

			  }
			  if(flag==0){
				  alarmlist.push(0);
			  }
		  }
		 
		
	  }
  }



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
	            data : dateArray
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


function getAll(begin,end,timet){

	var abs = new Array();
	var abs1 = new Array();
	var aes = new Array();
	var aes1 = new Array();
	var dateArray = new Array();

/*	var ab = begin.split("-");
	var ae = end.split("-");*/
	
	var abs=begin.substring(0,10).split('-');
	var abs1 = begin.substring(10,19).split(':');
	var aes=end.substring(0,10).split('-');
	var aes1=end.substring(10,19).split(':');
	
	var db = new Date();
	db.setUTCFullYear(abs[0], abs[1]-1, abs[2]);
	db.setHours(abs1[0], abs1[1], abs1[2]);
	var de = new Date();
	de.setUTCFullYear(aes[0], aes[1]-1, aes[2]);
	de.setHours(aes1[0], aes1[1], aes1[2]);
	var unixDb=db.getTime();
	var unixDe=de.getTime();
	for(var k=unixDb;k<=unixDe;){
		var tmp = (new Date(parseInt(k))).format(timet);
		if(timet=="1"){
			//判断是否存在
			if(jQuery.inArray(tmp, dateArray)==-1){
				dateArray.push(tmp);
			}
			k=k+60*60*1000;
		}else if(timet=="2"){
			dateArray.push(tmp);
			k=k+24*60*60*1000;
		}else if(timet=="3"){
			//判断是否存在
			if(jQuery.inArray(tmp, dateArray)==-1){
				dateArray.push(tmp);
			}
			k=k+24*60*60*1000;
		}

	}
	if(timet!="2"){
/*		dateArray = dateArray.sort(function(a,b){
			return a-b;
		});*/
		dateArray.sort();
	}

	return dateArray;
/*	$.each(dateArray,function(n,value) {
	    //do something here
	    alert(value);
	   });*/
}

Date.prototype.format=function (timet){
	var s='';
	if(timet=="2"){//日
		s+= this.getFullYear()+"-";
		s+=(this.getMonth()+1) +"-";
		if(this.getDate().toString().length<2){
			s+= "0"+this.getDate();
		}else{
			s+= this.getDate();
		}
	}else if(timet=="3"){//月
		if((this.getMonth()+1).toString().length<2){
			s+= "0"+(this.getMonth()+1);
		}else{
			s+= (this.getMonth()+1);
		}
	}else if(timet=="1"){//时
		if(this.getHours().toString().length<2){
			s+= "0"+this.getHours();
		}else{
			s+= this.getHours();
		}
	}

	return(s);// 返回日期。
};