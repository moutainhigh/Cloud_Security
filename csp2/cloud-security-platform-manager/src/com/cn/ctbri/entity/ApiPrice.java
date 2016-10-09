package com.cn.ctbri.entity;

public class ApiPrice {
	private int id;//主键
	private int serviceId;//服务id
	private int timesG;//大于
	private int timesLE;//小于等于
	private double price;//价格
	private int delFlag;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public int getTimesG() {
		return timesG;
	}
	public void setTimesG(int timesG) {
		this.timesG = timesG;
	}
	public int getTimesLE() {
		return timesLE;
	}
	public void setTimesLE(int timesLE) {
		this.timesLE = timesLE;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	
	
	
}
