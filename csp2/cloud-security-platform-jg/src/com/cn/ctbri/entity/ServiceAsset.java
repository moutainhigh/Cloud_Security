package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  txr
 * 创建日期：   2015-1-15
 * 描        述：  服务资产类型实体类
 * 版        本：  1.0
 */
public class ServiceAsset {
    //主键id
	private int id;
	//资产名称
	private String name;
	//资产类型：1为URL，2为IP
	private int type;
	//资产地址
	private String addr;
	//资产状态(1：已验证，0：未验证)
	private int status;
	//用户ID
	private User user;
	//创建日期
	private Date createDate;
	//备注
	private String remarks;
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
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
	
}
