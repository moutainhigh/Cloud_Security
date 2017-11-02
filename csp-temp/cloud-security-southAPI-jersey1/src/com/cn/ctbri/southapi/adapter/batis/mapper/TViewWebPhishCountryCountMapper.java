package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishCountryCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishCountryCountExample;
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

public interface TViewWebPhishCountryCountMapper {
    @SelectProvider(type=TViewWebPhishCountryCountSqlProvider.class, method="countByExample")
    int countByExample(TViewWebPhishCountryCountExample example);

    @SelectProvider(type=TViewWebPhishCountryCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_country", property="webphishCountry", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_countrycode", property="webphishCountrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_count", property="webphishCount", jdbcType=JdbcType.BIGINT),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER)
    })
    List<TViewWebPhishCountryCount> selectByExampleWithRowbounds(TViewWebPhishCountryCountExample example, RowBounds rowBounds);

    @SelectProvider(type=TViewWebPhishCountryCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_country", property="webphishCountry", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_countrycode", property="webphishCountrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_count", property="webphishCount", jdbcType=JdbcType.BIGINT),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER)
    })
    List<TViewWebPhishCountryCount> selectByExample(TViewWebPhishCountryCountExample example);
    
    @Select({
    	"select",
    	"webphish_country,webphish_countrycode,SUM(webphish_count) as webphish_count", 
    	"from t_view_web_phish_country_count",
    	"GROUP BY webphish_country,webphish_countrycode"
    })
    @Results({
        @Result(column="webphish_country", property="webphishCountry", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_countrycode", property="webphishCountrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_count", property="webphishCount", jdbcType=JdbcType.BIGINT)
    })
    List<TViewWebPhishCountryCount> selectByExampleGroupBy();
}