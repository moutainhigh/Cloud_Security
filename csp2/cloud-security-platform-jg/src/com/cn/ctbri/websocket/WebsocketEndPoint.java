package com.cn.ctbri.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IIPPositionService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.MapUtil;
import com.cn.ctbri.util.NumberUtils;
import com.cn.ctbri.vo.AttackVO;

/**
 * 创 建 人 ： 于永波 创建日期： 2014-12-30 描 述： WebSocket服务器消息监听控制器 版 本： 1.0
 */
public class WebsocketEndPoint extends TextWebSocketHandler {
	/**
	 * 数据解析来源信息状态值
	 * 0：未知数据，或者初始数据，1：电信接口数据，2：百度接口通过ip查询出经纬度数据，但是不能根据经纬度查询出国家城市信息，3
	 * ：百度接口不仅通过ip将经纬度查询出来，还根据经纬度将城市信息查询出来
	 */
	public final int INITIALIZE = 0;
	public final int SOURCE_DIANXIN = 1;
	public final int SOURCE_BAIDU_IP = 2;
	public final int SOURCE_BAIDU_CITY = 3;

	@Autowired
	IIPPositionService ipPositionService;
	@Autowired
	IAssetService assetService;
	// 存储格式（IP,IPPosition）
	static Map<String, IPPosition> ipPositionMap = null;
	private long id = 0;

	private static int maxSize = 20;
	WafAPIWorker worker = new WafAPIWorker();

	public synchronized Map<String, IPPosition> getIPPositions() {
		if (ipPositionMap == null) {
			ipPositionMap = ipPositionService.findIPPositions();
		}
		return ipPositionMap;

	}

