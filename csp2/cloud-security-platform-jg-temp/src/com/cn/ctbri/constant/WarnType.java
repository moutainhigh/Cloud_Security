package com.cn.ctbri.constant;
/**
 * 告警级别
 * @author txr
 *
 */
public enum WarnType {

	LOWLEVEL("lowlevel"),
	MIDDLELEVEL("middlelevel"),
	HIGHLEVEL("highlevel");
	
	private String name;
	
	private WarnType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
