package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.DistrictDataDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.City;
import com.cn.ctbri.entity.District;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-08-20
 * 描        述：   数据展示数据访问层实现类
 * 版        本：  1.0
 */
@Repository
@Transactional
public class DistrictDataDaoImpl extends DaoCommon implements DistrictDataDao {
	
	/**
	 * 功        能： DistrictDataMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.DistrictDataMapper.";
	private String nsCity = "com.cn.ctbri.entity.CityMapper.";

    public List<District> getDistrictByAll(Map<String, Object> paramMap) {
        return getSqlSession().selectList(ns + "findDistrictList",paramMap);
    }

    public List getDistrictDataById(Map<String, Object> paramMap) {
        return getSqlSession().selectList(ns + "getDistrictDataById",paramMap);
    }

    public List getDistrictAlarmTop5(Map<String, Object> paramMap) {
    	District d = new District();
        d.setLimit("true");
        paramMap.put("limit", "true");
        return getSqlSession().selectList(ns + "findDistrictList",paramMap);
    }

    public List getServiceAlarmTop5(Map<String, Object> paramMap) {
        return getSqlSession().selectList(ns + "getServiceAlarmTop5", paramMap);
    }

    public List getServiceAlarmMonth5(Map<String, Object> paramMap) {
        return getSqlSession().selectList(ns + "getServiceAlarmMonth5", paramMap);
    }

    public int getMax(Map<String, Object> paramMap) {
        List<Alarm> max = this.getSqlSession().selectList(ns + "getMax",paramMap);
        int maxValue = 0;
        if(max.size()>0){
            maxValue = max.get(0).getCount();
        }
        return maxValue;
    }

	public List<District> getDistrictList() {
		return getSqlSession().selectList(ns + "findDistrictListAll");
	}

	public List<City> getCityListByProv(Map<String, Object> paramMap) {
		return getSqlSession().selectList(nsCity + "findCityList",paramMap);
	}

	public String getProvNameById(Map<String, Object> paramMap) {
		return getSqlSession().selectOne(ns + "findProvName", paramMap);
	}		

	public String getMonth(int i) {
		String months = this.getSqlSession().selectOne(ns + "getMonth",i);
		return String.valueOf(months);
	}

	public Alarm getServiceAlarmByMonth(Map<String, Object> paramMap) {
		return getSqlSession().selectOne(ns + "getServiceAlarmByMonth", paramMap);
	}

	public List getVulnscanAlarmOneHour(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"getVulnscanAlarmOneHour", paramMap);
	}

	public List getVulnscanAlarmTOP20() {
		return this.getSqlSession().selectList(ns+"getVulnscanAlarmTOP20");
	}

	public List getVulnscanAlarm(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"getVulnscanAlarm", paramMap);
	}

	public List getServiceUseInfoMonth6(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"getServiceUseInfoMonth6", paramMap);
	}

	public List getIndustryOrderCount() {
		return this.getSqlSession().selectList(ns+"getIndustryOrderCount");
	}

	public List getIndustryUserCount() {
		return this.getSqlSession().selectList(ns+"getIndustryUserCount");
	}

	public List getOrderCountTimesAndServiceId(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"getOrderCountTimesAndServiceId",paramMap);
	}

	public List getDaysInYear() {
		return this.getSqlSession().selectList(ns+"getDaysInYear");
	}

	public List getLastDayForMonthInYear(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"getLastDayForMonthInYear",paramMap);
	}		
	
	public List getVulnscanAlarmOver3() {
		
		return this.getSqlSession().selectList(ns+"getVulnscanAlarmOver3");
	}

	public List getVulnscanAlarmAllCount() {
		return this.getSqlSession().selectList(ns+"getVulnscanAlarmAllCount");
	}

	public List getAssetPurpose() {
		return this.getSqlSession().selectList(ns+"getAssetPurpose");
	}

	public List getDaysInYear(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"getDaysInYear",paramMap);
	}
	
	public void updateDistrict(Map<String, Object> disMap) {
		getSqlSession().update(ns+"updateDistrict", disMap);
	}

	public void updateSiteCount(List<District> list) {
		getSqlSession().update(ns+"updateSiteCount", list);
		
	}

	public List<District> getSiteCount() {
		return getSqlSession().selectList(ns+"getSiteCount");
		
	}

	public List getAllAlarmCount() {
		return getSqlSession().selectList(ns+"getAllAlarmCount");
	}

	public void updateWafAlarmCount(List<District> list) {
		getSqlSession().update(ns+"updateWafAlarmCount", list);
		
	}
}
