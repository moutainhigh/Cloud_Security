package com.cn.ctbri.websocket;
import java.util.Map;
 
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
/**
 * 创 建 人  ：   于永波
 * 创建日期：   2014-12-30
 * 描        述：    WebSocket拦截器
 * 版        本：   1.0
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{
 
	 @Override  
	    public boolean beforeHandshake(ServerHttpRequest request,  
	            ServerHttpResponse response, WebSocketHandler wsHandler,  
	            Map<String, Object> attributes) throws Exception {  
	        System.out.println("GOMA ===> Before Handshake");  
	        if(request.getHeaders().containsKey("Sec-WebSocket-Extensions")){
	    		request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
	    	}
	        return super.beforeHandshake(request, response, wsHandler, attributes);  
	    }  
	  
	    @Override  
	    public void afterHandshake(ServerHttpRequest request,  
	            ServerHttpResponse response, WebSocketHandler wsHandler,  
	            Exception ex) {  
	        System.out.println("GOMA ===> After Handshake");  
	        super.afterHandshake(request, response, wsHandler, ex);  
	    }  
 
}