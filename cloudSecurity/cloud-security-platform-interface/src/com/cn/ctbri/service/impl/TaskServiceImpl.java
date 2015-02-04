package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.ITaskService;
@Service
public class TaskServiceImpl implements ITaskService{

	@Autowired
	TaskDao taskDao;

	public List<Task> findTask(Map<String, Object> paramMap) {
		return this.taskDao.findTask(paramMap);
	}

	public void update(Task task) {
		this.taskDao.update(task);
	}
	
}
