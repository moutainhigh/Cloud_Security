package com.cn.ctbri.southapi.adapter.waf;

import java.io.IOException;
import java.io.Reader;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
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

import com.cn.ctbri.southapi.adapter.batis.mapper.TWafLogArpMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TWafLogDdosMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TWafLogDefaceMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TWafLogWebsecCountMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TWafLogWebsecDstMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TWafLogWebsecMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TWafLogWebsecSrcMapper;
import com.cn.ctbri.southapi.adapter.batis.mapper.TWafNsfocusTargetinfoMapper;
import com.cn.ctbri.southapi.adapter.batis.model.*;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample.Criteria;
import com.cn.ctbri.southapi.adapter.common.CommonDatabaseController;
import com.cn.ctbri.southapi.adapter.common.WafDatabaseController;
import com.cn.ctbri.southapi.adapter.waf.config.*;
import com.cn.ctbri.southapi.adapter.waf.syslog.WAFSyslogManager;
import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;
import com.cn.ctbri.southapi.adapter.utils.CTSTimeConverter;
import com.cn.ctbri.southapi.adapter.utils.IPUtility;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;


public class NsfocusWAFAdapter {	
	//告警等级：高、中、低
	private static final String[] ALERT_LEVEL_STRINGS = {"LOW","MEDIUM","HIGH"};
	private static String LIMIT_NUM_STRING;
	public static HashMap<Integer, HashMap<Integer, NsfocusWAFOperation>> mapNsfocusWAFOperationGroup = new HashMap<Integer, HashMap<Integer,NsfocusWAFOperation>>();
	public NsfocusWAFOperation nsfocusWAFOperation = null;
	public static Blob blob;
	
