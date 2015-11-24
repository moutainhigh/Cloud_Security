package com.ctbri.service;

import javax.ws.rs.FormParam;

public interface UserLoginService {
	/**
	 * 
	 * @Title: login
	 * @Description: 用户登陆
	 * @param account
	 * @param password
	 * @throws CloudException
	 *             设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String login(@FormParam("account")
	String account, @FormParam("password")
	String password);
}
