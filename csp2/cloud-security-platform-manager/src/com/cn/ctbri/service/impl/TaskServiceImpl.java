package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.ITaskService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-27
 * 描        述：  任务业务层实现类
 * 版        本：  1.0
 */
@Service
public class TaskServiceImpl implements ITaskService{

	@Autowired
	TaskDao taskDao;
	/**
	 * 功       能：  根据OrderAssetId查询任务
	 * 创建日期：  2015-1-27
	 * 版        本：  1.0
	 */

	public List<Task> findTaskByOrderAssetId(int orderAssetId) {
		List<Task> list = taskDao.findTaskByOrderAssetId(orderAssetId);
		return list;
	}
	public Object findByOrderId(String orderId) {
		Object obj = taskDao.findByOrderId(orderId);
		return obj;
	}
    public int insert(Task task) {
        int taskId = taskDao.insert(task);
        return taskId;
    }
	public Task findProgressByOrderId(Map<String, Object> paramMap) {
		return taskDao.findProgressByOrderId(paramMap);
	}
	public Task findBasicInfoByOrderId(Map<String, Object> paramMap) {
		return taskDao.findBasicInfoByOrderId(paramMap);
	}
	public List<Task> findScanTimeByOrderId(String orderId) {
		return taskDao.findScanTimeByOrderId(orderId);
	}
    public Task findNearlyTask(String orderId) {
        return taskDao.findNearlyTask(orderId);
    }
    public Task findTaskList(Map<String, Object> hisMap) {
        return taskDao.findTaskList(hisMap);
    }
	public Task getNewStatus(Map<String, Object> paramMap) {
		return taskDao.getNewStatus(paramMap);
	}
    public void deleteTaskByOaId(String order_asset_Id) {
        taskDao.deleteTaskByOaId(order_asset_Id);
    }
	public int getCount(int engine) {
		return taskDao.getCount(engine);
	}
	public void update(Map<String, Object> paramMap) {
		taskDao.update(paramMap);
		
	}

}
