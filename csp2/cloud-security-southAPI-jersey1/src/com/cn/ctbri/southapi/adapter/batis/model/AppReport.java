package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.Date;

public class AppReport {
    private Integer id;

    private Integer taskId;

    private String hostStatus;

    private Date addTime;

    private String responseTime;

    private String portReport;

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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
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
}