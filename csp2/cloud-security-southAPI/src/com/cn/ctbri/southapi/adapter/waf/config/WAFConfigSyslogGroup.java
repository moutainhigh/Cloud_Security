package com.cn.ctbri.southapi.adapter.waf.config;

import java.util.ArrayList;
import java.util.List;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFConfigSyslogGroup {
	private String type;

	//public HashMap<String,WAFConfigSyslog> mapWAFConfigSyslog = new HashMap<String,WAFConfigSyslog>(); //String : Factory
	public List<WAFConfigSyslog> listWAFConfigSyslog = new ArrayList<WAFConfigSyslog>();//String : id

	
	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = (type == null ? "" : type);
	}
	
	
}
