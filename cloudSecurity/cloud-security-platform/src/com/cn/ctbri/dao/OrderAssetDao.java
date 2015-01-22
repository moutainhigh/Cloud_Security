package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.OrderAsset;

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

}
