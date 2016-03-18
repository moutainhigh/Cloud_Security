package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：   2016-03-15
 * 描        述：  购物车实体类
 * 版        本：  1.0
 */
public class ShoppingCar {
	
	private int id;
	private String serviceId;//服务Id
	private Integer status;//状态(1：已结算，0：未结算)
	private int userid;//用户ID
	private Date create_date;//创建日期
	private String remarks;//备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
