package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArp;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArpExample;
import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

public interface TWafLogArpMapper {
	@SelectProvider(type=TWafLogArpSqlProvider.class, method="countByExample")
    int countByExample(TWafLogArpExample example);

    @SelectProvider(type=TWafLogArpSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="attack_type", property="attackType", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_mac", property="srcMac", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_mac", property="dstMac", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="action", property="action", jdbcType=JdbcType.VARCHAR),
        @Result(column="def_ip", property="defIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="def_mac", property="defMac", jdbcType=JdbcType.VARCHAR),
        @Result(column="conflit_mac", property="conflitMac", jdbcType=JdbcType.VARCHAR),
        @Result(column="count_num", property="countNum", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogArp> selectByExample(TWafLogArpExample example);

    @Select({
        "select",
        "log_id, resource_id, resource_uri, resource_ip, stat_time, alertlevel, event_type, ",
        "attack_type, src_ip, src_mac, dst_ip, dst_mac, status, action, def_ip, def_mac, ",
        "conflit_mac, count_num",
        "from t_waf_log_arp",
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
        @Result(column="attack_type", property="attackType", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_mac", property="srcMac", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_mac", property="dstMac", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="action", property="action", jdbcType=JdbcType.VARCHAR),
        @Result(column="def_ip", property="defIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="def_mac", property="defMac", jdbcType=JdbcType.VARCHAR),
        @Result(column="conflit_mac", property="conflitMac", jdbcType=JdbcType.VARCHAR),
        @Result(column="count_num", property="countNum", jdbcType=JdbcType.VARCHAR)
    })
    TWafLogArp selectByPrimaryKey(Long logId);
}