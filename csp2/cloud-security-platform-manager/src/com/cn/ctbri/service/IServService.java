package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Service;

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
    //查询全部服务
    List<Serv> findAllService();    
    /**
     * 功能描述：保存服务价格
     *       @time 2015-1-16
     */
    void insertPrice(Price price);
    /*
     * 删除价格
     */
    int delPrice(int serviceId);
    /*
     * 根据serviceid查询价格列表
     */
    List<Price> findPriceByServiceId(int serviceId);
    /*
     * 根据参数查询价格
     */
    List<Price> findPriceByParam(Map map);
}
