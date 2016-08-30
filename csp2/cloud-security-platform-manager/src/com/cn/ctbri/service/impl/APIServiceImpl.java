package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.APIDao;
import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.service.IAPIService;

/**
 * 创 建 人  ：zx
 * 创建日期：  2015-11-02
 * 描        述：  API统计业务层实现类
 * 版        本：  1.0
 */
@Service
public class APIServiceImpl implements IAPIService{
	@Autowired
	APIDao apiDao;
	
	public List getAPICount(Map<String,Object> map) {
		return apiDao.getAPICount(map);
	}

	public List getAPICountLast7Days() {
		return apiDao.getAPICountLast7Days();
	}

	public List getAPIUserCountTop5(Map<String,Object> map) {
		return apiDao.getAPIUserCountTop5(map);
	}

	public List getAllAPIUserList(Map<String, Object> map) {
		return apiDao.getAllAPIUserList(map);
	}

	public List getAPICountByUser(Map<String, Object> map) {
		return apiDao.getAPICountByUser(map);
	}

	public List getAPIUseTimes(Map<String, Object> map) {
		return apiDao.getAPIUseTimes(map);
	}

	public List getAPITimesByUser(Map<String, Object> map) {
		return apiDao.getAPITimesByUser(map);
	}

	public int getAPIUseCount() {
		return apiDao.getAPIUseCount();
	}

	public int getAllAPICount() {
		return apiDao.getAllAPICount();
	}

}
