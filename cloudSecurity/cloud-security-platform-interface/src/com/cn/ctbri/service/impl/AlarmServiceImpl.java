package com.cn.ctbri.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.service.IAlarmService;
@Service
public class AlarmServiceImpl implements IAlarmService{

	@Autowired
	AlarmDao alarmDao;

	public void saveAlarm(Alarm alarm) {
		alarmDao.insert(alarm);
	}
	
}
