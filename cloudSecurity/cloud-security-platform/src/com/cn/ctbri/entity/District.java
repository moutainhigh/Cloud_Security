package com.cn.ctbri.entity;


/**
 * 创 建 人  ：  txr
 * 创建日期：   2014-08-20
 * 描        述：  地区实体类
 * 版        本：  1.0
 */
public class District {
	
	private int id;
	private String name;//地区名称
	private String longitude;//地图坐标-经度
	private String latitude;//地图坐标-纬度
	private String count;
	private String limit;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getLimit() {
        return limit;
    }
    public void setLimit(String limit) {
        this.limit = limit;
    }

    
	
}
