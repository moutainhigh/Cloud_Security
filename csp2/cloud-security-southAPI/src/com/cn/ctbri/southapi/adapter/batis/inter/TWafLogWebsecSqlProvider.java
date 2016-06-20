package com.cn.ctbri.southapi.adapter.batis.inter;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsec;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample;
import java.util.List;
import java.util.Map;

public class TWafLogWebsecSqlProvider {

    public String countByExample(TWafLogWebsecExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("t_waf_log_websec");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TWafLogWebsecExample example) {
        BEGIN();
        DELETE_FROM("t_waf_log_websec");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(TWafLogWebsec record) {
        BEGIN();
        INSERT_INTO("t_waf_log_websec");
        
        if (record.getLogId() != null) {
            VALUES("log_id", "#{logId,jdbcType=BIGINT}");
        }
        
        if (record.getResourceId() != null) {
            VALUES("resource_id", "#{resourceId,jdbcType=INTEGER}");
        }
        
        if (record.getResourceUri() != null) {
            VALUES("resource_uri", "#{resourceUri,jdbcType=VARCHAR}");
        }
        
        if (record.getResourceIp() != null) {
            VALUES("resource_ip", "#{resourceIp,jdbcType=VARCHAR}");
        }
        
        if (record.getSiteId() != null) {
            VALUES("site_id", "#{siteId,jdbcType=VARCHAR}");
        }
        
        if (record.getProtectId() != null) {
            VALUES("protect_id", "#{protectId,jdbcType=VARCHAR}");
        }
        
        if (record.getDstIp() != null) {
            VALUES("dst_ip", "#{dstIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDstPort() != null) {
            VALUES("dst_port", "#{dstPort,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcIp() != null) {
            VALUES("src_ip", "#{srcIp,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcPort() != null) {
            VALUES("src_port", "#{srcPort,jdbcType=VARCHAR}");
        }
        
        if (record.getMethod() != null) {
            VALUES("method", "#{method,jdbcType=VARCHAR}");
        }
        
        if (record.getDomain() != null) {
            VALUES("domain", "#{domain,jdbcType=VARCHAR}");
        }
        
        if (record.getUri() != null) {
            VALUES("uri", "#{uri,jdbcType=VARCHAR}");
        }
        
        if (record.getAlertlevel() != null) {
            VALUES("alertlevel", "#{alertlevel,jdbcType=VARCHAR}");
        }
        
        if (record.getEventType() != null) {
            VALUES("event_type", "#{eventType,jdbcType=VARCHAR}");
        }
        
        if (record.getStatTime() != null) {
            VALUES("stat_time", "#{statTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getPolicyId() != null) {
            VALUES("policy_id", "#{policyId,jdbcType=VARCHAR}");
        }
        
        if (record.getRuleId() != null) {
            VALUES("rule_id", "#{ruleId,jdbcType=VARCHAR}");
        }
        
        if (record.getAction() != null) {
            VALUES("action", "#{action,jdbcType=VARCHAR}");
        }
        
        if (record.getBlock() != null) {
            VALUES("block", "#{block,jdbcType=VARCHAR}");
        }
        
        if (record.getBlockInfo() != null) {
            VALUES("block_info", "#{blockInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getAlertinfo() != null) {
            VALUES("alertinfo", "#{alertinfo,jdbcType=VARCHAR}");
        }
        
        if (record.getProxyInfo() != null) {
            VALUES("proxy_info", "#{proxyInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getCharacters() != null) {
            VALUES("characters", "#{characters,jdbcType=VARCHAR}");
        }
        
        if (record.getCountNum() != null) {
            VALUES("count_num", "#{countNum,jdbcType=VARCHAR}");
        }
        
        if (record.getProtocolType() != null) {
            VALUES("protocol_type", "#{protocolType,jdbcType=VARCHAR}");
        }
        
        if (record.getWci() != null) {
            VALUES("wci", "#{wci,jdbcType=VARCHAR}");
        }
        
        if (record.getWsi() != null) {
            VALUES("wsi", "#{wsi,jdbcType=VARCHAR}");
        }
        
        if (record.getHttp() != null) {
            VALUES("http", "#{http,jdbcType=LONGVARBINARY}");
        }
        
        return SQL();
    }

    public String selectByExampleWithBLOBs(TWafLogWebsecExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("log_id");
        } else {
            SELECT("log_id");
        }
        SELECT("resource_id");
        SELECT("resource_uri");
        SELECT("resource_ip");
        SELECT("site_id");
        SELECT("protect_id");
        SELECT("dst_ip");
        SELECT("dst_port");
        SELECT("src_ip");
        SELECT("src_port");
        SELECT("method");
        SELECT("domain");
        SELECT("uri");
        SELECT("alertlevel");
        SELECT("event_type");
        SELECT("stat_time");
        SELECT("policy_id");
        SELECT("rule_id");
        SELECT("action");
        SELECT("block");
        SELECT("block_info");
        SELECT("alertinfo");
        SELECT("proxy_info");
        SELECT("characters");
        SELECT("count_num");
        SELECT("protocol_type");
        SELECT("wci");
        SELECT("wsi");
        SELECT("http");
        FROM("t_waf_log_websec");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String selectByExample(TWafLogWebsecExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("log_id");
        } else {
            SELECT("log_id");
        }
        SELECT("resource_id");
        SELECT("resource_uri");
        SELECT("resource_ip");
        SELECT("site_id");
        SELECT("protect_id");
        SELECT("dst_ip");
        SELECT("dst_port");
        SELECT("src_ip");
        SELECT("src_port");
        SELECT("method");
        SELECT("domain");
        SELECT("uri");
        SELECT("alertlevel");
        SELECT("event_type");
        SELECT("stat_time");
        SELECT("policy_id");
        SELECT("rule_id");
        SELECT("action");
        SELECT("block");
        SELECT("block_info");
        SELECT("alertinfo");
        SELECT("proxy_info");
        SELECT("characters");
        SELECT("count_num");
        SELECT("protocol_type");
        SELECT("wci");
        SELECT("wsi");
        FROM("t_waf_log_websec");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TWafLogWebsec record = (TWafLogWebsec) parameter.get("record");
        TWafLogWebsecExample example = (TWafLogWebsecExample) parameter.get("example");
        
        BEGIN();
        UPDATE("t_waf_log_websec");
        
        if (record.getLogId() != null) {
            SET("log_id = #{record.logId,jdbcType=BIGINT}");
        }
        
        if (record.getResourceId() != null) {
            SET("resource_id = #{record.resourceId,jdbcType=INTEGER}");
        }
        
        if (record.getResourceUri() != null) {
            SET("resource_uri = #{record.resourceUri,jdbcType=VARCHAR}");
        }
        
        if (record.getResourceIp() != null) {
            SET("resource_ip = #{record.resourceIp,jdbcType=VARCHAR}");
        }
        
        if (record.getSiteId() != null) {
            SET("site_id = #{record.siteId,jdbcType=VARCHAR}");
        }
        
        if (record.getProtectId() != null) {
            SET("protect_id = #{record.protectId,jdbcType=VARCHAR}");
        }
        
        if (record.getDstIp() != null) {
            SET("dst_ip = #{record.dstIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDstPort() != null) {
            SET("dst_port = #{record.dstPort,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcIp() != null) {
            SET("src_ip = #{record.srcIp,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcPort() != null) {
            SET("src_port = #{record.srcPort,jdbcType=VARCHAR}");
        }
        
        if (record.getMethod() != null) {
            SET("method = #{record.method,jdbcType=VARCHAR}");
        }
        
        if (record.getDomain() != null) {
            SET("domain = #{record.domain,jdbcType=VARCHAR}");
        }
        
        if (record.getUri() != null) {
            SET("uri = #{record.uri,jdbcType=VARCHAR}");
        }
        
        if (record.getAlertlevel() != null) {
            SET("alertlevel = #{record.alertlevel,jdbcType=VARCHAR}");
        }
        
        if (record.getEventType() != null) {
            SET("event_type = #{record.eventType,jdbcType=VARCHAR}");
        }
        
        if (record.getStatTime() != null) {
            SET("stat_time = #{record.statTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getPolicyId() != null) {
            SET("policy_id = #{record.policyId,jdbcType=VARCHAR}");
        }
        
        if (record.getRuleId() != null) {
            SET("rule_id = #{record.ruleId,jdbcType=VARCHAR}");
        }
        
        if (record.getAction() != null) {
            SET("action = #{record.action,jdbcType=VARCHAR}");
        }
        
        if (record.getBlock() != null) {
            SET("block = #{record.block,jdbcType=VARCHAR}");
        }
        
        if (record.getBlockInfo() != null) {
            SET("block_info = #{record.blockInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getAlertinfo() != null) {
            SET("alertinfo = #{record.alertinfo,jdbcType=VARCHAR}");
        }
        
        if (record.getProxyInfo() != null) {
            SET("proxy_info = #{record.proxyInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getCharacters() != null) {
            SET("characters = #{record.characters,jdbcType=VARCHAR}");
        }
        
        if (record.getCountNum() != null) {
            SET("count_num = #{record.countNum,jdbcType=VARCHAR}");
        }
        
        if (record.getProtocolType() != null) {
            SET("protocol_type = #{record.protocolType,jdbcType=VARCHAR}");
        }
        
        if (record.getWci() != null) {
            SET("wci = #{record.wci,jdbcType=VARCHAR}");
        }
        
        if (record.getWsi() != null) {
            SET("wsi = #{record.wsi,jdbcType=VARCHAR}");
        }
        
        if (record.getHttp() != null) {
            SET("http = #{record.http,jdbcType=LONGVARBINARY}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("t_waf_log_websec");
        
        SET("log_id = #{record.logId,jdbcType=BIGINT}");
        SET("resource_id = #{record.resourceId,jdbcType=INTEGER}");
        SET("resource_uri = #{record.resourceUri,jdbcType=VARCHAR}");
        SET("resource_ip = #{record.resourceIp,jdbcType=VARCHAR}");
        SET("site_id = #{record.siteId,jdbcType=VARCHAR}");
        SET("protect_id = #{record.protectId,jdbcType=VARCHAR}");
        SET("dst_ip = #{record.dstIp,jdbcType=VARCHAR}");
        SET("dst_port = #{record.dstPort,jdbcType=VARCHAR}");
        SET("src_ip = #{record.srcIp,jdbcType=VARCHAR}");
        SET("src_port = #{record.srcPort,jdbcType=VARCHAR}");
        SET("method = #{record.method,jdbcType=VARCHAR}");
        SET("domain = #{record.domain,jdbcType=VARCHAR}");
        SET("uri = #{record.uri,jdbcType=VARCHAR}");
        SET("alertlevel = #{record.alertlevel,jdbcType=VARCHAR}");
        SET("event_type = #{record.eventType,jdbcType=VARCHAR}");
        SET("stat_time = #{record.statTime,jdbcType=TIMESTAMP}");
        SET("policy_id = #{record.policyId,jdbcType=VARCHAR}");
        SET("rule_id = #{record.ruleId,jdbcType=VARCHAR}");
        SET("action = #{record.action,jdbcType=VARCHAR}");
        SET("block = #{record.block,jdbcType=VARCHAR}");
        SET("block_info = #{record.blockInfo,jdbcType=VARCHAR}");
        SET("alertinfo = #{record.alertinfo,jdbcType=VARCHAR}");
        SET("proxy_info = #{record.proxyInfo,jdbcType=VARCHAR}");
        SET("characters = #{record.characters,jdbcType=VARCHAR}");
        SET("count_num = #{record.countNum,jdbcType=VARCHAR}");
        SET("protocol_type = #{record.protocolType,jdbcType=VARCHAR}");
        SET("wci = #{record.wci,jdbcType=VARCHAR}");
        SET("wsi = #{record.wsi,jdbcType=VARCHAR}");
        SET("http = #{record.http,jdbcType=LONGVARBINARY}");
        
        TWafLogWebsecExample example = (TWafLogWebsecExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("t_waf_log_websec");
        
        SET("log_id = #{record.logId,jdbcType=BIGINT}");
        SET("resource_id = #{record.resourceId,jdbcType=INTEGER}");
        SET("resource_uri = #{record.resourceUri,jdbcType=VARCHAR}");
        SET("resource_ip = #{record.resourceIp,jdbcType=VARCHAR}");
        SET("site_id = #{record.siteId,jdbcType=VARCHAR}");
        SET("protect_id = #{record.protectId,jdbcType=VARCHAR}");
        SET("dst_ip = #{record.dstIp,jdbcType=VARCHAR}");
        SET("dst_port = #{record.dstPort,jdbcType=VARCHAR}");
        SET("src_ip = #{record.srcIp,jdbcType=VARCHAR}");
        SET("src_port = #{record.srcPort,jdbcType=VARCHAR}");
        SET("method = #{record.method,jdbcType=VARCHAR}");
        SET("domain = #{record.domain,jdbcType=VARCHAR}");
        SET("uri = #{record.uri,jdbcType=VARCHAR}");
        SET("alertlevel = #{record.alertlevel,jdbcType=VARCHAR}");
        SET("event_type = #{record.eventType,jdbcType=VARCHAR}");
        SET("stat_time = #{record.statTime,jdbcType=TIMESTAMP}");
        SET("policy_id = #{record.policyId,jdbcType=VARCHAR}");
        SET("rule_id = #{record.ruleId,jdbcType=VARCHAR}");
        SET("action = #{record.action,jdbcType=VARCHAR}");
        SET("block = #{record.block,jdbcType=VARCHAR}");
        SET("block_info = #{record.blockInfo,jdbcType=VARCHAR}");
        SET("alertinfo = #{record.alertinfo,jdbcType=VARCHAR}");
        SET("proxy_info = #{record.proxyInfo,jdbcType=VARCHAR}");
        SET("characters = #{record.characters,jdbcType=VARCHAR}");
        SET("count_num = #{record.countNum,jdbcType=VARCHAR}");
        SET("protocol_type = #{record.protocolType,jdbcType=VARCHAR}");
        SET("wci = #{record.wci,jdbcType=VARCHAR}");
        SET("wsi = #{record.wsi,jdbcType=VARCHAR}");
        
        TWafLogWebsecExample example = (TWafLogWebsecExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(TWafLogWebsec record) {
        BEGIN();
        UPDATE("t_waf_log_websec");
        
        if (record.getResourceId() != null) {
            SET("resource_id = #{resourceId,jdbcType=INTEGER}");
        }
        
        if (record.getResourceUri() != null) {
            SET("resource_uri = #{resourceUri,jdbcType=VARCHAR}");
        }
        
        if (record.getResourceIp() != null) {
            SET("resource_ip = #{resourceIp,jdbcType=VARCHAR}");
        }
        
        if (record.getSiteId() != null) {
            SET("site_id = #{siteId,jdbcType=VARCHAR}");
        }
        
        if (record.getProtectId() != null) {
            SET("protect_id = #{protectId,jdbcType=VARCHAR}");
        }
        
        if (record.getDstIp() != null) {
            SET("dst_ip = #{dstIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDstPort() != null) {
            SET("dst_port = #{dstPort,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcIp() != null) {
            SET("src_ip = #{srcIp,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcPort() != null) {
            SET("src_port = #{srcPort,jdbcType=VARCHAR}");
        }
        
        if (record.getMethod() != null) {
            SET("method = #{method,jdbcType=VARCHAR}");
        }
        
        if (record.getDomain() != null) {
            SET("domain = #{domain,jdbcType=VARCHAR}");
        }
        
        if (record.getUri() != null) {
            SET("uri = #{uri,jdbcType=VARCHAR}");
        }
        
        if (record.getAlertlevel() != null) {
            SET("alertlevel = #{alertlevel,jdbcType=VARCHAR}");
        }
        
        if (record.getEventType() != null) {
            SET("event_type = #{eventType,jdbcType=VARCHAR}");
        }
        
        if (record.getStatTime() != null) {
            SET("stat_time = #{statTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getPolicyId() != null) {
            SET("policy_id = #{policyId,jdbcType=VARCHAR}");
        }
        
        if (record.getRuleId() != null) {
            SET("rule_id = #{ruleId,jdbcType=VARCHAR}");
        }
        
        if (record.getAction() != null) {
            SET("action = #{action,jdbcType=VARCHAR}");
        }
        
        if (record.getBlock() != null) {
            SET("block = #{block,jdbcType=VARCHAR}");
        }
        
        if (record.getBlockInfo() != null) {
            SET("block_info = #{blockInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getAlertinfo() != null) {
            SET("alertinfo = #{alertinfo,jdbcType=VARCHAR}");
        }
        
        if (record.getProxyInfo() != null) {
            SET("proxy_info = #{proxyInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getCharacters() != null) {
            SET("characters = #{characters,jdbcType=VARCHAR}");
        }
        
        if (record.getCountNum() != null) {
            SET("count_num = #{countNum,jdbcType=VARCHAR}");
        }
        
        if (record.getProtocolType() != null) {
            SET("protocol_type = #{protocolType,jdbcType=VARCHAR}");
        }
        
        if (record.getWci() != null) {
            SET("wci = #{wci,jdbcType=VARCHAR}");
        }
        
        if (record.getWsi() != null) {
            SET("wsi = #{wsi,jdbcType=VARCHAR}");
        }
        
        if (record.getHttp() != null) {
            SET("http = #{http,jdbcType=LONGVARBINARY}");
        }
        
        WHERE("log_id = #{logId,jdbcType=BIGINT}");
        
        return SQL();
    }

    protected void applyWhere(TWafLogWebsecExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}