package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDdos;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDdosExample;
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

public interface TWafLogDdosMapper {
    @SelectProvider(type=TWafLogDdosSqlProvider.class, method="countByExample")
    int countByExample(TWafLogDdosExample example);

    @SelectProvider(type=TWafLogDdosSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="action", property="action", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogDdos> selectByExample(TWafLogDdosExample example);

    @Select({
        "select",
        "log_id, resource_id, resource_uri, resource_ip, stat_time, alertlevel, event_type, ",
        "dst_ip, dst_port, action",
        "from t_waf_log_ddos",
        "where log_id = #{logId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="action", property="action", jdbcType=JdbcType.VARCHAR)
    })
    TWafLogDdos selectByPrimaryKey(Long logId);
}