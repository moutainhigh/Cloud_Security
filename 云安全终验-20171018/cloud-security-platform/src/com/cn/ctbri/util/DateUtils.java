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
	 * 计算两时间之间的毫秒数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getMsByDays(Date beginDate, Date endDate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		try {
			beginDate=sdf.parse(sdf.format(beginDate));  
			endDate=sdf.parse(sdf.format(endDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(beginDate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(endDate);    
        long time2 = cal.getTimeInMillis();         
        long between_ms=time2-time1;  
            
        return between_ms; 

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
    
    /**
     * 得到下个月的日期
     * @param d
     * @return
     */
    public static Date getDayAfterMonth(Date d){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.MONTH,1); 
         date = calendar.getTime();  
         return date;
    }    
    /**
     * 得到当前日期的十分钟后的时间
     * @param d
     * @return
     */
    public static Date getDateAfter10Mins(Date d){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.MINUTE,10); 
         date = calendar.getTime();  
         return date;
    }
    
    /**
     * 得到当前日期的下一年
     * @param d
     * @return
     */
    public static Date getDateAfterOneYear(Date d){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.YEAR,1); 
         date = calendar.getTime();  
         return date;
    }
	
    /**
     * 得到后几个月的日期
     * @param d
     * @return
     */
    public static Date getDateAfterMonths(Date d, int count){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.MONTH,count); 
         date = calendar.getTime();  
         return date;
    }
    /**
     * 
	 * 功能描述： 得到上一个月的日期
	 * 参数描述：   date某个时间
	 *@date:2016-8-3下午2:02:05
	 * 返回值    ：  上一个月当天的日期
	 * 异        常：
     */
    public static Date getBeforeMonthDate(Date date){
    	if(date!=null){
    		return getDateAfterMonths(date,-1);
    	}
    	return null;
    }
    /**
     * 获取当前日期一个小时前日期
     * @param date
     * @return
     */
    public static Date getDateBeforeOneHour(Date d){
    	Date date = d;
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.HOUR,-1); 
        date = calendar.getTime();  
        return date;
    }
    
    /**
     * 得到当前日期的上一年
     * @param d
     * @return
     */
    public static Date getDateBeforeOneYear(Date d){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.YEAR,-1); 
         date = calendar.getTime();  
         return date;
    }
    /**
     * 获取当前日期24小时后的日期
     * @param date
     * @return
     */
    public static Date getDateAfterHour(Date d){
    	Date date = d;
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.HOUR,24); 
        date = calendar.getTime();  
        return date;
    }
    
    /**
     * 获取当前日期24小时前的日期
     * @param date
     * @return
     */
    public static Date getDateBeforeHour(Date d){
    	Date date = d;
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.HOUR,-24); 
        date = calendar.getTime();  
        return date;
    }
    /**
     * 获取当前日期7天前的日期
     * @param date
     * @return
     */
    public static Date getDateBeforeDay(Date d){
    	Date date = d;
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DATE,-7); 
        date = calendar.getTime();  
        return date;
    }
    
    /**
     * 得到当前日期的十分钟后的时间
     * @param d
     * @return
     */
    public static int getMonthSpace(Date d1,Date d2){
         Calendar calendar1 = Calendar.getInstance();  
         Calendar calendar2 = Calendar.getInstance();  
         calendar1.setTime(d1);  
         calendar2.setTime(d2);  
         int result = calendar2.get(Calendar.MONTH)-calendar1.get(Calendar.MONTH); 
         return result == 0 ? 1 : Math.abs(result);
    }
}
