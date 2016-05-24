package com.cn.ctbri.southapi.adapter.waf.nsfocus.syslog;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigDevice;
import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigSyslog;
import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigSyslogItem;

/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogDisposeFactoryNSFocus implements IWAFSyslogDisposeFactory {
	public final String DEFAULT_NSFOCUS_SYSLOG_CONTENT_REGX = "[ \t]";
	public final String DEFAULT_NSFOCUS_SYSLOG_ITEM_REGX = ":";
	public final String DEFAULT_NSFOCUS_SYSLOG_TAG = "tag";
	public final String DEFAULT_NSFOCUS_SYSLOG_WAFSTAT = "waf_log_wafstat";
	public final String DEFAULT_NSFOCUS_SYSLOG_WAFSTAT_CPU = "cpu";
	public final String DEFAULT_NSFOCUS_SYSLOG_WAFSTAT_MEM = "mem";

	public WAFSyslogDBHelper wafSyslogDBHelper;
	public WAFSyslogHostParam wafSyslogHostParam;
	

	public boolean disposeSyslogEvent(String message,WAFSyslogHostParam wafSyslogHostParam,WAFSyslogDBHelper wafSyslogDBHelper) {
		this.wafSyslogDBHelper = wafSyslogDBHelper;
		this.wafSyslogHostParam = wafSyslogHostParam;
		
		WAFConfigSyslog wafConfigSyslog = wafSyslogHostParam.getWafConfigSyslog();
		
		//Get Syslog Message Tag
		String tagValue = getSyslogMessageTagRegex(message,wafConfigSyslog);		
		WAFConfigSyslogItem item = getWAFConfigSyslogItem(tagValue,wafConfigSyslog);
		if ( item == null ) return false;
		
		//Get Syslog Message Key/Value
		HashMap<String,String> mapSyslogKeyValue = disposeSyslogMessageRegex(message,item) ;
		if ( mapSyslogKeyValue == null ) return false;
		
		
		//CPU,Memory Performance Information
		if ( DEFAULT_NSFOCUS_SYSLOG_WAFSTAT.equalsIgnoreCase(tagValue) ) {
			WAFConfigDevice wafConfigDevice = wafSyslogHostParam.getWafConfigDevice();
			
			return updateWAFConfigDevicePerfInfo(wafConfigDevice,mapSyslogKeyValue);
		}		
	
		//Get SQL Field List
		String arrayFields[] = item.getSqlFieldNames();
		if ( arrayFields == null ) {
			arrayFields = getSQLFieldArray(item.getSqlForDB());
			if ( arrayFields == null || arrayFields.length <= 0 ) return false;
			//Store to Item
			item.setSqlFieldNames(arrayFields);
		}
		
		//Get SQL Value NameList		
		String arrayValueNames[] = item.getSqlValueNames();
		if ( arrayValueNames == null ) {
			arrayValueNames = getSQLValueArray(item.getSqlForDB());
			if ( arrayValueNames == null || arrayValueNames.length <= 0 ) return false;
			//Store to Item
			item.setSqlValueNames(arrayValueNames);
		}				
		
		//Get SQL String
		String sql = item.getSqlInsertSentence();
		if ( sql == null ) {
			sql = getSQLSentence(item.getSqlForDB(),arrayFields.length);
			item.setSqlInsertSentence(sql);
		}
		
		//Get PreparedStatement
		PreparedStatement ps = wafSyslogDBHelper.getPreparedStatement(sql);
		disposePreparedStatement(arrayValueNames,mapSyslogKeyValue,item,ps);
		
		wafSyslogDBHelper.executePreparedStatement(ps);
				
		return true;
	}
	
	
	
	private boolean updateWAFConfigDevicePerfInfo(WAFConfigDevice wafConfigDevice,HashMap<String,String> mapSyslogKeyValue) {
		
		try {
			String cpu = mapSyslogKeyValue.get(DEFAULT_NSFOCUS_SYSLOG_WAFSTAT_CPU);
			String memory = mapSyslogKeyValue.get(DEFAULT_NSFOCUS_SYSLOG_WAFSTAT_MEM);
			
			wafConfigDevice.setCpuPerf(Integer.parseInt(cpu));
			wafConfigDevice.setMemoryPerf(Integer.parseInt(memory));
			
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	private String getSyslogMessageTagRegex(String message,WAFConfigSyslog wafConfigSyslog) {
		String syslogRegTag = wafConfigSyslog.getSyslogRegTag();
		if ( syslogRegTag == null || "".equals(syslogRegTag)) return null;
		
		try {
			//Get Tag		
			Pattern p = Pattern.compile(syslogRegTag);
			Matcher m = p.matcher(message);
			while (m.find()) {
				String tagValue = m.group();
				tagValue = tagValue.trim();
				 String temp[] = tagValue.split(DEFAULT_NSFOCUS_SYSLOG_ITEM_REGX);
		    	 if ( temp == null ) continue;
		    	 
		    	 String value = temp[1];
		    	 if ( value == null || "".equals(value) ) return null;
		    	 
		    	 return value.trim();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	private HashMap<String,String> disposeSyslogMessageRegex(String message,WAFConfigSyslogItem item) {
		String regex = item.getMatchRegx();
		if ( regex == null || "".equals(regex)) return null;
		
			 
		try { 
			HashMap<String,String> mapSyslogKeyValue = new HashMap<String,String>();//key : value

			//Get Message Field		
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(message);
	        while(m.find()) {
		    	 String strFieldValue =  m.group();
		    	 strFieldValue = strFieldValue.trim();
		    	 if ( strFieldValue == null || "".equals(strFieldValue) ) continue;
		    	 
		    	 //String temp[] = strFieldValue.split(DEFAULT_NSFOCUS_SYSLOG_ITEM_REGX);
		    	 String temp[] = splitFieldAndValue(strFieldValue,DEFAULT_NSFOCUS_SYSLOG_ITEM_REGX);
		    	 if ( temp == null ) continue;
		    	 
		    	 String key = temp[0];
		    	 String value = temp[1];
		    	 
		    	 if ( key == null || value == null || "".equals(key)) continue;
		    	 
		    	 mapSyslogKeyValue.put(key, value);
		    }
	        
	        return mapSyslogKeyValue;
		} catch(Exception e) {
			return null;
		}
	}
	
	
	
	private String[] getSQLFieldArray(String sqlStr) {
		String temp = sqlStr;
		try {
			int nHead = temp.indexOf('(');
			int nTail = temp.indexOf(')');
			temp = temp.substring(nHead+1, nTail);

			return temp.split(",");
		} catch(Exception e) {
			return null;
		}		
	}
	
	
	private String[] getSQLValueArray(String sqlStr) {
		String temp = sqlStr;
		try {
			int nHead = temp.indexOf("VALUES(");
			temp = temp.substring(nHead+"VALUES".length()+1, temp.length()-1);

			return temp.split(",");
		} catch(Exception e) {
			return null;
		}		
	}
	
	private String getSQLSentence(String sql,int nLen) {
		String temp = sql;
		int nTail = temp.indexOf(')');
		temp = temp.substring(0, nTail+1);
		
		String sqlSentence = temp + " VALUES(?";
		for( int i=1; i<nLen; i++) sqlSentence += ",?";
		
		sqlSentence += ")";
		
		return sqlSentence;
	}
	
	
	private String[] splitFieldAndValue(String fieldValue,String split) {
		if ( fieldValue == null || split == null || "".endsWith(fieldValue) || "".equals(split)) return null;
		
		try { 
			String temp[] = new String[2]; 

			int nHead = fieldValue.indexOf(split);
			//int nTail = temp.indexOf(')');
			temp[0] = fieldValue.substring(0, nHead);
			temp[1] = fieldValue.substring(nHead+split.length(), fieldValue.length());

			return temp;
		} catch(Exception e) {
			return null;
		}		
	}

	
	
	private boolean disposePreparedStatement(String[] arrayValueNames,HashMap<String,String> mapSyslogKeyValue,WAFConfigSyslogItem item,PreparedStatement ps) {
		
		/*
		 * 1.遍历所有的SQL中的字段
		 * 2.找Syslog中对应的内容
		 * 3.检查Syslog中的内容是否需要进行处理：格式转换 or PreparedStatement 处理。
		 * 4.默认为字符串，不做处理
		 */
		try {
			for( int i=0; i<arrayValueNames.length; i++ ) {
				String valueName = arrayValueNames[i];
				
				disposePreparedStatementField(i+1,valueName,mapSyslogKeyValue,item,ps);		//ps : 1.....n		
			}
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	
	
	private boolean disposePreparedStatementField(int nIndex,String valueName,HashMap<String,String> mapSyslogKeyValue,WAFConfigSyslogItem item,PreparedStatement ps) {
		
		char tagChar = valueName.charAt(0);
		valueName = valueName.substring(1);
		String fieldContent = mapSyslogKeyValue.get(valueName);
		
		if (tagChar == '@') {
			return disposePreparedStatementFieldForQuote(nIndex,valueName,item,ps);
		} else if (tagChar == '#') {
			return disposePreparedStatementFieldForExpress(nIndex,valueName,fieldContent,item,ps);
		}

		return true;
	}

	
	private boolean disposePreparedStatementFieldForQuote(int nIndex,String fieldValue,WAFConfigSyslogItem item,PreparedStatement ps) {		
		try {
			WAFConfigDevice wafConfigDevice = wafSyslogHostParam.getWafConfigDevice();
			
			if ("ResourceID".equalsIgnoreCase(fieldValue)) {
				ps.setInt(nIndex, wafConfigDevice.getResourceID());
			} else if ("ResourceURI".equalsIgnoreCase(fieldValue)) {
				ps.setString(nIndex,(wafConfigDevice.getResourceURI() == null ? "" : wafConfigDevice.getResourceURI()));
			} else if ("WAFDevPhyIP".equalsIgnoreCase(fieldValue)) {
				ps.setString(nIndex,(wafConfigDevice.getDevicePhyIP() == null ? "" : wafConfigDevice.getDevicePhyIP()));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	
	
	private boolean disposePreparedStatementFieldForExpress(int nIndex,String fieldValue,String filedContent,WAFConfigSyslogItem item,PreparedStatement ps) {
		HashMap<String,String> mapWAFConfigSyslogItemTrans = item.getMapWAFConfigSyslogItemTrans();
		String express = null;
		
		try {
			/*
			 * 看看是什么类型，是否需要转换
			 */
			if ((null == mapWAFConfigSyslogItemTrans)
					|| null == (express = mapWAFConfigSyslogItemTrans
							.get(fieldValue))) {
				ps.setString(nIndex, (filedContent == null ? "" : filedContent));
			} else {
				if ("Integer.parseInt".equalsIgnoreCase(express)) {
					int nContent = -1;
					try {
						nContent = Integer.parseInt(filedContent); // 不论是否为空或者“”，依赖异常处理，取缺省-1值。
					} catch (Exception e) {

					}
					ps.setInt(nIndex, nContent);
				} else if ("StringtoDate".equalsIgnoreCase(express)) {
					try {
						java.sql.Timestamp time = java.sql.Timestamp.valueOf(filedContent);
						ps.setTimestamp(nIndex, time);
					} catch (Exception e) {						
						//Exception : fill origin string
						ps.setString(nIndex, (filedContent == null ? "" : filedContent));
					}
				} else if ("Base64toString".equalsIgnoreCase(express)) {
					String temp = null;
					try {
						temp = WAFSyslogUtil.decode(filedContent);
					} catch (Exception e) {

					}
					ps.setString(nIndex, (temp == null ? "" : temp));
				}
			}
		} catch(Exception e) {
			return false;
		}

		return true;
	}
	
	
	@SuppressWarnings("rawtypes")
	private WAFConfigSyslogItem getWAFConfigSyslogItem(String tagValue,WAFConfigSyslog wafConfigSyslog) {
		if ( tagValue == null || wafConfigSyslog == null ) return null;
		
		Iterator iter = wafConfigSyslog.mapWAFConfigSyslogItem.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			WAFConfigSyslogItem item = (WAFConfigSyslogItem)entry.getValue();
			
			if ( tagValue.equalsIgnoreCase(item.getTag()) ) return item;
			
		}
		return null;
	}

}
