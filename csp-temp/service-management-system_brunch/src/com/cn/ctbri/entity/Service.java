package com.cn.ctbri.entity;

import java.io.Serializable;

/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-05
 * 描        述：  服务实体
 * 版        本：  1.0
 * <br>已废弃
 * @see Serv实体类
 */
@Deprecated
public class Service  implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//主键ID
	private long id;
	//服务名称
	private String name;
	//服务厂家(先存名称)
	private String factory;
	//模板名称
	private String module_name;
	//类型(1：扫描类，2：监控类，3：防护类，4：其他)
	private int type;
	//服务状态(1：可用，0：不可用)
	private int status;
	//备注
	private String remarks;
	/**
	 * 功能描述：取服务ID
	 *		 @time 2015-01-05
	 */
	public long getId() {
		return id;
	}
	/**
	 * 功能描述：设置服务主键
	 * 参数描述： long id 服务要设置的主键值
	 *		 @time 2015-01-05
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 功能描述：取服务名称
	 *		 @time 2015-01-05
	 */
	public String getName() {
		return name;
	}
	/**
	 * 功能描述：设置服务名称
	 * 参数描述： String name 服务要设置的名称值
	 *		 @time 2015-01-05
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 功能描述：取服务厂家名称
	 *		 @time 2015-01-05
	 */
	public String getFactory() {
		return factory;
	}
	/**
	 * 功能描述：设置服务厂商名称
	 * 参数描述： String factory 服务要设置的厂商名称值
	 *		 @time 2015-01-05
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}
	/**
	 * 功能描述：取服务模板名称
	 *		 @time 2015-01-05
	 */
	public String getModule_name() {
		return module_name;
	}
	/**
	 * 功能描述：设置服务模板名称
	 * 参数描述： String module_name 服务要设置的模板名称值
	 *		 @time 2015-01-05
	 */
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	/**
	 * 功能描述：取服务类型
	 *		 @time 2015-01-05
	 */
	public int getType() {
		return type;
	}
	/**
	 * 功能描述：设置服务类型
	 * 参数描述： int type 服务要设置的类型值
	 *		 @time 2015-01-05
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * 功能描述：取服务状态
	 *		 @time 2015-01-05
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 功能描述：设置服务状态
	 * 参数描述： int status 服务要设置的状态值
	 *		 @time 2015-01-05
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * 功能描述：取服务备注
	 *		 @time 2015-01-05
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 功能描述：设置服务备注
	 * 参数描述： String remarks 服务要设置的备注值
	 *		 @time 2015-01-05
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
