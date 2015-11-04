var oldTimeArray = [];//旧时间列表
var oldCountArray = [];//对应旧的告警数量列表

$(window).ready(function(){
	getWarnList();
	getAlarmTop5(0);
	window.setInterval(getWarnList,60*1000);
	$('.anlist li').click(function(){
		var index=$(this).index();
		$(this).addClass('active').siblings('li').removeClass('active');
		
		$(this).parent('.anlist').siblings('.analyse_tabCont').children('.analyse_tabItem:eq('+index+')').show().siblings().hide();

		getAlarmTop5(index);
	});
}); 

//获取告警数量（每小时）
function getWarnList(){
	var timeArray = [];//时间列表
	var countArray = [];//告警数量列表
	var flag = true;//新旧列表相同
	$.ajax({
        type: "POST",
        cache: false,
        dataType: "json",
        url: "getWarnCountRT.html", 
        success: function(obj){
        	if(obj.length>0){
        		for (var i = 0; i<obj.length; i++) {
        			timeArray.push(obj[i].time);
            		countArray.push(obj[i].count);
    			}

        		//与之前的列表比较
        		if(oldTimeArray.length!=0){
        			for(var i = 0; i<oldTimeArray.length; i++){
        				if((oldTimeArray[i] != timeArray[i]) ||(oldCountArray[i] != countArray[i])){
        					flag = false;
        					oldTimeArray = timeArray;
        					oldCountArray = countArray;
        				}
        			}
        			if(!flag){//数据有变化时重绘
                    	redrawWarn(oldTimeArray,oldCountArray);
        			}
        		}else{
        			oldTimeArray = timeArray;
        			oldCountArray = countArray;
                	redrawWarn(oldTimeArray,oldCountArray);
        		}
        	}else{
        		$("#warn").empty();
        	}
        	
     	}
	});
}
//实时告警趋势图
function redrawWarn(times,counts) {
	$('#warn').highcharts({ 
		chart: {
            backgroundColor: 'rgba(0,0,0,0)',
            type: 'line',
            height: 240,
			width: 430
        },
        credits: {
            enabled: false
        },
        exporting: {
            enabled:false
        },
		title: { 
			text: "实时告警趋势图", 
			style : {
				color : "#00b7ee",
//				align : 'left',
				fontFamily : '微软雅黑',
				fontSize: 14,
				fontWeight : 'normal'
			},
			x: -20 //center 
		}, 
		subtitle: { 
			text: '', 
			x: -20 
		}, 
		xAxis: { 
//			type: 'category',
			categories: times,
			//gridLineColor: '#197F07',//纵向网格线颜色
			gridLineDashStyle: 'longdash',//横向网格线样式
			gridLineWidth: 1 //纵向网格线宽度
			
		}, 
		yAxis: { 
			min: 0,
			//gridLineColor: '#197F07',//横向网格线颜色
			gridLineDashStyle: 'longdash',//横向网格线样式
			gridLineWidth: 1,//横向网格线宽度
			title: { 
				text: '告警数量(个)' 
			}, 
			plotLines: [{ 
				value: 0, 
				width: 1, 
				color: '#808080' 
			}] }, 
			tooltip: { 
				valueSuffix: '个'
			}, 
			legend: { 
				layout: 'vertical', 
				align: 'right', 
				verticalAlign: 'middle', 
				borderWidth: 0,
				enabled: false
			}, 
			series: [{ 
				name: '漏洞个数', 
				data: counts
			}] 
	});
}




/*告警TOP5*/
function getAlarmTop5(index){
	var nameArray = [];
	var dataArray = [];
	var colorArray = ['#FF7F50','#FF8C00','#FFA54F','#FFB90F','#FFDEAD'];
/*	var dataArray = [{'color':'#FF7F50','y':51}, 
	           {'color':'#FF8C00','y':42}, 
	           {'color':'#FFA54F','y':33}, 
	           {'color':'#FFB90F','y':24}, 
	           {'color':'#FFDEAD','y':15}] */
	var serviceId = index + 1;
	$.ajax({
        type: "POST",
        cache: false,
        dataType: "json",
        url: "getAlarmTop5.html?serviceId="+serviceId, 
        success: function(obj){ 
			if(obj.length>0){
	    		for (var i = 0; i<obj.length; i++) {
	    			nameArray.push(obj[i].name);	    			
	    			var map = {};
	        		map['color'] = colorArray[i];
	        		map['y'] = obj[i].count;
	        		dataArray.push(map);
				}
	    		
	    		redrawByService(nameArray,dataArray,serviceId);
	    	}else{
	    		$("#data"+serviceId).empty();
	    	}
     	}
	});
}



function redrawByService(names,datas,serviceId){
    $('#data'+serviceId).highcharts({
        chart: {
            type: 'bar',
            height: 240,
			width: 430
        },
        credits: {
            enabled: false
        },
        exporting: {
            enabled:false
        },
        title: {
            text: ''
        },
        xAxis: {
             gridLineWidth: 0,
             lineWidth :0,
             tickWidth:0,
            categories: names,
            labels: {
                rotation: 0,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: '微软雅黑'
                }
            }
            
        },
        yAxis: {
            gridLineWidth: 0,
            min: 0,
            title: {
                text: ''
            },
	        labels:{
	            enabled:false
	        }
        },
        legend: {
            enabled: false
        },

        series: [{
            name: '个数',
            data: datas,
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#080808',
                align: 'right',
                x: 20,
                y: 0,
                style: {
                    fontSize: '13px',
                    fontFamily: '微软雅黑',
                    textShadow: '0 0 0 black'
                }
            }
        }]
    });
}