package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfo;
import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfoExample;
import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TWafNsfocusTargetinfoMapper {
    long countByExample(TWafNsfocusTargetinfoExample example);

    int deleteByExample(TWafNsfocusTargetinfoExample example);

    int deleteByPrimaryKey(TWafNsfocusTargetinfoKey key);

    int insert(TWafNsfocusTargetinfo record);

    int insertSelective(TWafNsfocusTargetinfo record);

    List<TWafNsfocusTargetinfo> selectByExampleWithRowbounds(TWafNsfocusTargetinfoExample example, RowBounds rowBounds);

    List<TWafNsfocusTargetinfo> selectByExample(TWafNsfocusTargetinfoExample example);

    TWafNsfocusTargetinfo selectByPrimaryKey(TWafNsfocusTargetinfoKey key);

    int updateByExampleSelective(@Param("record") TWafNsfocusTargetinfo record, @Param("example") TWafNsfocusTargetinfoExample example);

    int updateByExample(@Param("record") TWafNsfocusTargetinfo record, @Param("example") TWafNsfocusTargetinfoExample example);

    int updateByPrimaryKeySelective(TWafNsfocusTargetinfo record);

    int updateByPrimaryKey(TWafNsfocusTargetinfo record);
}