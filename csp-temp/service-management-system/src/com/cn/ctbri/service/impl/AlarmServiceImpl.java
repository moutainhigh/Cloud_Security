package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

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

	//根据orderid查询告警信息
    public List<Alarm> findAlarmByOrderId(Map<String, Object> paramMap) {
        return alarmDao.findAlarmByOrderId(paramMap);
    }

	public void updateDistrict(Map<String, Object> disMap) {
		alarmDao.updateDistrict(disMap);
	}

	public List<Alarm> findAlarmBygroupId(Map<String, Object> paramMap) {
		return alarmDao.findAlarmBygroupId(paramMap);
	}

	public String findAdvice(String name) {
		return alarmDao.findAdvice(name);
	}

	public void updateAlarm(Alarm alarm) {
		alarmDao.update(alarm);
	}

	public List<Alarm> findAlarmByTaskId(String taskId) {
		return alarmDao.findAlarmByTaskId(taskId);
	}

	public List findAlarmByOrderIdorGroupId(Map<String, Object> paramMap) {
		return alarmDao.findAlarmByOrderIdorGroupId(paramMap);
	}
	
}
