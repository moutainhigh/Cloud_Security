package com.cn.ctbri.southapi.adapter.waf;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
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

import com.cn.ctbri.southapi.adapter.batis.inter.TWafLogArpMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TWafLogDdosMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TWafLogDefaceMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.TWafLogWebsecMapper;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArp;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogArpExample;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDdos;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDdosExample;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDeface;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogDefaceExample;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsec;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample;
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
		
	public String getWafLogWebsec(String dstIp) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			example.or().andDstIpEqualTo(dstIp);
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
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			example.or().andLogIdEqualTo(Long.parseLong(logId));
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
		
	public String getWafLogArp(String dstIp) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogArpExample example = new TWafLogArpExample();
			example.or().andDstIpEqualTo(dstIp);
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			List<TWafLogArp> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogArp", TWafLogArp.class);
			xStream.alias("wafLogArpList", List.class);
			String xmlString = xStream.toXML(allList);
			sqlSession.close();
			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogArpList\":\"error\"}";
		}
	}
	
	public String getWafLogArpById(String logId) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogArpExample example = new TWafLogArpExample();
			example.or().andLogIdEqualTo(Long.parseLong(logId));
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			List<TWafLogArp> allList = mapper.selectByExample(example);
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogArp", TWafLogArp.class);
			xStream.alias("wafLogArpList", List.class);
			String xmlString = xStream.toXML(allList);
			sqlSession.close();
			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogArpList\":\"error\"}";
		}
	}
	
	public String getWafLogDeface(String dstIp) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogDefaceExample example = new TWafLogDefaceExample();
			example.or().andDstIpEqualTo(dstIp);
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
			TWafLogDefaceExample example = new TWafLogDefaceExample();
			example.or().andLogIdEqualTo(Long.parseLong(logId));
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
	
	public String getWafLogDDOS(String dstIp) {
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogDdosExample example = new TWafLogDdosExample();
			example.or().andDstIpEqualTo(dstIp);
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
		try {
			SqlSession sqlSession = getSqlSession();
			TWafLogDdosExample example = new TWafLogDdosExample();
			example.or().andLogIdEqualTo(Long.parseLong(logId));
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
	


	
	private SqlSession getSqlSession() throws IOException{
		Reader reader;
		reader = Resources.getResourceAsReader(RESOURCE_DATABASE_CONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	
	
}
