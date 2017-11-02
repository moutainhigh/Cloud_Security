package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.dao.AssertAlarmDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.AssertAlarm;
import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssertAlarmService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-30
 * 描        述：  告警业务层实现类
 * 版        本：  1.0
 */
@Service
public class AssertAlarmServiceImpl implements IAssertAlarmService{

	@Autowired
	AssertAlarmDao assertAlarmDao;
	
	/**
	 * 功能描述：获取资产告警统计分析数据
	 * @param paramMap
	 * @return
	 */
	public List<AssertAlarm> findAssertAlarmByMap(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return assertAlarmDao.findAssertAlarmByMap(paramMap);
	}

	/**
	 * 功能描述：获取资产告警类型统计分析数据
	 * @param paramMap
	 * @return
	 */
	public List<AssertAlarm> findAssertAlarmTypeByMap(
			Map<String, Object> paramMap) {
		return assertAlarmDao.findAssertAlarmTypeByMap(paramMap);
	}

	/**
	 * 功能描述：获取资产告警趋势分析数据
	 * @param paramMap
	 * @return
	 */
	public List<AssertAlarm> findAssertAlarmTrendByMap(
			Map<String, Object> paramMap) {
		return assertAlarmDao.findAssertAlarmTrendByMap(paramMap);
	}
	
	
}
