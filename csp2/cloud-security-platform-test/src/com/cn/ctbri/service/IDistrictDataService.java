package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.AlarmSum;
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
    /**
     * 最近一个小时内漏洞跟踪
     * @param paramMap
     * @return
     */
    List getVulnscanAlarmOneHour(Map<String, Object> paramMap);

    List getServiceAlarmMonth5(Map<String, Object> paramMap);

    int getMax(Map<String, Object> paramMap);

	List<District> getDistrictList();

	List<City> getCityListByProv(Map<String, Object> paramMap);
	String getMonth(int i);

	Alarm getServiceAlarmByMonth(Map<String, Object> paramMap);

	
	String getProvNameById(Map<String, Object> paramMap);
    /**
     * 获取漏洞情况TOP20
     * @param paramMap
     * @return
     */
    List getVulnscanAlarmTOP20();

    /**
     * 根据月份获取各等级漏洞分布
     * @return
     */
    List getVulnscanAlarm(Map<String, Object> paramMap);
    /**
     * 最近6个月内不同类型服务订单数量变化
     * @param month
     * @return
     */
    List getServiceUseInfoMonth6(Map<String, Object> paramMap);
    /**
     * 获取各行业用户数
     * @return
     */
    List getIndustryUserCount();
    /**
     * 获取各行业订单数
     * @return
     */
    List getIndustryOrderCount();
    /**
     * 根据servID获取按日期分组的订单数量
     * @return
     */
    List getOrderCountTimesAndServiceId(Map<String, Object> paramMap);
    /**
     * 获取最近一年内的日期
     * @return
     */
    List getDaysInYear(Map<String, Object> paramMap);
    List getDaysInYear();
    /**
     * 获取一年内的所有月份的最后一天 
     * @return
     */
    List getLastDayForMonthInYear(Map<String, Object> paramMap);
    
    /**
     * 获取同一网站同一漏洞数大于等于3的
     * @return
     */
    List getVulnscanAlarmOver3();
    /**
     * 获取同一网站同一漏洞数
     * @return
     */
    List getVulnscanAlarmAllCount();
    /**
     * 获取漏洞大于3的网站的用途分组个数
     * @return
     */
    List getAssetPurpose();
    
    //更新地域告警数
  	void updateDistrict(Map<String, Object> disMap);
  	
  	void updateSiteCount(List<District> list);
  	
  	List<District> getSiteCount();
  	
  	List getAllAlarmCount();
  	
  	void updateWafAlarmCount(List<District> list);
}
