package com.cn.ctbri.southapi.adapter.waf;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cn.ctbri.southapi.adapter.batis.inter.*;
import com.cn.ctbri.southapi.adapter.batis.model.*;
import com.cn.ctbri.southapi.adapter.waf.config.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class NsfocusWAFAdapter {	
	private static final String RESOURCE_DATABASE_CONFIG = "./DataBaseConf.xml";
	
	public static HashMap<Integer, NsfocusWAFOperation> mapNsfocusWAFOperation = new HashMap<Integer, NsfocusWAFOperation>();
	public static HashMap<Integer, HashMap<Integer, NsfocusWAFOperation>> mapNsfocusWAFOperationGroup = new HashMap<Integer, HashMap<Integer,NsfocusWAFOperation>>();
	public NsfocusWAFOperation nsfocusWAFOperation = null;
	public static WAFConfigManager wafConfigManager = new WAFConfigManager();
	
	
	public boolean initDeviceAdapter(String wafRoot){
		System.out.println("wafAdapter="+wafRoot);
		if(!wafConfigManager.loadWAFConfig(wafRoot)) return false;
		Iterator<?> iterator = wafConfigManager.mapWAFConfigDeviceManager.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer,WAFConfigDeviceGroup> entry = (Entry<Integer, WAFConfigDeviceGroup>)iterator.next();
			int resourceId = entry.getKey();
			if("".equals(resourceId)) continue;
			WAFConfigDeviceGroup wafConfigDeviceGroup = entry.getValue();
			Iterator<?> wafConfDevIterator = wafConfigDeviceGroup.mapWAFConfigDevice.entrySet().iterator();
			while (wafConfDevIterator.hasNext()) {
				Map.Entry<Integer, WAFConfigDevice> wafConfigDeviceEntry = (Entry<Integer, WAFConfigDevice>) wafConfDevIterator.next();
				int devId = wafConfigDeviceEntry.getKey();
				WAFConfigDevice wafConfigDevice = wafConfigDeviceEntry.getValue();
				String apiAddr = wafConfigDevice.getApiAddr();
				String apiKey = wafConfigDevice.getApiKey();
				String apiValue = wafConfigDevice.getApiValue();
				String apiUsername = wafConfigDevice.getApiUserName();
				String apiPassword = wafConfigDevice.getApiPwd();
				System.out.println(apiAddr);
				nsfocusWAFOperation = new NsfocusWAFOperation(apiAddr, apiKey, apiValue, apiUsername, apiPassword);
				mapNsfocusWAFOperation.put(devId, nsfocusWAFOperation);
			}
			mapNsfocusWAFOperationGroup.put(resourceId, mapNsfocusWAFOperation);
			 
		}
		//mapNsfocusWAFOperation.put("30001", value)
		return true;
	}
	public String getSites(int resourceId) {
		HashMap<Integer,NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONArray siteArray = new JSONArray();
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			JSONArray tempDeviceJsonArray = JSONArray.fromObject(entry.getValue().getSites());
			JSONObject tempDeviceJsonObject = new JSONObject();
			tempDeviceJsonObject.put("deviceId", entry.getKey());
			tempDeviceJsonObject.put("InfoList", tempDeviceJsonArray);
			siteArray.add(tempDeviceJsonObject);
		}
		return siteArray.toString();
	}
	
	

	public String getSites(int resourceId,int deviceId) {
		return getDeviceById(resourceId, deviceId).getSites();
	}
	
	public String getSitesInResource(int resourceId) {
		HashMap<Integer, NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONArray getSitesArray = new JSONArray();
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			JSONObject responseJsonObject = JSONObject.fromObject(entry.getValue().getSites());
			JSONObject tempDeviceJsonObject = new JSONObject();
			tempDeviceJsonObject.put("deviceId", entry.getKey());
			tempDeviceJsonObject.put("InfoList", responseJsonObject);
			getSitesArray.add(tempDeviceJsonObject);
		}
		return getSitesArray.toString();
	}
	
	
	public String getSite(int resourceId, int deviceId,JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).getSite(jsonObject);
	}
	
	public String createSite(int resourceId, JSONObject jsonObject) {
		HashMap<Integer, NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONArray createSiteArray = new JSONArray();
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			JSONObject responseJsonObject = JSONObject.fromObject(entry.getValue().createSite(jsonObject));
			JSONObject tempDeviceJsonObject = new JSONObject();
			tempDeviceJsonObject.put("deviceId", entry.getKey());
			tempDeviceJsonObject.put("InfoList", responseJsonObject);
			createSiteArray.add(tempDeviceJsonObject);
		}
		return createSiteArray.toString();
	}
	
	public String createSite(int resourceId,int deviceId,JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).createSite(jsonObject);
	}
	
	public String alterSite(int resourceId, JSONObject jsonObject) {
		HashMap<Integer, NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONArray alterSiteJsonArray = new JSONArray();
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			JSONObject responseJsonObject = JSONObject.fromObject(entry.getValue().alterSite(jsonObject));
			JSONObject tempDeviceJsonObject = new JSONObject();
			tempDeviceJsonObject.put("deviceId", entry.getKey());
			tempDeviceJsonObject.put("InfoList", responseJsonObject);
			alterSiteJsonArray.add(tempDeviceJsonObject);
		}
		return alterSiteJsonArray.toString();
	}
	
	public String alterSite(int resourceId, int deviceId, JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).alterSite(jsonObject);
	}
	
	
	public String createVirtSite(int resourceId, JSONObject jsonObject) {
		HashMap<Integer, NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONArray createVirtSiteJsonArray = new JSONArray();
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			JSONObject responseJsonObject = JSONObject.fromObject(entry.getValue().createVirtSite(jsonObject));
			JSONObject tempDeviceJsonObject = new JSONObject();
			tempDeviceJsonObject.put("deviceId", entry.getKey());
			tempDeviceJsonObject.put("InfoList", responseJsonObject);
			createVirtSiteJsonArray.add(tempDeviceJsonObject);
		}
		return createVirtSiteJsonArray.toString();
	}
	
	public String createVSite(int resourceId, int deviceId, JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).createVirtSite(jsonObject);
	}
	
	public String getVirtSite(int resourceId,int deviceId, JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).getVirtSite(jsonObject);
	}
	
	public String alterVirtSite(int resourceId,JSONObject jsonObject) {
		HashMap<Integer, NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONArray alterVirtSiteJsonArray = new JSONArray();
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			JSONObject responseJsonObject = JSONObject.fromObject(entry.getValue().alterVirtSite(jsonObject));
			JSONObject tempDeviceJsonObject = new JSONObject();
			tempDeviceJsonObject.put("deviceId", entry.getKey());
			tempDeviceJsonObject.put("InfoList", responseJsonObject);
			alterVirtSiteJsonArray.add(tempDeviceJsonObject);
		}
		return alterVirtSiteJsonArray.toString();
	}
	
	public String alterVSite(int resourceId, int deviceId,JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).alterVirtSite(jsonObject);
	}
	
	public String deleteVSite(int resourceId, JSONObject jsonObject) {
		HashMap<Integer, NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONArray deleteVirtJsonArray = new JSONArray();
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			JSONObject responseJsonObject = JSONObject.fromObject(entry.getValue().deleteVirtSite(jsonObject));
			JSONObject tempDeviceJsonObject = new JSONObject();
			tempDeviceJsonObject.put("deviceId", entry.getKey());
			tempDeviceJsonObject.put("InfoList", responseJsonObject);
			deleteVirtJsonArray.add(tempDeviceJsonObject);
		}
		return deleteVirtJsonArray.toString();
	}
	
	public String deleteVSite(int resourceId, int deviceId, JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).deleteVirtSite(jsonObject);
	}
	
	
	
	public String postIpToEth(int resourceId, int deviceId,JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).postIpToEth(jsonObject);
	}
	
	private NsfocusWAFOperation getDeviceById(int resourceId, int deviceId) {
		System.out.println("resourceId="+resourceId);
		NsfocusWAFOperation nsfocusWAFOperation = mapNsfocusWAFOperationGroup.get(resourceId).get(deviceId);
		return nsfocusWAFOperation;
	}
		
	public String getWafLogWebsec(List<String> dstIpList) {
		try {
			System.out.println(dstIpList);
			SqlSession sqlSession = getSqlSession();
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			example.or().andDstIpIn(dstIpList);
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<TWafLogWebsec> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogWebsec", TWafLogWebsec.class);
			xStream.alias("wafLogWebsecList", List.class);
			String xmlString =  xStream.toXML(allList);
			sqlSession.close();
			return xmlString;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"error\"}";
		}
	}
	
	
	public String getWafLogWebSecById(String logId) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			TWafLogWebsec wafLogWebsec = mapper.selectByPrimaryKey(Long.parseLong(logId));
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogWebsec", TWafLogWebsec.class);
			String jsonString =  xStream.toXML(wafLogWebsec);
			sqlSession.close();
			return jsonString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogWebsec\":\"error\"}";
		}
		
	}
		
	public String getWafLogWebsecInTime(JSONObject jsonObject){
		try {
			//FOR IP LIST
			int interval = jsonObject.getInt("interval");
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			System.out.println("ip="+dstIpList.toString());
			
			//FOR TIME INTERVAL
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			
			SqlSession sqlSession = getSqlSession();
			
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			example.or().andStatTimeBetween(dateBefore,dateNow).andDstIpIn(dstIpList);
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<TWafLogWebsec> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogWebsec", TWafLogWebsec.class);
			xStream.alias("wafLogWebsecList", List.class);
			String jsonString =  xStream.toXML(allList);
			sqlSession.close();
			return jsonString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"error\"}";
		}
		
	}
	
	
	
	public String getWafLogArp(List<String> dstIpList) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogArpExample example = new TWafLogArpExample();
			example.or().andDstIpIn(dstIpList);
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			List<TWafLogArp> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogArp", TWafLogArp.class);
			String xmlString = xStream.toXML(allList);
			sqlSession.close();
			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogArp\":\"error\"}";
		}
	}
	
	public String getWafLogArpById(String logId) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			TWafLogArp wafLogArp = mapper.selectByPrimaryKey(Long.parseLong(logId));
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogArp", TWafLogArp.class);
			String xmlString = xStream.toXML(wafLogArp);
			sqlSession.close();
			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogArp\":\"error\"}";
		}
	}
	
	public String getWafLogArpInTime(JSONObject jsonObject){
		SqlSession sqlSession = null;
		
		try {
			sqlSession = getSqlSession();
			int interval = jsonObject.getInt("interval");
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			System.out.println("ip="+dstIpList.toString());
			

			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			TWafLogArpExample example = new TWafLogArpExample();
			example.or().andStatTimeBetween(dateBefore, dateNow).andDstIpIn(dstIpList);
			
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			List<TWafLogArp> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogArp", TWafLogArp.class);
			xStream.alias("wafLogArpList", List.class);
			String xmlString = xStream.toXML(allList);
			return xmlString;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "{\"wafLogArpList\":\"error\"}";
		}finally {
			sqlSession.close();
		}
	}
	
	public String getWafLogDeface(List<String> dstIpList) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogDefaceExample example = new TWafLogDefaceExample();
			example.or().andDstIpIn(dstIpList);
			TWafLogDefaceMapper mapper = sqlSession.getMapper(TWafLogDefaceMapper.class);
			List<TWafLogDeface> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDeface", TWafLogDeface.class);
			xStream.alias("wafLogDefaceList", List.class);
			String xmlString = xStream.toXML(allList);
			sqlSession.close();
			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogDefaceList\":\"error\"}";
		}
		
	}
	
	public String getWafLogDefaceById(String logId) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogDefaceMapper mapper = sqlSession.getMapper(TWafLogDefaceMapper.class);
			TWafLogDeface wafLogDeface = mapper.selectByPrimaryKey(Long.parseLong(logId));
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDeface", TWafLogDeface.class);
			String xmlString = xStream.toXML(wafLogDeface);
			sqlSession.close();
			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogDeface\":\"error\"}";
		}
	}
	
	public String getWafLogDefaceInTime(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			
			int interval = jsonObject.getInt("interval");
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			System.out.println("ip="+dstIpList.toString());
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			TWafLogDefaceExample example = new TWafLogDefaceExample();
			example.or().andDstIpIn(dstIpList).andStatTimeBetween(dateBefore, dateNow);
			TWafLogDefaceMapper mapper = sqlSession.getMapper(TWafLogDefaceMapper.class);
			List<TWafLogDeface> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDeface", TWafLogDeface.class);
			xStream.alias("wafLogDefaceList", List.class);
			String xmlString = xStream.toXML(allList);
			sqlSession.close();
			return xmlString;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogDefaceList\":\"error\"}";
		} finally {
			sqlSession.close();
		}
	}
	
	public String getWafLogDDOS(List<String> dstIpList) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogDdosExample example = new TWafLogDdosExample();
			example.or().andDstIpIn(dstIpList);
			TWafLogDdosMapper mapper = sqlSession.getMapper(TWafLogDdosMapper.class);
			List<TWafLogDdos> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDDOS", TWafLogDdos.class);
			xStream.alias("wafLogDDOSList", List.class);
			String xmlString = xStream.toXML(allList);
			sqlSession.close();
			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogDDOSList\":\"error\"}";
		}
	}
	
	public String getWafLogDDOSById(String logId) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogDdosMapper mapper = sqlSession.getMapper(TWafLogDdosMapper.class);
			TWafLogDdos wafLogDdos = mapper.selectByPrimaryKey(Long.parseLong(logId));
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDDOS", TWafLogDdos.class);
			String xmlString = xStream.toXML(wafLogDdos);

			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogDDOS\":\"error\"}";
		} finally {
			sqlSession.close();
		}
	}
	
	public String getWaflogDDOSInTime(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			//IP List
			int interval = jsonObject.getInt("interval");
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			System.out.println("ip="+dstIpList.toString());
			//System Time
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			TWafLogDdosExample example = new TWafLogDdosExample();
			example.or().andDstIpIn(dstIpList).andStatTimeBetween(dateBefore, dateNow);
			TWafLogDdosMapper mapper = sqlSession.getMapper(TWafLogDdosMapper.class);
			List<TWafLogDdos> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDDOS", TWafLogDdos.class);
			xStream.alias("wafLogDDOSList", List.class);
			String xmlString = xStream.toXML(allList);
			sqlSession.close();
			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogDDOSList\":\"error\"}";
		} finally {
			sqlSession.close();
		}
	}

	
	private SqlSession getSqlSession() throws IOException{
		Reader reader;
		reader = Resources.getResourceAsReader(RESOURCE_DATABASE_CONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	public static void main(String[] args) {
		NsfocusWAFAdapter adapter = new NsfocusWAFAdapter();
		JSONObject jsonObject = new JSONObject();
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add("219.141.189.183");
		jsonArray.add("219.141.189.184");
		jsonObject.put("dstIp",jsonArray);
		jsonObject.put("interval", "3");
		String string = adapter.getWafLogWebsecInTime(jsonObject);
		System.out.println(string);
	}
	
	
	
}
