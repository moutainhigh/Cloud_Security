package com.cn.ctbri.model;

import java.util.Date;

public class CntBySrc {
    private Long id;

    private Long num;

    private String dstIp;

    private String domain;

    private String srcIp;

    private String srcCountry;

    private String srcSubdivision1;

    private Date day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp == null ? null : dstIp.trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp == null ? null : srcIp.trim();
    }

    public String getSrcCountry() {
        return srcCountry;
    }

    public void setSrcCountry(String srcCountry) {
        this.srcCountry = srcCountry == null ? null : srcCountry.trim();
    }

    public String getSrcSubdivision1() {
        return srcSubdivision1;
    }

    public void setSrcSubdivision1(String srcSubdivision1) {
        this.srcSubdivision1 = srcSubdivision1 == null ? null : srcSubdivision1.trim();
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}