package com.cn.ctbri.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
    
    
    // 查账户信息的http地址
    private static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    //通用发送接口的http地址
    private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    // 模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";

    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

	/**
	 * @param args
	 * @throws IOException
	 */
	public void sendMessage(String phoneNumber,String activationCode) throws IOException,URISyntaxException{
		//修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后用户中心首页看到
        String apikey = Configuration.getApikey();
        //修改为您要发送的手机号
        String mobile = phoneNumber;
        /**************** 查账户信息调用示例 *****************/
//        System.out.println(getUserInfo(apikey));
        /**************** 使用通用接口发短信 *****************/
        //发短信调用示例
//        System.out.println(sendSms(apikey, text, mobile));
        
        //设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
        long tpl_id = Long.parseLong(Configuration.getRegister_model());
        //设置对应的模板变量值
        //如果变量名或者变量值中带有#&=%中的任意一个特殊符号，需要先分别进行urlencode编码
        //如code值是#1234#,需作如下编码转换
        String codeValue = URLEncoder.encode(activationCode, ENCODING);
        String tpl_value = "#code#=" + codeValue;
        //模板发送的调用示例
        System.out.println(tplSendSms(apikey, tpl_id, tpl_value, mobile));

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
	
	
	public static void main(String[] args) throws IOException {
	    String apikey = "312ba7231765d58bfb2830988c3dec82";
        //修改为您要发送的手机号
        String mobile = "18210965018";
        /**************** 使用通用接口发短信(推荐) *****************/
        //设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String text = "【云片网】您的验证码是1234";
        //发短信调用示例
        System.out.println(sendSms(apikey, text, mobile));

	}
	
    
	/**
     * 取账户信息
     *
     * @return json格式字符串
     * @throws java.io.IOException
     */
    public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        return post(URI_GET_USER_INFO, params);
    }

    /**
     * 通用接口发短信（推荐）
     *
     * @param apikey apikey
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public static String sendSms(String apikey, String text, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return post(URI_SEND_SMS, params);
    }

    /**
     * 通过模板发送短信(不推荐，建议使用通用接口)
     *
     * @param apikey    apikey
     * @param tpl_id    　模板id
     * @param tpl_value 　模板变量值
     * @param mobile    　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
