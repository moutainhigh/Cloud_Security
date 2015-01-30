package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderDao;
import com.cn.ctbri.entity.Order;
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
}
