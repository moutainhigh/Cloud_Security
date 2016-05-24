package com.cn.ctbri.southapi.adapter.config.waf.nsfocus;

import java.util.List;

public class WafLog{
	private String item;
	private String tag;
	private String dboptSql;
	private String matchReg;
	private List<Trans> trans;
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDboptSql() {
		return dboptSql;
	}
	public void setDboptSql(String dboptSql) {
		this.dboptSql = dboptSql;
	}
	public String getMatchReg() {
		return matchReg;
	}
	public void setMatchReg(String matchReg) {
		this.matchReg = matchReg;
	}
	public List<Trans> getTrans() {
		return trans;
	}
	public void setTrans(List<Trans> trans) {
		this.trans = trans;
	}
	
}
