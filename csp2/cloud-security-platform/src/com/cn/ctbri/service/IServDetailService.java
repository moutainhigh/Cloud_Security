package com.cn.ctbri.service;

import com.cn.ctbri.entity.ServiceDetail;

public interface IServDetailService {
	
	public ServiceDetail findByServId(int serviceId);
	
	public void delete(int serviceId, int parentC);

	public void insert(ServiceDetail sd);

}
