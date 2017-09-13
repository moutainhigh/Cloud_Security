package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.ScanTypeDao;
import com.cn.ctbri.entity.ScanType;

@Repository
public class ScanTypeDaoImpl extends DaoCommon implements ScanTypeDao {
	/**
	 * 功        能：SelfHelpOrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.ScanTypeMapper.";
	
	public List<ScanType> findScanTypeById(int serviceId){
		return this.getSqlSession().selectList(ns+"getScanTypeById", serviceId);
	}

	public List<ScanType> findScanType(int serviceId, int scanType) {
		Map map = new HashMap();
		map.put("serviceId", serviceId);
		map.put("scanType", scanType);
		return this.getSqlSession().selectList(ns+"getScanType", map);
	}

	public void deleteByServiceId(int serviceId) {
		this.getSqlSession().delete(ns+"deleteByServiceId", serviceId);
		
	}

	public void insert(ScanType scanType) {
		this.getSqlSession().insert(ns+"insert", scanType);
		
	}

}
