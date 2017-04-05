package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishProvinceCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishProvinceCountExample;
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

public interface TViewWebPhishProvinceCountMapper {
    @SelectProvider(type=TViewWebPhishProvinceCountSqlProvider.class, method="countByExample")
    int countByExample(TViewWebPhishProvinceCountExample example);

    @DeleteProvider(type=TViewWebPhishProvinceCountSqlProvider.class, method="deleteByExample")
    int deleteByExample(TViewWebPhishProvinceCountExample example);

    @Insert({
        "insert into t_view_web_phish_province_count (webphish_subdivision1, webphish_countrycode, ",
        "isvalid, count)",
        "values (#{webphishSubdivision1,jdbcType=VARCHAR}, #{webphishCountrycode,jdbcType=VARCHAR}, ",
        "#{isvalid,jdbcType=INTEGER}, #{count,jdbcType=BIGINT})"
    })
    int insert(TViewWebPhishProvinceCount record);

    @InsertProvider(type=TViewWebPhishProvinceCountSqlProvider.class, method="insertSelective")
    int insertSelective(TViewWebPhishProvinceCount record);

    @SelectProvider(type=TViewWebPhishProvinceCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_subdivision1", property="webphishSubdivision1", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_countrycode", property="webphishCountrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER),
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT)
    })
    List<TViewWebPhishProvinceCount> selectByExampleWithRowbounds(TViewWebPhishProvinceCountExample example, RowBounds rowBounds);

    @SelectProvider(type=TViewWebPhishProvinceCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_subdivision1", property="webphishSubdivision1", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_countrycode", property="webphishCountrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER),
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT)
    })
    List<TViewWebPhishProvinceCount> selectByExample(TViewWebPhishProvinceCountExample example);

    @UpdateProvider(type=TViewWebPhishProvinceCountSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TViewWebPhishProvinceCount record, @Param("example") TViewWebPhishProvinceCountExample example);

    @UpdateProvider(type=TViewWebPhishProvinceCountSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TViewWebPhishProvinceCount record, @Param("example") TViewWebPhishProvinceCountExample example);
}