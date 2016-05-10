package com.cn.ctbri.southapi.adpater.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class NsfocusWAFAdapter {
	public static HashMap<String, NsfocusWAFOperation> mapNsfocusWAFOperation = new HashMap<String, NsfocusWAFOperation>();
	public NsfocusWAFOperation nsfocusWAFOperation = null;
	public String aString;

	public NsfocusWAFOperation getDeviceById(String deviceId) {
		NsfocusWAFOperation nsfocusWAFOperation = mapNsfocusWAFOperation.get(deviceId);
		return nsfocusWAFOperation;
	}
}
