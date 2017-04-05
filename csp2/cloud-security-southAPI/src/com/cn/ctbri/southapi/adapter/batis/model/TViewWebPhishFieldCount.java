package com.cn.ctbri.southapi.adapter.batis.model;

public class TViewWebPhishFieldCount {
    private String webphishField;

    private Long count;

    private Integer isvalid;

    public String getWebphishField() {
        return webphishField;
    }

    public void setWebphishField(String webphishField) {
        this.webphishField = webphishField;
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