<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>ECharts</title>
<link rel="stylesheet" href="${ctx}/source/css/refresh.css" />
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/echarts3/echarts.min.js"></script>
<script src="${ctx}/source/scripts/echarts3/china.js"></script>
<script src="${ctx}/source/scripts/echarts3/world.js"></script>

<style>
#china-map {
	width: 100%;
	height: 800px;
	overflow: hidden;
	margin: auto;
}
#backImg {
	background: url("${ctx}/source/images/background4.png");
	background-size: cover;
}

.rides-cs {
	filter: alpha(Opacity = 80);
	-moz-opacity: 0.1;
	opacity: 0.95;
	background-color:rgba(1,1,1,0.4);
	position: absolute;
	bottom: 0px;
	right: 1px;
	z-index: 999;
}

.rides-cs .floatL {
	width: 35px;
	float: left;
	position: relative;
	z-index: 5;
}

.rides-cs .floatL a {
	font-size: 0;
	text-indent: -999em;
	display: block;
}

.rides-cs .floatR {
	width: 800px;
	float: left;
	padding: 5px;
	overflow: hidden;
}

.rides-cs .floatR .cn {
	
	background:#F7F7F7;
}

.rides-cs .cn h3 {
	font-size: 14px;
	color: #333;
	font-weight: 600;
	line-height: 24px;
	padding: 5px
}

.rides-cs .btnOpen, .rides-cs .btnCtn {
	position: relative;
	z-index: 9;
	top: 50%;
	margin-top:-80px;
	left: 0;
	background: url(${ctx}/source/images/shopnc.png) no-repeat;
	
	background-size:cover;;
	display: block;
	width: 20px;
	height: 160px; 
	overflow: hidden;
	padding: 8px;
}

.rides-cs .btnOpen {
	background-position: 8px 12px;
}

.rides-cs .btnCtn {
	background-position: -31px 12px;
}
#table-id tr .one{
	width:15%;
}
#table-id tr .two{
	width:35%;
}
#table-id tr .three{
	width:20%;
}
#table-id tr .four{
	width:15%;
}
#table-id tr .five{
	width:15%;
}
</style>
</head>

