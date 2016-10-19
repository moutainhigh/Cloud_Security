package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.DistrictDataDao;
import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.AlarmSum;
import com.cn.ctbri.entity.City;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IDistrictDataService;
import com.cn.ctbri.service.ITaskService;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-08-20
 * 描        述：  数据展示业务层实现类
 * 版        本：  1.0
 */
@Service
public class DistrictDataServiceImpl implements IDistrictDataService{

	@Autowired
	DistrictDataDao districtDataDao;

    public List<District> getDistrictByAll(Map<String, Object> paramMap) {
        return districtDataDao.getDistrictByAll(paramMap);
    }

    public List getDistrictDataById(Map<String, Object> paramMap) {
        return districtDataDao.getDistrictDataById(paramMap);
    }

    public List getDistrictAlarmTop5(Map<String, Object> paramMap) {
        return districtDataDao.getDistrictAlarmTop5(paramMap);
    }

    public List getServiceAlarmTop5(Map<String, Object> paramMap) {
        return districtDataDao.getServiceAlarmTop5(paramMap);
    }

    public List getServiceAlarmMonth5(Map<String, Object> paramMap) {
        return districtDataDao.getServiceAlarmMonth5(paramMap);
    }

    public int getMax(Map<String, Object> paramMap) {
        return districtDataDao.getMax(paramMap);
    }

	public List<District> getDistrictList() {
		return districtDataDao.getDistrictList();
	}
	public String getMonth(int i) {
		return districtDataDao.getMonth(i);
	}

	public List<City> getCityListByProv(Map<String, Object> paramMap) {
		return districtDataDao.getCityListByProv(paramMap);
	}
	public Alarm getServiceAlarmByMonth(Map<String, Object> paramMap) {
		return districtDataDao.getServiceAlarmByMonth(paramMap);
	}

	public String getProvNameById(Map<String, Object> paramMap) {
		return districtDataDao.getProvNameById(paramMap);
	}

	public List getVulnscanAlarmOneHour(Map<String, Object> paramMap) {
		return districtDataDao.getVulnscanAlarmOneHour(paramMap);
	}

	public List getVulnscanAlarmTOP20() {
		return districtDataDao.getVulnscanAlarmTOP20();
	}

	public List getVulnscanAlarm(Map<String, Object> paramMap) {
		return districtDataDao.getVulnscanAlarm(paramMap);
	}

	public List getServiceUseInfoMonth6(Map<String, Object> paramMap) {
		return districtDataDao.getServiceUseInfoMonth6(paramMap);
	}

	public List getIndustryOrderCount() {
		return districtDataDao.getIndustryOrderCount();
	}

	public List getIndustryUserCount() {
		return districtDataDao.getIndustryUserCount();
	}

	public List getOrderCountTimesAndServiceId(Map<String, Object> paramMap) {
		return districtDataDao.getOrderCountTimesAndServiceId(paramMap);
	}

	public List getDaysInYear() {
		return districtDataDao.getDaysInYear();
	}

	public List getLastDayForMonthInYear(Map<String, Object> paramMap) {
		return districtDataDao.getLastDayForMonthInYear(paramMap);
	}

	public List getVulnscanAlarmOver3() {
		return districtDataDao.getVulnscanAlarmOver3();
	}

	public List getVulnscanAlarmAllCount() {
		return districtDataDao.getVulnscanAlarmAllCount();
	}

	public List getAssetPurpose() {
		return districtDataDao.getAssetPurpose();
	}

	public List getDaysInYear(Map<String, Object> paramMap) {
		return districtDataDao.getDaysInYear(paramMap);
	}

	public void updateDistrict(Map<String, Object> disMap) {
		districtDataDao.updateDistrict(disMap);
	}

	public void updateSiteCount(List<District> list) {
		districtDataDao.updateSiteCount(list);
	}

	public List<District> getSiteCount() {
		return districtDataDao.getSiteCount();
		
	}

	public List getAllAlarmCount() {
		return districtDataDao.getAllAlarmCount();
	}

	public void updateWafAlarmCount(List<District> list) {
		districtDataDao.updateWafAlarmCount(list);
		
	}
}
