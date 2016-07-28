package com.cn.ctbri.util;

import java.security.MessageDigest;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-4-9
 * 描        述：  token加密
 * 版        本：  1.0
 */
public class MD5Token {
    /*** 
	 * MD5加密 生成32位md5码
	 * @param 待加密字符串
	 * @return 返回32位md5码
	 */
	public static String md5Encode(String inStr) throws Exception {
	    MessageDigest md5 = null;
	    try {
	        md5 = MessageDigest.getInstance("MD5");
	    } catch (Exception e) {
	        System.out.println(e.toString());
	        e.printStackTrace();
	        return "";
	    }
	
	    byte[] byteArray = inStr.getBytes("UTF-8");
	    byte[] md5Bytes = md5.digest(byteArray);
	    StringBuffer hexValue = new StringBuffer();
	    for (int i = 0; i < md5Bytes.length; i++) {
	        int val = ((int) md5Bytes[i]) & 0xff;
	        if (val < 16) {
	            hexValue.append("0");
	        }
	        hexValue.append(Integer.toHexString(val));
	    }
	    return hexValue.toString();
	}

	/**
	 * 测试主函数
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		String apikey = "skksiksiwkerkerj";
		String userid = "100010101010";
		String radomchar = "sfdsfe1090w8w7w997w9";
		
		String str = apikey+","+userid+","+radomchar;
		
		String token = md5Encode(str);
	    System.out.println("Source=" + str);
	    System.out.println("toke=" + token);
	   
	
	}

}