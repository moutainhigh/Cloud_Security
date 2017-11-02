package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AdvertisementDao;
import com.cn.ctbri.entity.Advertisement;
import com.cn.ctbri.service.IAdvertisementService;
/**
 * 创 建 人  ：  张少华
 * 创建日期  ：  2016-06-12
 * 描        述：  广告业务层实现类
 * 版        本：  1.0
 */
@Service
public class AdvertisementServiceImpl implements IAdvertisementService{

    @Autowired
    AdvertisementDao advertisementDao;
    /**
     * 功能描述：查询所有广告
     *       @time 2016-06-12
     */
    public List<Advertisement> findADbyType(int type) {
        return advertisementDao.findADbyType(type);
    }
    /**
     * 功能描述：添加广告
     *       @time 2016-06-12
     */
    public int insert(Advertisement advertisement) {
    	return advertisementDao.addAdvertisement(advertisement);
    }
    /**
     * 功能描述：删除广告
     *       @time 2016-06-12
     */
    public void delete(int id) {
    	advertisementDao.deleteAdvertisement(id);
    }
	public int getMaxOrderIndex(int type) {
		return advertisementDao.getMaxOrderIndex(type);
	}
	public void update(Advertisement advertisement) {
		advertisementDao.update(advertisement);
		
	}

}
