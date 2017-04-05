package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4Location;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4LocationExample;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface TViewIpv4LocationMapper {
    @SelectProvider(type=TViewIpv4LocationSqlProvider.class, method="countByExample")
    int countByExample(TViewIpv4LocationExample example);

    @DeleteProvider(type=TViewIpv4LocationSqlProvider.class, method="deleteByExample")
    int deleteByExample(TViewIpv4LocationExample example);

    @Insert({
        "insert into t_view_ipv4_location (latlong_id, network, ",
        "netmask, latitude, ",
        "longitude, accuracy_radius, ",
        "postal_code, locale_code, ",
        "continent_name, country_iso_code, ",
        "country_name, subdivision_1_name, ",
        "subdivision_2_name, city_name, ",
        "time_zone)",
        "values (#{latlongId,jdbcType=BIGINT}, #{network,jdbcType=VARCHAR}, ",
        "#{netmask,jdbcType=VARCHAR}, #{latitude,jdbcType=VARCHAR}, ",
        "#{longitude,jdbcType=VARCHAR}, #{accuracyRadius,jdbcType=VARCHAR}, ",
        "#{postalCode,jdbcType=VARCHAR}, #{localeCode,jdbcType=VARCHAR}, ",
        "#{continentName,jdbcType=VARCHAR}, #{countryIsoCode,jdbcType=VARCHAR}, ",
        "#{countryName,jdbcType=VARCHAR}, #{subdivision1Name,jdbcType=VARCHAR}, ",
        "#{subdivision2Name,jdbcType=VARCHAR}, #{cityName,jdbcType=VARCHAR}, ",
        "#{timeZone,jdbcType=VARCHAR})"
    })
    int insert(TViewIpv4Location record);

    @InsertProvider(type=TViewIpv4LocationSqlProvider.class, method="insertSelective")
    int insertSelective(TViewIpv4Location record);

    @SelectProvider(type=TViewIpv4LocationSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="latlong_id", property="latlongId", jdbcType=JdbcType.BIGINT),
        @Result(column="network", property="network", jdbcType=JdbcType.VARCHAR),
        @Result(column="netmask", property="netmask", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="accuracy_radius", property="accuracyRadius", jdbcType=JdbcType.VARCHAR),
        @Result(column="postal_code", property="postalCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="locale_code", property="localeCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="continent_name", property="continentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_iso_code", property="countryIsoCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_name", property="countryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_1_name", property="subdivision1Name", jdbcType=JdbcType.VARCHAR),
        @Result(column="subdivision_2_name", property="subdivision2Name", jdbcType=JdbcType.VARCHAR),
        @Result(column="city_name", property="cityName", jdbcType=JdbcType.VARCHAR),
        @Result(column="time_zone", property="timeZone", jdbcType=JdbcType.VARCHAR)
    })
    List<TViewIpv4Location> selectByExample(TViewIpv4LocationExample example);

    @UpdateProvider(type=TViewIpv4LocationSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TViewIpv4Location record, @Param("example") TViewIpv4LocationExample example);

    @UpdateProvider(type=TViewIpv4LocationSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TViewIpv4Location record, @Param("example") TViewIpv4LocationExample example);
}