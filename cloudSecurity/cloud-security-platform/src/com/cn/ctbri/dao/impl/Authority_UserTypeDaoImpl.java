package com.cn.ctbri.dao.impl;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.Authority_UserTypeDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.Authority_UserType;
@Repository
public class Authority_UserTypeDaoImpl  extends DaoCommon implements Authority_UserTypeDao {

	/**
	 * 功        能： AuthorityMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.Authority_UserTypeMapper.";

	public void insert(Authority_UserType au) {
		getSqlSession().insert(ns+"add", au);
	}
}
