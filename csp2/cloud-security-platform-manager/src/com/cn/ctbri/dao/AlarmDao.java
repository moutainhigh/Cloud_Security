package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmBug;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.Task;

/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-07
 * 描        述：  报警信息Dao接口
 * 版        本：  1.0
 */
public interface AlarmDao {
	/**
	 * 功能描述：插入报警日志
	 * 参数描述：Alarm alarm 报警日志对象
	 *		 @time 2015-01-07
	 * 返回值    ：  long 若成功主键ID，若不成功返回-1
	 */
	public long insert(Alarm alarm);
	/**
	 * 功能描述：查询报警日志
	 * 参数描述：Map<String,Object> map 查询参数
	 *		 @time 2015-01-07
	 * 返回值    ：  List<Alarm> 报警日志对象集合
	 */
	public List<Alarm> findAlarm(Map<String,Object> map);
	/**
	 * 功能描述：根据用户id查询告警信息
	 * 参数描述：int id
	 *       @time 2015-1-30
	 * 返回值    ：List<Alarm>
	 */
	public List findAlarmByUserId(int id);
	/**
     * 功能描述：根据orderId查询告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Alarm>
     */
    public List<Alarm> getAlarmByOrderId(Map<String, Object> paramMap);
	/**
     * 功能描述：根据orderId查询DDOS告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Alarm>
     */
    public List<AlarmDDOS> getAlarmDdosByOrderId(Map<String, Object> paramMap);
    /**
     * 功能描述：根据orderId查询任务信息
     *       @time 2015-2-4
     * 返回值    ：List<Task>
     */
    public List<Task> getTaskByOrderId(Map<String, Object> paramMap);/**
     * 功能描述：根据taskId查询告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Task>
     */
    public List<Alarm> getAlarmByTaskId(Map<String, Object> param);
    /**
     * 功能描述：查询扫描总数
     *       @time 2015-3-12
     */
	public List<Alarm> findAll();
	 /**
     * 功能描述：根据组合查询条件查询告警
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	public List<Alarm> findAlarmByParam(Map<String, Object> paramMap);
	/**
     * 功能描述：根据组合查询条件查询告警--参数为空level为空，alarm_type不为空
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	public List<Alarm> findAlarmByParamAlarm_type(Map<String, Object> paramMap);
	/**
     * 功能描述：根据组合查询条件查询告警--参数为空level不为空，alarm_type不为空
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	public List<Alarm> findAlarmByParamAlarm_typeAndLevel(
			Map<String, Object> paramMap);
	public List<Alarm> findAlarmByOrderIdAndExecute_time(
			Map<String, Object> paramMap);
	
	/**
	 * 功能描述：根据任务id查询告警信息
	 * 参数描述：int taskid
	 * 返回值    ：List<Alarm>
	 */
	public List<AlarmDDOS> findAlarmByTaskId(int taskid);
    public List<AlarmDDOS> findEndAlarmByTaskId(int taskId);
	//敏感词统计折线图
	public List<Alarm> findSensitiveWordByOrderId(Map<String, Object> paramMap);
	public List<Alarm> findKeywordWarningByOrderId(String orderId);
	public List<Alarm> findRightByOrderIdAndUrl(Map<String, Object> map);
	public List<Alarm> findKeywordByUrlAndOrderId(Map<String, Object> map);
	//删除告警
    public void deleteAlarmByTaskId(Map<String, Object> paramMap);
    //根据orderid查询告警分类数,报表导出用
    public List findAlarmByOrderIdorGroupId(Map<String, Object> paramMap);
	//获取系统前5个小时
    String getHours5(int i);
    //根据时间获取告警列表
    List<Alarm> getAlarmNumByTime(String time);
    //根据服务获取告警TOP5
    List getAlarmTop5ByService(Map<String, Object> paramMap);
    
	public List<Alarm> getAlarmByAsset(Map<String, Object> paramAlarm);
	public List getAlarmByParam(Map<String, Object> paramMap);
	
	public List<AlarmBug> getAlarmBugCounts(Map<String, Object> paramMap);
	
	public List<AlarmBug> getBugMaxCounts(Map<String, Object> paramMap);
}
