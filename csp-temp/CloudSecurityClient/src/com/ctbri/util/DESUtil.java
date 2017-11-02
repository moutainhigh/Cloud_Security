package com.ctbri.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Encoder;

public class DESUtil {
	private static Log log = LogFactory.getLog(DESUtil.class);
	/**
	 * DES�㷨��Կ
	 */
	private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128, -65 };
	
	/**
	 * ��ݼ��ܣ��㷨��DES��
	 * 
	 * @param data
	 *            Ҫ���м��ܵ����
	 * @return ���ܺ�����
	 */
	public static String encryptBasedDes(String data) {
		String encryptedData = null;
		try {
			// DES�㷨Ҫ����һ�������ε������Դ
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// ����һ���ܳ׹�����Ȼ�������DESKeySpecת����һ��SecretKey����
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// ���ܶ���
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// ���ܣ������ֽ����������ַ�
			encryptedData = new BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			log.error("���ܴ��󣬴�����Ϣ��", e);
			throw new RuntimeException("���ܴ��󣬴�����Ϣ��", e);
		}
		return encryptedData;
	}
	
	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static String decryptBasedDes(String cryptData) {
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
		} catch (Exception e) {
			log.error("解密错误，错误信息：", e);
			throw new RuntimeException("解密错误，错误信息：", e);
		}
		return decryptedData;
	}

}
