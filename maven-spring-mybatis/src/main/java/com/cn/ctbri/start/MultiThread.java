package com.cn.ctbri.start;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cn.ctbri.dao.CityMapper;
import com.cn.ctbri.dao.IpMapper;
import com.cn.ctbri.dao.WebsecMapper;
import com.cn.ctbri.model.City;
import com.cn.ctbri.model.Ip;
import com.cn.ctbri.model.Websec;
import com.cn.ctbri.utils.IPUtility;

public class MultiThread implements Runnable {
	private CopyOnWriteArrayList<Websec> segList ;
	private ApplicationContext ctx ;
	private  MultiThread(CopyOnWriteArrayList<Websec> segList,ApplicationContext ctx){
		this.segList = segList ;
		this.ctx = ctx ;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
		System.out.println("=====需要提交的记录条数：  ==== "+segList.size());
		int cn = 80 ;
		int commitCnt = segList.size()/cn ;
		
		System.out.println("==================分： "+(commitCnt+1) +"次提交");
		 Map<String,List<Websec>> map = new HashMap<String,List<Websec>>();
		if(segList.size()>0 && commitCnt == 0){
			System.out.println("======开始查询所有"+segList.size()+"条记录============");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    System.out.println(sdf.format(getStartTime()));  
		    List<Websec> udpList = upd(segList);
		    if(udpList.size()>0){
		        map.put("list", udpList);
			   
			    websecDao.batchUpd(map);
			    System.out.println("======结束提交所有"+udpList.size()+"条记录============");
				System.out.println(sdf.format(getEndTime())); 
		    }	
		}else if(segList.size()>0 && commitCnt > 0){
			List<Websec> udpList = null ;
			for(int i = 0 ; i <= commitCnt ; i++){
				
				if( i == commitCnt ){
					udpList = upd(segList.subList(i*cn, segList.size()));
				}else {
					udpList = upd(segList.subList(i*cn,(i+1)*cn));
				}
				
				//如果udpList为空，则不执行批量更新
				if(udpList.size() > 0){
					int n = i+1 ;
					System.out.println("======开始提交第"+n+"个"+cn+"条记录============");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				    System.out.println(sdf.format(getStartTime()));  
					map.put("list", udpList);
					websecDao.batchUpd(map);
					System.out.println("======结束提交第"+n+"个"+cn+"条记录============");
					System.out.println(sdf.format(getEndTime())); 
				}
				 
			}//end for 
		}
	
	}
	List<Websec> upd(List<Websec> _segList){
		List<Websec> udpList = new ArrayList<Websec>();
		 CityMapper cityDao=(CityMapper) ctx.getBean("cityDao");
		IpMapper ipDao=(IpMapper) ctx.getBean("ipDao");
		for(Websec websec : _segList){
			
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
			
		    
		   // System.out.println("======转中文值查询结束============");
		   // System.out.println("======更新结束============Logid： "+upd.getLogId()+"============ipLatlongValid: "+upd.getIpLatlongValid());
		}
		return udpList ;
	}
	public static void main(String[] args) {
			ApplicationContext ctx=null;
			 
			try{ 
			    
			    
				 
				// String path=System.getProperty("user.dir");
				// System.out.println(path);
			   
			    ctx=new ClassPathXmlApplicationContext("conf/applicationContext.xml");
			     
			}catch(Exception e){
				e.printStackTrace();
			} 
			
		    //Long pre_maxLogId = getPropsLogId();
		    Long pre_maxLogId = 199003l;
			//Long maxLogId = websecDao.getMaxLogId();
			Long maxLogId = 299003l;
			
			Map<String,Long> hm = new HashMap<String,Long>();
			hm.put("preLogId", pre_maxLogId);
			hm.put("maxLogId", maxLogId);
			
			//hm.put("maxLogId", 4l);
			//hm.put("maxLogId", 219974l);
			WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
			final CopyOnWriteArrayList<Websec> websecList = (CopyOnWriteArrayList<Websec>) websecDao.selectSeg(hm);
			int websecListLenth = websecList.size();
			CopyOnWriteArrayList<Websec> seg_websecList = null ;
			for(int i=0 ; i< 10 ; i++){
				if(i!= 10-1){
					seg_websecList = new CopyOnWriteArrayList<Websec>(websecList.subList(i*websecListLenth/10,(i+1)*websecListLenth/10));
				}else{
					seg_websecList = new CopyOnWriteArrayList<Websec>(websecList.subList(i*websecListLenth/10,websecListLenth));
				}
				MultiThread m = new MultiThread(seg_websecList,ctx);
				new Thread(m).start();
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
