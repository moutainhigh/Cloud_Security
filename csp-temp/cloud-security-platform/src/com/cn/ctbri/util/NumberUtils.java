package com.cn.ctbri.util;

public class NumberUtils {
	private static final String formater="%.1f";
	
	/**
	 * 根据formater格式将双精度浮点数格式化
	 * @param formater
	 * @param num
	 * @return
	 */
	public static String getFormaterNumber(String formater,double num){
		String result = String .format(formater,num);
		return result;
	}
	public static String getPointAfterOneNumber(double num){
		return getFormaterNumber(formater,num);
	}
}
