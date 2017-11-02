package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.Alarm;
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
     * 功能描述：根据orderid查询告警信息
     * 参数描述：String orderId 查询参数
     * @return 
     *       @time 2015-01-07
     * 返回值    ：  List<Alarm> 报警对象集合
     */
    public List<Alarm> findAlarmByOrderId(Map<String, Object> paramMap) {
        return getSqlSession().selectList(ns+"findAlarmByOrderId", paramMap);
    }
	public void updateDistrict(Map<String, Object> disMap) {
		getSqlSession().update(ns+"updateDistrict", disMap);
	}
	public List<Alarm> findAlarmBygroupId(Map<String, Object> paramMap) {
		return getSqlSession().selectList(ns+"findAlarmBygroupId", paramMap);
	}
	public String findAdvice(String name) {
		List advices = getSqlSession().selectList(ns+"findAdvice", name);
		if(advices!=null&&advices.size()>0){
			return (String) advices.get(0);
		}else{
			return null;
		}
	}
	public void update(Alarm alarm) {
		getSqlSession().update(ns+"updateAlarm", alarm);
	}
	public List<Alarm> findAlarmByTaskId(String taskId) {
		return getSqlSession().selectList(ns+"findAlarmByTaskId", taskId);
	}
	//根据orderid查询告警分类数,报表导出用
    public List findAlarmByOrderIdorGroupId(Map<String, Object> paramMap) {
        return getSqlSession().selectList(ns+"findAlarmByOrderIdorGroupId", paramMap);
    }

}
