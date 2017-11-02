package com.cn.ctbri.southapi.adapter.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class EncryptUtility {
	
	//MD5加密
	public static String getMD5(String str) {
	    try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(str.getBytes());
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
            e.printStackTrace();
            return null;
	    }
	}
	
    public static String encodeBase64Str(String plainText){  
        byte[] b=plainText.getBytes();  
        Base64 base64=new Base64();  
        b=base64.encode(b);  
        String s=new String(b);  
        return s;  
    }  
	
    public static String decodeBase64Str(String encodeStr){  
        byte[] b=encodeStr.getBytes();  
        Base64 base64=new Base64();  
        b=base64.decode(b);  
        String s=new String(b);  
        return s;  
    }  
	//SHA256加密
	public static String SHA256(final String strText)  
	{  
	    return SHA(strText, "SHA-256");  
	} 
	//SHA512加密
	public static String SHA512(final String strText) {
		return SHA(strText, "SHA-512");
	}
	//SHA1加密
	public static String SHA1(final String strText){
		return SHA(strText, "SHA-1");
	}
	//SHA加密算法
	/**
	 * 
	 * @param strText 加密的字符串
	 * @param strType 加密类型
	 * @return
	 */
	private static String SHA(final String strText, final String strType)  
	{  
	    // 返回值  
	    String strResult = null;  
	  
	    // 是否是有效字符串  
	    if (strText != null && strText.length() > 0)  
	    {  
	      try  
	      {  
	        // SHA 加密开始  
	        // 创建加密对象 ，指定加密算法类型
	        MessageDigest messageDigest = MessageDigest.getInstance(strType);  
	        // 传入要加密的字符串  
	        messageDigest.update(strText.getBytes());  
	        // 得到 byte结果
	        byte byteBuffer[] = messageDigest.digest();  
	  
	        //	转为String 
	        StringBuffer strHexString = new StringBuffer();   
	        for (int i = 0; i < byteBuffer.length; i++)  
	        {  
	          String hex = Integer.toHexString(0xff & byteBuffer[i]);  
	          if (hex.length() == 1)  
	          {  
	            strHexString.append('0');  
	          }  
	          strHexString.append(hex);  
	        }  
	        // 得到结果
	        strResult = strHexString.toString();  
	      }  
	      catch (NoSuchAlgorithmException e)  
	      {  
	        e.printStackTrace();  
	      }  
	    }
	    return strResult;  
	}  
    //HMAC SHA256加密
    public static String HMACSHA256(String string,String secret){
    	try {
			Mac sha256Mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256Mac.init(secretKey);
			byte[] doFinal = sha256Mac.doFinal(string.getBytes());
			byte[] hex = new Hex().encode(doFinal);
			
			return new String(hex);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}
    }
}
