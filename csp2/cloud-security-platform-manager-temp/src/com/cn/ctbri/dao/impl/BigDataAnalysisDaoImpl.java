package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.BigDataAnalysisDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.AlarmSum;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.entity.UserDistribution;

@Repository
public class BigDataAnalysisDaoImpl extends DaoCommon implements BigDataAnalysisDao{
	
	private String ns = "com.cn.ctbri.entity.BigDataAnalysisMapper.";
	
	//以省为单位，累加所有漏洞、木马、关键字、篡改、可用性告警数据总和超过100的网站数
//	public List findHighRiskSite() {
//		return this.getSqlSession().selectList(ns + "findHighRiskSite");
//	}
	
	//查询每个省份的用户数量
	public List<UserDistribution> findUserCountByDistrict() {
		return this.getSqlSession().selectList(ns + "findUserCountByDistrict");
	}
	
	//根据省份查询经纬度
	public District findlDistrictDataByProName(String proName) {
		return this.getSqlSession().selectOne(ns + "findlLongitudeAndLatitude", proName);
	}
	
	//以网站资产为单位，累加所有漏洞、木马、关键字、篡改、可用性告警数据总和
	public List<AlarmSum> findAlarmSum() {
		return this.getSqlSession().selectList(ns + "findAlarmSum");
	}

	public Map<String, String> findAssetInfoByIp(String ip) {
		return this.getSqlSession().selectOne(ns + "findAssetInfoByIp", "%"+ip+"%");
	}

	public List findAssetCountByDistrict() {
		return this.getSqlSession().selectList(ns + "findAssetCountByDistrict");
	}

}
