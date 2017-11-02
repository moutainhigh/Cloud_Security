package com.cn.ctbri.southapi.adapter.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaskInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private long id;
	/**
	 * 
	 */
	private String taskId;
	/**
	 * 服务类型名称
	 * description:
	 * 	arnhem:name
	 */
	private String typeName;
	/**
	 * 扫描目标站点地址
	 * description:
	 * 	arnhem: site.name
	 * 	nsfocus: targets
	 */
	private String siteName;
	/**
	 * 扫描目标站点IP
	 * description:	
	 * 	arnhem: site.domain
	 * 	非必须,绿盟暂无
	 */
	private String siteDomain;
	/**
	 * 扫描目标站点服务器
	 * description:
	 * 	arnhem: site.server
	 * 	非必须，绿盟暂无
	 */
	private String siteServer;
	/**
	 * 告警时间
	 * description:
	 * 	arnhem: time
	 */
	private Date alarmTime;
	
	private Date startTime;
	
	private String vulCount;
	
	private String riskpoint;
	private List<VulInfo> vulInfos;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteDomain() {
		return siteDomain;
	}
	public void setSiteDomain(String siteDomain) {
		this.siteDomain = siteDomain;
	}
	public String getSiteServer() {
		return siteServer;
	}
	public void setSiteServer(String siteServer) {
		this.siteServer = siteServer;
	}
	public Date getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getVulCount() {
		return vulCount;
	}
	public void setVulCount(String vulCount) {
		this.vulCount = vulCount;
	}
	public String getRiskpoint() {
		return riskpoint;
	}
	public void setRiskpoint(String riskpoint) {
		this.riskpoint = riskpoint;
	}
	public List<VulInfo> getVulInfos() {
		return vulInfos;
	}
	public void setVulInfos(List<VulInfo> vulInfos) {
		this.vulInfos = vulInfos;
	}
	public String toString() {
		// TODO Auto-generated method stub
		List<VulInfo> vulInfos = getVulInfos();
		String vulInfosString = null;
		StringBuilder vulInfosBuilder = new StringBuilder(vulInfosString); 
		for(VulInfo vulInfo : vulInfos){
			vulInfosBuilder.append("\r\n"+vulInfo.toString());
		};
		return super.toString()+
				": id="+this.getId()+
				"; taskId="+this.getTaskId()+
				"; typeName="+this.getTypeName()+
				"; siteName="+this.getSiteName()+
				"; siteDomain="+this.getSiteDomain()+
				"; siteServer="+this.getSiteServer()+
				"; taskTime="+this.getAlarmTime()+
				"; startTime="+this.getStartTime()+
				"; volCount="+this.getVulCount()+
				"; riskpoint="+this.getRiskpoint()+
				"\r\n vulInfos="+vulInfosString;
	}
}
