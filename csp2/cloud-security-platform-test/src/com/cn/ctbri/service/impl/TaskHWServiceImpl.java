package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.dao.TaskHWDao;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskHW;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-4-14
 * 描        述：  华为任务业务层实现类
 * 版        本：  1.0
 */
@Service
public class TaskHWServiceImpl implements ITaskHWService{

    @Autowired
    TaskHWDao taskhwDao;
    
    public int insert(TaskHW taskhw) {
        int taskId = taskhwDao.insert(taskhw);
        return taskId;
    }

    public List<TaskHW> findTaskhw(Map<String, Object> map) {
        return this.taskhwDao.findTask(map);
    }

    public void update(TaskHW t) {
        taskhwDao.update(t);
    }

    public OrderIP getIpByTaskId(int order_ip_id) {
        return taskhwDao.getIpByTaskId(order_ip_id);
    }

	public List<TaskHW> findAlarmbyTaskhw(Map<String, Object> map) {
		return this.taskhwDao.findAlarmbyTaskhw(map);
	}

	

}
