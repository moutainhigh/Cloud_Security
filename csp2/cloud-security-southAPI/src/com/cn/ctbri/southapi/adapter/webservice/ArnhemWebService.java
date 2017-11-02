package com.cn.ctbri.southapi.adapter.webservice;

import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.net.ssl.SSLContext;



import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;





import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Component
@Path("arnhem")
public class ArnhemWebService {
	//日志对象
	private Logger log = Logger.getLogger(this.getClass());

	//主动告警
	@POST
    @Path("arnhemGetWarn")
	@Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_XML)
    public String arnhemGetWarn(@Context HttpServletRequest request,@Context HttpServletResponse response) {
	    response.setContentType("Application/xml");
        try {
            ServletInputStream io = request.getInputStream();
            //判断流对象是否存在
            if(io != null){
                //创建流读取对象
                SAXReader reader = new SAXReader();
                //读取流封装为文档对象
                Document document = reader.read(io);
                System.err.println(document.asXML());
//              String str = request.getParameter("xmlstr");
//              decode = URLDecoder.decode(str, "UTF-8");
//              Document document = DocumentHelper.parseText(decode);
                return postMethod("http://101.200.234.126:80/cspi/ws/arnhem/arnhemGetWarn", document.asXML());
                
           

                
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("[任务主动告警]:任务主动告警-解析任务主动告警发生异常!");
            throw new RuntimeException("[任务主动告警]:任务主动告警-解析任务主动告警发生异常!");
        }
        return "<Result value=\"Success\"></Result>";
    }
	private  String postMethod(String url, String xml) {
		//创建客户端配置对象
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//连接服务器
		WebResource service = client.resource(url);
		
		//获取响应结果
		String response = service.type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class, xml);
		
		//For 2
		client.destroy();
		return response;
	}
	private  void buildConfig(String url,ClientConfig config) {
		if(url.startsWith("https")) {
        	SSLContext ctx = getSSLContext();
        	config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
        		     new HostnameVerifier() {
        		         public boolean verify( String s, SSLSession sslSession ) {
        		             return true;
        		         }
        		     }, ctx
        		 ));
        }
	}
	private  SSLContext getSSLContext() {
		//创建认证管理器
    	TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
    	    public java.security.cert.X509Certificate[] getAcceptedIssuers(){return null;}
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
    	}};
    	try {
    		//创建安全传输层对象
    	    SSLContext sc = SSLContext.getInstance("SSL");
    	    //初始化参数
    	    sc.init(null, trustAllCerts, new SecureRandom());
    	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    	    return sc;
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	return null;
    }

}
