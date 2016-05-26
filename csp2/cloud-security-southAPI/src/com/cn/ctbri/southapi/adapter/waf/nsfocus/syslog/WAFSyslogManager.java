package com.cn.ctbri.southapi.adapter.waf.nsfocus.syslog;


import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.SyslogServerConfigIF;
import org.productivity.java.syslog4j.server.SyslogServerEventHandlerIF;
import org.productivity.java.syslog4j.server.SyslogServerIF;

import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigManager;

/*
 * Description : NSFOCUS WAF Syslog Server
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogManager {
	private SyslogServerIF serverIF = null;
	private WAFSyslogAnalyze wafSyslogAnalyze = null;
	
	
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
		//For Syslog Server
		serverIF = SyslogServer.getInstance("udp");
		SyslogServerConfigIF config = serverIF.getConfig();
		config.setHost(WAFSyslogConfig.syslogServerIP);
		config.setPort(WAFSyslogConfig.syslogServerPort);
		
		//For Syslog Event Handler
		SyslogServerEventHandlerIF eventHandler = new WAFSyslogHandler();
		config.addEventHandler(eventHandler);		
		
		//Run
		serverIF.initialize("udp", config);
		//serverIF.run();
		
		//Start Syslog Receive message Thread
		WAFSyslogServer wafSyslogServer = new WAFSyslogServer(serverIF);
		wafSyslogServer.start();

		return true;
	}
	
	
	/*
	 * Description: Stop Syslog Server 
	 * Params: 
	 */
	private void stopSyslogServer() {
		if ( serverIF != null )  serverIF.shutdown();
	}
		
	
	
	
	private boolean createWAFSyslogAnalyzeThread() {
		
		//Create Syslog analyze thread
		wafSyslogAnalyze = new WAFSyslogAnalyze();
				
		//Start Thread
		wafSyslogAnalyze.start();
		
		return true;
	}

	
	private void stopWAFSyslogAnalyzeThread() {
		//Stop Syslog Analyze Thread
		wafSyslogAnalyze.stopWAFSyslogAnalyzeThread();
		
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
		WAFConfigManager wafConfigManager = new WAFConfigManager();
		wafConfigManager.loadWAFConfig();
		
		WAFSyslogManager wsm = new WAFSyslogManager();
		wsm.initWAFSyslogManager();
		
		
		
	}

}
