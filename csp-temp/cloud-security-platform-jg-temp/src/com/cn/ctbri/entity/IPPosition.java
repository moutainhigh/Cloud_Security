package com.cn.ctbri.entity;

import java.util.Date;

public class IPPosition {
	private String ip;
	private String longitude;
	private String latitude;
	private String countryProvince;
	private Date registerTime;
	private int sourceStatus;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getCountryProvince() {
		return countryProvince;
	}
	public void setCountryProvince(String countryProvince) {
		this.countryProvince = countryProvince;
	}
	public int getSourceStatus() {
		return sourceStatus;
	}
	public void setSourceStatus(int sourceStatus) {
		this.sourceStatus = sourceStatus;
	}
	
}
