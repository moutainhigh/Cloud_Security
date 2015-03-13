package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.OrderDao;
import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.Order;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述：   用户数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class OrderDaoImpl extends DaoCommon implements OrderDao{

	/**
	 * 功        能： OrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.OrderMapper.";
	/**
	 * 功能描述：查询所有订单
	 * 参数描述：int id
	 *		 @time 2015-1-15
	 * 返回值    ：  List<Order>
	 */
	public List findByUserId(int id) {
		List list = this.getSqlSession().selectList(ns + "list",id);
		return list;
	}
	
	public List findByCombine(Map<String, Object> paramMap) {
		List list = this.getSqlSession().selectList(ns + "findByCombine",paramMap);
		return list;
	}

	/**
     * 功能描述：根据用户查询所有记录
     * 参数描述：int userId
     *       @time 2015-1-21
     * 返回值    ：  List
     */
    public List<Order> getOrderByUserId(int userId) {
        return this.getSqlSession().selectList(ns + "findOrderByUserId",userId);
    }

    /**
     * 功能描述：组合查询订单追踪
     *       @time 2015-1-15
     * 返回值    ：  List<Order>
     */
    public List findByCombineOrderTrack(Map<String, Object> paramMap) {
        List list = this.getSqlSession().selectList(ns + "findByCombineOrderTrack",paramMap);
        return list;
    }

    /**
     * 功能描述： 根据订单Id查询记录
     *       @time 2015-2-2
     * 返回值    ：  Order
     */
    public List findByOrderId(String orderId) {
        List order = this.getSqlSession().selectList(ns + "findOrderByOrderId",orderId);
        return order;
    }

    /**
     * 功能描述： 根据pageIndex和用户id查询记录
     *       @time 2015-3-4
     * 返回值    ：  Order
     */
    public List findByUserIdAndPage(int id, int pageIndex) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        //当前页
        int pageSize = 10;
        int pageNow = pageIndex*pageSize;
        paramMap.put("userId", id);
        paramMap.put("pageNow", pageNow);
        paramMap.put("pageSize", pageSize);
        List list = this.getSqlSession().selectList(ns + "getOderByPage",paramMap);
        return list;
    }

    /**
     * 功能描述：组合查询订单追踪-分页
     *       @time 2015-1-15
     * 返回值    ：  List<Order>
     */
    public List findByCombineOrderTrackByPage(Map<String, Object> paramMap) {
        List list = this.getSqlSession().selectList(ns + "findByCombineOrderTrackByPage",paramMap);
        return list;
    }  
    /**
     * 功能描述： 数据分析--订单统计分析
     *       @time 2015-3-10
     */
	public List<DataAnalysis> findByCombineDataAnalysis(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns + "findByCombineDataAnalysis",paramMap);
	}

	/**
     * 功能描述：分页组合查询订单
     * 参数描述：String name
     *       @time 2015-3-13
     * 返回值    ：  List<Order>
     */
    public List findByCombineByPage(Map<String, Object> paramMap) {
        List list = this.getSqlSession().selectList(ns + "findByCombineByPage",paramMap);
        return list;
    } 
	
}
