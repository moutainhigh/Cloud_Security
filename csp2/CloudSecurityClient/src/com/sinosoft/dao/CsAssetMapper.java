package com.sinosoft.dao;

import java.util.List;
import java.util.Map;

import com.sinosoft.vo.CsAsset;

public interface CsAssetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CsAsset record);

    int insertSelective(CsAsset record);

    CsAsset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CsAsset record);

    int updateByPrimaryKey(CsAsset record);
    
    List<CsAsset> selectByUserId(Map map);
    
    List<CsAsset> selectByUserIdAndStatus(Map map);
}