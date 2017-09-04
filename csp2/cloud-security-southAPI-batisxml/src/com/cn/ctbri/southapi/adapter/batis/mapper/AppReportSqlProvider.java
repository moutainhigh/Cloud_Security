package com.cn.ctbri.southapi.adapter.batis.mapper;

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

import com.cn.ctbri.southapi.adapter.batis.model.AppReport;
import com.cn.ctbri.southapi.adapter.batis.model.AppReportExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.AppReportExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.AppReportExample;
import java.util.List;
import java.util.Map;

public class AppReportSqlProvider {

    public String countByExample(AppReportExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("app_report");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(AppReportExample example) {
        BEGIN();
        DELETE_FROM("app_report");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(AppReport record) {
        BEGIN();
        INSERT_INTO("app_report");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getTaskId() != null) {
            VALUES("task_id", "#{taskId,jdbcType=INTEGER}");
        }
        
        if (record.getHostStatus() != null) {
            VALUES("host_status", "#{hostStatus,jdbcType=VARCHAR}");
        }
        
        if (record.getAddTime() != null) {
            VALUES("add_time", "#{addTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResponseTime() != null) {
            VALUES("response_time", "#{responseTime,jdbcType=VARCHAR}");
        }
        
        if (record.getPortReport() != null) {
            VALUES("port_report", "#{portReport,jdbcType=LONGVARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExampleWithBLOBs(AppReportExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("task_id");
        SELECT("host_status");
        SELECT("add_time");
        SELECT("response_time");
        SELECT("port_report");
        FROM("app_report");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String selectByExample(AppReportExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("task_id");
        SELECT("host_status");
        SELECT("add_time");
        SELECT("response_time");
        FROM("app_report");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        AppReport record = (AppReport) parameter.get("record");
        AppReportExample example = (AppReportExample) parameter.get("example");
        
        BEGIN();
        UPDATE("app_report");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getTaskId() != null) {
            SET("task_id = #{record.taskId,jdbcType=INTEGER}");
        }
        
        if (record.getHostStatus() != null) {
            SET("host_status = #{record.hostStatus,jdbcType=VARCHAR}");
        }
        
        if (record.getAddTime() != null) {
            SET("add_time = #{record.addTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResponseTime() != null) {
            SET("response_time = #{record.responseTime,jdbcType=VARCHAR}");
        }
        
        if (record.getPortReport() != null) {
            SET("port_report = #{record.portReport,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("app_report");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("task_id = #{record.taskId,jdbcType=INTEGER}");
        SET("host_status = #{record.hostStatus,jdbcType=VARCHAR}");
        SET("add_time = #{record.addTime,jdbcType=TIMESTAMP}");
        SET("response_time = #{record.responseTime,jdbcType=VARCHAR}");
        SET("port_report = #{record.portReport,jdbcType=LONGVARCHAR}");
        
        AppReportExample example = (AppReportExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("app_report");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("task_id = #{record.taskId,jdbcType=INTEGER}");
        SET("host_status = #{record.hostStatus,jdbcType=VARCHAR}");
        SET("add_time = #{record.addTime,jdbcType=TIMESTAMP}");
        SET("response_time = #{record.responseTime,jdbcType=VARCHAR}");
        
        AppReportExample example = (AppReportExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(AppReport record) {
        BEGIN();
        UPDATE("app_report");
        
        if (record.getTaskId() != null) {
            SET("task_id = #{taskId,jdbcType=INTEGER}");
        }
        
        if (record.getHostStatus() != null) {
            SET("host_status = #{hostStatus,jdbcType=VARCHAR}");
        }
        
        if (record.getAddTime() != null) {
            SET("add_time = #{addTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResponseTime() != null) {
            SET("response_time = #{responseTime,jdbcType=VARCHAR}");
        }
        
        if (record.getPortReport() != null) {
            SET("port_report = #{portReport,jdbcType=LONGVARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(AppReportExample example, boolean includeExamplePhrase) {
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