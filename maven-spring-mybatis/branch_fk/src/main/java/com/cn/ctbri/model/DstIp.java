package com.cn.ctbri.model;

public class DstIp {
    private Integer id;

    private String dstIp;

    private String dstCountryCode;

    private String dstCountry;

    private String dstSubdivision1;

    private String dstSubdivision2;

    private String dstCity;

    private String dstLatitude;

    private String dstLongitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp == null ? null : dstIp.trim();
    }

    public String getDstCountryCode() {
        return dstCountryCode;
    }

    public void setDstCountryCode(String dstCountryCode) {
        this.dstCountryCode = dstCountryCode == null ? null : dstCountryCode.trim();
    }

    public String getDstCountry() {
        return dstCountry;
    }

    public void setDstCountry(String dstCountry) {
        this.dstCountry = dstCountry == null ? null : dstCountry.trim();
    }

    public String getDstSubdivision1() {
        return dstSubdivision1;
    }

    public void setDstSubdivision1(String dstSubdivision1) {
        this.dstSubdivision1 = dstSubdivision1 == null ? null : dstSubdivision1.trim();
    }

    public String getDstSubdivision2() {
        return dstSubdivision2;
    }

    public void setDstSubdivision2(String dstSubdivision2) {
        this.dstSubdivision2 = dstSubdivision2 == null ? null : dstSubdivision2.trim();
    }

    public String getDstCity() {
        return dstCity;
    }

    public void setDstCity(String dstCity) {
        this.dstCity = dstCity == null ? null : dstCity.trim();
    }

    public String getDstLatitude() {
        return dstLatitude;
    }

    public void setDstLatitude(String dstLatitude) {
        this.dstLatitude = dstLatitude == null ? null : dstLatitude.trim();
    }

    public String getDstLongitude() {
        return dstLongitude;
    }

    public void setDstLongitude(String dstLongitude) {
        this.dstLongitude = dstLongitude == null ? null : dstLongitude.trim();
    }
}