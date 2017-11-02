package com.cn.ctbri.service;

import com.cn.ctbri.entity.APINum;
import com.cn.ctbri.entity.User;

public interface IUserService {

    void insert(User user);

	void update(User user);

	User findUserByToken(String token);

	User findUserByUserId(int userId);

	void updateCount(User user);

	void insertAPINum(APINum num);

	User findUserByApiKey(String apiKey);

	User finUserByOrder(String orderId);
    
}
