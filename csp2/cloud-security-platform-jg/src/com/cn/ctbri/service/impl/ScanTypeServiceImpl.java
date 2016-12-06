package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.ScanTypeDao;
import com.cn.ctbri.entity.ScanType;
import com.cn.ctbri.service.IScanTypeService;

@Service
public class ScanTypeServiceImpl implements IScanTypeService {
	
	@Autowired
	ScanTypeDao scanTypeDao;

	public List<ScanType> findScanTypeById(int serviceId){
		return scanTypeDao.findScanTypeById(serviceId);
	}

	public List<ScanType> findScanType(int serviceId, int scanType) {
		return scanTypeDao.findScanType(serviceId, scanType);
	}

	public void deleteByServiceId(int serviceId) {
		scanTypeDao.deleteByServiceId(serviceId);
		
	}

	public void insert(ScanType scanType) {
		scanTypeDao.insert(scanType);
		
	}

}
