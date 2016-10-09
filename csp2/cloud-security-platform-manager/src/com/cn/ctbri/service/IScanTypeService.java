package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.ScanType;

public interface IScanTypeService {
	
	List<ScanType> findByServiceId(int serviceId);

}
