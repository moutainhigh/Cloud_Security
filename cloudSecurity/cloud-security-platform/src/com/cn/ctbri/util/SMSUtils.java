package com.cn.ctbri.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.cn.ctbri.cfg.Configuration;

/**
 * 文件名称：sendSMS_demo.java
 * 
 * 文件作用：发送短信
 * 
 * 创建时间：2015-1-6
 * 
 * 
返回值											说明
success:msgid								提交成功，发送状态请见4.1
error:msgid									提交失败
error:Missing username						用户名为空
error:Missing password						密码为空
error:Missing apikey						APIKEY为空
error:Missing recipient						手机号码为空
error:Missing message content				短信内容为空
error:Account is blocked					帐号被禁用
error:Unrecognized encoding					编码未能识别
error:APIKEY or password error				APIKEY 或密码错误
error:Unauthorized IP address				未授权 IP 地址
error:Account balance is insufficient		余额不足
error:Black keywords is:党中央				屏蔽词
 */

public class SMSUtils {

	/**
	 * @param args
	 * @throws IOException
	 */
	public void sendMessage(String phoneNumber,String activationCode) throws IOException{
		//发送内容
		String content = "您好，这是来自云安全平台的短信，验证码："+activationCode+"【云安全平台】";
		
		//创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(Configuration.getStringBuffer());
		
		//APIKEY
		sb.append("apikey="+Configuration.getApikey());
		
		//用户名
		sb.append("&username="+Configuration.getUsername());

		// 向StringBuffer追加密码
		sb.append("&password="+Configuration.getPasswordMobile());

		// 向StringBuffer追加手机号码
		sb.append("&mobile="+phoneNumber);

		// 向StringBuffer追加消息内容转URL标准码
		try {
			sb.append("&content="+URLEncoder.encode(content,"GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		// 返回发送结果
		String inputline = in.readLine();

		// 输出结果
		System.out.println(inputline);
	}
	
	public void sendAlarm(String phoneNumber,String content) throws IOException{
		
		//创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(Configuration.getStringBuffer());
		
		//APIKEY
		sb.append("apikey="+Configuration.getApikey());
		
		//用户名
		sb.append("&username="+Configuration.getUsername());

		// 向StringBuffer追加密码
		sb.append("&password="+Configuration.getPasswordMobile());

		// 向StringBuffer追加手机号码
		sb.append("&mobile="+phoneNumber);

		// 向StringBuffer追加消息内容转URL标准码
		try {
			sb.append("&content="+URLEncoder.encode(content,"GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		// 返回发送结果
		String inputline = in.readLine();

		// 输出结果
		System.out.println(inputline);
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public void sendMessageToParent(String phoneNumber) throws IOException{
		//发送内容
		String content = "您好，您申请绑定幼儿园的请求已通过，欢迎使用幼学堂系统随时了解幼儿园教学动态、获取更多幼教资源！【幼学堂】";
		
		//创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://115.28.23.78/api/send/?");
		
		//APIKEY
		sb.append("apikey=24ee61b28b763df320162a27969940c9");
		
		//用户名
		sb.append("&username=dianxin");

		// 向StringBuffer追加密码
		sb.append("&password=dianxin123");

		// 向StringBuffer追加手机号码
		sb.append("&mobile="+phoneNumber);

		// 向StringBuffer追加消息内容转URL标准码
		try {
			sb.append("&content="+URLEncoder.encode(content,"GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		// 返回发送结果
		String inputline = in.readLine();

		// 输出结果
		System.out.println(inputline);
	}
	
	
	public static void main(String[] args) throws IOException {
		//发送内容
		String content = "123,【云安全平台】";
		
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://115.28.23.78/api/send/?");
		
		// APIKEY
		sb.append("apikey=24ee61b28b763df320162a27969940c9");

		//用户名
		sb.append("&username=dianxin");

		// 向StringBuffer追加密码
		sb.append("&password=dianxin123");

		// 向StringBuffer追加手机号码
		sb.append("&mobile=18611661173");

		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content="+URLEncoder.encode(content,"GBK"));

		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		// 返回发送结果
		String inputline = in.readLine();

		// 输出结果
		System.out.println(inputline);

	}

}
