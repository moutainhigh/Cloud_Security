package com.cn.ctbri.entity;

public class Price {
	private int id;//主键
	private int serviceId;//服务id
	private Integer type;//类型：（0:单次；1：长期；2：大于）
	private Integer timesG;//大于
	private Integer timesLE;//小于等于
	private double price;//价格
	private Integer scanType;
	private int delFlag; //是否已经删除（0:未删除；1：已删除）
	
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getTimesG() {
		return timesG;
	}
	public void setTimesG(Integer timesG) {
		this.timesG = timesG;
	}
	public Integer getTimesLE() {
		return timesLE;
	}
	public void setTimesLE(Integer timesLE) {
		this.timesLE = timesLE;
	}
	public Integer getScanType() {
		return scanType;
	}
	public void setScanType(Integer scanType) {
		this.scanType = scanType;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

}
