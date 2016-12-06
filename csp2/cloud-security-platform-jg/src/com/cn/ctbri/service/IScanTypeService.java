package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.ScanType;

public interface IScanTypeService {
	
	//根据服务id查询服务频率信息
	List<ScanType> findScanTypeById(int serviceId);
	
	//根据服务id和服务频率查询服务频率信息
	List<ScanType> findScanType(int serviceId, int scanType);
	
	void deleteByServiceId(int serviceId);

	void insert(ScanType scanType);

}
