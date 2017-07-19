package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.model.Ip;

public interface IpMapper {
    int deleteByPrimaryKey(String latlongId);

    int insert(Ip record);

    int insertSelective(Ip record);

    Ip selectByPrimaryKey(String latlongId);

    int updateByPrimaryKeySelective(Ip record);

    int updateByPrimaryKey(Ip record);
    
    List<Ip> getLatLongByIp(Long ip);
}