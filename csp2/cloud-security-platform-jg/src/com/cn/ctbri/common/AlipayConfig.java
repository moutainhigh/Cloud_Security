package com.cn.ctbri.common;

import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.*;
import com.alipay.api.request.*;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayConfig {

	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

		// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
		public static String app_id = "2017121900977899";
		
		// 商户私钥，您的PKCS8格式RSA2私钥
		public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCevGdi3fCG3Joej3iyWxqINy82RwbhG5TbR/t28qK0A/hLTbn/DdqG8lCX+kSK2U7zOB9qInVtcJcFWpaLi4mErNBvsoLQhagYYSyZGyAzdhdzZR+iy34PxLb+j4sV/ro0wp+yCSDRKURZXiiUL0faQWSvBWGXisDZqWvwlQ6Ifxqk3xrSITZodzSjOaeDaW5X/AQNHa5UbQGVR+tcqXPZs+59LwXrxSCq6m8zsk+MqOsta6XQ0BDezJlQ637A9AJV0W7M8rJs2ltgHojzuXKm0Dx8vtqy+FmItabkkZufOo2eysKk73XN4hgYco99ONZ+fvmXX0lB8jtqa8169ghrAgMBAAECggEBAJAYze2VNfTnK81URqf6RgKjvesS3S7an+3T0XYwWvz5oZuSiqb7h4X7UKg2G6TEEoR+TR5G+7yGecH1czZoTEUxr+HF9jSfMHI5PIgcdMXijPVHNknNWrmrxMcfvY4Q+C5PAzNWUAEv6gkKjeG/bgW2WVT8mJQ99EVjJEZ4uYG0GpEB2+35GSNCqFpIE99g+BKj3xtEHzRO9fBJQo5yzJEclGuont5qusiuVTkZE2fbOBc6/Y/DIWqQI/I/V2y1T0mJz1AxvROYsup5jqajIs+HNAUZnMpC8l0A/NptIvIhQuZZDUtThexLDtsAB0lijHWfPoIA3PcOO8cQYOR98xkCgYEAzCQH/r5FiZ1lzESATvNUrGuzjAm+WDePn4JXUf71fuTI459EVovq9/Sp7auORUy1VCEIqQ6oLv/IUBNoAKuJqg3YgTR1d90Pspev1DXvrsdQ3/KsCXmV4NRsUU95HGvf57vrGVQzsYuVuC4SaIrzE1Oc817+tJfkcwpDHGMeLUUCgYEAxw+LRf1iHkechIMXYjZpNgmc3Xt11tTnkCDTKao9R4CHeyPyxcEaMiEls+1P8RCCCaUJRSY5WPelFok6fR+1mtUZLPZHGY09rDdMQ5jC8nwgrOE+7ZcbEHsX6ia4AizMuJsnw7MsstHROccNc3YF92Noo7+psHU3UyoHzueyge8CgYBvmn84eONhYciGjSisgQFYpiTpuWXzqqR9au+8JywlrIRbVPYybpoN5xeDlRfZsR8tGEY4zoeJ8lKb3JAuVZmUe69Sq9BQsG1lkOISnU4k+livpmcBYxRabyesqEspC5zEfwr/tPXx6cL3h1CPEO/lMAANWXDcYHIHpqOcAGA+FQKBgQCxibCKzNG89dun6A9r/eFaW7p3Y0yXPa4FwvDyoS4kXoL3FfnJ3E8T1LJ+xjJda36/QV2COfQ94ClybxQmUqizHkpk6oZBSNJvjaZMR6vff0vZRM/OMc3mphKPONc2wWQZZfBSP7SnDDFSGbFqgCIUIapLGX8rrW+ZkcwDAm0YKwKBgDKfqExJ4EWu3DuJSwGlwq6UYGxN8AhnnKgLdgW4Xb+s05rCXGs7lmdt4ly+5DFVJCUlP7m1g+LnZXDYc+irLRX1lq6CwQTTb27p2as99gtFfQrYOUyE6lOowR0BLWXREGn1rtfc4HJR+3QO8+eh+1ewM4pKLCOS8VHBKXP4e3Rf";
		// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	    //public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1adKBg/VhEEy+HboJmb6bDZUVApyU1ee1+qFgeLivMJrwK/3o/tUv3tpbn+oyLO8PQTRyR8Jcl2pbYMu4rDWlc8q26W3sskGN84T3UHoaPHLzuLcq1K2u1PI6IwlZJvkrNz/eR0h1hR2gjgpo3O4kjvReAsdrBnfpBtuoDVdwPCR0cHV71TipPWhuEgb4sf5GPHE8a2DC0CUxHlWvoEPaOJ4hMYwxScLkaEu9MnRHMlrtOpC0bDZcb6tT27Zjl/cbxtRZ3INpotWbIFZgrdU5EMIcGomytQHn1djFEqW17qtYvvo/hqOaPdZpQ7PbCT5jALRqsgkK4e+qlOPov/B1QIDAQAB";
	    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnVs2TLtfM+HUo5ht4zVYoKHSabxSDVL0Ua/daatSiKC6mzL0Wm2iKT7uVQFsla/jpTc/zxKfSAAw/aVEkDTaLk/vdbBiUlt/LbzdFiEb0qP+1r61VLeeGqTJ35oINq41u2O4DekRY9AVz3Pt+8DW8dpDCGd4F0En4p3wgDIv/LzU4yhVn4Vjn5lKW20CooDIXmXxD6zAbrQ4uoKVmK9BAEshpTDH/kOzAyuwWiRMCmzFsx5+lh9mUM3NxqAFkdIIpGzDJINMFCMMv5kpuMHBaYHl2XhHvPmVPgKX4oY6W6oU4JnCwOAK/hmJJRFkvnNMIjTN5A3FKR4Zz7X2zedJLQIDAQAB";
		
		// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://124.127.116.208/alipay.trade.page.pay-JAVA-UTF-8/WebContent/notify_url.jsp";

		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String return_url = "http://124.127.116.208/alipay.trade.page.pay-JAVA-UTF-8/WebContent/return_url.jsp";

		// 签名方式
		public static String sign_type = "RSA2";
		
		// 字符编码格式
		public static String charset = "utf-8";
		
		// 支付宝网关
		public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
		
		// 支付宝网关
		public static String log_path = "C:\\";


	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
		
		/** 
	     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	     * @param sWord 要写入日志里的文本内容
	     */
	    public static void logResult(String sWord) {
	        FileWriter writer = null;
	        try {
	            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
	            writer.write(sWord);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (writer != null) {
	                try {
	                    writer.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	    
	    
	    public void  doPost(HttpServletRequest httpRequest,HttpServletResponse httpResponse) throws ServletException, IOException{
	    	AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
	    			app_id, 
	    			merchant_private_key, 
	    			"json", 
	    			charset, 
	    			alipay_public_key, 
	    			"RSA2");
	    	//实例化具体API对应的request类,类名称和接口名称对应,当前}
	    	
	    	//alipayClient.
	    	AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
	    	//alipayRequest.setReturnUrl(returnUrl);
	    	String returnUrl = "http://localhost:8080/csp/cashierUI.html";
	    	String notifyUrl = "http://localhost:8080/csp/payConfirm.html";
	    	alipayRequest.setReturnUrl(returnUrl);
	    	alipayRequest.setNotifyUrl(notifyUrl);
	    	 alipayRequest.setBizContent("{" +
	    		        "    \"out_trade_no\":\"20150320010101001\"," +
	    		        "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
	    		        "    \"total_amount\":88.88," +
	    		        "    \"subject\":\"Iphone6 16G\"," +
	    		        "    \"body\":\"Iphone6 16G\"," +
	    		        "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
	    		        "    \"extend_params\":{" +
	    		        "    \"sys_service_provider_id\":\"2088511833207846\"" +
	    		        "    }"+
	    		        "  }");//填充业务参数
	    	 
	    	 String form="";
	    	    try {
	    	        form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
	    	    } catch (AlipayApiException e) {
	    	        e.printStackTrace();
	    	    }
	    	    
	    	    httpResponse.setContentType("text/html;charset=" + charset);
	    	    httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
	    	    httpResponse.getWriter().flush();
	    	    httpResponse.getWriter().close();
	    	    
	    	    
	    	
	    }
		
		
}
