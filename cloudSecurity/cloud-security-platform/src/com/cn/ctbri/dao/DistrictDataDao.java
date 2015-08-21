package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.District;


/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-08-20
 * 描        述： 数据展示 数据访问层接口类
 * 版        本：  1.0
 */
public interface DistrictDataDao {

    List<District> getDistrictByAll();

    List getDistrictDataById(String districtId);

}
