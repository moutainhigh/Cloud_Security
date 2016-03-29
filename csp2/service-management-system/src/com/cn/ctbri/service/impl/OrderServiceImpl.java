package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderDao;
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
    public List<Order> findOrderByUserId(int userId) {
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
    public List<Order> findByOrderId(String orderId) {
        List<Order> order = orderDao.findByOrderId(orderId);
        return order;
    }
    
    /**
     * 功能描述： 根据任务查询订单
     *       @time 2015-2-2
     * 返回值    ：  Order
     */
	public List<Order> findOrderByTask(Task task) {
		List<Order> order = orderDao.findOrderByTask(task);
		return order;
	}
	
    /**
     * 功能描述： 更新订单
     *       @time 2015-2-2
     * 返回值    ：  Order
     */
	public void update(Order o) {
		orderDao.update(o);
	}
    public List<Linkman> findLinkmanById(int contactId) {
        List<Linkman> linkman = orderDao.findLinkmanById(contactId);
        return linkman;
    }
    /**
     * 功能描述：保存订单
     *       @time 2015-11-10
     */
	public void insertOrder(Order order) {
		orderDao.save(order);
	}
	public Order findOrderByOrderId(String orderId) {
		Order order = orderDao.findOrderByOrderId(orderId);
		return order;
	}
	public void deleteOrderById(String orderId) {
		orderDao.delete(orderId);
	}
}
