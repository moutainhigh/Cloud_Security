package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;

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
	
	/**
     * 功能描述：根据用户查询所有记录
     * 参数描述：int userId
     *       @time 2015-1-21
     * 返回值    ：  List
     */
    List<Order> findOrderByUserId(int userId);
    /**
     * 功能描述：组合查询订单追踪
     *       @time 2015-1-15
     * 返回值    ：  List<Order>
     */
    List findByCombineOrderTrack(Map<String, Object> paramMap);
    
    List findByUserId(String orderId);
    /**
     * 功能描述： 根据订单Id查询记录
     *       @time 2015-2-2
     * 返回值    ：  Order
     */
    List<Order> findByOrderId(String orderId);

    /**
     * 功能描述： 根据任务查询订单
     *       @time 2015-2-9
     * 返回值    ：  Order
     */
    List<Order> findOrderByTask(Task task);
    
	void update(Order o);
	//查找联系人
    List<Linkman> findLinkmanById(int contactId);
    
    /**
     * 功能描述：保存订单
     *       @time 2015-11-10
     */
    void insertOrder(Order order);
    
	Order findOrderByOrderId(String orderId);
	//删除订单
	void deleteOrderById(String orderId);
}
