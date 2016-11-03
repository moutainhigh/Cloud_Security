package com.cn.ctbri.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.ServDetailDao;
import com.cn.ctbri.entity.ServiceDetail;
import com.cn.ctbri.service.IServDetailService;

@Service
public class ServDetailServiceImpl implements IServDetailService {

	 @Autowired
	 ServDetailDao servDetailDao;
	 
	@Override
	public ServiceDetail findByServId(int serviceId) {
		return servDetailDao.findByServId(serviceId);
	}

	@Override
	public void delete(int serviceId, int parentC) {
		servDetailDao.delete(serviceId, parentC);
		
	}

	@Override
	public void insert(ServiceDetail sd) {
		servDetailDao.insert(sd);
		
	}

}
