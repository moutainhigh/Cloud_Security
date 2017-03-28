package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;

/**
 * 创 建 人 ： 邓元元 创建日期： 2015-1-14 描 述： 订单业务层接口 版 本： 1.0
 */
public interface IOrderService {
	/**
	 * 功能描述：根据用户名和serviceId 查询订单 
	 * 
	 * @time 2017-3-16 返回值 ： List
	 */
	List findOrderByUserIdAndServiceId(int userId,int serviceId);
	
	/**
	 * 功能描述：根据用户名查找已经支付过的最新的系统安全帮订单
	 * 
	 * @time 2017-3-16 返回值 ： List
	 */
	List findPaidSysOrderByUserId(int userId);
	
	
	/**
	 * 功能描述：根据用户名查询订单 参数描述：int id
	 * 
	 * @time 2015-1-15 返回值 ： List
	 */
	List findByUserId(int id);

	/**
	 * 功能描述：组合查询订单 参数描述：String name
	 * 
	 * @time 2015-1-15 返回值 ： List<Order>
	 */
	List findByCombine(Map<String, Object> paramMap);

	/**
	 * 功能描述：根据用户查询所有记录 参数描述：int userId
	 * 
	 * @time 2015-1-21 返回值 ： List
	 */
	List findOrderByUserId(int userId);
	
	

	/**
	 * 功能描述：组合查询订单追踪
	 * 
	 * @time 2015-1-15 返回值 ： List<Order>
	 */
	List findByCombineOrderTrack(Map<String, Object> paramMap);

	List findByUserId(String orderId);

	/**
	 * 功能描述： 根据订单Id查询记录
	 * 
	 * @time 2015-2-2 返回值 ： Order
	 */
	List findByOrderId(String orderId);

	/**
	 * 根据订单id查询api服务信息
	 * 
	 * @param orderId
	 * @return
	 */
	List findAPIInfoByOrderId(String orderId);

	/**
	 * 功能描述： 根据订单Id查询IP
	 * 
	 * @time 2015-2-2 返回值 ： Order
	 */
	List findIPByOrderId(String orderId);

	/**
	 * 功能描述： 根据pageIndex和用户id查询记录
	 * 
	 * @param list_group
	 * @time 2015-3-4
	 */
	List findByUserIdAndPage(int id, int pageIndex, String state, String type,
			int list_group);

	/**
	 * 功能描述：组合查询订单追踪-分页
	 * 
	 * @time 2015-1-15 返回值 ： List<Order>
	 */
	List findByCombineOrderTrackByPage(Map<String, Object> paramMap);

	/**
	 * 功能描述： 数据分析--订单统计分析
	 * 
	 * @time 2015-3-10
	 */
	List<DataAnalysis> findByCombineDataAnalysis(Map<String, Object> paramMap);

	/**
	 * 功能描述：分页组合查询订单 参数描述：String name
	 * 
	 * @time 2015-3-13 返回值 ： List<Order>
	 */
	List findByCombineByPage(Map<String, Object> paramMap);

	/**
	 * 功能描述：根据订单id查询扫描次数 参数描述：String orderId
	 * 
	 * @time 2015-3-24 返回值 ： List<DataAnalysis>
	 */
	int findScanCountByOrderId(String orderId);

	/**
	 * 功能描述：根据order_ip_Id查询订单 参数描述：int order_ip_Id 返回值 ： List
	 */
	List<Order> findOrder(int order_ip_Id);

	/**
	 * 功能描述：更新发送状态 参数描述：int id 返回值 ： void
	 */
	void update(Order order);

	/**
	 * 功能描述：根据orderId查询正在执行的任务 参数描述：String orderId
	 */
	List findTaskRunning(String orderId);

	// 判断订单id是否存在
	public String getOrderById(String orderId, String type, int userId);

	// 删除订单
	void deleteOrderById(String orderId, int userId);

	// 查找订单
	Order findOrderById(String orderId);

	// 根据用户查询所有任务数
	Object findTaskNumsByUserId(int userId);

	// 根据任务查询订单
	List<Order> findOrderByTask(Task task);

	// 根据条件查询订单
	List<Order> findOrderByMap(Map<String, Object> map);

	// 查找联系人信息
	List<Linkman> findLinkmanById(int contactId);

	/**
	 * 功能描述：组合查询订单追踪-分页
	 * 
	 * @time 2016-4-25 返回值 ： List<Order>
	 */
	List findByCombineOrderTrackByPageAsset(Map<String, Object> paramMap);

	// 根据订单id 查询联系人
	public Linkman findLinkmanByOrderId(String orderId);

	// 修改联系人信息
	public void updateLinkManByOrderId(Linkman linkman, String orderId);

	// 修改api联系人信息
	public void updateLinkManByAPIId(Linkman linkman, String orderId);

	// 删除联系人信息
	public void delLinkmanByOrderId(String orderId, int userId);

	List findByOrderListId(String orderListId, String state);

	/**
	 * 根据orderId查询购买次数
	 * 
	 * @param paramMap
	 * @return
	 */
	int findAPICountByParam(Map<String, Object> paramMap);

	/**
	 * 
	 * 功能描述： 统计订购单次服务的用户数与订购过长期服务的用户数 
	 * 参数描述：
	 * 
	 * @date:2016-8-5下午2:39:04 
	 * 返回值 ： 统计订购单次服务的用户数与订购过长期服务的用户数 
	 * 异 常：
	 */
	List<Map<String,Object>> findServiceUserCount();
	/**
	 * 
	 * 功能描述：  统计长期和单次订单中各服务订单的对比
	 * 参数描述：   
	 *@date:2016-8-8上午10:43:41
	 * 返回值    ：  长期和单次订单中各服务订单的对比
	 * 异        常：
	 */
	List<Map<String,Object>> findServiceCount();
	
	//根据条件查询删除订单
	List<Order> findDelOrderByMap(Map<String, Object> map);
	
	/**
	 * 功能描述：根据用户名和serviceId 查询购物车订单 
	 * 
	 * @time 2017-3-27 返回值 ： List
	 */
	List findOrderByUserIdAndServiceIdCheckShopCar(int id, int parseInt);
	

}
