package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.AlarmSum;
import com.cn.ctbri.entity.AttackSource;
import com.cn.ctbri.entity.UserDistribution;
import com.cn.ctbri.service.IAttackSourceService;
import com.cn.ctbri.service.IDataAnalysisService;
import com.cn.ctbri.service.IDistrictDataService;
import com.cn.ctbri.util.AddressUtils;

@Controller
public class DataAnalysisController {
	@Autowired
	IDataAnalysisService dataAnalysisService;
	@Autowired
    IDistrictDataService districtDataService;
	@Autowired
	IAttackSourceService attackSourceService;
	
	/**
	 * 功能描述： 
	 * 参数描述： Model m
	 *		 @time 2016-8-3
	 */
	@RequestMapping(value="/highSiteMapUI.html")
	public String highSiteMap(Model m){
		return "/source/page/anquanbang/highSiteMap";
	}
	
	@RequestMapping(value="/MapUI.html")
	public String Map(Model m){
		return "/source/page/personalCenter/anquan_state";
	}
	
	/**
	 * 功能描述： 高危网站地图（所有监测数据与WAF数据结合）：
	 * 			以省为单位，累加所有漏洞、木马、关键字、篡改、可用性及WAF告警数据总和超过100的网站数
	 * 参数描述： Model m
	 *		 @time 2016-8-3
	 */
	@RequestMapping(value="/highSiteMap.html", method=RequestMethod.POST)
	@ResponseBody
	public String highRiskSiteMap(HttpServletResponse response,HttpServletRequest request) throws IOException {
        
		Map<String, Integer> SiteCountMap = new HashMap<String, Integer>();
		
		//查询 漏洞、木马、关键字、篡改、可用性告警数据总和
		List<AlarmSum> alarmSumList = dataAnalysisService.findAlarmSum();
		
		//查询 WAF告警数量
		String wafcreate = WafAPIWorker.getWafLogWebSecDstIpList();
		JSONObject jsonObject = JSONObject.fromObject(wafcreate);			
		JSONArray jsonArray = jsonObject.getJSONArray("WafLogWebSecDstIpList");
		
		//根据网站，累加所有漏洞、木马、关键字、篡改、可用性及WAF告警数据总和
		//----------------------累加告警数据总和  start ----------------------
		int wafWebSiteSize = jsonArray.size();
		for (int i = 0;i < wafWebSiteSize; i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			String dstIp = obj.getString("dstIp");
			int count = obj.getInt("count");
			//DB:根据waf防护目标的ip查询网站名、所在省份
			Map<String, String> assetInfo = dataAnalysisService.findAssetInfoByIp(dstIp);
			if (assetInfo != null) {
				String disName = assetInfo.get("districtName");
				String addr = assetInfo.get("addr");
				
				//累计标志 true：已累计 false：未累计
				boolean totalFlag = false;
				for(AlarmSum alarmSum: alarmSumList) {
					if (alarmSum.getUrl().equals(disName) && 
							alarmSum.getDistrictName().equals(disName)) {
						alarmSum.setAlarmCount(alarmSum.getAlarmCount() + count);
						totalFlag = true;
						break;
					}
				}
				
				if(!totalFlag){
					AlarmSum alarmSum = new AlarmSum();
					alarmSum.setDistrictName(disName);
					alarmSum.setUrl(addr);
					alarmSum.setAlarmCount(count);
					alarmSumList.add(alarmSum);
				}
			}
			
		}
		//----------------------累加告警数据总和  end ----------------------
		
		//判断告警数据总和是否超过100,超过100则该省分的网站数量加1
		for(AlarmSum alarmSum: alarmSumList) {
			if (alarmSum.getAlarmCount() <100) {
				continue;
			}
			
			String disName = alarmSum.getDistrictName();
			
			if (SiteCountMap.containsKey(disName)) {
				int count = SiteCountMap.get(disName) + 1;
				SiteCountMap.remove(disName);
				SiteCountMap.put(disName, count);
			}else {
				SiteCountMap.put(disName, 1);
			}
		}
		
		//--------------------------demo start-----------
//		SiteCountMap.put("北京", 5000);
//		SiteCountMap.put("河北", 100);
//		SiteCountMap.put("四川", 200);
//		SiteCountMap.put("台湾", 300);
//		SiteCountMap.put("香港", 400);
//		SiteCountMap.put("新疆", 500);
//		SiteCountMap.put("黑龙江", 600);
//		SiteCountMap.put("内蒙古", 700);
//		SiteCountMap.put("宁夏", 800);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//--------------------------demo end-----------
		
		JSONObject jo = new JSONObject(); 
		jo.put("map", SiteCountMap);
		String resultGson = jo.toString();//转成json数据
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
	}
	
//	private String getDistributName(String name){
//		if(name == null || name.equals("")) {
//			return "其他";
//		}else if (name.startsWith("内蒙古")) {
//			return "内蒙古";
//		}else if (name.startsWith("黑龙江")) {
//			return "黑龙江";
//		}else {
//			return name.substring(0, 2);
//		}
//		
//	}
	
