package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.ArrayList;
import java.util.List;

public class TIpv4LatlongExample {
    protected String orderByClause;

    //limit语句偏移量
    protected String offset;
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
	
    public String getOffset() {
		return offset;
	}

    
    
    protected List<Criteria> oredCriteria;

    public TIpv4LatlongExample() {
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

        public Criteria andStartipIsNull() {
            addCriterion("startip is null");
            return (Criteria) this;
        }

        public Criteria andStartipIsNotNull() {
            addCriterion("startip is not null");
            return (Criteria) this;
        }

        public Criteria andStartipEqualTo(Long value) {
            addCriterion("startip =", value, "startip");
            return (Criteria) this;
        }

        public Criteria andStartipNotEqualTo(Long value) {
            addCriterion("startip <>", value, "startip");
            return (Criteria) this;
        }

        public Criteria andStartipGreaterThan(Long value) {
            addCriterion("startip >", value, "startip");
            return (Criteria) this;
        }

        public Criteria andStartipGreaterThanOrEqualTo(Long value) {
            addCriterion("startip >=", value, "startip");
            return (Criteria) this;
        }

        public Criteria andStartipLessThan(Long value) {
            addCriterion("startip <", value, "startip");
            return (Criteria) this;
        }

        public Criteria andStartipLessThanOrEqualTo(Long value) {
            addCriterion("startip <=", value, "startip");
            return (Criteria) this;
        }

        public Criteria andStartipIn(List<Long> values) {
            addCriterion("startip in", values, "startip");
            return (Criteria) this;
        }

        public Criteria andStartipNotIn(List<Long> values) {
            addCriterion("startip not in", values, "startip");
            return (Criteria) this;
        }

        public Criteria andStartipBetween(Long value1, Long value2) {
            addCriterion("startip between", value1, value2, "startip");
            return (Criteria) this;
        }

        public Criteria andStartipNotBetween(Long value1, Long value2) {
            addCriterion("startip not between", value1, value2, "startip");
            return (Criteria) this;
        }

        public Criteria andEndipIsNull() {
            addCriterion("endip is null");
            return (Criteria) this;
        }

        public Criteria andEndipIsNotNull() {
            addCriterion("endip is not null");
            return (Criteria) this;
        }

        public Criteria andEndipEqualTo(Long value) {
            addCriterion("endip =", value, "endip");
            return (Criteria) this;
        }

        public Criteria andEndipNotEqualTo(Long value) {
            addCriterion("endip <>", value, "endip");
            return (Criteria) this;
        }

        public Criteria andEndipGreaterThan(Long value) {
            addCriterion("endip >", value, "endip");
            return (Criteria) this;
        }

        public Criteria andEndipGreaterThanOrEqualTo(Long value) {
            addCriterion("endip >=", value, "endip");
            return (Criteria) this;
        }

        public Criteria andEndipLessThan(Long value) {
            addCriterion("endip <", value, "endip");
            return (Criteria) this;
        }

        public Criteria andEndipLessThanOrEqualTo(Long value) {
            addCriterion("endip <=", value, "endip");
            return (Criteria) this;
        }

        public Criteria andEndipIn(List<Long> values) {
            addCriterion("endip in", values, "endip");
            return (Criteria) this;
        }

        public Criteria andEndipNotIn(List<Long> values) {
            addCriterion("endip not in", values, "endip");
            return (Criteria) this;
        }

        public Criteria andEndipBetween(Long value1, Long value2) {
            addCriterion("endip between", value1, value2, "endip");
            return (Criteria) this;
        }

        public Criteria andEndipNotBetween(Long value1, Long value2) {
            addCriterion("endip not between", value1, value2, "endip");
            return (Criteria) this;
        }

        public Criteria andLocationIdIsNull() {
            addCriterion("location_id is null");
            return (Criteria) this;
        }

        public Criteria andLocationIdIsNotNull() {
            addCriterion("location_id is not null");
            return (Criteria) this;
        }

        public Criteria andLocationIdEqualTo(Long value) {
            addCriterion("location_id =", value, "locationId");
            return (Criteria) this;
        }

        public Criteria andLocationIdNotEqualTo(Long value) {
            addCriterion("location_id <>", value, "locationId");
            return (Criteria) this;
        }

        public Criteria andLocationIdGreaterThan(Long value) {
            addCriterion("location_id >", value, "locationId");
            return (Criteria) this;
        }

        public Criteria andLocationIdGreaterThanOrEqualTo(Long value) {
            addCriterion("location_id >=", value, "locationId");
            return (Criteria) this;
        }

        public Criteria andLocationIdLessThan(Long value) {
            addCriterion("location_id <", value, "locationId");
            return (Criteria) this;
        }

        public Criteria andLocationIdLessThanOrEqualTo(Long value) {
            addCriterion("location_id <=", value, "locationId");
            return (Criteria) this;
        }

        public Criteria andLocationIdIn(List<Long> values) {
            addCriterion("location_id in", values, "locationId");
            return (Criteria) this;
        }

        public Criteria andLocationIdNotIn(List<Long> values) {
            addCriterion("location_id not in", values, "locationId");
            return (Criteria) this;
        }

        public Criteria andLocationIdBetween(Long value1, Long value2) {
            addCriterion("location_id between", value1, value2, "locationId");
            return (Criteria) this;
        }

        public Criteria andLocationIdNotBetween(Long value1, Long value2) {
            addCriterion("location_id not between", value1, value2, "locationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdIsNull() {
            addCriterion("registered_country_location_id is null");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdIsNotNull() {
            addCriterion("registered_country_location_id is not null");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdEqualTo(Long value) {
            addCriterion("registered_country_location_id =", value, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdNotEqualTo(Long value) {
            addCriterion("registered_country_location_id <>", value, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdGreaterThan(Long value) {
            addCriterion("registered_country_location_id >", value, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdGreaterThanOrEqualTo(Long value) {
            addCriterion("registered_country_location_id >=", value, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdLessThan(Long value) {
            addCriterion("registered_country_location_id <", value, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdLessThanOrEqualTo(Long value) {
            addCriterion("registered_country_location_id <=", value, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdIn(List<Long> values) {
            addCriterion("registered_country_location_id in", values, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdNotIn(List<Long> values) {
            addCriterion("registered_country_location_id not in", values, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdBetween(Long value1, Long value2) {
            addCriterion("registered_country_location_id between", value1, value2, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRegisteredCountryLocationIdNotBetween(Long value1, Long value2) {
            addCriterion("registered_country_location_id not between", value1, value2, "registeredCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdIsNull() {
            addCriterion("represented_country_location_id is null");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdIsNotNull() {
            addCriterion("represented_country_location_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdEqualTo(Long value) {
            addCriterion("represented_country_location_id =", value, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdNotEqualTo(Long value) {
            addCriterion("represented_country_location_id <>", value, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdGreaterThan(Long value) {
            addCriterion("represented_country_location_id >", value, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdGreaterThanOrEqualTo(Long value) {
            addCriterion("represented_country_location_id >=", value, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdLessThan(Long value) {
            addCriterion("represented_country_location_id <", value, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdLessThanOrEqualTo(Long value) {
            addCriterion("represented_country_location_id <=", value, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdIn(List<Long> values) {
            addCriterion("represented_country_location_id in", values, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdNotIn(List<Long> values) {
            addCriterion("represented_country_location_id not in", values, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdBetween(Long value1, Long value2) {
            addCriterion("represented_country_location_id between", value1, value2, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andRepresentedCountryLocationIdNotBetween(Long value1, Long value2) {
            addCriterion("represented_country_location_id not between", value1, value2, "representedCountryLocationId");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyIsNull() {
            addCriterion("is_anonymous_proxy is null");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyIsNotNull() {
            addCriterion("is_anonymous_proxy is not null");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyEqualTo(String value) {
            addCriterion("is_anonymous_proxy =", value, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyNotEqualTo(String value) {
            addCriterion("is_anonymous_proxy <>", value, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyGreaterThan(String value) {
            addCriterion("is_anonymous_proxy >", value, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyGreaterThanOrEqualTo(String value) {
            addCriterion("is_anonymous_proxy >=", value, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyLessThan(String value) {
            addCriterion("is_anonymous_proxy <", value, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyLessThanOrEqualTo(String value) {
            addCriterion("is_anonymous_proxy <=", value, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyLike(String value) {
            addCriterion("is_anonymous_proxy like", value, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyNotLike(String value) {
            addCriterion("is_anonymous_proxy not like", value, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyIn(List<String> values) {
            addCriterion("is_anonymous_proxy in", values, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyNotIn(List<String> values) {
            addCriterion("is_anonymous_proxy not in", values, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyBetween(String value1, String value2) {
            addCriterion("is_anonymous_proxy between", value1, value2, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousProxyNotBetween(String value1, String value2) {
            addCriterion("is_anonymous_proxy not between", value1, value2, "isAnonymousProxy");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderIsNull() {
            addCriterion("is_satellite_provider is null");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderIsNotNull() {
            addCriterion("is_satellite_provider is not null");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderEqualTo(String value) {
            addCriterion("is_satellite_provider =", value, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderNotEqualTo(String value) {
            addCriterion("is_satellite_provider <>", value, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderGreaterThan(String value) {
            addCriterion("is_satellite_provider >", value, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderGreaterThanOrEqualTo(String value) {
            addCriterion("is_satellite_provider >=", value, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderLessThan(String value) {
            addCriterion("is_satellite_provider <", value, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderLessThanOrEqualTo(String value) {
            addCriterion("is_satellite_provider <=", value, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderLike(String value) {
            addCriterion("is_satellite_provider like", value, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderNotLike(String value) {
            addCriterion("is_satellite_provider not like", value, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderIn(List<String> values) {
            addCriterion("is_satellite_provider in", values, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderNotIn(List<String> values) {
            addCriterion("is_satellite_provider not in", values, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderBetween(String value1, String value2) {
            addCriterion("is_satellite_provider between", value1, value2, "isSatelliteProvider");
            return (Criteria) this;
        }

        public Criteria andIsSatelliteProviderNotBetween(String value1, String value2) {
            addCriterion("is_satellite_provider not between", value1, value2, "isSatelliteProvider");
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