package com.cn.ctbri.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cn.ctbri.model.CntBySrc;
import com.cn.ctbri.model.CntByType;

public interface CntBySrcMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CntBySrc record);

    int insertSelective(CntBySrc record);

    CntBySrc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CntBySrc record);

    int updateByPrimaryKey(CntBySrc record);
    Date selectMaxDay();
    List<CntBySrc> selectCntBySrc(Map<String,Date> map);
    void batchInsert(Map<String,List<CntBySrc>> map);
}