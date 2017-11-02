package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.PriceDao;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.service.IPriceService;

/**
 * 创 建 人  ：  彭东梅
 * 创建日期：  2016-5-19
 * 描        述： 服务价格类业务层实现类
 * 版        本：  1.0
 */
@Service
public class PriceServiceImpl implements IPriceService{

	@Autowired
	PriceDao priceDao;
	


	public List<Price> findPriceByServiceId(int serviceId,int scanType) {
		return priceDao.findPriceByServiceId(serviceId,scanType);
	}

	public void insertPrice(Price price) {
		priceDao.insertPrice(price);
		
	}

	public int delPrice(int serviceId) {
		return priceDao.delPrice(serviceId);
	}

	public List<Price> findPriceByScanTypeNull(int serviceId) {
		return priceDao.findPriceByScanTypeNull(serviceId);
	}

	public List<Price> findPriceByServiceIdAndType(String serviceId, String type) {
		return priceDao.findPriceByServiceIdAndType(serviceId,type);
	}

}
