package com.cn.ctbri.southapi.adapter.basedata;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.tomcat.jni.Local;

import com.cn.ctbri.southapi.adapter.batis.inter.TCityLocationMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TIpv4LatlongMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TViewIpv4LocationMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TWebPhishMapper;
import com.cn.ctbri.southapi.adapter.batis.model.TCityLocation;
import com.cn.ctbri.southapi.adapter.batis.model.TCityLocationExample;
import com.cn.ctbri.southapi.adapter.batis.model.TIpv4Latlong;
import com.cn.ctbri.southapi.adapter.batis.model.TIpv4LatlongExample;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4Location;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4LocationExample;
import com.cn.ctbri.southapi.adapter.batis.model.TWebPhish;
import com.cn.ctbri.southapi.adapter.batis.model.TWebPhishExample;
import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;
import com.cn.ctbri.southapi.adapter.utils.IPUtility;

import net.sf.json.JSONArray;
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
	
	private  SqlSession getOpenPhishSqlSession() throws IOException{
		Reader reader;
		reader = Resources.getResourceAsReader(DeviceAdapterConstant.RESOURCE_DATABASE_CONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "openphish");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	
	//根据ip地址查询地理位置信息
	//XXX 效率低，重新设计开发
	public String getLocationFromIp(JSONObject jsonObject) {
		if (jsonObject.get("ip")==null||jsonObject.getString("ip").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Ip is null!!!");
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
	
	public String getIpLatlongFromIpList(JSONObject jsonObject) {
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
			locationFromIpListObject.put("latlongList", locationArray);
			return locationFromIpListObject.toString();
		}catch(IOException exception){
			return null;
		}
		
	}
	
	//获取地址库中的数据条数
	public String getIpLatlongCount() {
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
	
	//TODO 已解决mybatis generator不支持limit的问题，等待开发
	/**
	 * 
	 * 推荐查询范围50000以内，10000以内效率较高
	 * @param jsonObject
	 * @return
	 */
	public String getLocationInBlock(JSONObject jsonObject){
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
			errorJsonObject.put("message", "Database error");
			return errorJsonObject.toString();
		}
				
	}
	
	public String getCountInChinaFromIpDatabase() {
		SqlSession sqlSession = null;
		try {
			//直接查询全部条数
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			List<String> list = new ArrayList<String>();
			list.add("CH");
			list.add("HK");
			list.add("TW");
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
			return countJsonObject.toString();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Can not connect Database!!!");
			return errorJsonObject.toString();
		}
	}
	
	public String getLocationInChinaBlock(JSONObject jsonObject) {
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
			return "Database error";
		}
	}

	//4.2国家地理数据
	
	//获取国内地理信息总数
	public String getLocationCNCount() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			List<String> list = new ArrayList<String>();
			list.add("CH");
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
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		}

	}
	
	//获取国内地理信息数据
	public String getLocationCNBlock(){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			List<String> list = new ArrayList<String>();
			list.add("CH");
			list.add("HK");
			list.add("TW");
			tCityLocationExample.or().andCountryIsoCodeIn(list);
			List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
			JSONArray locationJsonArray = new JSONArray();
			for (TCityLocation tCityLocation : cityLocationList) {
				JSONObject locationJsonObject = new JSONObject();
				locationJsonObject.put("country", tCityLocation.getCountryName());
				locationJsonObject.put("country_iso_code", tCityLocation.getCountryIsoCode());
				locationJsonObject.put("subdivision_1_name", tCityLocation.getSubdivision1Name());
				locationJsonObject.put("subdivision_2_name", tCityLocation.getSubdivision2Name());
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
		}

	}
	
	//获取国内地理信息总数
		public String getLocationCount() {
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
				errorJsonObject.put("message", "Database error!!!");
				return errorJsonObject.toString();
			}

		}
		
		//获取国内地理信息数据
	public String getLocationBlock(){
			SqlSession sqlSession = null;
			try {
				sqlSession = getSqlSession();
				TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
				TCityLocationExample tCityLocationExample = new TCityLocationExample();
				List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
				JSONArray locationJsonArray = new JSONArray();
				for (TCityLocation tCityLocation : cityLocationList) {
					JSONObject locationJsonObject = new JSONObject();
					locationJsonObject.put("country", tCityLocation.getCountryName());
					locationJsonObject.put("country_iso_code", tCityLocation.getCountryIsoCode());
					locationJsonObject.put("subdivision_1_name", tCityLocation.getSubdivision1Name());
					locationJsonObject.put("subdivision_2_name", tCityLocation.getSubdivision2Name());
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
			}

	}
	
	public String getMalUrlCNToday() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).isValid();
			webPhishExample.or().andVerifiedTimeLike("%"+sdf.format(new Date())+"%");
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		}
	}
	
	public String getMalUrlToday() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).isValid();
			webPhishExample.or().andVerifiedTimeLike("%"+sdf.format(new Date())+"%");
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		}
	}
	
	public String getMalUrlCNPeriod(JSONObject jsonObject) {
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begDate = sdf.parse(jsonObject.getString("begDate"));
			Date endDate = sdf.parse(jsonObject.getString("endDate"));
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).isValid();
			webPhishExample.or().andVerifiedTimeBetween(sdf.format(begDate), sdf.format(endDate));
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
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
		}
	}
	
	public String getMalUrlPeriod(JSONObject jsonObject) {
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begDate = sdf.parse(jsonObject.getString("begDate"));
			Date endDate = sdf.parse(jsonObject.getString("endDate"));
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).isValid();
			webPhishExample.or().andVerifiedTimeBetween(sdf.format(begDate), sdf.format(endDate));
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
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
		}
	}
	
	public String getMalUrlCN() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).isValid();
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			JSONArray malUrlJsonArray = new JSONArray();
			for (TWebPhish tWebPhish : webPhishList) {
				JSONObject malUrlJsonObject = new JSONObject();
				malUrlJsonObject.put("url", tWebPhish.getWebphishUrl());
				malUrlJsonObject.put("field", tWebPhish.getWebphishField());
				malUrlJsonObject.put("", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		}
	}
	
	public String getMalUrl() {
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
				malUrlJsonObject.put("", tWebPhish.getWebphishDomain());
				malUrlJsonObject.put("", tWebPhish.getWebphishIp());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsn());
				malUrlJsonObject.put("", tWebPhish.getWebphishAsnname());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision1());
				malUrlJsonObject.put("", tWebPhish.getWebphishSubdivision2());
				malUrlJsonObject.put("", tWebPhish.getWebphishCity());
				malUrlJsonObject.put("", tWebPhish.getWebphishTarget());
				malUrlJsonObject.put("",tWebPhish.getVerifiedTime());
				malUrlJsonObject.put("", tWebPhish.getIsvalid());
				malUrlJsonArray.add(malUrlJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("webphishList", malUrlJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		}
	}
	
	public String getTargetListByCN() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
			List<String> cnCodeList = new ArrayList<String>();
			cnCodeList.add("CN");
			cnCodeList.add("HK");
			cnCodeList.add("TW");
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).isValid();
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			List<String> targetList = new ArrayList<String>();
			for (TWebPhish tWebPhish : webPhishList) {
				targetList.add(tWebPhish.getWebphishTarget());
			}
			List targetListWithoutDup = new ArrayList(new HashSet(targetList));
			JSONArray targetJsonArray = JSONArray.fromObject(targetListWithoutDup);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("targetList", targetJsonArray);
			return returnJsonObject.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		}
	}
	
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
			returnJsonObject.put("targetList", targetJsonArray);
			return returnJsonObject.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
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
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).isValid();
			
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			List<TWebPhish> webPhishList = webPhishMapper.selectByExample(webPhishExample);
			List<String> targetList = new ArrayList<String>();
			for (TWebPhish tWebPhish : webPhishList) {
				targetList.add(tWebPhish.getWebphishTarget());
			}
			List targetListWithoutDup = new ArrayList(new HashSet(targetList));
			JSONArray targetJsonArray = JSONArray.fromObject(targetListWithoutDup);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("targetList", targetJsonArray);
			return returnJsonObject.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		}
	}
	
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
			webPhishExample.createCriteria().andWebphishCountrycodeIn(cnCodeList).isValid();
			webPhishExample.createCriteria().andWebphishTargetLike("%"+jsonObject.getString("target")+"%");
			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			//TODO List<TWebPhish> 
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static void main(String args[]){
		IPDataBaseAdapter adapter = new IPDataBaseAdapter();
		JSONObject object = new JSONObject();
		object.put("ip", "1.27.89.1");
		object.put("begIndex", "1");
		object.put("endIndex", "10000");
		JSONArray array = new JSONArray();
		array.add("1.27.89.1");
		array.add("1.229.205.1");
		object.put("ipList", array);
		System.out.println(adapter.getTargetList());
		//System.out.println(adapter.getSessionTets());
		//System.out.println(adapter.getLocationFromIpList(object));
		//System.out.println(System.currentTimeMillis()+"\r\n"+adapter.getLocationInChinaBlock(object)+"\r\n"+System.currentTimeMillis());
	}
}
