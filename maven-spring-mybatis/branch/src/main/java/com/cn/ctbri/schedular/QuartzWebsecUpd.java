package com.cn.ctbri.schedular;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cn.ctbri.dao.CityMapper;
import com.cn.ctbri.dao.IpMapper;
import com.cn.ctbri.dao.WebsecMapper;
import com.cn.ctbri.model.City;
import com.cn.ctbri.model.Ip;
import com.cn.ctbri.model.Websec;
import com.cn.ctbri.start.LogController;
import com.cn.ctbri.utils.IPUtility;

public class QuartzWebsecUpd implements org.quartz.Job{
	
	
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("0");
		 ApplicationContext applicationContext=null;  
	     try {  
	            applicationContext=getApplicationContext(jobContext);  
	            upd(applicationContext);
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	}
	
	private void upd(ApplicationContext ctx){
			System.out.println("000000");
			WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
			IpMapper ipDao=(IpMapper) ctx.getBean("ipDao");
		    CityMapper cityDao=(CityMapper) ctx.getBean("cityDao");
			
		    //Long pre_maxLogId = getPropsLogId();
		    Long pre_maxLogId = 40000l;
			//Long maxLogId = websecDao.getMaxLogId();
			Long maxLogId = 50000l;
			
		
		
			Map<String,Long> hm = new HashMap<String,Long>();
			hm.put("preLogId", pre_maxLogId);
			hm.put("maxLogId", maxLogId);
			
			//hm.put("maxLogId", 4l);
			//hm.put("maxLogId", 219974l);
			final CopyOnWriteArrayList<Websec> websecList = (CopyOnWriteArrayList<Websec>) websecDao.selectSeg(hm);
			List<Websec> udpList = new ArrayList<Websec>();
			System.out.println("=====???????????????  ==== "+websecList.size());
			for(Websec websec : websecList){
				//List<Websec> udpList = new ArrayList<Websec>();
				Websec upd = null;
				//??????????????????ip???????????????????????????????????????
				if(websec.getSrcIp() != null && !websec.getSrcIp().equals("")){//???????????????ip????????????????????????????????????
					upd = new Websec();
					Ip srcIp = getLocationId(websec.getSrcIp(),ipDao);
					if(srcIp != null){//??????ip???????????????????????????????????? ????????????1???????????????
						upd.setIpLatlongValid(1);
						upd.setSrcLatitude(srcIp.getLatitude());
						upd.setSrcLongitude(srcIp.getLongitude());
						City srccity = cityDao.selectByPrimaryKey(srcIp.getLocationId());
						if(srccity != null){
							upd.setSrcCity(srccity.getCityName());
							upd.setSrcCountry(srccity.getCountryName());
							upd.setSrcCountryCode(srccity.getCountryIsoCode());
							upd.setSrcSubdivision1(srccity.getSubdivision1Name());
							upd.setSrcSubdivision2(srccity.getSubdivision2Name());
						}					
						
					}else{//??????ip???????????????????????????????????? ????????????-1???????????????
						upd.setIpLatlongValid(-1);
					}
					//??????????????????????????????
					//??????domain????????????none???????????????????????????????????????????????????ip???????????????????????????????????????
					if(websec.getDomain() != null && !websec.getDomain().equalsIgnoreCase("None")){
						if(websec.getDstIp() != null && !websec.getDstIp().equals("")){
							Ip dstIp = getLocationId(websec.getDstIp(),ipDao);
							if(dstIp != null){
								
								upd.setDstLatitude(dstIp.getLatitude());
								upd.setDstLongitude(dstIp.getLongitude());
								City dstcity = cityDao.selectByPrimaryKey(dstIp.getLocationId());
								if(dstcity != null){
									upd.setDstCity(dstcity.getCityName());
									upd.setDstCountry(dstcity.getCountryName());
									upd.setDstCountryCode(dstcity.getCountryIsoCode());
									upd.setDstSubdivision1(dstcity.getSubdivision1Name());
									upd.setDstSubdivision2(dstcity.getSubdivision2Name());
								}					
								
							}
					   }
				    }
					upd.setLogId(websec.getLogId());
					udpList.add(upd)	;
				}else{//?????????????????????????????????????????????????????????
					
				}
				
			    
			    System.out.println("======????????????????????????============");
			   /* Map<String,List<Websec>> map = new HashMap<String,List<Websec>>();
			    map.put("list", udpList);
				 websecDao.batchUpd(map);*/
			    System.out.println("======????????????============Logid??? "+upd.getLogId()+"============ipLatlongValid: "+upd.getIpLatlongValid());
		    }
			
			if(udpList.size() > 0){
				System.out.println("=====??????????????????????????????  ==== "+udpList.size());
				int cn = 80 ;
				int commitCnt = udpList.size()/cn ;
				System.out.println("==================?????? "+(commitCnt+1) +"?????????");
				 Map<String,List<Websec>> map = new HashMap<String,List<Websec>>();
				if(commitCnt == 0){
					System.out.println("======??????????????????"+udpList.size()+"?????????============");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				    System.out.println(sdf.format(getStartTime()));  
				    
				    map.put("list", udpList);
				    websecDao.batchUpd(map);
				    System.out.println("======??????????????????"+udpList.size()+"?????????============");
					System.out.println(sdf.format(getEndTime())); 
					
				}else{
					for(int i = 0 ; i <= commitCnt ; i++){
						List<Websec> segList = null ;
						if(i == 0){
							segList = udpList.subList(0, cn);
						}else if( i == commitCnt ){
							segList = udpList.subList(i*cn, udpList.size());
						}else {
							segList = udpList.subList(i*cn,(i+1)*cn);
						}
						
						//??????list?????????????????????????????????
						if(segList.size() > 0){
							int n = i+1 ;
							System.out.println("======???????????????"+n+"???"+cn+"?????????============");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						    System.out.println(sdf.format(getStartTime()));  
							map.put("list", segList);
							websecDao.batchUpd(map);
							System.out.println("======???????????????"+n+"???"+cn+"?????????============");
							System.out.println(sdf.format(getEndTime())); 
						}
						 
					}
				}
			}
			this.updProps(maxLogId);
			
	}
		
	
	
