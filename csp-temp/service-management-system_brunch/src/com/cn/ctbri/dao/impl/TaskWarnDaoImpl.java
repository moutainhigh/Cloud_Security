package com.cn.ctbri.dao.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.TaskWarnDao;
import com.cn.ctbri.entity.TaskWarn;
/**
 * 创 建 人  ： 邓元元
 * 创建日期：  2015-04-14
 * 描        述：  告警数据访问层实现类
 * 版        本：  1.0
 */
@Repository
@Transactional
public class TaskWarnDaoImpl extends DaoCommon implements TaskWarnDao {
	
	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.TaskWarnMapper.";
		
	//根据订单id获取告警信息
	public List<TaskWarn> findTaskWarnByOrderId(Map<String, Object> hashmap) {
		return getSqlSession().selectList(ns+"findTaskWarnByOrderId", hashmap);
	}
	//根据订单id获取告警次数
	public TaskWarn findTaskWarnCountByOrderId(String orderId) {
		return getSqlSession().selectOne(ns+"findTaskWarnCountByOrderId", orderId);
	}
	//可用率统计
	public List<TaskWarn> findUseableByOrderId(String orderId) {
		return getSqlSession().selectList(ns+"findUseableByOrderId", orderId);
	}
	//根据group_id查询
	public List<TaskWarn> findTaskWarnByGroupId(String groupId) {
		return getSqlSession().selectList(ns+"findTaskWarnByGroupId", groupId);
	}
	public List<TaskWarn> findTaskWarnBytaskId(int taskId) {
		return getSqlSession().selectList(ns+"findTaskWarnByTaskId", taskId);
	}
	
}
