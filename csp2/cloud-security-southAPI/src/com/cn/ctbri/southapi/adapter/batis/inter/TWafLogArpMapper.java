package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArp;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArpExample;
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

public interface TWafLogArpMapper {
    @SelectProvider(type=TWafLogArpSqlProvider.class, method="countByExample")
    int countByExample(TWafLogArpExample example);

    @DeleteProvider(type=TWafLogArpSqlProvider.class, method="deleteByExample")
    int deleteByExample(TWafLogArpExample example);

    @Delete({
        "delete from t_waf_log_arp",
        "where log_id = #{logId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long logId);

    @Insert({
        "insert into t_waf_log_arp (log_id, resource_id, ",
        "resource_uri, resource_ip, ",
        "stat_time, alertlevel, ",
        "event_type, attack_type, ",
        "src_ip, src_mac, dst_ip, ",
        "dst_mac, status, ",
        "action, def_ip, def_mac, ",
        "conflit_mac, count_num)",
        "values (#{logId,jdbcType=BIGINT}, #{resourceId,jdbcType=INTEGER}, ",
        "#{resourceUri,jdbcType=VARCHAR}, #{resourceIp,jdbcType=VARCHAR}, ",
        "#{statTime,jdbcType=TIMESTAMP}, #{alertlevel,jdbcType=VARCHAR}, ",
        "#{eventType,jdbcType=VARCHAR}, #{attackType,jdbcType=VARCHAR}, ",
        "#{srcIp,jdbcType=VARCHAR}, #{srcMac,jdbcType=VARCHAR}, #{dstIp,jdbcType=VARCHAR}, ",
        "#{dstMac,jdbcType=VARCHAR}, #{status,jdbcType=VARCHAR}, ",
        "#{action,jdbcType=VARCHAR}, #{defIp,jdbcType=VARCHAR}, #{defMac,jdbcType=VARCHAR}, ",
        "#{conflitMac,jdbcType=VARCHAR}, #{countNum,jdbcType=INTEGER})"
    })
    int insert(TWafLogArp record);

    @InsertProvider(type=TWafLogArpSqlProvider.class, method="insertSelective")
    int insertSelective(TWafLogArp record);

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
        @Result(column="count_num", property="countNum", jdbcType=JdbcType.INTEGER)
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
        @Result(column="count_num", property="countNum", jdbcType=JdbcType.INTEGER)
    })
    TWafLogArp selectByPrimaryKey(Long logId);

    @UpdateProvider(type=TWafLogArpSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TWafLogArp record, @Param("example") TWafLogArpExample example);

    @UpdateProvider(type=TWafLogArpSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TWafLogArp record, @Param("example") TWafLogArpExample example);

    @UpdateProvider(type=TWafLogArpSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TWafLogArp record);

    @Update({
        "update t_waf_log_arp",
        "set resource_id = #{resourceId,jdbcType=INTEGER},",
          "resource_uri = #{resourceUri,jdbcType=VARCHAR},",
          "resource_ip = #{resourceIp,jdbcType=VARCHAR},",
          "stat_time = #{statTime,jdbcType=TIMESTAMP},",
          "alertlevel = #{alertlevel,jdbcType=VARCHAR},",
          "event_type = #{eventType,jdbcType=VARCHAR},",
          "attack_type = #{attackType,jdbcType=VARCHAR},",
          "src_ip = #{srcIp,jdbcType=VARCHAR},",
          "src_mac = #{srcMac,jdbcType=VARCHAR},",
          "dst_ip = #{dstIp,jdbcType=VARCHAR},",
          "dst_mac = #{dstMac,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=VARCHAR},",
          "action = #{action,jdbcType=VARCHAR},",
          "def_ip = #{defIp,jdbcType=VARCHAR},",
          "def_mac = #{defMac,jdbcType=VARCHAR},",
          "conflit_mac = #{conflitMac,jdbcType=VARCHAR},",
          "count_num = #{countNum,jdbcType=INTEGER}",
        "where log_id = #{logId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TWafLogArp record);
}