package com.cn.ctbri.service;

import com.cn.ctbri.entity.OrderList;


/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  订单业务层接口
 * 版        本：  1.0
 */
public interface IOrderListService {

	//插入购物订单
	void insert(OrderList ol);
	
}
