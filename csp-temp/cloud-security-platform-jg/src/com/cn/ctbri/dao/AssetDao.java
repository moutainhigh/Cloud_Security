package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;

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
	 * 功能描述：根据用户名查询订单addr
	 * @param userId
	 * @return
	 */
	List<String> findDomainByUserId(int userId);
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
	List<Asset> searchAssetsCombine(Map<String, Object> map);
	/**
	 * 功能描述：根据资产id获取资产
	 * 参数描述：int id
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	Asset findById(int id,int userid);
	/**
	 * 功能描述：更新资产状态
	 * 参数描述：Asset asset
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	void updateAsset(Asset asset);
	
	/**
     * 功能描述： 根据条件查询服务资产
     * 参数描述：  OrderAsset orderAsset
     *       @time 2015-1-21
     */
    List<Asset> getorderAssetByServId(OrderAsset orderAsset);
    /**
     * 功能描述： 根据条件查询服务IP段
     * 参数描述：  OrderIP orderIP
     *       @time 2015-1-21
     */
    List<Order> getorderIP(Map<String, Object> paramMap);
    /**
     * 功能描述： 根据资产地址查询资产
     * 参数描述：  String addr
     *       @time 2015-3-9
     */
	List<Asset> findByAssetAddr(Map<String, Object> paramMap);
	 /**
     * 功能描述： 查询所有资产
     *       @time 2015-3-9
     */
	List<Asset> findAllAssetAddr();
	/**
	 * 分页
	 * @param criteria
	 * @param pageCode
	 * @return
	 */
	List<Asset> queryByPage(Asset criteria, int i, int pageSize);
	
	List<Asset> getAssetByTask(Task task);
	
	Asset findByOrderAssetId(int orderAssetId);
	 /**
     * 功能描述： 查询所有资产
     *       @time 2015-3-9
     */
	List<Asset> findAllAssetInfo();
	 /**
     * 功能描述： 查询所有资产
     *       @time 2016-8-24
     */
	String getProvinceIdByName(String provinceName);
}
