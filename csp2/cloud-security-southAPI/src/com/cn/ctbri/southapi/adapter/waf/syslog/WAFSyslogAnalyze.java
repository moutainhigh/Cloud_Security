package com.cn.ctbri.southapi.adapter.waf.syslog;

import java.util.concurrent.ArrayBlockingQueue;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.server.SyslogServerEventIF;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogAnalyze extends Thread {
	public static boolean flagStopSyslogAnalyze = false;
	
	//private final int DEFAULT_SYSLOG_PACKET_DISPOSE_BLOCKSIZE = 10;	
	private final int DEFAULT_SYSLOG_PACKET_DISPOSE_TIMEOUT = 5 * 1000;  //5 s
	
	private SyslogIF relaySyslogIF = null;
	private WAFSyslogDispose wafSyslogDispose = null;
	
	public WAFSyslogAnalyze() {
		
	}
	
	
	public boolean initWAFSyslogAnalyze() {
		String pdm = WAFSyslogConfig.getPacketDisposeMode();
		if ( pdm == null || "".equals(pdm) )
		{
			System.out.println("Please config packet dispose mode!");
			return false;
		}
		
		int nPos = pdm.indexOf(WAFSyslogConfig.DEFAULT_PACKET_DISPOSEMODE_STORE);
		if ( nPos != -1 ) {
			//For store
			wafSyslogDispose = new WAFSyslogDispose();
			wafSyslogDispose.createSyslogAnalyzeThreadPool();
			
			WAFSyslogConfig.PACKET_DISPOSEMODE_FLAG = 1;
		}
		
		nPos = pdm.indexOf(WAFSyslogConfig.DEFAULT_PACKET_DISPOSEMODE_RELAY);
		if ( nPos != -1 ) {
			//For relay
			if ( (null == WAFSyslogConfig.getRelayProtocol()) ||
				 (null == WAFSyslogConfig.getRelayServerIP()) || 
				 (0 == WAFSyslogConfig.getRelayServerPort())  )
				   return false;
				   
			relaySyslogIF = Syslog.getInstance(WAFSyslogConfig.getRelayProtocol());
			relaySyslogIF.getConfig().setHost(WAFSyslogConfig.getRelayServerIP());
			relaySyslogIF.getConfig().setPort(WAFSyslogConfig.getRelayServerPort());
			
			if ( WAFSyslogConfig.PACKET_DISPOSEMODE_FLAG == 1 )
				WAFSyslogConfig.PACKET_DISPOSEMODE_FLAG = 10;
			else 
				WAFSyslogConfig.PACKET_DISPOSEMODE_FLAG = 2;
		}
		return true;
	}
	
	
	public void destoryWAFSyslogAnalyze() {
		if ( wafSyslogDispose != null ) stopWAFSyslogAnalyzeThread();
	}
	
	/*
	 * Description: 
	 * Params: 
	 */
	private void stopWAFSyslogAnalyzeThread() {
		flagStopSyslogAnalyze = true;
		
		wafSyslogDispose.destorySyslogAnalyzeThreadPool();
	}
	
	public boolean isWaitTimeout(long lBeginTime) {
		if ( System.currentTimeMillis() -lBeginTime > DEFAULT_SYSLOG_PACKET_DISPOSE_TIMEOUT )
			return true;
		
		return false;
	}
	
	@Override
    public void run() {

		long lcurrTime = System.currentTimeMillis();
		
		while( !flagStopSyslogAnalyze ) {
			//Wait packet
			if ( WAFSyslogHandler.getSyslogEventQueue().size() <= 0 )
			{
				try {
					Thread.sleep(100); 
				} catch(Exception e) {
					
				}
				continue;
			}

			//Queue size < 10, Wait 5 S
			if ( (WAFSyslogHandler.getSyslogEventQueue().size() < WAFSyslogConfig.getDisposeBlockSize() ) &&
					!isWaitTimeout(lcurrTime) )
			{
				try {
					Thread.sleep(100); 
				} catch(Exception e) {
					
				}
				continue;
			}
			
			lcurrTime = System.currentTimeMillis();

			//From major queue poll packet, and put dispose queue
			ArrayBlockingQueue<SyslogServerEventIF> queueDispose = new ArrayBlockingQueue<SyslogServerEventIF>(WAFSyslogConfig.getDisposeBlockSize()+1);
			
			//Poll 10 Event
			int nCount = 0;
			for( nCount=0; nCount<WAFSyslogConfig.getDisposeBlockSize(); nCount++ ) {
				SyslogServerEventIF event = WAFSyslogHandler.getSyslogEventQueue().poll();
				if ( event == null ) break;
				
				//For Relay
				if ( WAFSyslogConfig.PACKET_DISPOSEMODE_FLAG == 2 || WAFSyslogConfig.PACKET_DISPOSEMODE_FLAG == 10 ) {   //Relay or Store and relay
					disposeSyslogEventForRelay(event);
				}

				queueDispose.offer(event);
			}			
			
			if ( WAFSyslogConfig.PACKET_DISPOSEMODE_FLAG == 1 || WAFSyslogConfig.PACKET_DISPOSEMODE_FLAG == 10 ) {   //Store or Store and relay
				disposeSyslogEventForStore(queueDispose);
			}	
			
		}
    }
	
	public void disposeSyslogEventForStore(ArrayBlockingQueue<SyslogServerEventIF> queueDispose) {
		if ( queueDispose == null ) return ;
		
		//Dispose Syslog Event
		wafSyslogDispose.disposeSyslogEvent(queueDispose);		
	}
	
	
	public void disposeSyslogEventForRelay(SyslogServerEventIF event) {
		if ( event == null ) return;
			
		relaySyslogIF.log(event.getLevel(), event.getMessage());	

	}
}
