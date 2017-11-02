package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.ServDetailDao;
import com.cn.ctbri.entity.ServiceDetail;

@Repository
public class ServDetailDaoImpl extends DaoCommon implements ServDetailDao {
	/**
	 * 功        能： OrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.ServDetailMapper.";
	

	public ServiceDetail findByServId(int serviceId) {
		return this.getSqlSession().selectOne(ns + "findByServId",serviceId);
	}


	public void delete(int serviceId, int parentC) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("servId", serviceId);
		map.put("parent", parentC);
		this.getSqlSession().delete(ns + "delete",map);
	}

	public void insert(ServiceDetail sd) {
		this.getSqlSession().insert(ns + "insert", sd);
		
	}

}
