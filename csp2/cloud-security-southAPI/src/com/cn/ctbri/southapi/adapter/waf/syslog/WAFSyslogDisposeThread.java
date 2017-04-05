package com.cn.ctbri.southapi.adapter.waf.syslog;

import java.util.concurrent.ArrayBlockingQueue;

import org.productivity.java.syslog4j.server.SyslogServerEventIF;

import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigDevice;
import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigManager;
import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigSyslog;
import com.cn.ctbri.southapi.adapter.waf.syslog.nsfocus.WAFSyslogDisposeFactoryNSFocus;

/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogDisposeThread extends Thread {
	
	ArrayBlockingQueue<SyslogServerEventIF> queue = null;
	WAFSyslogDBHelper wafSyslogDBHelper = new WAFSyslogDBHelper();

	public WAFSyslogDisposeThread(ArrayBlockingQueue<SyslogServerEventIF>  queue) {
		this.queue = queue;
	}

	@Override
    public void run() {
		if ( queue == null || queue.size() <= 0 ) return;
		
		//Connect DB
		if ( !wafSyslogDBHelper.isConnect() )
			wafSyslogDBHelper.connectDB(WAFSyslogConfig.jdbDriver,WAFSyslogConfig.jdbcURL,WAFSyslogConfig.jdbcUser,WAFSyslogConfig.jdbcPwd);
		
		
		SyslogServerEventIF event = null;
		while ( ( event = this.queue.poll() ) != null ) {
			this.disposeSyslogEvent(event);
		}
      
		//Close DB
		wafSyslogDBHelper.closeDB();
    }

	public void disposeSyslogEvent(SyslogServerEventIF event) {
		String ipAddr = event.getHost();
		if ( ipAddr == null || "".equals(ipAddr) ) return;
		
		if ("localhost".equalsIgnoreCase(ipAddr) ) ipAddr = "127.0.0.1";
		
		WAFSyslogHostParam wafSyslogHostParam = WAFSyslogDispose.getWAFConfigSyslogByIPAddr(ipAddr);
		if ( wafSyslogHostParam == null ) {
			WAFConfigDevice wafConfigDevice = WAFConfigManager.getWAFConfigDeviceByIP(ipAddr);
			if ( wafConfigDevice == null ) return;
			
			//Get WAF Factory
			String factory = wafConfigDevice.getFactory();
			
			//If factory is "" or default, use default dispose
			WAFConfigSyslog wafConfigSyslog = WAFConfigManager.getWAFConfigSyslogByFactoryAndVer(factory,wafConfigDevice.getSyslogVer());
			if ( wafConfigSyslog == null ) return;
			
			wafSyslogHostParam = new WAFSyslogHostParam();
			wafSyslogHostParam.setIpAddr(ipAddr);
			wafSyslogHostParam.setWafConfigDevice(wafConfigDevice);
			wafSyslogHostParam.setWafConfigSyslog(wafConfigSyslog);

			WAFSyslogDispose.putWAFConfigSyslogByIPAddr(ipAddr, wafSyslogHostParam);
		}
		
		String message = event.getMessage();
		if ( message == null || "".equals(message))  return;
		
		//dispose Syslog Event and data into db
		disposeSyslogEvent(message,wafSyslogHostParam);
	}
	
	
	
	public boolean disposeSyslogEvent(String message,WAFSyslogHostParam wafSyslogHostParam) {
		WAFConfigDevice wafConfigDevice  = wafSyslogHostParam.getWafConfigDevice();
		
		//FOR nsfocus
		if ( WAFSyslogConfig.FACTORY_SYSLOG_NSFOCUS.equalsIgnoreCase(wafConfigDevice.getFactory()) ) {
			WAFSyslogDisposeFactoryNSFocus wafSyslogDisposeFactoryNSFocus = new WAFSyslogDisposeFactoryNSFocus();
			return wafSyslogDisposeFactoryNSFocus.disposeSyslogEvent(message,wafSyslogHostParam,wafSyslogDBHelper);
		}
			
		return false;
	}	
	


}
