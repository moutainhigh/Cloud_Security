package com.ctbri.util;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	public static final String encodeObject2Json(Object pObject) {
		String jsonString = "[]";
		if (pObject==null) {
			// log.warn("�����Java����Ϊ��,���ܽ������л�ΪJson���ϸ�ʽ.����!");
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
//			log.info("���л����JSON�������:\n" + jsonString);
//		}
		return jsonString;
	}
}
