package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArp;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TWafLogArpMapper {
    long countByExample(TWafLogArpExample example);

    int deleteByExample(TWafLogArpExample example);

    int deleteByPrimaryKey(Long logId);

    int insert(TWafLogArp record);

    int insertSelective(TWafLogArp record);

    List<TWafLogArp> selectByExampleWithRowbounds(TWafLogArpExample example, RowBounds rowBounds);

    List<TWafLogArp> selectByExample(TWafLogArpExample example);

    TWafLogArp selectByPrimaryKey(Long logId);

    int updateByExampleSelective(@Param("record") TWafLogArp record, @Param("example") TWafLogArpExample example);

    int updateByExample(@Param("record") TWafLogArp record, @Param("example") TWafLogArpExample example);

    int updateByPrimaryKeySelective(TWafLogArp record);

    int updateByPrimaryKey(TWafLogArp record);
}