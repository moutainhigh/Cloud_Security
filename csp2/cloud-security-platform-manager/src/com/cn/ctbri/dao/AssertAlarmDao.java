package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.AssertAlarm;

/**
 * 创 建 人  ：  zx
 * 创建日期：  2015-11-02
 * 描        述：  资产报警Dao接口
 * 版        本：  1.0
 */
public interface AssertAlarmDao {
	/**
	 * 功能描述：获取资产告警统计分析数据
	 * @param paramMap
	 * @return
	 */
	List<AssertAlarm> findAssertAlarmByMap(Map<String, Object> paramMap);
	
	
	/**
	 * 功能描述：获取资产告警类型统计分析数据
	 * @param paramMap
	 * @return
	 */
	List<AssertAlarm> findAssertAlarmTypeByMap(Map<String, Object> paramMap);
	
	/**
	 * 功能描述：获取资产告警趋势分析数据
	 * @param paramMap
	 * @return
	 */
	List<AssertAlarm> findAssertAlarmTrendByMap(Map<String, Object> paramMap);

}
