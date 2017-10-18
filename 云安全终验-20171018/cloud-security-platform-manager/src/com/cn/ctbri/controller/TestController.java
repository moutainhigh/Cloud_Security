package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cn.ctbri.websocket.WebsocketSessionStore;

@Controller
public class TestController {
	@RequestMapping(value="test.html",method=RequestMethod.GET)
	public ModelAndView test() throws IOException{
		Collection<WebSocketSession> values = WebsocketSessionStore.sessionStores.values();
		if(values != null){
			Iterator<WebSocketSession> it = values.iterator();
			while(it.hasNext()){
				WebSocketSession sess = it.next();
				if(sess.isOpen()){
					TextMessage returnMessage = new TextMessage(System.currentTimeMillis()+" received at server");
					sess.sendMessage(returnMessage);
				}
			}
		}
		ModelAndView view = new ModelAndView("index");
		return view;
	}
}
