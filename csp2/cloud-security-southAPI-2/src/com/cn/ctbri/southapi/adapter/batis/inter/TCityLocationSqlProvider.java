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

import com.cn.ctbri.southapi.adapter.batis.model.TCityLocation;
import com.cn.ctbri.southapi.adapter.batis.model.TCityLocationExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.TCityLocationExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.TCityLocationExample;
import java.util.List;
import java.util.Map;

public class TCityLocationSqlProvider {

    public String countByExample(TCityLocationExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("t_city_location");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TCityLocationExample example) {
        BEGIN();
        DELETE_FROM("t_city_location");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(TCityLocation record) {
        BEGIN();
        INSERT_INTO("t_city_location");
        
        if (record.getLocationId() != null) {
            VALUES("location_id", "#{locationId,jdbcType=BIGINT}");
        }
        
        if (record.getLocaleCode() != null) {
            VALUES("locale_code", "#{localeCode,jdbcType=VARCHAR}");
        }
        
        if (record.getContinentCode() != null) {
            VALUES("continent_code", "#{continentCode,jdbcType=VARCHAR}");
        }
        
        if (record.getContinentName() != null) {
            VALUES("continent_name", "#{continentName,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryIsoCode() != null) {
            VALUES("country_iso_code", "#{countryIsoCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryName() != null) {
            VALUES("country_name", "#{countryName,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision1IsoCode() != null) {
            VALUES("subdivision_1_iso_code", "#{subdivision1IsoCode,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision1Name() != null) {
            VALUES("subdivision_1_name", "#{subdivision1Name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision2IsoCode() != null) {
            VALUES("subdivision_2_iso_code", "#{subdivision2IsoCode,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision2Name() != null) {
            VALUES("subdivision_2_name", "#{subdivision2Name,jdbcType=VARCHAR}");
        }
        
        if (record.getCityName() != null) {
            VALUES("city_name", "#{cityName,jdbcType=VARCHAR}");
        }
        
        if (record.getMetroCode() != null) {
            VALUES("metro_code", "#{metroCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTimeZone() != null) {
            VALUES("time_zone", "#{timeZone,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExample(TCityLocationExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("location_id");
        } else {
            SELECT("location_id");
        }
        SELECT("locale_code");
        SELECT("continent_code");
        SELECT("continent_name");
        SELECT("country_iso_code");
        SELECT("country_name");
        SELECT("subdivision_1_iso_code");
        SELECT("subdivision_1_name");
        SELECT("subdivision_2_iso_code");
        SELECT("subdivision_2_name");
        SELECT("city_name");
        SELECT("metro_code");
        SELECT("time_zone");
        FROM("t_city_location");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TCityLocation record = (TCityLocation) parameter.get("record");
        TCityLocationExample example = (TCityLocationExample) parameter.get("example");
        
        BEGIN();
        UPDATE("t_city_location");
        
        if (record.getLocationId() != null) {
            SET("location_id = #{record.locationId,jdbcType=BIGINT}");
        }
        
        if (record.getLocaleCode() != null) {
            SET("locale_code = #{record.localeCode,jdbcType=VARCHAR}");
        }
        
        if (record.getContinentCode() != null) {
            SET("continent_code = #{record.continentCode,jdbcType=VARCHAR}");
        }
        
        if (record.getContinentName() != null) {
            SET("continent_name = #{record.continentName,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryIsoCode() != null) {
            SET("country_iso_code = #{record.countryIsoCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryName() != null) {
            SET("country_name = #{record.countryName,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision1IsoCode() != null) {
            SET("subdivision_1_iso_code = #{record.subdivision1IsoCode,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision1Name() != null) {
            SET("subdivision_1_name = #{record.subdivision1Name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision2IsoCode() != null) {
            SET("subdivision_2_iso_code = #{record.subdivision2IsoCode,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision2Name() != null) {
            SET("subdivision_2_name = #{record.subdivision2Name,jdbcType=VARCHAR}");
        }
        
        if (record.getCityName() != null) {
            SET("city_name = #{record.cityName,jdbcType=VARCHAR}");
        }
        
        if (record.getMetroCode() != null) {
            SET("metro_code = #{record.metroCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTimeZone() != null) {
            SET("time_zone = #{record.timeZone,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("t_city_location");
        
        SET("location_id = #{record.locationId,jdbcType=BIGINT}");
        SET("locale_code = #{record.localeCode,jdbcType=VARCHAR}");
        SET("continent_code = #{record.continentCode,jdbcType=VARCHAR}");
        SET("continent_name = #{record.continentName,jdbcType=VARCHAR}");
        SET("country_iso_code = #{record.countryIsoCode,jdbcType=VARCHAR}");
        SET("country_name = #{record.countryName,jdbcType=VARCHAR}");
        SET("subdivision_1_iso_code = #{record.subdivision1IsoCode,jdbcType=VARCHAR}");
        SET("subdivision_1_name = #{record.subdivision1Name,jdbcType=VARCHAR}");
        SET("subdivision_2_iso_code = #{record.subdivision2IsoCode,jdbcType=VARCHAR}");
        SET("subdivision_2_name = #{record.subdivision2Name,jdbcType=VARCHAR}");
        SET("city_name = #{record.cityName,jdbcType=VARCHAR}");
        SET("metro_code = #{record.metroCode,jdbcType=VARCHAR}");
        SET("time_zone = #{record.timeZone,jdbcType=VARCHAR}");
        
        TCityLocationExample example = (TCityLocationExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(TCityLocation record) {
        BEGIN();
        UPDATE("t_city_location");
        
        if (record.getLocaleCode() != null) {
            SET("locale_code = #{localeCode,jdbcType=VARCHAR}");
        }
        
        if (record.getContinentCode() != null) {
            SET("continent_code = #{continentCode,jdbcType=VARCHAR}");
        }
        
        if (record.getContinentName() != null) {
            SET("continent_name = #{continentName,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryIsoCode() != null) {
            SET("country_iso_code = #{countryIsoCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryName() != null) {
            SET("country_name = #{countryName,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision1IsoCode() != null) {
            SET("subdivision_1_iso_code = #{subdivision1IsoCode,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision1Name() != null) {
            SET("subdivision_1_name = #{subdivision1Name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision2IsoCode() != null) {
            SET("subdivision_2_iso_code = #{subdivision2IsoCode,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision2Name() != null) {
            SET("subdivision_2_name = #{subdivision2Name,jdbcType=VARCHAR}");
        }
        
        if (record.getCityName() != null) {
            SET("city_name = #{cityName,jdbcType=VARCHAR}");
        }
        
        if (record.getMetroCode() != null) {
            SET("metro_code = #{metroCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTimeZone() != null) {
            SET("time_zone = #{timeZone,jdbcType=VARCHAR}");
        }
        
        WHERE("location_id = #{locationId,jdbcType=BIGINT}");
        
        return SQL();
    }

    protected void applyWhere(TCityLocationExample example, boolean includeExamplePhrase) {
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