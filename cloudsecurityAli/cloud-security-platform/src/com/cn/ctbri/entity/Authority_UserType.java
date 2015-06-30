package com.cn.ctbri.entity;

import java.io.Serializable;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-02-05
 * 描        述：  用户类型权限类
 * 版        本：  1.0
 */
public class Authority_UserType implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	//private int id;//主键
	private int userType;//用户类型（0：超级管理员，1：管理员，2：用户）
	private int authorityId;//权限Id
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}
	
}
