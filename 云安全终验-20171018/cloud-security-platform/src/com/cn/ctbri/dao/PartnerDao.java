package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Partner;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2016-11-30
 * 描        述：  合作方数据访问层接口类
 * 版        本：  1.0
 */
public interface PartnerDao {
    /**
     * 功能描述：查询所有合作方
     *       @time 2016-11-30
     */
    List<Partner> findAllPartner();

    /**
     * 功能描述：添加合作方
     *       @time 2016-11-30
     */
    void addPartner(Partner partner);
    /**
     * 功能描述：删除合作方
     *       @time 2016-11-30
     */
    void deletePartner(String oldName);
    /**
     * 功能描述：修改合作方
     *       @time 2016-11-30
     */
    void updatePartner(Partner partner);
    /**
     * 功能描述：查询合作方
     *       @time 2016-11-30
     */
    Partner findPartnerById(Partner partner);
    /**
     * 功能描述：根据条件查询合作方
     *       @time 2016-11-30
     */
	List<Partner> findPartnerByParam(Map<String, Object> map);

	
}
