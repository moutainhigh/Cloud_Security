package com.cn.ctbri.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.ApiPriceDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.ApiPrice;

/**
 * 创 建 人  ：  张少华
 * 创建日期：  2016-7-27
 * 描        述：  API价格DAO类
 * 版        本：  1.0
 */
@Repository
public class ApiPriceDaoImpl extends DaoCommon implements ApiPriceDao {
	/**
	 * 功        能： ApiPriceMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.ApiPriceMapper.";
	
	
	public List<ApiPrice> findPriceByServiceId(int serviceId) {
		return this.getSqlSession().selectList(ns + "findPriceByServiceId", serviceId);
	}
	
	public void insertPrice(ApiPrice price) {
		this.getSqlSession().insert(ns+"insertPrice", price);
	}

	public int delPrice(int serviceId) {
		return this.getSqlSession().delete("delPrice", serviceId);
	}

}
