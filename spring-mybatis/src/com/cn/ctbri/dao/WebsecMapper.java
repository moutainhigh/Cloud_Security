package com.cn.ctbri.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.ctbri.model.Websec;

public interface WebsecMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(Websec record);

    int insertSelective(Websec record);

    Websec selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(Websec record);

    int updateByPrimaryKeyWithBLOBs(Websec record);

    int updateByPrimaryKey(Websec record);
    Long getMaxLogId();
    List<Websec> selectAll();
    int batchUpd(List<Websec> websecList);
    List<Websec>  selectSeg(Map<String,Long> hm);
}