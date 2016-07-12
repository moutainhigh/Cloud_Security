package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.APIDao;
import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.service.IAPIService;

/**
 * 创 建 人  ：zx
 * 创建日期：  2015-11-02
 * 描        述：  API统计业务层实现类
 * 版        本：  1.0
 */
@Service
public class APIServiceImpl implements IAPIService{
	@Autowired
	APIDao apiDao;
	
	public List getAPICount(int serviceType) {
		return apiDao.getAPICount(serviceType);
	}

}
