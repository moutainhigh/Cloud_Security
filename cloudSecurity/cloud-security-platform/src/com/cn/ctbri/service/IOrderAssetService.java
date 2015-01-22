package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.OrderAsset;

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

	
}
