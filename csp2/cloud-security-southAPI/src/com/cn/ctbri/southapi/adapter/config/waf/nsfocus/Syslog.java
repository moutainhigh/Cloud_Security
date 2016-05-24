package com.cn.ctbri.southapi.adapter.config.waf.nsfocus;

import java.util.List;

public class Syslog{
	private String SyslogVer;
	private String RegexTag;
	private List<WafLog> WafLog;
	public String getSyslogVer() {
		return SyslogVer;
	}
	public void setSyslogVer(String syslogVer) {
		SyslogVer = syslogVer;
	}
	public String getRegexTag() {
		return RegexTag;
	}
	public void setRegexTag(String regexTag) {
		RegexTag = regexTag;
	}
	public List<WafLog> getWafLog() {
		return WafLog;
	}
	public void setWafLog(List<WafLog> wafLog) {
		WafLog = wafLog;
	}
}