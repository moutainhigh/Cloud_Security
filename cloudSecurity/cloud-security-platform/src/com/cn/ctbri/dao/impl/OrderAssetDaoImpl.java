package com.cn.ctbri.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import com.cn.ctbri.dao.OrderAssetDao;
import com.cn.ctbri.entity.OrderAsset;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-20
 * 描        述：   订单资产据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class OrderAssetDaoImpl extends SqlSessionDaoSupport implements OrderAssetDao{

	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}  
	
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
		
}
