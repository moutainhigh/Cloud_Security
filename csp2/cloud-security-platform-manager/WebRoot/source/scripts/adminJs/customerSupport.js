//用户信息查询
function activeCustomerSupport(){
	var username = $.trim($("#username").val());
	var email = $.trim($("#email").val());
	var mobile = $.trim($("#mobile").val());
	var orderno = $.trim($("#orderno").val());
	var assetname = $.trim($("#assetname").val());
	var assetaddr = $.trim($("#assetaddr").val());
	
	$.ajax({
        type: "POST",
        url: "customerSupport.html",
        data: {"username":username,"email":email,"mobile":mobile,"orderno":orderno,"assetname":assetname,"assetaddr":assetaddr},
        dataType:"json",
        success: function(data){
        	$("#userInfoTab").empty();
        	var userInfoList = data.userInfoList;
        	if(userInfoList == null || userInfoList.length == 0){
        		alert("未找到符合条件的结果！");
        	} else {
            	for (var i=0; i<userInfoList.length; i++){
            		var html = '<div class="tableBox" style="margin-top:10px;">';
            			html += '<div class="tableUser" style="">';
            			html += '<table cellpadding="0" cellspacing="0" border="1" width="954" bordercolor="#e0e0e0">';
            			html += '<thead><tr style="width:100%">';
            			html += '<th>用户名</th><th>单位</th><th>所属行业</th><th>电话</th><th>注册时间</th></tr>';
            			html += '</thead><tbody id="userInfo">';
            			html += '<tr><td>'+userInfoList[i].username+'</td>';
            			html += '<td>'+userInfoList[i].usercompony+'</td>';
            			html += '<td>'+userInfoList[i].userindustry+'</td>';
            			html += '<td>'+userInfoList[i].usermobile+'</td>';
            			html += '<td>'+userInfoList[i].usercreatetime+'</td></tr>';
            			html += '</tbody></table></div>';

            			if(userInfoList[i].assetlist.length > 0){
                			html += '<div class="tableUser" style="margin-top:10px;">';
                			html += '<table cellpadding="0" cellspacing="0" border="1" width="954" bordercolor="#e0e0e0">';
                			html += '<thead><tr style="width:100%"><th>资产名称</th><th>资产地址</th></tr></thead>';
                			html += '<tbody id="assetInfo">';
                			for(var j=0; j<userInfoList[i].assetlist.length; j++){
                				if(userInfoList[i].assetlist[j] != null){
                					if(userInfoList[i].assetlist[j].assetName != null && userInfoList[i].assetlist[j].assetName != ""
                						&& userInfoList[i].assetlist[j].assetAddr != null && userInfoList[i].assetlist[j].assetAddr != ""){
                        				html += '<tr><td><a href="javascript:void(0)" onclick="assetDetail('+userInfoList[i].assetlist[j].assetId+')">'+userInfoList[i].assetlist[j].assetName+'</a></td>';
                			         	html +=	'<td>'+userInfoList[i].assetlist[j].assetAddr+'</td></tr>';
                					}
                				}
                			}

                			html += '</tbody></table></div>';
            			}
            			
            			if(userInfoList[i].orderlist.length > 0){
                			html += '<div class="tableUser" style="margin-top:10px;">';
                			html += '<table cellpadding="0" cellspacing="0" border="1" width="954" bordercolor="#e0e0e0">';
                			html += '<thead><tr style="width:100%"><th>订单编号</th><th>下单时间</th><th>下单用户</th><th>订单类型</th><th>订单服务起止时间</th><th>服务类型</th></tr></thead>';
                			html += '<tbody id="orderInfo">';
                			for(var k=0; k<userInfoList[i].orderlist.length; k++){
                				if(userInfoList[i].orderlist[k] != null){
                					var order = userInfoList[i].orderlist[k];
                					if(order.id != null && order.id != ""){
                        				html += '<tr><td>'+getOrderDetail(order.serviceId,order.isAPI,order.id,order.status,order.websoc,order.begin_date,order.type)+'</td>';
                        				html += '<td>'+format(order.create_date.time, 'yyyy-MM-dd HH:mm:ss')+'</td>';
                        				html += '<td>'+userInfoList[i].username+'</td>';
                        				if(order.type == 1){
                        					html += '<td>长期</td>';
                        					html += '<td>'+format(order.begin_date.time,  'yyyy-MM-dd HH:mm:ss')+'~'+format(order.end_date.time,  'yyyy-MM-dd HH:mm:ss')+'</td>';
                        				} else {
                        					html += '<td>单次</td>';
                        					html += '<td>'+format(order.begin_date.time,  'yyyy-MM-dd HH:mm:ss')+'</td>';
                        				}
                        				
                        				
                        				if(order.isAPI == 0){
                        					html += '<td>监测服务</td>';
                        				} else if(order.isAPI == 0){
                        					html += '<td>API服务</td>';
                        				} else {
                        					html += '<td>WAF防护</td>';
                        				}
                        				html += '</tr>';
                					}
                				}
                			}
                			html += '</tbody></table></div>';
                			html += '</div>';
            			}
            			$("#userInfoTab").append(html);
            	}
        	}
},
     });
}

