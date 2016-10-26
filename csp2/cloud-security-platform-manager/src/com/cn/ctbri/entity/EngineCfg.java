package com.cn.ctbri.entity;

import java.io.Serializable;

/**
 * 创 建 人  ：  tang
 * 创建日期：  2015-06-012
 * 描        述：  引擎实体
 * 版        本：  1.0
 */
public class EngineCfg implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//主键Id
	private int id;
	//引擎编号
	private String engine_number;
	//引擎名称
	private String engine_name;
	//设备厂家
    private String equipment_factory;
	//引擎地址
	private String engine_addr;
	//引擎api链接地址
	private String engine_api;
	//引擎登录用户名
	private String username;
	//引擎登录密码
	private String password;
	//引擎能力
	private String engine_capacity;
	//引擎能力模板
	private String engine_capacity_model;
	//引擎支持的最大任务数
	private int maxTask;
	//引擎活跃度
	private int activity;
	//是否可用
	private int status;
	
	private int engine;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEngine_number() {
		return engine_number;
	}
	public void setEngine_number(String engine_number) {
		this.engine_number = engine_number;
	}
	public String getEngine_name() {
		return engine_name;
	}
	public void setEngine_name(String engine_name) {
		this.engine_name = engine_name;
	}
	public String getEquipment_factory() {
		return equipment_factory;
	}
	public void setEquipment_factory(String equipment_factory) {
		this.equipment_factory = equipment_factory;
	}
	public String getEngine_addr() {
		return engine_addr;
	}
	public void setEngine_addr(String engine_addr) {
		this.engine_addr = engine_addr;
	}
	public String getEngine_api() {
		return engine_api;
	}
	public void setEngine_api(String engine_api) {
		this.engine_api = engine_api;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEngine_capacity() {
		return engine_capacity;
	}
	public void setEngine_capacity(String engine_capacity) {
		this.engine_capacity = engine_capacity;
	}
	public String getEngine_capacity_model() {
		return engine_capacity_model;
	}
	public void setEngine_capacity_model(String engine_capacity_model) {
		this.engine_capacity_model = engine_capacity_model;
	}
	public int getMaxTask() {
		return maxTask;
	}
	public void setMaxTask(int maxTask) {
		this.maxTask = maxTask;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    public int getActivity() {
        return activity;
    }
    public void setActivity(int activity) {
        this.activity = activity;
    }
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getEngine() {
		return engine;
	}
	public void setEngine(int engine) {
		this.engine = engine;
	}
	
}
