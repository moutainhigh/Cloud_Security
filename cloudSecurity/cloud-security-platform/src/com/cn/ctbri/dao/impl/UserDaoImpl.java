package com.cn.ctbri.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.UserDao;
import com.cn.ctbri.entity.User;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2014-12-31
 * 描        述：   用户数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{

	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}  
	
	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.UserMapper.";		
	
	/**
	 * 功能描述：插入用户
	 * 参数描述： User user
	 *		 @time 2014-12-31
	 */
	public void insert(User user) {
		this.getSqlSession().insert(ns + "insert", user);
	}

	/**
	 * 功能描述：根据用户名查询用户
	 * 参数描述：String name
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByName(String name) {
		return this.getSqlSession().selectList(ns + "findUserByName", name);
	}

	/**
	 * 功能描述：根据手机号码查询用户
	 * 参数描述：String mobile
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByMobile(String mobile) {
		return this.getSqlSession().selectList(ns + "findUserByMobile", mobile);
	}

	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：String email
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByEmail(String email) {
		return this.getSqlSession().selectList(ns + "findUserByEmail", email);
	}

	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：User user
	 *		 @time 2015-1-5
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByCombine(User user) {
		return this.getSqlSession().selectList(ns + "findUserByCombine",user);
	}

	public void update(User globle_user) {
		this.getSqlSession().update(ns + "update",globle_user);
	}

	
}
