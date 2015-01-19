package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.UserDao;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IUserService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-4
 * 描        述：  用户业务层实现类
 * 版        本：  1.0
 */
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	UserDao userDao;
	/**
	 * 功能描述：插入用户
	 * 参数描述： User user
	 *		 @time 2014-12-31
	 */
	public void insert(User user) {
		userDao.insert(user);
	}

	/**
	 * 功能描述：根据用户名查询用户
	 * 参数描述：String name
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByName(String name) {
		return userDao.findUserByName(name);
	}

	/**
	 * 功能描述：根据手机号码查询用户
	 * 参数描述：String mobile
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByMobile(String mobile) {
		return userDao.findUserByMobile(mobile);
	}

	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：String email
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：User user
	 *		 @time 2015-1-5
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByCombine(User user) {
		return userDao.findUserByCombine(user);
	}

	/**
	 * 功能描述：修改用户
	 * 参数描述：User globle_user
	 *		 @time 2015-1-5
	 */
	public void update(User globle_user) {
		userDao.update(globle_user);
	}
}
