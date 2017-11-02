package com.cn.ctbri.entity;

public class ServiceAPI {
	
	private int id;//主键Id
	private String name;//服务名称
	private String factory;//服务厂家(先存名称)
	private int status;//服务状态(1：可用，0：不可用)
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
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
