package com.cn.ctbri.southapi.adapter.basedata;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cn.ctbri.southapi.adapter.batis.inter.TIpv4LatlongMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TViewIpv4LocationMapper;
import com.cn.ctbri.southapi.adapter.batis.model.TIpv4Latlong;
import com.cn.ctbri.southapi.adapter.batis.model.TIpv4LatlongExample;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4Location;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4LocationExample;
import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;
import com.cn.ctbri.southapi.adapter.utils.IPUtility;

import net.sf.json.JSONObject;

public class IPDataBaseAdapter {
	//获取sqlSession
	private  SqlSession getSqlSession() throws IOException{
		Reader reader;
		reader = Resources.getResourceAsReader(DeviceAdapterConstant.RESOURCE_DATABASE_CONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	//sqlsession判空
	public static void closeSqlSession(SqlSession sqlSession){
		if(sqlSession==null){
			return;
		}
		sqlSession.close();
	}
	
	//根据ip地址查询地理位置信息
	public String getLocationFromIp(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession =  getSqlSession();
			if (jsonObject.get("ip")==null||jsonObject.getString("ip").length()<=0) {
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "Ip is null!!!");
				return errorJsonObject.toString();
			}else {
				String ip = jsonObject.getString("ip");
				JSONObject locationFromIpObject = new JSONObject();
				locationFromIpObject.put("ip", ip);
				
				TIpv4LatlongExample ipLatlongExample = new TIpv4LatlongExample();
				ipLatlongExample.or().andStartipLessThanOrEqualTo(IPUtility.ip2long(ip)).andEndipGreaterThanOrEqualTo(IPUtility.ip2long(ip));
				ipLatlongExample.setOrderByClause("netmask desc");
				
				TIpv4LatlongMapper ipLatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
				List<TIpv4Latlong> tIpv4Latlongs = ipLatlongMapper.selectByExample(ipLatlongExample);
				if(tIpv4Latlongs.isEmpty()){
					return locationFromIpObject.toString();
				}
				
				TIpv4Latlong tIpv4Latlong = tIpv4Latlongs.get(0);

				
				TViewIpv4LocationExample locationExample = new TViewIpv4LocationExample();
				locationExample.createCriteria().andLatlongIdEqualTo(tIpv4Latlong.getLatlongId());
				TViewIpv4LocationMapper ipLocationMapper = sqlSession.getMapper(TViewIpv4LocationMapper.class);
				List<TViewIpv4Location> locations = ipLocationMapper.selectByExample(locationExample);
				


				if (!locations.isEmpty()) {
					TViewIpv4Location location = locations.get(0);
					locationFromIpObject.put("latitude", tIpv4Latlong.getLatitude());
					locationFromIpObject.put("longtitude", tIpv4Latlong.getLongitude());
					locationFromIpObject.put("continent", location.getContinentName());
					locationFromIpObject.put("country", location.getCountryName());
					locationFromIpObject.put("city", location.getCityName());
				}
				return locationFromIpObject.toString();
			}
		} catch (IOException exception) {
			exception.printStackTrace();			
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Get location from IP error!!!");
			return errorJsonObject.toString();
			// TODO: handle exception
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//获取地址库中的数据条数
	public String getCountFromIpDatabase() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TIpv4LatlongExample example = new TIpv4LatlongExample();
			TIpv4LatlongMapper ipv4LatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
			
			JSONObject countJsonObject = new JSONObject();
			countJsonObject.put("count", ipv4LatlongMapper.countByExample(new TIpv4LatlongExample()));
			return countJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Get count from IP error!!!");
			return errorJsonObject.toString();
		}
	}
	
	//获取范围内数据
	public String getLocationFrom(){
		return null;
	}
	

}
