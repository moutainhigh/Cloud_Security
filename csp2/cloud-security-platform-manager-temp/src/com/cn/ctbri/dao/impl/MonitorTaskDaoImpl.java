package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.MonitorTaskDao;
import com.cn.ctbri.entity.MonitorTask;
@Repository
public class MonitorTaskDaoImpl extends DaoCommon implements MonitorTaskDao {

	private String ns = "com.cn.ctbri.entity.MonitorTask.";
	
	
	public List findAllTask() {
		// TODO Auto-generated method stub
		List list = this.getSqlSession().selectList(ns + "findAllTask");
		
		return list;
		//return null;
	}

	
	public List findTaskListByTaskName(String taskname) {
		// TODO Auto-generated method stub
		List list = this.getSqlSession().selectList(ns + "findTaskListByTaskName",taskname);
		
		return list;
	}

	
	public List findTaskListByTaskNameAccurate(String taskName) {
		// TODO Auto-generated method stub
		List list = this.getSqlSession().selectList(ns + "findTaskListByTaskNameAccurate",taskName);
		
		return list;
	}
	
	
	public List findTaskListByTargetUrl(String targeturl) {
		// TODO Auto-generated method stub
		List list = this.getSqlSession().selectList(ns + "findTaskListByTargetUrl",targeturl);
		
		return list;
	}


	
	public List findTaskListById(int id) {
		// TODO Auto-generated method stub
		List list = this.getSqlSession().selectList(ns + "findTaskListById",id);
		
		return list;
	}

	
	public void insertMonitorTask(MonitorTask task) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(ns + "insertMonitorTask", task);
	}

	

}
