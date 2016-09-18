package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.User;



public interface CustomerSupportDao {

	List<OrderAsset> queryAssetInfo(Map<String, Object> map);

	List<Order> querOrderInfo(Map<String, Object> map);

	List<User> queryUserInfo(Map<String, Object> map);
	
	List<User> getUserInfoByAssetId(Map<String, Object> map);

	List<Order> getOrderInfo(Map<String, Object> map);


}
