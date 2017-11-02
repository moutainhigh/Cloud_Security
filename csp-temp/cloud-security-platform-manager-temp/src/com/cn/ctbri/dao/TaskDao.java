package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Task;

import java.util.Map;

/**
 * 任务信息dao接口
 * @author googe
 *
 */
public interface TaskDao {

	/**
	 * 插入一条要执行的任务信息
	 * @param task 
	 * @return long 若成功主键ID，若不成功返回-1
	 */
	public int insert(Task task);

	/**
	 * 查询任务信息
	 * @param map
	 * @return 任务信息集合
	 */
	public List<Task> findTask(Map<String,Object> map);
	/**
	 * 功       能：  根据OrderAssetId查询任务
	 * 创建日期：  2015-1-27
	 * 版        本：  1.0
	 */
	public List<Task> findTaskByOrderAssetId(int orderAssetId);

	public Object findByOrderId(String orderId);
	public Task findProgressByOrderId(Map<String, Object> paramMap);

	public Task findBasicInfoByOrderId(Map<String, Object> paramMap);
	public List<Task> findScanTimeByOrderId(String orderId);

    public Task findNearlyTask(String orderId);

    public Task findTaskList(Map<String, Object> hisMap);

	public Task getNewStatus(Map<String, Object> paramMap);

    public void deleteTaskByOaId(String order_asset_Id);
    
    public int getCount(int engine);

	public void update(Map<String, Object> paramMap);
}
