package com.mucfc;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.entity.CrtOrder;
@DisallowConcurrentExecution
public class MyJob implements Job{
	private static final Logger logger = Logger.getLogger(MyJob.class);  
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String s = (String)context.getJobDetail().getJobDataMap().get("crtOrder");
		System.out.println("Hello quzrtz  "+
		    s +
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()));
		net.sf.json.JSONObject o = JSONObject.fromObject(s);
		CrtOrder crtO =(CrtOrder) JSONObject.toBean(o,CrtOrder.class);
		
	}

}
