package com.cn.ctbri.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.LoggerDao;
import com.cn.ctbri.entity.LogInfo;
/**
 * 日志信息dao实现类
 * @author txr
 *
 */
@Repository
@Transactional
public class LoggerDaoImpl extends DaoCommon implements LoggerDao {
	
	/**
	 * 功        能： LoggerMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.LoggerMapper.";		
	
	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}  
	
	
	public int insert(LogInfo logger) {
		return getSqlSession().insert(ns+"insert", logger);
	}

}
