package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmBug;
import com.cn.ctbri.entity.AlarmDDOS;
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
	public List findAlarmByUserId(int id) {
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
     * 功能描述：根据orderId查询DDOS告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Alarm>
     */
    public List<AlarmDDOS> getAlarmDdosByOrderId(Map<String, Object> paramMap) {
        return alarmDao.getAlarmDdosByOrderId(paramMap);
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
	 /**
     * 功能描述：根据组合查询条件查询告警
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	public List<Alarm> findAlarmByParam(Map<String, Object> paramMap) {
		return alarmDao.findAlarmByParam(paramMap);
	}
	/**
     * 功能描述：根据组合查询条件查询告警--参数为空level为空，alarm_type不为空
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	public List<Alarm> findAlarmByParamAlarm_type(Map<String, Object> paramMap) {
		return alarmDao.findAlarmByParamAlarm_type(paramMap);
	}
	/**
     * 功能描述：根据组合查询条件查询告警--参数为空level不为空，alarm_type不为空
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	public List<Alarm> findAlarmByParamAlarm_typeAndLevel(
			Map<String, Object> paramMap) {
		return  alarmDao.findAlarmByParamAlarm_typeAndLevel(paramMap);
	}
	public List<Alarm> findAlarmByOrderIdAndExecute_time(
			Map<String, Object> paramMap) {
		return  alarmDao.findAlarmByOrderIdAndExecute_time(paramMap);
	}
    public List<Alarm> findAlarm(Map<String, Object> paramMap) {
        return alarmDao.findAlarm(paramMap);
    }
    
    public List<AlarmDDOS> findAlarmByTaskId(int TaskId){
    	return alarmDao.findAlarmByTaskId(TaskId);
    	
    }

	public List<Alarm> findSensitiveWordByOrderId(Map<String, Object> paramMap) {
		return alarmDao.findSensitiveWordByOrderId(paramMap);
	}
    public List<AlarmDDOS> findEndAlarmByTaskId(int taskId) {
        return alarmDao.findEndAlarmByTaskId(taskId);
    }
	public List<Alarm> findKeywordWarningByOrderId(String orderId) {
		return alarmDao.findKeywordWarningByOrderId(orderId);
	}
	public List<Alarm> findRightByOrderIdAndUrl(Map<String, Object> map) {
		return alarmDao.findRightByOrderIdAndUrl(map);
	}
	public List<Alarm> findKeywordByUrlAndOrderId(Map<String, Object> map) {
		return alarmDao.findKeywordByUrlAndOrderId(map);
	}
    public void deleteAlarmByTaskId(Map<String, Object> paramMap) {
        alarmDao.deleteAlarmByTaskId(paramMap);
    }
    //根据orderid查询告警分类数,报表导出用
    public List findAlarmByOrderIdorGroupId(Map<String, Object> paramMap) {
        return alarmDao.findAlarmByOrderIdorGroupId(paramMap);
    }
	public String getHours5(int i) {
		return alarmDao.getHours5(i);
	}
	public List<Alarm> getAlarmNumByTime(String time) {
		return alarmDao.getAlarmNumByTime(time);
	}
	public List getAlarmTop5ByService(Map<String, Object> paramMap) {
		return alarmDao.getAlarmTop5ByService(paramMap);
	}
	//资产查询告警
	public List<Alarm> getAlarmByAsset(Map<String, Object> paramAlarm) {
		return alarmDao.getAlarmByAsset(paramAlarm);
	}
	public List getAlarmByParam(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return alarmDao.getAlarmByParam(paramMap);
	}
	public List<AlarmBug> findAlarmBugCounts(Map<String, Object> paramMap) {
		return alarmDao.getAlarmBugCounts(paramMap);
	}
	public List<AlarmBug> findBugMaxCounts(Map<String, Object> paramMap) {
		return alarmDao.getBugMaxCounts(paramMap);
	}
}
