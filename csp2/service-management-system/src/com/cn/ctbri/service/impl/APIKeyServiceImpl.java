package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.APIKeyDao;
import com.cn.ctbri.dao.OrderDao;
import com.cn.ctbri.entity.APIKey;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAPIKeyService;
import com.cn.ctbri.service.IOrderService;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  APIKey业务层实现类
 * 版        本：  1.0
 */
@Service
public class APIKeyServiceImpl implements IAPIKeyService{
	@Autowired
	APIKeyDao apiKeyDao;

	public APIKey findByKey(String apiKey) {
		return apiKeyDao.findByKey(apiKey);
	}

	
}
