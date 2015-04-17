package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.TaskWarn;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-4-14
 * 描        述：  告警业务层接口
 * 版        本：  1.0
 */
public interface ITaskWarnService {

	//根据订单id获取告警信息
	List<TaskWarn> findTaskWarnByOrderId(String orderId);
	//根据订单id获取告警次数
	TaskWarn findTaskWarnCountByOrderId(String orderId);

}
