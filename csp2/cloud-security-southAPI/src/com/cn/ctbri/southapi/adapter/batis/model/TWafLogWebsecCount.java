package com.cn.ctbri.southapi.adapter.batis.model;

public class TWafLogWebsecCount {
    private Long count;
    private String eventType;

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

    public String getStatTime() {
        return statTime;
    }

    public void setStatTime(String statTime) {
        this.statTime = statTime;
    }
}