	public void deleteIp(String ip) {
		ipPositionService.delete(ip);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		
	}

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		User user  = (User) session.getAttributes().get("globle_user");
		long startId = 0;
		String receiveMsg = message.getPayload();
		// System.out.println("receiveMsg+++"+receiveMsg);
		if (StringUtils.isNotEmpty(receiveMsg)) {
			String[] arrayToken = receiveMsg.split("--");
			// 为了安全做的一个类似token认证
			if (arrayToken != null && arrayToken[0] != null
					&& "135de9e2fb6ae653e45f06ed18fbe9a7".equals(arrayToken[0])) {
				startId = Long.parseLong(arrayToken[1]);
				// 源端的经纬度
				LinkedList<String> srcPositionList = new LinkedList<String>();
				// 目的端的经纬度
				LinkedList<String> desPositionList = new LinkedList<String>();
				boolean flag = true;
				boolean flag2=true;//加一个初始条件判断是不是第一次进入循环，主要原因是之前的代码断开连接靠异常处理，如果没有数据会陷入死循环，浪费连接
				boolean initFlag=true;//第一次需要初始化8条数据需要用
				String dataText = null;
				while (flag) {
					//System.out.println("startId:"+startId);
					flag = session.isOpen();
					//System.out.println("flag第一次："+flag);
					try {
						int userid=user.getId();
						int type=user.getType();
						//由于type功能无法使用，为了临时方便看效果加上该条
						if(user.getName().equals("anquanbang")||user.getName().equals("anquanbangtest")){
							type=0;
						}
						if(user.getName().equals("anquanbang_demo")){
							type=4;
						}
						List<String>addrList=new ArrayList<String>();
						if(type!=0&&type!=4){
							addrList=assetService.findDomainByUserId(userid);
						}
						dataText = getWafData(startId,addrList,type);
						// System.out.println("dataText"+dataText);
						JSONObject json = JSONObject.fromObject(dataText);
						JSONArray array = (JSONArray) json
								.get("wafLogWebsecList");
						//注意下面if判断只会第一次调用的时候执行你 
						if(flag2){
							//第一次载入500条如果没有数据默认资产没有报警数据，直接跳出循环不再监听
							if(array.size()==0){
								flag=false;
							}
							flag2=false;
						}
						//System.out.println("aaaaaa:"+array.size());
						startId = json.getLong("currentId");
						if (null != array && array.size() == 0) {
							// System.out.println("++++++++");
							Thread.sleep(1000 * 10);
							continue;
						}
						flag = perSendData(session, message, array,
								srcPositionList, desPositionList,initFlag);
						initFlag=false;
						// System.out.println(message.toString());
						// System.out.println(array);
					} catch (Exception ex) {
						flag=false;
						ex.printStackTrace();
					}
				}
			}
		}
	}

	class DateComparator implements Comparator<Date> {

		public int compare(Date o1, Date o2) {
			return (int) (o1.getTime() - o2.getTime());
		}

	}

	public String getFirstWafData() {
		String text = worker.getWafLogWebsecCurrent(500);
		return text;
	}

	public String getWafData(long currentId,List<String> addrList,int type) {
		WafAPIWorker worker = new WafAPIWorker();
		String text = worker.getAllWafLogWebsecInTime(currentId, addrList,type);
		return text;
	}

	public boolean perSendData(WebSocketSession session, TextMessage message,
			JSONArray array, LinkedList srcPositionList,
			LinkedList desPositionList,boolean initFlag) throws IOException,
			InterruptedException {
		if (array == null) {
			return true;
		}
		long size = array.size();
		// System.out.println("array.size"+size);
		/**
		 * Date：外层Map以时间作为key，时间精确到秒，每秒可能产生几百条数据量
		 * String：内层Map以攻击类型作为Key，为了便于统计同一时间产生不同类型的攻击数量
		 * LinkedList<Attack>存储同一时间下，同一攻击类型的详细攻击信息
		 */
		Map<Date, HashMap<Integer, LinkedList<Attack>>> attackSortMap = new TreeMap<Date, HashMap<Integer, LinkedList<Attack>>>(
				new DateComparator());
		for (int i = 0; i < size; i++) {
			// System.out.println("i:"+i);
			JSONObject obj = (JSONObject) array.get(i);
			String srcIP = obj.getString("srcIp").trim();
			String desIP = obj.getString("dstIp").trim();
			if ("0.0.0.0".equals(srcIP) || "0.0.0.0".equals(desIP)) {
				continue;
			}
			String desPort = obj.getString("dstPort");
			Date startTime = DateUtils.stringToDateNYRSFM(obj
					.getString("statTime"));
			// byte[] base64Bytes =
			// Base64.decodeBase64(obj.getString("eventType").getBytes());
			byte[] base64Bytes = obj.getString("eventType").getBytes();
			String eventType = new String(base64Bytes, "UTF-8");
			Integer typeCode = EventTypeCode.typeToCodeMap.get(eventType);
			String srcLongitude = null;
			String srcLatitude = null;
			String desLongitude = null;
			String desLatitude = null;
			String srcName = null;
			String desName = null;
			IPPosition srcIPPosition = getIPPositions().get(srcIP);
			// System.out.println("src前"+srcIPPosition.getIp()+" ;"+srcIPPosition.getLongitude()+" ;"+srcIPPosition.getLatitude());
			// 1.优先使用百度接口根据ip查询经纬度
			if (null == srcIPPosition || srcIPPosition.getLongitude() == null
					|| srcIPPosition.getLatitude() == null) {// 数据库中不存在
				// System.out.println("++++++++");
				// System.out.println(srcIPPosition);
				deleteIp(srcIP);
				// System.out.println("++++++++");
				srcIPPosition = new IPPosition();

				srcIPPosition.setSourceStatus(INITIALIZE);
				ipPositionMap.put(srcIP, srcIPPosition);
				// 1.先调电信的接口
				String ipMessage = WafAPIWorker.getLocationFromIp(srcIP);
				JSONObject ipJson = JSONObject.fromObject(ipMessage);
				// 存在数据的情况返回数据格式：{"ip":"1.54.44.1","latitude":"16.0000","longtitude":"106.0000","continent":"亚洲","country":"越南","city":""}
				// 不存在数据的情况下返回的数据格式:{"ip":"1.2.2.3"}
				try {
					srcLatitude = ipJson.getString("latitude");
				} catch (Exception e) {
					// 电信接口不存在数据
				}
				if (StringUtils.isNotEmpty(srcLatitude)) {// 电信接口存在数据
					srcLongitude = ipJson.getString("longtitude");
					String wafCountry = ipJson.getString("country");
					String wafCity = ipJson.getString("city");
					srcName = wafCountry + wafCity;
					srcIPPosition.setIp(srcIP);
					// System.out.println("++++++++++++");
					// System.out.println(srcLongitude+srcLatitude);
					srcIPPosition.setRegisterTime(new Date());
					srcIPPosition.setLongitude(srcLongitude);
					srcIPPosition.setLatitude(srcLatitude);
					srcIPPosition.setCountryProvince(srcName);
					srcIPPosition.setSourceStatus(SOURCE_DIANXIN);
					ipPositionService.saveIPPosition(srcIPPosition);
					continue;
				} else {// 电信接口不存在数据
						// 2.电信接口如果不存在的话，再掉百度的接口

					// 源经纬度
					String srcPosition = MapUtil.getPositionByIp(srcIP);
					srcIPPosition.setIp(srcIP);
					srcIPPosition.setRegisterTime(new Date());
					// System.out.println("srcPosition"+srcPosition);
					if (null != srcPosition) {
						String[] positionArray = srcPosition.split(",");
						srcLongitude = positionArray[0];
						srcLatitude = positionArray[1];
						srcIPPosition.setLongitude(srcLongitude);
						srcIPPosition.setLatitude(srcLatitude);
						srcIPPosition.setSourceStatus(SOURCE_BAIDU_IP);
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
						srcIPPosition.setSourceStatus(SOURCE_BAIDU_CITY);
						ipPositionService.saveIPPosition(srcIPPosition);
						continue;
					}

				}

			} else {// 已经存在于数据库中
				srcLongitude = srcIPPosition.getLongitude();
				srcLatitude = srcIPPosition.getLatitude();
				srcName = srcIPPosition.getCountryProvince();
			}
			// System.out.println("src后"+srcIPPosition.getIp()+" ;"+srcIPPosition.getLongitude()+" ;"+srcIPPosition.getLatitude());
			// 1.优先使用百度接口根据ip查询经纬度
			// 分析目标ip
			IPPosition desIPPosition = getIPPositions().get(desIP);
			// System.out.println("des前"+desIPPosition.getIp()+" ;"+desIPPosition.getLongitude()+" ;"+desIPPosition.getLatitude());
			if (null == desIPPosition || desIPPosition.getLongitude() == null
					|| desIPPosition.getLatitude() == null) {// 数据库中不存在数据
				//System.out.println("执行了删除");
				deleteIp(desIP);
				desIPPosition = new IPPosition();
				desIPPosition.setSourceStatus(INITIALIZE);
				ipPositionMap.put(desIP, desIPPosition);

				// 1.先调电信的接口
				String ipMessage = WafAPIWorker.getLocationFromIp(desIP);
				JSONObject ipJson = JSONObject.fromObject(ipMessage);
				// 存在数据的情况返回数据格式：{"ip":"1.54.44.1","latitude":"16.0000","longtitude":"106.0000","continent":"亚洲","country":"越南","city":""}
				// 不存在数据的情况下返回的数据格式:{"ip":"1.2.2.3"}
				try {
					desLatitude = ipJson.getString("latitude");
				} catch (Exception e) {
				}
				if (StringUtils.isNotEmpty(desLatitude)) {// 电信接口存在数据
					desLongitude = ipJson.getString("longtitude");
					String wafCountry = ipJson.getString("country");
					String wafCity = ipJson.getString("city");
					desName = wafCountry + wafCity;
					desIPPosition.setIp(desIP);
					desIPPosition.setRegisterTime(new Date());
					desIPPosition.setLongitude(desLongitude);
					desIPPosition.setLatitude(desLatitude);
					desIPPosition.setCountryProvince(desName);
					desIPPosition.setSourceStatus(SOURCE_DIANXIN);
					ipPositionService.saveIPPosition(desIPPosition);
					continue;
				} else {// 电信接口不存在数据

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
						desIPPosition.setSourceStatus(SOURCE_BAIDU_IP);
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
						desIPPosition.setSourceStatus(SOURCE_BAIDU_CITY);
						ipPositionService.saveIPPosition(desIPPosition);
						continue;
					}

				}
			} else {// 数据库中已存在
				desLongitude = desIPPosition.getLongitude();
				desLatitude = desIPPosition.getLatitude();
				desName = desIPPosition.getCountryProvince();
			}
			// System.out.println("des后"+desIPPosition.getIp()+" ;"+desIPPosition.getLongitude()+" ;"+desIPPosition.getLatitude());
			// System.out.println("srcname:"+srcName+";desName"+desName);
			if (StringUtils.isNotEmpty(srcName)
					&& StringUtils.isNotEmpty(desName)) {
				// 组装数据
				Attack attack = new Attack();
				attack.setSrcLongitude(srcLongitude);
				attack.setSrcLatitude(srcLatitude);

				attack.setDesLongitude(desLongitude);
				attack.setDesLatitude(desLatitude);
				// System.out.println(srcLatitude+srcLatitude+desLongitude);
				attack.setDesName(desName);
				attack.setSrcName(srcName);
				attack.setStartTime(startTime);
				attack.setType(eventType);
				attack.setTypeCode(typeCode);
				attack.setSrcIP(srcIP);
				attack.setDesIP(desIP);
				attack.setPort(desPort);
				HashMap<Integer, LinkedList<Attack>> attackListMap = attackSortMap
						.get(startTime);
				if (attackListMap == null || attackListMap.isEmpty()) {
					attackSortMap.put(startTime,
							new HashMap<Integer, LinkedList<Attack>>());
				}
				LinkedList<Attack> attackList = attackSortMap.get(startTime)
						.get(eventType);
				if (attackList == null || attackList.isEmpty()) {
					attackSortMap.get(startTime).put(
							EventTypeCode.typeToCodeMap.get(eventType),
							new LinkedList<Attack>());
				}
				attackSortMap.get(startTime)
						.get(EventTypeCode.typeToCodeMap.get(eventType))
						.add(attack);
			}
		}
		JSONObject jsonObject = new JSONObject();
		ArrayList<AttackCount> attackCountList = null;
		int index =0;
		for (Entry<Date, HashMap<Integer, LinkedList<Attack>>> entry : attackSortMap
				.entrySet()) {
			/**
			 * 存储同一时间内各个攻击类型的数量
			 */
			index++;
			attackCountList = new ArrayList<AttackCount>();
			HashMap<Integer, LinkedList<Attack>> attackMap = entry.getValue();
			for (Entry<Integer, LinkedList<Attack>> attackEntry : attackMap
					.entrySet()) {
				attackCountList.add(new AttackCount(EventTypeCode.codeToTypeMap
						.get(attackEntry.getKey()), attackEntry.getValue()
						.size()));
			}
			/**
			 * 随机读取同一时间内的三条数据，推送到前端
			 */
			int includeType = attackMap.size();// 同一时间内的攻击类型
			// 1.如果同一时间攻击类型>=3种类型
			if (includeType > 2) {
				int[] randomArray = new int[3];
				for (int i = 0; i < 3; i++) {
					Random random = new Random();
					randomArray[i] = random.nextInt(includeType);
				}
				Arrays.sort(randomArray);
				int i = 0;
				for (Entry<Integer, LinkedList<Attack>> attackListEntry : attackMap
						.entrySet()) {
					if (i == randomArray[0] || i == randomArray[1]
							|| i == randomArray[2]) {
						LinkedList<Attack> attackList = attackListEntry
								.getValue();
						Attack attack = attackList.get(0);
						attack.setId(id);
						if (id < Long.MAX_VALUE) {
							id++;
						} else {
							id = 0;
						}
						if (i == 2) {
							attack.setAttackCount(attackCountList);
						}
						AttackVO attackVO = new AttackVO(attack);
						judgeRepeat(attackVO, srcPositionList, desPositionList);
						jsonObject.put("attack", attackVO);
						// System.out.println("positionDetail:"+attackVO.getSrcLongitude()+"-"+attackVO.getSrcLatitude()+"----"+attackVO.getDesLongitude()+"-"+attackVO.getDesLatitude());
						TextMessage returnMessage = new TextMessage(
								jsonObject.toString());
						try {
							session.sendMessage(returnMessage);
						} catch (Exception e) {
							e.printStackTrace();
							return false;
						}
						if((!initFlag)||index>8){
							Thread.currentThread().sleep(350);
						}						
					}
					i++;

				}
			} else {
				// 2.如果同一时间攻击类型<3种类型,从开始找三个数据就ok了
				int i = 0;
				for (Entry<Integer, LinkedList<Attack>> attackEntry : attackMap
						.entrySet()) {
					for (Attack attack : attackEntry.getValue()) {
						if (i == 0) {
							attack.setAttackCount(attackCountList);
						}
						i++;
						attack.setId(id);
						if (id < Long.MAX_VALUE) {
							id++;
						} else {
							id = 0;
						}
						AttackVO attackVO = new AttackVO(attack);
						judgeRepeat(attackVO, srcPositionList, desPositionList);
						jsonObject.put("attack", attackVO);
						TextMessage returnMessage = new TextMessage(
								jsonObject.toString());
						try {
							session.sendMessage(returnMessage);
						} catch (Exception e) {
							e.printStackTrace();
							return false;
						}
						if((!initFlag)||index>8){
							Thread.currentThread().sleep(350);
						}
						if (i == 3) {
							return true;
						}
					}
				}
			}

		}
		return true;

	}

	public void judgeRepeat(AttackVO attackVO, LinkedList srcPositionList,
			LinkedList desPositionList) {
		String srcPosition = attackVO.getSrcLongitude() + "--"
				+ attackVO.getSrcLatitude();
		String desPosition = attackVO.getDesLongitude() + "--"
				+ attackVO.getDesLatitude();
		// 对原经纬度进行分析
		int srcPositionListSize = srcPositionList.size();
		if (srcPositionListSize == 0) {
			srcPositionList.add(srcPosition);
		} else if (srcPositionListSize < maxSize) {
			boolean flag = srcPositionList.contains(srcPosition);
			if (flag) {// 如果已经存在了就要去重（方法是：经纬度加1或者-1（视经纬度的大小进行调整））
				srcPosition = updateRepeat(srcPosition, srcPositionList);
				String[] positionArray = srcPosition.split("--");
				srcPositionList.add(srcPosition);
				attackVO.setSrcLongitude(positionArray[0]);
				attackVO.setSrcLatitude(positionArray[1]);
			} else {
				srcPositionList.add(srcPosition);
			}
		} else {
			boolean flag = srcPositionList.contains(srcPosition);
			if (flag) {// 如果已经存在了就要去重（方法是：经纬度加1或者-1（视经纬度的大小进行调整））
				srcPosition = updateRepeat(srcPosition, srcPositionList);
				String[] positionArray = srcPosition.split("--");
				srcPositionList.add(srcPosition);
				srcPositionList.removeFirst();
				attackVO.setSrcLongitude(positionArray[0]);
				attackVO.setSrcLatitude(positionArray[1]);
			} else {
				srcPositionList.add(srcPosition);
				srcPositionList.removeFirst();
			}
		}
		// 对目的经纬度进行分析
		int desPositionListSize = desPositionList.size();
		if (desPositionListSize == 0) {
			desPositionList.add(desPosition);
		} else if (desPositionListSize < maxSize) {
			boolean flag = desPositionList.contains(desPosition);
			if (flag) {// 如果已经存在了就要去重（方法是：经纬度加1或者-1（视经纬度的大小进行调整））
				desPosition = updateRepeat(desPosition, desPositionList);
				String[] positionArray = desPosition.split("--");
				desPositionList.add(desPosition);
				attackVO.setDesLongitude(positionArray[0]);
				attackVO.setDesLatitude(positionArray[1]);
			} else {
				desPositionList.add(desPosition);
			}
		} else {
			boolean flag = desPositionList.contains(desPosition);
			if (flag) {// 如果已经存在了就要去重（方法是：经纬度加1或者-1（视经纬度的大小进行调整））
				desPosition = updateRepeat(desPosition, desPositionList);
				String[] positionArray = desPosition.split("--");
				desPositionList.add(desPosition);
				desPositionList.removeFirst();
				attackVO.setDesLongitude(positionArray[0]);
				attackVO.setDesLatitude(positionArray[1]);
			} else {
				desPositionList.add(desPosition);
				desPositionList.removeFirst();
			}
		}
	}

	public String updateRepeat(String position, LinkedList positionList) {
		if (StringUtils.isEmpty(position) || positionList == null
				|| positionList.isEmpty()) {
			try {
				throw new Exception("去重经纬度代码逻辑错误");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		boolean flag = positionList.contains(position);
		if (!flag) {
			return position;
		}
		// 分割经纬度
		// 经度范围[-180,180],纬度范围[-90,90]
		String[] positionArray = position.split("--");
		String longitudeStr = positionArray[0];
		String latitudeStr = positionArray[1];
		// 1.先用经度进行去重
		double longitude = Double.parseDouble(longitudeStr);
		if (longitude > (180 - maxSize)) {
			longitude -= 0.1;
		} else if (longitude < (-180 + maxSize)) {
			longitude += 0.1;
		} else {// longitude在[(-180+maxSize),(180-maxSize)]区间采用加1的策略
			longitude += 0.1;
		}
		longitude = Double.parseDouble(NumberUtils
				.getPointAfterOneNumber(longitude));
		position = longitude + "--" + latitudeStr;
		flag = positionList.contains(position);
		if (!flag) {
			return position;
		}
		// 2.再用纬度进行去重
		double latitude = Double.parseDouble(latitudeStr);
		if (latitude > (90 - maxSize)) {
			latitude -= 0.1;
		} else if (latitude < (-90 + maxSize)) {
			latitude += 0.1;
		} else {// longitude在[(-90+maxSize),(90-maxSize)]区间采用加1的策略
			latitude += 0.1;
		}
		latitude = Double.parseDouble(NumberUtils
				.getPointAfterOneNumber(latitude));
		position = longitude + "--" + latitude;
		flag = positionList.contains(position);
		if (!flag) {
			return position;
		}
		return updateRepeat(position, positionList);
	}
}