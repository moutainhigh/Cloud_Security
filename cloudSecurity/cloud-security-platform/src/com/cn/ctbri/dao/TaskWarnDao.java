package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.TaskWarn;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-4-14
 * 描        述：  告警数据访问层接口类
 * 版        本：  1.0
 */
public interface TaskWarnDao {

	List<TaskWarn> findTaskWarnByOrderId(String orderId);

	TaskWarn findTaskWarnCountByOrderId(String orderId);

	List<TaskWarn> findUseableByOrderId(String orderId);

	List<TaskWarn> findWarnUrlByOrderId(Map<String, Object> m);

	List<TaskWarn> findWarnByOrderIdAndUrl(Map<String, Object> map);

}
