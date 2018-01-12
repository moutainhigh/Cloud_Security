package com.cn.ctbri.model;

public class Ip {
    private Long latlongId;

    private String network;

    private int netmask;

    private Long startip;

    private Long endip;

    private Long locationId;

    private Long registeredCountryLocationId;

    private Long representedCountryLocationId;

    private String isAnonymousProxy;

    private String isSatelliteProvider;

    private String postalCode;

    private String latitude;

    private String longitude;

    private String accuracyRadius;

    public Long getLatlongId() {
        return latlongId;
    }

    public void setLatlongId(Long latlongId) {
        this.latlongId = latlongId;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network == null ? null : network.trim();
    }

    public int getNetmask() {
        return netmask;
    }

    public void setNetmask(int netmask) {
        this.netmask = netmask ;
    }

    public Long getStartip() {
        return startip;
    }

    public void setStartip(Long startip) {
        this.startip = startip;
    }

    public Long getEndip() {
        return endip;
    }

    public void setEndip(Long endip) {
        this.endip = endip;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getRegisteredCountryLocationId() {
        return registeredCountryLocationId;
    }

    public void setRegisteredCountryLocationId(Long registeredCountryLocationId) {
        this.registeredCountryLocationId = registeredCountryLocationId;
    }

    public Long getRepresentedCountryLocationId() {
        return representedCountryLocationId;
    }

    public void setRepresentedCountryLocationId(Long representedCountryLocationId) {
        this.representedCountryLocationId = representedCountryLocationId;
    }

    public String getIsAnonymousProxy() {
        return isAnonymousProxy;
    }

    public void setIsAnonymousProxy(String isAnonymousProxy) {
        this.isAnonymousProxy = isAnonymousProxy == null ? null : isAnonymousProxy.trim();
    }

    public String getIsSatelliteProvider() {
        return isSatelliteProvider;
    }

    public void setIsSatelliteProvider(String isSatelliteProvider) {
        this.isSatelliteProvider = isSatelliteProvider == null ? null : isSatelliteProvider.trim();
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode == null ? null : postalCode.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getAccuracyRadius() {
        return accuracyRadius;
    }

    public void setAccuracyRadius(String accuracyRadius) {
        this.accuracyRadius = accuracyRadius == null ? null : accuracyRadius.trim();
    }
}