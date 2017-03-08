package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TCityLocation;
import com.cn.ctbri.southapi.adapter.batis.model.TCityLocationExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

public interface TCityLocationMapper {
    @SelectProvider(type=TCityLocationSqlProvider.class, method="countByExample")
    int countByExample(TCityLocationExample example);

    @DeleteProvider(type=TCityLocationSqlProvider.class, method="deleteByExample")
    int deleteByExample(TCityLocationExample example);

    @Delete({
        "delete from t_city_location",
        "where location_id = #{locationId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long locationId);

    @Insert({
        "insert into t_city_location (location_id, locale_code, ",
        "continent_code, continent_name, ",
        "country_iso_code, country_name, ",
        "subdivision_1_iso_code, subdivision_1_name, ",
        "subdivision_2_iso_code, subdivision_2_name, ",
        "city_name, metro_code, ",
        "time_zone)",
        "values (#{locationId,jdbcType=BIGINT}, #{localeCode,jdbcType=VARCHAR}, ",
        "#{continentCode,jdbcType=VARCHAR}, #{continentName,jdbcType=VARCHAR}, ",
        "#{countryIsoCode,jdbcType=VARCHAR}, #{countryName,jdbcType=VARCHAR}, ",
        "#{subdivision1IsoCode,jdbcType=VARCHAR}, #{subdivision1Name,jdbcType=VARCHAR}, ",
        "#{subdivision2IsoCode,jdbcType=VARCHAR}, #{subdivision2Name,jdbcType=VARCHAR}, ",
        "#{cityName,jdbcType=VARCHAR}, #{metroCode,jdbcType=VARCHAR}, ",
        "#{timeZone,jdbcType=VARCHAR})"
    })
    int insert(TCityLocation record);

    @InsertProvider(type=TCityLocationSqlProvider.class, method="insertSelective")
    int insertSelective(TCityLocation record);

    @SelectProvider(type=TCityLocationSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="location_id", property="locationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="locale_code", property="localeCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="continent_code", property="continentCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="continent_name", property="continentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_iso_code", property="countryIsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_name", property="countryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_1_iso_code", property="subdivision1IsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_1_name", property="subdivision1Name", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_2_iso_code", property="subdivision2IsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_2_name", property="subdivision2Name", jdbcType=JdbcType.VARCHAR),
        @Result(column="city_name", property="cityName", jdbcType=JdbcType.VARCHAR),
        @Result(column="metro_code", property="metroCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="time_zone", property="timeZone", jdbcType=JdbcType.VARCHAR)
    })
    List<TCityLocation> selectByExampleWithRowbounds(TCityLocationExample example, RowBounds rowBounds);

    @SelectProvider(type=TCityLocationSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="location_id", property="locationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="locale_code", property="localeCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="continent_code", property="continentCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="continent_name", property="continentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_iso_code", property="countryIsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_name", property="countryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_1_iso_code", property="subdivision1IsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_1_name", property="subdivision1Name", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_2_iso_code", property="subdivision2IsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_2_name", property="subdivision2Name", jdbcType=JdbcType.VARCHAR),
        @Result(column="city_name", property="cityName", jdbcType=JdbcType.VARCHAR),
        @Result(column="metro_code", property="metroCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="time_zone", property="timeZone", jdbcType=JdbcType.VARCHAR)
    })
    List<TCityLocation> selectByExample(TCityLocationExample example);

    @Select({
        "select",
        "location_id, locale_code, continent_code, continent_name, country_iso_code, ",
        "country_name, subdivision_1_iso_code, subdivision_1_name, subdivision_2_iso_code, ",
        "subdivision_2_name, city_name, metro_code, time_zone",
        "from t_city_location",
        "where location_id = #{locationId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="location_id", property="locationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="locale_code", property="localeCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="continent_code", property="continentCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="continent_name", property="continentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_iso_code", property="countryIsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_name", property="countryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_1_iso_code", property="subdivision1IsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_1_name", property="subdivision1Name", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_2_iso_code", property="subdivision2IsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_2_name", property="subdivision2Name", jdbcType=JdbcType.VARCHAR),
        @Result(column="city_name", property="cityName", jdbcType=JdbcType.VARCHAR),
        @Result(column="metro_code", property="metroCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="time_zone", property="timeZone", jdbcType=JdbcType.VARCHAR)
    })
    TCityLocation selectByPrimaryKey(Long locationId);

    @UpdateProvider(type=TCityLocationSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TCityLocation record, @Param("example") TCityLocationExample example);

    @UpdateProvider(type=TCityLocationSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TCityLocation record, @Param("example") TCityLocationExample example);

    @UpdateProvider(type=TCityLocationSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TCityLocation record);

    @Update({
        "update t_city_location",
        "set locale_code = #{localeCode,jdbcType=VARCHAR},",
          "continent_code = #{continentCode,jdbcType=VARCHAR},",
          "continent_name = #{continentName,jdbcType=VARCHAR},",
          "country_iso_code = #{countryIsoCode,jdbcType=VARCHAR},",
          "country_name = #{countryName,jdbcType=VARCHAR},",
          "subdivision_1_iso_code = #{subdivision1IsoCode,jdbcType=VARCHAR},",
          "subdivision_1_name = #{subdivision1Name,jdbcType=VARCHAR},",
          "subdivision_2_iso_code = #{subdivision2IsoCode,jdbcType=VARCHAR},",
          "subdivision_2_name = #{subdivision2Name,jdbcType=VARCHAR},",
          "city_name = #{cityName,jdbcType=VARCHAR},",
          "metro_code = #{metroCode,jdbcType=VARCHAR},",
          "time_zone = #{timeZone,jdbcType=VARCHAR}",
        "where location_id = #{locationId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TCityLocation record);
}