package com.cn.ctbri.southapi.adapter.waf.config;

import com.cn.ctbri.southapi.adapter.waf.syslog.WAFSyslogConfig;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFConfigDevice {
	//For Config Device Group
	public int resourceID;
	public String resourceURI;
	public String deployMode;
	
	//For Device Performance 
	public int cpuPerf = -1;
	public int memoryPerf = -1;
	
	//For Device
	private int deviceID;
	private String factory;
	private String factoryName;
	private String devicePhyIP;
	private String[] devicePublicIPList = null;
	
	//For API
	private String apiAddr;
	private String apiKey;
	private String apiValue;
	private String apiUserName;
	private String apiPwd;
	
	//For Syslog
	private String syslogIdentifyType;
	private String syslogVer;
	private String syslogCode;
	private String syslogDeviceTag;
	
	
	//For Syslog Config Information
	private WAFSyslogConfig wafSyslogConfig;
	
	
	
	public int getResourceID() {
		return resourceID;
	}
	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
	}
	public String getResourceURI() {
		return resourceURI;
	}
	public void setResourceURI(String resourceURI) {
		this.resourceURI = resourceURI;
	}
	public String getDeployMode() {
		return deployMode;
	}
	public void setDeployMode(String deployMode) {
		this.deployMode = deployMode;
	}
	

	public int getCpuPerf() {
		return cpuPerf;
	}
	public void setCpuPerf(int cpuPerf) {
		this.cpuPerf = cpuPerf;
	}
	public int getMemoryPerf() {
		return memoryPerf;
	}
	public void setMemoryPerf(int memoryPerf) {
		this.memoryPerf = memoryPerf;
	}
	
	
	public int getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getDevicePhyIP() {
		return devicePhyIP;
	}
	public void setDevicePhyIP(String devicePhyIP) {
		this.devicePhyIP = devicePhyIP;
	}
	

	public String[] getDevicePublicIPList() {
		return devicePublicIPList;
	}
	public void setDevicePublicIPList(String[] devicePublicIPList) {
		this.devicePublicIPList = devicePublicIPList;
	}
	public String getApiAddr() {
		return apiAddr;
	}
	public void setApiAddr(String apiAddr) {
		this.apiAddr = apiAddr;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getApiValue() {
		return apiValue;
	}
	public void setApiValue(String apiValue) {
		this.apiValue = apiValue;
	}
	public String getApiUserName() {
		return apiUserName;
	}
	public void setApiUserName(String apiUserName) {
		this.apiUserName = apiUserName;
	}
	public String getApiPwd() {
		return apiPwd;
	}
	public void setApiPwd(String apiPwd) {
		this.apiPwd = apiPwd;
	}
	public String getSyslogIdentifyType() {
		return syslogIdentifyType;
	}
	public void setSyslogIdentifyType(String syslogIdentifyType) {
		this.syslogIdentifyType = syslogIdentifyType;
	}
	public String getSyslogVer() {
		return syslogVer;
	}
	public void setSyslogVer(String syslogVer) {
		this.syslogVer = syslogVer;
	}
	public String getSyslogCode() {
		return syslogCode;
	}
	public void setSyslogCode(String syslogCode) {
		this.syslogCode = syslogCode;
	}
	public String getSyslogDeviceTag() {
		return syslogDeviceTag;
	}
	public void setSyslogDeviceTag(String syslogDeviceTag) {
		this.syslogDeviceTag = syslogDeviceTag;
	}
	
	
	public WAFSyslogConfig getWafSyslogConfig() {
		return wafSyslogConfig;
	}
	public void setWafSyslogConfig(WAFSyslogConfig wafSyslogConfig) {
		this.wafSyslogConfig = wafSyslogConfig;
	}
	
		
}
