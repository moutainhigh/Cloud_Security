package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cn.ctbri.dao.OrderAssetDao;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.service.IOrderAssetService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-20
 * 描        述：  订单资产业务层实现类
 * 版        本：  1.0
 */
@Service
public class OrderAssetServiceImpl implements IOrderAssetService{

	@Autowired
	OrderAssetDao orderAssetDao;

	public List<OrderAsset> findAssetById(int assetId) {
		List<OrderAsset> list = orderAssetDao.findAssetById(assetId);
		return list;
	}
	
}
