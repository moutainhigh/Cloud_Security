package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Task;
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
}
