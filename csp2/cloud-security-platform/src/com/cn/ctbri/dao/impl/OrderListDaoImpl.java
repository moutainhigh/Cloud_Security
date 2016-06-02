package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.OrderListDao;
import com.cn.ctbri.entity.OrderList;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：   用户数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class OrderListDaoImpl extends DaoCommon implements OrderListDao{

	/**
	 * 功        能： OrderListMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.OrderListMapper.";

	public void insert(OrderList ol) {
		this.getSqlSession().insert(ns + "insert", ol);
	}
	
	public OrderList findById(String id){
		return this.getSqlSession().selectOne(ns + "findById", id);
	}
	
	public List<OrderList> findAllPayRecord(int userId) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("pageNow", null);
        paramMap.put("pageSize", null);
		List<OrderList> list = getSqlSession().selectList(ns +"queryPayRecordByPageAndUserId", paramMap);
		return list;
	}
	
	public void update(OrderList ol){
		this.getSqlSession().update(ns + "update", ol);
	}
	
	/**
     * 功能描述： 消费记录分页
     * @param userId
     * @param  i  开始记录的个数
     * @param pageSize  每页记录条数
     *       @time 2016-5-19
     */
	public List<OrderList> queryPayRecordByPage(int userId, int offset, int pageSize) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("pageNow", offset);
        paramMap.put("pageSize", pageSize);
        List<OrderList> list = getSqlSession().selectList(ns +"queryPayRecordByPageAndUserId", paramMap);
		return list;
	}
	
}
