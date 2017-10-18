package com.cn.ctbri.entity;

import java.io.Serializable;

public class AlarmSum implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	
	private String url;
	private int districtId;
	private String districtName;
	
	private int alarmCount;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public int getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(int alarmCount) {
		this.alarmCount = alarmCount;
	}
	
	

}
