package com.cn.ctbri.dao.impl;

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
	
	
}
