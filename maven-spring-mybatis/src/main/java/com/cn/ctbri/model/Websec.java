package com.cn.ctbri.model;

import java.util.Date;

public class Websec {
    private Long logId;

    private Integer resourceId;

    private String resourceUri;

    private String resourceIp;

    private String siteId;

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

    private String policyId;

    private String ruleId;

    private String action;

    private String block;

    private String blockInfo;

    private String alertinfo;

    private String proxyInfo;

    private String characters;

    private String countNum;

    private String protocolType;

    private String wci;

    private String wsi;

    private Integer ipLatlongValid;

    private String srcCountryCode;

    private String srcCountry;

    private String srcSubdivision1;

    private String srcSubdivision2;

    private String srcCity;

    private String srcLatitude;

    private String srcLongitude;

    private String dstCountryCode;

    private String dstCountry;

    private String dstSubdivision1;

    private String dstSubdivision2;

    private String dstCity;

    private String dstLatitude;

    private String dstLongitude;

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
        this.resourceUri = resourceUri == null ? null : resourceUri.trim();
    }

    public String getResourceIp() {
        return resourceIp;
    }

    public void setResourceIp(String resourceIp) {
        this.resourceIp = resourceIp == null ? null : resourceIp.trim();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId == null ? null : siteId.trim();
    }

    public String getProtectId() {
        return protectId;
    }

    public void setProtectId(String protectId) {
        this.protectId = protectId == null ? null : protectId.trim();
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp == null ? null : dstIp.trim();
    }

    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort == null ? null : dstPort.trim();
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp == null ? null : srcIp.trim();
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort == null ? null : srcPort.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public String getAlertlevel() {
        return alertlevel;
    }

    public void setAlertlevel(String alertlevel) {
        this.alertlevel = alertlevel == null ? null : alertlevel.trim();
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType == null ? null : eventType.trim();
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
        this.policyId = policyId == null ? null : policyId.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block == null ? null : block.trim();
    }

    public String getBlockInfo() {
        return blockInfo;
    }

    public void setBlockInfo(String blockInfo) {
        this.blockInfo = blockInfo == null ? null : blockInfo.trim();
    }

    public String getAlertinfo() {
        return alertinfo;
    }

    public void setAlertinfo(String alertinfo) {
        this.alertinfo = alertinfo == null ? null : alertinfo.trim();
    }

    public String getProxyInfo() {
        return proxyInfo;
    }

    public void setProxyInfo(String proxyInfo) {
        this.proxyInfo = proxyInfo == null ? null : proxyInfo.trim();
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters == null ? null : characters.trim();
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum == null ? null : countNum.trim();
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType == null ? null : protocolType.trim();
    }

    public String getWci() {
        return wci;
    }

    public void setWci(String wci) {
        this.wci = wci == null ? null : wci.trim();
    }

    public String getWsi() {
        return wsi;
    }

    public void setWsi(String wsi) {
        this.wsi = wsi == null ? null : wsi.trim();
    }

    public Integer getIpLatlongValid() {
        return ipLatlongValid;
    }

    public void setIpLatlongValid(Integer ipLatlongValid) {
        this.ipLatlongValid = ipLatlongValid;
    }

    public String getSrcCountryCode() {
        return srcCountryCode;
    }

    public void setSrcCountryCode(String srcCountryCode) {
        this.srcCountryCode = srcCountryCode == null ? null : srcCountryCode.trim();
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

    public String getSrcSubdivision2() {
        return srcSubdivision2;
    }

    public void setSrcSubdivision2(String srcSubdivision2) {
        this.srcSubdivision2 = srcSubdivision2 == null ? null : srcSubdivision2.trim();
    }

    public String getSrcCity() {
        return srcCity;
    }

    public void setSrcCity(String srcCity) {
        this.srcCity = srcCity == null ? null : srcCity.trim();
    }

    public String getSrcLatitude() {
        return srcLatitude;
    }

    public void setSrcLatitude(String srcLatitude) {
        this.srcLatitude = srcLatitude == null ? null : srcLatitude.trim();
    }

    public String getSrcLongitude() {
        return srcLongitude;
    }

    public void setSrcLongitude(String srcLongitude) {
        this.srcLongitude = srcLongitude == null ? null : srcLongitude.trim();
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

    public byte[] getHttp() {
        return http;
    }

    public void setHttp(byte[] http) {
        this.http = http;
    }
}