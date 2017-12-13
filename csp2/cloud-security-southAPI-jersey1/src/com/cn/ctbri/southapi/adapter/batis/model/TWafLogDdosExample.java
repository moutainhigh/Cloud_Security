package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TWafLogDdosExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TWafLogDdosExample() {
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

        public Criteria andLogIdIsNull() {
            addCriterion("log_id is null");
            return (Criteria) this;
        }

        public Criteria andLogIdIsNotNull() {
            addCriterion("log_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogIdEqualTo(Long value) {
            addCriterion("log_id =", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotEqualTo(Long value) {
            addCriterion("log_id <>", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThan(Long value) {
            addCriterion("log_id >", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("log_id >=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThan(Long value) {
            addCriterion("log_id <", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThanOrEqualTo(Long value) {
            addCriterion("log_id <=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdIn(List<Long> values) {
            addCriterion("log_id in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotIn(List<Long> values) {
            addCriterion("log_id not in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdBetween(Long value1, Long value2) {
            addCriterion("log_id between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotBetween(Long value1, Long value2) {
            addCriterion("log_id not between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andResourceIdIsNull() {
            addCriterion("resource_id is null");
            return (Criteria) this;
        }

        public Criteria andResourceIdIsNotNull() {
            addCriterion("resource_id is not null");
            return (Criteria) this;
        }

        public Criteria andResourceIdEqualTo(Integer value) {
            addCriterion("resource_id =", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdNotEqualTo(Integer value) {
            addCriterion("resource_id <>", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdGreaterThan(Integer value) {
            addCriterion("resource_id >", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_id >=", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdLessThan(Integer value) {
            addCriterion("resource_id <", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("resource_id <=", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdIn(List<Integer> values) {
            addCriterion("resource_id in", values, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdNotIn(List<Integer> values) {
            addCriterion("resource_id not in", values, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdBetween(Integer value1, Integer value2) {
            addCriterion("resource_id between", value1, value2, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_id not between", value1, value2, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceUriIsNull() {
            addCriterion("resource_uri is null");
            return (Criteria) this;
        }

        public Criteria andResourceUriIsNotNull() {
            addCriterion("resource_uri is not null");
            return (Criteria) this;
        }

        public Criteria andResourceUriEqualTo(String value) {
            addCriterion("resource_uri =", value, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriNotEqualTo(String value) {
            addCriterion("resource_uri <>", value, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriGreaterThan(String value) {
            addCriterion("resource_uri >", value, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriGreaterThanOrEqualTo(String value) {
            addCriterion("resource_uri >=", value, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriLessThan(String value) {
            addCriterion("resource_uri <", value, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriLessThanOrEqualTo(String value) {
            addCriterion("resource_uri <=", value, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriLike(String value) {
            addCriterion("resource_uri like", value, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriNotLike(String value) {
            addCriterion("resource_uri not like", value, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriIn(List<String> values) {
            addCriterion("resource_uri in", values, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriNotIn(List<String> values) {
            addCriterion("resource_uri not in", values, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriBetween(String value1, String value2) {
            addCriterion("resource_uri between", value1, value2, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceUriNotBetween(String value1, String value2) {
            addCriterion("resource_uri not between", value1, value2, "resourceUri");
            return (Criteria) this;
        }

        public Criteria andResourceIpIsNull() {
            addCriterion("resource_ip is null");
            return (Criteria) this;
        }

        public Criteria andResourceIpIsNotNull() {
            addCriterion("resource_ip is not null");
            return (Criteria) this;
        }

        public Criteria andResourceIpEqualTo(String value) {
            addCriterion("resource_ip =", value, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpNotEqualTo(String value) {
            addCriterion("resource_ip <>", value, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpGreaterThan(String value) {
            addCriterion("resource_ip >", value, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpGreaterThanOrEqualTo(String value) {
            addCriterion("resource_ip >=", value, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpLessThan(String value) {
            addCriterion("resource_ip <", value, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpLessThanOrEqualTo(String value) {
            addCriterion("resource_ip <=", value, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpLike(String value) {
            addCriterion("resource_ip like", value, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpNotLike(String value) {
            addCriterion("resource_ip not like", value, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpIn(List<String> values) {
            addCriterion("resource_ip in", values, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpNotIn(List<String> values) {
            addCriterion("resource_ip not in", values, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpBetween(String value1, String value2) {
            addCriterion("resource_ip between", value1, value2, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andResourceIpNotBetween(String value1, String value2) {
            addCriterion("resource_ip not between", value1, value2, "resourceIp");
            return (Criteria) this;
        }

        public Criteria andStatTimeIsNull() {
            addCriterion("stat_time is null");
            return (Criteria) this;
        }

        public Criteria andStatTimeIsNotNull() {
            addCriterion("stat_time is not null");
            return (Criteria) this;
        }

        public Criteria andStatTimeEqualTo(Date value) {
            addCriterion("stat_time =", value, "statTime");
            return (Criteria) this;
        }

        public Criteria andStatTimeNotEqualTo(Date value) {
            addCriterion("stat_time <>", value, "statTime");
            return (Criteria) this;
        }

        public Criteria andStatTimeGreaterThan(Date value) {
            addCriterion("stat_time >", value, "statTime");
            return (Criteria) this;
        }

        public Criteria andStatTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("stat_time >=", value, "statTime");
            return (Criteria) this;
        }

        public Criteria andStatTimeLessThan(Date value) {
            addCriterion("stat_time <", value, "statTime");
            return (Criteria) this;
        }

        public Criteria andStatTimeLessThanOrEqualTo(Date value) {
            addCriterion("stat_time <=", value, "statTime");
            return (Criteria) this;
        }

        public Criteria andStatTimeIn(List<Date> values) {
            addCriterion("stat_time in", values, "statTime");
            return (Criteria) this;
        }

        public Criteria andStatTimeNotIn(List<Date> values) {
            addCriterion("stat_time not in", values, "statTime");
            return (Criteria) this;
        }

        public Criteria andStatTimeBetween(Date value1, Date value2) {
            addCriterion("stat_time between", value1, value2, "statTime");
            return (Criteria) this;
        }

        public Criteria andStatTimeNotBetween(Date value1, Date value2) {
            addCriterion("stat_time not between", value1, value2, "statTime");
            return (Criteria) this;
        }

        public Criteria andAlertlevelIsNull() {
            addCriterion("alertlevel is null");
            return (Criteria) this;
        }

        public Criteria andAlertlevelIsNotNull() {
            addCriterion("alertlevel is not null");
            return (Criteria) this;
        }

        public Criteria andAlertlevelEqualTo(String value) {
            addCriterion("alertlevel =", value, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelNotEqualTo(String value) {
            addCriterion("alertlevel <>", value, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelGreaterThan(String value) {
            addCriterion("alertlevel >", value, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelGreaterThanOrEqualTo(String value) {
            addCriterion("alertlevel >=", value, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelLessThan(String value) {
            addCriterion("alertlevel <", value, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelLessThanOrEqualTo(String value) {
            addCriterion("alertlevel <=", value, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelLike(String value) {
            addCriterion("alertlevel like", value, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelNotLike(String value) {
            addCriterion("alertlevel not like", value, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelIn(List<String> values) {
            addCriterion("alertlevel in", values, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelNotIn(List<String> values) {
            addCriterion("alertlevel not in", values, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelBetween(String value1, String value2) {
            addCriterion("alertlevel between", value1, value2, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andAlertlevelNotBetween(String value1, String value2) {
            addCriterion("alertlevel not between", value1, value2, "alertlevel");
            return (Criteria) this;
        }

        public Criteria andEventTypeIsNull() {
            addCriterion("event_type is null");
            return (Criteria) this;
        }

        public Criteria andEventTypeIsNotNull() {
            addCriterion("event_type is not null");
            return (Criteria) this;
        }

        public Criteria andEventTypeEqualTo(String value) {
            addCriterion("event_type =", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotEqualTo(String value) {
            addCriterion("event_type <>", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeGreaterThan(String value) {
            addCriterion("event_type >", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeGreaterThanOrEqualTo(String value) {
            addCriterion("event_type >=", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeLessThan(String value) {
            addCriterion("event_type <", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeLessThanOrEqualTo(String value) {
            addCriterion("event_type <=", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeLike(String value) {
            addCriterion("event_type like", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotLike(String value) {
            addCriterion("event_type not like", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeIn(List<String> values) {
            addCriterion("event_type in", values, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotIn(List<String> values) {
            addCriterion("event_type not in", values, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeBetween(String value1, String value2) {
            addCriterion("event_type between", value1, value2, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotBetween(String value1, String value2) {
            addCriterion("event_type not between", value1, value2, "eventType");
            return (Criteria) this;
        }

        public Criteria andDstIpIsNull() {
            addCriterion("dst_ip is null");
            return (Criteria) this;
        }

        public Criteria andDstIpIsNotNull() {
            addCriterion("dst_ip is not null");
            return (Criteria) this;
        }

        public Criteria andDstIpEqualTo(String value) {
            addCriterion("dst_ip =", value, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpNotEqualTo(String value) {
            addCriterion("dst_ip <>", value, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpGreaterThan(String value) {
            addCriterion("dst_ip >", value, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpGreaterThanOrEqualTo(String value) {
            addCriterion("dst_ip >=", value, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpLessThan(String value) {
            addCriterion("dst_ip <", value, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpLessThanOrEqualTo(String value) {
            addCriterion("dst_ip <=", value, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpLike(String value) {
            addCriterion("dst_ip like", value, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpNotLike(String value) {
            addCriterion("dst_ip not like", value, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpIn(List<String> values) {
            addCriterion("dst_ip in", values, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpNotIn(List<String> values) {
            addCriterion("dst_ip not in", values, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpBetween(String value1, String value2) {
            addCriterion("dst_ip between", value1, value2, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstIpNotBetween(String value1, String value2) {
            addCriterion("dst_ip not between", value1, value2, "dstIp");
            return (Criteria) this;
        }

        public Criteria andDstPortIsNull() {
            addCriterion("dst_port is null");
            return (Criteria) this;
        }

        public Criteria andDstPortIsNotNull() {
            addCriterion("dst_port is not null");
            return (Criteria) this;
        }

        public Criteria andDstPortEqualTo(String value) {
            addCriterion("dst_port =", value, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortNotEqualTo(String value) {
            addCriterion("dst_port <>", value, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortGreaterThan(String value) {
            addCriterion("dst_port >", value, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortGreaterThanOrEqualTo(String value) {
            addCriterion("dst_port >=", value, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortLessThan(String value) {
            addCriterion("dst_port <", value, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortLessThanOrEqualTo(String value) {
            addCriterion("dst_port <=", value, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortLike(String value) {
            addCriterion("dst_port like", value, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortNotLike(String value) {
            addCriterion("dst_port not like", value, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortIn(List<String> values) {
            addCriterion("dst_port in", values, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortNotIn(List<String> values) {
            addCriterion("dst_port not in", values, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortBetween(String value1, String value2) {
            addCriterion("dst_port between", value1, value2, "dstPort");
            return (Criteria) this;
        }

        public Criteria andDstPortNotBetween(String value1, String value2) {
            addCriterion("dst_port not between", value1, value2, "dstPort");
            return (Criteria) this;
        }

        public Criteria andActionIsNull() {
            addCriterion("action is null");
            return (Criteria) this;
        }

        public Criteria andActionIsNotNull() {
            addCriterion("action is not null");
            return (Criteria) this;
        }

        public Criteria andActionEqualTo(String value) {
            addCriterion("action =", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotEqualTo(String value) {
            addCriterion("action <>", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionGreaterThan(String value) {
            addCriterion("action >", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionGreaterThanOrEqualTo(String value) {
            addCriterion("action >=", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLessThan(String value) {
            addCriterion("action <", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLessThanOrEqualTo(String value) {
            addCriterion("action <=", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLike(String value) {
            addCriterion("action like", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotLike(String value) {
            addCriterion("action not like", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionIn(List<String> values) {
            addCriterion("action in", values, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotIn(List<String> values) {
            addCriterion("action not in", values, "action");
            return (Criteria) this;
        }

        public Criteria andActionBetween(String value1, String value2) {
            addCriterion("action between", value1, value2, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotBetween(String value1, String value2) {
            addCriterion("action not between", value1, value2, "action");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidIsNull() {
            addCriterion("ip_latlong_valid is null");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidIsNotNull() {
            addCriterion("ip_latlong_valid is not null");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidEqualTo(Integer value) {
            addCriterion("ip_latlong_valid =", value, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidNotEqualTo(Integer value) {
            addCriterion("ip_latlong_valid <>", value, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidGreaterThan(Integer value) {
            addCriterion("ip_latlong_valid >", value, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidGreaterThanOrEqualTo(Integer value) {
            addCriterion("ip_latlong_valid >=", value, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidLessThan(Integer value) {
            addCriterion("ip_latlong_valid <", value, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidLessThanOrEqualTo(Integer value) {
            addCriterion("ip_latlong_valid <=", value, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidIn(List<Integer> values) {
            addCriterion("ip_latlong_valid in", values, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidNotIn(List<Integer> values) {
            addCriterion("ip_latlong_valid not in", values, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidBetween(Integer value1, Integer value2) {
            addCriterion("ip_latlong_valid between", value1, value2, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andIpLatlongValidNotBetween(Integer value1, Integer value2) {
            addCriterion("ip_latlong_valid not between", value1, value2, "ipLatlongValid");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeIsNull() {
            addCriterion("src_country_code is null");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeIsNotNull() {
            addCriterion("src_country_code is not null");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeEqualTo(String value) {
            addCriterion("src_country_code =", value, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeNotEqualTo(String value) {
            addCriterion("src_country_code <>", value, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeGreaterThan(String value) {
            addCriterion("src_country_code >", value, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("src_country_code >=", value, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeLessThan(String value) {
            addCriterion("src_country_code <", value, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeLessThanOrEqualTo(String value) {
            addCriterion("src_country_code <=", value, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeLike(String value) {
            addCriterion("src_country_code like", value, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeNotLike(String value) {
            addCriterion("src_country_code not like", value, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeIn(List<String> values) {
            addCriterion("src_country_code in", values, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeNotIn(List<String> values) {
            addCriterion("src_country_code not in", values, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeBetween(String value1, String value2) {
            addCriterion("src_country_code between", value1, value2, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryCodeNotBetween(String value1, String value2) {
            addCriterion("src_country_code not between", value1, value2, "srcCountryCode");
            return (Criteria) this;
        }

        public Criteria andSrcCountryIsNull() {
            addCriterion("src_country is null");
            return (Criteria) this;
        }

        public Criteria andSrcCountryIsNotNull() {
            addCriterion("src_country is not null");
            return (Criteria) this;
        }

        public Criteria andSrcCountryEqualTo(String value) {
            addCriterion("src_country =", value, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryNotEqualTo(String value) {
            addCriterion("src_country <>", value, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryGreaterThan(String value) {
            addCriterion("src_country >", value, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryGreaterThanOrEqualTo(String value) {
            addCriterion("src_country >=", value, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryLessThan(String value) {
            addCriterion("src_country <", value, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryLessThanOrEqualTo(String value) {
            addCriterion("src_country <=", value, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryLike(String value) {
            addCriterion("src_country like", value, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryNotLike(String value) {
            addCriterion("src_country not like", value, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryIn(List<String> values) {
            addCriterion("src_country in", values, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryNotIn(List<String> values) {
            addCriterion("src_country not in", values, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryBetween(String value1, String value2) {
            addCriterion("src_country between", value1, value2, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcCountryNotBetween(String value1, String value2) {
            addCriterion("src_country not between", value1, value2, "srcCountry");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1IsNull() {
            addCriterion("src_subdivision_1 is null");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1IsNotNull() {
            addCriterion("src_subdivision_1 is not null");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1EqualTo(String value) {
            addCriterion("src_subdivision_1 =", value, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1NotEqualTo(String value) {
            addCriterion("src_subdivision_1 <>", value, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1GreaterThan(String value) {
            addCriterion("src_subdivision_1 >", value, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1GreaterThanOrEqualTo(String value) {
            addCriterion("src_subdivision_1 >=", value, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1LessThan(String value) {
            addCriterion("src_subdivision_1 <", value, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1LessThanOrEqualTo(String value) {
            addCriterion("src_subdivision_1 <=", value, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1Like(String value) {
            addCriterion("src_subdivision_1 like", value, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1NotLike(String value) {
            addCriterion("src_subdivision_1 not like", value, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1In(List<String> values) {
            addCriterion("src_subdivision_1 in", values, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1NotIn(List<String> values) {
            addCriterion("src_subdivision_1 not in", values, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1Between(String value1, String value2) {
            addCriterion("src_subdivision_1 between", value1, value2, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision1NotBetween(String value1, String value2) {
            addCriterion("src_subdivision_1 not between", value1, value2, "srcSubdivision1");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2IsNull() {
            addCriterion("src_subdivision_2 is null");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2IsNotNull() {
            addCriterion("src_subdivision_2 is not null");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2EqualTo(String value) {
            addCriterion("src_subdivision_2 =", value, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2NotEqualTo(String value) {
            addCriterion("src_subdivision_2 <>", value, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2GreaterThan(String value) {
            addCriterion("src_subdivision_2 >", value, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2GreaterThanOrEqualTo(String value) {
            addCriterion("src_subdivision_2 >=", value, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2LessThan(String value) {
            addCriterion("src_subdivision_2 <", value, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2LessThanOrEqualTo(String value) {
            addCriterion("src_subdivision_2 <=", value, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2Like(String value) {
            addCriterion("src_subdivision_2 like", value, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2NotLike(String value) {
            addCriterion("src_subdivision_2 not like", value, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2In(List<String> values) {
            addCriterion("src_subdivision_2 in", values, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2NotIn(List<String> values) {
            addCriterion("src_subdivision_2 not in", values, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2Between(String value1, String value2) {
            addCriterion("src_subdivision_2 between", value1, value2, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcSubdivision2NotBetween(String value1, String value2) {
            addCriterion("src_subdivision_2 not between", value1, value2, "srcSubdivision2");
            return (Criteria) this;
        }

        public Criteria andSrcCityIsNull() {
            addCriterion("src_city is null");
            return (Criteria) this;
        }

        public Criteria andSrcCityIsNotNull() {
            addCriterion("src_city is not null");
            return (Criteria) this;
        }

        public Criteria andSrcCityEqualTo(String value) {
            addCriterion("src_city =", value, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityNotEqualTo(String value) {
            addCriterion("src_city <>", value, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityGreaterThan(String value) {
            addCriterion("src_city >", value, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityGreaterThanOrEqualTo(String value) {
            addCriterion("src_city >=", value, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityLessThan(String value) {
            addCriterion("src_city <", value, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityLessThanOrEqualTo(String value) {
            addCriterion("src_city <=", value, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityLike(String value) {
            addCriterion("src_city like", value, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityNotLike(String value) {
            addCriterion("src_city not like", value, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityIn(List<String> values) {
            addCriterion("src_city in", values, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityNotIn(List<String> values) {
            addCriterion("src_city not in", values, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityBetween(String value1, String value2) {
            addCriterion("src_city between", value1, value2, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcCityNotBetween(String value1, String value2) {
            addCriterion("src_city not between", value1, value2, "srcCity");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeIsNull() {
            addCriterion("src_latitude is null");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeIsNotNull() {
            addCriterion("src_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeEqualTo(String value) {
            addCriterion("src_latitude =", value, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeNotEqualTo(String value) {
            addCriterion("src_latitude <>", value, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeGreaterThan(String value) {
            addCriterion("src_latitude >", value, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeGreaterThanOrEqualTo(String value) {
            addCriterion("src_latitude >=", value, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeLessThan(String value) {
            addCriterion("src_latitude <", value, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeLessThanOrEqualTo(String value) {
            addCriterion("src_latitude <=", value, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeLike(String value) {
            addCriterion("src_latitude like", value, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeNotLike(String value) {
            addCriterion("src_latitude not like", value, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeIn(List<String> values) {
            addCriterion("src_latitude in", values, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeNotIn(List<String> values) {
            addCriterion("src_latitude not in", values, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeBetween(String value1, String value2) {
            addCriterion("src_latitude between", value1, value2, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLatitudeNotBetween(String value1, String value2) {
            addCriterion("src_latitude not between", value1, value2, "srcLatitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeIsNull() {
            addCriterion("src_longitude is null");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeIsNotNull() {
            addCriterion("src_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeEqualTo(String value) {
            addCriterion("src_longitude =", value, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeNotEqualTo(String value) {
            addCriterion("src_longitude <>", value, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeGreaterThan(String value) {
            addCriterion("src_longitude >", value, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("src_longitude >=", value, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeLessThan(String value) {
            addCriterion("src_longitude <", value, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeLessThanOrEqualTo(String value) {
            addCriterion("src_longitude <=", value, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeLike(String value) {
            addCriterion("src_longitude like", value, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeNotLike(String value) {
            addCriterion("src_longitude not like", value, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeIn(List<String> values) {
            addCriterion("src_longitude in", values, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeNotIn(List<String> values) {
            addCriterion("src_longitude not in", values, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeBetween(String value1, String value2) {
            addCriterion("src_longitude between", value1, value2, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andSrcLongitudeNotBetween(String value1, String value2) {
            addCriterion("src_longitude not between", value1, value2, "srcLongitude");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeIsNull() {
            addCriterion("dst_country_code is null");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeIsNotNull() {
            addCriterion("dst_country_code is not null");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeEqualTo(String value) {
            addCriterion("dst_country_code =", value, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeNotEqualTo(String value) {
            addCriterion("dst_country_code <>", value, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeGreaterThan(String value) {
            addCriterion("dst_country_code >", value, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("dst_country_code >=", value, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeLessThan(String value) {
            addCriterion("dst_country_code <", value, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeLessThanOrEqualTo(String value) {
            addCriterion("dst_country_code <=", value, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeLike(String value) {
            addCriterion("dst_country_code like", value, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeNotLike(String value) {
            addCriterion("dst_country_code not like", value, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeIn(List<String> values) {
            addCriterion("dst_country_code in", values, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeNotIn(List<String> values) {
            addCriterion("dst_country_code not in", values, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeBetween(String value1, String value2) {
            addCriterion("dst_country_code between", value1, value2, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryCodeNotBetween(String value1, String value2) {
            addCriterion("dst_country_code not between", value1, value2, "dstCountryCode");
            return (Criteria) this;
        }

        public Criteria andDstCountryIsNull() {
            addCriterion("dst_country is null");
            return (Criteria) this;
        }

        public Criteria andDstCountryIsNotNull() {
            addCriterion("dst_country is not null");
            return (Criteria) this;
        }

        public Criteria andDstCountryEqualTo(String value) {
            addCriterion("dst_country =", value, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryNotEqualTo(String value) {
            addCriterion("dst_country <>", value, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryGreaterThan(String value) {
            addCriterion("dst_country >", value, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryGreaterThanOrEqualTo(String value) {
            addCriterion("dst_country >=", value, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryLessThan(String value) {
            addCriterion("dst_country <", value, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryLessThanOrEqualTo(String value) {
            addCriterion("dst_country <=", value, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryLike(String value) {
            addCriterion("dst_country like", value, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryNotLike(String value) {
            addCriterion("dst_country not like", value, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryIn(List<String> values) {
            addCriterion("dst_country in", values, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryNotIn(List<String> values) {
            addCriterion("dst_country not in", values, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryBetween(String value1, String value2) {
            addCriterion("dst_country between", value1, value2, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstCountryNotBetween(String value1, String value2) {
            addCriterion("dst_country not between", value1, value2, "dstCountry");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1IsNull() {
            addCriterion("dst_subdivision_1 is null");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1IsNotNull() {
            addCriterion("dst_subdivision_1 is not null");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1EqualTo(String value) {
            addCriterion("dst_subdivision_1 =", value, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1NotEqualTo(String value) {
            addCriterion("dst_subdivision_1 <>", value, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1GreaterThan(String value) {
            addCriterion("dst_subdivision_1 >", value, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1GreaterThanOrEqualTo(String value) {
            addCriterion("dst_subdivision_1 >=", value, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1LessThan(String value) {
            addCriterion("dst_subdivision_1 <", value, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1LessThanOrEqualTo(String value) {
            addCriterion("dst_subdivision_1 <=", value, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1Like(String value) {
            addCriterion("dst_subdivision_1 like", value, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1NotLike(String value) {
            addCriterion("dst_subdivision_1 not like", value, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1In(List<String> values) {
            addCriterion("dst_subdivision_1 in", values, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1NotIn(List<String> values) {
            addCriterion("dst_subdivision_1 not in", values, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1Between(String value1, String value2) {
            addCriterion("dst_subdivision_1 between", value1, value2, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision1NotBetween(String value1, String value2) {
            addCriterion("dst_subdivision_1 not between", value1, value2, "dstSubdivision1");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2IsNull() {
            addCriterion("dst_subdivision_2 is null");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2IsNotNull() {
            addCriterion("dst_subdivision_2 is not null");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2EqualTo(String value) {
            addCriterion("dst_subdivision_2 =", value, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2NotEqualTo(String value) {
            addCriterion("dst_subdivision_2 <>", value, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2GreaterThan(String value) {
            addCriterion("dst_subdivision_2 >", value, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2GreaterThanOrEqualTo(String value) {
            addCriterion("dst_subdivision_2 >=", value, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2LessThan(String value) {
            addCriterion("dst_subdivision_2 <", value, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2LessThanOrEqualTo(String value) {
            addCriterion("dst_subdivision_2 <=", value, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2Like(String value) {
            addCriterion("dst_subdivision_2 like", value, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2NotLike(String value) {
            addCriterion("dst_subdivision_2 not like", value, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2In(List<String> values) {
            addCriterion("dst_subdivision_2 in", values, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2NotIn(List<String> values) {
            addCriterion("dst_subdivision_2 not in", values, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2Between(String value1, String value2) {
            addCriterion("dst_subdivision_2 between", value1, value2, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstSubdivision2NotBetween(String value1, String value2) {
            addCriterion("dst_subdivision_2 not between", value1, value2, "dstSubdivision2");
            return (Criteria) this;
        }

        public Criteria andDstCityIsNull() {
            addCriterion("dst_city is null");
            return (Criteria) this;
        }

        public Criteria andDstCityIsNotNull() {
            addCriterion("dst_city is not null");
            return (Criteria) this;
        }

        public Criteria andDstCityEqualTo(String value) {
            addCriterion("dst_city =", value, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityNotEqualTo(String value) {
            addCriterion("dst_city <>", value, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityGreaterThan(String value) {
            addCriterion("dst_city >", value, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityGreaterThanOrEqualTo(String value) {
            addCriterion("dst_city >=", value, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityLessThan(String value) {
            addCriterion("dst_city <", value, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityLessThanOrEqualTo(String value) {
            addCriterion("dst_city <=", value, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityLike(String value) {
            addCriterion("dst_city like", value, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityNotLike(String value) {
            addCriterion("dst_city not like", value, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityIn(List<String> values) {
            addCriterion("dst_city in", values, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityNotIn(List<String> values) {
            addCriterion("dst_city not in", values, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityBetween(String value1, String value2) {
            addCriterion("dst_city between", value1, value2, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstCityNotBetween(String value1, String value2) {
            addCriterion("dst_city not between", value1, value2, "dstCity");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeIsNull() {
            addCriterion("dst_latitude is null");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeIsNotNull() {
            addCriterion("dst_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeEqualTo(String value) {
            addCriterion("dst_latitude =", value, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeNotEqualTo(String value) {
            addCriterion("dst_latitude <>", value, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeGreaterThan(String value) {
            addCriterion("dst_latitude >", value, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeGreaterThanOrEqualTo(String value) {
            addCriterion("dst_latitude >=", value, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeLessThan(String value) {
            addCriterion("dst_latitude <", value, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeLessThanOrEqualTo(String value) {
            addCriterion("dst_latitude <=", value, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeLike(String value) {
            addCriterion("dst_latitude like", value, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeNotLike(String value) {
            addCriterion("dst_latitude not like", value, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeIn(List<String> values) {
            addCriterion("dst_latitude in", values, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeNotIn(List<String> values) {
            addCriterion("dst_latitude not in", values, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeBetween(String value1, String value2) {
            addCriterion("dst_latitude between", value1, value2, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLatitudeNotBetween(String value1, String value2) {
            addCriterion("dst_latitude not between", value1, value2, "dstLatitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeIsNull() {
            addCriterion("dst_longitude is null");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeIsNotNull() {
            addCriterion("dst_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeEqualTo(String value) {
            addCriterion("dst_longitude =", value, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeNotEqualTo(String value) {
            addCriterion("dst_longitude <>", value, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeGreaterThan(String value) {
            addCriterion("dst_longitude >", value, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("dst_longitude >=", value, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeLessThan(String value) {
            addCriterion("dst_longitude <", value, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeLessThanOrEqualTo(String value) {
            addCriterion("dst_longitude <=", value, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeLike(String value) {
            addCriterion("dst_longitude like", value, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeNotLike(String value) {
            addCriterion("dst_longitude not like", value, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeIn(List<String> values) {
            addCriterion("dst_longitude in", values, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeNotIn(List<String> values) {
            addCriterion("dst_longitude not in", values, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeBetween(String value1, String value2) {
            addCriterion("dst_longitude between", value1, value2, "dstLongitude");
            return (Criteria) this;
        }

        public Criteria andDstLongitudeNotBetween(String value1, String value2) {
            addCriterion("dst_longitude not between", value1, value2, "dstLongitude");
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