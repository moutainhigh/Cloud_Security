package com.cn.ctbri.southapi.adapter.scanservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import net.sf.json.JSONObject;

public class ScanServiceSocket {
	private static String baseURL;
	private static int basePort;
	static{	
		try {
			Properties properties = new Properties();
			properties.load(ScanServiceSocket.class.getClassLoader().getResourceAsStream("SystemServiceConfig.properties"));
			baseURL = properties.getProperty("ScanServiceBaseURL");
			basePort = Integer.parseInt(properties.getProperty("ScanServiceBasePort"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public String createScanServiceTask(JSONObject jsonObject){
		try {
	         Socket s = new Socket(baseURL,basePort);
	         System.out.println("socket success");
	         JSONObject jo=new JSONObject();
	         jo.put("type","CreateTask");
	         String str=jo.toString();
	         System.out.println(str);
	         //String info="type:";
	         
	         //构建IO
	         InputStream is = s.getInputStream();//构建输入流，即客户端发送的数据
	         OutputStream os = s.getOutputStream();//构建输出流，即客户端接收的数据
	         BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os));
	         bw.write(str);
	         bw.flush();
	         BufferedReader br = new BufferedReader(new InputStreamReader(is));
	         String mess = br.readLine();
	         System.out.println("服务器："+mess);
	         bw.close();
	         br.close();
	         is.close();
	         os.close();
	         s.close();
	         return mess;
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      
		return null;
	}
	*/
}
