package com.cn.ctbri.southapi.adapter.waf.syslog;

import java.io.File;

import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigManager;


/*
 * Description : NSFOCUS WAF Syslog Server
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogMainDeamon {
	public static class Options {		
		public String configFileName = null;

		public String usage = null;
	}

	public static void usage(String problem) {
		if (problem != null) {
			System.out.println("Error: " + problem);
			System.out.println();
		}
		
		System.out.println("Usage:");
		System.out.println();
		System.out.println("    WAFSyslogMainDeamon -i <configfile>");
		System.out.println();
		System.out.println("    -i <configfile>      input WAF Syslog config file");
		System.out.println();
		System.out.println("    Copyright : anquanbang; Web:http://www.anquanbang.net");
		System.out.println();
	}	
	
	public static Options parseOptions(String[] args) {
		Options options = new Options();
		
		int i = 0;
		while(i < args.length) {
			String arg = args[i++];
				
			if ("-i".equals(arg)) { if (i == args.length) { options.usage = "Must specify file with -i"; return options; } options.configFileName = args[i++]; }

		}
		
		if ( options.configFileName == null || "".equalsIgnoreCase(options.configFileName)) {
			options.usage = "Must input WAF Syslog config file; -i <configfile>";
			return options;
		}

		File file=new File( options.configFileName);  
		if ( !file.exists() ) {
			options.usage = "WAF Syslog config file not exist!";
			return options;			
		}
		
		return options;
	}	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Options options = parseOptions(args);

		if (options.usage != null) {
			usage(options.usage);
			System.exit(1); 
		}
		//Format logo
		System.out.println("");
		System.out.println("");
		System.out.print("*******************************************************************************\n");
		System.out.print("*                     ��ȫ��й������ư�ȫ���������̳�                      *\n");
		System.out.print("*                         Web:http://www.anquanbang.net                       *\n");
		System.out.print("*******************************************************************************\n");
		System.out.println("");
		System.out.println("");
		
		System.out.println("Waf syslong manger service starting...");		
		System.out.println("Loading config file:" + options.configFileName);

		WAFConfigManager wafConfigManager = new WAFConfigManager();
		wafConfigManager.loadWAFConfig(options.configFileName);
		
		
		System.out.println("Starting waf syslog manager service!");		
		WAFSyslogManager wsm = new WAFSyslogManager();
		wsm.initWAFSyslogManager();
		
		
		//For System config information
		String configInfo = "\n	ServerIP: " + WAFSyslogConfig.getSyslogServerIP() + " Port: " + WAFSyslogConfig.getSyslogServerPort() +
				"\n	ThreadPoolSize: " + WAFSyslogConfig.getThreadPoolSize() + " DisposeBlockSize: " + WAFSyslogConfig.getDisposeBlockSize() + 
				"\n	PacketDisposeMode: "+ WAFSyslogConfig.getPacketDisposeMode() ;
		System.out.println("Waf syslog server config: " +configInfo );
		
		System.out.println("Waf syslog current dispose mode: " + WAFSyslogConfig.getPacketDisposeMode() );
		if ( WAFSyslogConfig.DEFAULT_PACKET_DISPOSEMODE_RELAY.equalsIgnoreCase(WAFSyslogConfig.getPacketDisposeMode()) ) {
			System.out.println("Relay ServerIP: " + WAFSyslogConfig.getRelayServerIP() + "Port: " + WAFSyslogConfig.getRelayServerPort() + "Mode: " + WAFSyslogConfig.getRelayMode());
		} else if ( WAFSyslogConfig.DEFAULT_PACKET_DISPOSEMODE_STORE.equalsIgnoreCase(WAFSyslogConfig.getPacketDisposeMode()) ) {  //For store
			System.out.println("\tJdbcURL: " + WAFSyslogConfig.getJdbcURL() );
		}
		
		System.out.println("Waf syslog manager service running!");	

	}

}
