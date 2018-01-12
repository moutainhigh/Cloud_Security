package com.cn.ctbri.dao;

import com.cn.ctbri.model.Ip;
import java.util.List;

public interface IpMapper {
    int deleteByPrimaryKey(Long latlongId);

    int insert(Ip record);

    int insertSelective(Ip record);

    Ip selectByPrimaryKey(Long latlongId);

    int updateByPrimaryKeySelective(Ip record);

    int updateByPrimaryKey(Ip record);
    
    List<Ip> getLatLongByIp(Long ip);
}