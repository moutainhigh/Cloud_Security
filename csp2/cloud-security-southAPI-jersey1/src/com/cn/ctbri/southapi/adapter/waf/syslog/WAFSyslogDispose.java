package com.cn.ctbri.southapi.adapter.waf.syslog;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.productivity.java.syslog4j.server.SyslogServerEventIF;

/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogDispose {
	public ExecutorService threadPool = null;
	
	public static HashMap<String,WAFSyslogHostParam> mapHostAndWAFConfigSyslogMapping = new HashMap<String,WAFSyslogHostParam>(); //String : host ip addr


	/*
	 * Description: 
	 * Params: 
	 */
	public void createSyslogAnalyzeThreadPool() {
		//Create Fix Thread Pool
		threadPool = Executors.newFixedThreadPool(WAFSyslogConfig.threadPoolSize);
	}
	
	
	
	/*
	 * Description: 
	 * Params: 
	 */
	public void destorySyslogAnalyzeThreadPool() {
		threadPool.shutdown();
	}
	
	
	/*
	 * Description: 
	 * Params: 
	 */
	public boolean disposeSyslogEvent(ArrayBlockingQueue<SyslogServerEventIF> queueDispose) {
		//Create Runnable Interface
		Thread taskThread = new WAFSyslogDisposeThread(queueDispose);
			
		//Submit Task
		threadPool.execute(taskThread);

		return true;
	}
	
	
	/*
	 * Description: 
	 * Params: 
	 */
	public static void putWAFConfigSyslogByIPAddr(String ipAddr,WAFSyslogHostParam wafSyslogHost) {
		if ( ipAddr == null || "".equals(ipAddr) || wafSyslogHost == null ) return;
				
		if ( null != mapHostAndWAFConfigSyslogMapping.get(ipAddr) ) return;
		
		mapHostAndWAFConfigSyslogMapping.put(ipAddr, wafSyslogHost);		
	}
	
	/*
	 * Description: 
	 * Params: 
	 */
	public static WAFSyslogHostParam getWAFConfigSyslogByIPAddr(String ipAddr) {
		if ( ipAddr == null || "".equals(ipAddr) ) return null;
				
		return mapHostAndWAFConfigSyslogMapping.get(ipAddr);
	}


}
