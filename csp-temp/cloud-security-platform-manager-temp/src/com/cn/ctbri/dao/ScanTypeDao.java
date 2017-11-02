package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.ScanType;

public interface ScanTypeDao {
	
	List<ScanType> findByServiceId(int serviceId);

}
