package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.Date;

public class TWafLogDeface {
    private Long logId;

    private Integer resourceId;

    private String resourceUri;

    private String resourceIp;

    private Integer siteId;

    private Integer protectId;

    private Date statTime;

    private String alertlevel;

    private String eventType;

    private String dstIp;

    private String dstPort;

    private String url;

    private String reason;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getResourceIp() {
        return resourceIp;
    }

    public void setResourceIp(String resourceIp) {
        this.resourceIp = resourceIp;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getProtectId() {
        return protectId;
    }

    public void setProtectId(Integer protectId) {
        this.protectId = protectId;
    }

    public Date getStatTime() {
        return statTime;
    }

    public void setStatTime(Date statTime) {
        this.statTime = statTime;
    }

    public String getAlertlevel() {
        return alertlevel;
    }

    public void setAlertlevel(String alertlevel) {
        this.alertlevel = alertlevel;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}