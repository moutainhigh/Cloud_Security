package com.cn.ctbri.service.impl;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.portscanDao.AppReportDao;
import com.cn.ctbri.portscanEntity.AppReport;
import com.cn.ctbri.service.IAppReportService;

@Service
public class AppReportService implements IAppReportService{
	@Autowired
	private AppReportDao appReportDao;
	public AppReport getAppReportById(int id) {
		// TODO Auto-generated method stub
		return appReportDao.getAppReportById(id);
	}

}
