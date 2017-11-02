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

import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfo;
import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfoExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfoExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfoExample;
import java.util.List;
import java.util.Map;

public class TWafNsfocusTargetinfoSqlProvider {

    public String countByExample(TWafNsfocusTargetinfoExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("t_waf_nsfocus_targetinfo");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TWafNsfocusTargetinfoExample example) {
        BEGIN();
        DELETE_FROM("t_waf_nsfocus_targetinfo");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(TWafNsfocusTargetinfo record) {
        BEGIN();
        INSERT_INTO("t_waf_nsfocus_targetinfo");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=VARCHAR}");
        }
        
        if (record.getResourceid() != null) {
            VALUES("resourceId", "#{resourceid,jdbcType=INTEGER}");
        }
        
        if (record.getDeviceid() != null) {
            VALUES("deviceId", "#{deviceid,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSiteid() != null) {
            VALUES("siteId", "#{siteid,jdbcType=VARCHAR}");
        }
        
        if (record.getGroupid() != null) {
            VALUES("groupId", "#{groupid,jdbcType=VARCHAR}");
        }
        
        if (record.getVirtsiteid() != null) {
            VALUES("virtSiteId", "#{virtsiteid,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExample(TWafNsfocusTargetinfoExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("resourceId");
        SELECT("deviceId");
        SELECT("name");
        SELECT("siteId");
        SELECT("groupId");
        SELECT("virtSiteId");
        FROM("t_waf_nsfocus_targetinfo");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TWafNsfocusTargetinfo record = (TWafNsfocusTargetinfo) parameter.get("record");
        TWafNsfocusTargetinfoExample example = (TWafNsfocusTargetinfoExample) parameter.get("example");
        
        BEGIN();
        UPDATE("t_waf_nsfocus_targetinfo");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=VARCHAR}");
        }
        
        if (record.getResourceid() != null) {
            SET("resourceId = #{record.resourceid,jdbcType=INTEGER}");
        }
        
        if (record.getDeviceid() != null) {
            SET("deviceId = #{record.deviceid,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            SET("name = #{record.name,jdbcType=VARCHAR}");
        }
        
        if (record.getSiteid() != null) {
            SET("siteId = #{record.siteid,jdbcType=VARCHAR}");
        }
        
        if (record.getGroupid() != null) {
            SET("groupId = #{record.groupid,jdbcType=VARCHAR}");
        }
        
        if (record.getVirtsiteid() != null) {
            SET("virtSiteId = #{record.virtsiteid,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("t_waf_nsfocus_targetinfo");
        
        SET("id = #{record.id,jdbcType=VARCHAR}");
        SET("resourceId = #{record.resourceid,jdbcType=INTEGER}");
        SET("deviceId = #{record.deviceid,jdbcType=INTEGER}");
        SET("name = #{record.name,jdbcType=VARCHAR}");
        SET("siteId = #{record.siteid,jdbcType=VARCHAR}");
        SET("groupId = #{record.groupid,jdbcType=VARCHAR}");
        SET("virtSiteId = #{record.virtsiteid,jdbcType=VARCHAR}");
        
        TWafNsfocusTargetinfoExample example = (TWafNsfocusTargetinfoExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(TWafNsfocusTargetinfo record) {
        BEGIN();
        UPDATE("t_waf_nsfocus_targetinfo");
        
        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSiteid() != null) {
            SET("siteId = #{siteid,jdbcType=VARCHAR}");
        }
        
        if (record.getGroupid() != null) {
            SET("groupId = #{groupid,jdbcType=VARCHAR}");
        }
        
        if (record.getVirtsiteid() != null) {
            SET("virtSiteId = #{virtsiteid,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=VARCHAR}");
        WHERE("resourceId = #{resourceid,jdbcType=INTEGER}");
        WHERE("deviceId = #{deviceid,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(TWafNsfocusTargetinfoExample example, boolean includeExamplePhrase) {
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