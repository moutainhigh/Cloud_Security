package com.cn.ctbri.southapi.adapter.batis.model;

public class TWebPhishKey {
    private Long webphishId;

    private String urlsign;

    public Long getWebphishId() {
        return webphishId;
    }

    public void setWebphishId(Long webphishId) {
        this.webphishId = webphishId;
    }

    public String getUrlsign() {
        return urlsign;
    }

    public void setUrlsign(String urlsign) {
        this.urlsign = urlsign;
    }
}