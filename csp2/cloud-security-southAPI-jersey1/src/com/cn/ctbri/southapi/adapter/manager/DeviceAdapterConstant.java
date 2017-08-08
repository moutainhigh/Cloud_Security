package com.cn.ctbri.southapi.adapter.manager;

/**
 * 设备适配管理配置参数
 * @version beta
 * @author shao
 * @time 2015-11-04
 */
public class DeviceAdapterConstant {
	public static String RootPath = DeviceAdapterConstant.class.getClassLoader().getResource("/").getPath();
	/**
	 * 设备加载失败的返回信息
	 */
	public static final String ERROR_DEVICEINFO_NOCONTENT = "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	/**
	 * scanner在配置文件中的位置路径
	 */
	public static final String DEVICE_SCANNER_ROOT = "/DeviceAdapterConfig/DeviceList/DeviceScannerList/DeviceScanner";
	/**
	 * waf在配置文件中的位置路径
	 */
	public static final String DEVICE_WAFLIST_ROOT = "/DeviceAdapterConfig/DeviceList/DeviceWAFList";
	/**
	 * 
	 */
	/**
	 * Mybatis数据库配置路径
	 */
	public static final String RESOURCE_DATABASE_CONFIG = "./DataBaseConf.xml";
	//部署用
	//public static final String RESOURCE_DATABASE_CONFIG = "../conf/DataBaseConf.xml";
	
	public static final String WAF_CONFIG = RootPath+"WafConfig.xml";
	/**
	 * waf告警事件类型
	 */
	public static final String WAF_NSFOCUS_EVENT_TYPE = RootPath+"WAF_Nsfocus_EventType.xml";
	//部署用
	//public static final String WAF_NSFOCUS_EVENT_TYPE = "../conf/WAF_Nsfocus_EventType.xml";

	
	//相对路径，部署环境变化时有可能会引起错误
	public static final String FILE_DEVICE_CONFIG = RootPath+"DeviceConfig.xml";
	//部署用
	//public static final String FILE_DEVICE_CONFIG = "../conf/DeviceConfig.xml";
	
	/**
	 * 安恒扫描设备名
	 */
	public static final String DEVICE_SCANNER_ARNHEM = "Arnhem";
	/**
	 * 知道创宇扫描设备名
	 */
	public static final String DEVICE_SCANNER_WEBSOC = "Websoc";
	public static final String DEFALUT_DEVICE_TYPE_SCANNER = "SCANNER" ;
	public static final String DEFALUT_DEVICE_TYPE_DDOS = "DDOS" ;	
	
	public static final String DEVICE_STATE_ON = "ON";
	public static final String DEVICE_STATE_OFF = "OFF";
}
