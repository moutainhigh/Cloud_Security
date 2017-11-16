package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.MonitorTaskDao;
import com.cn.ctbri.dao.OrderDao;
import com.cn.ctbri.entity.MonitorTask;
import com.cn.ctbri.service.IMonitorTaskService;
@Service
public class IMonitorTaskServiceImpl implements IMonitorTaskService {
	@Autowired
	MonitorTaskDao taskDao;
	
	@Override
	public List findAllTask() {
		// TODO Auto-generated method stub
		List taskList = taskDao.findAllTask();
		return taskList;
	}

	@Override
	public List findTaskListByTaskName(String taskName) {
		// TODO Auto-generated method stub
		List taskList = taskDao.findTaskListByTaskName(taskName);
		return taskList;
	}

	@Override
	public List findTaskListByTargetUrl(String targetUrl) {
		// TODO Auto-generated method stub
		List taskList = taskDao.findTaskListByTargetUrl(targetUrl);
		return taskList;
	}

	@Override
	public List findTaskListById(int id) {
		// TODO Auto-generated method stub
		List taskList = taskDao.findTaskListById(id);
		return taskList;
	}

	@Override
	public List findTaskListByTaskNameAccurate(String taskName) {
		// TODO Auto-generated method stub
		List taskList = taskDao.findTaskListByTaskNameAccurate(taskName);
		return taskList;
	}

	@Override
	public void insertMonitorTask(MonitorTask task) {
		// TODO Auto-generated method stub
		taskDao.insertMonitorTask(task);
	}

}
