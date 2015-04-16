package com.cn.ctbri.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 创 建 人  ： txr
 * 创建日期：  2015-03-31
 * 描        述：  DDOS告警实体
 * 版        本：  1.0
 */
public class AlarmDDOS implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//主键Id
	private long id;
	//攻击类型
	private String attack_type;
	//攻击开始时间
	private Date start_time;
	//攻击源IP
	private String addr_ip;
	//攻击持续时间（处理已结束）
	private Date attack_time;
	//攻击流量（处理已结束）
	private String attack_flow;
	//攻击结束时间（处理已结束）
	private Date end_time;
	//告警上报时间
	private Date alarm_time;
	//任务Id
    private long taskId;
    private String alarmTime;//告警上报时间vo
    private String startTime;//告警上报时间vo
    
	/**
	 * 功能描述：取报警信息ID
	 *		 @time 2015-01-07
	 */
	public long getId() {
		return id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	/**
	 * 功能描述：设置报警信息主键
	 * 参数描述： long id 报警信息要设置的主键值
	 *		 @time 2015-01-07
	 */
	public void setId(long id) {
		this.id = id;
	}
    public String getAttack_type() {
        return attack_type;
    }
    public void setAttack_type(String attack_type) {
        this.attack_type = attack_type;
    }
    public Date getStart_time() {
        return start_time;
    }
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }
    public String getAddr_ip() {
        return addr_ip;
    }
    public void setAddr_ip(String addr_ip) {
        this.addr_ip = addr_ip;
    }
    public Date getAttack_time() {
        return attack_time;
    }
    public void setAttack_time(Date attack_time) {
        this.attack_time = attack_time;
    }
    public String getAttack_flow() {
        return attack_flow;
    }
    public void setAttack_flow(String attack_flow) {
        this.attack_flow = attack_flow;
    }
    public Date getEnd_time() {
        return end_time;
    }
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
    public Date getAlarm_time() {
        return alarm_time;
    }
    public void setAlarm_time(Date alarm_time) {
        this.alarm_time = alarm_time;
    }
    public long getTaskId() {
        return taskId;
    }
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
	

}
