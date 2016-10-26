package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.BigDataAnalysisDao;
import com.cn.ctbri.entity.AlarmSum;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.entity.UserDistribution;
import com.cn.ctbri.service.IBigDataAnalysisService;

@Service
public class BigDataAnalysisServiceImpl implements IBigDataAnalysisService {
	
	@Autowired
	BigDataAnalysisDao dataAnalysisDao;
	
	//以省为单位，累加所有漏洞、木马、关键字、篡改、可用性告警数据总和超过100的网站数
//	public List findHighRiskSite() {
//		return dataAnalysisDao.findHighRiskSite();
//	}
	
	//查询每个省份的用户数量
	public List<UserDistribution> findUserCountByDistrict() {
		return dataAnalysisDao.findUserCountByDistrict();
	}

	public District findlLongitudeAndLatitude(String proName) {
		return dataAnalysisDao.findlDistrictDataByProName(proName);
	}
	
	public List<AlarmSum> findAlarmSum(){
		return dataAnalysisDao.findAlarmSum();
	}

	//根据waf防护目标的ip查询网站名、所在省份
	public Map<String, String> findAssetInfoByIp(String ip) {
		return dataAnalysisDao.findAssetInfoByIp(ip);
	}

	public List findAssetCountByDistrict() {
		return dataAnalysisDao.findAssetCountByDistrict();
	}

}
