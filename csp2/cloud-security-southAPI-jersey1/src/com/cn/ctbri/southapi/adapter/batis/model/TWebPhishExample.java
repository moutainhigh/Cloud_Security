package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TWebPhishExample {
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

    public TWebPhishExample() {
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

        public Criteria andWebphishIdIsNull() {
            addCriterion("webphish_id is null");
            return (Criteria) this;
        }

        public Criteria andWebphishIdIsNotNull() {
            addCriterion("webphish_id is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishIdEqualTo(Long value) {
            addCriterion("webphish_id =", value, "webphishId");
            return (Criteria) this;
        }

        public Criteria andWebphishIdNotEqualTo(Long value) {
            addCriterion("webphish_id <>", value, "webphishId");
            return (Criteria) this;
        }

        public Criteria andWebphishIdGreaterThan(Long value) {
            addCriterion("webphish_id >", value, "webphishId");
            return (Criteria) this;
        }

        public Criteria andWebphishIdGreaterThanOrEqualTo(Long value) {
            addCriterion("webphish_id >=", value, "webphishId");
            return (Criteria) this;
        }

        public Criteria andWebphishIdLessThan(Long value) {
            addCriterion("webphish_id <", value, "webphishId");
            return (Criteria) this;
        }

        public Criteria andWebphishIdLessThanOrEqualTo(Long value) {
            addCriterion("webphish_id <=", value, "webphishId");
            return (Criteria) this;
        }

        public Criteria andWebphishIdIn(List<Long> values) {
            addCriterion("webphish_id in", values, "webphishId");
            return (Criteria) this;
        }

        public Criteria andWebphishIdNotIn(List<Long> values) {
            addCriterion("webphish_id not in", values, "webphishId");
            return (Criteria) this;
        }

        public Criteria andWebphishIdBetween(Long value1, Long value2) {
            addCriterion("webphish_id between", value1, value2, "webphishId");
            return (Criteria) this;
        }

        public Criteria andWebphishIdNotBetween(Long value1, Long value2) {
            addCriterion("webphish_id not between", value1, value2, "webphishId");
            return (Criteria) this;
        }

        public Criteria andUrlsignIsNull() {
            addCriterion("urlsign is null");
            return (Criteria) this;
        }

        public Criteria andUrlsignIsNotNull() {
            addCriterion("urlsign is not null");
            return (Criteria) this;
        }

        public Criteria andUrlsignEqualTo(String value) {
            addCriterion("urlsign =", value, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignNotEqualTo(String value) {
            addCriterion("urlsign <>", value, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignGreaterThan(String value) {
            addCriterion("urlsign >", value, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignGreaterThanOrEqualTo(String value) {
            addCriterion("urlsign >=", value, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignLessThan(String value) {
            addCriterion("urlsign <", value, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignLessThanOrEqualTo(String value) {
            addCriterion("urlsign <=", value, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignLike(String value) {
            addCriterion("urlsign like", value, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignNotLike(String value) {
            addCriterion("urlsign not like", value, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignIn(List<String> values) {
            addCriterion("urlsign in", values, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignNotIn(List<String> values) {
            addCriterion("urlsign not in", values, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignBetween(String value1, String value2) {
            addCriterion("urlsign between", value1, value2, "urlsign");
            return (Criteria) this;
        }

        public Criteria andUrlsignNotBetween(String value1, String value2) {
            addCriterion("urlsign not between", value1, value2, "urlsign");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlIsNull() {
            addCriterion("webphish_url is null");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlIsNotNull() {
            addCriterion("webphish_url is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlEqualTo(String value) {
            addCriterion("webphish_url =", value, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlNotEqualTo(String value) {
            addCriterion("webphish_url <>", value, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlGreaterThan(String value) {
            addCriterion("webphish_url >", value, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_url >=", value, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlLessThan(String value) {
            addCriterion("webphish_url <", value, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlLessThanOrEqualTo(String value) {
            addCriterion("webphish_url <=", value, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlLike(String value) {
            addCriterion("webphish_url like", value, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlNotLike(String value) {
            addCriterion("webphish_url not like", value, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlIn(List<String> values) {
            addCriterion("webphish_url in", values, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlNotIn(List<String> values) {
            addCriterion("webphish_url not in", values, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlBetween(String value1, String value2) {
            addCriterion("webphish_url between", value1, value2, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishUrlNotBetween(String value1, String value2) {
            addCriterion("webphish_url not between", value1, value2, "webphishUrl");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldIsNull() {
            addCriterion("webphish_field is null");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldIsNotNull() {
            addCriterion("webphish_field is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldEqualTo(String value) {
            addCriterion("webphish_field =", value, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldNotEqualTo(String value) {
            addCriterion("webphish_field <>", value, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldGreaterThan(String value) {
            addCriterion("webphish_field >", value, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_field >=", value, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldLessThan(String value) {
            addCriterion("webphish_field <", value, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldLessThanOrEqualTo(String value) {
            addCriterion("webphish_field <=", value, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldLike(String value) {
            addCriterion("webphish_field like", value, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldNotLike(String value) {
            addCriterion("webphish_field not like", value, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldIn(List<String> values) {
            addCriterion("webphish_field in", values, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldNotIn(List<String> values) {
            addCriterion("webphish_field not in", values, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldBetween(String value1, String value2) {
            addCriterion("webphish_field between", value1, value2, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishFieldNotBetween(String value1, String value2) {
            addCriterion("webphish_field not between", value1, value2, "webphishField");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainIsNull() {
            addCriterion("webphish_domain is null");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainIsNotNull() {
            addCriterion("webphish_domain is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainEqualTo(String value) {
            addCriterion("webphish_domain =", value, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainNotEqualTo(String value) {
            addCriterion("webphish_domain <>", value, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainGreaterThan(String value) {
            addCriterion("webphish_domain >", value, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_domain >=", value, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainLessThan(String value) {
            addCriterion("webphish_domain <", value, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainLessThanOrEqualTo(String value) {
            addCriterion("webphish_domain <=", value, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainLike(String value) {
            addCriterion("webphish_domain like", value, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainNotLike(String value) {
            addCriterion("webphish_domain not like", value, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainIn(List<String> values) {
            addCriterion("webphish_domain in", values, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainNotIn(List<String> values) {
            addCriterion("webphish_domain not in", values, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainBetween(String value1, String value2) {
            addCriterion("webphish_domain between", value1, value2, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishDomainNotBetween(String value1, String value2) {
            addCriterion("webphish_domain not between", value1, value2, "webphishDomain");
            return (Criteria) this;
        }

        public Criteria andWebphishIpIsNull() {
            addCriterion("webphish_ip is null");
            return (Criteria) this;
        }

        public Criteria andWebphishIpIsNotNull() {
            addCriterion("webphish_ip is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishIpEqualTo(String value) {
            addCriterion("webphish_ip =", value, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpNotEqualTo(String value) {
            addCriterion("webphish_ip <>", value, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpGreaterThan(String value) {
            addCriterion("webphish_ip >", value, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_ip >=", value, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpLessThan(String value) {
            addCriterion("webphish_ip <", value, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpLessThanOrEqualTo(String value) {
            addCriterion("webphish_ip <=", value, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpLike(String value) {
            addCriterion("webphish_ip like", value, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpNotLike(String value) {
            addCriterion("webphish_ip not like", value, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpIn(List<String> values) {
            addCriterion("webphish_ip in", values, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpNotIn(List<String> values) {
            addCriterion("webphish_ip not in", values, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpBetween(String value1, String value2) {
            addCriterion("webphish_ip between", value1, value2, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishIpNotBetween(String value1, String value2) {
            addCriterion("webphish_ip not between", value1, value2, "webphishIp");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnIsNull() {
            addCriterion("webphish_asn is null");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnIsNotNull() {
            addCriterion("webphish_asn is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnEqualTo(String value) {
            addCriterion("webphish_asn =", value, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnNotEqualTo(String value) {
            addCriterion("webphish_asn <>", value, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnGreaterThan(String value) {
            addCriterion("webphish_asn >", value, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_asn >=", value, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnLessThan(String value) {
            addCriterion("webphish_asn <", value, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnLessThanOrEqualTo(String value) {
            addCriterion("webphish_asn <=", value, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnLike(String value) {
            addCriterion("webphish_asn like", value, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnNotLike(String value) {
            addCriterion("webphish_asn not like", value, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnIn(List<String> values) {
            addCriterion("webphish_asn in", values, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnNotIn(List<String> values) {
            addCriterion("webphish_asn not in", values, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnBetween(String value1, String value2) {
            addCriterion("webphish_asn between", value1, value2, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnNotBetween(String value1, String value2) {
            addCriterion("webphish_asn not between", value1, value2, "webphishAsn");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameIsNull() {
            addCriterion("webphish_asnname is null");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameIsNotNull() {
            addCriterion("webphish_asnname is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameEqualTo(String value) {
            addCriterion("webphish_asnname =", value, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameNotEqualTo(String value) {
            addCriterion("webphish_asnname <>", value, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameGreaterThan(String value) {
            addCriterion("webphish_asnname >", value, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_asnname >=", value, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameLessThan(String value) {
            addCriterion("webphish_asnname <", value, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameLessThanOrEqualTo(String value) {
            addCriterion("webphish_asnname <=", value, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameLike(String value) {
            addCriterion("webphish_asnname like", value, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameNotLike(String value) {
            addCriterion("webphish_asnname not like", value, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameIn(List<String> values) {
            addCriterion("webphish_asnname in", values, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameNotIn(List<String> values) {
            addCriterion("webphish_asnname not in", values, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameBetween(String value1, String value2) {
            addCriterion("webphish_asnname between", value1, value2, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishAsnnameNotBetween(String value1, String value2) {
            addCriterion("webphish_asnname not between", value1, value2, "webphishAsnname");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryIsNull() {
            addCriterion("webphish_country is null");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryIsNotNull() {
            addCriterion("webphish_country is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryEqualTo(String value) {
            addCriterion("webphish_country =", value, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryNotEqualTo(String value) {
            addCriterion("webphish_country <>", value, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryGreaterThan(String value) {
            addCriterion("webphish_country >", value, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_country >=", value, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryLessThan(String value) {
            addCriterion("webphish_country <", value, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryLessThanOrEqualTo(String value) {
            addCriterion("webphish_country <=", value, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryLike(String value) {
            addCriterion("webphish_country like", value, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryNotLike(String value) {
            addCriterion("webphish_country not like", value, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryIn(List<String> values) {
            addCriterion("webphish_country in", values, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryNotIn(List<String> values) {
            addCriterion("webphish_country not in", values, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryBetween(String value1, String value2) {
            addCriterion("webphish_country between", value1, value2, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountryNotBetween(String value1, String value2) {
            addCriterion("webphish_country not between", value1, value2, "webphishCountry");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeIsNull() {
            addCriterion("webphish_countrycode is null");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeIsNotNull() {
            addCriterion("webphish_countrycode is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeEqualTo(String value) {
            addCriterion("webphish_countrycode =", value, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeNotEqualTo(String value) {
            addCriterion("webphish_countrycode <>", value, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeGreaterThan(String value) {
            addCriterion("webphish_countrycode >", value, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_countrycode >=", value, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeLessThan(String value) {
            addCriterion("webphish_countrycode <", value, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeLessThanOrEqualTo(String value) {
            addCriterion("webphish_countrycode <=", value, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeLike(String value) {
            addCriterion("webphish_countrycode like", value, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeNotLike(String value) {
            addCriterion("webphish_countrycode not like", value, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeIn(List<String> values) {
            addCriterion("webphish_countrycode in", values, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeNotIn(List<String> values) {
            addCriterion("webphish_countrycode not in", values, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeBetween(String value1, String value2) {
            addCriterion("webphish_countrycode between", value1, value2, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishCountrycodeNotBetween(String value1, String value2) {
            addCriterion("webphish_countrycode not between", value1, value2, "webphishCountrycode");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1IsNull() {
            addCriterion("webphish_subdivision1 is null");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1IsNotNull() {
            addCriterion("webphish_subdivision1 is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1EqualTo(String value) {
            addCriterion("webphish_subdivision1 =", value, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1NotEqualTo(String value) {
            addCriterion("webphish_subdivision1 <>", value, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1GreaterThan(String value) {
            addCriterion("webphish_subdivision1 >", value, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1GreaterThanOrEqualTo(String value) {
            addCriterion("webphish_subdivision1 >=", value, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1LessThan(String value) {
            addCriterion("webphish_subdivision1 <", value, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1LessThanOrEqualTo(String value) {
            addCriterion("webphish_subdivision1 <=", value, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1Like(String value) {
            addCriterion("webphish_subdivision1 like", value, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1NotLike(String value) {
            addCriterion("webphish_subdivision1 not like", value, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1In(List<String> values) {
            addCriterion("webphish_subdivision1 in", values, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1NotIn(List<String> values) {
            addCriterion("webphish_subdivision1 not in", values, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1Between(String value1, String value2) {
            addCriterion("webphish_subdivision1 between", value1, value2, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision1NotBetween(String value1, String value2) {
            addCriterion("webphish_subdivision1 not between", value1, value2, "webphishSubdivision1");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2IsNull() {
            addCriterion("webphish_subdivision2 is null");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2IsNotNull() {
            addCriterion("webphish_subdivision2 is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2EqualTo(String value) {
            addCriterion("webphish_subdivision2 =", value, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2NotEqualTo(String value) {
            addCriterion("webphish_subdivision2 <>", value, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2GreaterThan(String value) {
            addCriterion("webphish_subdivision2 >", value, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2GreaterThanOrEqualTo(String value) {
            addCriterion("webphish_subdivision2 >=", value, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2LessThan(String value) {
            addCriterion("webphish_subdivision2 <", value, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2LessThanOrEqualTo(String value) {
            addCriterion("webphish_subdivision2 <=", value, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2Like(String value) {
            addCriterion("webphish_subdivision2 like", value, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2NotLike(String value) {
            addCriterion("webphish_subdivision2 not like", value, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2In(List<String> values) {
            addCriterion("webphish_subdivision2 in", values, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2NotIn(List<String> values) {
            addCriterion("webphish_subdivision2 not in", values, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2Between(String value1, String value2) {
            addCriterion("webphish_subdivision2 between", value1, value2, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishSubdivision2NotBetween(String value1, String value2) {
            addCriterion("webphish_subdivision2 not between", value1, value2, "webphishSubdivision2");
            return (Criteria) this;
        }

        public Criteria andWebphishCityIsNull() {
            addCriterion("webphish_city is null");
            return (Criteria) this;
        }

        public Criteria andWebphishCityIsNotNull() {
            addCriterion("webphish_city is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishCityEqualTo(String value) {
            addCriterion("webphish_city =", value, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityNotEqualTo(String value) {
            addCriterion("webphish_city <>", value, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityGreaterThan(String value) {
            addCriterion("webphish_city >", value, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_city >=", value, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityLessThan(String value) {
            addCriterion("webphish_city <", value, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityLessThanOrEqualTo(String value) {
            addCriterion("webphish_city <=", value, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityLike(String value) {
            addCriterion("webphish_city like", value, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityNotLike(String value) {
            addCriterion("webphish_city not like", value, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityIn(List<String> values) {
            addCriterion("webphish_city in", values, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityNotIn(List<String> values) {
            addCriterion("webphish_city not in", values, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityBetween(String value1, String value2) {
            addCriterion("webphish_city between", value1, value2, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishCityNotBetween(String value1, String value2) {
            addCriterion("webphish_city not between", value1, value2, "webphishCity");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetIsNull() {
            addCriterion("webphish_target is null");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetIsNotNull() {
            addCriterion("webphish_target is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetEqualTo(String value) {
            addCriterion("webphish_target =", value, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetNotEqualTo(String value) {
            addCriterion("webphish_target <>", value, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetGreaterThan(String value) {
            addCriterion("webphish_target >", value, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetGreaterThanOrEqualTo(String value) {
            addCriterion("webphish_target >=", value, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetLessThan(String value) {
            addCriterion("webphish_target <", value, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetLessThanOrEqualTo(String value) {
            addCriterion("webphish_target <=", value, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetLike(String value) {
            addCriterion("webphish_target like", value, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetNotLike(String value) {
            addCriterion("webphish_target not like", value, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetIn(List<String> values) {
            addCriterion("webphish_target in", values, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetNotIn(List<String> values) {
            addCriterion("webphish_target not in", values, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetBetween(String value1, String value2) {
            addCriterion("webphish_target between", value1, value2, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andWebphishTargetNotBetween(String value1, String value2) {
            addCriterion("webphish_target not between", value1, value2, "webphishTarget");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeIsNull() {
            addCriterion("verified_time is null");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeIsNotNull() {
            addCriterion("verified_time is not null");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeEqualTo(String value) {
            addCriterion("verified_time =", value, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeNotEqualTo(String value) {
            addCriterion("verified_time <>", value, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeGreaterThan(String value) {
            addCriterion("verified_time >", value, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeGreaterThanOrEqualTo(String value) {
            addCriterion("verified_time >=", value, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeLessThan(String value) {
            addCriterion("verified_time <", value, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeLessThanOrEqualTo(String value) {
            addCriterion("verified_time <=", value, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeLike(String value) {
            addCriterion("verified_time like", value, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeNotLike(String value) {
            addCriterion("verified_time not like", value, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeIn(List<String> values) {
            addCriterion("verified_time in", values, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeNotIn(List<String> values) {
            addCriterion("verified_time not in", values, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeBetween(String value1, String value2) {
            addCriterion("verified_time between", value1, value2, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andVerifiedTimeNotBetween(String value1, String value2) {
            addCriterion("verified_time not between", value1, value2, "verifiedTime");
            return (Criteria) this;
        }

        public Criteria andFromSourceIsNull() {
            addCriterion("from_source is null");
            return (Criteria) this;
        }

        public Criteria andFromSourceIsNotNull() {
            addCriterion("from_source is not null");
            return (Criteria) this;
        }

        public Criteria andFromSourceEqualTo(String value) {
            addCriterion("from_source =", value, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceNotEqualTo(String value) {
            addCriterion("from_source <>", value, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceGreaterThan(String value) {
            addCriterion("from_source >", value, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceGreaterThanOrEqualTo(String value) {
            addCriterion("from_source >=", value, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceLessThan(String value) {
            addCriterion("from_source <", value, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceLessThanOrEqualTo(String value) {
            addCriterion("from_source <=", value, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceLike(String value) {
            addCriterion("from_source like", value, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceNotLike(String value) {
            addCriterion("from_source not like", value, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceIn(List<String> values) {
            addCriterion("from_source in", values, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceNotIn(List<String> values) {
            addCriterion("from_source not in", values, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceBetween(String value1, String value2) {
            addCriterion("from_source between", value1, value2, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceNotBetween(String value1, String value2) {
            addCriterion("from_source not between", value1, value2, "fromSource");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdIsNull() {
            addCriterion("from_source_id is null");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdIsNotNull() {
            addCriterion("from_source_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdEqualTo(Long value) {
            addCriterion("from_source_id =", value, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdNotEqualTo(Long value) {
            addCriterion("from_source_id <>", value, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdGreaterThan(Long value) {
            addCriterion("from_source_id >", value, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_source_id >=", value, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdLessThan(Long value) {
            addCriterion("from_source_id <", value, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdLessThanOrEqualTo(Long value) {
            addCriterion("from_source_id <=", value, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdIn(List<Long> values) {
            addCriterion("from_source_id in", values, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdNotIn(List<Long> values) {
            addCriterion("from_source_id not in", values, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdBetween(Long value1, Long value2) {
            addCriterion("from_source_id between", value1, value2, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceIdNotBetween(Long value1, Long value2) {
            addCriterion("from_source_id not between", value1, value2, "fromSourceId");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailIsNull() {
            addCriterion("from_source_detail is null");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailIsNotNull() {
            addCriterion("from_source_detail is not null");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailEqualTo(String value) {
            addCriterion("from_source_detail =", value, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailNotEqualTo(String value) {
            addCriterion("from_source_detail <>", value, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailGreaterThan(String value) {
            addCriterion("from_source_detail >", value, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailGreaterThanOrEqualTo(String value) {
            addCriterion("from_source_detail >=", value, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailLessThan(String value) {
            addCriterion("from_source_detail <", value, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailLessThanOrEqualTo(String value) {
            addCriterion("from_source_detail <=", value, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailLike(String value) {
            addCriterion("from_source_detail like", value, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailNotLike(String value) {
            addCriterion("from_source_detail not like", value, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailIn(List<String> values) {
            addCriterion("from_source_detail in", values, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailNotIn(List<String> values) {
            addCriterion("from_source_detail not in", values, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailBetween(String value1, String value2) {
            addCriterion("from_source_detail between", value1, value2, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceDetailNotBetween(String value1, String value2) {
            addCriterion("from_source_detail not between", value1, value2, "fromSourceDetail");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveIsNull() {
            addCriterion("from_source_reserve is null");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveIsNotNull() {
            addCriterion("from_source_reserve is not null");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveEqualTo(String value) {
            addCriterion("from_source_reserve =", value, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveNotEqualTo(String value) {
            addCriterion("from_source_reserve <>", value, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveGreaterThan(String value) {
            addCriterion("from_source_reserve >", value, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveGreaterThanOrEqualTo(String value) {
            addCriterion("from_source_reserve >=", value, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveLessThan(String value) {
            addCriterion("from_source_reserve <", value, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveLessThanOrEqualTo(String value) {
            addCriterion("from_source_reserve <=", value, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveLike(String value) {
            addCriterion("from_source_reserve like", value, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveNotLike(String value) {
            addCriterion("from_source_reserve not like", value, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveIn(List<String> values) {
            addCriterion("from_source_reserve in", values, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveNotIn(List<String> values) {
            addCriterion("from_source_reserve not in", values, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveBetween(String value1, String value2) {
            addCriterion("from_source_reserve between", value1, value2, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andFromSourceReserveNotBetween(String value1, String value2) {
            addCriterion("from_source_reserve not between", value1, value2, "fromSourceReserve");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNull() {
            addCriterion("expire_time is null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNotNull() {
            addCriterion("expire_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeEqualTo(Date value) {
            addCriterion("expire_time =", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotEqualTo(Date value) {
            addCriterion("expire_time <>", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThan(Date value) {
            addCriterion("expire_time >", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expire_time >=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThan(Date value) {
            addCriterion("expire_time <", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanOrEqualTo(Date value) {
            addCriterion("expire_time <=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIn(List<Date> values) {
            addCriterion("expire_time in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotIn(List<Date> values) {
            addCriterion("expire_time not in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeBetween(Date value1, Date value2) {
            addCriterion("expire_time between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotBetween(Date value1, Date value2) {
            addCriterion("expire_time not between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andIsvalidIsNull() {
            addCriterion("isvalid is null");
            return (Criteria) this;
        }

        public Criteria andIsvalidIsNotNull() {
            addCriterion("isvalid is not null");
            return (Criteria) this;
        }

        public Criteria andIsvalidEqualTo(Integer value) {
            addCriterion("isvalid =", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidNotEqualTo(Integer value) {
            addCriterion("isvalid <>", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidGreaterThan(Integer value) {
            addCriterion("isvalid >", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidGreaterThanOrEqualTo(Integer value) {
            addCriterion("isvalid >=", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidLessThan(Integer value) {
            addCriterion("isvalid <", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidLessThanOrEqualTo(Integer value) {
            addCriterion("isvalid <=", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidIn(List<Integer> values) {
            addCriterion("isvalid in", values, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidNotIn(List<Integer> values) {
            addCriterion("isvalid not in", values, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidBetween(Integer value1, Integer value2) {
            addCriterion("isvalid between", value1, value2, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidNotBetween(Integer value1, Integer value2) {
            addCriterion("isvalid not between", value1, value2, "isvalid");
            return (Criteria) this;
        }

        public Criteria andReserveIsNull() {
            addCriterion("reserve is null");
            return (Criteria) this;
        }

        public Criteria andReserveIsNotNull() {
            addCriterion("reserve is not null");
            return (Criteria) this;
        }

        public Criteria andReserveEqualTo(String value) {
            addCriterion("reserve =", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveNotEqualTo(String value) {
            addCriterion("reserve <>", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveGreaterThan(String value) {
            addCriterion("reserve >", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveGreaterThanOrEqualTo(String value) {
            addCriterion("reserve >=", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveLessThan(String value) {
            addCriterion("reserve <", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveLessThanOrEqualTo(String value) {
            addCriterion("reserve <=", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveLike(String value) {
            addCriterion("reserve like", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveNotLike(String value) {
            addCriterion("reserve not like", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveIn(List<String> values) {
            addCriterion("reserve in", values, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveNotIn(List<String> values) {
            addCriterion("reserve not in", values, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveBetween(String value1, String value2) {
            addCriterion("reserve between", value1, value2, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveNotBetween(String value1, String value2) {
            addCriterion("reserve not between", value1, value2, "reserve");
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