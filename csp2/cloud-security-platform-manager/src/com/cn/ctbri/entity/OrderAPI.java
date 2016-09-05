package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  zhang_shaohua
 * 创建日期：  2016-9-1
 * 描        述： 订单API
 * 版        本：  1.0
 */
public class OrderAPI {
	
	private String id;//主键Id
	private Date begin_date;//开始时间
	private Date end_date;//结束日期
	private int apiId;//apiId
	private Date create_date;//下单日期
	private int package_type;//套餐种类
	private int num;//购买次数
	private int userId;//用户ID
	private int contactId;//联系人Id
	private String remarks;//备注
	private int message;//是否发送告警信息
	private int payFlag;//是否结算
	private int buyNum;//购买数量
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public int getApiId() {
		return apiId;
	}
	public void setApiId(int apiId) {
		this.apiId = apiId;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public int getPackage_type() {
		return package_type;
	}
	public void setPackage_type(int package_type) {
		this.package_type = package_type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	public int getMessage() {
		return message;
	}
	public void setMessage(int message) {
		this.message = message;
	}
	public int getPayFlag() {
		return payFlag;
	}
	public void setPayFlag(int payFlag) {
		this.payFlag = payFlag;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	
	
}
