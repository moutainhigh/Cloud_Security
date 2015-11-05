package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AssetDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.Task;
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
	/**
	 * 功能描述：删除资产
	 * 参数描述：int id
	 *		 @time 2015-1-19
	 * 返回值    ：无
	 */
	public void delete(int id) {
		assetDao.delete(id);
	}
	/**
	 * 功能描述：联合搜索资产
	 * 参数描述：Asset asset
	 *		 @time 2015-1-19
	 * 返回值    ：List<Asset>
	 */
	public List<Asset> searchAssetsCombine(Asset asset) {
		List<Asset> list = assetDao.searchAssetsCombine(asset);
		return list;
	}
	/**
	 * 功能描述：根据资产id获取资产
	 * 参数描述：int id
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	public Asset findById(int id) {
		Asset asset = assetDao.findById(id);
		return asset;
	}
	/**
	 * 功能描述：更新资产状态
	 * 参数描述：Asset asset
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	public void updateAsset(Asset asset) {
		assetDao.updateAsset(asset);
	}
	
	/**
     * 功能描述： 根据条件查询服务资产
     * 参数描述：  OrderAsset orderAsset
     *       @time 2015-1-21
     */
    public List<Asset> findorderAssetByServId(OrderAsset orderAsset) {
        return assetDao.getorderAssetByServId(orderAsset);
    }
    /**
     * 功能描述： 根据条件查询服务IP段
     * 参数描述：  OrderIP orderIP
     *       @time 2015-1-21
     */
    public List<OrderIP> findorderIP(OrderIP orderIP) {
        return assetDao.getorderIP(orderIP);
    }
    
    /**
     * 根据任务获取资产信息
     * @param task
     * @return
     */
	public List<Asset> findByTask(Task task) {
		return assetDao.getAssetByTask(task);
	}
    public Asset findAssetById(int id) {
        return assetDao.findAssetById(id);
    }
	
}
