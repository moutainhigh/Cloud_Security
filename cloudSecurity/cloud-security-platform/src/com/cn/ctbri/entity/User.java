package com.cn.ctbri.entity;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：   2014-12-31
 * 描        述：  用户实体类
 * 版        本：  1.0
 */
public class User {
	private int id;
	//用户名
	private String name;
	//密码
	private String password;
	//手机号码
	private String mobile;
	//邮箱
	private String email;
	//用户状态(1：正常，0：停用)
	private int status;
	//用户类型（0：超级管理员，1：管理员，2：用户）
	private int type;
	//备注
	private String remarks;
	//邮箱验证码
	private String verification_code;
	//登录页面验证码
	private String checkNumber;
	/**
	 * 功能描述：取用户ID
	 *		 @time 2015-01-05
	 */
	public int getId() {
		return id;
	}
	/**
	 * 功能描述：设置用户主键
	 * 参数描述： int id 用户要设置的主键值
	 *		 @time 2015-01-05
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 功能描述：取用户名称
	 *		 @time 2015-01-05
	 */
	public String getName() {
		return name;
	}
	/**
	 * 功能描述：设置用户名称
	 * 参数描述： String name 用户要设置的名称值
	 *		 @time 2015-01-05
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 功能描述：取用户密码
	 *		 @time 2015-01-05
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 功能描述：设置用户密码
	 * 参数描述： String password 用户要设置的密码值
	 *		 @time 2015-01-05
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 功能描述：取用户手机号
	 *		 @time 2015-01-05
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 功能描述：设置用户手机
	 * 参数描述： String mobile 用户要设置的手机值
	 *		 @time 2015-01-05
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 功能描述：取用户邮箱
	 *		 @time 2015-01-05
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 功能描述：设置用户邮箱
	 * 参数描述： String email 用户要设置的邮箱值
	 *		 @time 2015-01-05
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 功能描述：取用户状态
	 *		 @time 2015-01-05
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 功能描述：设置用户状态
	 * 参数描述： String status 用户要设置的状态值
	 *		 @time 2015-01-05
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * 功能描述：取用户类型
	 *		 @time 2015-01-05
	 */
	public int getType() {
		return type;
	}
	/**
	 * 功能描述：设置用户类型
	 * 参数描述： String type 用户要设置的类型值
	 *		 @time 2015-01-05
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * 功能描述：取用户备注
	 *		 @time 2015-01-05
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 功能描述：设置用户备注
	 * 参数描述： String remarks 用户要设置的备注值
	 *		 @time 2015-01-05
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 功能描述：取用户验证码
	 *		 @time 2015-01-05
	 */
	public String getVerification_code() {
		return verification_code;
	}
	/**
	 * 功能描述：设置用户邮箱验证码
	 * 参数描述： String verification_code 用户要设置的邮箱验证码值
	 *		 @time 2015-01-05
	 */
	public void setVerification_code(String verification_code) {
		this.verification_code = verification_code;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	
}
