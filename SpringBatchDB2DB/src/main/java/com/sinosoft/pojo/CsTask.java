package com.sinosoft.pojo;

import java.util.Date;

public class CsTask {
    private Integer taskid;

    private String orderAssetId;

    private Date executeTime;

    private Boolean status;

    private Date groupFlag;

    private String remarks;

    private String engineip;

    private String taskprogress;

    private String currenturl;

    private Date beginTime;

    private Date endTime;

    private String scantime;

    private String issuecount;

    private String requestcount;

    private String urlcount;

    private String averresponse;

    private String aversendcount;

    private String sendbytes;

    private String receivebytes;

    private Integer websoc;

    private String groupId;

    private Integer engine;

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getOrderAssetId() {
        return orderAssetId;
    }

    public void setOrderAssetId(String orderAssetId) {
        this.orderAssetId = orderAssetId == null ? null : orderAssetId.trim();
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Date groupFlag) {
        this.groupFlag = groupFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getEngineip() {
        return engineip;
    }

    public void setEngineip(String engineip) {
        this.engineip = engineip == null ? null : engineip.trim();
    }

    public String getTaskprogress() {
        return taskprogress;
    }

    public void setTaskprogress(String taskprogress) {
        this.taskprogress = taskprogress == null ? null : taskprogress.trim();
    }

    public String getCurrenturl() {
        return currenturl;
    }

    public void setCurrenturl(String currenturl) {
        this.currenturl = currenturl == null ? null : currenturl.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getScantime() {
        return scantime;
    }

    public void setScantime(String scantime) {
        this.scantime = scantime == null ? null : scantime.trim();
    }

    public String getIssuecount() {
        return issuecount;
    }

    public void setIssuecount(String issuecount) {
        this.issuecount = issuecount == null ? null : issuecount.trim();
    }

    public String getRequestcount() {
        return requestcount;
    }

    public void setRequestcount(String requestcount) {
        this.requestcount = requestcount == null ? null : requestcount.trim();
    }

    public String getUrlcount() {
        return urlcount;
    }

    public void setUrlcount(String urlcount) {
        this.urlcount = urlcount == null ? null : urlcount.trim();
    }

    public String getAverresponse() {
        return averresponse;
    }

    public void setAverresponse(String averresponse) {
        this.averresponse = averresponse == null ? null : averresponse.trim();
    }

    public String getAversendcount() {
        return aversendcount;
    }

    public void setAversendcount(String aversendcount) {
        this.aversendcount = aversendcount == null ? null : aversendcount.trim();
    }

    public String getSendbytes() {
        return sendbytes;
    }

    public void setSendbytes(String sendbytes) {
        this.sendbytes = sendbytes == null ? null : sendbytes.trim();
    }

    public String getReceivebytes() {
        return receivebytes;
    }

    public void setReceivebytes(String receivebytes) {
        this.receivebytes = receivebytes == null ? null : receivebytes.trim();
    }

    public Integer getWebsoc() {
        return websoc;
    }

    public void setWebsoc(Integer websoc) {
        this.websoc = websoc;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Integer getEngine() {
        return engine;
    }

    public void setEngine(Integer engine) {
        this.engine = engine;
    }
}