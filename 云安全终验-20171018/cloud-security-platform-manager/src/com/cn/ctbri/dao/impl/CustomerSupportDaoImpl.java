package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.CustomerSupportDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.User;
@Repository
public class CustomerSupportDaoImpl extends DaoCommon implements CustomerSupportDao {

	/**
	 * CustomerSupportMapper的命名空间
	 */
	private String ns = "com.cn.ctbri.entity.CustomerSupportMapper.";

	public List<OrderAsset> queryAssetInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(ns+"queryAssetInfo",map);
	}

	public List<Order> querOrderInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(ns+"querOrderInfo",map);
	}

	public List<User> queryUserInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(ns+"queryUserInfo",map);
	}
	
	public List<User> getUserInfoByAssetId(Map<String, Object> map) {
		return this.getSqlSession().selectList(ns+"getUserInfoByAssetId",map);
	}

	public List<Order> getOrderInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(ns+"getOrderInfo",map);
	}
	
	
}
