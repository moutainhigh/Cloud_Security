package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.ServiceSysDao;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceSys;
import com.cn.ctbri.service.IServiceSysService;
/**
 * 创 建 人  ：  cy
 * 创建日期：  2017-2-16
 * 描        述： 系统安全帮类业务层实现类
 * 版        本：  1.0
 */
@Service
public  class ServiceSysServiceImpl implements IServiceSysService{
	@Autowired
	ServiceSysDao serviceSysDao;
	/**
	 * 功能描述： 根据id查询
	 * 参数描述： int apiId
	 *		 @time 2016-3-28
	 *	返回值 ：ServiceAPI
	 */
	public ServiceSys findById(int SysId) {
		ServiceSys serviceSys = serviceSysDao.findById(SysId);
		return serviceSys;
	}
	public List<ServiceSys> findServiceSys() {
		return serviceSysDao.findServiceSys();
	}
	public List findSysPriceList() {
		return serviceSysDao.findSysPriceList();
	}
	
	public int insert(ServiceSys service) {
		return serviceSysDao.insert(service);
	}

	public void updateById(ServiceSys serviceSys) {
		serviceSysDao.updateById(serviceSys);
		
	}

	public void deleteById(int SysId) {
		serviceSysDao.deleteById(SysId);
		
	}

}
