package com.cn.ctbri.southapi.adpater.config.waf.nsfocus;

import java.util.List;



public class WafConfig {
	private List<WafDeviceGroup> wafDeviceGroups;
	private List<Syslog> syslogGroup;
	public List<Syslog> getSyslogGroup() {
		return syslogGroup;
	}
	public void setSyslogGroup(List<Syslog> syslogGroup) {
		this.syslogGroup = syslogGroup;
	}
	public List<WafDeviceGroup> getWafDeviceGroups() {
		return wafDeviceGroups;
	}

	public void setWafDeviceGroups(List<WafDeviceGroup> wafDeviceGroups) {
		this.wafDeviceGroups = wafDeviceGroups;
	}
}










