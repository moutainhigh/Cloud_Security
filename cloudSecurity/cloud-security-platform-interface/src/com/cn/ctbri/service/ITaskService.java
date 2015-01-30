package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Task;

public interface ITaskService {
	List<Task> findTask(Map<String, Object> paramMap);
}
