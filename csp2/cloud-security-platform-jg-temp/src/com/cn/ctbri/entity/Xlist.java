package com.cn.ctbri.entity;

public class Xlist {
	private int id;//主键Id
	private String remarks;//备注
    private String categoryIcon;//二级页服务图标
    private String linkPage;//跳转链接
    private String name;//服务名称
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCategoryIcon() {
		return categoryIcon;
	}
	public void setCategoryIcon(String categoryIcon) {
		this.categoryIcon = categoryIcon;
	}
	public String getlinkPage() {
		return linkPage;
	}
	public void setlinkPage(String linkPage) {
		this.linkPage = linkPage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
