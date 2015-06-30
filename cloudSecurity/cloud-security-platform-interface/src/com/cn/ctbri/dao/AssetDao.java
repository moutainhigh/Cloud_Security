package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;
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
    List<OrderIP> getorderIP(OrderIP orderIP);
    
	/**
     * 功能描述： 根据订单查询要执行服务的资产集合
     * 参数描述：  OrderAsset orderAsset
     * @time 2015-1-28
     */
    List<Asset> getAssetByOrderId(Order order);
    
    /**
     * 根据任务查询对应的资产信息
     * @time 2014-01-02
     * @param task
     * @return 资产集合
     */
    List<Asset> getAssetByTask(Task task);
    
    Asset findAssetById(int id);

}
