package com.cn.ctbri.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.cn.ctbri.model.Websec;
import com.cn.ctbri.model.WebsecSeg;

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
    void batchUpd(Map<String,List<Websec>> map);
    void batchUpdSrc(Map<String,List<Websec>> map);
    void batchUpdDst(Map<String,List<WebsecSeg>> map);
    void updDst(WebsecSeg websecSeg);
    CopyOnWriteArrayList<Websec>  selectSeg(Map<String,Long> hm);
    Date selectMaxDay();
    int selectToUpdNum(Map<String,Long> hm);
    Long selectOffset(Long preLogId);
    List<Websec>  selectByLimit(Map<String,Long> hm);
    Long selectMaxLogIdByLimit(Map<String,Long> hm);
    List<Websec>  selectDstByLimit(Map<String,Long> hm);
}