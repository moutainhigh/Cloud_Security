package com.cn.ctbri.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
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
	
	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}  
	
	public List<Task> findTask(Map<String, Object> map) {
		String taskpage = String.valueOf(map.get("page"));
		if(taskpage == null || "".equals(taskpage)){
			taskpage = "20";
		}
		return getSqlSession().selectList(ns+"findTask", map);
	}
	
	public int insert(Task task) {
		return getSqlSession().insert(ns+"insert", task);
	}

	public void update(Task task) {
		getSqlSession().update(ns+"update", task);
	}

	/**
     * 根据资产获取订单类型
     * @param order_asset_Id
     */
    public OrderAsset getTypeByAssetId(int order_asset_Id) {
        return getSqlSession().selectOne(ns+"getTypeByAssetId", order_asset_Id);
    }

    /**
     * 下一次扫描时间
     * @param paramMap
     */
    public Date getNextScanTime(Map<String, Object> paramMap) {
        return (Date) getSqlSession().selectList(ns+"getNextScanTime", paramMap).get(0);
    }

    public void updateTask(Task task) {
        getSqlSession().update(ns+"updateTask", task);
    }

    public void insertTaskWarn(TaskWarn taskwarn) {
        getSqlSession().insert(ns+"insertTaskWarn", taskwarn);
    }

    public List<Task> getTaskStatus(Order order) {
        List list = this.getSqlSession().selectList(ns+"getTaskStatus", order);
        return list;
    }

    public List<Task> findDelTask(Map<String, Object> delmap) {
        String taskpage = String.valueOf(delmap.get("page"));
        if(taskpage == null || "".equals(taskpage)){
            taskpage = "20";
        }
        return getSqlSession().selectList(ns+"findDelTask", delmap);
    }

    public List<Task> findTaskByGroupId(String group_id) {
        List<Task> list = this.getSqlSession().selectList(ns+"findTaskByGroupId", group_id);
        return list;
    }

    public List<Task> findExpTask(Map<String, Object> map) {
        String taskpage = String.valueOf(map.get("page"));
        if(taskpage == null || "".equals(taskpage)){
            taskpage = "20";
        }
        return getSqlSession().selectList(ns+"findExpTask", map);
    }

    public List getArnhemTask() {
        List list = this.getSqlSession().selectList(ns+"getArnhemTask");
        return list;
    }

    public List getWebsocTask() {
        List list = this.getSqlSession().selectList(ns+"getWebsocTask");
        return list;
    }

	public int findDistrictIdByTaskId(String taskId) {
		return (Integer) getSqlSession().selectList(ns+"findDistrictIdByTaskId", taskId).get(0);
	}

	public List getArnhemTaskByEngine(String engine_addr) {
		List list = this.getSqlSession().selectList(ns+"getArnhemTaskByEngine",engine_addr);
        return list;
	}

	public List<Task> getDels() {
		List list = this.getSqlSession().selectList(ns+"getDels");
        return list;
	}

	public Task findByOrderTaskId(String orderTaskId) {
		Task t = this.getSqlSession().selectOne(ns+"findByOrderTaskId",orderTaskId);
        return t;
	}

	public List findTaskByOrderId(Map<String, Object> paramMap) {
		List t = this.getSqlSession().selectList(ns+"findTaskByOrderId",paramMap);
        return t;
	}

	public Task findTaskByTaskId(String taskId) {
		Task t = this.getSqlSession().selectOne(ns+"findTaskByTaskId",taskId);
        return t;
	}

	public List<Task> findRunningTask(String orderId) {
		List<Task> list = this.getSqlSession().selectList(ns+"findRunningTask", orderId);
        return list;
	}

	public int findissueCount(String orderId) {
		return getSqlSession().selectOne(ns+"findissueCount", orderId);
	}
	
	

}
