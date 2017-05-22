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
}