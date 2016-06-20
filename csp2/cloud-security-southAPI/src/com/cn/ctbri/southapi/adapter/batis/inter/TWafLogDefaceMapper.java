package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDeface;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDefaceExample;
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

public interface TWafLogDefaceMapper {
    @SelectProvider(type=TWafLogDefaceSqlProvider.class, method="countByExample")
    int countByExample(TWafLogDefaceExample example);

    @DeleteProvider(type=TWafLogDefaceSqlProvider.class, method="deleteByExample")
    int deleteByExample(TWafLogDefaceExample example);

    @Delete({
        "delete from t_waf_log_deface",
        "where log_id = #{logId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long logId);

    @Insert({
        "insert into t_waf_log_deface (log_id, resource_id, ",
        "resource_uri, resource_ip, ",
        "site_id, protect_id, ",
        "stat_time, alertlevel, ",
        "event_type, dst_ip, ",
        "dst_port, url, reason)",
        "values (#{logId,jdbcType=BIGINT}, #{resourceId,jdbcType=INTEGER}, ",
        "#{resourceUri,jdbcType=VARCHAR}, #{resourceIp,jdbcType=VARCHAR}, ",
        "#{siteId,jdbcType=VARCHAR}, #{protectId,jdbcType=VARCHAR}, ",
        "#{statTime,jdbcType=TIMESTAMP}, #{alertlevel,jdbcType=VARCHAR}, ",
        "#{eventType,jdbcType=VARCHAR}, #{dstIp,jdbcType=VARCHAR}, ",
        "#{dstPort,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{reason,jdbcType=VARCHAR})"
    })
    int insert(TWafLogDeface record);

    @InsertProvider(type=TWafLogDefaceSqlProvider.class, method="insertSelective")
    int insertSelective(TWafLogDeface record);

    @SelectProvider(type=TWafLogDefaceSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="site_id", property="siteId", jdbcType=JdbcType.VARCHAR),
        @Result(column="protect_id", property="protectId", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="reason", property="reason", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogDeface> selectByExample(TWafLogDefaceExample example);

    @Select({
        "select",
        "log_id, resource_id, resource_uri, resource_ip, site_id, protect_id, stat_time, ",
        "alertlevel, event_type, dst_ip, dst_port, url, reason",
        "from t_waf_log_deface",
        "where log_id = #{logId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="site_id", property="siteId", jdbcType=JdbcType.VARCHAR),
        @Result(column="protect_id", property="protectId", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="reason", property="reason", jdbcType=JdbcType.VARCHAR)
    })
    TWafLogDeface selectByPrimaryKey(Long logId);

    @UpdateProvider(type=TWafLogDefaceSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TWafLogDeface record, @Param("example") TWafLogDefaceExample example);

    @UpdateProvider(type=TWafLogDefaceSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TWafLogDeface record, @Param("example") TWafLogDefaceExample example);

    @UpdateProvider(type=TWafLogDefaceSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TWafLogDeface record);

    @Update({
        "update t_waf_log_deface",
        "set resource_id = #{resourceId,jdbcType=INTEGER},",
          "resource_uri = #{resourceUri,jdbcType=VARCHAR},",
          "resource_ip = #{resourceIp,jdbcType=VARCHAR},",
          "site_id = #{siteId,jdbcType=VARCHAR},",
          "protect_id = #{protectId,jdbcType=VARCHAR},",
          "stat_time = #{statTime,jdbcType=TIMESTAMP},",
          "alertlevel = #{alertlevel,jdbcType=VARCHAR},",
          "event_type = #{eventType,jdbcType=VARCHAR},",
          "dst_ip = #{dstIp,jdbcType=VARCHAR},",
          "dst_port = #{dstPort,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "reason = #{reason,jdbcType=VARCHAR}",
        "where log_id = #{logId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TWafLogDeface record);
}