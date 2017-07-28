package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsec;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecAlertLevelCount;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecDstSrc;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecEventTypeCount;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
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

    @Select("<script>"+
    		"select count(*) as count,src_ip from t_waf_log_websec "+
    		"where dst_ip in "+
    		"<foreach item='item' index='index' collection='strList' open='(' separator=',' close=')'>"+
    		"#{item}" +
    		"</foreach> " +
    		" AND stat_time between #{startDate,jdbcType=TIMESTAMP} and #{endDate,jdbcType=TIMESTAMP} "+
    		" GROUP BY src_ip  ORDER BY count desc "+
    		"limit #{limitNum,jdbcType=BIGINT}"+
    		"</script>"
    )
    @Results({
        @Result(column="count", property="count", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsecDstSrc> selectSrcIp(@Param("strList") List<String> strList,@Param("limitNum") int limitNum,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
    

    @Select("<script>"+
    		"SELECT e.event_type as event_type,COUNT(event_type) AS count "+
    		"from (SELECT t.event_type from t_waf_log_websec as t "+
    		"<where>"+
    		"t.domain in "+
    		"<foreach item='item' index='index' collection='domain' open='(' separator=',' close=')'>"+
    		"#{item}"+
    		"</foreach>"+
    		"</where> "+
    		"ORDER BY t.stat_time desc limit #{limitNum,jdbcType=BIGINT}) as e GROUP BY event_type"+
    		"</script>"		
    )
    @Results({
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="count", property="count", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsecEventTypeCount> selectEventTypeCountsByDomain(@Param("domain") List<String> domain,@Param("limitNum") int limitNum);
    
    @Select("<script>"+
    		"SELECT e.event_type as event_type,COUNT(event_type) AS count "+
    		"from (SELECT t.event_type from t_waf_log_websec as t "+
    		"<where>"+
    		"t.dst_ip in "+
    		"<foreach item='item' index='index' collection='dstIp' open='(' separator=',' close=')'>"+
    		"#{item}"+
    		"</foreach>"+
    		"</where> "+
    		"ORDER BY t.stat_time desc limit #{limitNum,jdbcType=BIGINT}) as e GROUP BY event_type"+
    		"</script>"		
    )
    @Results({
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="count", property="count", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsecEventTypeCount> selectEventTypeCountsByDstIp(@Param("dstIp") List<String> dstIp,@Param("limitNum") int limitNum);
    
    @Select("<script>"+
    		"SELECT e.alertlevel as alertlevel,COUNT(alertlevel) AS count "
    		+ "from (SELECT t.alertlevel from t_waf_log_websec as t "
    		+ "<where> "
    		+ "t.domain in "
    		+ "<foreach item='item' index='index' collection='domain' open='(' separator=',' close=')'>"
    		+ "#{item}"
    		+ "</foreach>"
    		+ "</where>"
    		+ "ORDER BY t.stat_time desc limit #{limitNum,jdbcType=BIGINT}) as e GROUP BY alertlevel"
    		+ "</script>"
    )
    @Results({
        @Result(column="alertlevel", property="alertlevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="count", property="count", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsecAlertLevelCount> selectAlertLevelCountByDomain(@Param("domain") List<String> domain,@Param("limitNum") int limitNum);
    

}