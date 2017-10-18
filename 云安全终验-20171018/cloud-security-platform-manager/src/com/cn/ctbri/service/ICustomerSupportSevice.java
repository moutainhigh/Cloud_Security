package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Order;


public interface ICustomerSupportSevice {

	List<Map<String, Object>> queryUserInfo(Map<String, Object> paramMap);

	List<Order> queryOrderInfo(Map<String, Object> paramMap);

}
