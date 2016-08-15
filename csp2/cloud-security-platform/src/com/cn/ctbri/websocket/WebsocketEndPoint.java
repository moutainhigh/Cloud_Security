package com.cn.ctbri.websocket;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
/**
 * 创 建 人  ：   于永波
 * 创建日期：   2014-12-30
 * 描        述：    WebSocket服务器消息监听控制器
 * 版        本：   1.0
 */
public class WebsocketEndPoint extends TextWebSocketHandler {  
    
      
    @Override  
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {  
          
        super.handleTextMessage(session, message);  
        System.out.println("GOMA === > WebSocketEndPoint.handlerTextMessage...");  
          
        TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");  
        session.sendMessage(returnMessage);  
          
    }  
}  