package com.cn.ctbri.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
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
     * 根据资产获取订单类型
     * @param order_asset_Id
     */
    OrderAsset getTypeByAssetId(int order_asset_Id);

    /**
     * 下一次扫描时间
     * @param paramMap
     */
    Date getNextScanTime(Map<String, Object> paramMap);

    void updateTask(Task task);

    void insertTaskWarn(TaskWarn taskwarn);

    List<Task> getTaskStatus(Order order);

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

	List getArnhemTaskByEngine(String engine_addr);

	List<Task> getDels();

	//二期新增 2015-12-1 by tangxr
	Task findByOrderTaskId(String orderTaskId);

	List findTaskByOrderId(Map<String, Object> paramMap);

	Task findTaskByTaskId(String taskId);

	//查找运行的task
	List<Task> findRunningTask(String orderId);

	int findissueCount(String orderId);
}
