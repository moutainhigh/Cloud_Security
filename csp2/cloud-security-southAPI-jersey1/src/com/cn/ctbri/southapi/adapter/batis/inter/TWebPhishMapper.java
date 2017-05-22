package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TWebPhish;
import com.cn.ctbri.southapi.adapter.batis.model.TWebPhishExample;
import com.cn.ctbri.southapi.adapter.batis.model.TWebPhishKey;
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

public interface TWebPhishMapper {
    @SelectProvider(type=TWebPhishSqlProvider.class, method="countByExample")
    int countByExample(TWebPhishExample example);

    @DeleteProvider(type=TWebPhishSqlProvider.class, method="deleteByExample")
    int deleteByExample(TWebPhishExample example);

    @Delete({
        "delete from t_web_phish",
        "where webphish_id = #{webphishId,jdbcType=BIGINT}",
          "and urlsign = #{urlsign,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(TWebPhishKey key);

    @Insert({
        "insert into t_web_phish (webphish_id, urlsign, ",
        "webphish_url, webphish_field, ",
        "webphish_domain, webphish_ip, ",
        "webphish_asn, webphish_asnname, ",
        "webphish_country, webphish_countrycode, ",
        "webphish_subdivision1, webphish_subdivision2, ",
        "webphish_city, webphish_target, ",
        "verified_time, from_source, ",
        "from_source_id, from_source_detail, ",
        "from_source_reserve, update_time, ",
        "expire_time, isvalid, ",
        "reserve)",
        "values (#{webphishId,jdbcType=BIGINT}, #{urlsign,jdbcType=VARCHAR}, ",
        "#{webphishUrl,jdbcType=VARCHAR}, #{webphishField,jdbcType=VARCHAR}, ",
        "#{webphishDomain,jdbcType=VARCHAR}, #{webphishIp,jdbcType=VARCHAR}, ",
        "#{webphishAsn,jdbcType=VARCHAR}, #{webphishAsnname,jdbcType=VARCHAR}, ",
        "#{webphishCountry,jdbcType=VARCHAR}, #{webphishCountrycode,jdbcType=VARCHAR}, ",
        "#{webphishSubdivision1,jdbcType=VARCHAR}, #{webphishSubdivision2,jdbcType=VARCHAR}, ",
        "#{webphishCity,jdbcType=VARCHAR}, #{webphishTarget,jdbcType=VARCHAR}, ",
        "#{verifiedTime,jdbcType=CHAR}, #{fromSource,jdbcType=VARCHAR}, ",
        "#{fromSourceId,jdbcType=BIGINT}, #{fromSourceDetail,jdbcType=VARCHAR}, ",
        "#{fromSourceReserve,jdbcType=VARCHAR}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{expireTime,jdbcType=TIMESTAMP}, #{isvalid,jdbcType=INTEGER}, ",
        "#{reserve,jdbcType=VARCHAR})"
    })
    int insert(TWebPhish record);

    @InsertProvider(type=TWebPhishSqlProvider.class, method="insertSelective")
    int insertSelective(TWebPhish record);

    @SelectProvider(type=TWebPhishSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_id", property="webphishId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="urlsign", property="urlsign", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="webphish_url", property="webphishUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_field", property="webphishField", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_domain", property="webphishDomain", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_ip", property="webphishIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_asn", property="webphishAsn", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_asnname", property="webphishAsnname", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_country", property="webphishCountry", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_countrycode", property="webphishCountrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_subdivision1", property="webphishSubdivision1", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_subdivision2", property="webphishSubdivision2", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_city", property="webphishCity", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_target", property="webphishTarget", jdbcType=JdbcType.VARCHAR),
        @Result(column="verified_time", property="verifiedTime", jdbcType=JdbcType.CHAR),
        @Result(column="from_source", property="fromSource", jdbcType=JdbcType.VARCHAR),
        @Result(column="from_source_id", property="fromSourceId", jdbcType=JdbcType.BIGINT),
        @Result(column="from_source_detail", property="fromSourceDetail", jdbcType=JdbcType.VARCHAR),
        @Result(column="from_source_reserve", property="fromSourceReserve", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="expire_time", property="expireTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER),
        @Result(column="reserve", property="reserve", jdbcType=JdbcType.VARCHAR)
    })
    List<TWebPhish> selectByExampleWithRowbounds(TWebPhishExample example, RowBounds rowBounds);

    @SelectProvider(type=TWebPhishSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="webphish_id", property="webphishId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="urlsign", property="urlsign", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="webphish_url", property="webphishUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_field", property="webphishField", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_domain", property="webphishDomain", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_ip", property="webphishIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_asn", property="webphishAsn", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_asnname", property="webphishAsnname", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_country", property="webphishCountry", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_countrycode", property="webphishCountrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_subdivision1", property="webphishSubdivision1", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_subdivision2", property="webphishSubdivision2", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_city", property="webphishCity", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_target", property="webphishTarget", jdbcType=JdbcType.VARCHAR),
        @Result(column="verified_time", property="verifiedTime", jdbcType=JdbcType.CHAR),
        @Result(column="from_source", property="fromSource", jdbcType=JdbcType.VARCHAR),
        @Result(column="from_source_id", property="fromSourceId", jdbcType=JdbcType.BIGINT),
        @Result(column="from_source_detail", property="fromSourceDetail", jdbcType=JdbcType.VARCHAR),
        @Result(column="from_source_reserve", property="fromSourceReserve", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="expire_time", property="expireTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER),
        @Result(column="reserve", property="reserve", jdbcType=JdbcType.VARCHAR)
    })
    List<TWebPhish> selectByExample(TWebPhishExample example);

    @Select({
        "select",
        "webphish_id, urlsign, webphish_url, webphish_field, webphish_domain, webphish_ip, ",
        "webphish_asn, webphish_asnname, webphish_country, webphish_countrycode, webphish_subdivision1, ",
        "webphish_subdivision2, webphish_city, webphish_target, verified_time, from_source, ",
        "from_source_id, from_source_detail, from_source_reserve, update_time, expire_time, ",
        "isvalid, reserve",
        "from t_web_phish",
        "where webphish_id = #{webphishId,jdbcType=BIGINT}",
          "and urlsign = #{urlsign,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="webphish_id", property="webphishId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="urlsign", property="urlsign", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="webphish_url", property="webphishUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_field", property="webphishField", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_domain", property="webphishDomain", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_ip", property="webphishIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_asn", property="webphishAsn", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_asnname", property="webphishAsnname", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_country", property="webphishCountry", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_countrycode", property="webphishCountrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_subdivision1", property="webphishSubdivision1", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_subdivision2", property="webphishSubdivision2", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_city", property="webphishCity", jdbcType=JdbcType.VARCHAR),
        @Result(column="webphish_target", property="webphishTarget", jdbcType=JdbcType.VARCHAR),
        @Result(column="verified_time", property="verifiedTime", jdbcType=JdbcType.CHAR),
        @Result(column="from_source", property="fromSource", jdbcType=JdbcType.VARCHAR),
        @Result(column="from_source_id", property="fromSourceId", jdbcType=JdbcType.BIGINT),
        @Result(column="from_source_detail", property="fromSourceDetail", jdbcType=JdbcType.VARCHAR),
        @Result(column="from_source_reserve", property="fromSourceReserve", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="expire_time", property="expireTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="isvalid", property="isvalid", jdbcType=JdbcType.INTEGER),
        @Result(column="reserve", property="reserve", jdbcType=JdbcType.VARCHAR)
    })
    TWebPhish selectByPrimaryKey(TWebPhishKey key);

    @UpdateProvider(type=TWebPhishSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TWebPhish record, @Param("example") TWebPhishExample example);

    @UpdateProvider(type=TWebPhishSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TWebPhish record, @Param("example") TWebPhishExample example);

    @UpdateProvider(type=TWebPhishSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TWebPhish record);

    @Update({
        "update t_web_phish",
        "set webphish_url = #{webphishUrl,jdbcType=VARCHAR},",
          "webphish_field = #{webphishField,jdbcType=VARCHAR},",
          "webphish_domain = #{webphishDomain,jdbcType=VARCHAR},",
          "webphish_ip = #{webphishIp,jdbcType=VARCHAR},",
          "webphish_asn = #{webphishAsn,jdbcType=VARCHAR},",
          "webphish_asnname = #{webphishAsnname,jdbcType=VARCHAR},",
          "webphish_country = #{webphishCountry,jdbcType=VARCHAR},",
          "webphish_countrycode = #{webphishCountrycode,jdbcType=VARCHAR},",
          "webphish_subdivision1 = #{webphishSubdivision1,jdbcType=VARCHAR},",
          "webphish_subdivision2 = #{webphishSubdivision2,jdbcType=VARCHAR},",
          "webphish_city = #{webphishCity,jdbcType=VARCHAR},",
          "webphish_target = #{webphishTarget,jdbcType=VARCHAR},",
          "verified_time = #{verifiedTime,jdbcType=CHAR},",
          "from_source = #{fromSource,jdbcType=VARCHAR},",
          "from_source_id = #{fromSourceId,jdbcType=BIGINT},",
          "from_source_detail = #{fromSourceDetail,jdbcType=VARCHAR},",
          "from_source_reserve = #{fromSourceReserve,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "expire_time = #{expireTime,jdbcType=TIMESTAMP},",
          "isvalid = #{isvalid,jdbcType=INTEGER},",
          "reserve = #{reserve,jdbcType=VARCHAR}",
        "where webphish_id = #{webphishId,jdbcType=BIGINT}",
          "and urlsign = #{urlsign,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(TWebPhish record);
}