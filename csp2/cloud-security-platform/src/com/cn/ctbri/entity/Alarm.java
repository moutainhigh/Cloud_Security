package com.cn.ctbri.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-07
 * 描        述：  告警实体
 * 版        本：  1.0
 */
public class Alarm implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//主键Id
	private long id;
	//告警名称
	private String name;
	//告警时间
	private Date alarm_time;
	//得分
    private String score;
	//告警等级
	private int level;
	//告警建议
	private String advice;
	//告警内容
	private String alarm_content;
	//告警资源地址
	private String url;
	//任务Id
	private long taskId;
	//用户id
	private int userId;
	//关键字
	private String keyword;
	//检测频率vo
	private int scan_type;//扫描类型(1：每天，2：每周，3：每月)
	//告警统计分析VO字段
	private int count;
	//统计开始时间VO
	private Date begin_datevo;
	//统计结束时间VO
	private Date end_datevo;
	//告警时间VO
	private String alarmTime;
	//告警分类个数
	private int num;
	//服务id
	private int serviceId;
	//地区id
	private int districtId;
	
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getBegin_datevo() {
		return begin_datevo;
	}
	public void setBegin_datevo(Date begin_datevo) {
		this.begin_datevo = begin_datevo;
	}
	public Date getEnd_datevo() {
		return end_datevo;
	}
	public void setEnd_datevo(Date end_datevo) {
		this.end_datevo = end_datevo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	//告警服务类型
	private String alarm_type;
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
	/**
	 * 功能描述：取报警名称
	 *		 @time 2015-01-07
	 */
	public String getName() {
		return name;
	}
	/**
	 * 功能描述：设置报警名称
	 * 参数描述：String name 报警要设置的名称值
	 *		 @time 2015-01-07
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 功能描述：取报警时间
	 *		 @time 2015-01-07
	 */
	public Date getAlarm_time() {
		return alarm_time;
	}
	/**
	 * 功能描述：设置报警时间
	 * 参数描述：Date alarm_time 报警要设置的时间值
	 *		 @time 2015-01-07
	 */
	public void setAlarm_time(Date alarm_time) {
		this.alarm_time = alarm_time;
	}
	
	public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    /**
	 * 功能描述：取报警等级
	 *		 @time 2015-01-07
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * 功能描述：设置报警等级
	 * 参数描述：int level 报警要设置的等级值
	 *		 @time 2015-01-07
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * 功能描述：取报警解决建议
	 *		 @time 2015-01-07
	 */
	public String getAdvice() {
		return advice;
	}
	/**
	 * 功能描述：设置报警解决建议
	 * 参数描述：String advice 报警要设置的解决建议值
	 *		 @time 2015-01-07
	 */
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	/**
	 * 功能描述：取报警内容
	 *		 @time 2015-01-07
	 */
	public String getAlarm_content() {
		return alarm_content;
	}
	/**
	 * 功能描述：设置报警内容
	 * 参数描述：String alarm_content 报警要设置的内容值
	 *		 @time 2015-01-07
	 */
	public void setAlarm_content(String alarm_content) {
		this.alarm_content = alarm_content;
	}
	/**
	 * 功能描述：取报警资源信息地址
	 *		 @time 2015-01-07
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 功能描述：设置报警资源信息地址
	 * 参数描述：String url 报警要设置的资源信息地址值
	 *		 @time 2015-01-07
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 功能描述：取任务ID
	 *		 @time 2015-01-07
	 */
	public long getTaskId() {
		return taskId;
	}
	/**
	 * 功能描述：设置任务ID
	 * 参数描述：long taskId 报警要设置的任务ID值
	 *		 @time 2015-01-07
	 */
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	/**
	 * 功能描述：取报警类型
	 *		 @time 2015-01-07
	 */
	public String getAlarm_type() {
		return alarm_type;
	}
	/**
	 * 功能描述：设置报警类型
	 * 参数描述：String alarm_type 报警要设置的类型值
	 *		 @time 2015-01-07
	 */
	public void setAlarm_type(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	public int getScan_type() {
		return scan_type;
	}
	public void setScan_type(int scan_type) {
		this.scan_type = scan_type;
	}
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

    
}
