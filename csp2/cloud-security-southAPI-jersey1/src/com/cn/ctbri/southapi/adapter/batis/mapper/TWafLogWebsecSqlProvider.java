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
import static org.apache.ibatis.jdbc.SqlBuilder.GROUP_BY;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsec;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample.Criterion;

import sun.print.resources.serviceui;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample;
import java.util.List;
import java.util.Map;

public class TWafLogWebsecSqlProvider {
	private static final String YEAR = "year";
	private static final String MONTH = "month";
	private static final String DAY = "day";
	
    public String countByExample(TWafLogWebsecExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("t_waf_log_websec");
        applyWhere(example, false);
        return SQL();
    }
    
    public String countInTimeByExample(TWafLogWebsecExample example){
    	BEGIN();
    	if (example.getTimeUnit().equalsIgnoreCase(YEAR)) {
			SELECT("DATE_FORMAT(stat_time,'%Y') as time");
		}else if (example.getTimeUnit().equalsIgnoreCase(MONTH)) {
			SELECT("DATE_FORMAT(stat_time,'%Y-%m') as time");
		}else{
			SELECT("DATE_FORMAT(stat_time,'%Y-%m-%d') as time");
		}
    	SELECT("count(stat_time) as count");
    	FROM("t_waf_log_websec");
    	applyWhere(example, false);
    	GROUP_BY("time");
    	return SQL();
    }
    
    
    public String countEventTypeByExample(TWafLogWebsecExample example) {
        BEGIN();
        SELECT("count(event_type) as count");
        SELECT("event_type");
        FROM("t_waf_log_websec");
        applyWhere(example, false);
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        GROUP_BY("event_type");
        return SQL();
	}
    
    public String countAlertLevelByExample(TWafLogWebsecExample example){
    	BEGIN();
    	if (example.getTimeUnit().equalsIgnoreCase(MONTH)) {
			SELECT("DATE_FORMAT(stat_time,'%Y-%m') as time");
		}
    	SELECT("alertlevel");
    	SELECT("count(alertlevel) as count");
    	FROM("t_waf_log_websec");
    	applyWhere(example, false);
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
    	GROUP_BY("alertlevel");
    	if (example.getTimeUnit().equalsIgnoreCase(MONTH)) {
			GROUP_BY("time");
		}
    	return SQL();
    }
    
    public String countEventTypeThanCurrent(TWafLogWebsecExample example){
    	BEGIN();
    	SELECT("MIN(a.log_id) as log_id");
    	FROM(selectByExampleWithBLOBs(example)+"as a");
    	return SQL();
    }
    
    
    
    public String selectMaxByExample(TWafLogWebsecExample example) {
		BEGIN();
		SELECT("max(log_id) as log_id");
        FROM("t_waf_log_websec");
        applyWhere(example, false);
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
        if (example != null && example.getRows() != null){
        	if(example.getOffset() != null){
        		return SQL() + " limit " + example.getOffset()+","+example.getRows();
        	}
        	return SQL() + " limit " + example.getRows();
        }else{
            return SQL();
        }
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