package com.cn.ctbri.portscanEntity;

public class AppReport {

	private Integer id;
	private Integer taskId;
	private String hostStatus;
	private String portReport;
	private String addTime;
	private String responseTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getHostStatus() {
		return hostStatus;
	}
	public void setHostStatus(String hostStatus) {
		this.hostStatus = hostStatus;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getPortReport() {
		return portReport;
	}
	public void setPortReport(String portReport) {
		this.portReport = portReport;
	}
	@Override
	public String toString() {
		return "AppReport [id=" + id + ", taskId=" + taskId + ", hostStatus="
				+ hostStatus + ", portReport=" + portReport + ", addTime="
				+ addTime + ", responseTime=" + responseTime + "]";
	}
	
	
}
