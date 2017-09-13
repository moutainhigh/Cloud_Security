package com.cn.ctbri.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.IPPositionDao;
import com.cn.ctbri.entity.IPPosition;
@Repository
public class IPPositionDaoImpl extends DaoCommon implements IPPositionDao{
	/**
	 * 功        能： OrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.IPPositionMapper.";
	
	public void saveAsset(IPPosition ipPosition) {
		this.getSqlSession().insert(ns + "saveIPPosition", ipPosition);
	}

	public List<IPPosition> getIPPositions() {
		return getSqlSession().selectList(ns + "getIPPositionList");
	}

	public IPPosition findIPPositionByIP(String ip) {
		// TODO Auto-generated method stub
		return null;
	}
	public int deleteIP(IPPosition ipPosition){
		return getSqlSession().delete(ns + "deleteIP",ipPosition);
	}

}
