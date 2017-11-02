package com.cn.ctbri.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.AdvertisementDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.Advertisement;
/**
 * 创 建 人  ：  张少华
 * 创建日期：  2016-06-12
 * 描        述：   广告数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class AdvertisementDaoImpl extends DaoCommon implements AdvertisementDao{

	/**
	 * 功        能： AdvertisementMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.AdvertisementMapper.";		

	/**
     * 功能描述：查询所有广告
     *       @time 2016-06-12
     */
	public List<Advertisement> findADbyType(int type) {
		return getSqlSession().selectList(ns + "findADbyType", type);

	}

	/**
     * 功能描述：添加广告
     *       @time 2016-06-12
     */
    public int addAdvertisement(Advertisement advertisement) {
        return this.getSqlSession().insert(ns + "insert", advertisement);
    }

    /**
     * 功能描述：删除广告
     *       @time 22016-06-12
     */
    public void deleteAdvertisement(int id) {
        getSqlSession().delete(ns +"delete",id);
    }

	public int getMaxOrderIndex(int type) {
		Integer index = getSqlSession().selectOne(ns + "findMaxOrderIndex", type);
		if (index == null) {
			return 0;
		}
		return index;
	}

	public void update(Advertisement advertisement) {
		getSqlSession().update(ns+"update", advertisement);
	}

}
