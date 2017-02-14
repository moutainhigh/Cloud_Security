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
	//XXX 效率低，重新设计开发
	public String getLocationFromIp(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession =  getSqlSession();
			//判断ip是否为空，ip为空返回错误提示
			if (jsonObject.get("ip")==null||jsonObject.getString("ip").length()<=0) {
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "Ip is null!!!");
				return errorJsonObject.toString();
			}else {
				//ip不为空，新建位置查询json对象，存入ip
				String ip = jsonObject.getString("ip");
				JSONObject locationFromIpObject = new JSONObject();
				locationFromIpObject.put("ip", ip);
				
				//根据ip范围组装查询条件
				TIpv4LatlongExample ipLatlongExample = new TIpv4LatlongExample();
				//查询条件为：起始ip小于要查询的ip且结束ip大于要查询的ip，根据网关降序排列（确保排在前面的范围更精确）
				ipLatlongExample.or().andStartipLessThanOrEqualTo(IPUtility.ip2long(ip)).andEndipGreaterThanOrEqualTo(IPUtility.ip2long(ip));
				ipLatlongExample.setOrderByClause("netmask desc");
				
				//执行查询，如果没查到，直接返回ip
				TIpv4LatlongMapper ipLatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
				List<TIpv4Latlong> tIpv4Latlongs = ipLatlongMapper.selectByExample(ipLatlongExample);
				if(tIpv4Latlongs.isEmpty()){
					return locationFromIpObject.toString();
				}
				
				//如果查到了，取第一个值（排序后范围最小最精确地值）
				TIpv4Latlong tIpv4Latlong = tIpv4Latlongs.get(0);

				//根据ip信息中的位置编号，查询ip地理位置信息
				TViewIpv4LocationExample locationExample = new TViewIpv4LocationExample();
				locationExample.createCriteria().andLatlongIdEqualTo(tIpv4Latlong.getLatlongId());
				TViewIpv4LocationMapper ipLocationMapper = sqlSession.getMapper(TViewIpv4LocationMapper.class);
				List<TViewIpv4Location> locations = ipLocationMapper.selectByExample(locationExample);
				

				//如果地理位置信息不为空，则填充地理位置信息，如果为空，则直接返回ip查询结果（不带地理位置信息）
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
		} catch (Exception exception) {
			//如果执行查询操作时产生错误，返回状态失败，提示获取位置产生错误
			exception.printStackTrace();			
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Get location from IP error!!!");
			return errorJsonObject.toString();
		}finally {
			//关闭连接
			closeSqlSession(sqlSession);
		}
	}
	
	//TODO 已解决mybatis generator不支持limit的问题，等待开发
	public String getLocationWithLimit(JSONObject jsonObject){
		SqlSession sqlSession = null;
		
		return null;
				
	}
	//获取地址库中的数据条数
	public String getCountFromIpDatabase() {
		SqlSession sqlSession = null;
		try {
			//直接查询全部条数
			sqlSession = getSqlSession();
			TIpv4LatlongMapper ipv4LatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
			
			JSONObject countJsonObject = new JSONObject();
			countJsonObject.put("count", ipv4LatlongMapper.countByExample(new TIpv4LatlongExample()));
			return countJsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			//如果查询产生错误，返回状态失败，提示错误信息
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Get count from IP error!!!");
			return errorJsonObject.toString();
		}
	}
	
	//获取范围内数据
	public String getLocationFrom(){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TIpv4LatlongMapper ipv4LatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
			JSONObject jsonObject = new JSONObject();
			TIpv4LatlongExample ipv4LatlongExample = new TIpv4LatlongExample();
			
			
		} catch (IOException e) {
			// TODO: handle exception
		}
		return null;
	}
	public static void main(String args[]){
		IPDataBaseAdapter adapter = new IPDataBaseAdapter();
		JSONObject object = new JSONObject();
		object.put("ip", ".2.76.1");
		System.out.println(adapter.getLocationFromIp(object));
	}
	

}
