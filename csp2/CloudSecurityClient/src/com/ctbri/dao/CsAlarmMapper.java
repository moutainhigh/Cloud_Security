package com.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.ctbri.vo.CsAlarm;

public interface CsAlarmMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CsAlarm record);

	int insertSelective(CsAlarm record);

	Map selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CsAlarm record);

	int updateByPrimaryKey(CsAlarm record);

	List<Map> findAlarmByUserId(Integer userId);

	List<Map> findAlarmByOrderId(Map map);
	
	List<CsAlarm> findAlarmByAlarmId(Map map);
}