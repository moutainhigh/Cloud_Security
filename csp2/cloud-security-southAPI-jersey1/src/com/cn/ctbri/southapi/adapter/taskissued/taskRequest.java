package com.cn.ctbri.southapi.adapter.taskissued;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import net.sf.json.JSONObject;

public class taskRequest {
	public String sendRequest(JSONObject jsonObject){
		try {
			 System.out.println("socket success.............");
	         Socket s = new Socket("192.168.204.129",9999);
	         System.out.println("socket success");
	         //JSONObject jo=new JSONObject();
	         //jo.put("type","CreateTask");
	         String str=jsonObject.toString();
	         System.out.println(str);
	         //String info="type:";
	         
	         //构建IO
	         InputStream is = s.getInputStream();//构建输入流，即客户端发送的数据
	         OutputStream os = s.getOutputStream();//构建输出流，即客户端接收的数据
	         BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os));
	         bw.write(str);
	         bw.flush();
	         //bw.close();
	         //is.close();
	         //os.close();
	         //s.close();
	         
	         /*
	         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
	         //向服务器端发送一条消息
	         bw.write("测试客户端和服务器通信，服务器接收到消息返回到客户端\n");
	         bw.flush();*/
	         
	         //读取服务器返回的消息
	         BufferedReader br = new BufferedReader(new InputStreamReader(is));
	         String mess = br.readLine();
	         System.out.println("服务器："+mess);
	         bw.close();
	         br.close();
	         is.close();
	         os.close();
	         s.close();
	         System.out.println("socket end");
	         return mess;
	         
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	         return null;
	      } catch (IOException e) {
	         e.printStackTrace();
	         return null;
	      }
	}

}
