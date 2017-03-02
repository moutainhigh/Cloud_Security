package com.cn.ctbri.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.UserDao;
import com.cn.ctbri.entity.APINum;
import com.cn.ctbri.entity.User;
/**
 * 用户信息dao实现类
 * @author tangxr
 *
 */
@Repository
@Transactional
public class UserDaoImpl extends DaoCommon implements UserDao {
	
	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.UserMapper.";		
	
	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}  
	
	
	public void insert(User user) {
		getSqlSession().insert(ns+"insert", user);
	}


	public void update(User user) {
		getSqlSession().update(ns+"update", user);
	}


	public User findUserByToken(String token) {
		return getSqlSession().selectOne(ns+"findUserByToken", token);
	}


	public User findUserByUserId(int userId) {
		return getSqlSession().selectOne(ns+"findUserByUserId", userId);
	}


	public void updateCount(User user) {
		getSqlSession().update(ns+"updateCount", user);
	}


	public void insertAPINum(APINum num) {
		getSqlSession().insert(ns+"insertAPINum", num);
	}


	public User findUserByApiKey(String apiKey) {
		return getSqlSession().selectOne(ns+"findUserByApiKey", apiKey);
	}


	public User finUserByOrder(String orderId) {
		return getSqlSession().selectOne(ns+"finUserByOrder", orderId);
	}

}
