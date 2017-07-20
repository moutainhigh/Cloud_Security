package com.cn.ctbri.schedular;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.cn.ctbri.dao.CntByHourMapper;
import com.cn.ctbri.dao.CntBySrcMapper;
import com.cn.ctbri.dao.CntByTypeMapper;
import com.cn.ctbri.dao.WebsecMapper;
import com.cn.ctbri.model.CntByHour;
import com.cn.ctbri.model.CntBySrc;
import com.cn.ctbri.model.CntByType;

public class QuartzWebsecCnt implements org.quartz.Job{

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext=null;  
	     try {  
	    	 applicationContext = getApplicationContext(jobContext);
	    	 //cntType(applicationContext);
	    	 //cntHour(applicationContext);
	    	 cntSrc(applicationContext);
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
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
	   private void cntType(ApplicationContext ctx) {
		   WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
		   CntByTypeMapper cntByTypeDao = (CntByTypeMapper)ctx.getBean("cntByTypeDao");
		   Date max_websecDate = websecDao.selectMaxDay();
		   Date max_cntByTypeDate = cntByTypeDao.selectMaxDay() ;
		   Map<String,Date> map = new HashMap<String,Date> ();
		   if(max_cntByTypeDate!= null && max_websecDate.after(max_cntByTypeDate)){
			 
			 map.put("maxDate", max_cntByTypeDate);
			  
		   }
		   List<CntByType> l = cntByTypeDao.selectCntByType(map);
		   for(CntByType c:l){
			   System.out.println(c.getDomain());
		   }
		   Map<String,List<CntByType>> maplist= new HashMap<String,List<CntByType>> ();
		   maplist.put("list", l);
		   cntByTypeDao.batchInsert(maplist);
	   }
	   private void cntHour(ApplicationContext ctx) {
		   WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
		   CntByHourMapper cntByHourDao = (CntByHourMapper)ctx.getBean("cntByHourDao");
		   Date max_websecDate = websecDao.selectMaxDay();
		   Date max_cntByTypeDate = cntByHourDao.selectMaxDay() ;
		   Map<String,Date> map = new HashMap<String,Date> ();
		   if(max_cntByTypeDate!= null && max_websecDate.after(max_cntByTypeDate)){
			 
			 map.put("maxDate", max_cntByTypeDate);
			  
		   }
		   List<CntByHour> l = cntByHourDao.selectCntByHour(map);
		   for(CntByHour c:l){
			   System.out.println(c.getDomain());
		   }
		   Map<String,List<CntByHour>> maplist= new HashMap<String,List<CntByHour>> ();
		   maplist.put("list", l);
		   cntByHourDao.batchInsert(maplist);
	   }
	   private void cntSrc(ApplicationContext ctx) {
		   WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
		   CntBySrcMapper cntBySrcDao = (CntBySrcMapper)ctx.getBean("cntBySrcDao");
		   Date max_websecDate = websecDao.selectMaxDay();
		   Date max_cntByTypeDate = cntBySrcDao.selectMaxDay() ;
		   Map<String,Date> map = new HashMap<String,Date> ();
		   if(max_cntByTypeDate!= null && max_websecDate.after(max_cntByTypeDate)){
			 
			 map.put("maxDate", max_cntByTypeDate);
			  
		   }
		   List<CntBySrc> l = cntBySrcDao.selectCntBySrc(map);
		   for(CntBySrc c:l){
			   System.out.println(c.getDomain());
		   }
		   Map<String,List<CntBySrc>> maplist= new HashMap<String,List<CntBySrc>> ();
		   maplist.put("list", l);
		   cntBySrcDao.batchInsert(maplist);
	   }
}
