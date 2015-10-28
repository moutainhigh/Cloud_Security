package com.sinosoft.dao;

import java.util.List;
import java.util.Map;

import com.sinosoft.vo.CsAlarm;

public interface CsAlarmMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CsAlarm record);

	int insertSelective(CsAlarm record);

	CsAlarm selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CsAlarm record);

	int updateByPrimaryKey(CsAlarm record);

	List<Map> findAlarmByUserId(Integer userId);

	List<CsAlarm> findAlarmByOrderId(Map map);
}