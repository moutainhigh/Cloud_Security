package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cn.ctbri.dao.OrderAssetDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;
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

	/**
     * 功能描述：保存服务资产
     *       @time 2015-1-20
     */
    public void insertOrderAsset(OrderAsset orderAsset) {
        orderAssetDao.insert(orderAsset);
    }

    /**
     * 功能描述：保存ip段
     *       @time 2015-1-20
     */
    public void insertOrderIP(OrderIP orderIP) {
        orderAssetDao.insertIP(orderIP);
    }
    /**
     * 功能描述：根据orderid查询OrderAsset
     *       @time 2015-1-27
     */
	public List<OrderAsset> findOrderAssetByOrderId(String orderId) {
		List<OrderAsset> list = orderAssetDao.findOrderAssetByOrderId(orderId);
		return list;
	}

	/**
     * 功能描述：根据orderid查询Asset名称
     *       @time 2015-2-2
     */
    public List<Asset> findAssetNameByOrderId(String orderId) {
        List<Asset> list = orderAssetDao.findAssetNameByOrderId(orderId);
        return list;
    }

    /**
     * 功能描述：根据orderid查询最近检测时间
     *       @time 2015-3-12
     */
    public List findLastTimeByOrderId(Map<String, Object> paramMap) {
        List lastTime = orderAssetDao.findLastTimeByOrderId(paramMap);
        return lastTime;
    }

    /**
     * 功能描述：根据orderid查询ip名称
     *       @time 2015-2-2
     */
    public List<OrderIP> findIpByOrderId(String orderId) {
        List<OrderIP> list = orderAssetDao.findIpByOrderId(orderId);
        return list;
    }

    //根据orderId删除订单资产
    public void deleteOaByOrderId(String orderId) {
        orderAssetDao.deleteOaByOrderId(orderId);
    }

	public List getOrdersByAsset(int assetId) {
		return orderAssetDao.getOrdersByAsset(assetId);
	}

	public List findAssetsByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return orderAssetDao.findAssetsByOrderId(orderId);
	}
	
}
