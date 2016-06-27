package com.cn.ctbri.southapi.adapter.waf;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cn.ctbri.southapi.adapter.batis.inter.*;
import com.cn.ctbri.southapi.adapter.batis.model.*;
import com.cn.ctbri.southapi.adapter.waf.config.*;
import com.cn.ctbri.southapi.adapter.waf.syslog.WAFSyslogManager;
import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;


public class NsfocusWAFAdapter {	
	private static final String[] ALERT_LEVEL_STRINGS = {"LOW","MEDIUM","HIGH"};
	
	public static HashMap<Integer, NsfocusWAFOperation> mapNsfocusWAFOperation = new HashMap<Integer, NsfocusWAFOperation>();
	public static HashMap<Integer, HashMap<Integer, NsfocusWAFOperation>> mapNsfocusWAFOperationGroup = new HashMap<Integer, HashMap<Integer,NsfocusWAFOperation>>();
	public NsfocusWAFOperation nsfocusWAFOperation = null;
	
	
	public boolean initDeviceAdapter(WAFConfigManager wafConfigManager){
		System.out.println(">>>initDeviceAdapter");
		WAFSyslogManager wsm = new WAFSyslogManager();
		wsm.initWAFSyslogManager();
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
				String[] apiPublicIp = wafConfigDevice.getDevicePublicIPList();
				String apiAddr = wafConfigDevice.getApiAddr();
				String apiKey = wafConfigDevice.getApiKey();
				String apiValue = wafConfigDevice.getApiValue();
				String apiUsername = wafConfigDevice.getApiUserName();
				String apiPassword = wafConfigDevice.getApiPwd();
				nsfocusWAFOperation = new NsfocusWAFOperation(apiAddr, apiKey, apiValue, apiUsername, apiPassword, apiPublicIp);
				mapNsfocusWAFOperation.put(devId, nsfocusWAFOperation);
			}
			mapNsfocusWAFOperationGroup.put(resourceId, mapNsfocusWAFOperation);
			 
		}
		//mapNsfocusWAFOperation.put("30001", value)
		return true;
	}
	
	private String stringToBase64(String string){
		byte[] base64Bytes = Base64.encodeBase64Chunked(string.trim().getBytes());
		return new String(base64Bytes).trim();
	}
	
	private Map<String, String> getWafEventTypeMap() throws DocumentException {
		Map<String, String> wafEventTypeMap = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
		List<Element> list = document.selectNodes("/TypeList/Type");
		for (Element element : list) {
			wafEventTypeMap.put(element.attributeValue("name"), element.getTextTrim());
		}
		return wafEventTypeMap;
		
	}
	
	private TWafLogWebsec getTWafLogWebsecBase64(TWafLogWebsec tWafLogWebsec) throws DocumentException {
		//事件类型
	
		if (tWafLogWebsec.getEventType()!=null&&!tWafLogWebsec.getEventType().equals("")) {
			//事件类型翻译 英文翻译为中文
			Map<String, String> eventTypeMap = getWafEventTypeMap();
			String eventTypeString = tWafLogWebsec.getEventType();
			if (eventTypeMap.get(eventTypeString)!=null) {
				tWafLogWebsec.setEventType(eventTypeMap.get(eventTypeString));
			}
			
			String eventTypeBase64 = stringToBase64(eventTypeString);
			tWafLogWebsec.setEventType(eventTypeBase64);
		}
		//http请求或响应信息
		if (tWafLogWebsec.getHttp()!=null) {
			byte[] httpBytes = tWafLogWebsec.getHttp();
			byte[] httpBytesBase64 = Base64.encodeBase64(httpBytes);
			tWafLogWebsec.setHttp(httpBytesBase64);
		}
		//告警补充描述
		if (tWafLogWebsec.getAlertinfo()!=null&&!tWafLogWebsec.getAlertinfo().equals("")) {
			String alertInfoString = tWafLogWebsec.getAlertinfo();
			String alertInfoBase64 = stringToBase64(alertInfoString);
			tWafLogWebsec.setAlertinfo(alertInfoBase64);
		}
		//攻击字符
		if (tWafLogWebsec.getCharacters()!=null&&!tWafLogWebsec.getCharacters().equals("")) {
			String charactersString = tWafLogWebsec.getCharacters();
			String charactersBase64 = stringToBase64(charactersString);
			tWafLogWebsec.setCharacters(charactersBase64);
		}
		//浏览器识别
		if (tWafLogWebsec.getWci()!=null&&!tWafLogWebsec.getWci().equals("")) {
			String wciBase64 = stringToBase64(tWafLogWebsec.getWci());
			tWafLogWebsec.setWci(wciBase64);
		}
		//waf会话识别
		if (tWafLogWebsec.getWsi()!=null&&!tWafLogWebsec.getWsi().equals("")) {
			String wsiBase64 = stringToBase64(tWafLogWebsec.getWsi());
			tWafLogWebsec.setWsi(wsiBase64);
		}
		return tWafLogWebsec;
	}
	
	private TWafLogDdos getTWafLogDdosBase64(TWafLogDdos tWafLogDdos){
		if(tWafLogDdos.getAction()!=null&&!tWafLogDdos.getAction().equals("")){
			String actionBase64 = stringToBase64(tWafLogDdos.getAction());
			tWafLogDdos.setAction(actionBase64);
		}
		return tWafLogDdos;
	}
	
	private TWafLogDeface getTWafLogDefaceBase64(TWafLogDeface tWafLogDeface) {
		if (tWafLogDeface.getReason()!=null&&!tWafLogDeface.getReason().equals("")) {
			String reasonBase64 = stringToBase64(tWafLogDeface.getReason());
			tWafLogDeface.setReason(reasonBase64);
		}
		return tWafLogDeface;
	}
	
	public String getPublicIpListInResource(int resourceId) {
		HashMap<Integer,NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONArray publicIpArray = new JSONArray();
		for(Entry<Integer, NsfocusWAFOperation> entry: map.entrySet()){
			JSONObject tempPublicIpObject = new JSONObject();
			tempPublicIpObject.put("deviceId", entry.getKey());
			tempPublicIpObject.put("publicIpList", entry.getValue().getPublicIpList());
			publicIpArray.add(tempPublicIpObject);
		}
		return publicIpArray.toString();
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
		String targetId = UUID.randomUUID().toString();
		
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			JSONObject responseJsonObject = JSONObject.fromObject(entry.getValue().createSite(jsonObject));
			//int siteId = responseJsonObject.getInt("id");
			//int groupId = responseJsonObject.getJSONObject("website").getJSONObject("group").getInt("id");
			
			TWafNsfocusTargetinfoKey key = new TWafNsfocusTargetinfoKey();
			key.setId(targetId);
			key.setResourceid(resourceId);
			key.setDeviceid(entry.getKey());
			TWafNsfocusTargetinfo record =new TWafNsfocusTargetinfo();
			record.setDeviceid(entry.getKey());
			record.setId(targetId);
			record.setResourceid(resourceId);
			//record.setSiteid(siteId);
			//record.setGroup(groupId);
			try {
				SqlSession sqlSession = getSqlSession();
				TWafNsfocusTargetinfoMapper mapper = sqlSession.getMapper(TWafNsfocusTargetinfoMapper.class);
				mapper.insertSelective(record);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONObject tempDeviceJsonObject = new JSONObject();
			tempDeviceJsonObject.put("deviceId", entry.getKey());
			tempDeviceJsonObject.put("InfoList", responseJsonObject);
			createSiteArray.add(tempDeviceJsonObject);
		}
		return createSiteArray.toString();
	}
	
	public String createSite(int resourceId,int deviceId,JSONObject jsonObject) {
		String responseString =  getDeviceById(resourceId, deviceId).createSite(jsonObject);
		JSONObject responseJsonObject = JSONObject.fromObject(responseString);
		


		return responseString;
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
	
	/**public String delSite(int resourceId, int deviceId, String targetId) {
		int
	}**/
	
	/**
	 * 在资源组内新建虚拟站点
	 * @param resourceId
	 * @param jsonObject
	 * @return
	 */
	public String createVirtSite(int resourceId, JSONObject jsonObject) {
		HashMap<Integer, NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONObject createVirtSiteJsonObject = new JSONObject();
		JSONArray createVirtSiteJsonArray = new JSONArray();
		String targetId = UUID.randomUUID().toString();
		int statusCount = 0;
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			//新建站点
			String siteJsonString = entry.getValue().createSite(jsonObject);
			JSONObject siteResponseJsonObject = JSONObject.fromObject(siteJsonString);
			if (siteResponseJsonObject.get("status")==null || !siteResponseJsonObject.getString("status").equals("success")){
				siteResponseJsonObject.put("deviceId", entry.getKey());
				createVirtSiteJsonArray.add(siteResponseJsonObject); 
				continue;
			}
			//组织入库信息
			String siteId = siteResponseJsonObject.getString("id").trim();
			String groupId = siteResponseJsonObject.getJSONObject("website").getJSONObject("group").getString("id").trim();			

			
			jsonObject.put("parent", groupId);
			JSONObject responseJsonObject = JSONObject.fromObject(entry.getValue().createVirtSite(jsonObject));
			//虚拟站点id
			if (responseJsonObject.get("status")==null|| !responseJsonObject.getString("status").equals("success")) {
				responseJsonObject.put("deviceId", entry.getKey());
				createVirtSiteJsonArray.add(responseJsonObject);
				continue;
			}
			String virtSiteId = responseJsonObject.getString("id");

			//入库
			TWafNsfocusTargetinfo record =new TWafNsfocusTargetinfo();
			record.setDeviceid(entry.getKey());
			record.setId(targetId);
			record.setResourceid(resourceId);
			record.setSiteid(siteId);
			record.setGroupid(groupId);
			record.setVirtsiteid(virtSiteId);
			SqlSession sqlSession = null;
			try {
				sqlSession = getSqlSession();
				TWafNsfocusTargetinfoMapper mapper = sqlSession.getMapper(TWafNsfocusTargetinfoMapper.class);
				mapper.insertSelective(record);
				sqlSession.commit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				sqlSession.rollback();
			} finally {
				sqlSession.close();
			}
			
			
			JSONObject tempDeviceJsonObject = JSONObject.fromObject(responseJsonObject);
			tempDeviceJsonObject.put("deviceId", entry.getKey());
			createVirtSiteJsonArray.add(tempDeviceJsonObject);
			statusCount+=1;
		}
		createVirtSiteJsonObject.put("targetKey", targetId);
		createVirtSiteJsonObject.put("virtualSiteList", createVirtSiteJsonArray);
		if (statusCount==map.size()) {
			createVirtSiteJsonObject.put("status", "success");
		}else {
			createVirtSiteJsonObject.put("status", "failed");
		}

		return createVirtSiteJsonObject.toString();
	}
	/**
	 * 新建虚拟站点
	 * @param resourceId
	 * @param deviceId
	 * @param jsonObject
	 * @return
	 */
	public String createVirtSite(int resourceId, int deviceId, JSONObject jsonObject) {
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
		TWafNsfocusTargetinfoKey targetinfoKey = new TWafNsfocusTargetinfoKey();
		targetinfoKey.setId(jsonObject.getString("targetKey"));
		targetinfoKey.setResourceid(resourceId);
		SqlSession sqlSession = null;
		try {
			for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
				targetinfoKey.setDeviceid(entry.getKey());
				TWafNsfocusTargetinfo tWafNsfocusTargetinfo = new TWafNsfocusTargetinfo();
				sqlSession = getSqlSession();
				TWafNsfocusTargetinfoMapper mapper = sqlSession.getMapper(TWafNsfocusTargetinfoMapper.class);
				tWafNsfocusTargetinfo = mapper.selectByPrimaryKey(targetinfoKey);
				JSONObject responseJsonObject = JSONObject.fromObject(entry.getValue().deleteVirtSite(JSONObject.fromObject("{\"vSiteId\":"+tWafNsfocusTargetinfo.getVirtsiteid()+"}")));
				
				System.out.println(entry.getValue().delSite(JSONObject.fromObject("{\"siteId\":"+tWafNsfocusTargetinfo.getSiteid()+"}")));
				JSONObject tempDeviceJsonObject = new JSONObject();
				tempDeviceJsonObject.put("deviceId", entry.getKey());
				tempDeviceJsonObject.put("InfoList", responseJsonObject);
				deleteVirtJsonArray.add(tempDeviceJsonObject);
			}
			sqlSession.commit();
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
		return deleteVirtJsonArray.toString();
	}
	
	public String deleteVSite(int resourceId, int deviceId, JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).deleteVirtSite(jsonObject);
	}
	
	public String getAllIpFromEth(int resourceId, int deviceId, JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).getAllIpFromEth(jsonObject);
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
		SqlSession sqlSession =null;
		try {
			sqlSession = getSqlSession();
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			example.or().andDstIpIn(dstIpList);
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<TWafLogWebsec> allList = mapper.selectByExample(example);
			for (TWafLogWebsec tWafLogWebsec : allList) {
				tWafLogWebsec = getTWafLogWebsecBase64(tWafLogWebsec);
			}
			sqlSession.commit();
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogWebsec", TWafLogWebsec.class);
			xStream.alias("wafLogWebsecList", List.class);
			String xmlString =  xStream.toXML(allList);
			return xmlString;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			return "{\"wafLogWebsecList\":\"error\"}";
		}finally {
			sqlSession.close();
		}
	}
	
	
	public String getWafLogWebSecById(String logId) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			TWafLogWebsec wafLogWebsec = mapper.selectByPrimaryKey(Long.parseLong(logId));
			
			wafLogWebsec = getTWafLogWebsecBase64(wafLogWebsec);
			sqlSession.commit();
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogWebsec", TWafLogWebsec.class);
			String jsonString =  xStream.toXML(wafLogWebsec);
			
			return jsonString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			return "{\"wafLogWebsec\":\"error\"}";
		} finally {
			sqlSession.close();
		}
		
	}
		
	public String getWafLogWebsecInTime(JSONObject jsonObject){
		SqlSession sqlSession = null;
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
			
			
			sqlSession = getSqlSession();
			
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			example.or().andStatTimeBetween(dateBefore,dateNow).andDstIpIn(dstIpList);
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<TWafLogWebsec> allList = mapper.selectByExample(example);


			for (TWafLogWebsec tWafLogWebsec : allList) {
				tWafLogWebsec = getTWafLogWebsecBase64(tWafLogWebsec);
			}
			sqlSession.commit();
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogWebsec", TWafLogWebsec.class);
			xStream.alias("wafLogWebsecList", List.class);
			String jsonString =  xStream.toXML(allList);
			return jsonString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			return "{\"wafLogWebsecList\":\"error\"}";
		} finally {
			sqlSession.close();
		}
		
	}
	
	
	
	public String getWafLogArp(List<String> dstIpList) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogArpExample example = new TWafLogArpExample();
			example.or().andDstIpIn(dstIpList);
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			List<TWafLogArp> allList = mapper.selectByExample(example);
			sqlSession.commit();
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogArp", TWafLogArp.class);
			String jsonString = xStream.toXML(allList);
			
			return jsonString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			return "{\"wafLogArp\":\"error\"}";
		} finally {
			sqlSession.close();
		}
	}
	
	public String getWafLogArpById(String logId) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			TWafLogArp wafLogArp = mapper.selectByPrimaryKey(Long.parseLong(logId));
			sqlSession.commit();
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogArp", TWafLogArp.class);
			String xmlString = xStream.toXML(wafLogArp);
			
			return xmlString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			return "{\"wafLogArp\":\"error\"}";
		} finally {
			sqlSession.close();
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
			sqlSession.commit();
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
			sqlSession.rollback();
			return "{\"wafLogArpList\":\"error\"}";
		}finally {
			sqlSession.close();
		}
	}
	
	public String getWafLogDeface(List<String> dstIpList) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogDefaceExample example = new TWafLogDefaceExample();
			example.or().andDstIpIn(dstIpList);
			TWafLogDefaceMapper mapper = sqlSession.getMapper(TWafLogDefaceMapper.class);
			List<TWafLogDeface> allList = mapper.selectByExample(example);
			for (TWafLogDeface tWafLogDeface : allList) {
				tWafLogDeface = getTWafLogDefaceBase64(tWafLogDeface);
			}
			sqlSession.commit();
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDeface", TWafLogDeface.class);
			xStream.alias("wafLogDefaceList", List.class);
			String xmlString = xStream.toXML(allList);
			
			return xmlString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			return "{\"wafLogDefaceList\":\"error\"}";
		} finally {
			sqlSession.close();
		}
		
	}
	
	public String getWafLogDefaceById(String logId) {
		SqlSession sqlSession =null;
		try {
			sqlSession = getSqlSession();
			TWafLogDefaceMapper mapper = sqlSession.getMapper(TWafLogDefaceMapper.class);
			TWafLogDeface wafLogDeface = mapper.selectByPrimaryKey(Long.parseLong(logId));
			
			wafLogDeface = getTWafLogDefaceBase64(wafLogDeface);
			sqlSession.commit();
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDeface", TWafLogDeface.class);
			String xmlString = xStream.toXML(wafLogDeface);
			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			return "{\"wafLogDeface\":\"error\"}";
		} finally {
			sqlSession.close();
		}
	}
	
	public String getWafLogDefaceInTime(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			
			int interval = jsonObject.getInt("interval");
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			TWafLogDefaceExample example = new TWafLogDefaceExample();
			example.or().andDstIpIn(dstIpList).andStatTimeBetween(dateBefore, dateNow);
			TWafLogDefaceMapper mapper = sqlSession.getMapper(TWafLogDefaceMapper.class);
			List<TWafLogDeface> allList = mapper.selectByExample(example);
			
			for (TWafLogDeface tWafLogDeface : allList) {
				tWafLogDeface = getTWafLogDefaceBase64(tWafLogDeface);
			}
			sqlSession.commit();
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDeface", TWafLogDeface.class);
			xStream.alias("wafLogDefaceList", List.class);
			String xmlString = xStream.toXML(allList);
			return xmlString;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
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
			for (TWafLogDdos tWafLogDdos : allList) {
				tWafLogDdos = getTWafLogDdosBase64(tWafLogDdos);
			}
			
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
			wafLogDdos = getTWafLogDdosBase64(wafLogDdos);
			sqlSession.commit();
			JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
			XStream xStream = new XStream(driver);
			xStream.autodetectAnnotations(true);
			xStream.alias("wafLogDDOS", TWafLogDdos.class);
			String xmlString = xStream.toXML(wafLogDdos);

			return xmlString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
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
	
			for (TWafLogDdos tWafLogDdos : allList) {
				tWafLogDdos = getTWafLogDdosBase64(tWafLogDdos);
			}
			sqlSession.commit();
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
			sqlSession.rollback();
			return "{\"wafLogDDOSList\":\"error\"}";
		} finally {
			sqlSession.close();
		}
	}
	
	public String getEventTypeCount() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			
			
			SAXReader reader = new SAXReader();
			Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			List<JSONObject> typeCountList = new ArrayList<JSONObject>();
			for (Element element : typeElements) {
				String eventTypeBase64 = stringToBase64(element.getTextTrim());
				TWafLogWebsecExample example = new TWafLogWebsecExample();
				example.or().andEventTypeEqualTo(element.attributeValue("name"));
				TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
				JSONObject eventTypeJsonObject = new JSONObject();
				eventTypeJsonObject.put("eventType", eventTypeBase64);
				eventTypeJsonObject.put("count", mapper.countByExample(example));
				typeCountList.add(eventTypeJsonObject);
			}
			sqlSession.commit();
			return JSONArray.fromObject(typeCountList).toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype count error!!!");
			return errorJsonObject.toString();
		}finally {
			sqlSession.close();
		}
	}
	
	public String getEventTypeCountInTime(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			//根据时间间隔获取时间段
			int interval = jsonObject.getInt("interval");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			SAXReader reader = new SAXReader();
			Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			List<JSONObject> typeCountList = new ArrayList<JSONObject>();
			for (Element element : typeElements) {
				String eventTypeBase64 = stringToBase64(element.getTextTrim());
				TWafLogWebsecExample example = new TWafLogWebsecExample();
				example.or().andEventTypeEqualTo(element.attributeValue("name")).andStatTimeBetween(dateBefore, dateNow);
				TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
				JSONObject eventTypeJsonObject = new JSONObject();
				eventTypeJsonObject.put("eventType", eventTypeBase64);
				eventTypeJsonObject.put("count", mapper.countByExample(example));
				typeCountList.add(eventTypeJsonObject);
			}
			sqlSession.commit();
			return JSONArray.fromObject(typeCountList).toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype count error!!!");
			return errorJsonObject.toString();
		}finally {
			sqlSession.close();
		}
	}
	
	public String getAlertLevelCount() {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			
			List<String> alertLevelList = Arrays.asList(ALERT_LEVEL_STRINGS);
			Map<String, Integer> mapAlertLevelCount = new HashMap<String, Integer>();
			for (String alertLevelString : alertLevelList) {
				TWafLogWebsecExample example = new TWafLogWebsecExample();
				example.or().andAlertlevelEqualTo(alertLevelString);
				TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
				System.out.println(alertLevelString+">>>>"+mapper.countByExample(example));
				mapAlertLevelCount.put(alertLevelString, mapper.countByExample(example));
			}
			sqlSession.commit();
			JSONObject alertLevelJsonObject = JSONObject.fromObject(mapAlertLevelCount);
			return alertLevelJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "AlertLevel count error!!!");
			return errorJsonObject.toString();
		}finally {
			sqlSession.close();
		}
	}

	public String getAlertLevelCountInTime(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			
			int interval = jsonObject.getInt("interval");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			List<String> alertLevelList = Arrays.asList(ALERT_LEVEL_STRINGS);
			System.out.println(alertLevelList.toString());
			Map<String, Integer> mapAlertLevelCount = new HashMap<String, Integer>();
			for (String alertLevelString : alertLevelList) {
				TWafLogWebsecExample example = new TWafLogWebsecExample();
				example.or().andAlertlevelEqualTo(alertLevelString).andStatTimeBetween(dateBefore, dateNow);
				TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
				mapAlertLevelCount.put(alertLevelString, mapper.countByExample(example));
			}
			sqlSession.commit();
			JSONObject alertLevelJsonObject = JSONObject.fromObject(mapAlertLevelCount);
			return alertLevelJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sqlSession.rollback();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "AlertLevel count error!!!");
			return errorJsonObject.toString();
		}finally {
			sqlSession.close();
		}
	}
	
	private SqlSession getSqlSession() throws IOException{
		Reader reader;
		reader = Resources.getResourceAsReader(DeviceAdapterConstant.RESOURCE_DATABASE_CONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	public static void main(String[] args) {

	}
	
	
	
}
