package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class TWafLogWebsec {
	private Long logId;
    @XStreamOmitField
    private Integer resourceId;
    @XStreamOmitField
    private String resourceUri;
    @XStreamOmitField
    private String resourceIp;
    @XStreamOmitField
    private String siteId;
    @XStreamOmitField
    private String protectId;

    private String dstIp;

    private String dstPort;

    private String srcIp;

    private String srcPort;

    private String method;

    private String domain;

    private String uri;

    private String alertlevel;

    private String eventType;

    private Date statTime;
    @XStreamOmitField
    private String policyId;
    @XStreamOmitField
    private String ruleId;

    private String action;

    private String block;

    private String blockInfo;

    private String alertinfo;

    private String proxyInfo;
    @XStreamOmitField
    private String characters;

    private String countNum;

    private String protocolType;
    @XStreamOmitField 
    private String wci;
    @XStreamOmitField 
    private String wsi;
    @XStreamOmitField 
    private byte[] http;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getResourceIp() {
        return resourceIp;
    }

    public void setResourceIp(String resourceIp) {
        this.resourceIp = resourceIp;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getProtectId() {
        return protectId;
    }

    public void setProtectId(String protectId) {
        this.protectId = protectId;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAlertlevel() {
        return alertlevel;
    }

    public void setAlertlevel(String alertlevel) {
        this.alertlevel = alertlevel;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getStatTime() {
        return statTime;
    }

    public void setStatTime(Date statTime) {
        this.statTime = statTime;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBlockInfo() {
        return blockInfo;
    }

    public void setBlockInfo(String blockInfo) {
        this.blockInfo = blockInfo;
    }

    public String getAlertinfo() {
        return alertinfo;
    }

    public void setAlertinfo(String alertinfo) {
        this.alertinfo = alertinfo;
    }

    public String getProxyInfo() {
        return proxyInfo;
    }

    public void setProxyInfo(String proxyInfo) {
        this.proxyInfo = proxyInfo;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getWci() {
        return wci;
    }

    public void setWci(String wci) {
        this.wci = wci;
    }

    public String getWsi() {
        return wsi;
    }

    public void setWsi(String wsi) {
        this.wsi = wsi;
    }

    public byte[] getHttp() {
        return http;
    }

    public void setHttp(byte[] http) {
        this.http = http;
    }
}