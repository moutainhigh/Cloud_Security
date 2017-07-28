package com.cn.ctbri.common;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cn.ctbri.controller.WafController;
import com.cn.ctbri.util.DateUtils;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2017-07-11
 * 描        述：  解析接口
 * 版        本：  1.0
 */
public class WafAPIAnalysis {

	/**
     * 解析level统计信息
     * @param levelStr
     */
    public static Map getWafAlertLevelCount(String levelStr){
    	Map<String,Object> reMap = new HashMap<String,Object>();
    	try {
    		JSONObject obj = JSONObject.fromObject(levelStr);
    		int high = obj.getInt("HIGH");
    		int low = obj.getInt("LOW");
    		int mid = obj.getInt("MEDIUM");
    		reMap.put("high", high);
    		reMap.put("low", low);
    		reMap.put("mid", mid);
    		reMap.put("total", high+low+mid);
    		
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reMap;
    }

	public static Map analysisWafLogWebSecTimeCount(String eventStr) {
		Map<String,Object> reMap = new HashMap<String,Object>();
    	List<Object> arr = new ArrayList<Object>();
		List<Object> arra = new ArrayList<Object>();
    	try {
    		JSONObject jsonObj = new JSONObject().fromObject(eventStr);
    		JSONArray obj = jsonObj.getJSONArray("countList");
    		for (Object aObj : obj) {
    			JSONObject e = (JSONObject) aObj;
    			String time = e.getString("time");
    			int count = e.getInt("count");
    			arr.add(time);
				arra.add(count);
    		}	
			reMap.put("name", arr);
			reMap.put("value", arra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reMap;
	}
	
	public static List analysisWafLogWebSecTimeCountList(String eventStr) {
		List reList = new ArrayList();
    	try {
    		JSONObject obj = JSONObject.fromObject(eventStr);
    		JSONArray jsonArray = obj.getJSONArray("countList");
    		if(jsonArray!=null && jsonArray.size()>0){
    			for (int i = 0; i < jsonArray.size(); i++) {
    				Map<String,Object> newMap = new HashMap<String,Object>();
    				String object = jsonArray.getString(i);
			        JSONObject jsonObject = JSONObject.fromObject(object);
			        int count = jsonObject.getInt("count");
			        if(count!=0){
			        	String time = jsonObject.getString("time");
				        newMap.put("count", count);
				        newMap.put("time", time);
				        reList.add(newMap);
			        }
				}
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reList;
	}

	public static List getWafLogWebsecSrcIp(String reStr) {
		List reList = new ArrayList();
    	try {
    		JSONObject obj = JSONObject.fromObject(reStr);
    		JSONArray jsonArray = obj.getJSONArray("list");
    		if(jsonArray!=null && jsonArray.size()>0){
    			for (int i = 0; i < jsonArray.size(); i++) {
    				Map<String,Object> newMap = new HashMap<String,Object>();
    				String object = jsonArray.getString(i);
			        JSONObject jsonObject = JSONObject.fromObject(object);
			        
			        int count = jsonObject.getInt("count");
			        String dstIp = jsonObject.getString("srcIp");
			        newMap.put("count", count);
			        newMap.put("dstIp", dstIp);
			        reList.add(newMap);
				}
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reList;
	}
	
	
    /**
     * 解析安全事件类型统计信息
     * @param eventStr
     */
    public static Map getWafEventTypeCount(String eventStr){
    	Map<String,Object> reMap = new HashMap<String,Object>();
    	List<Object> arr = new ArrayList<Object>();
		List<Object> arra = new ArrayList<Object>();
		JSONArray json = new JSONArray();
    	try {
    		JSONArray obj = new JSONArray().fromObject(eventStr);
    		for (Object aObj : obj) {
    			JSONObject e = (JSONObject) aObj;
    			int count = e.getInt("count");
    			if(count!=0){
    				JSONObject jo = new JSONObject();
    				byte[] base64Bytes = e.getString("eventType").toString().getBytes();	
    				String eventType = new String(base64Bytes,"UTF-8");
    				arr.add(eventType);
    				arra.add(count);
    				jo.put("value", count);
    				jo.put("name", eventType);
    				json.add(jo);
    			}
    			
    		}	
			reMap.put("name", arr);
			reMap.put("value", arra);
			reMap.put("json", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reMap;
    }
    
    public static Map getWafEventTypeCountBase64(String eventStr){
    	Map<String,Object> reMap = new HashMap<String,Object>();
    	List<Object> arr = new ArrayList<Object>();
		List<Object> arra = new ArrayList<Object>();
		JSONArray json = new JSONArray();
    	try {
    		JSONArray obj = new JSONArray().fromObject(eventStr);
    		for (Object aObj : obj) {
    			JSONObject e = (JSONObject) aObj;
    			int count = e.getInt("count");
    			if(count!=0){
    				JSONObject jo = new JSONObject();
    				byte[] base64Bytes = Base64.decodeBase64(e.getString("eventType").toString().getBytes());	
    				String eventType = new String(base64Bytes,"UTF-8");
    				arr.add(eventType);
    				arra.add(count);
    				jo.put("value", count);
    				jo.put("name", eventType);
    				json.add(jo);
    			}
    			
    		}	
			reMap.put("name", arr);
			reMap.put("value", arra);
			reMap.put("json", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reMap;
    }

	public static Map getWafEventTypeCountInTime(String eventStr) {
		Map<String,Object> reMap = new HashMap<String,Object>();
    	List<Object> arr = new ArrayList<Object>();
		List<Object> arra = new ArrayList<Object>();
		JSONArray json = new JSONArray();
    	try {
    		JSONObject ob = JSONObject.fromObject(eventStr);
    		JSONArray obj = ob.getJSONArray("list");
    		for (Object aObj : obj) {
    			JSONObject e = (JSONObject) aObj;
    			int count = Integer.parseInt(e.getString("count"));
    			if(count!=0){
    				JSONObject jo = new JSONObject();
    				byte[] base64Bytes = Base64.decodeBase64(e.getString("eventType").toString().getBytes());	
    				String eventType = new String(base64Bytes,"UTF-8");
    				arr.add(eventType);
    				arra.add(count);
    				jo.put("value", count);
    				jo.put("name", eventType);
    				json.add(jo);
    			}
    			
    		}	
			reMap.put("name", arr);
			reMap.put("value", arra);
			reMap.put("json", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reMap;
	}
	
	public static Map getWafEventTypeCountInTimeNoDecode(String eventStr) {
		Map<String,Object> reMap = new HashMap<String,Object>();
    	List<Object> arr = new ArrayList<Object>();
		List<Object> arra = new ArrayList<Object>();
		JSONArray json = new JSONArray();
    	try {
    		JSONObject ob = JSONObject.fromObject(eventStr);
    		JSONArray obj = ob.getJSONArray("list");
    		int total = 0;
    		for (Object aObj : obj) {
    			JSONObject e = (JSONObject) aObj;
    			int count = Integer.parseInt(e.getString("count"));
    			if(count!=0){
    				JSONObject jo = new JSONObject();
    				String eventType = e.getString("eventType");
    				arr.add(eventType);
    				arra.add(count);
    				jo.put("value", count);
    				jo.put("name", eventType);
    				json.add(jo);
    			}
    			total = total+count;
    			
    		}	
			reMap.put("name", arr);
			reMap.put("value", arra);
			reMap.put("json", json);
			reMap.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reMap;
	}
	
	public static List getEventTypeCountNoDecode(String reStr) {
		List reList = new ArrayList();
    	try {
    		JSONObject obj = JSONObject.fromObject(reStr);
    		JSONArray jsonArray = obj.getJSONArray("list");
    		if(jsonArray!=null && jsonArray.size()>0){
    			for (int i = 0; i < jsonArray.size(); i++) {
    				Map<String,Object> newMap = new HashMap<String,Object>();
    				String object = jsonArray.getString(i);
			        JSONObject jsonObject = JSONObject.fromObject(object);
			        int count = jsonObject.getInt("count");
			        if(count!=0){
			        	String eventType = jsonObject.getString("eventType");
				        newMap.put("count", count);
				        newMap.put("eventType", eventType);
				        reList.add(newMap);
			        }
				}
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reList;
	}

}