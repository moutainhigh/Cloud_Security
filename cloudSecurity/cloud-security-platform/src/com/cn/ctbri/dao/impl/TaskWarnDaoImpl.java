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
	public List<TaskWarn> findTaskWarnByOrderId(Map<String, Object> paramMap) {
		return getSqlSession().selectList(ns+"findTaskWarnByOrderId", paramMap);
	}
	//根据订单id获取告警次数
	public TaskWarn findTaskWarnCountByOrderId(String orderId) {
		return getSqlSession().selectOne(ns+"findTaskWarnCountByOrderId", orderId);
	}
	//可用率统计
	public List<TaskWarn> findUseableByOrderId(Map<String, Object> paramMap) {
		return getSqlSession().selectList(ns+"findUseableByOrderId", paramMap);
	}
	//查找告警的url资产
	public List<TaskWarn> findWarnUrlByOrderId(Map<String, Object> m) {
		return getSqlSession().selectList(ns+"findWarnUrlByOrderId", m);
	}
	//根据url和orderID查找告警
	public List<TaskWarn> findWarnByOrderIdAndUrl(Map<String, Object> map) {
		return getSqlSession().selectList(ns+"findWarnByOrderIdAndUrl", map);
	}
	
    public void deleteTaskWarnByTaskId(Map<String, Object> paramMap) {
        this.getSqlSession().delete(ns + "deleteTaskWarnByTaskId",paramMap);
    }		
	
}
