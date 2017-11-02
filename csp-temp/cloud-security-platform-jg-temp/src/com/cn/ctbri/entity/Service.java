package com.cn.ctbri.entity;

public class Service {
	
	private int id;//主键Id
	private String name;//服务名称
	private String factory;//服务厂家(先存名称)
	private String module_name;//模板名称
	private int type;//类型(1：扫描类，2：监控类，3：防护类，4：其他)
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
	public String getModule_name() {
		return module_name;
	}
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
