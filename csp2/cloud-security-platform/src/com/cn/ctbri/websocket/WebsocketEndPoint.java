package com.cn.ctbri.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.constant.EventTypeCode;
import com.cn.ctbri.entity.Attack;
import com.cn.ctbri.entity.AttackCount;
import com.cn.ctbri.entity.IPPosition;
import com.cn.ctbri.service.IIPPositionService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.MapUtil;
import com.cn.ctbri.util.NumberUtils;
import com.cn.ctbri.vo.AttackVO;

/**
 * 创 建 人 ： 于永波 创建日期： 2014-12-30 描 述： WebSocket服务器消息监听控制器 版 本： 1.0
 */
public class WebsocketEndPoint extends TextWebSocketHandler {
	@Autowired
	IIPPositionService ipPositionService;
	// 存储格式（IP,IPPosition）
	static Map<String, IPPosition> ipPositionMap = null;
	private long id=0;
	
	private static int maxSize=20;
	WafAPIWorker worker = new WafAPIWorker();
	public synchronized Map<String, IPPosition> getIPPositions() {
		if (ipPositionMap == null) {
			ipPositionMap = ipPositionService.findIPPositions();
		}
		return ipPositionMap;

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
	}

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		long startId=0;
		String receiveMsg=message.getPayload();
		if(StringUtils.isNotEmpty(receiveMsg)){
			String[] arrayToken=receiveMsg.split("--");
			//为了安全做的一个类似token认证
			if(arrayToken!=null&&arrayToken[0]!=null&&"135de9e2fb6ae653e45f06ed18fbe9a7".equals(arrayToken[0])){
				startId=Long.parseLong(arrayToken[1]);
				//源端的经纬度
				LinkedList<String> srcPositionList=new LinkedList<String>();
				//目的端的经纬度
				LinkedList<String> desPositionList=new LinkedList<String>();
				
				String dataText =null;
				while(true){
					System.out.println("startId:"+startId);
					try{
						dataText=getWafData(startId);
						System.out.println("dataText:"+dataText);
						JSONObject json = JSONObject.fromObject(dataText);
						JSONArray array = (JSONArray) json.get("wafLogWebsecList");
						startId=json.getLong("currentId");
						if(null!=array&&array.size()==0){
							Thread.sleep(1000*10);
							continue;
						}
						perSendData(session,message,array,srcPositionList,desPositionList);
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
				}
			}
		}
	}
	class DateComparator implements Comparator<Date>{

		@Override
		public int compare(Date o1, Date o2) {
			return (int) (o1.getTime()-o2.getTime());
		}
		
	}
	public String getFirstWafData(){
		String text=worker.getWafLogWebsecCurrent(500);
		return text;
	}
	public String getWafData(long currentId){
		WafAPIWorker worker = new WafAPIWorker();
		String text = worker.getAllWafLogWebsecInTime(currentId);
		return text;
	}
	
