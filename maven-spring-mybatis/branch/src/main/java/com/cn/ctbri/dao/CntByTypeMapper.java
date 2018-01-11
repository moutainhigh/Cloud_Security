package com.cn.ctbri.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cn.ctbri.model.CntByType;
import com.cn.ctbri.model.Websec;

public interface CntByTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CntByType record);

    int insertSelective(CntByType record);

    CntByType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CntByType record);

    int updateByPrimaryKey(CntByType record);
    Date selectMaxDay();
    List<CntByType> selectCntByType(Map<String,Date> map);
    void batchInsert(Map<String,List<CntByType>> map);
}