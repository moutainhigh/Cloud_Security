$(function() {
	getAllEngine();
});
var getAllEngine = function() {
	var tbody = $("#tbody");
	var engineName = $("#engineName").val();
	var engineAddr = $("#engineAddr").val();
	var factory = $("#factory").val();
	$.ajax({
		type : "POST",
		url : "equFindAll.html",
		dataType : "json",
		data : {
			engineName : engineName,
			engineAddr : engineAddr,
			factory : factory
		},
		success : function(data) {
			tbody.empty();
			$.each(data, function(i, p) {
				var factory = "";
				if (p['equipment_factory'] == 0) {
					factory = "安恒";
				}else{
					factory = "创宇";
				}
//				tbody.append("<tr height='40'>" + "<td style='display: none;'>" + p['id'] + "</td>"
//						+ "<td width='250'><a href='javascript:void(0)'><span onclick='openNamePopBox("+p['id']+","+p['engine']+", " +
//								"\""+p['engine_addr']+"\")'>" + p['engine_name']
//						+ "</span></a></td>" + "<td width='336'>"
//						+ p['engine_addr'] + "</td>" + "<td width='250'>"
//						+ factory + "</td>"
//						+ "<td width='260'><a href='javascript:void(0)' onclick='upd(" + p['id']
//						+ ")' >修改</a><a href='javascript:void(0)' onclick='del(" + p['id']
//						+ ")'>删除</a></td>" + "</tr>");
				
				tbody.append("<tr>"
						+ "<td><a href='javascript:void(0)' style='color:#f1a31a;width:auto'><span onclick='openNamePopBox("+p['id']+","+p['engine']+", " +
								"\""+p['engine_addr']+"\")'>" + p['engine_name']
						+ "</span></a></td>" + "<td>"
						+ p['engine_addr'] + "</td>" + "<td>"
						+ factory + "</td>"
						+ "<td><a href='javascript:void(0)' class='add_change' onclick='upd(" + p['id']
						+ ")' >修改</a><a href='javascript:void(0)' class='delet' onclick='del(" + p['id']
						+ ")'>删除</a></td>" + "</tr>");
			});
		}
	});
}, upd = function(id) {
	$('#popBox').dialog({
		title : '修改设备引擎信息'
	});
	$.ajax({
		type : "POST",
		url : "findEquById.html",
		dataType : "json",
		data : {
			id : id
		},
		success : function(data) {
			$("#id").attr("value", data.id);
			$("#equName").attr("value", data.engine_name);
			$("#equIP").attr("value", data.engine_addr);
			$("#factoryName").attr("value", data.equipment_factory);
			$("#oldEquName").attr("value", data.engine_name);
		}
	});
	$("#butType").empty();
	$("#butType").append(
			"<input type='button' value='立即修改' class='submit' onclick='addAndUpdate()' id='upd' />");
	$("#popBox").dialog("open");
	return false;
}, vaildata = function() {
	var id = $("#id").val();
	if (id != '') {
		$("#addAndUpd").attr("action", "updEqu.html");
	} else {
		$("#addAndUpd").attr("action", "addEqu.html");
	}
}, del = function(id) {
	if (confirm("是否确认删除?")) {
		$.ajax({
			type : "POST",
			url : "delEquById.html",
			dataType : "json",
			data : {
				id : id
			},
			success : function(data) {
				window.location.href = "";
			}
		});
	}
},openNamePopBox=function(id,engine,ip){
	$( "#namePopBox" ).dialog( "open" );
	getCpu(id,engine,ip);
	getRam(id,engine,ip);
	getDisk(id,engine,ip);
	getCount(id);
	return false;
},addAndUpdate=function(){
	var engineName = $.trim($("#equName").val());
	var engineAddr = $.trim($("#equIP").val());
	var engineId = $.trim($("#id").val());
	
	var newRegex = /^((?!([hH][tT][tT][pP][sS]?)\:*\/*)([\w\.\-]+(\:[\w\.\&%\$\-]+)*@)?((([^\s\(\)\<\>\\\"\.\[\]\,@;:]+)(\.[^\s\(\)\<\>\\\"\.\[\]\,@;:]+)*(\.[a-zA-Z]{2,4}))|((([01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}([01]?\d{1,2}|2[0-4]\d|25[0-5])))(\b\:(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|[1-5]\d{4}|[1-9]\d{0,3}|0)\b)?((\/[^\/][\w\.\,\?\'\\\/\+&%\$#\=~_\-@]*)*[^\.\,\?\"\'\(\)\[\]!;<>{}\s\x7F-\xFF])?)$/;
    var strRegex = /^((([hH][tT][tT][pP][sS]?):\/\/)([\w\.\-]+(\:[\w\.\&%\$\-]+)*@)?((([^\s\(\)\<\>\\\"\.\[\]\,@;:]+)(\.[^\s\(\)\<\>\\\"\.\[\]\,@;:]+)*(\.[a-zA-Z]{2,4}))|((([01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}([01]?\d{1,2}|2[0-4]\d|25[0-5])))(\b\:(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|[1-5]\d{4}|[1-9]\d{0,3}|0)\b)?((\/[^\/][\w\.\,\?\'\\\/\+&%\$#\=~_\-@]*)*[^\.\,\?\"\'\(\)\[\]!;<>{}\s\x7F-\xFF])?)$/;

	if(engineName==null||engineName==''){
		alert("设备引擎名称不能为空！");
		return false;
	}
	if(engineAddr==null||engineAddr==''){
		alert("设备引擎IP地址不能为空！");
		return false;
	}
	var pattern = /\d+\.\d+\.\d+\.\d+/;
	if((!strRegex.test(engineAddr)&&!newRegex.test(engineAddr))||(strRegex.test(engineAddr)&&engineAddr.indexOf('\/\/\/')!=-1)){
		alert("IP地址输入有误！");
		return false;
	}

	$.ajax({
		type:"POST",
		url:"checkEngineNameAndAddr.html",
		dataType:"json",
		data:{
			"equId":engineId,
			"engineName":engineName,
			"engineAddr":engineAddr,
		},
		success:function(data){
			if (data.message != null && data.message != ''){
				alert(data.message);
			}else {
				$("#addAndUpd").submit();
			}
		}
	});

}