package com.cn.ctbri.entity;
/**
 * 创 建 人  ：  txr
 * 创建日期：   2015-1-19
 * 描        述：  联系人实体类
 * 版        本：  1.0
 */
public class Linkman {
	private int id;
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
	private int userId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

}
