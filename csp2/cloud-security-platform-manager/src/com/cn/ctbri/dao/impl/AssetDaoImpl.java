package com.cn.ctbri.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.AssetDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.City;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.entity.OrderAsset;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-16
 * 描        述：   资产据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class AssetDaoImpl extends DaoCommon implements AssetDao{

	
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
    public List getorderIP(Map<String, Object> paramMap) {
        List list = this.getSqlSession().selectList(ns+"findorderIP", paramMap);
        return list;
    }
    /**
     * 功能描述： 根据资产地址查询资产
     * 参数描述： String addr
     *       @time 2015-3-9
     */
	public List<Asset> findByAssetAddr(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"findByAssetAddr", paramMap);
	}
	 /**
     * 功能描述： 查询所有资产
     *       @time 2015-3-9
     */
	public List<Asset> findAllAssetAddr() {
		return this.getSqlSession().selectList(ns+"findAllAssetAddr");
	}
	/**
	 * 分页
	 */
	public List<Asset> queryByPage(Asset criteria, int offset, int pageSize) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pageNow", offset);
        paramMap.put("pageSize", pageSize);
        List<Asset> list = getSqlSession().selectList(ns +"queryByPage",paramMap);
		return list;
	}
	/**
	 * 功能描述：资产地理位置统计分析
	 */
	public List<Asset> findByAssetProAndCity(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"findAssetByProviceAndCity", paramMap);
	}
	/**
	 * 功能描述：资产用途统计分析
	 */
	public List<Asset> findByAssetPurposeList(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"findAssertPurpose", paramMap);
	}
	public int getAssetCount() {
		return this.getSqlSession().selectOne(ns+"getAssetCount");
	}
    /**
     * 功能描述： 根据资产地址或者名称查询资产
     * 参数描述： String addr
     *        String name
     *       @time 2016-9-5
     */
	public List<Asset> findByAssetAddrOrName(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"findByAssetAddrOrName", paramMap);
	}
	public Asset findByOrderAssetId(int orderAssetId) {
		return this.getSqlSession().selectOne(ns+"findByOrderAssetId", orderAssetId);
	}
	/**
	 * 功能描述：根据资产id获取资产
	 * 参数描述：int id
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	public Asset findById(int id,int userid) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userid", userid);
		Asset asset = this.getSqlSession().selectOne(ns+"findByIdAndUserId", map);
		return asset;
	}
	
	
}
