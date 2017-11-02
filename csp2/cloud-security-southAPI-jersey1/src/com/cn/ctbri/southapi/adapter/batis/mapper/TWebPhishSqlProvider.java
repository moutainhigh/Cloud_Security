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

import com.cn.ctbri.southapi.adapter.batis.model.TWebPhish;
import com.cn.ctbri.southapi.adapter.batis.model.TWebPhishExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.TWebPhishExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.TWebPhishExample;
import java.util.List;
import java.util.Map;

public class TWebPhishSqlProvider {

    public String countByExample(TWebPhishExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("t_web_phish");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TWebPhishExample example) {
        BEGIN();
        DELETE_FROM("t_web_phish");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(TWebPhish record) {
        BEGIN();
        INSERT_INTO("t_web_phish");
        
        if (record.getWebphishId() != null) {
            VALUES("webphish_id", "#{webphishId,jdbcType=BIGINT}");
        }
        
        if (record.getUrlsign() != null) {
            VALUES("urlsign", "#{urlsign,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishUrl() != null) {
            VALUES("webphish_url", "#{webphishUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishField() != null) {
            VALUES("webphish_field", "#{webphishField,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishDomain() != null) {
            VALUES("webphish_domain", "#{webphishDomain,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishIp() != null) {
            VALUES("webphish_ip", "#{webphishIp,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishAsn() != null) {
            VALUES("webphish_asn", "#{webphishAsn,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishAsnname() != null) {
            VALUES("webphish_asnname", "#{webphishAsnname,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishCountry() != null) {
            VALUES("webphish_country", "#{webphishCountry,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishCountrycode() != null) {
            VALUES("webphish_countrycode", "#{webphishCountrycode,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishSubdivision1() != null) {
            VALUES("webphish_subdivision1", "#{webphishSubdivision1,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishSubdivision2() != null) {
            VALUES("webphish_subdivision2", "#{webphishSubdivision2,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishCity() != null) {
            VALUES("webphish_city", "#{webphishCity,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishTarget() != null) {
            VALUES("webphish_target", "#{webphishTarget,jdbcType=VARCHAR}");
        }
        
        if (record.getVerifiedTime() != null) {
            VALUES("verified_time", "#{verifiedTime,jdbcType=CHAR}");
        }
        
        if (record.getFromSource() != null) {
            VALUES("from_source", "#{fromSource,jdbcType=VARCHAR}");
        }
        
        if (record.getFromSourceId() != null) {
            VALUES("from_source_id", "#{fromSourceId,jdbcType=BIGINT}");
        }
        
        if (record.getFromSourceDetail() != null) {
            VALUES("from_source_detail", "#{fromSourceDetail,jdbcType=VARCHAR}");
        }
        
        if (record.getFromSourceReserve() != null) {
            VALUES("from_source_reserve", "#{fromSourceReserve,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getExpireTime() != null) {
            VALUES("expire_time", "#{expireTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsvalid() != null) {
            VALUES("isvalid", "#{isvalid,jdbcType=INTEGER}");
        }
        
        if (record.getReserve() != null) {
            VALUES("reserve", "#{reserve,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExample(TWebPhishExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("webphish_id");
        } else {
            SELECT("webphish_id");
        }
        SELECT("urlsign");
        SELECT("webphish_url");
        SELECT("webphish_field");
        SELECT("webphish_domain");
        SELECT("webphish_ip");
        SELECT("webphish_asn");
        SELECT("webphish_asnname");
        SELECT("webphish_country");
        SELECT("webphish_countrycode");
        SELECT("webphish_subdivision1");
        SELECT("webphish_subdivision2");
        SELECT("webphish_city");
        SELECT("webphish_target");
        SELECT("verified_time");
        SELECT("from_source");
        SELECT("from_source_id");
        SELECT("from_source_detail");
        SELECT("from_source_reserve");
        SELECT("update_time");
        SELECT("expire_time");
        SELECT("isvalid");
        SELECT("reserve");
        FROM("t_web_phish");
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

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TWebPhish record = (TWebPhish) parameter.get("record");
        TWebPhishExample example = (TWebPhishExample) parameter.get("example");
        
        BEGIN();
        UPDATE("t_web_phish");
        
        if (record.getWebphishId() != null) {
            SET("webphish_id = #{record.webphishId,jdbcType=BIGINT}");
        }
        
        if (record.getUrlsign() != null) {
            SET("urlsign = #{record.urlsign,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishUrl() != null) {
            SET("webphish_url = #{record.webphishUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishField() != null) {
            SET("webphish_field = #{record.webphishField,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishDomain() != null) {
            SET("webphish_domain = #{record.webphishDomain,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishIp() != null) {
            SET("webphish_ip = #{record.webphishIp,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishAsn() != null) {
            SET("webphish_asn = #{record.webphishAsn,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishAsnname() != null) {
            SET("webphish_asnname = #{record.webphishAsnname,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishCountry() != null) {
            SET("webphish_country = #{record.webphishCountry,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishCountrycode() != null) {
            SET("webphish_countrycode = #{record.webphishCountrycode,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishSubdivision1() != null) {
            SET("webphish_subdivision1 = #{record.webphishSubdivision1,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishSubdivision2() != null) {
            SET("webphish_subdivision2 = #{record.webphishSubdivision2,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishCity() != null) {
            SET("webphish_city = #{record.webphishCity,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishTarget() != null) {
            SET("webphish_target = #{record.webphishTarget,jdbcType=VARCHAR}");
        }
        
        if (record.getVerifiedTime() != null) {
            SET("verified_time = #{record.verifiedTime,jdbcType=CHAR}");
        }
        
        if (record.getFromSource() != null) {
            SET("from_source = #{record.fromSource,jdbcType=VARCHAR}");
        }
        
        if (record.getFromSourceId() != null) {
            SET("from_source_id = #{record.fromSourceId,jdbcType=BIGINT}");
        }
        
        if (record.getFromSourceDetail() != null) {
            SET("from_source_detail = #{record.fromSourceDetail,jdbcType=VARCHAR}");
        }
        
        if (record.getFromSourceReserve() != null) {
            SET("from_source_reserve = #{record.fromSourceReserve,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getExpireTime() != null) {
            SET("expire_time = #{record.expireTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsvalid() != null) {
            SET("isvalid = #{record.isvalid,jdbcType=INTEGER}");
        }
        
        if (record.getReserve() != null) {
            SET("reserve = #{record.reserve,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("t_web_phish");
        
        SET("webphish_id = #{record.webphishId,jdbcType=BIGINT}");
        SET("urlsign = #{record.urlsign,jdbcType=VARCHAR}");
        SET("webphish_url = #{record.webphishUrl,jdbcType=VARCHAR}");
        SET("webphish_field = #{record.webphishField,jdbcType=VARCHAR}");
        SET("webphish_domain = #{record.webphishDomain,jdbcType=VARCHAR}");
        SET("webphish_ip = #{record.webphishIp,jdbcType=VARCHAR}");
        SET("webphish_asn = #{record.webphishAsn,jdbcType=VARCHAR}");
        SET("webphish_asnname = #{record.webphishAsnname,jdbcType=VARCHAR}");
        SET("webphish_country = #{record.webphishCountry,jdbcType=VARCHAR}");
        SET("webphish_countrycode = #{record.webphishCountrycode,jdbcType=VARCHAR}");
        SET("webphish_subdivision1 = #{record.webphishSubdivision1,jdbcType=VARCHAR}");
        SET("webphish_subdivision2 = #{record.webphishSubdivision2,jdbcType=VARCHAR}");
        SET("webphish_city = #{record.webphishCity,jdbcType=VARCHAR}");
        SET("webphish_target = #{record.webphishTarget,jdbcType=VARCHAR}");
        SET("verified_time = #{record.verifiedTime,jdbcType=CHAR}");
        SET("from_source = #{record.fromSource,jdbcType=VARCHAR}");
        SET("from_source_id = #{record.fromSourceId,jdbcType=BIGINT}");
        SET("from_source_detail = #{record.fromSourceDetail,jdbcType=VARCHAR}");
        SET("from_source_reserve = #{record.fromSourceReserve,jdbcType=VARCHAR}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        SET("expire_time = #{record.expireTime,jdbcType=TIMESTAMP}");
        SET("isvalid = #{record.isvalid,jdbcType=INTEGER}");
        SET("reserve = #{record.reserve,jdbcType=VARCHAR}");
        
        TWebPhishExample example = (TWebPhishExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(TWebPhish record) {
        BEGIN();
        UPDATE("t_web_phish");
        
        if (record.getWebphishUrl() != null) {
            SET("webphish_url = #{webphishUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishField() != null) {
            SET("webphish_field = #{webphishField,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishDomain() != null) {
            SET("webphish_domain = #{webphishDomain,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishIp() != null) {
            SET("webphish_ip = #{webphishIp,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishAsn() != null) {
            SET("webphish_asn = #{webphishAsn,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishAsnname() != null) {
            SET("webphish_asnname = #{webphishAsnname,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishCountry() != null) {
            SET("webphish_country = #{webphishCountry,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishCountrycode() != null) {
            SET("webphish_countrycode = #{webphishCountrycode,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishSubdivision1() != null) {
            SET("webphish_subdivision1 = #{webphishSubdivision1,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishSubdivision2() != null) {
            SET("webphish_subdivision2 = #{webphishSubdivision2,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishCity() != null) {
            SET("webphish_city = #{webphishCity,jdbcType=VARCHAR}");
        }
        
        if (record.getWebphishTarget() != null) {
            SET("webphish_target = #{webphishTarget,jdbcType=VARCHAR}");
        }
        
        if (record.getVerifiedTime() != null) {
            SET("verified_time = #{verifiedTime,jdbcType=CHAR}");
        }
        
        if (record.getFromSource() != null) {
            SET("from_source = #{fromSource,jdbcType=VARCHAR}");
        }
        
        if (record.getFromSourceId() != null) {
            SET("from_source_id = #{fromSourceId,jdbcType=BIGINT}");
        }
        
        if (record.getFromSourceDetail() != null) {
            SET("from_source_detail = #{fromSourceDetail,jdbcType=VARCHAR}");
        }
        
        if (record.getFromSourceReserve() != null) {
            SET("from_source_reserve = #{fromSourceReserve,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getExpireTime() != null) {
            SET("expire_time = #{expireTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsvalid() != null) {
            SET("isvalid = #{isvalid,jdbcType=INTEGER}");
        }
        
        if (record.getReserve() != null) {
            SET("reserve = #{reserve,jdbcType=VARCHAR}");
        }
        
        WHERE("webphish_id = #{webphishId,jdbcType=BIGINT}");
        WHERE("urlsign = #{urlsign,jdbcType=VARCHAR}");
        
        return SQL();
    }

    protected void applyWhere(TWebPhishExample example, boolean includeExamplePhrase) {
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