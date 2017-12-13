package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDdos;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDdosExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TWafLogDdosMapper {
    long countByExample(TWafLogDdosExample example);

    int deleteByExample(TWafLogDdosExample example);

    int deleteByPrimaryKey(Long logId);

    int insert(TWafLogDdos record);

    int insertSelective(TWafLogDdos record);

    List<TWafLogDdos> selectByExampleWithRowbounds(TWafLogDdosExample example, RowBounds rowBounds);

    List<TWafLogDdos> selectByExample(TWafLogDdosExample example);

    TWafLogDdos selectByPrimaryKey(Long logId);

    int updateByExampleSelective(@Param("record") TWafLogDdos record, @Param("example") TWafLogDdosExample example);

    int updateByExample(@Param("record") TWafLogDdos record, @Param("example") TWafLogDdosExample example);

    int updateByPrimaryKeySelective(TWafLogDdos record);

    int updateByPrimaryKey(TWafLogDdos record);
}