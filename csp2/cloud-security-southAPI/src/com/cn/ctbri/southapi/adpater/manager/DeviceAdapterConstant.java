package com.cn.ctbri.southapi.adpater.manager;

/**
 * 设备适配管理配置参数
 * @version beta
 * @author shao
 * @time 2015-11-04
 */
public class DeviceAdapterConstant {
	/**
	 * 设备加载失败的返回信息
	 */
	public static final String ERROR_DEVICEINFO_NOCONTENT = "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	/**
	 * scanner在配置文件中的位置路径
	 */
	public static final String DEVICE_SCANNER_ROOT = "/DeviceAdapterConfig/DeviceList/DeviceScannerList/DeviceScanner";
	/**
	 * 
	 */
	public static final String FILE_DEVICE_CONFIG = DeviceAdapterConstant.class.getResource("/DeviceConfig.xml").toString();
	/**
	 * 安恒扫描设备名
	 */
	public static final String DEVICE_SCANNER_ARNHEM = "Arnhem";
	/**
	 * 知道创宇扫描设备名
	 */
	public static final String DEVICE_SCANNER_WEBSOC = "Websoc";
	public static String DEFALUT_DEVICE_TYPE_SCANNER = "SCANNER" ;
	public static String DEFALUT_DEVICE_TYPE_DDOS = "DDOS" ;	
}
