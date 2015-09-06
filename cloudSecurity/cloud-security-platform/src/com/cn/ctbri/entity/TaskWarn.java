package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  txr
 * 创建日期：   2015-4-10
 * 描        述：  任务告警实体类
 * 版        本：  1.0
 */
public class TaskWarn{
	private int id;//任务Id(主键)
	private String cat1;//事件1级分类
	private String cat2;//事件2级分类
	private String name;//安全事件名称
	private int severity;//危害等级
	private String rule;//触发告警的规则或者特征
	private int ct;//原始事件（归并后）数量
	private String app_p;//应用层协议
    private String tran_p;//传输协议
    private String url;//事件相关域名或URL
    private String msg;//事件内容详细信息
    private String task_id;//
    private Date warn_time;
    private int count;//告警次数vo
    private String warnTime;//VO
    private String level;//描述 告警等级高中低
    private int scan_type;//检测频率VO
    private int serviceId;
    public int getScan_type() {
		return scan_type;
	}
	public void setScan_type(int scan_type) {
		this.scan_type = scan_type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getWarnTime() {
		return warnTime;
	}
	public void setWarnTime(String warnTime) {
		this.warnTime = warnTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTask_id() {
        return task_id;
    }
    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }
    public String getCat1() {
        return cat1;
    }
    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }
    public String getCat2() {
        return cat2;
    }
    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSeverity() {
        return severity;
    }
    public void setSeverity(int severity) {
        this.severity = severity;
    }
    public String getRule() {
        return rule;
    }
    public void setRule(String rule) {
        this.rule = rule;
    }
    public int getCt() {
        return ct;
    }
    public void setCt(int ct) {
        this.ct = ct;
    }
    public String getApp_p() {
        return app_p;
    }
    public void setApp_p(String app_p) {
        this.app_p = app_p;
    }
    public String getTran_p() {
        return tran_p;
    }
    public void setTran_p(String tran_p) {
        this.tran_p = tran_p;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Date getWarn_time() {
        return warn_time;
    }
    public void setWarn_time(Date warn_time) {
        this.warn_time = warn_time;
    }
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
    
}
