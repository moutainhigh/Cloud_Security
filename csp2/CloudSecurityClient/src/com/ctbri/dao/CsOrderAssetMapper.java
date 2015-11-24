package com.ctbri.dao;

import com.ctbri.vo.CsAsset;
import com.ctbri.vo.CsOrderAsset;

public interface CsOrderAssetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CsOrderAsset record);

    int insertSelective(CsOrderAsset record);

    CsOrderAsset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CsOrderAsset record);

    int updateByPrimaryKey(CsOrderAsset record);
    
    CsAsset findAssetNameByOrderId(String orderId);
}