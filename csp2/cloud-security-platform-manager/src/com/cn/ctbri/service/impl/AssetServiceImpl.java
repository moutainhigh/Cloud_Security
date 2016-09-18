package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AssetDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.pager.PageBean;
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
    public List findorderIP(Map<String, Object> paramMap) {
        List list=assetDao.getorderIP(paramMap);
    	return list;
    }
    /**
     * 功能描述： 根据资产地址查询资产
     * 参数描述： String addr
     *       @time 2015-3-9
     */
	public List<Asset> findByAssetAddr(Map<String, Object> paramMap) {
		return assetDao.findByAssetAddr(paramMap);
	}
	 /**
     * 功能描述： 查询所有资产
     *       @time 2015-3-9
     */
	public List<Asset> findAllAssetAddr() {
		return assetDao.findAllAssetAddr();
	}
	/**
     * 功能描述： 分页
     *       @time 2015-3-16
     */
	public PageBean<Asset> queryByPage(Asset criteria,int pageCode) {
		try {
			int totalRecord = 0 ;
			List<Asset> list = assetDao.findAllAssetAddr();//获取总记录数
			if(list!=null &&list.size()>=0){
				totalRecord = list.size();
			}
			// 使用当前页码和总记录数创建PageBean
			PageBean<Asset> pb = new PageBean<Asset>(pageCode, totalRecord);
			// 查询本页记录
			List<Asset> datas = assetDao.queryByPage(criteria, (pageCode - 1) * pb.getPageSize(), pb.getPageSize());
			// 保存pageBean中
			pb.setDatas(datas);
			return pb;//返回pageBean
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 功能描述：资产地理位置统计分析
	 *      @time 2015-10-28
	 */
	public List<Asset> findByAssetProAndCity(Map<String, Object> paramMap) {
		return assetDao.findByAssetProAndCity(paramMap);
	}
	/**
	 * 功能描述：资产用途统计分析
	 *      @time 2015-10-29
	 */
	public List<Asset> findByAssetPurposeList(Map<String, Object> paramMap) {
		return assetDao.findByAssetPurposeList(paramMap);
	}
	public int getAssetCount() {
		return assetDao.getAssetCount();
	}
	public Asset findByOrderAssetId(int orderAssetId) {
		return assetDao.findByOrderAssetId(orderAssetId);
	}
	
	/**
	 * 功能描述：根据资产id获取资产
	 * 参数描述：int id
	 *		 @time 2015-1-21
	 * 返回值    ：Asset
	 */
	public Asset findById(int id,int userid) {
		Asset asset = assetDao.findById(id,userid);
		return asset;
	}
}
