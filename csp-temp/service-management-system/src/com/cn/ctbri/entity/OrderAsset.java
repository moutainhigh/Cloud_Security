package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述： 订单资产
 * 版        本：  1.0
 */
public class OrderAsset {
	private int id;//主键Id
	private String orderId;//订单Id
	private int assetId;//资产Id
	private int serviceId;//服务Id
	private int scan_type;//扫描类型
	private Date scan_date;//扫描类型
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
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
    public int getServiceId() {
        return serviceId;
    }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
    public int getScan_type() {
        return scan_type;
    }
    public void setScan_type(int scan_type) {
        this.scan_type = scan_type;
    }
    public Date getScan_date() {
        return scan_date;
    }
    public void setScan_date(Date scan_date) {
        this.scan_date = scan_date;
    }
    
}
