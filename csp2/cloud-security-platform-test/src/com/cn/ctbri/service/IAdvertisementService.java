package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.Advertisement;
/**
 * 创 建 人  ：  张少华
 * 创建日期：  2016-06-12
 * 描        述：  广告业务层接口
 * 版        本：  1.0
 */
public interface IAdvertisementService {
	
	/**
     * 功能描述：根据id查询广告
     *       @time 2016-06-12
     */
	Advertisement findById(int id);
	/**
	 * 功能描述：查询所有有效期内的广告
	 *		 @time 2016-06-12
	 */
	List<Advertisement> findAdvertisementByType(int type);
	/**
     * 功能描述：添加广告
     *       @time 2016-06-12
     */
    int insert(Advertisement advertisement);
    /**
     * 功能描述：删除广告
     *       @time 2016-06-12
     */
    void delete(int id);
    
    void update(Advertisement advertisement);
	
}
