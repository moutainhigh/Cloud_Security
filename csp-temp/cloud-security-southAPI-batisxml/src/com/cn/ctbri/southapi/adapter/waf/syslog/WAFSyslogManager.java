package com.cn.ctbri.southapi.adapter.waf.syslog;



/*
 * Description : NSFOCUS WAF Syslog Server
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogManager {
	//private SyslogServerIF serverIF = null;
	private WAFSyslogAnalyze wafSyslogAnalyze = null;
	private WAFSyslogServer wafSyslogServer = null;
	
	
	public boolean initWAFSyslogManager() {
		//Create Syslog dispatch thread
		this.createWAFSyslogAnalyzeThread();
	
		//Start Syslog Server
		this.startSyslogServer();
	
		return true;
	}

	
	public void destoryWAFSyslogManager() {
		this.stopSyslogServer();
		
		this.stopWAFSyslogAnalyzeThread();	
	}


	
	/*
	 * Description: Start Syslog Server 
	 * Params: 
	 */
	private boolean startSyslogServer() {		

		//Start Syslog Receive message Thread
		wafSyslogServer = new WAFSyslogServer();
		wafSyslogServer.initWAFSyslogServer();

		return true;
	}
	
	
	/*
	 * Description: Stop Syslog Server 
	 * Params: 
	 */
	private void stopSyslogServer() {
		if ( wafSyslogServer != null )  wafSyslogServer.destoryWAFSyslogServer();
	}
		
	
	
	
	private boolean createWAFSyslogAnalyzeThread() {
		
		//Create Syslog analyze thread
		wafSyslogAnalyze = new WAFSyslogAnalyze();
		wafSyslogAnalyze.initWAFSyslogAnalyze();
		
		//Start Thread
		wafSyslogAnalyze.start();
		
		return true;
	}

	
	private void stopWAFSyslogAnalyzeThread() {
		//Stop Syslog Analyze Thread
		wafSyslogAnalyze.destoryWAFSyslogAnalyze();
		
	}
	
	/**
	 * @param args
	 */
	/*	
	public static void main(String[] args) {		

		WAFConfigManager wafConfigManager = new WAFConfigManager();
		wafConfigManager.loadWAFConfig();
		
		WAFSyslogManager wsm = new WAFSyslogManager();
		wsm.initWAFSyslogManager();
			
		
	}*/	

}
