package com.cn.ctbri.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShopCar implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	private String orderId;//订单id
	private String serverName;//服务名称
	private String astName;//资产名称
	private String addr;//
	private double price;//价格
	private int num;//数量
	private int serviceId;//服务id
	private int isAPI;//是否是api
	private int orderType;//订单类型(1：长期，2：单次)
	private Date beginDate;
	private Date endDate;
	private int scanPeriod;//扫描类型(1：每天，2：每周，3：每月)
	private int websoc;//创宇标志
	private int status;//状态 -1订单已作废
	private int pack_type;//类型
	private int buynum;
	private Date begin_date;//开始时间
	private Date end_date;//结束日期
	private Date createDate;
	private int scan_type;	
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getScan_type() {
		return scan_type;
	}
	public void setScan_type(int scan_type) {
		this.scan_type = scan_type;
	}
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
	public int getIsAPI() {
		return isAPI;
	}
	public void setIsAPI(int isAPI) {
		this.isAPI = isAPI;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getScanPeriod() {
		return scanPeriod;
	}
	public void setScanPeriod(int scanPeriod) {
		this.scanPeriod = scanPeriod;
	}
	public int getWebsoc() {
		return websoc;
	}
	public void setWebsoc(int websoc) {
		this.websoc = websoc;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPack_type() {
		return pack_type;
	}
	public void setPack_type(int packType) {
		pack_type = packType;
	}
	public int getBuynum() {
		return buynum;
	}
	public void setBuynum(int buynum) {
		this.buynum = buynum;
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
