package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskHW;

import java.util.Map;

/**
 * 华为任务信息dao接口
 * @author txr
 *
 */
public interface TaskHWDao {

	/**
	 * 插入一条要执行的任务信息
	 * @param task 
	 * @return long 若成功主键ID，若不成功返回-1
	 */
	public int insert(TaskHW taskhw);

    public List<TaskHW> findTask(Map<String, Object> map);

    public void update(TaskHW t);

    public OrderIP getIpByTaskId(int order_ip_id);

	public List<TaskHW> findAlarmbyTaskhw(Map<String, Object> map);

}
