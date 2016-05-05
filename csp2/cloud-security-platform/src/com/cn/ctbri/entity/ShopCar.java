package com.cn.ctbri.entity;

import java.io.Serializable;

public class ShopCar implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	private String orderId;//订单id
	private String serverName;//服务名称
	private String astName;//资产名称
	private double price;//价格
	private int num;//数量
	private int serviceId;//服务id
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getAstName() {
		return astName;
	}
	public void setAstName(String astName) {
		this.astName = astName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	
}
