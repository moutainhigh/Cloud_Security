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
//		String taskpage = String.valueOf(map.get("page"));
//		if(taskpage == null || "".equals(taskpage)){
//			taskpage = "20";
//		}
		return getSqlSession().selectList(ns+"findTask", map);
	}
	
	public int insert(Task task) {
		return getSqlSession().insert(ns+"insert", task);
	}

	public void update(Task task) {
		getSqlSession().update(ns+"update", task);
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

    public List<Task> findDelTask(Map<String, Object> delmap) {
//        String taskpage = String.valueOf(delmap.get("page"));
//        if(taskpage == null || "".equals(taskpage)){
//            taskpage = "20";
//        }
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

	public Task findTaskById(int taskId) {
		return this.getSqlSession().selectOne(ns+"findTaskById",taskId);
	}

	public List<Task> findTaskByOrderTaskId(Task task) {
		return this.getSqlSession().selectList(ns+"findTaskByOrderTaskId",task);
	}

	public List<Task> findTaskByOrderId(String orderId) {
		return this.getSqlSession().selectList(ns+"findTaskByOrderId",orderId);
	}

	public Task findTaskByTaskObj(Task task) {
		return this.getSqlSession().selectOne(ns+"findTaskByTaskObj",task);
	}

	/* (non-Javadoc)
	 * @see com.cn.ctbri.dao.TaskDao#countTaskByEngine(int)
	 */
	public int countTaskByEngine(int engineId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(ns+"countTaskByEngine",engineId);
	}
	
	

}
