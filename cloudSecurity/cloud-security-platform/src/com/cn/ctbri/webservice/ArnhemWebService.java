package com.cn.ctbri.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.cn.ctbri.common.ArnhemWorker;
import com.sun.jersey.spi.resource.Singleton;
/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-05
 * 描        述：  接收安恒WebService请求接口
 * 版        本：  1.0
 */
@Controller
@Singleton
@Component
@Path("ws/arnhem")
public class ArnhemWebService {
	//日志对象
	private Logger log = Logger.getLogger(this.getClass());
	/**
	 * 功能描述： 接收任务状态
	 * 参数描述：  request 请求对象
	 *		 @time 2015-01-05
	 */
	@Path("receiveTaskStatus")
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public String receiveTaskStatus(@Context HttpServletRequest request){
		try {
			//从请求中获取输入流
			ServletInputStream io = request.getInputStream();
			//判断流对象是否存在
			if(io != null){
				//创建流读取对象
				SAXReader reader = new SAXReader();
				//读取流封装为文档对象
				Document document = reader.read(io);
				/**
				 * 获取文档的格式
				 * <Task>
				 * 		<TaskID></TaskID>
				 * 		<CustomID></CustomID>
				 *      <ReportID></ReportID>
				 * </Task>
				 * */
				//解析XML，获取任务状态
				Element task = document.getRootElement();
				//获取任务ID
				Element taskIDNode = task.element("TaskID");
				//任务ID
				String taskId = null;
				if(taskIDNode != null){
					taskId = taskIDNode.getTextTrim();
				}
				//登陆服务器
				String sessionId = ArnhemWorker.getSessionId();
				//根据taskId查询任务执行结果数
				Map<String, String> resultMap = getResultNumByTaskId(taskId, sessionId);
				//获取总数
				String total = resultMap.get("Total");
				//获取引擎功能
				String productId = resultMap.get("ProductId");
				//当总数不为0的时候，获取扫描记录，并保存到本地库
				if(total != null && !"0".equals(total.trim())){
					String reportXml = ArnhemWorker.getReportByTaskID(sessionId, taskId, productId, 0, Integer.parseInt(total));
					parseReportToDB(reportXml);
				}
			}
		} catch (Exception e) {
			log.error("WebService流解析异常!", e);
		}
		return "<Result value=\"Success\"></Result>";
	}
	/**
	 * 功能描述： 解析reportXml,将扫描记录保存到数据库
	 * 参数描述：String reportXml 
	 * @throws DocumentException 
	 *		 @time 2015-01-05
	 */
	private void parseReportToDB(String reportXml) throws DocumentException {
		if(reportXml != null){
			//解析XML为文档对象
			Document document = DocumentHelper.parseText(reportXml);
			//
		}
	}
	/**
	 * 功能描述： 接收任务状态
	 * 参数描述： String taskId 任务ID, String sessionId 回话ID
	 *		 @time 2015-01-05
	 */
	private Map<String, String> getResultNumByTaskId(String taskId, String sessionId)
			throws DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		//获取结果数XML
		String resultNumXml = ArnhemWorker.getResultCountByTaskID(sessionId, taskId);
		//解析XML为文档对象
		Document resultNumDocument = DocumentHelper.parseText(resultNumXml);
		/**
		 *  <Result value="Success" allNumber="4000">
		 *		<Funcs>
		 *			<Func>
		 *				<Total>1000</Total>
		 *				<ProductId>1</ProductId>
		 *			</Func>
		 *			<Func>
		 *				<Total>3000</Total>
		 *				<ProductId>2</ProductId>
		 *			</Func>
		 *		</Funcs>
		 *	</Result>
		 *  ProductId =1 漏洞扫描
		 *	ProductId =2 木马检测
		 *	ProductId =3 篡改检测
		 *	ProductId =4 敏感关键字
		 * */
		//由于只有一种服务，所以得到的Func只有一个
		Element result = resultNumDocument.getRootElement();
		//Funcs
		Element funcs = result.element("Funcs");
		//Func
		Element func = funcs.element("Func");
		//Total
		Element total = funcs.element("Total");
		//ProductId
		Element productId = funcs.element("ProductId");
		map.put("Total",total.getTextTrim());
		map.put("ProductId",productId.getTextTrim());
		return map;
	}
}
