package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-20
 * 描        述：  订单资产业务层接口
 * 版        本：  1.0
 */
public interface IOrderAssetService {
	/**
	 * 功能描述：根据资产id查询订单
	 * 参数描述：int id
	 *		 @time 2015-1-20
	 * 返回值    ：  List<OrderAsset>
	 */
	List<OrderAsset> findAssetById(int assetId);

	/**
     * 功能描述：保存服务资产
     *       @time 2015-1-20
     */
    void insertOrderAsset(OrderAsset orderAsset);

    /**
     * 功能描述：保存ip段
     *       @time 2015-1-20
     */
    void insertOrderIP(OrderIP orderIP);
    /**
     * 功能描述：根据orderid查询OrderAsset
     *       @time 2015-1-27
     */
	List<OrderAsset> findOrderAssetByOrderId(String orderId);

	/**
     * 功能描述：根据orderid查询Asset名称
     *       @time 2015-2-2
     */
    List<Asset> findAssetNameByOrderId(String orderId);

    /**
     * 功能描述：根据orderid查询最近检测时间
     *       @time 2015-2-12
     */
    List findLastTimeByOrderId(Map<String, Object> paramMap);

    /**
     * 功能描述：根据orderid查询ip名称
     *       @time 2015-2-2
     */
    List<OrderIP> findIpByOrderId(String orderId);

    //根据orderId删除订单资产
    void deleteOaByOrderId(String orderId);

	List getOrdersByAsset(int assetId);

	List findAssetsByOrderId(String orderId);

	
}
