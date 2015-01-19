package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AssetDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.service.IAssetService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-16
 * 描        述：  资产业务层实现类
 * 版        本：  1.0
 */
@Service
public class AssetServiceImpl implements IAssetService{

	@Autowired
	AssetDao assetDao;
	/**
	 * 功能描述：根据用户名查询订单
	 * 参数描述：int id
	 *		 @time 2015-1-16
	 * 返回值    ：  List<Asset>
	 */
	public List<Asset> findByUserId(int id) {
		List<Asset> list = assetDao.findByUserId(id);
		return list;
	}
	/**
	 * 功能描述：新增资产
	 * 参数描述：Asset asset
	 *		 @time 2015-1-16
	 * 返回值    ：无
	 */
	public void saveAsset(Asset asset) {
		assetDao.saveAsset(asset);
	}
	
}
