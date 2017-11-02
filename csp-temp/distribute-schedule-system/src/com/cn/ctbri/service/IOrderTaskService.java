package com.cn.ctbri.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-11-10
 * 描        述：  订单任务业务层接口
 * 版        本：  1.0
 */
public interface IOrderTaskService {
	
    /**
     * 功能描述：保存订单
     *       @time 2015-11-10
     */
    void insertOrderTask(OrderTask orderTask);
    
    /**
	 * 根据条件查询任务信息
	 * @param paramMap
	 * @return
	 */
    List<OrderTask> findOrderTask(Map<String, Object> paramMap);
	
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

	List getArnhemTaskByEngine(String engine_addr);

	List<Task> getDels();
}
