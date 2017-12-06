package com.cn.ctbri.southapi.adapter.batis.model;

import java.io.Serializable;

public class TWafLogWebsecCount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long count;
    private String eventType;
    private String alertLevel;
    private String statTime;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    

    public String getAlertLevel() {
		return alertLevel;
	}

	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}

	public String getStatTime() {
        return statTime;
    }

    public void setStatTime(String statTime) {
        this.statTime = statTime;
    }
}