package com.cn.ctbri.southapi.adapter.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

public class DataTypeConverter {
	public static String blobToString(Blob blob) {
		String result = "";
		  try {
		   ByteArrayInputStream msgContent =(ByteArrayInputStream) blob.getBinaryStream();
		   byte[] byte_data = new byte[msgContent.available()];
		   msgContent.read(byte_data, 0,byte_data.length);
		   result = new String(byte_data);
		  } catch (SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  return result;
	}
	private static void createImage(String fileName, String data) 
            throws ServletException, IOException {
        try {
        	String replacedata = data.replaceAll(" ", "+");
        	System.out.println(replacedata);
            String[] url = replacedata.split(",");
            String u = url[1];
            // Base64解码
            byte[] b = new BASE64Decoder().decodeBuffer(u);
            // 生成图片
            OutputStream out = new FileOutputStream(new File(fileName));
            out.write(b);
            out.flush();
            out.close();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
