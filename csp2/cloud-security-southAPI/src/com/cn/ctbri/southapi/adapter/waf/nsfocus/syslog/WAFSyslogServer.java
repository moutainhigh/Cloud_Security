package com.cn.ctbri.southapi.adapter.waf.nsfocus.syslog;

import org.productivity.java.syslog4j.server.SyslogServerIF;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogServer extends Thread {
	private SyslogServerIF serverIF = null;

	public WAFSyslogServer(SyslogServerIF serverIF) {
		this.serverIF = serverIF;
	}
	
	
	@Override
    public void run() {
		if ( this.serverIF == null ) return;
		
		serverIF.run();
    }
}
