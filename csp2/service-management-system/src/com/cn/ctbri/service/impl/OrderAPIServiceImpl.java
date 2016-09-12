package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderAPIDao;
import com.cn.ctbri.entity.API;
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

	public List<OrderAPI> findByParam(Map<String, Object> paramMap) {
		return orderAPIDao.findByParam(paramMap);
	}

	public List<API> findAPIByParam(Map<String, Object> paramMap) {
		return orderAPIDao.findAPIByParam(paramMap);
	}

	public List findAllAPIByParam(Map<String, Object> paramMap) {
		return orderAPIDao.findAllAPIByParam(paramMap);
	}

	public List findAPIHistoryInfoByParam(Map<String, Object> paramMap) {
		return orderAPIDao.findAPIHistoryInfoByParam(paramMap);
	}
	

	public List<OrderAPI> findUseableByParam(Map<String, Object> paramMap) {
		return orderAPIDao.findUseableByParam(paramMap);
	}

	public void updateCount(Map<String, Object> param) {
		orderAPIDao.updateCount(param);
	}

	public API findUsedByParam(Map<String, Object> paramMap) {
		return orderAPIDao.findUsedByParam(paramMap);
	}
	
}
