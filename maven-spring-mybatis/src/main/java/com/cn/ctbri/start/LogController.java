package com.cn.ctbri.start;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cn.ctbri.dao.CityMapper;
import com.cn.ctbri.dao.CntByTypeMapper;
import com.cn.ctbri.dao.IpMapper;
import com.cn.ctbri.dao.WebsecMapper;
import com.cn.ctbri.model.City;
import com.cn.ctbri.model.CntByType;
import com.cn.ctbri.model.Ip;
import com.cn.ctbri.model.Websec;
import com.cn.ctbri.utils.IPUtility;

public class LogController {
	public static void main(String[] args) {
		
		File directory = new File("");//设定为当前文件夹 
		try{ 
		    
		    ApplicationContext ctx=null;
			 
			// String path=System.getProperty("user.dir");
			// System.out.println(path);
		   
		     ctx=new ClassPathXmlApplicationContext("conf/applicationContext.xml");
		     upd(ctx);
		     cntType(ctx);
		}catch(Exception e){
			e.printStackTrace();
		} 
		 
	}
	 private static void cntType(ApplicationContext ctx) {
		   System.out.println("11111");
		   WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
		   CntByTypeMapper cntByTypeDao = (CntByTypeMapper)ctx.getBean("cntByTypeDao");
		   Date max_websecDate = websecDao.selectMaxDay();
		   Date max_cntByTypeDate = cntByTypeDao.selectMaxDay() ;
		   Map<String,Date> map = new HashMap<String,Date> ();
		   if(max_cntByTypeDate!= null && max_websecDate.after(max_cntByTypeDate)){
			 
			 map.put("maxDate", max_cntByTypeDate);
			  
		   }
		   List<CntByType> l = cntByTypeDao.selectCntByType(map);
		   if(l.size() > 0 ){
			   for(CntByType c:l){
				   System.out.println(c.getDomain());
			   }
			   Map<String,List<CntByType>> maplist= new HashMap<String,List<CntByType>> ();
			   maplist.put("list", l);
			   cntByTypeDao.batchInsert(maplist);
		   }
		  
	   }
	 private static  void upd(ApplicationContext ctx){
			System.out.println("000000");
			WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
			IpMapper ipDao=(IpMapper) ctx.getBean("ipDao");
		    CityMapper cityDao=(CityMapper) ctx.getBean("cityDao");
			
		    Long pre_maxLogId = getPropsLogId();
		   // Long pre_maxLogId = 40000l;
			Long maxLogId = websecDao.getMaxLogId();
			//Long maxLogId = 50000l;
			
		
		
			Map<String,Long> hm = new HashMap<String,Long>();
			hm.put("preLogId", pre_maxLogId);
			hm.put("maxLogId", maxLogId);
			
			//hm.put("maxLogId", 4l);
			//hm.put("maxLogId", 219974l);
			final CopyOnWriteArrayList<Websec> websecList = (CopyOnWriteArrayList<Websec>) websecDao.selectSeg(hm);
			List<Websec> udpList = new ArrayList<Websec>();
			System.out.println("=====记录条数：  ==== "+websecList.size());
			for(Websec websec : websecList){
				//List<Websec> udpList = new ArrayList<Websec>();
				Websec upd = null;
				//设置攻击源的ip的经纬度和所在国家城市信息
				if(websec.getSrcIp() != null && !websec.getSrcIp().equals("")){//只有攻击源ip不为空的情况，才新建对象
					upd = new Websec();
					Ip srcIp = getLocationId(websec.getSrcIp(),ipDao);
					if(srcIp != null){//通过ip查到了经纬度地址，则设置 标志位为1，表示有效
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
						
					}else{//通过ip没查到经纬度地址，则设置 标志位为-1，表示无效
						upd.setIpLatlongValid(-1);
					}
					//继续设置攻击目标信息
					//只有domain不是不为none，才是有效的攻击目标地址，设置目的ip的经纬度和所在国家城市信息
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
				}else{//攻击源地址未查到，就不查被攻击目标端了
					
				}
				
			    
			    System.out.println("======转中文值查询结束============");
			   /* Map<String,List<Websec>> map = new HashMap<String,List<Websec>>();
			    map.put("list", udpList);
				 websecDao.batchUpd(map);*/
			    System.out.println("======更新结束============Logid： "+upd.getLogId()+"============ipLatlongValid: "+upd.getIpLatlongValid());
		    }
			
			if(udpList.size() > 0){
				System.out.println("=====需要提交的记录条数：  ==== "+udpList.size());
				int cn = 80 ;
				int commitCnt = udpList.size()/cn ;
				System.out.println("==================分： "+(commitCnt+1) +"次提交");
				 Map<String,List<Websec>> map = new HashMap<String,List<Websec>>();
				if(commitCnt == 0){
					System.out.println("======开始提交所有"+udpList.size()+"条记录============");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				    System.out.println(sdf.format(getStartTime()));  
				    
				    map.put("list", udpList);
				    websecDao.batchUpd(map);
				    System.out.println("======结束提交所有"+udpList.size()+"条记录============");
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
						
						//如果list为空，则不执行批量查询
						if(segList.size() > 0){
							int n = i+1 ;
							System.out.println("======开始提交第"+n+"个"+cn+"条记录============");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						    System.out.println(sdf.format(getStartTime()));  
							map.put("list", segList);
							websecDao.batchUpd(map);
							System.out.println("======结束提交第"+n+"个"+cn+"条记录============");
							System.out.println(sdf.format(getEndTime())); 
						}
						 
					}
				}
			}
			updProps(maxLogId);
			
	}
		
	
	
	private static Long getPropsLogId(){
		Long logid= 0l ;
		 Properties prop = new Properties();     
	     try{
	         //读取属性文件a.properties
	    	// String path=System.getProperty("user.dir");
	 	     //InputStream in = new BufferedInputStream (new FileInputStream(path+"/conf/maxLogId.properties"));
	 	    //InputStream in = new LogController().getClass().getResourceAsStream("classpath:conf/maxLogId.properties");
	    	 InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/maxLogid.properties");
	 	     prop.load(in);     ///加载属性列表
	         Iterator<String> it=prop.stringPropertyNames().iterator();
	         while(it.hasNext()){
	             String key=it.next();
	             System.out.println(key+":"+prop.getProperty(key));
	             logid = Long.parseLong((String)prop.get(key));
	         }
	         in.close();   
	     }catch(Exception e){
	    	 
	         System.out.println("maxLogId.properties文件读取失败： "+e);
	     }
	     return logid;
	 } 
	
	private static void updProps(Long logid){
		 Properties prop = new Properties();     
	     try{
	         //读取属性文件a.properties
	    	 String path=System.getProperty("user.dir");
	 		//保存属性到properties文件
	         FileOutputStream oFile = new FileOutputStream(path+"/maxLogId.properties", true);//true表示追加打开
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
	 * @return 根据地址返回经纬度和城市id
	 */
	private static Ip getLocationId(String ipv4,IpMapper ipDao){
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
