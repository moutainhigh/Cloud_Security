package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.APICount;
import com.cn.ctbri.entity.OrderAPI;


/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  用户数据访问层接口类
 * 版        本：  1.0
 */
public interface OrderAPIDao {

	void insert(OrderAPI oAPI);

	void insertOrUpdateCount(APICount count);

	List<OrderAPI> findOrderAPIByType(Map<String, Object> paramMap);
	//删除订单
	void deleteOrderAPI(String orderId,int userId);
}
