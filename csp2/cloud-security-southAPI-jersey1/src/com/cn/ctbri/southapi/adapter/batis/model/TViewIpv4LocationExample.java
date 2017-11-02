package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.ArrayList;
import java.util.List;

public class TViewIpv4LocationExample {
    protected String orderByClause;
    
    //limit语句偏移量
    protected String offset;
    
    public String getOffset() {
		return offset;
	}

	//limit语句返回行数
    protected String rows;

    protected boolean distinct;

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	protected List<Criteria> oredCriteria;

    public TViewIpv4LocationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andLatlongIdIsNull() {
            addCriterion("latlong_id is null");
            return (Criteria) this;
        }

        public Criteria andLatlongIdIsNotNull() {
            addCriterion("latlong_id is not null");
            return (Criteria) this;
        }

        public Criteria andLatlongIdEqualTo(Long value) {
            addCriterion("latlong_id =", value, "latlongId");
            return (Criteria) this;
        }

        public Criteria andLatlongIdNotEqualTo(Long value) {
            addCriterion("latlong_id <>", value, "latlongId");
            return (Criteria) this;
        }

        public Criteria andLatlongIdGreaterThan(Long value) {
            addCriterion("latlong_id >", value, "latlongId");
            return (Criteria) this;
        }

        public Criteria andLatlongIdGreaterThanOrEqualTo(Long value) {
            addCriterion("latlong_id >=", value, "latlongId");
            return (Criteria) this;
        }

        public Criteria andLatlongIdLessThan(Long value) {
            addCriterion("latlong_id <", value, "latlongId");
            return (Criteria) this;
        }

        public Criteria andLatlongIdLessThanOrEqualTo(Long value) {
            addCriterion("latlong_id <=", value, "latlongId");
            return (Criteria) this;
        }

        public Criteria andLatlongIdIn(List<Long> values) {
            addCriterion("latlong_id in", values, "latlongId");
            return (Criteria) this;
        }

        public Criteria andLatlongIdNotIn(List<Long> values) {
            addCriterion("latlong_id not in", values, "latlongId");
            return (Criteria) this;
        }

        public Criteria andLatlongIdBetween(Long value1, Long value2) {
            addCriterion("latlong_id between", value1, value2, "latlongId");
            return (Criteria) this;
        }

        public Criteria andLatlongIdNotBetween(Long value1, Long value2) {
            addCriterion("latlong_id not between", value1, value2, "latlongId");
            return (Criteria) this;
        }

        public Criteria andNetworkIsNull() {
            addCriterion("network is null");
            return (Criteria) this;
        }

        public Criteria andNetworkIsNotNull() {
            addCriterion("network is not null");
            return (Criteria) this;
        }

