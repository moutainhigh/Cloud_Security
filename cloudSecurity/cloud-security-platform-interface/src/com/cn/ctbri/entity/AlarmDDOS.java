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
	private Date start_time_attack;
	//攻击源IP
	private String attacker;
	//攻击持续时间（处理已结束）
	private String duration;
	//攻击流量（处理已结束）
	private String attack_flow;
	//攻击结束时间（处理已结束）
	private Date end_time;
	//告警上报时间
	private Date start_time_alert;
	//任务Id
    private long taskId;
    //total_kbps
    private long total_kbps;
	/**
	 * 功能描述：取报警信息ID
	 *		 @time 2015-01-07
	 */
	public long getId() {
		return id;
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
    public Date getStart_time_attack() {
        return start_time_attack;
    }
    public void setStart_time_attack(Date start_time_attack) {
        this.start_time_attack = start_time_attack;
    }
    
    public String getAttacker() {
        return attacker;
    }
    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
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
    public Date getStart_time_alert() {
        return start_time_alert;
    }
    public void setStart_time_alert(Date start_time_alert) {
        this.start_time_alert = start_time_alert;
    }
    public long getTaskId() {
        return taskId;
    }
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
    public long getTotal_kbps() {
        return total_kbps;
    }
    public void setTotal_kbps(long total_kbps) {
        this.total_kbps = total_kbps;
    }
	

}
