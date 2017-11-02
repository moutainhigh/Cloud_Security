package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.Date;

public class TWafLogArp {
    private Long logId;

    private Integer resourceId;

    private String resourceUri;

    private String resourceIp;

    private Date statTime;

    private String alertlevel;

    private String eventType;

    private String attackType;

    private String srcIp;

    private String srcMac;

    private String dstIp;

    private String dstMac;

    private String status;

    private String action;

    private String defIp;

    private String defMac;

    private String conflitMac;

    private String countNum;

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

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getSrcMac() {
        return srcMac;
    }

    public void setSrcMac(String srcMac) {
        this.srcMac = srcMac;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public String getDstMac() {
        return dstMac;
    }

    public void setDstMac(String dstMac) {
        this.dstMac = dstMac;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDefIp() {
        return defIp;
    }

    public void setDefIp(String defIp) {
        this.defIp = defIp;
    }

    public String getDefMac() {
        return defMac;
    }

    public void setDefMac(String defMac) {
        this.defMac = defMac;
    }

    public String getConflitMac() {
        return conflitMac;
    }

    public void setConflitMac(String conflitMac) {
        this.conflitMac = conflitMac;
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }
}