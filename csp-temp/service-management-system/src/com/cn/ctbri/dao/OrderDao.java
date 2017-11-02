package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述：  用户数据访问层接口类
 * 版        本：  1.0
 */
public interface OrderDao {
	/**
	 * 功能描述：查询所有订单
	 * 参数描述：无
	 *		 @time 2015-1-15
	 * 返回值    ：  List<Order>
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
    List<Order> getOrderByUserId(int userId);
    /**
     * 功能描述：组合查询订单追踪
     *       @time 2015-1-15
     * 返回值    ：  List<Order>
     */
    List findByCombineOrderTrack(Map<String, Object> paramMap);
    
    /**
     * 功能描述： 根据订单Id查询记录
     *       @time 2015-2-2
     * 返回值    ：  Order
     */
    List<Order> findByOrderId(String orderId);
    
    /**
     * 功能描述： 根据任务查询订单
     *       @time 2015-2-2
     * 返回值    ：  Order
     */
    List<Order> findOrderByTask(Task task);
    
    /**
     * 功能描述： 更新订单信息
     *       @time 2015-2-2
     * 返回值    ：  无
     */
	void update(Order o);
	//查找联系人
    List<Linkman> findLinkmanById(int contactId);
    /**
     * 功能描述：保存订单
     *       @time 2015-11-10
     */
	void save(Order order);
	
	Order findOrderByOrderId(String orderId);
	
	void delete(String orderId);
	
	//add by la 2017-6-9
	//List<Order> findByAPIKey(String APIKey);
	
}
