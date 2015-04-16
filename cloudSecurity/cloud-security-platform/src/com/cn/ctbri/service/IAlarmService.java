package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.Task;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-30
 * 描        述：  告警业务层接口
 * 版        本：  1.0
 */
public interface IAlarmService {

	/**
	 * 功能描述：根据用户id查询告警信息
	 * 参数描述：int id
	 *       @time 2015-1-30
	 * 返回值    ：List<Alarm>
	 */
	List<Alarm> findAlarmByUserId(int id);
	/**
     * 功能描述：根据orderId查询告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Alarm>
     */
    List<Alarm> getAlarmByOrderId(Map<String, Object> paramMap);
	/**
     * 功能描述：根据orderId查询DDOS告警信息
     *       @time 2015-2-4
     * 返回值    ：List<AlarmDDOS>
     */
    List<AlarmDDOS> getAlarmDdosByOrderId(Map<String, Object> paramMap);
    /**
     * 功能描述：根据orderId查询任务信息
     *       @time 2015-2-4
     * 返回值    ：List<Task>
     */
    List<Task> getTaskByOrderId(Map<String, Object> paramMap);
    /**
     * 功能描述：根据taskId查询告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Task>
     */
    List<Alarm> getAlarmByTaskId(Map<String, Object> param);
    /**
     * 功能描述：查询扫描总数
     *       @time 2015-3-12
     */
	List<Alarm> findAll();
	 /**
     * 功能描述：根据组合查询条件查询告警
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	List<Alarm> findAlarmByParam(Map<String, Object> paramMap);
	/**
     * 功能描述：根据组合查询条件查询告警--参数为空level为空，alarm_type不为空
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	List<Alarm> findAlarmByParamAlarm_type(Map<String, Object> paramMap);
	/**
     * 功能描述：根据组合查询条件查询告警--参数为空level不为空，alarm_type不为空
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	List<Alarm> findAlarmByParamAlarm_typeAndLevel(Map<String, Object> paramMap);
	List<Alarm> findAlarmByOrderIdAndExecute_time(Map<String, Object> paramMap);
    List<Alarm> findAlarm(Map<String, Object> paramMap);
    
	/**
	 * 功能描述：根据任务id查询告警信息
	 * 参数描述：int taskid
	 *       @time 2015-1-30
	 * 返回值    ：List<Alarm>
	 */
	List<Alarm> findAlarmByTaskId(int TaskId);
}
