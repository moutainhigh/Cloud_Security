package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsec;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample;
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

public interface TWafLogWebsecMapper {
    @SelectProvider(type=TWafLogWebsecSqlProvider.class, method="countByExample")
    int countByExample(TWafLogWebsecExample example);
    
    @SelectProvider(type=TWafLogWebsecSqlProvider.class, method="selectMaxByExample")
    int selectMaxByExample(TWafLogWebsecExample example);
    
    @SelectProvider(type=TWafLogWebsecSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="site_id", property="siteId", jdbcType=JdbcType.VARCHAR),
        @Result(column="protect_id", property="protectId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_port", property="srcPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="method", property="method", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain", property="domain", jdbcType=JdbcType.VARCHAR),
        @Result(column="uri", property="uri", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="policy_id", property="policyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="rule_id", property="ruleId", jdbcType=JdbcType.VARCHAR),
        @Result(column="action", property="action", jdbcType=JdbcType.VARCHAR),
        @Result(column="block", property="block", jdbcType=JdbcType.VARCHAR),
        @Result(column="block_info", property="blockInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertinfo", property="alertinfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="proxy_info", property="proxyInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="characters", property="characters", jdbcType=JdbcType.VARCHAR),
        @Result(column="count_num", property="countNum", jdbcType=JdbcType.VARCHAR),
        @Result(column="protocol_type", property="protocolType", jdbcType=JdbcType.VARCHAR),
        @Result(column="wci", property="wci", jdbcType=JdbcType.VARCHAR),
        @Result(column="wsi", property="wsi", jdbcType=JdbcType.VARCHAR),
        @Result(column="http", property="http", jdbcType=JdbcType.LONGVARBINARY)
    })
    List<TWafLogWebsec> selectByExampleWithBLOBsWithRowbounds(TWafLogWebsecExample example, RowBounds rowBounds);

    @SelectProvider(type=TWafLogWebsecSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="site_id", property="siteId", jdbcType=JdbcType.VARCHAR),
        @Result(column="protect_id", property="protectId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_port", property="srcPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="method", property="method", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain", property="domain", jdbcType=JdbcType.VARCHAR),
        @Result(column="uri", property="uri", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="policy_id", property="policyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="rule_id", property="ruleId", jdbcType=JdbcType.VARCHAR),
        @Result(column="action", property="action", jdbcType=JdbcType.VARCHAR),
        @Result(column="block", property="block", jdbcType=JdbcType.VARCHAR),
        @Result(column="block_info", property="blockInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertinfo", property="alertinfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="proxy_info", property="proxyInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="characters", property="characters", jdbcType=JdbcType.VARCHAR),
        @Result(column="count_num", property="countNum", jdbcType=JdbcType.VARCHAR),
        @Result(column="protocol_type", property="protocolType", jdbcType=JdbcType.VARCHAR),
        @Result(column="wci", property="wci", jdbcType=JdbcType.VARCHAR),
        @Result(column="wsi", property="wsi", jdbcType=JdbcType.VARCHAR),
        @Result(column="http", property="http", jdbcType=JdbcType.LONGVARBINARY)
    })
    List<TWafLogWebsec> selectByExampleWithBLOBs(TWafLogWebsecExample example);

    @SelectProvider(type=TWafLogWebsecSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="site_id", property="siteId", jdbcType=JdbcType.VARCHAR),
        @Result(column="protect_id", property="protectId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_port", property="srcPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="method", property="method", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain", property="domain", jdbcType=JdbcType.VARCHAR),
        @Result(column="uri", property="uri", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="policy_id", property="policyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="rule_id", property="ruleId", jdbcType=JdbcType.VARCHAR),
        @Result(column="action", property="action", jdbcType=JdbcType.VARCHAR),
        @Result(column="block", property="block", jdbcType=JdbcType.VARCHAR),
        @Result(column="block_info", property="blockInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertinfo", property="alertinfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="proxy_info", property="proxyInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="characters", property="characters", jdbcType=JdbcType.VARCHAR),
        @Result(column="count_num", property="countNum", jdbcType=JdbcType.VARCHAR),
        @Result(column="protocol_type", property="protocolType", jdbcType=JdbcType.VARCHAR),
        @Result(column="wci", property="wci", jdbcType=JdbcType.VARCHAR),
        @Result(column="wsi", property="wsi", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsec> selectByExampleWithRowbounds(TWafLogWebsecExample example, RowBounds rowBounds);

    @SelectProvider(type=TWafLogWebsecSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="site_id", property="siteId", jdbcType=JdbcType.VARCHAR),
        @Result(column="protect_id", property="protectId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_port", property="srcPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="method", property="method", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain", property="domain", jdbcType=JdbcType.VARCHAR),
        @Result(column="uri", property="uri", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="policy_id", property="policyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="rule_id", property="ruleId", jdbcType=JdbcType.VARCHAR),
        @Result(column="action", property="action", jdbcType=JdbcType.VARCHAR),
        @Result(column="block", property="block", jdbcType=JdbcType.VARCHAR),
        @Result(column="block_info", property="blockInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertinfo", property="alertinfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="proxy_info", property="proxyInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="characters", property="characters", jdbcType=JdbcType.VARCHAR),
        @Result(column="count_num", property="countNum", jdbcType=JdbcType.VARCHAR),
        @Result(column="protocol_type", property="protocolType", jdbcType=JdbcType.VARCHAR),
        @Result(column="wci", property="wci", jdbcType=JdbcType.VARCHAR),
        @Result(column="wsi", property="wsi", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsec> selectByExample(TWafLogWebsecExample example);

    @Select({
        "select",
        "log_id, resource_id, resource_uri, resource_ip, site_id, protect_id, dst_ip, ",
        "dst_port, src_ip, src_port, method, domain, uri, alertlevel, event_type, stat_time, ",
        "policy_id, rule_id, action, block, block_info, alertinfo, proxy_info, characters, ",
        "count_num, protocol_type, wci, wsi, http",
        "from t_waf_log_websec",
        "where log_id = #{logId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="log_id", property="logId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="resource_uri", property="resourceUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ip", property="resourceIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="site_id", property="siteId", jdbcType=JdbcType.VARCHAR),
        @Result(column="protect_id", property="protectId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_port", property="srcPort", jdbcType=JdbcType.VARCHAR),
        @Result(column="method", property="method", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain", property="domain", jdbcType=JdbcType.VARCHAR),
        @Result(column="uri", property="uri", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="policy_id", property="policyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="rule_id", property="ruleId", jdbcType=JdbcType.VARCHAR),
        @Result(column="action", property="action", jdbcType=JdbcType.VARCHAR),
        @Result(column="block", property="block", jdbcType=JdbcType.VARCHAR),
        @Result(column="block_info", property="blockInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="alertinfo", property="alertinfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="proxy_info", property="proxyInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="characters", property="characters", jdbcType=JdbcType.VARCHAR),
        @Result(column="count_num", property="countNum", jdbcType=JdbcType.VARCHAR),
        @Result(column="protocol_type", property="protocolType", jdbcType=JdbcType.VARCHAR),
        @Result(column="wci", property="wci", jdbcType=JdbcType.VARCHAR),
        @Result(column="wsi", property="wsi", jdbcType=JdbcType.VARCHAR),
        @Result(column="http", property="http", jdbcType=JdbcType.LONGVARBINARY)
    })
    TWafLogWebsec selectByPrimaryKey(Long logId);

}