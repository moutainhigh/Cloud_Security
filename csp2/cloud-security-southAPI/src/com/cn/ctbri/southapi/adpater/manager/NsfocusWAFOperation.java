package com.cn.ctbri.southapi.adpater.manager;

import com.cn.ctbri.southapi.adpater.config.waf.nsfocus.Syslog;
import com.cn.ctbri.southapi.adpater.config.waf.nsfocus.Trans;
import com.cn.ctbri.southapi.adpater.config.waf.nsfocus.WafConfig;
import com.cn.ctbri.southapi.adpater.config.waf.nsfocus.WafDevice;
import com.cn.ctbri.southapi.adpater.config.waf.nsfocus.WafDeviceGroup;
import com.cn.ctbri.southapi.adpater.config.waf.nsfocus.WafLog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thoughtworks.xstream.XStream;


public class NsfocusWAFOperation extends CommonDeviceOperation {
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
		ClientResponse response = service.type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(ClientResponse.class, xml);
		String cookie = response.getCookies().toString();
		String body = response.getEntity(String.class);
		//For 2
		return cookie+"/r/n"+body;
	}
	
	/**
	 * 功能描述：post方法请求(不填充xml)
	 * @time 
	 * 2015-10-21
	 * @param url
	 * @param sessionId
	 * @return String响应结果
	 */
	private   String postMethod(String url) {
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //service.type("application/x-download");
        //连接服务器，返回结果
        //String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).delete(String.class);
        String response = service.type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class);
        return response;
	}
	private String getMethod(String url){
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter("admin", "nsfocus"));
        WebResource service = client.resource(url);
        System.out.println(service.toString());
        String response = service.type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_JSON).get(String.class);
        return response;
    }
	public WafConfig getWafConfig() throws DocumentException {
		WafConfig wafConfig = new WafConfig();
		SAXReader reader = new SAXReader();
		Document document = reader.read("./conf/WafConfig.xml");
		List<?> list = document.selectNodes("/WAF_CONFIG/WAFDeviceManage/WAFDeviceGroup");
		List<WafDeviceGroup> wafDeviceGroups = new ArrayList<WafDeviceGroup>();
		Iterator<?> iterator = list.iterator();
		while (iterator.hasNext()) {
			Element elementWAFDeviceGroup = (Element) iterator.next();
			WafDeviceGroup wafDeviceGroup = new WafDeviceGroup();
			for(Iterator<?> it = elementWAFDeviceGroup.elementIterator();it.hasNext();){
				Element element = (Element) it.next();
				if ("ResourceID".equalsIgnoreCase(element.getName())) {
					if ( null == element.getTextTrim() || "".equals(element.getTextTrim())){ 	
            		  	System.err.println("Config Error:  is null!!!");
            		  	continue;
					}else {
						wafDeviceGroup.setResourceID(element.getTextTrim());
						System.out.println(element.getTextTrim());
					}
				}
				if ("ResourceURI".equalsIgnoreCase(element.getName())) {
					if ( null == element.getTextTrim() || "".equals(element.getTextTrim())) {
	           		  	System.err.println("Config Error:  is null!!!");
            		  	continue;
					} else {
						wafDeviceGroup.setResourceURI(element.getTextTrim());
						System.out.println(wafDeviceGroup.getResourceURI());
					}
				}
				if ("DeployMode".equalsIgnoreCase(element.getName())) {
					if ( null == element.getTextTrim() || "".equals(element.getTextTrim())) {
	           		  	System.err.println("Config Error:  is null!!!");
            		  	continue;
					} else {
						wafDeviceGroup.setDeployMode(element.getTextTrim());
						System.out.println(wafDeviceGroup.getDeployMode());
					}
				}
				List<WafDevice> wafDevices = new ArrayList<WafDevice>();
				if ("WAFDeviceList".equalsIgnoreCase(element.getName())) {
					WafDevice wafDevice = new WafDevice();
					Document wafDeviceDocument = DocumentHelper.parseText(element.asXML());	
					List<?> wafDeviceRootList = wafDeviceDocument.selectNodes("/WAFDeviceList/WAFDevice");
					Iterator<?> wafDeviceListIterator = wafDeviceRootList.iterator();
					while (wafDeviceListIterator.hasNext()) {
						Element wafDeviceElement  = (Element) wafDeviceListIterator.next();
						for(Iterator<?> wafDeviceIterator = wafDeviceElement.elementIterator();wafDeviceIterator.hasNext();){
							Element wafDevParam = (Element) wafDeviceIterator.next();
							if ("WAFDevID".equalsIgnoreCase(wafDevParam.getName())) {
								if ( null == wafDevParam.getTextTrim() || "".equals(wafDevParam.getTextTrim())) {
				           		  	System.err.println("Config Error: WAFDevID  is null!!!");
			            		  	continue;
								} else {
									wafDevice.setWAFDevID(wafDevParam.getTextTrim());
									System.out.println(wafDevParam.getTextTrim());
								}
							}
							if ("WAFFactory".equalsIgnoreCase(wafDevParam.getName())) {
								if ( null == wafDevParam.getTextTrim() || "".equals(wafDevParam.getTextTrim())) {
				           		  	System.err.println("Config Error: WAFFactory  is null!!!");
			            		  	continue;
								} else {
									wafDevice.setWAFFactory(wafDevParam.getTextTrim());
									System.out.println(wafDevParam.getTextTrim());
								}								
							}
							if ("WAFFactoryName".equalsIgnoreCase(wafDevParam.getName())) {
								if ( null == wafDevParam.getTextTrim() || "".equals(wafDevParam.getTextTrim())) {
				           		  	System.err.println("Config Error: WAFFactory  is null!!!");
			            		  	continue;
								} else {
									wafDevice.setWAFFactoryName(wafDevParam.getTextTrim());
									System.out.println(wafDevParam.getTextTrim());
								}
							}
							if ("WAFDevPhyIP".equalsIgnoreCase(wafDevParam.getName())) {
								if ( null == wafDevParam.getTextTrim() || "".equals(wafDevParam.getTextTrim())) {
				           		  	System.err.println("Config Error: WAFDevPhyIP  is null!!!");
			            		  	continue;
								} else {
									wafDevice.setWAFDevPhyIP(wafDevParam.getTextTrim());
									System.out.println(wafDevParam.getTextTrim());
								}
							}
							if ("WAFDevAPI".equalsIgnoreCase(wafDevParam.getName())) {
								for(Element apiElement:(List<Element>)wafDevParam.elements()){
									if ("APIAddr".equalsIgnoreCase(apiElement.getName())) {
										if (null == apiElement.getTextTrim()||"".equals(apiElement.getTextTrim())) {
											System.err.println("Config Error:APIAddr is null!!!");
											continue;
										} else {
											wafDevice.setAPIAddr(apiElement.getTextTrim());
											System.out.println(wafDevice.getAPIAddr());
										}
									}else if ("APIKey".equalsIgnoreCase(apiElement.getName())) {
										if (null == apiElement.getTextTrim()||"".equals(apiElement.getTextTrim())) {
											System.err.println("Config Error:APIKey is null!!!");
											continue;
										} else {
											wafDevice.setAPIKey(apiElement.getTextTrim());
											System.out.println(wafDevice.getAPIKey());
										}
									}else if ("APIValue".equalsIgnoreCase(apiElement.getName())) {
										if (null == apiElement.getTextTrim()||"".equals(apiElement.getTextTrim())) {
											System.err.println("Config Error:APIValue is null!!!");
											continue;
										} else {
											wafDevice.setAPIValue(apiElement.getTextTrim());
											System.out.println(wafDevice.getAPIValue());
										}
									}else if ("APIUserName".equalsIgnoreCase(apiElement.getName())) {
										if (null == apiElement.getTextTrim()||"".equals(apiElement.getTextTrim())) {
											System.err.println("Config Error:APIValue is null!!!");
											continue;
										} else {
											wafDevice.setAPIUserName(apiElement.getTextTrim());
											System.out.println(wafDevice.getAPIUserName());
										}
									}else if ("APIPwd".equalsIgnoreCase(apiElement.getName())) {
										if (null == apiElement.getTextTrim()||"".equals(apiElement.getTextTrim())) {
											System.err.println("Config Error:APIPassword is null!!!");
											continue;
										} else {
											wafDevice.setAPIPwd(apiElement.getTextTrim());
											System.out.println(wafDevice.getAPIPwd());
										}
									}
									
								}
							}else if ("WAFDevSyslog".equalsIgnoreCase(wafDevParam.getName())) {
								wafDevice.setIdentifyType(wafDevParam.attributeValue("IdentifyType"));
								wafDevice.setSyslogVer(wafDevParam.attributeValue("SyslogVer"));
								wafDevice.setSyslogCode(wafDevParam.attributeValue("SyslogCode"));
								if (null==wafDevParam.element("WAFDevTag")) {
									System.err.println("Config Error:WAFDevTag is not exist!!!");
								}else{
									wafDevice.setWAFDevTag(wafDevParam.elementText("WAFDevTag"));
									System.out.println(wafDevice.getWAFDevTag());
								}
							}
						}
					}
					wafDevices.add(wafDevice);
					
				}
			wafDeviceGroup.setWAFDeviceList(wafDevices);
			wafDeviceGroups.add(wafDeviceGroup);
			}
			wafConfig.setWafDeviceGroups(wafDeviceGroups);
		}
		List<?> list2 = document.selectNodes("/WAF_CONFIG/SyslogGroup");
		Iterator<?> iterator2 = list2.iterator();
		List<Syslog> syslogList = new ArrayList<Syslog>();
		while (iterator2.hasNext()) {
			Element syslogGroupElement = (Element) iterator2.next();
			for(Iterator<?> syslogGroupIterator = syslogGroupElement.elementIterator();syslogGroupIterator.hasNext();){
				Element syslogElement =(Element)syslogGroupIterator.next();
				Syslog syslog = new Syslog();
				syslog.setSyslogVer(syslogElement.attributeValue("Ver"));
				syslog.setRegexTag(syslogElement.attributeValue("RegexTag"));
				List<WafLog> waflogList = new ArrayList<WafLog>();
				for(Iterator<?> syslogIterator = syslogElement.elementIterator();syslogIterator.hasNext();){
					Element waflogeElement = (Element) syslogIterator.next();
					WafLog wafLog =new WafLog();
					wafLog.setItem(waflogeElement.attributeValue("item"));
					wafLog.setTag(waflogeElement.attributeValue("tag"));
					Element dboptElement = waflogeElement.element("dbopt");
					wafLog.setDboptSql(dboptElement.attributeValue("sql"));
					Element matchElement = waflogeElement.element("match");
					wafLog.setMatchReg(matchElement.attributeValue("reg"));
					List<Trans> transList = new ArrayList<Trans>();
					for(Iterator<?> matchIterator=matchElement.elementIterator();matchIterator.hasNext();){
						Trans trans = new Trans();
						Element transElement = (Element)matchIterator.next();
						trans.setId(transElement.attributeValue("id"));
						trans.setName(transElement.attributeValue("name"));
						trans.setExpress(transElement.attributeValue("express"));
						transList.add(trans);
					}
					wafLog.setTrans(transList);
					waflogList.add(wafLog);	
				}
				syslog.setWafLog(waflogList);
				syslogList.add(syslog);
			}
			wafConfig.setSyslogGroup(syslogList);
		}
		return wafConfig;
	}
	public static Iterator<?> loadInIterator(String xmlString,String nodes) throws DocumentException {
		Document document = DocumentHelper.parseText(xmlString);
		List<?> list = document.selectNodes(nodes);
		Iterator<?> iterator = list.iterator();
		return iterator;
	}
	/*public static String setParam(String pname,Element element) {
		Object object = new Object();
		object.
		if (pname.equalsIgnoreCase(element.getName())) {
			
		} else {

		}
	}*/
	public WafConfig getWafConfigStream(){
		XStream xStream = new XStream();
		WafConfig wafConfig = (WafConfig)xStream.fromXML("./conf/WafConfig.xml");
		System.out.println(wafConfig.getWafDeviceGroups().toString());
		return wafConfig;
		
	}
	
	public static String getWAFAuth(String apiKey, String apiValue, String method) {
		long timestamp = System.currentTimeMillis();
		String apivalue1 = apiValue;
		String signature = "apikey="+apiKey+
							"method="+method+
							"timestamp="+System.currentTimeMillis()+apivalue1;
			//System.out.println(signature);
		String md5String = getMd5(signature);
			//System.out.println(md5String);
		String authString = "?timestamp="+timestamp+
							"&apikey="+apiKey+
							"&method=get&sign="+md5String;
		return authString;

	}
	public static String getMd5(String plainText) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(plainText.getBytes());  
            byte b[] = md.digest();  
            int i;  
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            //32位加密  
            return buf.toString();  
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return null;  
        }  
  
    }
	
	public String getText(String url) {
		String authString = getWAFAuth("vmwaf", "E34A-44A6-E12B-E1C9", "get");
		String returnString = getMethod(url+authString);
		return returnString;
	}
	
	public static void main(String[] args) {
		NsfocusWAFOperation operation = new NsfocusWAFOperation();
		try {
			WafConfig wafConf = operation.getWafConfig();
			System.out.println("haha="+wafConf.getWafDeviceGroups().get(0).getWAFDeviceList().get(0).getAPIAddr());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(operation.getText("https://219.141.189.189:58442/rest/v1/sites"));
	}
	
}









