package com.cn.ctbri.entity;
/**
 * 创 建 人  ：  cy
 * 创建日期：   2017-2-16
 * 描        述：  系统安全帮
 * 版        本：  1.0
 */

public class ServiceSys {
	private int id;//主键Id
	private String name;//服务名称
	private String factory;//服务厂家(先存名称)
	private String module_name;//模板名称
	private int type;//类型(1：扫描类，2：监控类，3：防护类，4：其他)
	private int status;//服务状态(1：可用，0：不可用)
	private String remarks;//备注
    private int orderType;	//订单类型(0:单次和长期,1:长期,2:单次)
    private int parentC;//服务大类
    private int websoc;//创宇标志
    private String homeIcon;//首页服务图标
    private String categoryIcon;//二级页服务图标
    private String detailIcon;//详情页服务图标
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
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    public int getParentC() {
        return parentC;
    }
    public void setParentC(int parentC) {
        this.parentC = parentC;
    }
    public int getWebsoc() {
        return websoc;
    }
    public void setWebsoc(int websoc) {
        this.websoc = websoc;
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

}
