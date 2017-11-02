package com.cn.ctbri.southapi.adapter.waf.config;

import java.util.HashMap;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFConfigDeviceGroup {

	public int resourceID;
	public String resourceURI;
	public String deployMode;
	
	public HashMap<Integer,WAFConfigDevice> mapWAFConfigDevice = new HashMap<Integer,WAFConfigDevice>();

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

	public HashMap<Integer, WAFConfigDevice> getMapWAFConfigDevice() {
		return mapWAFConfigDevice;
	}

	public void setMapWAFConfigDevice(
			HashMap<Integer, WAFConfigDevice> mapWAFConfigDevice) {
		this.mapWAFConfigDevice = mapWAFConfigDevice;
	}
	
	
	
}
