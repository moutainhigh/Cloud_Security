package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.OrderAssetDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-20
 * 描        述：   订单资产据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class OrderAssetDaoImpl extends DaoCommon implements OrderAssetDao{
	/**
	 * 功        能： AssetMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.OrderAssetMapper.";
	/**
	 * 功能描述：根据订单id查询资产订单
	 * 参数描述：int assetId
	 *		 @time 2015-1-20
	 * 返回值    ：  List<Order>
	 */
	public List<OrderAsset> findAssetById(int assetId) {
		List<OrderAsset> list = this.getSqlSession().selectList(ns+"findAssetById", assetId);
		return list;
	}
	/**
     * 功能描述：保存服务资产
     *       @time 2015-1-20
     */
    public void insert(OrderAsset orderAsset) {
        this.getSqlSession().insert(ns + "insert", orderAsset);
    }
    
    /**
     * 功能描述：保存ip段
     *       @time 2015-1-20
     */
    public void insertIP(OrderIP orderIP) {
        this.getSqlSession().insert(ns + "insertIP", orderIP);
    }
    /**
     * 功能描述：根据orderid查询OrderAsset
     *       @time 2015-1-27
     */
	public List<OrderAsset> findOrderAssetByOrderId(String orderId) {
		List<OrderAsset> list = this.getSqlSession().selectList(ns+"findOrderAssetByOrderId",orderId);
		return list;
	}
	/**
     * 功能描述：根据orderid查询Asset名称
     *       @time 2015-2-2
     */
    public List<Asset> findAssetNameByOrderId(String orderId) {
        List<Asset> list = this.getSqlSession().selectList(ns+"findAssetNameByOrderId",orderId);
        return list;
    }
    /**
     * 功能描述：根据orderid查询最近检测时间
     *       @time 2015-3-12
     */
    public List findLastTimeByOrderId(Map<String, Object> paramMap) {
        List list = this.getSqlSession().selectList(ns+"findLastTimeByOrderId",paramMap);
        return list;
    }
    /**
     * 功能描述：根据orderid查询ip名称
     *       @time 2015-2-2
     */
    public List<OrderIP> findIpByOrderId(String orderId) {
        List<OrderIP> list = this.getSqlSession().selectList(ns+"findIpByOrderId",orderId);
        return list;
    }
    
    //根据orderId删除订单资产
    public void deleteOaByOrderId(String orderId,int userId) {
       Map map = new HashMap();
       map.put("orderId", orderId);
       map.put("userId", userId);
        this.getSqlSession().delete(ns + "deleteOaByOrderId",map);
    }
	public List getOrdersByAsset(int assetId) {
		List list = this.getSqlSession().selectList(ns+"getOrdersByAsset", assetId);
		return list;
	}
	public List<OrderAsset> findOrderAssetId(Map<String, Object> param) {
		List<OrderAsset> list = this.getSqlSession().selectList(ns+"findOrderAssetId",param);
		return list;
	}
	
	//add by tangxr 2016-5-5
	//查询订单的资产 
	public List findAssetsByOrderId(String orderId) {
		List list = this.getSqlSession().selectList(ns+"findAssetsByOrderId",orderId);
		return list;
	}
	public void update(OrderAsset oa) {
		this.getSqlSession().update(ns + "update", oa);
	}
	public List<OrderAsset> findRunAssetById(int id) {
		List list = this.getSqlSession().selectList(ns+"findRunAssetById",id);
		return list;
	}
	public OrderAsset findOrderAssetById(int orderAssetId) {
		OrderAsset oa = this.getSqlSession().selectOne(ns+"findOrderAssetById",orderAssetId);
		return oa;
	}
		
}
