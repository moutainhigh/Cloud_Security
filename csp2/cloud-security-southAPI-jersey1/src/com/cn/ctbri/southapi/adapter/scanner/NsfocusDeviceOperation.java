package com.cn.ctbri.southapi.adapter.scanner;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cn.ctbri.southapi.adapter.bean.TaskInfo;
import com.cn.ctbri.southapi.adapter.bean.URLInfo;
import com.cn.ctbri.southapi.adapter.bean.VulInfo;
import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;
import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;
import com.cn.ctbri.southapi.adapter.utils.PropertiesUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class NsfocusDeviceOperation extends CommonDeviceOperation {
	private static String API_CONFIG_NAME = "NsfocusScannerAPI";
	private static String nsfocusServerWebrootUrl = "";
	private static String username;
	private static String password;
	private static String connectSessionId = "";
	static{

		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			username = p.readValue("username");
			password = p.readValue("password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//用于格式化配置文件，给配置信息赋值
	private String formatConfig(String[] params,String configKey) throws IOException{
		PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
		String fcString = MessageFormat.format(p.readValue(configKey), params);
		return fcString;
		
	}
	
	public boolean createSessionId(String username, String password, String serverWebRoot) {
		NsfocusDeviceOperation.username = username;
		NsfocusDeviceOperation.password = password;
		nsfocusServerWebrootUrl = serverWebRoot;
		String url = nsfocusServerWebrootUrl+"/httprpc/login/";
		ClientConfig config = new DefaultClientConfig();
		//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		try {
			String[] params ={username,password};
			String xmlContent = formatConfig(params, "task.login");

			


			//连接服务器
			WebResource service = client.resource(url);
			//获取响应结果
			ClientResponse response = service.type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(ClientResponse.class, xmlContent);
			
			String[] cookie = response.getCookies().get(0).toString().split(";");
			HashMap<String, String> cookieHashMap = new HashMap<String, String>();
			for (int i = 0; i < cookie.length; i++) {
				String[] s = cookie[i].split("=");
				cookieHashMap.put(s[0], s[1]);
			}

			connectSessionId = cookieHashMap.get("sessionid");
			System.out.println(connectSessionId);
			return true;
		} catch (UniformInterfaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			client.destroy();
		}
	}
	
	private  String postMethod(String url, String xml) {
		//创建客户端配置对象
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//
		//连接服务器
		WebResource service = client.resource(url);
		//获取响应结果
		ClientResponse response = service.cookie(new Cookie("sessionid", connectSessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(ClientResponse.class, xml);
		String cookie = response.getCookies().toString();
		String body = response.getEntity(String.class);
		//For 2
		client.destroy();
		return body;
	}
	
	/**
	 * 功能描述：post方法请求(不填充xml)
	 * @time 
	 * 
	 * 
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
        String response = service.cookie(new NewCookie("sessionid",connectSessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class);
        client.destroy();
        return response;
	}
	
	/**
	 * 下发新建任务
	 * @time 2017-10-12
	 * @param scannerTaskUniParam
	 * @return 
	 */
	//暂停使用该方法
	//TODO
	public String disposeScanTask2(ScannerTaskUniParam scannerTaskUniParam) {
		String xml ="xml=<?xml version='1.0' encoding='utf-8' ?>"+"<task><base>"
					+"<task_name><![CDATA["+scannerTaskUniParam.getTaskId()+"]]></task_name>"
					+"<target><![CDATA["+scannerTaskUniParam.getDestIP()+"]]></target>"
					+"<description><![CDATA["+"test task"+"]]></description>"
					+"<plugin_template_id><![CDATA["+"0"+"]]></plugin_template_id></base>"
					+"<webauth><item><login_auth>Basic</login_auth>"
					+"<login_username><![CDATA["+username+"]]></login_username>"
					+"<login_userpwd><![CDATA["+password+"]]></login_userpwd>"
					+"<cookie><![CDATA[]]></cookie></item>"
					+"</webauth></task>";
		
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/newTask/", xml);
	}
	/**
	 * 下发新建任务
	 * @return
	 */
	//可正常新建并下发
	public String disposeScanTask(ScannerTaskUniParam scannerTaskUniParam) {
		
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId(),scannerTaskUniParam.getDestIP(),"0",username,password};
			String xml = MessageFormat.format(p.readValue("task.dispose"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/newTask/", xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

	}
	/**
	 * 暂停任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	//TODO
	public String pauseTask(ScannerTaskUniParam scannerTaskUniParam) {
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId()};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/pauseTask", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
	}
	/**
	 * 继续任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	//TODO
	public String resumeTask(ScannerTaskUniParam scannerTaskUniParam) {
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId()};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/resumeTask", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
	}
	/**
	 * 停止任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	//TODO
	public String stopTask(ScannerTaskUniParam scannerTaskUniParam){
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId()};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/stopTask", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
	}
	/**
	 * 断点续扫
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String createTask(ScannerTaskUniParam scannerTaskUniParam) {
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId()};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/createTask", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
		
	}
	/**
	 * 重新扫描
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String rescanTask(ScannerTaskUniParam scannerTaskUniParam) {
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId()};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/rescanTask", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}	
	}
	/**
	 * 删除任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String  deleteTask(ScannerTaskUniParam scannerTaskUniParam) {
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId()};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/deleteTask", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
	}
	/**
	 * 暂停周期任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String pauseCrontask(ScannerTaskUniParam scannerTaskUniParam){
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId()};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/pauseCrontask", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
	}
	/**
	 * 继续扫描周期任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String createCrontask(ScannerTaskUniParam scannerTaskUniParam) {
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId()};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/createCrontask", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}	
	}
	/**
	 * 获取任务状态
	 * @time 2017-10-12
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String getStatusByTaskId(ScannerTaskUniParam scannerTaskUniParam) {
		
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId(),scannerTaskUniParam.getDestIP(),"0",username,password};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/taskStatus/", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
			
	}
	/**
	 * 获取模板列表
	 * @time 2017-10-12
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String getTemplate() {
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/pluginTemplateList/");	
	}
	/**
	 * 获取任务报表
	 * @time 2017-10-12
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String getReportByTaskId(ScannerTaskUniParam scannerTaskUniParam) {
		try {
			PropertiesUtil p = new PropertiesUtil(API_CONFIG_NAME);
			String[] params = {scannerTaskUniParam.getTaskId()};
			String xml = MessageFormat.format(p.readValue("task.id"), params);
			return postMethod(nsfocusServerWebrootUrl+"/httprpc/getTaskreport", xml);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
	}
	public void getNsfocusReport(String nsfocusReportString) {
		try {
			Document doc = DocumentHelper.parseText(nsfocusReportString);
			Element root = doc.getRootElement();
			String name = root.elementText("task_name");
			String riskpoint = root.elementText("risk_point");
			String targets = root.elementText("targets");
			String vulCount = root.elementText("vuln_file");
			String startTime = root.elementText("start_time");
			Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
			String time = root.elementText("end_time");
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
			Element vuls = root.element("vuln_list");
			@SuppressWarnings("unchecked")
			List<Element> vulInfoList = vuls.elements("vul_info");
			List<VulInfo> vulInfos = new ArrayList<VulInfo>();
			for (Element vulInfoElement : vulInfoList) {
				String vulInfoId = vulInfoElement.elementTextTrim("vul_id");
				String typeName = vulInfoElement.elementTextTrim("i18n_name");
				String typeDescription = vulInfoElement.elementTextTrim("i18n_description");
				String advice = vulInfoElement.elementTextTrim("i18n_solution");
				String serverityPoints = vulInfoElement.elementTextTrim("severity_points");
				Element urls = vulInfoElement.element("urls");
				@SuppressWarnings("unchecked")
				List<Element> urlInfoList = urls.elements("url");
				List<URLInfo> urlInfos = new ArrayList<URLInfo>();
				for (Element urlInfoElement : urlInfoList) {
					String urlString = urlInfoElement.elementTextTrim("url_info");
					String urlParam = urlInfoElement.elementTextTrim("vul_params");
					String verifyUrl = urlInfoElement.elementTextTrim("verify_url");
					URLInfo urlInfo = new URLInfo();
					urlInfo.setUrlString(urlString);
					urlInfo.setUrlParam(urlParam);
					urlInfo.setVerifyUrl(verifyUrl);
					urlInfos.add(urlInfo);
				}
				VulInfo vulInfo = new VulInfo();
				vulInfo.setVulInfoId(vulInfoId);
				vulInfo.setTypeName(typeName);
				vulInfo.setTypeDescription(typeDescription);
				vulInfo.setAdvice(advice);
				vulInfo.setLevel(serverityPoints);
				vulInfo.setUrlInfos(urlInfos);
			}
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setSiteName(name);
			taskInfo.setAlarmTime(date);
			taskInfo.setSiteName(targets);
			taskInfo.setStartTime(startDate);
			taskInfo.setVulCount(vulCount);
			taskInfo.setRiskpoint(riskpoint);
			taskInfo.setVulInfos(vulInfos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void temp() {
		NsfocusDeviceOperation operation = new NsfocusDeviceOperation();
		nsfocusServerWebrootUrl = "https://192.168.50.184";
		operation.createSessionId(operation.username,operation.password, nsfocusServerWebrootUrl);
		ScannerTaskUniParam scannerTaskUniParam = new ScannerTaskUniParam();
		scannerTaskUniParam.setTaskId("test");
		scannerTaskUniParam.setDestIP("http://www.testfire.net");
		String taskString = operation.disposeScanTask2(scannerTaskUniParam);
		System.out.println(taskString);

		try {
			SAXReader reader = new SAXReader();
			Document doc= reader.read(IOUtils.toInputStream(taskString));
			Element ele = doc.getRootElement();
			String eString = ele.element("response_msg").getStringValue();
			System.out.println(eString);
			String pString = eString.substring(eString.indexOf("任务号:")+4);
			System.out.println(pString);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		NsfocusDeviceOperation operation = new NsfocusDeviceOperation();
		operation.temp();
//		nsfocusServerWebrootUrl = "https://192.168.50.184";
//		operation.createSessionId(operation.username,operation.password, nsfocusServerWebrootUrl);
//		ScannerTaskUniParam scannerTaskUniParam = new ScannerTaskUniParam();
//		scannerTaskUniParam.setTaskId("49");
//		scannerTaskUniParam.setDestIP("http://www.testfire.net");
//		String taskString = operation.getStatusByTaskId(scannerTaskUniParam);
//		System.out.println(taskString);
	}
}
