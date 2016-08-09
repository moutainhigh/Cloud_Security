package com.cn.ctbri.entity;

import java.io.Serializable;

public class AlarmBug  implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	/**
	 * 漏洞类型名称
	 */
	private String name;
	/**
	 * 漏洞统计数量
	 */
	private long countVals;
	/**
	 * 执行漏洞扫描日期
	 */
	private String alarmTime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCountVals() {
		return countVals;
	}
	public void setCountVals(long countVals) {
		this.countVals = countVals;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	@Override
	public String toString() {
		return "[\"" + name + "\", countVals=" + countVals
				+ ",\"" + alarmTime + "\"]";
	}
	
}