	private Long getPropsLogId(){
		Long logid= 0l ;
		 Properties prop = new Properties();     
	     try{
	         //??????????????????a.properties
	    	// String path=System.getProperty("user.dir");
	 	     //InputStream in = new BufferedInputStream (new FileInputStream(path+"/conf/maxLogId.properties"));
	 	    InputStream in = LogController.class.getClass().getResourceAsStream("/conf/maxLogId.properties");
		 	   
	 	     prop.load(in);     ///??????????????????
	         Iterator<String> it=prop.stringPropertyNames().iterator();
	         while(it.hasNext()){
	             String key=it.next();
	             System.out.println(key+":"+prop.getProperty(key));
	             logid = Long.parseLong((String)prop.get(key));
	         }
	         in.close();   
	     }catch(Exception e){
	         System.out.println(e);
	     }
	     return logid;
	 } 
	
	private void updProps(Long logid){
		 Properties prop = new Properties();     
	     try{
	         //??????????????????a.properties
	    	 String path=System.getProperty("user.dir");
	 		//???????????????properties??????
	         FileOutputStream oFile = new FileOutputStream(path+"/maxLogId.properties", true);//true??????????????????
	         prop.setProperty("log_id", Long.toString(logid));
	         prop.store(oFile, "The New properties file");
	         oFile.close();
	     }
	     catch(Exception e){
	         System.out.println(e);
	     }
	}
	/**
	 * @param ipv4
	 * @param ipDao
	 * @return ????????????????????????????????????id
	 */
	private Ip getLocationId(String ipv4,IpMapper ipDao){
		Ip ip = null;
		Long ipLong = IPUtility.ip2long(ipv4);
		List<Ip> list = (ArrayList<Ip>)ipDao.getLatLongByIp(ipLong);
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
	    private static Date getStartTime() {  
	        Calendar todayStart = Calendar.getInstance();  
	        return todayStart.getTime();  
	    }  
	  
	    private static Date getEndTime() {  
	        Calendar todayEnd = Calendar.getInstance();  
	        return todayEnd.getTime();  
	    }  
	 
}
