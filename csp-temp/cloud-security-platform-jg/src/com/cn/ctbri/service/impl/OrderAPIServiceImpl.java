package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderAPIDao;
import com.cn.ctbri.entity.APICount;
import com.cn.ctbri.entity.OrderAPI;
import com.cn.ctbri.service.IOrderAPIService;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  订单业务层实现类
 * 版        本：  1.0
 */
@Service
public class OrderAPIServiceImpl implements IOrderAPIService{
	@Autowired
	OrderAPIDao orderAPIDao;

	public void insert(OrderAPI oAPI) {
		orderAPIDao.insert(oAPI);
	}

	public void insertOrUpdateCount(APICount count) {
		orderAPIDao.insertOrUpdateCount(count);
	}

	public List<OrderAPI> findOrderAPIByType(Map<String, Object> paramMap) {
		return orderAPIDao.findOrderAPIByType(paramMap);
	}

	public void deleteOrderAPI(String orderId,int userId) {
		// TODO Auto-generated method stub
		orderAPIDao.deleteOrderAPI(orderId,userId);
	}
	
}
