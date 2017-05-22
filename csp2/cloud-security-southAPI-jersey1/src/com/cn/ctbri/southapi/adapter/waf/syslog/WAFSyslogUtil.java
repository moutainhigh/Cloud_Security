package com.cn.ctbri.southapi.adapter.waf.syslog;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogUtil {
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/*
	 * Description : 
	 * Params : 
	 */
	public static String decode(String strContent) {
		if ( strContent == null || "".equals(strContent) ) return null;
		
		byte[] temp = Base64.decodeBase64(strContent.getBytes());
		if ( temp == null ) return null;
		
		
		return new String(temp);
	}


	/*
	 * Description : 
	 * Params : 
	 */
	public static byte[] decode(final byte[] bytes) {
		return Base64.decodeBase64(bytes);
	}


	/*
	 * Description : 
	 * Params : 
	 */
	public static String encode(final byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}

	/*
	 * Description : 
	 * Params : 
	 */
	public static int covenStrToInteger(String content) {
		if ( content == null || "".equals(content) ) 
			throw new NullPointerException("Param is null or invalid!");
		
		return Integer.parseInt(content);
	}
	
	
	/*
	 * Description : 
	 * Params : fomat : "yyyy-MM-dd " 
	 */
	public static Date conventStrToData(String content) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			return sdf.parse(content);
		} catch (Exception e) {
			return null;
		}
	}
	/*
	 * Description : 
	 * Params : fomat : "yyyy-MM-dd " 
	 */
	public static Date conventStrToData(String content,String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(content);
		} catch (Exception e) {
			return null;
		}
	}

}
