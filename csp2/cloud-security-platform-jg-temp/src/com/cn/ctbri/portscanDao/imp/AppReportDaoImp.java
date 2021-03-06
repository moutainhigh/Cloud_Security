package com.cn.ctbri.portscanDao.imp;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.portscanDao.AppReportDao;
import com.cn.ctbri.portscanDao.PortScanSqlSessionDaoSupport;
import com.cn.ctbri.portscanEntity.AppReport;
@Repository
@Transactional
public class AppReportDaoImp extends PortScanSqlSessionDaoSupport implements AppReportDao{
	private String ns = "com.cn.ctbri.portscanEntity.AppReportMapper.";		
	public AppReport getAppReportById(int id) {
		return getSqlSession().selectOne(ns+"getAppReportById", id);
	}

}
