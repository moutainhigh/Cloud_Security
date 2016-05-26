package com.cn.ctbri.southapi.adapter.config.waf.nsfocus;

import java.util.List;

public class WafDeviceGroup {
	private String ResourceID;
	private String ResourceURI;
	private String DeployMode;
	private List<WafDevice> WAFDeviceList;
	public String getResourceID() {
		return ResourceID;
	}
	public void setResourceID(String resourceID) {
		ResourceID = resourceID;
	}
	public String getResourceURI() {
		return ResourceURI;
	}
	public void setResourceURI(String resourceURI) {
		ResourceURI = resourceURI;
	}
	/**
	 * @return the wAFDeviceList
	 */
	public List<WafDevice> getWAFDeviceList() {
		return WAFDeviceList;
	}
	/**
	 * @param wAFDeviceList the wAFDeviceList to set
	 */
	public void setWAFDeviceList(List<WafDevice> wAFDeviceList) {
		WAFDeviceList = wAFDeviceList;
	}
	public String getDeployMode() {
		return DeployMode;
	}
	public void setDeployMode(String deployMode) {
		DeployMode = deployMode;
	}
}
