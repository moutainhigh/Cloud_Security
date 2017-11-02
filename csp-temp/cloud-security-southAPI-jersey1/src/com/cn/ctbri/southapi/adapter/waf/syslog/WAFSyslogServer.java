package com.cn.ctbri.southapi.adapter.waf.syslog;

import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.SyslogServerConfigIF;
import org.productivity.java.syslog4j.server.SyslogServerEventHandlerIF;
import org.productivity.java.syslog4j.server.SyslogServerIF;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogServer extends Thread {
	private SyslogServerIF serverIF = null;
	private String DEFAULT_SYSLOGSERVER_PROTOCOL = "udp";
	
	public WAFSyslogServer() {
	}
	
	public boolean initWAFSyslogServer() {
		//For Syslog Server
		serverIF = SyslogServer.getInstance(DEFAULT_SYSLOGSERVER_PROTOCOL);
		
		SyslogServerConfigIF config = serverIF.getConfig();
		
		config.setHost(WAFSyslogConfig.syslogServerIP);
		config.setPort(WAFSyslogConfig.syslogServerPort);
		
		//For Syslog Event Handler
		SyslogServerEventHandlerIF eventHandler = new WAFSyslogHandler();
		config.addEventHandler(eventHandler);		

		//Set packet thread priority
		//config.setThreadPriority(Thread.MAX_PRIORITY);
		
		SyslogServer.getThreadedInstance(DEFAULT_SYSLOGSERVER_PROTOCOL);

		return true;
	}
	
	
	public void destoryWAFSyslogServer() {
		if ( serverIF != null ) serverIF.shutdown();
	}
	
	
	
	/*	
	@Override
    public void run() {
		if ( this.serverIF == null ) return;
		
		serverIF.run();
    }
    */
}
