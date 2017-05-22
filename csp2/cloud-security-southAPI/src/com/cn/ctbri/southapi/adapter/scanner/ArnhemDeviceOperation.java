package com.cn.ctbri.southapi.adapter.scanner;

import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;
import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.*;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ArnhemDeviceOperation extends CommonDeviceOperation {

	static Logger logger = Logger.getLogger(ArnhemDeviceOperation.class.getName());

	private String arnhemServerWebrootUrl = "https://219.141.189.187:61443";
	private String username = "";
	private String password = "";
	private String connectSessionId = "";

	public ArnhemDeviceOperation() {
	}

	/**
	 * 功能描述：空字符串转化方法 参数描述:String str 要转化的字符
	 * 
	 * @time 2015-01-05
	 */
	private String nullFilter(String str) {
		return str == null ? "" : str;
	}

	private WebTarget createBasicWebTarget(String targetUrl) {
		SSLContext ctx = getSSLContext();
		ClientBuilder clientBuilder = ClientBuilder.newBuilder().sslContext(ctx);
		HostnameVerifier allowAll = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// buildConfig(targetUrl, clientBuilder);
		Client c = clientBuilder.hostnameVerifier(allowAll).build();
		// 连接服务器
		WebTarget webTarget = c.target(targetUrl);
		return webTarget;
	}

	/**
	 * 序号：4.1 功能描述： 获取SessionId
	 * 
	 * @time 2015-01-05
	 */
	public boolean createSessionId(String username, String password, String serverWebRoot) {
		// 登陆信息
		this.username = username;
		this.password = password;
		arnhemServerWebrootUrl = serverWebRoot;
		String xmlContent = "<Login><Name>" + username + "</Name><Password>" + password + "</Password></Login>";
		// 登陆服务器地址
		String url = "/rest/login";

		WebTarget webTarget = createBasicWebTarget(arnhemServerWebrootUrl);

		try {

			String response = webTarget.path(url).request()
					.post(Entity.entity(xmlContent, MediaType.APPLICATION_XML_TYPE), String.class);
			System.out.println(response);
			if (response == null) {
				return false;
			}
			// 创建XML解析对象
			SAXReader reader = new SAXReader();
			// 加载XML
			Document doc = reader.read(IOUtils.toInputStream(response));
			// 解析且获取回话ID
			Element ele = doc.getRootElement();
			Element s = ele.element("SessionId");
			connectSessionId = s.getText();

			arnhemServerWebrootUrl = serverWebRoot;

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// client.destroy();
		}
	}

	private String postMethod(String url, String xml) {
		WebTarget webTarget = createBasicWebTarget(arnhemServerWebrootUrl);
		Response response = webTarget.path(url).request().cookie(new NewCookie("sessionid",connectSessionId)).post(Entity.entity(xml, MediaType.APPLICATION_XML_TYPE),
				Response.class);
		String responseString = response.readEntity(String.class);
		System.out.println(responseString);
		// 解析响应结果内容，如果登录错误重新登录
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(IOUtils.toInputStream(responseString));
			Element rootElement = document.getRootElement();
			String value = rootElement.attributeValue("value");
			if ("AuthErr" == value && createSessionId(username, password, arnhemServerWebrootUrl)) {
				response = webTarget.path(url).request()
						.post(Entity.entity(xml, MediaType.APPLICATION_XML_TYPE), Response.class);
				return response.readEntity(String.class);
			} else {
				return responseString;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return responseString;
		} finally {
			response.close();
		}
	}
	
	private String postMethod(String url) {
		WebTarget webTarget = createBasicWebTarget(arnhemServerWebrootUrl);
		Response response = webTarget.path(url).request().cookie(new NewCookie("sessionid",connectSessionId)).post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE),
				Response.class);
		String responseString = response.readEntity(String.class);
		System.out.println(responseString);
		// 解析响应结果内容，如果登录错误重新登录
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(IOUtils.toInputStream(responseString));
			Element rootElement = document.getRootElement();
			String value = rootElement.attributeValue("value");
			if ("AuthErr" == value && createSessionId(username, password, arnhemServerWebrootUrl)) {
				response = webTarget.path(url).request().cookie(new NewCookie("sessionid",connectSessionId))
						.post(Entity.entity("", MediaType.APPLICATION_XML_TYPE), Response.class);
				return response.readEntity(String.class);
			} else {
				return responseString;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return responseString;
		} finally {
			response.close();
		}
	}

	/**
	 * 功能描述：post方法请求(不填充xml)
	 * 
	 * @time
	 * 
	 * 
	 * 		2015-10-21
	 * @param url
	 * @param sessionId
	 * @return String响应结果
	 */
	

	/**
	 * 功能描述：get方法请求 参数描述:String url 请求路径, String sessionId 回话ID
	 * 
	 * @param url
	 * @param sessionId
	 * @return String response响应结果
	 * @time 2015-01-05
	 */
	private String getMethod(String url){
		WebTarget webTarget = createBasicWebTarget(arnhemServerWebrootUrl);
		Response response = webTarget.path(url).request().cookie(new NewCookie("sessionid",connectSessionId)).get();
		String responseString = response.readEntity(String.class);
		// 解析响应结果内容，如果登录错误重新登录
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(IOUtils.toInputStream(responseString));
			Element rootElement = document.getRootElement();
			String value = rootElement.attributeValue("value");
			if ("AuthErr" == value && createSessionId(username, password, arnhemServerWebrootUrl)) {
				response = webTarget.path(url).request().cookie(new NewCookie("sessionid",connectSessionId))
						.get();
				return response.readEntity(String.class);
			} else {
				return responseString;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return responseString;
		} finally {
			response.close();
		}
	}
	
	


	/**
	 * 4.2.1 获取平台当前扫描状态
	 * 
	 * @param sessionId
	 *            会话id
	 * @param 引擎地址
	 *            engine_api
	 * @return 任务状态代码
	 */
	public String getState() {
		String url = "/rest/control/GetState";
		return getMethod(url);
	}

	/**
	 * 4.2.2 引擎的整体启动
	 * 
	 * @param sessionId
	 * @param 引擎地址
	 *            engine_api
	 * @return 任务状态代码
	 */
	public String startEngine() {
		String url = "/rest/control/Start";
		return postMethod(url);
	}

	/**
	 * 4.2.2 引擎的整体停止
	 * 
	 * @param sessionId
	 * @param 引擎地址
	 *            engine_api
	 * @return 任务状态代码
	 */
	/*
	 * public String stopEngine(String engine_api){ String url =
	 * engine_api+"/rest/control/Stop"; return postMethod(url); }
	 */
	/**
	 * 4.2.3 任务的扫描模板的获取
	 * 
	 * @param sessionId
	 *            会话id
	 * @param engine_api引擎接口
	 * @return 任务状态代码
	 */
	public String getTemplate(String engine_api) {
		// 创建路径
		String url = "/rest/control/SLA";
		return getMethod(url);
	}

	/**
	 * 序号:4.3.1 功能描述：下发任务 参数描述：sessionId 回话Id,taskId 任务ID,destURL 监测目标URL,destIP
	 * 监测目标IP destPort 监测目标PORT,taskSLA 任务模板名称
	 * 
	 * @time 2015-01-05
	 */
	public String disposeScanTask(ScannerTaskUniParam scannerTaskUniParam) {
		String xml = "<Task><TaskID>" + nullFilter(scannerTaskUniParam.getTaskId())
				+ "</TaskID><CustomID>123123</CustomID><TaskInfo><DestURL>"
				+ nullFilter(scannerTaskUniParam.getDestURL()) + "</DestURL><DestIP>"
				+ nullFilter(scannerTaskUniParam.getDestIP()) + "</DestIP><DestPort>"
				+ nullFilter(scannerTaskUniParam.getDestPort()) + "</DestPort></TaskInfo><TaskSLA>"
				+ scannerTaskUniParam.getTaskSLA() + "</TaskSLA></Task>";
		// 创建任务发送路径
		String url = "/rest/task";
		String responseString = postMethod(url, xml);
		JSONObject jsonObj = new JSONObject();
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(IOUtils.toInputStream(responseString));
			Element ele = doc.getRootElement();
			String s = ele.attributeValue("value");
			jsonObj.put("status", s);
			if ("fail".equalsIgnoreCase(s)) {
				jsonObj.put("message", ele.element("ErrorMsg").getText());
			}
		} catch (DocumentException e) {

		}
		return jsonObj.toString();
	}

	/**
	 * 序号:4.3.2 功能描述：取消下发的任务 参数描述:String sessionId, String taskId
	 * 
	 * @time 2015-01-08
	 */
	public String removeTask(ScannerTaskUniParam scannerTaskUniParam) {
		// 创建路径
		String url = "/rest/task/Remove/" + scannerTaskUniParam.getTaskId();
		return postMethod(url);
	}

	/**
	 * 序号:4.3.3 功能描述：启动下发的任务 参数描述:String sessionId, String taskId
	 * 
	 * @time 2015-01-08
	 */
	public String startTask(ScannerTaskUniParam scannerTaskUniParam) {
		// 创建路径
		String url = "/rest/task/Start/" + scannerTaskUniParam.getTaskId();
		return postMethod(url);
	}

	/**
	 * 序号:4.3.3 功能描述：暂停下发的任务 参数描述:String sessionId, String taskId
	 * 
	 * @time 2015-10-19
	 */
	public String pauseTask(ScannerTaskUniParam scannerTaskUniParam) {
		// 创建路径
		String url = "/rest/task/Pause/" + scannerTaskUniParam.getTaskId();
		return postMethod(url);
	}

	/**
	 * 序号:4.3.3 功能描述：停止下发的任务 参数描述:String sessionId, String taskId
	 * 
	 * @time 2015-10-19
	 */
	public String stopTask(ScannerTaskUniParam scannerTaskUniParam) {
		// 创建路径
		String url = "/rest/task/Stop/" + scannerTaskUniParam.getTaskId();
		return postMethod(url);
	}

	/**
	 * 4.3.4 根据任务id获取任务当前状态
	 * 
	 * @param sessionId
	 *            会话id
	 * @param taskId
	 *            任务id
	 * @return 任务状态代码
	 */
	public String getStatusByTaskId(ScannerTaskUniParam scannerTaskUniParam) {
		// 创建路径
		String url ="/rest/task/Test/" + scannerTaskUniParam.getTaskId();
		// 测试
		String returnString = postMethod(url);
		return returnString;
	}

	/**
	 * 序号:4.3.5 根据任务id获取任务当前进度
	 * 
	 * @param sessionId
	 *            会话id
	 * @param taskId
	 *            任务id
	 * @param ProductId
	 * @return 任务状态代码
	 */
	public String getProgressByTaskId(ScannerTaskUniParam scannerTaskUniParam) {
		// 组织发送内容XML
		String xml = "<Task><TaskID>" + scannerTaskUniParam.getTaskId() + "</TaskID><ProductID>"
				+ scannerTaskUniParam.getProductId() + "</ProductID ></Task>";
		// 创建路径
		String url = "/rest/task/getTaskProgress";
		String returnString = postMethod(url, xml);
		return returnString;

	}

	/**
	 * 4.3.6 任务负载查询
	 * 
	 * @param sessionId
	 *            会话id
	 * @param engine_api引擎接口
	 * @return 任务状态代码
	 */
	public String getTaskLoadInfo() {
		// 创建路径
		String url = "/rest/task/GetTaskLoadInfo";
		return getMethod(url);
	}

	public String distortChangeActive(ScannerTaskUniParam scannerTaskUniParam) {
		String url = "/rest/task/distortChangeActive/" + scannerTaskUniParam.getDestIP();
		return postMethod(url);
	}

	/**
	 * 4.4.3 功能描述：根据任务ID获取任务执行结果数 参数描述:String sessionId 回话ID, String taskId任务ID
	 * 
	 * @time 2015-01-05
	 */
	public String getResultCountByTaskID(ScannerTaskUniParam scannerTaskUniParam) {
		// 创建Url
		String url = "/rest/report/ResultCount/TaskID/" + scannerTaskUniParam.getTaskId();
		JSON json = new XMLSerializer().read(getMethod(url));
		JSONObject responseObject = new JSONObject().fromObject(json.toString());
		String status = responseObject.getString("@value");
		responseObject.remove("@value");
		responseObject.put("status", status);
		String allNumber = responseObject.getString("@allNumber");
		responseObject.remove("@allNumber");
		responseObject.put("allNumber", allNumber);
		return responseObject.toString();
	}

	/**
	 * 4.4.4 功能描述：根据任务ID分页获取扫描结果 参数描述:String sessionId 回话ID, String taskId任务ID,
	 * String productId 引擎功能, int startNum 起始数, int size 每页大小
	 * 
	 * @time 2015-01-05
	 */
	public String getReportByTaskID(ScannerTaskUniParam scannerTaskUniParam) {
		// 创建请求路径
		String url = "/rest/report/TaskID";
		// 组织请求内容XML
		String xml = "<ResultParam><TaskID>" + scannerTaskUniParam.getTaskId() + "</TaskID>" + "<ProductID>"
				+ scannerTaskUniParam.getProductId() + "</ProductID><StartNum>" + scannerTaskUniParam.getStartNum()
				+ "</StartNum><Size>" + scannerTaskUniParam.getSize() + "</Size></ResultParam>";
		// 发送POST方法
		JSON json = new XMLSerializer().read(postMethod(url, xml));
		JSONObject responseObject = new JSONObject().fromObject(json.toString());
		return responseObject.toString();
	}

	/**
	 * 4.4.5 获取监测网站总数
	 * 
	 * @param sessionId
	 *            会话id
	 * @return 网站总数
	 */
	public String getWebsiteCount() {
		String url = "/rest/report/GetWebsiteCount";
		return getMethod(url);
	}

	/**
	 * 4.4.6 获取监测网站列表
	 * 
	 * @param sessionId
	 *            会话id
	 * @return 网站列表及问题分布
	 */
	public String getWebsiteList(ScannerTaskUniParam scannerTaskUniParam) {
		String url = "/rest/report/GetWebsiteList/";
		String xml = "<PageParam><StartNum>" + scannerTaskUniParam.getStartNum() + "</StartNum><Size>"
				+ scannerTaskUniParam.getSize() + "</Size></PageParam>";
		return postMethod(url, xml);
	}

	/**
	 * 4.4.7 根据网站id获取report总数
	 * 
	 * @param webId
	 *            网站id(和taskid不同)
	 * @return 网站列表及问题分布
	 */
	public String GetReportCountByWebID(ScannerTaskUniParam scannerTaskUniParam) {
		String url = "/rest/report/GetReportCountByWebID/" + scannerTaskUniParam.getWebId();
		return getMethod(url);
	}

	/**
	 * 4.4.7 根据网站id获取reportid列表
	 * 
	 * @param taskNum
	 *            网站id(和taskid不同)
	 * @return 网站列表及问题分布
	 */
	public String GetReportIDListByWebId(ScannerTaskUniParam scannerTaskUniParam, int StartNum, int Size) {
		String url = "/rest/report/GetReportIDListByWebId";
		String xml = "<Param><WebId>" + scannerTaskUniParam.getWebId() + "</WebId><ProductId>"
				+ scannerTaskUniParam.getProductId() + "</ProductId><StartNum>" + StartNum + "</StartNum><Size>" + Size
				+ "</Size></Param>";
		return postMethod(url, xml);
	}

	/**
	 * 4.4.9 根据汇总结果id获取记录总数
	 * 
	 * @param reportId
	 *            汇总结果id(任务结束时，由监测平台告知外部系统)
	 * @return
	 */
	public String getResultCountByReportID(ScannerTaskUniParam scannerTaskUniParam) {
		String url = "/rest/report/ResultCount/ReportID/" + scannerTaskUniParam.getReportId();
		return getMethod(url);
	}

	/**
	 * 4.4.10 根据汇总结果id直接分页获取信息
	 * 
	 * @param reportID
	 * @param startNum
	 * @param size
	 * @return
	 */
	public String getReportByReportIdInP(ScannerTaskUniParam scannerTaskUniParam, int startNum, int size) {
		String url = "/rest/report/ReportID";
		String xml = "<ReportParam><ReportID>" + scannerTaskUniParam.getReportId() + "</ReportID><StartNum>" + startNum
				+ "</StartNum><Size>" + size + "</Size></ReportParam>";
		return postMethod(url, xml);
	}

	/**
	 * 获取report总数 id:4.4.11
	 * 
	 * @param sessionId
	 * @return
	 */
	public String getReportCount() {
		String url = "/rest/report/ReportCount";
		return getMethod(url);
	}

	/**
	 * id:4.12 获取report id列表
	 * 
	 * @param sessionId
	 * @param ProductID
	 * @param startNum
	 * @param size
	 * @return
	 */
	public String getReportIDList(ScannerTaskUniParam scannerTaskUniParam, int startNum, int size) {
		String url = "/rest/report/ReportIDList/" + scannerTaskUniParam.getProductId();
		String xml = "<PageParam><StartNum>" + startNum + "</StartNum><Size>" + size + "</Size></PageParam >";
		return postMethod(url, xml);
	}

	/**
	 * 序号：4.4.13 获取引擎的存活状态(性能数据和版本号)
	 * 
	 * @param sessionId
	 *            会话id
	 * @param taskId
	 *            任务id
	 * @return 任务状态代码
	 */
	public String getEngineState() {
		// 创建路径
		String url = "/rest/control/GetEngineState";
		return getMethod(url);
	}

	/**
	 * id:4.4.14 获取漏洞库的信息
	 * 
	 * @param sessionId
	 * @return
	 */
	public String getIssueRepositoryList() {
		String url = "/rest/control/GetIssueRepositoryList";
		return getMethod(url);
	}

	/**
	 * id:4.4.15 更新工具版本
	 * 
	 * @param sessionId
	 * @return
	 */
	public String updateEngine() {
		String url = "/rest/control/UpdateEngine";
		String xml = "<RequestCommand><command>update_version</command></RequestCommand>";
		return postMethod(url, xml);
	}

	/**
	 * 4.4.16 生成任务报表文件
	 * 
	 * @param sessionId
	 * @param taskId
	 * @param reportType
	 *            = (A1,B3,B5)
	 * @return
	 */

	public String createReport(ScannerTaskUniParam scannerTaskUniParam, String reportType) {
		String url = "/rest/report/CreateReport/" + scannerTaskUniParam.getTaskId();
		String xml = "<ReportParam><ReportType>" + reportType + "</ReportType></ReportParam>";
		return postMethod(url, xml);
	}

	/**
	 * 4.4.17 获取报表文件生成状态
	 * 
	 * @param sessionId
	 * @param reportZipFileName
	 * @return
	 */

	public String getReportFileStatus(String reportZipFileName) {
		String url = "/rest/report/GetReportFileStatus/" + reportZipFileName;
		return postMethod(url);
	}

	public String downloadFile(String reportZipFileName) {
		String url = "/rest/report/DownloadReportFile/" + reportZipFileName;
		return postMethod(url);
	}
	
	public static void main(String[] args) {
		ArnhemDeviceOperation operation = new ArnhemDeviceOperation();
		operation.createSessionId("admin", "admin123456", "https://219.141.189.187:61443");
		ScannerTaskUniParam scannerTaskUniParam = new ScannerTaskUniParam();
		scannerTaskUniParam.setTaskId("188807_17051711070657257");
		scannerTaskUniParam.setProductId("1");
		scannerTaskUniParam.setDestURL("http://www.testfire.net");
		scannerTaskUniParam.setTaskSLA("漏洞扫描模板105");
		System.out.println(operation.getState());
	}
}
