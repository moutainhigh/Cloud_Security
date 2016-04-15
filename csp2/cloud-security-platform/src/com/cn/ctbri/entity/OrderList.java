package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ： tangxr
 * 创建日期：  2016-3-30
 * 描        述： 购物车订单
 * 版        本：  1.0
 */
public class OrderList {
	
	private String id;//主键Id
	private Date create_date;//下单日期
	private int userId;//用户ID
	private int contactId;//联系人Id
	private String remarks;//备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
