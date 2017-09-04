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

import com.cn.ctbri.southapi.adapter.batis.model.AppTask;
import com.cn.ctbri.southapi.adapter.batis.model.AppTaskExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.AppTaskExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.AppTaskExample;
import java.util.List;
import java.util.Map;

public class AppTaskSqlProvider {

    public String countByExample(AppTaskExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("app_task");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(AppTaskExample example) {
        BEGIN();
        DELETE_FROM("app_task");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(AppTask record) {
        BEGIN();
        INSERT_INTO("app_task");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getTaskType() != null) {
            VALUES("task_type", "#{taskType,jdbcType=VARCHAR}");
        }
        
        if (record.getAgentId() != null) {
            VALUES("agent_id", "#{agentId,jdbcType=VARCHAR}");
        }
        
        if (record.getTarget() != null) {
            VALUES("target", "#{target,jdbcType=VARCHAR}");
        }
        
        if (record.getPort() != null) {
            VALUES("port", "#{port,jdbcType=VARCHAR}");
        }
        
        if (record.getOptions() != null) {
            VALUES("options", "#{options,jdbcType=VARCHAR}");
        }
        
        if (record.getIntervalTime() != null) {
            VALUES("interval_time", "#{intervalTime,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getRunTime() != null) {
            VALUES("run_time", "#{runTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            VALUES("end_time", "#{endTime,jdbcType=VARCHAR}");
        }
        
        if (record.getInsertTime() != null) {
            VALUES("insert_time", "#{insertTime,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String selectByExample(AppTaskExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("task_type");
        SELECT("agent_id");
        SELECT("target");
        SELECT("port");
        SELECT("options");
        SELECT("interval_time");
        SELECT("status");
        SELECT("run_time");
        SELECT("end_time");
        SELECT("insert_time");
        FROM("app_task");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        AppTask record = (AppTask) parameter.get("record");
        AppTaskExample example = (AppTaskExample) parameter.get("example");
        
        BEGIN();
        UPDATE("app_task");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getTaskType() != null) {
            SET("task_type = #{record.taskType,jdbcType=VARCHAR}");
        }
        
        if (record.getAgentId() != null) {
            SET("agent_id = #{record.agentId,jdbcType=VARCHAR}");
        }
        
        if (record.getTarget() != null) {
            SET("target = #{record.target,jdbcType=VARCHAR}");
        }
        
        if (record.getPort() != null) {
            SET("port = #{record.port,jdbcType=VARCHAR}");
        }
        
        if (record.getOptions() != null) {
            SET("options = #{record.options,jdbcType=VARCHAR}");
        }
        
        if (record.getIntervalTime() != null) {
            SET("interval_time = #{record.intervalTime,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{record.status,jdbcType=VARCHAR}");
        }
        
        if (record.getRunTime() != null) {
            SET("run_time = #{record.runTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            SET("end_time = #{record.endTime,jdbcType=VARCHAR}");
        }
        
        if (record.getInsertTime() != null) {
            SET("insert_time = #{record.insertTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("app_task");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("task_type = #{record.taskType,jdbcType=VARCHAR}");
        SET("agent_id = #{record.agentId,jdbcType=VARCHAR}");
        SET("target = #{record.target,jdbcType=VARCHAR}");
        SET("port = #{record.port,jdbcType=VARCHAR}");
        SET("options = #{record.options,jdbcType=VARCHAR}");
        SET("interval_time = #{record.intervalTime,jdbcType=VARCHAR}");
        SET("status = #{record.status,jdbcType=VARCHAR}");
        SET("run_time = #{record.runTime,jdbcType=TIMESTAMP}");
        SET("end_time = #{record.endTime,jdbcType=VARCHAR}");
        SET("insert_time = #{record.insertTime,jdbcType=TIMESTAMP}");
        
        AppTaskExample example = (AppTaskExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(AppTask record) {
        BEGIN();
        UPDATE("app_task");
        
        if (record.getTaskType() != null) {
            SET("task_type = #{taskType,jdbcType=VARCHAR}");
        }
        
        if (record.getAgentId() != null) {
            SET("agent_id = #{agentId,jdbcType=VARCHAR}");
        }
        
        if (record.getTarget() != null) {
            SET("target = #{target,jdbcType=VARCHAR}");
        }
        
        if (record.getPort() != null) {
            SET("port = #{port,jdbcType=VARCHAR}");
        }
        
        if (record.getOptions() != null) {
            SET("options = #{options,jdbcType=VARCHAR}");
        }
        
        if (record.getIntervalTime() != null) {
            SET("interval_time = #{intervalTime,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{status,jdbcType=VARCHAR}");
        }
        
        if (record.getRunTime() != null) {
            SET("run_time = #{runTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            SET("end_time = #{endTime,jdbcType=VARCHAR}");
        }
        
        if (record.getInsertTime() != null) {
            SET("insert_time = #{insertTime,jdbcType=TIMESTAMP}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(AppTaskExample example, boolean includeExamplePhrase) {
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