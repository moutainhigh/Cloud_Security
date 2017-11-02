package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.PartnerDao;
import com.cn.ctbri.entity.Partner;
import com.cn.ctbri.service.IPartnerService;
/**
 * 创 建 人  ： txr
 * 创建日期：  2016-11-30
 * 描        述：  合作方业务层实现类
 * 版        本：  1.0
 */
@Service
public class PartnerServiceImpl implements IPartnerService{

    @Autowired
    PartnerDao partnerDao;
    /**
     * 功能描述：查询所有合作方
     *       @time 2016-11-30
     */
    public List<Partner> findAllPartner() {
        return partnerDao.findAllPartner();
    }
    /**
     * 功能描述：添加合作方
     *       @time 2016-11-30
     */
    public void insert(Partner partner) {
    	partnerDao.addPartner(partner);
    }
    /**
     * 功能描述：删除合作方
     *       @time 2016-11-30
     */
    public void delete(String oldName) {
    	partnerDao.deletePartner(oldName);
    }
    /**
     * 功能描述：修改合作方
     *       @time 2016-11-30
     */
    public void update(Partner partner) {
    	partnerDao.updatePartner(partner);
    }
    /**
     * 功能描述：查询合作方
     *       @time 2016-11-30
     */
    public Partner findPartnerById(Partner partner) {
        return partnerDao.findPartnerById(partner);
    }
    /**
     * 功能描述：根据条件查询合作方
     *       @time 2016-11-30
     */
	public List<Partner> findPartnerByParam(Map<String, Object> map) {
		return partnerDao.findPartnerByParam(map);
	}

}
