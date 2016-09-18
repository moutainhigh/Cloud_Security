package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.ServiceAPIDao;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceAPI;
import com.cn.ctbri.service.IServiceAPIService;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-28
 * 描        述： 服务API类业务层实现类
 * 版        本：  1.0
 */
@Service
public class ServiceAPIServiceImpl implements IServiceAPIService{
	@Autowired
	ServiceAPIDao serviceAPIDao;
	/**
	 * 功能描述： 根据id查询
	 * 参数描述： int apiId
	 *		 @time 2016-3-28
	 *	返回值 ：ServiceAPI
	 */
	public ServiceAPI findById(int apiId) {
		ServiceAPI serviceAPI = serviceAPIDao.findById(apiId);
		return serviceAPI;
	}
	public List<ServiceAPI> findServiceAPI() {
		return serviceAPIDao.findServiceAPI();
	}
	public List findApiPriceList() {
		return serviceAPIDao.findApiPriceList();
	}
	
}
