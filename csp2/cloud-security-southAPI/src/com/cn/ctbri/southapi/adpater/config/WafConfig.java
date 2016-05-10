package com.cn.ctbri.southapi.adpater.config;

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
class WafDeviceGroup{
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
class WafDevice{
	private String WAFDevID;
	private String WAFFactory;
	private String WAFFactoryName;
	private String WAFDevPhyIP;
	private String IdentifyType;
	private String SyslogVer;
	private String WAFDevTag;
	private WAFDevAPI wafDevAPI;
	private WAFDevSyslog wafDevSyslog;
	public String getWAFDevID() {
		return WAFDevID;
	}
	public void setWAFDevID(String wAFDevID) {
		WAFDevID = wAFDevID;
	}
	public String getWAFFactory() {
		return WAFFactory;
	}
	public void setWAFFactory(String wAFFactory) {
		WAFFactory = wAFFactory;
	}
	public String getWAFFactoryName() {
		return WAFFactoryName;
	}
	public void setWAFFactoryName(String wAFFactoryName) {
		WAFFactoryName = wAFFactoryName;
	}
	public String getWAFDevPhyIP() {
		return WAFDevPhyIP;
	}
	public void setWAFDevPhyIP(String wAFDevPhyIP) {
		WAFDevPhyIP = wAFDevPhyIP;
	}

	public String getSyslogVer() {
		return SyslogVer;
	}
	public void setSyslogVer(String syslogVer) {
		SyslogVer = syslogVer;
	}
	public String getIdentifyType() {
		return IdentifyType;
	}
	public void setIdentifyType(String identifyType) {
		IdentifyType = identifyType;
	}
	public String getWAFDevTag() {
		return WAFDevTag;
	}
	public void setWAFDevTag(String wAFDevTag) {
		WAFDevTag = wAFDevTag;
	}
	public WAFDevAPI getWafDevAPI() {
		return wafDevAPI;
	}
	public void setWafDevAPI(WAFDevAPI wafDevAPI) {
		this.wafDevAPI = wafDevAPI;
	}
	public WAFDevSyslog getWafDevSyslog() {
		return wafDevSyslog;
	}
	public void setWafDevSyslog(WAFDevSyslog wafDevSyslog) {
		this.wafDevSyslog = wafDevSyslog;
	}
}
class WAFDevAPI{
	private String APIAddr;
	private String APIKey;
	private String APIValue;
	private String APIUserName;
	private String APIPwd;
	public String getAPIAddr() {
		return APIAddr;
	}
	public void setAPIAddr(String aPIAddr) {
		APIAddr = aPIAddr;
	}
	public String getAPIKey() {
		return APIKey;
	}
	public void setAPIKey(String aPIKey) {
		APIKey = aPIKey;
	}
	public String getAPIValue() {
		return APIValue;
	}
	public void setAPIValue(String aPIValue) {
		APIValue = aPIValue;
	}
	public String getAPIUserName() {
		return APIUserName;
	}
	public void setAPIUserName(String aPIUserName) {
		APIUserName = aPIUserName;
	}
	public String getAPIPwd() {
		return APIPwd;
	}
	public void setAPIPwd(String aPIPwd) {
		APIPwd = aPIPwd;
	}
}
class WAFDevSyslog{
	private String IdentifyType;
	private String SyslogVer;
	private String SyslogCode;
	public String getIdentifyType() {
		return IdentifyType;
	}
	public void setIdentifyType(String identifyType) {
		IdentifyType = identifyType;
	}
	public String getSyslogVer() {
		return SyslogVer;
	}
	public void setSyslogVer(String syslogVer) {
		SyslogVer = syslogVer;
	}
	public String getSyslogCode() {
		return SyslogCode;
	}
	public void setSyslogCode(String syslogCode) {
		SyslogCode = syslogCode;
	}
	
}

class Syslog{
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
class WafLog{
	private String item;
	private String tag;
	private String dboptSql;
	private Match match;
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDboptSql() {
		return dboptSql;
	}
	public void setDboptSql(String dboptSql) {
		this.dboptSql = dboptSql;
	}
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
}
class Match{
	private String reg;
	private List<Trans> trans;
	public String getReg() {
		return reg;
	}
	public void setReg(String reg) {
		this.reg = reg;
	}
	public List<Trans> getTrans() {
		return trans;
	}
	public void setTrans(List<Trans> trans) {
		this.trans = trans;
	}
	
}
class Trans{
	private String id;
	private String name;
	private String express;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	
}
