package com.cn.ctbri.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.service.IDataAnalysisService;
import com.cn.ctbri.service.IDistrictDataService;

public class WafAlarmAnalysisWorker {
	static Logger logger = Logger.getLogger(WafAlarmAnalysisWorker.class.getName());
	
	@Autowired
	IDataAnalysisService dataAnalysisService;
	@Autowired
    IDistrictDataService districtDataService;
	
	
	public void execute() throws Exception {
		
		 logger.info(new Date()+"[WAF告警数量统计任务]:任务开始......");
		
		//查询 WAF告警数量
		String wafcreate = WafAPIWorker.getWafLogWebSecDstIpList();
		JSONObject jsonObject = JSONObject.fromObject(wafcreate);			
		JSONArray jsonArray = jsonObject.getJSONArray("WafLogWebSecDstIpList");
		
		
		List<District> list = districtDataService.getDistrictList();
		
		int wafWebSiteSize = jsonArray.size();
		for (int i = 0;i < wafWebSiteSize; i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			String dstIp = obj.getString("dstIp");
			int count = obj.getInt("count");
			
			//DB:根据waf防护目标的ip查询网站名、所在省份
			Map<String, String> assetInfo = dataAnalysisService.findAssetInfoByIp(dstIp);
			if (assetInfo != null) {
				String disName = assetInfo.get("districtName");
				
				for(District dis : list) {
					if (dis.getName().equals(disName)) {
						dis.setWafAlarmCount(count);
						break;
					}
				}
			}
			
		}
		
		//更新DB
		districtDataService.updateWafAlarmCount(list);
		logger.info(new Date()+"[WAF告警数量统计任务]:任务结束......");
	}
	
}
