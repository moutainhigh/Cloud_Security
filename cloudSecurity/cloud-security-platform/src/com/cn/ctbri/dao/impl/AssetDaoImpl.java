package com.cn.ctbri.dao.impl;


import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.AssetDao;
import com.cn.ctbri.entity.Asset;
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
}
