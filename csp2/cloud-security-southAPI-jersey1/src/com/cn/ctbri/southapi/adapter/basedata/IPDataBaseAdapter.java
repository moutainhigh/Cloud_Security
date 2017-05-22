package com.cn.ctbri.southapi.adapter.basedata;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cn.ctbri.southapi.adapter.batis.inter.TCityLocationMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TIpv4LatlongMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TViewIpv4LocationMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TViewWebPhishCountryCountMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TViewWebPhishFieldCountMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TViewWebPhishProvinceCountMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TViewWebPhishTargetCountMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TWebPhishMapper;
import com.cn.ctbri.southapi.adapter.batis.model.TCityLocation;
import com.cn.ctbri.southapi.adapter.batis.model.TCityLocationExample;
import com.cn.ctbri.southapi.adapter.batis.model.TIpv4Latlong;
import com.cn.ctbri.southapi.adapter.batis.model.TIpv4LatlongExample;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4Location;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4LocationExample;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishCountryCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishCountryCountExample;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishFieldCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishFieldCountExample;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishProvinceCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishProvinceCountExample;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishTargetCount;
import com.cn.ctbri.southapi.adapter.batis.model.TViewWebPhishTargetCountExample;
import com.cn.ctbri.southapi.adapter.batis.model.TWebPhish;
import com.cn.ctbri.southapi.adapter.batis.model.TWebPhishExample;
import com.cn.ctbri.southapi.adapter.common.CommonDatabaseController;
import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;
import com.cn.ctbri.southapi.adapter.utils.IPUtility;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class IPDataBaseAdapter {	
	//获取sqlSession
	private  SqlSession getSqlSession() throws IOException{
		return CommonDatabaseController.getSqlSession();
	}
	//sqlsession判空
	private static void closeSqlSession(SqlSession sqlSession){
		CommonDatabaseController.closeSqlSession(sqlSession);
	}
	
	private  SqlSession getOpenPhishSqlSession() throws IOException{
		return CommonDatabaseController.getOpenPhishSqlSession();
	}
	
	
	//根据ip地址查询地理位置信息
	//XXX 效率低，重新设计开发
	public String getLatlongByIP(JSONObject jsonObject) {
		if (jsonObject.get("ip")==null||jsonObject.getString("ip").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "parameter error");
			return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
		try {
			sqlSession =  getSqlSession();
			//判断ip是否为空，ip为空返回错误提示
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
				locationFromIpObject.put("network", tIpv4Latlong.getNetwork());
				locationFromIpObject.put("netmask", tIpv4Latlong.getNetmask());
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
					locationFromIpObject.put("subdivisionNo1Name", location.getSubdivision1Name());
					locationFromIpObject.put("subdivisionNo2Name", location.getSubdivision2Name());
					locationFromIpObject.put("city", location.getCityName());
					locationFromIpObject.put("countryISOCode", location.getCountryIsoCode());
				}
				locationFromIpObject.put("status", "success");
				return locationFromIpObject.toString();
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
	
	
	//2.根据IP地址列表获取多IP经纬度数据
	public String getLatlongByIpList(JSONObject jsonObject) {
		if (jsonObject.get("ipList")==null
		||jsonObject.getJSONArray("ipList")==null
		||jsonObject.getJSONArray("ipList").get(0)==null
		||jsonObject.getJSONArray("ipList").getString(0).length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Ip is null!!!");
			return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
		try {
			sqlSession =  getSqlSession();
			JSONObject locationFromIpListObject = new JSONObject();
			JSONArray locationArray = new JSONArray();
			List<String> ipList = JSONArray.toList(jsonObject.getJSONArray("ipList"));
			for (String ip : ipList) {
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
				locationFromIpObject.put("network", tIpv4Latlong.getNetwork());
				locationFromIpObject.put("netmask", tIpv4Latlong.getNetmask());
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
					locationFromIpObject.put("subdivisionNo1Name", location.getSubdivision1Name());
					locationFromIpObject.put("subdivisionNo2Name", location.getSubdivision2Name());
					locationFromIpObject.put("city", location.getCityName());
					locationFromIpObject.put("countryISOCode", location.getCountryIsoCode());
				}
				locationArray.add(locationFromIpObject);
			}
			locationFromIpListObject.put("status", "success");
			locationFromIpListObject.put("latlongList", locationArray);
			return locationFromIpListObject.toString();
		}catch(IOException exception){
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
		
	}
	//3.获取国内ip地址经纬度数据总数
	public String getIpLatlongCNCount() {
		SqlSession sqlSession = null;
		try {
			//直接查询全部条数
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			List<String> list = new ArrayList<String>();
			list.add("CN");
			list.add("HK");
			list.add("TW");
			list.add("MO");
			tCityLocationExample.or().andCountryIsoCodeIn(list);
			List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
			List<Long> locationIdList = new ArrayList<Long>();
			for (TCityLocation tCityLocation : cityLocationList) {
				locationIdList.add(tCityLocation.getLocationId());
			}
			TIpv4LatlongMapper ipv4LatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
			TIpv4LatlongExample tIpv4LatlongExample = new TIpv4LatlongExample();
			tIpv4LatlongExample.or().andLocationIdIn(locationIdList);
			JSONObject countJsonObject = new JSONObject();
			countJsonObject.put("count", ipv4LatlongMapper.countByExample(tIpv4LatlongExample));
			countJsonObject.put("status", "success");
			return countJsonObject.toString();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	//4.获取国内IP地址经纬度数据块
	public String getIpLatlongCNDataBlock(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			String begIndex = jsonObject.getString("begIndex");
			String endIndex = jsonObject.getString("endIndex");
			TIpv4LatlongExample ipLatlongExample = new TIpv4LatlongExample();
			
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			List<String> list = new ArrayList<String>();
			list.add("CN");
			list.add("HK");
			list.add("TW");
			list.add("MO");
			tCityLocationExample.or().andCountryIsoCodeIn(list);
			List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
			List<Long> locationIdList = new ArrayList<Long>();
			for (TCityLocation tCityLocation : cityLocationList) {
				locationIdList.add(tCityLocation.getLocationId());
			}
			TIpv4LatlongMapper ipv4LatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
			ipLatlongExample.createCriteria().andLocationIdIn(locationIdList);
			ipLatlongExample.setOrderByClause("latlong_id asc");
			ipLatlongExample.setOffset(String.valueOf(Integer.parseInt(begIndex)-1));
			ipLatlongExample.setRows(String.valueOf(Integer.parseInt(endIndex)-1));
			List<TIpv4Latlong> ipv4LatlongList = ipv4LatlongMapper.selectByExample(ipLatlongExample);
			JSONArray locationJsonArray = new JSONArray();
			for (TIpv4Latlong tIpv4Latlong : ipv4LatlongList) {
				JSONObject locationJsonObject = new JSONObject();
				locationJsonObject.put("network", tIpv4Latlong.getNetwork());
				locationJsonObject.put("netmask", tIpv4Latlong.getNetmask());
				locationJsonObject.put("latitude", tIpv4Latlong.getLatitude());
				locationJsonObject.put("longtitude", tIpv4Latlong.getLongitude());
				TCityLocation cityLocation = tCityLocationMapper.selectByPrimaryKey(tIpv4Latlong.getLocationId());
				if (cityLocation!=null) {
					locationJsonObject.put("continent", cityLocation.getContinentName());
					locationJsonObject.put("country", cityLocation.getCountryName());
					locationJsonObject.put("country_iso_code", cityLocation.getCountryIsoCode());
					locationJsonObject.put("subdivision_1_name", cityLocation.getSubdivision1Name());
					locationJsonObject.put("subdivision_2_name", cityLocation.getSubdivision2Name());
					locationJsonObject.put("city", cityLocation.getCityName());
				}

				locationJsonArray.add(locationJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("latlongList", locationJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	//5.获取地址库中的数据条数
	public String getIpLatlongTotalCount() {
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
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//6.获取全球IP地址经纬度数据块
	/**
	 * 
	 * 推荐查询范围50000以内，10000以内效率较高
	 * @param jsonObject
	 * @return
	 */
	public String getIpLatlongDataBlock(JSONObject jsonObject){
		if (jsonObject.get("begIndex")==null
		||jsonObject.getString("begIndex")==null
		||jsonObject.get("endIndex")==null
		||jsonObject.getString("endIndex")==null){
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Index error");
			return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			String begIndex = jsonObject.getString("begIndex");
			String endIndex = jsonObject.getString("endIndex");
			TIpv4LatlongExample ipLatlongExample = new TIpv4LatlongExample();
			ipLatlongExample.or().andLatlongIdBetween(Long.parseLong(begIndex), Long.parseLong(endIndex));
			TIpv4LatlongMapper ipv4LatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
			List<TIpv4Latlong> ipv4LatlongList =  ipv4LatlongMapper.selectByExample(ipLatlongExample);
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			JSONArray locationJsonArray = new JSONArray();
			for (TIpv4Latlong tIpv4Latlong : ipv4LatlongList) {
				JSONObject locationJsonObject = new JSONObject();
				locationJsonObject.put("network", tIpv4Latlong.getNetwork());
				locationJsonObject.put("netmask", tIpv4Latlong.getNetmask());
				locationJsonObject.put("latitude", tIpv4Latlong.getLatitude());
				locationJsonObject.put("longtitude", tIpv4Latlong.getLongitude());
				TCityLocation cityLocation = tCityLocationMapper.selectByPrimaryKey(tIpv4Latlong.getLocationId());
				if (cityLocation!=null) {
					locationJsonObject.put("continent", cityLocation.getContinentName());
					locationJsonObject.put("country", cityLocation.getCountryName());
					locationJsonObject.put("country_iso_code", cityLocation.getCountryIsoCode());
					locationJsonObject.put("subdivision_1_name", cityLocation.getSubdivision1Name());
					locationJsonObject.put("subdivision_2_name", cityLocation.getSubdivision2Name());
					locationJsonObject.put("city", cityLocation.getCityName());
				}

				locationJsonArray.add(locationJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("latlongList", locationJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
				
	}
	

	
	

	/*
	 * 安全基础数据-国家地理数据接口
	 * 文档编号：5
	 */
	//5.1获取国内地理信息数据总数
	public String getNationLocationCNCount() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			List<String> list = new ArrayList<String>();
			list.add("CN");
			list.add("HK");
			list.add("TW");
			tCityLocationExample.or().andCountryIsoCodeIn(list);
			int recordCount = tCityLocationMapper.countByExample(tCityLocationExample);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("recordCount", recordCount);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}

	}
	/*
	//5.2获取国内地理信息数据块
	public String getNationLocationCNDataBlock(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			String begIndex = jsonObject.getString("begIndex");
			String endIndex = jsonObject.getString("endIndex");
			TIpv4LatlongExample ipLatlongExample = new TIpv4LatlongExample();
			
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			List<String> list = new ArrayList<String>();
			list.add("CH");
			list.add("HK");
			list.add("TW");
			list.add("MO");
			tCityLocationExample.or().andCountryIsoCodeIn(list);
			List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
			List<Long> locationIdList = new ArrayList<Long>();
			for (TCityLocation tCityLocation : cityLocationList) {
				locationIdList.add(tCityLocation.getLocationId());
			}
			TIpv4LatlongMapper ipv4LatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
			ipLatlongExample.createCriteria().andLocationIdIn(locationIdList);
			ipLatlongExample.setOrderByClause("latlong_id asc");
			ipLatlongExample.setOffset(String.valueOf(Integer.parseInt(begIndex)-1));
			ipLatlongExample.setRows(String.valueOf(Integer.parseInt(endIndex)-1));
			List<TIpv4Latlong> ipv4LatlongList = ipv4LatlongMapper.selectByExample(ipLatlongExample);
			JSONArray locationJsonArray = new JSONArray();
			for (TIpv4Latlong tIpv4Latlong : ipv4LatlongList) {
				JSONObject locationJsonObject = new JSONObject();
				locationJsonObject.put("network", tIpv4Latlong.getNetwork());
				locationJsonObject.put("netmask", tIpv4Latlong.getNetmask());
				locationJsonObject.put("latitude", tIpv4Latlong.getLatitude());
				locationJsonObject.put("longtitude", tIpv4Latlong.getLongitude());
				TCityLocation cityLocation = tCityLocationMapper.selectByPrimaryKey(tIpv4Latlong.getLocationId());
				if (cityLocation!=null) {
					locationJsonObject.put("continent", cityLocation.getContinentName());
					locationJsonObject.put("country", cityLocation.getCountryName());
					locationJsonObject.put("country_iso_code", cityLocation.getCountryIsoCode());
					locationJsonObject.put("subdivision_1_name", cityLocation.getSubdivision1Name());
					locationJsonObject.put("subdivision_2_name", cityLocation.getSubdivision2Name());
					locationJsonObject.put("city", cityLocation.getCityName());
				}

				locationJsonArray.add(locationJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("latlongList", locationJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}*/
	//5.2获取国内地理信息数据块
	public String getNationLocationCNDataBlock(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			String begIndex = jsonObject.getString("begIndex");
			String endIndex = jsonObject.getString("endIndex");
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			List<String> list = new ArrayList<String>();
			list.add("CN");
			list.add("HK");
			list.add("TW");
			list.add("MO");
			tCityLocationExample.setOrderByClause("location_id asc");
			tCityLocationExample.setOffset(String.valueOf(Integer.parseInt(begIndex)-1));
			tCityLocationExample.setRows(String.valueOf(Integer.parseInt(endIndex)-1));
			tCityLocationExample.or().andCountryIsoCodeIn(list);
			List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
			JSONArray locationJsonArray = new JSONArray();
			for (TCityLocation tCityLocation : cityLocationList) {
				JSONObject locationJsonObject = new JSONObject();
				locationJsonObject.put("continent_code", tCityLocation.getContinentName());
				locationJsonObject.put("continent_name", tCityLocation.getContinentCode());
				locationJsonObject.put("country", tCityLocation.getCountryName());
				locationJsonObject.put("country_iso_code", tCityLocation.getCountryIsoCode());
				locationJsonObject.put("subdivision_1_name", tCityLocation.getSubdivision1Name());
				locationJsonObject.put("subdivision_1_iso_code", tCityLocation.getSubdivision1IsoCode());
				locationJsonObject.put("subdivision_2_name", tCityLocation.getSubdivision2Name());
				locationJsonObject.put("subdivision_2_iso_code", tCityLocation.getSubdivision2IsoCode());
				locationJsonObject.put("city", tCityLocation.getCityName());
				locationJsonObject.put("timezone", tCityLocation.getTimeZone());
				locationJsonArray.add(locationJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("locationList", locationJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}

	}
	
	//5.3获取全球地理信息数据总数
	public String getNationLocationTotalCount() {
		SqlSession sqlSession = null;
			try {
				sqlSession = getSqlSession();
				TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
				TCityLocationExample tCityLocationExample = new TCityLocationExample();
				int recordCount = tCityLocationMapper.countByExample(tCityLocationExample);
				JSONObject returnJsonObject = new JSONObject();
				returnJsonObject.put("recordCount", recordCount);
				return returnJsonObject.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "database error");
				return errorJsonObject.toString();
			} finally {
				closeSqlSession(sqlSession);
			}
	}
		
	//5.4获取全球地理信息数据块
	public String getNationLocationDataBlock(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			String begIndex = jsonObject.getString("begIndex");
			String endIndex = jsonObject.getString("endIndex");
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			tCityLocationExample.setOrderByClause("location_id asc");
			tCityLocationExample.setOffset(String.valueOf(Integer.parseInt(begIndex)-1));
			tCityLocationExample.setRows(String.valueOf(Integer.parseInt(endIndex)-1));
			List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
			JSONArray locationJsonArray = new JSONArray();
			for (TCityLocation tCityLocation : cityLocationList) {
				JSONObject locationJsonObject = new JSONObject();
				
				locationJsonObject.put("continent_code", tCityLocation.getContinentName());
				locationJsonObject.put("continent_name", tCityLocation.getContinentCode());
				locationJsonObject.put("country", tCityLocation.getCountryName());
				locationJsonObject.put("country_iso_code", tCityLocation.getCountryIsoCode());
				locationJsonObject.put("subdivision_1_name", tCityLocation.getSubdivision1Name());
				locationJsonObject.put("subdivision_1_iso_code", tCityLocation.getSubdivision1IsoCode());
				locationJsonObject.put("subdivision_2_name", tCityLocation.getSubdivision2Name());
				locationJsonObject.put("subdivision_2_iso_code", tCityLocation.getSubdivision2IsoCode());
				locationJsonObject.put("city", tCityLocation.getCityName());
				locationJsonObject.put("timezone", tCityLocation.getTimeZone());
				locationJsonArray.add(locationJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("locationList", locationJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.1获取当天国内活动恶意url信息
	public String getMalurlDataByCNToday() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			cnCodeList.add("MO");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).andVerifiedTimeLike("%"+sdf.format(new Date())+"%").andIsvalidEqualTo(1);
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.2获取当天全球活动恶意URL信息
	public String getMalurlDataByToday() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			webPhishExample.createCriteria().andVerifiedTimeLike("%"+sdf.format(new Date())+"%").andIsvalidEqualTo(1);			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	//3.3获取指定时间段内国内活动恶意URL信息
	public String getMalurlDataByCNPeriod(JSONObject jsonObject) {
		if (jsonObject.get("begDate")==null
			||jsonObject.getString("begDate").length()<=0
			||jsonObject.get("endDate")==null
			||jsonObject.getString("endDate").length()<=0) {
				JSONObject errorJsonObject = new JSONObject();
				jsonObject.put("status", "failed");
				jsonObject.put("message", "begDate or endDate is null");
				return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date begDate = sdf.parse(jsonObject.getString("begDate"));
			Date endDate = sdf.parse(jsonObject.getString("endDate"));
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).andIsvalidEqualTo(1);
			webPhishExample.or().andVerifiedTimeBetween(sdf.format(begDate), sdf.format(endDate));
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "parse date error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	//3.4获取指定时间段内全球活动恶意url信息
	public String getMalurlDataByPeriod(JSONObject jsonObject) {
		if (jsonObject.get("begDate")==null
			||jsonObject.getString("begDate").length()<=0
			||jsonObject.get("endDate")==null
			||jsonObject.getString("endDate").length()<=0) {
				JSONObject errorJsonObject = new JSONObject();
				jsonObject.put("status", "failed");
				jsonObject.put("message", "begDate or endDate is null");
				return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date begDate = sdf.parse(jsonObject.getString("begDate"));
			Date endDate = sdf.parse(jsonObject.getString("endDate"));
			webPhishExample.createCriteria().andVerifiedTimeBetween(sdf.format(begDate),sdf.format(endDate)).andIsvalidEqualTo(1);

			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Parse date error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.5获取国内所有活动恶意URL信息
	public String getMalurlDataByCN() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).andIsvalidEqualTo(1);
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.6获取全球所有活动恶意URL信息
	public String getMalurlData(){
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			webPhishExample.createCriteria().isValid();
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("country", tWebPhish.getWebphishCountry());
				malUrlJsonObject.put("countryCode", tWebPhish.getWebphishCountrycode());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.6获取全球所有活动恶意URL信息-最新x条
	public String getMalurlTopData(JSONObject jsonObject){
		if (jsonObject.get("topNum")==null||jsonObject.getString("topNum").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			jsonObject.put("status", "failed");
			jsonObject.put("message", "topNum is null");
			return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			webPhishExample.createCriteria().andIsvalidEqualTo(1);
			webPhishExample.setOrderByClause("webphish_id desc");
			webPhishExample.setRows(jsonObject.getString("topNum"));
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("id", tWebPhish.getWebphishId());
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("country", tWebPhish.getWebphishCountry());
				malUrlJsonObject.put("countryCode", tWebPhish.getWebphishCountrycode());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.7 获取国内所有活动恶意URL针对的目标列表
	public String getMalurlTargetListByCN() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).andIsvalidEqualTo(1);
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			List<String> targetList = new ArrayList<String>();
			for (TWebPhish tWebPhish : webPhishList) {
				targetList.add(tWebPhish.getWebphishTarget());
			}
			List targetListWithoutDup = new ArrayList(new HashSet(targetList));
			JSONArray targetJsonArray = JSONArray.fromObject(targetListWithoutDup);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("targetList", targetJsonArray);
			return returnJsonObject.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	
	
	//3.8 
	public String getTargetList() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			webPhishExample.createCriteria().isValid();
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			List<String> targetList = new ArrayList<String>();
			for (TWebPhish tWebPhish : webPhishList) {
				targetList.add(tWebPhish.getWebphishTarget());
			}
			List targetListWithoutDup = new ArrayList(new HashSet(targetList));
			JSONArray targetJsonArray = JSONArray.fromObject(targetListWithoutDup);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("targetList", targetJsonArray);
			return returnJsonObject.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getCNTargetList() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).andIsvalidEqualTo(1);
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			List<String> targetList = new ArrayList<String>();
			for (TWebPhish tWebPhish : webPhishList) {
				targetList.add(tWebPhish.getWebphishTarget());
			}
			List targetListWithoutDup = new ArrayList(new HashSet(targetList));
			JSONArray targetJsonArray = JSONArray.fromObject(targetListWithoutDup);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("targetList", targetJsonArray);
			return returnJsonObject.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	//3.8
	public String getDataByCNTarget(JSONObject jsonObject) {
		if (jsonObject.get("target")==null||jsonObject.getString("target").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Target is null");
			return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).andWebphishTargetLike("%"+jsonObject.getString("target")+"%").andIsvalidEqualTo(1);
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			//TODO List<TWebPhish> 
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	//3.9
	public String getFieldListByCN() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).andIsvalidEqualTo(1);
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			List<String> fieldList = new ArrayList<String>();
			for (TWebPhish tWebPhish : webPhishList) {
				fieldList.add(tWebPhish.getWebphishField());
			}
			List fieldListWithoutDup = new ArrayList(new HashSet(fieldList));
			JSONArray fieldJsonArray = JSONArray.fromObject(fieldListWithoutDup);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("fieldList", fieldJsonArray);
			return returnJsonObject.toString();
			//List targetListWithoutDup = new ArrayList(new HashSet(targetList));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
	//3.10
	public String getDataByCNField(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		if (null==jsonObject.get("field")||jsonObject.getString("field").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			jsonObject.put("status", "failed");
			jsonObject.put("message", "field is null");
			return errorJsonObject.toString();
		}
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			webPhishExample.createCriteria()
				.andWebphishCountrycodeIn(cnCodeList)
				.andWebphishFieldEqualTo(jsonObject.getString("field"))
				.andIsvalidEqualTo(1);
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			webPhishMapper.selectByExample(webPhishExample);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	public String getDataByDomain(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		if (null==jsonObject.get("condition")||jsonObject.getString("condition").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "condition is null");
			return errorJsonObject.toString();
		}
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			webPhishExample.createCriteria()
				.andWebphishUrlLike("%"+jsonObject.getString("condition")+"%")
				.andIsvalidEqualTo(1);
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			webPhishMapper.selectByExample(webPhishExample);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("domain", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("ip", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("asn", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("asnName", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("subdivision1", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("subdivision2", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("city", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("target", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("verifiedTime",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("isValid", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
//	1.按国家类别分类获取有效的恶意url个数
	public String getMalUrlCountByCountryValid() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TViewWebPhishCountryCountExample webPhishCountryCountExample = new TViewWebPhishCountryCountExample();
			webPhishCountryCountExample.createCriteria().andIsvalidEqualTo(1);
			TViewWebPhishCountryCountMapper webPhishCountryCountMapper = sqlSession.getMapper(TViewWebPhishCountryCountMapper.class);
			List<TViewWebPhishCountryCount> countList = webPhishCountryCountMapper.selectByExample(webPhishCountryCountExample);
			JSONArray countJsonArray = new JSONArray();
			for (TViewWebPhishCountryCount tViewWebPhishCountryCount : countList) {
				JSONObject countJsonObject = new JSONObject();
				countJsonObject.put("country", tViewWebPhishCountryCount.getWebphishCountry());
				countJsonObject.put("countryCode", tViewWebPhishCountryCount.getWebphishCountrycode());
				countJsonObject.put("count", tViewWebPhishCountryCount.getWebphishCount());
				countJsonObject.put("isValid", tViewWebPhishCountryCount.getIsvalid());
				countJsonArray.add(countJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("countList", countJsonArray);
			
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	//2按国家类别分类获取全部的恶意url个数，带是否有效状态
	public String getMalUrlCountByCountryWithValidState() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TViewWebPhishCountryCountExample webPhishCountryCountExample = new TViewWebPhishCountryCountExample();
			TViewWebPhishCountryCountMapper webPhishCountryCountMapper = sqlSession.getMapper(TViewWebPhishCountryCountMapper.class);
			List<TViewWebPhishCountryCount> countList = webPhishCountryCountMapper.selectByExample(webPhishCountryCountExample);
			JSONArray countJsonArray = new JSONArray();
			for (TViewWebPhishCountryCount tViewWebPhishCountryCount : countList) {
				JSONObject countJsonObject = new JSONObject();
				countJsonObject.put("country", tViewWebPhishCountryCount.getWebphishCountry());
				countJsonObject.put("countryCode", tViewWebPhishCountryCount.getWebphishCountrycode());
				countJsonObject.put("count", tViewWebPhishCountryCount.getWebphishCount());
				countJsonObject.put("isValid", tViewWebPhishCountryCount.getIsvalid());
				countJsonArray.add(countJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("countList", countJsonArray);
			
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//2b按国家类别分类获取全部的恶意url个数
	public String getMalUrlAllCountByCountry() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TViewWebPhishCountryCountMapper webPhishCountryCountMapper = sqlSession.getMapper(TViewWebPhishCountryCountMapper.class);
			List<TViewWebPhishCountryCount> countList = webPhishCountryCountMapper.selectByExampleGroupBy();
			JSONArray countJsonArray = new JSONArray();
			for (TViewWebPhishCountryCount tViewWebPhishCountryCount : countList) {
				JSONObject countJsonObject = new JSONObject();
				countJsonObject.put("country", tViewWebPhishCountryCount.getWebphishCountry());
				countJsonObject.put("countryCode", tViewWebPhishCountryCount.getWebphishCountrycode());
				countJsonObject.put("count", tViewWebPhishCountryCount.getWebphishCount());
				countJsonArray.add(countJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("countList", countJsonArray);
			
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
//	3.获取有效的恶意url个数
	public String getMalUrlCountValid() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			webPhishExample.createCriteria().andIsvalidEqualTo(1);
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			int countNum = webPhishMapper.countByExample(webPhishExample);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("count", countNum);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
//	4.获取全部的恶意url个数
	public String getMalUrlCount() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			
			TWebPhishExample webPhishExample = new TWebPhishExample();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date begDate = sdf.parse("2016-01-01");
			Date endDate = sdf.parse("2017-01-01");
			webPhishExample.createCriteria();

			
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			int countNum = webPhishMapper.countByExample(webPhishExample);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("count", countNum);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "date error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getMalUrlCountInChina() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> list = new ArrayList<String>();
			list.add("CN");
			list.add("HK");
			list.add("TW");
			list.add("MO");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(list);
			int countNum = webPhishMapper.countByExample(webPhishExample);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("count", countNum);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getMalUrlCountInChinaValid() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> list = new ArrayList<String>();
			list.add("CN");
			list.add("HK");
			list.add("TW");
			list.add("MO");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(list).andIsvalidEqualTo(1);
			int countNum = webPhishMapper.countByExample(webPhishExample);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("count", countNum);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
//	5.按月份获取有效的恶意url个数
	public String getMalUrlCountByMonth(JSONObject jsonObject){
		if(null==jsonObject.get("month")||jsonObject.getInt("month")<=0){
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "month is null");
			return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Calendar c = Calendar.getInstance(); 
		    c.setTime(new Date());
		    int monthNum = jsonObject.getInt("month");
		    TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
		    TWebPhishExample webPhishExampleValid = new TWebPhishExample();
		    JSONArray monthCountJsonArray = new JSONArray();
		     for (int i = 0; i < monthNum; i++) {
		    	 Date d = c.getTime();
			     String month = sdf.format(d);
		    	 JSONObject monthCountJsonObject = new JSONObject();
		    	 webPhishExampleValid.clear();
		    	 webPhishExampleValid.createCriteria().andVerifiedTimeLessThanOrEqualTo(month).andIsvalidEqualTo(1);
		    	 int countValid = webPhishMapper.countByExample(webPhishExampleValid);
			     monthCountJsonObject.put("month", month);
		    	 monthCountJsonObject.put("count", countValid);
		    	 monthCountJsonObject.put("isvalid", 1);
		    	 monthCountJsonArray.add(monthCountJsonObject);
		    	 webPhishExampleValid.clear();
		    	 webPhishExampleValid.createCriteria().andVerifiedTimeLessThanOrEqualTo(month).andIsvalidEqualTo(0);
		    	 int count = webPhishMapper.countByExample(webPhishExampleValid);
		    	 monthCountJsonObject.clear();
			     monthCountJsonObject.put("month", month);
		    	 monthCountJsonObject.put("count", count);
		    	 monthCountJsonObject.put("isvalid", 0);
		    	 monthCountJsonArray.add(monthCountJsonObject);
		    	 c.add(Calendar.MONTH,-1);
		     }
		     JSONObject returnJsonObject = new JSONObject();
		     returnJsonObject.put("status", "success");
		     returnJsonObject.put("countList", monthCountJsonArray);
		     return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
//	6.按省分获取中国有效的恶意url个数
	public String getMalUrlCountByCNProvince() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TViewWebPhishProvinceCountExample webPhishProvinceCountExample = new TViewWebPhishProvinceCountExample();
			webPhishProvinceCountExample.createCriteria().andIsvalidEqualTo(1);
			TViewWebPhishProvinceCountMapper webPhishProvinceCountMapper = sqlSession.getMapper(TViewWebPhishProvinceCountMapper.class);
			List<TViewWebPhishProvinceCount> provinceCountList = webPhishProvinceCountMapper.selectByExample(webPhishProvinceCountExample);
			JSONArray provinceCountArray = new JSONArray();
			for (TViewWebPhishProvinceCount tViewWebPhishProvinceCount : provinceCountList) {
				JSONObject provinceCountObject = new JSONObject();
				provinceCountObject.put("countryCode", tViewWebPhishProvinceCount.getWebphishCountrycode());
				provinceCountObject.put("province", tViewWebPhishProvinceCount.getWebphishSubdivision1());
				provinceCountObject.put("count", tViewWebPhishProvinceCount.getCount());
				provinceCountArray.add(provinceCountObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("countList", provinceCountArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getMalUrlAllCountByCNProvince() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();

			TViewWebPhishProvinceCountMapper webPhishProvinceCountMapper = sqlSession.getMapper(TViewWebPhishProvinceCountMapper.class);
			List<TViewWebPhishProvinceCount> provinceCountList = webPhishProvinceCountMapper.selectByExampleWithoutValid();
			JSONArray provinceCountArray = new JSONArray();
			for (TViewWebPhishProvinceCount tViewWebPhishProvinceCount : provinceCountList) {
				JSONObject provinceCountObject = new JSONObject();
				provinceCountObject.put("countryCode", tViewWebPhishProvinceCount.getWebphishCountrycode());
				provinceCountObject.put("province", tViewWebPhishProvinceCount.getWebphishSubdivision1());
				provinceCountObject.put("count", tViewWebPhishProvinceCount.getCount());
				provinceCountArray.add(provinceCountObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("countList", provinceCountArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//7.按仿冒对象行业为单位获取有效的恶意url个数
	public String getMalurlCountByFieldTop5() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TViewWebPhishFieldCountExample fieldCountExample = new TViewWebPhishFieldCountExample();
			fieldCountExample.or().andWebphishFieldIsNotNull().andIsvalidEqualTo(1).andWebphishFieldNotEqualTo("");
			fieldCountExample.setOrderByClause("count desc");
			fieldCountExample.setRows("10");
			TViewWebPhishFieldCountMapper fieldCountMapper = sqlSession.getMapper(TViewWebPhishFieldCountMapper.class);
			List<TViewWebPhishFieldCount> fieldCountList = fieldCountMapper.selectByExample(fieldCountExample);
			JSONArray fieldCountArray = new JSONArray();
			for (TViewWebPhishFieldCount tViewWebPhishFieldCount : fieldCountList) {
				JSONObject fieldCountJsonObject = new JSONObject();
				fieldCountJsonObject.put("field", tViewWebPhishFieldCount.getWebphishField());
				fieldCountJsonObject.put("count", tViewWebPhishFieldCount.getCount());
				fieldCountJsonObject.put("isvalid", tViewWebPhishFieldCount.getIsvalid());
				fieldCountArray.add(fieldCountJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("countList", fieldCountArray);
			return returnJsonObject.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//8.按仿冒对象为单位获取有效的恶意url个数
	public String getMalurlCountByTargetTop10() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TViewWebPhishTargetCountExample targetCountExample = new TViewWebPhishTargetCountExample();
			targetCountExample.createCriteria().andWebphishTargetIsNotNull().andWebphishTargetNotEqualTo("").andWebphishTargetNotEqualTo("Other").andIsvalidEqualTo(1);
			targetCountExample.setOrderByClause("count desc");
			targetCountExample.setRows("10");
			TViewWebPhishTargetCountMapper targetCountMapper = sqlSession.getMapper(TViewWebPhishTargetCountMapper.class);
			List<TViewWebPhishTargetCount> targetCountList = targetCountMapper.selectByExample(targetCountExample);
			JSONArray targetCountJsonArray = new JSONArray();
			for (TViewWebPhishTargetCount tViewWebPhishTargetCount : targetCountList) {
				JSONObject targetCountJsonObject = new JSONObject();
				targetCountJsonObject.put("target", tViewWebPhishTargetCount.getWebphishTarget());
				targetCountJsonObject.put("count", tViewWebPhishTargetCount.getCount());
				targetCountJsonObject.put("isvalid", tViewWebPhishTargetCount.getIsvalid());
				targetCountJsonArray.add(targetCountJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("countList", targetCountJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}	
	}	
}
