package com.cn.ctbri.southapi.adapter.waf.config;

import java.util.HashMap;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFConfigSyslog {
	public String factory;
	public String syslogVer;
	public String syslogRegTag;
	
	
	public HashMap<String,WAFConfigSyslogItem> mapWAFConfigSyslogItem = new HashMap<String,WAFConfigSyslogItem>();//String : Item


	public String getFactory() {
		return factory;
	}


	public void setFactory(String factory) {
		this.factory = factory;
	}


	public String getSyslogVer() {
		return syslogVer;
	}


	public void setSyslogVer(String syslogVer) {
		this.syslogVer = syslogVer;
	}


	public String getSyslogRegTag() {
		return syslogRegTag;
	}


	public void setSyslogRegTag(String syslogRegTag) {
		this.syslogRegTag = syslogRegTag;
	}

	
	
}
