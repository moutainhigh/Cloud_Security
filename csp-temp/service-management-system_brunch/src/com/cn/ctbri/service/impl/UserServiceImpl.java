package com.cn.ctbri.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.UserDao;
import com.cn.ctbri.entity.APINum;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IUserService;
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	UserDao userDao;

    public void insert(User user) {
        userDao.insert(user);
    }

	public void update(User user) {
		userDao.update(user);
	}

	public User findUserByToken(String token) {
		return userDao.findUserByToken(token);
	}

	public User findUserByUserId(int userId) {
		return userDao.findUserByUserId(userId);
	}

	public void updateCount(User user) {
		userDao.updateCount(user);
	}

	public void insertAPINum(APINum num) {
		userDao.insertAPINum(num);
	}

	public User findUserByApiKey(String apiKey) {
		return userDao.findUserByApiKey(apiKey);
	}

	public User finUserByOrder(String orderId) {
		return userDao.finUserByOrder(orderId);
	}

}
