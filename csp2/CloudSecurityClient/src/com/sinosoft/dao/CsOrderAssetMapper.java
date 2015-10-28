package com.sinosoft.dao;

import com.sinosoft.vo.CsAsset;
import com.sinosoft.vo.CsOrderAsset;

public interface CsOrderAssetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CsOrderAsset record);

    int insertSelective(CsOrderAsset record);

    CsOrderAsset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CsOrderAsset record);

    int updateByPrimaryKey(CsOrderAsset record);
    
    CsAsset findAssetNameByOrderId(String orderId);
}