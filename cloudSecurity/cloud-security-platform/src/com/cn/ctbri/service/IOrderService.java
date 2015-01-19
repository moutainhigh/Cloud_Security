package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.User;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述：  订单业务层接口
 * 版        本：  1.0
 */
public interface IOrderService {
	/**
	 * 功能描述：根据用户名查询订单
	 * 参数描述：int id
	 *		 @time 2015-1-15
	 * 返回值    ：  List
	 */
	List findByUserId(int id);
	/**
	 * 功能描述：组合查询订单
	 * 参数描述：String name
	 *		 @time 2015-1-15
	 * 返回值    ：  List<Order>
	 */
	List findByCombine(Map<String, Object> paramMap);


}
