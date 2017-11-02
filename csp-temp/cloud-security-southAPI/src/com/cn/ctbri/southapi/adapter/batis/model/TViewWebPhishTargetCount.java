package com.cn.ctbri.southapi.adapter.batis.model;

public class TViewWebPhishTargetCount {
    private String webphishTarget;

    private Long count;

    private Integer isvalid;

    public String getWebphishTarget() {
        return webphishTarget;
    }

    public void setWebphishTarget(String webphishTarget) {
        this.webphishTarget = webphishTarget;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }
}