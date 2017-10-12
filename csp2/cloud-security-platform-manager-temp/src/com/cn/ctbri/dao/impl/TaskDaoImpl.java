package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Task;
/**
 * 任务信息dao实现类
 * @author googe
 *
 */
@Repository
@Transactional
public class TaskDaoImpl extends DaoCommon implements TaskDao {
	
	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.TaskMapper.";		
	
	public List<Task> findTask(Map<String, Object> map) {
		return getSqlSession().selectList(ns+"findTask", map);
	}
	
	public int insert(Task task) {
		return getSqlSession().insert(ns+"insert", task);
	}


	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}  
	

	public List<Task> findTaskByOrderId(String orderId) {
		List<Task> list = this.getSqlSession().selectList(ns+"findTaskByOrderId",orderId);
		return list;
	}

	public List<Task> findTaskByOrderAssetId(int orderAssetId) {
		List<Task> list = this.getSqlSession().selectList(ns+"findTaskByOrderAssetId",orderAssetId);
		return list;
	}

	public Object findByOrderId(String orderId) {
		return this.getSqlSession().selectOne(ns+"findByOrderId",orderId);
	}

	public Task findProgressByOrderId(Map<String, Object> paramMap) {
		return this.getSqlSession().selectOne(ns+"findProgressByOrderId",paramMap);
	}

	public Task findBasicInfoByOrderId(Map<String, Object> paramMap) {
//		  int count=this.getSqlSession().selectOne(ns+"getCountByOrderId",orderId);  
//		  Map<String, Object> paramMap = new HashMap<String, Object>();
//		  paramMap.put("orderId", orderId);
//	      paramMap.put("count", count);
		  
		return this.getSqlSession().selectOne(ns+"findBasicInfoByOrderId",paramMap);
	}

	public List<Task> findScanTimeByOrderId(String orderId) {
		return this.getSqlSession().selectList(ns+"findScanTimeByOrderId",orderId);
	}

    public Task findNearlyTask(String orderId) {
        return this.getSqlSession().selectOne(ns+"findNearlyTask",orderId);
    }

    public Task findTaskList(Map<String, Object> hisMap) {
        return this.getSqlSession().selectOne(ns+"findTaskList",hisMap);
    }

	public Task getNewStatus(Map<String, Object> paramMap) {
		return this.getSqlSession().selectOne(ns+"getNewStatus",paramMap);
	}

    public void deleteTaskByOaId(String order_asset_Id) {
        this.getSqlSession().delete(ns + "deleteTaskByOaId",order_asset_Id);
    }

	public int getCount(int engine) {
		return this.getSqlSession().selectOne(ns + "countTestRunning",engine);
	}
	
	public void update(Map<String, Object> paramMap) {
		this.getSqlSession().update(ns+"update", paramMap);
		
	}
	
}
