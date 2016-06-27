package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.OrderList;


/**
 * 创 建 人  ： tangxr
 * 创建日期：  2016-3-30
 * 描        述：  用户数据访问层接口类
 * 版        本：  1.0
 */
public interface OrderListDao {

	void insert(OrderList ol);
	
	OrderList findById(String id);
	
	/**
     * 功能描述： 根据用户ID查询所有消费记录
     * @param userId
     * @param  offset  开始记录的个数
     * @param pageSize  每页记录条数
     *       @time 2016-5-19
     */
	List<OrderList> findAllPayRecord(int userId);
	
	void update(OrderList ol);
	
	/**
     * 功能描述： 消费记录分页
     * @param userId
     * @param  i  开始记录的个数
     * @param pageSize  每页记录条数
     *       @time 2016-5-19
     */
	List<OrderList> queryPayRecordByPage(int userId, int offset, int pageSize);
	
	/**
     * 功能描述： 根据订单编号更改领取安全币状态
     */
	void updateBalanceFlag(OrderList ol);
	
	void updateOrderListId(String oldId, String newId);
	
}
