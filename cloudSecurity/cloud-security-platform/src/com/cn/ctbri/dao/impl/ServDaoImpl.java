package com.cn.ctbri.dao.impl;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import com.cn.ctbri.dao.ServDao;
import com.cn.ctbri.entity.Serv;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-15
 * 描        述：   服务数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class ServDaoImpl extends SqlSessionDaoSupport implements ServDao{

	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}

	
	/**
	 * 功        能： OrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.ServiceMapper.";


	public Serv findById(int serviceid) {
		return this.getSqlSession().selectOne(ns + "findById",serviceid);
	}
	
	
	
}
