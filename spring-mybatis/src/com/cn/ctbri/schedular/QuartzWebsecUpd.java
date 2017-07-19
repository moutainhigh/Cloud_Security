package com.cn.ctbri.schedular;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.cn.ctbri.dao.CityMapper;
import com.cn.ctbri.dao.IpMapper;
import com.cn.ctbri.dao.WebsecMapper;
import com.cn.ctbri.model.City;
import com.cn.ctbri.model.Ip;
import com.cn.ctbri.model.Websec;
import com.cn.ctbri.utils.IPUtility;

public class QuartzWebsecUpd implements org.quartz.Job{
	
	
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
	   	    	 System.out.println("uuuuuuuuuuuuuuip not null");
	   	     }else
	   	    	 websec.setIpLatlongValid(-1);
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	}
	
	private void upd(ApplicationContext ctx){
		List<Websec> updlist = new ArrayList<Websec>();
		WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
		IpMapper ipDao=(IpMapper) ctx.getBean("ipDao");
	    CityMapper cityDao=(CityMapper) ctx.getBean("cityDao");
		Properties p = new Properties();
		try {
			p.load(QuartzWebsecUpd.class.getClassLoader().getResourceAsStream("conf/maxLogId.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long pre_maxLogId = Long.parseLong(p.getProperty("log_id"));
		Long maxLogId = websecDao.getMaxLogId();
		p.put("log_id", maxLogId);
		List<Websec> websecList = null ;
		if(pre_maxLogId == 0){
			websecList = websecDao.selectAll();
			for(Websec websec : websecList){
				Websec upd = new Websec();
				upd.setLogId(websec.getLogId());
				//设置攻击源的ip的经纬度和所在国家城市信息
				if(websec.getSrcIp() != null && !websec.getSrcIp().equals("")){
					Ip srcIp = getLocationId(websec.getSrcIp(),ipDao);
					if(srcIp != null){
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
						
					}else{
						upd.setIpLatlongValid(-1);
					}
				}
				//设置目的ip的经纬度和所在国家城市信息
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
			websecList.add(upd)	;	
		}
		
	}else{
		Map<String,Long> hm = new HashMap<String,Long>();
		hm.put("preLogId", pre_maxLogId);
		hm.put("LogId", maxLogId);
		websecList = websecDao.selectSeg(hm);
		for(Websec websec : websecList){
			Websec upd = new Websec();
			upd.setLogId(websec.getLogId());
			//设置攻击源的ip的经纬度和所在国家城市信息
			if(websec.getSrcIp() != null && !websec.getSrcIp().equals("")){
				Ip srcIp = getLocationId(websec.getSrcIp(),ipDao);
				if(srcIp != null){
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
					
				}else{
					upd.setIpLatlongValid(-1);
				}
			}
			//设置目的ip的经纬度和所在国家城市信息
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
		websecList.add(upd)	;	
	 }
   }
		websecDao.batchUpd(websecList);
		}
	
	/**
	 * @param ipv4
	 * @param ipDao
	 * @return 根据地址返回经纬度和城市id
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
}
