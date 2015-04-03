package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.Task;
/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-07
 * 描        述：  报警信息Dao接口实现类
 * 版        本：  1.0
 */
@Repository
@Transactional
public class AlarmDaoImpl extends DaoCommon implements AlarmDao {
	
	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.AlarmMapper.";		
	/**
	 * 功能描述：插入报警日志
	 * 参数描述：Alarm alarm 报警日志对象
	 *		 @time 2015-01-07
	 * 返回值    ：  long 若成功主键ID，若不成功返回-1
	 */
	public long insert(Alarm alarm) {
		return getSqlSession().insert(ns+"insert", alarm);
	}
	/**
	 * 功能描述：查询报警日志
	 * 参数描述：Map<String,Object> map 查询参数
	 *		 @time 2015-01-07
	 * 返回值    ：  List<Alarm> 报警日志对象集合
	 */
	public List<Alarm> findAlarm(Map<String, Object> map) {
		return getSqlSession().selectList(ns+"findAlarm", map);
	}
	/**
	 * 功能描述：根据用户id查询告警信息
	 * 参数描述：int id
	 *       @time 2015-1-30
	 * 返回值    ：List<Alarm>
	 */
	public List<Alarm> findAlarmByUserId(int id) {
		return getSqlSession().selectList(ns+"findAlarmByUserId", id);
	}
	/**
     * 功能描述：根据orderId查询告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Alarm>
     */
    public List<Alarm> getAlarmByOrderId(Map<String, Object> paramMap) {
        return getSqlSession().selectList(ns+"findAlarmByOrderId", paramMap);
    }
	/**
     * 功能描述：根据orderId查询DDOS告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Alarm>
     */
    public List<AlarmDDOS> getAlarmDdosByOrderId(Map<String, Object> paramMap) {
        return getSqlSession().selectList(ns+"findAlarmDdosByOrderId", paramMap);
    }
    /**
     * 功能描述：根据orderId查询任务信息
     *       @time 2015-2-4
     * 返回值    ：List<Task>
     */
    public List<Task> getTaskByOrderId(Map<String, Object> paramMap) {
        return getSqlSession().selectList(ns+"findTaskByOrderId", paramMap);
    }
    /**
     * 功能描述：根据taskId查询告警信息
     *       @time 2015-2-4
     * 返回值    ：List<Task>
     */
    public List<Alarm> getAlarmByTaskId(Map<String, Object> param) {
        return getSqlSession().selectList(ns+"findAlarmByTaskId", param);
    }
    /**
     * 功能描述：查询扫描总数
     *       @time 2015-3-12
     */
	public List<Alarm> findAll() {
		return getSqlSession().selectList(ns+"findAll");
	}
	 /**
     * 功能描述：根据组合查询条件查询告警
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	public List<Alarm> findAlarmByParam(Map<String, Object> paramMap) {
		return getSqlSession().selectList(ns+"findAlarmByParam",paramMap);
	}
	/**
     * 功能描述：根据组合查询条件查询告警--参数为空level为空，alarm_type不为空
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	public List<Alarm> findAlarmByParamAlarm_type(Map<String, Object> paramMap) {
		return getSqlSession().selectList(ns+"findAlarmByParamAlarm_type",paramMap);
	}
	/**
     * 功能描述：根据组合查询条件查询告警--参数为空level不为空，alarm_type不为空
     *       @time 2015-3-12
     *       返回值    ：List<Alarm>
     */
	public List<Alarm> findAlarmByParamAlarm_typeAndLevel(
			Map<String, Object> paramMap) {
		return getSqlSession().selectList(ns+"findAlarmByParamAlarm_typeAndLevel",paramMap);
	}
}
