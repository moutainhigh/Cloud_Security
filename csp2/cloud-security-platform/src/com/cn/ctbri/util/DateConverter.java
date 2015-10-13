package com.cn.ctbri.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter {
	/**
	 * 把字符串转换成Date
	 * value是字符串
	 * type是Date.class
	 */
	public Object convert(Class type, Object value) {
		// 判断目标类型是否为Date
		if(type != java.util.Date.class) {
			return null;
		}
		// 判断值是否为字符串类型
		if(!(value instanceof String)) {
			return null;
		}
		String s = (String)value;
		
		// 把字符串转换成Date类型
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
