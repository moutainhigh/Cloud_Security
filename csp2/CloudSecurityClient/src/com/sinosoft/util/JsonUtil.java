package com.sinosoft.util;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	public static final String encodeObject2Json(Object pObject) {
		String jsonString = "[]";
		if (pObject==null) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
		}
//		if (log.isInfoEnabled()) {
//			log.info("序列化后的JSON资料输出:\n" + jsonString);
//		}
		return jsonString;
	}
}
