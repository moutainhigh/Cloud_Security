package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.ApiPriceDao;
import com.cn.ctbri.entity.ApiPrice;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.service.IApiPriceService;

/**
 * 创 建 人  ：  张少华
 * 创建日期：  2016-7-27
 * 描        述：  API价格业务层实现类
 * 版        本：  1.0
 */
@Service
public class ApiPriceServiceImpl implements IApiPriceService {
	
	@Autowired
	ApiPriceDao apiPriceDao;
	
	public List<ApiPrice> findPriceByServiceId(int serviceId) {
		return apiPriceDao.findPriceByServiceId(serviceId);
	}
	
	public void insertPrice(ApiPrice price) {
		apiPriceDao.insertPrice(price);
		
	}

	public int delPrice(int serviceId) {
		return apiPriceDao.delPrice(serviceId);
	}

	public ApiPrice findPrice(int serviceId, int num) {
		return apiPriceDao.findPrice(serviceId, num);
	}

}