var format = function(time, format){
    var t = new Date(time);
    var tf = function(i){return (i < 10 ? '0' : '') + i};
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
        switch(a){
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    });
}
//订单信息查询
function activeQueryOrder(){
	var createDateS = $("#order_createdate_start").val();
	var createDateE = $("#order_createdate_end").val();
	var createDateStart=new Date(createDateS.replace("-", "/").replace("-", "/"));  
    var createDateEnd=new Date(createDateE.replace("-", "/").replace("-", "/"));
    if(createDateEnd < createDateStart){
    	alert("下单时间段的结束时间小于开始时间！");
    	return;
    }
    var servDateS = $("#service_startdate").val();
    var servDateE = $("#service_enddate").val();
    var servDateStart = new Date(servDateS.replace("-", "/").replace("-", "/"));
    var servDateEnd = new Date(servDateE.replace("-", "/").replace("-", "/"));
    if(servDateEnd < servDateStart){
    	alert("服务时间的结束时间小于开始时间！");
    	return;
    }
    var orderno = $.trim($("#orderno2").val());
    var orderusername = $.trim($("#orderusername").val());
    var isapi = $.trim($("#isApiSel").val());
    var assetname = $.trim($("#assetname2").val());
    var assetaddr = $.trim($("#assetaddr2").val());
    
    $.ajax({
        type: "POST",
        url: "customerOrder.html",
        data: {"orderno":orderno,"createDateS":createDateS,"createDateE":createDateE,"servDateS":servDateS,"servDateE":servDateE,"orderusername":orderusername,"isapi":isapi,"assetname":assetname,"assetaddr":assetaddr},
        dataType:"json",
        success:function(data){
        	var orderList = data.orderList;
        	$("#queryOrder").empty();
        	if(orderList.length == 0){
        		alert("未找到符合条件的结果！");
        		return;
        	}
        	for(var i=0; i<orderList.length; i++){
        		var order = orderList[i];
        		var html = '<tr><td>'+getOrderDetail(order.serviceId,order.isAPI,order.id,order.status,order.websoc,order.begin_date,order.type)+'</td>';
        			html += '<td>'+format(order.create_date.time, 'yyyy-MM-dd HH:mm:ss')+'</td>';
        			html += '<td>'+order.res1+'</td>';
        			if(order.type == 1){
        				html += '<td>长期</td>';
        				html += '<td>'+format(order.begin_date.time, 'yyyy-MM-dd HH:mm:ss')+'~'+format(order.end_date.time, 'yyyy-MM-dd HH:mm:ss')+'</td>';
        			} else {
        				html += '<td>单次</td>';
        				html += '<td>'+format(order.begin_date.time, 'yyyy-MM-dd HH:mm:ss')+'</td>';
        			}
        			if(order.isAPI == 0){
        				html += '<td>监测服务</td>';
        			} else if(order.isAPI == 1){
        				html += '<td>API服务</td>';
        			} else {
        				html += '<td>WAF防护</td>';
        			}
        			
        			$("#queryOrder").append(html);
        	}
        }
    });
}

