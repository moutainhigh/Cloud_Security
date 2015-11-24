package com.ctbri.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrMap {
	private static Logger log = LoggerFactory.getLogger(ErrMap.class);

	/** 错误码与错误信息 **/
	private static Properties properties;

	/** 初始化 **/
	static {
		InputStream inputStream = null;
		try {
			properties = new Properties();
			inputStream = ErrMap.class.getClassLoader().getResourceAsStream("errmap.properties");
			properties.load(inputStream);
		} catch (Exception ex) {
			log.error("ErrMap初始化错误", ex);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception ee) {
				log.error("ErrMap初始化错误", ee);
			}
		}
	}

	public static String getProperty(String key, String defaultValue) {
		try {
			String result = (String) properties.get(key.trim());
			if (StringUtils.isBlank(result))
				result = defaultValue;
			return result;
		} catch (Exception ex) {
			log.error("获取属性错误", ex);
			return defaultValue;
		}
	}

	public static String getProperty(String key) {
		return getProperty(key, "");
	}
}
