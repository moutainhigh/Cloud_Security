package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishTargetCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishTargetCountExample;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

public interface TViewWebPhishTargetCountMapper {
    @SelectProvider(type=TViewWebPhishTargetCountSqlProvider.class, method="countByExample")
    int countByExample(TViewWebPhishTargetCountExample example);

    @DeleteProvider(type=TViewWebPhishTargetCountSqlProvider.class, method="deleteByExample")
    int deleteByExample(TViewWebPhishTargetCountExample example);

    @Insert({
        "insert into t_view_web_phish_target_count (webphish_target, COUNT, ",
        "isvalid)",
        "values (#{webphishTarget,jdbcType=VARCHAR}, #{count,jdbcType=BIGINT}, ",
        "#{isvalid,jdbcType=INTEGER})"
    })
    int insert(TViewWebPhishTargetCount record);

    @InsertProvider(type=TViewWebPhishTargetCountSqlProvider.class, method="insertSelective")
    int insertSelective(TViewWebPhishTargetCount record);

    @SelectProvider(type=TViewWebPhishTargetCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_target", property="webphishTarget", jdbcType=JdbcType.VARCHAR),
        @Result(column="COUNT", property="count", jdbcType=JdbcType.BIGINT),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER)
    })
    List<TViewWebPhishTargetCount> selectByExampleWithRowbounds(TViewWebPhishTargetCountExample example, RowBounds rowBounds);

    @SelectProvider(type=TViewWebPhishTargetCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_target", property="webphishTarget", jdbcType=JdbcType.VARCHAR),
        @Result(column="COUNT", property="count", jdbcType=JdbcType.BIGINT),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER)
    })
    List<TViewWebPhishTargetCount> selectByExample(TViewWebPhishTargetCountExample example);

    @UpdateProvider(type=TViewWebPhishTargetCountSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TViewWebPhishTargetCount record, @Param("example") TViewWebPhishTargetCountExample example);

    @UpdateProvider(type=TViewWebPhishTargetCountSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TViewWebPhishTargetCount record, @Param("example") TViewWebPhishTargetCountExample example);
}