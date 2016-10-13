package com.cn.ctbri.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.ScanTypeDao;
import com.cn.ctbri.entity.ScanType;

@Repository
public class ScanTypeDaoImpl extends DaoCommon implements ScanTypeDao {
	
	/**
	 * 功        能： ScanTypeMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.ScanTypeMapper.";

	public List<ScanType> findByServiceId(int serviceId) {
		return this.getSqlSession().selectList(ns+"findByServiceId", serviceId);
	}

}
