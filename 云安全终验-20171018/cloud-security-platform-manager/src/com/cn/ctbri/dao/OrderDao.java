package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.Order;

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
    List findByOrderId(String orderId);
	/**
     * 功能描述：根据orderid查询IP
     *       @time 2015-2-2
     */
    List findIPByOrderId(String orderId);
    /**
     * 功能描述： 根据pageIndex和用户id查询记录
     *       @time 2015-3-4
     * 返回值    ：  Order
     */
    List findByUserIdAndPage(int id, int pageIndex,String state,String type);
    /**
     * 功能描述：组合查询订单追踪-分页
     *       @time 2015-1-15
     * 返回值    ：  List<Order>
     */
    List findByCombineOrderTrackByPage(Map<String, Object> paramMap);
    /**
     * 功能描述： 数据分析--订单统计分析
     *       @time 2015-3-10
     */
	List<DataAnalysis> findByCombineDataAnalysis(Map<String, Object> paramMap);
	/**
     * 功能描述：分页组合查询订单
     * 参数描述：String name
     *       @time 2015-3-13
     * 返回值    ：  List<Order>
     */
	List findByCombineByPage(Map<String, Object> paramMap);
	/**
     * 功能描述：根据订单id查询扫描次数
     * 参数描述：String orderId
     *       @time 2015-3-24
     * 返回值    ：  List<DataAnalysis>
     */
	int findScanCountByOrderId(String orderId);
	
	/**
	 * 功能描述：根据order_ip_Id查询订单
	 * 参数描述：无
	 *		 @time 2015-1-15
	 * 返回值    ：  List<Order>
	 */
	List<Order> findOrder(int order_ip_Id);
	
	/**
	 * 功能描述：修改有告警的订单
	 * 参数描述：Order order
	 */
	void update(Order order);
	/**
     * 功能描述：根据orderId查询正在执行的任务
     * 参数描述：String orderId
     */
    List findTaskRunning(String orderId);
    //判断订单id是否存在
    String getOrderById(String orderId,String type,int userId);
    
    //删除订单
    void deleteOrderById(String orderId);
    //查找订单
    Order findOrderById(String orderId);
    //查找用户下单最集中时间段top5
    List findOrderTimesTop5(Map<String, Object> paramMap);
    //根据行业查询下单用户数量
    List findUserCountByIndus(Map<String, Object> paramMap);
    //查询当前时间到前5小时内的数据统计
    List findOrderTimesLine(Map<String, Object> paramMap);
    //查询当前时间到前5小时内的服务数据统计
    List findOrderTimesPie(Map<String, Object> paramMap);
    //查询订单时间段分布统计
    List findOrderWithServiceId(Map<String, Object> paramMap);
    //回购率
	List<Map> findOrderWithServiceIdReBuy(Map<String, Object> paramMap);
	//购买数
	List<Map> findOrderWithServiceIdBuy(Map<String, Object> paramMap);
	/**
	 * 功能描述：根据orderId查询order信息(模糊匹配)
	 * @param id
	 * @return
	 */
	List<Order> findOrderByOrderIdMatch(Map<String, Object> paramMap);

	List findAPIInfoByOrderId(String orderId);

	int findAPICountByParam(Map<String, Object> paramMap);
	/**
	 * 
	 * 功能描述：统计订购单次服务的用户数与订购过长期服务的用户数 
	 * 参数描述：   
	 *@date:2016-8-5下午2:35:54
	 * 返回值    ：  订购单次服务的用户数与订购过长期服务的用户数 
	 * 异        常：
	 */
	List<Map<String,Object>> getServiceUserCount();
	/**
	 * 
	 * 功能描述： 统计长期和单次订单中各服务订单的对比
	 * 参数描述：   
	 *@date:2016-8-8上午10:37:34
	 * 返回值    ：  长期和单次订单中各服务订单的对比
	 * 异        常：
	 */
	List<Map<String, Object>> getServiceCount();
	//总订单
	List<Order> getOrder();
}
