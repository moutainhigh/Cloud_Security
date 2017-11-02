package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.XlistDao;
import com.cn.ctbri.entity.Xlist;
import com.cn.ctbri.portscanDao.AppReportDao;
import com.cn.ctbri.portscanEntity.AppReport;
import com.cn.ctbri.service.IXlistService;

/**
 * 创 建 人  ：  裴丹丹
 * 创建日期：  2017年8月22日
 * 描        述：  
 * 版        本：  1.0
 */
@Service
public class XlistServiceImpl implements IXlistService {
	
	@Autowired
	private XlistDao xlistDao;
	@Autowired
	private AppReportDao appReportDao;

	public List<Xlist> listXlist() {
		return xlistDao.listXlist();
	}

	public Xlist findById(int id) {
		return xlistDao.findById(id);
	}


	public int insert(Xlist xlist) {
		return xlistDao.insert(xlist);
	}


	public int updateById(int id) {
		return xlistDao.updateById(id);
	}


	public int deleteById(int id) {
		return xlistDao.deleteById(id);
	}

	public AppReport getAppReportById(int id) {
		// TODO Auto-generated method stub
		return appReportDao.getAppReportById(id);
	}

}
