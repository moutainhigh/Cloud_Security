package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDeface;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDefaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TWafLogDefaceMapper {
    long countByExample(TWafLogDefaceExample example);

    int deleteByExample(TWafLogDefaceExample example);

    int deleteByPrimaryKey(Long logId);

    int insert(TWafLogDeface record);

    int insertSelective(TWafLogDeface record);

    List<TWafLogDeface> selectByExampleWithRowbounds(TWafLogDefaceExample example, RowBounds rowBounds);

    List<TWafLogDeface> selectByExample(TWafLogDefaceExample example);

    TWafLogDeface selectByPrimaryKey(Long logId);

    int updateByExampleSelective(@Param("record") TWafLogDeface record, @Param("example") TWafLogDefaceExample example);

    int updateByExample(@Param("record") TWafLogDeface record, @Param("example") TWafLogDefaceExample example);

    int updateByPrimaryKeySelective(TWafLogDeface record);

    int updateByPrimaryKey(TWafLogDeface record);
}