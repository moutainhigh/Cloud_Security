package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.TaskWarn;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-4-14
 * 描        述：  告警业务层接口
 * 版        本：  1.0
 */
public interface ITaskWarnService {

	//根据订单id获取告警信息
	List<TaskWarn> findTaskWarnByOrderId(Map<String, Object> paramMap);
	//根据订单id获取告警次数
	TaskWarn findTaskWarnCountByOrderId(String orderId);
	//可用率统计
	List<TaskWarn> findUseableByOrderId(Map<String, Object> paramMap);
	//查找告警的url资产
	List<TaskWarn> findWarnUrlByOrderId(Map<String, Object> m);
	//根据url和orderID查找告警
	List<TaskWarn> findWarnByOrderIdAndUrl(Map<String, Object> map);
	//删除可用性告警
    void deleteTaskWarnByTaskId(Map<String, Object> paramMap);

}