	private static String getLimitNumString() throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(DeviceAdapterConstant.WAF_CONFIG);
		Element e = document.getRootElement().element("AlertConfig").element("statistic");
		LIMIT_NUM_STRING = e.getTextTrim();
		return LIMIT_NUM_STRING;	
	}
	
	public SqlSession getSqlSession() throws IOException{
		return WafDatabaseController.getSqlSession();
	}
	
	public void closeSqlSession(SqlSession sqlSession) {
		WafDatabaseController.closeSqlSession(sqlSession);
	}
	
	public SqlSession getDeviceSqlSession() throws IOException{
		return CommonDatabaseController.getSqlSession();
	}
	
	public void closeDeviceSqlSession(SqlSession sqlSession) {
		CommonDatabaseController.closeSqlSession(sqlSession);
	}
	/**
	 * 初始化waf适配器
	 * @param wafConfigManager waf配置
	 * @return
	 */
	public boolean initDeviceAdapter(WAFConfigManager wafConfigManager){
		//初始化
		//初始化Syslog
		WAFSyslogManager wsm = new WAFSyslogManager();
		wsm.initWAFSyslogManager();
		
		//加载waf设备配置、初始化waf设备、保存设备操作对象
		Iterator<?> iterator = wafConfigManager.mapWAFConfigDeviceManager.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer,WAFConfigDeviceGroup> entry = (Entry<Integer, WAFConfigDeviceGroup>)iterator.next();
			System.out.println("***WafConfig***\r\nkey="+entry.getKey()+"\r\nvalue="+entry.getValue().toString()+"************");
			int resourceId = entry.getKey();
			if("".equals(resourceId)) continue;
			WAFConfigDeviceGroup wafConfigDeviceGroup = entry.getValue();
			Iterator<?> wafConfDevIterator = wafConfigDeviceGroup.mapWAFConfigDevice.entrySet().iterator();
			HashMap<Integer, NsfocusWAFOperation> mapNsfocusWAFOperation = new HashMap<Integer, NsfocusWAFOperation>();
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
		return true;
	}
	
	//字符串Base64编码
	private String stringToBase64(String string){
		byte[] base64Bytes = Base64.encodeBase64Chunked(string.trim().getBytes());
		return new String(base64Bytes).trim();
	}

	//获取waf安全事件类型，英译中
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
	//Web安全事件防护日志Base64编码
	private TWafLogWebsec getTWafLogWebsecBase64(TWafLogWebsec tWafLogWebsec) throws DocumentException {
		//事件类型
	
		if (tWafLogWebsec.getEventType()!=null&&tWafLogWebsec.getEventType().length()>0) {
			//事件类型翻译 英文翻译为中文
			Map<String, String> eventTypeMap = getWafEventTypeMap();
			String eventTypeString = tWafLogWebsec.getEventType();
			if (eventTypeMap.get(eventTypeString)!=null) {
				tWafLogWebsec.setEventType(eventTypeMap.get(eventTypeString));
			}
			
			//String eventTypeBase64 = stringToBase64(tWafLogWebsec.getEventType());
			String eventTypeBase64 = tWafLogWebsec.getEventType();
			tWafLogWebsec.setEventType(eventTypeBase64);
		}
		//http请求或响应信息
		/*if (tWafLogWebsec.getHttp()!=null) {
			byte[] httpBytes = tWafLogWebsec.getHttp();
			byte[] httpBytesBase64 = Base64.encodeBase64(httpBytes);
			tWafLogWebsec.setHttp(httpBytesBase64);
		}*/
	
		//告警补充描述
		if (tWafLogWebsec.getAlertinfo()!=null&&tWafLogWebsec.getAlertinfo().length()>0) {
			String alertInfoString = tWafLogWebsec.getAlertinfo();
			String alertInfoBase64 = stringToBase64(alertInfoString);
			tWafLogWebsec.setAlertinfo(alertInfoBase64);
		}
		//攻击字符
		if (tWafLogWebsec.getCharacters()!=null&&tWafLogWebsec.getCharacters().length()>0) {
			String charactersString = tWafLogWebsec.getCharacters();
			String charactersBase64 = stringToBase64(charactersString);
			tWafLogWebsec.setCharacters(charactersBase64);
		}
		//浏览器识别
		if (tWafLogWebsec.getWci()!=null&&tWafLogWebsec.getWci().length()>0) {
			String wciBase64 = stringToBase64(tWafLogWebsec.getWci());
			tWafLogWebsec.setWci(wciBase64);
		}
		//waf会话识别
		if (tWafLogWebsec.getWsi()!=null&&tWafLogWebsec.getWsi().length()>0) {
			String wsiBase64 = stringToBase64(tWafLogWebsec.getWsi());
			tWafLogWebsec.setWsi(wsiBase64);
		}
		return tWafLogWebsec;
	}
	
	//DDOS防护日志BASE64编码
	private TWafLogDdos getTWafLogDdosBase64(TWafLogDdos tWafLogDdos){
		if(tWafLogDdos.getAction()!=null&&tWafLogDdos.getAction().length()>0){
			String actionBase64 = stringToBase64(tWafLogDdos.getAction());
			tWafLogDdos.setAction(actionBase64);
		}
		return tWafLogDdos;
	}
	//防篡改防护日志BASE64编码
	private TWafLogDeface getTWafLogDefaceBase64(TWafLogDeface tWafLogDeface) {
		if (tWafLogDeface.getReason()!=null&&tWafLogDeface.getReason().length()>0) {
			String reasonBase64 = stringToBase64(tWafLogDeface.getReason());
			tWafLogDeface.setReason(reasonBase64);
		}
		return tWafLogDeface;
	}
	//获取sqlSession

	//sqlsession判空

	//配置xstream
	private XStream getXStream() {
		//转换格式为json
		JsonHierarchicalStreamDriver driver = new JsonHierarchicalStreamDriver();
		XStream xStream = new XStream(driver);
		//自动解析JavaBean中的Annotation
		xStream.autodetectAnnotations(true);
		//配置日期转换器的时间格式为北京时间(东八区时间)
		xStream.registerConverter(new CTSTimeConverter());
		return xStream;
	}
	/**
	 * 获取resource内所有设备公网虚拟IP
	 * @param resourceId
	 * @return publicIp 
	 */
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
	/**
	 * 获取resource内指定编号的设备站点信息
	 * @param resourceId
	 * @param deviceId
	 * @return siteInfo
	 */
	public String getSites(int resourceId,int deviceId) {
		return getDeviceById(resourceId, deviceId).getSites();
	}
	
	/**
	 * 获取resource内所有设备站点信息
	 * @param resourceId
	 * @return siteInfo
	 */
	public String getSitesInResource(int resourceId) {
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
	
	/**
	 * 获取resource内指定设备的站点信息
	 * @param resourceId
	 * @param deviceId
	 * @param jsonObject
	 * @return
	 */
	public String getSite(int resourceId, int deviceId,JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).getSite(jsonObject);
	}
	
	public String createSite(int resourceId, JSONObject jsonObject) {
		//根据resourceId获取waf设备组列表中的一组设备
		HashMap<Integer, NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		//生成标签id
		String targetId = UUID.randomUUID().toString();
		//新建站点array
		JSONArray createSiteArray = new JSONArray();
		//循环获取一组设备中的waf设备
		for (Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()) {
			//建立waf设备操作类
			NsfocusWAFOperation nsfocusWAFOperation = entry.getValue();
			//调用设备操作类中的新建站点方法，获取新建站点响应结果
			JSONObject responseJsonObject = JSONObject.fromObject(nsfocusWAFOperation.createSite(jsonObject));
			//新建waf标签复合主键类，并初始化相关参数
			TWafNsfocusTargetinfoKey key = new TWafNsfocusTargetinfoKey();
			key.setId(targetId);
			key.setResourceid(resourceId);
			key.setDeviceid(entry.getKey());
			//新建waf任务标签信息并入库
			TWafNsfocusTargetinfo record =new TWafNsfocusTargetinfo();
			record.setDeviceid(entry.getKey());
			record.setId(targetId);
			
			record.setResourceid(resourceId);
			SqlSession sqlSession = null;
			try {
				sqlSession = getDeviceSqlSession();
				TWafNsfocusTargetinfoMapper mapper = sqlSession.getMapper(TWafNsfocusTargetinfoMapper.class);
				mapper.insertSelective(record);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeDeviceSqlSession(sqlSession);
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
	
	public String deleteSiteInResource(int resourceId, JSONObject jsonObject) {
		HashMap<Integer, NsfocusWAFOperation> map = mapNsfocusWAFOperationGroup.get(resourceId);
		JSONArray deleteSiteJsonArray = new JSONArray();
		for(Entry<Integer, NsfocusWAFOperation> entry : map.entrySet()){
			JSONObject responseJsonObject = new JSONObject();
			entry.getValue().deleteSite(jsonObject);
			
		}
		return null;
	}
	
	/**
	 * 在资源组内新建虚拟站点
	 * @param resourceId
	 * @param jsonObject
	 * @return
	 */
	public String createVirtSite(int resourceId, JSONObject jsonObject) {
		//获取指定resource内需要操作的一组waf设备
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

			//创建虚拟站点
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
				sqlSession = getDeviceSqlSession();
				TWafNsfocusTargetinfoMapper mapper = sqlSession.getMapper(TWafNsfocusTargetinfoMapper.class);
				mapper.insertSelective(record);
				sqlSession.commit();
			} catch (Exception e) {
				// 
				e.printStackTrace();
				sqlSession.rollback();
			} finally {
				closeDeviceSqlSession(sqlSession);
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
	/**
	 * 删除resource中全部虚拟站点
	 * @param resourceId
	 * @param jsonObject targetKey 
	 * @return
	 */
	public String deleteVirtSite(int resourceId, JSONObject jsonObject) {
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
				sqlSession = getDeviceSqlSession();
				TWafNsfocusTargetinfoMapper mapper = sqlSession.getMapper(TWafNsfocusTargetinfoMapper.class);
				JSONObject responseJsonObject = new JSONObject();
				if (mapper.selectByPrimaryKey(targetinfoKey) != null) {
					tWafNsfocusTargetinfo = mapper.selectByPrimaryKey(targetinfoKey);
					JSONObject tempResJsonObject = JSONObject.fromObject(entry.getValue().deleteVirtSite(JSONObject.fromObject("{\"vSiteId\":"+tWafNsfocusTargetinfo.getVirtsiteid()+"}")));
					responseJsonObject = JSONObject.fromObject(tempResJsonObject.get(tWafNsfocusTargetinfo.getVirtsiteid()));
					mapper.deleteByPrimaryKey(targetinfoKey);
				}else {
					responseJsonObject.put("result", "not found");
				}
				JSONObject tempDeviceJsonObject = new JSONObject();
				tempDeviceJsonObject.put("deviceId", entry.getKey());
				tempDeviceJsonObject.put("InfoList", responseJsonObject);
				deleteVirtJsonArray.add(tempDeviceJsonObject);
			}
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		} finally {
			closeDeviceSqlSession(sqlSession);
		}
		return deleteVirtJsonArray.toString();
	}
	
	public String deleteVSite(int resourceId, int deviceId, JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).deleteVirtSite(jsonObject);
	}
	
	public String getAllIpFromEth(int resourceId, int deviceId, JSONObject jsonObject) {
		return getDeviceById(resourceId, deviceId).getAllIpFromEth(jsonObject);
	}
	private NsfocusWAFOperation getDeviceById(int resourceId, int deviceId) {
		NsfocusWAFOperation nsfocusWAFOperation = mapNsfocusWAFOperationGroup.get(resourceId).get(deviceId);
		return nsfocusWAFOperation;
	}
		
	public String getWafLogWebsecByIp(List<String> dstIpList) {
		SqlSession sqlSession = null;
		
		try {
			sqlSession = getSqlSession();
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			example.or().andDstIpIn(dstIpList);
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<TWafLogWebsec> allList = mapper.selectByExampleWithBLOBs(example);
			for (TWafLogWebsec tWafLogWebsec : allList) {
				tWafLogWebsec = getTWafLogWebsecBase64(tWafLogWebsec);
			}
			XStream xStream = getXStream();
			xStream.alias("wafLogWebsec", TWafLogWebsec.class);
			xStream.alias("wafLogWebsecList", List.class);
			return xStream.toXML(allList);
		}catch (IOException e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"database connection error\"}";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"log format error\"}";
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	/**
	 * 根据域名或者ip获得websec日志
	 * @param jsonObject
	 * @return
	 */
	public String getWafLogWebsec(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			
			
			sqlSession = getSqlSession();
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			Criteria criteria = new TWafLogWebsecExample().createCriteria();
			if (jsonObject.getJSONArray("dstIp")!=null&&jsonObject.getJSONArray("dstIp").get(0)!=null&&jsonObject.getJSONArray("dstIp").getString(0).length()>0) {
				List<String> dstIp = JSONArray.toList(jsonObject.getJSONArray("dstIp"));
				criteria.andDstIpIn(dstIp);
			}
			if (jsonObject.containsKey("domain")&&jsonObject.getString("domain").length()>0) {
				criteria.andDomainEqualTo(jsonObject.getString("domain"));
			}
			example.or(criteria);
			if (jsonObject.containsKey("limit")&&jsonObject.get("limit")!=null&&jsonObject.getString("limit").length()>0) {
				example.setRows(jsonObject.getString("limit"));
			}else {
				example.setRows(getLimitNumString());
			}
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<TWafLogWebsec> allList = mapper.selectByExampleWithBLOBs(example);
			for (TWafLogWebsec tWafLogWebsec : allList) {
				tWafLogWebsec = getTWafLogWebsecBase64(tWafLogWebsec);
			}
			XStream xStream = getXStream();
			xStream.alias("wafLogWebsec", TWafLogWebsec.class);
			xStream.alias("wafLogWebsecList", List.class);
			return xStream.toXML(allList);
		}catch (IOException e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"database connection error\"}";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"log format error\"}";
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	
	public String getWafLogWebSecCount(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			if (jsonObject.containsKey("domain")&&jsonObject.getJSONArray("domain").isArray()) {
				example.or().andDomainIn(JSONArray.toList(jsonObject.getJSONArray("domain")));
			}
			int count = mapper.countByExample(example);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("code", "200");
			returnJsonObject.put("count", count);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "failed");
			returnJsonObject.put("code", "500");
			return returnJsonObject.toString();
		}
	}
	
	public String getWafLogWebSecById(String logId) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			TWafLogWebsec wafLogWebsec = mapper.selectByPrimaryKey(Long.parseLong(logId));
			sqlSession.clearCache();
			wafLogWebsec = getTWafLogWebsecBase64(wafLogWebsec);
			XStream xStream = getXStream();
			xStream.alias("wafLogWebsec", TWafLogWebsec.class);
			String jsonString =  xStream.toXML(wafLogWebsec);
			
			return jsonString;
		} catch (Exception e) {
			
			e.printStackTrace();
			return "{\"wafLogWebsec\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
		
	public String getWafLogWebsecInTime(JSONObject jsonObject){
		if (jsonObject.get("interval")==null||jsonObject.getInt("interval")<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype parameter error!!!");
		}
		SqlSession sqlSession = null;
		try {
			//获取时间间隔
			int interval = jsonObject.getInt("interval");
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			
			
			//获取当前时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			//根据时间间隔和当前时间计算开始时间
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			
			sqlSession = getSqlSession();
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			Criteria criteria = new TWafLogWebsecExample().createCriteria();
			//如果有目标ip，组装查询条件时加入目标ip
			//如果没有，只按开始结束时间组装查询条件
			if (!dstIpList.isEmpty()) {
				criteria.andStatTimeBetween(dateBefore,dateNow).andDstIpIn(dstIpList);
			}else {
				criteria.andStatTimeBetween(dateBefore, dateNow);
			}
			example.or(criteria);
			//按告警时间降序排列
			example.setOrderByClause("stat_time desc");
			//如果有条数限制，取规定条数数据
			//如果没有，默认取前一千条
			if (jsonObject.containsKey("limit")&&jsonObject.get("limit")!=null&&jsonObject.getString("limit").length()>0) {
				example.setRows(jsonObject.getString("limit"));
			}
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<TWafLogWebsec> allList = mapper.selectByExampleWithBLOBs(example);

			for (TWafLogWebsec tWafLogWebsec : allList) {
				tWafLogWebsec = getTWafLogWebsecBase64(tWafLogWebsec);

				
			}

			XStream xStream = getXStream();
			xStream.alias("wafLogWebsecList", List.class);
			String jsonString =  xStream.toXML(allList);
			return jsonString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"sql session error\"}";
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"eventtype parse error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	//根据域名查询websec日志
	public String getWafLogWebsecByDomainCurrent(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		
		
		try {
			Criteria criteria = new TWafLogWebsecExample().createCriteria();
			if (jsonObject.containsKey("domain")&&jsonObject.getJSONArray("domain").isArray()) {
				criteria.andDomainIn(JSONArray.toList(jsonObject.getJSONArray("domain")));
			}
			
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			example.or(criteria);
			example.setOrderByClause("stat_time desc");
			if (jsonObject.containsKey("limit")&&jsonObject.get("limit")!=null&&jsonObject.getString("limit").length()>0) {
				example.setRows(jsonObject.getString("limit"));
			}else {
				example.setRows(getLimitNumString());
			}
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<TWafLogWebsec> allList = mapper.selectByExampleWithBLOBs(example);
			
			for (TWafLogWebsec tWafLogWebsec : allList) {
				tWafLogWebsec = getTWafLogWebsecBase64(tWafLogWebsec);
			}
			sqlSession.clearCache();
			XStream xStream = getXStream();
			xStream.alias("wafLogWebsecList", List.class);
			String jsonString =  xStream.toXML(allList);
			return jsonString;
		} catch (IOException e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"sql session error\"}";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"eventtype parse error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	
	public String getAllWafLogWebsecInTime(JSONObject jsonObject) {
		if (jsonObject.get("timeUnit")==null||jsonObject.getString("timeUnit").length()<=0
		||jsonObject.get("interval")==null||jsonObject.getInt("interval")<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype parameter error!!!");
		}
		SqlSession sqlSession =null;
		try {
			//根据时间间隔获取时间段
			int interval = jsonObject.getInt("interval");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			//当前时间
			Date dateNow = calendar.getTime();
			//获取时间间隔单位
			if (jsonObject.getString("timeUnit").equalsIgnoreCase("millisecond")) {
				calendar.add(Calendar.MILLISECOND, -interval);
			}else if (jsonObject.getString("timeUnit").equalsIgnoreCase("hour")) {
				calendar.add(Calendar.HOUR, -interval);
			}else if(jsonObject.getString("timeUnit").equalsIgnoreCase("minute")){
				calendar.add(Calendar.MINUTE, -interval);
			}else if(jsonObject.getString("timeUnit").equalsIgnoreCase("date")){
				calendar.add(Calendar.DATE, -interval);
			}else if (jsonObject.getString("timeUnit").equalsIgnoreCase("second")) {
				calendar.add(Calendar.SECOND, -interval);
			}
			//开始时间
			Date dateBefore = calendar.getTime();
		
			//组装查询条件并进行查询
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			example.or().andStatTimeBetween(dateBefore,dateNow);
			//查询并返回结果
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<TWafLogWebsec> allList = mapper.selectByExample(example);
			
			//base64编码
			for (TWafLogWebsec tWafLogWebsec : allList) {
				tWafLogWebsec = getTWafLogWebsecBase64(tWafLogWebsec);
			}
			
			//Java对象转为json数据
			XStream xStream = getXStream();
			xStream.alias("wafLogWebsecList", List.class);
			String jsonString =  xStream.toXML(allList);
			return jsonString;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getAllWafLogWebsecThanCurrentId(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			//根据
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			Criteria criteria = example.createCriteria();
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			int maxNum = mapper.selectMaxByExample(example);
			if (jsonObject.get("currentId")!=null&&!jsonObject.getString("currentId").isEmpty()&&jsonObject.getLong("currentId")>0 ){
				criteria.andLogIdGreaterThan(Long.parseLong(jsonObject.getString("currentId")));
			}
			if(jsonObject.get("domain")!=null&&jsonObject.getJSONArray("domain").isArray()){
				List domainList;
				if (jsonObject.getJSONArray("domain").isEmpty()) {
					domainList=new ArrayList();
					domainList.add("");
				} else {
					domainList = JSONArray.toList(jsonObject.getJSONArray("domain"));
				}
				criteria.andDomainIn(domainList);
			}
			//组装查询条件并进行查询
			
			
			//查询并返回结果

			
			List<TWafLogWebsec> allList = mapper.selectByExampleWithBLOBs(example);
			//base64编码
			for (TWafLogWebsec tWafLogWebsec : allList) {
				tWafLogWebsec = getTWafLogWebsecBase64(tWafLogWebsec);
			}
			sqlSession.clearCache();
			//Java对象转为json数据
			XStream xStream = getXStream();
			xStream.alias("wafLogWebsecList", List.class);
			String jsonString =  xStream.toXML(allList);
			JSONObject returnJsonObject = JSONObject.fromObject(jsonString);
			returnJsonObject.put("currentId", maxNum);
			return returnJsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"sql session error\"}";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"error\"}";
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	
	public String getWafLogWebsecCurrent(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			//组装查询条件并进行查询
			TWafLogWebsecExample example = new TWafLogWebsecExample();
			
			//查询并返回结果
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			Criteria criteria =example.createCriteria();
			int maxNum = mapper.selectMaxByExample(example);
			int startNum = 0;
			
			if (null!=jsonObject.get("topNum")&&jsonObject.getInt("topNum")>0) {
				startNum = maxNum-jsonObject.getInt("topNum")+1;
			}
			if(jsonObject.get("domain")!=null&&!jsonObject.getJSONArray("domain").isEmpty()){
				System.out.println(">>>>>>>>domainList");
				List domainList = JSONArray.toList(jsonObject.getJSONArray("domain"));
				criteria.andDomainIn(domainList);
			}
			criteria.andLogIdBetween(Long.valueOf(startNum), Long.valueOf(maxNum));
			List<TWafLogWebsec> allList = mapper.selectByExample(example);

			//base64编码
			for (TWafLogWebsec tWafLogWebsec : allList) {
				tWafLogWebsec = getTWafLogWebsecBase64(tWafLogWebsec);
			}
			
			//Java对象转为json数据
			XStream xStream = getXStream();
			xStream.alias("wafLogWebsecList", List.class);
			String jsonString =  xStream.toXML(allList);
			JSONObject returnJsonObject = JSONObject.fromObject(jsonString);
			returnJsonObject.put("currentId", maxNum);
			return returnJsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"wafLogWebsecList\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}				
	}
	

	//public String getAllWafLogWebsecInTime() {}
	
	
	public String getWafLogArp(List<String> dstIpList) {
		SqlSession sqlSession = null;
		try {
			
			TWafLogArpExample example = new TWafLogArpExample();
			example.or().andDstIpIn(dstIpList);
			
			sqlSession = getSqlSession();
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			List<TWafLogArp> allList = mapper.selectByExample(example);
			XStream xStream = getXStream();
			xStream.alias("wafLogArp", TWafLogArp.class);
			String jsonString = xStream.toXML(allList);
			
			return jsonString;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"wafLogArp\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getWafLogArpById(String logId) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			TWafLogArp wafLogArp = mapper.selectByPrimaryKey(Long.parseLong(logId));
			XStream xStream = getXStream();
			xStream.alias("wafLogArp", TWafLogArp.class);
			String xmlString = xStream.toXML(wafLogArp);
			
			return xmlString;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"wafLogArp\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getWafLogArpInTime(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {
			
			int interval = jsonObject.getInt("interval");
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			
			//根据时间间隔获取时间段
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			sqlSession = getSqlSession();
			TWafLogArpExample example = new TWafLogArpExample();
			example.or().andStatTimeBetween(dateBefore, dateNow).andDstIpIn(dstIpList);
			TWafLogArpMapper mapper =sqlSession.getMapper(TWafLogArpMapper.class);
			List<TWafLogArp> allList = mapper.selectByExample(example);
			
			
			XStream xStream = getXStream();
			xStream.alias("wafLogArp", TWafLogArp.class);
			xStream.alias("wafLogArpList", List.class);
			String xmlString = xStream.toXML(allList);
			return xmlString;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"wafLogArpList\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
	}

	public String getWafLogDeface(List<String> dstIpList) {
		SqlSession sqlSession = null;
		try {
			
			TWafLogDefaceExample example = new TWafLogDefaceExample();
			example.or().andDstIpIn(dstIpList);
			
			sqlSession = getSqlSession();
			TWafLogDefaceMapper mapper = sqlSession.getMapper(TWafLogDefaceMapper.class);
			List<TWafLogDeface> allList = mapper.selectByExample(example);
			for (TWafLogDeface tWafLogDeface : allList) {
				tWafLogDeface = getTWafLogDefaceBase64(tWafLogDeface);
			}
			
			XStream xStream = getXStream();
			xStream.alias("wafLogDeface", TWafLogDeface.class);
			xStream.alias("wafLogDefaceList", List.class);
			String xmlString = xStream.toXML(allList);
			
			return xmlString;
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			return "{\"wafLogDefaceList\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
		
	}
	
	public String getWafLogDefaceById(String logId) {
		SqlSession sqlSession =null;
		try {
			sqlSession = getSqlSession();
			TWafLogDefaceMapper mapper = sqlSession.getMapper(TWafLogDefaceMapper.class);
			TWafLogDeface wafLogDeface = mapper.selectByPrimaryKey(Long.parseLong(logId));
			
			wafLogDeface = getTWafLogDefaceBase64(wafLogDeface);
			XStream xStream = getXStream();
			xStream.alias("wafLogDeface", TWafLogDeface.class);
			String xmlString = xStream.toXML(wafLogDeface);
			return xmlString;
		} catch (IOException e) {
			e.printStackTrace();
			return "{\"wafLogDeface\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getWafLogDefaceInTime(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			
			
			int interval = jsonObject.getInt("interval");
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			//根据时间间隔获取时间段
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			//组织查询内容
			TWafLogDefaceExample example = new TWafLogDefaceExample();
			example.or().andDstIpIn(dstIpList).andStatTimeBetween(dateBefore, dateNow);
			
			sqlSession = getSqlSession();
			TWafLogDefaceMapper mapper = sqlSession.getMapper(TWafLogDefaceMapper.class);
			List<TWafLogDeface> allList = mapper.selectByExample(example);
			
			for (TWafLogDeface tWafLogDeface : allList) {
				tWafLogDeface = getTWafLogDefaceBase64(tWafLogDeface);
			}
			XStream xStream = getXStream();
			xStream.alias("wafLogDeface", TWafLogDeface.class);
			xStream.alias("wafLogDefaceList", List.class);
			String xmlString = xStream.toXML(allList);
			return xmlString;

		} catch (Exception e) {
			e.printStackTrace();
			return "{\"wafLogDefaceList\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getWafLogDDOS(List<String> dstIpList) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogDdosMapper mapper = sqlSession.getMapper(TWafLogDdosMapper.class);
			
			TWafLogDdosExample example = new TWafLogDdosExample();
			example.or().andDstIpIn(dstIpList);
			List<TWafLogDdos> allList = mapper.selectByExample(example);
			for (TWafLogDdos tWafLogDdos : allList) {
				tWafLogDdos = getTWafLogDdosBase64(tWafLogDdos);
			}
			XStream xStream = getXStream();
			xStream.alias("wafLogDDOS", TWafLogDdos.class);
			xStream.alias("wafLogDDOSList", List.class);
			String xmlString = xStream.toXML(allList);
			return xmlString;
		} catch (IOException e) {
			e.printStackTrace();
			return "{\"wafLogDDOSList\":\"error\"}";
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getWafLogDDOSById(String logId) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			
			TWafLogDdosMapper mapper = sqlSession.getMapper(TWafLogDdosMapper.class);
			TWafLogDdos wafLogDdos = mapper.selectByPrimaryKey(Long.parseLong(logId));
			wafLogDdos = getTWafLogDdosBase64(wafLogDdos);
			XStream xStream = getXStream();
			xStream.alias("wafLogDDOS", TWafLogDdos.class);
			String xmlString = xStream.toXML(wafLogDdos);

			return xmlString;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"wafLogDDOS\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getWaflogDDOSInTime(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			//IP List
			int interval = jsonObject.getInt("interval");
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			//根据时间间隔获取时间段
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.HOUR, -interval);
			Date dateBefore = calendar.getTime();
			
			TWafLogDdosExample example = new TWafLogDdosExample();
			TWafLogDdosMapper mapper = sqlSession.getMapper(TWafLogDdosMapper.class);
			
			example.or().andDstIpIn(dstIpList).andStatTimeBetween(dateBefore, dateNow);
			List<TWafLogDdos> allList = mapper.selectByExample(example);
	
			for (TWafLogDdos tWafLogDdos : allList) {
				tWafLogDdos = getTWafLogDdosBase64(tWafLogDdos);
			}
			XStream xStream = getXStream();
			xStream.alias("wafLogDDOS", TWafLogDdos.class);
			xStream.alias("wafLogDDOSList", List.class);
			String xmlString = xStream.toXML(allList);
			return xmlString;
		} catch (IOException e) {
			e.printStackTrace();
			return "{\"wafLogDDOSList\":\"error\"}";
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getEventTypeCount() {
		SqlSession sqlSession = null;
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			
			
			
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			
			List<JSONObject> typeCountList = new ArrayList<JSONObject>();
			for (Element element : typeElements) {
				TWafLogWebsecExample example = new TWafLogWebsecExample();
				example.or().andEventTypeEqualTo(element.attributeValue("name"));
			
				JSONObject eventTypeJsonObject = new JSONObject();
				eventTypeJsonObject.put("eventType", element.getTextTrim());
				eventTypeJsonObject.put("count", mapper.countByExample(example));
				
				typeCountList.add(eventTypeJsonObject);
			}
			return JSONArray.fromObject(typeCountList).toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype count error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	public String getEventTypeCountInTimeCurrent(JSONObject jsonObject) {
		if (jsonObject.get("topNum")==null) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype parameter error!!!");
		}
		SqlSession sqlSession = null;
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			List<JSONObject> typeCountList = new ArrayList<JSONObject>();
			List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
			
			sqlSession = getSqlSession();
			
			//根据时间间隔获取时间段
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			int maxNum = mapper.selectMaxByExample(new TWafLogWebsecExample());
			Criteria tWafLogWebseCriteria = new TWafLogWebsecExample().or();
			int startNum = maxNum;
			if (null!=jsonObject.get("topNum")&&jsonObject.getInt("topNum")>0) {
				startNum = maxNum-jsonObject.getInt("topNum");
				tWafLogWebseCriteria.andLogIdLessThanOrEqualTo(Long.valueOf(startNum));
			}
			
			
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			
			if (jsonObject.get("dstIp")!=null&&!dstIpList.isEmpty()) {
				tWafLogWebseCriteria.andDstIpIn(dstIpList);
			}
			if(jsonObject.get("domain")!=null&&jsonObject.getJSONArray("domain").isArray()){
				List domainList;
				if (jsonObject.getJSONArray("domain").isEmpty()) {
					domainList=new ArrayList();
					domainList.add("");
				} else {
					domainList = JSONArray.toList(jsonObject.getJSONArray("domain"));
				}
				tWafLogWebseCriteria.andDomainIn(domainList);
			}
			int interval = 0;
			if (jsonObject.get("timeUnit")!=null&&!jsonObject.getString("timeUnit").equalsIgnoreCase("forever")) {
				interval = jsonObject.getInt("interval");
				if (jsonObject.getString("timeUnit").equalsIgnoreCase("hour")) {
					calendar.add(Calendar.HOUR, -interval);
					tWafLogWebseCriteria.andStatTimeBetween(calendar.getTime(), dateNow);
				}else if(jsonObject.getString("timeUnit").equalsIgnoreCase("minute")){
					calendar.add(Calendar.MINUTE, -interval);
					tWafLogWebseCriteria.andStatTimeBetween(calendar.getTime(), dateNow);
				}else if(jsonObject.getString("timeUnit").equalsIgnoreCase("date")){
					calendar.add(Calendar.DATE, -interval);
					tWafLogWebseCriteria.andStatTimeBetween(calendar.getTime(), dateNow);
				}else {
					JSONObject errorJsonObject = new JSONObject();
					errorJsonObject.put("status", "failed");
					errorJsonObject.put("message", "Eventtype's timeunit error!!!");
					return errorJsonObject.toString();
				}
			}
	
			//查询并返回结果			
			for (Element element : typeElements) {
				String eventTypeBase64 = stringToBase64(element.getTextTrim());
				tWafLogWebseCriteria.andEventTypeEqualTo(element.attributeValue("name"));
				TWafLogWebsecExample tWafLogWebsecExample = new TWafLogWebsecExample();
				tWafLogWebsecExample.or(tWafLogWebseCriteria);
				JSONObject eventTypeJsonObject = new JSONObject();
				eventTypeJsonObject.put("eventType", element.getTextTrim());
				eventTypeJsonObject.put("count", mapper.countByExample(tWafLogWebsecExample));
				typeCountList.add(eventTypeJsonObject);
				tWafLogWebseCriteria.criteria.remove(tWafLogWebseCriteria.criteria.size()-1);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("eventTypeCountList", JSONArray.fromObject(typeCountList));
			returnJsonObject.put("currentId", startNum);
			return returnJsonObject.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype counts' IO error!!!");
			return errorJsonObject.toString();
		} catch (DocumentException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype counts' document read error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	

	
	public String getEventTypeCountInterval(JSONObject jsonObject) {
		if (jsonObject.get("timeUnit")==null||jsonObject.getString("timeUnit").length()<=0
		||jsonObject.get("interval")==null||jsonObject.getInt("interval")<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype parameter error!!!");
		}
		SqlSession sqlSession = null;
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			List<JSONObject> typeCountList = new ArrayList<JSONObject>();
			
			
			sqlSession = getSqlSession();
			
			//根据时间间隔获取时间段
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			int maxNum = mapper.selectMaxByExample(new TWafLogWebsecExample());
			Criteria tWafLogWebseCriteria = new TWafLogWebsecExample().or();
			int startNum = maxNum;
			if (null!=jsonObject.get("topNum")&&jsonObject.getInt("topNum")>0) {
				startNum = maxNum-jsonObject.getInt("topNum");
				tWafLogWebseCriteria.andLogIdLessThanOrEqualTo(Long.valueOf(startNum));
			}
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			
			if (jsonObject.containsKey("dstIp")&&!jsonObject.getJSONArray("dstIp").isEmpty()) {
				List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
				tWafLogWebseCriteria.andDstIpIn(dstIpList);
			}
			if (jsonObject.containsKey("domain")&&!jsonObject.getJSONArray("domain").isEmpty()) {
				List<String> domainList = JSONArray.toList(jsonObject.getJSONArray("domain"));
				tWafLogWebseCriteria.andDomainIn(domainList);
			}
			if(jsonObject.containsKey("domain")&&jsonObject.getJSONArray("domain").isArray()){
				List domainList;
				if (jsonObject.getJSONArray("domain").isEmpty()) {
					domainList=new ArrayList();
					domainList.add("");
				} else {
					domainList = JSONArray.toList(jsonObject.getJSONArray("domain"));
				}
				tWafLogWebseCriteria.andDomainIn(domainList);
			}
			int interval = 0;
			if (!jsonObject.getString("timeUnit").equalsIgnoreCase("forever")) {
				interval = jsonObject.getInt("interval");
				if (jsonObject.getString("timeUnit").equalsIgnoreCase("hour")) {
					calendar.add(Calendar.HOUR, -interval);
					tWafLogWebseCriteria.andStatTimeBetween(calendar.getTime(), dateNow);
				}else if(jsonObject.getString("timeUnit").equalsIgnoreCase("minute")){
					calendar.add(Calendar.MINUTE, -interval);
					tWafLogWebseCriteria.andStatTimeBetween(calendar.getTime(), dateNow);
				}else if(jsonObject.getString("timeUnit").equalsIgnoreCase("date")){
					calendar.add(Calendar.DATE, -interval);
					tWafLogWebseCriteria.andStatTimeBetween(calendar.getTime(), dateNow);
				}else {
					JSONObject errorJsonObject = new JSONObject();
					errorJsonObject.put("status", "failed");
					errorJsonObject.put("message", "Eventtype's timeunit error!!!");
					return errorJsonObject.toString();
				}
			}
	
			//查询并返回结果			
			for (Element element : typeElements) {
				
				String eventTypeBase64 = stringToBase64(element.getTextTrim());
				tWafLogWebseCriteria.andEventTypeEqualTo(element.attributeValue("name"));
				TWafLogWebsecExample tWafLogWebsecExample = new TWafLogWebsecExample();
				tWafLogWebsecExample.or(tWafLogWebseCriteria);
				JSONObject eventTypeJsonObject = new JSONObject();
				eventTypeJsonObject.put("eventType", element.getTextTrim());
				eventTypeJsonObject.put("count", mapper.countByExample(tWafLogWebsecExample));
				typeCountList.add(eventTypeJsonObject);
				tWafLogWebseCriteria.criteria.remove(tWafLogWebseCriteria.criteria.size()-1);
			}
			return JSONArray.fromObject(typeCountList).toString();
			
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype counts' IO error!!!");
			return errorJsonObject.toString();
		} catch (DocumentException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype counts' document read error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	/**
	 * 最近攻击事件类型统计，默认1000
	 * @param jsonObject
	 * @return
	 */
	public String getEventTypeCountLimitByDomain(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			if (!jsonObject.containsKey("domain")||jsonObject.getJSONArray("domain").isEmpty()) {
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "domain is null");
				return errorJsonObject.toString();
			}
			List<String> domainList = JSONArray.toList(jsonObject.getJSONArray("domain"));
			int limitNum;
			if (jsonObject.containsKey("limit")&&jsonObject.getString("limit").length()>0&&jsonObject.getInt("limit")>0) {
				limitNum = jsonObject.getInt("limit");
			}else {
				limitNum = Integer.parseInt(getLimitNumString());
			}
			List<TWafLogWebsecEventTypeCount> list = mapper.selectEventTypeCountsByDomain(domainList, limitNum);
			sqlSession.clearCache();
			SAXReader reader = new SAXReader();
			Document document = reader.read(NsfocusWAFAdapter.class.getClassLoader().getResource("/").getPath()+"WAF_Nsfocus_EventType.xml");
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			for (TWafLogWebsecEventTypeCount t : list) {
				for (Element element : typeElements) {
					if (t.getEventType().equals(element.attributeValue("name"))) {
						t.setEventType(element.getTextTrim());
						continue;
					}
				}
			}
			XStream xStream = getXStream();
			JSONObject returnJsonObject = JSONObject.fromObject(xStream.toXML(list));
			returnJsonObject.put("status", "success");
			return returnJsonObject.getJSONArray("list").toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "domain is null");
			return errorJsonObject.toString();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "event type format error");
			return errorJsonObject.toString();
		} catch (JSONException e) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "json error");
			return errorJsonObject.toString();
		}
		finally {
			closeDeviceSqlSession(sqlSession);
		}
	}
	
	//huoqu1000tiao
	public String getEventTypeCountLimitByIp(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			if (!jsonObject.containsKey("dstIp")||jsonObject.getJSONArray("dstIp").isEmpty()) {
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "dst ip is null");
				return errorJsonObject.toString();
			}
			List<String> dstIpList = JSONArray.toList(jsonObject.getJSONArray("dstIp"));
			List<TWafLogWebsecEventTypeCount> list = mapper.selectEventTypeCountsByDstIp(dstIpList, Integer.parseInt(LIMIT_NUM_STRING));
			//事件类型转译为中文
			SAXReader reader = new SAXReader();
			Document document = reader.read(NsfocusWAFAdapter.class.getClassLoader().getResource("/").getPath()+"WAF_Nsfocus_EventType.xml");
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			for (Element element : typeElements) {
				for (TWafLogWebsecEventTypeCount t : list) {
					if (t.getEventType().equals(element.attributeValue("name"))) {
						t.setEventType(element.getTextTrim());
						continue;
					}
				}
			}
			XStream xStream = getXStream();
			JSONObject returnJsonObject = JSONObject.fromObject(xStream.toXML(list));
			returnJsonObject.put("status", "success");
			return returnJsonObject.getJSONArray("list").toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "dst ip is null");
			return errorJsonObject.toString();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "event type format error");
			return errorJsonObject.toString();
		}finally {
			closeDeviceSqlSession(sqlSession);
		}
	}
	
	public String getEventTypeCountInTime(JSONObject jsonObject) {
		if (jsonObject.get("timeUnit")==null||jsonObject.getString("timeUnit").length()<=0
		||null==jsonObject.get("startDate")||jsonObject.getString("startDate").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "parameter error");
		}
		SqlSession sqlSession = null;
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			List<JSONObject> typeCountList = new ArrayList<JSONObject>();
			
			sqlSession = getSqlSession();
			
			//根据时间间隔获取时间段
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			Criteria criteria = new TWafLogWebsecExample().or();
			
			String timeUnitString = jsonObject.getString("timeUnit");
			SimpleDateFormat sdf = new SimpleDateFormat();
			if (timeUnitString.equalsIgnoreCase("year")) {
				sdf = new SimpleDateFormat("yyyy");
			}else if (timeUnitString.equalsIgnoreCase("month")) {
				sdf = new SimpleDateFormat("yyyy-MM");
			}else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			Date startDate = sdf.parse(jsonObject.getString("startDate"));
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			if (timeUnitString.equalsIgnoreCase("year")) {
				cal.add(Calendar.YEAR, 1);
			}else if (timeUnitString.equalsIgnoreCase("month")) {
				cal.add(Calendar.MONTH, 1);
			}else if(timeUnitString.equalsIgnoreCase("week")){
				cal.add(Calendar.DATE, 7);
			}else {
				cal.add(Calendar.DATE, 1);
			}
			Date endDate = cal.getTime();
			if(jsonObject.containsKey("endDate")&&jsonObject.getString("endDate").length()>0){
				endDate = sdf.parse(jsonObject.getString("endDate"));
			}

			if (jsonObject.containsKey("dstIp")&&!jsonObject.getJSONArray("dstIp").isEmpty()) {
				List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
				criteria.andDstIpIn(dstIpList);
			}
			if (jsonObject.containsKey("domain")&&!jsonObject.getJSONArray("domain").isEmpty()) {
				List<String> domainList = (List<String>) jsonObject.get("domain");
				criteria.andDomainIn(domainList);
			}

			if (!startDate.before(endDate)) {
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "parameter error");
				return errorJsonObject.toString();
			}
			criteria.andStatTimeBetween(startDate, endDate);
			//查询并返回结果			
			for (Element element : typeElements) {
				
				String eventTypeBase64 = element.getTextTrim();
				criteria.andEventTypeEqualTo(element.attributeValue("name"));
				TWafLogWebsecExample tWafLogWebsecExample = new TWafLogWebsecExample();
				tWafLogWebsecExample.or(criteria);
				JSONObject eventTypeJsonObject = new JSONObject();
				eventTypeJsonObject.put("eventType", eventTypeBase64);
				eventTypeJsonObject.put("count", mapper.countByExample(tWafLogWebsecExample));
				typeCountList.add(eventTypeJsonObject);
				criteria.criteria.remove(criteria.criteria.size()-1);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("list", JSONArray.fromObject(typeCountList));
			return returnJsonObject.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype counts' IO error!!!");
			return errorJsonObject.toString();
		} catch (DocumentException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype counts' document read error!!!");
			return errorJsonObject.toString();
		} catch (ParseException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "time unit error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	//按天查询统计告警类型信息
	public String getEventTypeCountByDay(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			
			int interval = jsonObject.getInt("interval");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date dateNow = calendar.getTime();
			calendar.add(Calendar.DATE, -interval);
			Date dateBefore = calendar.getTime();
			//根据时间间隔获取时间段
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			sqlSession = getSqlSession();
			TWafLogWebsecCountMapper mapper = sqlSession.getMapper(TWafLogWebsecCountMapper.class);
			SAXReader reader = new SAXReader();
			Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			JSONArray jsonArray = new JSONArray();
			for (Element element : typeElements) {
				String eventTypeString = element.getTextTrim();
				String eventTypeBase64 = stringToBase64(eventTypeString);
				TWafLogWebsecCountExample countExample = new TWafLogWebsecCountExample();
				countExample.or().andStatTimeBetween(sdf.format(dateBefore), sdf.format(dateNow)).andEventTypeEqualTo(element.attributeValue("name"));
				List<TWafLogWebsecCount> counts = mapper.selectByExample(countExample);
				JSONObject counntInTimeJsonObject = JSONObject.fromObject(getXStream().toXML(counts));				
				counntInTimeJsonObject.put("eventTypeBase64", eventTypeBase64);
				counntInTimeJsonObject.put("eventType", eventTypeString);
				jsonArray.add(counntInTimeJsonObject);
			}
			
			return jsonArray.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype counts' IO error!!!");
			JSONArray errorJsonArray = new JSONArray();
			errorJsonArray.add(errorJsonObject);
			return errorJsonArray.toString();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype counts' document read error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	
	public String getEventTypeCountByMonth(JSONObject jsonObject) {
			//校验传入参数
			if(null==jsonObject.get("startDate")||jsonObject.getString("startDate").length()<0){
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "parameter is null");
				return errorJsonObject.toString();
			}
			SqlSession sqlSession = null;
		try {
			
			//获取时间间隔
			int interval;
			if(jsonObject.get("interval")==null||jsonObject.getInt("interval")<=0){
				interval = 1;
			}else {
				interval = jsonObject.getInt("interval");
			}
			
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM");
			//获取结束时间
			Date endDate;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			if(jsonObject.get("endDate")==null||jsonObject.getString("endDate").length()<=0){
				endDate = calendar.getTime();
			}else {
				endDate = sdf.parse(jsonObject.getString("endDate"));
			}
			//组装时间格式
			
	
			//获取事件类型列表
			SAXReader reader = new SAXReader();
			Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
			List<Element> typeElements = document.selectNodes("/TypeList/Type");
			
			//按月统计事件类型
			
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			
			List<JSONObject> typeCountList = new ArrayList<JSONObject>();
			for (int i=0;i<typeElements.size();i++) {
				Element element = typeElements.get(i);
				String eventTypeBase64 = element.getTextTrim();
				JSONObject eventTypeJsonObject = new JSONObject();
				eventTypeJsonObject.put("eventType", eventTypeBase64);
				JSONArray eventTypeInTimeArray = new JSONArray();
				Date startDate = sdf.parse(jsonObject.getString("startDate"));
				while (!startDate.after(endDate)) {
					TWafLogWebsecExample exampleElement = new TWafLogWebsecExample();
					JSONObject eventTypeInTimeJsonObject = new JSONObject();
					calendar.setTime(startDate);
					calendar.add(Calendar.MONTH, interval);
					exampleElement.or().andEventTypeEqualTo(element.attributeValue("name")).andStatTimeBetween(startDate, calendar.getTime());

					eventTypeInTimeJsonObject.put("date", sdf.format(startDate));
					eventTypeInTimeJsonObject.put("count", mapper.countByExample(exampleElement));
					eventTypeInTimeArray.add(eventTypeInTimeJsonObject);
					startDate = calendar.getTime();
				}
				eventTypeJsonObject.put("countInTime", eventTypeInTimeArray);
				typeCountList.add(eventTypeJsonObject);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("list", JSONArray.fromObject(typeCountList));
			return returnJsonObject.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype count error!!!");
			return errorJsonObject.toString();
		} finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getEventTypeCountByYear(JSONObject jsonObject) {
		
		
		//校验传入参数
	
		if(null==jsonObject.get("startDate")||jsonObject.getString("startDate").length()<0){
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "parameter is null");
			return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
	try {
		
		//获取时间间隔
		int interval;
		if(jsonObject.get("interval")==null||jsonObject.getInt("interval")<=0){
			interval = 1;
		}else {
			interval = jsonObject.getInt("interval");
		}
		
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy");
		//获取结束时间
		Date endDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if(jsonObject.get("endDate")==null||jsonObject.getString("endDate").length()<=0){
			endDate = calendar.getTime();
		}else {
			endDate = sdf.parse(jsonObject.getString("endDate"));
		}
		//组装时间格式
		

		//获取事件类型列表
		SAXReader reader = new SAXReader();
		Document document = reader.read(DeviceAdapterConstant.WAF_NSFOCUS_EVENT_TYPE);
		List<Element> typeElements = document.selectNodes("/TypeList/Type");
		
		//按月统计事件类型
		
		sqlSession = getSqlSession();
		TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
		
		List<JSONObject> typeCountList = new ArrayList<JSONObject>();
		for (int i=0;i<typeElements.size();i++) {
			Element element = typeElements.get(i);
			String eventTypeBase64 = element.getTextTrim();
			JSONObject eventTypeJsonObject = new JSONObject();
			eventTypeJsonObject.put("eventType", eventTypeBase64);
			JSONArray eventTypeInTimeArray = new JSONArray();
			Date startDate = sdf.parse(jsonObject.getString("startDate"));
			while (!startDate.after(endDate)) {
				TWafLogWebsecExample exampleElement = new TWafLogWebsecExample();
				JSONObject eventTypeInTimeJsonObject = new JSONObject();
				calendar.setTime(startDate);
				calendar.add(Calendar.YEAR, interval);
				exampleElement.or().andEventTypeEqualTo(element.attributeValue("name")).andStatTimeBetween(startDate, calendar.getTime());

				eventTypeInTimeJsonObject.put("date", sdf.format(startDate));
				eventTypeInTimeJsonObject.put("count", mapper.countByExample(exampleElement));
				eventTypeInTimeArray.add(eventTypeInTimeJsonObject);
				startDate = calendar.getTime();
			}
			eventTypeJsonObject.put("countInTime", eventTypeInTimeArray);
			typeCountList.add(eventTypeJsonObject);
		}
		JSONObject returnJsonObject = new JSONObject();
		returnJsonObject.put("status", "success");
		returnJsonObject.put("list", JSONArray.fromObject(typeCountList));
		return returnJsonObject.toString();
		
		} catch (Exception e) {
		e.printStackTrace();
		JSONObject errorJsonObject = new JSONObject();
		errorJsonObject.put("status", "failed");
		errorJsonObject.put("message", "Eventtype count error!!!");
		return errorJsonObject.toString();
		} finally {
		closeSqlSession(sqlSession);
		}
	}
	
	
	
	public String getWafLogWebSecDstIpList(){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogWebsecDstMapper mapper =sqlSession.getMapper(TWafLogWebsecDstMapper.class);
			TWafLogWebsecDstExample example = new TWafLogWebsecDstExample();
			List<TWafLogWebsecDst> dsts = mapper.selectByExample(example);
			XStream xStream = getXStream();
			xStream.alias("WafLogWebSecDstIpList", List.class);
			String jsonString = xStream.toXML(dsts);
			return jsonString;
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "WafLogWebSecDstIp Count error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getWafLogWebSecSrcIpList(){
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			TWafLogWebsecSrcMapper mapper =sqlSession.getMapper(TWafLogWebsecSrcMapper.class);
			TWafLogWebsecSrcExample example = new TWafLogWebsecSrcExample();
			List<TWafLogWebsecSrc> srcs = mapper.selectByExample(example);
			XStream xStream = getXStream();
			xStream.alias("WafLogWebSecSrcIpList", List.class);
			String jsonString = xStream.toXML(srcs);
			return jsonString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "WafLogWebSecSrcIp Count error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	

	
	public String getWafLogWebSecTimeCount(JSONObject jsonObject) {
		if (jsonObject.get("timeUnit").equals(null)||jsonObject.getString("timeUnit").length()<=0
				||!(jsonObject.getString("timeUnit").equalsIgnoreCase("day")||jsonObject.getString("timeUnit").equalsIgnoreCase("month")||jsonObject.getString("timeUnit").equalsIgnoreCase("week")||jsonObject.getString("timeUnit").equalsIgnoreCase("year"))
				||jsonObject.get("startDate").equals(null)||jsonObject.getString("startDate").length()<=0
			) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "parameter error");
			return errorJsonObject.toString();
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSession();
			//获取防护对象ip列表
			
			Criteria criteria = new TWafLogWebsecExample().createCriteria();
//设置dspIp列表
			if (jsonObject.containsKey("dstIp")&&!jsonObject.getJSONArray("dstIp").isEmpty()) {
				List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
				criteria.andDstIpIn(dstIpList);
			}
			if (jsonObject.containsKey("domain")&&!jsonObject.getJSONArray("domain").isEmpty()) {
				List<String> domainList = (List<String>) jsonObject.get("domain");
				criteria.andDomainIn(domainList);
			}
			//设置开始结束时间
			//tWafLogWebsecCriteria.andStatTimeBetween(dateBefore, dateNow);
			String timeUnitString = jsonObject.getString("timeUnit");
			SimpleDateFormat sdf = new SimpleDateFormat();
			if (timeUnitString.equalsIgnoreCase("year")) {
				sdf = new SimpleDateFormat("yyyy");
			}else if (timeUnitString.equalsIgnoreCase("month")) {
				sdf = new SimpleDateFormat("yyyy-MM");
			}else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			Date startDate = sdf.parse(jsonObject.getString("startDate"));
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			if (timeUnitString.equalsIgnoreCase("year")) {
				cal.add(Calendar.YEAR, 1);
			}else if (timeUnitString.equalsIgnoreCase("month")) {
				cal.add(Calendar.YEAR, 1);
			}else if (timeUnitString.equalsIgnoreCase("week")){
				cal.add(Calendar.DATE, 7);
			}else{
				cal.add(Calendar.MONTH, 1);
			}
			Date endDate = cal.getTime();
			if(null!=jsonObject.get("endDate")&&jsonObject.getString("endDate").length()>0){
				endDate = sdf.parse(jsonObject.getString("endDate"));
			}
			//判断开始结束日期顺序是否正确
			if (startDate.after(endDate)) {
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "parameter date error");
				return errorJsonObject.toString();
			}
			
			JSONArray timeCountJsonArray = new JSONArray();
			int interval = 1;
			
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			while (startDate.before(endDate)) {
				cal.setTime(startDate);
				if (timeUnitString.equalsIgnoreCase("year")) {
					cal.add(Calendar.YEAR, interval);
				}else if (timeUnitString.equalsIgnoreCase("month")) {
					cal.add(Calendar.MONTH, interval);
				}else {
					cal.add(Calendar.DATE, interval);
				}
				criteria.andStatTimeBetween(startDate, cal.getTime());
				TWafLogWebsecExample example = new TWafLogWebsecExample();
				example.or(criteria);
				JSONObject timeCountJsonObject = new JSONObject();
				timeCountJsonObject.put("time", sdf.format(startDate));
				timeCountJsonObject.put("count", mapper.countByExample(example));
				timeCountJsonArray.add(timeCountJsonObject);
				startDate = cal.getTime();
				criteria.criteria.remove(criteria.criteria.size()-1);
			}
			
			
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("countList", timeCountJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// 
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "sql session error");
			return errorJsonObject.toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "json format error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
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
				mapAlertLevelCount.put(alertLevelString, mapper.countByExample(example));
			}
			JSONObject alertLevelJsonObject = JSONObject.fromObject(mapAlertLevelCount);
			return alertLevelJsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "AlertLevel count error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	/**
	 * 按时间单位获取告警级别分布
	 * @param jsonObject
	 * @param timeUnit 时间单位（day、month、year）
	 * @param dstIp 防护对象ip列表
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return alertLevelCountJsonString
	 */
	public String getAlertLevelCountInTime(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		//验证必要的输入参数
		if (jsonObject.get("timeUnit").equals(null)||jsonObject.getString("timeUnit").length()<=0
				||!(jsonObject.getString("timeUnit").equalsIgnoreCase("day")||jsonObject.getString("timeUnit").equalsIgnoreCase("month")||jsonObject.getString("timeUnit").equalsIgnoreCase("week")||jsonObject.getString("timeUnit").equalsIgnoreCase("year"))
				||jsonObject.get("startDate").equals(null)||jsonObject.getString("startDate").length()<=0
			) {
			//返回参数错误信息
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "parameter error");
			return errorJsonObject.toString();
		}
		try {
			
			//取告警等级列表<目前为高、中、低>
			List<String> alertLevelList = Arrays.asList(ALERT_LEVEL_STRINGS);
			Criteria criteria = new TWafLogWebsecExample().createCriteria();
			//设置dspIp列表
			if (jsonObject.containsKey("dstIp")&&!jsonObject.getJSONArray("dstIp").isEmpty()&&jsonObject.getJSONArray("dstIp").getString(0).length()>0) {
				List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
				criteria.andDstIpIn(dstIpList);
			}
			//设置防护对象域名domain列表
			if (jsonObject.containsKey("domain")&&!jsonObject.getJSONArray("domain").isEmpty()&&jsonObject.getJSONArray("domain").getString(0).length()>0) {
				List<String> domainList = (List<String>) jsonObject.get("domain");
				criteria.andDomainIn(domainList);
			}
			//
			//设置开始结束时间
			//tWafLogWebsecCriteria.andStatTimeBetween(dateBefore, dateNow);
			//设置时间单位，默认为天
			String timeUnitString = jsonObject.getString("timeUnit");
			SimpleDateFormat sdf = new SimpleDateFormat();
			if (timeUnitString.equalsIgnoreCase("year")) {
				sdf = new SimpleDateFormat("yyyy");
			}else if (timeUnitString.equalsIgnoreCase("month")) {
				sdf = new SimpleDateFormat("yyyy-MM");
			}else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			//根据时间单位获取开始时间、结束时间
			Date startDate = sdf.parse(jsonObject.getString("startDate"));
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			//如果没有结束时间参数结束时间为开始时间加1时间单位（年、月、日）
			if (timeUnitString.equalsIgnoreCase("year")) {
				cal.add(Calendar.YEAR, 1);
			}else if (timeUnitString.equalsIgnoreCase("month")) {
				cal.add(Calendar.MONTH, 1);
			}else if (timeUnitString.equalsIgnoreCase("week")) {
				cal.add(Calendar.DATE, 7);
			}else{
				cal.add(Calendar.DATE, 1);
			}
			Date endDate = cal.getTime();
			//如果输入了结束时间参数，按时间单位获取结束时间
			if(null!=jsonObject.get("endDate")&&jsonObject.getString("endDate").length()>0){
				endDate = sdf.parse(jsonObject.getString("endDate"));
			}
			//判断开始结束日期顺序是否正确
			if (!startDate.before(endDate)) {
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "parameter error");
				return errorJsonObject.toString();
			}
			criteria.andStatTimeBetween(startDate, endDate);
			
			//获取告警等级统计信息
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			Map<String, Integer> mapAlertLevelCount = new HashMap<String, Integer>();
			for (String alertLevelString : alertLevelList) {
				criteria.andAlertlevelEqualTo(alertLevelString);
				TWafLogWebsecExample example = new TWafLogWebsecExample();
				example.or(criteria);
				
				mapAlertLevelCount.put(alertLevelString, mapper.countByExample(example));
				criteria.criteria.remove(criteria.criteria.size()-1);
			}
			JSONObject alertLevelJsonObject = JSONObject.fromObject(mapAlertLevelCount);
			alertLevelJsonObject.put("status", "success");
			return alertLevelJsonObject.toString();
		} catch (Exception e) {
			// 
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "AlertLevel count error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}

	public String getAlertLevelCountByHour(JSONObject jsonObject) {
		
		if (jsonObject.get("interval")==null||jsonObject.getInt("interval")<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "Eventtype parameter error!!!");
		}
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
			//取告警等级列表<目前为高、中、低>
			List<String> alertLevelList = Arrays.asList(ALERT_LEVEL_STRINGS);
			
			Criteria tWafLogWebsecCriteria = new TWafLogWebsecExample().createCriteria();
			//设置dspIp列表
			if (jsonObject.containsKey("dstIp")&&!jsonObject.getJSONArray("dstIp").isEmpty()&&jsonObject.getJSONArray("dstIp").getString(0).length()>0) {
				List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
				tWafLogWebsecCriteria.andDstIpIn(dstIpList);
			}
			if (jsonObject.containsKey("domain")&&jsonObject.getString("domain").length()>0) {
				tWafLogWebsecCriteria.andDomainEqualTo(jsonObject.getString("domain"));
			}
			tWafLogWebsecCriteria.andStatTimeBetween(dateBefore, dateNow);
			
			//获取告警等级统计信息
			
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			Map<String, Integer> mapAlertLevelCount = new HashMap<String, Integer>();
			for (String alertLevelString : alertLevelList) {
				tWafLogWebsecCriteria.andAlertlevelEqualTo(alertLevelString);
				TWafLogWebsecExample example = new TWafLogWebsecExample();
				example.or(tWafLogWebsecCriteria);
				
				mapAlertLevelCount.put(alertLevelString, mapper.countByExample(example));
				tWafLogWebsecCriteria.criteria.remove(tWafLogWebsecCriteria.criteria.size()-1);
			}
			JSONObject alertLevelJsonObject = JSONObject.fromObject(mapAlertLevelCount);
			return alertLevelJsonObject.toString();
		} catch (Exception e) {
			// 
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "AlertLevel count error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	public String getAlertLevelCountLimitByDomain(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {
			if (!jsonObject.containsKey("domain")||jsonObject.getJSONArray("domain").isEmpty()) {
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "domain is null");
				return errorJsonObject.toString();
			}
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			List<String> domainList = JSONArray.toList(jsonObject.getJSONArray("domain"));
			
			int limitNum = Integer.parseInt(getLimitNumString());
			if (jsonObject.containsKey("limit")&&jsonObject.getString("limit").length()>0&&jsonObject.getInt("limit")>=0) {
				limitNum = jsonObject.getInt("limit");
			}
			List<TWafLogWebsecAlertLevelCount> list = mapper.selectAlertLevelCountByDomain(domainList, limitNum);
			sqlSession.clearCache();
			JSONObject returnJsonObject = new JSONObject();
			List<String> alertLevelList = Arrays.asList(ALERT_LEVEL_STRINGS);
			for (String string : alertLevelList) {
				returnJsonObject.put(string, 0);
				for (TWafLogWebsecAlertLevelCount count : list) {
					if (count.getAlertlevel().equalsIgnoreCase(string)) {
						returnJsonObject.put(string, count.getCount());
						break;
					}
				}
			}

			
			return returnJsonObject.toString();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "sql session error");
			return errorJsonObject.toString();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "limit number error");
			return errorJsonObject.toString();
		} catch (DocumentException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "document error");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	
	
	public String getAlertLevelCountByMonth(JSONObject jsonObject){
		SqlSession sqlSession = null;
		try {

			//获取时间间隔
			int interval = jsonObject.getInt("interval");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date endDate = calendar.getTime();
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM");
			
			//取告警等级列表<目前为高、中、低>
			List<String> alertLevelList = Arrays.asList(ALERT_LEVEL_STRINGS);

			//获取告警等级统计信息
			sqlSession  = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			JSONArray allAlertLevelaArray = new JSONArray();
			for (String alertLevelString : alertLevelList) {
				JSONObject alertLevelObject = new JSONObject();
				Date startDate = sdf.parse(jsonObject.getString("startDate"));
				JSONArray alertLevelArray = new JSONArray();
				while (startDate.before(endDate)) {
					calendar.setTime(startDate);
					calendar.add(Calendar.MONTH, interval);
					
					TWafLogWebsecExample example = new TWafLogWebsecExample();
					Criteria websecCriteria = example.createCriteria();
					if(jsonObject.get("domain")!=null&&jsonObject.getJSONArray("domain").isArray()){
						List domainList;
						if (jsonObject.getJSONArray("domain").isEmpty()) {
							domainList=new ArrayList();
							domainList.add("");
						} else {
							domainList = JSONArray.toList(jsonObject.getJSONArray("domain"));
						}
						websecCriteria.andDomainIn(domainList);
					}
					websecCriteria.andAlertlevelEqualTo(alertLevelString).andStatTimeBetween(startDate, calendar.getTime());					
					//组织返回内容
					JSONObject alertLevelOneMonthObject = new JSONObject();
					alertLevelOneMonthObject.put("count", mapper.countByExample(example));
					alertLevelOneMonthObject.put("time", sdf.format(startDate));
					
					alertLevelArray.add(alertLevelOneMonthObject);
					
					startDate = calendar.getTime();
				}
				alertLevelObject.put("alertLevel", alertLevelString);
				alertLevelObject.put("countList", alertLevelArray);
				
				allAlertLevelaArray.add(alertLevelObject);
			}
			return allAlertLevelaArray.toString();
		} catch (IOException e) {
			// 
			e.printStackTrace();
			//错误
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "AlertLevel count error!!!");
			return errorJsonObject.toString();
		}catch (ParseException e) {
			// 
			e.printStackTrace();
			//错误
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "AlertLevel date format count error!!!");
			return errorJsonObject.toString();
		}finally {
			closeSqlSession(sqlSession);
		}
	}
	public String getWafLogWebsecSrcIpCountInTime(JSONObject jsonObject) {
		SqlSession sqlSession = null;
		try {
			//判断json内容是否正确
			if(null==jsonObject.get("startDate")||jsonObject.getString("startDate").length()<=0
					||null==jsonObject.get("limitNum")||jsonObject.getInt("limitNum")<=0
					||null==jsonObject.get("timeUnit")||jsonObject.getString("timeUnit").length()<=0
			){
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "parameter error");
			}
			
			sqlSession = getSqlSession();
			TWafLogWebsecMapper mapper = sqlSession.getMapper(TWafLogWebsecMapper.class);
			
			
			//默认获取攻击源ip前10位
			int limitNum = 10;
			if (jsonObject.containsKey("limit")&&jsonObject.getString("limit").length()>0&&jsonObject.getInt("limit")>=0) {
				limitNum = jsonObject.getInt("limit");
			}
			
			
			String timeUnitString = jsonObject.getString("timeUnit");
			SimpleDateFormat sdf = new SimpleDateFormat();
			if (timeUnitString.equalsIgnoreCase("year")) {
				sdf = new SimpleDateFormat("yyyy");
			}else if (timeUnitString.equalsIgnoreCase("month")) {
				sdf = new SimpleDateFormat("yyyy-MM");
			}else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			Date startDate = sdf.parse(jsonObject.getString("startDate"));
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			if (timeUnitString.equalsIgnoreCase("year")) {
				cal.add(Calendar.YEAR, 1);
			}else if (timeUnitString.equalsIgnoreCase("month")) {
				cal.add(Calendar.MONTH, 1);
			}else if (timeUnitString.equalsIgnoreCase("week")){
				cal.add(Calendar.DATE, 7);
			}else{
				cal.add(Calendar.DATE, 1);
			}
			
			Date endDate = cal.getTime();
			
			if(null!=jsonObject.get("endDate")&&jsonObject.getString("endDate").length()>0){
				endDate = sdf.parse(jsonObject.getString("endDate"));
			}

			//判断开始结束日期顺序是否正确
			if (!startDate.before(endDate)) {
				JSONObject errorJsonObject = new JSONObject();
				errorJsonObject.put("status", "failed");
				errorJsonObject.put("message", "parameter error");
				return errorJsonObject.toString();
			}
			List<TWafLogWebsecDstSrc> list = new ArrayList<TWafLogWebsecDstSrc>();
			if (jsonObject.containsKey("domain")&&!jsonObject.getJSONArray("domain").isEmpty()) {
				List<String> strList = JSONArray.toList(jsonObject.getJSONArray("domain"));
				list = mapper.selectSrcIp(strList, limitNum, startDate, endDate);
			}
			if (jsonObject.containsKey("dstIp")&&!jsonObject.getJSONArray("dstIp").isEmpty()) {
				List<String> strList = JSONArray.toList(jsonObject.getJSONArray("dstIp"));
				list = mapper.selectSrcIpByDstIp(strList, limitNum, startDate, endDate);
			}
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("list", JSONArray.fromObject(list));
			return returnJsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "sql connection error");
			return errorJsonObject.toString();
		} catch (ParseException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "json format error");
			return errorJsonObject.toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "limit number error");
			return errorJsonObject.toString();			
		}finally {
			closeSqlSession(sqlSession);
		}
		
	}

}
