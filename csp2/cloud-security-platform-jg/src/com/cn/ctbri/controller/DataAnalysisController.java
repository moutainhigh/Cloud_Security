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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.AttackSource;
import com.cn.ctbri.entity.District;
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
	 * 功能描述： 高危网站地图（所有监测数据与WAF数据结合）：
	 * 			以省为单位，累加所有漏洞、木马、关键字、篡改、可用性及WAF告警数据总和超过100的网站数
	 * 参数描述： Model m
	 *		 @time 2016-8-3
	 */
	@RequestMapping(value="/highSiteMap.html", method=RequestMethod.POST)
	@ResponseBody
	public String highRiskSiteMap(HttpServletResponse response,HttpServletRequest request) throws IOException {
        
		
//		List<District> result = districtDataService.getSiteCount();
		
		//累加所有漏洞、木马、关键字、篡改、可用性告警数据
		List result = districtDataService.getAllAlarmCount();
		
//		//查询 WAF告警数量
//		String wafcreate = WafAPIWorker.getWafLogWebSecDstIpList();
//		JSONObject jsonObject = JSONObject.fromObject(wafcreate);			
//		JSONArray jsonArray = jsonObject.getJSONArray("WafLogWebSecDstIpList");
		
//		累加所有漏洞、木马、关键字、篡改、可用性及WAF告警数据总和
//		----------------------累加告警数据总和  start ----------------------
//		int wafWebSiteSize = jsonArray.size();
//		for (int i = 0;i < wafWebSiteSize; i++) {
//			JSONObject obj = (JSONObject) jsonArray.get(i);
//			String dstIp = obj.getString("dstIp");
//			int count = obj.getInt("count");
//			System.out.print(dstIp + "/"+count+": ");
//			//DB:根据waf防护目标的ip查询网站名、所在省份
//			Map<String, String> assetInfo = dataAnalysisService.findAssetInfoByIp(dstIp);
//			if (assetInfo != null) {
//				String disName = assetInfo.get("districtName");
////				String addr = assetInfo.get("addr");
//				System.out.println(disName);
//				
//				//累计标志 true：已累计 false：未累计
//				boolean totalFlag = false;
//				for(AlarmSum alarmSum: result) {
//					if (alarmSum.getDistrictName().equals(disName)) {
//						alarmSum.setAlarmCount(alarmSum.getAlarmCount() + count);
//					}
//				}
//				
//			}
//			
//		}
		
		
		
		//--------------------------demo start-----------
//		List<District> result = new ArrayList<District>();
//		
//		District dis1 = new District();
//		dis1.setName("北京");
//		dis1.setSiteCount(5000);
//		result.add(dis1);
//		
//		District dis2 = new District();
//		dis2.setName("河北");
//		dis2.setSiteCount(100);
//		result.add(dis2);
//		
//		District dis3 = new District();
//		dis3.setName("四川");
//		dis3.setSiteCount(200);
//		result.add(dis3);
//		
//		District dis4 = new District();
//		dis4.setName("台湾");
//		dis4.setSiteCount(300);
//		result.add(dis4);
		
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
				if (!ipProvice.equals("获取IP地址异常：") || !ipProvice.equals("IP地址有误")) {
					attackSource = new AttackSource();
					attackSource.setIp(srcIp);
					attackSource.setProvice(ipProvice);
					attackSourceService.save(attackSource);
				}
				
			}
			
			if (attackCountMap.containsKey(attackSource.getProvice())) {
				int count = attackCountMap.get(attackSource.getProvice()) + 1;
				attackCountMap.remove(attackSource.getProvice());
				attackCountMap.put(attackSource.getProvice(), count);
			}else {
				attackCountMap.put(attackSource.getProvice(), 1);
			}
		}
		
		List result = new ArrayList();
		for (Map.Entry<String, Integer> entry: attackCountMap.entrySet()) {
			String districtName = entry.getKey();
			if (districtName == null || districtName.equals("")) {
				continue;
			}
			//地理位置
			Map<String,Object> map = new HashMap<String,Object>();
			District dis= dataAnalysisService.findlLongitudeAndLatitude(districtName.substring(0, 2));
			if (dis!= null){
				map.put("name", districtName);
				map.put("val", entry.getValue());
//				map.put("val", 30);
				map.put("longitude", dis.getLongitude());
				map.put("latitude", dis.getLatitude());
				result.add(map);
			}
		}
		
		
		//--------------------------demo start-----------