<body>
	<div id="backImg" style="width:100%;height:100%;;position:relative">
		<div id="logo" onclick="getContextPath()"
			style="width:116px;height:43px;left:3%;top:3%;position:absolute;z-index:10;background: url('${ctx}/source/images/anquanbang_white_logo.png');"></div>
		<div id="china-map"></div>
		<div class="rides-cs" style="position:absolute;z-index:10;">

			<div class="floatL" id="urlLeft">
				<a style="display:block" id="aFloatTools_Show" class="btnOpen"
					title="" href="javascript:void(0);">??????</a> <a style="display:none"
					id="aFloatTools_Hide" class="btnCtn" title=""
					href="javascript:void(0);">??????</a>
			</div>
			<div id="divFloatToolsView" class="floatR" style="display:none">
				<table id="events" style="height:100%">
					<colgroup class="second-level">
						<col class="col-timestamp" span="1"/>
						<col class="col-border" span="1"/>
						<col class="col-attacker-org" span="1"/>
						<col class="col-border" span="1"/>
						<col class="col-attacker-ip" span="1"/>
						<col class="col-border" span="1"/>
						<col class="col-attacked-org" span="1"/>
						<col class="col-border" span="1"/>
						<col class="col-attacked-ip" span="1"/>
						<col class="col-border" span="1"/>
						<col class="col-service" span="1"/>
						<col class="col-border" span="1"/>
						<col class="col-port" span="1"/>
					</colgroup>
					<tbody id='table-id'style="height:100%">

					</tbody>
				</table>

			</div>


		</div>

	</div>

	<script>
		function lhs() {

			setInterval(dsb, 1000);
		}
		
		var urlData;
		var x = 0;
		function dsb() {
			var tbody = document.getElementById("table-id");
			var url;	
			if (x < 13) {
				
				tbody.innerHTML = "<tr class='second-level'><th class='th-title col-service one'>??????</th><th class='col-border'></th><th class='th-title col-attacker-ip two'>URL</th><th class='col-border'></th><th class='th-title col-attacker-ip three'>IP</th><th class='col-border'></th><th class='th-title col-attacked-ip four'>??????</th><th class='col-border'></th><th class='th-title col-service five'>??????</th></tr>";
				for (var i = 0; i < 8; i++) {
					var tr = document.createElement("tr");
					tr.setAttribute("class", "third-level");
					td = document.createElement("td");
					td.innerHTML = i + x+1;
					td.setAttribute("align", "center");
					tr.appendChild(td);
					td2 = document.createElement("td");
					td2.setAttribute("class", "col-border");
					tr.appendChild(td2);

					td3 = document.createElement("td");
					if(urlData[i + x].url.length<=5){
						url="";
					}else if(urlData[i + x].url.length<=50){
						url=urlData[i + x].url;
					}
					else{
						url=urlData[i + x].url.substring(0,50);
						//url=urlData[i + x].url;
					}
					td3.innerHTML = url;
					td3.setAttribute("align", "left");
					tr.appendChild(td3);
					td4 = document.createElement("td");
					td4.setAttribute("class", "col-border");
					tr.appendChild(td4);
					
					td3 = document.createElement("td");
					td3.innerHTML = urlData[i + x].ip;
					td3.setAttribute("align", "left");
					tr.appendChild(td3);
					td4 = document.createElement("td");
					td4.setAttribute("class", "col-border");
					tr.appendChild(td4);

					td3 = document.createElement("td");
					td3.innerHTML = urlData[i + x].country;
					td3.setAttribute("align", "center");
					tr.appendChild(td3);
					td4 = document.createElement("td");
					td4.setAttribute("class", "col-border");
					tr.appendChild(td4);

					td3 = document.createElement("td");
					td3.innerHTML = urlData[i + x].city;
					td3.setAttribute("align", "center");
					tr.appendChild(td3);
					td4 = document.createElement("td");
					td4.setAttribute("class", "col-border");
					tr.appendChild(td4);

					
					tbody.appendChild(tr);
				}
				x = x + 1;
				// con.appendChild(table); 
			}
		}
		function getContextPath() {
			var pathName = document.location.pathname;
			var index = pathName.substr(1).indexOf("/");
			var result = pathName.substr(0, index + 1);
			parent.location.href = "index.html";
		}
		/**
		 *??????????????????????????????
		 */
		var mainContainer = document.getElementById('backImg');
		var urlLeft = document.getElementById('urlLeft');
		var urlRight = document.getElementById('divFloatToolsView');
		var btnHide = document.getElementById('aFloatTools_Hide');	
		var btnShow = document.getElementById('aFloatTools_Show');
		var resizemainMapContainer = function() {
			mainContainer.style.width = window.innerWidth + 'px';
			mainContainer.style.height = window.innerHeight+ 'px';
			urlLeft.style.height = window.innerHeight*0.31+ 'px';
			urlRight.style.width = window.innerWidth*0.66+ 'px';
			urlRight.style.height = window.innerHeight*0.31+ 'px';
		};
		resizemainMapContainer();

		var worldMapContainer = document.getElementById('china-map');
		//?????????chart????????????????????????,????????????????????????????????????
		var resizeWorldMapContainer = function() {
			//worldMapContainer.style.width = window.innerWidth-10+'px';
			worldMapContainer.style.height = window.innerHeight*1 + 'px';
		};
		//??????????????????
		resizeWorldMapContainer();

		var myChart = echarts.init(document.getElementById('china-map'));
		var latlong = {};
		latlong.AD = {
			'latitude' : 42.5,
			'longitude' : 1.5
		};
		latlong.AE = {
			'latitude' : 24,
			'longitude' : 54
		};
		latlong.AF = {
			'latitude' : 33,
			'longitude' : 65
		};
		latlong.AG = {
			'latitude' : 17.05,
			'longitude' : -61.8
		};
		latlong.AI = {
			'latitude' : 18.25,
			'longitude' : -63.1667
		};
		latlong.AL = {
			'latitude' : 41,
			'longitude' : 20
		};
		latlong.AM = {
			'latitude' : 40,
			'longitude' : 45
		};
		latlong.AN = {
			'latitude' : 12.25,
			'longitude' : -68.75
		};
		latlong.AO = {
			'latitude' : -12.5,
			'longitude' : 18.5
		};
		latlong.AP = {
			'latitude' : 35,
			'longitude' : 105
		};
		latlong.AQ = {
			'latitude' : -90,
			'longitude' : 0
		};
		latlong.AR = {
			'latitude' : -34,
			'longitude' : -64
		};
		latlong.AS = {
			'latitude' : -14.3333,
			'longitude' : -170
		};
		latlong.AT = {
			'latitude' : 47.3333,
			'longitude' : 13.3333
		};
		latlong.AU = {
			'latitude' : -27,
			'longitude' : 133
		};
		latlong.AW = {
			'latitude' : 12.5,
			'longitude' : -69.9667
		};
		latlong.AZ = {
			'latitude' : 40.5,
			'longitude' : 47.5
		};
		latlong.BA = {
			'latitude' : 44,
			'longitude' : 18
		};
		latlong.BB = {
			'latitude' : 13.1667,
			'longitude' : -59.5333
		};
		latlong.BD = {
			'latitude' : 24,
			'longitude' : 90
		};
		latlong.BE = {
			'latitude' : 50.8333,
			'longitude' : 4
		};
		latlong.BF = {
			'latitude' : 13,
			'longitude' : -2
		};
		latlong.BG = {
			'latitude' : 43,
			'longitude' : 25
		};
		latlong.BH = {
			'latitude' : 26,
			'longitude' : 50.55
		};
		latlong.BI = {
			'latitude' : -3.5,
			'longitude' : 30
		};
		latlong.BJ = {
			'latitude' : 9.5,
			'longitude' : 2.25
		};
		latlong.BM = {
			'latitude' : 32.3333,
			'longitude' : -64.75
		};
		latlong.BN = {
			'latitude' : 4.5,
			'longitude' : 114.6667
		};
		latlong.BO = {
			'latitude' : -17,
			'longitude' : -65
		};
		latlong.BR = {
			'latitude' : -10,
			'longitude' : -55
		};
		latlong.BS = {
			'latitude' : 24.25,
			'longitude' : -76
		};
		latlong.BT = {
			'latitude' : 27.5,
			'longitude' : 90.5
		};
		latlong.BV = {
			'latitude' : -54.4333,
			'longitude' : 3.4
		};
		latlong.BW = {
			'latitude' : -22,
			'longitude' : 24
		};
		latlong.BY = {
			'latitude' : 53,
			'longitude' : 28
		};
		latlong.BZ = {
			'latitude' : 17.25,
			'longitude' : -88.75
		};
		latlong.CA = {
			'latitude' : 54,
			'longitude' : -100
		};
		latlong.CC = {
			'latitude' : -12.5,
			'longitude' : 96.8333
		};
		latlong.CD = {
			'latitude' : 0,
			'longitude' : 25
		};
		latlong.CF = {
			'latitude' : 7,
			'longitude' : 21
		};
		latlong.CG = {
			'latitude' : -1,
			'longitude' : 15
		};
		latlong.CH = {
			'latitude' : 47,
			'longitude' : 8
		};
		latlong.CI = {
			'latitude' : 8,
			'longitude' : -5
		};
		latlong.CK = {
			'latitude' : -21.2333,
			'longitude' : -159.7667
		};
		latlong.CL = {
			'latitude' : -30,
			'longitude' : -71
		};
		latlong.CM = {
			'latitude' : 6,
			'longitude' : 12
		};
		latlong.CN = {
			'latitude' : 35,
			'longitude' : 105
		};
		latlong.CO = {
			'latitude' : 4,
			'longitude' : -72
		};
		latlong.CR = {
			'latitude' : 10,
			'longitude' : -84
		};
		latlong.CU = {
			'latitude' : 21.5,
			'longitude' : -80
		};
		latlong.CV = {
			'latitude' : 16,
			'longitude' : -24
		};
		latlong.CX = {
			'latitude' : -10.5,
			'longitude' : 105.6667
		};
		latlong.CY = {
			'latitude' : 35,
			'longitude' : 33
		};
		latlong.CZ = {
			'latitude' : 49.75,
			'longitude' : 15.5
		};
		latlong.DE = {
			'latitude' : 51,
			'longitude' : 9
		};
		latlong.DJ = {
			'latitude' : 11.5,
			'longitude' : 43
		};
		latlong.DK = {
			'latitude' : 56,
			'longitude' : 10
		};
		latlong.DM = {
			'latitude' : 15.4167,
			'longitude' : -61.3333
		};
		latlong.DO = {
			'latitude' : 19,
			'longitude' : -70.6667
		};
		latlong.DZ = {
			'latitude' : 28,
			'longitude' : 3
		};
		latlong.EC = {
			'latitude' : -2,
			'longitude' : -77.5
		};
		latlong.EE = {
			'latitude' : 59,
			'longitude' : 26
		};
		latlong.EG = {
			'latitude' : 27,
			'longitude' : 30
		};
		latlong.EH = {
			'latitude' : 24.5,
			'longitude' : -13
		};
		latlong.ER = {
			'latitude' : 15,
			'longitude' : 39
		};
		latlong.ES = {
			'latitude' : 40,
			'longitude' : -4
		};
		latlong.ET = {
			'latitude' : 8,
			'longitude' : 38
		};
		latlong.EU = {
			'latitude' : 47,
			'longitude' : 8
		};
		latlong.FI = {
			'latitude' : 62,
			'longitude' : 26
		};
		latlong.FJ = {
			'latitude' : -18,
			'longitude' : 175
		};
		latlong.FK = {
			'latitude' : -51.75,
			'longitude' : -59
		};
		latlong.FM = {
			'latitude' : 6.9167,
			'longitude' : 158.25
		};
		latlong.FO = {
			'latitude' : 62,
			'longitude' : -7
		};
		latlong.FR = {
			'latitude' : 46,
			'longitude' : 2
		};
		latlong.GA = {
			'latitude' : -1,
			'longitude' : 11.75
		};
		latlong.GB = {
			'latitude' : 54,
			'longitude' : -2
		};
		latlong.GD = {
			'latitude' : 12.1167,
			'longitude' : -61.6667
		};
		latlong.GE = {
			'latitude' : 42,
			'longitude' : 43.5
		};
		latlong.GF = {
			'latitude' : 4,
			'longitude' : -53
		};
		latlong.GH = {
			'latitude' : 8,
			'longitude' : -2
		};
		latlong.GI = {
			'latitude' : 36.1833,
			'longitude' : -5.3667
		};
		latlong.GL = {
			'latitude' : 72,
			'longitude' : -40
		};
		latlong.GM = {
			'latitude' : 13.4667,
			'longitude' : -16.5667
		};
		latlong.GN = {
			'latitude' : 11,
			'longitude' : -10
		};
		latlong.GP = {
			'latitude' : 16.25,
			'longitude' : -61.5833
		};
		latlong.GQ = {
			'latitude' : 2,
			'longitude' : 10
		};
		latlong.GR = {
			'latitude' : 39,
			'longitude' : 22
		};
		latlong.GS = {
			'latitude' : -54.5,
			'longitude' : -37
		};
		latlong.GT = {
			'latitude' : 15.5,
			'longitude' : -90.25
		};
		latlong.GU = {
			'latitude' : 13.4667,
			'longitude' : 144.7833
		};
		latlong.GW = {
			'latitude' : 12,
			'longitude' : -15
		};
		latlong.GY = {
			'latitude' : 5,
			'longitude' : -59
		};
		latlong.HK = {
			'latitude' : 22.25,
			'longitude' : 114.1667
		};
		latlong.HM = {
			'latitude' : -53.1,
			'longitude' : 72.5167
		};
		latlong.HN = {
			'latitude' : 15,
			'longitude' : -86.5
		};
		latlong.HR = {
			'latitude' : 45.1667,
			'longitude' : 15.5
		};
		latlong.HT = {
			'latitude' : 19,
			'longitude' : -72.4167
		};
		latlong.HU = {
			'latitude' : 47,
			'longitude' : 20
		};
		latlong.ID = {
			'latitude' : -5,
			'longitude' : 120
		};
		latlong.IE = {
			'latitude' : 53,
			'longitude' : -8
		};
		latlong.IL = {
			'latitude' : 31.5,
			'longitude' : 34.75
		};
		latlong.IN = {
			'latitude' : 20,
			'longitude' : 77
		};
		latlong.IO = {
			'latitude' : -6,
			'longitude' : 71.5
		};
		latlong.IQ = {
			'latitude' : 33,
			'longitude' : 44
		};
		latlong.IR = {
			'latitude' : 32,
			'longitude' : 53
		};
		latlong.IS = {
			'latitude' : 65,
			'longitude' : -18
		};
		latlong.IT = {
			'latitude' : 42.8333,
			'longitude' : 12.8333
		};
		latlong.JM = {
			'latitude' : 18.25,
			'longitude' : -77.5
		};
		latlong.JO = {
			'latitude' : 31,
			'longitude' : 36
		};
		latlong.JP = {
			'latitude' : 36,
			'longitude' : 138
		};
		latlong.KE = {
			'latitude' : 1,
			'longitude' : 38
		};
		latlong.KG = {
			'latitude' : 41,
			'longitude' : 75
		};
		latlong.KH = {
			'latitude' : 13,
			'longitude' : 105
		};
		latlong.KI = {
			'latitude' : 1.4167,
			'longitude' : 173
		};
		latlong.KM = {
			'latitude' : -12.1667,
			'longitude' : 44.25
		};
		latlong.KN = {
			'latitude' : 17.3333,
			'longitude' : -62.75
		};
		latlong.KP = {
			'latitude' : 40,
			'longitude' : 127
		};
		latlong.KR = {
			'latitude' : 37,
			'longitude' : 127.5
		};
		latlong.KW = {
			'latitude' : 29.3375,
			'longitude' : 47.6581
		};
		latlong.KY = {
			'latitude' : 19.5,
			'longitude' : -80.5
		};
		latlong.KZ = {
			'latitude' : 48,
			'longitude' : 68
		};
		latlong.LA = {
			'latitude' : 18,
			'longitude' : 105
		};
		latlong.LB = {
			'latitude' : 33.8333,
			'longitude' : 35.8333
		};
		latlong.LC = {
			'latitude' : 13.8833,
			'longitude' : -61.1333
		};
		latlong.LI = {
			'latitude' : 47.1667,
			'longitude' : 9.5333
		};
		latlong.LK = {
			'latitude' : 7,
			'longitude' : 81
		};
		latlong.LR = {
			'latitude' : 6.5,
			'longitude' : -9.5
		};
		latlong.LS = {
			'latitude' : -29.5,
			'longitude' : 28.5
		};
		latlong.LT = {
			'latitude' : 55,
			'longitude' : 24
		};
		latlong.LU = {
			'latitude' : 49.75,
			'longitude' : 6
		};
		latlong.LV = {
			'latitude' : 57,
			'longitude' : 25
		};
		latlong.LY = {
			'latitude' : 25,
			'longitude' : 17
		};
		latlong.MA = {
			'latitude' : 32,
			'longitude' : -5
		};
		latlong.MC = {
			'latitude' : 43.7333,
			'longitude' : 7.4
		};
		latlong.MD = {
			'latitude' : 47,
			'longitude' : 29
		};
		latlong.ME = {
			'latitude' : 42.5,
			'longitude' : 19.4
		};
		latlong.MG = {
			'latitude' : -20,
			'longitude' : 47
		};
		latlong.MH = {
			'latitude' : 9,
			'longitude' : 168
		};
		latlong.MK = {
			'latitude' : 41.8333,
			'longitude' : 22
		};
		latlong.ML = {
			'latitude' : 17,
			'longitude' : -4
		};
		latlong.MM = {
			'latitude' : 22,
			'longitude' : 98
		};
		latlong.MN = {
			'latitude' : 46,
			'longitude' : 105
		};
		latlong.MO = {
			'latitude' : 22.1667,
			'longitude' : 113.55
		};
		latlong.MP = {
			'latitude' : 15.2,
			'longitude' : 145.75
		};
		latlong.MQ = {
			'latitude' : 14.6667,
			'longitude' : -61
		};
		latlong.MR = {
			'latitude' : 20,
			'longitude' : -12
		};
		latlong.MS = {
			'latitude' : 16.75,
			'longitude' : -62.2
		};
		latlong.MT = {
			'latitude' : 35.8333,
			'longitude' : 14.5833
		};
		latlong.MU = {
			'latitude' : -20.2833,
			'longitude' : 57.55
		};
		latlong.MV = {
			'latitude' : 3.25,
			'longitude' : 73
		};
		latlong.MW = {
			'latitude' : -13.5,
			'longitude' : 34
		};
		latlong.MX = {
			'latitude' : 23,
			'longitude' : -102
		};
		latlong.MY = {
			'latitude' : 2.5,
			'longitude' : 112.5
		};
		latlong.MZ = {
			'latitude' : -18.25,
			'longitude' : 35
		};
		latlong.NA = {
			'latitude' : -22,
			'longitude' : 17
		};
		latlong.NC = {
			'latitude' : -21.5,
			'longitude' : 165.5
		};
		latlong.NE = {
			'latitude' : 16,
			'longitude' : 8
		};
		latlong.NF = {
			'latitude' : -29.0333,
			'longitude' : 167.95
		};
		latlong.NG = {
			'latitude' : 10,
			'longitude' : 8
		};
		latlong.NI = {
			'latitude' : 13,
			'longitude' : -85
		};
		latlong.NL = {
			'latitude' : 52.5,
			'longitude' : 5.75
		};
		latlong.NO = {
			'latitude' : 62,
			'longitude' : 10
		};
		latlong.NP = {
			'latitude' : 28,
			'longitude' : 84
		};
		latlong.NR = {
			'latitude' : -0.5333,
			'longitude' : 166.9167
		};
		latlong.NU = {
			'latitude' : -19.0333,
			'longitude' : -169.8667
		};
		latlong.NZ = {
			'latitude' : -41,
			'longitude' : 174
		};
		latlong.OM = {
			'latitude' : 21,
			'longitude' : 57
		};
		latlong.PA = {
			'latitude' : 9,
			'longitude' : -80
		};
		latlong.PE = {
			'latitude' : -10,
			'longitude' : -76
		};
		latlong.PF = {
			'latitude' : -15,
			'longitude' : -140
		};
		latlong.PG = {
			'latitude' : -6,
			'longitude' : 147
		};
		latlong.PH = {
			'latitude' : 13,
			'longitude' : 122
		};
		latlong.PK = {
			'latitude' : 30,
			'longitude' : 70
		};
		latlong.PL = {
			'latitude' : 52,
			'longitude' : 20
		};
		latlong.PM = {
			'latitude' : 46.8333,
			'longitude' : -56.3333
		};
		latlong.PR = {
			'latitude' : 18.25,
			'longitude' : -66.5
		};
		latlong.PS = {
			'latitude' : 32,
			'longitude' : 35.25
		};
		latlong.PT = {
			'latitude' : 39.5,
			'longitude' : -8
		};
		latlong.PW = {
			'latitude' : 7.5,
			'longitude' : 134.5
		};
		latlong.PY = {
			'latitude' : -23,
			'longitude' : -58
		};
		latlong.QA = {
			'latitude' : 25.5,
			'longitude' : 51.25
		};
		latlong.RE = {
			'latitude' : -21.1,
			'longitude' : 55.6
		};
		latlong.RO = {
			'latitude' : 46,
			'longitude' : 25
		};
		latlong.RS = {
			'latitude' : 44,
			'longitude' : 21
		};
		latlong.RU = {
			'latitude' : 60,
			'longitude' : 100
		};
		latlong.RW = {
			'latitude' : -2,
			'longitude' : 30
		};
		latlong.SA = {
			'latitude' : 25,
			'longitude' : 45
		};
		latlong.SB = {
			'latitude' : -8,
			'longitude' : 159
		};
		latlong.SC = {
			'latitude' : -4.5833,
			'longitude' : 55.6667
		};
		latlong.SD = {
			'latitude' : 15,
			'longitude' : 30
		};
		latlong.SE = {
			'latitude' : 62,
			'longitude' : 15
		};
		latlong.SG = {
			'latitude' : 1.3667,
			'longitude' : 103.8
		};
		latlong.SH = {
			'latitude' : -15.9333,
			'longitude' : -5.7
		};
		latlong.SI = {
			'latitude' : 46,
			'longitude' : 15
		};
		latlong.SJ = {
			'latitude' : 78,
			'longitude' : 20
		};
		latlong.SK = {
			'latitude' : 48.6667,
			'longitude' : 19.5
		};
		latlong.SL = {
			'latitude' : 8.5,
			'longitude' : -11.5
		};
		latlong.SM = {
			'latitude' : 43.7667,
			'longitude' : 12.4167
		};
		latlong.SN = {
			'latitude' : 14,
			'longitude' : -14
		};
		latlong.SO = {
			'latitude' : 10,
			'longitude' : 49
		};
		latlong.SR = {
			'latitude' : 4,
			'longitude' : -56
		};
		latlong.ST = {
			'latitude' : 1,
			'longitude' : 7
		};
		latlong.SV = {
			'latitude' : 13.8333,
			'longitude' : -88.9167
		};
		latlong.SY = {
			'latitude' : 35,
			'longitude' : 38
		};
		latlong.SZ = {
			'latitude' : -26.5,
			'longitude' : 31.5
		};
		latlong.TC = {
			'latitude' : 21.75,
			'longitude' : -71.5833
		};
		latlong.TD = {
			'latitude' : 15,
			'longitude' : 19
		};
		latlong.TF = {
			'latitude' : -43,
			'longitude' : 67
		};
		latlong.TG = {
			'latitude' : 8,
			'longitude' : 1.1667
		};
		latlong.TH = {
			'latitude' : 15,
			'longitude' : 100
		};
		latlong.TJ = {
			'latitude' : 39,
			'longitude' : 71
		};
		latlong.TK = {
			'latitude' : -9,
			'longitude' : -172
		};
		latlong.TM = {
			'latitude' : 40,
			'longitude' : 60
		};
		latlong.TN = {
			'latitude' : 34,
			'longitude' : 9
		};
		latlong.TO = {
			'latitude' : -20,
			'longitude' : -175
		};
		latlong.TR = {
			'latitude' : 39,
			'longitude' : 35
		};
		latlong.TT = {
			'latitude' : 11,
			'longitude' : -61
		};
		latlong.TV = {
			'latitude' : -8,
			'longitude' : 178
		};
		latlong.TW = {
			'latitude' : 23.5,
			'longitude' : 121
		};
		latlong.TZ = {
			'latitude' : -6,
			'longitude' : 35
		};
		latlong.UA = {
			'latitude' : 49,
			'longitude' : 32
		};
		latlong.UG = {
			'latitude' : 1,
			'longitude' : 32
		};
		latlong.UM = {
			'latitude' : 19.2833,
			'longitude' : 166.6
		};
		latlong.US = {
			'latitude' : 38,
			'longitude' : -97
		};
		latlong.UY = {
			'latitude' : -33,
			'longitude' : -56
		};
		latlong.UZ = {
			'latitude' : 41,
			'longitude' : 64
		};
		latlong.VA = {
			'latitude' : 41.9,
			'longitude' : 12.45
		};
		latlong.VC = {
			'latitude' : 13.25,
			'longitude' : -61.2
		};
		latlong.VE = {
			'latitude' : 8,
			'longitude' : -66
		};
		latlong.VG = {
			'latitude' : 18.5,
			'longitude' : -64.5
		};
		latlong.VI = {
			'latitude' : 18.3333,
			'longitude' : -64.8333
		};
		latlong.VN = {
			'latitude' : 16,
			'longitude' : 106
		};
		latlong.VU = {
			'latitude' : -16,
			'longitude' : 167
		};
		latlong.WF = {
			'latitude' : -13.3,
			'longitude' : -176.2
		};
		latlong.WS = {
			'latitude' : -13.5833,
			'longitude' : -172.3333
		};
		latlong.YE = {
			'latitude' : 15,
			'longitude' : 48
		};
		latlong.YT = {
			'latitude' : -12.8333,
			'longitude' : 45.1667
		};
		latlong.ZA = {
			'latitude' : -29,
			'longitude' : 24
		};
		latlong.ZM = {
			'latitude' : -15,
			'longitude' : 30
		};
		latlong.ZW = {
			'latitude' : -20,
			'longitude' : 30
		};
		latlong.IM = {
			'latitude' : 54.1,
			'longitude' : -4.3
		};
		latlong.AX = {
				'latitude' : 60.05,
				'longitude' : 19.56
			};
		
		$(document)
				.ready(
						function() {
							lhs();
							jQuery.ajax({
								type : 'GET',
								//contentType: 'application/json',  
								url : "getTopData.html",
								dataType : 'json',

								success : function(data) {
									urlData = data.webphishList;

								}
							});

							$("#aFloatTools_Show").click(
									function() {
										$('#divFloatToolsView').animate({
											width : 'show',
											opacity : 'show'
										}, 'normal', function() {
											$('#divFloatToolsView').show();
											//lhs();
											x = 0;
										});
										$('#aFloatTools_Show').attr('style',
												'display:none');
										$('#aFloatTools_Hide').attr('style',
												'display:block');
									});

							$("#aFloatTools_Hide")
									.click(
											function() {
												$('#divFloatToolsView')
														.animate(
																{
																	width : 'hide',
																	opacity : 'hide'
																},
																'normal',
																function() {

																	var tbody = document
																			.getElementById("table-id");
																	tbody.innerHTML = "";
																	$(
																			'#divFloatToolsView')
																			.hide();
																});
												$('#aFloatTools_Show').attr(
														'style',
														'display:block');
												$('#aFloatTools_Hide')
														.attr('style',
																'display:none');
											});

							jQuery
									.ajax({
										type : 'GET',
										//contentType: 'application/json',  
										url : "countryURL.html",
										dataType : 'json',

										success : function(data) {

											var mapData = data.mapdata;
											var count = data.count;
											var validcount = data.validCount;
											mapData.sort(function(a, b) {
												return b.value - a.value;
											})
											var xData = mapData.slice(0, 5);
											var max = -Infinity;
											var min = Infinity;
											mapData.forEach(function(itemOpt) {
												if (itemOpt.value > max) {
													max = itemOpt.value;
												}
												if (itemOpt.value < min) {
													min = itemOpt.value;
												}
											});
											var categoryData = [];
											var barData = [];
											for (var i = 0; i < 5; i++) {
												categoryData
														.push(mapData[i].name);

												barData.push(mapData[i].value);
												//sum+=convertedData[0][i].key;
											}
											option = {
												backgroundColor : 'rgba(128, 128, 128, 0)',
												title : [
														{
															text : '????????????URL?????????',
															subtext : '?????????????????????'
																	+ count
																	+ '???     '+'????????????'+validcount+'???',
															left : 'center',
															top : '1%',
															textStyle : {
																color : '#fff'
															},
															subtextStyle : {
																color : '#f3e925',
																fontWeight:'bolder',
																fontSize:15
																
															},
														},
														{
															id : 'statistic',
															text : '????????????URL??????Top5',
															left : '10%',
															top : '65%',
															width : 100,
															textStyle : {
																color : '#fff',
																fontSize : 16
															}
														} ],
												tooltip : {
													trigger : 'item',
													formatter : function(params) {//???????????????

														if (params.value > 0) {
															return params.name
																	+ " :"
																	+ params.value;

														} else {
															var value = (params.value + '')
																	.split(',');
															return params.seriesName
																	+ " "
																	+ params.name
																	+ ' : '
																	+ value[2];

														}

													}
												},
												/*   visualMap: {
												       show: false,
												       min: 0,
												       max: max,
												       inRange: {
												           symbolSize: [6, 30]
												       }
												   },*/
												geo : {
													name : '????????????URL?????????',
													type : 'map',
													map : 'world',
													roam : false,
													label : {
														emphasis : {
															show : false
														}
													},
													itemStyle : {
														normal : {

															areaColor : 'rgba(128, 128, 128, 0)',
															borderColor : '#00FFFF'
														},
														emphasis : {
															areaColor : '#2a333d'
														}
													}
												},
												grid : {
													left : '7%',
													top : '70%',
													bottom : 2,
													//height: '20%'
													height : '25%',
													width : '20%'
												},
												xAxis : {
													show : false,
													type : 'value',
													scale : true,
													position : 'top',
													boundaryGap : false,
													splitLine : {
														show : false
													},
													axisLine : {
														show : false
													},
													axisTick : {
														show : false
													},
													axisLabel : {
														margin : 2,
														textStyle : {
															color : '#aaa'
														}
													},
												},
												yAxis : {
													type : 'category',
													//  name: 'TOP 20',
													nameGap : 16,
													axisLine : {
														show : true,
														lineStyle : {
															color : '#ddd'
														}
													},
													axisTick : {
														show : false,
														lineStyle : {
															color : '#ddd'
														}
													},
													axisLabel : {
														interval : 0,
														textStyle : {
															color : '#ddd'
														}
													},
													data : categoryData
															.reverse()
												},
												series : [
														{
															name : '',
															type : 'scatter',
															coordinateSystem : 'geo',

															data : mapData
																	.map(function(
																			itemOpt) {
																		//console.log(itemOpt.code);
																		return {
																			name : itemOpt.name,
																			//xvalue:itemOpt.value,
																			value : [
																					latlong[itemOpt.code].longitude,
																					latlong[itemOpt.code].latitude,
																					itemOpt.value ],

																			label : {
																				emphasis : {
																					position : 'right',
																					show : true
																				}
																			},
																			itemStyle : {
																				normal : {
																					color : '#ddb926'
																				}
																			}
																		};
																	}),
															symbolSize : function(
																	val) {
																var max = 1500;
																max = Math.min(
																		max,
																		val[2]);
																return max / 40;
															},
														},
														{
															name : '',
															type : 'effectScatter',
															coordinateSystem : 'geo',
															data : xData
																	.map(function(
																			itemOpt) {
																		return {
																			name : itemOpt.name,
																			value : [
																					latlong[itemOpt.code].longitude,
																					latlong[itemOpt.code].latitude,
																					itemOpt.value ],
																			label : {
																				emphasis : {
																					position : 'right',
																					show : true
																				}
																			},
																			itemStyle : {
																				normal : {
																					color : '#f4e925',
																					shadowBlur : 10,
																					shadowColor : '#333'
																				}
																			}
																		};
																	}),
															symbolSize : function(
																	val) {
																var max = 1500;
																max = Math.min(
																		max,
																		val[2]);
																return max / 40;
															},
														},

														{
															id : 'bar',
															zlevel : 2,
															type : 'bar',
															symbol : 'none',

															itemStyle : {
																normal : {
																	//formatter: '{b} : {c}',
																	color : function(
																			params) {
																		// build a color map as your need.
																		var colorList = [
																				'#00FFFF',
																				'#00ff00',
																				'#FCCE10',
																				'#FF6100',
																				'#C1232B' ];
																		return colorList[params.dataIndex]
																	},
																	label : {
																		show : true,
																		position : 'right',
																		formatter : '{c}'
																	}

																}
															},

															data : xData
																	.reverse(),
														} ]
											};

											myChart.setOption(option);
											window.onresize = function () { 
												resizemainMapContainer();
												resizeWorldMapContainer();
												myChart.resize({width:window.innerWidth, height:window.innerHeight}); };
											//window.onresize = myChart.resize;
										}
									});
						});
	</script>
</body>
</html>