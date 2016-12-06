package com.cn.ctbri.dao;

import java.util.Map;

import com.cn.ctbri.entity.ServiceDetail;

public interface ServDetailDao {

	public ServiceDetail findByServId(int serviceId);

	public void delete(int serviceId, int parentC);

	public void insert(ServiceDetail sd);
}
