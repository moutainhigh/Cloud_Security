package com.cn.ctbri.southapi.adapter.waf.nsfocus.syslog;

import java.net.SocketAddress;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

import org.productivity.java.syslog4j.server.SyslogServerEventIF;
import org.productivity.java.syslog4j.server.SyslogServerIF;
import org.productivity.java.syslog4j.server.SyslogServerSessionlessEventHandlerIF;

import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigDevice;
import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigManager;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogHandler implements SyslogServerSessionlessEventHandlerIF {
	/**
	 * 
	 */
	private static final long serialVersionUID = -61510782785410777L;
	
	
	private static LinkedBlockingQueue<SyslogServerEventIF> syslogEventQueue = new LinkedBlockingQueue<SyslogServerEventIF>();
	public Hashtable<String,WAFConfigDevice> mapWAFConfigDevice = new Hashtable<String,WAFConfigDevice>();    //String : ip

	@Override
	public void destroy(SyslogServerIF arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(SyslogServerIF arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void event(SyslogServerIF syslogServer,
			SocketAddress socketAddress, SyslogServerEventIF event) {
		
		/*
		 * �ж�IP�Ƿ�Ϊϵͳ�����ܽ��յĵ�ַ��������ǣ�������־��Ϣ
		 */
		String host = event.getHost();
		if ( !isValidSyslogHost(host) ) {
			//System.out.println(">>>>>>>>>>>>>>>>>>>"+ event.getMessage());
			return;		
		}
		//System.out.print( event.getMessage());
		
		//Event offer queue
		syslogEventQueue.offer(event);		
	}

	@Override
	public void exception(SyslogServerIF arg0, SocketAddress arg1,
			Exception arg2) {
		// TODO Auto-generated method stub

	}
	
	
	public static LinkedBlockingQueue<SyslogServerEventIF> getSyslogEventQueue() {
		return WAFSyslogHandler.syslogEventQueue;
	}
	
	
	private boolean isValidSyslogHost(String host) {
		if ( host == null || "".equals(host) ) return false;
		if ( "localhost".equalsIgnoreCase(host) ) host = "127.0.0.1";
		
		/*
		 * 1.Find Local Hash Table
		 * 2.Find Device Config List and Store to Hash Table
		 */
		//Hash Table
		WAFConfigDevice wafConfigDevice = mapWAFConfigDevice.get(host);
		if ( null != wafConfigDevice )  return true;
		
		//Device Config List
		wafConfigDevice = WAFConfigManager.getWAFConfigDeviceByIP(host);
		if ( wafConfigDevice == null ) return false;
		
		mapWAFConfigDevice.put(host, wafConfigDevice);
		return true;
	}


}
