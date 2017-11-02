package com.cn.ctbri.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;

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

    int insert(Task task);

    /**
     * 下一次扫描时间
     * @param paramMap
     */
    Date getNextScanTime(Map<String, Object> paramMap);

    void updateTask(Task task);

    void insertTaskWarn(TaskWarn taskwarn);

    //定时删除的任务
    List<Task> findDelTask(Map<String, Object> delmap);
    //根据创宇group_id查询任务
    List<Task> findTaskByGroupId(String group_id);

    //查找引擎异常为下发的任务
    List<Task> findExpTask(Map<String, Object> map);

    List getArnhemTask();

    List getWebsocTask();
    //根据taskId查询地区
	int findDistrictIdByTaskId(String taskId);
	//根据ID查询task
	Task findTaskById(int taskId);
	//add by tangxr 2015-11-20
	//根据订单Id和url查询任务
	List<Task> findTaskByOrderTaskId(Task task);

	List<Task> findTaskByOrderId(String orderId);

	Task findTaskByTaskObj(Task task);
	int countTaskByEngine(int engineId);
}
