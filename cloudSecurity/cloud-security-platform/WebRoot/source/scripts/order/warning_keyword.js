var chart; 
var time = [];
$(function() { 
	var orderId = $("#orderId").val();
    chart = new Highcharts.Chart({ 
        chart: { 
            renderTo: 'pic', //图表放置的容器，DIV 
            defaultSeriesType: 'line', //图表类型line(折线图), 
            zoomType: 'x'   //x轴方向可以缩放 
        }, 
        credits: { 
            enabled: false   //右下角不显示LOGO 
        }, 
        title: { 
            text: '告警统计' //图表标题 
        }, 
//        subtitle: { 
//            text: ''  //副标题 
//        }, 
        xAxis: {
        	//type: 'datetime',//x轴 
        	categories: time
        //  tickWidth:2000
//            categories: ['01:00:00', '02:00:00', '03:00:00', '04:00:00', '05:00:00', '06:00:00', '07:00:00', '8:00:00', '09:00:00', '10:00:00', 
// '11:00:00', '12:00:00', '13:00:00', '14:00:00', '15:00:00', '16:00:00', '17:00:00', '18:00:00', '19:00:00', '20:00:00', '21:00:00', '22:00:00', '23:00:00'], //x轴标签名称 
//            gridLineWidth: 1, //设置网格宽度为1 
//            lineWidth: 2,  //基线宽度 
//            labels:{y:26}  //x轴标签位置：距X轴下方26像素 
        }, 
        yAxis: {  //y轴 
            title: {text: '告警个数'}, //标题 
            lineWidth: 2 //基线宽度 
        }, 
        plotOptions:{ //设置数据点 
            line:{ 
                dataLabels:{ 
                    enabled:true  //在数据点上显示对应的数据值 
                }, 
                enableMouseTracking: false //取消鼠标滑向触发提示框 
            } 
        }, 
//        legend: {  //图例 
//            layout: 'horizontal',  //图例显示的样式：水平（horizontal）/垂直（vertical） 
//            backgroundColor: '#ffc', //图例背景色 
//            align: 'left',  //图例水平对齐方式 
//            verticalAlign: 'top',  //图例垂直对齐方式 
//            x: 100,  //相对X位移 
//            y: 70,   //相对Y位移 
//            floating: true, //设置可浮动 
//            shadow: true  //设置阴影 
//        }, 
        exporting: { 
            enabled: false  //设置导出按钮不可用 
        }, 
        series: [{  //数据列 
            name: '关键字告警统计', 
        }] 
    }); 
  //异步请求数据
    $.ajax({
    	url:"getData.html",
    	data: {"orderId":orderId},
    	dataType:"json",
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	type: "post",
        success:function(data){
            //定义一个数组
        	datas = [],
            //迭代，把异步获取的数据放到数组中
            $.each(data,function(i,d){
            	datas.push([d.alarm_time,d.count]);
            	time.push(d.alarm_time);
            });
        	//设置数据
        	if(datas.length==0){
        		alert("没有相关结果");
        	}else{
        		 chart.series[0].setData(datas); 
        	}
        },
    });
}); 
