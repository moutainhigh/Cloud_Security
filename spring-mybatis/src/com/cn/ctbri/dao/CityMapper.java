package com.cn.ctbri.dao;

import com.cn.ctbri.model.City;

public interface CityMapper {
    int deleteByPrimaryKey(String locationId);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(String locationId);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}