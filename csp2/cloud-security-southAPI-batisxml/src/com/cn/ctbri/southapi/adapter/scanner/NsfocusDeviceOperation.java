package com.cn.ctbri.southapi.adapter.scanner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.cn.ctbri.southapi.adapter.bean.TaskInfo;
import com.cn.ctbri.southapi.adapter.bean.URLInfo;
import com.cn.ctbri.southapi.adapter.bean.VulInfo;
import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;
import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class NsfocusDeviceOperation extends CommonDeviceOperation {
	private static String nsfocusServerWebrootUrl = "";
	private String username = "admin";
	private String password = "1qa2ws3ed";
	private String connectSessionId = "";
	

	
	public boolean createSessionId(String username, String password, String serverWebRoot) {
		this.username = username;
		this.password = password;
		nsfocusServerWebrootUrl = serverWebRoot;
		
		String xmlContent = "xml=<?xml version='1.0' encoding='utf-8' ?><root>"
				+"<username><![CDATA["+username+"]]></username>"
				+"<password><![CDATA["+password+"]]></password></root>";
		String url = nsfocusServerWebrootUrl+"/httprpc/login/";
		
		ClientConfig config = new DefaultClientConfig();
		//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);

		try {
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
			return true;
		} catch (UniformInterfaceException e) {
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
		return cookie+"/r/n"+body;
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
	 * @param scannerTaskUniParam
	 * @return
	 */
	//调整格式中
	public String disposeScanTask(ScannerTaskUniParam scannerTaskUniParam) {
		String xml ="xml=<?xml version='1.0' encoding='utf-8' ?>"+"<task><base>"
					+"<task_name><![CDATA["+scannerTaskUniParam.getTaskId()+"]]></task_name>"
					+"<target><![CDATA["+scannerTaskUniParam.getDestIP()+"]]></target>"
					+"<descripition><![CDATA["+""+"]]></description>"
					+"<plugin_template_id><![CDATA["+"0"+"]]></plugin_template_id></base>"
					+"<webauth><item><login_auth>Basic</login_auth>"
					+"<login_username><!CDATA["+username+"]]></login_username>"
					+"<login_userpwd><!CDATA["+password+"]]></login_passpwd>"
					+"<cookie><!CDATA[]]></cookie></item>"
					+"</webauth></task>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/newTask/", xml);
	}
	/**
	 * 下发新建任务
	 * @return
	 */
	//可正常新建并下发
	public String disposeScanTask2(ScannerTaskUniParam scannerTaskUniParam) {
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><task><base>"+
					"<task_name><![CDATA["+scannerTaskUniParam.getTaskId()+"]]></task_name>"+
					"<target><![CDATA["+scannerTaskUniParam.getDestURL()+"]]></target></base>"+
					"<description><![CDATA[this is a httprpctask]]></description>"+
					"<plugin_template_id><![CDATA["+scannerTaskUniParam.getTaskSLA()+"]]></plugin_template_id>"+
					"<webauth></webauth></task>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/newTask/", xml);
	}
	/**
	 * 暂停任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String pauseTask(ScannerTaskUniParam scannerTaskUniParam) {
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/pauseTask/", xml);
	}
	/**
	 * 继续任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String resumeTask(ScannerTaskUniParam scannerTaskUniParam) {
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/pauseTask/", xml);
	}
	/**
	 * 停止任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String stopTask(ScannerTaskUniParam scannerTaskUniParam){
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/stopTask/", xml);
	}
	/**
	 * 断点续扫
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String createTask(ScannerTaskUniParam scannerTaskUniParam) {
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/createTask/", xml);	
		
	}
	/**
	 * 重新扫描
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String rescanTask(ScannerTaskUniParam scannerTaskUniParam) {
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/rescanTask/", xml);			
	}
	/**
	 * 删除任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String  deleteTask(ScannerTaskUniParam scannerTaskUniParam) {
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/deleteTask/", xml);		
	}
	/**
	 * 暂停周期任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String pauseCrontask(ScannerTaskUniParam scannerTaskUniParam){
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/pauseCrontask/", xml);		
	}
	/**
	 * 继续扫描周期任务
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String createCrontask(ScannerTaskUniParam scannerTaskUniParam) {
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/createCrontask/", xml);			
	}
	/**
	 * 获取任务状态
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String getStatusByTaskId(ScannerTaskUniParam scannerTaskUniParam) {
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/taskStatus/", xml);			
	}
	/**
	 * 获取模板列表
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String getTemplate() {
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/pluginTemplateList/");	
	}
	/**
	 * 获取任务报表
	 * @param scannerTaskUniParam
	 * @return
	 */
	public String getReportByTaskId(ScannerTaskUniParam scannerTaskUniParam) {
		String xml="xml=<?xml version='1.0' encoding='utf-8' ?><root><task_id>"+scannerTaskUniParam.getTaskId()+"</task_id></root>";
		return postMethod(nsfocusServerWebrootUrl+"/httprpc/getTaskreport/", xml);		
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
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		NsfocusDeviceOperation operation = new NsfocusDeviceOperation();
		nsfocusServerWebrootUrl = "";
	}
}
