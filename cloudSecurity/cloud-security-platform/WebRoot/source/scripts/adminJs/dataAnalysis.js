$(function () {
	orderData();
});

function orderData(){

	var begin_datevo = $("#begin_date").val();
	var end_datevo = $("#end_date").val();
	var state = $("#state").val();
	var type = $("#type").val();
	var servName = $("#servName").val();
	var datas = [];
	var chart = new Highcharts.Chart({
        chart: {
        	renderTo: 'orderStatisticsAnalysis', 
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        exporting:{
            enabled:false
        },
        credits: {
            enabled: false
        },
        title: {
            text: ''
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: ' ',
        }]
    });
	
	//异步请求数据
    $.ajax({
    	url:"orderStatisticsAnalysis.html",
    	data: {"begin_datevo":begin_datevo,"end_datevo":end_datevo,"state":state ,"type":type,"servName":servName},
    	dataType:"json",
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	type: "post",
        success:function(data){
//        	alert(data);
            //定义一个数组
        	datas = [],
            //迭代，把异步获取的数据放到数组中
            $.each(data,function(i,d){
            	datas.push([d.name,d.count]);
            });
            //设置数据
            chart.series[0].setData(datas);  
        },
    });

}