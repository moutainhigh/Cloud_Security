package com.entity;

public class CrtOrder {
	private String orderId ;//订单号
	private String assetId ;//资产号
	private String assetUrl ;//资产url
	private String startDate ;//任务开始时间
	private String endDate ;//任务结束时间
	private int serviceType ;//服务类型，1为漏扫，2为。。
	private int isCycle ;//1为周期任务 0为单次任务 
	private int periodic ;//周期值，  1为每周 ， 2为每月
	private int origin ;//来源值 ， 1为portal下单，2为服务能力下单
	
	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetUrl() {
		return assetUrl;
	}

	public void setAssetUrl(String assetUrl) {
		this.assetUrl = assetUrl;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public int getIsCycle() {
		return isCycle;
	}

	public void setIsCycle(int isCycle) {
		this.isCycle = isCycle;
	}

	public int getPeriodic() {
		return periodic;
	}

	public void setPeriodic(int periodic) {
		periodic = periodic;
	}

	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
