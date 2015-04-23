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
}
