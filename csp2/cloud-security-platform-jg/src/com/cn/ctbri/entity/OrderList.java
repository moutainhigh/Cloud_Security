package com.cn.ctbri.entity;

import java.util.Date;
import java.util.Map;

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
	private String orderId;//订单Id
	private double price;//总价
	private String serverName;//服务名称
	private Date pay_date;//支付时间
	private int balanceFlag; //是否领取安全币（0:未领取；1:已领取）
	
	private Map<String, Integer> serverNameMap;
	
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getPay_date() {
		return pay_date;
	}
	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public int getBalanceFlag() {
		return balanceFlag;
	}
	public void setBalanceFlag(int balanceFlag) {
		this.balanceFlag = balanceFlag;
	}
	public Map<String, Integer> getServerNameMap() {
		return serverNameMap;
	}
	public void setServerNameMap(Map<String, Integer> serverNameMap) {
		this.serverNameMap = serverNameMap;
	}
	
}
