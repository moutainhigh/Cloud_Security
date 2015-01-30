package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.service.IAlarmService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-30
 * 描        述：  告警业务层实现类
 * 版        本：  1.0
 */
@Service
public class AlarmServiceImpl implements IAlarmService{

	@Autowired
	AlarmDao alarmDao;
	/**
	 * 功能描述：根据用户id查询告警信息
	 * 参数描述：int id
	 *       @time 2015-1-30
	 * 返回值    ：List<Alarm>
	 */
	public List<Alarm> findAlarmByUserId(int id) {
		return alarmDao.findAlarmByUserId(id);
	}
	
}
