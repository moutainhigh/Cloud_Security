package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-20
 * 描        述：  订单资产数据访问层接口类
 * 版        本：  1.0
 */
public interface OrderAssetDao {
	/**
	 * 功能描述：查询所有订单
	 * 参数描述：int assetId
	 *		 @time 2015-1-20
	 * 返回值    ：  List<OrderAsset>
	 */
	List<OrderAsset> findAssetById(int assetId);

	/**
     * 功能描述：保存服务资产
     *       @time 2015-1-20
     */
    void insert(OrderAsset orderAsset);

    /**
     * 功能描述：保存ip段
     *       @time 2015-1-20
     */
    void insertIP(OrderIP orderIP);
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
     *       @time 2015-3-12
     */
    List findLastTimeByOrderId(Map<String, Object> paramMap);

    /**
     * 功能描述：根据orderid查询ip名称
     *       @time 2015-2-2
     */
    List<OrderIP> findIpByOrderId(String orderId);

    //根据orderId删除订单资产
    void deleteOaByOrderId(String orderId,int userId);

	List getOrdersByAsset(int assetId);

	List<OrderAsset> findOrderAssetId(Map<String, Object> param);

	//add by tangxr 2016-5-5
	//查询订单的资产 
	List findAssetsByOrderId(String orderId);

	void update(OrderAsset oa);

	List<OrderAsset> findRunAssetById(int id);

	OrderAsset findOrderAssetById(int orderAssetId);

}
