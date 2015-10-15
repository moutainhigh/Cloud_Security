package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.DistrictDataDao;
import com.cn.ctbri.dao.TaskDao;
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

	public String getMonth(int i) {
		return districtDataDao.getMonth(i);
	}

	public Alarm getServiceAlarmByMonth(Map<String, Object> paramMap) {
		return districtDataDao.getServiceAlarmByMonth(paramMap);
	}



}
