package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.APIDao;
import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.dao.LogsDao;
import com.cn.ctbri.service.IAPIService;
import com.cn.ctbri.service.ILogsService;

/**
 * 创 建 人  ： tang
 * 创建日期：  2016-8-25
 * 描        述：  logs统计业务层实现类
 * 版        本：  1.0
 */
@Service
public class LogsServiceImpl implements ILogsService{
	@Autowired
	LogsDao logsDao;

	public List<Map> findLogsTimesLine(Map<String, Object> paramMap) {
		return logsDao.findLogsTimesLine(paramMap);
	}
	
	

}
