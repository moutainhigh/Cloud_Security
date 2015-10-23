package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.City;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.entity.Alarm;



/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-08-20
 * 描        述：  数据展示业务层接口
 * 版        本：  1.0
 */
public interface IDistrictDataService {

    List getDistrictByAll(Map<String, Object> paramMap);

    List getDistrictDataById(Map<String, Object> paramMap);

    List getDistrictAlarmTop5(Map<String, Object> paramMap);

    List getServiceAlarmTop5(Map<String, Object> paramMap);

    List getServiceAlarmMonth5(Map<String, Object> paramMap);

    int getMax(Map<String, Object> paramMap);

	List<District> getDistrictList();

	List<City> getCityListByProv(Map<String, Object> paramMap);
	String getMonth(int i);

	Alarm getServiceAlarmByMonth(Map<String, Object> paramMap);

	
	String getProvNameById(Map<String, Object> paramMap);
}
