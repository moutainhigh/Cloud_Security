package com.cn.ctbri.dao.impl;


import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.AssetDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.Task;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-16
 * 描        述：   资产据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class AssetDaoImpl extends SqlSessionDaoSupport implements AssetDao{

	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}  
	
	/**
	 * 功        能： AssetMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.AssetMapper.";
	/**
	 * 功能描述：根据用户名查询订单
	 * 参数描述：int id
	 *		 @time 2015-1-16
	 * 返回值    ：  List
	 */
	public List<Asset> findByUserId(int userid) {
		List<Asset> list = this.getSqlSession().selectList(ns + "findByUserId",userid);
		return list;
	}
	/**
	 * 功能描述：新增资产
	 * 参数描述：Asset asset
	 *		 @time 2015-1-16
	 * 返回值    ：无
	 */
	public void saveAsset(Asset asset) {
		this.getSqlSession().insert(ns + "saveAsset", asset);
	}
	/**
	 * 功能描述：删除资产
	 * 参数描述：int id
	 *		 @time 2015-1-19
	 * 返回值    ：无
	 */
	public void delete(int id) {
		this.getSqlSession().delete(ns +"delete",id);
	}
	/**
	 * 功能描述：联合搜索资产
	 * 参数描述：Asset asset
	 *		 @time 2015-1-19
	 * 返回值    ：List<Asset>
	 */
	public List<Asset> searchAssetsCombine(Asset asset) {
		List<Asset> list = this.getSqlSession().selectList(ns +"searchAssetsCombine",asset);
		return list;
	}
	/**
	 * 功能描述：根据资产id获取资产
	 * 参数描述：int id
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	public Asset findById(int id) {
		Asset asset = this.getSqlSession().selectOne(ns+"findById", id);
		return asset;
	}
	/**
	 * 功能描述：更新资产状态
	 * 参数描述：Asset asset
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	public void updateAsset(Asset asset) {
		this.getSqlSession().update(ns+"updateAsset", asset);
	}	
	
	/**
     * 功能描述： 根据条件查询服务资产
     * 参数描述：  OrderAsset orderAsset
     *       @time 2015-1-21
     */
    public List<Asset> getorderAssetByServId(OrderAsset orderAsset) {
        List list = this.getSqlSession().selectList(ns+"findorderAssetByServId", orderAsset);
        return list;
    }
    /**
     * 功能描述： 根据条件查询服务IP段
     * 参数描述：  OrderIP orderIP
     *       @time 2015-1-21
     */
    public List<OrderIP> getorderIP(OrderIP orderIP) {
        List list = this.getSqlSession().selectList(ns+"findorderIP", orderIP);
        return list;
    }
    
	public List<Asset> getAssetByOrderId(Order order) {
        List list = this.getSqlSession().selectList(ns+"getAssetByOrderId", order);
        return list;
	}
	
	public List<Asset> getAssetByTask(Task task) {
        List list = this.getSqlSession().selectList(ns+"getAssetByTask", task);
        return list;
	}
    public Asset findAssetById(int id) {
        Asset asset = this.getSqlSession().selectOne(ns+"findAssetById", id);
        return asset;
    }
}
