package com.sinosoft;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class teateate {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-5);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(calendar.getTime().getClass());
		System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));
		System.out.println("当前的时间：" + df.format(new Date()));
	}
}
