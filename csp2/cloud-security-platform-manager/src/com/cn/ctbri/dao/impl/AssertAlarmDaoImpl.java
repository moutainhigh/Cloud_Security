package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.dao.AssertAlarmDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.AssertAlarm;
import com.cn.ctbri.entity.Task;
/**
 * 创 建 人  ：  zx
 * 创建日期：  2015-11-02
 * 描        述：  资产报警Dao接口实现类
 * 版        本：  1.0
 */
@Repository
@Transactional
public class AssertAlarmDaoImpl extends DaoCommon implements AssertAlarmDao {
	
	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.AssertAlarmMapper.";
	
	/**
	 * 功能描述：获取资产告警统计分析数据
	 * @param paramMap
	 * @return
	 */
	public List<AssertAlarm> findAssertAlarmByMap(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"findAssertAlarmByParam", paramMap);
	}

	/**
	 * 功能描述：获取资产告警类型统计分析数据
	 * @param paramMap
	 * @return
	 */
	public List<AssertAlarm> findAssertAlarmTypeByMap(
			Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"findAssertAlarmTypeByParam", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.cn.ctbri.dao.AssertAlarmDao#findAssertAlarmTrendByMap(java.util.Map)
	 */
	public List<AssertAlarm> findAssertAlarmTrendByMap(
			Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"findAssertAlarmTrendByParam", paramMap);
	}		
	
}
