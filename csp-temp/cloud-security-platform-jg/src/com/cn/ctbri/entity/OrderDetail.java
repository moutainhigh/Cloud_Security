package com.cn.ctbri.entity;

import java.util.Date;

public class OrderDetail {
	private String id;//主键Id
	private int type;//订单类型(1：长期，2：单次)
	private Date begin_date;//开始时间
	private Date end_date;//结束日期
	private int serviceId;//服务ID
	private Date create_date;//下单日期
	private int userId;//用户ID
    private int isAPI;
	private double price;//价格
	private String asstId;
	private int scan_type;//服务频率
	private String serviceName;//服务名称
	private String assetName;//资产名称
	private String assetAddr;//资产地址
	private String ipArray;//ip地址
	private int wafTimes;//waf 服务期限（数字代表月份） or API服务的购买数量  or系统安全帮的购买时长
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(Date beginDate) {
		begin_date = beginDate;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date endDate) {
		end_date = endDate;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date createDate) {
		create_date = createDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getIsAPI() {
		return isAPI;
	}
	public void setIsAPI(int isAPI) {
		this.isAPI = isAPI;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	public String getAsstId() {
		return asstId;
	}
	public void setAsstId(String asstId) {
		this.asstId = asstId;
	}
	public int getScan_type() {
		return scan_type;
	}
	public void setScan_type(int scanType) {
		scan_type = scanType;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
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
	public String getIpArray() {
		return ipArray;
	}
	public void setIpArray(String ipArray) {
		this.ipArray = ipArray;
	}
	public int getWafTimes() {
		return wafTimes;
	}
	public void setWafTimes(int wafTimes) {
		this.wafTimes = wafTimes;
	}
	
	
	
}