	public void perSendData(WebSocketSession session,TextMessage message,JSONArray array,LinkedList srcPositionList,LinkedList desPositionList) throws IOException, InterruptedException{
		if(array==null){
			return;
		}
		long size = array.size();
		/**
		 * Date：外层Map以时间作为key，时间精确到秒，每秒可能产生几百条数据量
		 * String：内层Map以攻击类型作为Key，为了便于统计同一时间产生不同类型的攻击数量
		 * LinkedList<Attack>存储同一时间下，同一攻击类型的详细攻击信息
		 */
		Map<Date,HashMap<Integer,LinkedList<Attack>>> attackSortMap=new TreeMap<Date,HashMap<Integer,LinkedList<Attack>>>(new DateComparator());
		for (int i = 0; i < size; i++) {
			JSONObject obj = (JSONObject) array.get(i);
			String srcIP = obj.getString("srcIp");
			String desIP = obj.getString("dstIp");
			String desPort = obj.getString("dstPort");
			Date startTime =DateUtils.stringToDateNYRSFM(obj.getString("statTime")); ;
			byte[] base64Bytes = Base64.decodeBase64(obj.getString("eventType")
					.getBytes());
			String eventType = new String(base64Bytes, "UTF-8");
			Integer typeCode = EventTypeCode.typeToCodeMap.get(eventType);
			String srcLongitude = null;
			String srcLatitude = null;
			String desLongitude = null;
			String desLatitude = null;
			String srcName = null;
			String desName = null;
			IPPosition srcIPPosition = getIPPositions().get(srcIP);
			// 1.优先使用百度接口根据ip查询经纬度
			if (null == srcIPPosition) {// 数据库中不存在
				srcIPPosition = new IPPosition();
				ipPositionMap.put(srcIP, srcIPPosition);
				//1.先调电信的接口
				String ipMessage=WafAPIWorker.getLocationFromIp(srcIP);
				JSONObject ipJson = JSONObject.fromObject(ipMessage); 
				//存在数据的情况返回数据格式：{"ip":"1.54.44.1","latitude":"16.0000","longtitude":"106.0000","continent":"亚洲","country":"越南","city":""}
				//不存在数据的情况下返回的数据格式:{"ip":"1.2.2.3"}
				try {
					srcLatitude=ipJson.getString("latitude");
				} catch (Exception e) {
					//电信接口不存在数据
				}
				if(StringUtils.isNotEmpty(srcLatitude)){//电信接口存在数据
					srcLongitude=ipJson.getString("longtitude");
					String wafCountry=ipJson.getString("country");
					String wafCity=ipJson.getString("city");
					srcName=wafCountry+wafCity;
					srcIPPosition.setIp(srcIP);
					srcIPPosition.setRegisterTime(new Date());
					srcIPPosition.setLongitude(srcLongitude);
					srcIPPosition.setLatitude(srcLatitude);
					srcIPPosition.setCountryProvince(srcName);
					ipPositionService.saveIPPosition(srcIPPosition);
				}else{//电信接口不存在数据
					//2.电信接口如果不存在的话，再掉百度的接口
					
					// 源经纬度
					String srcPosition = MapUtil.getPositionByIp(srcIP);
					srcIPPosition.setIp(srcIP);
					srcIPPosition.setRegisterTime(new Date());
					if (null != srcPosition) {
						String[] positionArray = srcPosition.split(",");
						srcLongitude = positionArray[0];
						srcLatitude = positionArray[1];
						srcIPPosition.setLongitude(srcLongitude);
						srcIPPosition.setLatitude(srcLatitude);
					} else {
						// 入库
						ipPositionService.saveIPPosition(srcIPPosition);
						continue;

					}
					srcName = MapUtil.getCountryByPosition(srcLongitude,
							srcLatitude);
					if (null == srcName) {
						// 入库
						ipPositionService.saveIPPosition(srcIPPosition);
						continue;
					} else {
						srcIPPosition.setCountryProvince(srcName);
						ipPositionService.saveIPPosition(srcIPPosition);
					}

				}
				
				
				
				
			} else {// 已经存在于数据库中
				srcLongitude = srcIPPosition.getLongitude();
				srcLatitude = srcIPPosition.getLatitude();
				srcName = srcIPPosition.getCountryProvince();
			}

			// 1.优先使用百度接口根据ip查询经纬度
			// 分析目标ip
			IPPosition desIPPosition = getIPPositions().get(desIP);
			if (null == desIPPosition) {// 数据库中不存在数据
				
				desIPPosition = new IPPosition();
				ipPositionMap.put(desIP, desIPPosition);
				
				//1.先调电信的接口
				String ipMessage=WafAPIWorker.getLocationFromIp(desIP);
				JSONObject ipJson = JSONObject.fromObject(ipMessage); 
				//存在数据的情况返回数据格式：{"ip":"1.54.44.1","latitude":"16.0000","longtitude":"106.0000","continent":"亚洲","country":"越南","city":""}
				//不存在数据的情况下返回的数据格式:{"ip":"1.2.2.3"}
				try {
					desLatitude=ipJson.getString("latitude");
				} catch (Exception e) {
				}
				if(StringUtils.isNotEmpty(desLatitude)){//电信接口存在数据
					desLongitude=ipJson.getString("longtitude");
					String wafCountry=ipJson.getString("country");
					String wafCity=ipJson.getString("city");
					desName=wafCountry+wafCity;
					desIPPosition.setIp(desIP);
					desIPPosition.setRegisterTime(new Date());
					desIPPosition.setLongitude(desLongitude);
					desIPPosition.setLatitude(desLatitude);
					desIPPosition.setCountryProvince(desName);
					ipPositionService.saveIPPosition(desIPPosition);
				}else{//电信接口不存在数据
					
					// 目标经纬度
					String desPosition = MapUtil.getPositionByIp(desIP);
					desIPPosition.setIp(desIP);
					desIPPosition.setRegisterTime(new Date());
					if (null != desPosition) {
						String[] positionArray = desPosition.split(",");
						desLongitude = positionArray[0];
						desLatitude = positionArray[1];
						desIPPosition.setLongitude(desLongitude);
						desIPPosition.setLatitude(desLatitude);
					} else {

						// 入库
						ipPositionService.saveIPPosition(desIPPosition);
						continue;

					}

					desName = MapUtil.getCountryByPosition(desLongitude,
							desLatitude);

					if (null == desName) {
						// 入库
						ipPositionService.saveIPPosition(desIPPosition);
						continue;
					} else {
						desIPPosition.setCountryProvince(desName);
						ipPositionService.saveIPPosition(desIPPosition);
					}
					
				}
			} else {// 数据库中已存在
				desLongitude = desIPPosition.getLongitude();
				desLatitude = desIPPosition.getLatitude();
				desName = desIPPosition.getCountryProvince();
			}

			if (StringUtils.isNotEmpty(srcName)
					&& StringUtils.isNotEmpty(desName)) {
				// 组装数据
				Attack attack = new Attack();
				attack.setSrcLongitude(srcLongitude);
				attack.setSrcLatitude(srcLatitude);
				attack.setDesLongitude(desLongitude);
				attack.setDesLatitude(desLatitude);
				attack.setDesName(desName);
				attack.setSrcName(srcName);
				attack.setStartTime(startTime);
				attack.setType(eventType);
				attack.setTypeCode(typeCode);
				attack.setSrcIP(srcIP);
				attack.setDesIP(desIP);
				attack.setPort(desPort);
				HashMap<Integer,LinkedList<Attack>> attackListMap=attackSortMap.get(startTime);
				if(attackListMap==null||attackListMap.isEmpty()){
					attackSortMap.put(startTime,new HashMap<Integer,LinkedList<Attack>>());
				}
				LinkedList<Attack> attackList= attackSortMap.get(startTime).get(eventType);
				if(attackList==null||attackList.isEmpty()){
					attackSortMap.get(startTime).put(EventTypeCode.typeToCodeMap.get(eventType),new LinkedList<Attack>());
				}
				attackSortMap.get(startTime).get(EventTypeCode.typeToCodeMap.get(eventType)).add(attack);
			}
		}
		JSONObject jsonObject = new JSONObject();
		ArrayList<AttackCount> attackCountList=null;
		for(Entry<Date, HashMap<Integer, LinkedList<Attack>>> entry :attackSortMap.entrySet()){
			/**
			 * 存储同一时间内各个攻击类型的数量
			 */
			attackCountList=new ArrayList<AttackCount>();
			HashMap<Integer, LinkedList<Attack>> attackMap=entry.getValue();
			for(Entry<Integer,LinkedList<Attack>> attackEntry:attackMap.entrySet()){
				attackCountList.add(new AttackCount(EventTypeCode.codeToTypeMap.get(attackEntry.getKey()),attackEntry.getValue().size()));
			}
			/**
			 * 随机读取同一时间内的三条数据，推送到前端
			 */
			int includeType=attackMap.size();//同一时间内的攻击类型
			//1.如果同一时间攻击类型>=3种类型
			if(includeType>2){
				int [] randomArray=new int[3];
				for(int i=0;i<3;i++){					
					Random random = new Random();
					randomArray[i]=random.nextInt(includeType);
				}
				Arrays.sort(randomArray);
				int i=0;
				for(Entry<Integer,LinkedList<Attack>> attackListEntry:attackMap.entrySet()){
					if(i==randomArray[0]||i==randomArray[1]||i==randomArray[2]){
						LinkedList<Attack> attackList=attackListEntry.getValue();
						Attack attack=attackList.get(0);
						attack.setId(id);
						if(id<Long.MAX_VALUE){
							id++;
						}else{
							id=0;
						}
						if(i==2){
							attack.setAttackCount(attackCountList);
						}
						AttackVO attackVO=new AttackVO(attack);
						judgeRepeat(attackVO,srcPositionList,desPositionList);
						jsonObject.put("attack", attackVO);
						System.out.println("positionDetail:"+attackVO.getSrcLongitude()+"-"+attackVO.getSrcLatitude()+"----"+attackVO.getDesLongitude()+"-"+attackVO.getDesLatitude());
						TextMessage returnMessage = new TextMessage(jsonObject.toString());
						session.sendMessage(returnMessage);
						Thread.currentThread().sleep(350);
					}
					i++;
					
				}
			}else{
				//2.如果同一时间攻击类型<3种类型,从开始找三个数据就ok了
				int i=0;
				for(Entry<Integer,LinkedList<Attack>> attackEntry:attackMap.entrySet()){
					for(Attack attack:attackEntry.getValue()){
						if(i==0){
							attack.setAttackCount(attackCountList);
						}
						i++;
						attack.setId(id);
						if(id<Long.MAX_VALUE){
							id++;
						}else{
							id=0;
						}
						AttackVO attackVO=new AttackVO(attack);
						judgeRepeat(attackVO,srcPositionList,desPositionList);
						jsonObject.put("attack", attackVO);
						System.out.println("positionDetail:"+attackVO.getSrcLongitude()+"-"+attackVO.getSrcLatitude()+"----"+attackVO.getDesLongitude()+"-"+attackVO.getDesLatitude());
						TextMessage returnMessage = new TextMessage(jsonObject.toString());
						session.sendMessage(returnMessage);
						Thread.currentThread().sleep(350);
						if(i==3){
							return;
						}
					}
				}
			}
			
		}
		
	}
	public void judgeRepeat(AttackVO attackVO,LinkedList srcPositionList,LinkedList desPositionList){
		String srcPosition=attackVO.getSrcLongitude()+"--"+attackVO.getSrcLatitude();
		String desPosition=attackVO.getDesLongitude()+"--"+attackVO.getDesLatitude();
		//对原经纬度进行分析
		int srcPositionListSize=srcPositionList.size();
		if(srcPositionListSize==0){
			srcPositionList.add(srcPosition);
		}else if(srcPositionListSize<maxSize){
			boolean flag=srcPositionList.contains(srcPosition);
			if(flag){//如果已经存在了就要去重（方法是：经纬度加1或者-1（视经纬度的大小进行调整））
				srcPosition=updateRepeat(srcPosition,srcPositionList);
				String[] positionArray=srcPosition.split("--");
				srcPositionList.add(srcPosition);
				attackVO.setSrcLongitude(positionArray[0]);
				attackVO.setSrcLatitude(positionArray[1]);
			}else{
				srcPositionList.add(srcPosition);
			}
		}else{
			boolean flag=srcPositionList.contains(srcPosition);
			if(flag){//如果已经存在了就要去重（方法是：经纬度加1或者-1（视经纬度的大小进行调整））
				srcPosition=updateRepeat(srcPosition,srcPositionList);
				String[] positionArray=srcPosition.split("--");
				srcPositionList.add(srcPosition);
				srcPositionList.removeFirst();
				attackVO.setSrcLongitude(positionArray[0]);
				attackVO.setSrcLatitude(positionArray[1]);
			}else{
				srcPositionList.add(srcPosition);
				srcPositionList.removeFirst();
			}
		}
		//对目的经纬度进行分析
		int desPositionListSize=desPositionList.size();
		if(desPositionListSize==0){
			desPositionList.add(desPosition);
		}else if(desPositionListSize<maxSize){
			boolean flag=desPositionList.contains(desPosition);
			if(flag){//如果已经存在了就要去重（方法是：经纬度加1或者-1（视经纬度的大小进行调整））
				desPosition=updateRepeat(desPosition,desPositionList);
				String[] positionArray=desPosition.split("--");
				desPositionList.add(desPosition);
				attackVO.setDesLongitude(positionArray[0]);
				attackVO.setDesLatitude(positionArray[1]);
			}else{
				desPositionList.add(desPosition);
			}
		}else{
			boolean flag=desPositionList.contains(desPosition);
			if(flag){//如果已经存在了就要去重（方法是：经纬度加1或者-1（视经纬度的大小进行调整））
				desPosition=updateRepeat(desPosition,desPositionList);
				String[] positionArray=desPosition.split("--");
				desPositionList.add(desPosition);
				desPositionList.removeFirst();
				attackVO.setDesLongitude(positionArray[0]);
				attackVO.setDesLatitude(positionArray[1]);
			}else{
				desPositionList.add(desPosition);
				desPositionList.removeFirst();
			}
		}
	}
	public String updateRepeat(String position,LinkedList positionList){
		if(StringUtils.isEmpty(position)||positionList==null||positionList.isEmpty()){
			try {
				throw new Exception("去重经纬度代码逻辑错误");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		boolean flag=positionList.contains(position);
		if(!flag){
			return position;
		}
		//分割经纬度
		//经度范围[-180,180],纬度范围[-90,90]
		String [] positionArray=position.split("--");
		String longitudeStr=positionArray[0];
		String latitudeStr=positionArray[1];
		//1.先用经度进行去重
		double longitude=Double.parseDouble(longitudeStr);
		if(longitude>(180-maxSize)){
			longitude-=0.1;
		}else if(longitude<(-180+maxSize)){
			longitude+=0.1;
		}else{//longitude在[(-180+maxSize),(180-maxSize)]区间采用加1的策略
			longitude+=0.1;
		}
		longitude=Double.parseDouble(NumberUtils.getPointAfterOneNumber(longitude));
		position=longitude+"--"+latitudeStr;
		flag=positionList.contains(position);
		if(!flag){
			return position;
		}
		//2.再用纬度进行去重
		double latitude=Double.parseDouble(latitudeStr);
		if(latitude>(90-maxSize)){
			latitude-=0.1;
		}else if(latitude<(-90+maxSize)){
			latitude+=0.1;
		}else{//longitude在[(-90+maxSize),(90-maxSize)]区间采用加1的策略
			latitude+=0.1;
		}
		latitude=Double.parseDouble(NumberUtils.getPointAfterOneNumber(latitude));
		position=longitude+"--"+latitude;
		flag=positionList.contains(position);
		if(!flag){
			return position;
		}
		return updateRepeat(position,positionList);
	}
	public static void main(String[] args) {
//		String position="1.3-1.8";
//		String [] positionArray=position.split("-");
//		System.out.println(positionArray[0]);
//		System.out.println(positionArray[1]);
//		double d = 3.1415926;
//		double d=3.1415926;
//		String result = String .format("%.1f",d);
//		System.out.println(result);
//		String text=WafAPIWorker.getWafLogWebsecCurrent(2);
//		System.out.println("getFirstWafData:"+text);
		String text="{\"wafLogWebsecList\": ["+
  "{"
+    "\"logId\": 69062,"+
    "\"resourceId\": 10001,"+
    "\"resourceUri\": \"/WAF/DEVICE/CTBRI/Group001\","+
    "\"resourceIp\": \"172.16.100.210\","+
    "\"siteId\": \"1472538509\","+
    "\"protectId\": \"2472538509\","+
    "\"dstIp\": \"219.141.189.183\","+
    "\"dstPort\": \"80\","+
    "\"srcIp\": \"106.184.4.137\","+
    "\"srcPort\": \"60242\","+
    "\"method\": \"GET\","+
    "\"domain\": \"None\","+
    "\"uri\": \"/docs/\","+
    "\"alertlevel\": \"MEDIUM\","+
    "\"eventType\": \"SFRUUOWNj+iurui/neiDjA==\","+
    "\"statTime\": \"2016-09-07 15:55:29\","+
    "\"policyId\": \"1\","+
    "\"ruleId\": \"0\","+
    "\"action\": \"Block\","+
    "\"block\": \"No\","+
    "\"blockInfo\": \"None\","+
    "\"alertinfo\": \"cmVxdWVzdCBleGNlZWRzIHN5c3RlbSdzIGxpbWl0\","+
    "\"proxyInfo\": \"None\","+
    "\"characters\": \"Tm9uZQ==\","+
    "\"countNum\": \"1\","+
    "\"protocolType\": \"HTTP\","+
    "\"wci\": \"Tm9uZQ==\","+
    "\"wsi\": \"Tm9uZQ==\""+
  "}"+
"],\"currentId\":10}";
//		System.out.println(text);
//		JSONObject json=JSONObject.fromObject(text);
//		int currentId=json.getInt("currentId");
//		System.out.println(currentId);
//		String text2=WafAPIWorker.getAllWafLogWebsecInTime(1+"", "date");
//		System.out.println(text2);
		String dataText=WafAPIWorker.getAllWafLogWebsecInTime(70677);
		JSONObject json = JSONObject.fromObject(dataText);
		JSONArray array = (JSONArray) json.get("wafLogWebsecList");
		if(null!=array&&array.size()==0){
			System.out.println("size==0");
		}
	}
}