package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Alarm;

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
     * 功能描述：根据orderid查询告警信息
     * 参数描述：String orderId 查询参数
	 * @return 
     *       @time 2015-01-07
     * 返回值    ：  List<Alarm> 报警对象集合
     */
    public List<Alarm> findAlarmByOrderId(Map<String, Object> paramMap);
    
	public void updateDistrict(Map<String, Object> disMap);
	
	public List<Alarm> findAlarmBygroupId(Map<String, Object> paramMap);
	
	public String findAdvice(String name);
	
	public void update(Alarm alarm);
	
	public List<Alarm> findAlarmByTaskId(String taskId);
	
	public List findAlarmByOrderIdorGroupId(Map<String, Object> paramMap);
}
