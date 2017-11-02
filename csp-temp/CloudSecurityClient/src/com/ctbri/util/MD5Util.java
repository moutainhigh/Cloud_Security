package com.ctbri.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/**
	 * MD5 摘要，使用系统缺省字符集编码
	 * 
	 * @param input
	 * @return
	 */
	public static String MD5(String input) {
		return MD5(input, Charset.defaultCharset());
	}

	/**
	 * MD5 摘要
	 * 
	 * @param input
	 * @param charset
	 * @return
	 */
	public static String MD5(String input, String charset) {
		return MD5(input, Charset.forName(charset));
	}

	/**
	 * MD5 摘要
	 * 
	 * @param input
	 * @param charset
	 * @return
	 */
	public static String MD5(String input, Charset charset) {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		md.update(input.getBytes(charset));

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		byte tmp[] = md.digest();
		char str[] = new char[16 * 2];

		int k = 0;
		for (int i = 0; i < 16; i++) {
			byte byte0 = tmp[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}

		String result = new String(str);

		return result;
	}

}
