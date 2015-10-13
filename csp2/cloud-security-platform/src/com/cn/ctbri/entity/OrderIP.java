package com.cn.ctbri.entity;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-21
 * 描        述： 订单IP
 * 版        本：  1.0
 */
public class OrderIP {
	private int id;//主键Id
	private String orderId;//订单Id
	private String ip;//监控对象IP地址/IP地址段
	private int bandwidth;//防护带宽(10M、100M、500M、1G、5G、10G)
	private int serviceId;//服务Id
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getBandwidth() {
        return bandwidth;
    }
    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }
    public int getServiceId() {
        return serviceId;
    }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
	
}
