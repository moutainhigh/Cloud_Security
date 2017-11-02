package com.cn.ctbri.southapi.adapter.waf.syslog;

import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigDevice;
import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigSyslog;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogHostParam {
	private String ipAddr;
	
	private WAFConfigSyslog wafConfigSyslog;
	private WAFConfigDevice wafConfigDevice;
	
	
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public WAFConfigSyslog getWafConfigSyslog() {
		return wafConfigSyslog;
	}
	public void setWafConfigSyslog(WAFConfigSyslog wafConfigSyslog) {
		this.wafConfigSyslog = wafConfigSyslog;
	}
	public WAFConfigDevice getWafConfigDevice() {
		return wafConfigDevice;
	}
	public void setWafConfigDevice(WAFConfigDevice wafConfigDevice) {
		this.wafConfigDevice = wafConfigDevice;
	}
	
	
	
}