//		List result = new ArrayList();
//		Map<String,Object> map = new HashMap<String,Object>(); 
//		map.put("name", "北京");
//		map.put("longitude", 116.4135540000);
//		map.put("latitude", 39.9110130000);
//		map.put("val", 15);
//		result.add(map);
////		
//		Map<String,Object> map2 = new HashMap<String,Object>();
//		map2.put("name", "河北");
//		map2.put("longitude", 114.5208280000);
//		map2.put("latitude", 38.0486840000);
//		map2.put("val", 40);
//		result.add(map2);
////		
//		Map<String,Object> map3 = new HashMap<String,Object>();
//		map3.put("name", "四川");
//		map3.put("longitude", 104.0712160000);
//		map3.put("latitude", 30.5762790000);
//		map3.put("val", 90);
//		result.add(map3);
//		
//		Map<String,Object> map4 = new HashMap<String,Object>();
//		map4.put("name", "河南");
//		map4.put("longitude", 113.6313490000);
//		map4.put("latitude", 34.7534880000);
//		map4.put("val", 280);
//		result.add(map4);
//		
//		Map<String,Object> map5 = new HashMap<String,Object>();
//		map5.put("name", "辽宁");
//		map5.put("longitude", 123.4389730000);
//		map5.put("latitude", 41.8113390000);
//		map5.put("val", 280);
//		result.add(map5);
//		
//		Map<String,Object> map6 = new HashMap<String,Object>();
//		map6.put("name", "新疆");
//		map6.put("longitude", 87.6233140000);
//		map6.put("latitude", 43.8328060000);
//		map6.put("val", 500);
//		result.add(map6);
//		
//		Map<String,Object> map7 = new HashMap<String,Object>();
//		map7.put("name", "香港");
//		map7.put("longitude", 114.1719940000);
//		map7.put("latitude", 22.2810890000);
//		map7.put("val", 200);
//		result.add(map7);
//		
//		Map<String,Object> map8 = new HashMap<String,Object>(); 
//		map8.put("name", "西藏");
//		map8.put("longitude", 91.1210250000);
//		map8.put("latitude", 29.6500880000);
//		map8.put("val", 200);
//		result.add(map8);
		
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
	public String userDistributionMap(HttpServletResponse response,HttpServletRequest request) throws IOException {
		List<UserDistribution> result = dataAnalysisService.findUserCountByDistrict();
		for (UserDistribution userDistribution : result) {
			String prociveName = userDistribution.getProviceName();
			if(prociveName!= null && !prociveName.equals("")) {
				
				District dis = dataAnalysisService.findlLongitudeAndLatitude(prociveName.substring(0, 2));
				if(dis != null){
					userDistribution.setLatitude(dis.getLatitude());
					userDistribution.setLongitude(dis.getLongitude());
				}
			}
		}
		
		//--------------------------demo start-----------
//		List<UserDistribution> result = new ArrayList<UserDistribution>();
//		UserDistribution user1 = new UserDistribution(); 
//		user1.setProviceName("北京");
//		user1.setLongitude("116.4135540000");
//		user1.setLatitude("39.9110130000");
//		user1.setUserCount(2500);
//		result.add(user1);
//		
//		UserDistribution user2 = new UserDistribution(); 
//		user2.setProviceName("河北");
//		user2.setLongitude("114.5208280000");
//		user2.setLatitude("38.0486840000");
//		user2.setUserCount(3200);
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
//		user4.setUserCount(4500);
//		result.add(user4);
//		
//		UserDistribution user5 = new UserDistribution(); 
//		user5.setProviceName("新疆");
//		user5.setLongitude("87.6233140000");
//		user5.setLatitude("43.8328060000");
//		user5.setUserCount(1800);
//		result.add(user5);
//		
//		UserDistribution user6 = new UserDistribution(); 
//		user5.setProviceName("西藏");
//		user5.setLongitude("91.1210250000");
//		user5.setLatitude("29.6500880000");
//		user5.setUserCount(1800);
//		result.add(user5);
		
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
	
	/**
	 * 资产分布
	 * */
	@RequestMapping(value="/AssetMap.html", method=RequestMethod.POST)
	@ResponseBody
	public String assetDistributionMap(HttpServletResponse response,HttpServletRequest request) throws IOException {
		List result = dataAnalysisService.findAssetCountByDistrict();
		
		//--------------------------demo start-----------
//		List result = new ArrayList();
//		Map<String,Object> map = new HashMap<String,Object>(); 
//		map.put("name", "北京");
//		map.put("longitude", 116.4135540000);
//		map.put("latitude", 39.9110130000);
//		map.put("num", 15);
//		result.add(map);
////		
//		Map<String,Object> map2 = new HashMap<String,Object>();
//		map2.put("name", "河北");
//		map2.put("longitude", 114.5208280000);
//		map2.put("latitude", 38.0486840000);
//		map2.put("num", 40);
//		result.add(map2);
////		
//		Map<String,Object> map3 = new HashMap<String,Object>();
//		map3.put("name", "四川");
//		map3.put("longitude", 104.0712160000);
//		map3.put("latitude", 30.5762790000);
//		map3.put("num", 90);
//		result.add(map3);
//		
//		Map<String,Object> map4 = new HashMap<String,Object>();
//		map4.put("name", "河南");
//		map4.put("longitude", 113.6313490000);
//		map4.put("latitude", 34.7534880000);
//		map4.put("num", 280);
//		result.add(map4);
//		
//		Map<String,Object> map5 = new HashMap<String,Object>();
//		map5.put("name", "辽宁");
//		map5.put("longitude", 123.4389730000);
//		map5.put("latitude", 41.8113390000);
//		map5.put("num", 280);
//		result.add(map5);
//		
//		Map<String,Object> map6 = new HashMap<String,Object>();
//		map6.put("name", "新疆");
//		map6.put("longitude", 87.6233140000);
//		map6.put("latitude", 43.8328060000);
//		map6.put("num", 500);
//		result.add(map6);
//		
//		Map<String,Object> map7 = new HashMap<String,Object>();
//		map7.put("name", "香港");
//		map7.put("longitude", 114.1719940000);
//		map7.put("latitude", 22.2810890000);
//		map7.put("num", 200);
//		result.add(map7);
//		
//		Map<String,Object> map8 = new HashMap<String,Object>(); 
//		map8.put("name", "西藏");
//		map8.put("longitude", 91.1210250000);
//		map8.put("latitude", 29.6500880000);
//		map8.put("num", 200);
//		result.add(map8);
		
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
