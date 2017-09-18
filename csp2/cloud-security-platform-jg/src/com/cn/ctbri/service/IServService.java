package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.Advertisement;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Service;
//import com.cn.ctbri.entity.Service;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-15
 * 描        述：  服务接口类
 * 版        本：  1.0
 */
public interface IServService {
	/**
	 * 功能描述： 根据id查询服务
	 * 参数描述： int serviceid
	 *		 @time 2015-1-15
	 *	返回值 ：Service
	 */
	Serv findById(int serviceid);

	/**
     * 功能描述： 根据条件查询服务
     * 参数描述： Serv service
     *       @time 2015-1-21
     *  返回值 ：Serv
     */
    List<Serv> findServiceByParam(Serv service);

    /**
     * 查询所有服务
     * @return
     */
    List<Serv> findAllService();
    
    /**
     * 功能描述：添加广告
     *       @time 2016-06-12
     */
    int insert(Serv service);

	void updateById(Serv service);

	void deleteById(int serviceId);
}
