package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  txr
 * 创建日期：   2015-04-02
 * 描        述：  任务信息实体类
 * 版        本：  1.0
 */
public class TaskInfo{
    private int id;
	private int taskId;//任务Id(主键)
	private Date begin_time;//开始时间
	private Date end_time;//结束时间
	private String scanTime;//扫描时长
	private int issueCount;//已经发现弱点个数
	private int requestCount;//请求次数
	private int urlCount;//url个数
	private int averResponse;//平均响应时间
	private int averSendCount;//每秒访问个数
	private int sendBytes;//发送字节
	private int receiveBytes;//接收字节
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public Date getBegin_time() {
        return begin_time;
    }
    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }
    public Date getEnd_time() {
        return end_time;
    }
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
    public String getScanTime() {
        return scanTime;
    }
    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }
    public int getIssueCount() {
        return issueCount;
    }
    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }
    public int getRequestCount() {
        return requestCount;
    }
    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }
    public int getUrlCount() {
        return urlCount;
    }
    public void setUrlCount(int urlCount) {
        this.urlCount = urlCount;
    }
    public int getAverResponse() {
        return averResponse;
    }
    public void setAverResponse(int averResponse) {
        this.averResponse = averResponse;
    }
    public int getAverSendCount() {
        return averSendCount;
    }
    public void setAverSendCount(int averSendCount) {
        this.averSendCount = averSendCount;
    }
    public int getSendBytes() {
        return sendBytes;
    }
    public void setSendBytes(int sendBytes) {
        this.sendBytes = sendBytes;
    }
    public int getReceiveBytes() {
        return receiveBytes;
    }
    public void setReceiveBytes(int receiveBytes) {
        this.receiveBytes = receiveBytes;
    }
	
}
