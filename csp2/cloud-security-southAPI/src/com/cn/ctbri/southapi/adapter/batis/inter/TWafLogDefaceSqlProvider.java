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

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDeface;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDefaceExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDefaceExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDefaceExample;
import java.util.List;
import java.util.Map;

public class TWafLogDefaceSqlProvider {

    public String countByExample(TWafLogDefaceExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("t_waf_log_deface");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TWafLogDefaceExample example) {
        BEGIN();
        DELETE_FROM("t_waf_log_deface");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(TWafLogDeface record) {
        BEGIN();
        INSERT_INTO("t_waf_log_deface");
        
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
        
        if (record.getStatTime() != null) {
            VALUES("stat_time", "#{statTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAlertlevel() != null) {
            VALUES("alertlevel", "#{alertlevel,jdbcType=VARCHAR}");
        }
        
        if (record.getEventType() != null) {
            VALUES("event_type", "#{eventType,jdbcType=VARCHAR}");
        }
        
        if (record.getDstIp() != null) {
            VALUES("dst_ip", "#{dstIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDstPort() != null) {
            VALUES("dst_port", "#{dstPort,jdbcType=VARCHAR}");
        }
        
        if (record.getUrl() != null) {
            VALUES("url", "#{url,jdbcType=VARCHAR}");
        }
        
        if (record.getReason() != null) {
            VALUES("reason", "#{reason,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExample(TWafLogDefaceExample example) {
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
        SELECT("stat_time");
        SELECT("alertlevel");
        SELECT("event_type");
        SELECT("dst_ip");
        SELECT("dst_port");
        SELECT("url");
        SELECT("reason");
        FROM("t_waf_log_deface");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TWafLogDeface record = (TWafLogDeface) parameter.get("record");
        TWafLogDefaceExample example = (TWafLogDefaceExample) parameter.get("example");
        
        BEGIN();
        UPDATE("t_waf_log_deface");
        
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
        
        if (record.getStatTime() != null) {
            SET("stat_time = #{record.statTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAlertlevel() != null) {
            SET("alertlevel = #{record.alertlevel,jdbcType=VARCHAR}");
        }
        
        if (record.getEventType() != null) {
            SET("event_type = #{record.eventType,jdbcType=VARCHAR}");
        }
        
        if (record.getDstIp() != null) {
            SET("dst_ip = #{record.dstIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDstPort() != null) {
            SET("dst_port = #{record.dstPort,jdbcType=VARCHAR}");
        }
        
        if (record.getUrl() != null) {
            SET("url = #{record.url,jdbcType=VARCHAR}");
        }
        
        if (record.getReason() != null) {
            SET("reason = #{record.reason,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("t_waf_log_deface");
        
        SET("log_id = #{record.logId,jdbcType=BIGINT}");
        SET("resource_id = #{record.resourceId,jdbcType=INTEGER}");
        SET("resource_uri = #{record.resourceUri,jdbcType=VARCHAR}");
        SET("resource_ip = #{record.resourceIp,jdbcType=VARCHAR}");
        SET("site_id = #{record.siteId,jdbcType=VARCHAR}");
        SET("protect_id = #{record.protectId,jdbcType=VARCHAR}");
        SET("stat_time = #{record.statTime,jdbcType=TIMESTAMP}");
        SET("alertlevel = #{record.alertlevel,jdbcType=VARCHAR}");
        SET("event_type = #{record.eventType,jdbcType=VARCHAR}");
        SET("dst_ip = #{record.dstIp,jdbcType=VARCHAR}");
        SET("dst_port = #{record.dstPort,jdbcType=VARCHAR}");
        SET("url = #{record.url,jdbcType=VARCHAR}");
        SET("reason = #{record.reason,jdbcType=VARCHAR}");
        
        TWafLogDefaceExample example = (TWafLogDefaceExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(TWafLogDeface record) {
        BEGIN();
        UPDATE("t_waf_log_deface");
        
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
        
        if (record.getStatTime() != null) {
            SET("stat_time = #{statTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAlertlevel() != null) {
            SET("alertlevel = #{alertlevel,jdbcType=VARCHAR}");
        }
        
        if (record.getEventType() != null) {
            SET("event_type = #{eventType,jdbcType=VARCHAR}");
        }
        
        if (record.getDstIp() != null) {
            SET("dst_ip = #{dstIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDstPort() != null) {
            SET("dst_port = #{dstPort,jdbcType=VARCHAR}");
        }
        
        if (record.getUrl() != null) {
            SET("url = #{url,jdbcType=VARCHAR}");
        }
        
        if (record.getReason() != null) {
            SET("reason = #{reason,jdbcType=VARCHAR}");
        }
        
        WHERE("log_id = #{logId,jdbcType=BIGINT}");
        
        return SQL();
    }

    protected void applyWhere(TWafLogDefaceExample example, boolean includeExamplePhrase) {
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