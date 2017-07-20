package com.cn.ctbri.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cn.ctbri.model.CntByHour;
import com.cn.ctbri.model.CntByType;

public interface CntByHourMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CntByHour record);

    int insertSelective(CntByHour record);

    CntByHour selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CntByHour record);

    int updateByPrimaryKey(CntByHour record);
    Date selectMaxDay();
    List<CntByHour> selectCntByHour(Map<String,Date> map);
    void batchInsert(Map<String,List<CntByHour>> map);
}