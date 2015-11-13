package com.cn.ctbri.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期转换
 * 邓元元
 * 2015/1/1
 */
public class DateUtils {
	/**将日期类型转换成String类型*/
	public static String dateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sDate = "";
		if(date!=null && !date.equals("")){
			sDate = dateFormat.format(date);
		}
		return sDate;
	}
	
	/**将日期转换成年月日格式*/
	public static String dateToDate(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
	/**将String类型转换成日期类型*/
	public static Date stringToDate(String sDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**将String类型转换成日期类型 年月日时分秒*/
	public static Date stringToDateNYRSFM(String sDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			if(sDate!=null && !sDate.equals("")){
				date = dateFormat.parse(sDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**将日期类型转换成String类型*/
	public static String dateToString1(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(date);
	}
	/**将String类型转换成日期类型*/
	public static Date stringToDate1(String sDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = null;
		try {
			date = dateFormat.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
}
