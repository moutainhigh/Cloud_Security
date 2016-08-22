package com.cn.ctbri.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

/**
 * 创 建 人 ： 于永波 创建日期： 2014-12-30 描 述： WebSocket服务器消息监听控制器 版 本： 1.0
 */
public class WebsocketEndPoint extends TextWebSocketHandler {
	@Autowired
	IIPPositionService ipPositionService;
	// 存储格式（IP,IPPosition）
	static Map<String, IPPosition> ipPositionMap = null;

	public synchronized Map<String, IPPosition> getIPPositions() {
		if (ipPositionMap == null) {
			ipPositionMap = ipPositionService.findIPPositions();
		}
		return ipPositionMap;

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
//		WafAPIWorker worker = new WafAPIWorker();
//		String texts = worker.getWafEventTypeCount("1", "hour");
//		JSONArray array = JSONArray.fromObject(texts);
//		List<AttackCount> attackCountList = new ArrayList<AttackCount>();
//		for (int i = 0; i < array.size(); i++) {
//			JSONObject obj = (JSONObject) array.get(i);
//			byte[] base64Bytes = Base64.decodeBase64(obj.get("eventType")
//					.toString().getBytes());
//			String eventType = new String(base64Bytes, "UTF-8");
//			Integer typeCode = EventTypeCode.typeToCodeMap.get(eventType);
//			Integer count = (Integer) obj.get("count");
//			attackCountList.add(new AttackCount(typeCode, count));
//		}
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("wafEventTypeCount", attackCountList);
//		String resultJson = jsonObject.toString();// 转成json数据
//		session.sendMessage(new TextMessage(resultJson));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		String receiveMsg=message.getPayload();
		//为了安全做的一个类似token认证
		if("135de9e2fb6ae653e45f06ed18fbe9a7".equals(receiveMsg)){
			while(true){
				System.out.println("testxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				perSendData(session,message);
//				Thread.sleep(10000);
			}
		}
	}
	public void perSendData(WebSocketSession session,TextMessage message) throws IOException, InterruptedException{
		long startTime1 = System.currentTimeMillis();
		WafAPIWorker worker = new WafAPIWorker();
		String text = worker.getAllWafLogWebsecInTime("3", "date");
		JSONObject json = JSONObject.fromObject(text);
		JSONArray array = (JSONArray) json.get("wafLogWebsecList");
		long size = array.size();
//		List<Attack> attackList = new LinkedList<Attack>();
		for (int i = 0; i < size; i++) {
			JSONObject obj = (JSONObject) array.get(i);
			String srcIP = obj.getString("srcIp");
			String desIP = obj.getString("dstIp");
			String desPort = obj.getString("dstPort");
			String startTime = obj.getString("statTime");
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
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("attack", attack);
				TextMessage returnMessage = new TextMessage(jsonObject.toString());
				session.sendMessage(returnMessage);
				Thread.currentThread().sleep(350);
//				attackList.add(attack);
			}
		}
//		long endTime = System.currentTimeMillis();
//		System.out.println("时间差：" + (endTime - startTime1) / 1000);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("attackList", attackList);
//		TextMessage returnMessage = new TextMessage(jsonObject.toString());
//		session.sendMessage(returnMessage);
	}
}