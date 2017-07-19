package com.cn.ctbri.start;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cn.ctbri.dao.CityMapper;
import com.cn.ctbri.dao.IpMapper;
import com.cn.ctbri.dao.WebsecMapper;
import com.cn.ctbri.model.City;
import com.cn.ctbri.model.Ip;
import com.cn.ctbri.model.Websec;
import com.cn.ctbri.utils.IPUtility;

public class LogController {
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		 ApplicationContext ctx=null;
		 String path=System.getProperty("user.dir");
	     ctx=new FileSystemXmlApplicationContext(path+"/conf/applicationContext.xml");
	     /*WebsecMapper websecDao=(WebsecMapper) ctx.getBean("websecDao");
	     IpMapper ipDao=(IpMapper) ctx.getBean("ipDao");
	     Ip ip = getLocationId("76.123.103.168",ipDao);
	     Websec websec = new Websec();
	     if(ip != null){
	    	 websec.setIpLatlongValid(1);
	     }else
	    	 websec.setIpLatlongValid(-1);
	     CityMapper cityDao=(CityMapper) ctx.getBean("cityDao");
	     City city = cityDao.selectByPrimaryKey("1000000001");
	    
	     
	     websec.setDstCity(city.getCityName());
	     websec.setDstCountry(city.getCountryName());
	     websec.setDstCountryCode(city.getCountryIsoCode());
	     websec.setDstSubdivision1(city.getSubdivision1Name());
	     websec.setDstSubdivision2(city.getSubdivision2Name());
	     websec.setDstLatitude(ip.getLatitude());
	     websec.setDstLongitude(ip.getLongitude());
	     websec.setLogId(235873l);
	    //logid= 235873
	     websecDao.updateByPrimaryKeySelective(websec);*/
	}
	static Ip getLocationId(String ipv4,IpMapper ipDao){
		Ip ip = null;
		Long ipLong = IPUtility.ip2long(ipv4);
		List<Ip> list = (ArrayList<Ip>)ipDao.getLatLongByIp(ipLong);
		if(list.size() > 0 )
			ip = list.get(0);
		return ip;
	}
	
}
