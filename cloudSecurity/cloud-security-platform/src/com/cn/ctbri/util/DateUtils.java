package com.cn.ctbri.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期转换
 * 邓元元
 * 2014/3/11
 */
public class DateUtils {
	/**将日期类型转换成Date类型*/
	public static Date stringToDate11(String sDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateFormat.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**将日期类型转换成String类型*/
	public static String dateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
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
			date = dateFormat.parse(sDate);
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
	
	/**
	 * 返回两个日期的 日期差，以天为单位，Date格式：yyyy-MM-dd hh:mm:ss
	 * @param date1
	 * @param date2
	 * @return
	 */
	public final static long getSurplusDays(Date date1,Date date2){
		//将转为String类型，格式：yyyy-MM-dd
		String strDate1 = dateToString(date1);
		String strDate2 = dateToString(date2);
		
		//将yyyy-MM-dd格式的String转为Date并比较，返回两个日期相差的天数
		Date compareDate1 = stringToDate(strDate1);
		Date compareDate2 = stringToDate(strDate2);
		
		return (compareDate2.getTime()-compareDate1.getTime())/(1000*60*60*24);
		
	}
}
