package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TWafLogArpExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TWafLogArpExample() {
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

        public Criteria andAttackTypeIsNull() {
            addCriterion("attack_type is null");
            return (Criteria) this;
        }

        public Criteria andAttackTypeIsNotNull() {
            addCriterion("attack_type is not null");
            return (Criteria) this;
        }

        public Criteria andAttackTypeEqualTo(String value) {
            addCriterion("attack_type =", value, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeNotEqualTo(String value) {
            addCriterion("attack_type <>", value, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeGreaterThan(String value) {
            addCriterion("attack_type >", value, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeGreaterThanOrEqualTo(String value) {
            addCriterion("attack_type >=", value, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeLessThan(String value) {
            addCriterion("attack_type <", value, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeLessThanOrEqualTo(String value) {
            addCriterion("attack_type <=", value, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeLike(String value) {
            addCriterion("attack_type like", value, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeNotLike(String value) {
            addCriterion("attack_type not like", value, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeIn(List<String> values) {
            addCriterion("attack_type in", values, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeNotIn(List<String> values) {
            addCriterion("attack_type not in", values, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeBetween(String value1, String value2) {
            addCriterion("attack_type between", value1, value2, "attackType");
            return (Criteria) this;
        }

        public Criteria andAttackTypeNotBetween(String value1, String value2) {
            addCriterion("attack_type not between", value1, value2, "attackType");
            return (Criteria) this;
        }

        public Criteria andSrcIpIsNull() {
            addCriterion("src_ip is null");
            return (Criteria) this;
        }

        public Criteria andSrcIpIsNotNull() {
            addCriterion("src_ip is not null");
            return (Criteria) this;
        }

        public Criteria andSrcIpEqualTo(String value) {
            addCriterion("src_ip =", value, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpNotEqualTo(String value) {
            addCriterion("src_ip <>", value, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpGreaterThan(String value) {
            addCriterion("src_ip >", value, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpGreaterThanOrEqualTo(String value) {
            addCriterion("src_ip >=", value, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpLessThan(String value) {
            addCriterion("src_ip <", value, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpLessThanOrEqualTo(String value) {
            addCriterion("src_ip <=", value, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpLike(String value) {
            addCriterion("src_ip like", value, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpNotLike(String value) {
            addCriterion("src_ip not like", value, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpIn(List<String> values) {
            addCriterion("src_ip in", values, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpNotIn(List<String> values) {
            addCriterion("src_ip not in", values, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpBetween(String value1, String value2) {
            addCriterion("src_ip between", value1, value2, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcIpNotBetween(String value1, String value2) {
            addCriterion("src_ip not between", value1, value2, "srcIp");
            return (Criteria) this;
        }

        public Criteria andSrcMacIsNull() {
            addCriterion("src_mac is null");
            return (Criteria) this;
        }

        public Criteria andSrcMacIsNotNull() {
            addCriterion("src_mac is not null");
            return (Criteria) this;
        }

        public Criteria andSrcMacEqualTo(String value) {
            addCriterion("src_mac =", value, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacNotEqualTo(String value) {
            addCriterion("src_mac <>", value, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacGreaterThan(String value) {
            addCriterion("src_mac >", value, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacGreaterThanOrEqualTo(String value) {
            addCriterion("src_mac >=", value, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacLessThan(String value) {
            addCriterion("src_mac <", value, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacLessThanOrEqualTo(String value) {
            addCriterion("src_mac <=", value, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacLike(String value) {
            addCriterion("src_mac like", value, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacNotLike(String value) {
            addCriterion("src_mac not like", value, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacIn(List<String> values) {
            addCriterion("src_mac in", values, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacNotIn(List<String> values) {
            addCriterion("src_mac not in", values, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacBetween(String value1, String value2) {
            addCriterion("src_mac between", value1, value2, "srcMac");
            return (Criteria) this;
        }

        public Criteria andSrcMacNotBetween(String value1, String value2) {
            addCriterion("src_mac not between", value1, value2, "srcMac");
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

        public Criteria andDstMacIsNull() {
            addCriterion("dst_mac is null");
            return (Criteria) this;
        }

        public Criteria andDstMacIsNotNull() {
            addCriterion("dst_mac is not null");
            return (Criteria) this;
        }

        public Criteria andDstMacEqualTo(String value) {
            addCriterion("dst_mac =", value, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacNotEqualTo(String value) {
            addCriterion("dst_mac <>", value, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacGreaterThan(String value) {
            addCriterion("dst_mac >", value, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacGreaterThanOrEqualTo(String value) {
            addCriterion("dst_mac >=", value, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacLessThan(String value) {
            addCriterion("dst_mac <", value, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacLessThanOrEqualTo(String value) {
            addCriterion("dst_mac <=", value, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacLike(String value) {
            addCriterion("dst_mac like", value, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacNotLike(String value) {
            addCriterion("dst_mac not like", value, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacIn(List<String> values) {
            addCriterion("dst_mac in", values, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacNotIn(List<String> values) {
            addCriterion("dst_mac not in", values, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacBetween(String value1, String value2) {
            addCriterion("dst_mac between", value1, value2, "dstMac");
            return (Criteria) this;
        }

        public Criteria andDstMacNotBetween(String value1, String value2) {
            addCriterion("dst_mac not between", value1, value2, "dstMac");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andDefIpIsNull() {
            addCriterion("def_ip is null");
            return (Criteria) this;
        }

        public Criteria andDefIpIsNotNull() {
            addCriterion("def_ip is not null");
            return (Criteria) this;
        }

        public Criteria andDefIpEqualTo(String value) {
            addCriterion("def_ip =", value, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpNotEqualTo(String value) {
            addCriterion("def_ip <>", value, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpGreaterThan(String value) {
            addCriterion("def_ip >", value, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpGreaterThanOrEqualTo(String value) {
            addCriterion("def_ip >=", value, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpLessThan(String value) {
            addCriterion("def_ip <", value, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpLessThanOrEqualTo(String value) {
            addCriterion("def_ip <=", value, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpLike(String value) {
            addCriterion("def_ip like", value, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpNotLike(String value) {
            addCriterion("def_ip not like", value, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpIn(List<String> values) {
            addCriterion("def_ip in", values, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpNotIn(List<String> values) {
            addCriterion("def_ip not in", values, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpBetween(String value1, String value2) {
            addCriterion("def_ip between", value1, value2, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefIpNotBetween(String value1, String value2) {
            addCriterion("def_ip not between", value1, value2, "defIp");
            return (Criteria) this;
        }

        public Criteria andDefMacIsNull() {
            addCriterion("def_mac is null");
            return (Criteria) this;
        }

        public Criteria andDefMacIsNotNull() {
            addCriterion("def_mac is not null");
            return (Criteria) this;
        }

        public Criteria andDefMacEqualTo(String value) {
            addCriterion("def_mac =", value, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacNotEqualTo(String value) {
            addCriterion("def_mac <>", value, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacGreaterThan(String value) {
            addCriterion("def_mac >", value, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacGreaterThanOrEqualTo(String value) {
            addCriterion("def_mac >=", value, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacLessThan(String value) {
            addCriterion("def_mac <", value, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacLessThanOrEqualTo(String value) {
            addCriterion("def_mac <=", value, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacLike(String value) {
            addCriterion("def_mac like", value, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacNotLike(String value) {
            addCriterion("def_mac not like", value, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacIn(List<String> values) {
            addCriterion("def_mac in", values, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacNotIn(List<String> values) {
            addCriterion("def_mac not in", values, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacBetween(String value1, String value2) {
            addCriterion("def_mac between", value1, value2, "defMac");
            return (Criteria) this;
        }

        public Criteria andDefMacNotBetween(String value1, String value2) {
            addCriterion("def_mac not between", value1, value2, "defMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacIsNull() {
            addCriterion("conflit_mac is null");
            return (Criteria) this;
        }

        public Criteria andConflitMacIsNotNull() {
            addCriterion("conflit_mac is not null");
            return (Criteria) this;
        }

        public Criteria andConflitMacEqualTo(String value) {
            addCriterion("conflit_mac =", value, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacNotEqualTo(String value) {
            addCriterion("conflit_mac <>", value, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacGreaterThan(String value) {
            addCriterion("conflit_mac >", value, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacGreaterThanOrEqualTo(String value) {
            addCriterion("conflit_mac >=", value, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacLessThan(String value) {
            addCriterion("conflit_mac <", value, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacLessThanOrEqualTo(String value) {
            addCriterion("conflit_mac <=", value, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacLike(String value) {
            addCriterion("conflit_mac like", value, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacNotLike(String value) {
            addCriterion("conflit_mac not like", value, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacIn(List<String> values) {
            addCriterion("conflit_mac in", values, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacNotIn(List<String> values) {
            addCriterion("conflit_mac not in", values, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacBetween(String value1, String value2) {
            addCriterion("conflit_mac between", value1, value2, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andConflitMacNotBetween(String value1, String value2) {
            addCriterion("conflit_mac not between", value1, value2, "conflitMac");
            return (Criteria) this;
        }

        public Criteria andCountNumIsNull() {
            addCriterion("count_num is null");
            return (Criteria) this;
        }

        public Criteria andCountNumIsNotNull() {
            addCriterion("count_num is not null");
            return (Criteria) this;
        }

        public Criteria andCountNumEqualTo(String value) {
            addCriterion("count_num =", value, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumNotEqualTo(String value) {
            addCriterion("count_num <>", value, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumGreaterThan(String value) {
            addCriterion("count_num >", value, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumGreaterThanOrEqualTo(String value) {
            addCriterion("count_num >=", value, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumLessThan(String value) {
            addCriterion("count_num <", value, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumLessThanOrEqualTo(String value) {
            addCriterion("count_num <=", value, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumLike(String value) {
            addCriterion("count_num like", value, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumNotLike(String value) {
            addCriterion("count_num not like", value, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumIn(List<String> values) {
            addCriterion("count_num in", values, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumNotIn(List<String> values) {
            addCriterion("count_num not in", values, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumBetween(String value1, String value2) {
            addCriterion("count_num between", value1, value2, "countNum");
            return (Criteria) this;
        }

        public Criteria andCountNumNotBetween(String value1, String value2) {
            addCriterion("count_num not between", value1, value2, "countNum");
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