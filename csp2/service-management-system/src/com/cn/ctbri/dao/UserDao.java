package com.cn.ctbri.dao;

import com.cn.ctbri.entity.APINum;
import com.cn.ctbri.entity.User;
/**
 * 用户信息dao接口
 * @author tangxr
 *
 */
public interface UserDao {

	public void insert(User user);

	public void update(User user);

	public User findUserByToken(String token);

	public User findUserByUserId(int userId);

	public void updateCount(User user);

	public void insertAPINum(APINum num);

	public User findUserByApiKey(String apiKey);

	public User finUserByOrder(String orderId);
}
