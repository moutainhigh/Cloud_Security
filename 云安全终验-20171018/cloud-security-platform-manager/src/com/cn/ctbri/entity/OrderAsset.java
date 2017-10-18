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
	private int userId;
	private String ipArray;//网站域名对应的IP地址
	private int Sermonth;//服务期限
	private String assetName;//资产名称
	private String assetAddr;//资产地址
	private String targetKey;
	private String domainIp;
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getAssetAddr() {
		return assetAddr;
	}
	public void setAssetAddr(String assetAddr) {
		this.assetAddr = assetAddr;
	}
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
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
	public String getIpArray() {
		return ipArray;
	}
	public void setIpArray(String ipArray) {
		this.ipArray = ipArray;
	}
	public int getSermonth() {
		return Sermonth;
	}
	public void setSermonth(int sermonth) {
		Sermonth = sermonth;
	}
	public String getTargetKey() {
		return targetKey;
	}
	public void setTargetKey(String targetKey) {
		this.targetKey = targetKey;
	}
	public String getDomainIp() {
		return domainIp;
	}
	public void setDomainIp(String domainIp) {
		this.domainIp = domainIp;
	}
    
}
