package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Price;

/**
 * 创 建 人  ：  彭东梅
 * 创建日期：  2016-5-19
 * 描        述：  服务价格DAO类
 * 版        本：  1.0
 */
public interface PriceDao {
    /*
     * 保存价格
     */
    void insertPrice(Price price);
    /*
     * 删除价格
     */
    int delPrice(int serviceId);
    /*
     * 根据serviceid查询价格列表
     */
    List<Price> findPriceByServiceId(int serviceId,int type);
    /*
     * 根据serviceid查询scanType为空的价格列表
     */
	List<Price> findPriceByScanTypeNull(int serviceId);
	
	/*
     * 根据serviceid和选类型查询价格列表
     */
	List<Price> findPriceByServiceIdAndType(String serviceId, String type);
}
