package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.PartnerDao;
import com.cn.ctbri.entity.Partner;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2016-11-30
 * 描        述：   合作方数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class PartnerDaoImpl extends DaoCommon implements PartnerDao{

	/**
	 * 功        能： PartnerMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.PartnerMapper.";		

	/**
     * 功能描述：查询所有合作方
     *       @time 2016-11-30
     */
	public List<Partner> findAllPartner() {
		return getSqlSession().selectList(ns + "list");

	}

	/**
     * 功能描述：添加合作方
     *       @time 2016-11-30
     */
    public void addPartner(Partner partner) {
        this.getSqlSession().insert(ns + "insert", partner);
    }

    /**
     * 功能描述：删除合作方
     *       @time 2016-11-30
     */
    public void deletePartner(String oldName) {
        getSqlSession().delete(ns +"delete",oldName);
    }

    /**
     * 功能描述：修改合作方
     *       @time 2016-11-30
     */
    public void updatePartner(Partner partner) {
        this.getSqlSession().update(ns + "update",partner);
    }

    /**
     * 功能描述：查询合作方
     *       @time 2016-11-30
     */
    public Partner findPartnerById(Partner partner) {
        return getSqlSession().selectOne(ns + "list",partner);
    }

	public List<Partner> findPartnerByParam(Map<String, Object> map) {
		return getSqlSession().selectList(ns + "getlist",map);
	}

}
