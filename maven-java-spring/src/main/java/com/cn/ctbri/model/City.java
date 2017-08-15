package com.cn.ctbri.model;

public class City {
    private Long locationId;

    private String localeCode;

    private String continentCode;

    private String continentName;

    private String countryIsoCode;

    private String countryName;

    private String subdivision1IsoCode;

    private String subdivision1Name;

    private String subdivision2IsoCode;

    private String subdivision2Name;

    private String cityName;

    private String metroCode;

    private String timeZone;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode == null ? null : localeCode.trim();
    }

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode == null ? null : continentCode.trim();
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName == null ? null : continentName.trim();
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode == null ? null : countryIsoCode.trim();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName == null ? null : countryName.trim();
    }

    public String getSubdivision1IsoCode() {
        return subdivision1IsoCode;
    }

    public void setSubdivision1IsoCode(String subdivision1IsoCode) {
        this.subdivision1IsoCode = subdivision1IsoCode == null ? null : subdivision1IsoCode.trim();
    }

    public String getSubdivision1Name() {
        return subdivision1Name;
    }

    public void setSubdivision1Name(String subdivision1Name) {
        this.subdivision1Name = subdivision1Name == null ? null : subdivision1Name.trim();
    }

    public String getSubdivision2IsoCode() {
        return subdivision2IsoCode;
    }

    public void setSubdivision2IsoCode(String subdivision2IsoCode) {
        this.subdivision2IsoCode = subdivision2IsoCode == null ? null : subdivision2IsoCode.trim();
    }

    public String getSubdivision2Name() {
        return subdivision2Name;
    }

    public void setSubdivision2Name(String subdivision2Name) {
        this.subdivision2Name = subdivision2Name == null ? null : subdivision2Name.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getMetroCode() {
        return metroCode;
    }

    public void setMetroCode(String metroCode) {
        this.metroCode = metroCode == null ? null : metroCode.trim();
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone == null ? null : timeZone.trim();
    }
}