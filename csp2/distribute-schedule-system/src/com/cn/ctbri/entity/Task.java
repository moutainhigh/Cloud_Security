package com.cn.ctbri.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：   2015-1-27
 * 描        述：  任务实体类
 * 版        本：  1.0
 */
public class Task implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int taskId;//任务Id(主键)
	private String order_asset_Id;//
	private Date execute_time;//执行时间
	private int status;//任务状态（1：已执行，未执行）
	private Date group_flag;//分组标记
	private int exceptMark;//异常标识0~-5 默认0
	private String engineIP;//引擎ip
	private String taskProgress;//进度
	private String currentUrl;//当前url
	private Date begin_time;//开始时间
    private Date end_time;//结束时间
    private String scanTime;//扫描时长
    private String issueCount;//已经发现弱点个数
    private String requestCount;//请求次数
    private String urlCount;//url个数
    private String averResponse;//平均响应时间
    private String averSendCount;//每秒访问个数
    private String sendBytes;//发送字节
    private String receiveBytes;//接收字节
    private int websoc;//创宇标志
    private String group_id;//创宇返回组id
    private int engine;
	private int serviceId;//服务ID
	private String order_id;//订单id
	private String orderTaskId;//订单任务id
	private String assetAddr;//资产地址
	private String moduleName;//模板名称
	private int scanMode;//订单类型（1：长期；2：单次）
	private int scanType;//扫描周期
	private int warn;//推送（0：失败；1：成功）
	private String beginTime;//vo开始时间
    private String endTime;//vo结束时间
    private String executeTime;//vo执行时间
    private String groupFlag;//vo执行时间
	private int isNextTask;//是否下一次扫描任务
	private String partner;//合作方
    
	public int getTaskId() {
		return taskId;
	}
	public String getOrder_asset_Id() {
		return order_asset_Id;
	}
	public void setOrder_asset_Id(String order_asset_Id) {
		this.order_asset_Id = order_asset_Id;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public Date getExecute_time() {
		return execute_time;
	}
	public void setExecute_time(Date execute_time) {
		this.execute_time = execute_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getGroup_flag() {
        return group_flag;
    }
    public void setGroup_flag(Date group_flag) {
        this.group_flag = group_flag;
    }
    public int getExceptMark() {
		return exceptMark;
	}
	public void setExceptMark(int exceptMark) {
		this.exceptMark = exceptMark;
	}
	public String getEngineIP() {
        return engineIP;
    }
    public void setEngineIP(String engineIP) {
        this.engineIP = engineIP;
    }
    public String getTaskProgress() {
        return taskProgress;
    }
    public void setTaskProgress(String taskProgress) {
        this.taskProgress = taskProgress;
    }
    public String getCurrentUrl() {
        return currentUrl;
    }
    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
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
    public String getIssueCount() {
        return issueCount;
    }
    public void setIssueCount(String issueCount) {
        this.issueCount = issueCount;
    }
    public String getRequestCount() {
        return requestCount;
    }
    public void setRequestCount(String requestCount) {
        this.requestCount = requestCount;
    }
    public String getUrlCount() {
        return urlCount;
    }
    public void setUrlCount(String urlCount) {
        this.urlCount = urlCount;
    }
    public String getAverResponse() {
        return averResponse;
    }
    public void setAverResponse(String averResponse) {
        this.averResponse = averResponse;
    }
    public String getAverSendCount() {
        return averSendCount;
    }
    public void setAverSendCount(String averSendCount) {
        this.averSendCount = averSendCount;
    }
    public String getSendBytes() {
        return sendBytes;
    }
    public void setSendBytes(String sendBytes) {
        this.sendBytes = sendBytes;
    }
    public String getReceiveBytes() {
        return receiveBytes;
    }
    public void setReceiveBytes(String receiveBytes) {
        this.receiveBytes = receiveBytes;
    }
    public int getWebsoc() {
        return websoc;
    }
    public void setWebsoc(int websoc) {
        this.websoc = websoc;
    }
    public String getGroup_id() {
        return group_id;
    }
    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
    public int getEngine() {
        return engine;
    }
    public void setEngine(int engine) {
        this.engine = engine;
    }
	
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String orderId) {
		order_id = orderId;
	}
	public String getOrderTaskId() {
		return orderTaskId;
	}
	public void setOrderTaskId(String orderTaskId) {
		this.orderTaskId = orderTaskId;
	}
	public String getAssetAddr() {
		return assetAddr;
	}
	public void setAssetAddr(String assetAddr) {
		this.assetAddr = assetAddr;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public int getScanMode() {
		return scanMode;
	}
	public void setScanMode(int scanMode) {
		this.scanMode = scanMode;
	}
	public int getScanType() {
		return scanType;
	}
	public void setScanType(int scanType) {
		this.scanType = scanType;
	}
	public int getWarn() {
		return warn;
	}
	public void setWarn(int warn) {
		this.warn = warn;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}
    public String getGroupFlag() {
		return groupFlag;
	}
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}
	public int getIsNextTask() {
		return isNextTask;
	}
	public void setIsNextTask(int isNextTask) {
		this.isNextTask = isNextTask;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	
}
