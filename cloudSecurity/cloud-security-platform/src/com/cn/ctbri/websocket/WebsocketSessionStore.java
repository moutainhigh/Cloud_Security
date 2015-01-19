package com.cn.ctbri.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

/**
 * 创 建 人  ：   于永波
 * 创建日期：   2014-12-30
 * 描        述：    WebSocket客户端Session存储，在客户端新增连接时存入，在后台推消息时使用。
 * 版        本：   1.0
 */
public class WebsocketSessionStore {

	public static Map<String, WebSocketSession> sessionStores = new HashMap<String, WebSocketSession>();
	
}
