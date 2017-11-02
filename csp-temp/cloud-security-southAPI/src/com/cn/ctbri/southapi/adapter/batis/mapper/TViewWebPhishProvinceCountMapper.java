package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishProvinceCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishProvinceCountExample;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

public interface TViewWebPhishProvinceCountMapper {
    @SelectProvider(type=TViewWebPhishProvinceCountSqlProvider.class, method="countByExample")
    int countByExample(TViewWebPhishProvinceCountExample example);

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
    
    @Select({
    	"select",
    	"webphish_subdivision1,webphish_countrycode,sum(count) as count",
    	"from t_view_web_phish_province_count",
    	"GROUP BY webphish_countrycode,webphish_subdivision1"
    })
    @Results({
        @Result(column="webphish_subdivision1", property="webphishSubdivision1", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_countrycode", property="webphishCountrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT)
    })
    List<TViewWebPhishProvinceCount> selectByExampleWithoutValid();
    
    
}