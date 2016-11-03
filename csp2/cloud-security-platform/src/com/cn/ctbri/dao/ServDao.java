package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Serv;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-15
 * 描        述： 用户数据访问层接口类
 * 版        本：  1.0
 */
public interface ServDao {

	
	/**
	 * 功能描述： 根据id查询服务
	 * 参数描述： int serviceid
	 *		 @time 2015-1-15
	 *	返回值 ：Serv
	 */
	Serv findById(int serviceid);

	/**
     * 功能描述： 根据条件查询服务
     * 参数描述： Serv service
     *       @time 2015-1-21
     *  返回值 ：Serv
     */
    List<Serv> getServiceByParam(Serv service);

    /**
     * 查询所有服务
     * @return
     */
    List<Serv> findAllService();

	int insert(Serv service);

	void updateById(Serv service);

	void deleteById(int serviceId);
	
}
