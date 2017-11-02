package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishFieldCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishFieldCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

public interface TViewWebPhishFieldCountMapper {
    @SelectProvider(type=TViewWebPhishFieldCountSqlProvider.class, method="countByExample")
    int countByExample(TViewWebPhishFieldCountExample example);


    @Insert({
        "insert into t_view_web_phish_field_count (webphish_field, count, ",
        "isvalid)",
        "values (#{webphishField,jdbcType=VARCHAR}, #{count,jdbcType=BIGINT}, ",
        "#{isvalid,jdbcType=INTEGER})"
    })
    int insert(TViewWebPhishFieldCount record);

    @InsertProvider(type=TViewWebPhishFieldCountSqlProvider.class, method="insertSelective")
    int insertSelective(TViewWebPhishFieldCount record);

    @SelectProvider(type=TViewWebPhishFieldCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_field", property="webphishField", jdbcType=JdbcType.VARCHAR),
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER)
    })
    List<TViewWebPhishFieldCount> selectByExampleWithRowbounds(TViewWebPhishFieldCountExample example, RowBounds rowBounds);

    @SelectProvider(type=TViewWebPhishFieldCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_field", property="webphishField", jdbcType=JdbcType.VARCHAR),
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER)
    })
    List<TViewWebPhishFieldCount> selectByExample(TViewWebPhishFieldCountExample example);

    @UpdateProvider(type=TViewWebPhishFieldCountSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TViewWebPhishFieldCount record, @Param("example") TViewWebPhishFieldCountExample example);

    @UpdateProvider(type=TViewWebPhishFieldCountSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TViewWebPhishFieldCount record, @Param("example") TViewWebPhishFieldCountExample example);
}