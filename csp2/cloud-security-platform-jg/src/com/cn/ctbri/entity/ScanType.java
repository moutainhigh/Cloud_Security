package com.cn.ctbri.entity;

public class ScanType {

	private String id;//主键
	private int serviceId;//服务主键
	private  int scan_type;//服务频率
	private String scan_name;//服务频率名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public void setScan_type(int scanType) {
		scan_type = scanType;
	}
	public String getScan_name() {
		return scan_name;
	}
	public void setScan_name(String scanName) {
		scan_name = scanName;
	}
	
}
