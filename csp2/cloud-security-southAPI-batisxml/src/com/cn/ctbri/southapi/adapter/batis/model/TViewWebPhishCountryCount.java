package com.cn.ctbri.southapi.adapter.batis.model;

public class TViewWebPhishCountryCount {
    private String webphishCountry;

    private String webphishCountrycode;

    private Long webphishCount;

    private Integer isvalid;

    public String getWebphishCountry() {
        return webphishCountry;
    }

    public void setWebphishCountry(String webphishCountry) {
        this.webphishCountry = webphishCountry;
    }

    public String getWebphishCountrycode() {
        return webphishCountrycode;
    }

    public void setWebphishCountrycode(String webphishCountrycode) {
        this.webphishCountrycode = webphishCountrycode;
    }

    public Long getWebphishCount() {
        return webphishCount;
    }

    public void setWebphishCount(Long webphishCount) {
        this.webphishCount = webphishCount;
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }
}