package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.MonitorTask;

public interface MonitorTaskDao {

	/**
	 * 功能描述：返回所有监控任务
	 * 
	 * 返回值    ：  List
	 */
	List findAllTask();
	/**
	 * 功能描述：根据taskname 查询任务  模糊查找
	 * 参数描述：
	 *		 @time 2017－11－13
	 * 返回值    ：  List<Order>
	 */

	List findTaskListByTaskName(String taskName);
	
	/**
	 * 功能描述：根据taskname 查询任务  精确查找
	 * 参数描述：
	 *		 @time 2017－11－13
	 * 返回值    ：  List<Order>
	 */

	List findTaskListByTaskNameAccurate(String taskName);
	/**
	 * 功能描述：根据targetUrl查询任务列表
	 * 
	 * 返回值    ：  List
	 */
	List findTaskListByTargetUrl(String targetUrl);
	/**
	 * 功能描述：根据任务 id查询任务列表
	 * 
	 * 返回值    ：  List
	 */
	List findTaskListById(int id);
	
	void insertMonitorTask(MonitorTask task);
	
	
}
