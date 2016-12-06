package com.cn.ctbri.entity;

public class Price {
	private int id;//主键
	private int serviceId;//服务id
	private int type;//类型：（0:单次；1：长期；2：大于）
	private int timesG;//大于
	private int timesLE;//小于等于
	private double price;//价格
	
	private Integer scanType;
	
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public Integer getScanType() {
		return scanType;
	}
	public void setScanType(Integer scanType) {
		this.scanType = scanType;
	}


	
}
