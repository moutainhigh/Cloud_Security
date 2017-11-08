package com.cn.ctbri.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

public class MapUtil {
	
	/**
	 * 
	 * 功能描述： 根据IP获取经纬度坐标
	 * 参数描述：   
	 *@date:2016-8-16下午4:47:18
	 * 返回值    ：  
	 * 异        常：
	 */
	public static String getPositionByIp(String ip) {
		if(StringUtils.isEmpty(ip)){
			return null;
		}
		
		//String key="0af9068ad7b3a0dc01846710a61765d5";
		String httpUrl="http://www.ipadjust.net/api/";
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl +ip;
	    //System.out.println("aaaaaaa+++++++++");
	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        //connection.setRequestProperty("apikey",key);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	       // System.out.println("返回json"+result);
	        JSONObject json=JSONObject.fromObject(result);
	        int code=json.getInt("code");
	        String  location=json.getString("location");
	        if(code==1&&null!=location&&location.length()>0){
	        	String longitude=json.getString("lon");
	        	String latitude=json.getString("lat");
	        	if(StringUtils.isNotEmpty(longitude)&&StringUtils.isNotEmpty(latitude)){
	        		try{
	        			Double.valueOf(longitude);
	        			Double.valueOf(latitude);
	        		}catch (Exception e) {
	        			return null;
	        		}
	        		return longitude+","+latitude;
	        	}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	public static String getCountryByPosition(String longitude,String latitude) {
		if(StringUtils.isEmpty(longitude)|| StringUtils.isEmpty(latitude)){
			return null;
		}
//		String httpUrl="http://api.map.baidu.com/geocoder/v2/?ak=BBb222f348ecadcfdb9278850a5f0411&location=37.31514,-122.010662&output=json&pois=1";
		String httpUrl="http://api.map.baidu.com/geocoder/v2/?ak=BBb222f348ecadcfdb9278850a5f0411&location="+latitude+","+longitude+"&output=json&pois=1";
//		String httpUrl="http://api.map.baidu.com/geocoder/v2/?ak=BBb222f348ecadcfdb9278850a5f0411&callback=renderReverse&location=39.983424,116.322987&output=json&pois=1";
		BufferedReader reader = null;
		 String result = null;
		 StringBuffer sbf = new StringBuffer();
		 try {
		        URL url = new URL(httpUrl);
		        HttpURLConnection connection = (HttpURLConnection) url
		                .openConnection();
		        connection.setRequestMethod("GET");
		        connection.connect();
		        InputStream is = connection.getInputStream();
		        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        String strRead = null;
		        while ((strRead = reader.readLine()) != null) {
		            sbf.append(strRead);
		            sbf.append("\r\n");
		        }
		        reader.close();
		        result = sbf.toString();
		        //System.out.println(result);
		        JSONObject json=JSONObject.fromObject(result);
		        int status=json.getInt("status");
		        if(status==0){
		        	JSONObject obj=json.getJSONObject("result");
		        	if(null!=obj){
		        		JSONObject addressComponent=(JSONObject) obj.get("addressComponent");
		        		String country=addressComponent.getString("country");
		        		String province=addressComponent.getString("province");
		        		return country+province;
		        	}
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		 return null;
	}
	/**
	 * 
	 * 功能描述： 
	 * 参数描述：   
	 *@date:2016-8-18上午11:43:29
	 * 返回值    ：  
	 * 异        常：
	 */					 
	public static String getPositionByAccountIP(String ip){

		if(StringUtils.isEmpty(ip)){
			return null;
		}
		String httpUrl="http://www.bunian.cn/gongjv/ip/?ip="+ip;
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	        JSONObject json=JSONObject.fromObject(result);
	        String  longitude=json.getString("longitude");
	        String latitude=json.getString("latitude");
	        if(StringUtils.isNotEmpty(longitude)&&StringUtils.isNotEmpty(latitude)){	        	
	        	return longitude+","+latitude;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
