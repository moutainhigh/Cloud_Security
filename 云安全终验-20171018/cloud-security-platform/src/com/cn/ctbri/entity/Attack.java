package com.cn.ctbri.entity;

import java.util.ArrayList;
import java.util.Date;

public class Attack {
	
	//时间，攻击方，IP,被攻击方，IP，攻击类型，类型编码，端口,经度，纬度
	private long id;
	private Date startTime;
	private String srcName;
	private String srcIP;
	private String desName;
	private String desIP;
	private String type;
	private Integer typeCode;
	private String port;
	private String srcLongitude;
	private String srcLatitude;
	private String desLongitude;
	private String desLatitude;
	private ArrayList<AttackCount> attackCount;
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getSrcName() {
		return srcName;
	}
	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}
	public String getSrcIP() {
		return srcIP;
	}
	public void setSrcIP(String srcIP) {
		this.srcIP = srcIP;
	}
	public String getDesName() {
		return desName;
	}
	public void setDesName(String desName) {
		this.desName = desName;
	}
	public String getDesIP() {
		return desIP;
	}
	public void setDesIP(String desIP) {
		this.desIP = desIP;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getSrcLongitude() {
		return srcLongitude;
	}
	public void setSrcLongitude(String srcLongitude) {
		this.srcLongitude = srcLongitude;
	}
	public String getSrcLatitude() {
		return srcLatitude;
	}
	public void setSrcLatitude(String srcLatitude) {
		this.srcLatitude = srcLatitude;
	}
	public String getDesLongitude() {
		return desLongitude;
	}
	public void setDesLongitude(String desLongitude) {
		this.desLongitude = desLongitude;
	}
	public String getDesLatitude() {
		return desLatitude;
	}
	public void setDesLatitude(String desLatitude) {
		this.desLatitude = desLatitude;
	}
	public ArrayList<AttackCount> getAttackCount() {
		return attackCount;
	}
	public void setAttackCount(ArrayList<AttackCount> attackCount) {
		this.attackCount = attackCount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "[srcName:" + srcName + ", srcIP:" + srcIP + ", desName:"
				+ desName + ", desIP:" + desIP + ", type:" + type
				+ ", typeCode:" + typeCode + ", port:" + port
				+ ", srcLongitude:" + srcLongitude + ", srcLatitude:"
				+ srcLatitude + ", desLongitude:" + desLongitude
				+ ", desLatitude:" + desLatitude + "]";
	}
	
}
