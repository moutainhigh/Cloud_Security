package com.cn.ctbri.entity;

import java.io.Serializable;
/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-05
 * 描        述：  联系人实体
 * 版        本：  1.0
 */
public class Contactor implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//主键Id
	private long id;
	//联系人名称
	private String name;
	//电话
	private String mobile;
	//邮箱
	private String email;
	//单位名称
	private String company;
	//地址
	private String address;
	//创建人Id
	private long userid;
	/**
	 * 功能描述：取联系人ID
	 *		 @time 2015-01-05
	 */
	public long getId() {
		return id;
	}
	/**
	 * 功能描述：设置联系人主键
	 * 参数描述： long id 联系人要设置的主键值
	 *		 @time 2015-01-05
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 功能描述：取联系人名称
	 *		 @time 2015-01-05
	 */
	public String getName() {
		return name;
	}
	/**
	 * 功能描述：设置联系人名称
	 * 参数描述： String name 联系人要设置的名称值
	 *		 @time 2015-01-05
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 功能描述：取联系人电话号
	 *		 @time 2015-01-05
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 功能描述：设置联系人电话
	 * 参数描述： String mobile 联系人要设置的电话值
	 *		 @time 2015-01-05
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 功能描述：取联系人邮箱
	 *		 @time 2015-01-05
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 功能描述：设置联系人邮箱
	 * 参数描述：String email 联系人要设置的邮箱值
	 *		 @time 2015-01-05
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 功能描述：取联系人单位名称
	 *		 @time 2015-01-05
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * 功能描述：设置联系人单位名称
	 * 参数描述：String company 联系人要设置的单位名称值
	 *		 @time 2015-01-05
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * 功能描述：取联系人地址
	 *		 @time 2015-01-05
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 功能描述：设置联系人地址
	 * 参数描述：String address 联系人要设置的地址值
	 *		 @time 2015-01-05
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 功能描述：取联系人创建者Id
	 *		 @time 2015-01-05
	 */
	public long getUserid() {
		return userid;
	}
	/**
	 * 功能描述：设置联系人地址
	 * 参数描述：long userid 联系人要设置的地址值
	 *		 @time 2015-01-05
	 */
	public void setUserid(long userid) {
		this.userid = userid;
	}
	
}
