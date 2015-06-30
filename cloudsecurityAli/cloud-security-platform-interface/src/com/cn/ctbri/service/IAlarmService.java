package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Alarm;

public interface IAlarmService {
	void saveAlarm(Alarm alarm);
	//根据orderid查询告警信息
    List<Alarm> findAlarmByOrderId(Map<String, Object> paramMap);
}
