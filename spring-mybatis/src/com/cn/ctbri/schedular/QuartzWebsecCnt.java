package com.cn.ctbri.schedular;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.cn.ctbri.dao.IpMapper;
import com.cn.ctbri.dao.WebsecMapper;
import com.cn.ctbri.model.Ip;
import com.cn.ctbri.model.Websec;
import com.cn.ctbri.utils.IPUtility;

public class QuartzWebsecCnt implements org.quartz.Job{

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext=null;  
	     try {  
	            applicationContext=getApplicationContext(jobContext);  
	            WebsecMapper websecDao=(WebsecMapper) applicationContext.getBean("websecDao");
	            
	   	     IpMapper ipDao=(IpMapper) applicationContext.getBean("ipDao");
	   	     Ip ip = getLocationId("76.123.103.168",ipDao);
	   	     Websec websec = new Websec();
	   	     if(ip != null){
	   	    	 websec.setIpLatlongValid(1);
	   	    	 System.out.println("CCCCCCCCCCCCCip not null");
	   	     }else
	   	    	 websec.setIpLatlongValid(-1);
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	}
	static Ip getLocationId(String ipv4,IpMapper ipDao){
		Ip ip = null;
		Long ipLong = IPUtility.ip2long(ipv4);
		List<Ip> list = (ArrayList<Ip>)ipDao.getLocationidByIp(ipLong);
		if(list.size() > 0 )
			ip = list.get(0);
		return ip;
	}
	  private static final String APPLICATION_CONTEXT_KEY = "applicationContextKey";  
	    private ApplicationContext getApplicationContext(JobExecutionContext context) throws Exception {  
	            ApplicationContext appCtx = null;  
	            appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);  
	            if (appCtx == null) {  
	                throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");  
	            }  
	            return appCtx;  
	    }  

}
