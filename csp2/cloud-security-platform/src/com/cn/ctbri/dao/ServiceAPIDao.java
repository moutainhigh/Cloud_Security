package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceAPI;


/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-28
 * 描        述： 服务API数据访问层接口类
 * 版        本：  1.0
 */
public interface ServiceAPIDao {

	
	/**
	 * 功能描述： 根据id查询服务API
	 * 参数描述： int apiId
	 *		 @time 2016-3-28
	 *	返回值 ：ServiceAPI
	 */
	ServiceAPI findById(int apiId);

	List<ServiceAPI> findServiceAPI();

	
}
