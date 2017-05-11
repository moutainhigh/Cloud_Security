package com.cn.ctbri.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
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
	 * 按条件查询任务信息集合
	 * @param map
	 * @return 任务信息集合
	 */
	public List<Task> findTask(Map<String,Object> map);
	
	/**
	 * 更新任务信息
	 * @param task
	 */
	public void update(Task task);

	/**
     * 根据资产获取订单类型
     * @param order_asset_Id
     */
    public OrderAsset getTypeByAssetId(int order_asset_Id);

    /**
     * 下一次扫描时间
     * @param paramMap
     */
    public Date getNextScanTime(Map<String, Object> paramMap);

    public void updateTask(Task task);

    public void insertTaskWarn(TaskWarn taskwarn);

    public List<Task> getTaskStatus(Order order);

    public List<Task> findDelTask(Map<String, Object> delmap);

    public List<Task> findTaskByGroupId(String group_id);

    public List<Task> findExpTask(Map<String, Object> map);

    public List getArnhemTask();

    public List getWebsocTask();

	public int findDistrictIdByTaskId(String taskId);

	public List getArnhemTaskByEngine(String engine_addr);

	public List<Task> getDels();

	public Task findByOrderTaskId(String orderTaskId);

	public List findTaskByOrderId(Map<String, Object> paramMap);

	public Task findTaskByTaskId(String taskId);

	public List<Task> findRunningTask(String orderId);

	public int findissueCount(String orderId);
}
