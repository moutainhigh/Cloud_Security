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
	private int count1;
	private int count2;
	private int count3;
	private int count4;
	private int count5;
	private String limit;
	private int siteCount;  //高危网站数量
	private int wafAlarmCount; //WAF告警数量
	
	private String count;
	
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
    
	public String getLimit() {
        return limit;
    }
    public void setLimit(String limit) {
        this.limit = limit;
    }
	public int getSiteCount() {
		return siteCount;
	}
	public void setSiteCount(int siteCount) {
		this.siteCount = siteCount;
	}
	public int getWafAlarmCount() {
		return wafAlarmCount;
	}
	public void setWafAlarmCount(int wafAlarmCount) {
		this.wafAlarmCount = wafAlarmCount;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public int getCount1() {
		return count1;
	}
	public void setCount1(int count1) {
		this.count1 = count1;
	}
	public int getCount2() {
		return count2;
	}
	public void setCount2(int count2) {
		this.count2 = count2;
	}
	public int getCount3() {
		return count3;
	}
	public void setCount3(int count3) {
		this.count3 = count3;
	}
	public int getCount4() {
		return count4;
	}
	public void setCount4(int count4) {
		this.count4 = count4;
	}
	public int getCount5() {
		return count5;
	}
	public void setCount5(int count5) {
		this.count5 = count5;
	}
}
