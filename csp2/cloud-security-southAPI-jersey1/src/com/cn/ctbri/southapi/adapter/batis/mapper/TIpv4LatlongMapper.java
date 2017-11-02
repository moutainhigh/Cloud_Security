package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TIpv4Latlong;
import com.cn.ctbri.southapi.adapter.batis.model.TIpv4LatlongExample;
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
import org.apache.ibatis.type.JdbcType;

public interface TIpv4LatlongMapper {
    @SelectProvider(type=TIpv4LatlongSqlProvider.class, method="countByExample")
    int countByExample(TIpv4LatlongExample example);

    @SelectProvider(type=TIpv4LatlongSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="latlong_id", property="latlongId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="network", property="network", jdbcType=JdbcType.VARCHAR),
        @Result(column="netmask", property="netmask", jdbcType=JdbcType.VARCHAR),
        @Result(column="startip", property="startip", jdbcType=JdbcType.BIGINT),
        @Result(column="endip", property="endip", jdbcType=JdbcType.BIGINT),
        @Result(column="location_id", property="locationId", jdbcType=JdbcType.BIGINT),
        @Result(column="registered_country_location_id", property="registeredCountryLocationId", jdbcType=JdbcType.BIGINT),
        @Result(column="represented_country_location_id", property="representedCountryLocationId", jdbcType=JdbcType.BIGINT),
        @Result(column="is_anonymous_proxy", property="isAnonymousProxy", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_satellite_provider", property="isSatelliteProvider", jdbcType=JdbcType.VARCHAR),
        @Result(column="postal_code", property="postalCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="accuracy_radius", property="accuracyRadius", jdbcType=JdbcType.VARCHAR)
    })
    List<TIpv4Latlong> selectByExample(TIpv4LatlongExample example);

    @Select({
        "select",
        "latlong_id, network, netmask, startip, endip, location_id, registered_country_location_id, ",
        "represented_country_location_id, is_anonymous_proxy, is_satellite_provider, ",
        "postal_code, latitude, longitude, accuracy_radius",
        "from t_ipv4_latlong",
        "where latlong_id = #{latlongId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="latlong_id", property="latlongId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="network", property="network", jdbcType=JdbcType.VARCHAR),
        @Result(column="netmask", property="netmask", jdbcType=JdbcType.VARCHAR),
        @Result(column="startip", property="startip", jdbcType=JdbcType.BIGINT),
        @Result(column="endip", property="endip", jdbcType=JdbcType.BIGINT),
        @Result(column="location_id", property="locationId", jdbcType=JdbcType.BIGINT),
        @Result(column="registered_country_location_id", property="registeredCountryLocationId", jdbcType=JdbcType.BIGINT),
        @Result(column="represented_country_location_id", property="representedCountryLocationId", jdbcType=JdbcType.BIGINT),
        @Result(column="is_anonymous_proxy", property="isAnonymousProxy", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_satellite_provider", property="isSatelliteProvider", jdbcType=JdbcType.VARCHAR),
        @Result(column="postal_code", property="postalCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="accuracy_radius", property="accuracyRadius", jdbcType=JdbcType.VARCHAR)
    })
    TIpv4Latlong selectByPrimaryKey(Long latlongId);
}