package com.cn.ctbri.entity;

public class ServiceAPI {
	
	private int id;//主键Id
	private String name;//服务名称
	private String factory;//服务厂家(先存名称)
	private int status;//服务状态(1：可用，0：不可用)
	private String remarks;//备注
	private int parentC;//服务大类
    private String homeIcon;//首页服务图标
    private String categoryIcon;//二级页服务图标
    private String detailIcon;//详情页服务图标
    private int type;//类型(1：扫描类，2：监控类，3：防护类，4：其他)
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
	public int getParentC() {
		return parentC;
	}
	public void setParentC(int parentC) {
		this.parentC = parentC;
	}
	public String getHomeIcon() {
		return homeIcon;
	}
	public void setHomeIcon(String homeIcon) {
		this.homeIcon = homeIcon;
	}
	public String getCategoryIcon() {
		return categoryIcon;
	}
	public void setCategoryIcon(String categoryIcon) {
		this.categoryIcon = categoryIcon;
	}
	public String getDetailIcon() {
		return detailIcon;
	}
	public void setDetailIcon(String detailIcon) {
		this.detailIcon = detailIcon;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
