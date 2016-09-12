package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.OrderAPIDao;
import com.cn.ctbri.entity.API;
import com.cn.ctbri.entity.APICount;
import com.cn.ctbri.entity.OrderAPI;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：   用户数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class OrderAPIDaoImpl extends DaoCommon implements OrderAPIDao{

	/**
	 * 功        能： OrderAPIMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.OrderAPIMapper.";
	private String nv = "com.cn.ctbri.entity.APIMapper.";

	public void insert(OrderAPI oAPI) {
		this.getSqlSession().insert(ns + "insert", oAPI);
	}

	public void insertOrUpdateCount(APICount count) {
		this.getSqlSession().insert(ns + "insertOrUpdateCount", count);
	}

	public List<OrderAPI> findByParam(Map<String, Object> paramMap) {
		return getSqlSession().selectList(ns+"findByParam", paramMap);
	}

	public List<API> findAPIByParam(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(nv+"findAPIByParam",paramMap);
	}

	public List findAllAPIByParam(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(nv+"findAllAPIByParam",paramMap);
	}

	public List findAPIHistoryInfoByParam(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(nv+"findAPIHistoryInfoByParam",paramMap);
	}

	public List<OrderAPI> findUseableByParam(Map<String, Object> paramMap) {
		return getSqlSession().selectList(ns+"findUseableByParam", paramMap);
	}

	public void updateCount(Map<String, Object> param) {
		this.getSqlSession().insert(ns + "updateCount", param);
	}

	public API findUsedByParam(Map<String, Object> paramMap) {
		return getSqlSession().selectOne(ns+"findUsedByParam", paramMap);
	}
	
}
