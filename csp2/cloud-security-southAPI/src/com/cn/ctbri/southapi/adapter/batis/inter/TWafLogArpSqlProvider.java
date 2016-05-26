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

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArp;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArpExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArpExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArpExample;
import java.util.List;
import java.util.Map;

public class TWafLogArpSqlProvider {

    public String countByExample(TWafLogArpExample example) {
        BEGIN();
        SELECT("count (*)");
        FROM("t_waf_log_arp");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TWafLogArpExample example) {
        BEGIN();
        DELETE_FROM("t_waf_log_arp");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(TWafLogArp record) {
        BEGIN();
        INSERT_INTO("t_waf_log_arp");
        
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
        
        if (record.getStatTime() != null) {
            VALUES("stat_time", "#{statTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAlertlevel() != null) {
            VALUES("alertlevel", "#{alertlevel,jdbcType=VARCHAR}");
        }
        
        if (record.getEventType() != null) {
            VALUES("event_type", "#{eventType,jdbcType=VARCHAR}");
        }
        
        if (record.getAttackType() != null) {
            VALUES("attack_type", "#{attackType,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcIp() != null) {
            VALUES("src_ip", "#{srcIp,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcMac() != null) {
            VALUES("src_mac", "#{srcMac,jdbcType=VARCHAR}");
        }
        
        if (record.getDstIp() != null) {
            VALUES("dst_ip", "#{dstIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDstMac() != null) {
            VALUES("dst_mac", "#{dstMac,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getAction() != null) {
            VALUES("action", "#{action,jdbcType=VARCHAR}");
        }
        
        if (record.getDefIp() != null) {
            VALUES("def_ip", "#{defIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDefMac() != null) {
            VALUES("def_mac", "#{defMac,jdbcType=VARCHAR}");
        }
        
        if (record.getConflitMac() != null) {
            VALUES("conflit_mac", "#{conflitMac,jdbcType=VARCHAR}");
        }
        
        if (record.getCountNum() != null) {
            VALUES("count_num", "#{countNum,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String selectByExample(TWafLogArpExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("log_id");
        } else {
            SELECT("log_id");
        }
        SELECT("resource_id");
        SELECT("resource_uri");
        SELECT("resource_ip");
        SELECT("stat_time");
        SELECT("alertlevel");
        SELECT("event_type");
        SELECT("attack_type");
        SELECT("src_ip");
        SELECT("src_mac");
        SELECT("dst_ip");
        SELECT("dst_mac");
        SELECT("status");
        SELECT("action");
        SELECT("def_ip");
        SELECT("def_mac");
        SELECT("conflit_mac");
        SELECT("count_num");
        FROM("t_waf_log_arp");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TWafLogArp record = (TWafLogArp) parameter.get("record");
        TWafLogArpExample example = (TWafLogArpExample) parameter.get("example");
        
        BEGIN();
        UPDATE("t_waf_log_arp");
        
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
        
        if (record.getStatTime() != null) {
            SET("stat_time = #{record.statTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAlertlevel() != null) {
            SET("alertlevel = #{record.alertlevel,jdbcType=VARCHAR}");
        }
        
        if (record.getEventType() != null) {
            SET("event_type = #{record.eventType,jdbcType=VARCHAR}");
        }
        
        if (record.getAttackType() != null) {
            SET("attack_type = #{record.attackType,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcIp() != null) {
            SET("src_ip = #{record.srcIp,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcMac() != null) {
            SET("src_mac = #{record.srcMac,jdbcType=VARCHAR}");
        }
        
        if (record.getDstIp() != null) {
            SET("dst_ip = #{record.dstIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDstMac() != null) {
            SET("dst_mac = #{record.dstMac,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{record.status,jdbcType=VARCHAR}");
        }
        
        if (record.getAction() != null) {
            SET("action = #{record.action,jdbcType=VARCHAR}");
        }
        
        if (record.getDefIp() != null) {
            SET("def_ip = #{record.defIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDefMac() != null) {
            SET("def_mac = #{record.defMac,jdbcType=VARCHAR}");
        }
        
        if (record.getConflitMac() != null) {
            SET("conflit_mac = #{record.conflitMac,jdbcType=VARCHAR}");
        }
        
        if (record.getCountNum() != null) {
            SET("count_num = #{record.countNum,jdbcType=INTEGER}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("t_waf_log_arp");
        
        SET("log_id = #{record.logId,jdbcType=BIGINT}");
        SET("resource_id = #{record.resourceId,jdbcType=INTEGER}");
        SET("resource_uri = #{record.resourceUri,jdbcType=VARCHAR}");
        SET("resource_ip = #{record.resourceIp,jdbcType=VARCHAR}");
        SET("stat_time = #{record.statTime,jdbcType=TIMESTAMP}");
        SET("alertlevel = #{record.alertlevel,jdbcType=VARCHAR}");
        SET("event_type = #{record.eventType,jdbcType=VARCHAR}");
        SET("attack_type = #{record.attackType,jdbcType=VARCHAR}");
        SET("src_ip = #{record.srcIp,jdbcType=VARCHAR}");
        SET("src_mac = #{record.srcMac,jdbcType=VARCHAR}");
        SET("dst_ip = #{record.dstIp,jdbcType=VARCHAR}");
        SET("dst_mac = #{record.dstMac,jdbcType=VARCHAR}");
        SET("status = #{record.status,jdbcType=VARCHAR}");
        SET("action = #{record.action,jdbcType=VARCHAR}");
        SET("def_ip = #{record.defIp,jdbcType=VARCHAR}");
        SET("def_mac = #{record.defMac,jdbcType=VARCHAR}");
        SET("conflit_mac = #{record.conflitMac,jdbcType=VARCHAR}");
        SET("count_num = #{record.countNum,jdbcType=INTEGER}");
        
        TWafLogArpExample example = (TWafLogArpExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(TWafLogArp record) {
        BEGIN();
        UPDATE("t_waf_log_arp");
        
        if (record.getResourceId() != null) {
            SET("resource_id = #{resourceId,jdbcType=INTEGER}");
        }
        
        if (record.getResourceUri() != null) {
            SET("resource_uri = #{resourceUri,jdbcType=VARCHAR}");
        }
        
        if (record.getResourceIp() != null) {
            SET("resource_ip = #{resourceIp,jdbcType=VARCHAR}");
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
        
        if (record.getAttackType() != null) {
            SET("attack_type = #{attackType,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcIp() != null) {
            SET("src_ip = #{srcIp,jdbcType=VARCHAR}");
        }
        
        if (record.getSrcMac() != null) {
            SET("src_mac = #{srcMac,jdbcType=VARCHAR}");
        }
        
        if (record.getDstIp() != null) {
            SET("dst_ip = #{dstIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDstMac() != null) {
            SET("dst_mac = #{dstMac,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{status,jdbcType=VARCHAR}");
        }
        
        if (record.getAction() != null) {
            SET("action = #{action,jdbcType=VARCHAR}");
        }
        
        if (record.getDefIp() != null) {
            SET("def_ip = #{defIp,jdbcType=VARCHAR}");
        }
        
        if (record.getDefMac() != null) {
            SET("def_mac = #{defMac,jdbcType=VARCHAR}");
        }
        
        if (record.getConflitMac() != null) {
            SET("conflit_mac = #{conflitMac,jdbcType=VARCHAR}");
        }
        
        if (record.getCountNum() != null) {
            SET("count_num = #{countNum,jdbcType=INTEGER}");
        }
        
        WHERE("log_id = #{logId,jdbcType=BIGINT}");
        
        return SQL();
    }

    protected void applyWhere(TWafLogArpExample example, boolean includeExamplePhrase) {
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