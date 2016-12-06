package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.ApiPrice;

/**
 * 创 建 人  ：  张少华
 * 创建日期：  2016-7-27
 * 描        述：  API价格接口类
 * 版        本：  1.0
 */
public interface IApiPriceService {
	
	/*
     * 根据serviceid查询价格列表
     */
    List<ApiPrice> findPriceByServiceId(int serviceId);
    
    ApiPrice findPrice(int serviceId, int num);
    
    /*
     * 删除价格
     */
    int delPrice(int serviceId);
    
    /*
     * 保存价格
     */
    void insertPrice(ApiPrice price);

}
