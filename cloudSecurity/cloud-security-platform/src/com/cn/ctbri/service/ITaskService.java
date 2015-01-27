package com.cn.ctbri.service;

import java.util.List;

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

}
