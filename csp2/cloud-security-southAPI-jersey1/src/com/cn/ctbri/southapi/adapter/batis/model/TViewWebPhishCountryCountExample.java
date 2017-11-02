package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.ArrayList;
import java.util.List;

public class TViewWebPhishCountryCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TViewWebPhishCountryCountExample() {
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

        public Criteria andWebphishCountIsNull() {
            addCriterion("webphish_count is null");
            return (Criteria) this;
        }

        public Criteria andWebphishCountIsNotNull() {
            addCriterion("webphish_count is not null");
            return (Criteria) this;
        }

        public Criteria andWebphishCountEqualTo(Long value) {
            addCriterion("webphish_count =", value, "webphishCount");
            return (Criteria) this;
        }

        public Criteria andWebphishCountNotEqualTo(Long value) {
            addCriterion("webphish_count <>", value, "webphishCount");
            return (Criteria) this;
        }

        public Criteria andWebphishCountGreaterThan(Long value) {
            addCriterion("webphish_count >", value, "webphishCount");
            return (Criteria) this;
        }

        public Criteria andWebphishCountGreaterThanOrEqualTo(Long value) {
            addCriterion("webphish_count >=", value, "webphishCount");
            return (Criteria) this;
        }

        public Criteria andWebphishCountLessThan(Long value) {
            addCriterion("webphish_count <", value, "webphishCount");
            return (Criteria) this;
        }

        public Criteria andWebphishCountLessThanOrEqualTo(Long value) {
            addCriterion("webphish_count <=", value, "webphishCount");
            return (Criteria) this;
        }

        public Criteria andWebphishCountIn(List<Long> values) {
            addCriterion("webphish_count in", values, "webphishCount");
            return (Criteria) this;
        }

        public Criteria andWebphishCountNotIn(List<Long> values) {
            addCriterion("webphish_count not in", values, "webphishCount");
            return (Criteria) this;
        }

        public Criteria andWebphishCountBetween(Long value1, Long value2) {
            addCriterion("webphish_count between", value1, value2, "webphishCount");
            return (Criteria) this;
        }

        public Criteria andWebphishCountNotBetween(Long value1, Long value2) {
            addCriterion("webphish_count not between", value1, value2, "webphishCount");
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