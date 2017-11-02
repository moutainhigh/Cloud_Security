package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Alarm;

public interface IAlarmService {
	void saveAlarm(Alarm alarm);
	//根据orderid查询告警信息
    List<Alarm> findAlarmByOrderId(Map<String, Object> paramMap);
    //更新地域告警数
	void updateDistrict(Map<String, Object> disMap);
	//查询本次告警数
	List<Alarm> findAlarmBygroupId(Map<String, Object> paramMap);
	
	List<Alarm> findAlarmByTaskId(Map<String, Object> paramMap);
}
