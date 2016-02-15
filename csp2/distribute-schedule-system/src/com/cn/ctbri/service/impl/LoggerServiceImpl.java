package com.cn.ctbri.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.LoggerDao;
import com.cn.ctbri.entity.LogInfo;
import com.cn.ctbri.service.ILoggerService;
@Service
public class LoggerServiceImpl implements ILoggerService{

	@Autowired
	LoggerDao loggerDao;
	
	public int insert(LogInfo logger) {
		return loggerDao.insert(logger);
	}

	
}
