package com.cn.ctbri.southapi.adapter.config;

public class DeviceConfigInfo {
	public String deviceID;
	public String deviceType;   //设备类型 ：Scanner,DDOS.......
	public String scannerFactory;
	public String scannerFactoryName;
	public String scannerWebUrl;
	public String scannerEngineAPI;
	public String scannerUserName;
	public String scannerPassword;
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getScannerFactory() {
		return scannerFactory;
	}
	public void setScannerFactory(String scannerFactory) {
		this.scannerFactory = scannerFactory;
	}
	public String getScannerFactoryName() {
		return scannerFactoryName;
	}
	
	public void setScannerFactoryName(String scannerFactoryName) {
		this.scannerFactoryName = scannerFactoryName;
	}
	public String getScannerWebUrl() {
		return scannerWebUrl;
	}
	public void setScannerWebUrl(String scannerWebUrl) {
		this.scannerWebUrl = scannerWebUrl;
	}
	public String getScannerEngineAPI() {
		return scannerEngineAPI;
	}
	public void setScannerEngineAPI(String scannerEngineAPI) {
		this.scannerEngineAPI = scannerEngineAPI;
	}
	public String getScannerUserName() {
		return scannerUserName;
	}
	public void setScannerUserName(String scannerUserName) {
		this.scannerUserName = scannerUserName;
	}
	public String getScannerPassword() {
		return scannerPassword;
	}
	public void setScannerPassword(String scannerPassword) {
		this.scannerPassword = scannerPassword;
	}


}
