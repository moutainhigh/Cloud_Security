package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.ArrayList;
import java.util.List;

public class TViewWebPhishProvinceCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TViewWebPhishProvinceCountExample() {
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

        public Criteria andCountIsNull() {
            addCriterion("count is null");
            return (Criteria) this;
        }

        public Criteria andCountIsNotNull() {
            addCriterion("count is not null");
            return (Criteria) this;
        }

        public Criteria andCountEqualTo(Long value) {
            addCriterion("count =", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotEqualTo(Long value) {
            addCriterion("count <>", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThan(Long value) {
            addCriterion("count >", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThanOrEqualTo(Long value) {
            addCriterion("count >=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThan(Long value) {
            addCriterion("count <", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThanOrEqualTo(Long value) {
            addCriterion("count <=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountIn(List<Long> values) {
            addCriterion("count in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotIn(List<Long> values) {
            addCriterion("count not in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountBetween(Long value1, Long value2) {
            addCriterion("count between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotBetween(Long value1, Long value2) {
            addCriterion("count not between", value1, value2, "count");
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