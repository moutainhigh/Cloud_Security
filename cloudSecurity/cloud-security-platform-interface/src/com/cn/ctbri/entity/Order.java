package com.cn.ctbri.entity;
import java.io.Serializable;
import java.util.Date;
/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-05
 * 描        述：  订单实体
 * 版        本：  1.0
 */
public class Order implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//主键ID
	private long id;
	//订单类型(1：长期，2：单次)
	private int type;
	//开始时间
	private Date begin_date;
	//结束日期
	private Date end_date;
	//订单属于的服务类型
	private Service service;
	//下单日期
	private Date create_date;
	//任务起始日期
	private Date task_date;
	//扫描类型(1：每天，2：每周，3：每月)
	private int scan_type;
	//联系人
	private Contactor contactor;
	//备注
	private String remarks;
	/**
	 * 功能描述：取订单ID
	 *		 @time 2015-01-05
	 */
	public long getId() {
		return id;
	}
	/**
	 * 功能描述：设置订单主键
	 * 参数描述： long id 订单要设置的主键值
	 *		 @time 2015-01-05
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 功能描述：取订单类型
	 *		 @time 2015-01-05
	 */
	public int getType() {
		return type;
	}
	/**
	 * 功能描述：设置订单类型
	 * 参数描述： int type 订单要设置的类型值
	 *		 @time 2015-01-05
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * 功能描述：取订单开始日期
	 *		 @time 2015-01-05
	 */
	public Date getBegin_date() {
		return begin_date;
	}
	/**
	 * 功能描述：设置订单开始日期
	 * 参数描述： Date begin_date 订单要设置的开始日期值
	 *		 @time 2015-01-05
	 */
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}
	/**
	 * 功能描述：取订单结束日期
	 *		 @time 2015-01-05
	 */
	public Date getEnd_date() {
		return end_date;
	}
	/**
	 * 功能描述：设置订单结束日期
	 * 参数描述：Date end_date 订单要设置的结束日期值
	 *		 @time 2015-01-05
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	/**
	 * 功能描述：取订单服务类型实体
	 *		 @time 2015-01-05
	 */
	public Service getService() {
		return service;
	}
	/**
	 * 功能描述：设置订单服务类型实体
	 * 参数描述：Service service 订单要设置的服务类型实体值
	 *		 @time 2015-01-05
	 */
	public void setService(Service service) {
		this.service = service;
	}
	/**
	 * 功能描述：取订单创建日期
	 *		 @time 2015-01-05
	 */
	public Date getCreate_date() {
		return create_date;
	}
	/**
	 * 功能描述：设置订单创建日期
	 * 参数描述：Date create_date 订单要设置的创建日期值
	 *		 @time 2015-01-05
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	/**
	 * 功能描述：取订单任务起始日期
	 *		 @time 2015-01-05
	 */
	public Date getTask_date() {
		return task_date;
	}
	/**
	 * 功能描述：设置订单任务起始日期
	 * 参数描述：Date task_date 订单要设置的任务起始日期值
	 *		 @time 2015-01-05
	 */
	public void setTask_date(Date task_date) {
		this.task_date = task_date;
	}
	/**
	 * 功能描述：取订单扫描类型
	 *		 @time 2015-01-05
	 */
	public int getScan_type() {
		return scan_type;
	}
	/**
	 * 功能描述：设置订单扫描类型
	 * 参数描述：int scan_type 订单要设置的扫描类型值
	 *		 @time 2015-01-05
	 */
	public void setScan_type(int scan_type) {
		this.scan_type = scan_type;
	}
	/**
	 * 功能描述：取订单联系人
	 *		 @time 2015-01-05
	 */
	public Contactor getContactor() {
		return contactor;
	}
	/**
	 * 功能描述：设置订单联系人
	 * 参数描述：Contactor contactor 订单要设置的联系人值
	 *		 @time 2015-01-05
	 */
	public void setContactor(Contactor contactor) {
		this.contactor = contactor;
	}
	/**
	 * 功能描述：取订单备注
	 *		 @time 2015-01-05
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 功能描述：设置订单备注
	 * 参数描述：String remarks 订单要设置的备注值
	 *		 @time 2015-01-05
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
