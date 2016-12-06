package com.cn.ctbri.entity;
/**
 * 创 建 人  ：  txr
 * 创建日期：   2015-1-14
 * 描        述：  服务类型实体类
 * 版        本：  1.0
 */
public class ServiceType {
    //主键id
	private int id;
	//服务类型名称
	private String name;
	
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
	
	
}
