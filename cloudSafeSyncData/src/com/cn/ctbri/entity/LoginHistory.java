package com.cn.ctbri.entity;

import java.util.Date;

public class LoginHistory {
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;//用户ID
	private int userType;//用户TYPE
	private Date loginTime;//登录时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
