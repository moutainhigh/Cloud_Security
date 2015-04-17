package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.TaskWarnDao;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.ITaskWarnService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-4-14
 * 描        述：  告警业务层实现类
 * 版        本：  1.0
 */
@Service
public class TaskWarnServiceImpl implements ITaskWarnService{

	@Autowired
	TaskWarnDao taskWarnDao;

	public List<TaskWarn> findTaskWarnByOrderId(String orderId) {
		return taskWarnDao.findTaskWarnByOrderId(orderId);
	}

	public TaskWarn findTaskWarnCountByOrderId(String orderId) {
		return taskWarnDao.findTaskWarnCountByOrderId(orderId);
	}
	
}
