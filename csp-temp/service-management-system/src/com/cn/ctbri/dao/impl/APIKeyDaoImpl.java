package com.cn.ctbri.dao.impl;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.APIKeyDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.APIKey;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述：   用户数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class APIKeyDaoImpl extends DaoCommon implements APIKeyDao{
	
	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}  

	/**
	 * 功        能： APIKeyMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.APIKeyMapper.";

	public APIKey findByKey(String apiKey) {
		return this.getSqlSession().selectOne(ns+"findByKey", apiKey);
	}
	
}
