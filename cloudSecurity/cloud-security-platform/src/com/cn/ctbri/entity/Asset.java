package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：   2014-12-31
 * 描        述：  资产实体类
 * 版        本：  1.0
 */
public class Asset {
	
	private int id;
	private String name;//资产名称
	private int type;//资产类型：1为URL，2为IP
	private String addr;//资产地址
	private int status;//资产状态(1：已验证，0：未验证)
	private int userid;//用户ID
	private Date create_date;//创建日期
	private String remarks;//备注
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
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
