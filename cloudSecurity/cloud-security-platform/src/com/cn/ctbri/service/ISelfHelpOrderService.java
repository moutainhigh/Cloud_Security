package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceType;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-14
 * 描        述：  自助下单层接口
 * 版        本：  1.0
 */
public interface ISelfHelpOrderService {
	
	/**
	 * 功能描述：查询服务配置类型
	 *		 @time 2015-1-14
	 * 返回值    ：  List<ServiceType>
	 */
	List<ServiceType> findServiceType();

	/**
     * 功能描述：查询厂商
     *       @time 2015-1-15
     * 返回值    ：  List<Factory>
     */
    List<Factory> findListFactory();

    /**
     * 功能描述：查询服务资产
     *       @time 2015-1-15
     * 返回值    ：  List<ServiceAsset>
     */
    List<Asset> findServiceAsset();

    /**
     * 功能描述：保存订单
     *       @time 2015-1-16
     */
    void insertOrder(Order order);

    /**
     * 功能描述：查询服务
     *       @time 2015-1-19
     * 返回值    ：  List<Serv>
     */
    List<Serv> findService();

	
}
