package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.ScanType;

public interface ScanTypeDao {
	
	public List<ScanType> findScanTypeById(int serviceId);

	public List<ScanType> findScanType(int serviceId, int scanType);

	public void deleteByServiceId(int serviceId);

	public void insert(ScanType scanType);

}
