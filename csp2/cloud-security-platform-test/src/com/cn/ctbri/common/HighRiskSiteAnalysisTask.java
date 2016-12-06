package com.cn.ctbri.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.AlarmSum;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.service.IDataAnalysisService;
import com.cn.ctbri.service.IDistrictDataService;

public class HighRiskSiteAnalysisTask {
	static Logger logger = Logger.getLogger(HighRiskSiteAnalysisTask.class.getName());
	
	@Autowired
	IDataAnalysisService dataAnalysisService;
	@Autowired
    IDistrictDataService districtDataService;
	
	
	public void execute() throws Exception {
		
		 logger.info(new Date()+"[高危网站统计任务]:任务开始......");
		 
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
		
		
		List<District> list = districtDataService.getDistrictList();
		//判断告警数据总和是否超过100,超过100则该省分的网站数量加1
		for(AlarmSum alarmSum: alarmSumList) {
			if (alarmSum.getAlarmCount() <100) {
				continue;
			}
			
			String disName = alarmSum.getDistrictName();
			
			for(District dis : list) {
				if (dis.getName().equals(disName)) {
					dis.setSiteCount(dis.getSiteCount() + 1);
					break;
				}
			}
			
		}
		
		//更新DB
		districtDataService.updateSiteCount(list);
		logger.info(new Date()+"[高危网站统计任务]:任务结束......");
		
	}
	
}
