package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.Asset;



/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-16
 * 描        述：  资产业务层接口
 * 版        本：  1.0
 */
public interface IAssetService {
	/**
	 * 功能描述：根据用户名查询订单
	 * 参数描述：int id
	 *		 @time 2015-1-16
	 * 返回值    ：  List
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

	
}
