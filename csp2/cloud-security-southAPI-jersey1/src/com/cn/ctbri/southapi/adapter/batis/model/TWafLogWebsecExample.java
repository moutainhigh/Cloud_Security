package com.cn.ctbri.southapi.adapter.batis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TWafLogWebsecExample {
    protected String orderByClause;

    protected boolean distinct;

    //limit语句偏移量
    protected String offset;
	//limit语句返回行数
    protected String rows;
    
    protected List<Criteria> oredCriteria;

    public TWafLogWebsecExample() {
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
    
    public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
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
        public List<Criterion> criteria;

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

        public Criteria andSiteIdEqualTo(String value) {
            addCriterion("site_id =", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotEqualTo(String value) {
            addCriterion("site_id <>", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThan(String value) {
            addCriterion("site_id >", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(String value) {
            addCriterion("site_id >=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThan(String value) {
            addCriterion("site_id <", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(String value) {
            addCriterion("site_id <=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLike(String value) {
            addCriterion("site_id like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotLike(String value) {
            addCriterion("site_id not like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIn(List<String> values) {
            addCriterion("site_id in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotIn(List<String> values) {
            addCriterion("site_id not in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdBetween(String value1, String value2) {
            addCriterion("site_id between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotBetween(String value1, String value2) {
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

        public Criteria andProtectIdEqualTo(String value) {
            addCriterion("protect_id =", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdNotEqualTo(String value) {
            addCriterion("protect_id <>", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdGreaterThan(String value) {
            addCriterion("protect_id >", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdGreaterThanOrEqualTo(String value) {
            addCriterion("protect_id >=", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdLessThan(String value) {
            addCriterion("protect_id <", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdLessThanOrEqualTo(String value) {
            addCriterion("protect_id <=", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdLike(String value) {
            addCriterion("protect_id like", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdNotLike(String value) {
            addCriterion("protect_id not like", value, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdIn(List<String> values) {
            addCriterion("protect_id in", values, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdNotIn(List<String> values) {
            addCriterion("protect_id not in", values, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdBetween(String value1, String value2) {
            addCriterion("protect_id between", value1, value2, "protectId");
            return (Criteria) this;
        }

        public Criteria andProtectIdNotBetween(String value1, String value2) {
            addCriterion("protect_id not between", value1, value2, "protectId");
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

        public Criteria andSrcPortIsNull() {
            addCriterion("src_port is null");
            return (Criteria) this;
        }

        public Criteria andSrcPortIsNotNull() {
            addCriterion("src_port is not null");
            return (Criteria) this;
        }

        public Criteria andSrcPortEqualTo(String value) {
            addCriterion("src_port =", value, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortNotEqualTo(String value) {
            addCriterion("src_port <>", value, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortGreaterThan(String value) {
            addCriterion("src_port >", value, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortGreaterThanOrEqualTo(String value) {
            addCriterion("src_port >=", value, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortLessThan(String value) {
            addCriterion("src_port <", value, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortLessThanOrEqualTo(String value) {
            addCriterion("src_port <=", value, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortLike(String value) {
            addCriterion("src_port like", value, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortNotLike(String value) {
            addCriterion("src_port not like", value, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortIn(List<String> values) {
            addCriterion("src_port in", values, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortNotIn(List<String> values) {
            addCriterion("src_port not in", values, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortBetween(String value1, String value2) {
            addCriterion("src_port between", value1, value2, "srcPort");
            return (Criteria) this;
        }

        public Criteria andSrcPortNotBetween(String value1, String value2) {
            addCriterion("src_port not between", value1, value2, "srcPort");
            return (Criteria) this;
        }

        public Criteria andMethodIsNull() {
            addCriterion("method is null");
            return (Criteria) this;
        }

        public Criteria andMethodIsNotNull() {
            addCriterion("method is not null");
            return (Criteria) this;
        }

        public Criteria andMethodEqualTo(String value) {
            addCriterion("method =", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotEqualTo(String value) {
            addCriterion("method <>", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodGreaterThan(String value) {
            addCriterion("method >", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodGreaterThanOrEqualTo(String value) {
            addCriterion("method >=", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLessThan(String value) {
            addCriterion("method <", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLessThanOrEqualTo(String value) {
            addCriterion("method <=", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLike(String value) {
            addCriterion("method like", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotLike(String value) {
            addCriterion("method not like", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodIn(List<String> values) {
            addCriterion("method in", values, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotIn(List<String> values) {
            addCriterion("method not in", values, "method");
            return (Criteria) this;
        }

        public Criteria andMethodBetween(String value1, String value2) {
            addCriterion("method between", value1, value2, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotBetween(String value1, String value2) {
            addCriterion("method not between", value1, value2, "method");
            return (Criteria) this;
        }

        public Criteria andDomainIsNull() {
            addCriterion("domain is null");
            return (Criteria) this;
        }

        public Criteria andDomainIsNotNull() {
            addCriterion("domain is not null");
            return (Criteria) this;
        }

        public Criteria andDomainEqualTo(String value) {
            addCriterion("domain =", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotEqualTo(String value) {
            addCriterion("domain <>", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThan(String value) {
            addCriterion("domain >", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThanOrEqualTo(String value) {
            addCriterion("domain >=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThan(String value) {
            addCriterion("domain <", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThanOrEqualTo(String value) {
            addCriterion("domain <=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLike(String value) {
            addCriterion("domain like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotLike(String value) {
            addCriterion("domain not like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainIn(List<String> values) {
            addCriterion("domain in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotIn(List<String> values) {
            addCriterion("domain not in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainBetween(String value1, String value2) {
            addCriterion("domain between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotBetween(String value1, String value2) {
            addCriterion("domain not between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andUriIsNull() {
            addCriterion("uri is null");
            return (Criteria) this;
        }

        public Criteria andUriIsNotNull() {
            addCriterion("uri is not null");
            return (Criteria) this;
        }

        public Criteria andUriEqualTo(String value) {
            addCriterion("uri =", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotEqualTo(String value) {
            addCriterion("uri <>", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriGreaterThan(String value) {
            addCriterion("uri >", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriGreaterThanOrEqualTo(String value) {
            addCriterion("uri >=", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLessThan(String value) {
            addCriterion("uri <", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLessThanOrEqualTo(String value) {
            addCriterion("uri <=", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLike(String value) {
            addCriterion("uri like", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotLike(String value) {
            addCriterion("uri not like", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriIn(List<String> values) {
            addCriterion("uri in", values, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotIn(List<String> values) {
            addCriterion("uri not in", values, "uri");
            return (Criteria) this;
        }

        public Criteria andUriBetween(String value1, String value2) {
            addCriterion("uri between", value1, value2, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotBetween(String value1, String value2) {
            addCriterion("uri not between", value1, value2, "uri");
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

        public Criteria andPolicyIdIsNull() {
            addCriterion("policy_id is null");
            return (Criteria) this;
        }

        public Criteria andPolicyIdIsNotNull() {
            addCriterion("policy_id is not null");
            return (Criteria) this;
        }

        public Criteria andPolicyIdEqualTo(String value) {
            addCriterion("policy_id =", value, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdNotEqualTo(String value) {
            addCriterion("policy_id <>", value, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdGreaterThan(String value) {
            addCriterion("policy_id >", value, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdGreaterThanOrEqualTo(String value) {
            addCriterion("policy_id >=", value, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdLessThan(String value) {
            addCriterion("policy_id <", value, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdLessThanOrEqualTo(String value) {
            addCriterion("policy_id <=", value, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdLike(String value) {
            addCriterion("policy_id like", value, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdNotLike(String value) {
            addCriterion("policy_id not like", value, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdIn(List<String> values) {
            addCriterion("policy_id in", values, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdNotIn(List<String> values) {
            addCriterion("policy_id not in", values, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdBetween(String value1, String value2) {
            addCriterion("policy_id between", value1, value2, "policyId");
            return (Criteria) this;
        }

        public Criteria andPolicyIdNotBetween(String value1, String value2) {
            addCriterion("policy_id not between", value1, value2, "policyId");
            return (Criteria) this;
        }

        public Criteria andRuleIdIsNull() {
            addCriterion("rule_id is null");
            return (Criteria) this;
        }

        public Criteria andRuleIdIsNotNull() {
            addCriterion("rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andRuleIdEqualTo(String value) {
            addCriterion("rule_id =", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotEqualTo(String value) {
            addCriterion("rule_id <>", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThan(String value) {
            addCriterion("rule_id >", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThanOrEqualTo(String value) {
            addCriterion("rule_id >=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThan(String value) {
            addCriterion("rule_id <", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThanOrEqualTo(String value) {
            addCriterion("rule_id <=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLike(String value) {
            addCriterion("rule_id like", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotLike(String value) {
            addCriterion("rule_id not like", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdIn(List<String> values) {
            addCriterion("rule_id in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotIn(List<String> values) {
            addCriterion("rule_id not in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdBetween(String value1, String value2) {
            addCriterion("rule_id between", value1, value2, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotBetween(String value1, String value2) {
            addCriterion("rule_id not between", value1, value2, "ruleId");
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

        public Criteria andBlockIsNull() {
            addCriterion("block is null");
            return (Criteria) this;
        }

        public Criteria andBlockIsNotNull() {
            addCriterion("block is not null");
            return (Criteria) this;
        }

        public Criteria andBlockEqualTo(String value) {
            addCriterion("block =", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockNotEqualTo(String value) {
            addCriterion("block <>", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockGreaterThan(String value) {
            addCriterion("block >", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockGreaterThanOrEqualTo(String value) {
            addCriterion("block >=", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockLessThan(String value) {
            addCriterion("block <", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockLessThanOrEqualTo(String value) {
            addCriterion("block <=", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockLike(String value) {
            addCriterion("block like", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockNotLike(String value) {
            addCriterion("block not like", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockIn(List<String> values) {
            addCriterion("block in", values, "block");
            return (Criteria) this;
        }

        public Criteria andBlockNotIn(List<String> values) {
            addCriterion("block not in", values, "block");
            return (Criteria) this;
        }

        public Criteria andBlockBetween(String value1, String value2) {
            addCriterion("block between", value1, value2, "block");
            return (Criteria) this;
        }

        public Criteria andBlockNotBetween(String value1, String value2) {
            addCriterion("block not between", value1, value2, "block");
            return (Criteria) this;
        }

        public Criteria andBlockInfoIsNull() {
            addCriterion("block_info is null");
            return (Criteria) this;
        }

        public Criteria andBlockInfoIsNotNull() {
            addCriterion("block_info is not null");
            return (Criteria) this;
        }

        public Criteria andBlockInfoEqualTo(String value) {
            addCriterion("block_info =", value, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoNotEqualTo(String value) {
            addCriterion("block_info <>", value, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoGreaterThan(String value) {
            addCriterion("block_info >", value, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoGreaterThanOrEqualTo(String value) {
            addCriterion("block_info >=", value, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoLessThan(String value) {
            addCriterion("block_info <", value, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoLessThanOrEqualTo(String value) {
            addCriterion("block_info <=", value, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoLike(String value) {
            addCriterion("block_info like", value, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoNotLike(String value) {
            addCriterion("block_info not like", value, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoIn(List<String> values) {
            addCriterion("block_info in", values, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoNotIn(List<String> values) {
            addCriterion("block_info not in", values, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoBetween(String value1, String value2) {
            addCriterion("block_info between", value1, value2, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andBlockInfoNotBetween(String value1, String value2) {
            addCriterion("block_info not between", value1, value2, "blockInfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoIsNull() {
            addCriterion("alertinfo is null");
            return (Criteria) this;
        }

        public Criteria andAlertinfoIsNotNull() {
            addCriterion("alertinfo is not null");
            return (Criteria) this;
        }

        public Criteria andAlertinfoEqualTo(String value) {
            addCriterion("alertinfo =", value, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoNotEqualTo(String value) {
            addCriterion("alertinfo <>", value, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoGreaterThan(String value) {
            addCriterion("alertinfo >", value, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoGreaterThanOrEqualTo(String value) {
            addCriterion("alertinfo >=", value, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoLessThan(String value) {
            addCriterion("alertinfo <", value, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoLessThanOrEqualTo(String value) {
            addCriterion("alertinfo <=", value, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoLike(String value) {
            addCriterion("alertinfo like", value, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoNotLike(String value) {
            addCriterion("alertinfo not like", value, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoIn(List<String> values) {
            addCriterion("alertinfo in", values, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoNotIn(List<String> values) {
            addCriterion("alertinfo not in", values, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoBetween(String value1, String value2) {
            addCriterion("alertinfo between", value1, value2, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andAlertinfoNotBetween(String value1, String value2) {
            addCriterion("alertinfo not between", value1, value2, "alertinfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoIsNull() {
            addCriterion("proxy_info is null");
            return (Criteria) this;
        }

        public Criteria andProxyInfoIsNotNull() {
            addCriterion("proxy_info is not null");
            return (Criteria) this;
        }

        public Criteria andProxyInfoEqualTo(String value) {
            addCriterion("proxy_info =", value, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoNotEqualTo(String value) {
            addCriterion("proxy_info <>", value, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoGreaterThan(String value) {
            addCriterion("proxy_info >", value, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoGreaterThanOrEqualTo(String value) {
            addCriterion("proxy_info >=", value, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoLessThan(String value) {
            addCriterion("proxy_info <", value, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoLessThanOrEqualTo(String value) {
            addCriterion("proxy_info <=", value, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoLike(String value) {
            addCriterion("proxy_info like", value, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoNotLike(String value) {
            addCriterion("proxy_info not like", value, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoIn(List<String> values) {
            addCriterion("proxy_info in", values, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoNotIn(List<String> values) {
            addCriterion("proxy_info not in", values, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoBetween(String value1, String value2) {
            addCriterion("proxy_info between", value1, value2, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andProxyInfoNotBetween(String value1, String value2) {
            addCriterion("proxy_info not between", value1, value2, "proxyInfo");
            return (Criteria) this;
        }

        public Criteria andCharactersIsNull() {
            addCriterion("characters is null");
            return (Criteria) this;
        }

        public Criteria andCharactersIsNotNull() {
            addCriterion("characters is not null");
            return (Criteria) this;
        }

        public Criteria andCharactersEqualTo(String value) {
            addCriterion("characters =", value, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersNotEqualTo(String value) {
            addCriterion("characters <>", value, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersGreaterThan(String value) {
            addCriterion("characters >", value, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersGreaterThanOrEqualTo(String value) {
            addCriterion("characters >=", value, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersLessThan(String value) {
            addCriterion("characters <", value, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersLessThanOrEqualTo(String value) {
            addCriterion("characters <=", value, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersLike(String value) {
            addCriterion("characters like", value, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersNotLike(String value) {
            addCriterion("characters not like", value, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersIn(List<String> values) {
            addCriterion("characters in", values, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersNotIn(List<String> values) {
            addCriterion("characters not in", values, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersBetween(String value1, String value2) {
            addCriterion("characters between", value1, value2, "characters");
            return (Criteria) this;
        }

        public Criteria andCharactersNotBetween(String value1, String value2) {
            addCriterion("characters not between", value1, value2, "characters");
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

        public Criteria andProtocolTypeIsNull() {
            addCriterion("protocol_type is null");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeIsNotNull() {
            addCriterion("protocol_type is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeEqualTo(String value) {
            addCriterion("protocol_type =", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeNotEqualTo(String value) {
            addCriterion("protocol_type <>", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeGreaterThan(String value) {
            addCriterion("protocol_type >", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeGreaterThanOrEqualTo(String value) {
            addCriterion("protocol_type >=", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeLessThan(String value) {
            addCriterion("protocol_type <", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeLessThanOrEqualTo(String value) {
            addCriterion("protocol_type <=", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeLike(String value) {
            addCriterion("protocol_type like", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeNotLike(String value) {
            addCriterion("protocol_type not like", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeIn(List<String> values) {
            addCriterion("protocol_type in", values, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeNotIn(List<String> values) {
            addCriterion("protocol_type not in", values, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeBetween(String value1, String value2) {
            addCriterion("protocol_type between", value1, value2, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeNotBetween(String value1, String value2) {
            addCriterion("protocol_type not between", value1, value2, "protocolType");
            return (Criteria) this;
        }

        public Criteria andWciIsNull() {
            addCriterion("wci is null");
            return (Criteria) this;
        }

        public Criteria andWciIsNotNull() {
            addCriterion("wci is not null");
            return (Criteria) this;
        }

        public Criteria andWciEqualTo(String value) {
            addCriterion("wci =", value, "wci");
            return (Criteria) this;
        }

        public Criteria andWciNotEqualTo(String value) {
            addCriterion("wci <>", value, "wci");
            return (Criteria) this;
        }

        public Criteria andWciGreaterThan(String value) {
            addCriterion("wci >", value, "wci");
            return (Criteria) this;
        }

        public Criteria andWciGreaterThanOrEqualTo(String value) {
            addCriterion("wci >=", value, "wci");
            return (Criteria) this;
        }

        public Criteria andWciLessThan(String value) {
            addCriterion("wci <", value, "wci");
            return (Criteria) this;
        }

        public Criteria andWciLessThanOrEqualTo(String value) {
            addCriterion("wci <=", value, "wci");
            return (Criteria) this;
        }

        public Criteria andWciLike(String value) {
            addCriterion("wci like", value, "wci");
            return (Criteria) this;
        }

        public Criteria andWciNotLike(String value) {
            addCriterion("wci not like", value, "wci");
            return (Criteria) this;
        }

        public Criteria andWciIn(List<String> values) {
            addCriterion("wci in", values, "wci");
            return (Criteria) this;
        }

        public Criteria andWciNotIn(List<String> values) {
            addCriterion("wci not in", values, "wci");
            return (Criteria) this;
        }

        public Criteria andWciBetween(String value1, String value2) {
            addCriterion("wci between", value1, value2, "wci");
            return (Criteria) this;
        }

        public Criteria andWciNotBetween(String value1, String value2) {
            addCriterion("wci not between", value1, value2, "wci");
            return (Criteria) this;
        }

        public Criteria andWsiIsNull() {
            addCriterion("wsi is null");
            return (Criteria) this;
        }

        public Criteria andWsiIsNotNull() {
            addCriterion("wsi is not null");
            return (Criteria) this;
        }

        public Criteria andWsiEqualTo(String value) {
            addCriterion("wsi =", value, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiNotEqualTo(String value) {
            addCriterion("wsi <>", value, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiGreaterThan(String value) {
            addCriterion("wsi >", value, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiGreaterThanOrEqualTo(String value) {
            addCriterion("wsi >=", value, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiLessThan(String value) {
            addCriterion("wsi <", value, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiLessThanOrEqualTo(String value) {
            addCriterion("wsi <=", value, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiLike(String value) {
            addCriterion("wsi like", value, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiNotLike(String value) {
            addCriterion("wsi not like", value, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiIn(List<String> values) {
            addCriterion("wsi in", values, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiNotIn(List<String> values) {
            addCriterion("wsi not in", values, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiBetween(String value1, String value2) {
            addCriterion("wsi between", value1, value2, "wsi");
            return (Criteria) this;
        }

        public Criteria andWsiNotBetween(String value1, String value2) {
            addCriterion("wsi not between", value1, value2, "wsi");
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