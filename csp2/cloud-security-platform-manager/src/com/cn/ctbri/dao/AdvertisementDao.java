package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Advertisement;
/**
 * 创 建 人  ：  张少华
 * 创建日期：  2016-06-12
 * 描        述：  广告数据访问层接口类
 * 版        本：  1.0
 */
public interface AdvertisementDao {
    /**
     * 功能描述：查询所有广告
     *       @time 2016-06-12
     */
    List<Advertisement> findADbyType(int type);

    /**
     * 功能描述：添加广告
     *       @time 2016-06-12
     */
    int addAdvertisement(Advertisement advertisement);
    /**
     * 功能描述：删除广告
     *       @time 2016-06-12
     */
    void deleteAdvertisement(int id);
    
    int getMaxOrderIndex(int type);
    
    void update(Advertisement advertisement);
}
