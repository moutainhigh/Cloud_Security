package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TWafLogDefaceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TWafLogDefaceExample() {
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

        public Criteria andSiteIdIsNull() {
            addCriterion("site_id is null");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNotNull() {
            addCriterion("site_id is not null");
            return (Criteria) this;
        }

        public Criteria andSiteIdEqualTo(Integer value) {
            addCriterion("site_id =", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotEqualTo(Integer value) {
            addCriterion("site_id <>", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThan(Integer value) {
            addCriterion("site_id >", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("site_id >=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThan(Integer value) {
            addCriterion("site_id <", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(Integer value) {
            addCriterion("site_id <=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIn(List<Integer> values) {
            addCriterion("site_id in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotIn(List<Integer> values) {
            addCriterion("site_id not in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdBetween(Integer value1, Integer value2) {
            addCriterion("site_id between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotBetween(Integer value1, Integer value2) {
            addCriterion("site_id not between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andProtectIdIsNull() {
            addCriterion("protect_id is null");
            return (Criteria) this;
        }

        public Criteria andProtectIdIsNotNull() {
            addCriterion("protect_id is not null");
            return (Criteria) this;
        }

        public Criteria andProtectIdEqualTo(Integer value) {
            addCriterion("protect_id =", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdNotEqualTo(Integer value) {
            addCriterion("protect_id <>", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdGreaterThan(Integer value) {
            addCriterion("protect_id >", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("protect_id >=", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdLessThan(Integer value) {
            addCriterion("protect_id <", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdLessThanOrEqualTo(Integer value) {
            addCriterion("protect_id <=", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdIn(List<Integer> values) {
            addCriterion("protect_id in", values, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdNotIn(List<Integer> values) {
            addCriterion("protect_id not in", values, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdBetween(Integer value1, Integer value2) {
            addCriterion("protect_id between", value1, value2, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("protect_id not between", value1, value2, "protectId");
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

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
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