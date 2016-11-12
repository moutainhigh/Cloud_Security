package com.cn.ctbri.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.ServiceAPIDao;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceAPI;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-28
 * 描        述：  服务API数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class ServiceAPIDaoImpl extends DaoCommon implements ServiceAPIDao{


	
	/**
	 * 功        能： ServiceAPIMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.ServiceAPIMapper.";


	public ServiceAPI findById(int apiId) {
		return this.getSqlSession().selectOne(ns + "findById",apiId);
	}


	public List<ServiceAPI> findServiceAPI() {
		return this.getSqlSession().selectList(ns + "list");
	}


	public List findApiPriceList() {
		return this.getSqlSession().selectList(ns + "findApiPriceList");
	}


	public int insert(ServiceAPI serviceAPI) {
		this.getSqlSession().insert(ns + "insertServAPI", serviceAPI);
        return serviceAPI.getId();
	}


	public void updateById(ServiceAPI serviceAPI) {
		this.getSqlSession().update(ns+"updateById", serviceAPI);
		
	}


	public void deleteById(int apiId) {
		this.getSqlSession().delete(ns+"deleteById", apiId);
		
	}
	
}
