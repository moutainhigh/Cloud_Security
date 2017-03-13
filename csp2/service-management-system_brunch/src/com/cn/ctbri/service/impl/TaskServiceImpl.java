package com.cn.ctbri.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
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

    public int insert(Task task) {
        int taskId = taskDao.insert(task);
        return taskId;
    }

    /**
     * 根据资产获取订单类型
     * @param order_asset_Id
     */
    public OrderAsset getTypeByAssetId(int order_asset_Id) {
        return taskDao.getTypeByAssetId(order_asset_Id);
    }

    /**
     * 下一次扫描时间
     * @param paramMap
     */
    public Date getNextScanTime(Map<String, Object> paramMap) {
        return taskDao.getNextScanTime(paramMap);
    }

    public void updateTask(Task task) {
        this.taskDao.updateTask(task);
    }

    public void insertTaskWarn(TaskWarn taskwarn) {
        this.taskDao.insertTaskWarn(taskwarn);
    }

    public List<Task> getTaskStatus(Order order) {
        return taskDao.getTaskStatus(order);
    }

    public List<Task> findDelTask(Map<String, Object> delmap) {
        return this.taskDao.findDelTask(delmap);
    }

    public List<Task> findTaskByGroupId(String group_id) {
        return this.taskDao.findTaskByGroupId(group_id);
    }

    public List<Task> findExpTask(Map<String, Object> map) {
        return this.taskDao.findExpTask(map);
    }

    public List getArnhemTask() {
        return this.taskDao.getArnhemTask();
    }

    public List getWebsocTask() {
        return this.taskDao.getWebsocTask();
    }

	public int findDistrictIdByTaskId(String taskId) {
		return this.taskDao.findDistrictIdByTaskId(taskId);
	}

	public List getArnhemTaskByEngine(String engine_addr) {
		return this.taskDao.getArnhemTaskByEngine(engine_addr);
	}

	public List<Task> getDels() {
		return this.taskDao.getDels();
	}

	public Task findByOrderTaskId(String orderTaskId) {
		return this.taskDao.findByOrderTaskId(orderTaskId);
	}

	public List findTaskByOrderId(Map<String, Object> paramMap) {
		return this.taskDao.findTaskByOrderId(paramMap);
	}

	public Task findTaskByTaskId(String taskId) {
		return this.taskDao.findTaskByTaskId(taskId);
	}

	public List<Task> findRunningTask(String orderId) {
		return this.taskDao.findRunningTask(orderId);
	}

	public int findissueCount(String orderId) {
		return this.taskDao.findissueCount(orderId);
	}
	
}
