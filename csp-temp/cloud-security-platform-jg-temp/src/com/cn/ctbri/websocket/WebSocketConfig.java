package com.cn.ctbri.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration  
@EnableWebSocket  
public class WebSocketConfig implements WebSocketConfigurer {  
  
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {  
        registry.addHandler(myhandler(), "/websocket").addInterceptors(myInterceptors());
        registry.addHandler(myhandler(), "/websocket").addInterceptors(myInterceptors()).withSockJS();  
//    	registry.addHandler(myhandler(), "/websocket");
//    	registry.addHandler(myhandler(), "/websocket").withSockJS();
    }  
  
    @Bean  
    public WebSocketHandler myhandler() {  
        return new WebsocketEndPoint();  
    }  
  
    @Bean  
    public HandshakeInterceptor myInterceptors() {  
        return new HandshakeInterceptor();  
    }  
}  
