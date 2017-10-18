package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.ServDao;
import com.cn.ctbri.entity.ApiPrice;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.service.IServService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-15
 * 描        述： 服务类业务层实现类
 * 版        本：  1.0
 */
@Service
public class ServServiceImpl implements IServService{
	@Autowired
	ServDao servDao;
	/**
	 * 功能描述： 根据id查询服务
	 * 参数描述： int serviceid
	 *		 @time 2015-1-15
	 *	返回值 ：Serv
	 */
	public Serv findById(int serviceid) {
		Serv serv = servDao.findById(serviceid);
		return serv;
	}
	
	/**
     * 功能描述： 根据条件查询服务
     * 参数描述： Serv service
     *       @time 2015-1-21
     *  返回值 ：Serv
     */
    public List<Serv> findServiceByParam(Serv service) {
        return servDao.getServiceByParam(service);
    }
    public List<Serv> findAllService() {
		return servDao.findAllService();
	}

	public void insertPrice(Price price) {
		servDao.insertPrice(price);
	}

	public int delPrice(int serviceId) {
		return servDao.delPrice(serviceId);
	}

	public List<Price> findPriceByServiceId(int serviceId) {
		return servDao.findPriceByServiceId(serviceId);
	}

	public List<Price> findPriceByParam(Map map) {
		return servDao.findPriceByParam(map);
	}

	public List<ApiPrice> findApiPriceByServiceId(int serviceId) {
		return servDao.findApiPriceByServiceId(serviceId);
	}

	public List<Price> findLongPriceByServiceId(int serviceId) {
		return servDao.findLongPriceByServiceId(serviceId);
	}

	public void updatePriceDeleteFlag(int serviceId) {
		servDao.updatePriceDeleteFlag(serviceId);
		
	}

	public void updateApiPriceDeleteFlag(int serviceId) {
		servDao.updateApiPriceDeleteFlag(serviceId);
		
	}

	public void insertApiPrice(ApiPrice price) {
		servDao.insertApiPrice(price);
		
	}
	
}
