function redrawEventList(eventnamelist, eventcountlist) {

	var myChart = echarts.init(document.getElementById('regionTOP'));

	var option = {
		grid : {
			show : false,
			borderWidth : 0,
			x : 100,
			y : 0,
			x2 : -22,
			y2 : 0,
		},
		textStyle : {
			color : "white",
			align : 'right',
			fontFamily : '微软雅黑',
			fontWeight : 'bold'
		},

		xAxis : [ {
			// 坐标的竖线是否显示
			show : false,
			type : 'value',
			textStyle : {
				color : 'white',
				fontFamily : '微软雅黑',
				fontSize : 10,
				//fontStyle : 'normal',
			// fontWeight : 'bold'
			}
		} ],
		yAxis : [
		         
		         {
		        	// min : 300,
			splitLine : {
				show : false
			},
			show : true,
			// 设置坐标轴y轴的字体格式
			axisLabel : {
				show : true,
				textStyle : {
					color : 'white',
					fontFamily : '微软雅黑',
					fontSize : 12,
					//fontStyle : 'normal',
					//fontWeight : 'bold'
				}
			},
			type : 'category',
			//data : eventnamelist
			data:eventnamelist[
	                {
	                    value:eventnamelist[0],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                },
	                {
	                    value:eventnamelist[1],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                },
	                {
	                    value:eventnamelist[2],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                },
	                {
	                    value:eventnamelist[3],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                },
	                {
	                    value:eventnamelist[4],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                }
			      ]
		} ],
		series : [ {
			name : '',
			type : 'bar',
			stack : '总量',
			itemStyle : {
				normal : {
					//color : "rgb(251,132,4)",
					color:function(params){
						//var colorList = [ "limegreen","blue","fuchsia","tomato","red","indigo","maroon","navy","white","purple","violetred", "orange","tomato","limegreen", "lawngreen","pink","darkred","deeppink"];
						//var colorList = ["#00a1e9", "#52c3f1", "#52c3f1","#9ed8f6", "#9ed8f6","#d3edfb", "#d3edfb","#d3edfb","#ffffff","#ffffff"];
						var colorList = ["#f4b4b7" , "#f4b4b7", "#ee878c","#e9505d","#e40027"];
						return colorList[params.dataIndex];
					},
					label : {
						show : true,
						position : 'insideLeft'
					}
				}
			},
			barCategoryGap : '60%',
			data : eventcountlist
		} ]
	};

	// 为echarts对象加载数据
	myChart.setOption(option);

}

/*var screenheight = $(window).height();
var triheight, triwidth;
if(screenheight <= 300){
	triheight = "12px";
	triwidth = "8px";
}
else{
	triheight = "12px";
	triwidth = "8px";
}*/

//alert(testlinear(78));
var colorizer =["#e40027", "#e9505d", "#ee878c","#f4b4b7","#fadcde"];
var colorizer1 =["#00a1e9", "#52c3f1", "#52c3f1","#9ed8f6", "#9ed8f6","#d3edfb", "#d3edfb","#d3edfb","#ffffff","#ffffff"];


function redrawLevelList(sllist){
	  $("#left-data tr").remove();
	   var originstate = d3.map();
	   for(var i=0;i<sllist.length; i++){
	   	originstate.set($.trim(sllist[i].name), sllist[i].count); 
	   }
	   //console.log(originstate);
	   var data = originstate.entries()
	       .sort(function(d1, d2) { return d2.value - d1.value; })
	       .slice(0, 10);
	   var barwidth = d3.scale.linear()
		.domain([1, data[0].value])
		.range([1, 180])
		.clamp(true);
	   
	   var rows = d3.select("#left-data").selectAll("tr.row")
	   		.data(data, function(d) { return d.key; });
     rows.enter()
         .append("tr")
         .attr("class", "row")
         .style("color", "red");
     var cols = rows.selectAll("td")
     .data(function(d,i) {
  	   var val=parseInt(d.value);
         return [
					"<span title='" + d.key + "' style='float:right; color:white; padding-bottom:6px; padding-top: 6px;'>" + d.key+ "</span>",
					"<div class='newbar' style='width:"+(barwidth(d.value)+60)+"px;'><div  class='bar' style='width:"+barwidth(d.value) +"px; color:"+colorizer[i]+";background-color:"+colorizer[i]+";' ></div><div class='bartext'>&nbsp;" + d.value + "</div></div>"]; });
					         
 cols.enter().append("td");
 cols.html(function(d) { return d; });
	
 }