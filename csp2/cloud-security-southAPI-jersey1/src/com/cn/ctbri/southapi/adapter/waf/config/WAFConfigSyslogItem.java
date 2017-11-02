package com.cn.ctbri.southapi.adapter.waf.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFConfigSyslogItem {
	public String item;
	public String tag;
	public String sqlForDB;
	public String matchRegx;
	
	public String sqlInsertSentence = null;
	public String sqlFieldNames[] = null;
	public String sqlValueNames[] = null;
	
	public List<WAFConfigSyslogItemPre> listWAFConfigSyslogItemPre = new ArrayList<WAFConfigSyslogItemPre>();

	public HashMap<String,String> mapWAFConfigSyslogItemTrans = new HashMap<String,String>();//name : express	
	public List<WAFConfigSyslogItemTrans> listWAFConfigSyslogItemTrans = new ArrayList<WAFConfigSyslogItemTrans>();//String : id

	
	@SuppressWarnings("rawtypes")
	public void buildSyslogItemTrans() {
		if ( listWAFConfigSyslogItemTrans == null ) return;
		
		Iterator it = listWAFConfigSyslogItemTrans.iterator();
	      while ( it.hasNext() ){
	    	  WAFConfigSyslogItemTrans wafConfigSyslogItemTrans = (WAFConfigSyslogItemTrans)it.next();
	    	  
	    	  if ( wafConfigSyslogItemTrans == null || wafConfigSyslogItemTrans.getName() == null || "".equals(wafConfigSyslogItemTrans.getName())) continue;
	    	  mapWAFConfigSyslogItemTrans.put(wafConfigSyslogItemTrans.getName(), wafConfigSyslogItemTrans.getExpress());
	      }     

	}
	
	public String getSyslogItemTransByName(String name) {
		return mapWAFConfigSyslogItemTrans.get(name);
	}
	
	
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

	public String getSqlForDB() {
		return sqlForDB;
	}

	public void setSqlForDB(String sqlForDB) {
		this.sqlForDB = sqlForDB;
	}

	public String getMatchRegx() {
		return matchRegx;
	}

	public void setMatchRegx(String matchRegx) {
		this.matchRegx = matchRegx;
	}

	public String getSqlInsertSentence() {
		return sqlInsertSentence;
	}

	public void setSqlInsertSentence(String sqlInsertSentence) {
		this.sqlInsertSentence = sqlInsertSentence;
	}


	public String[] getSqlFieldNames() {
		return sqlFieldNames;
	}

	public void setSqlFieldNames(String[] sqlFieldNames) {
		this.sqlFieldNames = sqlFieldNames;
	}

	public String[] getSqlValueNames() {
		return sqlValueNames;
	}

	public void setSqlValueNames(String[] sqlValueNames) {
		this.sqlValueNames = sqlValueNames;
	}

	public List<WAFConfigSyslogItemTrans> getListWAFConfigSyslogItemTrans() {
		return listWAFConfigSyslogItemTrans;
	}

	public void setListWAFConfigSyslogItemTrans(
			List<WAFConfigSyslogItemTrans> listWAFConfigSyslogItemTrans) {
		this.listWAFConfigSyslogItemTrans = listWAFConfigSyslogItemTrans;
	}

	public HashMap<String, String> getMapWAFConfigSyslogItemTrans() {
		return mapWAFConfigSyslogItemTrans;
	}

	public void setMapWAFConfigSyslogItemTrans(
			HashMap<String, String> mapWAFConfigSyslogItemTrans) {
		this.mapWAFConfigSyslogItemTrans = mapWAFConfigSyslogItemTrans;
	}

	public List<WAFConfigSyslogItemPre> getListWAFConfigSyslogItemPre() {
		return listWAFConfigSyslogItemPre;
	}

	public void setListWAFConfigSyslogItemPre(
			List<WAFConfigSyslogItemPre> listWAFConfigSyslogItemPre) {
		this.listWAFConfigSyslogItemPre = listWAFConfigSyslogItemPre;
	}

	
	
}
