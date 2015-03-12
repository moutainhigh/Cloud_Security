package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.Task;
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
	/**
     * 功能描述：根据orderId查询告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Alarm>
     */
    public List<Alarm> getAlarmByOrderId(Map<String, Object> paramMap) {
        return alarmDao.getAlarmByOrderId(paramMap);
    }
    /**
     * 功能描述：根据orderId查询任务信息
     *       @time 2015-2-4
     * 返回值    ：List<Task>
     */
    public List<Task> getTaskByOrderId(Map<String, Object> paramMap) {
        return alarmDao.getTaskByOrderId(paramMap);
    }
    /**
     * 功能描述：根据taskId查询告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Task>
     */
    public List<Alarm> getAlarmByTaskId(Map<String, Object> param) {
        return alarmDao.getAlarmByTaskId(param);
    }
    /**
     * 功能描述：查询扫描总数
     *       @time 2015-3-12
     */
	public List<Alarm> findAll() {
		return alarmDao.findAll();
	}
	
}
