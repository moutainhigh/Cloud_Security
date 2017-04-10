package com.cn.ctbri.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

import com.cn.ctbri.common.InternalWorker;
import com.cn.ctbri.listener.CspContextListener;

import net.sf.json.JSONObject;

public class Common {

	/**
	 * 校验日期是否小于当前日期
	 * @param date
	 * @return
	 */
	public static boolean isSameDate(Date date) {
		 Calendar cal1 = Calendar.getInstance();
	       cal1.setTime(new Date());
	       Calendar cal2 = Calendar.getInstance();
	       cal2.setTime(date);
	       boolean isSameYear = cal1.get(Calendar.YEAR) < cal2
	               .get(Calendar.YEAR);
	       boolean isSameMonth = cal1.get(Calendar.MONTH)+1 < cal2.get(Calendar.MONTH)+1;
	       boolean isSameDate = cal1.get(Calendar.DAY_OF_MONTH) < cal2
	                       .get(Calendar.DAY_OF_MONTH);
	       
	       boolean isSameYear1 = cal1.get(Calendar.YEAR) == cal2
	               .get(Calendar.YEAR);
	       boolean isSameMonth1 = cal1.get(Calendar.MONTH)+1 == cal2.get(Calendar.MONTH)+1;
	       boolean isSameDate1 = cal1.get(Calendar.DAY_OF_MONTH) == cal2
	                       .get(Calendar.DAY_OF_MONTH);
	       
	       if(isSameYear){
	    	   return isSameYear;
	       }

	       if(isSameYear1){
	    	   if(isSameMonth){
	    		   if(isSameDate){
	    			   return isSameDate;
	    		   }
	    		   return isSameMonth;
	    	   }
	    	  
	    	   if(isSameMonth1){
	    		   if(isSameDate){
	    			   return isSameDate;
	    		   }
	    		   if(isSameDate1){
		    		  
	    			   boolean isSameHour = cal1.get(Calendar.HOUR_OF_DAY) < cal2
	    					   .get(Calendar.HOUR_OF_DAY);
	    			   
	    			   boolean isSameHour1 = cal1.get(Calendar.HOUR_OF_DAY) == cal2
	    					   .get(Calendar.HOUR_OF_DAY);
	    			   boolean isSameMinute = cal1.get(Calendar.MINUTE) < cal2
							   .get(Calendar.MINUTE);
	    			   
	    			   boolean isSameMinute1 = cal1.get(Calendar.MINUTE) == cal2
	    					   .get(Calendar.MINUTE);
	    			   
	    			   
					   if(isSameHour){
						   if(isSameMinute){
							   return isSameMinute;
						   }
						   return isSameHour;
					   }
					   if(isSameHour1){
						   if(isSameMinute){
							   return isSameMinute;
						   }
						   if(isSameMinute1){
							   boolean isSameSecond = cal1.get(Calendar.SECOND) < cal2
									   .get(Calendar.SECOND);
							   return isSameSecond;
						   }
						   
					   }
		    	   }
	    	  }
	       }
	      return false;
	   }

	    /**
	     * 校验url是否有效
	     * @param targetURL
	     * @return
	     */
		public static boolean urlCheck(String targetURL) {
			
			String strRegex = "^((http|https)\\://)?([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"   
			        + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-04][0-9]|[0-1]{1}[0-9]{"   
			        + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"   
			        + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"   
			        + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"   
			        + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"   
			        + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"   
			        + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
				Pattern pat = Pattern.compile(strRegex);
				if(!pat.matcher(targetURL).matches()){
					return true;
				}
			return false;
		}
	public static void successCodeAndMessage(JSONObject json){
		json.put("code",CspContextListener.allPropertisMap.get("successCode"));
		json.put("message", CspContextListener.allPropertisMap.get("successMessage"));
	}
	public static void failCodeAndMessage(JSONObject json){
		json.put("code",CspContextListener.allPropertisMap.get("failCode"));
		json.put("message", CspContextListener.allPropertisMap.get("failMessage"));
	}
	public static void paramErrCodeAndMessage(JSONObject json){
		json.put("code",CspContextListener.allPropertisMap.get("paramErrCode"));
		json.put("message", CspContextListener.allPropertisMap.get("paramErrMessage"));
	}
	public static void tokenInvalidCodeAndMessage(JSONObject json){
		json.put("code",CspContextListener.allPropertisMap.get("failCode"));
		json.put("message", CspContextListener.allPropertisMap.get("failMessage"));
	}
	public static void permissionDeniedCodeAndMessage(JSONObject json){
		json.put("code",CspContextListener.allPropertisMap.get("permissionDeniedCode"));
		json.put("message", CspContextListener.allPropertisMap.get("permissionDeniedMessage"));
	}
	public static void expiredCodeAndMessage(JSONObject json){
		json.put("code",CspContextListener.allPropertisMap.get("expiredCode"));
		json.put("message", CspContextListener.allPropertisMap.get("expiredMessage"));
	}
	public static void usedUpCodeCodeAndMessage(JSONObject json){
		json.put("code",CspContextListener.allPropertisMap.get("usedUpCode"));
		json.put("message", CspContextListener.allPropertisMap.get("usedUpMessage"));
	}
}
