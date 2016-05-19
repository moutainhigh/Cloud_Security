package com.cn.ctbri.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.PriceDao;
import com.cn.ctbri.entity.Price;

/**
 * 创 建 人  ：  彭东梅
 * 创建日期：  2016-5-19
 * 描        述：  服务价格DAO实现类
 * 版        本：  1.0
 */
@Repository
public class PriceDaoImpl extends DaoCommon implements PriceDao {
	/**
	 * 功        能： PriceMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.PriceMapper.";


	public List<Price> findPriceByServiceId(int serviceId) {
		return this.getSqlSession().selectList(ns+"findPriceByServiceId", serviceId);
	}

	public void insertPrice(Price price) {
		this.getSqlSession().insert(ns+"insertPrice", price);
	}

	public int delPrice(int serviceId) {
		return this.getSqlSession().delete("delPrice", serviceId);
	}

}
