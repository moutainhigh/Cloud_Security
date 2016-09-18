package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Task;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-27
 * 描        述：  任务业务层接口
 * 版        本：  1.0
 */
public interface ITaskService {

	/**
	 * 功       能：  根据OrderAssetId查询任务
	 * 创建日期：  2015-1-27
	 * 版        本：  1.0
	 */
	List<Task> findTaskByOrderAssetId(int orderAssetId);

	Object findByOrderId(String orderId);

    int insert(Task task);

	Task findProgressByOrderId(Map<String, Object> paramMap);

	Task findBasicInfoByOrderId(Map<String, Object> paramMap);

	List<Task> findScanTimeByOrderId(String orderId);

    Task findNearlyTask(String orderId);

    Task findTaskList(Map<String, Object> hisMap);

	Task getNewStatus(Map<String, Object> paramMap);

	//根据订单资产id,删除任务
    void deleteTaskByOaId(String order_asset_Id);
    
    public int getCount(int engine);

	void update(Map<String, Object> paramMap);

}
