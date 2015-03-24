package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderDao;
import com.cn.ctbri.entity.DataAnalysis;
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
     * 功能描述： 根据pageIndex和用户id查询记录
     *       @time 2015-3-4
     * 返回值    ：  Order
     */
    public List findByUserIdAndPage(int id, int pageIndex) {
        List list = orderDao.findByUserIdAndPage(id,pageIndex);
        return list;
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
}
