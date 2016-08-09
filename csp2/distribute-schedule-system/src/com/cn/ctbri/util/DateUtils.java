package com.cn.ctbri.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	/**
	 * 获取周期时间
	 * @param beginDate 开始时间
	 * @param endDate	结束时间
	 * @param scanType	扫描类型
	 * @return
	 */
	public static Date getOrderPeriods(String beginDate, String endDate, String scanType){
		//初始化
		int count = 0;
		Date begin_date = null;
		Date end_date = null;
		Date executeTime = null;
		
        String beginHour = beginDate.substring(11, 13);
        String beginMinute = beginDate.substring(14, 16);

        String endHour = endDate.substring(11, 13);
        String endMinute = endDate.substring(14, 16);
        
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟 
			begin_date = sdf.parse(beginDate);
			end_date = sdf.parse(endDate);
			
	        calBegin.setTime(begin_date);  
	        calEnd.setTime(end_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//周期为每天（00:10:00）
		Date taskTime = DateUtils.stringToDateNYRSFM(beginDate.substring(0, 10).concat(" 00:10:00"));
		if(scanType.equals("1")){  
            if(taskTime.compareTo(DateUtils.stringToDateNYRSFM(endDate))>0){
            	executeTime = null;
            }else if(taskTime.compareTo(DateUtils.stringToDateNYRSFM(beginDate))==0){
            	executeTime = taskTime;
            }else if(beginHour.equals("00")&&beginMinute.compareTo("10")<0){
            	executeTime = taskTime;
            }else{
            	executeTime = getAfterDate(taskTime,1);
            }
		}else if(scanType.equals("5")){ //周期为每周（每周一00:10:00）
			if(taskTime.compareTo(DateUtils.stringToDateNYRSFM(endDate))>0){
            	executeTime = null;
            }else if(taskTime.compareTo(DateUtils.stringToDateNYRSFM(beginDate))==0){
            	executeTime = taskTime;
            }else{
            	if(calBegin.get(Calendar.DAY_OF_WEEK) - 1 == 1){  
            		if(beginHour.equals("00")&&beginMinute.compareTo("10")<0){
                    	executeTime = taskTime;
                    }else{
                    	executeTime = getAfterDate(taskTime,7);
                    }
            	}else{
            		int n = 8 - (calBegin.get(Calendar.DAY_OF_WEEK) - 1);
            		executeTime = getAfterDate(taskTime,n);
            	}
            }
		}else if(scanType.equals("6")){ //周期为每月（1日00:10:00）
			if(taskTime.compareTo(DateUtils.stringToDateNYRSFM(endDate))>0){
            	executeTime = null;
            }else if(taskTime.compareTo(DateUtils.stringToDateNYRSFM(beginDate))==0){
            	executeTime = taskTime;
            }else{
            	if(calBegin.get(Calendar.DAY_OF_MONTH)  == 1){  
            		if(beginHour.equals("00")&&beginMinute.compareTo("10")<0){
                    	executeTime = taskTime;
                    }else{
                    	executeTime = getAfterMonth(taskTime);
                    }
            	}else{
            		executeTime = getAfterMonth(taskTime);
            	}
            }
		}
		return executeTime;
	}
	
	
	/**
     * 得到某个日期的后一天日期
     * @param d
     * @return
     */
    public static Date getAfterDate(Date d,int n){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.DAY_OF_MONTH,n);  
         date = calendar.getTime();  
         return date;
    }
    
    /**
     * 得到某个日期的后一天日期
     * @param d
     * @return
     */
    public static Date getAfterMonth(Date d){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.MONTH,1); 
         calendar.set(Calendar.DAY_OF_MONTH, 1);  
         date = calendar.getTime();  
         return date;
    }
    
    public static String nowDate(){
    	//获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        return temp;
    }
	
}
