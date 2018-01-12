package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.model.DstIp;

public interface DstIpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DstIp record);

    int insertSelective(DstIp record);

    DstIp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DstIp record);

    int updateByPrimaryKey(DstIp record);
    List<DstIp> selectAll();
}