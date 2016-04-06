package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderDao;
import com.cn.ctbri.dao.OrderListDao;
import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  订单业务层实现类
 * 版        本：  1.0
 */
@Service
public class OrderListServiceImpl implements IOrderListService{
	@Autowired
	OrderListDao orderListDao;

	public void insert(OrderList ol) {
		orderListDao.insert(ol);
	}
	
	
}
