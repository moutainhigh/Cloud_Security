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

import com.cn.ctbri.southapi.adapter.batis.mapper.TCityLocationMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TIpv4LatlongMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TViewIpv4LocationMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TViewWebPhishCountryCountMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TViewWebPhishFieldCountMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TViewWebPhishProvinceCountMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TViewWebPhishTargetCountMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TWebPhishMapper;
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
	//??????sqlSession
	private  SqlSession getSqlSession() throws IOException{
		return CommonDatabaseController.getSqlSession();
	}
	//sqlsession??????
	private static void closeSqlSession(SqlSession sqlSession){
		CommonDatabaseController.closeSqlSession(sqlSession);
	}
	
	private  SqlSession getOpenPhishSqlSession() throws IOException{
		return CommonDatabaseController.getOpenPhishSqlSession();
	}
	
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
			//??????ip???????????????ip????????????????????????
				//ip??????????????????????????????json???????????????ip
				String ip = jsonObject.getString("ip");
				JSONObject locationFromIpObject = new JSONObject();
				locationFromIpObject.put("ip", ip);
				
				//??????ip????????????????????????
				TIpv4LatlongExample ipLatlongExample = new TIpv4LatlongExample();
				//????????????????????????ip??????????????????ip?????????ip??????????????????ip?????????????????????????????????????????????????????????????????????
				ipLatlongExample.or().andStartipLessThanOrEqualTo(IPUtility.ip2long(ip)).andEndipGreaterThanOrEqualTo(IPUtility.ip2long(ip));
				ipLatlongExample.setOrderByClause("netmask desc");
				
				//?????????????????????????????????????????????ip
				TIpv4LatlongMapper ipLatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
				List<TIpv4Latlong> tIpv4Latlongs = ipLatlongMapper.selectByExample(ipLatlongExample);
				if(tIpv4Latlongs.isEmpty()){
					return locationFromIpObject.toString();
				}
				
				//???????????????????????????????????????????????????????????????????????????
				TIpv4Latlong tIpv4Latlong = tIpv4Latlongs.get(0);
				locationFromIpObject.put("network", tIpv4Latlong.getNetwork());
				locationFromIpObject.put("netmask", tIpv4Latlong.getNetmask());
				//??????ip?????????????????????????????????ip??????????????????
				TViewIpv4LocationExample locationExample = new TViewIpv4LocationExample();
				locationExample.createCriteria().andLatlongIdEqualTo(tIpv4Latlong.getLatlongId());
				TViewIpv4LocationMapper ipLocationMapper = sqlSession.getMapper(TViewIpv4LocationMapper.class);
				List<TViewIpv4Location> locations = ipLocationMapper.selectByExample(locationExample);
				

				//????????????????????????????????????????????????????????????????????????????????????????????????ip??????????????????????????????????????????
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
			//?????????????????????????????????????????????????????????????????????????????????????????????
			exception.printStackTrace();			
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Get location from IP error!!!");
			return errorJsonObject.toString();
		}finally {
			//????????????
			closeSqlSession(sqlSession);
		}
	}
	//??????ip??????????????????????????????
	//XXX ??????????????????????????????
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
			//??????ip???????????????ip????????????????????????
				//ip??????????????????????????????json???????????????ip
				String ip = jsonObject.getString("ip");
				JSONObject locationFromIpObject = new JSONObject();
				locationFromIpObject.put("ip", ip);
				
				//??????ip????????????????????????
				TIpv4LatlongExample ipLatlongExample = new TIpv4LatlongExample();
				//????????????????????????ip??????????????????ip?????????ip??????????????????ip?????????netmask??????????????????????????????????????????????????????
				ipLatlongExample.or().andStartipLessThanOrEqualTo(IPUtility.ip2long(ip)).andEndipGreaterThanOrEqualTo(IPUtility.ip2long(ip));
				ipLatlongExample.setOrderByClause("netmask desc");
				
				//?????????????????????????????????????????????ip
				TIpv4LatlongMapper ipLatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
				List<TIpv4Latlong> tIpv4Latlongs = ipLatlongMapper.selectByExample(ipLatlongExample);
				if(tIpv4Latlongs.isEmpty()){
					return locationFromIpObject.toString();
				}
				
				//???????????????????????????????????????????????????????????????????????????
				TIpv4Latlong tIpv4Latlong = tIpv4Latlongs.get(0);
				locationFromIpObject.put("network", tIpv4Latlong.getNetwork());
				locationFromIpObject.put("netmask", tIpv4Latlong.getNetmask());
				//??????ip?????????????????????????????????ip??????????????????
				TViewIpv4LocationExample locationExample = new TViewIpv4LocationExample();
				locationExample.createCriteria().andLatlongIdEqualTo(tIpv4Latlong.getLatlongId());
				TViewIpv4LocationMapper ipLocationMapper = sqlSession.getMapper(TViewIpv4LocationMapper.class);
				List<TViewIpv4Location> locations = ipLocationMapper.selectByExample(locationExample);
				

				//????????????????????????????????????????????????????????????????????????????????????????????????ip??????????????????????????????????????????
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
			//?????????????????????????????????????????????????????????????????????????????????????????????
			exception.printStackTrace();			
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Get location from IP error!!!");
			return errorJsonObject.toString();
		}finally {
			//????????????
			closeSqlSession(sqlSession);
		}
	}
	
	
	//2.??????IP?????????????????????IP???????????????
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
				//??????ip????????????????????????
				TIpv4LatlongExample ipLatlongExample = new TIpv4LatlongExample();
				
				//????????????????????????ip??????????????????ip?????????ip??????????????????ip?????????????????????????????????????????????????????????????????????
				ipLatlongExample.or().andStartipLessThanOrEqualTo(IPUtility.ip2long(ip)).andEndipGreaterThanOrEqualTo(IPUtility.ip2long(ip));
				ipLatlongExample.setOrderByClause("netmask desc");
				
				//?????????????????????????????????????????????ip
				TIpv4LatlongMapper ipLatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
				List<TIpv4Latlong> tIpv4Latlongs = ipLatlongMapper.selectByExample(ipLatlongExample);
				if(tIpv4Latlongs.isEmpty()){
					return locationFromIpObject.toString();
				}
				
				//???????????????????????????????????????????????????????????????????????????
				TIpv4Latlong tIpv4Latlong = tIpv4Latlongs.get(0);
				locationFromIpObject.put("network", tIpv4Latlong.getNetwork());
				locationFromIpObject.put("netmask", tIpv4Latlong.getNetmask());
				//??????ip?????????????????????????????????ip??????????????????
				TViewIpv4LocationExample locationExample = new TViewIpv4LocationExample();
				locationExample.createCriteria().andLatlongIdEqualTo(tIpv4Latlong.getLatlongId());
				TViewIpv4LocationMapper ipLocationMapper = sqlSession.getMapper(TViewIpv4LocationMapper.class);
				List<TViewIpv4Location> locations = ipLocationMapper.selectByExample(locationExample);
				

				//????????????????????????????????????????????????????????????????????????????????????????????????ip??????????????????????????????????????????
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
	//3.????????????ip???????????????????????????
	public String getIpLatlongCNCount() {
		SqlSession sqlSession = null;
		try {
			//????????????????????????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	//4.????????????IP????????????????????????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	//5.?????????????????????????????????
	public String getIpLatlongTotalCount() {
		SqlSession sqlSession = null;
		try {
			//????????????????????????
			sqlSession = getSqlSession();
			TIpv4LatlongMapper ipv4LatlongMapper = sqlSession.getMapper(TIpv4LatlongMapper.class);
			
			JSONObject countJsonObject = new JSONObject();
			countJsonObject.put("count", ipv4LatlongMapper.countByExample(new TIpv4LatlongExample()));
			return countJsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			//??????????????????????????????????????????????????????????????????
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Get count from IP error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//6.????????????IP????????????????????????
	/**
	 * 
	 * ??????????????????50000?????????10000??????????????????
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
	 * ??????????????????-????????????????????????
	 * ???????????????5
	 */
	//5.1????????????????????????????????????
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
	//5.2?????????????????????????????????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}*/
	
	public String getNationLocationCNByCity(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			tCityLocationExample.createCriteria().andCityNameEqualTo(jsonObject.getString("city"));
			List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
			TCityLocation tCityLocation = cityLocationList.get(0);
			JSONObject locationJsonObject = new JSONObject();
			locationJsonObject.put("subdivision_1_name", tCityLocation.getSubdivision1Name());
			locationJsonObject.put("subdivision_1_iso_code", tCityLocation.getSubdivision1IsoCode());
			locationJsonObject.put("city", tCityLocation.getCityName());
			locationJsonObject.put("timezone", tCityLocation.getTimeZone());
			return locationJsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "param error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	public String getNationLocationCNByCities(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			JSONArray cityJsonArray = jsonObject.getJSONArray("cities");
			List<String> cityList = JSONArray.toList(cityJsonArray);
			tCityLocationExample.createCriteria().andCityNameIn(cityList);
			List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
			JSONArray locationJsonArray = new JSONArray();
			for (TCityLocation tCityLocation : cityLocationList) {
				JSONObject locationJsonObject = new JSONObject();
				locationJsonObject.put("subdivision_1_name", tCityLocation.getSubdivision1Name());
				locationJsonObject.put("subdivision_1_iso_code", tCityLocation.getSubdivision1IsoCode());	
				locationJsonObject.put("city", tCityLocation.getCityName());
				locationJsonObject.put("timezone", tCityLocation.getTimeZone());
				locationJsonArray.add(locationJsonObject);
			}			
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("locationList", locationJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "param error");
			return errorJsonObject.toString();
		} catch (ClassCastException e) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "cities' param is null");
			return errorJsonObject.toString();
		}
		finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	
	
	
	//5.2?????????????????????????????????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//5.3????????????????????????????????????
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
				e.printStackTrace();
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "database error");
				return errorJsonObject.toString();
			} finally {
				closeSqlSession(sqlSession);
			}
	}
	//?????????????????????????????????????????????
	public String getNationLocationByCity(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			tCityLocationExample.createCriteria().andCityNameEqualTo(jsonObject.getString("city"));
			List<TCityLocation> cityLocationList = tCityLocationMapper.selectByExample(tCityLocationExample);
			TCityLocation tCityLocation = cityLocationList.get(0);
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
			return locationJsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "param error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	public String getNationLocationByCities(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			JSONArray cityJsonArray = jsonObject.getJSONArray("cities");
			List<String> cityList = JSONArray.toList(cityJsonArray);
			tCityLocationExample.createCriteria().andCityNameIn(cityList);
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "param error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	
	public String getNationLocationWithParam(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TCityLocationMapper tCityLocationMapper = sqlSession.getMapper(TCityLocationMapper.class);
			TCityLocationExample tCityLocationExample = new TCityLocationExample();
			if (jsonObject.get("city")!=null||jsonObject.getString("city").length()<=0) {
				tCityLocationExample.createCriteria().andCityNameEqualTo(jsonObject.getString("city"));
			}else if (jsonObject.get("subdivision_2_iso_code")!=null||jsonObject.getString("subdivision_2_iso_code").length()<0) {
				tCityLocationExample.createCriteria().andSubdivision2IsoCodeEqualTo(jsonObject.getString("subdivision_2_iso_code"));
			}else if (jsonObject.get("subdivision_2_name")!=null||jsonObject.getString("subdivision_2_name").length()<0) {
				tCityLocationExample.createCriteria().andSubdivision2NameEqualTo(jsonObject.getString("subdivision_2_name"));
			}else if (jsonObject.get("subdivision_1_iso_code")!=null||jsonObject.getString("subdivision_1_iso_code").length()<0) {
				tCityLocationExample.createCriteria().andSubdivision1IsoCodeEqualTo(jsonObject.getString("subdivision_1_iso_code"));
			}else if (jsonObject.get("subdivision_1_name")!=null||jsonObject.getString("subdivision_1_name").length()<0) {
				tCityLocationExample.createCriteria().andSubdivision1NameEqualTo(jsonObject.getString("subdivision_1_name"));
			}else if (jsonObject.get("country_iso_code")!=null||jsonObject.getString("country_iso_code").length()<0) {
				tCityLocationExample.createCriteria().andCountryIsoCodeEqualTo(jsonObject.getString("country_iso_code"));
			}else if (jsonObject.get("country_name")!=null||jsonObject.getString("country_name").length()<0) {
				tCityLocationExample.createCriteria().andCountryNameEqualTo(jsonObject.getString("country_name"));
			}
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
			e.printStackTrace();
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
		
	}
		
	//5.4?????????????????????????????????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.1??????????????????????????????url??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.2??????????????????????????????URL??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	//3.3??????????????????????????????????????????URL??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} catch (ParseException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "parse date error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	//3.4??????????????????????????????????????????url??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} catch (ParseException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Parse date error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.5??????????????????????????????URL??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.6??????????????????????????????URL??????
	public String getMalurlData(){
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			TWebPhishExample webPhishExample = new TWebPhishExample();
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.6??????????????????????????????URL??????-??????x???
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Database error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//3.7 ??????????????????????????????URL?????????????????????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
//	1.??????????????????????????????????????????url??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	//2??????????????????????????????????????????url??????????????????????????????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//2b??????????????????????????????????????????url??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
//	3.?????????????????????url??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
//	4.?????????????????????url??????
	public String getMalUrlCount() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getOpenPhishSqlSession();
			
			TWebPhishExample webPhishExample = new TWebPhishExample();
			
			webPhishExample.createCriteria();

			TWebPhishMapper webPhishMapper = sqlSession.getMapper(TWebPhishMapper.class);
			int countNum = webPhishMapper.countByExample(webPhishExample);
			sqlSession.clearCache();
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("count", countNum);
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
//	5.??????????????????????????????url??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
//	6.????????????????????????????????????url??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//7.???????????????????????????????????????????????????url??????
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
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database error");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	//8.?????????????????????????????????????????????url??????
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
