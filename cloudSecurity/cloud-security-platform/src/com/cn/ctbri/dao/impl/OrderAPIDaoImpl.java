package com.cn.ctbri.dao.impl;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.OrderAPIDao;
import com.cn.ctbri.entity.APICount;
import com.cn.ctbri.entity.OrderAPI;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：   用户数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class OrderAPIDaoImpl extends DaoCommon implements OrderAPIDao{

	/**
	 * 功        能： OrderAPIMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.OrderAPIMapper.";

	public void insert(OrderAPI oAPI) {
		this.getSqlSession().insert(ns + "insert", oAPI);
	}

	public void insertOrUpdateCount(APICount count) {
		this.getSqlSession().insert(ns + "insertOrUpdateCount", count);
	}
	
}
