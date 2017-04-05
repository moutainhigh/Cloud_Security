package com.cn.ctbri.southapi.adapter.utils;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;

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
}
