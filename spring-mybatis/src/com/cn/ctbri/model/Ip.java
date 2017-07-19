package com.cn.ctbri.model;

public class Ip {
    private String latlongId;

    private String network;

    private String netmask;

    private String startip;

    private String endip;

    private String locationId;

    private String registeredCountryLocationId;

    private String representedCountryLocationId;

    private String isAnonymousProxy;

    private String isSatelliteProvider;

    private String postalCode;

    private String latitude;

    private String longitude;

    private String accuracyRadius;

    public String getLatlongId() {
        return latlongId;
    }

    public void setLatlongId(String latlongId) {
        this.latlongId = latlongId == null ? null : latlongId.trim();
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network == null ? null : network.trim();
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask == null ? null : netmask.trim();
    }

    public String getStartip() {
        return startip;
    }

    public void setStartip(String startip) {
        this.startip = startip == null ? null : startip.trim();
    }

    public String getEndip() {
        return endip;
    }

    public void setEndip(String endip) {
        this.endip = endip == null ? null : endip.trim();
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId == null ? null : locationId.trim();
    }

    public String getRegisteredCountryLocationId() {
        return registeredCountryLocationId;
    }

    public void setRegisteredCountryLocationId(String registeredCountryLocationId) {
        this.registeredCountryLocationId = registeredCountryLocationId == null ? null : registeredCountryLocationId.trim();
    }

    public String getRepresentedCountryLocationId() {
        return representedCountryLocationId;
    }

    public void setRepresentedCountryLocationId(String representedCountryLocationId) {
        this.representedCountryLocationId = representedCountryLocationId == null ? null : representedCountryLocationId.trim();
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