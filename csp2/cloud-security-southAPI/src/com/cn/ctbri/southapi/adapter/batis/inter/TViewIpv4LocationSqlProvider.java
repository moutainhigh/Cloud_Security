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

import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4Location;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4LocationExample.Criteria;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4LocationExample.Criterion;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4LocationExample;
import java.util.List;
import java.util.Map;

public class TViewIpv4LocationSqlProvider {

    public String countByExample(TViewIpv4LocationExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("t_view_ipv4_location");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TViewIpv4LocationExample example) {
        BEGIN();
        DELETE_FROM("t_view_ipv4_location");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(TViewIpv4Location record) {
        BEGIN();
        INSERT_INTO("t_view_ipv4_location");
        
        if (record.getLatlongId() != null) {
            VALUES("latlong_id", "#{latlongId,jdbcType=BIGINT}");
        }
        
        if (record.getNetwork() != null) {
            VALUES("network", "#{network,jdbcType=VARCHAR}");
        }
        
        if (record.getNetmask() != null) {
            VALUES("netmask", "#{netmask,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            VALUES("latitude", "#{latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLongitude() != null) {
            VALUES("longitude", "#{longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getAccuracyRadius() != null) {
            VALUES("accuracy_radius", "#{accuracyRadius,jdbcType=VARCHAR}");
        }
        
        if (record.getPostalCode() != null) {
            VALUES("postal_code", "#{postalCode,jdbcType=VARCHAR}");
        }
        
        if (record.getLocaleCode() != null) {
            VALUES("locale_code", "#{localeCode,jdbcType=VARCHAR}");
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
        
        if (record.getSubdivision1Name() != null) {
            VALUES("subdivision_1_name", "#{subdivision1Name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision2Name() != null) {
            VALUES("subdivision_2_name", "#{subdivision2Name,jdbcType=VARCHAR}");
        }
        
        if (record.getCityName() != null) {
            VALUES("city_name", "#{cityName,jdbcType=VARCHAR}");
        }
        
        if (record.getTimeZone() != null) {
            VALUES("time_zone", "#{timeZone,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExample(TViewIpv4LocationExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("latlong_id");
        } else {
            SELECT("latlong_id");
        }
        SELECT("network");
        SELECT("netmask");
        SELECT("latitude");
        SELECT("longitude");
        SELECT("accuracy_radius");
        SELECT("postal_code");
        SELECT("locale_code");
        SELECT("continent_name");
        SELECT("country_iso_code");
        SELECT("country_name");
        SELECT("subdivision_1_name");
        SELECT("subdivision_2_name");
        SELECT("city_name");
        SELECT("time_zone");
        FROM("t_view_ipv4_location");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TViewIpv4Location record = (TViewIpv4Location) parameter.get("record");
        TViewIpv4LocationExample example = (TViewIpv4LocationExample) parameter.get("example");
        
        BEGIN();
        UPDATE("t_view_ipv4_location");
        
        if (record.getLatlongId() != null) {
            SET("latlong_id = #{record.latlongId,jdbcType=BIGINT}");
        }
        
        if (record.getNetwork() != null) {
            SET("network = #{record.network,jdbcType=VARCHAR}");
        }
        
        if (record.getNetmask() != null) {
            SET("netmask = #{record.netmask,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            SET("latitude = #{record.latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLongitude() != null) {
            SET("longitude = #{record.longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getAccuracyRadius() != null) {
            SET("accuracy_radius = #{record.accuracyRadius,jdbcType=VARCHAR}");
        }
        
        if (record.getPostalCode() != null) {
            SET("postal_code = #{record.postalCode,jdbcType=VARCHAR}");
        }
        
        if (record.getLocaleCode() != null) {
            SET("locale_code = #{record.localeCode,jdbcType=VARCHAR}");
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
        
        if (record.getSubdivision1Name() != null) {
            SET("subdivision_1_name = #{record.subdivision1Name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubdivision2Name() != null) {
            SET("subdivision_2_name = #{record.subdivision2Name,jdbcType=VARCHAR}");
        }
        
        if (record.getCityName() != null) {
            SET("city_name = #{record.cityName,jdbcType=VARCHAR}");
        }
        
        if (record.getTimeZone() != null) {
            SET("time_zone = #{record.timeZone,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("t_view_ipv4_location");
        
        SET("latlong_id = #{record.latlongId,jdbcType=BIGINT}");
        SET("network = #{record.network,jdbcType=VARCHAR}");
        SET("netmask = #{record.netmask,jdbcType=VARCHAR}");
        SET("latitude = #{record.latitude,jdbcType=VARCHAR}");
        SET("longitude = #{record.longitude,jdbcType=VARCHAR}");
        SET("accuracy_radius = #{record.accuracyRadius,jdbcType=VARCHAR}");
        SET("postal_code = #{record.postalCode,jdbcType=VARCHAR}");
        SET("locale_code = #{record.localeCode,jdbcType=VARCHAR}");
        SET("continent_name = #{record.continentName,jdbcType=VARCHAR}");
        SET("country_iso_code = #{record.countryIsoCode,jdbcType=VARCHAR}");
        SET("country_name = #{record.countryName,jdbcType=VARCHAR}");
        SET("subdivision_1_name = #{record.subdivision1Name,jdbcType=VARCHAR}");
        SET("subdivision_2_name = #{record.subdivision2Name,jdbcType=VARCHAR}");
        SET("city_name = #{record.cityName,jdbcType=VARCHAR}");
        SET("time_zone = #{record.timeZone,jdbcType=VARCHAR}");
        
        TViewIpv4LocationExample example = (TViewIpv4LocationExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    protected void applyWhere(TViewIpv4LocationExample example, boolean includeExamplePhrase) {
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