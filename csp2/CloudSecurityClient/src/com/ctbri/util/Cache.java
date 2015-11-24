package com.ctbri.util;

public class Cache {
	public static java.util.Hashtable<String,Object> __cacheList = new java.util.Hashtable<String,Object>(); 

	public Cache() { 

	} 
	public synchronized static void add(String key, Object value) { 
		Cache.add(key, value); 
	} 
	
	public synchronized static Object get(String key) { 
		Object obj = Cache.__cacheList.get(key); 
		if (obj != null) { 
			return obj;
		} 
		return obj;
	}
}
