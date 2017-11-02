package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.pager.PageBean;


/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  订单业务层接口
 * 版        本：  1.0
 */
public interface IOrderListService {

	//插入购物订单
	void insert(OrderList ol);
	
	//查询购物订单
	OrderList findById(String id);
	
	void update(OrderList ol);
	
	/**
     * 功能描述：消费记录-分页
     *       @time 2016-5-19
     * 返回值    ：  List<Order>
     */
	PageBean<OrderList> queryPayRecordByPage(int userId, int pageCode);
	
	/**
     * 功能描述： 根据订单编号更改领取安全币状态
     */
	void updateBalanceFlag(OrderList ol);
	/**
     * 功能描述： 查询支付订单
     */
	List<OrderList> getPayRecord(Map<String, Object> param);
	
	void updateOrderListId(String oldId, String newId);
	List getOrderInfoById(String orderId);
}
