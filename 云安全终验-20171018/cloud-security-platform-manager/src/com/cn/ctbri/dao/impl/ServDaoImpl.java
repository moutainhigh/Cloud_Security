package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.ServDao;
import com.cn.ctbri.entity.ApiPrice;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.entity.Serv;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-15
 * 描        述：   服务数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class ServDaoImpl extends DaoCommon implements ServDao{


	/**
	 * 功        能： OrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.ServMapper.";
	/**
	 * 功        能：PriceMapper命名空间
	 */
	private String np = "com.cn.ctbri.entity.PriceMapper.";
	/**
	 * 功        能：ApiPriceMapper命名空间
	 */
	private String na = "com.cn.ctbri.entity.ApiPriceMapper.";

	public Serv findById(int serviceid) {
		return this.getSqlSession().selectOne(ns + "findById",serviceid);
	}

	/**
     * 功能描述： 根据条件查询服务
     * 参数描述： Serv service
     *       @time 2015-1-21
     *  返回值 ：Serv
     */
    public List<Serv> getServiceByParam(Serv service) {
        return this.getSqlSession().selectList(ns + "findServiceByParam",service);
    }
	
    public List<Serv> findAllService() {
		return this.getSqlSession().selectList(ns+"list");
	}

	public void insertPrice(Price price) {
		this.getSqlSession().insert(np+"insertPrice",price);		
	}

	public int delPrice(int serviceId) {
		return this.getSqlSession().delete(np+"delPrice",serviceId);
	}

	public List<Price> findPriceByServiceId(int serviceId) {
		return this.getSqlSession().selectList(np+"findPriceByServiceId", serviceId);
	}

	public List<Price> findPriceByParam(Map map) {
		
		return this.getSqlSession().selectList(np+"findPriceByParam", map);
	}

	public List<ApiPrice> findApiPriceByServiceId(int serviceId) {
		return this.getSqlSession().selectList(na+"findApiPriceByServiceId", serviceId);
	}

	public List<Price> findLongPriceByServiceId(int serviceId) {
		return this.getSqlSession().selectList(np+"findLongPriceByServiceId", serviceId);
	}

	public void updatePriceDeleteFlag(int serviceId) {
		this.getSqlSession().update(np+"updatePriceDeleteFlag", serviceId);
		
	}

	public void updateApiPriceDeleteFlag(int serviceId) {
		this.getSqlSession().update(na+"updateApiPriceDeleteFlag", serviceId);
		
	}

	public void insertApiPrice(ApiPrice price) {
		this.getSqlSession().update(na+"insertApiPrice", price);
		
	}
	
}
