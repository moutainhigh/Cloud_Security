package com.cn.ctbri.southapi.adapter.batis.model;

public class TViewWebPhishProvinceCount {
    private String webphishSubdivision1;

    private String webphishCountrycode;

    private Integer isvalid;

    private Long count;

    public String getWebphishSubdivision1() {
        return webphishSubdivision1;
    }

    public void setWebphishSubdivision1(String webphishSubdivision1) {
        this.webphishSubdivision1 = webphishSubdivision1;
    }

    public String getWebphishCountrycode() {
        return webphishCountrycode;
    }

    public void setWebphishCountrycode(String webphishCountrycode) {
        this.webphishCountrycode = webphishCountrycode;
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}