package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.JerseyJsonUtil;


/**
 * 
 * @author ：刘汉生
 * @date： 2017-3-23下午2:47:25 修 改 人 ：刘汉生  修改日期：
 * @description： 统计分析 版 本： 1.0
 */
@Controller
public class MaliciousUrlAllDataController {

	/**
	 * 
	 * 功能描述： 请求到按国家类别分类获取有效的恶意url个数，并进行翻译转发为json
	 * 
	 * 
	 */
	private static ResourceBundle bundle=ResourceBundle.getBundle("url");
	private static String SOUTH_SERVER_WEB_ROOT=bundle.getString("SOUTH_SERVER_WEB_ROOT");
	     
	
	@RequestMapping(value="source/page/resource/html/countryURL.html")
	@ResponseBody
	public void worldMapURL(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		HashMap<String, String>map=new HashMap<String, String>();
		map.put("AF","阿富汗");
		map.put("AX","奥兰群岛");
		map.put("AL","阿尔巴尼亚");
		map.put("DZ","阿尔及利亚");
		map.put("AS","美属萨摩亚");
		map.put("AD","安道尔");
		map.put("AO","安哥拉");
		map.put("AI","安圭拉");
		map.put("AQ","南极洲");
		map.put("AG","安提瓜和巴布达");
		map.put("AR","阿根廷");
		map.put("AM","亚美尼亚");
		map.put("AW","阿鲁巴");
		map.put("AU","澳大利亚");
		map.put("AT","奥地利");
		map.put("AZ","阿塞拜疆");
		map.put("BS","巴哈马");
		map.put("BH","巴林");
		map.put("BD","孟加拉国");
		map.put("BB","巴巴多斯");
		map.put("BY","白俄罗斯");
		map.put("BE","比利时");
		map.put("BZ","伯利兹");
		map.put("BJ","贝宁");
		map.put("BM","百慕大");
		map.put("BT","不丹");
		map.put("BO","玻利维亚");
		map.put("BA","波黑");
		map.put("BW","博茨瓦纳");
		map.put("BV","布维岛");
		map.put("BR","巴西");
		map.put("IO","英属印度洋领地");
		map.put("BN","文莱");
		map.put("BG","保加利亚");
		map.put("BF","布基纳法索");
		map.put("BI","布隆迪");
		map.put("KH","柬埔寨");
		map.put("CM","喀麦隆");
		map.put("CA","加拿大");
		map.put("CV","佛得角");
		map.put("KY","开曼群岛");
		map.put("CF","中非");
		map.put("TD","乍得");
		map.put("CL","智利");
		map.put("CN","中国");
		map.put("CX","圣诞岛");
		map.put("CC","科科斯（基林）群岛");
		map.put("CO","哥伦比亚");
		map.put("KM","科摩罗");
		map.put("CG","刚果（布）");
		map.put("CD","刚果（金）");
		map.put("CK","库克群岛");
		map.put("CR","哥斯达黎加");
		map.put("CI","科特迪瓦");
		map.put("HR","克罗地亚");
		map.put("CU","古巴");
		map.put("CY","塞浦路斯");
		map.put("CZ","捷克");
		map.put("DK","丹麦");
		map.put("DJ","吉布提");
		map.put("DM","多米尼克");
		map.put("DO","多米尼加");
		map.put("EC","厄瓜多尔");
		map.put("EG","埃及");
		map.put("SV","萨尔瓦多");
		map.put("GQ","赤道几内亚");
		map.put("ER","厄立特里亚");
		map.put("EE","爱沙尼亚");
		map.put("ET","埃塞俄比亚");
		map.put("FK","福克兰群岛（马尔维纳斯）");
		map.put("FO","法罗群岛");
		map.put("FJ","斐济");
		map.put("FI","芬兰");
		map.put("FR","法国");
		map.put("GF","法属圭亚那");
		map.put("PF","法属波利尼西亚");
		map.put("TF","法属南部领地");
		map.put("GA","加蓬");
		map.put("GM","冈比亚");
		map.put("GE","格鲁吉亚");
		map.put("DE","德国");
		map.put("GH","加纳");
		map.put("GI","直布罗陀");
		map.put("GR","希腊");
		map.put("GL","格陵兰");
		map.put("GD","格林纳达");
		map.put("GP","瓜德罗普");
		map.put("GU","关岛");
		map.put("GT","危地马拉");
		map.put("GG","格恩西岛");
		map.put("GN","几内亚");
		map.put("GW","几内亚比绍");
		map.put("GY","圭亚那");
		map.put("HT","海地");
		map.put("HM","赫德岛和麦克唐纳岛");
		map.put("VA","梵蒂冈");
		map.put("HN","洪都拉斯");
		map.put("HK","香港");
		map.put("HU","匈牙利");
		map.put("IS","冰岛");
		map.put("IN","印度");
		map.put("ID","印度尼西亚");
		map.put("IR","伊朗");
		map.put("IQ","伊拉克");
		map.put("IE","爱尔兰");
		map.put("IM","英国属地曼岛");
		map.put("IL","以色列");
		map.put("IT","意大利");
		map.put("JM","牙买加");
		map.put("JP","日本");
		map.put("JE","泽西岛");
		map.put("JO","约旦");
		map.put("KZ","哈萨克斯坦");
		map.put("KE","肯尼亚");
		map.put("KI","基里巴斯");
		map.put("KP","朝鲜");
		map.put("KR","韩国");
		map.put("KW","科威特");
		map.put("KG","吉尔吉斯斯坦");
		map.put("LA","老挝");
		map.put("LV","拉脱维亚");
		map.put("LB","黎巴嫩");
		map.put("LS","莱索托");
		map.put("LR","利比里亚");
		map.put("LY","利比亚");
		map.put("LI","列支敦士登");
		map.put("LT","立陶宛");
		map.put("LU","卢森堡");
		map.put("MO","澳门");
		map.put("MK","前南马其顿");
		map.put("MG","马达加斯加");
		map.put("MW","马拉维");
		map.put("MY","马来西亚");
		map.put("MV","马尔代夫");
		map.put("ML","马里");
		map.put("MT","马耳他");
		map.put("MH","马绍尔群岛");
		map.put("MQ","马提尼克");
		map.put("MR","毛利塔尼亚");
		map.put("MU","毛里求斯");
		map.put("YT","马约特");
		map.put("MX","墨西哥");
		map.put("FM","密克罗尼西亚联邦");
		map.put("MD","摩尔多瓦");
		map.put("MC","摩纳哥");
		map.put("MN","蒙古");
		map.put("ME","黑山");
		map.put("MS","蒙特塞拉特");
		map.put("MA","摩洛哥");
		map.put("MZ","莫桑比克");
		map.put("MM","缅甸");
		map.put("NA","纳米比亚");
		map.put("NR","瑙鲁");
		map.put("NP","尼泊尔");
		map.put("NL","荷兰");
		map.put("AN","荷属安的列斯");
		map.put("NC","新喀里多尼亚");
		map.put("NZ","新西兰");
		map.put("NI","尼加拉瓜");
		map.put("NE","尼日尔");
		map.put("NG","尼日利亚");
		map.put("NU","纽埃");
		map.put("NF","诺福克岛");
		map.put("MP","北马里亚纳");
		map.put("NO","挪威");
		map.put("OM","阿曼");
		map.put("PK","巴基斯坦");
		map.put("PW","帕劳");
		map.put("PS","巴勒斯坦");
		map.put("PA","巴拿马");
		map.put("PG","巴布亚新几内亚");
		map.put("PY","巴拉圭");
		map.put("PE","秘鲁");
		map.put("PH","菲律宾");
		map.put("PN","皮特凯恩");
		map.put("PL","波兰");
		map.put("PT","葡萄牙");
		map.put("PR","波多黎各");
		map.put("QA","卡塔尔");
		map.put("RE","留尼汪");
		map.put("RO","罗马尼亚");
		map.put("RU","俄罗斯联邦");
		map.put("RW","卢旺达");
		map.put("SH","圣赫勒拿");
		map.put("KN","圣基茨和尼维斯");
		map.put("LC","圣卢西亚");
		map.put("PM","圣皮埃尔和密克隆");
		map.put("VC","圣文森特和格林纳丁斯");
		map.put("WS","萨摩亚");
		map.put("SM","圣马力诺");
		map.put("ST","圣多美和普林西比");
		map.put("SA","沙特阿拉伯");
		map.put("SN","塞内加尔");
		map.put("RS","塞尔维亚");
		map.put("SC","塞舌尔");
		map.put("SL","塞拉利昂");
		map.put("SG","新加坡");
		map.put("SK","斯洛伐克");
		map.put("SI","斯洛文尼亚");
		map.put("SB","所罗门群岛");
		map.put("SO","索马里");
		map.put("ZA","南非");
		map.put("GS","南乔治亚岛和南桑德韦奇岛");
		map.put("ES","西班牙");
		map.put("LK","斯里兰卡");
		map.put("SD","苏丹");
		map.put("SR","苏里南");
		map.put("SJ","斯瓦尔巴岛和扬马延岛");
		map.put("SZ","斯威士兰");
		map.put("SE","瑞典");
		map.put("CH","瑞士");
		map.put("SY","叙利亚");
		map.put("TW","台湾");
		map.put("TJ","塔吉克斯坦");
		map.put("TZ","坦桑尼亚");
		map.put("TH","泰国");
		map.put("TL","东帝汶");
		map.put("TG","多哥");
		map.put("TK","托克劳");
		map.put("TO","汤加");
		map.put("TT","特立尼达和多巴哥");
		map.put("TN","突尼斯");
		map.put("TR","土耳其");
		map.put("TM","土库曼斯坦");
		map.put("TC","特克斯和凯科斯群岛");
		map.put("TV","图瓦卢");
		map.put("UG","乌干达");
		map.put("UA","乌克兰");
		map.put("AE","阿联酋");
		map.put("GB","英国");
		map.put("US","美国");
		map.put("UM","美国本土外小岛屿");
		map.put("UY","乌拉圭");
		map.put("UZ","乌兹别克斯坦");
		map.put("VU","瓦努阿图");
		map.put("VE","委内瑞拉");
		map.put("VN","越南");
		map.put("VG","英属维尔京群岛");
		map.put("VI","美属维尔京群岛");
		map.put("WF","瓦利斯和富图纳");
		map.put("EH","西撒哈拉");
		map.put("YE","也门");
		map.put("ZM","赞比亚");
		map.put("ZW","津巴布韦");
		
		String url=bundle.getString("Country_All_Url");
		String countUrl=bundle.getString("Malurl_All_COUNT");
		JerseyJsonUtil jerseyJsonUtil=new JerseyJsonUtil();
		String countStr=jerseyJsonUtil.getMethod(SOUTH_SERVER_WEB_ROOT+countUrl);
		JSONObject jObject=JSONObject.fromObject(countStr);
		String count=jObject.get("count").toString();
		String str=jerseyJsonUtil.getMethod(SOUTH_SERVER_WEB_ROOT+url);
		JSONObject jb=JSONObject.fromObject(str);
		JSONArray countList=(JSONArray) jb.get("countList");
		Iterator<Object>it=countList.iterator();
		JSONArray jsonArray=new JSONArray();
		while(it.hasNext()){
			JSONObject ob=(JSONObject) it.next();
			if(ob.get("countryCode")!=null&&ob.get("countryCode").toString().length()>0){
				//System.out.println(ob.getString("countryCode"));
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("code",ob.getString("countryCode"));
				jsonObject.put("name",map.get(ob.getString("countryCode")));
				jsonObject.put("value",ob.getString("count"));
				jsonObject.put("color","#eea638");
				jsonArray.add(jsonObject);
				//System.out.println(map.get(ob.getString("countryCode"))+":"+ob.getString("count"));
			}
		}
		//System.out.println(jsonArray);
		JSONObject jsonObject2=new JSONObject();
		jsonObject2.put("mapData", jsonArray);
		jsonObject2.put("count", count);
		try {
			//CommonUtil.writeJsonToJsp(response, jsonArray);
			CommonUtil.writeToJsp(response, jsonObject2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	/**
	 * 
	 * 功能描述： 请求到按国家类别分类获取有效的恶意url个数，并进行翻译转发为json
	 * 
	 * 
	 */
	
	@RequestMapping(value="source/page/resource/html/chinaURL.html")
	@ResponseBody
	public void chinaMapUI(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		HashSet<String>provinceSet=new HashSet<String>();
		provinceSet.add("北京");
		provinceSet.add("天津");
		provinceSet.add("河北");
		provinceSet.add("山西");
		provinceSet.add("内蒙古");
		provinceSet.add("辽宁");
		provinceSet.add("吉林");
		provinceSet.add("黑龙江");
		provinceSet.add("上海");
		provinceSet.add("江苏");
		provinceSet.add("浙江");
		provinceSet.add("安徽");
		provinceSet.add("福建");
		provinceSet.add("江西");
		provinceSet.add("山东");
		provinceSet.add("河南");
		provinceSet.add("湖北");
		provinceSet.add("湖南");
		provinceSet.add("广西");
		provinceSet.add("海南");
		provinceSet.add("重庆");
		provinceSet.add("四川");
		provinceSet.add("贵州");
		provinceSet.add("云南");
		provinceSet.add("西藏");
		provinceSet.add("陕西");
		provinceSet.add("甘肃");
		provinceSet.add("青海");
		provinceSet.add("宁夏");
		provinceSet.add("新疆");
		provinceSet.add("香港");
		provinceSet.add("澳门");
		provinceSet.add("台湾");
		String url=bundle.getString("Province_All_Url");
		String countUrl=bundle.getString("China_Malurl_All_Count");
		JerseyJsonUtil jerseyJsonUtil=new JerseyJsonUtil();
		String countStr=jerseyJsonUtil.getMethod(SOUTH_SERVER_WEB_ROOT+countUrl);
		String count=JSONObject.fromObject(countStr).get("count").toString();
		String str=jerseyJsonUtil.getMethod(SOUTH_SERVER_WEB_ROOT+url);
		JSONObject jb=JSONObject.fromObject(str);
		JSONArray countList=(JSONArray) jb.get("countList");
		Iterator<Object>it=countList.iterator();
		JSONArray jsonArray=new JSONArray();
		HashSet<String>set=new HashSet<String>();
		while(it.hasNext()){
			JSONObject ob=(JSONObject) it.next();
			JSONObject jsonObject=new JSONObject();
			
			if(ob.getString("countryCode").equals("TW")&&set.add("台湾")){
				provinceSet.remove("台湾");
				jsonObject.put("name","台湾");
				jsonObject.put("value",ob.getString("count"));
				//System.out.println("进入台湾了");
				jsonArray.add(jsonObject);
			}else if(ob.getString("countryCode").equals("HK")&&set.add("香港")){
				provinceSet.remove("香港");
				jsonObject.put("name","香港");
				jsonObject.put("value",ob.getString("count"));
				jsonArray.add(jsonObject);
			}else if(!ob.getString("province").endsWith("省")&&ob.getString("province").length()<5&&ob.getString("province").length()>1){
				provinceSet.remove(ob.getString("province"));
				jsonObject.put("name",ob.getString("province"));
				jsonObject.put("value",ob.getString("count"));
				jsonArray.add(jsonObject);
			}else{
				//System.out.println(ob.getString("province"));
				}
		}
		for(String provinceStr:provinceSet){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("name",provinceStr);
			jsonObject.put("value","0");
			jsonArray.add(jsonObject);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("mapData", jsonArray);
		jsonObject.put("count", count);
		//System.out.println(jsonArray.toString());
		try {
			CommonUtil.writeToJsp(response, jsonObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	/**
	 * 
	 * 功能描述：获取按月份的恶意url数据信息，并进行翻译转发为json
	 * 
	 * 
	 */
	
	@RequestMapping(value="source/page/resource/html/monthUrl.html")
	@ResponseBody
	public void monthURL(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ResourceBundle bundle=ResourceBundle.getBundle("url");
		String url=bundle.getString("monthUrl");
		JerseyJsonUtil jerseyJsonUtil=new JerseyJsonUtil();
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("month","7");
		String str=jerseyJsonUtil.postMethod(url, jsonObject2.toString());
		JSONObject jb=JSONObject.fromObject(str);
		
		JSONArray countList=(JSONArray) jb.get("countList");
		Iterator<Object>it=countList.iterator();
		//JSONArray jsonO=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray1=new JSONArray();
		JSONArray jsonArray2=new JSONArray();
		JSONArray jsonArray3=new JSONArray();
		JSONArray jsonArray4=new JSONArray();
		int index=0;
		int sumIndex=0;
		boolean flag=false;
		while(it.hasNext()){
			JSONObject ob=(JSONObject) it.next();
			if(ob.getString("isvalid").equals("1")){
				jsonArray1.add(ob.getString("month"));
				jsonArray2.add(ob.getString("count"));
				index=Integer.valueOf(ob.getString("count"));
			}else{
				jsonArray3.add(ob.getString("count"));
				int x=sumIndex-Integer.valueOf(ob.getString("count"))-index;
				if(x<0){
					x=0;
				}else jsonArray4.add(x);
				sumIndex=Integer.valueOf(ob.getString("count"))+index;
			}
			
		}
		jsonObject.put("month", jsonArray1);
		jsonObject.put("valid", jsonArray2);
		jsonObject.put("unvalid", jsonArray3);
		jsonObject.put("increase", jsonArray4);
		try {
			CommonUtil.writeToJsp(response, jsonObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	/**
	 * 
	 * 功能描述：获取仿冒行业top5
	 * 
	 * 
	 */
	
	@RequestMapping(value="source/page/resource/html/getcountbyfieldtop5.html")
	@ResponseBody
	public void getcountbyfieldtop5(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ResourceBundle bundle=ResourceBundle.getBundle("url");
		String url=bundle.getString("getcountbyfieldtop5");
		JerseyJsonUtil jerseyJsonUtil=new JerseyJsonUtil();
		String str=jerseyJsonUtil.getMethod(url);
		JSONObject jb=JSONObject.fromObject(str);
		JSONArray countList=(JSONArray) jb.get("countList");
		Iterator<Object>it=countList.iterator();
		//JSONArray jsonO=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray1=new JSONArray();
		JSONArray jsonArray2=new JSONArray();
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("Email Provider","电子邮件");
		map.put("e-Commerce","电子商务");
		map.put("Financial","金融");
		map.put("Online Services","在线服务");
		map.put("Payment Services","支付服务");
		map.put("Telecommunications","通讯");
		while(it.hasNext()){
			JSONObject ob=(JSONObject) it.next();
			jsonArray1.add(map.get(ob.getString("field")));
			jsonArray2.add(ob.getString("count"));
			
		}
		jsonObject.put("filed", jsonArray1);
		jsonObject.put("count", jsonArray2);
		try {
			CommonUtil.writeToJsp(response, jsonObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	/**
	 * 
	 * 功能描述：获取仿冒对象top10
	 * 
	 * 
	 */
	
	@RequestMapping(value="source/page/resource/html/getcountbytargettop10.html")
	@ResponseBody
	public void getcountbytargettop10(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ResourceBundle bundle=ResourceBundle.getBundle("url");
		String url=bundle.getString("getcountbytargettop10");
		JerseyJsonUtil jerseyJsonUtil=new JerseyJsonUtil();
		String str=jerseyJsonUtil.getMethod(url);
		JSONObject jb=JSONObject.fromObject(str);
		JSONArray countList=(JSONArray) jb.get("countList");
		Iterator<Object>it=countList.iterator();
		//JSONArray jsonO=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray1=new JSONArray();
		JSONArray jsonArray2=new JSONArray();
		while(it.hasNext()){
			JSONObject ob=(JSONObject) it.next();
			jsonArray1.add(ob.getString("target"));
			jsonArray2.add(ob.getString("count"));
			
		}
		jsonObject.put("target", jsonArray1);
		jsonObject.put("count", jsonArray2);
		try {
			CommonUtil.writeToJsp(response, jsonObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
