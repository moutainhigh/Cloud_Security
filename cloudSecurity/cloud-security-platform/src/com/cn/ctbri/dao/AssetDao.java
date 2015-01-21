package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Asset;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-16
 * 描        述：  资产数据访问层接口类
 * 版        本：  1.0
 */
public interface AssetDao {
	/**
	 * 功能描述：根据用户名查询订单
	 * 参数描述：int id
	 *		 @time 2015-1-16
	 * 返回值    ：List<Assert>
	 */
	List<Asset> findByUserId(int id);
	/**
	 * 功能描述：新增资产
	 * 参数描述：Asset asset
	 *		 @time 2015-1-16
	 * 返回值    ：无
	 */
	void saveAsset(Asset asset);
	/**
	 * 功能描述：删除资产
	 * 参数描述：int id
	 *		 @time 2015-1-19
	 * 返回值    ：无
	 */
	void delete(int id);
	/**
	 * 功能描述：联合搜索资产
	 * 参数描述：Asset asset
	 *		 @time 2015-1-19
	 * 返回值    ：List<Asset>
	 */
	List<Asset> searchAssetsCombine(Asset asset);
	/**
	 * 功能描述：根据资产id获取资产
	 * 参数描述：int id
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	Asset findById(int id);
	/**
	 * 功能描述：更新资产状态
	 * 参数描述：Asset asset
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	void updateAsset(Asset asset);

}
