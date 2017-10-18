package com.cn.ctbri.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderDao;
import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IOrderService;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述：  订单业务层实现类
 * 版        本：  1.0
 */
@Service
public class OrderServiceImpl implements IOrderService{
	@Autowired
	OrderDao orderDao;

	/**
	 * 功能描述：查询所有订单
	 * 参数描述：int id
	 *		 @time 2015-1-15
	 * 返回值    ：  List<Order>
	 */
	public List<Order> findByUserId(int id) {
		List<Order> list = orderDao.findByUserId(id);
		return list;
	}
	/**
	 * 功能描述：组合查询订单
	 * 参数描述：String name
	 *		 @time 2015-1-15
	 * 返回值    ：  List<Order>
	 */
	public List findByCombine(Map<String, Object> paramMap) {
		List list = orderDao.findByCombine(paramMap);
		return list;
	}
	
	/**
     * 功能描述：根据用户查询所有记录
     * 参数描述：int userId
     *       @time 2015-1-21
     * 返回值    ：  List
     */
    public List findOrderByUserId(int userId) {
        return orderDao.getOrderByUserId(userId);
    }
    /**
     * 功能描述：组合查询订单追踪
     *       @time 2015-1-15
     * 返回值    ：  List<Order>
     */
    public List findByCombineOrderTrack(Map<String, Object> paramMap) {
        List list = orderDao.findByCombineOrderTrack(paramMap);
        return list;
    }
    public List findByUserId(String orderId) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * 功能描述： 根据订单Id查询记录
     *       @time 2015-2-2
     * 返回值    ：  Order
     */
    public List findByOrderId(String orderId) {
        List order = orderDao.findByOrderId(orderId);
        return order;
    }
	/**
     * 功能描述：根据orderid查询Asset名称
     *       @time 2015-2-2
     */
    public List findIPByOrderId(String orderId) {
        List list = orderDao.findIPByOrderId(orderId);
        return list;
    }
    /**
     * 功能描述： 根据pageIndex和用户id查询记录
     *       @time 2015-3-4
     * 返回值    ：  Order
     */
    public List findByUserIdAndPage(int id, int pageIndex,String state,String type,int list_group) {
        return orderDao.findByUserIdAndPage(id,pageIndex,state,type,list_group);
    }
    /**
     * 功能描述：组合查询订单追踪-分页
     *       @time 2015-1-15
     * 返回值    ：  List<Order>
     */
    public List findByCombineOrderTrackByPage(Map<String, Object> paramMap) {
        List list = orderDao.findByCombineOrderTrackByPage(paramMap);
        return list;
    }
    /**
     * 功能描述： 数据分析--订单统计分析
     *       @time 2015-3-10
     */
	public List<DataAnalysis> findByCombineDataAnalysis(Map<String, Object> paramMap) {
		return orderDao.findByCombineDataAnalysis(paramMap);
	}
	/**
     * 功能描述：分页组合查询订单
     * 参数描述：String name
     *       @time 2015-3-13
     * 返回值    ：  List<Order>
     */
    public List findByCombineByPage(Map<String, Object> paramMap) {
        List list = orderDao.findByCombineByPage(paramMap);
        return list;
    }
	/**
     * 功能描述：根据订单id查询扫描次数
     * 参数描述：String orderId
     *       @time 2015-3-24
     * 返回值    ：  List<DataAnalysis>
     */
	public int findScanCountByOrderId(String orderId) {
		
		return orderDao.findScanCountByOrderId(orderId);
	}
	
	/**
     * 功能描述：根据order_ip_Id查询订单
     * 参数描述：int order_ip_Id
     * 返回值    ：  List
     */
	public List<Order> findOrder(int order_ip_Id){
		List<Order> list = orderDao.findOrder(order_ip_Id);
		return list;
	}
	
	/**
	 * 功能描述：修改用户
	 * 参数描述：User globle_user
	 *		 @time 2015-1-5
	 */
	public void update(Order order) {
		orderDao.update(order);
	}
	/**
     * 功能描述：根据orderId查询正在执行的任务
     * 参数描述：String orderId
     */
    public List findTaskRunning(String orderId) {
        return orderDao.findTaskRunning(orderId);
    }
	public String getOrderById(String orderId, String type, int userId) {
		// TODO Auto-generated method stub
		String a = orderDao.getOrderById(orderId, type, userId);
		return a ;
	}
	
	//删除订单
    public void deleteOrderById(String orderId,int userId) {
        orderDao.deleteOrderById(orderId,userId);
    }
    //查找订单
    public Order findOrderById(String orderId) {
        return orderDao.findOrderById(orderId);
    }
	public Object findTaskNumsByUserId(int userId) {
		return orderDao.findTaskNumsByUserId(userId);
	}
	public List<Order> findOrderByTask(Task task) {
		return orderDao.findOrderByTask(task);
	}
	public List<Order> findOrderByMap(Map<String, Object> map) {
		return orderDao.findOrderByMap(map);
	}
	public List<Linkman> findLinkmanById(int contactId) {
		return orderDao.findLinkmanById(contactId);
	}
	public List findByCombineOrderTrackByPageAsset(Map<String, Object> paramMap) {
		List list = orderDao.findByCombineOrderTrackByPageAsset(paramMap);
        return list;
	}

	public Linkman findLinkmanByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return orderDao.findLinkmanByOrderId(orderId);
	}

	public void updateLinkManByOrderId(Linkman linkman,String orderId) {
		// TODO Auto-generated method stub
		orderDao.updateLinkManByOrderId(linkman, orderId);
	}

	public void updateLinkManByAPIId(Linkman linkman, String orderId) {
		// TODO Auto-generated method stub
		orderDao.updateLinkManByAPIId(linkman, orderId);
	}

	public void delLinkmanByOrderId(String orderId,int userId) {
		// TODO Auto-generated method stub
		orderDao.delLinkmanByOrderId(orderId,userId);
	}
	public List findByOrderListId(String orderListId, String state) {
		return orderDao.findByOrderListId(orderListId,state);
	}
	public List findAPIInfoByOrderId(String orderId) {
		return orderDao.findAPIInfoByOrderId(orderId);
	}
	public int findAPICountByParam(Map<String, Object> paramMap) {
		return orderDao.findAPICountByParam(paramMap);
	}
	public List<Map<String,Object>> findServiceUserCount() {
		return orderDao.getServiceUserCount();
	}
	public List<Map<String, Object>> findServiceCount() {
		return orderDao.getServiceCount();
	}
	
	public List<Order> findDelOrderByMap(Map<String, Object> map) {
		return orderDao.findDelOrderByMap(map);
	}
	public List<Order> getWafOrderById() {
		// TODO Auto-generated method stub
		return orderDao.getWafOrderById();
	}
	public void updateLinkRenew(Map dateMap) {
		// TODO Auto-generated method stub
		orderDao.updateLinkRenew(dateMap);
	}

	
}