	/**
	 * 功能描述： 黑客分布地图（基于WAF数据）：
	 * 			中国地图，基于攻击源IP所在地址位置进行统计，以省为单位
	 * 参数描述： Model m
	 *		 @time 2016-8-3
	 */
	@RequestMapping(value="/HackerMap.html", method=RequestMethod.POST)
	@ResponseBody
	public String hackerDistributionMap(HttpServletResponse response,HttpServletRequest request) throws IOException {
		Map<String, Integer> attackCountMap = new HashMap<String, Integer>();
		//获取攻击源攻击统计信息
		String wafcreate = WafAPIWorker.getWafLogWebSecSrcIpList();
		
		JSONObject jsonObject = JSONObject.fromObject(wafcreate);			
		JSONArray jsonArray = jsonObject.getJSONArray("WafLogWebSecSrcIpList");
		
	    AddressUtils addressUtils = new AddressUtils(); 
		int attackSourceSize = jsonArray.size();
		for (int i = 0; i < attackSourceSize; i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			String srcIp = obj.getString("srcIp");
			
			//根据Ip查询所在省份
			AttackSource attackSource = attackSourceService.findByIp(srcIp);
			
			if (attackSource == null){
				String ipProvice = addressUtils.GetAddressByIp(srcIp);
				//insert into DB
				attackSource = new AttackSource();
				attackSource.setIp(srcIp);
				attackSource.setProvice(ipProvice);
				attackSourceService.save(attackSource);
				
			}
			
			if (attackCountMap.containsKey(attackSource.getProvice())) {
				int count = attackCountMap.get(attackSource.getProvice()) + 1;
				attackCountMap.remove(attackSource.getProvice());
				attackCountMap.put(attackSource.getProvice(), count);
			}else {
				attackCountMap.put(attackSource.getProvice(), 1);
			}
		}
		
		districtDataService.getDistrictList();
		List result = new ArrayList();
		for (Map.Entry<String, Integer> entry: attackCountMap.entrySet()) {
			String districtName = entry.getKey();
			if (districtName == null || districtName.equals("")) {
				continue;
			}
			//地理位置
			Map<String,Object> map = dataAnalysisService.findlLongitudeAndLatitude(districtName.substring(0, 2));
			//map.put("longitude", 116.4135540000);
			//map.put("latitude", 39.9110130000);
			map.put("name", districtName);
			map.put("val", entry.getValue());
			result.add(map);
		}
		
		
		//--------------------------demo start-----------
//		List result = new ArrayList();
//		Map<String,Object> map = new HashMap<String,Object>(); 
//		map.put("name", "北京");
//		map.put("longitude", 116.4135540000);
//		map.put("latitude", 39.9110130000);
//		map.put("val", 30);
//		result.add(map);
////		
//		Map<String,Object> map2 = new HashMap<String,Object>();
//		map2.put("name", "河北");
//		map2.put("longitude", 114.5208280000);
//		map2.put("latitude", 38.0486840000);
//		map2.put("val", 70);
//		result.add(map2);
//		
//		Map<String,Object> map3 = new HashMap<String,Object>();
//		map3.put("name", "四川");
//		map3.put("longitude", 104.0712160000);
//		map3.put("latitude", 30.5762790000);
//		map3.put("val", 150);
//		result.add(map3);
//		
//		Map<String,Object> map4 = new HashMap<String,Object>();
//		map4.put("name", "河南");
//		map4.put("longitude", 113.6313490000);
//		map4.put("latitude", 34.7534880000);
//		map4.put("val", 200);
//		result.add(map4);
//		
//		Map<String,Object> map5 = new HashMap<String,Object>();
//		map5.put("name", "辽宁");
//		map5.put("longitude", 123.4389730000);
//		map5.put("latitude", 41.8113390000);
//		map5.put("val", 5);
//		result.add(map5);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//--------------------------demo end-----------
		
		JSONObject jo = new JSONObject(); 
		jo.put("list", result);
		String resultGson = jo.toString();//转成json数据
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
	}
	
	
	@RequestMapping(value="/UserMap.html", method=RequestMethod.POST)
	@ResponseBody
	public String UserDistributionMap(HttpServletResponse response,HttpServletRequest request) throws IOException {
		List<UserDistribution> result = dataAnalysisService.findUserCountByDistrict();
		for (UserDistribution userDistribution : result) {
			String prociveName = userDistribution.getProviceName();
			if(prociveName!= null && !prociveName.equals("")) {
				
				Map<String,Object> map = dataAnalysisService.findlLongitudeAndLatitude(prociveName.substring(0, 2));
				if(map != null && map.size()>0){
					userDistribution.setLatitude((String)map.get("latitude"));
					userDistribution.setLongitude((String)map.get("longitude"));
				}
			}
		}
		
		//--------------------------demo start-----------
//		List<UserDistribution> result = new ArrayList<UserDistribution>();
//		UserDistribution user1 = new UserDistribution(); 
//		user1.setProviceName("北京");
//		user1.setLongitude("116.4135540000");
//		user1.setLatitude("39.9110130000");
//		user1.setUserCount(1000);
//		result.add(user1);
//		
//		UserDistribution user2 = new UserDistribution(); 
//		user2.setProviceName("河北");
//		user2.setLongitude("114.5208280000");
//		user2.setLatitude("38.0486840000");
//		user2.setUserCount(3000);
//		result.add(user2);
//		
//		UserDistribution user3 = new UserDistribution(); 
//		user3.setProviceName("四川");
//		user3.setLongitude("104.0712160000");
//		user3.setLatitude("30.5762790000");
//		user3.setUserCount(660);
//		result.add(user3);
//		
//		UserDistribution user4 = new UserDistribution(); 
//		user4.setProviceName("宁夏");
//		user4.setLongitude("106.2389760000");
//		user4.setLatitude("38.4923920000");
//		user4.setUserCount(1500);
//		result.add(user4);
		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//--------------------------demo end-----------
		
		JSONObject jo = new JSONObject(); 
		jo.put("list", result);
		String resultGson = jo.toString();//转成json数据
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
	}

}
