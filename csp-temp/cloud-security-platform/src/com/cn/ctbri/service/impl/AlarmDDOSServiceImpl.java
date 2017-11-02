package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AlarmDDOSDao;
import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAlarmDDOSService;
import com.cn.ctbri.service.IAlarmService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-4-16
 * 描        述：  安恒告警业务层实现类
 * 版        本：  1.0
 */
@Service
public class AlarmDDOSServiceImpl implements IAlarmDDOSService{
	@Autowired
	AlarmDDOSDao AlarmDDOSDao;
	public List<AlarmDDOS> findAlarmDDOSByOrderId(String orderId) {
		return AlarmDDOSDao.findAlarmDDOSByOrderId(orderId);
	}
	
}
