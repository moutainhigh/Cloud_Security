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

import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishTargetCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishTargetCountExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishTargetCountExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishTargetCountExample;
import java.util.List;
import java.util.Map;

public class TViewWebPhishTargetCountSqlProvider {

    public String countByExample(TViewWebPhishTargetCountExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("t_view_web_phish_target_count");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TViewWebPhishTargetCountExample example) {
        BEGIN();
        DELETE_FROM("t_view_web_phish_target_count");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(TViewWebPhishTargetCount record) {
        BEGIN();
        INSERT_INTO("t_view_web_phish_target_count");
        
        if (record.getWebphishTarget() != null) {
            VALUES("webphish_target", "#{webphishTarget,jdbcType=VARCHAR}");
        }
        
        if (record.getCount() != null) {
            VALUES("COUNT", "#{count,jdbcType=BIGINT}");
        }
        
        if (record.getIsvalid() != null) {
            VALUES("isvalid", "#{isvalid,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String selectByExample(TViewWebPhishTargetCountExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("webphish_target");
        } else {
            SELECT("webphish_target");
        }
        SELECT("COUNT");
        SELECT("isvalid");
        FROM("t_view_web_phish_target_count");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        if (example != null && example.getRows() != null){
        	if(example.getOffset() != null){
        		return SQL() + " limit " + example.getOffset()+","+example.getRows();
        	}
        	return SQL() + " limit " + example.getRows();
        }else{
            return SQL();
        }
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TViewWebPhishTargetCount record = (TViewWebPhishTargetCount) parameter.get("record");
        TViewWebPhishTargetCountExample example = (TViewWebPhishTargetCountExample) parameter.get("example");
        
        BEGIN();
        UPDATE("t_view_web_phish_target_count");
        
        if (record.getWebphishTarget() != null) {
            SET("webphish_target = #{record.webphishTarget,jdbcType=VARCHAR}");
        }
        
        if (record.getCount() != null) {
            SET("COUNT = #{record.count,jdbcType=BIGINT}");
        }
        
        if (record.getIsvalid() != null) {
            SET("isvalid = #{record.isvalid,jdbcType=INTEGER}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("t_view_web_phish_target_count");
        
        SET("webphish_target = #{record.webphishTarget,jdbcType=VARCHAR}");
        SET("COUNT = #{record.count,jdbcType=BIGINT}");
        SET("isvalid = #{record.isvalid,jdbcType=INTEGER}");
        
        TViewWebPhishTargetCountExample example = (TViewWebPhishTargetCountExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    protected void applyWhere(TViewWebPhishTargetCountExample example, boolean includeExamplePhrase) {
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