        public Criteria andNetworkEqualTo(String value) {
            addCriterion("network =", value, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkNotEqualTo(String value) {
            addCriterion("network <>", value, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkGreaterThan(String value) {
            addCriterion("network >", value, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkGreaterThanOrEqualTo(String value) {
            addCriterion("network >=", value, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkLessThan(String value) {
            addCriterion("network <", value, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkLessThanOrEqualTo(String value) {
            addCriterion("network <=", value, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkLike(String value) {
            addCriterion("network like", value, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkNotLike(String value) {
            addCriterion("network not like", value, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkIn(List<String> values) {
            addCriterion("network in", values, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkNotIn(List<String> values) {
            addCriterion("network not in", values, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkBetween(String value1, String value2) {
            addCriterion("network between", value1, value2, "network");
            return (Criteria) this;
        }

        public Criteria andNetworkNotBetween(String value1, String value2) {
            addCriterion("network not between", value1, value2, "network");
            return (Criteria) this;
        }

        public Criteria andNetmaskIsNull() {
            addCriterion("netmask is null");
            return (Criteria) this;
        }

        public Criteria andNetmaskIsNotNull() {
            addCriterion("netmask is not null");
            return (Criteria) this;
        }

        public Criteria andNetmaskEqualTo(String value) {
            addCriterion("netmask =", value, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskNotEqualTo(String value) {
            addCriterion("netmask <>", value, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskGreaterThan(String value) {
            addCriterion("netmask >", value, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskGreaterThanOrEqualTo(String value) {
            addCriterion("netmask >=", value, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskLessThan(String value) {
            addCriterion("netmask <", value, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskLessThanOrEqualTo(String value) {
            addCriterion("netmask <=", value, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskLike(String value) {
            addCriterion("netmask like", value, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskNotLike(String value) {
            addCriterion("netmask not like", value, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskIn(List<String> values) {
            addCriterion("netmask in", values, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskNotIn(List<String> values) {
            addCriterion("netmask not in", values, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskBetween(String value1, String value2) {
            addCriterion("netmask between", value1, value2, "netmask");
            return (Criteria) this;
        }

        public Criteria andNetmaskNotBetween(String value1, String value2) {
            addCriterion("netmask not between", value1, value2, "netmask");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(String value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(String value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(String value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(String value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(String value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(String value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLike(String value) {
            addCriterion("latitude like", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotLike(String value) {
            addCriterion("latitude not like", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<String> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<String> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(String value1, String value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(String value1, String value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(String value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(String value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(String value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(String value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(String value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLike(String value) {
            addCriterion("longitude like", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotLike(String value) {
            addCriterion("longitude not like", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<String> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<String> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(String value1, String value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(String value1, String value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusIsNull() {
            addCriterion("accuracy_radius is null");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusIsNotNull() {
            addCriterion("accuracy_radius is not null");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusEqualTo(String value) {
            addCriterion("accuracy_radius =", value, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusNotEqualTo(String value) {
            addCriterion("accuracy_radius <>", value, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusGreaterThan(String value) {
            addCriterion("accuracy_radius >", value, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusGreaterThanOrEqualTo(String value) {
            addCriterion("accuracy_radius >=", value, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusLessThan(String value) {
            addCriterion("accuracy_radius <", value, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusLessThanOrEqualTo(String value) {
            addCriterion("accuracy_radius <=", value, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusLike(String value) {
            addCriterion("accuracy_radius like", value, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusNotLike(String value) {
            addCriterion("accuracy_radius not like", value, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusIn(List<String> values) {
            addCriterion("accuracy_radius in", values, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusNotIn(List<String> values) {
            addCriterion("accuracy_radius not in", values, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusBetween(String value1, String value2) {
            addCriterion("accuracy_radius between", value1, value2, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andAccuracyRadiusNotBetween(String value1, String value2) {
            addCriterion("accuracy_radius not between", value1, value2, "accuracyRadius");
            return (Criteria) this;
        }

        public Criteria andPostalCodeIsNull() {
            addCriterion("postal_code is null");
            return (Criteria) this;
        }

        public Criteria andPostalCodeIsNotNull() {
            addCriterion("postal_code is not null");
            return (Criteria) this;
        }

        public Criteria andPostalCodeEqualTo(String value) {
            addCriterion("postal_code =", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeNotEqualTo(String value) {
            addCriterion("postal_code <>", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeGreaterThan(String value) {
            addCriterion("postal_code >", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeGreaterThanOrEqualTo(String value) {
            addCriterion("postal_code >=", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeLessThan(String value) {
            addCriterion("postal_code <", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeLessThanOrEqualTo(String value) {
            addCriterion("postal_code <=", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeLike(String value) {
            addCriterion("postal_code like", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeNotLike(String value) {
            addCriterion("postal_code not like", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeIn(List<String> values) {
            addCriterion("postal_code in", values, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeNotIn(List<String> values) {
            addCriterion("postal_code not in", values, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeBetween(String value1, String value2) {
            addCriterion("postal_code between", value1, value2, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeNotBetween(String value1, String value2) {
            addCriterion("postal_code not between", value1, value2, "postalCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeIsNull() {
            addCriterion("locale_code is null");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeIsNotNull() {
            addCriterion("locale_code is not null");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeEqualTo(String value) {
            addCriterion("locale_code =", value, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeNotEqualTo(String value) {
            addCriterion("locale_code <>", value, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeGreaterThan(String value) {
            addCriterion("locale_code >", value, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeGreaterThanOrEqualTo(String value) {
            addCriterion("locale_code >=", value, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeLessThan(String value) {
            addCriterion("locale_code <", value, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeLessThanOrEqualTo(String value) {
            addCriterion("locale_code <=", value, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeLike(String value) {
            addCriterion("locale_code like", value, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeNotLike(String value) {
            addCriterion("locale_code not like", value, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeIn(List<String> values) {
            addCriterion("locale_code in", values, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeNotIn(List<String> values) {
            addCriterion("locale_code not in", values, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeBetween(String value1, String value2) {
            addCriterion("locale_code between", value1, value2, "localeCode");
            return (Criteria) this;
        }

        public Criteria andLocaleCodeNotBetween(String value1, String value2) {
            addCriterion("locale_code not between", value1, value2, "localeCode");
            return (Criteria) this;
        }

        public Criteria andContinentNameIsNull() {
            addCriterion("continent_name is null");
            return (Criteria) this;
        }

        public Criteria andContinentNameIsNotNull() {
            addCriterion("continent_name is not null");
            return (Criteria) this;
        }

        public Criteria andContinentNameEqualTo(String value) {
            addCriterion("continent_name =", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameNotEqualTo(String value) {
            addCriterion("continent_name <>", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameGreaterThan(String value) {
            addCriterion("continent_name >", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameGreaterThanOrEqualTo(String value) {
            addCriterion("continent_name >=", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameLessThan(String value) {
            addCriterion("continent_name <", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameLessThanOrEqualTo(String value) {
            addCriterion("continent_name <=", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameLike(String value) {
            addCriterion("continent_name like", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameNotLike(String value) {
            addCriterion("continent_name not like", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameIn(List<String> values) {
            addCriterion("continent_name in", values, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameNotIn(List<String> values) {
            addCriterion("continent_name not in", values, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameBetween(String value1, String value2) {
            addCriterion("continent_name between", value1, value2, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameNotBetween(String value1, String value2) {
            addCriterion("continent_name not between", value1, value2, "continentName");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeIsNull() {
            addCriterion("country_iso_code is null");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeIsNotNull() {
            addCriterion("country_iso_code is not null");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeEqualTo(String value) {
            addCriterion("country_iso_code =", value, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeNotEqualTo(String value) {
            addCriterion("country_iso_code <>", value, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeGreaterThan(String value) {
            addCriterion("country_iso_code >", value, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeGreaterThanOrEqualTo(String value) {
            addCriterion("country_iso_code >=", value, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeLessThan(String value) {
            addCriterion("country_iso_code <", value, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeLessThanOrEqualTo(String value) {
            addCriterion("country_iso_code <=", value, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeLike(String value) {
            addCriterion("country_iso_code like", value, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeNotLike(String value) {
            addCriterion("country_iso_code not like", value, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeIn(List<String> values) {
            addCriterion("country_iso_code in", values, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeNotIn(List<String> values) {
            addCriterion("country_iso_code not in", values, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeBetween(String value1, String value2) {
            addCriterion("country_iso_code between", value1, value2, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryIsoCodeNotBetween(String value1, String value2) {
            addCriterion("country_iso_code not between", value1, value2, "countryIsoCode");
            return (Criteria) this;
        }

        public Criteria andCountryNameIsNull() {
            addCriterion("country_name is null");
            return (Criteria) this;
        }

        public Criteria andCountryNameIsNotNull() {
            addCriterion("country_name is not null");
            return (Criteria) this;
        }

        public Criteria andCountryNameEqualTo(String value) {
            addCriterion("country_name =", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotEqualTo(String value) {
            addCriterion("country_name <>", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameGreaterThan(String value) {
            addCriterion("country_name >", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameGreaterThanOrEqualTo(String value) {
            addCriterion("country_name >=", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameLessThan(String value) {
            addCriterion("country_name <", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameLessThanOrEqualTo(String value) {
            addCriterion("country_name <=", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameLike(String value) {
            addCriterion("country_name like", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotLike(String value) {
            addCriterion("country_name not like", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameIn(List<String> values) {
            addCriterion("country_name in", values, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotIn(List<String> values) {
            addCriterion("country_name not in", values, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameBetween(String value1, String value2) {
            addCriterion("country_name between", value1, value2, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotBetween(String value1, String value2) {
            addCriterion("country_name not between", value1, value2, "countryName");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameIsNull() {
            addCriterion("subdivision_1_name is null");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameIsNotNull() {
            addCriterion("subdivision_1_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameEqualTo(String value) {
            addCriterion("subdivision_1_name =", value, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameNotEqualTo(String value) {
            addCriterion("subdivision_1_name <>", value, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameGreaterThan(String value) {
            addCriterion("subdivision_1_name >", value, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameGreaterThanOrEqualTo(String value) {
            addCriterion("subdivision_1_name >=", value, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameLessThan(String value) {
            addCriterion("subdivision_1_name <", value, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameLessThanOrEqualTo(String value) {
            addCriterion("subdivision_1_name <=", value, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameLike(String value) {
            addCriterion("subdivision_1_name like", value, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameNotLike(String value) {
            addCriterion("subdivision_1_name not like", value, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameIn(List<String> values) {
            addCriterion("subdivision_1_name in", values, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameNotIn(List<String> values) {
            addCriterion("subdivision_1_name not in", values, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameBetween(String value1, String value2) {
            addCriterion("subdivision_1_name between", value1, value2, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision1NameNotBetween(String value1, String value2) {
            addCriterion("subdivision_1_name not between", value1, value2, "subdivision1Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameIsNull() {
            addCriterion("subdivision_2_name is null");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameIsNotNull() {
            addCriterion("subdivision_2_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameEqualTo(String value) {
            addCriterion("subdivision_2_name =", value, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameNotEqualTo(String value) {
            addCriterion("subdivision_2_name <>", value, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameGreaterThan(String value) {
            addCriterion("subdivision_2_name >", value, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameGreaterThanOrEqualTo(String value) {
            addCriterion("subdivision_2_name >=", value, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameLessThan(String value) {
            addCriterion("subdivision_2_name <", value, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameLessThanOrEqualTo(String value) {
            addCriterion("subdivision_2_name <=", value, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameLike(String value) {
            addCriterion("subdivision_2_name like", value, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameNotLike(String value) {
            addCriterion("subdivision_2_name not like", value, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameIn(List<String> values) {
            addCriterion("subdivision_2_name in", values, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameNotIn(List<String> values) {
            addCriterion("subdivision_2_name not in", values, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameBetween(String value1, String value2) {
            addCriterion("subdivision_2_name between", value1, value2, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andSubdivision2NameNotBetween(String value1, String value2) {
            addCriterion("subdivision_2_name not between", value1, value2, "subdivision2Name");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNull() {
            addCriterion("city_name is null");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNotNull() {
            addCriterion("city_name is not null");
            return (Criteria) this;
        }

        public Criteria andCityNameEqualTo(String value) {
            addCriterion("city_name =", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotEqualTo(String value) {
            addCriterion("city_name <>", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThan(String value) {
            addCriterion("city_name >", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("city_name >=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThan(String value) {
            addCriterion("city_name <", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThanOrEqualTo(String value) {
            addCriterion("city_name <=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLike(String value) {
            addCriterion("city_name like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotLike(String value) {
            addCriterion("city_name not like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameIn(List<String> values) {
            addCriterion("city_name in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotIn(List<String> values) {
            addCriterion("city_name not in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameBetween(String value1, String value2) {
            addCriterion("city_name between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotBetween(String value1, String value2) {
            addCriterion("city_name not between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andTimeZoneIsNull() {
            addCriterion("time_zone is null");
            return (Criteria) this;
        }

        public Criteria andTimeZoneIsNotNull() {
            addCriterion("time_zone is not null");
            return (Criteria) this;
        }

        public Criteria andTimeZoneEqualTo(String value) {
            addCriterion("time_zone =", value, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneNotEqualTo(String value) {
            addCriterion("time_zone <>", value, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneGreaterThan(String value) {
            addCriterion("time_zone >", value, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneGreaterThanOrEqualTo(String value) {
            addCriterion("time_zone >=", value, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneLessThan(String value) {
            addCriterion("time_zone <", value, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneLessThanOrEqualTo(String value) {
            addCriterion("time_zone <=", value, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneLike(String value) {
            addCriterion("time_zone like", value, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneNotLike(String value) {
            addCriterion("time_zone not like", value, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneIn(List<String> values) {
            addCriterion("time_zone in", values, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneNotIn(List<String> values) {
            addCriterion("time_zone not in", values, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneBetween(String value1, String value2) {
            addCriterion("time_zone between", value1, value2, "timeZone");
            return (Criteria) this;
        }

        public Criteria andTimeZoneNotBetween(String value1, String value2) {
            addCriterion("time_zone not between", value1, value2, "timeZone");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}