function getOrderDetail(serviceId,isAPI,orderId,status,websoc,begin_date,type){
	var html = '';
	var currentTime = new Date().getTime();
	if(serviceId >= 1 && serviceId <= 5){
		if(isAPI == 1){
			html = '<a href="'+getCurrentAddress()+'/apiDetails.html?orderId='+orderId+'" target="_blank" >'+orderId+'</a>';
		} else if(isAPI != 1 && status == 0){
			html = '<a href="'+getCurrentAddress()+'/orderDetails.html?orderId='+orderId+'" target="_blank">'+orderId+'</a>';
		} else if(status == 2){
			html = '<a href="'+getCurrentAddress()+'/warningInit.html?orderId='+orderId+'&type='+type+'&websoc='+websoc+'" target="_blank">'+orderId+'</a>';
		} else if(isAPI == 0 && status == 1){
			html = '<a href="'+getCurrentAddress()+'/warningInit.html?orderId='+orderId+'&type='+type+'&websoc='+websoc+'" target="_blank">'+orderId+'</a>';
		} else if(isAPI == 1 && status == 1){
			html = '<a href="'+getCurrentAddress()+'/selfHelpOrderAPIInit.html?apiId='+serviceId+'&indexPage=2">'+orderId+'</a>';
		} else if((status == 4 || status == 5) && websoc != 2){
			html = '<a href="'+getCurrentAddress()+'/warningInit.html?orderId='+orderId+'&type='+type+'&websoc='+websoc+'" target="_blank">'+orderId+'</a>';
		} else if(begin_date.time <= currentTime && status == 3){
			html = '<a href="'+getCurrentAddress()+'/warningInit.html?orderId='+orderId+'&type='+type+'&websoc='+websoc+'" target="_blank">'+orderId+'</a>';
		} else {
			html = '<a href="#">'+orderId+'</a>';
		}
	} else if(serviceId == 6){
		if(isAPI == 2 && status == 4){
			html = '<a href="'+getCurrentAddress()+'/warningWaf.html?orderId='+orderId+'&type='+type+'" target="_blank">'+orderId+'</a>';
		} else {
			html = '<a href="#">'+orderId+'</a>';
		}
	} else {
		html = '<a href="#">'+orderId+'</a>';
	}
	return html;
}

function getCurrentAddress(){
	var url = location.href;
	return url.substring(0,url.lastIndexOf("/"));
}

//资源信息查询
function activeQueryResource(){
	var resourcename = $.trim($("#resourcename").val());
	var resourceaddr = $.trim($("#resourceaddr").val());
    var isapi = $.trim($("#isApiSel").val());
    
    $.ajax({
        type: "POST",
        url: "customerResource.html",
        data: {"resourcename":resourcename,"resourceaddr":resourceaddr,"isapi":isapi},
        dataType:"json",
        success:function(data){
        	var resourceList = data.resourceList;
        	$("#queryResource").empty();
        	if(resourceList.length == 0){
        		alert("未找到符合条件的结果！");
        		return;
        	}
        	for(var i=0; i<resourceList.length; i++){
        		var resource = resourceList[i];
        		var html = '<tr><td>'+resource.engine_number+'</td>';
        			html += '<td>'+resource.engine_addr+'</td>';
        			html += '<td>'+'监测服务'+'</td>';
        			if(resource.status == 1){
        				html += '<td>正常</td>';
        			} else {
        				html += '<td>异常</td>';
        			}
        			html += '<td>'+resource.memoryUsage+'</td>';
        			html += '<td>'+resource.cpuUsage+'</td>';
        			html += '<td>'+resource.diskUsage+'</td>';
        			html += '<td>'+'暂无'+'</td>';
        			$("#queryResource").append(html);
        	}
        }
    });
}

function assetDetail(assetId){
	$.ajax({
	       type: "POST",
	       url: "assetDetail.html",
	       data: {"assetId":assetId},
	       dataType:"json",
	       success: function(data){
	      		$("#assetName").html(data.assetName);
	      		$("#assetAddr").html(data.assetAddr);
	      		$("#create_date").html(data.create_date);
	      		$("#purpose").html(data.purpose);
	      		
	      		$(".mark,.data_tanc").show();
	      	}
	     });
}