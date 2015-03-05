package com.cn.ctbri.entity;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-3-5
 * 描        述：  服务器参数配置实体类
 * 版        本：  1.0
 */
public class ServerParamConfiguration {
	
	private Integer sessionTime;//用户回话时常
	private String serverEmailAdd;//邮件服务器地址
	private String serverEmailName;//邮件用户名
	private String serverEmailPassword;//邮件密码
	
	public Integer getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(Integer sessionTime) {
		this.sessionTime = sessionTime;
	}
	public String getServerEmailAdd() {
		return serverEmailAdd;
	}
	public void setServerEmailAdd(String serverEmailAdd) {
		this.serverEmailAdd = serverEmailAdd;
	}
	public String getServerEmailName() {
		return serverEmailName;
	}
	public void setServerEmailName(String serverEmailName) {
		this.serverEmailName = serverEmailName;
	}
	public String getServerEmailPassword() {
		return serverEmailPassword;
	}
	public void setServerEmailPassword(String serverEmailPassword) {
		this.serverEmailPassword = serverEmailPassword;
	}
	
}
