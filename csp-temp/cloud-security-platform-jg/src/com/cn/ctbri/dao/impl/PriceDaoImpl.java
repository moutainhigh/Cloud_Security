package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


	public List<Price> findPriceByServiceId(int serviceId,int type) {
		Map map = new HashMap();
		map.put("serviceId", serviceId);
		map.put("type", type);
		return this.getSqlSession().selectList(ns+"findPriceByServiceId", map);
	}

	public void insertPrice(Price price) {
		this.getSqlSession().insert(ns+"insertPrice", price);
	}

	public int delPrice(int serviceId) {
		return this.getSqlSession().delete(ns+"delPrice", serviceId);
	}

	public List<Price> findPriceByScanTypeNull(int serviceId) {
		Map map = new HashMap();
		map.put("serviceId", serviceId);
		return this.getSqlSession().selectList(ns+"findPriceByScanTypeNull", map);
	}

	public List<Price> findPriceByServiceIdAndType(String serviceId, String type) {
		Map map = new HashMap();
		map.put("serviceId", serviceId);
		map.put("type", type);
		return this.getSqlSession().selectList(ns+"findPriceByServiceIdAndType", map);
	}

}
