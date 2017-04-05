package com.cn.ctbri.southapi.adapter.waf.syslog;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogConfig {
	//public static final String syslogConfigFile = "../conf/WafSyslogConfig.properties";
	public static final String FACTORY_SYSLOG_NSFOCUS = "nsfocus";
	public static final String DEFAULT_PACKET_DISPOSEMODE_STORE = "Store";	
	public static final String DEFAULT_PACKET_DISPOSEMODE_RELAY = "Relay";

	public static int PACKET_DISPOSEMODE_FLAG = 0; //0:INVALID; 1:STORE,2:RELAY ,10:STORE AND RELAY
			
	public static String syslogServerIP = "127.0.0.1";
	public static Integer syslogServerPort = 514;

	public static Integer threadPoolSize = 10;
	public static Integer disposeBlockSize = 10;
	public static String packetDisposeMode = DEFAULT_PACKET_DISPOSEMODE_STORE;
	public static boolean isDebugMode = false;
	
	//For Store Mode
	public static String jdbDriver = "com.mysql.jdbc.Driver";
	public static String jdbcURL = "jdbc:mysql://localhost:3306/southapi";
	public static String jdbcUser = "root";
	public static String jdbcPwd = "root";

	//For Relay Mode
	public static String relayServerIP = "127.0.0.1";
	public static Integer relayServerPort = 514;
	public static String relayProtocol = "udp";
	public static String relayMode = "Raw";
	
	
	/*
	 * Read WAF Syslog Config
	 */
	/*
	static {
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(syslogConfigFile);//�����ļ���      
			prop.load(fis);//

			
			//For Syslog Server
			String temp = prop.getProperty("SyslogServerIP");
			if ( temp != null ) syslogServerIP = temp.trim();
			
			temp = prop.getProperty("SyslogServerPort");
			if ( temp != null ) syslogServerPort = Integer.parseInt(temp.trim());
			
			temp = prop.getProperty("ThreadPoolSize");
			if ( temp != null ) threadPoolSize = Integer.parseInt(temp.trim());
			
			temp = prop.getProperty("jdbDriver");
			if ( temp != null ) jdbDriver = temp.trim();
			
			temp = prop.getProperty("jdbcURL");
			if ( temp != null ) jdbcURL = temp.trim();
			
			temp = prop.getProperty("jdbcUser");
			if ( temp != null ) jdbcUser = temp.trim();
			
			temp = prop.getProperty("jdbcPwd");
			if ( temp != null ) jdbcPwd = temp.trim();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/



	public static String getSyslogServerIP() {
		return syslogServerIP;
	}



	public static void setSyslogServerIP(String syslogServerIP) {
		WAFSyslogConfig.syslogServerIP = syslogServerIP;
	}




	public static Integer getSyslogServerPort() {
		return syslogServerPort;
	}



	public static void setSyslogServerPort(Integer syslogServerPort) {
		WAFSyslogConfig.syslogServerPort = syslogServerPort;
	}



	public static String getPacketDisposeMode() {
		return packetDisposeMode;
	}



	public static void setPacketDisposeMode(String packetDisposeMode) {
		WAFSyslogConfig.packetDisposeMode = packetDisposeMode;
	}





	public static String getRelayServerIP() {
		return relayServerIP;
	}



	public static void setRelayServerIP(String relayServerIP) {
		WAFSyslogConfig.relayServerIP = relayServerIP;
	}



	public static Integer getRelayServerPort() {
		return relayServerPort;
	}



	public static void setRelayServerPort(Integer relayServerPort) {
		WAFSyslogConfig.relayServerPort = relayServerPort;
	}



	public static String getRelayProtocol() {
		return relayProtocol;
	}



	public static void setRelayProtocol(String relayProtocol) {
		WAFSyslogConfig.relayProtocol = relayProtocol;
	}



	public static String getRelayMode() {
		return relayMode;
	}



	public static void setRelayMode(String relayMode) {
		WAFSyslogConfig.relayMode = relayMode;
	}



	public static Integer getThreadPoolSize() {
		return threadPoolSize;
	}



	public static void setThreadPoolSize(Integer threadPoolSize) {
		WAFSyslogConfig.threadPoolSize = threadPoolSize;
	}



	public static Integer getDisposeBlockSize() {
		return disposeBlockSize;
	}



	public static void setDisposeBlockSize(Integer disposeBlockSize) {
		WAFSyslogConfig.disposeBlockSize = disposeBlockSize;
	}



	public static String getJdbDriver() {
		return jdbDriver;
	}



	public static void setJdbDriver(String jdbDriver) {
		WAFSyslogConfig.jdbDriver = jdbDriver;
	}



	public static String getJdbcURL() {
		return jdbcURL;
	}



	public static void setJdbcURL(String jdbcURL) {
		WAFSyslogConfig.jdbcURL = jdbcURL;
	}



	public static String getJdbcUser() {
		return jdbcUser;
	}



	public static void setJdbcUser(String jdbcUser) {
		WAFSyslogConfig.jdbcUser = jdbcUser;
	}



	public static String getJdbcPwd() {
		return jdbcPwd;
	}



	public static void setJdbcPwd(String jdbcPwd) {
		WAFSyslogConfig.jdbcPwd = jdbcPwd;
	}



	public static boolean isDebugMode() {
		return isDebugMode;
	}



	public static void setDebugMode(boolean isDebugMode) {
		WAFSyslogConfig.isDebugMode = isDebugMode;
	}
	
	
}
