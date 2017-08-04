package com.cn.ctbri.dao;

import com.cn.ctbri.model.City;

public interface CityMapper {
    int deleteByPrimaryKey(Long locationId);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Long locationId);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}