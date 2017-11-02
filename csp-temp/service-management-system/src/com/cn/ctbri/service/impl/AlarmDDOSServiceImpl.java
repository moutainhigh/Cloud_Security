package com.cn.ctbri.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AlarmDDOSDao;
import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.service.IAlarmDDOSService;
import com.cn.ctbri.service.IAlarmService;
@Service
public class AlarmDDOSServiceImpl implements IAlarmDDOSService{

	@Autowired
	AlarmDDOSDao alarmDDOSDao;

	public void saveAlarmDDOS(AlarmDDOS alarmDDOS) {
		alarmDDOSDao.insert(alarmDDOS);
	}

    public void updeteAlarmDDOS(AlarmDDOS ddos) {
        alarmDDOSDao.update(ddos);
    }
	
}
