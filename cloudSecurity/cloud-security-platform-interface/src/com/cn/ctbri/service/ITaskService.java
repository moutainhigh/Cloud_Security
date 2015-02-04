package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Task;

public interface ITaskService {
	/**
	 * 根据条件查询任务信息
	 * @param paramMap
	 * @return
	 */
	List<Task> findTask(Map<String, Object> paramMap);
	
	/**
	 * 更新任务信息
	 * @param task
	 */
	void update(Task task);
}
