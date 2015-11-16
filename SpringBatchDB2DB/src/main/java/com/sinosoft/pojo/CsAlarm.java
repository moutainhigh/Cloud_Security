package com.sinosoft.pojo;

import java.io.Serializable;
import java.util.Date;

public class CsAlarm implements Serializable  {
    private Integer id;

    private String name;

    private Date alarmTime;

    private String score;

    private int level;

    private String advice;

    private String alarmContent;

    private String url;

    private String keyword;

    private Integer taskid;

    private String alarmType;

    private Integer userid;

    private String groupId;

    private Integer serviceid;

    private Integer districtid;
    
    public CsAlarm(){}
    
    public CsAlarm(Integer id, String name, Date alarmTime, String score,
			int level, String advice, String alarmContent, String url,
			String keyword, Integer taskid, String alarmType, Integer userid,
			String groupId, Integer serviceid, Integer districtid) {
		super();
		this.id = id;
		this.name = name;
		this.alarmTime = alarmTime;
		this.score = score;
		this.level = level;
		this.advice = advice;
		this.alarmContent = alarmContent;
		this.url = url;
		this.keyword = keyword;
		this.taskid = taskid;
		this.alarmType = alarmType;
		this.userid = userid;
		this.groupId = groupId;
		this.serviceid = serviceid;
		this.districtid = districtid;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice == null ? null : advice.trim();
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent == null ? null : alarmContent.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType == null ? null : alarmType.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Integer getServiceid() {
        return serviceid;
    }

    public void setServiceid(Integer serviceid) {
        this.serviceid = serviceid;
    }

    public Integer getDistrictid() {
        return districtid;
    }

    public void setDistrictid(Integer districtid) {
        this.districtid = districtid;
    }
}