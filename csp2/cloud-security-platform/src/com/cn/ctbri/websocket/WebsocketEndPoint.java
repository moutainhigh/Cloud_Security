package com.cn.ctbri.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.taskdefs.Sleep;
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
		String receiveMsg=message.getPayload();
		//为了安全做的一个类似token认证
		if("135de9e2fb6ae653e45f06ed18fbe9a7".equals(receiveMsg)){
			
//			while(true){
//				System.out.println("testxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//				perSendData(session,message);
//			}
			WafAPIWorker worker = new WafAPIWorker();
			//第一次读取数据
			boolean firstRead=true;
			long startReadTime=0;
			String dataText =null;
			do{
				if(firstRead){//第一次读取数据
					startReadTime=System.currentTimeMillis();
					dataText = getFirstWafData();
				}else{
					long currentReadTime=System.currentTimeMillis();
					int seconds=(int) ((currentReadTime-startReadTime)/1000);
					int minute=(int) ((currentReadTime-startReadTime)/1000/60);
					dataText=getWafData(minute);
					System.out.println("seconds:"+seconds+"minute:"+minute+"dataText:"+dataText);
					startReadTime=currentReadTime;
				}
				JSONObject json = JSONObject.fromObject(dataText);
				JSONArray array = (JSONArray) json.get("wafLogWebsecList");
				if(null!=array&&array.size()==0){
					Thread.sleep(1000*60*3);
					continue;
				}
				perSendData(session,message,array);
				firstRead=false;
			}while(!firstRead);
		}
	}
	class DateComparator implements Comparator<Date>{

		@Override
		public int compare(Date o1, Date o2) {
			return (int) (o1.getTime()-o2.getTime());
		}
		
	}
	public String getFirstWafData(){
		WafAPIWorker worker = new WafAPIWorker();
		long startTimeTest=System.currentTimeMillis();
		String text = worker.getAllWafLogWebsecInTime("3", "date");
		return text;
	}
	public String getWafData(int minute){
		WafAPIWorker worker = new WafAPIWorker();
		String text = worker.getAllWafLogWebsecInTime(minute+"", "minute");
		return text;
	}
	
	public void perSendData(WebSocketSession session,TextMessage message,JSONArray array) throws IOException, InterruptedException{
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
						jsonObject.put("attack", attackVO);
						System.out.println("jsonObject:"+jsonObject.toString());
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
						jsonObject.put("attack", attackVO);
						System.out.println("jsonObject:"+jsonObject.toString());
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
	 
}