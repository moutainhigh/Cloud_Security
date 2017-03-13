package com.cn.ctbri.util;

import java.util.Properties;
import java.util.regex.Pattern;

import org.codehaus.jettison.json.JSONException;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

import com.cn.ctbri.common.InternalWorker;
import com.cn.ctbri.common.Scheduler4Task;
import com.cn.ctbri.entity.OrderTask;

public class GetPath extends BreadthCrawler {
	//爬取的url
	private static String url;
	//深度
	private static String depth;
	//订单任务
	private static OrderTask o;

	static {
		try {
			Properties p = new Properties();
			p.load(Scheduler4Task.class.getClassLoader().getResourceAsStream("default.properties"));
			depth = p.getProperty("depth");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GetPath(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
	}
	
	public void visit(Page page, CrawlDatums next) {
		String question_regex = url;
		String[] CustomManu = new String[0];//指定厂家
        if(Pattern.matches(question_regex, page.getUrl())){
        	// 开始时间
			String begin_date = DateUtils.dateToString(o.getBegin_date());
			// 结束时间
			String end_date = DateUtils.dateToString(o.getEnd_date());
			// 任务执行时间
			String task_date = DateUtils.dateToString(o.getTask_date());

			String result = null;
//			result = InternalWorker.vulnScanCreate(String.valueOf(o.getType()), page.getUrl(), "", begin_date,end_date, String.valueOf(o.getScan_type()), "", "", "",CustomManu, o.getOrderId(),String.valueOf(o.getServiceId()),String.valueOf(o.getWebsoc()), task_date,o.getOrderTaskId());
//			System.out.println(result);
        }
		
	}

	public static void getUrl(OrderTask orderTask) throws Exception {
		o = orderTask;
		GetPath crawler = new GetPath("crawl", true);
		crawler.addSeed(o.getUrl());
		if (o.getUrl().endsWith("/")) {
			url = o.getUrl() + ".*";
		} else {
			url = o.getUrl() + "/.*";
		}
		crawler.addRegex(url);
		crawler.start(Integer.parseInt(depth));// 深度
	}
}
