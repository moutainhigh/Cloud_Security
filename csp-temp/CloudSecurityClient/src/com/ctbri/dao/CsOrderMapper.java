package com.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.ctbri.vo.CsOrder;

public interface CsOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(CsOrder record);

    int insertSelective(CsOrder record);

    Map findOrderByOrderId(String id);

    int updateByPrimaryKeySelective(CsOrder record);

    int updateByPrimaryKey(CsOrder record);
    
    List<Map> findOrderByUserIdAndState(Map map);

    Map findOrderMessage(Map map);
    
    List<Map> findByCombineOrderTrack(Map map);
    
    List<CsOrder> findOrderByUserId(Integer userId);
    
    List<Map> findScoreByOrderId(String orderId);
}