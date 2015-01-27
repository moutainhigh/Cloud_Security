package com.cn.ctbri.entity;

import java.io.Serializable;
import java.sql.Date;

public class Task implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 任务id
	 */
	private Long teskId;
	
	/**
	 * 订单资产详情Id
	 */
	private Long order_asset_Id;
	
	/**
	 * 任务执行时间
	 */
	private Date execute_time;
	
	/**
	 * 任务状态
	 */
	private int status;
	
	/**
	 * 备注
	 */
	private String remarks;
}
