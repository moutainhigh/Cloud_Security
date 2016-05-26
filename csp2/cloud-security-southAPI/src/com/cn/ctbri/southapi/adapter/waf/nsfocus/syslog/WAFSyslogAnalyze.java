package com.cn.ctbri.southapi.adapter.waf.nsfocus.syslog;

import java.util.concurrent.ArrayBlockingQueue;

import org.productivity.java.syslog4j.server.SyslogServerEventIF;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogAnalyze extends Thread {
	public static boolean flagStopSyslogAnalyze = false;

	private final int countPollSyslogEvent = 10;	
	//private LinkedBlockingQueue<SyslogServerEventIF> queueMajor = null;
	
	
	private WAFSyslogDispose wafSyslogDispose = new WAFSyslogDispose();
	
	public WAFSyslogAnalyze() {
		wafSyslogDispose.createSyslogAnalyzeThreadPool();
	}
	
	/*
	 * Description: 
	 * Params: 
	 */
	public void stopWAFSyslogAnalyzeThread() {
		flagStopSyslogAnalyze = true;
		
		wafSyslogDispose.destorySyslogAnalyzeThreadPool();
	}
	
	
	@Override
    public void run() {

		while( !flagStopSyslogAnalyze ) {
			
			if ( WAFSyslogHandler.getSyslogEventQueue().size() <= 0 )
			{
				try {
					Thread.sleep(100); 
				} catch(Exception e) {
					
				}
				continue;
			}
			
			ArrayBlockingQueue<SyslogServerEventIF> queueDispose = new ArrayBlockingQueue<SyslogServerEventIF>(countPollSyslogEvent+1);
			
			//Poll 10 Event
			int nCount = 0;
			for( nCount=0; nCount<countPollSyslogEvent; nCount++ ) {
				SyslogServerEventIF event = WAFSyslogHandler.getSyslogEventQueue().poll();
				if ( event == null ) break;
				
				queueDispose.offer(event);
			}
			
			/*if ( nCount <= 0 ) {
				try {
					Thread.sleep(100); 
				} catch(Exception e) {
					
				}
				
				continue; 
			}*/
			
			//Dispose Syslog Event
			wafSyslogDispose.disposeSyslogEvent(queueDispose);
			
		}
    }
	

}
